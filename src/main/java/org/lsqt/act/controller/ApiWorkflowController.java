package org.lsqt.act.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.lsqt.act.ActUtil;
import org.lsqt.act.FastDFSUtil;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionFile;
import org.lsqt.act.model.ApproveOpinionFileQuery;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.Definition;
import org.lsqt.act.model.DefinitionQuery;
import org.lsqt.act.model.InstanceVariable;
import org.lsqt.act.model.InstanceVariableQuery;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeButtonQuery;
import org.lsqt.act.model.NodeForm;
import org.lsqt.act.model.NodeFormQuery;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.model.ProcessInstance;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunInstanceQuery;
import org.lsqt.act.model.Task;
import org.lsqt.act.model.TaskQuery;
import org.lsqt.act.service.ApproveOpinionFileService;
import org.lsqt.act.service.ApproveOpinionService;
import org.lsqt.act.service.DefinitionService;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.act.service.RuntimeService;
import org.lsqt.act.service.TaskService;
import org.lsqt.act.service.impl.ActFreemarkUtil;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.context.annotation.mvc.RequestMapping.View;
import org.lsqt.components.context.annotation.mvc.RequestPayload;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.lang.StringUtil;

import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.util.ResourceUtil;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.FileRenamePolicy;

import freemarker.template.Template;

/**
 * 移动端的审批接口
 * @author mmyuan
 *
 */
@Controller(mapping = { "/api/workflow" })
public class ApiWorkflowController {
	private static final Logger  log = LoggerFactory.getLogger(ApiWorkflowController.class);
	@Inject private DefinitionService definitionService;
	@Inject private RuntimeService runtimeService;
	@Inject private TaskService taskService;
	@Inject private ApproveOpinionService approveOpinionService ;
	@Inject private ApproveOpinionFileService approveOpinionFileService;
	@Inject private NodeUserService nodeUserService;
	@Inject private UserService userService;
	
	@Inject private Db db;
	@Inject private PlatformDb db2;
	
	@RequestMapping(mapping={"/update_instance_business_status","/m/update_instance_business_status"},text="更新业务状态")
	public Result updateInstanceBusinessStatus(String instanceId,String businessKey , String status,String statusDesc) {
		int cnt = 0;
		if(StringUtil.isNotBlank(instanceId,businessKey)) {
			cnt = db.executeUpdate("update ext_run_instance set business_status=?, business_status_desc=? where instance_id=? and business_key=?", status,statusDesc,instanceId,businessKey);
		}
		return Result.ok("更新影响行数:"+cnt,cnt);
	}
	
	@RequestMapping(mapping={"/update_instance_title","/m/update_instance_title"},text="更流程实例标题")
	public Result updateInstanceTitle(String instanceId,String businessKey , String title) {
		int cnt = 0;
		if(StringUtil.isNotBlank(instanceId,businessKey,title)) {
			RunInstanceQuery query = new RunInstanceQuery();
			query.setInstanceId(instanceId);
			query.setBusinessKey(businessKey);
			RunInstance model = db.queryForObject("queryForPage", RunInstance.class, query);
			if(model!=null) {
				model.setTitle(title);
				db.update(model, "title");
			}
			return Result.ok(String.format("更新影响行数:%s行",cnt));
		} else {
			return Result.fail("没有更新到流程标题");
		}
	}
	
	@RequestMapping(mapping={"/instance_delete","/m/instance_delete"},text="（重审）删除流程实例")
	public Result deleteInstance(String instanceId) {
		if(StringUtil.isNotBlank(instanceId )) {
			runtimeService.deleteProcessInstance(instanceId, "业务删除");
		}
		return Result.ok();
	}
	
