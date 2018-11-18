package org.lsqt.chk.model;

/**
 * 犯罪记录详情表
 */
public class CrimeDetail {

	/***/
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
