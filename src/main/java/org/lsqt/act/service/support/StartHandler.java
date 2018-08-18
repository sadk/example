package org.lsqt.act.service.support;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.lsqt.act.ActUtil;
import org.lsqt.act.model.ActRunningContext;
import org.lsqt.act.model.ApproveObject;
import org.lsqt.act.model.ApproveOpinion;
import org.lsqt.act.model.Definition;
import org.lsqt.act.model.InstanceVariable;
import org.lsqt.act.model.Node;
import org.lsqt.act.model.NodeButton;
import org.lsqt.act.model.NodeQuery;
import org.lsqt.act.model.PrintInfo;
import org.lsqt.act.model.PrintInfoQuery;
import org.lsqt.act.model.ReDefinition;
import org.lsqt.act.model.RunInstance;
import org.lsqt.act.model.Task;
import org.lsqt.components.db.Db;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.syswin.uum.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;

/**
 * 流程发起逻辑处理
 * @author mmyuan
 *
 */
public class StartHandler {
	private static final Logger  log = LoggerFactory.getLogger(StartHandler.class);
	
	transient long handleStartTime;

	ActRunningContext context;
	
	public void setActRunningConext(ActRunningContext context) {
		this.context = context;
	}
	
	public void handle() {
		this.handleStartTime = System.currentTimeMillis();

		final RuntimeService actRuntimeService = ActUtil.getRuntimeService();
		final ProcessDefinition currActDefinition = context.getCurrActDefinition();
		final ActRunningContext.RunningForm form = context.getForm();
		final Db db = context.getDb();
		final User loginUser = context.getLoginUser();
		
		TaskQueryUtil.fillVirtualUser4AutoJumpNode(context); //为自动跳过节点设置虚拟用户
	 	TaskQueryUtil.fillMeetingVariableIfExists(context); //填充会签用户变量
	 	
	 	
	 	putPrintNodeVariableIfExists(context) ; // 填充印章审批人
	 	
	 	log.debug("流程发起入参:"+JSON.toJSONString(context.getInputVariable(), true));
	 	log.debug("流程发起聚合:"+JSON.toJSONString(context.getCompleteVariable(), true));

	 	
	 	final org.activiti.engine.runtime.ProcessInstance currActProcessInstance= actRuntimeService.startProcessInstanceById(currActDefinition.getId(),form.getBusinessKey(),context.getCompleteVariable());
		context.setCurrActProcessInstance(currActProcessInstance);
		
		final org.lsqt.act.model.ProcessInstance instance = getPreparedProcessInstance(currActDefinition, currActProcessInstance);
		
		final ApproveOpinion approveOpinion = getPreparedApproveOpinion(context);   
		final RunInstance runInstance = getPreparedRunInstace(context); //扩展流程实例信息
		final InstanceVariable instanceVariable = getPreparedInstanceVariable(context); //扩展流程变量信息
		
		context.setApproveOpinion(approveOpinion);
		context.setCurrProcessInstance(runInstance);
		
		db.save(approveOpinion);
		db.save(runInstance);
		db.save(instanceVariable);
		
		
		doAutoJump4DraftNodeIfExists(context); // 拟稿节点自动跳过
		
		context.setPrevTaskCandidateUserIds(loginUser.getUserId().toString());
		
		TaskQueryUtil.doAutoJumpIfExist(context); //假定下一步有自动跳过
		
		context.setNextTaskCandidateUserIds(TaskQueryUtil.getNextTaskCandidateUserIds(context));
		instance.getExtProperty().put(org.lsqt.act.model.ProcessInstance.CANDIDATE_USER_IDS_KEY, context.getNextTaskCandidateUserIds());
		
		context.setStartedProcessInstance(instance);
		
		 
		// 扩展的流程定义
		ReDefinition currReDefinion = TaskQueryUtil.loadReDefinion(context);
		context.setCurrReDefinion(currReDefinion);
		
		final DefaultScriptExecutor scriptExecutor = new DefaultScriptExecutor();
		//执行后置脚本
		List<Task> currTaskList = TaskQueryUtil.getCurrentMutilTaskList(db, currActProcessInstance.getProcessInstanceId());
		if(ArrayUtil.isNotBlank(currTaskList)) {
			context.setRuningCurrTask(currTaskList.get(0));
		}
		scriptExecutor.executeGlobalAfterScript(context);  
		scriptExecutor.executeButtonAfterScript(context);
		
		updatePrintInfoReffersIfExsist(context);
		
		log.debug(" --- 流程发起后，审批人"+instance.getExtProperty().get(org.lsqt.act.model.ProcessInstance.CANDIDATE_USER_IDS_KEY));
		if (StringUtil.isBlank(context.getNextTaskCandidateUserIds())) {
			String msg = "";
			List<Task> list = TaskQueryUtil.getCurrentMutilTaskList(db, runInstance.getInstanceId());
			if (ArrayUtil.isNotBlank(list)) {
				msg = "-流程节点=" + list.get(0).getName() + ",节点编码=" + list.get(0).getTaskDefinitionKey();
			}
			throw new RuntimeException("流程发起检测到有后续结点无审批人" + msg);
		}
	}
 
