package org.lsqt.act.service.support;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.AssignOwnerTaskIds;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.RunTaskAssignForwardCcQuery;
import org.lsqt.act.model.Task;
import org.lsqt.act.model.TaskQuery;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 加签处理
 * 
 * 加签、转发、抄送, 流程引擎不往下走，只是添加可以查看到待办的用户，见表：ext_run_task_assign_forward_cc 
 * 1.加签流程支持加签，可以加签至流程节点之外的人员审批(A选择B来加签，B处理完了后还要再回到A处理!!!) 。
 * 注意：A加签给B，B再加签给C，C再加签给D，那么B、C、D都处理完了，再回到A
 * 
 * 2.转发审批人可以将流程转发至下级或平级人员查看、审批 
 * 3.抄送 审批人可以将流程抄送至上级人员查看
 * 
 * @author mmyuan
 *
 */
public class AssignHandler {
	private static final Logger  log = LoggerFactory.getLogger(AssignHandler.class);
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
		final Map<String, List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final DefaultScriptExecutor scriptExecutor = new DefaultScriptExecutor();
		final Db db = context.getDb();
		
		if (!NodeButton.BTN_TYPE_ADD_ASSIGN.equals(opinion.getApproveAction())) {
			return ;
		}
		
		if(StringUtil.isBlank(opinion.getAssignForwardCcUserIds())) {
			throw new RuntimeException("加签的用户不能为空");
		}
	
		if(StringUtil.split(opinion.getAssignForwardCcUserIds(),",").contains(loginUser.getUserId().toString())) {
			throw new RuntimeException("不能加签给自己");
		}
		
		//执行前置脚本
		scriptExecutor.executeGlobalBeforeScript(context);
		scriptExecutor.executeButtonAfterScript(context);
		
		// 加签给多个用户会产生多条记录
		List<RunTaskAssignForwardCc> assignList = new ArrayList<>();
		List<String> userIds = StringUtil.split(opinion.getAssignForwardCcUserIds(), ",");
		
		
		List<String> cadidateUserList = ApproveObject.toIdList(nodeUserMap.get(inputTask.getTaskDefinitionKey())); // 当前节点默认的审批用户
		if(ArrayUtil.isNotBlank(cadidateUserList)) {
			for(String userId: cadidateUserList) {
				//ActUtil.getRuntimeService().deleteUserIdentityLink(inputTask.getProcessInstanceId(), userId, IdentityLinkType.CANDIDATE);
				ActUtil.getTaskService().deleteCandidateUser(inputTask.getId(), userId);
			}
		}
		
		for (String userId : userIds) {
			RunTaskAssignForwardCc model = new RunTaskAssignForwardCc();
			model.setApproveAction(opinion.getApproveAction());
			model.setApproveUserId(loginUser.getUserId().toString());
			model.setApproveUserName(loginUser.getUserName());
			model.setAssignForwardCcUserIds(userId);
			model.setBusinessKey(inputTask.getBusinessKey());
			model.setDefinitionId(inputTask.getProcessDefinitionId());
			model.setDefinitionKey(inputTask.getProcessDefinitionKey());
			model.setDefinitionName(inputTask.getProcessDefinitionName());
			model.setProcessInstanceId(inputTask.getProcessInstanceId());
			model.setTaskId(inputTask.getId());
			model.setTaskKey(inputTask.getTaskDefinitionKey());
			model.setTaskName(inputTask.getName());
			model.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
			
			if (ArrayUtil.isNotBlank(cadidateUserList)) {
				java.util.Collections.sort(cadidateUserList);
				model.setApproveCandiateUserIds("," + StringUtil.join(cadidateUserList, ",") + ",");
			}
			
			assignList.add(model);
		}
		db.batchSave(assignList);
		
		//saveAssignOwnerTaskIds(db,inputTask,cadidateUserList);
		
		//List<Task> mutilTask = TaskQueryUtil.getCurrentMutilTaskList(db, inputTask.getProcessInstanceId());
		//List<String> taskIdList = Task.toTaskIdList(mutilTask);

		// 未处理的加签用户
		RunTaskAssignForwardCcQuery ccQuery = new RunTaskAssignForwardCcQuery();
		ccQuery.setProcessInstanceId(inputTask.getProcessInstanceId());
		ccQuery.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
		ccQuery.setApproveAction(NodeButton.BTN_TYPE_ADD_ASSIGN);
		
		
		//ccQuery.setTaskIdList(taskIdList);
		List<RunTaskAssignForwardCc> rs = db.queryForList("queryForPage", RunTaskAssignForwardCc.class, ccQuery);

		Set<String> userIdLis = new HashSet<>();
		if (ArrayUtil.isNotBlank(rs)) {
			for(RunTaskAssignForwardCc e: rs) {
				userIdLis.addAll(StringUtil.split(e.getAssignForwardCcUserIds(), ","));
			}
		}
		context.setNextTaskCandidateUserIds(StringUtil.join(userIdLis));
		
		//执行后置脚本
		scriptExecutor.executeGlobalAfterScript(context);  
		scriptExecutor.executeButtonAfterScript(context);
		
	}
	
	/**
	 * 加签候选人累计保存，用于我的待办查询时，去除加签主人的待办.
	 * 如：当前节点的审批用户是A、B, 其中A加签了给三个用户X、Y、Z，那么登陆用户A登陆时是看不到待办的（只有X、Y、Z能看到待办）
	 */
	private void saveAssignOwnerTaskIds(Db db,Task inputTask,List<String> cadiateUserIdList) {
		if(ArrayUtil.isNotBlank(cadiateUserIdList)) {
			List<AssignOwnerTaskIds> aots = new ArrayList<>();
			for(String u: cadiateUserIdList) {
				AssignOwnerTaskIds aot = new AssignOwnerTaskIds();
				aot.setProcessInstanceId(Long.valueOf(inputTask.getProcessInstanceId()));
				aot.setTaskKey(inputTask.getTaskDefinitionKey());
				aot.setAssignOwnerId(Long.valueOf(u));
				
				org.lsqt.act.model.TaskQuery tq= new TaskQuery();
				tq.setUserId(u);
				List<String> taskList = db.queryForList(Task.class.getName(), "getReferTaskAboutUser", String.class, tq); // 获取用户参与的任务

				if(ArrayUtil.isNotBlank(taskList)) {
					aot.setTaskIds(StringUtil.join(taskList));
				}
				aots.add(aot);
			}
			if(!aots.isEmpty()) {
				db.batchSave(aots);
			}
		}
	}

	/**
	 * 流转失败，则回滚操作
	 * @param context
	 */
	public void handleCancel(ActRunningContext context)  {
		
	}

	public void printCost() {
		if(log.isDebugEnabled() || log.isErrorEnabled()) {
			System.out.printf("cost: %.2fs", (System.currentTimeMillis() - this.handleStartTime) * 1e-9);
		}
	}
}
