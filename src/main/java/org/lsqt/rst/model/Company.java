package org.lsqt.rst.model;

/**
 * 公司信息
 */
public class Company {

	/***/
	private Long id;

	/** 企业编号 */
	private String code;

	/** 企业名称 */
	private String fullName;

	/** 企业简称 */
	private String shortName;

	/** 实际上班地点 */
	private String actualWorkAddress;

	/** 企业介绍 */
	private String introduction;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 更新时间 */
	private java.util.Date updateTime;

	/** 状态(0:有效，1:无效) */
	private Integer status;

	/** 创建人 */
	private String createUser;

	/** 更新人 */
	private String updateUser;
	
	/**考勤月切时间:企业的考勤月切预留天数。例如：5天后不能修改考勤数据**/
	private Integer attendanceDay;
	
	/** 租户码**/
	private String tenantCode;
	
	private Integer enableOcrIdCard; //是否开启Ocr身份证识别
	private Integer enableOcrBankCard; //是否开启Orc银行认证识别
	private Integer enableFaceDetect; //是否开启人脸识别

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

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setActualWorkAddress(String actualWorkAddress) {
		this.actualWorkAddress = actualWorkAddress;
	}

	public String getActualWorkAddress() {
		return this.actualWorkAddress;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public String getIntroduction() {
		return this.introduction;
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

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public Integer getAttendanceDay() {
		return attendanceDay;
	}

	public void setAttendanceDay(Integer attendanceDay) {
		this.attendanceDay = attendanceDay;
	}

	public Integer getEnableOcrIdCard() {
		return enableOcrIdCard;
	}

	public void setEnableOcrIdCard(Integer enableOcrIdCard) {
		this.enableOcrIdCard = enableOcrIdCard;
	}

	public Integer getEnableOcrBankCard() {
		return enableOcrBankCard;
	}

	public void setEnableOcrBankCard(Integer enableOcrBankCard) {
		this.enableOcrBankCard = enableOcrBankCard;
	}

	public Integer getEnableFaceDetect() {
		return enableFaceDetect;
	}

	public void setEnableFaceDetect(Integer enableFaceDetect) {
		this.enableFaceDetect = enableFaceDetect;
	}

}
