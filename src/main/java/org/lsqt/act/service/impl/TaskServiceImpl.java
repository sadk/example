package org.lsqt.act.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.context.Context;
import org.activiti.engine.impl.interceptor.Command;
import org.activiti.engine.impl.interceptor.CommandContext;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.persistence.entity.TaskEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.ProcessDefinitionImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.TaskQuery;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.ClientProtocolException;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionFile;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeButtonQuery;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.ReDefinitionQuery;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunInstanceQuery;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.RunTaskAssignForwardCcQuery;
import org.lsqt.act.model.Task;
import org.lsqt.act.service.NodeButtonService;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.act.service.TaskService;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

import freemarker.template.Template;
import freemarker.template.TemplateException;


/**
 * 
 * @author mmyuan
 *
 */
@Service
public class TaskServiceImpl implements TaskService{
	private static final Logger  log = LoggerFactory.getLogger(TaskServiceImpl.class);
			
	private org.activiti.engine.TaskService actTaskService = ActUtil.getTaskService();
	private org.activiti.engine.RepositoryService actRepositoryService = ActUtil.getRepositoryService();
	private org.activiti.engine.RuntimeService actRuntimeService = ActUtil.getRuntimeService();
	
	@Inject private Db db;
	@Inject private PlatformDb db2;
	
	@Inject private NodeButtonService nodeButtonService;
	@Inject private NodeUserService nodeUserService;
	@Inject private UserService userService;
	
	
	public void setDb(Db db) {
		this.db = db;
	}
	
	Command<ActRunningContext> command;
	public void setCommand(Command<ActRunningContext> command) {
		this.command = command;
	}
	// ------------------------------------------------------  动作  -------------------------------------------
	public void claim(String taskId, String userId) {
		actTaskService.claim(taskId, userId);
	}
	
	public void unclaim(String taskId) {
		actTaskService.unclaim(taskId);
	}
	
	public void complete(String taskId) {
		actTaskService.complete(taskId);
	}

	public void complete(String taskId, Map<String, Object> variable) {
	
		actTaskService.complete(taskId, variable);
	}
	
	public void deleteTask(String... ids) {
		if(ids!=null && ids.length>0) {
			for(String taskId : ids) {
				actTaskService.deleteTask(taskId);
			}
		}
	}
	
	public void deleteTask(String deleteReason, String... ids) {
		if(ids!=null && ids.length>0) {
			for(String taskId : ids) {
				actTaskService.deleteTask(taskId,deleteReason);
			}
		}
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
		
		Iterator<Entry<String, List<ApproveObject>>> iter = entrys.iterator();
		while (iter.hasNext()) {
			Entry<String, List<ApproveObject>> e = iter.next();

			for (Node t : autoJumpNodeSetedList) {
				if (e.getKey().equals(t.getTaskKey())) {
					List<ApproveObject> list = e.getValue();
					if (list == null || list.size() == 0) {
						autoJumpNodeList.add(t);
					}
				}
			}
		}
		
		/*
		for(java.util.Map.Entry<String,List<ApproveObject>> e: entrys) {
			
			for (Node t : autoJumpNodeSetedList) {
				if (e.equals(t.getTaskKey())) {
					List<ApproveObject> list = e.getValue();
					if (list == null || list.size() == 0) {
						autoJumpNodeList.add(t);
					}
				}
			}
		}*/
		return autoJumpNodeList;
	}

	/**
	 * 自动跳过的节点，添加虚拟审批用户
	 * @param variables
	 * @param autoJumpNodeList
	 */
	void prepareVariables(Map<String, Object> variables,List<Node> autoJumpNodeList ) {
		if(autoJumpNodeList == null || autoJumpNodeList.isEmpty()) {
			return ;
		}
		
		for(Node node : autoJumpNodeList) {
			variables.put(node.getTaskKey(), Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID);
		}
	}
	
	/**
	 * 获取当前实例并发的任务（可能是会签节点产生）
	 * @param instance 流程实例对象
	 * @return
	 */
	public List<Task> getCurrentMutilTaskList(String processInstanceId) {
		org.lsqt.act.model.TaskQuery filter = new org.lsqt.act.model.TaskQuery();
		filter.setProcessInstanceId(processInstanceId);
		List<Task> taskList = db.queryForList("querySimple", Task.class, filter);
		
		return taskList;
	}
	
	/**
	 * 获取当前实例的任务（不支持并发模式）
	 * @param instance
	 * @return
	 */
	Task getNextNewTask(org.lsqt.act.model.ProcessInstance instance) {
		org.lsqt.act.model.TaskQuery filter = new org.lsqt.act.model.TaskQuery();
		filter.setProcessDefinitionId(instance.getProcessDefinitionId());
		filter.setProcessInstanceId(instance.getProcessInstanceId());
		List<Task> taskList = db.queryForList("querySimple", Task.class, filter);
		if (taskList != null && taskList.size() > 1) {
			throw new RuntimeException("不支持并发模式");
		}

		if(taskList.isEmpty()) {
			return null;
		}
		
		Task task = taskList.get(0);
		return task;
	}
	
	/**
	 *  获取当前实例的任务（不支持并发模式）
	 * @param processInstanceId
	 * @return
	 */
	public Task getNextNewTask(String processInstanceId) {
		org.lsqt.act.model.ProcessInstance instance = new org.lsqt.act.model.ProcessInstance();
		instance.setProcessInstanceId(processInstanceId);
		return getNextNewTask(instance);
	}
	
	/**
	 * 拟稿节点总是有审批人，那就是发流程发起用户
	 * @param draftNode 拟稿节点
	 * @param startUserId 流程发起用户
	 * @param nodeUserMap 流程节点审批用户
	 */
	void prepareDraftNodeUser(Node draftNode, String startUserId, Map<String, List<ApproveObject>> nodeUserMap) {
		if (draftNode != null) {
			ApproveObject temp = new ApproveObject();
			temp.setId(startUserId);

			if (nodeUserMap.get(draftNode.getTaskKey()) != null && nodeUserMap.get(draftNode.getTaskKey()).size() > 0) {
				nodeUserMap.get(draftNode.getTaskKey()).add(temp);
			} else {
				nodeUserMap.put(draftNode.getTaskKey(), Arrays.asList(temp));
			}
		}
	}
	
	/**
	 * 表单中的填制部门，传入到用户规则解析
	 * @param flowVariable
	 * @return
	 */
	Map<String, Object> getNodeUserVariable(String processInstanceId, Map<String, Object> flowVariable) {
		Map<String, Object> nodeUserVariable = new HashMap<String, Object>();
		if (flowVariable.containsKey(ActUtil.VARIABLES_CREATE_DEPT_ID)) {
			nodeUserVariable.put(ActUtil.VARIABLES_CREATE_DEPT_ID, flowVariable.get(ActUtil.VARIABLES_CREATE_DEPT_ID));
		} else {
			RunInstanceQuery query = new RunInstanceQuery();
			query.setInstanceId(processInstanceId);
			List<RunInstance> list = db.queryForList("queryForPage", RunInstance.class, query);
			if (list != null && list.size() > 0) {
				RunInstance inst = list.get(0);
				if (StringUtil.isNotBlank(inst.getCreateDeptId())) {
					nodeUserVariable.put(ActUtil.VARIABLES_CREATE_DEPT_ID, inst.getCreateDeptId());
				} else {
					throw new RuntimeException("填制人部门不能为空");
				}
			}
		}
		return nodeUserVariable;
	}
	
	/**
	 * 获取填制人部门
	 * @param loginUser
	 * @param variables
	 * @return
	 */
	Long prepareCreateDeptId(User loginUser,String processInstanceId, Map<String, Object> variables) {
		Object createDeptIdObj = variables.get(ActUtil.VARIABLES_CREATE_DEPT_ID);
		if (createDeptIdObj != null) {
			return Long.valueOf(createDeptIdObj.toString());
		} else {
			RunInstanceQuery query = new RunInstanceQuery();
			query.setInstanceId(processInstanceId);
			List<RunInstance> list = db.queryForList("queryForPage", RunInstance.class, query);
			if (list != null && list.size() > 0) {
				RunInstance inst = list.get(0);
				if (StringUtil.isNotBlank(inst.getCreateDeptId())) {
					return Long.valueOf(inst.getCreateDeptId());
				}
			}
		}
		
		return null;
	}
	
	/**
	 * 获取会签节点
	 * @param definitionId
	 * @return
	 */
	List<Node> getMeetingNode(String definitionId) {
		List<Node> list = new ArrayList<>();
		NodeQuery query = new NodeQuery();
		query.setTaskBizType(Node.TASK_BIZ_TYPE_MEETINGNODE);
		query.setDefinitionId(definitionId);
		list = db.queryForList("queryForPage", Node.class, query);
		return list;
	}
	
