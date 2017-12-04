package org.lsqt.act.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.IdentityLinkType;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.JumpForm;
import org.lsqt.act.model.ProcessInstance;
import org.lsqt.act.model.ProcessInstanceHis;
import org.lsqt.act.model.ProcessInstanceQuery;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.Task;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.act.service.RuntimeService;
import org.lsqt.act.service.TaskService;
import org.lsqt.components.context.annotation.Controller;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.mvc.RequestMapping;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;

import com.alibaba.fastjson.JSON;


@Controller(mapping = { "/act/runtime","/nv2/act/runtime"  })
public class RuntimeController {
	@Inject private RuntimeService runtimeService;
	@Inject private TaskService taskService;
	@Inject private NodeUserService nodeUserService;
	 
	
	@Inject private Db db;
	@Inject private PlatformDb db2;
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping={"/start_by_definition_id","/m/start_by_definition_id"},text="流程启动,按流程定义Id")
	public ProcessInstance startByDefinitionId(String definitionId,String variables) {
		if(StringUtil.isNotBlank(definitionId,variables)) {
			Map<String,Object> data = JSON.parseObject(variables, Map.class);
			return runtimeService.startProcessInstanceById(definitionId,data);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping={"/start_by_definition_key","/m/start_by_definition_key"},text="流程启动,按流程定义key")
	public ProcessInstance startByDefinitionKey(String definitionKey,String variables) {
		if(StringUtil.isNotBlank(definitionKey,variables)) {
			Map<String,Object> data = JSON.parseObject(variables, Map.class);
			return runtimeService.startProcessInstanceByKey(definitionKey,data);
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(mapping={"/complete","/m/complete"},text="流程下一步,其中approveOpinion.approveAction字段很重要")
	public void complete(Long loginUserId,String taskId,String variables,ApproveOpinion approveOpinion) {
		
		if(StringUtil.isNotBlank(taskId)) {
			Map<String,Object> var= new HashMap<>();
			
			if(StringUtil.isNotBlank(variables)){
				var = JSON.parseObject(variables, Map.class);
			}
			
			//处理前端送过来的审批动作code
			String action = approveOpinion.getApproveAction();
			if (action != null && action.startsWith("button_type_")) {
				approveOpinion.setApproveAction(action.replace("button_type_", ""));
			}
			taskService.complete(loginUserId,taskId, var,approveOpinion);
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
		return runtimeService.queryForPageRunning(query);
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
		if(StringUtil.isNotBlank(instanceIds )) {
			List<String> list = StringUtil.split(instanceIds, ",");
			for(String id: list) {
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

		taskService.jump(task.getId(), form.getTaskKeyTarget(), variables);
		
		
		// 添加流程指定的处理人
		Task nextTask = getNextNewTask(form.getInstanceId());
		if(nextTask!=null && StringUtil.isNotBlank(form.getTaskkeyTargetCandiateUserIds())) {
			List<String> ids = StringUtil.split(form.getTaskkeyTargetCandiateUserIds(),",");
			for(String id: ids) {
				ActUtil.getTaskService().addCandidateUser(nextTask.getId(), id);
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
		
		org.lsqt.act.model.ProcessInstance newInstance = runtimeService.startProcessInstanceById(form.getProcessDefinitionIdTarget(), form.getBusinessKey(), variables);
		
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
		ActUtil.getRuntimeService().deleteProcessInstance(form.getInstanceId(), "结束流程跳转调整");
		
		db.executeUpdate("delete from ext_approve_opinion where process_instance_id=?", newInstance.getProcessInstanceId());
		
		// 拷贝旧流程审批意见
		ApproveOpinionQuery aoq=new ApproveOpinionQuery();
		aoq.setProcessInstanceId(form.getInstanceId());
		List<ApproveOpinion> data = db.queryForList("queryForPage", ApproveOpinion.class, aoq);
		if(data!=null && data.size()>0){
			for(ApproveOpinion e: data) {
				e.setId(null);
				e.setProcessInstanceId(newInstance.getProcessInstanceId());
				db.save(e);
			}
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
}
