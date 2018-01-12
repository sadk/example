package org.lsqt.act.model;

import java.util.List;

import org.lsqt.components.db.Page;

/**
 * 用户规则矩阵：填制人部门审批用户设置
 */
public class UserRuleMatrixDeptUserQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 用户规则ID */

	private Long userRuleId;

	/** 填制人部门 */

	private Long createDeptId;

	/** 审批用户 */
	private String userIds;

	private List<Long> createDeptIdList;
	
	// getter、setter

	public void setUserRuleId(Long userRuleId) {
		this.userRuleId = userRuleId;
	}

	public Long getUserRuleId() {
		return this.userRuleId;
	}

	public void setCreateDeptId(Long createDeptId) {
		this.createDeptId = createDeptId;
	}

	public Long getCreateDeptId() {
		return this.createDeptId;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getUserIds() {
		return this.userIds;
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

	public List<Long> getCreateDeptIdList() {
		return createDeptIdList;
	}

	public void setCreateDeptIdList(List<Long> createDeptIdList) {
		this.createDeptIdList = createDeptIdList;
	}

}
