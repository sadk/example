package org.lsqt.rst.model;

/**
 * 门店管理员表
 */
public class StoreManager {

	/***/
	private String id;

	/** 门店id */
	private String storeCode;

	/** 门店名称 */
	private String storeName;

	/** 管理者id */
	private String managerCode;

	/** 管理者姓名 */
	private String managerName;

	/** 创建时间 */

	private java.util.Date createDate;

	/** 修改时间 */

	private java.util.Date updateDate;

	/** 租户编码 */
	private String tenantCode;

	// getter、setter
	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setStoreCode(String storeCode) {
		this.storeCode = storeCode;
	}

	public String getStoreCode() {
		return this.storeCode;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreName() {
		return this.storeName;
	}

	public void setManagerCode(String managerCode) {
		this.managerCode = managerCode;
	}

	public String getManagerCode() {
		return this.managerCode;
	}

	public void setManagerName(String managerName) {
		this.managerName = managerName;
	}

	public String getManagerName() {
		return this.managerName;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTenantCode() {
		return this.tenantCode;
	}

}