	@SuppressWarnings({ "unchecked" })
	public String complete(Long loginUserId,String taskId, Map<String, Object> variable,ApproveOpinion opinion) {
		
		if(opinion!=null && StringUtil.isBlank(opinion.getApproveAction())) {
			 throw new RuntimeException("审批动作【approveAction】参数不能为空");
		}

		final ActRunningContext context = new ActRunningContext(db);
		
		
		User loginUser = prepareLoginUser(loginUserId, opinion);
		Task task = getById(taskId);

		if(task == null) {
			throw new RuntimeException(String.format("任务id=%s的任务不存在或已被办理",taskId));
		}
		
		
		
		if(NodeButton.BTN_TYPE_ACCEPT.equals(opinion.getApproveAction())) {
			actTaskService.claim(taskId, loginUserId.toString());
			return null;
		}
		
		
		ProcessInstance actInstance = actRuntimeService.createProcessInstanceQuery().includeProcessVariables().processInstanceId(task.getProcessInstanceId()).singleResult();
		Object startUserIdObj = actInstance.getProcessVariables().get(ActUtil.VARIABLES_START_USER_ID);
		if(startUserIdObj == null) throw new RuntimeException("没有找到流程发起用户");
		
		
		// 补全业务主键
		if(StringUtil.isBlank(task.getBusinessKey())){
			task.setBusinessKey(actInstance.getBusinessKey());
		}
		
		// 预获取拟稿节点
		NodeQuery query = new NodeQuery();
		query.setTaskBizType(Node.TASK_BIZ_TYPE_DRAFTNODE);
		query.setDefinitionId(task.getProcessDefinitionId());
		final List<Node> draftNodeList = db.queryForList("queryForPage", Node.class, query);
		final Node draftNode = (draftNodeList == null || draftNodeList.isEmpty()  ? null: draftNodeList.get(0));
		
		if(draftNode!=null) { //如果是拟稿节点，存入发起人id
			draftNode.getExtProperty().put(ActUtil.VARIABLES_START_USER_ID, actInstance.getProcessVariables().get(ActUtil.VARIABLES_START_USER_ID));
		}
		
		// 当前节点用户,流程图里Candidate (注意: 拟稿节点可不需配置用户,直接以发起人startUserId的流程变量做审批用户)
		Long createDeptId = prepareCreateDeptId(loginUser,actInstance.getId(),variable);
		
		
		Map<String,Object> nodeUserVariable = new HashMap<>();
		nodeUserVariable.put(ActUtil.VARIABLES_CREATE_DEPT_ID, createDeptId);
		Map<String, List<ApproveObject>> nodeUserMap = nodeUserService.getNodeUsers(Long.valueOf(startUserIdObj.toString()), task.getProcessDefinitionId(),nodeUserVariable);

		prepareDraftNodeUser(draftNode,startUserIdObj.toString(),nodeUserMap);
		
		for(String key: nodeUserMap.keySet()) {
			variable.put(key, ApproveObject.toIdList(nodeUserMap.get(key)));
		}
		final List<Node> autoJumpNodeList = getAutoJumpNodeList(task.getProcessDefinitionId(),nodeUserMap);
		prepareVariables(variable, autoJumpNodeList);
		
		if(draftNode!=null && draftNode.getTaskKey().equals(task.getTaskDefinitionKey())) {
			variable.put(ActUtil.VARIABLES_START_USER_ID,actInstance.getProcessVariables().get(ActUtil.VARIABLES_START_USER_ID)); // 设置发起人变量
		} else {
			task = processAutoJumpForEmptyApproveUserNode(loginUser,opinion,task,draftNode,actInstance,task, variable,nodeUserMap);
			task = processAutoJumpForRessolveEmptyApproveUserNode(loginUser, opinion, task, draftNode, actInstance, task, nodeUserVariable, nodeUserMap);
		}
		
		// 非拟稿节点，检查节点审批人不能为空(空审批人节点自动跳过的除外）
		if (draftNode != null && !draftNode.getTaskKey().equals(task.getTaskDefinitionKey())) { 

			boolean isAutoJumpNode = false; // 当前任务节点是否是自动跳过的节点
			for (Node nd : autoJumpNodeList) {
				if (task.getTaskDefinitionKey().equals(nd.getTaskKey())) {
					isAutoJumpNode = true;
					break;
				}
			}

			if (!isAutoJumpNode) {
				List<ApproveObject> approveUsers = nodeUserMap.get(task.getTaskDefinitionKey());
				if (approveUsers == null || approveUsers.isEmpty()) {
					ProcessDefinition def= ActUtil.getRepositoryService().createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
					throw new RuntimeException("没有解析到当前节点审批用户，请联系管理员设置流程节点的审批用户【节点编码:"+task.getTaskDefinitionKey()+",流程版本号:"+def.getVersion());
				}
			}
		}
		
		

		
		// 添加意见(含附件)
		// 1.审批时间的任务信息字段
		opinion.setApproveTaskId(task.getId());
		opinion.setApproveTaskKey(task.getTaskDefinitionKey());
		opinion.setApproveTaskName(task.getName());
		
		// 2.整个流程的定义信息字段
		opinion.setDefinitionId(task.getProcessDefinitionId());
		opinion.setDefinitionName(task.getProcessDefinitionName());
		opinion.setDefinitionKey(task.getProcessDefinitionKey());
		
		
		// 3.流程实例信息字段
		opinion.setProcessInstanceId(task.getProcessInstanceId());
		
		if(StringUtil.isNotBlank(task.getBusinessKey())){
			opinion.setBusinessKey(task.getBusinessKey());
		}else {
			if(actInstance!=null) {
				opinion.setBusinessKey(actInstance.getBusinessKey());
			}
		}

		// 4.审批用户信息
		opinion.setApproveUserId(loginUserId+"");
		if(loginUser.getUserMainOrg()!=null) {
			opinion.setApproveUserOrgText(loginUser.getUserMainOrg().getName());
		}
		if(loginUser.getUserMainPosition()!=null) {
			opinion.setApproveUserPositionText(loginUser.getUserMainPosition().getName());
		}
		
		List<String> userIds = (List<String>)variable.get(task.getTaskDefinitionKey());
		if(userIds!=null) {
			opinion.setApproveTaskCandidateUserIds(StringUtil.join(userIds, ","));
		}
		// 拟稿节点，把流程发起人添加进来
		if(draftNode!=null && draftNode.getTaskKey().equals(task.getTaskDefinitionKey())) {
			String startUserId = variable.get(ActUtil.VARIABLES_START_USER_ID) +"";
			if(StringUtil.isBlank(opinion.getApproveTaskCandidateUserIds())) {
				opinion.setApproveTaskCandidateUserIds(startUserId);
			}else {
				opinion.setApproveTaskCandidateUserIds(opinion.getApproveTaskCandidateUserIds() + ","+startUserId);
			}
		}
		opinion.setVariablesJson(JSON.toJSONString(variable));
		 
		opinion.setApproveResult(NodeButton.getApproveActionShortDesc(opinion.getApproveAction()));
		opinion.setCreateTime(new Date());
		opinion.setUpdateTime(new Date());
		opinion.setApproveUserName(loginUser.getUserName());
		if(variable.get(ActUtil.VARIABLES_APPROVE_OPINION)!=null) {
			opinion.setApproveOpinion(variable.get(ActUtil.VARIABLES_APPROVE_OPINION)+"" );
		}
		db.save(opinion);
		
		try{
		// 补全流程附件表其它字段
		if(opinion!=null && opinion.getAttachmentList()!=null) {
			for(ApproveOpinionFile f : opinion.getAttachmentList()) {
				f.setApproveProcessInstanceId(task.getProcessInstanceId());
				f.setApproveTaskId(task.getId());
				f.setApproveTaskKey(task.getProcessDefinitionKey());
				f.setBusinessKey(task.getBusinessKey());
				f.setUpdateTime(new Date());
				if(loginUser!=null) {
					f.setUploadUserId(loginUser.getUserId().toString());
					f.setUploadUserName(loginUser.getUserName());
				}
				f.setOpinionId(opinion.getId().toString());
				db.update(f);
			}
		}
		
		
		if(opinion.getAttachmentList() != null && opinion.getAttachmentList().size()>0) {
			db.batchSave(opinion.getAttachmentList());
		}
		
		
		context.setLoginUser(loginUser);
		context.setCurrTask(task);
		context.setCurrActProcessInstance(actInstance);
		context.setDraftNode(draftNode);
		context.setApproveOpinion(opinion);
		
		context.getForm().setAction(opinion.getApproveAction());
		context.getForm().setActionTarget(opinion.getRejectToChooseNodeTaskKey());
		context.getForm().setBussinessKey(actInstance.getBusinessKey());
		context.getForm().setCreateDeptId(String.valueOf(createDeptId));
		context.getForm().setFlowNo(actInstance.getProcessVariables().get(ActUtil.VARIABLES_BUSINESS_FLOW_NO)+"");
		
		
		
		/* 加签、转发、抄送, 流程引擎不往下走，只是添加可以查看到待办的用户，见表：ext_run_task_assign_forward_cc
		 * 1.加签    流程支持加签，可以加签至流程节点之外的人员审批(A选择B来加签，B处理完了后还要再回到A处理!!!)
		 * 2.转发    审批人可以将流程转发至下级或平级人员查看、审批
		 * 3.抄送    审批人可以将流程抄送至上级人员查看
		 */
		if(NodeButton.BTN_TYPE_ADD_ASSIGN.equals(opinion.getApproveAction()) 
				||NodeButton.BTN_TYPE_FORWORD_READ.equals(opinion.getApproveAction())
				||NodeButton.BTN_TYPE_COPY_SEND.equals(opinion.getApproveAction())) {
			if(StringUtil.isBlank(opinion.getAssignForwardCcUserIds())) {
				throw new RuntimeException("加签、转发或抄送用户不能为空");
			}
			
			if(NodeButton.BTN_TYPE_ADD_ASSIGN.equals(opinion.getApproveAction()) && 
					StringUtil.split(opinion.getAssignForwardCcUserIds(),",").contains(loginUserId.toString())) {
				throw new RuntimeException("不能加签给自己");
			}
			
			String sumForwordUids = "";
			String sumRemark = "";
			if (NodeButton.BTN_TYPE_COPY_SEND.equals(opinion.getApproveAction())) { // 如果是转发累积所有的转发人
				StringBuffer temp = new StringBuffer();
				StringBuffer tempRemark = new StringBuffer();
				
				RunTaskAssignForwardCcQuery ccQuery = new RunTaskAssignForwardCcQuery();
				ccQuery.setTaskId(taskId);
				ccQuery.setApproveAction(NodeButton.BTN_TYPE_COPY_SEND);
				ccQuery.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
				List<RunTaskAssignForwardCc> tempList = db.queryForList("queryForPage", RunTaskAssignForwardCc.class,ccQuery);
				if (tempList != null) {
					for (RunTaskAssignForwardCc e : tempList) {
						if (StringUtil.isNotBlank(e.getAssignForwardCcUserIds())) {
							List<String> ids = StringUtil.split(e.getAssignForwardCcUserIds(), ",");
							temp.append(StringUtil.join(ids, ","));
							tempRemark.append(e.getRemark());
						}
					}
				}
				sumForwordUids = temp.toString();
				sumRemark = tempRemark.toString();
			}
			
			// 同一个任务，加签操作以最后的一个为准
			String sql = String.format("delete from %s where task_id=?", db.getFullTable(RunTaskAssignForwardCc.class));
			db.executeUpdate(sql, task.getId());
			
			RunTaskAssignForwardCc model = new RunTaskAssignForwardCc();
			model.setApproveAction(opinion.getApproveAction());
			model.setApproveUserId(loginUserId.toString());
			model.setApproveUserName(loginUser.getUserName());
			
			String assignForwardCcUserIds = opinion.getAssignForwardCcUserIds();
			if (!assignForwardCcUserIds.startsWith(",")) {
				assignForwardCcUserIds = "," + assignForwardCcUserIds;
			}
			if (!assignForwardCcUserIds.endsWith(",")) {
				assignForwardCcUserIds = assignForwardCcUserIds + ",";
			}
			
			if(StringUtil.isNotBlank(sumForwordUids)) {
				assignForwardCcUserIds = assignForwardCcUserIds +sumForwordUids+","; // 添加积累的抄送用户
			}
			model.setAssignForwardCcUserIds(assignForwardCcUserIds);
			
			model.setBusinessKey(actInstance.getBusinessKey());
			//model.setBusinessType();
			model.setDefinitionId(task.getProcessDefinitionId());
			model.setDefinitionKey(task.getProcessDefinitionKey());
			model.setDefinitionName(task.getProcessDefinitionName());
			model.setProcessInstanceId(actInstance.getId());
			model.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
			model.setTaskId(task.getId());
			model.setTaskKey(task.getTaskDefinitionKey());
			model.setTaskName(task.getName());
			
			
			String remark =String.format("用户%s%s给用户%s,(操作日期%s)",loginUserId,NodeButton.getApproveActionDesc(opinion.getApproveAction()), opinion.getAssignForwardCcUserIds(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
			if (StringUtil.isNotBlank(sumRemark)) {
				model.setRemark(remark + "; " + sumRemark);
			} else {
				model.setRemark(remark);
			}
			db.save(model);
			
			
			
			// 处理下一步审批人
			String nextTaskCandidateUserIds = "";
			if(NodeButton.BTN_TYPE_ADD_ASSIGN.equals(opinion.getApproveAction())
					|| NodeButton.BTN_TYPE_FORWORD_READ.equals(opinion.getApproveAction())) {
				nextTaskCandidateUserIds =  opinion.getAssignForwardCcUserIds();
				 
			}else {
				List<ApproveObject> tempObjs = nodeUserMap.get(task.getTaskDefinitionKey());
				if (tempObjs != null) {
					nextTaskCandidateUserIds = StringUtil.join(ApproveObject.toIdList(tempObjs), ",");
				}
			}
			
			//先执行全局回调角本、再执行按钮回调角本（就近原则!!)
			executeGlobalAfterScript(loginUser,ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds);
			executeAfterScript(loginUser,task,opinion,draftNode,nextTaskCandidateUserIds);
			
			
			return nextTaskCandidateUserIds;
		}
		
		
		/** 用户点击审批按钮逻辑 */
		if(NodeButton.BTN_TYPE_AGREE.equals(opinion.getApproveAction()) || NodeButton.BTN_TYPE_RESUBMIT.equals(opinion.getApproveAction())) {

			
			//检查1: 当前的审批的节点是否是上一步"用户驳（退）回到选择的节点"，
			boolean isLastStepRejectToChooseNode = false;
			
			ApproveOpinion backedLastStepNode = null; //退回的上一步
			
			ApproveOpinionQuery filter = new ApproveOpinionQuery();
			filter.setProcessInstanceId(task.getProcessInstanceId());
			filter.setDefinitionId(task.getProcessDefinitionId());
			
			filter.getApproveActionListNotIn().add(NodeButton.BTN_TYPE_ADD_ASSIGN);  // 去除加签、转发、抄送的数据
			filter.getApproveActionListNotIn().add(NodeButton.BTN_TYPE_FORWORD_READ);
			filter.getApproveActionListNotIn().add(NodeButton.BTN_TYPE_COPY_SEND);
			
			List<ApproveOpinion> apprOpinionList = db.queryForList("queryForPage", ApproveOpinion.class, filter);
			if (apprOpinionList.size() >= 2) {
				backedLastStepNode = apprOpinionList.get(apprOpinionList.size() - 2);
				if (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(backedLastStepNode.getApproveAction())
						&& StringUtil.isNotBlank(backedLastStepNode.getRejectToChooseNodeTaskKey())) {
					isLastStepRejectToChooseNode = true;
				} else if (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE_FOR_MUTIL_BACK
						.equals(backedLastStepNode.getApproveAction())
						&& StringUtil.isNotBlank(backedLastStepNode.getRejectToChooseNodeTaskKey())) {
					isLastStepRejectToChooseNode = true;
				}
			}
			
			Task lastTask = Task.getCloneObject(task);
			
			RunTaskAssignForwardCc assignTask = getAssignUserApporve(loginUserId,task);
			
			boolean isMutilReject = isMutilReject(apprOpinionList);
			if(!isMutilReject) {
				
				ApproveOpinion node = getUnResetMutilRejectNode(apprOpinionList);//查找最后一步退回的节点，未归位的,按“单个退回”处理
				if (node != null) {
					isMutilReject = false;
					backedLastStepNode = node;
					isLastStepRejectToChooseNode = true;
				}
			}
			
			if (isMutilReject) { // 多级退回
				
				// 如果是多级退回，已退回到拟稿人，则重新按流程顺序走；否则逆着原来的节点路径跳跃式前进,如： 1(拟稿节点)-2-3-4-5-6-7-8-9, 9退-7-5-2，2提交时执行路径为 2-5-7-9
				if(draftNode!=null && task.getTaskDefinitionKey().equals(draftNode.getTaskKey())) {//如果退回到拟稿节点了（也就是由拟稿节点提交过来的）
					List<Long> ids = ApproveOpinion.getIdList(apprOpinionList);
					if(!ids.isEmpty()) {
						String sql = String.format("update ext_approve_opinion set reject_re_run_complete_status=? where id in (%s)",StringUtil.join(ids, ","));
						db.executeUpdate(sql,ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE);
					}
					
					NextCompleteGeneralHandler nextComplete = new NextCompleteGeneralHandler();
					nextComplete.setTaskServiceImpl(this);
					
					Map<String,Object> argsMap = prepareCompleteVariable(actInstance, variable);
					argsMap.putAll(prepareMeetingVariable(ActUtil.convert(actInstance), nodeUserMap)); // 可能有会签
					
					return nextComplete.execute(loginUser, actInstance, task, argsMap , nodeUserMap, draftNode, opinion, lastTask);
				}
				
				
				ApproveOpinion lastRejectNode = getLastMutilRejectNode(apprOpinionList);
				
				Map<String,Object> argsMap = prepareCompleteVariable(actInstance, variable);
				argsMap.putAll(prepareMeetingVariable(ActUtil.convert(actInstance), nodeUserMap)); // 可能有会签
				
				jump(taskId, lastRejectNode.getApproveTaskKey(),prepareCompleteVariable(actInstance, variable));
				 
				task = getNextNewTask(ActUtil.convert(actInstance));
				task = processAutoJumpForEmptyApproveUserNode(loginUser,opinion,lastTask,draftNode,actInstance,task, variable,nodeUserMap);
				
				lastRejectNode.setRejectReRunCompleteStatus(ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE);
				db.update(lastRejectNode, "rejectReRunCompleteStatus");
				
				String nextTaskCandidateUserIds = getNextTaskCandidateUserIds(actInstance.getBusinessKey(), task.getProcessDefinitionId(), actInstance.getId(), nodeUserMap, draftNode,task);
				log.debug("多级退回,审批的户IDs:"+nextTaskCandidateUserIds);
				
				if ((task!=null && draftNode!=null ) && (task.getTaskDefinitionKey().equals(draftNode.getTaskKey()))) {
					updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已退回);
				}else {
					updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_审批中);
				}
				
				executeGlobalAfterScript(loginUser,ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds);
				executeAfterScript(loginUser, lastTask, opinion, draftNode,nextTaskCandidateUserIds);// 执行按钮回调角本
				//executeAfterScriptForAutoJumpNode(loginUser,opinion,lastTask,task,draftNode,nextTaskCandidateUserIds);

				return nextTaskCandidateUserIds;
				
			} else if (backedLastStepNode != null && isLastStepRejectToChooseNode) { //单级退回
				
				Map<String,Object> temp = prepareCompleteVariable(actInstance, variable);
				temp.putAll(prepareMeetingVariable(ActUtil.convert(actInstance), nodeUserMap)); // 可能有会签
				
				if (StringUtil.isNotBlank(backedLastStepNode.getVariablesJson())) {
					temp.putAll(JSON.parseObject(backedLastStepNode.getVariablesJson(), Map.class));
				}  
				
				jump(taskId, backedLastStepNode.getApproveTaskKey(),temp);
				
				task = getNextNewTask(ActUtil.convert(actInstance));
				task = processAutoJumpForEmptyApproveUserNode(loginUser,opinion,lastTask,draftNode,actInstance,task, variable,nodeUserMap);
				
				backedLastStepNode.setRejectReRunCompleteStatus(ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE);
				db.update(backedLastStepNode, "rejectReRunCompleteStatus");
				
				if ((task!=null && draftNode!=null ) && (task.getTaskDefinitionKey().equals(draftNode.getTaskKey()))) {
					updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已退回);
				}else {
					updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_审批中);
				}
				
				String nextTaskCandidateUserIds = getNextTaskCandidateUserIds(actInstance.getBusinessKey(), task.getProcessDefinitionId(), actInstance.getId(), nodeUserMap, draftNode,task);
				
				executeGlobalAfterScript(loginUser,ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds); //执行全局回调角本
				executeAfterScript(loginUser, lastTask, opinion, draftNode,nextTaskCandidateUserIds);// 执行按钮回调角本
				
				return nextTaskCandidateUserIds;
				
			} else if (assignTask!=null) { //检查2： 是否是加签用户在处理，处理完毕跳回至给他加签的主人
				
				Map<String,Object> argsMap = prepareCompleteVariable(actInstance, variable);
				argsMap.putAll(prepareMeetingVariable(ActUtil.convert(actInstance), nodeUserMap)); // 可能有会签
				
				jump(taskId, assignTask.getTaskKey(),argsMap);
				
				opinion.setApproveAction(NodeButton.BTN_TYPE_ADD_ASSIGN_AGREE);
				opinion.setApproveResult("加签提审");
				opinion.setApproveTaskName(opinion.getApproveUserPositionText());
				 
				db.update(opinion, "approveAction","approveResult","approveTaskName");
				
				if (!isInstanceEnded(actInstance.getId())) {
					task = getNextNewTask(ActUtil.convert(actInstance));
				}
				
				updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_审批中);
				
				String nextTaskCandidateUserIds = getNextTaskCandidateUserIds(actInstance.getBusinessKey(), task.getProcessDefinitionId(), actInstance.getId(), nodeUserMap, draftNode,task);
				
				executeGlobalAfterScript(loginUser,ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds); //执行全局回调角本
				executeAfterScript(loginUser, lastTask, opinion, draftNode,nextTaskCandidateUserIds);// 执行按钮回调角本
				
				db.executeUpdate("update ext_run_task_assign_forward_cc set task_complete_type=1 where process_instance_id=? and task_id=? and approve_action= ? ", actInstance.getId(),lastTask.getId(),NodeButton.BTN_TYPE_ADD_ASSIGN);
				
				return nextTaskCandidateUserIds;
			} else {
				
				NextCompleteGeneralHandler nextComplete = new NextCompleteGeneralHandler();
				nextComplete.setTaskServiceImpl(this);

				
				
				Map<String,Object> argsMap = prepareCompleteVariable(actInstance, variable);
				argsMap.putAll(prepareMeetingVariable(ActUtil.convert(actInstance), nodeUserMap)); // 可能有会签
				log.debug(JSON.toJSONString(argsMap, true));
				String cadidateUserIds= nextComplete.execute(loginUser, actInstance, task, argsMap , nodeUserMap, draftNode, opinion, lastTask);
				
				if(isInstanceEnded(actInstance.getProcessInstanceId())) {
					updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已通过);
				} else {
					updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_审批中);
				}
				
				return cadidateUserIds ;
			}
		}
		
		if(NodeButton.BTN_TYPE_REJECT.equals(opinion.getApproveAction())) {//驳回(驳回到上一步) 
				throw new UnsupportedOperationException("暂不支持");
			/*
			ApproveOpinionQuery query = new ApproveOpinionQuery();
			query.setProcessInstanceId(task.getProcessInstanceId());
			List<ApproveOpinion> opinionList = db.queryForList("queryForPage", ApproveOpinion.class, query);
			if(opinionList!=null && (opinionList.size()- 1)==1){
				throw new RuntimeException("流程发起没未进行任何审批，不能驳回");
			}
			
			String lastApproveTaskKey = task.getTaskDefinitionKey();
			
			ApproveOpinion stepLst1Node = opinionList.get(opinionList.size()-2); // 上一步
			ApproveOpinion stepLst2Node = opinionList.get(opinionList.size()-3); // 上两步
			
			
			jump(taskId, stepLst2Node.getApproveTaskKey()); // 退两步，往前走一步，生成待办
			//actTaskService.complete(taskId, variables);
			return ;
			*/
		}
	
		/**(新城 - 撤回)发起人在流程的任何节点都可以撤回流程,流程设置时需要定义拟稿节点**/
		if (NodeButton.BTN_TYPE_REJECT_TO_STARTER.equals(opinion.getApproveAction())  //驳回到发起人(指的是驳回到拟稿节点) 
				||NodeButton.BTN_TYPE_START_USER_REBACK.equals(opinion.getApproveAction())) { //（发起人）撤回
			
			Task lastTask = Task.getCloneObject(task);
			
			// 优先判断是否是加签过来的任务
			if(NodeButton.BTN_TYPE_REJECT_TO_STARTER.equals(opinion.getApproveAction())) {
				
				RunTaskAssignForwardCc assignTask = getAssignUserApporve(loginUserId,task);
				if (assignTask!=null) {
				
					Map<String,Object> argsMap = prepareCompleteVariable(actInstance, variable);
					//argsMap.putAll(prepareMeetingVariable(actInstance, nodeUserMap)); // 可能有会签
					
					jump(taskId, assignTask.getTaskKey(),argsMap); // 如果是加签 过来的，需要跳转到“主人”节点
					
					
					opinion.setApproveAction(NodeButton.BTN_TYPE_ADD_ASSIGN_DISAGREE);
					opinion.setApproveResult(NodeButton.getApproveActionDesc(NodeButton.BTN_TYPE_ADD_ASSIGN_DISAGREE));
					db.update(opinion, "approveAction","approveResult");
					
					if (!isInstanceEnded(actInstance.getId())) {
						task = getNextNewTask(ActUtil.convert(actInstance));
					}
					
					String nextTaskCandidateUserIds = getNextTaskCandidateUserIds(actInstance.getBusinessKey(), task.getProcessDefinitionId(), actInstance.getId(), nodeUserMap, draftNode,task);
					
					
					executeGlobalAfterScript(loginUser,ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds);
					//executeAfterScriptForAutoJumpNode(loginUser,opinion,lastTask,task,draftNode,nextTaskCandidateUserIds);
					executeAfterScript(loginUser, lastTask, opinion, draftNode,nextTaskCandidateUserIds);// 执行按钮回调角本
					
					updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_审批中);
					
					db.executeUpdate("update ext_run_task_assign_forward_cc set task_complete_type=1 where process_instance_id=? and task_id=? and approve_action= ? ", actInstance.getId(),lastTask.getId(),NodeButton.BTN_TYPE_ADD_ASSIGN);
					
					return nextTaskCandidateUserIds;
				}
			}
			
			// 撤回，使终是流程发起人才能撤回，如果别有权限帮发起人撤回，也是以发起人的身分撤回
			if (NodeButton.BTN_TYPE_START_USER_REBACK.equals(opinion.getApproveAction())) {
				RunInstanceQuery ft = new RunInstanceQuery();
				ft.setInstanceId(actInstance.getProcessInstanceId());
				RunInstance ri = db.queryForObject("queryForPage", RunInstance.class, ft);
				if(ri!=null && (opinion!=null && opinion.getId()!=null) && draftNode!=null) {
					opinion.setApproveTaskName(draftNode.getTaskName());
					opinion.setApproveTaskKey(draftNode.getTaskKey());
					opinion.setApproveTaskCandidateUserIds(ri.getStartUserId());
					opinion.setApproveUserName(ri.getStartUserName());
					opinion.setApproveUserOrgText(ri.getStartUserOrgText());
					opinion.setApproveUserPositionText(ri.getStartUserPositionText());
					db.update(opinion, "approveTaskName","approveTaskKey","approveTaskCandidateUserIds","approveUserName","approveUserOrgText","approveUserPositionText");
				}
				
			}
			
			// 查找设置的拟稿节点，如果没有，调用流程图起始节点的下一个节点??????
			if(draftNode == null) {
				throw new RuntimeException(String.format("没有找到拟稿节点，请联系管理员设置流程拟稿节点【流程ID=%s,流程名称=%s】",task.getProcessDefinitionId(),task.getName()));
			}
			if(draftNodeList.size()>1) {
				throw new RuntimeException(String.format("一个流程不能有超过有一个以上的拟稿节点【流程ID=%s,流程名称=%s】",task.getProcessDefinitionId(),task.getName()));
			}
			
			Map<String,Object> argsMap = prepareCompleteVariable(actInstance, variable);
			argsMap.putAll(prepareMeetingVariable(ActUtil.convert(actInstance), nodeUserMap)); 
			
			
			List<Task> mutilTaskList = new ArrayList<>();
			while (true) {
				mutilTaskList = getCurrentMutilTaskList(actInstance.getProcessInstanceId());// 当前流程的任务可能是（会签）多实例
				if (mutilTaskList != null && mutilTaskList.size() > 1) {
					for (int i=0; i < mutilTaskList.size(); i++) {
						try{
							List<Task> tempTask = getCurrentMutilTaskList(actInstance.getProcessInstanceId());
							if(tempTask!=null && tempTask.size()>1) {
								actTaskService.complete(mutilTaskList.get(i).getId(), argsMap);
							}
						}catch(Exception ex) {
							ex.printStackTrace();
						}
					}
					String nextTaskCandidateUserIds = getMutilTaskCandidateUserIds(mutilTaskList, nodeUserMap);
					executeGlobalAfterScript(loginUser, ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds);
					executeAfterScript(loginUser,task,opinion,draftNode,nextTaskCandidateUserIds);
				} 
				
				if (isInstanceEnded(actInstance.getProcessInstanceId())) {
					break;
				}
				if (mutilTaskList.size() == 1) {
					lastTask = getNextNewTask(actInstance.getProcessInstanceId());
					break;
				}
			}
			
			jump(lastTask.getId(), draftNode.getTaskKey(),argsMap);
			
			
			if (NodeButton.BTN_TYPE_REJECT_TO_STARTER.equals(opinion.getApproveAction())) {
				updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已退回);
			} else if (NodeButton.BTN_TYPE_START_USER_REBACK.equals(opinion.getApproveAction())) {
				updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已撤回);
			}

			String nextTaskCandidateUserIds=getNextTaskCandidateUserIds(actInstance.getBusinessKey(), actInstance.getProcessDefinitionId(), actInstance.getProcessInstanceId(), nodeUserMap, draftNode, lastTask);
			executeGlobalAfterScript(loginUser, ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds);
			executeAfterScript(loginUser,task,opinion,draftNode,nextTaskCandidateUserIds);
			
			return nextTaskCandidateUserIds;
		}
		
		/**(新城 - 退回到历史节点)审批人可以将流程退回至自己所在节点之前的任何节点，被退回人接受退回的流程可以直接提交至退回流程的审批人**/
		if (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(opinion.getApproveAction())
				|| NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE_FOR_MUTIL_BACK.equals(opinion.getApproveAction())) {
			if(StringUtil.isBlank(opinion.getRejectToChooseNodeTaskKey())) {
				if(opinion.getId()!=null){
					db.deleteById(ApproveOpinion.class, opinion.getId());
				}
				throw new RuntimeException("请选择要驳回的节点任务");
			}
			
			Map<String,Object> argsMap = prepareCompleteVariable(actInstance, variable);
			argsMap.putAll(prepareMeetingVariable(ActUtil.convert(actInstance), nodeUserMap)); // 可能有会签
			
			jump(taskId, opinion.getRejectToChooseNodeTaskKey(),argsMap);
			
			Task lastTask = Task.getCloneObject(task); // 用于执行后置角本用
			task = getNextNewTask(actInstance.getProcessInstanceId());
			if((task!=null && draftNode!=null) && task.getTaskDefinitionKey().equals(draftNode.getTaskKey())) {
				updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已退回);
			} else {
				updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_审批中);
			}
			
			String nextTaskCandidateUserIds = getNextTaskCandidateUserIds(actInstance.getBusinessKey(), task.getProcessDefinitionId(), actInstance.getId(), nodeUserMap, draftNode,task);
			executeGlobalAfterScript(loginUser,ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds);
			executeAfterScript(loginUser,lastTask,opinion,draftNode,nextTaskCandidateUserIds);
			
			return nextTaskCandidateUserIds;
		}
		
		/**任意撤回*/
		if (NodeButton.BTN_TYPE_ANY_REBACK.equals(opinion.getApproveAction())) {
			//查询当前登陆用户最近的一个审批，撤回!!!
			ApproveOpinionQuery filter = new ApproveOpinionQuery();
			filter.setIdNotIn(opinion.getId().toString()); // 去除进入方法时刚插入的"意见"记录
			filter.setProcessInstanceId(actInstance.getProcessInstanceId());
			filter.setApproveUserId(loginUserId.toString());
			filter.getApproveActionListNotIn().add(NodeButton.BTN_TYPE_START); 
			filter.setSortOrder("desc");
			filter.setSortField("id");
			filter.setLimit(1L);
			
			String destNodeKey = null;
			ApproveOpinion nearestNode = null;
			List<ApproveOpinion> nearNodeList = db.queryForList("queryForPage", ApproveOpinion.class, filter);
			
			if (nearNodeList == null || nearNodeList.isEmpty()) {
				destNodeKey = draftNode.getTaskKey();
				
			} else if (!nearNodeList.isEmpty()) {
				nearestNode = nearNodeList.get(0);
				destNodeKey = nearestNode.getApproveTaskKey();
				
			} else {
				throw new RuntimeException("没有找到当前用户最近的已审批过的节点");
			}
			
			Map<String,Object> argsMap = prepareCompleteVariable(actInstance, variable);
			argsMap.putAll(prepareMeetingVariable(ActUtil.convert(actInstance), nodeUserMap)); // 可能有会签
			
			jump(taskId, destNodeKey,argsMap);
			
			task = getNextNewTask(actInstance.getProcessInstanceId());
			if((task!=null && draftNode!=null) && task.getTaskDefinitionKey().equals(draftNode.getTaskKey())) {
				updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已撤回);
			} else {
				updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_审批中);
			}
			
			
			String nextTaskCandidateUserIds = getNextTaskCandidateUserIds(actInstance.getBusinessKey(), task.getProcessDefinitionId(), actInstance.getId(), nodeUserMap, draftNode,task);
			executeGlobalAfterScript(loginUser,ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds);
			executeAfterScript(loginUser,task,opinion,draftNode,nextTaskCandidateUserIds);
			
			return nextTaskCandidateUserIds;
		}
		
		
		
		/**作废(删除当前的流程实例)*/
		if(NodeButton.BTN_TYPE_ABORT.equals(opinion.getApproveAction())
				|| NodeButton.BTN_TYPE_DISAGREE_TO_CLEAR.equals(opinion.getApproveAction())) { 
			
			String nextTaskCandidateUserIds = getNextTaskCandidateUserIds(actInstance.getBusinessKey(), task.getProcessDefinitionId(), actInstance.getId(), nodeUserMap, draftNode,task);
			
			actRuntimeService.deleteProcessInstance(actInstance.getId(), String.format("%s作废流程实例(用户ID=%s),动作：%s",loginUser.getUserName(),loginUser.getUserId(),opinion.getApproveAction()));
			//db.executeUpdate("delete from ext_approve_opinion where process_instance_id=?", actInstance.getId());
			//db.executeUpdate("delete from ext_run_instance where instance_id=?", actInstance.getId());
			//db.executeUpdate("delete from ext_approve_opinion_file where approve_process_instance_id=?", actInstance.getId());
			
			updateInstanceStatus(actInstance.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已作废);
			
			try{
				executeGlobalAfterScript(loginUser, ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds);
				executeAfterScript(loginUser,task,opinion,draftNode,nextTaskCandidateUserIds);
				
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			return nextTaskCandidateUserIds;
		}
		
		if(NodeButton.BTN_TYPE_DISAGREE_CONTINUE_GO.equals(opinion.getApproveAction())) {
			Task lastTask = Task.getCloneObject(task);
 
			NextCompleteGeneralHandler nextComplete = new NextCompleteGeneralHandler();
			nextComplete.setTaskServiceImpl(this);
			
			Map<String,Object> argsMap = prepareCompleteVariable(actInstance, variable);
			argsMap.putAll(prepareMeetingVariable(ActUtil.convert(actInstance), nodeUserMap)); // 可能有会签
			
			return nextComplete.execute(loginUser, actInstance, task, argsMap, nodeUserMap, draftNode, opinion, lastTask);
		}
		
		Map<String,Object> argsMap = prepareCompleteVariable(actInstance, variable);
		argsMap.putAll(prepareMeetingVariable(ActUtil.convert(actInstance), nodeUserMap)); // 可能有会签
		
		actTaskService.complete(taskId, argsMap);
		
		String nextTaskCandidateUserIds = getNextTaskCandidateUserIds(actInstance.getBusinessKey(), task.getProcessDefinitionId(), actInstance.getId(), nodeUserMap, draftNode,task);
		executeGlobalAfterScript(loginUser,ActUtil.convert(actInstance), task.getProcessDefinitionId(),  nextTaskCandidateUserIds);
		executeAfterScript(loginUser,task,opinion,draftNode,nextTaskCandidateUserIds);
		
		
		return nextTaskCandidateUserIds;
		
		
		}catch(Exception ex) {
			ex.printStackTrace();
			if(opinion!=null && opinion.getId() !=null) {
				db.delete(opinion);
			}
			throw ex;
		}
	}

	/**
	 * 
	 * @param instance 流程实例
	 * @param variable 客户端显示要修改的变量数据
	 * @return 返回：流程实例变量+用户传入变量 （相同变量以传入变量为最新值)
	 */
	public Map<String,Object> prepareCompleteVariable(org.activiti.engine.runtime.ProcessInstance instance,Map<String,Object> variable) {
		Map<String,Object> instanceMap = instance.getProcessVariables();
		
		for (String e : variable.keySet()) {
			if (!instanceMap.containsKey(e)) {
				instanceMap.put(e, variable.get(e));
			} else {
				if (variable.get(e) != null) {
					instanceMap.put(e, variable.get(e));
				}
			}
		}
		return new HashMap<>(instanceMap);
	}
	
	/**
	 * 如果当前实例有会签，则填充会签变量
	 * @param instance 当前流程实例
	 * @param nodeUserMap 节点设置的审批用户
	 * @return 含审批用户+用户传入变量+会签需要的流程变量
	 */
	public Map<String,Object> prepareMeetingVariable(org.lsqt.act.model.ProcessInstance instance,Map<String, List<ApproveObject>> nodeUserMap) {
		Map<String, Object> meetingVar = new HashMap<>();
		
		List<Node> meetingNodeList = getMeetingNode(instance.getProcessDefinitionId());
		
		for(Node n: meetingNodeList) {
			List<String> assigneeList = new ArrayList<String>(); // 会签分配任务的人员
			
			List<ApproveObject> assignObjList = nodeUserMap.get(n.getTaskKey());
			assigneeList = ApproveObject.toIdList(String.class, assignObjList);
			
			meetingVar.put(n.getTaskKey()+"List", assigneeList);
		}
		
		return meetingVar;
	}
	
	/**
	 * 多级退回时，获取最后一个末归位节点
	 * @param apprOpinionList
	 * @return 
	 */
	ApproveOpinion getUnResetMutilRejectNode(List<ApproveOpinion> apprOpinionList) {
		if (apprOpinionList == null || apprOpinionList.isEmpty()) {
			return null;
		}
		
		ApproveOpinion rs = null;
		
		for (int i=0;i<apprOpinionList.size();i++) {
			ApproveOpinion curr = apprOpinionList.get(i);
			
			if(NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE_FOR_MUTIL_BACK.equals(curr.getApproveAction())
					|| NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(curr.getApproveAction())){
				
				if(!ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE.equals(curr.getRejectReRunCompleteStatus())){
					if ((i + 1) != apprOpinionList.size()) {
						ApproveOpinion next = apprOpinionList.get(i+1);
						if((NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE_FOR_MUTIL_BACK.equals(next.getApproveAction()) || NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(next.getApproveAction()))
								&& ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE.equals(next.getRejectReRunCompleteStatus())){
							return curr;
						}
					}
				}
			}
		}
		
		return rs;
	}
	
	/**
	 * 获取多级退回时，当前要归位的节点（不含最后一个归位结点)
	 * @param apprOpinionList
	 * @return
	 */
	ApproveOpinion getLastMutilRejectNode(List<ApproveOpinion> apprOpinionList) {
		if (apprOpinionList == null || apprOpinionList.isEmpty()) {
			return null;
		}
		
		List<ApproveOpinion> rejects = new ArrayList<>();
		for (int i=0;i<apprOpinionList.size();i++) {
			ApproveOpinion curr = apprOpinionList.get(i);
			if((NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE_FOR_MUTIL_BACK.equals(curr.getApproveAction()) || NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(curr.getApproveAction()))
				&& !ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE.equals(curr.getRejectReRunCompleteStatus())) {
				rejects.add(curr);
			}
		}
		
		if(rejects.size()>0){
			return rejects.get(rejects.size()-1);
		}
		return null;
	}
	
	
	
	/**
	 * 判断是否是连续的多级退回 (不含 最后一个未归位的节点）
	 * @param apprOpinionList 流程审批历史
	 * @return
	 */
	boolean isMutilReject(List<ApproveOpinion> apprOpinionList){
		boolean isLoopMutilReject = false;
		if (apprOpinionList == null || apprOpinionList.isEmpty()) {
			return isLoopMutilReject;
		}
		
		int mutilRejectCnt = 0 ;
		for (int i=0;i<apprOpinionList.size();i++) {
			ApproveOpinion curr = apprOpinionList.get(i);
			ApproveOpinion next = null;
			if((i+1)!=apprOpinionList.size()) {
				next = apprOpinionList.get(i+1);
			}

			if((NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE_FOR_MUTIL_BACK.equals(curr.getApproveAction()) || NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(curr.getApproveAction()))
					&& next!=null && !ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE.equals(curr.getRejectReRunCompleteStatus())) {
				
				mutilRejectCnt +=1;
			}
			
			if(mutilRejectCnt>1){
				
				return true;
			}
		}
		
		return isLoopMutilReject;
	}
	
	/**
	 * 判断当前任务是否是加签任务
	 * @param loginUserId
	 * @param instanceId
	 * @param taskId
	 * @return 如果是加签用户任务返回不为Null
	 */
	RunTaskAssignForwardCc getAssignUserApporve(Long loginUserId,Task task) {
		RunTaskAssignForwardCcQuery query = new RunTaskAssignForwardCcQuery();
		query.setApproveAction(NodeButton.BTN_TYPE_ADD_ASSIGN);
		query.setProcessInstanceId(task.getProcessInstanceId());
		query.setTaskId(task.getId());
		query.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
		query.setAssignForwardCcUserIds(loginUserId.toString());
		List<RunTaskAssignForwardCc>  list = db.queryForList("queryForPage", RunTaskAssignForwardCc.class, query);
		
		if (list == null || list.isEmpty()) {
			return null;
		}
		
		return list.get(list.size()-1); // 使终返回最后一条记录
	}
	
	public boolean isFromAssignedTask(String taskId) {
		Task task = getById(taskId);
		if (task == null) {
			return false;
		}
		ApproveOpinionQuery query = new ApproveOpinionQuery();
		query.setProcessInstanceId(task.getProcessInstanceId());
		List<ApproveOpinion> list = db.queryForList("queryForPage", ApproveOpinion.class, query);
		if (list != null && !list.isEmpty()) {
			ApproveOpinion opinion = list.get(list.size() - 1);
			if (NodeButton.BTN_TYPE_ADD_ASSIGN.equals(opinion.getApproveAction())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 加载登陆用户信息（含主岗、主部门）
	 * @param loginUserId
	 * @param opinion
	 * @return
	 */
	private User prepareLoginUser(Long loginUserId, ApproveOpinion opinion) {
		if(loginUserId == null) {
			throw new RuntimeException("登陆用户不能为空");
		}
		
		User loginUser = db2.getById(User.class, loginUserId);
		if(loginUser == null) {
			throw new RuntimeException("没有找到id号为"+loginUserId+"的登陆用户");
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
				
				opinion.setApproveUserOrgText(loginUser.getUserMainOrg().getName());
			}
		}
		
		if(loginUser.getUserMainPosition() == null) {
			PositionQuery qr = new PositionQuery();
			qr.setUserId(loginUser.getUserId());
			List<Position> tempPostion = db2.queryForList("queryForPage", Position.class, qr);
			if(tempPostion!=null && tempPostion.size()>0) {
				loginUser.setUserMainPosition(tempPostion.get(0));
				
				opinion.setApproveUserPositionText(loginUser.getUserMainPosition().getName());
			}
		}
		return loginUser;
	}

	/**
	 * 补全任务关联的业务主键
	 * @param task
	 * @param actInstance
	 */
	void prepareTaskBusinesskey(Task task,ProcessInstance actInstance) {
		if(task!=null && StringUtil.isBlank(task.getBusinessKey())) {
			task.setBusinessKey(actInstance.getBusinessKey());
		}
	}

	/**
	 * 解析到审批人为空的节点自动跳过(如果有连续多个循环处理)
	 * @param loginUser 登陆用户
	 * @param opnion 登陆用户提交的审批意见
	 * @param lastTask 上一个审批任务
	 * @param draftNode 拟稿节点
	 * @param actInstance 流程实例
	 * @param task 当前任务对象
	 * @param variable 任务对象的流程变量
	 * @param nodeUserMap 流程实例所有节点的审批用户
	 * @return 注意，流程结束之前的那个节点设置为自动跳过，将会返回空(因流程已经结束）
	 */
	Task processAutoJumpForRessolveEmptyApproveUserNode(User loginUser,ApproveOpinion opinion,Task lastTask,Node draftNode ,ProcessInstance actInstance,Task task,Map<String, Object> variable,Map<String, List<ApproveObject>> nodeUserMap) {
		/*
		if (hasApproveUser(task, nodeUserMap)) {
			prepareTaskBusinesskey(task,actInstance);
			return task;
		}
		*/
		while(true) {
			
			if (hasApproveUser(task, nodeUserMap)) {
				prepareTaskBusinesskey(task,actInstance);
				return task;
			}
			
			NodeQuery query = new NodeQuery();
			query.setDefinitionId(task.getProcessDefinitionId());
			query.setTaskKey(task.getTaskDefinitionKey());
			List<Node> list = db.queryForList("queryForPage", Node.class, query);
			if (list == null || list.size() == 0) {
				return task;
			}
			Node currNode = list.get(0);
			if (currNode.getNodeJumpType() != null && Node.NODE_JUMP_TYPE_RESOLVED_USER_EMPTY_AUTO_JUMP.equals(currNode.getNodeJumpType())) {
				
				variable.put(task.getTaskDefinitionKey(), Arrays.asList(Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID));
				actTaskService.complete(task.getId(), variable);
				if(task!=null) {
					//执行全局回调角本
					executeGlobalAfterScript(loginUser, ActUtil.convert(actInstance), task.getProcessDefinitionId(), Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID);

					// 执行按钮回调角本
					executeAfterScriptForAutoJumpNode(loginUser,opinion,lastTask,task,draftNode,"");
				}
				
				
				//lastTask = Task.getCloneObject(task);
				task = getNextNewTask(ActUtil.convert(actInstance));
				if(task==null) return null; //流程已结束
			} else {
				break;
			}
		}
		
		return getNextNewTask(ActUtil.convert(actInstance));
	}
	
	/**
	 * 处理设置审批用户为空的节点自动跳过(如果有连续多个循环处理)
	 * @param loginUser 登陆用户
	 * @param opnion 登陆用户提交的审批意见
	 * @param lastTask 上一个审批任务
	 * @param draftNode 拟稿节点
	 * @param actInstance 流程实例
	 * @param task 当前任务对象
	 * @param variable 任务对象的流程变量
	 * @param nodeUserMap 流程实例所有节点的审批用户
	 * @return 注意，流程结束之前的那个节点设置为自动跳过，将会返回空(因流程已经结束）
	 */
	Task processAutoJumpForEmptyApproveUserNode(User loginUser,ApproveOpinion opinion,Task lastTask,Node draftNode ,ProcessInstance actInstance,Task task,Map<String, Object> variable,Map<String, List<ApproveObject>> nodeUserMap) {
		
		//如果当前节点有设置审批人，直接break
		if (hasApproveUser(task, nodeUserMap)) {
			prepareTaskBusinesskey(task,actInstance);
			return task;
		}
		
		while (true) {
			if(task == null) {
				return null;
			}
			// 查询当前任务对应的节点
			NodeQuery nodeQuery = new NodeQuery();
			nodeQuery.setDefinitionId(task.getProcessDefinitionId());
			nodeQuery.setTaskKey(task.getTaskDefinitionKey());
			List<Node> temp = db.queryForList("queryForPage", Node.class, nodeQuery);
			if (temp == null || temp.isEmpty()) {
				return task;
			}
			Node currNode = temp.get(0);


			// 当前任务节点是否是自动跳过节点（并且没有审批用户）
			if (Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP.equals(currNode.getNodeJumpType()) && !hasApproveUser(task,nodeUserMap)) {
				variable.put(task.getTaskDefinitionKey(), Arrays.asList(Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID));
				actTaskService.complete(task.getId(), variable);
				if(task!=null) {
					//执行全局回调角本
					executeGlobalAfterScript(loginUser, ActUtil.convert(actInstance), task.getProcessDefinitionId(), Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID);

					// 执行按钮回调角本
					executeAfterScriptForAutoJumpNode(loginUser,opinion,lastTask,task,draftNode,"");
				}
				
				
				lastTask = Task.getCloneObject(task);
				task = getNextNewTask(ActUtil.convert(actInstance));
				String nextUserIds = "";
				if(task!=null) {
					List<ApproveObject> nextUser = nodeUserMap.get(task.getTaskDefinitionKey());
					nextUserIds = StringUtil.join(ApproveObject.toIdList(nextUser),",");
					if(StringUtil.isBlank(nextUserIds)) {
						nextUserIds = Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID;
					}
				}
				
				String definitionId= "";
				if(task == null) {
					definitionId = actInstance.getProcessDefinitionId();
				}else {
					definitionId = task.getProcessDefinitionId();
				}
				executeGlobalAfterScript(loginUser, ActUtil.convert(actInstance), definitionId, nextUserIds);//执行全局回调角本
				prepareTaskBusinesskey(task,actInstance);
				executeAfterScript(loginUser, task, opinion, draftNode,nextUserIds);// 执行按钮回调角本
				
				// 如果流程结束，执行最后一个节点的角本
				if (isInstanceEnded(actInstance.getId())) {
					executeGlobalAfterScript(loginUser, ActUtil.convert(actInstance), definitionId, nextUserIds);// 执行全局回调角本
					prepareTaskBusinesskey(lastTask, actInstance);
					executeAfterScript(loginUser, lastTask, opinion, draftNode,nextUserIds);// 执行按钮回调角本
				}
				
				/*
				if(task!=null) {
					//执行全局回调角本
					executeGlobalAfterScript(loginUser, ActUtil.convert(actInstance), task.getProcessDefinitionId(), Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID);

					// 执行按钮回调角本
					executeAfterScriptForAutoJumpNode(loginUser,opinion,lastTask,task,draftNode);
				}
				*/
				
			} else {
				break;

			}
		}
		
		if(StringUtil.isBlank(task.getBusinessKey())){
			task.setBusinessKey(actInstance.getBusinessKey());
		}
		return task;
	}
 
	// ----------------------------------------------------- 辅助方法  ------------------------------------
	/**
	 * 当前节点是否有审批用户
	 * @return true=有审批用户 false=没有审批用户
	 */
	boolean hasApproveUser(Task task,Map<String, List<ApproveObject>> nodeUserMap) {
		List<ApproveObject> appUserObjects = nodeUserMap.get(task.getTaskDefinitionKey());
		
		if (appUserObjects == null || appUserObjects.isEmpty()) {
			return false;
		}
		
		List<String> uids = ApproveObject.toIdList(appUserObjects);
		
		if(uids==null || uids.isEmpty()) {
			return false;
		}
		
		if (uids.size() == 1) {
			if(Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID.equals(uids.get(0))){ //虚拟用户不算业务上的审批用户
				return false;
			}else {
				return true;
			}
		}
	
		for(String id: uids){
			if(StringUtil.isNotBlank(id) && !Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID.equals(id)){
				return true;
			}
		}
	 
		return false;
	}
	
	/**
	 * 更新流程实例状态和业务状态
	 * @param instanceId 
	 * @param endStatus 流程结束状态
	 * @param businessStatus 业务状态
	 * @param businessDesc 业务状态说明
	 */
	void updateInstanceStatus(String instanceId,String businessStatus) {
		RunInstanceQuery query = new RunInstanceQuery();
		query.setInstanceId(instanceId);
		RunInstance model = db.queryForObject("queryForPage", RunInstance.class, query);
		
		if (model != null) {
			boolean isEnded = isInstanceEnded(instanceId);
			if (isEnded) {
				model.setEndStatus(Integer.valueOf(ActUtil.END_STATUS_已结束));
			} else {
				model.setEndStatus(Integer.valueOf(ActUtil.END_STATUS_未结束));
			}
			
			if(StringUtil.isNotBlank(businessStatus)) {
				model.setBusinessStatus(businessStatus);
				model.setBusinessStatusDesc(ActUtil.getBusinessStatusDesc(businessStatus));
			}
			db.update(model, "endStatus","businessStatus","businessStatusDesc");
		}
	}
	
	 /*
	void processRunInstaceStatus(String processInstanceId) {
		
		boolean isEnded = isInstanceEnded(processInstanceId);
		
		String sql = "update ext_run_instance set end_status = ?  where instance_id = ?";
		if (isEnded) {
			db.executeUpdate(sql, ActUtil.CLOSE_STATUS_YES,processInstanceId);
		} else {
			db.executeUpdate(sql, ActUtil.CLOSE_STATUS_NO,processInstanceId);
		}
	}

	void processRunInstaceBusinessStatus(String processInstanceId,String status,String statusDesc) {
		String sql = "update ext_run_instance set business_status = ? ,business_status_desc=? where instance_id = ?";
		if (StringUtil.isNotBlank(processInstanceId,status)) {
			db.executeUpdate(sql, status,statusDesc,processInstanceId);
		}
	}
	*/
	
	/**
	 * 判断流程是否已经结束
	 * @param processInstanceId
	 * @return
	 */
	boolean isInstanceEnded(String processInstanceId) {
		
		org.activiti.engine.RuntimeService runtimeService = ActUtil.getRuntimeService();
		ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
		 
		boolean isEnded = false;
		
		if(processInstance != null) {
			isEnded = processInstance.isEnded();
		}else {
			isEnded = true;
			log.debug(" --- 流程结束,instance="+processInstanceId);
		}
		
		return isEnded;
	}
	
	/**
	 * 获取（会签）产生的多实例任务的审批人集合
	 * @param mutilTaskList (会签）产生的多实例任务
	 * @param nodeUserMap 整个流程节点(瞬时)审批用户
	 * @return
	 */
	String getMutilTaskCandidateUserIds(List<Task> mutilTaskList,Map<String,List<ApproveObject>> nodeUserMap) {
		Collection<String> set = new HashSet<>();
		for (Task t : mutilTaskList) {
			for (String key : nodeUserMap.keySet()) {
				if (t.getTaskDefinitionKey().equals(key)) {
					set.addAll(ApproveObject.toIdList(nodeUserMap.get(key)));
					break;
				}
			}
		}
		return StringUtil.join(set);
	}
	
	/**
	 * 获取下一个节点的审批用户 （注意：并发分支流程的complete不支持此调用!!!!)
	 * @param businessKey 流程定义key
	 * @param definitionId 流程定义Id
	 * @param instanceId 流程实例ID
	 * @param nodeUserMap 整个流程节点(瞬时)审批用户
	 * @param draftNode 如果是拟稿节点, 不为null
	 * @return
	 */
	String getNextTaskCandidateUserIds(String businessKey,String definitionId,String instanceId,Map<String,List<ApproveObject>> nodeUserMap,Node draftNode,Task currNode) {
		Set<String> userIds=new HashSet<>();
		
		org.lsqt.act.model.TaskQuery filter = new org.lsqt.act.model.TaskQuery();
		filter.setProcessDefinitionId(definitionId);
		filter.setProcessInstanceId(instanceId);
		filter.setBusinessKey(businessKey);
		List<Task> taskList = db.queryForList("queryForPageDetail", Task.class, filter);//有可能是“并发”的待办
		
		
		if(taskList!=null && !taskList.isEmpty()) {
			
			for(Task t: taskList) {
				List<ApproveObject> temp = nodeUserMap.get(t.getTaskDefinitionKey());
				if(temp!=null){
					for(ApproveObject obj: temp) {
						userIds.add(obj.getId());
					}
				}
			}
			currNode = taskList.get(0);
		}
		
		if((draftNode!=null && currNode!=null) 
				&& (draftNode.getTaskKey().equals(currNode.getTaskDefinitionKey())) 
				&& !Node.NODE_JUMP_TYPE_AUTO_JUMP.equals(draftNode.getNodeJumpType())) { //如果是（不自动跳过的）拟稿节点,把发起人加进来
			
			// 排除已经走过的拟稿节点
			ApproveOpinionQuery tempQuery = new ApproveOpinionQuery();
			tempQuery.setProcessInstanceId(instanceId);
			tempQuery.setDefinitionId(definitionId);
			tempQuery.setApproveTaskKey(draftNode.getTaskKey());
			List<ApproveOpinion> tempList = db.queryForList("queryForPage", ApproveOpinion.class, tempQuery);
			if(tempList != null && tempList.size()==1) { // 非自动跳过的拟稿节点，在complete方法前面已经加了一个记录
				if(draftNode.getExtProperty()!=null && draftNode.getExtProperty().get(ActUtil.VARIABLES_START_USER_ID)!=null) {
					userIds.add(draftNode.getExtProperty().get(ActUtil.VARIABLES_START_USER_ID).toString());
				}
			}
		}
	
		return StringUtil.join(userIds, ",");
	}
	
	
	/***
	 * 执行后置角本
	 * @param task
	 * @param btnCode
	 * @throws ClientProtocolException
	 * @throws IOException
	 * @throws TemplateException
	 */
	void executeGlobalAfterScript(User loginUser,org.lsqt.act.model.ProcessInstance processInstance,String processDefinitionId,String nextTaskCandidateUserIds) throws RuntimeException {
		log.debug(" ------------------------------- 全局后置角本instance="+processInstance.getProcessInstanceId()+"（回调）--------------------------------------");

		try{
			ReDefinitionQuery query = new ReDefinitionQuery();
			query.setDefinitionId(processDefinitionId);
			
			List<ReDefinition> list = db.queryForList("queryForPage", ReDefinition.class, query);
			if(list!=null && !list.isEmpty()) {
				ReDefinition model = list.get(0);
				if(StringUtil.isNotBlank(model.getAfterScript())) {
					if(NodeButton.SCRIPT_TYPE_URL.equals(model.getAfterScriptType())){
						
						final Map<String, Object> root = new HashMap<>();
						root.put("loginUser", loginUser);
						root.put("processInstance", processInstance);
						//root.put("processDefinion", processDefinion);
						
						root.put("processInstanceId", processInstance.getId());
						root.put("processDefinitionId", processDefinitionId);
						root.put("businessKey", processInstance.getBusinessKey());
						root.put("nextTaskCandidateUserIds", nextTaskCandidateUserIds);
						log.debug(" --- 全局后置角本参数值："+root);
						
						Template tmpl = new Template("redefinition_"+model.getId()+System.currentTimeMillis(), new StringReader(model.getAfterScript()), ActFreemarkUtil.FTL_CONFIGURATION);
						StringWriter stringWriter = new StringWriter();
						BufferedWriter writer = new BufferedWriter(stringWriter);
						tmpl.process(root, writer);
		
						writer.flush();
						writer.close();
		
						String url = stringWriter.toString().trim();
						log.debug(" --- 执行全局后置角本,流程实例="+processInstance.getId()+",url="+url);
						
						/*
						CloseableHttpClient httpclient = HttpClientBuilder.create().build();
						
						HttpPost post = new HttpPost(url);
						HttpResponse response = httpclient.execute(post);

						HttpEntity resEntity = response.getEntity();
						String json = EntityUtils.toString(resEntity);

						EntityUtils.consumeQuietly(resEntity);
						post.releaseConnection();
						httpclient.close();
						*/
						URL urlObj = new URL(url);
						HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

						String json = "";
						connection.setRequestMethod("POST");
						if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
							json = IOUtils.toString(connection.getInputStream(), "utf-8");
							log.debug(" --- 执行全局后置角本成功，返回json:"+json);

						} else {
							log.debug(" --- 执行全局后置角本失败，返回json: "+json);
							throw new RuntimeException("全局回调失败，详情：" + json);
						}
			           
						
					     
					}else if(NodeButton.SCRIPT_TYPE_JAVASCRIPT_CODE.equals(model.getAfterScriptType())){
						
					}else if(NodeButton.SCRIPT_TYPE_JAVA_CODE.equals(model.getAfterScriptType())){
						
					}
				}
			}
		}catch(Exception ex){
			throw new RuntimeException(ex);
		}
	}

	/***
	 * 执行(审批按钮)后置角本
	 * @param task
	 * @param btnCode
	 */
	void executeAfterScriptForAutoJumpNode(User loginUser,ApproveOpinion opinion,Task lastTask,Task currTask,Node draftNode,String nextTaskCandidateUserIds) {
		log.debug(" ------------------------------- 开始执行审批按钮后置角本（自动跳过节点回调）--------------------------------------");
		NodeButtonQuery query = new NodeButtonQuery();
		query.setDefinitionId(lastTask.getProcessDefinitionId());
		query.setTaskKey(lastTask.getTaskDefinitionKey());
		//query.setBtnCode("button_type_"+opinion.getApproveAction());
		
		if (!opinion.getApproveAction().startsWith("button_type_")) {
			query.setBtnCode("button_type_" + opinion.getApproveAction());
		} else {
			query.setBtnCode(opinion.getApproveAction());
		}
		
		try{
			List<NodeButton> list = nodeButtonService.queryForList(query);
			if(list!=null && !list.isEmpty()) {
				NodeButton model = list.get(0);
				if(StringUtil.isNotBlank(model.getAfterScript())) {
					if(NodeButton.SCRIPT_TYPE_URL.equals(model.getAfterScriptType())){
						
						String url = resolveScriptTypeUrl(loginUser, currTask, opinion, model, draftNode,nextTaskCandidateUserIds);
						/*
						final Map<String, Object> root = new HashMap<>();
						root.put("loginUser", loginUser);
						root.put("task", currTask);
						//root.put("applicationContext", ContextUtil.getApplicationContext());
						
						root.put("taskId", currTask.getId());
						root.put("businessKey", currTask.getBusinessKey());
						root.put("processInstanceId",currTask.getProcessInstanceId());
						root.put("processDefinitionId",currTask.getProcessDefinitionId());
						root.put("action", btnCode);
						
						
						Template tmpl = new Template((currTask.getProcessDefinitionId()+currTask.getTaskDefinitionKey()+btnCode), new StringReader(model.getAfterScript()), ActFreemarkUtil.FTL_CONFIGURATION);
						StringWriter stringWriter = new StringWriter();
						BufferedWriter writer = new BufferedWriter(stringWriter);
						tmpl.process(root, writer);
		
						writer.flush();
						writer.close();
						 
						String url = stringWriter.toString().trim();
						*/
						log.debug(" --- 执行后置角本,类型为url: "+url);
						
						/*
						CloseableHttpClient httpclient = HttpClientBuilder.create().build();

						HttpPost post = new HttpPost(url);
						HttpResponse response = httpclient.execute(post);

						HttpEntity resEntity = response.getEntity();
						String json = EntityUtils.toString(resEntity);

						EntityUtils.consumeQuietly(resEntity);
						post.releaseConnection();
						httpclient.close();
						*/
						
						URL urlObj = new URL(url);
						HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

						String json = "";
						connection.setRequestMethod("POST");
						if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
							json = IOUtils.toString(connection.getInputStream(), "utf-8");
						} else {
							throw new RuntimeException("全局回调失败，详情：" + json);
						}
						
						log.debug(" --- 执行后置角本完成，返回结果: "+json);
					     
					}else if(NodeButton.SCRIPT_TYPE_JAVASCRIPT_CODE.equals(model.getAfterScriptType())){
						
					}else if(NodeButton.SCRIPT_TYPE_JAVA_CODE.equals(model.getAfterScriptType())){
						
					}
				}
			}
		}catch(Exception ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	/***
	 * 执行(审批按钮)后置角本
	 * @param task
	 * @param opinion 用户提交过来的审批意见对象
	 * @param draftNode 拟稿结点(可为null)
	 */
	void executeAfterScript(User loginUser,Task task,ApproveOpinion opinion,Node draftNode,String nextTaskCandidateUserIds)  {
		if(task == null){
			return;
		}
		
		NodeButtonQuery query = new NodeButtonQuery();
		query.setDefinitionId(task.getProcessDefinitionId()); 
		query.setTaskKey(task.getTaskDefinitionKey());
		if (!opinion.getApproveAction().startsWith("button_type_")) {
			query.setBtnCode("button_type_" + opinion.getApproveAction());
		} else {
			query.setBtnCode(opinion.getApproveAction());
		}

		
			List<NodeButton> list = nodeButtonService.queryForList(query);
			if(list!=null && !list.isEmpty()) {
				NodeButton model = list.get(0);
				if(StringUtil.isNotBlank(model.getAfterScript())) {
					
					log.debug(" ------------------------------- 按钮后置角本instance="+(task == null ? "null" : task.getProcessInstanceId())+"（回调）--------------------------------------");
					
					if(NodeButton.SCRIPT_TYPE_URL.equals(model.getAfterScriptType())){
						String url = null;
						try{
							url = resolveScriptTypeUrl(loginUser, task, opinion, model, draftNode,nextTaskCandidateUserIds);
						}catch(Exception ex){
							log.debug(" --- 执行按钮后置角本出时，解析url报错："+ExceptionUtil.getStackTrace(ex));
							throw new RuntimeException(ex);
						}
						
						if(task!=null && task.getProcessInstanceId()!=null) {
							log.debug(" --- 执行按钮后置角本instanceId="+task.getProcessInstanceId());
						}
						log.debug(" --- 执行按钮后置角本url="+url);
						
						/*
						CloseableHttpClient httpclient = HttpClientBuilder.create().build();

						HttpPost post = new HttpPost(url);
						HttpResponse response = httpclient.execute(post);

						HttpEntity resEntity = response.getEntity();
						String json = EntityUtils.toString(resEntity);

						EntityUtils.consumeQuietly(resEntity);
						post.releaseConnection();
						httpclient.close();
						*/
						
						try{
							URL urlObj = new URL(url);
							HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();
	
							String json = "";
							connection.setRequestMethod("POST");
							if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
								json = IOUtils.toString(connection.getInputStream(), "utf-8");
							}
							
							log.debug(" --- 执行审批按钮后置角本实例ID="+(task==null ? "null":task.getProcessInstanceId())+"返回结果: "+json);
						}catch(Exception ex) {
							ex.printStackTrace();
							throw new RuntimeException(" --- 执行审批按钮后置角本实例ID="+(task==null ? "null":task.getProcessInstanceId())+"报错");
						}
						
					     
					}else if(NodeButton.SCRIPT_TYPE_JAVASCRIPT_CODE.equals(model.getAfterScriptType())){
						
					}else if(NodeButton.SCRIPT_TYPE_JAVA_CODE.equals(model.getAfterScriptType())){
						
					}
				}
			}
		
	}

	/**
	 * 
	 * @param loginUser 登陆用户
	 * @param task 当前任务
	 * @param opinion 当前任务意见对象
	 * @param nodeButton 审批按钮
	 * @param draftNode 拟稿节点（可为null)
	 * @return
	 * @throws IOException
	 * @throws TemplateException
	 */
	private String resolveScriptTypeUrl(User loginUser, Task task, ApproveOpinion opinion, NodeButton nodeButton,Node draftNode,String nextTaskCandidateUserIds) throws IOException, TemplateException {
		final Map<String, Object> root = new HashMap<>();
		root.put("loginUser", loginUser);
		root.put("task", task);
		//root.put("applicationContext", ContextUtil.getApplicationContext());
		
		root.put("taskId", task.getId());
		root.put("businessKey", task.getBusinessKey());
		root.put("processInstanceId",task.getProcessInstanceId());
		root.put("processDefinitionId",task.getProcessDefinitionId());
		root.put("action", opinion.getApproveAction());
		
		// 如果用户点击"退回到指定结点",选择退回到拟稿节点,内置变量驳回类型：actionRejectType=0 就是驳回到拟稿节点 
		if(NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(opinion.getApproveAction())) {
			if(draftNode!=null && opinion.getRejectToChooseNodeTaskKey().equals(draftNode.getTaskKey())){
				root.put("actionRejectType", Node.TASK_BIZ_TYPE_DRAFTNODE);
			}else {
				root.put("actionRejectType", Node.TASK_BIZ_TYPE_UNDRAFTNODE);
			}
		}
		
		if(StringUtil.isNotBlank(nextTaskCandidateUserIds)) {
			root.put("nextTaskCandidateUserIds", nextTaskCandidateUserIds);
		} else {
			root.put("nextTaskCandidateUserIds", "");
		}
		
		Template tmpl = new Template((task.getProcessDefinitionId()+task.getTaskDefinitionKey()+opinion.getApproveAction()), new StringReader(nodeButton.getAfterScript()), ActFreemarkUtil.FTL_CONFIGURATION);
		StringWriter stringWriter = new StringWriter();
		BufferedWriter writer = new BufferedWriter(stringWriter);
		tmpl.process(root, writer);

		writer.flush();
		writer.close();

		String url = stringWriter.toString().trim();
		return url;
	}
	
	public static void main(String[] args) throws ClientProtocolException, IOException {

		
//		if(true)return ;
		for(int i=0;i<10;i++){
			URL urlObj = new URL("http://localhost/nets-authority/act/definition/page?deployCategory=test_work_flow_test_qingjia");  
            
			// 得到HttpURLConnection对象
			HttpURLConnection connection = (HttpURLConnection) urlObj.openConnection();

			String json = "";
			connection.setRequestMethod("POST");
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				json = IOUtils.toString(connection.getInputStream(), "utf-8");
				System.out.println(json);

			} else {
				throw new RuntimeException("全局回调失败，详情：" + json);
			}
            
            /*
			CloseableHttpClient httpclient = HttpClientBuilder.create().build();
	
			HttpPost post = new HttpPost("http://localhost:8080/nets-authority/act/definition/page?deployCategory=test_work_flow_test_qingjia");
			HttpResponse response = httpclient.execute(post);
	
			HttpEntity resEntity = response.getEntity();
			String json = EntityUtils.toString(resEntity);
			System.out.println(json);
			EntityUtils.consumeQuietly(resEntity);
			post.releaseConnection();
			httpclient.close();*/
		}
	}
	
	/**
	 * 全局流程变量，填充到当间节点变量（就近原则）
	 * @param actInstance
	 * @param currVariable
	
	void globalVariableFill222(Map<String,Object> currVariable,ProcessInstance actInstance) {
		if(actInstance.getProcessVariables()!=null && !actInstance.getProcessVariables().isEmpty()){
			for(String key: actInstance.getProcessVariables().keySet()) {
				if(currVariable.get(key) == null) {
					currVariable.put(key, actInstance.getProcessVariables().get(key));
				}
				if(currVariable.get(key)!=null && StringUtil.isBlank(currVariable.get(key).toString())){
					currVariable.put(key, actInstance.getProcessVariables().get(key));
				}
			}
		}
	} */
	
	/**
	 * 当前任务的流程变量从全局全量里copy进来（如果后台设置过）
	 * @param task
	 * @param variable
	 */
	void nodeVariableCopyIfSetOrNot(Task task,Map<String,Object> variable) {
		NodeQuery query = new NodeQuery();
		query.setDefinitionId(task.getProcessDefinitionId());
		query.setTaskKey(task.getTaskDefinitionKey());
		query.setNodeVariableCopy(Node.NODE_VARIABLE_COPY_FROM_GLOBAL);
		
		List<Node> list = db.queryForList("queryForPage",Node.class,query);
		if(list == null || list.isEmpty()) {
			return ;
		}
		
		Node node = list.get(0);
		if(node.getNodeVariableCopy()!=null && Node.NODE_VARIABLE_COPY_FROM_GLOBAL == node.getNodeVariableCopy()) {
			Map<String,Object> flowVariable = ActUtil.getTaskService().getVariables(task.getId());
			variable.putAll(flowVariable);
		}
	}
	
	/**
	 * 流程任意跳转
	 * @param taskId 待办任务ID
	 * @param targetTaskId 目标任务key
	 */
	public void jump(String taskId,String targetTaskKey,Map<String,Object> variables) {
		org.activiti.engine.task.Task task = actTaskService.createTaskQuery().taskId(taskId).singleResult();

		boolean isMutilNode = false;
		
		NodeQuery query = new NodeQuery();
		query.setDefinitionId(task.getProcessDefinitionId());
		query.setTaskBizType(Node.TASK_BIZ_TYPE_MEETINGNODE);
		List<Node> mutilNode = db.queryForList("queryForPage", Node.class, query);
		for (Node n: mutilNode) {
			if(targetTaskKey.equals(n.getTaskKey())) {
				isMutilNode = true;
				break;
			}
		}
		
		if (isMutilNode) { //跳到会签节点
			synchronized (TaskServiceImpl.class) {
		        ActivityImpl source = getActivity(task.getProcessDefinitionId(),task.getTaskDefinitionKey()); // 源结点
		        ActivityImpl target = getActivity(task.getProcessDefinitionId(), targetTaskKey) ; // 目标结点
		        
		        
		        Map<TransitionImpl,ActivityImpl> mapNode = new LinkedHashMap<>(); // 获取原来的目标节点
		        
		        // 设置源结点的（所有）出口为（会签）目标结点
		        List<PvmTransition> pvmList = source.getOutgoingTransitions();
		        for (PvmTransition p:pvmList) {
		        	TransitionImpl t = (TransitionImpl) p;
		        	mapNode.put(t, t.getDestination());
	
		        	t.setDestination(target);
		        }
		      
		        try{
		        	actTaskService.complete(taskId, variables);
		        }finally {
					for (PvmTransition p : pvmList) {
						TransitionImpl t = (TransitionImpl) p;
						ActivityImpl old = mapNode.get(p);
						t.setDestination(old);
					}
		        }
	        }
	        return ;
		}
		
		// 跳到普通节点
		org.activiti.engine.impl.TaskServiceImpl taskServiceImpl=(org.activiti.engine.impl.TaskServiceImpl)ActUtil.getTaskService();
	 
		taskServiceImpl.getCommandExecutor().execute(new JumpTaskCmd(task.getExecutionId(), targetTaskKey, variables));
		
	}
	
    /** 
     *  根据ActivityId 查询出来想要活动Activity 
     * @param id 
     * @return 
     */  
	@SuppressWarnings("unchecked")
	private ActivityImpl getActivity( String processDefinitionId, String taskKey) {
		RepositoryService repositoryService = ActUtil.getRepositoryService();
		ReadOnlyProcessDefinition deployedProcessDefinition = (ProcessDefinitionEntity) ((RepositoryServiceImpl) repositoryService)
				.getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activities = (List<ActivityImpl>) deployedProcessDefinition.getActivities();
		for (ActivityImpl activityImpl : activities) {
			if (activityImpl.getId().equals(taskKey)) {
				return activityImpl;
			}
		}
		return null;
	}

	
	
	/**
	 * 任意节点跳转(退回、跳任意思节点)
	 * @author mmyuan
	 *
	 */
	public static class JumpTaskCmd implements Command<java.lang.Void> {
		   
		protected String executionId;  
	    protected String activityId;  
	    protected Map<String,Object> variables;
	    
	    public JumpTaskCmd(String executionId, String activityId) {  
	        this.executionId = executionId;  
	        this.activityId = activityId;  
	    }  
	    
	    public JumpTaskCmd(String executionId, String activityId,Map<String,Object> variables) {  
	        this.executionId = executionId;  
	        this.activityId = activityId;  
	        this.variables = variables;
	    }  
	    
		@Override
		public Void execute(CommandContext commandContext) {
	        for (TaskEntity taskEntity : Context.getCommandContext().getTaskEntityManager().findTasksByExecutionId(executionId)) {  
	            Context.getCommandContext().getTaskEntityManager().deleteTask(taskEntity, "jump", false);
	        }
	        ExecutionEntity executionEntity = Context.getCommandContext().getExecutionEntityManager().findExecutionById(executionId);  
	        ProcessDefinitionImpl processDefinition = executionEntity.getProcessDefinition();  
	        ActivityImpl activity = processDefinition.findActivity(activityId);  
	        
	        
	        activity.setVariables(variables);
	        executionEntity.executeActivity(activity);  
	        return null;  
	    }
	}
	
	// ----------------------------------------------------------- 查询 -------------------------------------------------
	
	public Task getById(String taskId) {
		TaskQuery taskQuery = actTaskService.createTaskQuery();
		return convert(taskQuery.taskId(taskId).singleResult());
	}
	
	
	public Page<Task> queryForPage(org.lsqt.act.model.TaskQuery query) {
		Page<Task> page = new Page.PageModel<>();

		TaskQuery taskQuery = actTaskService.createTaskQuery();
		if (StringUtil.isNotBlank(query.getUserId())) {
			taskQuery = taskQuery.taskCandidateOrAssigned(query.getUserId());
		}

		long total = taskQuery.count();
		page.setTotal(total);

		final long pageCount = Double.valueOf(Math.ceil(total * 1.000 / query.getPageSize())).longValue();

		// 封装分页对象
		page.setTotal(total);
		page.setPageCount(pageCount);
		page.setPageIndex(query.getPageIndex());
		page.setPageSize(query.getPageSize());
		page.setHasNext(query.getPageIndex() + 1 < pageCount);
		page.setHasPrevious(query.getPageIndex() > 0 && query.getPageIndex() < pageCount - 1);

		List<org.activiti.engine.task.Task> data = taskQuery.listPage(query.getPageIndex()*query.getPageSize(), query.getPageSize());
		page.setData(convert(data));

		return page;
	}
	
	
	public List<Task> queryForList(org.lsqt.act.model.TaskQuery query) { 
		/*
		// =============== 已经签收的任务 ===============
		TaskQuery todoTaskQuery = actTaskService.createTaskQuery().taskAssignee(userId).active().includeProcessVariables().orderByTaskCreateTime().desc();
		todoTaskQuery = convert(query, todoTaskQuery);
		
		List<org.activiti.engine.task.Task> todoList = todoTaskQuery.list();
		result.addAll(convert(todoList));
		
		
		// =============== 等待签收的任务 ===============
		TaskQuery toClaimQuery = actTaskService.createTaskQuery().taskCandidateUser(userId).includeProcessVariables().active().orderByTaskCreateTime().desc();
		toClaimQuery = convert(query, todoTaskQuery);
		*/
		
		TaskQuery taskQuery = actTaskService.createTaskQuery();
		if (StringUtil.isNotBlank(query.getUserId())) {
			taskQuery = actTaskService.createTaskQuery().taskCandidateOrAssigned(query.getUserId());
		}

		List<org.activiti.engine.task.Task> data = taskQuery.list();
	
		return convert(data);
	}
	
	/**
	 * 处理加签转发的“我的待办”查询参数
	 * @param query
	 */
	private void fillAssignForwardTaskParam(org.lsqt.act.model.TaskQuery query) {
		if(StringUtil.isNotBlank(query.getUserId())) { // 查单个用户的我的待办
			org.lsqt.act.model.TaskQuery subQuery = new org.lsqt.act.model.TaskQuery();
			subQuery.setUserId(query.getUserId());
			List<String> taskList1 = db.queryForList(Task.class.getName(), "getTaskIdsIncludeAssignForward", String.class, subQuery); // 含加签、转发 用户的待办
			
			List<String> taskList2 = db.queryForList(Task.class.getName(), "getTaskIdsEscapeAssignForward", String.class, subQuery); // 去除加签\转发的"主人"待办
			
			if (taskList1!=null && !taskList1.isEmpty()) {
				query.setTaskIdsIncludeAssignForward(StringUtil.join(taskList1));
			}
			
			if (taskList2!=null && !taskList2.isEmpty()) {
				query.setTaskIdsEscapeAssignForward(StringUtil.join(taskList2));
			}
		}
		
		
		if(StringUtil.isNotBlank(query.getUserIds())) { // 查多个用户的我的待办
			org.lsqt.act.model.TaskQuery subQuery = new org.lsqt.act.model.TaskQuery();
			subQuery.setUserIds(query.getUserIds());
			List<String> taskList1 = db.queryForList(Task.class.getName(), "getTaskIdsIncludeAssignForwardBatchQuery", String.class, subQuery); // 含加签、转发 用户的待办
			
			List<String> taskList2 = db.queryForList(Task.class.getName(), "getTaskIdsEscapeAssignForwardBatchQuery", String.class, subQuery); // 去除加签\转发的"主人"待办
			
			if (taskList1!=null && !taskList1.isEmpty()) {
				query.setTaskIdsIncludeAssignForward(StringUtil.join(taskList1));
			}
			
			if (taskList2!=null && !taskList2.isEmpty()) {
				query.setTaskIdsEscapeAssignForward(StringUtil.join(taskList2));
			}
		}
	}
	
	public Page<Task> queryForPageDetail(org.lsqt.act.model.TaskQuery query) {
		fillAssignForwardTaskParam(query);
		
		return db.queryForPage("queryForPageDetail", query.getPageIndex(), query.getPageSize(), Task.class, query);
	}
	

	
	public List<Task> queryForListDetail(org.lsqt.act.model.TaskQuery query) {
		fillAssignForwardTaskParam(query);
		
		return db.queryForList("queryForPageDetail", Task.class, query);
	}
	// --------------------------------------------------- 辅助方法 ----------------------------------------------------
	/**
	 * 填充表单查询条件到DB
	 * @param query 
	 * @param actQuery
	 * @return
	 */
	TaskQuery convert(org.lsqt.act.model.TaskQuery query, TaskQuery actQuery) {
		// 设置查询条件
		if (StringUtil.isNotBlank(query.getProcessDefinitionKey())) {
			actQuery = actQuery.processDefinitionKey(query.getProcessDefinitionKey());
		}
		try{
			if (query.getCreateTimeBegin() != null) {
				actQuery = actQuery.taskCreatedAfter(new SimpleDateFormat("yyyy-MM-dd").parse(query.getCreateTimeBegin()));
			}
			if (query.getCreateTimeEnd() != null) {
				actQuery = actQuery.taskCreatedBefore(new SimpleDateFormat("yyyy-MM-dd").parse(query.getCreateTimeEnd()));
			}
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		if (StringUtil.isNotBlank(query.getAssignee())) {
			actQuery = actQuery.taskAssigneeLike(query.getAssignee());
		}
		if (StringUtil.isNotBlank(query.getDescription())) {
			actQuery = actQuery.taskDescriptionLike(query.getDescription());
		}
		
		return actQuery;
	}
	
	Task convert(org.activiti.engine.task.Task task) {
		if(task == null) return null;
		
		Task model = new Task();
		model.setAssignee(task.getAssignee());
		model.setCategory(task.getCategory());
		model.setCreateTime(task.getCreateTime());
		model.setDescription(task.getDescription());
		model.setDueDate(task.getDueDate());
		model.setExecutionId(task.getExecutionId());
		model.setFormKey(task.getFormKey());
		model.setId(task.getId());
		model.setName(task.getName());
		model.setOwner(task.getOwner());
		model.setParentTaskId(task.getParentTaskId());
		model.setPriority(task.getPriority());
		
		if(StringUtil.isNotBlank(task.getProcessDefinitionId())) {
			ProcessDefinition pd = actRepositoryService.createProcessDefinitionQuery().processDefinitionId(task.getProcessDefinitionId()).singleResult();
			if(pd!=null) {
				model.setProcessDefinitionKey(pd.getKey());
				model.setProcessDefinitionName(pd.getName());
			}
		}
		
		model.setProcessDefinitionId(task.getProcessDefinitionId());
		
		
		model.setProcessInstanceId(task.getProcessInstanceId());
		model.setTaskDefinitionKey(task.getTaskDefinitionKey());
		model.setTenantId(task.getTenantId());

		return model;
	}
	
	List<Task> convert(List<org.activiti.engine.task.Task> taskList) {
		List<Task> result = new ArrayList<>();
		if (taskList == null || taskList.size() == 0) {
			return result;
		}

		for(org.activiti.engine.task.Task task : taskList) {
			if(StringUtil.isNotBlank(task.getId())) {
				result.add(convert(task));
			}
		}
		return result;
	}

	
	// -------------------------------  优化我的待办，优化到“极致” -------------------------------------
	/**
	 * 获取待办 （含发起人、流程标题等信息），已优化到"极致"！
	 * @param query
	 * @return
	 */
	public Page<Task> queryMyToDoTaskPage(org.lsqt.act.model.TaskQuery query) {
		fillQuery(query);
		
		if (StringUtil.isNotBlank(query.getTaskIdsIncludeAssignForward())) {
			return db.queryForPage("queryMyToDoTask", query.getPageIndex(), query.getPageSize(), Task.class, query);
		}
		return new Page.PageModel<Task>();
	}
	
	private void fillQuery(org.lsqt.act.model.TaskQuery query) {
		Set<String> taskIdSet = new HashSet<>();

		// 1.获取直接assing给我的任务id集
		List<String> assignedList = db.queryForList(Task.class.getName(), "getAssignedToUser", String.class, query);

		// 2.获取"我参与的用户"任务
		List<String> referList = db.queryForList(Task.class.getName(), "getReferTaskAboutUser", String.class, query);

		// 3.获取加签、转发给我的任务ID集
		List<String> forwardList = db.queryForList(Task.class.getName(), "getTaskIdsIncludeAssignForward", String.class, query);

		taskIdSet.addAll(assignedList);
		taskIdSet.addAll(referList);
		taskIdSet.addAll(forwardList);
		 
		
		
		if (!taskIdSet.isEmpty()) {
			query.setTaskIdsIncludeAssignForward(StringUtil.join(taskIdSet));

			// 4.获取要去除的加签、转发主人的任务ID集
			List<String>  ccOwnerList = db.queryForList(Task.class.getName(), "getTaskIdsEscapeAssignForward", String.class, query);
		 
			if (!ccOwnerList.isEmpty()) {
				query.setTaskIdsEscapeAssignForward(StringUtil.join(ccOwnerList));
			}
		}
	}
	
	/**
	 * 获取待办 （含发起人、流程标题等信息），已优化到"极致"！
	 * 
	 * @param query
	 * @return
	 */
	public List<Task> queryMyToDoTaskList(org.lsqt.act.model.TaskQuery query) {
		if (StringUtil.isNotBlank(query.getTaskIdsIncludeAssignForward())) {
			return db.queryForList("queryMyToDoTask", Task.class, query);
		}
		return new ArrayList<Task>();
	}
}
