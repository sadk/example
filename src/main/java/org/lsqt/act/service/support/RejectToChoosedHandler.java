package org.lsqt.act.service.support;

import java.util.List;
import java.util.Map;

import org.activiti.engine.task.IdentityLinkType;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.Task;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 驳回到指定的目标节点
 * @author mmyuan
 *
 */
public class RejectToChoosedHandler {
	private static final Logger  log = LoggerFactory.getLogger(RejectToChoosedHandler.class);
	
	ActRunningContext context;
	
	public void setActRunningConext(ActRunningContext context) {
		this.context = context;
	}
	
	public void handle() {
		final org.activiti.engine.TaskService actTaskService = ActUtil.getTaskService();
		final ApproveOpinion opinion = context.getApproveOpinion();
		final Task inputTask = context.getInputTask();
		final User loginUser = context.getLoginUser();
		final Map<String, List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final DefaultScriptExecutor scriptExecutor = new DefaultScriptExecutor();
		final Db db = context.getDb();
		final RunInstance processInstance = context.getCurrProcessInstance();
		final Node draftNode = context.getDraftNode();
		final int nodeCount = context.getNodeCount();
		
		if (!NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(opinion.getApproveAction())) {
			return ;
		}
		
		if (StringUtil.isBlank(opinion.getRejectToChooseNodeTaskKey())) {
			throw new RuntimeException("请选择要驳回的目标节点");
		}
		
		//执行前置脚本
		scriptExecutor.executeGlobalBeforeScript(context);
		scriptExecutor.executeButtonBeforeScript(context);
		
		/*BugFix: 驳回到选择节点，
		List<ApproveObject> approveList = nodeUserMap.get(inputTask.getTaskDefinitionKey());
		if(ArrayUtil.isNotBlank(approveList)) {
			for(ApproveObject e: approveList) {
				//ActUtil.getTaskService().deleteCandidateUser(inputTask.getId(), e.getId());
				//ActUtil.getTaskService().deleteUserIdentityLink(inputTask.getId(), e.getId(), IdentityLinkType.CANDIDATE);
				//ActUtil.getTaskService().deleteu
			}
		}
		*/
		
		int cnt = 0;
		// 如果是(连续）并发（会签）多任务，结束任务,并跳转到拟稿节点；更新`ext_approve_opinion`.`reject_re_run_complete_status`
		while (true) {
			cnt++ ;
			if (TaskQueryUtil.isInstanceEnded(inputTask.getProcessInstanceId())) {
				break;
			}

			if(cnt > nodeCount) {
				break;
			}
			
			List<Task> mutilTaskList = TaskQueryUtil.getCurrentMutilTaskList(db, inputTask.getProcessInstanceId());// 当前流程的任务可能是（会签）多实例
			if (ArrayUtil.isBlank(mutilTaskList)) {
				break;
			}
			
			Task currTask = mutilTaskList.get(0);
			
			if (mutilTaskList.size() == 1) {
				context.setRuningCurrTask(currTask);
				break;

			} else {
				List<Node> endTaskList = TaskQueryUtil.getEndNodeList(context);
				if (ArrayUtil.isBlank(endTaskList)) {
					throw new RuntimeException("流程没有结束结点");
				}
				for (Node n : endTaskList) {
					if (n.getTaskKey().equals(currTask.getTaskDefinitionKey())) {
						throw new RuntimeException("末尾节点不能为会签节点，请联系管理员添加自动节点");
					}
				}

				for (int i = 0; i < mutilTaskList.size(); i++) {
					if (i == 0) {
						context.setRuningCurrTask(mutilTaskList.get(i));
						 // 会签节点，只执行一个任务的回调
						scriptExecutor.executeGlobalAfterScript(context);  
						scriptExecutor.executeButtonAfterScript(context);
					}
					ActUtil.getTaskService().complete(mutilTaskList.get(i).getId(), context.getCompleteVariable());
				}
			}
		}
		
		TaskQueryUtil.putPrintNodeVariableIfExists(context);
		
		log.info("驳回到选择的节点变量:"+JSON.toJSONString(context.getCompleteVariable(), true));
		TaskQueryUtil.jump(db, context.getRuningCurrTask().getId(),opinion.getRejectToChooseNodeTaskKey(), context.getCompleteVariable());
		
		
		if(draftNode.getTaskKey().equals(opinion.getRejectToChooseNodeTaskKey())) {
			TaskQueryUtil.updateInstanceStatus(db,inputTask.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已退回);
			context.setNextTaskCandidateUserIds(StringUtil.EMPTY);// BugFix:驳回到拟稿人不显示审批人
			
			TaskQueryUtil.deleteAssigned4Disagree(db,inputTask.getProcessInstanceId());
			//TaskQueryUtil.deleteAssigned4OwnerTaskIds(db,inputTask.getProcessInstanceId());
		} else {
			TaskQueryUtil.updateInstanceStatus(db,inputTask.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_审批中);
			context.setNextTaskCandidateUserIds(TaskQueryUtil.getNextTaskCandidateUserIds(context));
		}
		
		scriptExecutor.executeGlobalAfterScript(context);  
		scriptExecutor.executeButtonAfterScript(context);
	}
	
	public void handleCancel(ActRunningContext context)  {
		

		
		
	}
}
