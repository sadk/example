package org.lsqt.rst.model;

/**
 * 门店信息表
 */
public class StoreInfo {

	/***/
	private Long id;

	/** 门店编码 */
	private String code;

	/** 门店名称 */
	private String name;

	/** 所属区域 */
	private String belongRegion;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 修改时间 */
	private java.util.Date updateTime;

	/** 租户编码 */
	private String tenantCode;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setBelongRegion(String belongRegion) {
		this.belongRegion = belongRegion;
	}

	public String getBelongRegion() {
		return this.belongRegion;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTenantCode() {
		return this.tenantCode;
	}

	public java.util.Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

}
