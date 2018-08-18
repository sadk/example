package org.lsqt.act.service.impl;

import java.io.BufferedWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.Definition;
import org.lsqt.act.model.DefinitionQuery;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeForm;
import org.lsqt.act.model.NodeFormQuery;
import org.lsqt.act.model.ProcessInstance;
import org.lsqt.act.model.ProcessInstanceHis;
import org.lsqt.act.model.ProcessInstanceQuery;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunInstanceQuery;
import org.lsqt.act.model.RunTask;
import org.lsqt.act.model.RunTaskQuery;
import org.lsqt.act.model.Task;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.act.service.RuntimeService;
import org.lsqt.act.service.TaskService;
import org.lsqt.act.service.support.CompleteCommand;
import org.lsqt.act.service.support.EkpTaskUtil;
import org.lsqt.act.service.support.StartCommand;
import org.lsqt.act.service.support.StartHandler;
import org.lsqt.act.service.support.TaskQueryUtil;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.file.PropertiesUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.controller.CodeUtil;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import freemarker.template.Template;



/**
 * 流程流转相关
 * @author mmyuan
 *
 */
@Service
public class RuntimeServiceImpl implements RuntimeService{
	static final Logger log = LoggerFactory.getLogger(RuntimeServiceImpl.class);
	
	private org.activiti.engine.RuntimeService actRuntimeService = ActUtil.getRuntimeService();
	private org.activiti.engine.IdentityService actIdentityService = ActUtil.getIdentityService();
	private org.activiti.engine.HistoryService actHistoryService = ActUtil.getHistoryService();
	private org.activiti.engine.RepositoryService actRepositoryService = ActUtil.getRepositoryService();
	private org.activiti.engine.TaskService actTaskService = ActUtil.getTaskService();
	
	@Inject private Db db;
	@Inject private PlatformDb db2;
	
	@Inject private NodeUserService nodeUserService;
	@Inject private TaskService taskService;
	
	// ---------常规启动流程
	public ActRunningContext startProcessInstanceById(String processDefinitionId) {
		return startProcessInstanceById(processDefinitionId, null, new HashMap<>());
	}

	public ActRunningContext startProcessInstanceById(String processDefinitionId,Map<String, Object> variables) {
		return startProcessInstanceById(processDefinitionId, null , variables);
	}
	
	public ActRunningContext startProcessInstanceById(String processDefinitionId,String businessKey,Map<String, Object> variables) { // startById核心启动方法
		
		ProcessDefinition def = actRepositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();

		return excuteStart(businessKey, variables, def);
	}

	public ActRunningContext startProcessInstanceByKey(String processDefinitionKey) {
		return startProcessInstanceByKey(processDefinitionKey,null,new HashMap<>());
	}

