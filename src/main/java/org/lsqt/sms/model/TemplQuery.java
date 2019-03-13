package org.lsqt.sms.model;

import org.lsqt.components.db.Page;

public class TemplQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;
	
	
	private String templId;
	private String templName;
	private String templContent;
	private String createTime;
	private String templStatus;
	private String useStatus;

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
	public String getTemplId() {
		return templId;
	}
	public void setTemplId(String templId) {
		this.templId = templId;
	}
	public String getTemplName() {
		return templName;
	}
	public void setTemplName(String templName) {
		this.templName = templName;
	}
	public String getTemplContent() {
		return templContent;
	}
	public void setTemplContent(String templContent) {
		this.templContent = templContent;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}


	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public String getTemplStatus() {
		return templStatus;
	}

	public void setTemplStatus(String templStatus) {
		this.templStatus = templStatus;
	}
}
