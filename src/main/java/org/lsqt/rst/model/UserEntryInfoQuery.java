package org.lsqt.rst.model;

import org.lsqt.components.db.Page;

/**
 * 入职用户状态变化表
 */
public class UserEntryInfoQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 用户ID */
	private String userCode;

	/** 真实姓名 */
	private String userName;

	/** 手机号 */
	private String phone;

	/**
	 * 入职状态,100：待入职，120：身份证识别状态，140人脸识别状态，160银行卡是被状态，180入职信息完善，300待人工审核，400已入职，
	 * 500审核失败
	 */
	private String entryStatus;

	/** 用户当前所属企业(名称) */
	private String companyName;

	/** 用户当前所属企业(编码) */
	private String companyCode;

	/** 用户当前所属门店(编码) */
	private String storeCode;

	/** 用户当前所属门店 */
	private String storeName;

	/** 租户编码 */
	private String tenantCode;
	
	private String entryTimeBegin;
	private String entryTimeEnd;

	// getter、setter

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

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setEntryStatus(String entryStatus) {
		this.entryStatus = entryStatus;
	}

	public String getEntryStatus() {
		return this.entryStatus;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public String getCompanyCode() {
		return this.companyCode;
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

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTenantCode() {
		return this.tenantCode;
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

	public String getEntryTimeBegin() {
		return entryTimeBegin;
	}

	public void setEntryTimeBegin(String entryTimeBegin) {
		this.entryTimeBegin = entryTimeBegin;
	}

	public String getEntryTimeEnd() {
		return entryTimeEnd;
	}

	public void setEntryTimeEnd(String entryTimeEnd) {
		this.entryTimeEnd = entryTimeEnd;
	}

}
