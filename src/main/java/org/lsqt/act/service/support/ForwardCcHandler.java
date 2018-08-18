package org.lsqt.act.service.support;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.Task;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 转发（用户只可查看单据，流程并不做流转,不执行前、后置回调O）
 * @author mmyuan
 *
 */
public class ForwardCcHandler {
	private static final Logger  log = LoggerFactory.getLogger(ForwardCcHandler.class);
	
	transient long handleStartTime;
	
	ActRunningContext context;
	public void setActRunningConext(ActRunningContext context) {
		this.context = context;
	}

	
	public void handle()  {
		this.handleStartTime = System.currentTimeMillis();
		
		final ApproveOpinion opinion = context.getApproveOpinion();
		final Task inputTask = context.getInputTask();
		final User loginUser = context.getLoginUser();
		final Db db = context.getDb();
		final ProcessInstance currActProcessInstance = context.getCurrActProcessInstance();
		final Map<String, List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		//final DefaultScriptExecutor scriptExecutor = new DefaultScriptExecutor();
		
		
		if (!(NodeButton.BTN_TYPE_FORWORD_READ.equals(opinion.getApproveAction())
				|| NodeButton.BTN_TYPE_COPY_SEND.equals(opinion.getApproveAction()))) {
			return;
		}

		if(StringUtil.isBlank(opinion.getAssignForwardCcUserIds())) {
			throw new RuntimeException("转发的用户不能为空");
		}
	
		if(StringUtil.split(opinion.getAssignForwardCcUserIds(),",").contains(loginUser.getUserId().toString())) {
			throw new RuntimeException("不能转发给自己");
		}
		
		List<RunTaskAssignForwardCc> forwardList = new ArrayList<>();
		List<String> userIds = StringUtil.split(opinion.getAssignForwardCcUserIds(), ",");
		for (String userId : userIds) {
			RunTaskAssignForwardCc model = new RunTaskAssignForwardCc();
			model.setApproveAction(opinion.getApproveAction());
			model.setApproveUserId(loginUser.getUserId().toString());
			model.setApproveUserName(loginUser.getUserName());
			model.setAssignForwardCcUserIds(userId);
			model.setBusinessKey(currActProcessInstance.getBusinessKey());
			model.setDefinitionId(inputTask.getProcessDefinitionId());
			model.setDefinitionKey(inputTask.getProcessDefinitionKey());
			model.setDefinitionName(inputTask.getProcessDefinitionName());
			model.setProcessInstanceId(currActProcessInstance.getProcessInstanceId());
			model.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
			model.setTaskId(inputTask.getId());
			model.setTaskKey(inputTask.getTaskDefinitionKey());
			model.setTaskName(inputTask.getName());
			forwardList.add(model);
		}
		db.batchSave(forwardList);
	}
 
}