	/**
	 * 用印
	 * @param context
	 */
	public void putPrintNodeVariableIfExists(ActRunningContext context) {
		final Db db = context.getDb();
		final Map<String, List<ApproveObject>> nodeUserMap = context.getNodeUserMap();
		final ProcessDefinition currActDefinition = context.getCurrActDefinition();
		
		Map<String,Object> completeVariable = context.getCompleteVariable();
		String companyName = TaskQueryUtil.resolveCompany4Print(completeVariable);
		if(StringUtil.isBlank(companyName)) {
			return ;
		}
		
		PrintInfoQuery prt = new PrintInfoQuery();
		prt.setName(companyName);
		PrintInfo model = db.queryForObject("queryForPage", PrintInfo.class, prt);
		if (model == null) {
			return;
		}
		
		NodeQuery q = new NodeQuery();
		q.setDefinitionId(currActDefinition.getId());
		q.setPrintNodes(StringUtil.join(Arrays.asList(Node.PRINT_NODE_YES_用印,Node.PRINT_NODE_YES_用印归档,Node.PRINT_NODE_YES_档案管理员,Node.PRINT_NODE_YES_现保管员)));

		List<Node> list = db.queryForList("queryForPage", Node.class, q);
		if (ArrayUtil.isNotBlank(list)) {
			for (Node e : list) {
				if (e.getPrintNode() != null) {
					if (Node.PRINT_NODE_YES_用印 == e.getPrintNode()) {
						if(StringUtil.isNotBlank(model.getPrintManUserId())) {
							completeVariable.put(e.getTaskKey(), Arrays.asList(model.getPrintManUserId()));
							nodeUserMap.put(e.getTaskKey(), Arrays.asList(new ApproveObject(model.getPrintManUserId()))); // 换人！！
							//ActUtil.getRuntimeService().addParticipantUser(currProcessInstance.getInstanceId(), model.getPrintManUserId());
						}
					}
					if (Node.PRINT_NODE_YES_用印归档 == e.getPrintNode()) {
						if(StringUtil.isNotBlank(model.getPrintArchiveUserId() )) {
							completeVariable.put(e.getTaskKey(), Arrays.asList(model.getPrintArchiveUserId()));
							nodeUserMap.put(e.getTaskKey(), Arrays.asList(new ApproveObject(model.getPrintArchiveUserId()))); // 换人！！
							//ActUtil.getRuntimeService().addParticipantUser(currProcessInstance.getInstanceId(), model.getPrintArchiveUserId());
						}
					}
					if (Node.PRINT_NODE_YES_档案管理员 == e.getPrintNode()) {
						if(StringUtil.isNotBlank(model.getDocAdminUserId())) {
							completeVariable.put(e.getTaskKey(), Arrays.asList(model.getDocAdminUserId()));
							nodeUserMap.put(e.getTaskKey(), Arrays.asList(new ApproveObject(model.getDocAdminUserId()))); // 换人！！
							//ActUtil.getRuntimeService().addParticipantUser(currProcessInstance.getInstanceId(), model.getDocAdminUserId());
						}
					}
					if (Node.PRINT_NODE_YES_现保管员 == e.getPrintNode()) {
						if(StringUtil.isNotBlank( model.getProtectManUserId())) {
							completeVariable.put(e.getTaskKey(), Arrays.asList(model.getProtectManUserId()));
							nodeUserMap.put(e.getTaskKey(), Arrays.asList(new ApproveObject(model.getProtectManUserId()))); // 换人！！
							//ActUtil.getRuntimeService().addParticipantUser(currProcessInstance.getInstanceId(), model.getProtectManUserId());
						}
					}
				}
			}
		}
	}
	
	
	/**
	 * 如果印章被合同引用，则更新状态，后台不能删除
	 * 
	 * @param context
	 */
	private void updatePrintInfoReffersIfExsist(ActRunningContext context) {
		final Db db = context.getDb();

		String printInfoCompany = TaskQueryUtil.resolveCompany4Print(context.getInputVariable());
		if (StringUtil.isBlank(printInfoCompany)) {
			return;
		}
		PrintInfoQuery q = new PrintInfoQuery();
		q.setName(printInfoCompany);
		PrintInfo model = db.queryForObject("queryForPage", PrintInfo.class, q);
		if (model != null) {
			model.setReferContract(PrintInfo.REFER_CONTRACT_YES);
			db.update(model, "referContract");
		}
	}
	
