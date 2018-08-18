package org.lsqt.act.model;

/**
 * 
 */
public class ReModelInfo {

	/***/
	private String id;

	/***/
	private Integer rev;

	/***/
	private String name;

	/***/
	private String key;

	/***/
	private String category;

	/***/

	private java.util.Date createTime;

	/***/

	private java.util.Date updateTime;

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
	
	// -----------------------辅助字段
	private String remark;

	// getter、setter
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setRev(Integer rev) {
		this.rev = rev;
	}

	public Integer getRev() {
		return this.rev;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getKey() {
		return this.key;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
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

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
