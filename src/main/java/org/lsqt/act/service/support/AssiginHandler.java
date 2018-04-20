package org.lsqt.act.service.support;

import org.lsqt.act.model.ActRunningContext;
import org.lsqt.components.db.Db;

/**
 * 
 * 加签处理
 * @author mmyuan
 *
 */
public class AssiginHandler {

	ActRunningContext context;
	public void setActRunningConext(ActRunningContext context) {
		this.context = context;
	}
	
	private Db db ;
	public AssiginHandler(Db db) {
		this.db = db;
	}
	
	public void handle() {
		/*
		ApproveOpinion opinion = context.getApproveOpinion();
		
		if(StringUtil.isBlank(opinion.getAssignForwardCcUserIds())) {
			throw new NullPointerException("加签、转发或抄送用户不能为空");
		}
		
		if(NodeButton.BTN_TYPE_ADD_ASSIGN.equals(opinion.getApproveAction()) && 
				StringUtil.split(opinion.getAssignForwardCcUserIds(),",").contains(context.getLoginUser().getUserId().toString())) {
			throw new IllegalArgumentException("不能加签给自己");
		}
		
		String sumForwordUids = "";
		String sumRemark = "";
		if (NodeButton.BTN_TYPE_COPY_SEND.equals(opinion.getApproveAction())) { // 如果是转发累积所有的转发人
			StringBuffer temp = new StringBuffer();
			StringBuffer tempRemark = new StringBuffer();
			
			RunTaskAssignForwardCcQuery ccQuery = new RunTaskAssignForwardCcQuery();
			ccQuery.setTaskId(taskId);
			ccQuery.setApproveAction(NodeButton.BTN_TYPE_COPY_SEND);
			ccQuery.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
			List<RunTaskAssignForwardCc> tempList = db.queryForList("queryForPage", RunTaskAssignForwardCc.class,ccQuery);
			if (tempList != null) {
				for (RunTaskAssignForwardCc e : tempList) {
					if (StringUtil.isNotBlank(e.getAssignForwardCcUserIds())) {
						List<String> ids = StringUtil.split(e.getAssignForwardCcUserIds(), ",");
						temp.append(StringUtil.join(ids, ","));
						tempRemark.append(e.getRemark());
					}
				}
			}
			sumForwordUids = temp.toString();
			sumRemark = tempRemark.toString();
		}
		
		// 同一个任务，加签操作以最后的一个为准
		String sql = String.format("delete from %s where task_id=?", db.getFullTable(RunTaskAssignForwardCc.class));
		db.executeUpdate(sql, task.getId());
		
		RunTaskAssignForwardCc model = new RunTaskAssignForwardCc();
		model.setApproveAction(opinion.getApproveAction());
		model.setApproveUserId(loginUserId.toString());
		model.setApproveUserName(loginUser.getUserName());
		
		String assignForwardCcUserIds = opinion.getAssignForwardCcUserIds();
		if (!assignForwardCcUserIds.startsWith(",")) {
			assignForwardCcUserIds = "," + assignForwardCcUserIds;
		}
		if (!assignForwardCcUserIds.endsWith(",")) {
			assignForwardCcUserIds = assignForwardCcUserIds + ",";
		}
		
		if(StringUtil.isNotBlank(sumForwordUids)) {
			assignForwardCcUserIds = assignForwardCcUserIds +sumForwordUids+","; // 添加积累的抄送用户
		}
		model.setAssignForwardCcUserIds(assignForwardCcUserIds);
		
		model.setBusinessKey(actInstance.getBusinessKey());
		//model.setBusinessType();
		model.setDefinitionId(task.getProcessDefinitionId());
		model.setDefinitionKey(task.getProcessDefinitionKey());
		model.setDefinitionName(task.getProcessDefinitionName());
		model.setProcessInstanceId(actInstance.getId());
		model.setTaskCompleteType(RunTaskAssignForwardCc.TASK_COMPLETE_TYPE_UN_DONE);
		model.setTaskId(task.getId());
		model.setTaskKey(task.getTaskDefinitionKey());
		model.setTaskName(task.getName());
		
		
		String remark =String.format("用户%s%s给用户%s,(操作日期%s)",loginUserId,NodeButton.getApproveActionDesc(opinion.getApproveAction()), opinion.getAssignForwardCcUserIds(),DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
		if (StringUtil.isNotBlank(sumRemark)) {
			model.setRemark(remark + "; " + sumRemark);
		} else {
			model.setRemark(remark);
		}
		db.save(model);
		
		
		
		// 处理下一步审批人
		String nextTaskCandidateUserIds = "";
		if(NodeButton.BTN_TYPE_ADD_ASSIGN.equals(opinion.getApproveAction())
				|| NodeButton.BTN_TYPE_FORWORD_READ.equals(opinion.getApproveAction())) {
			nextTaskCandidateUserIds =  opinion.getAssignForwardCcUserIds();
			 
		}else {
			List<ApproveObject> tempObjs = nodeUserMap.get(task.getTaskDefinitionKey());
			if (tempObjs != null) {
				nextTaskCandidateUserIds = StringUtil.join(ApproveObject.toIdList(tempObjs), ",");
			}
		}
		
		//先执行全局回调角本、再执行按钮回调角本（就近原则!!)
		executeGlobalAfterScript(loginUser,ActUtil.convert(actInstance), task.getProcessDefinitionId(), nextTaskCandidateUserIds);
		executeAfterScript(loginUser,task,opinion,draftNode,nextTaskCandidateUserIds);
		
		
		
		//processRunInstaceStatus(actInstance.getId()); //流程如果结束更新流程状态为已完成
		
		
		return nextTaskCandidateUserIds;*/
	}




}