	@RequestMapping(mapping={"/instance_delete_cascade_opinion","/m/instance_delete_cascade_opinion"},text="删除流程实例和审批意见")
	public Result deleteInstanceAndOpinion(String instanceId,String businessKey) {
		if(StringUtil.isNotBlank(instanceId )) {
			runtimeService.deleteProcessInstance(instanceId, "业务删除");
			approveOpinionService.deleteBy(instanceId, businessKey);
		}
		return Result.ok();
	}
	
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/start_by_definition_key", "/m/start_by_definition_key" }, text = "流程启动,按流程定义key")
	public Result startByDefinitionKey(String loginUserId, String definitionKey, String variables) {
		log.debug(String.format(" --- 正在发起流程,参数:loginUserId = %s, definitionKey = %s , variables = %s",loginUserId,definitionKey,variables));
		
		if (StringUtil.isBlank(loginUserId, definitionKey)) {
			return Result.fail("登陆用户id和流程定义key不能为空");
		}

		Map<String, Object> data = new HashMap<>();
		if (StringUtil.isNotBlank(variables)) {
			Map<String, Object> map = JSON.parseObject(variables, Map.class);
			data.putAll(map);
		}

		data.put(ActUtil.VARIABLES_START_USER_ID, loginUserId);

		ProcessInstance instance = null;
		try{
			instance = runtimeService.startProcessInstanceByKey(definitionKey, data);
			
			// 如果流程发起后，没有找到下一步审批用户，删除流程实例避免出现两个以上的单据任务
			if (instance != null && instance.getExtProperty() != null) {
				Object candidateUserIds = instance.getExtProperty().get(ProcessInstance.CANDIDATE_USER_IDS_KEY);
				if (candidateUserIds == null || StringUtil.isBlank(candidateUserIds.toString())) {
					deleteInstanceAndOpinion(instance.getId(), instance.getBusinessKey());
					return Result.fail(String.format("流程发起后没有找到下一步审批人 (业务单据主键=%s,流程定义编码=%s)",instance.getBusinessKey(),definitionKey));
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			return Result.fail("发起流程失败:"+ExceptionUtil.getStackTrace(e));
		}
		
		if(instance==null){
			return Result.fail("发起流程失败:没有产生流程实例");
		}
		return Result.ok(instance);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping={"/complete","/m/complete"},text="流程下一步,其中approveOpinion.approveAction字段很重要")
	public Result complete(Long loginUserId,String taskId,String variables,ApproveOpinion approveOpinion,String opinionAttachmentIds) {
		log.debug(String.format(" --- 流程下一步处理,参数:%s, %s ,%s ,%s ,%s", loginUserId, taskId, variables,
				approveOpinion.getApproveAction(), opinionAttachmentIds));

		if(StringUtil.isBlank(taskId)) {
			return Result.fail("登陆用户Id不能为空");
		}
		if(StringUtil.isBlank(taskId)) {
			return Result.fail("待办任务Id不能为空");
		}
		
		if(approveOpinion == null || StringUtil.isBlank(approveOpinion.getApproveAction())) {
			return Result.fail("审批动作参数不能为空");
		}
		 
		
		Map<String,Object> var= new HashMap<>();
		if(StringUtil.isNotBlank(variables)){
			var = JSON.parseObject(variables, Map.class);
		}
		
		//处理前端送过来的审批动作code
		String action = approveOpinion.getApproveAction();
		if (action != null && action.startsWith("button_type_")) {
			approveOpinion.setApproveAction(action.replace("button_type_", ""));
		}
	 
		String nextApproveUser = null;
		Map<String,Object> data = new HashMap<>();
		try{
			nextApproveUser = taskService.complete(loginUserId,taskId, var,approveOpinion);
			data.put("candidateUserIds", nextApproveUser);
		}catch(Exception ex){
			ex.printStackTrace();
			return Result.fail("下一步处理失败,错误详情："+ex.getMessage());
		}
	 
		// 更新意见附件关系
		if (approveOpinion.getId() != null && StringUtil.isNotBlank(opinionAttachmentIds)) {
			List<Long> ids = StringUtil.split(Long.class, opinionAttachmentIds, ",");
			if (ids != null && ids.size() > 0) {
				String text = StringUtil.join(ids, ",");
				db.executeUpdate(String.format("update ext_approve_opinion_file set opinion_id=? where id in (%s)", text),approveOpinion.getId());
			}
		}
		
		Result r =Result.ok();
		r.setMessage(nextApproveUser); //兼容已有业务代码
		r.setData(data);
		return r;
	}
	
	
	// -------------------------------------------- 待办相关 (PC端) ---------------------------------------------------
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/get_my_task_count", "/m/get_my_task_count" }, text = "给EKP门户使用的我的待办数")
	public Object getMyTaskCount(String loginNo,String showNumber) {
		if(StringUtil.isBlank(loginNo)) {
			return Result.fail("登陆账号不能为空");
		}
		
		UserQuery uquery = new UserQuery();
		uquery.setLoginNo(loginNo);
		User user = db2.queryForObject("queryForPage", User.class, uquery);
		if (user==null) {
			return Result.fail("找不到登陆账号为"+loginNo+"的用户");
		}

		TaskQuery query = new TaskQuery();
		query.setUserId(user.getUserId().toString());
		
		log.debug("获取待办，请求参数userId: " + query.getUserId());
		query.setPageIndex(0);
		query.setPageSize(1);
		Result rs = queryTaskPage(query);
		Page<Task> page = (Page<Task>) rs.getData();
		if ("true".equals(showNumber)) {
			return page.getTotal();
		} else {
			return Result.ok("请求成功",page.getTotal());
		}
	}
	
	@RequestMapping(mapping = { "/get_task_by_instance_id", "/m/get_task_by_instance_id" }, text="跟据流程实例获取当前活动任务")
	public Result getTaskByInstanceId(String instanceId) {
		
		log.debug("获取待办，请求参数: "+instanceId);

		Task task = taskService.getNextNewTask(instanceId);
		if(task!=null) {
			return Result.ok(task);
		}

		return Result.fail("没有找到当前流程实例为"+instanceId+"的活动任务");
	}
	
	@RequestMapping(mapping = { "/get_task", "/m/get_task" }, text="PC端:跟据业务主键和流程定义key得到（一个）待办.注：并发分支产生的待办，不适用此查询")
	public Result getTask(TaskQuery query) {
		
		log.debug("获取待办，请求参数: "+JSON.toJSONString(query, true));

		List<Task> list = taskService.queryForListDetail(query);
		if (list != null && list.size() == 1) {
			return Result.ok(list.get(0));
		}

		if (list == null || list.isEmpty()) {
			return Result.fail("任务已被（移动端或PC端）办理，请刷新列表页面");
		}
		if (list.size() > 1) {
			//return Result.fail("跟据业务主键和流程定义key，找到多条待办任务（请检查是否是并发分支产生的待办或垃圾数据）");
			return Result.ok(list.get(list.size()-1)); // 取最后一条记录
		}

		return Result.fail();
	}
	
	@RequestMapping(mapping = { "/get_instance_variable", "/m/get_instance_variable" }, text="PC端:获取流程发起数据")
	public Result getInstanceVariableByBusinessKey(String processInstanceId,String businessKey) {
		if (StringUtil.isBlank(businessKey)) {
			return Result.fail("业务主键不能为空");
		}
		InstanceVariableQuery query = new InstanceVariableQuery();
		query.setBusinessKey(businessKey);
		query.setInstanceId(processInstanceId);
		List<InstanceVariable> list = db.queryForList("queryForPage", InstanceVariable.class, query);
		if (list != null && list.size() == 1) {
			InstanceVariable model = list.get(0);
			if (StringUtil.isNotBlank(model.getVariableJson())) {
				return Result.ok(JSON.parseObject(model.getVariableJson(),Map.class));
			}
		}
		log.debug(" --- 流程业务主键数据大于1条");
		return Result.fail();
	}
	
	@Deprecated
	@RequestMapping(mapping = { "/query_for_page_load_candiate_users", "/m/query_for_page_load_candiate_users" }, text="PC端:获取我的待办(并返回任务的可审批人)")
	public Result queryForPageLoadCandiateUsers(TaskQuery query) {
		Page<Task> page = taskService.queryForPageDetail(query);
		//加载当前任务的审批人
		for(Task task: page.getData()) {
			List<ApproveObject> list = nodeUserService.getNodeUsers(Long.valueOf(query.getUserId()), task.getProcessDefinitionId(), task.getTaskDefinitionKey(),new HashMap<>());
			Set<String> set = new HashSet<>();
			for(ApproveObject e: list) {
				set.add(e.getId());
			}
			task.setCandidateUserIds(StringUtil.join(set, ","));
		}
		return Result.ok(page);
	}
	
	@RequestMapping(mapping = { "/query_for_page", "/m/query_for_page" }, text="PC端:获取我的待办1")
	@RequestPayload
	@Deprecated
	public Result queryForPage(TaskQuery query) {
		Page<Task> page = taskService.queryForPageDetail(query);
		
		prepareTaskUrl(query.getUserId(), page.getData());
		return Result.ok(page);
	}

	@RequestMapping(mapping = { "/task_page_pc", "/m/task_page_pc" },text="PC端：获取待办2(含流程标题、发起人信息)")
	@RequestPayload
	public Result queryForPageDetailForPc(TaskQuery query) {
		return queryTaskPage(query);
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping = { "/get_ekp_task_count", "/m/get_ekp_task_count" },text="PC端：EKP手机端显示的泡泡数字")
	public Map<String,Object> getEkpTaskCount(TaskQuery query) {
		Map<String,Object> map = new HashMap<>();
		map.put("value", "");
		map.put("objValue", "0");
		map.put("ok", false);
		
		if(StringUtil.isBlank(query.getLoginNo())){
			map.put("value", "loginNo参数不能为空");
			return map;
		}
		
		List<User> list = db2.queryForList("queryForPage", User.class, query);
		if(list == null || list.isEmpty()){
			map.put("value", "没有找到登陆账号为"+query.getLoginNo()+"的用户");
			map.put("objValue", "0");
			return map;
		}
		if (list.size() > 1) {
			map.put("value", "登陆账号为" + query.getLoginNo() + "的用户有多个，系统数据错误");
			map.put("objValue", "0");
			return map;
		}
		query.setUserId(list.get(0).getUserId().toString());
		query.setLoginNo(null);
		
		
		query.setType(TYPE_我的待办);
		Result rs= queryTaskPage(query);
		
		Page<Task> page = (Page<Task>)rs.getData();
		map.put("objValue", page.getTotal()+"");
		map.put("ok", true);
		return map;
	}
	
	private void prepareTaskUrl(String loginUserId, Collection<Task> data) {
		// 加载全局表单url
		Set<String> definitionIds = new HashSet<>();
		for(Task t: data) {
			definitionIds.add(t.getProcessDefinitionId());
		}
				
		NodeFormQuery filter = new NodeFormQuery();
		filter.setDataType(NodeForm.DATA_TYPE_GLOBAL_FORM);
		filter.setFormType(NodeForm.FORM_TYPE_URL);
		filter.setDefinitionIdList(new ArrayList<>(definitionIds));
		List<NodeForm> list = db.queryForList("queryForPage", NodeForm.class, filter);
		if(list!=null && list.size()>0) {
			for(Task t: data) {
				for (NodeForm e : list) {
					if(e.getDefinitionId().equals(t.getProcessDefinitionId())) {
						t.setFormUrlViewGlobal(e.getFormDetailUrl());
						t.setFormUrlEidtGlobal(e.getFormUrl());
						t.setFormUrlCustom(prepareCustomUrl(loginUserId,t,e.getCustomUrl()));
						
						break;
					}
				}
			}
		}
	}
	
	/**待办详情表单，内置变量处理**/
	String prepareCustomUrl(String loginUserId,Task task,String url) {
		log.debug(" --- loginUserId:"+loginUserId+" , url:"+url);
		String rs = "";
		if( StringUtil.isBlank(url)){
			return rs;
		}
		
		
		final Map<String, Object> root = new HashMap<>();
		root.put("taskId", StringUtil.isNotBlank(task.getId()) ? task.getId():"-1");
		root.put("processInstanceId", task.getProcessInstanceId());
		root.put("processDefinitionId", task.getProcessDefinitionId());
		
		if (StringUtil.isNotBlank(task.getBusinessKey())) {
			root.put("businessKey", task.getBusinessKey());
		} else {
			RunInstanceQuery inst = new RunInstanceQuery();
			inst.setInstanceId(task.getProcessInstanceId());
			RunInstance model = db.queryForObject("queryForPage", RunInstance.class, inst);
			if (model != null && StringUtil.isNotBlank(model.getBusinessKey())) {
				root.put("businessKey", model.getBusinessKey());
			} else {
				root.put("businessKey", "-1");
			}
		}
		
		if(StringUtil.isNotBlank(loginUserId)) {
			User loginUser = db2.getById(User.class, loginUserId);
			if(loginUser!=null) {
				root.put("loginUser", loginUser);
			}
		}
		try{
			Template tmpl = new Template("custom_url_"+task.getId(), new StringReader(url), ActFreemarkUtil.FTL_CONFIGURATION);
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			tmpl.process(root, writer);
	
			writer.flush();
			writer.close();
			
			rs =  stringWriter.toString().trim();
		}catch(Exception ex) {
			ex.printStackTrace();
		}
		
		return rs;
	}
	// --------------------------------------  移动端  ------------------------------------------------
	//public static final String TYPE_全部="0";
	public static final String TYPE_我的待办="1";
	public static final String TYPE_我的已办="2";
	public static final String TYPE_抄送我的="3";
	public static final String TYPE_我发起的="4";
	
	@RequestMapping(mapping = { "/task_list", "/m/task_list" }, text="移动端：获取待办(含流程标题、发起人信息)")
	public Result queryForListDetail(TaskQuery query) {
		query.setEnableMobile(Definition.ENABLE_MOBILE_ENABLE);
		return  queryTaskList(query) ;
		
	}
 
	@RequestMapping(mapping = { "/task_page", "/m/task_page" },text="移动端：获取待办(含流程标题、发起人信息)")
	public Result queryForPageDetail(TaskQuery query) {
		query.setEnableMobile(Definition.ENABLE_MOBILE_ENABLE);
		return queryTaskPage(query);
	}

	/**分类获取我的待办**/
	private Result queryTaskList(TaskQuery query) {
		if (TYPE_我的待办.equals(query.getType())) {
			List<Task> list = taskService.queryForListDetail(query);
			prepareTaskUrl(query.getUserId(),list);
			return Result.ok(list);
		}

		if (TYPE_抄送我的.equals(query.getType())) {
			if (StringUtil.isBlank(query.getUserId())) {
				return Result.fail("请输入抄送用户Id");
			}
			String ccUserId = query.getUserId();
			query.setUserIdList(Arrays.asList(ccUserId));
			query.setUserId(null);
			
			List<Task> list = db.queryForList("queryMyCopySend", Task.class, query);
			prepareTaskUrl(ccUserId, list);
			return Result.ok(list);
		}

		if (TYPE_我的已办.equals(query.getType())) {
			List<Task> list = db.queryForList("queryTask", Task.class, query);
			prepareTaskUrl(query.getUserId(),list);
			return Result.ok(list);
		}

		if (TYPE_我发起的.equals(query.getType())) {
			if (StringUtil.isBlank(query.getUserId())) {
				return Result.fail("请输入userId");
			}
			String startUserId = query.getUserId();
			query.setStartUserId(startUserId);
			query.setUserId(null);
			
			List<Task> list = db.queryForList("queryTask", Task.class, query);
			prepareTaskUrl(query.getStartUserId(), list);
			return Result.ok(list);
		}
		
		Page<Task> page = taskService.queryForPageDetail(query);
		prepareTaskUrl(query.getUserId(), page.getData());
		return Result.ok(page);
	}
	
	/**分类获取我的待办**/
	private Result queryTaskPage(TaskQuery query) {

		// 如果有时间戳，填充到createTimeBegin\createTimeEnd
		if (query.getCreateTimeBeginMillis() != null && query.getCreateTimeEndMillis() != null) {
			Date startB = new Date(query.getCreateTimeBeginMillis());
			Date startE = new Date(query.getCreateTimeEndMillis());
			query.setCreateTimeBegin(new SimpleDateFormat("yyyy-MM-dd").format(startB));
			query.setCreateTimeEnd(new SimpleDateFormat("yyyy-MM-dd").format(startE));
		}
		
		if (TYPE_我的待办.equals(query.getType())) {
			Page<Task> page = taskService.queryForPageDetail(query);
			prepareTaskUrl(query.getUserId(), page.getData());
			return Result.ok(page);
		}

		if (TYPE_抄送我的.equals(query.getType())) {
			if (StringUtil.isBlank(query.getUserId())) {
				return Result.fail("请输入抄送用户Id");
			}
			String ccUserId = query.getUserId();
			query.setUserIdList(Arrays.asList(ccUserId));
			query.setUserId(null);
			
			Page<Task> page = db.queryForPage("queryMyCopySend", query.getPageIndex(), query.getPageSize(), Task.class, query);
			prepareTaskUrl(ccUserId, page.getData());
			return Result.ok(page);
		}

		if (TYPE_我的已办.equals(query.getType())) {
			Page<Task> page = db.queryForPage("queryTask", query.getPageIndex(), query.getPageSize(), Task.class, query);
			prepareTaskUrl(query.getUserId(), page.getData());
			return Result.ok(page);
		}

		if (TYPE_我发起的.equals(query.getType())) {
			if (StringUtil.isBlank(query.getUserId())) {
				return Result.fail("请输入userId");
			}
			String startUserId = query.getUserId();
			query.setStartUserId(startUserId);
			query.setUserId(null);
			
			Page<Task> page = db.queryForPage("queryTask", query.getPageIndex(), query.getPageSize(), Task.class, query);
			prepareTaskUrl(query.getStartUserId(), page.getData());
			return Result.ok(page);
		}
		
		Page<Task> page = taskService.queryForPageDetail(query);
		prepareTaskUrl(query.getUserId(), page.getData());
		return Result.ok(page);
	}
	
	@RequestMapping(mapping = { "/task_count", "/m/task_count" },text="移动端：获取待办(含流程标题、发起人信息)")
	public Result queryForCount(TaskQuery query,String type) {
		query.setEnableMobile(Definition.ENABLE_MOBILE_ENABLE);
		query.setPageSize(1);
		query.setPageIndex(0);
		
		String userId = query.getUserId();
		if (StringUtil.isBlank(query.getUserId())) {
			return Result.fail("请输入用户Id");
		}
		
		//我的待办
		Map<String,Object> map = new HashMap<>();
		map.put("myToDoCount", taskService.queryForPageDetail(query).getTotal());
		
		//抄送我的
		String ccUserId = userId;
		query.setUserIdList(Arrays.asList(ccUserId));
		query.setUserId(null);
		map.put("ccToMeCount",db.queryForPage("queryMyCopySend", query.getPageIndex(), query.getPageSize(), Task.class, query).getTotal());
	

		//我的已办
		query.setUserId(userId);
		map.put("myDoneCount",db.queryForPage("queryTask", query.getPageIndex(), query.getPageSize(), Task.class, query).getTotal());
		

		//我的发起
		String startUserId = userId;
		query.setStartUserId(startUserId);
		query.setUserId(null);
		map.put("myStartedCount",db.queryForPage("queryTask", query.getPageIndex(), query.getPageSize(), Task.class, query).getTotal());
	
		return Result.ok(map);
	}
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	@RequestMapping(mapping = { "/definion_list", "/m/definion_list" },text="获取流程定义手机端")
	public Result queryForListDefinion() {
		DefinitionQuery query = new DefinitionQuery();
		query.setIsDisplayNewest(true);
		List<Definition> list = definitionService.queryForList(query);
		
		List<Definition> rs = new ArrayList<>();
		// 只显示移动端的数据
		if(list!=null && !list.isEmpty()){
			for(Definition e: list) {
				System.out.println(e.getId()+"="+e.getEnableMobile());
				if(Definition.ENABLE_MOBILE_ENABLE.equals(e.getEnableMobile())){
					rs.add(e);
				}
			}
		}
		return Result.ok(rs);
	}
	
	@RequestMapping(mapping = { "/opinion_list", "/m/opinion_list" },text="获取流程审批意见-不分页")
	public Result queryForListOpinion(ApproveOpinionQuery query) {  
		
		if(StringUtil.isBlank(query.getProcessInstanceId())) {
			return Result.fail("processInstanceId必填");
		}
		
		Collection<ApproveOpinion> data = clearVariablesJson(approveOpinionService.queryForList(query));
		fetchAttachmentInfo(query.getProcessInstanceId(), data);
		
		return Result.ok(data);
	}
	
	@RequestMapping(mapping = { "/opinion_page", "/m/opinion_page" },text="获取流程审批意见-分页")
	public Result queryForPageOpinion(ApproveOpinionQuery query) {
		
		if(StringUtil.isBlank(query.getProcessInstanceId())) {
			return Result.fail("processInstanceId必填");
		}
		
		Page<ApproveOpinion> page = approveOpinionService.queryForPage(query);
		page.setData(clearVariablesJson(page.getData()));
		fetchAttachmentInfo(query.getProcessInstanceId(), page.getData());
		
		return Result.ok(page);
	}
	
	@RequestMapping(mapping = { "/get_business_form_button_list", "/m/get_business_form_button_list" },text="获取业务表单的提审按钮(业务表单记录需要存储流程实例id)，processInstanceId必填")
	public Result getBusinessFormButtonList(String processInstanceId) {
		if(StringUtil.isBlank(processInstanceId)){
			return Result.fail("流程实例不能为空");
		}
		
		try{
			Task task = taskService.getNextNewTask(processInstanceId);
			
			NodeButtonQuery q=new NodeButtonQuery();
			q.setDefinitionId(task.getProcessDefinitionId());
			q.setTaskKey(task.getTaskDefinitionKey());
			q.setProcessInstanceId(processInstanceId);
			return getFormButtonList(q);
		}catch(Exception ex){
			ex.printStackTrace();
			return Result.fail(ExceptionUtil.getStackTrace(ex));
		}
	}
	
	@RequestMapping(mapping = { "/reject_to_starter_by_instance", "/m/reject_to_starter_by_instance" },text="跟据流程实例撤回单据（processInstanceId必填）")
	public Result rejectToStarterByInstance(String loginUserId,String processInstanceId) {
		if (StringUtil.isBlank(loginUserId)) {
			return Result.fail("登陆用户不能为空");
		}
		if (StringUtil.isBlank(processInstanceId)) {
			return Result.fail("流程实不能为空");
		}

		try {
			Task task = taskService.getNextNewTask(processInstanceId);

			ApproveOpinion op = new ApproveOpinion();
			op.setApproveAction(NodeButton.BTN_TYPE_START_USER_REBACK);

			String r = taskService.complete(Long.valueOf(loginUserId), task.getId(), new HashMap<>(), op);
			return Result.ok(r);
		} catch (Exception ex) {
			ex.printStackTrace();
			return Result.fail(ExceptionUtil.getStackTrace(ex));
		}
	}
	
	@RequestMapping(mapping = { "/get_form_button_list", "/m/get_form_button_list" },text="获取流程表单按钮，其中definitionId和taskKey必填")
	public Result getFormButtonList(NodeButtonQuery query) {
		
		
		// 判断当前是否是加签的按钮
		if (StringUtil.isNotBlank(query.getProcessInstanceId())) {
			ApproveOpinionQuery q = new ApproveOpinionQuery();
			q.setProcessInstanceId(query.getProcessInstanceId());
			List<ApproveOpinion> opinionList = db.queryForList("queryForPage", ApproveOpinion.class, q);
			if (opinionList != null && opinionList.size() > 0) {
				ApproveOpinion model = opinionList.get(opinionList.size()-1);
				if(model!=null && NodeButton.BTN_TYPE_ADD_ASSIGN.equals(model.getApproveAction())) {
					/*
					RunInstanceQuery inst= new RunInstanceQuery();
					inst.setInstanceId(query.getProcessInstanceId());
					RunInstance ri = db.queryForObject("queryForPage", RunInstance.class, inst);
					*/
					NodeButton btnSubmitApprove= new NodeButton();
					btnSubmitApprove.setBtnCode("button_type_agree");
					btnSubmitApprove.setBtnName("提审");
					btnSubmitApprove.setBtnType(1);
					btnSubmitApprove.setRemark("提审");
					btnSubmitApprove.setCreateTime(new Date());
					btnSubmitApprove.setUpdateTime(new Date());
					return Result.ok(Arrays.asList(btnSubmitApprove));
				}
			}
		}
		
		if(StringUtil.isBlank(query.getDefinitionId()) || StringUtil.isBlank(query.getTaskKey())){
			return Result.fail("流程定义ID或结点key不能为空(参数名:definitionId、taskKey)");
		}
		List<NodeButton> dbList = db.queryForList("queryForPage", NodeButton.class, query);
		for(NodeButton n: dbList) {
			n.setTaskName(query.getTaskName());
		}
		return Result.ok(dbList);
	}
	
	@RequestMapping(mapping = { "/get_history_approved_node", "/m/get_history_approved_node" },text="获取已审批的历史节点",view=View.JSON)
	public Result getHistoryApproveNode(String processInstanceId) {
		RunInstanceQuery query = new RunInstanceQuery();
		query.setInstanceId(processInstanceId);
		RunInstance inst = db.queryForObject("queryForPage", RunInstance.class, query);
		if(inst==null) {
			return Result.fail("没有找到流程实例");
		}
		
		// 获取拟稿节点
		NodeQuery nodeQuery = new NodeQuery();
		nodeQuery.setTaskBizType(Node.TASK_BIZ_TYPE_DRAFTNODE);
		nodeQuery.setDefinitionId(inst.getProcessDefinitionId());
		Node draftNode = db.queryForObject("queryForPage", Node.class, nodeQuery);
		
		List<ApproveOpinion> list= db.queryForList("getHistoryApproveNode", ApproveOpinion.class, processInstanceId);
		if(draftNode == null || list==null || list.isEmpty()) {
			return Result.fail("");
		}
		
		if(list!=null && list.size()>0) {
			ApproveOpinion vo = list.get(0);
			if (StringUtil.isNotBlank(vo.getDefinitionId())) {
				
				
				boolean hasDraft = false;
				if(draftNode!=null) {
					for(ApproveOpinion e : list) {
						if(draftNode.getTaskKey().equals(e.getApproveTaskKey())) {
							hasDraft = true;
							break;
						}
					}
					if(!hasDraft) {
						ApproveOpinion temp = new ApproveOpinion();
						temp.setApproveTaskKey(draftNode.getTaskKey());
						temp.setApproveTaskName(draftNode.getTaskName());
						list.add(temp);
					}
				}
				
			}
		}
		
		return Result.ok(list);
	}
	
	@RequestMapping(mapping = { "/query_page_user", "/m/query_page_user" }, text = "查询加签、转发、抄送的用户", view = View.JSON)
	public Result queryPageUser(UserQuery query) {
		Page<User> page = db2.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), User.class,query);
		return Result.ok(page);
	}
	
