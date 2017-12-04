package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 流程任务的加签、转发、抄送表
 */
public class RunTaskAssignForwardCcQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

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

	/** 任务是否已完成（用以区分数据）：0=没有完成 1=已完成 */
	private String taskCompleteType;

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

	/** 备注 */
	private String remark;

	// getter、setter

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

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

}
