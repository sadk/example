package org.lsqt.act.service.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.impl.RepositoryServiceImpl;
import org.activiti.engine.impl.cmd.AddIdentityLinkForProcessInstanceCmd;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.ReadOnlyProcessDefinition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.Definition;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.model.PrintInfo;
import org.lsqt.act.model.PrintInfoQuery;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.ReDefinitionQuery;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunInstanceQuery;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.RunTaskAssignForwardCcQuery;
import org.lsqt.act.model.Task;
import org.lsqt.act.service.impl.TaskServiceImpl.JumpTaskCmd;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.Org;
import org.lsqt.syswin.uum.model.OrgQuery;
import org.lsqt.syswin.uum.model.Position;
import org.lsqt.syswin.uum.model.PositionQuery;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class TaskQueryUtil {
	private static final Logger  log = LoggerFactory.getLogger(TaskQueryUtil.class);
	
	/**
	 * 判断流程是否已经结束
	 * @param processInstanceId
	 * @return
	 */
	public static boolean isInstanceEnded(String processInstanceId) {
		
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
	 * 获取拟稿节点
	 * @param db
	 * @param processDefinitionId
	 * @return
	 */
	public static Node getDraftNode(Db db,String processDefinitionId) {
		NodeQuery query = new NodeQuery();
		query.setTaskBizType(Node.TASK_BIZ_TYPE_DRAFTNODE);
		query.setDefinitionId(processDefinitionId);
		Node draftNode = db.queryForObject("queryForPage", Node.class, query);
		return draftNode;
	}
	
	
	/**
	 * 收集（会签）产生的多实例任务的审批人集合
	 * @param mutilTaskList (会签）产生的多实例任务
	 * @param nodeUserMap 整个流程节点(瞬时)审批用户
	 * @return 返回审批用户IDs(以逗与分割)
	 */
	public static String collectMutilTaskCandidateUserIds(List<Task> mutilTaskList,Map<String,List<ApproveObject>> nodeUserMap) {
		Collection<String> set = new LinkedHashSet<>();
		for (Task t : mutilTaskList) {
			for (String key : nodeUserMap.keySet()) {
				if (t.getTaskDefinitionKey().equals(key)) {
					set.addAll(ApproveObject.toIdList(nodeUserMap.get(key)));
					break;
				}
			}
		}
		
		List<String> rs = new ArrayList<>(set);
		Collections.sort(rs); // 必须排序用于比较下一个节点的审批人
		return StringUtil.join(rs);
	}
	
	/**
	 * 获取当前实例并发的任务（可能是会签节点产生）
	 * @param db 流程平台DB
	 * @param processInstanceId 流程实例ID
	 * @return 非并发的任务List长度为一
	 */
	public static List<Task> getCurrentMutilTaskList(Db db,String processInstanceId) {
		org.lsqt.act.model.TaskQuery filter = new org.lsqt.act.model.TaskQuery();
		filter.setProcessInstanceId(processInstanceId);
		return db.queryForList("querySimple", Task.class, filter);
	}
	
	/**
	 * 获取下一步审批用户（支持单一任务和并发[会签]任务）
	 * @param context 流程实例对象不能为空
	 * @return 返回审批用户IDs(以逗与分割)
	 */
	public static String getNextTaskCandidateUserIds(ActRunningContext context) {
		
		final String businessKey = context.getCurrActProcessInstance().getBusinessKey();
		final String definitionId = context.getCurrActProcessInstance().getProcessDefinitionId();
		final String instanceId = context.getCurrActProcessInstance().getProcessInstanceId();
		final Map<String,List<ApproveObject>> nodeUserMap = context.getNodeUserMap() ;
		final Node draftNode = context.getDraftNode();
		final Db db = context.getDb();
		
		Set<String> userIds=new HashSet<>();
		
		org.lsqt.act.model.TaskQuery filter = new org.lsqt.act.model.TaskQuery();
		filter.setProcessDefinitionId(definitionId);
		filter.setProcessInstanceId(instanceId);
		filter.setBusinessKey(businessKey);
		List<Task> taskList = db.queryForList("queryForPageDetail", Task.class, filter);//有可能是“并发”的待办
		
		 
		if(taskList!=null && !taskList.isEmpty()) {
			if(taskList.size() == 1) { // 非并发任务
				Task currNode = taskList.get(0);
				if (!currNode.getTaskDefinitionKey().equals(draftNode.getTaskKey())) {
					List<ApproveObject> approveUser = nodeUserMap.get(currNode.getTaskDefinitionKey());
					userIds.addAll(ApproveObject.toIdList(approveUser));
				}
			} else { // 并发任务
				for(Task t: taskList) {
					List<ApproveObject> temp = nodeUserMap.get(t.getTaskDefinitionKey());
					if(temp!=null){
						for(ApproveObject obj: temp) {
							userIds.add(obj.getId());
						}
					}
				}
			}
		}
		
		List<String> rs = new ArrayList<>(userIds);
		Collections.sort(rs); // 必须排序，用于比较下一个节点审批人是否相同
		return StringUtil.join(rs, ",");
	}
	
	/**
	 * 流程任意跳转
	 * @param taskId 待办任务ID
	 * @param targetTaskId 目标任务key
	 */
	public static void jump(Db db,String taskId,String targetTaskKey,Map<String,Object> variables) {
		final org.activiti.engine.TaskService actTaskService = ActUtil.getTaskService();
		
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
			synchronized (TaskQueryUtil.class) {
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
		        	actTaskService.complete(taskId, variables,true);
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
	private static ActivityImpl getActivity( String processDefinitionId, String taskKey) {
		RepositoryService repositoryService = ActUtil.getRepositoryService();
		ReadOnlyProcessDefinition deployedProcessDefinition = (ProcessDefinitionEntity) 
				((RepositoryServiceImpl) repositoryService).getDeployedProcessDefinition(processDefinitionId);
		List<ActivityImpl> activities = (List<ActivityImpl>) deployedProcessDefinition.getActivities();
		for (ActivityImpl activityImpl : activities) {
			if (activityImpl.getId().equals(taskKey)) {
				return activityImpl;
			}
		}
		return null;
	}
	
 
	
	/**
	 * 判断当前任务是否是加签任务
	 * @param loginUserId
	 * @param instanceId
	 * @param taskId
	 * @return 如果是加签任务返回非空
	 */
	public static RunTaskAssignForwardCc getAssignUserApporve(Db db,Long loginUserId,Task task) {
		RunTaskAssignForwardCcQuery query = new RunTaskAssignForwardCcQuery();
		query.setApproveAction(NodeButton.BTN_TYPE_ADD_ASSIGN);
		query.setProcessInstanceId(task.getProcessInstanceId());
		query.setTaskKey(task.getTaskDefinitionKey());
		query.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
		query.setAssignForwardCcUserIds(loginUserId.toString());
		List<RunTaskAssignForwardCc>  list = db.queryForList("queryForPage", RunTaskAssignForwardCc.class, query);
		
		if (list == null || list.isEmpty()) {
			return null;
		}
		
		return list.get(list.size()-1); // 使终返回最后一条记录
	}

	/**
	 * 判断是否是连续的多级退回 (不含 最后一个未归位的节点）
	 * @param apprOpinionList 流程审批历史
	 * @return
	 */
	public static boolean isMutilReject(List<ApproveOpinion> approveTraceList){
		boolean isMutilReject = false;
		if (ArrayUtil.isBlank(approveTraceList)) {
			return false;
		}
		
		int  sequenceStartIndex = -1; // 连续驳回起始索引

		for (int i = approveTraceList.size() - 1; i > 0; i--) {
			ApproveOpinion curr = approveTraceList.get(i);
			ApproveOpinion prv = null;
			if (i - 1 >= 0) {
				prv = approveTraceList.get(i - 1);
			}

			if (prv != null && (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(curr.getApproveAction())
					&& NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(prv.getApproveAction()))) {
				sequenceStartIndex = i;
				
				for (int t = sequenceStartIndex; t > 0; t--) {
					if (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(approveTraceList.get(t).getApproveAction())) {
						if (StringUtil.isBlank(approveTraceList.get(t).getRejectReRunCompleteStatus())
								|| !ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE
										.equals(approveTraceList.get(t).getRejectReRunCompleteStatus())) {
							return true;
						}
					}
				}
				
				break;
			}
		}
		return isMutilReject;
	}
	
	/**
	 * <pre>
	 * 加签全部提审，并且主人也同意。
	 * fix bug(SZSY-290)：流程有驳回到选择节点， 然后再加签给一个用户B，B提审后，流程意见状态位没有置为已完成 -->
	 * </pre>
	 * 
	 * @param approveTraceList
	 * @return
	 */
	public static int getAssignFullCompletedBesideRejectChooseNode(List<ApproveOpinion> approveTraceList) {
		if (ArrayUtil.isBlank(approveTraceList)) {
			return -1;
		}

		int idx = -1;
		for (int i = approveTraceList.size() - 1; i >= 0; i--) {
			ApproveOpinion ap = approveTraceList.get(i);
			if (NodeButton.BTN_TYPE_ADD_ASSIGN_AGREE.equals(ap.getApproveAction())) {
				idx = i;
				break;
			}
		}

		// 加签 提审后的主人也同意
		if (idx != -1) {
			boolean isOwnerAgreed = false;
			if (idx + 1 <= approveTraceList.size() - 1) {
				ApproveOpinion agree = approveTraceList.get(idx+1);
				if (NodeButton.BTN_TYPE_AGREE.equals(agree.getApproveAction())) {
					isOwnerAgreed = true;
				}
			}
			
			if (isOwnerAgreed) { // 如果加签主人也同意了，往上找是否有驳回到选择节点的，
				
				for (int i = approveTraceList.size() - 1; i >= 0; i--) {
					ApproveOpinion ap = approveTraceList.get(i);
					if (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(ap.getApproveAction()) 
							&& !ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE.equals(ap.getRejectReRunCompleteStatus())) {
						return i;
					}
				}
			}
		}
		
		return -1;
	}

	/**
	 * 单级或多级退回时，获取最后一个末归位节点
	 * @param apprOpinionList
	 * @return 
	 */
	public static ApproveOpinion getUnResetMutilRejectNode(List<ApproveOpinion> approveTraceList) {
		if (ArrayUtil.isBlank(approveTraceList)) {
			return null;
		}
		 
		int idx = getAssignFullCompletedBesideRejectChooseNode(approveTraceList);
		if (idx!=-1) {
			return approveTraceList.get(idx);
		}
		
		
		
		
		int  sequenceStartIndex = -1; // 连续驳回起始索引

		for (int i = approveTraceList.size() - 1; i > 0; i--) {
			ApproveOpinion curr = approveTraceList.get(i);
			ApproveOpinion prv = null;
			if (i - 1 >= 0) {
				prv = approveTraceList.get(i - 1);
			}

			if (prv != null && (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(curr.getApproveAction())
					&& NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(prv.getApproveAction()))) {
				sequenceStartIndex = i;
				
				for (int t = sequenceStartIndex; t > 0; t--) {
					if (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(approveTraceList.get(t).getApproveAction())) {
						if (StringUtil.isBlank(approveTraceList.get(t).getRejectReRunCompleteStatus())
								|| !ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE
										.equals(approveTraceList.get(t).getRejectReRunCompleteStatus())) {
							return approveTraceList.get(t);
						}
					}
				}
				
				break;
			}
		}
		
		return null;
	}
	
	/**
	 * 获取多级退回时，当前要归位的节点（不含最后一个归位结点)
	 * @param apprOpinionList
	 * @return
	 */
	@Deprecated
	public static ApproveOpinion getLastMutilRejectNode(List<ApproveOpinion> apprOpinionList) {
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
	 * 更新流程实例状态和业务状态
	 * @param instanceId 
	 * @param endStatus 流程结束状态
	 * @param businessStatus 业务状态
	 * @param businessDesc 业务状态说明
	 */
	public static void updateInstanceStatus(Db db,String instanceId,String businessStatus) {
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
	
	public static void doAutoJumpIfExist(ActRunningContext context) {
		int nodeCount = context.getNodeCount();
		int cnt = 0 ;
		do {
			doAutoJump4ResovledSettedEmptyAndForcedJumpIfExist(context);
			doAutoJump4NeighborIfExists(context);

			if (!hasAutoJump(context)) {
				break;
			}
			
			cnt ++ ;
			log.debug("正在执行自动跳过，第"+cnt+"次");
			if(cnt>nodeCount) { // 跳过死循环
				log.error("流程发起自动跳过死循环");
				break;
			}
		} while (true);
	}
	
	/**
	 * 判断当前流转中的实例是否有自动跳过
	 * @param context
	 * @return
	 */
	public static boolean hasAutoJump(ActRunningContext context) {
		final User loginUser = context.getLoginUser();
		final ProcessInstance actInstance = context.getCurrActProcessInstance();
		final Db db = context.getDb();
		final Map<String, List<ApproveObject>> nodeUserMap = context.getNodeUserMap();

		if (isInstanceEnded(actInstance.getProcessInstanceId())) {
			context.setNextTaskCandidateUserIds(StringUtil.EMPTY);
			return false;
		}

		List<Task> mutilTaskList = getCurrentMutilTaskList(db, actInstance.getProcessInstanceId());// 当前流程的任务可能是（会签）多实例
		if (ArrayUtil.isBlank(mutilTaskList)) {
			return false;
		}

		List<Node> autoJumpNodeList = getAutoJumpNodeList(context);
		if (ArrayUtil.isBlank(autoJumpNodeList)) {
			return false;
		}
		
		if (mutilTaskList.size() == 1) {
			Task runningTask = mutilTaskList.get(0);
			context.setRuningCurrTask(runningTask);

			// 判断当前节点是否设置解析为空并自动跳过、设置为空并自动跳过、强制跳过、
			for (Node n: autoJumpNodeList) {
				if (n.getTaskKey().equals(runningTask.getTaskDefinitionKey())) {
					boolean isSetEmptyJump = Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP.equals(n.getNodeJumpType()) && !hasApproveUser(runningTask, nodeUserMap) ; // 当审批人设置为空时自动跳
					boolean isResolveEmptyJump = Node.NODE_JUMP_TYPE_RESOLVED_USER_EMPTY_AUTO_JUMP.equals(n.getNodeJumpType()) && !hasApproveUser(runningTask, nodeUserMap);//解析为空自动跳过
					boolean isForceJump = Node.NODE_JUMP_TYPE_FORCE_JUMP.equals(n.getNodeJumpType()); // 强制跳过的节点
					
					if(isSetEmptyJump || isResolveEmptyJump || isForceJump) {
						return true;
					}
				}
			}
			
			// 相邻节点是相同用户审批也自动跳过
			List<String> runningTaskApproveUsers = ApproveObject.toIdList(nodeUserMap.get(runningTask.getTaskDefinitionKey()));
			if (ArrayUtil.isNotBlank(runningTaskApproveUsers)) {
				if (runningTaskApproveUsers.size() == 1
						&& loginUser.getUserId().toString().equals(runningTaskApproveUsers.get(0))) {
					return true;
				}
			}
		} else { // 会签！！
			
		}
		
		return false;
	}
	
	/**
	 * 处理自动跳过（支持会签）: 
	 * 1.审批节点没有设置用户自动跳过
	 * 2.解析审批用户为空自动跳过
	 * 3.强制跳过
	 * @param context
	 */
	public static void doAutoJump4ResovledSettedEmptyAndForcedJumpIfExist(ActRunningContext context) {
		final ProcessInstance actInstance = context.getCurrActProcessInstance();
		final Db db = context.getDb();
		final Map<String, List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final TaskService actTaskService = ActUtil.getTaskService();
		final DefaultScriptExecutor defaultScriptExecutor = new DefaultScriptExecutor();
		final Task inputTask = context.getInputTask();
		final int maxCount = context.getNodeCount(); // 防止死循环
		
		// 如果流程已结束则返回
		boolean isInstanceEnded = TaskQueryUtil.isInstanceEnded(actInstance.getProcessInstanceId());
		if (isInstanceEnded) {
			context.setNextTaskCandidateUserIds(StringUtil.EMPTY);
			return;
		}
		
		List<Node> autoJumpNodeList = getAutoJumpNodeList(context);
		
		int cnt=0;
		while (true) {
			cnt++;
			if (cnt > maxCount) {
				log.debug("自动跳过死循环，业务单据号:" + context.getForm().getBusinessFlowNo()+",流程实例ID："+context.getForm().getProcessInstanceId());
				break;
			}
			
			if (isInstanceEnded(actInstance.getProcessInstanceId())) {
				context.setNextTaskCandidateUserIds(StringUtil.EMPTY);
				break ;
			}

			List<Task> mutilTaskList = getCurrentMutilTaskList(db, actInstance.getProcessInstanceId());// 当前流程的任务可能是（会签）多实例
			if (ArrayUtil.isBlank(mutilTaskList)) {
				break;
			}

			if (ArrayUtil.isBlank(autoJumpNodeList)) {
				break;
			}
			
			if (mutilTaskList.size() == 1) {
				Task runningTask = mutilTaskList.get(0);
				context.setRuningCurrTask(runningTask);
				
				boolean isSettedResolveAutoJump = false; // 是否设置解析自动跳过
				
				// 判断当前节点是否设置解析为空并自动跳过、设置为空并自动跳过、强制跳过
				for (Node n: autoJumpNodeList) {
					if (n.getTaskKey().equals(runningTask.getTaskDefinitionKey())) {
						boolean isSetEmptyJump = Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP.equals(n.getNodeJumpType()) && !hasApproveUser(runningTask, nodeUserMap) ; // 当审批人设置为空时自动跳
						boolean isResolveEmptyJump = Node.NODE_JUMP_TYPE_RESOLVED_USER_EMPTY_AUTO_JUMP.equals(n.getNodeJumpType()) && !hasApproveUser(runningTask, nodeUserMap);//解析为空自动跳过
						boolean isForceJump = Node.NODE_JUMP_TYPE_FORCE_JUMP.equals(n.getNodeJumpType()); // 强制跳过的节点
						
						if(isSetEmptyJump || isResolveEmptyJump || isForceJump) {
							
							context.setNextTaskCandidateUserIds(TaskQueryUtil.getNextTaskCandidateUserIds(context));
							defaultScriptExecutor.executeGlobalBeforeScript(context); //执行全局前置脚本
							defaultScriptExecutor.executeNodeBeforeScript(context); //执行节点前置脚本
							defaultScriptExecutor.executeButtonBeforeScript(context); // 执行按钮前置脚本
							
							actTaskService.complete(runningTask.getId(), context.getCompleteVariable());
							
							//执行后置脚本
							context.setNextTaskCandidateUserIds(TaskQueryUtil.getNextTaskCandidateUserIds(context));
							defaultScriptExecutor.executeGlobalAfterScript(context); //执行全局后置脚本
							defaultScriptExecutor.executeNodeAfterScript(context); // 执行节点后置脚本
							defaultScriptExecutor.executeButtonAfterScript(context); // 执行按钮后置脚本
							
							
							isSettedResolveAutoJump = true ; // 假定下个节点有自动跳过需求
							break;
						}
					}
				}
				
				if (!isSettedResolveAutoJump) {
					break;
				}
			} else {
				boolean isSetAutoJump = false;

				if (ArrayUtil.isBlank(autoJumpNodeList)) {
					break;
				}
				
				
				String candidateUserIds = collectMutilTaskCandidateUserIds(mutilTaskList, nodeUserMap);
				/*if (StringUtil.isNotBlank(candidateUserIds)) { // 如果有审批人将不执行自动跳
					break;
				}*/
				
				Task meetingNode = mutilTaskList.get(0);
				for (Node e : autoJumpNodeList) {
					if (e.getTaskKey().equals(meetingNode.getTaskDefinitionKey())) { //如果会签节点设置了自动跳过
						
						boolean isSetEmptyJump = Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP.equals(e.getNodeJumpType()) && StringUtil.isNotBlank(candidateUserIds); // 当审批人设置为空时自动跳
						boolean isResolveEmptyJump = Node.NODE_JUMP_TYPE_RESOLVED_USER_EMPTY_AUTO_JUMP.equals(e.getNodeJumpType()) && StringUtil.isNotBlank(candidateUserIds);//解析为空自动跳过
						boolean isForceJump = Node.NODE_JUMP_TYPE_FORCE_JUMP.equals(e.getNodeJumpType()); // 强制跳过的节点
						
						if(isSetEmptyJump || isResolveEmptyJump || isForceJump) {
							isSetAutoJump = true;
						}
						break;
					}
				}
				if (isSetAutoJump) {
					
					defaultScriptExecutor.executeGlobalBeforeScript(context);
					defaultScriptExecutor.executeNodeBeforeScript(context); 
					defaultScriptExecutor.executeButtonBeforeScript(context);
					
					for (Task e : mutilTaskList) {
						ActUtil.getTaskService().complete(e.getId(), context.getCompleteVariable());
					}
					
					defaultScriptExecutor.executeGlobalAfterScript(context);
					defaultScriptExecutor.executeNodeAfterScript(context);
					defaultScriptExecutor.executeButtonAfterScript(context);
				}
			}
		}

		//doAutoJump4NeighborIfExists(context);
	}
	
	
	/**
	 * 处理相邻节点是相同审批人自动跳过
	 * @param context
	 */
	private static void doAutoJump4NeighborIfExists(ActRunningContext context) {
		final ProcessInstance actInstance = context.getCurrActProcessInstance();
		final Db db = context.getDb();
		final Map<String,List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final Task inputTask = context.getInputTask();
		final TaskService actTaskService = ActUtil.getTaskService();
		final int maxCount = context.getNodeCount();
		final DefaultScriptExecutor defaultScriptExecutor = new DefaultScriptExecutor();
		
		// 如果开启 “相邻节点是相同用户就自动跳过”
		Definition extDefinition = db.getById(Definition.class, context.getCurrActProcessInstance().getProcessDefinitionId());
		if(extDefinition.getEnableNeighborJump() == null 
				|| Definition.ENABLE_NEIGHBOR_JUMP_DISABLE.equals(extDefinition.getEnableNeighborJump())) {
			return ;
		}
		
		if(StringUtil.isBlank(context.getPrevTaskCandidateUserIds())) {
			return ;
		}
		
		List<String> prevUserList = StringUtil.split(context.getPrevTaskCandidateUserIds(), ",");
		if(ArrayUtil.isBlank(prevUserList) || prevUserList.size()>1) {
			return ;
		}
		
		int cnt = 0;
		while (true) {
			cnt++;
			if (cnt > maxCount) {
				log.error("自动跳过死循环，业务单据号:" + context.getForm().getBusinessFlowNo());
				break;
			}
			
			if (isInstanceEnded(actInstance.getProcessInstanceId())) {
				return;
			}

			List<Task> mutilTaskList = getCurrentMutilTaskList(db, actInstance.getProcessInstanceId());// 当前流程的任务可能是（会签）多实例
			if (ArrayUtil.isBlank(mutilTaskList)) {
				return;
			}

			if (mutilTaskList.size() == 1) {
				Task runningTask = mutilTaskList.get(0);
				 
				context.setRuningCurrTask(runningTask);
				
				if (!hasApproveUser(runningTask, nodeUserMap)) {
					break ;
				}
				
				List<ApproveObject> userList = nodeUserMap.get(runningTask.getTaskDefinitionKey());
				if (ArrayUtil.isBlank(userList)) {
					break ;
				}
				
				if (inputTask!=null && runningTask.getTaskDefinitionKey().equals(inputTask.getTaskDefinitionKey())) { // 正在执行任务和inputTask不是同一个任务，是同一个任务表示流程并没有流转
					break;
				}
				
				if (userList.size() == 1) {
					if (context.getPrevTaskCandidateUserIds().equals(userList.get(0).getId())) {
						
						defaultScriptExecutor.executeGlobalBeforeScript(context);
						defaultScriptExecutor.executeNodeBeforeScript(context);
						defaultScriptExecutor.executeButtonBeforeScript(context);
						
						actTaskService.complete(runningTask.getId(), context.getCompleteVariable());
						
						defaultScriptExecutor.executeGlobalAfterScript(context);//执行全局回调角本
						defaultScriptExecutor.executeNodeAfterScript(context);
						defaultScriptExecutor.executeButtonAfterScript(context);// 执行按钮回调角本
					}else {
						break ;
					}
				}else {
					break;
				}
				 
			} else { // 会签！！
				if(context.getPrevTaskCandidateUserIds().equals(collectMutilTaskCandidateUserIds(mutilTaskList, nodeUserMap))) {
					
					defaultScriptExecutor.executeGlobalBeforeScript(context);
					defaultScriptExecutor.executeNodeBeforeScript(context);
					defaultScriptExecutor.executeButtonBeforeScript(context);
					
					for(Task t: mutilTaskList) {
						context.setRuningCurrTask(t);
						actTaskService.complete(t.getId(),context.getCompleteVariable());
					}
					
					defaultScriptExecutor.executeGlobalAfterScript(context);//执行全局回调角本
					defaultScriptExecutor.executeNodeAfterScript(context);
					defaultScriptExecutor.executeButtonAfterScript(context);// 执行按钮回调角本
					
				}else {
					break;
				}
			}
		}

		
	}
	
	/**
	 * 判断给定的节点是否有审批用户
	 * @return true=有审批用户 false=没有审批用户
	 */
	public static boolean hasApproveUser(Task task,Map<String, List<ApproveObject>> nodeUserMap) {
		List<ApproveObject> appUserObjects = nodeUserMap.get(task.getTaskDefinitionKey());
		
		if (ArrayUtil.isBlank(appUserObjects)) {
			return false;
		}
		
		List<String> uids = ApproveObject.toIdList(appUserObjects);
		
		if(ArrayUtil.isBlank(uids)) {
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
	 * <pre>
	 * 获取自动跳过的结点：设置为空、解析为空、强行跳过
	 * 
	 * 注意：（不包含拟稿节点、也不包括设置“自动跳过（当审批人设置为空）”但是节点设置了用户）
	 * </pre>
	 * 
	 * @param context  流程审批上下文对象
	 * @param isResolveEmptyApproveUser 是否抽取解析设置了“自动跳过（当审批人解析为空时）”的节点
	 * @return
	 */
	public static List<Node> getAutoJumpNodeList(ActRunningContext context) {
		final String definitionId = getPreparedDefinitionId(context);
		final Map<String,List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final Db db = context.getDb();
		
		List<Node> autoJumpNodeList = new ArrayList<>();
		if (StringUtil.isBlank(definitionId)) {
			return autoJumpNodeList;
		}
		
		// 获取“设置为空”的节点
		NodeQuery nodeQuery = new NodeQuery();
		nodeQuery.setNodeJumpType(Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP);
		nodeQuery.setDefinitionId(definitionId);
		List<Node> autoJumpNodeSetedList = db.queryForList("queryForPage", Node.class, nodeQuery);
		for (Node node : autoJumpNodeSetedList) {
			if(!nodeUserMap.containsKey(node.getTaskKey())) {
				autoJumpNodeList.add(node);
			} else {
				List<ApproveObject> currNodeApproveUsers = nodeUserMap.get(node.getTaskKey());
				if(ArrayUtil.isBlank(currNodeApproveUsers))  {
					autoJumpNodeList.add(node);
				}
			}
		}
		
		
 
		List<Node> autoJumpNodeForcedList = getForceJumpNode(db, definitionId);// 获取“自动跳过（强制）”的结点
		if (ArrayUtil.isNotBlank(autoJumpNodeForcedList)) {
			autoJumpNodeList.addAll(autoJumpNodeForcedList);
		}

		// 获取"自动跳过（当审批人解析为空时）"的结点
		nodeQuery = new NodeQuery();
		nodeQuery.setNodeJumpType(Node.NODE_JUMP_TYPE_RESOLVED_USER_EMPTY_AUTO_JUMP);
		nodeQuery.setDefinitionId(definitionId);
		List<Node> autoJumpNodeResolveEmptyList = db.queryForList("queryForPage", Node.class, nodeQuery);
		if (ArrayUtil.isNotBlank(autoJumpNodeResolveEmptyList)) {
			for (Node e : autoJumpNodeResolveEmptyList) {
				if (ArrayUtil.isBlank(nodeUserMap.get(e.getTaskKey()))) {
					autoJumpNodeList.add(e);
				}
			}
		}
		
		return autoJumpNodeList;
	}

	public static String getPreparedDefinitionId(ActRunningContext context) {
		final Task inputTask = context.getInputTask();
		final RunInstance currProcessInstance = context.getCurrProcessInstance();
		final ProcessDefinition currActDefinition = context.getCurrActDefinition();
		
		String processDefinitionId = null;
		if (currActDefinition != null) {
			processDefinitionId = currActDefinition.getId();
		} else if (currProcessInstance != null) {
			processDefinitionId = currProcessInstance.getProcessDefinitionId();
		} else if (inputTask != null) {
			processDefinitionId = inputTask.getProcessDefinitionId();
		} else {
			return null;
		}
		return processDefinitionId;
		
	}
	
	/**
	 * 自动跳过的节点，添加虚拟审批用户(含会签节自动跳过）
	 * @param variables
	 * @param givenAutoJumpNodeList 给定的自动跳过节点
	 */
	public static void fillVirtualUser4AutoJumpNode(ActRunningContext context) {
		final Map<String, Object> completeVariable = context.getCompleteVariable();

		List<Node>  givenAutoJumpNodeList = getAutoJumpNodeList(context); // 获取后台配置的自动跳过节点
		if (ArrayUtil.isBlank(givenAutoJumpNodeList)) {
			return;
		}
		
		for (Node node : givenAutoJumpNodeList) {
			boolean isMeetingNode = node.getTaskBizType() != null
					&& Node.TASK_BIZ_TYPE_MEETINGNODE == node.getTaskBizType();

			if (isMeetingNode) {
				completeVariable.put(node.getTaskKey() + "List",
						Arrays.asList(Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID));
				completeVariable.put(node.getTaskKey(),
						Arrays.asList(Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID));
			} else {
				completeVariable.put(node.getTaskKey(),
						Arrays.asList(Node.NODE_JUMP_TYPE_EMPTY_APPROVE_USER_AUTO_JUMP_VIRTUAL_USERID));
			}
		}
 
	}
	
	/**
	 * 获取会签节点
	 * @param definitionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<Node> getMeetingNode(Db db,String definitionId) {
		if (StringUtil.isBlank(definitionId)) {
			return ArrayUtil.EMPTY_LIST;
		}
		
		List<Node> list = new ArrayList<>();
		NodeQuery query = new NodeQuery();
		query.setTaskBizType(Node.TASK_BIZ_TYPE_MEETINGNODE);
		query.setDefinitionId(definitionId);
		list = db.queryForList("queryForPage", Node.class, query);
		return list;
	}
	
	/**
	 * 获取设置强制跳过的结点
	 * @param definitionId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static List<Node> getForceJumpNode(Db db,String definitionId) {
		if (StringUtil.isBlank(definitionId)) {
			return ArrayUtil.EMPTY_LIST;
		}
		
		List<Node> list = new ArrayList<>();
		NodeQuery query = new NodeQuery();
		query.setNodeJumpType(Node.NODE_JUMP_TYPE_FORCE_JUMP);
		query.setDefinitionId(definitionId);
		list = db.queryForList("queryForPage", Node.class, query);
		return list;
	}
	
	/**
	 * 不同意到拟稿人流程重新流转，标识rejectComplete状态
	 * 
	 * @param db
	 * @param processInstanceId
	 */
	public static void completeRejectStatus(Db db, String processInstanceId) {
		db.executeUpdate("update ext_approve_opinion set reject_re_run_complete_status=? where process_instance_id=?",
				ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE, processInstanceId);
	}
	
	/**
	 * 不同意（或撤回）到拟稿人，所有加签提交状态已完成
	 * @param db
	 * @param processInstanceId
	 */
	public static void deleteAssigned4Disagree(Db db, String processInstanceId) {
		db.executeUpdate("delete from ext_run_task_assign_forward_cc where process_instance_id=?", processInstanceId);
	}
	
	/**
	 * 不同意到拟稿人，所有加签主人的任务ID累计记录清空
	 * @param db
	 * @param processInstanceId
	 
	public static void deleteAssigned4OwnerTaskIds(Db db, String processInstanceId) {
		db.executeUpdate("delete from ext_assign_owner_task_ids where process_instance_id=?", processInstanceId);
	}
	*/
	
	/**
	 * 如果当前实例有会签，则填充会签变量
	 * @param instance 当前流程实例
	 * @param nodeUserMap 节点设置的审批用户
	 * @return 含审批用户+用户传入变量+会签需要的流程变量
	 */
	public static void fillMeetingVariableIfExists(ActRunningContext context) {
		final Db db = context.getDb();
		final Map<String,List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		
		
		Map<String, Object> meetingVar = new HashMap<>();
		
		List<Node> meetingNodeList = getMeetingNode(db,getPreparedDefinitionId(context));
		for(Node n: meetingNodeList) {
			List<String> assigneeList = new ArrayList<String>(); // 会签分配任务的人员
			
			List<ApproveObject> assignObjList = nodeUserMap.get(n.getTaskKey());
			assigneeList = ApproveObject.toIdList(String.class, assignObjList);
			
			meetingVar.put(n.getTaskKey()+"List", assigneeList);
		}
		
		context.getCompleteVariable().putAll(meetingVar);
	}
	
	/**
	 * 加载登陆用户信息（含主岗、主部门）
	 * @param loginUserId
	 * @param opinion
	 * @return
	 */
	public static User loadLoginUser(PlatformDb platformDB,Long loginUserId) {
		if(loginUserId == null) {
			throw new RuntimeException("登陆用户不能为空");
		}
		
		User loginUser = platformDB.getById(User.class, loginUserId);
		if(loginUser == null) {
			throw new RuntimeException("没有找到id号为"+loginUserId+"的登陆用户");
		}
		
		// 填充用户信息
		loginUser.setUserMainOrg(platformDB.queryForObject("getUserMainOrg", Org.class, loginUser.getUserId())); // 用户的主部门
		loginUser.setUserMainPosition(platformDB.queryForObject("getUserMainPosition", Position.class, loginUser.getUserId()));// 用户的主岗位
		
		if(loginUser.getUserMainOrg() == null) {
			OrgQuery qr=new OrgQuery();
			qr.setUserId(loginUser.getUserId());
			List<Org> tempOrg = platformDB.queryForList("queryForPage", Org.class, qr);
			if(tempOrg!=null && tempOrg.size()>0){
				loginUser.setUserMainOrg(tempOrg.get(0));
				
				//opinion.setApproveUserOrgText(loginUser.getUserMainOrg().getName());
			}
		}
		
		if(loginUser.getUserMainPosition() == null) {
			PositionQuery qr = new PositionQuery();
			qr.setUserId(loginUser.getUserId());
			List<Position> tempPostion = platformDB.queryForList("queryForPage", Position.class, qr);
			if(tempPostion!=null && tempPostion.size()>0) {
				loginUser.setUserMainPosition(tempPostion.get(0));
				
				//opinion.setApproveUserPositionText(loginUser.getUserMainPosition().getName());
			}
		}
		return loginUser;
	}
	
	/**
	 * 加载流程定义，用于执行全局前后置角本
	 * @param context
	 * @return
	 */
	public static ReDefinition loadReDefinion(ActRunningContext context) {
		final Db db = context.getDb();
		ReDefinitionQuery query = new ReDefinitionQuery();
		query.setSortField("id");
		query.setSortOrder("asc");
		query.setDefinitionId(getPreparedDefinitionId(context));
		List<ReDefinition> list = db.queryForList("queryForPage", ReDefinition.class, query);
		if(ArrayUtil.isNotBlank(list)) {
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 获取流程的节点数
	 * @param context
	 * @return
	 */
	public static int getNodeCount(ActRunningContext context) {
		final Db db = context.getDb();
		NodeQuery query = new NodeQuery();
		query.setDefinitionId(getPreparedDefinitionId(context));
		Integer cnt = db.queryForObject(Node.class.getName(), "getNodeCount", Integer.class, query);
		return (cnt == null ? 0 : cnt);
	}
	

	/**
	 * 获取结束节点
	 * @param context
	 * @return
	 */
	public static List<Node> getEndNodeList(ActRunningContext context) {
		final Db db = context.getDb();
		NodeQuery query = new NodeQuery();
		query.setDefinitionId(getPreparedDefinitionId(context));
		query.setTaskBizType(Node.TASK_BIZ_TYPE_LASTNODE);
		return db.queryForList("queryForPage", Node.class, query);
	}
	
	
	// ---------------------------------------------------------------------------流程发起 ------
	/**
	 * 优先从上下文取登陆用户，如果没有则从map获取key值为startUserId的字段作为登陆用户ID
	 * @param variables
	 */
	public static String resolveStartUserId(Map<String, Object> variables) {
		String startUserId = null;

		Object contextLoginId = ContextUtil.getLoginId();
		if (contextLoginId == null) {
			Object user = variables.get(ActUtil.VARIABLES_START_USER_ID);
			if (user != null) {
				startUserId = user.toString();
				//actIdentityService.setAuthenticatedUserId(startUserId);
				return startUserId;
			} else {
				log.warn(" --- 没有找到流程发起用户(登陆用户),请指定"+ActUtil.VARIABLES_START_USER_ID);
			}
		} else {
			//actIdentityService.setAuthenticatedUserId(contextLoginId.toString());
			startUserId = contextLoginId.toString();
		}
		return startUserId;
	}
	
	/**
	 * 获取单据主键
	 * @param businessKey 
	 * @param variables 流程变量
	 * @return 业务主键
	 */
	public static String resolveBusinessKey(String businessKey, Map<String, Object> variable) {
		if(StringUtil.isBlank(businessKey)){ 
			Object bk = variable.get(ActUtil.VARIABLES_BUSINESS_KEY);
			if(bk!=null){
				businessKey = bk.toString();
			}
		}
		return businessKey;
	}
	
	/**
	 * 获取单据流水号
	 * @param variables 流程发起变量
	 * @return 业务流水号
	 */
	public static String resolveBusinessFlowNo(Map<String, Object> variable) {
		if (variable != null) {
			Object flowNo = variable.get(ActUtil.VARIABLES_BUSINESS_FLOW_NO);
			if (flowNo != null) {
				return flowNo.toString();
			}
		}
		return null;
	}
	
	/**
	 * 获取单据填制人部门
	 * @param variables 流程发起变量
	 * @return 
	 */
	public static String resolveBusinessCreateDeptId(Map<String, Object> variable) {
		if (variable != null) {
			Object createDeptId = variable.get(ActUtil.VARIABLES_CREATE_DEPT_ID);
			if (createDeptId != null) {
				return createDeptId.toString();
			}
		}
		return null;
	}
	
	
	/**
	 * 获取单据标题
	 * @param variables 流程发起变量
	 * @return 
	 */
	public static String resolveBusinessTitle(Map<String, Object> variable) {
		if (variable != null) {
			Object title = variable.get(ActUtil.VARIABLES_TITLE);
			if (title != null) {
				return title.toString();
			}
		}
		return null;
	}
	
	/**
	 * 获取单据数据类型
	 * @param variables 流程发起变量
	 * @return 
	 */
	public static String resolveBusinessType(Map<String, Object> variable) {
		if (variable != null) {
			Object type = variable.get(ActUtil.VARIABLES_BUSINESS_TYPE);
			if (type != null) {
				return type.toString();
			}
		}
		return null;
	}
	
	
	/**
	 * 获取单据审批备注
	 * @param variables 流程发起变量
	 * @return 
	 */
	public static String resolveBusinessOpinion(Map<String, Object> variable) {
		if (variable != null) {
			Object opinion = variable.get(ActUtil.VARIABLES_APPROVE_OPINION);
			if (opinion != null) {
				return opinion.toString();
			}
		}
		return null;
	}
	
	/**
	 * 获取用印公司配置
	 * @param variables 流程发起变量
	 * @return 
	 */
	public static String resolveCompany4Print(Map<String, Object> variable) {
		if (variable != null) {
			Object companyName = variable.get(ActUtil.VARIABLES_PRINT_COMPANY);
			if (companyName != null) {
				return companyName.toString();
			}
		}
		return null;
	}
	
	public static Map<String,List<String>> toApproveUserMap(Map<String,List<ApproveObject>> nodeUserMap) {
		Map<String,List<String>> approveUserMap = new HashMap<>();
		for (String taskKey : nodeUserMap.keySet()) {
			List<ApproveObject> users = nodeUserMap.get(taskKey);
			approveUserMap.put(taskKey,ApproveObject.toIdList(users));
		}
		return approveUserMap;
	}
	
	
	
	/**
	 * 获取流程执行堆栈
	 * @param inputTask
	 * @param db
	 * @return
	 */
	public static List<ApproveOpinion> getExecuteTrace(ActRunningContext context) {
		List<ApproveOpinion> apprOpinionList = getExecuteSourceTrace(context);

		List<ApproveOpinion> rs = new ArrayList<>();
		for (int i = 0; i < apprOpinionList.size(); i++) {
			ApproveOpinion node = apprOpinionList.get(i);
			
			if (!node.getIsMeetingNode()) {
				rs.add(node);
			} else {
				// 会签开始提交的意见,多条合并成一条成一个节点
				int first = i ;
				int next  = i + 1;
				if (next >= apprOpinionList.size() - 1) {
					if (next == apprOpinionList.size() - 1) {
						rs.add(apprOpinionList.get(first));
					}
				} else {
					ApproveOpinion firstOne = apprOpinionList.get(first);
					ApproveOpinion secondOne = apprOpinionList.get(next);
					if (firstOne.getApproveTaskKey().equals(secondOne.getApproveTaskKey())) {
						i = first;
					} else {
						rs.add(firstOne);
					}
				}
			}

		}
		
		return rs;
	}

	/**
	 * 获取最原始的执行轨迹（会签任务意见不进行合并）
	 * @param context
	 * @return
	 */
	public static List<ApproveOpinion> getExecuteSourceTrace(ActRunningContext context) {
		final Db db = context.getDb();
		final ProcessInstance processInstance = context.getCurrActProcessInstance();
		final String processDefinitionId = getPreparedDefinitionId(context);

		ApproveOpinionQuery filter = new ApproveOpinionQuery();
		filter.setProcessInstanceId(processInstance.getProcessInstanceId());
		filter.setDefinitionId(processDefinitionId);
		filter.getApproveActionListNotIn().add(NodeButton.BTN_TYPE_ADD_ASSIGN); // 去除加签、转发、抄送的数据
		filter.getApproveActionListNotIn().add(NodeButton.BTN_TYPE_FORWORD_READ);
		filter.getApproveActionListNotIn().add(NodeButton.BTN_TYPE_COPY_SEND);

		List<ApproveOpinion> apprOpinionList = db.queryForList("queryForPage", ApproveOpinion.class, filter);
		
		List<Node> meetingNodeList = TaskQueryUtil.getMeetingNode(db, processDefinitionId);
		if(ArrayUtil.isBlank(meetingNodeList)) {
			return apprOpinionList; 
		}
		
		// 填充会签节点属性
		for (ApproveOpinion op : apprOpinionList) {
			op.setIsMeetingNode(false);
			for (Node e : meetingNodeList) {
				if (e.getTaskKey().equals(op.getApproveTaskKey())) {
					op.setIsMeetingNode(true);
					break;
				}
			}
		}
		return apprOpinionList;
	}
	
	
	// ------------------------------------
	/**
	 * 用印
	 * @param context
	 */
	public static void putPrintNodeVariableIfExists(ActRunningContext context) {
		final Db db = context.getDb();
		final Map<String, List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final RunInstance currProcessInstance = context.getCurrProcessInstance();
		
		Map<String,Object> completeVariable = context.getCompleteVariable();
		String companyName = TaskQueryUtil.resolveCompany4Print(completeVariable);
		if(StringUtil.isBlank(companyName)) {
			return ;
		}
		
		PrintInfoQuery prt = new PrintInfoQuery();
		prt.setName(companyName);
		PrintInfo model = db.queryForObject("queryForPage", PrintInfo.class, prt);
		if (model == null) {
			return;
		}
		
		NodeQuery q = new NodeQuery();
		q.setDefinitionId(getPreparedDefinitionId(context));
		q.setPrintNodes(StringUtil.join(Arrays.asList(Node.PRINT_NODE_YES_用印,Node.PRINT_NODE_YES_用印归档,Node.PRINT_NODE_YES_档案管理员,Node.PRINT_NODE_YES_现保管员)));

		List<Node> list = db.queryForList("queryForPage", Node.class, q);
		if (ArrayUtil.isNotBlank(list)) {
			for (Node e : list) {
				if (e.getPrintNode() != null) {
					if (Node.PRINT_NODE_YES_用印 == e.getPrintNode()) {
						if(StringUtil.isNotBlank(model.getPrintManUserId())) {
							completeVariable.put(e.getTaskKey(), Arrays.asList(model.getPrintManUserId()));
							nodeUserMap.put(e.getTaskKey(), Arrays.asList(new ApproveObject(model.getPrintManUserId()))); // 换人！！
							ActUtil.getRuntimeService().addParticipantUser(currProcessInstance.getInstanceId(), model.getPrintManUserId());
						}
					}
					if (Node.PRINT_NODE_YES_用印归档 == e.getPrintNode()) {
						if(StringUtil.isNotBlank(model.getPrintArchiveUserId() )) {
							completeVariable.put(e.getTaskKey(), Arrays.asList(model.getPrintArchiveUserId()));
							nodeUserMap.put(e.getTaskKey(), Arrays.asList(new ApproveObject(model.getPrintArchiveUserId()))); // 换人！！
							ActUtil.getRuntimeService().addParticipantUser(currProcessInstance.getInstanceId(), model.getPrintArchiveUserId());
						}
					}
					if (Node.PRINT_NODE_YES_档案管理员 == e.getPrintNode()) {
						if(StringUtil.isNotBlank(model.getDocAdminUserId())) {
							completeVariable.put(e.getTaskKey(), Arrays.asList(model.getDocAdminUserId()));
							nodeUserMap.put(e.getTaskKey(), Arrays.asList(new ApproveObject(model.getDocAdminUserId()))); // 换人！！
							ActUtil.getRuntimeService().addParticipantUser(currProcessInstance.getInstanceId(), model.getDocAdminUserId());
						}
					}
					if (Node.PRINT_NODE_YES_现保管员 == e.getPrintNode()) {
						if(StringUtil.isNotBlank( model.getProtectManUserId())) {
							completeVariable.put(e.getTaskKey(), Arrays.asList(model.getProtectManUserId()));
							nodeUserMap.put(e.getTaskKey(), Arrays.asList(new ApproveObject(model.getProtectManUserId()))); // 换人！！
							ActUtil.getRuntimeService().addParticipantUser(currProcessInstance.getInstanceId(), model.getProtectManUserId());
						}
					}
				}
			}
		}
	}
}