	private static List<String> WORD_SORT = Arrays.asList("A","B","C","D","E","F","G","H","I","J","K","L","M","N","O","P","Q","R","S","T","U","V","W","X","Y","Z");
	@RequestMapping(mapping = { "/query_list_user_order", "/m/query_list_user_order" }, text = "查询加签、转发、抄送的用户", view = View.JSON)
	public Result queryListUserSort(UserQuery query) {
		
		Map<String,List<User>> rs = new HashMap<>();
		List<User> list = db2.queryForList("queryForPage", User.class,query);
		if(list!=null && list.size()>0) {
			for(String w: WORD_SORT){
				rs.put(w, new ArrayList<>());
				for(User e: list) {
					if(StringUtil.isNotBlank(e.getLoginNo()) && w.equalsIgnoreCase(e.getLoginNo().substring(0,1))){
						rs.get(w).add(e);
					}
				}
			}
			
			List<User> other = new ArrayList<>();
			for(User e: list) {
				boolean isFind = false;
				for(String w: WORD_SORT){
					
					if(StringUtil.isNotBlank(e.getLoginNo()) && e.getLoginNo().substring(0,1).equalsIgnoreCase(w)){
						isFind = true;
					}
				}
				if(!isFind) {
					other.add(e);
				}
			}
			rs.put("#", other);
		}
		
		
		return Result.ok(rs);
	}
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(mapping = { "/upload", "/m/upload" },text="上传流程意见附件",view=View.JSON)
	public Result  uplodad() throws Exception{
		
		HttpServletRequest request = ContextUtil.getRequest();
		
		/* 表单example:
		 <form enctype="multipart/form-data" method = "post" action = "UploadServlet">  
	       <input type="text" name="userName" value="这里是一个常规字段!!!!"/>   
	       <p>上传文件1:<input type = "file" name = "File1" size = "20" maxlength = "20"><br></p>  
	       <p>上传文件2:<input type = "file" name = "File2" size = "20" maxlength = "20"><br></p>  
	       <p>上传文件3:<input type = "file" name = "File3" size = "20" maxlength = "20"><br></p>  
	       <p>上传文件4:<input type = "file" name = "File4" size = "20" maxlength = "20"><br></p>  
	       <input type = "submit" value = "上传">  
	    </form>
		 */
		
		String filePath = request.getServletContext().getRealPath("/") + "/upload";
        System.out.println(filePath);//输出存放上传文件所到的路径  
        File uploadPath = new File(filePath);  
        // 检查文件夹是否存在 不存在 创建一个  
        if (!uploadPath.exists()) {  
            uploadPath.mkdir();  
        } 
		
		int fileSize = 10;//文件最大允许10M
		//String savePath = "xxx";//文件的保存目录
		
		FileRenamePolicy fileNamePolicy = new FileRenamePolicy() {
			public File rename(File file) {
				String body = "";
				String ext = "";
				int pot = file.getName().lastIndexOf(".");
				
				
				if (pot != -1) {
					ext = file.getName().substring(pot);
				} else {
					ext = "";
				}
				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss_S");
				Date date = new Date();
				body = sdf.format(date) + "";
				
				String newName = body + ext;
				file = new File(file.getParent(), newName);
				return file;
			}
		};
		
		MultipartRequest mulit = new MultipartRequest(ContextUtil.getRequest(), filePath, 
				fileSize * 1024 * 1024, "UTF-8",fileNamePolicy);

		
		String businessKey = mulit.getParameter("businessKey");  
		//String opinionId = mulit.getParameter("opinionId");
		String definitionId = mulit.getParameter("definitionId");
		String definitionName = mulit.getParameter("definitionName");
		String definitionKey = mulit.getParameter("definitionKey");
		String approveProcessInstanceId = mulit.getParameter("approveProcessInstanceId");
		String approveTaskId = mulit.getParameter("approveTaskId");
		String approveTaskKey = mulit.getParameter("approveTaskKey");
		
		if(StringUtil.isBlank(approveProcessInstanceId)) {
			return Result.fail("流程实例参数【approveProcessInstanceId】不能为空");
		}
		
		List<ApproveOpinionFile> files = new ArrayList<>();
		
        log.debug("业务主键："+businessKey);
  
        int cnt = 0;
		Enumeration filesname = mulit.getFileNames();// 取得上传的所有文件(相当于标识)
		while (filesname.hasMoreElements()) {
			String id = (String) filesname.nextElement();// 标识
			String fileName = mulit.getFilesystemName(id); // 取得文件名
			String contentType = mulit.getContentType(id);// 文件类型
			String originalFileName = mulit.getOriginalFileName(id); //原始文件名
			if (fileName != null) {
				cnt++;
			}
			
			if(StringUtil.isNotBlank(fileName)) {
				String fastPath = FastDFSUtil.upload(uploadPath.getAbsolutePath()+"/"+fileName);
				
				ApproveOpinionFile appFile = new ApproveOpinionFile();
				
				appFile.setApproveProcessInstanceId(approveProcessInstanceId);
				appFile.setApproveTaskId(approveTaskId);
				appFile.setApproveTaskKey(approveTaskKey);
				appFile.setBusinessKey(businessKey);
				
				appFile.setDefinitionId(definitionId);
				appFile.setDefinitionKey(definitionKey);
				appFile.setDefinitionName(definitionName);
				
				appFile.setOriginalName(originalFileName);
				appFile.setPath(fastPath);
				appFile.setStoredName(fileName);
				db.save(appFile);
				files.add(appFile);
			}
			log.debug("文件名：" + fileName + " 文件类型： " + contentType+" 原始文件名："+originalFileName);
			//rs.put("serverPath", "/upload/"+fileName);
		}
		log.debug("共上传" + cnt + "个文件！");
		
		return Result.ok(files);
	}
	