	public ActRunningContext startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables) {
		return startProcessInstanceByKey(processDefinitionKey,null,variables);
	}

	public ActRunningContext startProcessInstanceByKey(String processDefinitionKey,String businessKey, Map<String, Object> variables) {
		return startProcessInstanceByKey(processDefinitionKey,businessKey,null,variables);
	}
 
	public ActRunningContext startProcessInstanceByKey(String processDefinitionKey,String businessKey,String businessType, Map<String, Object> variables) { // startByKey核心启动方法
		
		DefinitionQuery query = new DefinitionQuery();
		query.setKey(processDefinitionKey);
		query.setSortOrder("desc");
		query.setSortField("B.DEPLOY_TIME_");
		List<Definition> list = db.queryForList("queryForPage", Definition.class, query); //获取最新的版本（也就是最新的发布版本)
		if (list != null && !list.isEmpty()) {
			ProcessDefinition def = actRepositoryService.createProcessDefinitionQuery().processDefinitionId(list.get(0).getId()).singleResult();
			if(def!=null) {
				return excuteStart(businessKey,variables,def);
			}
		}
		
		//ProcessDefinition def = actRepositoryService.createProcessDefinitionQuery().latestVersion().processDefinitionKey(processDefinitionKey).singleResult();
		
		return null;
		
	}


	
	private ActRunningContext createRunningContext(String businessKey, Map<String, Object> variable, ProcessDefinition def) {
		final ActRunningContext context = new ActRunningContext(db);
		
		final String loginUserId = TaskQueryUtil.resolveStartUserId(variable); // 解析登陆用户ID
		final User loginUser = TaskQueryUtil.loadLoginUser(db2,Long.valueOf(loginUserId)); // 加载登陆用户对象(含主岗、主部门)
		
		
		final String bussinessKey = TaskQueryUtil.resolveBusinessKey(businessKey,variable);
		final String businessFlowNo = TaskQueryUtil.resolveBusinessFlowNo(variable);
		final String createDeptId = TaskQueryUtil.resolveBusinessCreateDeptId(variable);
		final String approveOpinion = TaskQueryUtil.resolveBusinessOpinion(variable);
		final String bussinessType = TaskQueryUtil.resolveBusinessType(variable);
		final String title = TaskQueryUtil.resolveBusinessTitle(variable);
		final String companyNamePrint = TaskQueryUtil.resolveCompany4Print(variable);
		
		final Node draftNode = TaskQueryUtil.getDraftNode(db, def.getId());

		final Map<String, List<ApproveObject>> nodeUserMap = nodeUserService.getNodeUsers(Long.valueOf(loginUserId), def.getId(),variable);
		if (draftNode != null) {
			nodeUserMap.put(draftNode.getTaskKey(), Arrays.asList(new ApproveObject(loginUserId))); // 流程发起人加进去
		}
 
		
		context.setLoginUser(loginUser);
		context.setCurrActDefinition(def);
		context.setNodeUserMap(nodeUserMap);
		
		context.getCompleteVariable().putAll(TaskQueryUtil.toApproveUserMap(nodeUserMap)); // 设置审批用户！！！
		context.getCompleteVariable().putAll(variable); // 设置业务变量！！！！
		
		
		context.setInputVariable(variable);
		context.setDb(db);
		context.setDraftNode(draftNode);
		
		
		context.getForm().setBusinessFlowNo(businessFlowNo);
		context.getForm().setBusinessKey(bussinessKey);
		context.getForm().setCreateDeptId(createDeptId);
		context.getForm().setProcessDefinitionId(def.getId());
		context.getForm().setStartUserId(loginUserId);
		context.getForm().setApproveOpinion(approveOpinion);
		context.getForm().setTitle(title);
		context.getForm().setBusinessType(bussinessType);
		context.getForm().setCompanyNamePrint(companyNamePrint);
		
		//context.getForm().setProcessInstanceId(processInstanceId);
		
		
		final ReDefinition currReDefinion = TaskQueryUtil.loadReDefinion(context);
		context.setCurrReDefinion(currReDefinion);
		
		final int nodeCount = TaskQueryUtil.getNodeCount(context);
		context.setNodeCount(nodeCount);
		
		return context;
	}
	
	private void checkBussiness(ActRunningContext context) {
		final ActRunningContext.RunningForm form = context.getForm();
		final Node draftNode = context.getDraftNode();
		
		// 1.填制人部门、业务主键 、流水号、流程发起人、单据标题不能为空
		if (StringUtil.isBlank(form.getCreateDeptId())) {
			throw new IllegalArgumentException("单据申请部门不能为空");
		}
		if (StringUtil.isBlank(form.getBusinessKey())) {
			throw new IllegalArgumentException("单据主键不能为空");
		}
		if (StringUtil.isBlank(form.getBusinessFlowNo())) {
			//throw new IllegalArgumentException("单据流水号不能为空");
		}
		if (StringUtil.isBlank(form.getStartUserId())) {
			throw new IllegalArgumentException("单据发起人不能为空");
		}
		if (StringUtil.isBlank(form.getTitle())) {
			throw new IllegalArgumentException("单据标题不能为空");
		}
		
		if (draftNode == null) {
			throw new IllegalArgumentException("拟稿节点不能为空");
		}
		
		// 1.检查发起变量的key，与业务变量的key是否有冲突，有冲突不让发起
		
	}
	
	/**
	 * 执行流程启动
	 * @param businessKey 单据业务主键
	 * @param variables 流程变量
	 * @param def 流程定义
	 * @return 
	 */
	private ActRunningContext excuteStart(String businessKey, Map<String, Object> variable, ProcessDefinition def) {
		
		final ActRunningContext context = createRunningContext(businessKey,variable,def) ;
 
		checkBussiness(context);
		
		actIdentityService.setAuthenticatedUserId(context.getForm().getStartUserId());
		
		StartHandler startHanler = new StartHandler();
		org.lsqt.act.service.support.Command<Void> startCmd = new StartCommand(startHanler);
		
		org.lsqt.act.service.support.Command<Void> macroCmd = new CompleteCommand(Arrays.asList(startCmd));
		try {
			macroCmd.execute(context);
		} catch (Exception ex) {
			ex.printStackTrace();
			macroCmd.executeCancel(context);
			throw new RuntimeException(ex.getMessage());
		}

		loadNextUserNamesIfExists(context);
		
		saveRunTask(context);
		
		return context;//.getStartedProcessInstance();
	}

	private void saveRunTask(ActRunningContext runContext) {
		List<RunTask> todoTaskList = new ArrayList<>();
		
		String nextTaskCandidateUserIds = runContext.getNextTaskCandidateUserIds();
		if (nextTaskCandidateUserIds == null || StringUtil.isBlank(nextTaskCandidateUserIds)) {
			return ;
		}
		
		final RunInstance instance = runContext.getCurrProcessInstance();
		
		List<Task> currMutilTaskList = TaskQueryUtil.getCurrentMutilTaskList(db, instance.getInstanceId());
		if (currMutilTaskList != null && currMutilTaskList.size() > 1) {
			log.error("暂时不支持会签");
			return ;
		}
		
		if (ArrayUtil.isBlank(currMutilTaskList)) {
			return ;
		}
		
		List<String> nextUserIdList = StringUtil.split(nextTaskCandidateUserIds.toString(),",");
		
		for (String uid : nextUserIdList) {
			RunTask runTask = new RunTask();
			
			runTask.setBusinessFlowNo(runContext.getForm().getBusinessFlowNo());
			runTask.setBusinessKey(runContext.getForm().getBusinessKey());
			
			if (instance != null) {
				runTask.setBusinessStatus(instance.getBusinessStatus());
				runTask.setBusinessStatusDesc(instance.getBusinessStatusDesc());
				runTask.setCompanyIdPrint(instance.getCompanyIdPrint());
				runTask.setCompanyNamePrint(instance.getCompanyNamePrint());
				runTask.setCreateDeptId(instance.getCreateDeptId());
				runTask.setCreateDeptText(instance.getCreateDeptName());
				runTask.setInstanceId(Long.valueOf(instance.getInstanceId()));
				runTask.setProcessDefinitionId(instance.getProcessDefinitionId());
				runTask.setProcessDefinitionKey(instance.getProcessDefinitionKey());
				runTask.setProcessDefinitionName(instance.getProcessDefinitionName());
				runTask.setStartLoginNo(instance.getStartLoginNo());
				runTask.setStartUserId(instance.getStartUserId());
				runTask.setStartUserName(instance.getStartUserName());
				runTask.setStartUserOrgText(instance.getStartUserOrgText());
				runTask.setStartUserPositionText(instance.getStartUserPositionText());
				
				runTask.setTitle( instance.getTitle());
			}
			runTask.setTaskId(Long.valueOf(currMutilTaskList.get(0).getId()));
			runTask.setTaskKey(currMutilTaskList.get(0).getTaskDefinitionKey());
			runTask.setTaskUserId(uid);
			User taskUser = db2.getById(User.class, uid);
			if (taskUser!=null) {
				runTask.setTaskUserLoginNo(taskUser.getLoginNo());
				runTask.setTaskUserName(taskUser.getUserName());
			}
			

			boolean isSetUrl = doAdapterUrl(runTask); //待办地址为空，发送待办到EKP将会失败!!
			if(isSetUrl) {
				todoTaskList.add(runTask);
			}
		}
		
		if (ArrayUtil.isNotBlank(todoTaskList)) {
			String authorityWeb = PropertiesUtil.getValue("api.authority");
			if (StringUtil.isBlank(authorityWeb)) {
				log.error("syswin.properties配置文件错误,请配置api.authority参数");
				return ;
			}
			
			for (RunTask m: todoTaskList) {
				db.saveOrUpdate(m); //先保存
				
				m.setTaskLink(authorityWeb + "/api/task_url_service/converted_redirect?runTaskId=" + m.getId());
				db.update(m, "taskLink");
			}
		}
		
		runContext.setDataHook(todoTaskList);
	}
	
	/**
	 * 点击待办任务时，打开的地址（适配移动端和PC端）
	 * @param runTask
	 */
	private boolean doAdapterUrl(RunTask runTask) {
		String authorityWeb = PropertiesUtil.getValue("api.authority");
		if (StringUtil.isBlank(authorityWeb)) {
			log.error("syswin.properties配置文件错误,请配置api.authority参数");
			return false;
		}

		String budgetMobile = PropertiesUtil.getValue("api.mobile.budget"); //移动端布署的地址
		if (StringUtil.isBlank(budgetMobile)) {
			log.error("syswin.properties配置文件错误,请配置api.mobile.budget参数");
			return false;
		}
		
		NodeFormQuery filter = new NodeFormQuery();
		filter.setDataType(NodeForm.DATA_TYPE_GLOBAL_FORM);
		filter.setFormType(NodeForm.FORM_TYPE_URL);
		filter.setDefinitionIdList(new ArrayList<>(Arrays.asList(runTask.getProcessDefinitionId())));
		NodeForm nodeForm = db.queryForObject("queryForPage", NodeForm.class, filter);
		if(nodeForm == null) {
			log.error("待办详情地址适配移动端和PC端错误，没有找到全局表单配置");
			return false;
		}
		
		if(StringUtil.isBlank(nodeForm.getCustomUrl())) {
			log.error("待办详情地址适配移动端和PC端错误，全局表单地址配置为空");
			return false;
		}
		
		final Map<String, Object> root = new HashMap<>();
		root.put("taskId", runTask.getTaskId());
		root.put("processInstanceId", runTask.getInstanceId());
		root.put("processDefinitionId",runTask.getProcessDefinitionId());
		root.put("businessKey", runTask.getBusinessKey());
		root.put("processDefinitionKey", runTask.getProcessDefinitionKey());
		root.put("taskDefinitionKey", runTask.getTaskKey());
		
		User loginUser = db2.getById(User.class, runTask.getTaskUserId());
		if (loginUser == null) {
			log.error("预算系统没有找到ID为" + runTask.getTaskUserId() + "的用户,请联系管理员同步用户");
			return false;
		}
		
		// 只要必要的登陆用户信息
		Map<String,Object> simpleUser= new HashMap<>();
		simpleUser.put("userId", loginUser.getUserId());
		simpleUser.put("loginNo", loginUser.getLoginNo());
		simpleUser.put("userName", loginUser.getUserName());
		root.put("loginUser", simpleUser);
		
		
		Map<String,Object> data = new HashMap<>(); // 中转链接的整个数据包
		
		Map<String,Object> mobileTaskDetailParam = new HashMap<>(); // 手机端待办详情需要的参数
		mobileTaskDetailParam.putAll(root);

		
		try{
			Template tmpl = new Template("custom_url_"+runTask.getTaskId(), new StringReader(nodeForm.getCustomUrl()), ActFreemarkUtil.FTL_CONFIGURATION);
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
			tmpl.process(root, writer);
	
			writer.flush();
			writer.close();
			
			String pcUrl =  stringWriter.toString().trim();
			
			data.put("pcUrl", pcUrl);
			
			
			String json = JSON.toJSONString(mobileTaskDetailParam);
			
			String paramData = URLEncoder.encode(Base64.getEncoder().encodeToString(json.getBytes("utf8")),"utf8");// base64加密转码-->URL的encode
			data.put("mobileUrl", budgetMobile+"/mobileApproval?paramData="+paramData+"#page=1");

		}catch(Exception ex) {
			log.error(ex.getMessage());
			return false;
		}
		
		String dataText = JSON.toJSONString(data);
		 
		log.debug("整个参数包长度:"+dataText.length());
		
		runTask.setExtData(dataText);
		log.debug("整个中转链接的长度、值："+runTask.getExtData().length()+"、"+runTask.getExtData());
		
		return true;
	}
	
	
	/**
	 * 流程发起后，加载审批人名称
	 * 
	 * @param context
	 */
	private void loadNextUserNamesIfExists(ActRunningContext context) {
		ProcessInstance inst = context.getStartedProcessInstance();
		Object candiateUserIds = inst.getExtProperty().get(ProcessInstance.CANDIDATE_USER_IDS_KEY);
		if (candiateUserIds != null && StringUtil.isNotBlank(candiateUserIds.toString())) {
			UserQuery uq = new UserQuery();
			uq.setIds(candiateUserIds.toString());
			List<User> list = db2.queryForList("queryForPage", User.class, uq);
			if (ArrayUtil.isNotBlank(list)) {
				List<String> userNames = new ArrayList<>();
				for (User u : list) {
					userNames.add(u.getUserName());
				}
				inst.getExtProperty().put(ProcessInstance.CANDIDATE_USER_NAMES_KEY, StringUtil.join(userNames));
			}
		}
	}
	

	
	// ------------- 挂起:如果一个流程实例状态暂停，与该实例的流程相关活动将不执行工作（定时器、消息）
	public void suspendProcessInstanceById(String processInstanceId) {
		actRuntimeService.suspendProcessInstanceById(processInstanceId);
	}

	// -------------- 删除 
	public void deleteProcessInstance(String processInstanceId, String deleteReason) {
		actRuntimeService.deleteProcessInstance(processInstanceId, deleteReason);
		//deleteProcessInstanceExt(processInstanceId);
		cascadeEkpTaskIfExists(processInstanceId);
	}

	public void deleteProcessInstance(String processInstanceId) {
		actRuntimeService.deleteProcessInstance(processInstanceId,null);
		//deleteProcessInstanceExt(processInstanceId);
		
		cascadeEkpTaskIfExists(processInstanceId);
	}

	void deleteProcessInstanceExt(String processInstanceId) {
		db.executeUpdate("delete from ext_approve_opinion where process_instance_id=?", processInstanceId);
		db.executeUpdate("delete from ext_run_instance where instance_id=?", processInstanceId);
		db.executeUpdate("delete from ext_instance_variable where instance_id=?", processInstanceId);
		db.executeUpdate("delete from ext_run_task_assign_forward_cc where process_instance_id=?", processInstanceId);
		
		cascadeEkpTaskIfExists(processInstanceId);
	}
	
	/**
	 * 同步删除（旭辉）EKP里的待办消息
	 * @param processInstanceId
	 */
	private void cascadeEkpTaskIfExists(String processInstanceId) {
		RunTaskQuery query = new RunTaskQuery();
		query.setInstanceId(Long.valueOf(processInstanceId));
		List<RunTask> list = db.queryForList("queryForPage", RunTask.class, query);
		if(ArrayUtil.isNotBlank(list)) {
			for(RunTask e: list) {
				EkpTaskUtil.exeEkpDeleteTask(e.getTaskId()+"");
			}
		}
	}
	
	// ------------------------------------------------------  查询  ----------------------------------------------------------------
	
	public List<Task> getCurrentTaskList(String processInstanceId) {
		ProcessInstance instance=getById(processInstanceId);
		if(instance!=null) {
			org.lsqt.act.model.TaskQuery filter = new org.lsqt.act.model.TaskQuery();
			filter.setProcessDefinitionId(instance.getProcessDefinitionId());
			filter.setProcessInstanceId(instance.getProcessInstanceId());
			List<Task> taskList = db.queryForList("querySimple", Task.class, filter);
			return taskList;
		}
		return new ArrayList<>();
	}
	
	public ProcessInstance getById(String processInstanceId) {
		org.activiti.engine.runtime.ProcessInstance p =  actRuntimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		return ActUtil.convert(p);
	}
	
	/**
	 * 运行中的流程
	 */
	public Page<ProcessInstance> queryForPageRunning(ProcessInstanceQuery query) {
		org.activiti.engine.runtime.ProcessInstanceQuery processInstanceQuery = actRuntimeService.createProcessInstanceQuery();
		
		if(StringUtil.isNotBlank(query.getUserId())) {
			processInstanceQuery.involvedUser(query.getUserId());
		}
		
	    if (StringUtil.isNotBlank(query.getProcessInstanceId())){
		    processInstanceQuery.processInstanceId(query.getProcessInstanceId());
	    }
	    
	    if (StringUtil.isNotBlank(query.getProcessDefinitionKey())){
		    processInstanceQuery.processDefinitionKey(query.getProcessDefinitionKey());
	    }
	    
	    if(StringUtil.isNotBlank(query.getProcessDefinitionId())) {
	    	processInstanceQuery.processDefinitionId(query.getProcessDefinitionId());
	    }
	    long total = processInstanceQuery.count();

		final long pageCount = Double.valueOf(Math.ceil(total * 1.000 / query.getPageSize())).longValue();

		// 封装分页对象
		Page<ProcessInstance> page = new Page.PageModel<>();
		page.setTotal(total);
		page.setPageCount(pageCount);
		page.setPageIndex(query.getPageIndex());
		page.setPageSize(query.getPageSize());
		page.setHasNext(query.getPageIndex() + 1 < pageCount);
		page.setHasPrevious(query.getPageIndex() > 0 && query.getPageIndex() < pageCount - 1);
	    
		List<org.activiti.engine.runtime.ProcessInstance> temp = processInstanceQuery.listPage(query.getPageIndex()*query.getPageSize(), query.getPageSize());
		page.setData(ActUtil.convert(temp));
		
		// 加载流程任务名称
		if(StringUtil.isNotBlank(query.getProcessDefinitionId())) {
			 
			// 获取流程的所有节点
			BpmnModel model = actRepositoryService.getBpmnModel(query.getProcessDefinitionId());
			if (model != null) {
				Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
				for(ProcessInstance p : page.getData()) {
					for(FlowElement e: flowElements) {
						if(p.getActivityId()!=null  && p.getActivityId().equals(e.getId())) {
							p.setTaskName(e.getName());
							break;
						}
					}
				}
			}
		}
		
		/*
		// 关键字查询时，单条记录加载taskName
		for(ProcessInstance p : page.getData()) {
			if(StringUtil.isBlank(p.getTaskName()) && StringUtil.isNotBlank(p.getProcessDefinitionId())) {
				BpmnModel model = actRepositoryService.getBpmnModel(p.getProcessDefinitionId());
				if (model != null) {
					Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
					 
					for(FlowElement e: flowElements) {
						if(p.getActivityId()!=null  && p.getActivityId().equals(e.getId())) {
							p.setTaskName(e.getName());
							break;
						}
					}
				}
			}
		}*/
		
		// 加载流程标题和发起人、业务主键
		List<String> processInstanceIds = new ArrayList<>();
		
		for (ProcessInstance e : page.getData()) {
			processInstanceIds.add(e.getId());
		}
		if (!processInstanceIds.isEmpty()) {
			RunInstanceQuery piq = new RunInstanceQuery();
			piq.setInstanceIdList(processInstanceIds);
			List<RunInstance> data = db.queryForList("queryForPage", RunInstance.class, piq);
			if (data != null) {

				for (ProcessInstance e : page.getData()) {
					for (RunInstance n : data) {
						if (e.getId().equals(n.getInstanceId())) {
							e.setTitle(n.getTitle());
							e.setBusinessKey(n.getBusinessKey());
							e.setStartUserId(n.getStartUserId());
							break;
						}
					}
				}
			}
		}
		
		return page;
	}

	/**
	 * 已结束的流程
	 */
	public Page<ProcessInstanceHis> queryForPageFinished(ProcessInstanceQuery q) { // 已结束的流程
		HistoricProcessInstanceQuery query=actHistoryService.createHistoricProcessInstanceQuery().finished().orderByProcessInstanceEndTime().desc();
		
		if(StringUtil.isNotBlank(q.getUserId())) {
			query.involvedUser(q.getUserId());
		}
		
	    if (StringUtil.isNotBlank(q.getProcessInstanceId())){
	    	query.processInstanceId(q.getProcessInstanceId());
	    }
	    
	    if (StringUtil.isNotBlank(q.getProcessDefinitionKey())){
	    	query.processDefinitionKey(q.getProcessDefinitionKey());
	    }
	    if(StringUtil.isNotBlank(q.getProcessDefinitionId())) {
	    	query.processDefinitionId(q.getProcessDefinitionId());
	    }
	    
	    long total = query.count();
	    final long pageCount = Double.valueOf(Math.ceil(total * 1.000 / q.getPageSize())).longValue();
	    
	    Page<ProcessInstanceHis> page = new Page.PageModel<>();
	    page.setTotal(total);
	    page.setPageCount(pageCount);
		page.setPageIndex(q.getPageIndex());
		page.setPageSize(q.getPageSize());
		page.setHasNext(q.getPageIndex() + 1 < pageCount);
		page.setHasPrevious(q.getPageIndex() > 0 && q.getPageIndex() < pageCount - 1);
		
		List<org.activiti.engine.history.HistoricProcessInstance> temp = query.listPage(q.getPageIndex()*q.getPageSize(), q.getPageSize());
		page.setData(ActUtil.convertHis(temp));
		
		/*
		// 加载流程任务名称
		if(StringUtil.isNotBlank(q.getProcessDefinitionId())) {
			 
			// 获取流程的所有节点
			BpmnModel model = actRepositoryService.getBpmnModel(q.getProcessDefinitionId());
			if (model != null) {
				Collection<FlowElement> flowElements = model.getMainProcess().getFlowElements();
				for(ProcessInstanceHis p : page.getData()) {
					for(FlowElement e: flowElements) {
						if(p.getStartActivityId()!=null  && p.getStartActivityId().equals(e.getId())) {
							p.setTaskName(e.getName());
							break;
						}
					}
				}
			}
		}
		*/
		return page;
	}

	/**
	 * 流程审批意见查询
	 */
	public List<ApproveOpinion> queryForListOpinion(ApproveOpinionQuery query) {
		return 	db.queryForList("queryForPage", ApproveOpinion.class, query);
	}
	



}