	/**
	 * 如果发起人与节点审批人是同一个人就自动跳过
	 * @param context
	 */
	private void doAutoJump4SamePeople(ActRunningContext context) {
		
		final Db db = context.getDb();
		final String startUserId = context.getForm().getStartUserId();
		final TaskService actTaskService = ActUtil.getTaskService();
		final String processInstanceId = context.getCurrActProcessInstance().getProcessInstanceId();
		final DefaultScriptExecutor defaultScriptExecutor = new DefaultScriptExecutor();
		
		/* 如果开启 “相邻节点是相同用户就自动跳过”
		Definition extDefinition = db.getById(Definition.class, context.getCurrActProcessInstance().getProcessDefinitionId());
		if(extDefinition.getEnableNeighborJump() == null 
				|| Definition.ENABLE_NEIGHBOR_JUMP_DISABLE == extDefinition.getEnableNeighborJump()) {
			return ;
		}*/
		
		if (StringUtil.isBlank(context.getNextTaskCandidateUserIds())) {
			return;
		}

		int cnt = 0;
		do {
			cnt ++;
			
			if (TaskQueryUtil.isInstanceEnded(processInstanceId)) {
				context.setNextTaskCandidateUserIds("-1");
				TaskQueryUtil.updateInstanceStatus(db, processInstanceId, ActUtil.BUSINESS_STATUS_已通过);
				break;
			}

			if (startUserId.equals(context.getNextTaskCandidateUserIds())) {
				List<Task> taskList = TaskQueryUtil.getCurrentMutilTaskList(db, processInstanceId);
				if (ArrayUtil.isNotBlank(taskList)) {
					if (taskList.size() == 1) {
						context.setRuningCurrTask(taskList.get(0));
						defaultScriptExecutor.executeGlobalBeforeScript(context);
						defaultScriptExecutor.executeButtonBeforeScript(context);
						
						actTaskService.complete(taskList.get(0).getId(),context.getCompleteVariable());
						
						defaultScriptExecutor.executeGlobalAfterScript(context);//执行全局回调角本
						defaultScriptExecutor.executeButtonAfterScript(context);// 执行按钮回调角本
						
					} else { //会签
						context.setRuningCurrTask(taskList.get(0));
						defaultScriptExecutor.executeGlobalBeforeScript(context);
						defaultScriptExecutor.executeButtonBeforeScript(context);
						
						for(Task t: taskList) {
							context.setRuningCurrTask(t);
							actTaskService.complete(t.getId(),context.getCompleteVariable());
						}
						
						defaultScriptExecutor.executeGlobalAfterScript(context);//执行全局回调角本
						defaultScriptExecutor.executeButtonAfterScript(context);// 执行按钮回调角本
					}
					context.setNextTaskCandidateUserIds(TaskQueryUtil.getNextTaskCandidateUserIds(context));
				}
			} else {
				break;
			}
			
			if (cnt > context.getNodeCount()) {
				log.error("流程发起后，发起人与节点审批人是同一个人就自动跳过死循环，单据号:" + context.getForm().getBusinessFlowNo() + ",流程实例:" + processInstanceId);
				break;
			}
		} while (true);
	}
	
