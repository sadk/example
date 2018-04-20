package org.lsqt.act.service.support;

import java.util.List;
import java.util.Map;

import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.Task;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.lang.StringUtil;

import com.alibaba.fastjson.JSON;

/**
 * 驳回到发起人
 * @author mmyuan
 *
 */
public class RejectUpHandler {
	ActRunningContext context;
	
	public void setActRunningConext(ActRunningContext context) {
		this.context = context;
	}
	
	private Db db;
	public RejectUpHandler(Db db) {
		this.db = db;
	}
	
	public void handle() {
		/*
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
		}*/
	}
}
