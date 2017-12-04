package org.lsqt.act.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowElement;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.repository.ProcessDefinition;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.InstanceVariable;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.model.ProcessInstance;
import org.lsqt.act.model.ProcessInstanceHis;
import org.lsqt.act.model.ProcessInstanceQuery;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunInstanceQuery;
import org.lsqt.act.model.Task;
import org.lsqt.act.model.TaskQuery;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.act.service.RuntimeService;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;

import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;



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
	
	// ---------常规启动流程
	public ProcessInstance startProcessInstanceById(String processDefinitionId) {
		return startProcessInstanceById(processDefinitionId, null, new HashMap<>());
	}

	public ProcessInstance startProcessInstanceById(String processDefinitionId,Map<String, Object> variables) {
		return startProcessInstanceById(processDefinitionId, null , variables);
	}
	
	public ProcessInstance startProcessInstanceById(String processDefinitionId,String businessKey,Map<String, Object> variables) { // startById核心启动方法
		
		ProcessDefinition def = actRepositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();

		return excuteStart(businessKey, variables, def);
	}

	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey) {
		return startProcessInstanceByKey(processDefinitionKey,null,new HashMap<>());
	}

	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey, Map<String, Object> variables) {
		return startProcessInstanceByKey(processDefinitionKey,null,variables);
	}

	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey,String businessKey, Map<String, Object> variables) {
		return startProcessInstanceByKey(processDefinitionKey,businessKey,null,variables);
	}
 
	public ProcessInstance startProcessInstanceByKey(String processDefinitionKey,String businessKey,String businessType, Map<String, Object> variables) { // startByKey核心启动方法
		ProcessDefinition def = actRepositoryService.createProcessDefinitionQuery().latestVersion().processDefinitionKey(processDefinitionKey).singleResult();
		
		return excuteStart(businessKey,variables,def);
		
	}
	
	/**
	 * 获取填制人部门
	 * @param loginUser
	 * @param variables
	 * @return
	
	String prepareCreateDeptId(User loginUser, Map<String, Object> variables) {
		Object createDeptIdObj = variables.get(ActUtil.VARIABLES_CREATE_DEPT_ID);
		if (createDeptIdObj != null) {
			return createDeptIdObj.toString();
		} else {
			Org org = loginUser.getUserMainOrg();
			if (org != null) {
				return (org.getId() + "");
				
			} else if (loginUser.getUserOrgList() != null && loginUser.getUserOrgList().size() > 0) {
				return (loginUser.getUserOrgList().get(0).getId() + "");
			} else {
				return null;
			}
		}
	} */
	
	/**
	 * 执行流程启动
	 * @param businessKey 单据业务主键
	 * @param variables 流程变量
	 * @param def 流程定义
	 * @return 
	 */
	private ProcessInstance excuteStart(String businessKey, Map<String, Object> variables, ProcessDefinition def) {
		if(def==null) return null;

		final long beginTime = System.currentTimeMillis();
		
		final String variableJSONStart = JSON.toJSONString(variables);
		
		final String loginUserId = prepareStartUserId(variables); // 解析登陆用户ID
		
		final User loginUser = prepareLoginUser(loginUserId); // 加载登陆用户对象(含主岗、主部门)
		
		businessKey = prepareBusinessKey(businessKey, variables);
		
		// 获取填制人部门，用于用户规则解析
		Map<String,Object> nodeUserVariable = new HashMap<>();
		Object createDeptIdObj = variables.get(ActUtil.VARIABLES_CREATE_DEPT_ID);
		if(createDeptIdObj == null) {
			throw new RuntimeException("填制人部门不能为空");
		}
		nodeUserVariable.put(ActUtil.VARIABLES_CREATE_DEPT_ID,Long.valueOf(createDeptIdObj.toString()));
		
		Map<String, List<ApproveObject>> map = nodeUserService.getNodeUsers(Long.valueOf(loginUserId), def.getId(),nodeUserVariable);
		
	 	final Node draftNode = getDraftNode(def);
	 	final List<Node> autoJumpNodeList = getAutoJumpNodeList(def.getId(),map);
	 	
	 	prepareVariablesForExistsDraftNode(variables, loginUserId, map, draftNode);  // 如果有拟稿节点，填充审批用户到流程变量
		prepareVariablesForNodeUser(variables, map); 
		prepareVariablesForEmptyNodeUser(variables, autoJumpNodeList);
		

		log.debug(" --- 流程准备发起 >>>>>> 流程定义ID: "+def.getId()+" 流程业务主键:"+businessKey+"  流程变量:\n"+JSON.toJSONString(variables, true));
		
		org.activiti.engine.runtime.ProcessInstance e= actRuntimeService.startProcessInstanceById(def.getId(),businessKey,variables);
		
		org.lsqt.act.model.ProcessInstance instance = fillProcessDefinitionInfo(def, e);
		
		
		saveOpinionForStart(loginUser,instance,variables); // 保存“发起意见”
		saveRunInstaceForStart(loginUser,instance,variables); //保存流程实例信息
		saveInstanceVariable(def,instance,variables,variableJSONStart); //保存流程变量信息
		 
		
		//填充（当前流程实例的）待办任务的审批人
		List<String> userIds = new ArrayList<>();
		if (draftNode!=null) { //如果设置了首个拟稿节点
			if (draftNode.getNodeJumpType()!=null && Node.NODE_JUMP_TYPE_AUTO_JUMP.equals(draftNode.getNodeJumpType())) { //如果设置拟稿节点自动跳过
				TaskQuery filter = new TaskQuery();
				filter.setProcessDefinitionId(def.getId());
				filter.setProcessInstanceId(instance.getId());
				List<Task> taskList = db.queryForList("querySimple", Task.class, filter);
				if(taskList!=null && taskList.size()>1) {
					throw new RuntimeException("自动跳过的节点，不支持并发模式");
				}
				
				Task task = taskList.get(0);
				if(task.getTaskDefinitionKey().equals(draftNode.getTaskKey())) { //如果当前任务节点是“自动跳过拟稿节点”
					ActUtil.getTaskService().complete(task.getId(), variables);
				}
				
				instance = ActUtil.convert(actRuntimeService.createProcessInstanceQuery().processInstanceId(instance.getProcessInstanceId()).singleResult());
				instance.setProcessDefinitionKey(def.getKey());
				instance.setProcessDefinitionName(def.getName());
				
				Set<String> nextUids = getNextTaskUserIds(instance,map);
				userIds.addAll(nextUids);
				
				if(!nextUids.contains(loginUserId)) { // (注意：有可能流程发起人和审批人是同一个人）
					userIds.remove(loginUserId);
				}
				
			} else {
				//List<ApproveObject> users = map.get(draftNode.getTaskKey());
				//List<String> uids = ApproveObject.toIdList(users);
				//userIds.addAll(uids);
				//userIds.add(loginUserId); //拟稿人节点，添加startUserId
				userIds.addAll(getNextTaskUserIds(instance,map));
			}
		} else {
			userIds.addAll(getNextTaskUserIds(instance,map));
		}
		
		instance.getExtProperty().put(org.lsqt.act.model.ProcessInstance.CANDIDATE_USER_IDS_KEY, StringUtil.join(userIds,","));
		
		processAutoJumpForEmptyApproveUserNode(instance,getNextNewTask(instance),variables,map);
		
		final long endTime = System.currentTimeMillis();
		log.debug(" ---- 流程起动耗时(ms)："+(endTime-beginTime)+",流程定义ID："+def.getId()+" ,流程实例:"+instance.getId());
		log.debug(" --- 流程发起，获取发起后的任务处理人:"+instance.getExtProperty().get(org.lsqt.act.model.ProcessInstance.CANDIDATE_USER_IDS_KEY));
		return instance;
	}
	
	/**
	 * 获取当前实例的任务（不支持并发模式）
	 * @param instance
	 * @return
	 */
	Task getNextNewTask(org.lsqt.act.model.ProcessInstance instance) {
		org.lsqt.act.model.TaskQuery filter = new org.lsqt.act.model.TaskQuery();
		filter.setProcessDefinitionId(instance.getProcessDefinitionId());
		filter.setProcessInstanceId(instance.getId());
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
	
	/**
	 * 获取审批用户为空自动跳过的节点（不包含拟稿节点、也不包括设置“审批用户为空自动跳过”但是节点设置了用户）
	 * @param def 流程定义
	 * @return 
	 */
	List<Node> getAutoJumpNodeList(String processDefinitionId,Map<String, List<ApproveObject>> nodeUserMap) {
		List<Node> autoJumpNodeList = new ArrayList<>();
		NodeQuery nodeQuery = new NodeQuery();
		nodeQuery.setNodeJumpType(Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP);
		nodeQuery.setDefinitionId(processDefinitionId);
		List<Node> autoJumpNodeSetedList = db.queryForList("queryForPage", Node.class, nodeQuery); // 获取"设置审批用户为空自动跳过"的节点
		
		Set<java.util.Map.Entry<String,List<ApproveObject>>> entrys = nodeUserMap.entrySet();
		for(Node t: autoJumpNodeSetedList) {
			boolean isRealAutoJump = true;
			for(java.util.Map.Entry<String,List<ApproveObject>> e: entrys) {
				List<ApproveObject> vs = e.getValue();
				if(vs!=null && vs.size()>0) {
					isRealAutoJump = false;
					break;
				}
			}
			if(isRealAutoJump) {
				autoJumpNodeList.add(t);
			}
		}
		return autoJumpNodeList;
	}

	/**
	 * 自动跳过的节点，添加虚拟审批用户
	 * @param variables
	 * @param autoJumpNodeList
	 */
	void prepareVariablesForEmptyNodeUser(Map<String, Object> variables,List<Node> autoJumpNodeList ) {
		if(autoJumpNodeList == null || autoJumpNodeList.isEmpty()) {
			return ;
		}
		
		for(Node node : autoJumpNodeList) {
			variables.put(node.getTaskKey(), Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID);
		}
	}
	
	/**
	 * 在流程发起前把流程审批用户"放置"到流程变量
	 * @param variables 流程变量
	 * @param map 流程设置的用户
	 */
	void prepareVariablesForNodeUser(Map<String, Object> variables, Map<String, List<ApproveObject>> map) {
		for (String taskKey : map.keySet()) {
			List<ApproveObject> users = map.get(taskKey);
			variables.put(taskKey,ApproveObject.toIdList(users));
		}
	}
	
	/**
	 * 处理设置审批用户为空的节点自动跳过(如果有连续多个循环处理)
	 * @param actInstance 流程实例
	 * @param task 当前任务对象
	 * @param variable 任务对象的流程变量
	 * @param nodeUserMap 流程实例所有节点的审批用户
	 * @return
	 */
	Task processAutoJumpForEmptyApproveUserNode(ProcessInstance instance,Task task,Map<String, Object> variable,Map<String, List<ApproveObject>> nodeUserMap) {
		
		//如果当前节点有设置审批人，直接break
		List<ApproveObject> list = nodeUserMap.get(task.getTaskDefinitionKey());
		if (list != null && list.size()>0) {
			return task;
		}
		
		while (true) {

			// 查询当前任务对应的节点
			NodeQuery nodeQuery = new NodeQuery();
			nodeQuery.setDefinitionId(task.getProcessDefinitionId());
			nodeQuery.setTaskKey(task.getTaskDefinitionKey());
			List<Node> temp = db.queryForList("queryForPage", Node.class, nodeQuery);
			if (temp == null || temp.isEmpty()) {
				return task;
			}
			Node currNode = temp.get(0);

			// 当前任务节点是否是自动跳过
			if (Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP.equals(currNode.getNodeJumpType())) {
				variable.put(task.getTaskDefinitionKey(), 
						Arrays.asList(Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID));
				actTaskService.complete(task.getId(), variable);

				task = getNextNewTask(instance);

			} else {
				break;

			}
		}
		
		if(StringUtil.isBlank(task.getBusinessKey())){
			task.setBusinessKey(instance.getBusinessKey());
		}
		return task;
	}
	/**
	 * 将流程定义的信息，设置到流程实例对象里
	 * @param def
	 * @param e
	 * @return
	 */
	org.lsqt.act.model.ProcessInstance fillProcessDefinitionInfo(ProcessDefinition def, org.activiti.engine.runtime.ProcessInstance e) {
		org.lsqt.act.model.ProcessInstance instance = ActUtil.convert(e);
		instance.setProcessDefinitionKey(def.getKey());// activiti没有返回这两个属性，补全！！
		instance.setProcessDefinitionName(def.getName());
		return instance;
	}

	/**
	 * 给存在的拟稿节点设置审批用户
	 * @param variables 流程变量
	 * @param loginUserId 登陆用户
	 * @param map 流程设置的审批用户
	 * @param draftNode 拟稿节点
	 */
	void prepareVariablesForExistsDraftNode(Map<String, Object> variables, String loginUserId,
			Map<String, List<ApproveObject>> map, final Node draftNode) {
		if (draftNode!=null) {
			List<ApproveObject> users = map.get(draftNode.getTaskKey());
			if (users == null || users.isEmpty()) {
				ApproveObject obj = new ApproveObject();
				obj.setId(loginUserId);
				map.put(draftNode.getTaskKey(), Arrays.asList(obj)); // 拟稿节点用户

				users = new ArrayList<>();
				users.add(obj);
			}
			List<String> uids = ApproveObject.toIdList(users);
			
			log.debug(String.format( " --- 流程【%s】启动，设置节点【%s】,审批用户ID【%s】",draftNode.getTaskKey(),draftNode.getTaskKey(),StringUtil.join(uids, ",")));
			variables.put(draftNode.getTaskKey(), uids);
		}
	}

	/**
	 * 跟据流程定义，获取拟稿节点
	 * 
	 * @param def
	 * @return
	 */
	Node getDraftNode(ProcessDefinition def) {
		// 流程默认以taskKey值为审批对象的变量（流程图的节点属性"Candidate user"设置）
		NodeQuery query = new NodeQuery();
		query.setTaskBizType(Node.TASK_BIZ_TYPE_DRAFTNODE);
		query.setDefinitionId(def.getId());
		List<Node> nodes = db.queryForList("queryForPage", Node.class, query);
		if (nodes == null || nodes.isEmpty()) {
			return null;
		}

		if (nodes.size() > 1) {
			throw new RuntimeException("拟稿节点设置不能多于两个");
		}
		return nodes.get(0);
	}

	/**
	 * 如果没有业务主键，从流程变量里解析业务主键
	 * @param businessKey 
	 * @param variables 流程变量
	 * @return 业务主键
	 */
	String prepareBusinessKey(String businessKey, Map<String, Object> variables) {
		if(StringUtil.isBlank(businessKey)){ 
			Object bk = variables.get(ActUtil.VARIABLES_BUSINESS_KEY);
			if(bk!=null){
				businessKey = bk.toString();
			}
		}
		return businessKey;
	}

	/**
	 * 加载登陆用户(含主部门、主岗信息)
	 * @param loginUserId 
	 * @return
	 */
	User prepareLoginUser(final String loginUserId) {
		final User loginUser = db2.getById(User.class, loginUserId);
		if(loginUser == null) {
			throw new RuntimeException(String.format("没有找到登陆用户(ID=%s)",loginUserId));
		}
		
		// 填充用户信息
		loginUser.setUserMainOrg(db2.queryForObject("getUserMainOrg", Org.class, loginUser.getUserId())); // 用户的主部门
		loginUser.setUserMainPosition(db2.queryForObject("getUserMainPosition", Position.class, loginUser.getUserId()));// 用户的主岗位
		
		if(loginUser.getUserMainOrg() == null) {
			OrgQuery qr=new OrgQuery();
			qr.setUserId(loginUser.getUserId());
			List<Org> tempOrg = db2.queryForList("queryForPage", Org.class, qr);
			if(tempOrg!=null && tempOrg.size()>0){
				loginUser.setUserMainOrg(tempOrg.get(0));
			}
		}
		
		if(loginUser.getUserMainPosition() == null) {
			PositionQuery qr = new PositionQuery();
			qr.setUserId(loginUser.getUserId());
			List<Position> tempPostion = db2.queryForList("queryForPage", Position.class, qr);
			if(tempPostion!=null && tempPostion.size()>0) {
				loginUser.setUserMainPosition(tempPostion.get(0));
			}
		}
		return loginUser;
	}
	
	/**
	 * 获取流程下一步处理人
	 * @param def
	 * @param instance 
	 * @param nodeUserMap 所有节点的审批用户（即时调用即时解析出来)
	 * @return
	 */
	Set<String> getNextTaskUserIds(org.lsqt.act.model.ProcessInstance instance,Map<String, List<ApproveObject>> nodeUserMap) {
		Set<String> userIds = new HashSet<>();
		TaskQuery filter = new TaskQuery();
		filter.setProcessDefinitionId(instance.getProcessDefinitionId());
		filter.setProcessInstanceId(instance.getId());
		filter.setBusinessKey(instance.getBusinessKey());
		List<Task> taskList = db.queryForList("querySimple", Task.class, filter);//有可能是“并发”的待办
		if(taskList!=null && !taskList.isEmpty()) {
			
			for(Task t: taskList) {
				List<ApproveObject> temp = nodeUserMap.get(t.getTaskDefinitionKey());
				if(temp!=null){
					for(ApproveObject obj: temp) {
						userIds.add(obj.getId());
					}
				}
			}
		}
		return userIds;
	}
	
	/**
	 * 全局流程变量，填充到当间节点变量（就近原则）
	 * @param actInstance
	 * @param currVariable
	
	void globalVariableFill(Map<String,Object> currVariable,Map<String,Object> variables) {
		if(variables!=null && !variables.isEmpty()){
			for(String key: variables.keySet()) {
				if(currVariable.get(key) == null) {
					currVariable.put(key, variables.get(key));
				}
				if(currVariable.get(key)!=null && StringUtil.isBlank(currVariable.get(key).toString())){
					currVariable.put(key,variables.get(key));
				}
			}
		}
	} */
	
	InstanceVariable saveInstanceVariable(ProcessDefinition definition, org.lsqt.act.model.ProcessInstance instance,Map<String,Object> variables,String variableJSONStart) {
		InstanceVariable model = new InstanceVariable();
		model.setBusinessKey(instance.getBusinessKey());
		model.setDefinitionId(definition.getId());
		model.setInstanceId(instance.getId());
		model.setTitle(instance.getTitle());
		model.setVariableJson(JSON.toJSONString(variables));
		model.setVariableJSONStart(variableJSONStart);
		db.save(model);
		return model;
	}
	
	// ------------- 挂起:如果一个流程实例状态暂停，与该实例的流程相关活动将不执行工作（定时器、消息）
	public void suspendProcessInstanceById(String processInstanceId) {
		actRuntimeService.suspendProcessInstanceById(processInstanceId);
	}

	// -------------- 删除 
	public void deleteProcessInstance(String processInstanceId, String deleteReason) {
		actRuntimeService.deleteProcessInstance(processInstanceId, deleteReason);
		
		//deleteProcessInstanceExt(processInstanceId);
	}

	public void deleteProcessInstance(String processInstanceId) {
		actRuntimeService.deleteProcessInstance(processInstanceId,null);
		
		//deleteProcessInstanceExt(processInstanceId);
	}

	void deleteProcessInstanceExt(String processInstanceId) {
		db.executeUpdate("delete from ext_approve_opinion where process_instance_id=?", processInstanceId);
		db.executeUpdate("delete from ext_run_instance where instance_id=?", processInstanceId);
		db.executeUpdate("delete from ext_instance_variable where instance_id=?", processInstanceId);
	}
	
	// ------------------------------------------------------  查询  ----------------------------------------------------------------
	
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
	
	
	// -----------------------------------------------------------------  辅助方法 -----------------------------------------------------------------------------------
	ApproveOpinion saveOpinionForStart(User loginUser,ProcessInstance instance,Map<String,Object> variables) {
		
		//补充流程标题
		String title = variables.get(ActUtil.VARIABLES_TITLE) ==null ? null:variables.get(ActUtil.VARIABLES_TITLE).toString();
		instance.setTitle(title);
		
		// 补充业务类型
		String businessType = variables.get(ActUtil.VARIABLES_BUSINESS_TYPE) == null ? null : variables.get(ActUtil.VARIABLES_BUSINESS_TYPE).toString();
		
		
		
		ApproveOpinion approveOpinion = new ApproveOpinion();
		
		approveOpinion.setProcessInstanceId(instance.getProcessInstanceId());
		approveOpinion.setBusinessKey(instance.getBusinessKey());
		approveOpinion.setCreateTime(new Date());
		approveOpinion.setDefinitionId(instance.getProcessDefinitionId());
		approveOpinion.setDefinitionKey(instance.getProcessDefinitionKey());
		approveOpinion.setDefinitionName(instance.getProcessDefinitionName());
		approveOpinion.setApproveAction(NodeButton.BTN_TYPE_START);
		approveOpinion.setApproveResult(NodeButton.getApproveActionShortDesc(NodeButton.BTN_TYPE_START));
		approveOpinion.setRemark(NodeButton.getApproveActionDesc(NodeButton.BTN_TYPE_START));
		approveOpinion.setApproveTaskName("拟稿人");
		if(StringUtil.isNotBlank(ContextUtil.getLoginName())) {
			approveOpinion.setApproveOpinion(String.format("%s发起流程",ContextUtil.getLoginName()));
		}
		approveOpinion.setBusinessType(businessType);
		
		if (loginUser != null) {
			approveOpinion.setApproveUserId(loginUser.getUserId() + "");
			approveOpinion.setApproveUserName(loginUser.getUserName());
			
			if(loginUser.getUserMainOrg()!=null) {
				approveOpinion.setApproveUserOrgText(loginUser.getUserMainOrg().getName());
			}
			
			if(loginUser.getUserMainPosition()!=null) {
				approveOpinion.setApproveUserPositionText(loginUser.getUserMainPosition().getName());
			}
		}
		
		String variableAndApproveUserIdsJSON = JSON.toJSONString(variables, true);
		//approveOpinion.setApproveTaskCandidateUserIds();
		approveOpinion.setVariablesJson(variableAndApproveUserIdsJSON);
		
		//approveOpinion.setGid(new AnnotationIdGenerator().getUUID58()+"");
		approveOpinion.setUpdateTime(new Date());
		db.save(approveOpinion);
		return approveOpinion ;
	}
	
	/**
	 * 发起流程时，保存流程实例扩展信息
	 * @param instance 
	 * @param businessType 用户自定义的业务类型
	 */
	RunInstance saveRunInstaceForStart(User loginUser,ProcessInstance instance,Map<String,Object> variables) {
		
		RunInstance model = new RunInstance();
		model.setAppCode(StringUtil.isNotBlank(instance.getTenantId()) ? Application.APP_CODE_DEFAULT:instance.getTenantId());
		
		// 补充业务类型
		String businessType = variables.get(ActUtil.VARIABLES_BUSINESS_TYPE) == null ? null : variables.get(ActUtil.VARIABLES_BUSINESS_TYPE).toString();
		model.setBusinessCategory(businessType);
		
		model.setBusinessKey(instance.getBusinessKey());
		model.setCreateTime(new Date());
		model.setProcessDefinitionId(instance.getProcessDefinitionId());
		model.setProcessDefinitionKey(instance.getProcessDefinitionKey());
		model.setProcessDefinitionName(instance.getProcessDefinitionName());
		model.setInstanceId(instance.getId());
		model.setStartUserId(loginUser.getUserId()+"");
		model.setStartUserName(loginUser.getUserName());
		model.setTitle(instance.getTitle());
		model.setBusinessStatus(ActUtil.BUSINESS_STATUS_审批中);
		model.setBusinessStatusDesc(ActUtil.getBusinessStatusDesc(ActUtil.BUSINESS_STATUS_审批中));
		
		if(variables.get(ActUtil.VARIABLES_CREATE_DEPT_ID)!=null) {
			model.setCreateDeptId(variables.get(ActUtil.VARIABLES_CREATE_DEPT_ID)+"");
		}else {
			throw new RuntimeException("填制人不能为空");
		}
		
		if(loginUser.getUserMainOrg()!=null) {
			model.setStartUserOrgText(loginUser.getUserMainOrg().getName());
		}else if(loginUser.getUserOrgList()!=null && !loginUser.getUserOrgList().isEmpty()) {
			model.setStartUserOrgText(loginUser.getUserOrgList().get(0).getName());
		}
			
		if(loginUser.getUserMainPosition()!=null) {
			model.setStartUserPositionText(loginUser.getUserMainPosition().getName());
		}
		
		db.save(model);
		
		return model;
	}


	/**
	 * 优先从上下文取登陆用户，如果没有则从map获取key值为startUserId的字段作为登陆用户ID
	 * @param variables
	 */
	String prepareStartUserId(Map<String, Object> variables) {
		String startUserId = null;

		Object contextLoginId = ContextUtil.getLoginId();
		if (contextLoginId == null) {
			Object user = variables.get(ActUtil.VARIABLES_START_USER_ID);
			if (user != null) {
				startUserId = user.toString();
				actIdentityService.setAuthenticatedUserId(startUserId);
				return startUserId;
			} else {
				log.warn(" --- 没有找到流程发起用户(登陆用户),请指定"+ActUtil.VARIABLES_START_USER_ID);
			}
		} else {
			actIdentityService.setAuthenticatedUserId(contextLoginId.toString());
			startUserId = contextLoginId.toString();
		}
		return startUserId;
	}
	



}
