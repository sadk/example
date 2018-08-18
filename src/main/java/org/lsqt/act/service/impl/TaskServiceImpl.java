package org.lsqt.act.service.impl;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
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
import org.lsqt.act.model.AssignOwnerTaskIds;
import org.lsqt.act.model.AssignOwnerTaskIdsQuery;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeButtonQuery;
import org.lsqt.act.model.NodeForm;
import org.lsqt.act.model.NodeFormQuery;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.model.PrintInfo;
import org.lsqt.act.model.PrintInfoQuery;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.ReDefinitionQuery;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunInstanceQuery;
import org.lsqt.act.model.RunTask;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.RunTaskAssignForwardCcQuery;
import org.lsqt.act.model.Task;
import org.lsqt.act.service.NodeButtonService;
import org.lsqt.act.service.NodeUserService;
import org.lsqt.act.service.TaskService;
import org.lsqt.act.service.support.AbortCommand;
import org.lsqt.act.service.support.AbortHandler;
import org.lsqt.act.service.support.AgreeCommand;
import org.lsqt.act.service.support.AgreeHandler;
import org.lsqt.act.service.support.AssignCommand;
import org.lsqt.act.service.support.AssignHandler;
import org.lsqt.act.service.support.CompleteCommand;
import org.lsqt.act.service.support.DisagreeCommand;
import org.lsqt.act.service.support.DisagreeHandler;
import org.lsqt.act.service.support.ForwardCcCommand;
import org.lsqt.act.service.support.ForwardCcHandler;
import org.lsqt.act.service.support.ReBackCommand;
import org.lsqt.act.service.support.ReBackHandler;
import org.lsqt.act.service.support.RejectToChoosedCommand;
import org.lsqt.act.service.support.RejectToChoosedHandler;
import org.lsqt.act.service.support.RejectUpCommand;
import org.lsqt.act.service.support.RejectUpHandler;
import org.lsqt.act.service.support.TaskQueryUtil;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.ExceptionUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.file.PropertiesUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.controller.CodeUtil;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.model.User;
import org.lsqt.syswin.uum.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.support.TaskUtils;

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
	@Deprecated
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
	
	
	private ActRunningContext createRunningContext(Long loginUserId,String taskId,Map<String, Object> variable) {
		final ActRunningContext context = new ActRunningContext(db,db2);

		User loginUser = TaskQueryUtil.loadLoginUser(db2,loginUserId);

		Task inputTask = getById(taskId);

		if(inputTask == null ) return context;
		
		ProcessInstance currActProcessInstance = actRuntimeService.createProcessInstanceQuery()
				.includeProcessVariables().processInstanceId(inputTask.getProcessInstanceId()).singleResult();
		inputTask.setBusinessKey(currActProcessInstance.getBusinessKey());
		
		if (currActProcessInstance.getProcessVariables() != null) {
			context.getCompleteVariable().putAll(currActProcessInstance.getProcessVariables());
		}

		// 获取扩展的流程实例
		RunInstanceQuery instQuery = new RunInstanceQuery();
		instQuery.setInstanceId(currActProcessInstance.getProcessInstanceId());
		RunInstance currProcessInstance = db.queryForObject("queryForPage", RunInstance.class, instQuery);
		
		// 获取拟稿节点
		Node draftNode = TaskQueryUtil.getDraftNode(db,inputTask.getProcessDefinitionId());

		// 瞬时获取审批用户
		Map<String, Object> deptMap = new HashMap<>();
		Object createDeptId = variable.get(ActUtil.VARIABLES_CREATE_DEPT_ID);
		if (createDeptId != null && StringUtil.isNotBlank(createDeptId.toString())) { // bugFix,单据撤回修改填制人部门,要重新解析审批人
			deptMap.put(ActUtil.VARIABLES_CREATE_DEPT_ID, createDeptId);
			currProcessInstance.setCreateDeptId(createDeptId.toString());
		} else {
			deptMap.put(ActUtil.VARIABLES_CREATE_DEPT_ID, currProcessInstance.getCreateDeptId());
		}

		Object title = variable.get(ActUtil.VARIABLES_TITLE);
		if (title != null && StringUtil.isNotBlank(title.toString())) {
			currProcessInstance.setTitle(title.toString());
		}
		
		String companyNamePrint = TaskQueryUtil.resolveCompany4Print(variable);
		if(StringUtil.isNotBlank(companyNamePrint)) {
			currProcessInstance.setCompanyNamePrint(companyNamePrint);
		}
		db.update(currProcessInstance, "title","createDeptId","companyNamePrint");
		
		Map<String, List<ApproveObject>> nodeUserMap = nodeUserService.getNodeUsers(
				Long.valueOf(currProcessInstance.getStartUserId()), inputTask.getProcessDefinitionId(), deptMap);
		
		nodeUserMap.put(draftNode.getTaskKey(), Arrays.asList(new ApproveObject(currProcessInstance.getStartUserId()))); // 流程发起人加进流程变量
		context.getCompleteVariable().putAll(TaskQueryUtil.toApproveUserMap(nodeUserMap)); //设置审批用户！！！
		
		
		// 上一步审批用户
		List<ApproveObject> approveObjectList = nodeUserMap.get(inputTask.getTaskDefinitionKey());
		if (ArrayUtil.isNotBlank(approveObjectList) && approveObjectList.size() == 1) {
			context.setPrevTaskCandidateUserIds(approveObjectList.get(0).getId());
		}

		context.setLoginUser(loginUser);
		context.setInputTask(inputTask);
		context.setCurrActProcessInstance(currActProcessInstance);
		context.setCurrProcessInstance(currProcessInstance);
		context.setDraftNode(draftNode);
		context.setRuningCurrTask(inputTask);
		context.setNodeUserMap(nodeUserMap);
		 
		
		context.getForm().setBusinessKey(currProcessInstance.getBusinessKey());
		context.getForm().setCreateDeptId(currProcessInstance.getCreateDeptId());
		context.getForm().setBusinessFlowNo(currProcessInstance.getBusinessFlowNo());
		
		// 填充会签用户变量
		TaskQueryUtil.fillMeetingVariableIfExists(context);
		
		// 扩展的流程定义
		ReDefinition currReDefinion = TaskQueryUtil.loadReDefinion(context);
		context.setCurrReDefinion(currReDefinion);
		
		// 整条流程的节点数
		final int nodeCount = TaskQueryUtil.getNodeCount(context);
		context.setNodeCount(nodeCount);
		
		return context;
	}

	/**
	 * 保存审批意见(含附件)
	 * @param context
	 */
	@SuppressWarnings("unchecked")
	private void saveOpinionIfExistAttatchment(ActRunningContext context) {
		final ApproveOpinion opinion = context.getApproveOpinion();
		final Task inputTask = context.getInputTask();
		final User loginUser = context.getLoginUser();
		final Node draftNode = context.getDraftNode();
		final Map<String,Object> completeVariable = context.getCompleteVariable();
		
		
		// 1.审批时间的任务信息字段
		opinion.setApproveTaskId(inputTask.getId());
		opinion.setApproveTaskKey(inputTask.getTaskDefinitionKey());
		opinion.setApproveTaskName(inputTask.getName());
		
		// 节点别名填充
		NodeQuery q = new NodeQuery();
		q.setDefinitionId(inputTask.getProcessDefinitionId());
		q.setTaskKey(inputTask.getTaskDefinitionKey());
		Node node = db.queryForObject("queryForPage", Node.class, q);
		if (node != null && StringUtil.isNotBlank(node.getTaskNameAlias())) {
			opinion.setApproveTaskNameAlias(node.getTaskNameAlias());
		}
		
		
		// 2.整个流程的定义信息字段
		opinion.setDefinitionId(inputTask.getProcessDefinitionId());
		opinion.setDefinitionName(inputTask.getProcessDefinitionName());
		opinion.setDefinitionKey(inputTask.getProcessDefinitionKey());
		
		
		// 3.流程实例信息字段
		opinion.setProcessInstanceId(inputTask.getProcessInstanceId());
		opinion.setBusinessKey(inputTask.getBusinessKey());
		

		// 4.审批用户信息
		opinion.setApproveUserId(loginUser.getUserId().toString());
		if(loginUser.getUserMainOrg()!=null) {
			opinion.setApproveUserOrgText(loginUser.getUserMainOrg().getName());
		}
		if(loginUser.getUserMainPosition()!=null) {
			opinion.setApproveUserPositionText(loginUser.getUserMainPosition().getName());
		}
		
		List<String> userIds = (List<String>)completeVariable.get(inputTask.getTaskDefinitionKey());
		if(userIds!=null) {
			opinion.setApproveTaskCandidateUserIds(StringUtil.join(userIds, ","));
		}
		// 拟稿节点，把流程发起人添加进来
		if(draftNode!=null && draftNode.getTaskKey().equals(inputTask.getTaskDefinitionKey())) {
			String startUserId = completeVariable.get(ActUtil.VARIABLES_START_USER_ID) +"";
			if(StringUtil.isBlank(opinion.getApproveTaskCandidateUserIds())) {
				opinion.setApproveTaskCandidateUserIds(startUserId);
			}else {
				opinion.setApproveTaskCandidateUserIds(opinion.getApproveTaskCandidateUserIds() + ","+startUserId);
			}
		}
		 
		opinion.setApproveResult(NodeButton.getApproveActionShortDesc(opinion.getApproveAction()));
		opinion.setApproveUserName(loginUser.getUserName());
		
		if(StringUtil.isBlank(opinion.getApproveOpinion())) {
			if(context.getInputVariable().get(ActUtil.VARIABLES_APPROVE_OPINION)!=null) {
				opinion.setApproveOpinion(completeVariable.get(ActUtil.VARIABLES_APPROVE_OPINION).toString());
			}
		}
		db.save(opinion);
		
		
		// 补全流程附件表其它字段
		if(opinion.getAttachmentList()!=null) {
			for(ApproveOpinionFile f : opinion.getAttachmentList()) {
				f.setApproveProcessInstanceId(inputTask.getProcessInstanceId());
				f.setApproveTaskId(inputTask.getId());
				f.setApproveTaskKey(inputTask.getProcessDefinitionKey());
				f.setBusinessKey(inputTask.getBusinessKey());
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
	}
	
	
	private void checkBussiness(ActRunningContext context) {
		final Node draftNode = context.getDraftNode();
		final Task inputTask = context.getInputTask();
		final Task runingCurrTask = context.getRuningCurrTask();
		final Map<String,Object> inputVariable = context.getInputVariable();
		final ReDefinition reDefinition= context.getCurrReDefinion();
		final  Map<String, List<ApproveObject>>  nodeUserMap = context.getNodeUserMap();
		
		if (inputTask == null) {
			throw new RuntimeException("任务已结束或不存在");
		}
		
		if (inputVariable == null) {
			throw new NullPointerException("流程变量入参不能为空指针");
		}
		
		if (draftNode == null) {
			throw new RuntimeException("拟稿节点不能为空");
		}
		
		if (reDefinition == null) {
			throw new NullPointerException("流程扩展定义不能为空");
		}
		
		// 非拟稿节点，检查节点审批人不能为空
		if (!draftNode.getTaskKey().equals(runingCurrTask.getTaskDefinitionKey())) {

			final List<Node> autoJumpNodeList = TaskQueryUtil.getAutoJumpNodeList(context);

			boolean isAutoJumpNode = false; // 当前任务节点是否是自动跳过的节点
			for (Node nd : autoJumpNodeList) {
				if (runingCurrTask.getTaskDefinitionKey().equals(nd.getTaskKey())) {
					isAutoJumpNode = true;
					break;
				}
			}

			if (!isAutoJumpNode) {
				List<ApproveObject> approveUsers = nodeUserMap.get(runingCurrTask.getTaskDefinitionKey());
				if (ArrayUtil.isBlank(approveUsers)) {
					String[] splitList = inputTask.getProcessDefinitionId().split(":");
					throw new RuntimeException("请联系管理员设置\"" + runingCurrTask.getName() + "("
							+ runingCurrTask.getTaskDefinitionKey() + ")\"审批节点的审批用户,流程版本号: " + splitList[1]+"; 或者检查审批用户矩阵是否启用到\"" + runingCurrTask.getName() + "("
							+ runingCurrTask.getTaskDefinitionKey() + ")\"节点");
				}
			}
		}
	}
	
	public ActRunningContext complete(Long loginUserId,String taskId, Map<String, Object> variable,ApproveOpinion opinion) {
		//variable.put("createDeptId", "1210");
		final ActRunningContext context = createRunningContext(loginUserId,taskId,variable);
		context.setApproveOpinion(opinion);
		context.setInputVariable(variable);
		context.getForm().setTitle(TaskQueryUtil.resolveBusinessTitle(variable)); // 用于待办标题显示更新
		
		TaskQueryUtil.putPrintNodeVariableIfExists(context); // 设置用印变量,从配置表里取用印用户
		
		checkBussiness(context);
		
		TaskQueryUtil.fillVirtualUser4AutoJumpNode(context); // 为自动跳过节点设置虚拟用户
		
		context.getCompleteVariable().putAll(variable);// 设置业务流程变量！！！！
		
		saveOpinionIfExistAttatchment(context);
		
		
		
		List<org.lsqt.act.service.support.Command<Void>> cmdList = new ArrayList<>();
		AgreeHandler agreeHandler = new AgreeHandler();
		DisagreeHandler disagreeHandler = new DisagreeHandler();
		RejectToChoosedHandler rejectToChoosedHandler = new RejectToChoosedHandler();
		ForwardCcHandler forwardCcHandler = new ForwardCcHandler();
		AssignHandler assignHandler = new AssignHandler();
		RejectUpHandler rejectUpHandler = new RejectUpHandler();
		AbortHandler abortHandler = new AbortHandler() ;
		ReBackHandler reBackHandler = new ReBackHandler();
		
		org.lsqt.act.service.support.Command<Void> agreeCmd = new AgreeCommand(agreeHandler);
		org.lsqt.act.service.support.Command<Void> disagreeCmd = new DisagreeCommand(disagreeHandler);
		org.lsqt.act.service.support.Command<Void> rejectToChoosedCmd = new RejectToChoosedCommand(rejectToChoosedHandler);
		org.lsqt.act.service.support.Command<Void> assignCmd = new AssignCommand(assignHandler);
		org.lsqt.act.service.support.Command<Void> forwardCcCmd = new ForwardCcCommand(forwardCcHandler);
		
		org.lsqt.act.service.support.Command<Void> rejectUpCmd = new RejectUpCommand(rejectUpHandler);
		org.lsqt.act.service.support.Command<Void> abortCmd = new AbortCommand(abortHandler);
		org.lsqt.act.service.support.Command<Void> reBackCmd = new ReBackCommand(reBackHandler);
		
		cmdList.add(agreeCmd);   // 同意
		cmdList.add(disagreeCmd); // 不同意
		cmdList.add(rejectToChoosedCmd); // 驳回到选择节点
		cmdList.add(assignCmd); // 加签 
		cmdList.add(rejectUpCmd); // 驳回到上一步
		cmdList.add(abortCmd); // 作废
		cmdList.add(reBackCmd); // 撤回
		cmdList.add(forwardCcCmd); // 转发抄送
		
		org.lsqt.act.service.support.Command<Void> macroCmd = new CompleteCommand(cmdList);
		try {
			macroCmd.execute(context);
		} catch (Exception ex) {
			ex.printStackTrace();
			macroCmd.executeCancel(context);
			throw new RuntimeException(ex.getMessage(),ex);
		}
		
		deleteInputedRunTask(context);
		saveRunTask(context);
		
		return context;
	}

	/**
	 * 删除待办
	 * @param runContext
	 */
	private void deleteInputedRunTask(ActRunningContext runContext) {
		if (runContext.getInputTask() != null && StringUtil.isNotBlank(runContext.getInputTask().getId())) {
			String sql = String.format("delete from %s where task_id=?", db.getFullTable(RunTask.class));
			db.executeUpdate(sql, runContext.getInputTask().getId());
		}
	}
	
	/**
	 * 保存待办（暂不支持会签！）
	 * @param runContext
	 */
	private void saveRunTask(ActRunningContext runContext) {
		List<RunTask> todoTaskList = new ArrayList<>();
		runContext.setDataHook(todoTaskList);
		
		String nextApproveUser = runContext.getNextTaskCandidateUserIds();
		if(StringUtil.isNotBlank(nextApproveUser)) {
			List<String> nextUserIdList = StringUtil.split(nextApproveUser, ",");
			
			final RunInstance instance = runContext.getCurrProcessInstance();
			
			List<Task> currMutilTaskList = TaskQueryUtil.getCurrentMutilTaskList(db, instance.getInstanceId());
			if (currMutilTaskList != null && currMutilTaskList.size() > 1) {
				log.error("暂时不支持会签");
				return ;
			}
			
			if (ArrayUtil.isBlank(currMutilTaskList)) {
				return ;
			}
			
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
					db.saveOrUpdate(m);
					
					m.setTaskLink(authorityWeb + "/api/task_url_service/converted_redirect?runTaskId=" + m.getId());
					db.update(m, "taskLink");
				}
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
		//dataText = CodeUtil.encode(dataText);
		log.debug("整个参数包长度:"+dataText.length());
		
		//runTask.setTaskLink();
		runTask.setExtData(dataText);
		log.debug("整个中转链接的长度、值："+runTask.getExtData().length()+"、"+runTask.getExtData());
		
		return true;
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
	 * 补全任务关联的业务主键
	 * @param task
	 * @param actInstance
	 */
	void prepareTaskBusinesskey(Task task,ProcessInstance actInstance) {
		if(task!=null && StringUtil.isBlank(task.getBusinessKey())) {
			task.setBusinessKey(actInstance.getBusinessKey());
		}
	}
  
	// ----------------------------------------------------- 辅助方法  ------------------------------------


	
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
			boolean isEnded = TaskQueryUtil.isInstanceEnded(instanceId);
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
		String a="pro_service_contract:3:1303661";
		System.out.println(a.split(":")[1]);
		
		//if(true)return ;
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
		return convert(getActTaskById(taskId));
	}
	
	public org.activiti.engine.task.Task  getActTaskById(String taskId) {
		TaskQuery taskQuery = actTaskService.createTaskQuery();
		return taskQuery.taskId(taskId).singleResult();
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
	private Page<Task> queryMyToDoTaskPage4User(Collection<Task> data) {
		if(ArrayUtil.isBlank(data)) {
			return new Page.PageModel<Task>();
		}
		return null;
	}
	
	/**
	 * 获取待办 （含发起人、流程标题等信息），已优化到"极致"！
	 * @param query
	 * @return
	 */
	public Page<Task> queryMyToDoTaskPage(org.lsqt.act.model.TaskQuery query) {
		fillQuery(query);
		
		if (StringUtil.isNotBlank(query.getTaskIdsIncludeAssignForward())) {
			Page<Task> page = db.queryForPage("queryMyToDoTask", query.getPageIndex(), query.getPageSize(), Task.class, query);
			
			/*
			if (ArrayUtil.isNotBlank(page.getData()) && StringUtil.isNotBlank(query.getUserIds())) {
					List<Task> data =new ArrayList<>(page.getData());
					
					List<String> userIds = StringUtil.split(query.getUserIds(), ",");
					if (userIds.size() ==1) { // 传入一个用户，表示是获取我的待办
						org.lsqt.act.model.TaskQuery rq = new org.lsqt.act.model.TaskQuery();
						List<String> taskIdListIn = Task.toTaskIdList(data);
						List<String> taskIdListNotIn = new ArrayList<>();
						
						rq.setTaskIdListIn(taskIdListIn);
						rq.setTaskIdListNotIn(taskIdListNotIn);
						
						//判断当前任务ID是否在加签主人任务ID里面
						Set<Long> processInstanceIdSet = new HashSet<>();
						for(Task t: data) {
							processInstanceIdSet.add(Long.valueOf(t.getProcessInstanceId()));
						}
						
						AssignOwnerTaskIdsQuery tquery = new AssignOwnerTaskIdsQuery();
						tquery.setAssignOwnerId(Long.valueOf(userIds.get(0)));
						tquery.setProcessInstanceIdList(new ArrayList<>(processInstanceIdSet));
						List<AssignOwnerTaskIds> aotList = db.queryForList("queryForPage", AssignOwnerTaskIds.class, tquery);
						if(ArrayUtil.isNotBlank(aotList)) {
							for(Task d: data) {
								boolean isExists = false;
								for(AssignOwnerTaskIds m: aotList) {
									if(d.getProcessInstanceId().equals(m.getProcessInstanceId().toString()) 
											&& d.getTaskDefinitionKey().equals(m.getTaskKey())) {
										
										org.lsqt.act.model.TaskQuery referQuery = new org.lsqt.act.model.TaskQuery();
										referQuery.setUserId(userIds.get(0));
										List<String> referList = db.queryForList(Task.class.getName(), "getReferTaskAboutUser", String.class, referQuery);
										
										m.setTaskIds(StringUtil.join(referList));
										
										if(StringUtil.isNotBlank(m.getTaskIds())) {
											List<String> taskIdList = StringUtil.split(m.getTaskIds() , ",");
											if(taskIdList.contains(d.getId())) {
												isExists = true;
												break;
											}
										}
									}
								}
								if(isExists) {
									taskIdListNotIn.add(d.getId());
								}
							}
						}
						
						return db.queryForPage("queryMyToDoTask", query.getPageIndex(), query.getPageSize(), Task.class,rq);
					}
			}*/
			return page;
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
			
			/* 5.BugFix: 可审批用户A、B，其中A加签给C和D，那么A、B将不能看到待办，只有C、D加签提审完了以后才能看到待办
			if (StringUtil.isNotBlank(query.getUserIds())) {
				List<String> uids = StringUtil.split(query.getUserIds(), ",");
				if (ArrayUtil.isNotBlank(uids)) {
					for (String u : uids) {
						org.lsqt.act.model.TaskQuery tempQuery = new org.lsqt.act.model.TaskQuery();
						tempQuery.setUserId(u);
						List<String> taskIds = db.queryForList(Task.class.getName(), "getTaskIdsEscapeAssignOwner",String.class, tempQuery);
						if (ArrayUtil.isNotBlank(taskIds)) {
							ccOwnerList.addAll(taskIds);
						}
					}
				}
			}*/
			
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
	
	/**
	 * 加载是否是历史任务状态字段
	 * @param taskData
	
	public void loadIsHistoryTask(Collection<Task> taskData) {
		if (ArrayUtil.isBlank(taskData)) {
			return;
		}
		
		List<String> taskIdList = Task.toTaskIdList(new ArrayList<>(taskData));
		org.lsqt.act.model.TaskQuery query = new org.lsqt.act.model.TaskQuery();
		query.setTaskIdListIn(taskIdList);
		List<Task> hisTask = db.queryForList("getHistoryTask", Task.class, query);
		if (ArrayUtil.isNotBlank(hisTask)) {
			for (Task t : taskData) {
				for (Task h : hisTask) {
					if (t.getId().equals(h.getId())) {
						t.setIsHistoryTask(true);
						break;
					}
				}
			}
		}
	}
	 */
	
	/**
	 * 我已审批，替换taskKey为最新的待办任务taskKey
	 * @param taskData
	 */
	public void replaceTaskKey(Collection<Task> taskData) {
		if (ArrayUtil.isBlank(taskData)) {
			return;
		}
		Set<String> processInstanceIdSet = new HashSet<>();
		for (Task t: taskData) {
			processInstanceIdSet.add(t.getProcessInstanceId());
		}
		
		Map<String,String> taskMap = new HashMap<String,String>(); // key=流程实例，value=最新的待办任务
		for(String instanceId :processInstanceIdSet) {
			List<Task> currTaskList = TaskQueryUtil.getCurrentMutilTaskList(db, instanceId);
			if(ArrayUtil.isNotBlank(currTaskList)) {
				taskMap.put(instanceId, currTaskList.get(0).getTaskDefinitionKey());
			}
		}
		
		if(!taskMap.isEmpty()) {
			for(Task t: taskData) {
				String taskKey = taskMap.get(t.getProcessInstanceId());
				if(StringUtil.isNotBlank(taskKey)) {
					t.setTaskDefinitionKey(taskKey);
				}
			}
		}
	}
	
	/**
	 * 加载节点审批用户、加签用户、用印用户
	 * @param page
	 */
	public void loadApproveUser(Collection<Task> taskData) {
		if(ArrayUtil.isBlank(taskData)) {
			return;
		}
		
		List<String> instanceIds =new ArrayList<>();
		List<String> definitionIds = new ArrayList<>();
		for (Task t: taskData) {
			if (StringUtil.isNotBlank(t.getProcessInstanceId())) {
				instanceIds.add(t.getProcessInstanceId());
			}
			if(StringUtil.isNotBlank(t.getProcessDefinitionId())) {
				definitionIds.add(t.getProcessDefinitionId());
			}
		}
		
		//System.out.println("积累的流程实例：--------- "+instanceIds);
		
		if(ArrayUtil.isNotBlank(instanceIds)) {
			
			//优先显示加签的用户、其次是打印节点用户
			RunTaskAssignForwardCcQuery aq = new RunTaskAssignForwardCcQuery();
			aq.setProcessInstanceIdList(instanceIds);
			aq.setApproveAction(NodeButton.BTN_TYPE_ADD_ASSIGN); //加签
			aq.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
			List<RunTaskAssignForwardCc> assginList = db.queryForList("queryForPage", RunTaskAssignForwardCc.class, aq);
			
			
			
			NodeQuery filter = new NodeQuery();
			filter.setDefinitionIdList(definitionIds);
			filter.setPrintNodes(StringUtil.join(Arrays.asList(Node.PRINT_NODE_YES_档案管理员,Node.PRINT_NODE_YES_现保管员,Node.PRINT_NODE_YES_用印,Node.PRINT_NODE_YES_用印归档)));
			List<Node> printNodeList = db.queryForList("queryForPage", Node.class, filter);

			
			
			
			/**/
			RunInstanceQuery riq = new RunInstanceQuery();
			riq.setInstanceIdList(instanceIds);
			riq.setIsActive(true);
			List<RunInstance> data = db.queryForList("queryForPageRunningDetail", RunInstance.class, riq);// 10242561、10245001（审批中）
			//List<RunInstance> data = db.queryForList("queryForPage", RunInstance.class, riq);
			
			
			Map<String /*流程实例Id*/,RunInstance /*对应的流程实例对象*/> instanceMapData = new HashMap<>(); //待办任务的流程实例
			for(RunInstance e: data) {
				if(!ActUtil.BUSINESS_STATUS_审批中.equals(e.getBusinessStatus())) {
					continue;
				}
				
				Map<String, Object> map = new HashMap<>();
				if (StringUtil.isNotBlank(e.getCreateDeptId())) {
					map.put(ActUtil.VARIABLES_CREATE_DEPT_ID, Long.valueOf(e.getCreateDeptId()));
				} else {
					log.error("流程实例ID=" + e.getInstanceId() + "的流程找不到填制人部门");
				}
				map.put(ActUtil.VARIABLES_START_USER_ID, e.getStartUserId());

				List<ApproveObject> approveUsers = null;
				try {
					Long startUserId = null;
					if (StringUtil.isNotBlank(e.getStartUserId())) {
						startUserId = Long.valueOf(e.getStartUserId());
					}
					approveUsers = nodeUserService.getNodeUsers(startUserId, e.getProcessDefinitionId(), e.getTaskKey(),map);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				StringBuilder approveUserText = new StringBuilder();
				StringBuilder approveUserIds = new StringBuilder();
				if (approveUsers != null) {
					for (ApproveObject t : approveUsers) {
						User user = db2.getById(User.class, t.getId());
						if (user != null) {
							approveUserText.append(t.getName() + "(" + user.getLoginNo() + "/" + user.getUserId() + ")");
							approveUserIds.append(user.getUserId()+",");
						}
					}
					e.setApproveUserText(approveUserText.toString());
					e.setApproveUserIds(approveUserIds.toString());
				}
				
				
				// 拟稿节点审批人就是发起人自己
				NodeQuery nq =new NodeQuery();
				nq.setTaskBizType(Node.TASK_BIZ_TYPE_DRAFTNODE);
				nq.setDefinitionId(e.getProcessDefinitionId());
				Node draftNode = db.queryForObject("queryForPage", Node.class, nq);
				if(draftNode!=null && e.getTaskKey() !=null && e.getTaskKey().equals(draftNode.getTaskKey())) {
					User user = db2.getById(User.class, e.getStartUserId());
					if (user!=null) {
						e.setApproveUserText(e.getStartUserName()+"("+user.getLoginNo()+"/"+user.getUserId()+")");
						e.setApproveUserIds(user.getUserId().toString());
					}
				}
				
				instanceMapData.put(e.getInstanceId(), e);
			}
			
			
			for (Task t : taskData) {
				RunInstance ri = instanceMapData.get(t.getProcessInstanceId());
				if (ri == null) {
					continue;
				}
				
				//System.out.println(ri.getTitle()+" ---------------------------------是否是审批中：" + !ActUtil.BUSINESS_STATUS_审批中.equals(ri.getBusinessStatus()));
				if (!ActUtil.BUSINESS_STATUS_审批中.equals(ri.getBusinessStatus())) {
					continue;
				}

				t.setCandidateUserNames(instanceMapData.get(t.getProcessInstanceId()).getApproveUserText()); // 默认的审批用户
				t.setCandidateUserIds(instanceMapData.get(t.getProcessInstanceId()).getApproveUserIds()); // 默认的审批用户ID

				// 显示用印用户
				if (ArrayUtil.isNotBlank(printNodeList)) {
					for (Node pt : printNodeList) {

						if ((t != null && pt != null) && t.getProcessDefinitionId().equals(pt.getDefinitionId()) && pt.getTaskKey().equals(t.getTaskDefinitionKey())) {

							if (StringUtil.isNotBlank(ri.getCompanyNamePrint())) {
								PrintInfoQuery qr = new PrintInfoQuery();
								qr.setName(ri.getCompanyNamePrint());
								PrintInfo info = db.queryForObject("queryForPage", PrintInfo.class, qr);
								if (info != null) {
									if (pt.getPrintNode() != null) {
										if (Node.PRINT_NODE_YES_用印 == pt.getPrintNode()) {
											User user = db2.getById(User.class, info.getPrintManUserId());
											if (user != null) {
												t.setCandidateUserIds(user.getUserId().toString());
												t.setCandidateUserNames(user.getUserName() + "(" + user.getLoginNo() + "/" + user.getUserId() + ")");
											}
											break;
										}
										if (Node.PRINT_NODE_YES_用印归档 == pt.getPrintNode()) {
											User user = db2.getById(User.class, info.getPrintArchiveUserId());
											if (user != null) {
												t.setCandidateUserIds(user.getUserId().toString());
												t.setCandidateUserNames(user.getUserName() + "(" + user.getLoginNo() + "/" + user.getUserId() + ")");
											}
											break;
										}
										if (Node.PRINT_NODE_YES_现保管员 == pt.getPrintNode()) {
											User user = db2.getById(User.class, info.getProtectManUserId());
											if (user != null) {
												t.setCandidateUserIds(user.getUserId().toString());
												t.setCandidateUserNames(user.getUserName() + "(" + user.getLoginNo() + "/" + user.getUserId() + ")");
											}
											break;
										}
										if (Node.PRINT_NODE_YES_档案管理员 == pt.getPrintNode()) {
											User user = db2.getById(User.class, info.getDocAdminUserId());
											if (user != null) {
												t.setCandidateUserIds(user.getUserId().toString());
												t.setCandidateUserNames(user.getUserName() + "(" + user.getLoginNo() + "/" + user.getUserId() + ")");
											}
											break;
										}
									}
								}
							}
						}
					}
				}

				// 优先显示加签的用户
				if (ArrayUtil.isNotBlank(assginList)) {
					List<String> appUserIds = new ArrayList<>();
					List<String> appUserNames = new ArrayList<>();
					for (RunTaskAssignForwardCc e : assginList) {
						
						if (ri.getInstanceId().equals(e.getProcessInstanceId()) && e.getTaskKey().equals(t.getTaskDefinitionKey())) {
							appUserIds.add(e.getAssignForwardCcUserIds());
							appUserNames.add(e.getAssignForwardCcUserIds());
						}
					}
					if (ArrayUtil.isNotBlank(appUserIds)) {
						StringBuilder sb = new StringBuilder();
						StringBuilder sbUserIds = new StringBuilder();
						for (int i = 0; i < appUserIds.size(); i++) {
							User user = db2.getById(User.class, appUserIds.get(i));
							if (user != null) {
								sb.append(user.getUserName() + "(" + user.getLoginNo() + "/" + user.getUserId() + ")");
								sbUserIds.append(user.getUserId());
								if (i != appUserIds.size() - 1) {
									sb.append(",");
									sbUserIds.append(",");
								}
							}
						}
						t.setCandidateUserNames(sb.toString());
						t.setCandidateUserIds(sbUserIds.toString());
					}
				}
			}
		}
	}
}
