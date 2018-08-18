package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 
 */
public class ReModelInfoQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	private String id;

	/***/
	private String name;

	/***/
	private String category;

	/***/

	private Integer version;

	/***/
	private String metaInfo;

	/***/
	private String deployeId;

	/***/
	private String editorSourceValueId;

	/***/
	private String editSourceExtraValueId;

	/***/
	private String tenantId;

	// getter、setter

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setMetaInfo(String metaInfo) {
		this.metaInfo = metaInfo;
	}

	public String getMetaInfo() {
		return this.metaInfo;
	}

	public void setDeployeId(String deployeId) {
		this.deployeId = deployeId;
	}

	public String getDeployeId() {
		return this.deployeId;
	}

	public void setEditorSourceValueId(String editorSourceValueId) {
		this.editorSourceValueId = editorSourceValueId;
	}

	public String getEditorSourceValueId() {
		return this.editorSourceValueId;
	}

	public void setEditSourceExtraValueId(String editSourceExtraValueId) {
		this.editSourceExtraValueId = editSourceExtraValueId;
	}

	public String getEditSourceExtraValueId() {
		return this.editSourceExtraValueId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantId() {
		return this.tenantId;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}
