package org.lsqt.act.service.support;

import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.Task;
import org.lsqt.components.db.Db;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 作废逻辑处理
 * @author mmyuan
 *
 */
public class AbortHandler {
	private static final Logger  log = LoggerFactory.getLogger(AbortHandler.class);
	
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
		final Map<String, List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final DefaultScriptExecutor scriptExecutor = new DefaultScriptExecutor();
		final Node draftNode = context.getDraftNode();
		final Db db = context.getDb();
		final RuntimeService actRuntimeService = ActUtil.getRuntimeService();
		
		if (!NodeButton.BTN_TYPE_ABORT.equals(opinion.getApproveAction())) {
			return;
		}
		
		scriptExecutor.executeGlobalBeforeScript(context);
		scriptExecutor.executeButtonBeforeScript(context);
		
		
		String nextTaskCandidateUserIds = TaskQueryUtil.getNextTaskCandidateUserIds(context);
		context.setNextTaskCandidateUserIds(nextTaskCandidateUserIds);
		
		actRuntimeService.deleteProcessInstance(inputTask.getProcessInstanceId(), String.format("%s作废流程实例(用户ID=%s),动作：%s",loginUser.getUserName(),loginUser.getUserId(),opinion.getApproveAction()));
		
		//db.executeUpdate("delete from ext_approve_opinion where process_instance_id=?", actInstance.getId());
		//db.executeUpdate("delete from ext_run_instance where instance_id=?", actInstance.getId());
		//db.executeUpdate("delete from ext_approve_opinion_file where approve_process_instance_id=?", actInstance.getId());
		
		TaskQueryUtil.updateInstanceStatus(db,inputTask.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已作废);
		
		scriptExecutor.executeGlobalAfterScript(context);
		scriptExecutor.executeButtonAfterScript(context);
		
	}
	
	
	/**
	 * 作废失败，则回滚操作
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
