package org.lsqt.rst.model;

/**
 * 工作地址表
 */
public class WorkAddress {

	/***/
	private Long id;

	/** 地址编码 */
	private String code;

	/** 省 */
	private String provinceName;

	/** 省编码 */
	private String provinceCode;

	/** 市 */
	private String cityName;

	/** 市编码 */
	private String cityCode;

	/** 区 */
	private String areaName;

	/** 地区编码 */
	private String areaCode;

	/** 地址名称 */
	private String address;

	/** 公司ID */
	private String companyCode;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 创建人 */
	private String createUser;

	/** 更新时间 */

	private java.util.Date updateTime;

	/** 更新人 */
	private String updateUser;

	/****/
	private String addressDesc;
	
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

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getProvinceCode() {
		return this.provinceCode;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getCityCode() {
		return this.cityCode;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaName() {
		return this.areaName;
	}

	public void setAreaCode(String areaCode) {
		this.areaCode = areaCode;
	}

	public String getAreaCode() {
		return this.areaCode;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyCode() {
		return this.companyCode;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public String getAddressDesc() {
		String r = "";
		if (this.areaName!=null) {
			r += this.areaName;
		}
		if (this.address!=null) {
			r += this.address;
		}
		return r;
	}

	public void setAddressDesc(String addressDesc) {
		this.addressDesc = addressDesc;
	}

}