	/**
	 * 发起失败，则回滚操作
	 * @param context
	 */
	public void handleCancel(ActRunningContext context) {
		final Db db = context.getDb();
		final ApproveOpinion opinion = context.getApproveOpinion();
		final String processInstanceId = context.getCurrActProcessInstance().getProcessInstanceId();

		// 删除审批意见；删除扩展的流程信息; 删除流程变量信息
		if (opinion.getId() != null) {
			db.delete(opinion);
		}

		if (StringUtil.isNotBlank(processInstanceId)) {
			db.executeUpdate("delete from ext_run_instance where instance_id=?", processInstanceId);
			db.executeUpdate("delete from ext_instance_variable where instance_id=?", processInstanceId);
			ActUtil.getRuntimeService().deleteProcessInstance(processInstanceId, "流程发起失败");
		}
		
	}
	
	/**
	 * 执行发起后是拟稿节点，如果设置自动跳过则跳过
	 * @param context
	 */
	private void doAutoJump4DraftNodeIfExists(ActRunningContext context) {
		final Node draftNode = context.getDraftNode();
		final RunInstance currProcessInstance = context.getCurrProcessInstance();
		final Db db = context.getDb();
		final org.activiti.engine.TaskService actTaskService = ActUtil.getTaskService();

		if (draftNode == null) {
			return;
		}

		if (!Node.NODE_JUMP_TYPE_AUTO_JUMP.equals(draftNode.getNodeJumpType())) { // 设置了流程启动后拟稿节点自动跳过
			return;
		}

		if (TaskQueryUtil.isInstanceEnded(currProcessInstance.getInstanceId())) {
			return;
		}

		List<Task> currentMutilTaskList = TaskQueryUtil.getCurrentMutilTaskList(db,
				currProcessInstance.getInstanceId());
		if (ArrayUtil.isBlank(currentMutilTaskList)) {
			return;
		}
		
		
		if (currentMutilTaskList.size() > 1) { // 会签
			Task mettingNode = currentMutilTaskList.get(0);

			if (draftNode.getTaskKey().equals(mettingNode.getTaskDefinitionKey())) { // 发起后第一个节点是会签节点、并设置了自动跳过
				for (Task t : currentMutilTaskList) {
					actTaskService.complete(t.getId(), context.getCompleteVariable());
				}
			}

		} else {
			actTaskService.complete(currentMutilTaskList.get(0).getId(), context.getCompleteVariable());
		}
	}
    
	/**
	 * 获取流程提交审批意见信息
	 * @param context
	 * @return
	 */
	private ApproveOpinion getPreparedApproveOpinion(ActRunningContext context) {
		final User loginUser = context.getLoginUser();
		final ProcessDefinition currActDefinition =  context.getCurrActDefinition();
		final org.activiti.engine.runtime.ProcessInstance currActProcessInstance = context.getCurrActProcessInstance();
		ApproveOpinion approveOpinion = new ApproveOpinion();
		approveOpinion.setProcessInstanceId(currActProcessInstance.getProcessInstanceId());
		approveOpinion.setBusinessKey(currActProcessInstance.getBusinessKey());
		
		approveOpinion.setDefinitionId(currActDefinition.getId());
		approveOpinion.setDefinitionKey(currActDefinition.getKey());
		approveOpinion.setDefinitionName(currActDefinition.getName());
		
		approveOpinion.setApproveAction(NodeButton.BTN_TYPE_START);
		approveOpinion.setApproveResult(NodeButton.getApproveActionShortDesc(NodeButton.BTN_TYPE_START));
		approveOpinion.setRemark(NodeButton.getApproveActionDesc(NodeButton.BTN_TYPE_START));
		approveOpinion.setApproveTaskName("拟稿人");
		
		
		approveOpinion.setApproveOpinion(context.getForm().getApproveOpinion());
		
		
		approveOpinion.setBusinessType(context.getForm().getBusinessType());
		
		if (loginUser != null) {
			approveOpinion.setApproveUserId(loginUser.getUserId() + "");
			approveOpinion.setApproveUserName(loginUser.getUserName());
			
			if(loginUser.getUserMainOrg()!=null) {
				approveOpinion.setApproveUserOrgText(loginUser.getUserMainOrg().getName());
			}
			
			if(loginUser.getUserMainPosition()!=null) {
				approveOpinion.setApproveUserPositionText(loginUser.getUserMainPosition().getName());
			}
		}
		
		String variableAndApproveUserIdsJSON = JSON.toJSONString(context.getInputVariable(), true);
		approveOpinion.setVariablesJson(variableAndApproveUserIdsJSON);
		
		return approveOpinion ;
	}
	
