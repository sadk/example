package org.lsqt.act.model;

/**
 * 后台流程跳转调整用
 * @author Sky
 *
 */
public class JumpForm {
	
	private String instanceId; // 流程实例ID
	private String processDefinitionKey; // 流程定义key
	private String processDefinitionId; // 
	private String processDefinitionIdTarget; // 要跳转的流程
	
	private String title;
	
	/** 当前审批节点名称 **/
	private String taskName;
	private String taskKey;
	
	private String businessKey;
	 
	/** 流程发起人 */
	private String startUserId ;
	private String startUserName;
	private String createDeptId; // 填制人部门ID
	
	
	private String approveAction;
	
	private String taskKeyTarget;// 目标节点key
	private String taskkeyTargetCandiateUserIds; // 目标节点的审批用户
	
	private String approveOpinion;
	
	private String variableJSON4Jump; // 流程跳转时，传入的变量
	private String variableJSONInit; // 流程发起最初使的流程变量 
	private String variableJSONStarting; // 流程启动时的变量
	
	public String getInstanceId() {
		return instanceId;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getApproveAction() {
		return approveAction;
	}

	public void setApproveAction(String approveAction) {
		this.approveAction = approveAction;
	}

	public String getApproveOpinion() {
		return approveOpinion;
	}

	public void setApproveOpinion(String approveOpinion) {
		this.approveOpinion = approveOpinion;
	}

	public String getTaskKeyTarget() {
		return taskKeyTarget;
	}

	public void setTaskKeyTarget(String taskKeyTarget) {
		this.taskKeyTarget = taskKeyTarget;
	}

	public String getVariableJSON4Jump() {
		return variableJSON4Jump;
	}

	public void setVariableJSON4Jump(String variableJSON4Jump) {
		this.variableJSON4Jump = variableJSON4Jump;
	}

	public String getVariableJSONInit() {
		return variableJSONInit;
	}

	public void setVariableJSONInit(String variableJSONInit) {
		this.variableJSONInit = variableJSONInit;
	}

	public String getVariableJSONStarting() {
		return variableJSONStarting;
	}

	public void setVariableJSONStarting(String variableJSONStarting) {
		this.variableJSONStarting = variableJSONStarting;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getCreateDeptId() {
		return createDeptId;
	}

	public void setCreateDeptId(String createDeptId) {
		this.createDeptId = createDeptId;
	}

	public String getTaskkeyTargetCandiateUserIds() {
		return taskkeyTargetCandiateUserIds;
	}

	public void setTaskkeyTargetCandiateUserIds(String taskkeyTargetCandiateUserIds) {
		this.taskkeyTargetCandiateUserIds = taskkeyTargetCandiateUserIds;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionIdTarget() {
		return processDefinitionIdTarget;
	}

	public void setProcessDefinitionIdTarget(String processDefinitionIdTarget) {
		this.processDefinitionIdTarget = processDefinitionIdTarget;
	}
	
}
