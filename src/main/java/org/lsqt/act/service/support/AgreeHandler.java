package org.lsqt.act.service.support;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLinkType;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.ApproveOpinionQuery;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.RunTaskAssignForwardCcQuery;
import org.lsqt.act.model.Task;
import org.lsqt.components.context.ContextUtil;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.tags.form.InputTag;

import com.alibaba.fastjson.JSON;

/**
 * 同意逻辑处理
 * @author mmyuan
 *
 */
public class AgreeHandler {
	private static final Logger  log = LoggerFactory.getLogger(AgreeHandler.class);
	
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
		final ProcessInstance currActProcessInstnace = context.getCurrActProcessInstance();
		
		if (!(NodeButton.BTN_TYPE_AGREE.equals(opinion.getApproveAction())
				|| NodeButton.BTN_TYPE_RESUBMIT.equals(opinion.getApproveAction()))) {
			return;
		}
		
		List<ApproveOpinion> approveTraceList = TaskQueryUtil.getExecuteTrace(context); // 获取流程执行堆栈
		
		
		//1.加签提交
		ApproveOpinion targetNode = null;
		RunTaskAssignForwardCc assignTask = TaskQueryUtil.getAssignUserApporve(db,loginUser.getUserId(),inputTask);
		if (assignTask != null) {
			targetNode = getAssignTarget(currActProcessInstnace);
			if(targetNode!=null) {
				
				
				RunTaskAssignForwardCcQuery rtQuery = new RunTaskAssignForwardCcQuery();
				rtQuery.setProcessInstanceId(inputTask.getProcessInstanceId());
				rtQuery.setTaskKey(inputTask.getTaskDefinitionKey());
				rtQuery.setApproveAction(NodeButton.BTN_TYPE_ADD_ASSIGN);
				rtQuery.setAssignForwardCcUserIds(loginUser.getUserId().toString());
				RunTaskAssignForwardCc submitedAssign = db.queryForObject("queryForPage", RunTaskAssignForwardCc.class, rtQuery);
				
				if(submitedAssign!=null && StringUtil.isNotBlank(submitedAssign.getApproveCandiateUserIds())) {
					List<String> cadidateUserList = StringUtil.split(submitedAssign.getApproveCandiateUserIds(),",");
					for(String userId: cadidateUserList) {
						ActUtil.getTaskService().deleteCandidateUser(inputTask.getId(), userId); // 删除加签的主人可查看待办任务的“权限”
					}
				}
				
				
				
				
				
				jumpTo(targetNode,true);
	
				// ----------------------------------------- 更新审批意见 Begin:-------------------------------------------------------
				opinion.setApproveAction(NodeButton.BTN_TYPE_ADD_ASSIGN_AGREE);
				opinion.setApproveResult("加签提审");
				
				// BugFix:加签提审显示用户的岗位，而不是他主人的节点名称
				if (loginUser.getUserMainPosition() != null
						&& StringUtil.isNotBlank(loginUser.getUserMainPosition().getName())) {
					opinion.setApproveTaskName(loginUser.getUserMainPosition().getName());
				} else {
					if (ArrayUtil.isNotBlank(loginUser.getUserPositionList())) {
						opinion.setApproveTaskName(loginUser.getUserPositionList().get(0).getName());
					} else {
						opinion.setApproveTaskName(StringUtil.EMPTY);
					}
				}
				db.update(opinion, "approveAction", "approveResult", "approveTaskName");
				// ----------------------------------------- 更新审批意见 End!-------------------------------------------------------
				
				
				if(submitedAssign!=null && StringUtil.isNotBlank(submitedAssign.getApproveCandiateUserIds())) {
					List<String> cadidateUserList = StringUtil.split(submitedAssign.getApproveCandiateUserIds(),",");

					// 跳转后任务ID变成新ID,再删除一次“主人任务”
					List<Task> currTaskList = TaskQueryUtil.getCurrentMutilTaskList(db, inputTask.getProcessInstanceId());
					if(ArrayUtil.isNotBlank(currTaskList)) {
						for(Task t: currTaskList) {
							for(String userId: cadidateUserList) {
								ActUtil.getTaskService().deleteCandidateUser(t.getId(), userId); // 删除加签的主人可查看待办任务的“权限”
								ActUtil.getTaskService().deleteCandidateUser(t.getId(), loginUser.getUserId().toString());
							}
						}
					}
				}
				
				if(submitedAssign!=null) {
					db.delete(submitedAssign);
				}
				
				
				String cntSql = "select count(1) cnt from ext_run_task_assign_forward_cc where process_instance_id=?  and task_key=? and approve_action='add_assign' ";
				Integer cnt =db.executeQueryForObject(cntSql, Integer.class, inputTask.getProcessInstanceId(),inputTask.getTaskDefinitionKey());
				
				if(cnt==null || cnt == 0) {
					db.executeUpdate(
							"delete from ext_run_task_assign_forward_cc where process_instance_id=? and task_key=? and approve_action= ? ",
							inputTask.getProcessInstanceId(), inputTask.getTaskDefinitionKey(), NodeButton.BTN_TYPE_ADD_ASSIGN);
					
					//db.executeUpdate("delete from ext_assign_owner_task_ids where process_instance_id=? and task_key=?", inputTask.getProcessInstanceId(), inputTask.getTaskDefinitionKey());
					
					
					// 加签全部提审完毕后，主人节点的审批用户，还原
					List<Task> currTaskList = TaskQueryUtil.getCurrentMutilTaskList(db, inputTask.getProcessInstanceId());
					if(ArrayUtil.isNotBlank(currTaskList)) {
						for(Task t: currTaskList) {
							List<ApproveObject> currNodeApproveUsers = nodeUserMap.get(t.getTaskDefinitionKey());
							if(ArrayUtil.isNotBlank(currNodeApproveUsers)) {
								for (ApproveObject e: currNodeApproveUsers) {
									ActUtil.getTaskService().addCandidateUser(t.getId(), e.getId());
								}
							}
						}
					}
					
				} else {
					// 如果加签还没有提交完，状态不能置为已完成
					targetNode.setRejectReRunCompleteStatus(null);
					db.update(targetNode, "rejectReRunCompleteStatus");
				}
			}
			return;
		}
		
