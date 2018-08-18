package org.lsqt.act.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.HiInstanceForwardCc;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.RunInstanceQuery;
import org.lsqt.act.model.RunTaskAssignForwardCc;
import org.lsqt.act.model.RunTaskAssignForwardCcQuery;
import org.lsqt.act.model.Task;
import org.lsqt.act.service.RunTaskAssignForwardCcService;
import org.lsqt.act.service.support.ForwardCcCommand;
import org.lsqt.act.service.support.ForwardCcHandler;
import org.lsqt.act.service.support.TaskQueryUtil;
import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.syswin.PlatformDb;
import org.lsqt.syswin.uum.model.User;

@Service
public class RunTaskAssignForwardCcServiceImpl implements RunTaskAssignForwardCcService{
	
	@Inject private Db db;
	@Inject private PlatformDb db2;
	
	public Page<RunTaskAssignForwardCc>  queryForPage(RunTaskAssignForwardCcQuery query) {
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), RunTaskAssignForwardCc.class, query);
	}

	public List<RunTaskAssignForwardCc> getAll(){
		  return db.queryForList("getAll", RunTaskAssignForwardCc.class);
	}
	
	public RunTaskAssignForwardCc saveOrUpdate(RunTaskAssignForwardCc model) {
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(RunTaskAssignForwardCc.class, Arrays.asList(ids).toArray());
	}
	
	/**
	 * 流程批量转发
	 * @param loginUserId 登陆用户
	 * @param processInstanceIds 转发的流程实例
	 * @param userIds  转发给哪些用户
	 */
	public void forward(String loginUserId,String processInstanceIds, String userIds) {
		if (StringUtil.isBlank(loginUserId)) {
			throw new IllegalArgumentException("登陆用户ID参数不能为空");
		}
		if (StringUtil.isBlank(processInstanceIds)) {
			throw new IllegalArgumentException("流程实例ID参数不能为空");
		}
		if (StringUtil.isBlank(userIds)) {
			throw new IllegalArgumentException("转发用户参数不能为空");
		}
		
		List<HiInstanceForwardCc> data = new ArrayList<>();

		List<String> instanceIdList = StringUtil.split(processInstanceIds, ",");
		for (String processInstanceId : instanceIdList) {
			if (TaskQueryUtil.isInstanceEnded(processInstanceId)) { // 已结速的流程转发添加记录
				RunInstanceQuery q=new RunInstanceQuery();
				q.setInstanceId(processInstanceId);
				RunInstance r = db.queryForObject("queryForPage", RunInstance.class, q);
				if(r!=null) {
					HiInstanceForwardCc model = new HiInstanceForwardCc();
					model.setProcessInstanceId(processInstanceId);
					model.setDefinitionId(r.getProcessDefinitionId());
					model.setDefinitionName(r.getProcessDefinitionName());
					model.setDefinitionKey(r.getProcessDefinitionKey());
					model.setBusinessKey(r.getBusinessKey());
					model.setBusinessFlowNo(r.getBusinessFlowNo());
					model.setBusinessStatus(r.getBusinessStatus());
					model.setBusinessStatusDesc(r.getBusinessStatusDesc());
					model.setStartUserId(r.getStartUserId());
					model.setStartUserName(r.getStartUserName());
					model.setStartUserOrgText(r.getStartUserOrgText());
					model.setStartUserPositionText(r.getStartUserPositionText());
					model.setTitle(r.getTitle());
					model.setForwardCcUserIds(","+userIds+",");
					model.setUpdateUserId(loginUserId);
					data.add(model);
				}
			} else {
				// 未结束的流程添加转发
				ForwardCcHandler forwardCcHandler = new ForwardCcHandler();
				org.lsqt.act.service.support.Command<Void> forwardCcCmd = new ForwardCcCommand(forwardCcHandler);
				forwardCcCmd.execute(createRunningContext(Long.valueOf(loginUserId),processInstanceId,userIds));
			}
		}
		
		if (ArrayUtil.isNotBlank(data)) {
			db.executeUpdate("delete from ext_hi_inst_forward_cc where process_instance_id in ("+processInstanceIds+") and update_user_id=?", loginUserId) ;//使终删除已结束的流程转发，再添加（同一个人操作多次）
			db.batchSave(data);
		}
		 
	}
	
	private ActRunningContext createRunningContext(Long loginUserId,String processInstanceId,String userIds) {
		final ApproveOpinion opinion = new ApproveOpinion();
		opinion.setAssignForwardCcUserIds(userIds);
		opinion.setApproveAction(NodeButton.BTN_TYPE_COPY_SEND);
		
		List<Task> currentMutilTaskList = TaskQueryUtil.getCurrentMutilTaskList(db, processInstanceId);
		if(ArrayUtil.isBlank(currentMutilTaskList)) {
			throw new RuntimeException("没有找到活动任务");
		}
		final Task inputTask = currentMutilTaskList.get(0);
		final org.activiti.engine.RuntimeService actRuntimeService = ActUtil.getRuntimeService();
		
		final User loginUser = TaskQueryUtil.loadLoginUser(db2, loginUserId);
		
		ActRunningContext context = new ActRunningContext(db);
		context.setNodeUserMap(new HashMap<>());
		context.setApproveOpinion(opinion);
		context.setInputTask(inputTask);
		context.setLoginUser(loginUser);
		// 
		org.activiti.engine.runtime.ProcessInstance currActProcessInstance = actRuntimeService.createProcessInstanceQuery()
				.includeProcessVariables().processInstanceId(inputTask.getProcessInstanceId()).singleResult();
		inputTask.setBusinessKey(currActProcessInstance.getBusinessKey());
		context.setCurrActProcessInstance(currActProcessInstance);
		
		// 获取扩展的流程实例
		RunInstanceQuery instQuery = new RunInstanceQuery();
		instQuery.setInstanceId(currActProcessInstance.getProcessInstanceId());
		RunInstance currProcessInstance = db.queryForObject("queryForPage", RunInstance.class, instQuery);
		context.setCurrProcessInstance(currProcessInstance);
		
		context.setRuningCurrTask(inputTask);
		
		// 扩展的流程定义
		ReDefinition currReDefinion = TaskQueryUtil.loadReDefinion(context);
		context.setCurrReDefinion(currReDefinion);
		return context;
	}
}