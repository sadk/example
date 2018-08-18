package org.lsqt.act.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionHis;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.JumpForm;
import org.lsqt.act.model.ProcessInstance;
import org.lsqt.act.model.ProcessInstanceHis;
import org.lsqt.act.model.ProcessInstanceQuery;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunTask;
import org.lsqt.act.model.Task;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.act.service.RuntimeService;
import org.lsqt.act.service.TaskService;
import org.lsqt.act.service.impl.TaskServiceImpl;
import org.lsqt.act.service.support.EkpTaskUtil;
import org.lsqt.act.service.support.HttpClientUtil;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.file.PropertiesUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.model.UserQuery;
import org.lsqt.syswin.uum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;


@Controller(mapping = { "/act/runtime","/nv2/act/runtime"  })
public class RuntimeController {
	private static final Logger  log = LoggerFactory.getLogger(RuntimeController.class);
	
	@Inject private RuntimeService runtimeService;
	@Inject private TaskService taskService;
	@Inject private NodeUserService nodeUserService;
	@Inject private UserService userService;
	
	@Inject private Db db;
	@Inject private PlatformDb db2;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping={"/start_by_definition_id","/m/start_by_definition_id"},text="流程启动,按流程定义Id")
	public ProcessInstance startByDefinitionId(String definitionId,String variables) {
		if(StringUtil.isNotBlank(definitionId,variables)) {
			Map<String,Object> data = JSON.parseObject(variables, Map.class);
			ActRunningContext runContext = runtimeService.startProcessInstanceById(definitionId,data);
			
			// 推送待办到旭辉系统
			if (runContext.getDataHook() != null && runContext.getDataHook() instanceof List) {
				List<RunTask> taskData = (List<RunTask>) runContext.getDataHook();

				EkpTaskUtil.exeEkpSendTask(taskData);
			}
			
			return runContext.getStartedProcessInstance() ;
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping={"/start_by_definition_key","/m/start_by_definition_key"},text="流程启动,按流程定义key")
	public ProcessInstance startByDefinitionKey(String definitionKey,String variables) {
		if(StringUtil.isNotBlank(definitionKey,variables)) {
			Map<String,Object> data = JSON.parseObject(variables, Map.class);
			return runtimeService.startProcessInstanceByKey(definitionKey,data).getStartedProcessInstance();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping={"/complete","/m/complete"},text="流程下一步,其中approveOpinion.approveAction字段很重要")
	public String complete(Long loginUserId,String taskId,String variables,ApproveOpinion approveOpinion) {
		
		if(StringUtil.isNotBlank(taskId)) {
			Map<String,Object> var= new HashMap<>();
			
			if(StringUtil.isNotBlank(variables)){
				var = JSON.parseObject(variables, Map.class);
			}
			
			// 处理前端送过来的审批动作code
			String action = approveOpinion.getApproveAction();
			if (action != null && action.startsWith("button_type_")) {
				approveOpinion.setApproveAction(action.replace("button_type_", ""));
			}


			ActRunningContext runContext = taskService.complete(loginUserId, taskId, var, approveOpinion);
			String userIds = runContext.getNextTaskCandidateUserIds();
			
			
			
			// 推送待办到旭辉系统(并把当前任务从旭辉待办里删除)
			if (runContext.getDataHook() != null && runContext.getDataHook() instanceof List) {
				List<RunTask> taskData = (List<RunTask>) runContext.getDataHook();

				if (runContext.getInputTask() != null) {
					EkpTaskUtil.exeEkpDeleteTask(runContext.getInputTask().getId());
				}
				EkpTaskUtil.exeEkpSendTask(taskData);
			}
			
			
			
			if (StringUtil.isNotBlank(userIds)) {
				UserQuery query = new UserQuery();
				query.setIds(userIds);
				List<User> list = userService.queryForList(query);
				StringBuilder cadiateUsers = new StringBuilder();
				if (list != null && list.size() > 0) {
					for (User e : list) {
						cadiateUsers.append(e.getUserName() + "(" + e.getLoginNo() + "/" + e.getUserId() + ")");
					}
				}
				return cadiateUsers.toString();
			} else {
				return "没有下一步处理人(流程可能已结束或退回至拟稿人)";
			}
		}else {
			throw new IllegalArgumentException("taskId不能为空");
		}
	}
	
	
	
	
	
	@RequestMapping(mapping={"/list_opinion","/m/list_opinion"},text="流程审批意见(含附件)")
	public List<ApproveOpinion> opinionList(String taskId) {
		if(StringUtil.isNotBlank(taskId )) {
			Task task = taskService.getById(taskId);
			if(task!=null) {
				ProcessInstance inst = runtimeService.getById(task.getProcessInstanceId());
				
				// 获取业务主键 
				ApproveOpinionQuery query = new ApproveOpinionQuery();
				query.setBusinessKey(inst.getBusinessKey());
				return runtimeService.queryForListOpinion(query);
			}
		}
		return null;
	}
	
	// -----------------------------------------------------------------------------------------------
	
	@RequestMapping(mapping = { "/page_running", "/m/page_running" }, text = "获取运行中的流程,act原始接口查询")
	public Page<ProcessInstance> queryForPageRunning(ProcessInstanceQuery query) {
		Page<ProcessInstance> page = runtimeService.queryForPageRunning(query);
		
		return page;
	}

	@RequestMapping(mapping = { "/page_finished", "/m/page_finished" }, text = "获取已结束的流程,act原始接口查询")
	public Page<ProcessInstanceHis> queryForPageFinished(ProcessInstanceQuery query) {
		return runtimeService.queryForPageFinished(query);
	}
	
	@RequestMapping(mapping = { "/page_finished_detail", "/m/page_finished_detail" }, text = "获取已结束的流程，带流程标题等详细信息")
	public Page<ProcessInstanceHis> queryForPageFinishedDetail(ProcessInstanceQuery query) {
		Page<ProcessInstanceHis> page = db.queryForPage("queryForPageFinishedDetail", query.getPageIndex(), query.getPageSize(), ProcessInstanceHis.class, query);
		
		// 加载填制人部门名称
		if(page.getData()!=null && page.getData().size()>0) {
			List<String> createDeptIds =new ArrayList<>();
			List<ProcessInstanceHis> data = (List<ProcessInstanceHis>)page.getData();
			for(ProcessInstanceHis e: data) {
				if(StringUtil.isNotBlank(e.getCreateDeptId())) {
					createDeptIds.add(e.getCreateDeptId());
				}
			}
			OrgQuery orgQuery = new OrgQuery();
			String sql = StringUtil.join(createDeptIds,",");
			
			if(StringUtil.isNotBlank(sql)) {
				orgQuery.setIds(sql);
				List<Org> list = db2.queryForList("queryForPage", Org.class, orgQuery);
				
				for(ProcessInstanceHis e: data) {
					for(Org n: list) {
						if(StringUtil.isNotBlank(e.getCreateDeptId())  
								&& e.getCreateDeptId().equals(n.getId().toString())) {
							e.setStartUserOrgText(n.getName());
							break;
						}
					}
				}
			}
		}
		return page;
	}
	
	@RequestMapping(mapping={"/instance_delete","/m/instance_delete"},text="删除流程实例")
	public void deleteInstance(String instanceIds) {
		if (StringUtil.isNotBlank(instanceIds)) {
			
			List<String> list = new ArrayList<>(new HashSet<>(StringUtil.split(instanceIds, ",")));
			
			for (String id : list) {
				/*
				List<Task> mutilTask = runtimeService.getCurrentTaskList(id); // 如果有会签并，找到根execution调用引擎API删除
				if (mutilTask.size() > 1) {
					String rootId = db.queryForObject(RunInstance.class.getName(), "getExecuteRootIdByInstance", String.class, id);
					runtimeService.deleteProcessInstance(rootId, "管理员删除");
				} else {
					runtimeService.deleteProcessInstance(id, "管理员删除");
				}
				*/
				
				runtimeService.deleteProcessInstance(id, "管理员删除");
			}
		}
	}
	
	 
	@RequestMapping(mapping={"/jump_next","/m/jump_next"},text="流程跳转调整")
	public void jumpNext(JumpForm form) {
		validateForm(form);
		
		if(form.getProcessDefinitionId().equals(form.getProcessDefinitionIdTarget())) {
			jumpCurrent(form);
		} else {
			jumpNew(form);
		}
	}

	/**
	 * 不新启流程跳转
	 * @param form
	 */
	@SuppressWarnings("unchecked")
	private void jumpCurrent(JumpForm form) {
		Task task = getNextNewTask(form.getInstanceId());
		if (task == null) {
			throw new RuntimeException("没有找到当前流程的任务信息，请偿试重启新流程到当前状态");
		}
		
		Map<String,Object> variables = JSON.parseObject(form.getVariableJSONStarting(), Map.class); 
		if(StringUtil.isNotBlank(form.getVariableJSON4Jump())) {
			Map<String,Object> variablesJump = JSON.parseObject(form.getVariableJSON4Jump(), Map.class);
			variables.putAll(variablesJump);
		}
		
		// 发起人、填制人变量
		variables.put(ActUtil.VARIABLES_CREATE_DEPT_ID, Long.valueOf(form.getCreateDeptId()));
		variables.put(ActUtil.VARIABLES_START_USER_ID, Long.valueOf(form.getStartUserId()));
		variables.put(ActUtil.VARIABLES_BUSINESS_KEY, form.getBusinessKey());
		
		Map<String, List<ApproveObject>> nodeUserMap = nodeUserService.getNodeUsers(Long.valueOf(form.getStartUserId()), task.getProcessDefinitionId(),variables);
		List<ApproveObject> list = nodeUserMap.get(form.getTaskKeyTarget());
		
		
		if(StringUtil.isBlank(form.getTaskkeyTargetCandiateUserIds())){
			variables.put(form.getTaskKeyTarget(), ApproveObject.toIdList(list));
		} else {
			variables.put(form.getTaskKeyTarget(), StringUtil.split(Long.class,form.getTaskkeyTargetCandiateUserIds(), ","));
		}

		org.activiti.engine.runtime.ProcessInstance actInstance =  ActUtil.getRuntimeService().createProcessInstanceQuery().includeProcessVariables().processInstanceId(task.getProcessInstanceId()).singleResult();
		
		TaskServiceImpl taskServiceImpl = (TaskServiceImpl)taskService;
		Map<String,Object> vars = new HashMap<>();
		vars.putAll(taskServiceImpl.prepareCompleteVariable(actInstance, vars)); // 会签条件变量
		vars.putAll(taskService.prepareMeetingVariable(ActUtil.convert(actInstance), nodeUserMap)); // 会签用户变量
		vars.putAll(variables); // 流程变量
		taskService.jump(task.getId(), form.getTaskKeyTarget(), vars);
		
		
		// 添加流程指定的处理人
		List<Task> mutilTaskList = taskService.getCurrentMutilTaskList(form.getInstanceId());
		if(mutilTaskList!=null && mutilTaskList.size()>1) { // 如果是加签节点，每个节点任务签加候选人
			for (Task t: mutilTaskList) {
				if(t!=null && StringUtil.isNotBlank(form.getTaskkeyTargetCandiateUserIds())) {
					List<String> ids = StringUtil.split(form.getTaskkeyTargetCandiateUserIds(),",");
					for(String id: ids) {
						ActUtil.getTaskService().addCandidateUser(t.getId(), id);
					}
				}
			}
		} else {
			Task nextTask = getNextNewTask(form.getInstanceId());
			if(nextTask!=null && StringUtil.isNotBlank(form.getTaskkeyTargetCandiateUserIds())) {
				List<String> ids = StringUtil.split(form.getTaskkeyTargetCandiateUserIds(),",");
				for(String id: ids) {
					ActUtil.getTaskService().addCandidateUser(nextTask.getId(), id);
				}
			}
		}
	}
 
	/**
	 * 新启流程跳转
	 * @param form
	 */
	@SuppressWarnings("unchecked")
	private void jumpNew(JumpForm form) {
		
		if(StringUtil.isBlank(form.getVariableJSONInit())) {
			throw new RuntimeException("流程发起参数不能为空");
		}
		
		
		Map<String,Object> variables = JSON.parseObject(form.getVariableJSONInit(), Map.class); 
		
		// 发起人、填制人部门、业务主键变量
		variables.put(ActUtil.VARIABLES_CREATE_DEPT_ID, Long.valueOf(form.getCreateDeptId()));
		variables.put(ActUtil.VARIABLES_START_USER_ID, Long.valueOf(form.getStartUserId()));
		variables.put(ActUtil.VARIABLES_BUSINESS_KEY, form.getBusinessKey());
		
		if(StringUtil.isNotBlank(form.getVariableJSON4Jump())) { // 填充管理员指定的流程变量
			Map<String,Object> variablesJump = JSON.parseObject(form.getVariableJSON4Jump(), Map.class);
			variables.putAll(variablesJump);
		}
		
		org.lsqt.act.model.ProcessInstance newInstance = runtimeService.startProcessInstanceById(form.getProcessDefinitionIdTarget(), form.getBusinessKey(), variables).getStartedProcessInstance();
		
		Task task = getNextNewTask(newInstance.getProcessInstanceId());
		
		
		Map<String, List<ApproveObject>> nodeUserMap = nodeUserService.getNodeUsers(Long.valueOf(form.getStartUserId()), task.getProcessDefinitionId(),variables);
		List<ApproveObject> list = nodeUserMap.get(form.getTaskKeyTarget());
		
		
		if(StringUtil.isBlank(form.getTaskkeyTargetCandiateUserIds())){
			variables.put(form.getTaskKeyTarget(), ApproveObject.toIdList(list));
		} else {
			variables.put(form.getTaskKeyTarget(), StringUtil.split(Long.class,form.getTaskkeyTargetCandiateUserIds(), ","));
		}

		taskService.jump(task.getId(), form.getTaskKeyTarget(), variables);
		
		
		// 添加流程指定的处理人
		Task nextTask = getNextNewTask(newInstance.getProcessInstanceId());
		if(nextTask!=null && StringUtil.isNotBlank(form.getTaskkeyTargetCandiateUserIds())) {
			List<String> ids = StringUtil.split(form.getTaskkeyTargetCandiateUserIds(),",");
			for(String id: ids) {
				ActUtil.getTaskService().addCandidateUser(nextTask.getId(), id);
			}
		}
		
		// 删除原始流程
		ActUtil.getRuntimeService().deleteProcessInstance(form.getInstanceId(), "结束流程,跳转调整");
		
		db.executeUpdate("delete from ext_approve_opinion where process_instance_id=?", newInstance.getProcessInstanceId());
		
		// 拷贝旧流程审批意见
		List<ApproveOpinion> newDataList = new ArrayList<>();
		ApproveOpinionQuery aoq=new ApproveOpinionQuery();
		aoq.setProcessInstanceId(form.getInstanceId());
		List<ApproveOpinion> data = db.queryForList("queryForPage", ApproveOpinion.class, aoq);
		if(data!=null && data.size()>0){
			for(ApproveOpinion e: data) {
				e.setId(null);
				e.setProcessInstanceId(newInstance.getProcessInstanceId());
				newDataList.add(e);
				//db.save(e);
			}
			db.batchSave(newDataList);
		}
		
	}
	private void validateForm(JumpForm form) {
		if(StringUtil.isBlank(form.getInstanceId())) {
			throw new RuntimeException("流程实例不能为空");
		}
		
		if(StringUtil.isBlank(form.getStartUserId())) {
			throw new RuntimeException("流程发起人不能为空");
		}
		
		if(StringUtil.isBlank(form.getCreateDeptId())) {
			throw new RuntimeException("填制人部门不能为空");
		}
		
		if(StringUtil.isBlank(form.getBusinessKey())) {
			throw new RuntimeException("业务主键不能为空");
		}
	}
	
	
	private Task getNextNewTask(String processInstanceId) {
		org.lsqt.act.model.TaskQuery filter = new org.lsqt.act.model.TaskQuery();
		filter.setProcessInstanceId(processInstanceId);
		List<Task> taskList = db.queryForList("querySimple", Task.class, filter);
		if (taskList != null && taskList.size() > 1) {
			throw new RuntimeException("自动跳过的节点，不支持并发模式");
		}

		if(taskList.isEmpty()) {
			return null;
		}
		
		Task task = taskList.get(0);
		return task;
	}
	
	
	@RequestMapping(mapping = { "/onece_pass", "/m/onece_pass" }, text = "运行中的流程实例一键通过（实质为删除流程实例，更新业务状态为已完成)")
	public void onecePass(String instanceIds,Boolean isDeleteOpinion) {
		instanceIds = StringUtil.escapeSql(instanceIds);
		
		if(StringUtil.isNotBlank(instanceIds )) {
			List<ApproveOpinionHis> data = new ArrayList<>();
			
			//流程意见迁移到历史表
			if(isDeleteOpinion!=null && isDeleteOpinion) {
				ApproveOpinionQuery query = new ApproveOpinionQuery();
				query.setProcessInstanceIds(instanceIds);
				List<ApproveOpinion> appinionList = db.queryForList("queryForPage", ApproveOpinion.class, query);
				if (appinionList!=null) {
					for(ApproveOpinion e: appinionList) {
						ApproveOpinionHis model= new ApproveOpinionHis();
						model.setApproveAction(e.getApproveAction());
						model.setApproveOpinion(e.getApproveOpinion());
						model.setApproveResult(e.getApproveResult());
						model.setApproveTaskCandidateUserIds(e.getApproveTaskCandidateUserIds());
						model.setApproveTaskId(e.getApproveTaskId());
						model.setApproveTaskKey(e.getApproveTaskKey());
						model.setApproveTaskName(e.getApproveTaskName());
						model.setApproveUserId(e.getApproveUserId());
						model.setApproveUserName(e.getApproveUserName());
						model.setApproveUserOrgText(e.getApproveUserOrgText());
						model.setApproveUserPositionText(e.getApproveUserPositionText());
						model.setAssignForwardCcUserIds(e.getAssignForwardCcUserIds());
						model.setBusinessKey(e.getBusinessKey());
						model.setBusinessType(e.getBusinessType());
						model.setDefinitionId(e.getDefinitionId());
						model.setCreateTime(e.getCreateTime());
						model.setDefinitionName(e.getDefinitionName());
						model.setDefinitionKey(e.getDefinitionKey());
						model.setProcessInstanceId(e.getProcessInstanceId());
						model.setRejectReRunCompleteStatus(e.getRejectReRunCompleteStatus());
						model.setRejectToChooseNodeTaskKey(e.getRejectToChooseNodeTaskKey());
						model.setRemark("一键通过，保留审批意见");
						model.setVariablesJson(e.getVariablesJson());
						data.add(model);
					}
				}
				db.executeUpdate(String.format("delete from ext_approve_opinion where process_instance_id in (%s)",StringUtil.join(StringUtil.split(instanceIds, ","), ",","'","'")));
				if (!data.isEmpty()) {
					db.batchSave(data);
				}
			}
			
			
			// 更新扩展的流程实例表业务状态
			db.executeUpdate(String.format("update ext_run_instance set business_status=?,business_status_desc=?,end_status=? where instance_id in (%s)",instanceIds),ActUtil.BUSINESS_STATUS_已通过,ActUtil.getBusinessStatusDesc(ActUtil.BUSINESS_STATUS_已通过),ActUtil.END_STATUS_已结束);
			
			// 删除act流程实例
			List<String> list = StringUtil.split(instanceIds, ",");
			for(String id: list) {
				runtimeService.deleteProcessInstance(id, "管理员删除");
			}
			
		}
	}
	
	
	@RequestMapping(mapping = { "/fix_business_status", "/m/fix_business_status" }, text = "一键修复流程状态，不关注业务")
	public List<String> fixBusinessStatus() {
		List<String> fixedInstanceIds = new ArrayList<>();
		
		// 已结束的流程，最后一个审批意见“同意”的，则为已通过，如果没有意见的就不处理；
		List<RunInstance> list = db.queryForList("fixBusinessStatusQuery", RunInstance.class);
		for(RunInstance e: list) {
			ApproveOpinion opinion = db.queryForObject("fixBusinessStatusQuery", ApproveOpinion.class, e.getInstanceId());
			if (opinion!=null) {
				if("agree".equals(opinion.getApproveAction())) {
					fixedInstanceIds.add(e.getInstanceId());
					
					e.setBusinessStatus(ActUtil.BUSINESS_STATUS_已通过);
					e.setBusinessStatusDesc("已通过");
					db.update(e, "businessStatus","businessStatusDesc");
				}
			}
		}
		
		// 处理未结束的流程，验准状态
		
		
		return fixedInstanceIds;
	}
	
	
	@RequestMapping(mapping = { "/delete_third_task", "/m/delete_third_task" }, text = "删除第三方待办任务")
	public void deleteThirdTask(String instanceIds) {
		if (StringUtil.isNotBlank(instanceIds)) {
			List<String> ids = StringUtil.split(instanceIds, ",");
			
			ApproveOpinionQuery query = new ApproveOpinionQuery();
			query.setProcessInstanceIds(StringUtil.join(ids));
			List<ApproveOpinion> list = db.queryForList("queryForPage", ApproveOpinion.class, query);
			if(ArrayUtil.isNotBlank(list)) {
				for (ApproveOpinion m: list) {
					if (StringUtil.isNotBlank(m.getApproveTaskId())) {
						EkpTaskUtil.exeEkpDeleteTask(m.getApproveTaskId());
					}
					
					db.delete(m);
				}
			}
		}
	}
}
