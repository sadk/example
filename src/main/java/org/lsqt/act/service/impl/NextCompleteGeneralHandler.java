package org.lsqt.act.service.impl;

import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.Task;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 流程下一步流转
 * @see org.lsqt.act.service.impl.NextCompleteHandler
 */
@Deprecated
public class NextCompleteGeneralHandler {
	private static final Logger  log = LoggerFactory.getLogger(NextCompleteGeneralHandler.class);
	
	private org.activiti.engine.TaskService actTaskService = ActUtil.getTaskService();


	private TaskServiceImpl taskServiceImpl;
	
	public void setTaskServiceImpl(TaskServiceImpl taskService) {
		this.taskServiceImpl = taskService;
	}
	
	/**
	 * 在流程流转中把流程审批用户"放置"到流程变量
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
	 * 流程执行下一步常规处理
	 * 
	 * @param loginUser 当前登陆用户
	 * @param actInstance 流程实例
	 * @param task 要执行的任务
	 * @param variable 流程变量
	 * @param nodeUserMap 流程节点审批人
	 * @param draftNode 流程拟稿节点
	 * @param lastTask 上一步任务（没有自动跳过就是当前任务）
	 * @param opinion 用户提交的审批意见（含核心审批动作）
	 * 
	 */
	public String execute(User loginUser, ProcessInstance actInstance, Task task, Map<String, Object> variable,
			Map<String, List<ApproveObject>> nodeUserMap, Node draftNode,ApproveOpinion opinion,Task lastTask) {

		actTaskService.complete(task.getId(), variable);
		
		final boolean isInstanceEnded = taskServiceImpl.isInstanceEnded(actInstance.getId());
		
		
		String nextTaskCandidateUserIds = "";
		if (!isInstanceEnded) { // 流程没有结束获取下一步任务
			List<Task> currTaskList = taskServiceImpl.getCurrentMutilTaskList(actInstance.getProcessInstanceId());
			if (currTaskList!=null && currTaskList.size()>1) {
				return taskServiceImpl.getMutilTaskCandidateUserIds(currTaskList,nodeUserMap);
			} else {
				task = taskServiceImpl.getNextNewTask(ActUtil.convert(actInstance));
				if(task == null) {
					return nextTaskCandidateUserIds;
				}
			}
			
			nextTaskCandidateUserIds = taskServiceImpl.getNextTaskCandidateUserIds(actInstance.getBusinessKey(), actInstance.getProcessDefinitionId(), actInstance.getId(), nodeUserMap, draftNode,task);
			
			taskServiceImpl.executeGlobalAfterScript(loginUser, ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds);//执行全局回调角本
			taskServiceImpl.executeAfterScript(loginUser, lastTask, opinion, draftNode,nextTaskCandidateUserIds);// 执行按钮回调角本
			
			task = taskServiceImpl.processAutoJumpForRessolveEmptyApproveUserNode(loginUser,opinion,lastTask,draftNode,actInstance,task,variable,nodeUserMap);//解析出用户为空自动跳过
		}
		
		if(!taskServiceImpl.isInstanceEnded(actInstance.getId())) {
			task = taskServiceImpl.processAutoJumpForEmptyApproveUserNode(loginUser,opinion,lastTask, draftNode,actInstance, task, variable,nodeUserMap); // 自动跳过(当审批用户设置为空时) -- 注：前、后置节点也会执行)
		}
		
		if(!taskServiceImpl.isInstanceEnded(actInstance.getId())) {
			task = taskServiceImpl.processAutoJumpForRessolveEmptyApproveUserNode(loginUser,opinion,lastTask,draftNode,actInstance,task,variable,nodeUserMap);//自动跳过(当审批用户解析为空时)
		}
		
		if(task !=null) {
			nextTaskCandidateUserIds = taskServiceImpl.getNextTaskCandidateUserIds(actInstance.getBusinessKey(), task.getProcessDefinitionId(), actInstance.getId(), nodeUserMap, draftNode,task);
			
			if (StringUtil.isBlank(nextTaskCandidateUserIds) && !isInstanceEnded) {
				
				// 防止流程任务游离，下一步处理人为空，将跳回原节点（事务补偿)
				taskServiceImpl.jump(task.getId(), lastTask.getTaskDefinitionKey(), variable);
				
				String msg = String.format("检测到下一步流程节点“%s”节点审批用户为空,请联系管理员设置审批人", task.getName());
				log.info(msg + ", 登陆用户:" + loginUser.getLoginNo() + ", 审批节点名称:" + task.getName() + ", 流程节点key:" + task.getTaskDefinitionKey());
				throw new RuntimeException(msg);
			}
			
			taskServiceImpl.executeGlobalAfterScript(loginUser, ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds);//执行全局回调角本
			//taskServiceImpl.executeAfterScriptForAutoJumpNode(loginUser,opinion,lastTask,task,draftNode,nextTaskCandidateUserIds);// 执行按钮回调角本
			taskServiceImpl.executeAfterScript(loginUser, lastTask, opinion, draftNode,nextTaskCandidateUserIds);// 执行按钮回调角本
		}

		return nextTaskCandidateUserIds;
	}
}
