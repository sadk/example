package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 数据映射配置
 */
public class UserDataMappingQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 数据配置ID(引用ext_user_data_config.id) */
	private String configId;

	/** 本地字段 */
	private String localField;

	/** 远程字段 */
	private String remoteField;

	/** 排序 */

	private Integer sn;

	/** 备注 */
	private String remark;

	// getter、setter

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getConfigId() {
		return this.configId;
	}

	public void setLocalField(String localField) {
		this.localField = localField;
	}

	public String getLocalField() {
		return this.localField;
	}

	public void setRemoteField(String remoteField) {
		this.remoteField = remoteField;
	}

	public String getRemoteField() {
		return this.remoteField;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return this.sn;
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
