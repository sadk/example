package org.lsqt.act.model;

import java.util.List;

import org.lsqt.components.db.Page;

/**
 * 加签主人的任务ID累计
 */
public class AssignOwnerTaskIdsQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private Long id;

	private String ids; // 用逗号分割的id字符

	/** 流程实例id */
	private Long processInstanceId;
	
	/** 加签时的节点key */
	private String taskKey;

	/***/
	private String taskIds;
	
	/**加签主人用户Id**/
	private Long assignOwnerId;

	/** 备注 */
	private String remark;

	/****/
	private List<Long> processInstanceIdList;
	
	
	// getter、setter

	public void setProcessInstanceId(Long processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public Long getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setTaskIds(String taskIds) {
		this.taskIds = taskIds;
	}

	public String getTaskIds() {
		return this.taskIds;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getAssignOwnerId() {
		return assignOwnerId;
	}

	public void setAssignOwnerId(Long assignOwnerId) {
		this.assignOwnerId = assignOwnerId;
	}

	public List<Long> getProcessInstanceIdList() {
		return processInstanceIdList;
	}

	public void setProcessInstanceIdList(List<Long> processInstanceIdList) {
		this.processInstanceIdList = processInstanceIdList;
	}

 
}
