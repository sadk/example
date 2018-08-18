package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 已结束流程转发
 */
public class HiInstanceForwardCcQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 流程实例id */
	private String processInstanceId;

	/** 流程定义ID */
	private String definitionId;

	/** 流程定义名称 */
	private String definitionName;

	/***/
	private String definitionKey;

	/** 表单业务主键id */
	private String businessKey;

	/***/
	private String businessFlowNo;

	/***/
	private String businessStatus;

	/***/
	private String businessStatusDesc;

	/***/
	private String startUserId;

	/***/
	private String startUserName;

	/***/
	private String startUserOrgText;

	/***/
	private String startUserPositionText;

	/***/
	private String title;

	/** 转发抄送的用户 */
	private String forwardCcUserIds;

	/***/
	private String updateUserId;

	/** 备注 */
	private String remark;

	// getter、setter

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

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessFlowNo(String businessFlowNo) {
		this.businessFlowNo = businessFlowNo;
	}

	public String getBusinessFlowNo() {
		return this.businessFlowNo;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getBusinessStatus() {
		return this.businessStatus;
	}

	public void setBusinessStatusDesc(String businessStatusDesc) {
		this.businessStatusDesc = businessStatusDesc;
	}

	public String getBusinessStatusDesc() {
		return this.businessStatusDesc;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getStartUserId() {
		return this.startUserId;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getStartUserName() {
		return this.startUserName;
	}

	public void setStartUserOrgText(String startUserOrgText) {
		this.startUserOrgText = startUserOrgText;
	}

	public String getStartUserOrgText() {
		return this.startUserOrgText;
	}

	public void setStartUserPositionText(String startUserPositionText) {
		this.startUserPositionText = startUserPositionText;
	}

	public String getStartUserPositionText() {
		return this.startUserPositionText;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setForwardCcUserIds(String forwardCcUserIds) {
		this.forwardCcUserIds = forwardCcUserIds;
	}

	public String getForwardCcUserIds() {
		return this.forwardCcUserIds;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public String getUpdateUserId() {
		return this.updateUserId;
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