	@RequestMapping(mapping = { "/download", "/m/download" },text="文件下载",view=View.JSON)
	public void download() {
		HttpServletRequest request = ContextUtil.getRequest();
		HttpServletResponse response = ContextUtil.getResponse();
		String attachId = request.getParameter("id");
		try{
			ApproveOpinionFile file = db.getById(ApproveOpinionFile.class, attachId);
			
			if(file!=null && StringUtil.isNotBlank(file.getPath())) {
				response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(file.getOriginalName(), "UTF-8"));
				response.setContentType("application/octet-stream; charset=UTF8");
			    
			    OutputStream os = response.getOutputStream();
			    os.write(FastDFSUtil.download(file.getPath()));
 
			    os.flush();
			    os.close();
			   
			}else {
				Result rs =Result.fail("意见附件path属性为空");
				response.getWriter().println(JSON.toJSONString(rs));
			}
		}catch(Exception ex) {
			ex.printStackTrace();
			Result rs =Result.fail("下载文件失败");
			try {
				response.getWriter().println(JSON.toJSONString(rs));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	void fetchAttachmentInfo(String processInstanceId,Collection<ApproveOpinion> data) {
		if(data!=null &&  StringUtil.isNotBlank(processInstanceId)) {
			//获取当前流程所有意见附件
			ApproveOpinionFileQuery query = new ApproveOpinionFileQuery();
			query.setApproveProcessInstanceId(processInstanceId);
			List<ApproveOpinionFile> files = approveOpinionFileService.queryForList(query);
			
			//HttpServletRequest request = ContextUtil.getRequest();
			for(ApproveOpinion e: data) {
				if(StringUtil.isNotBlank(e.getApproveTaskId())) {
					for(ApproveOpinionFile f: files) {
						if(StringUtil.isNotBlank(f.getApproveTaskId()) 
								&& f.getApproveTaskId().equals(e.getApproveTaskId())) {
							f.setPath(ResourceUtil.getValue("api.authority")+"/api/workflow/download?id="+f.getId());
							e.getAttachmentList().add(f);
						}
					}
				}
			}
		}
	}
	
	Collection<ApproveOpinion> clearVariablesJson(Collection<ApproveOpinion> data){
		if(data!=null) {
			for(ApproveOpinion e: data) {
				if(e!=null) {
					e.setVariablesJson(null);
				}
				if(StringUtil.isBlank(e.getApproveOpinion())){
					e.setApproveOpinion("");
				}
			}
		}
		return data;
	}
	
	// ------------------------------------------- 华丽的分割线 组织架构相关API ----------------------------------------
	@RequestMapping(mapping = { "/get_company", "/m/get_company" },text="获取分公司")
	public Result getCompany() throws IOException {
		 OrgQuery query = new OrgQuery();
		 query.setType(Org.TYPE_公司);
		 query.setSortOrder("asc");
		 query.setSortField("order_no");
		 return  Result.ok(db2.queryForList("queryForPage", Org.class, query));
	}
	
	@RequestMapping(mapping = { "/get_company_user_list", "/m/get_company_user_list" }, text = "获取分公司下面所有用户")
	public Result getCompanyUserList(Long orgId) throws IOException {
		Org org = db2.getById(Org.class, orgId);
		if (org == null) {
			return Result.fail(String.format("没有找到orgId为%s的公司", orgId));
		}
		if (StringUtil.isNotBlank(org.getNodePath())) {
			return Result.ok(db2.queryForList("getCompanyUsers", User.class, org.getNodePath() + "%"));
		}
		return Result.fail();
	}
	
	@RequestMapping(mapping = { "/query_user_for_list", "/m/query_user_for_list" },text="手机端搜索用户")
	public Result queryUserForList(UserQuery query) {
		List<User> list = userService.queryForList(query);
		
		// 加载用户的部门
		for(User u: list) {
			u.getExtProperty().put("department", null);
			
			// 用户的主部门
			Org mainOrg = db2.queryForObject("getUserMainOrg", Org.class, u.getUserId());
			if (mainOrg != null) {
				//u.setUserMainOrg(mainOrg);
				u.getExtProperty().put("department", mainOrg);
			} else {
				// 获取用户的部门
				OrgQuery query3 = new OrgQuery();
				query3.setUserId(u.getUserId());
				List<Org> data = db2.queryForList("queryForPage", Org.class, query3);
				u.setUserOrgList(data);
				
				if(data!=null && !data.isEmpty()){
					u.getExtProperty().put("department", data.get(0));
				}
			}
			
		}
		return Result.ok(list);
	}
	
}
