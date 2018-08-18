package org.lsqt.act.model;

/**
 * 流程任务的加签、转发、抄送表
 */
public class RunTaskAssignForwardCc {

	/***/
	private Long id;

	/** 表单数据id */
	private String businessKey;

	/** 表单数据（自定义）业务类型 */
	private String businessType;

	/** 流程实例id */
	private String processInstanceId;

	/** 流程定义ID */
	private String definitionId;

	/** 流程定义名称 */
	private String definitionName;

	/** 流程定义key */
	private String definitionKey;

	/** 记录审批时的节点ID（待办任务ID） */
	private String taskId;

	/** 任务是否已完成（用以区分数据）：0=没有完成 1=已完成 （该字段暂时没有用到！！！）*/
	private String taskCompleteType;
	public static final String TASK_COMPLETE_TYPE_UN_DONE="0";
	public static final String TASK_COMPLETE_TYPE_DONE = "1";

	/** 待办任务名称 */
	private String taskName;

	/** 待办任务Key */
	private String taskKey;

	/** 审批操作用户 */
	private String approveUserId;

	/** 审批用户名 */
	private String approveUserName;

	/** 审批操作 code： 同意、拒绝等 */
	private String approveAction;

	/** 加签、转发、抄送的用户（多个以逗号隔开） */
	private String assignForwardCcUserIds;

	/** 加签操作时的那个节点，可审批的用户**/
	private String approveCandiateUserIds;
	
	/** 备注 */
	private String remark;

	/***/
	private String gid;

	/** 创建日期 */

	private java.util.Date createTime;

	/***/

	private java.util.Date updateTime;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public String getBusinessType() {
		return this.businessType;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionId() {
		return this.definitionId;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public String getDefinitionName() {
		return this.definitionName;
	}

	public void setDefinitionKey(String definitionKey) {
		this.definitionKey = definitionKey;
	}

	public String getDefinitionKey() {
		return this.definitionKey;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public void setTaskCompleteType(String taskCompleteType) {
		this.taskCompleteType = taskCompleteType;
	}

	public String getTaskCompleteType() {
		return this.taskCompleteType;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskName() {
		return this.taskName;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
	}

	public String getApproveUserId() {
		return this.approveUserId;
	}

	public void setApproveUserName(String approveUserName) {
		this.approveUserName = approveUserName;
	}

	public String getApproveUserName() {
		return this.approveUserName;
	}

	public void setApproveAction(String approveAction) {
		this.approveAction = approveAction;
	}

	public String getApproveAction() {
		return this.approveAction;
	}

	public void setAssignForwardCcUserIds(String assignForwardCcUserIds) {
		this.assignForwardCcUserIds = assignForwardCcUserIds;
	}

	public String getAssignForwardCcUserIds() {
		return this.assignForwardCcUserIds;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGid() {
		return this.gid;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public String getApproveCandiateUserIds() {
		return approveCandiateUserIds;
	}

	public void setApproveCandiateUserIds(String approveCandiateUserIds) {
		this.approveCandiateUserIds = approveCandiateUserIds;
	}

}