	/**
	 * 将流程定义的信息，设置到流程实例对象里
	 * @param def
	 * @param e
	 * @return
	 */
	private org.lsqt.act.model.ProcessInstance getPreparedProcessInstance(ProcessDefinition def, org.activiti.engine.runtime.ProcessInstance e) {
		org.lsqt.act.model.ProcessInstance instance = ActUtil.convert(e);
		instance.setProcessDefinitionKey(def.getKey());// activiti没有返回这两个属性，补全！！
		instance.setProcessDefinitionName(def.getName());
		return instance;
	}
	
	/**
	 * 发起流程时，保存流程实例扩展信息
	 * @param instance 
	 * @param businessType 用户自定义的业务类型
	 */
	private RunInstance getPreparedRunInstace(ActRunningContext context) {
		final User loginUser = context.getLoginUser() ;
		final ActRunningContext.RunningForm form = context.getForm();
		final org.activiti.engine.repository.ProcessDefinition currActDefinition = context.getCurrActDefinition();
		final org.activiti.engine.runtime.ProcessInstance currActProcessInstance = context.getCurrActProcessInstance();
		
		RunInstance model = new RunInstance();
		model.setProcessDefinitionId(currActProcessInstance.getProcessDefinitionId());
		
		model.setProcessDefinitionKey(currActDefinition.getKey());
		model.setProcessDefinitionName(currActDefinition.getName());
		
		model.setInstanceId(currActProcessInstance.getProcessInstanceId());
		model.setBusinessKey(context.getForm().getBusinessKey());
		model.setBusinessFlowNo(context.getForm().getBusinessFlowNo());
		model.setTitle(form.getTitle());
		model.setCreateDeptId(context.getForm().getCreateDeptId());
		model.setStartUserId(loginUser.getUserId().toString());
		model.setCompanyNamePrint(context.getForm().getCompanyNamePrint());

		model.setStartUserName(loginUser.getUserName());
		model.setStartLoginNo(loginUser.getLoginNo());
		model.setBusinessStatus(ActUtil.BUSINESS_STATUS_审批中);
		model.setBusinessStatusDesc(ActUtil.getBusinessStatusDesc(ActUtil.BUSINESS_STATUS_审批中));
		model.setEndStatus(Integer.valueOf(ActUtil.END_STATUS_未结束));
		model.setBusinessCategory(context.getForm().getBusinessType());
		model.setAppCode(Application.APP_CODE_DEFAULT);
		
		if(loginUser.getUserMainOrg()!=null) {
			model.setStartUserOrgText(loginUser.getUserMainOrg().getName());
		}else if(loginUser.getUserOrgList()!=null && !loginUser.getUserOrgList().isEmpty()) {
			model.setStartUserOrgText(loginUser.getUserOrgList().get(0).getName());
		}
			
		if(loginUser.getUserMainPosition()!=null) {
			model.setStartUserPositionText(loginUser.getUserMainPosition().getName());
		} else {
			if(loginUser.getUserPositionList()!=null && !loginUser.getUserPositionList().isEmpty()) {
				model.setStartUserPositionText(loginUser.getUserPositionList().get(0).getName());
			}
		}
		
		//db.save(model);
		
		return model;
	}
	
	private InstanceVariable getPreparedInstanceVariable(ActRunningContext context) {
		final ActRunningContext.RunningForm form = context.getForm();
		final ProcessDefinition currActDefinition = context.getCurrActDefinition();
		final org.activiti.engine.runtime.ProcessInstance instance = context.getCurrActProcessInstance();
		final Map<String,Object> completeVariable = context.getCompleteVariable();
		final Map<String,Object> startVariable = context.getInputVariable();
		
		InstanceVariable model = new InstanceVariable();
		model.setBusinessKey(form.getBusinessKey());
		model.setDefinitionId(currActDefinition.getId());
		model.setInstanceId(instance.getProcessInstanceId());
		model.setTitle(context.getForm().getTitle());
		model.setVariableJson(JSON.toJSONString(completeVariable));
		model.setVariableJSONStart(JSON.toJSONString(startVariable));
		//db.save(model);
		return model;
	}
	
	public void printCost() {
		String costText = String.format("cost %s ms", System.currentTimeMillis() - this.handleStartTime);
		System.out.println(costText);
		log.debug(costText);
	}
}
