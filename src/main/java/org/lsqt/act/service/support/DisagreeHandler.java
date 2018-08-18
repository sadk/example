package org.lsqt.act.service.support;

import java.util.List;
import java.util.Map;

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

/**
 * 不同意-驳回到拟稿人
 * @author mmyuan
 *
 */
public class DisagreeHandler {
	private static final Logger  log = LoggerFactory.getLogger(DisagreeHandler.class);
	
	transient long handleStartTime;
	
	ActRunningContext context;
	public void setActRunningConext(ActRunningContext context) {
		this.context = context;
	}

	
	public void handle()  {
		this.handleStartTime = System.currentTimeMillis();
		this.handleStartTime = System.currentTimeMillis();

		final org.activiti.engine.runtime.ProcessInstance currActProcessInstance = context.getCurrActProcessInstance();
		final ApproveOpinion opinion = context.getApproveOpinion();
		final Task inputTask = context.getInputTask();
		final User loginUser = context.getLoginUser();
		final RunInstance currPorcessInstance = context.getCurrProcessInstance();
		final Map<String, List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final DefaultScriptExecutor scriptExecutor = new DefaultScriptExecutor();
		final Node draftNode = context.getDraftNode();
		final Db db = context.getDb();
		final int nodeCount = context.getNodeCount();
		
		if (!NodeButton.BTN_TYPE_REJECT_TO_STARTER.equals(opinion.getApproveAction())) {
			return;
		}
		
		//执行前置脚本
		scriptExecutor.executeGlobalBeforeScript(context);
		scriptExecutor.executeButtonBeforeScript(context);
		
		int cnt = 0;
		// 如果是(连续）并发（会签）多任务，结束任务,并跳转到拟稿节点；更新`ext_approve_opinion`.`reject_re_run_complete_status`; 更新加签 ext_run_task_assign_forward_cc.task_complete_type
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
		
		
		TaskQueryUtil.completeRejectStatus(db, inputTask.getProcessInstanceId());
		TaskQueryUtil.deleteAssigned4Disagree(db,inputTask.getProcessInstanceId());
		//TaskQueryUtil.deleteAssigned4OwnerTaskIds(db,inputTask.getProcessInstanceId());
		TaskQueryUtil.jump(db, context.getRuningCurrTask().getId(), draftNode.getTaskKey(), context.getCompleteVariable());
		
		TaskQueryUtil.updateInstanceStatus(db,inputTask.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已退回);

		//执行后置脚本
		context.setNextTaskCandidateUserIds(StringUtil.EMPTY);
		scriptExecutor.executeGlobalAfterScript(context);  
		scriptExecutor.executeButtonAfterScript(context);
	}
	
	public void handleCancel(ActRunningContext context)  {
		
	}
	
	public void printCost() {
		
	}
}
