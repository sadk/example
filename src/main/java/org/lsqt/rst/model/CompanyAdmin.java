package org.lsqt.rst.model;

/**
 * <pre>
 * 	select * from bu_company_admin_relationship ; -- 一对多:一个厂区有多个驻场管理员  
 *	select * from bu_company_store_relationship ; -- 一对多:一个门店管理多个厂区   另，门店管理多个厂区
 * </pre>
 * 驻场管理员信息表
 */
public class CompanyAdmin {

	/***/
	private Long id;

	/** 厂区id */
	private String companyCode;

	/** 厂区名称 */
	private String companyName;

	/** 驻场管理员id */
	private String userCode;

	/** 驻场管理员姓名 */
	private String userName;

	/** 创建时间 */

	private java.util.Date createDate;

	/** 修改时间 */

	private java.util.Date updateDate;

	/** 租户编码 */
	private String tenantCode;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
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
