package org.lsqt.syswin.authority.model;

import org.lsqt.components.db.Page;

/**
 * 岗位模块范围细分表
 */
public class PositionModuleConfigQuery {
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

	/** 授权范置：1=本人 2=下属下级 3=所有下级 */
	private Integer level;
	private String levels;

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

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Integer getLevel() {
		return this.level;
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

	public String getLevels() {
		return levels;
	}

	public void setLevels(String levels) {
		this.levels = levels;
	}

}