		//2.多级驳回，再提交
		if (TaskQueryUtil.isMutilReject(approveTraceList)) {
			targetNode = TaskQueryUtil.getUnResetMutilRejectNode(approveTraceList);
			if(targetNode!=null) {
				jumpTo(targetNode,false);
				
				return ;
			}
		}
		
		// 3.单级驳回,再提交
		int rejectCnt = getRejectTimeCount(approveTraceList);
		if (rejectCnt == 1) {
			int idx = TaskQueryUtil.getAssignFullCompletedBesideRejectChooseNode(approveTraceList);
			if (idx!=-1) {
				targetNode = approveTraceList.get(idx);
			} else {
				if (approveTraceList.size() >= 2) {
					targetNode = approveTraceList.get(approveTraceList.size() - 2);
				}
			}

			if (targetNode != null) {
				jumpTo(targetNode,false);
				return;
			}
		
		}

		//4无驳回，提交
		GeneralCompleteHandler complete = new GeneralCompleteHandler();
		complete.setActRunningConext(context);
		complete.handle();
		updateBusinessStatus();
	}

	/**
	 * 驳回的次数 0=无驳回 1=单级驳回 >1就是多级驳回
	 * 
	 * @param approveTraceList
	 * @return
	 */
	private int getRejectTimeCount(List<ApproveOpinion> approveTraceList) {
		int cnt = 0;
		for (ApproveOpinion e : approveTraceList) {
			if (NodeButton.BTN_TYPE_REJECT_TO_CHOOSE_NODE.equals(e.getApproveAction())
					&& !ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE.equals(e.getRejectReRunCompleteStatus())) {
				cnt++;
			}
		}
		return cnt;
	}
	
 
	
	/**
	 * 获取加签后，再提交，要跳转的结点
	 * @param approveTraceList
	 * @param isSingleReject
	 * @return
	 */
	private ApproveOpinion getAssignTarget(ProcessInstance actProcessInstance) {
		final Db db = context.getDb();
		
		ApproveOpinionQuery filter = new ApproveOpinionQuery();
		filter.setProcessInstanceId(actProcessInstance.getProcessInstanceId());
		filter.setDefinitionId(actProcessInstance.getProcessDefinitionId());
		
		filter.getApproveActionListNotIn().add(NodeButton.BTN_TYPE_FORWORD_READ);// 去除转发、抄送的数据
		filter.getApproveActionListNotIn().add(NodeButton.BTN_TYPE_COPY_SEND);

		List<ApproveOpinion> approveTraceList = db.queryForList("queryForPage", ApproveOpinion.class, filter);
		
		for (int i = approveTraceList.size() - 1; i > 0; i--) {
			ApproveOpinion curr = approveTraceList.get(i);
			if(NodeButton.BTN_TYPE_ADD_ASSIGN.equals(curr.getApproveAction())
					&& !ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE.equals(curr.getRejectReRunCompleteStatus())) {
				return curr;
			}
		}
		return null;
	}
	
	// -----------------------------------------------------------------------------
	
	private void jumpTo(ApproveOpinion targetNode,boolean isAssignedSubmit) {
		final Task inputTask = context.getInputTask();
		final Node draftNode = context.getDraftNode();
		final DefaultScriptExecutor scriptExecutor = new DefaultScriptExecutor();
		final Db db = context.getDb();
		final Map<String,List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final User loginUser = context.getLoginUser();
		final ProcessInstance currActProcessInstnace = context.getCurrActProcessInstance();
		
		TaskQueryUtil.putPrintNodeVariableIfExists(context);
		
		
		//执行前置脚本
		scriptExecutor.executeGlobalBeforeScript(context);
		
		if(!isAssignedSubmit) { // BugFix: 最后一个节点如果加签提交的，不执行后置脚本
			scriptExecutor.executeButtonAfterScript(context);
		}

		if(isAssignedSubmit) {
			RunTaskAssignForwardCcQuery aq = new RunTaskAssignForwardCcQuery();
			aq.setProcessInstanceIdList(Arrays.asList(inputTask.getProcessInstanceId()));
			aq.setApproveAction(NodeButton.BTN_TYPE_ADD_ASSIGN);
			aq.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
			aq.setTaskKey(inputTask.getTaskDefinitionKey());
			List<RunTaskAssignForwardCc> assginList = db.queryForList("queryForPage", RunTaskAssignForwardCc.class, aq);
			if(ArrayUtil.isNotBlank(assginList)) {
				List<String> completeUsers = new ArrayList<>();
				for(RunTaskAssignForwardCc t: assginList) {
					completeUsers.add(t.getAssignForwardCcUserIds());
				}
				context.getCompleteVariable().put(inputTask.getTaskDefinitionKey(), completeUsers); // 加签的用户放到流程变量里
			}
		}
		
		log.debug("跳转: "+JSON.toJSONString(context.getCompleteVariable(), true));
		TaskQueryUtil.jump(db,context.getInputTask().getId(), targetNode.getApproveTaskKey(),context.getCompleteVariable());

		if(!isAssignedSubmit) { //假定下一步有自动跳过（ 加签提交不做自动跳过解析）
			TaskQueryUtil.doAutoJumpIfExist(context);
		}
		
		
		// 添加最新的流程审批人!!!!!!(用户在后台换了审批用户，在这里更新！！！！）
		List<ApproveObject> nodeApporveUser = nodeUserMap.get(targetNode.getApproveTaskKey()); // 目标节点审批人
		
		
		ApproveOpinion nextAssignedNode = getAssignTarget(currActProcessInstnace); // 加签给多个人（后，还有后续加签的人，替换默认审批用户）
		if(nextAssignedNode!=null) {
			RunTaskAssignForwardCcQuery q = new RunTaskAssignForwardCcQuery();
			q.setProcessInstanceId(currActProcessInstnace.getProcessInstanceId());
			q.setTaskKey(inputTask.getTaskDefinitionKey());
			List<RunTaskAssignForwardCc> assigneds= db.queryForList("queryForPage", RunTaskAssignForwardCc.class, q);
			if(ArrayUtil.isNotBlank(assigneds)) {
				List<ApproveObject> newNodeApporveUser = new ArrayList<>();
				for(RunTaskAssignForwardCc ac: assigneds) {
					ApproveObject approveUser = new ApproveObject(ac.getAssignForwardCcUserIds());
					newNodeApporveUser.add(approveUser);
				}
				nodeApporveUser = newNodeApporveUser;
			}
		}
		
		if(ArrayUtil.isNotBlank(nodeApporveUser )) {
			List<Task> currentMutilTaskList = TaskQueryUtil.getCurrentMutilTaskList(db, inputTask.getProcessInstanceId());
			
			if(ArrayUtil.isNotBlank(currentMutilTaskList)) {
				for(Task tsk: currentMutilTaskList) {
					for(ApproveObject u: nodeApporveUser) {
						ActUtil.getTaskService().addCandidateUser(tsk.getId(), u.getId());
					}
				}
			}
		}
		
		targetNode.setRejectReRunCompleteStatus(ApproveOpinion.REJECT_RE_RUN_COMPLETESTATUS_COMPLETE);
		db.update(targetNode, "rejectReRunCompleteStatus");
		
		
		String nextTaskCandidateUserIds = TaskQueryUtil.getNextTaskCandidateUserIds(context);
		context.setNextTaskCandidateUserIds(nextTaskCandidateUserIds);
		calculateAssignedSubmitNextCadidateUserIdsIfExists(isAssignedSubmit);
		

		/* ---- BugFix 20150508： 审批人操作“驳回到选择节点”，选择“拟稿人"节点，此时节点审批人更新为B，拟稿人提审之后A和B两个人都会有待办任务 ---------*/
		if (!isAssignedSubmit) {
			List<Task> currTaskList = TaskQueryUtil.getCurrentMutilTaskList(db, currActProcessInstnace.getProcessInstanceId());
			if (ArrayUtil.isNotBlank(currTaskList)) {
				for (Task curr : currTaskList) {
					log.info("目标结点=" + targetNode.getApproveTaskKey() + ",可审批的用户="
							+ targetNode.getApproveTaskCandidateUserIds() + ",真实审批用户ID="
							+ targetNode.getApproveUserId());
					if (StringUtil.isNotBlank(targetNode.getApproveTaskCandidateUserIds())) {
						List<String> uids = StringUtil.split(targetNode.getApproveTaskCandidateUserIds(), ",");
						
						// 排除当前真实的要审批的用户
						List<String> realUserIds = StringUtil.split(context.getNextTaskCandidateUserIds(), ",");
						if(ArrayUtil.isNotBlank(realUserIds)) {
							for(String u: realUserIds) {
								uids.remove(u);
							}
						}
						
						if(ArrayUtil.isNotBlank(uids)) {
							for (String id : uids) {
								ActUtil.getTaskService().deleteUserIdentityLink(curr.getId(), id, IdentityLinkType.CANDIDATE);
							}
						}
					}
				}
			}
		}
		/* ---------------------------------------------------- BugFix: End! ------------------------------------------*/
		
		
		if ((context.getRuningCurrTask() != null && context.getDraftNode() != null)
				&& (context.getRuningCurrTask().getTaskDefinitionKey().equals(draftNode.getTaskKey()))) {
			TaskQueryUtil.updateInstanceStatus(db, inputTask.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已退回);
		} else {
			TaskQueryUtil.updateInstanceStatus(db, inputTask.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_审批中);
		}
		
		//执行后置脚本
		scriptExecutor.executeGlobalAfterScript(context);  
		if(!isAssignedSubmit) { // BugFix: 最后一个节点如果加签提交的，不执行后置脚本
			scriptExecutor.executeButtonAfterScript(context);
		}
	}
	
	/**
	 * 显示加签提审审批人（如果用户加签给3个或多个用户，只有其中一个人提交，还有2个（或其它）未提交的，只计算未提交的用户）
	 */
	private void calculateAssignedSubmitNextCadidateUserIdsIfExists(boolean isAssginSubmit) {
		final RunInstance processInstance = context.getCurrProcessInstance();
		final Db db = context.getDb();
		final PlatformDb db2 = context.getPlatformDb();
		final Task inputTask = context.getInputTask();
		final User loginUser = context.getLoginUser();

		// 获取当前任务
		org.lsqt.act.model.TaskQuery filter = new org.lsqt.act.model.TaskQuery();
		filter.setProcessDefinitionId(processInstance.getProcessDefinitionId());
		filter.setProcessInstanceId(processInstance.getInstanceId());
		filter.setBusinessKey(processInstance.getBusinessKey());
		List<Task> taskList = db.queryForList("queryForPageDetail", Task.class, filter);// 有可能是“并发”的待办

		// 优先显示加签的用户、其次是打印节点用户
		if(isAssginSubmit) {
			RunTaskAssignForwardCcQuery aq = new RunTaskAssignForwardCcQuery();
			aq.setProcessInstanceIdList(Arrays.asList(processInstance.getInstanceId()));
			aq.setApproveAction(NodeButton.BTN_TYPE_ADD_ASSIGN);
			aq.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
			List<RunTaskAssignForwardCc> assginList = db.queryForList("queryForPage", RunTaskAssignForwardCc.class, aq);

			//去除当前加签提审的记录
			RunTaskAssignForwardCc moveObj= null;
			if (ArrayUtil.isNotBlank(assginList)){
				for(RunTaskAssignForwardCc e: assginList) {
					if(e.getProcessInstanceId().equals(processInstance.getInstanceId()) 
							&& NodeButton.BTN_TYPE_ADD_ASSIGN.equals(e.getApproveAction())
							&& e.getTaskKey().equals(inputTask.getTaskDefinitionKey())
							&& loginUser.getUserId().toString().equals(e.getAssignForwardCcUserIds())){
						moveObj = e;
						break;
					}
				}
				if(moveObj!=null) {
					assginList.remove(moveObj);
				}
			}
			
			if (ArrayUtil.isNotBlank(assginList) && ArrayUtil.isNotBlank(taskList)) {
				for (Task t : taskList) {
					List<String> appUserIds = new ArrayList<>();
					List<String> appUserNames = new ArrayList<>();
					for (RunTaskAssignForwardCc e : assginList) {
						if (processInstance.getInstanceId().equals(e.getProcessInstanceId())
								&& t.getTaskDefinitionKey().equals(e.getTaskKey())) {
							appUserIds.add(e.getAssignForwardCcUserIds());
							appUserNames.add(e.getAssignForwardCcUserIds());
						}
					}
					if(ArrayUtil.isNotBlank(appUserIds)) {
						StringBuilder sb = new StringBuilder();
						StringBuilder sb2 = new StringBuilder();
						for (int i=0;i<appUserIds.size();i++) {
							User user = db2.getById(User.class, appUserIds.get(i));
							if(user!=null) {
								sb.append(user.getUserName()+"("+user.getLoginNo()+"/"+user.getUserId()+")");
								sb2.append(user.getUserId());
								if(i!=appUserIds.size()-1) {
									sb.append(",");
									sb2.append(",");
								}
							}
						}
						t.setCandidateUserNames(sb.toString());
						t.setCandidateUserIds(sb2.toString());
						context.setNextTaskCandidateUserIds(t.getCandidateUserIds());
					}
				}
			}
		}
		
		// 显示
	}
	
	
	/**
	 * 流转失败，则回滚操作
	 * @param context
	 */
	public void handleCancel(ActRunningContext context)  {
		final Db db = context.getDb();
		final Task inputTask = context.getInputTask();
		final ApproveOpinion opinion = context.getApproveOpinion();
		final Task runningTask = context.getRuningCurrTask();
		
		//流程回滚，删除之前添加的流程意见、会签节点全部结束并跳转到inputTask结点
		boolean isInstanceEnded = TaskQueryUtil.isInstanceEnded(inputTask.getProcessInstanceId());
		
		if(!isInstanceEnded && runningTask != null) {
			String nextTaskCandidateUserIds = TaskQueryUtil.getNextTaskCandidateUserIds(context);
			
			if (StringUtil.isBlank(nextTaskCandidateUserIds)) {
				
				// 防止流程任务游离，下一步处理人为空，将跳回原节点（事务补偿)
				TaskQueryUtil.jump(db, runningTask.getId(), context.getInputTask().getTaskDefinitionKey(), context.getCompleteVariable());
				
				db.delete(opinion);
				
				String msg = String.format("检测到流程节点(编码=%s)【%s】审批用户为空,请联系管理员设置审批人", runningTask.getTaskDefinitionKey(), runningTask.getName());
				log.error(msg);
				//throw new RuntimeException(msg);
			}
		}
	}
	
	
	private void updateBusinessStatus() {
		final Db db = context.getDb();
		final Task inputTask = context.getInputTask();
		if (TaskQueryUtil.isInstanceEnded(inputTask.getProcessInstanceId())) {
			TaskQueryUtil.updateInstanceStatus(db, inputTask.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_已通过);
		} else {
			TaskQueryUtil.updateInstanceStatus(db, inputTask.getProcessInstanceId(), ActUtil.BUSINESS_STATUS_审批中);
		}
	}
	
	/**
	 * 流程退回到拟稿人，拟稿人修改单据标题即时更新
	 */
	public void updateTitle4RejectSubimt() {
		final Db db = context.getDb();
		final ActRunningContext.RunningForm form = context.getForm();
		final RunInstance currProcessInstance = context.getCurrProcessInstance();
		if(StringUtil.isNotBlank(form.getTitle())) {
			if(!currProcessInstance.getTitle().equals(form.getTitle())) {
				currProcessInstance.setTitle(form.getTitle());
				db.update(currProcessInstance, "title");
			}
		}
	}
	
	public void printCost() {
		String costText = String.format("cost %s ms", System.currentTimeMillis() - this.handleStartTime);
		System.out.println(costText);
		log.debug(costText);
	}
}
