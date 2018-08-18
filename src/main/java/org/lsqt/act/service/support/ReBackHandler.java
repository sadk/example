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
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 撤回
 * 发起人撤回后，重新走流程
 * @author mmyuan
 *
 */
public class ReBackHandler {
	private static final Logger  log = LoggerFactory.getLogger(ReBackHandler.class);
	
	transient long handleStartTime;

	ActRunningContext context;
	
	public void setActRunningConext(ActRunningContext context) {
		this.context = context;
	}
	
	public void handle() {
		this.handleStartTime = System.currentTimeMillis();

		final ApproveOpinion opinion = context.getApproveOpinion();
		final Task inputTask = context.getInputTask();
		final User loginUser = context.getLoginUser();
		final RunInstance currPorcessInstance = context.getCurrProcessInstance();
		final Map<String, List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final DefaultScriptExecutor scriptExecutor = new DefaultScriptExecutor();
		final Node draftNode = context.getDraftNode();
		final Db db = context.getDb();
		
		if (!NodeButton.BTN_TYPE_START_USER_REBACK.equals(opinion.getApproveAction())) {
			return;
		}

		if (!loginUser.getUserId().toString().equals(currPorcessInstance.getStartUserId())) {
			throw new RuntimeException("不能撤回别人发起的单据");
		}

		boolean isEnd = TaskQueryUtil.isInstanceEnded(inputTask.getProcessInstanceId());
		if (isEnd) {
			throw new RuntimeException("流程已结束");
		}
		
		scriptExecutor.executeGlobalBeforeScript(context);
		scriptExecutor.executeButtonBeforeScript(context);
		
		
		// 如果是(连续）并发（会签）多任务，结束任务,并跳转到拟稿节点；更新`ext_approve_opinion`.`reject_re_run_complete_status`
		while (true) {
			List<Task> mutilTaskList = TaskQueryUtil.getCurrentMutilTaskList(db, inputTask.getProcessInstanceId());// 当前流程的任务可能是（会签）多实例

			if (mutilTaskList != null) {
				if (mutilTaskList.size() == 1) {
					context.setRuningCurrTask(mutilTaskList.get(0));
					break;
				} else {
					for (Task e : mutilTaskList) {
						ActUtil.getTaskService().complete(e.getId(), context.getCompleteVariable());
					}
				}
			}

			if (TaskQueryUtil.isInstanceEnded(inputTask.getProcessInstanceId())) {
				break;
			}
		}
		
		TaskQueryUtil.completeRejectStatus(db, inputTask.getProcessInstanceId());
		TaskQueryUtil.jump(db, context.getRuningCurrTask().getId(), draftNode.getTaskKey(), context.getCompleteVariable());
		TaskQueryUtil.updateInstanceStatus(db,inputTask.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已撤回);
		TaskQueryUtil.deleteAssigned4Disagree(db,inputTask.getProcessInstanceId());
		
		// BugFix: 撤回后，审批意见要显示的是拟稿人，而不是当前节点名称
		opinion.setApproveTaskName("拟稿人");
		opinion.setRemark("用户在节点="+inputTask.getName()+"("+inputTask.getTaskDefinitionKey()+") 时撤回");
		db.update(opinion, "approveTaskName","remark");
		
		context.setNextTaskCandidateUserIds(StringUtil.EMPTY); //BugFix:撤回也不显示审批人！
		scriptExecutor.executeGlobalAfterScript(context);
		scriptExecutor.executeButtonAfterScript(context);
	}
	
	
	/**
	 * 撤回失败，则回滚操作
	 * @param context
	 */
	public void handleCancel(ActRunningContext context)  {

	
	}
	
	public void printCost() {
		String costText = String.format("cost %s ms", System.currentTimeMillis() - this.handleStartTime);
		System.out.println(costText);
		log.debug(costText);
	}
}
