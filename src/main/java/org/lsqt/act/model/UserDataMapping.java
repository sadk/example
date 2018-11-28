package org.lsqt.act.model;

/**
 * 数据映射配置
 */
public class UserDataMapping {

	/***/
	private Long id;

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

	/***/
	private String gid;

	/** 创建日期 */
	private java.util.Date createTime;

	/***/
	private java.util.Date updateTime;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

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

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGid() {
		return this.gid;
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

}
