package org.lsqt.syswin.authority.model;

import org.lsqt.components.db.Page;

/**
 * 岗位权限结果表
 */
public class PositionPermitResultQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 岗位id */

	private Long positionId;

	/** 功能模块编码 */
	private String moduleCode;

	/** 数据ID */

	private Long orgId;

	/** 权限类型：1=数据查询 2=数据使用 */

	private Integer type;

	// getter、setter

	public void setPositionId(Long positionId) {
		this.positionId = positionId;
	}

	public Long getPositionId() {
		return this.positionId;
	}

	public void setModuleCode(String moduleCode) {
		this.moduleCode = moduleCode;
	}

	public String getModuleCode() {
		return this.moduleCode;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getOrgId() {
		return this.orgId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
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
