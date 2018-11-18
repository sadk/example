package org.lsqt.chk.model;

import org.lsqt.components.db.Page;

/**
 * 犯罪记录详情表
 */
public class CrimeDetailQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	private Long id;
	
	/** 引用chk_user_crime.id */
	private Long ucId;

	/** 案件类型 */
	private String crimeType;

	/** 案件数量 */
	private String count;

	/** 案件类型 */
	private String caseType;

	/** 案件来源 */
	private String caseSource;

	/** 案件时间段 */
	private String casePeriod;

	/** 案件级别 */
	private String caseLevel;

	/***/
	private Long sn;

	/***/
	private String remark;

	/***/
	private String appCode;

	public void setUcId(Long ucId) {
		this.ucId = ucId;
	}

	public Long getUcId() {
		return this.ucId;
	}

	public void setCrimeType(String crimeType) {
		this.crimeType = crimeType;
	}

	public String getCrimeType() {
		return this.crimeType;
	}

	public void setCount(String count) {
		this.count = count;
	}

	public String getCount() {
		return this.count;
	}

	public void setCaseType(String caseType) {
		this.caseType = caseType;
	}

	public String getCaseType() {
		return this.caseType;
	}

	public void setCaseSource(String caseSource) {
		this.caseSource = caseSource;
	}

	public String getCaseSource() {
		return this.caseSource;
	}

	public void setCasePeriod(String casePeriod) {
		this.casePeriod = casePeriod;
	}

	public String getCasePeriod() {
		return this.casePeriod;
	}

	public void setCaseLevel(String caseLevel) {
		this.caseLevel = caseLevel;
	}

	public String getCaseLevel() {
		return this.caseLevel;
	}

	public void setSn(Long sn) {
		this.sn = sn;
	}

	public Long getSn() {
		return this.sn;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
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
