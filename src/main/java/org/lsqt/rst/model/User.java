package org.lsqt.rst.model;

import org.lsqt.components.context.annotation.model.Pattern;

/**
 * 用户信息
 */
public class User {

	/***/
	private Long id;

	/** 用户ID */
	private String code;

	/** 真实姓名 */
	private String realName;

	/** 用户昵称 */
	private String nickName;

	/** 手机号 */
	private String mobile;

	/** 微信账号 */
	private String wxAccount;

	/** 性别 */
	private String sex;

	/** 出生日期 */
	@Pattern("yyyy-MM-dd")
	private java.util.Date birthday;

	/** 学历 */
	private String education;

	/** 省份 */
	private String provinceName;

	/** 城市 */
	private String cityName;

	/** 国家 */
	private String countryName;

	/** 用户头像 */
	private String headImgUrl;

	/** 角色编码(待删除) */
	private String roleCode;

	/** 角色名称(待删除) */
	private String roleName;

	/** 注册时间 */
	private java.util.Date registrationTime;

	/** 注册来源 */
	private String registrationSource;

	/** 推荐人编码 */
	private String refereeUserCode;

	/** 用户是否同意授权用户信息被使用 */
	private String isAgreeAuthoize;

	/** 意向工作 */
	private String expectedJob;

	/** 最近工作的职位编码(待删除) */
	private String latelyPositionCode;

	/** 最近工作的职位名称 */
	private String latelyPositionName;

	/** 用户密码 */
	private String userPassword;

	/** 最近一份工作的入职时间 */

	private java.util.Date latelyJobEntryDate;

	/** 公众号关注状态 */
	private String subscribe;

	/** 微信唯一标识 */
	private String unionId;

	/** 坐席电话 */
	private String seatNumber;

	/** 邮箱 */
	private String email;
	/*
	*//**用户离在职离职状态: L1 是离职 *//*
	private String entryStatus;
	public static final String ENTRY_STATUS_离职="L1";
	public static final String ENTRY_STATUS_入职="P5";

	*//**离职日期**//*
	private Date quitTime;
	
	*//**当前用户在职企业**//*
	private String dependCompanyName;
	private String dependCompanyCode;
	*/
	
	private String tenantCode;
	
	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

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

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getRealName() {
		return this.realName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getNickName() {
		return this.nickName;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setWxAccount(String wxAccount) {
		this.wxAccount = wxAccount;
	}

	public String getWxAccount() {
		return this.wxAccount;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSex() {
		return this.sex;
	}

	public void setBirthday(java.util.Date birthday) {
		this.birthday = birthday;
	}

	public java.util.Date getBirthday() {
		return this.birthday;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getEducation() {
		return this.education;
	}

	public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}

	public String getProvinceName() {
		return this.provinceName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCityName() {
		return this.cityName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

	public String getHeadImgUrl() {
		return this.headImgUrl;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRegistrationTime(java.util.Date registrationTime) {
		this.registrationTime = registrationTime;
	}

	public java.util.Date getRegistrationTime() {
		return this.registrationTime;
	}

	public void setRegistrationSource(String registrationSource) {
		this.registrationSource = registrationSource;
	}

	public String getRegistrationSource() {
		return this.registrationSource;
	}

	public void setRefereeUserCode(String refereeUserCode) {
		this.refereeUserCode = refereeUserCode;
	}

	public String getRefereeUserCode() {
		return this.refereeUserCode;
	}

	public void setIsAgreeAuthoize(String isAgreeAuthoize) {
		this.isAgreeAuthoize = isAgreeAuthoize;
	}

	public String getIsAgreeAuthoize() {
		return this.isAgreeAuthoize;
	}

	public void setExpectedJob(String expectedJob) {
		this.expectedJob = expectedJob;
	}

	public String getExpectedJob() {
		return this.expectedJob;
	}

	public void setLatelyPositionCode(String latelyPositionCode) {
		this.latelyPositionCode = latelyPositionCode;
	}

	public String getLatelyPositionCode() {
		return this.latelyPositionCode;
	}

	public void setLatelyPositionName(String latelyPositionName) {
		this.latelyPositionName = latelyPositionName;
	}

	public String getLatelyPositionName() {
		return this.latelyPositionName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setLatelyJobEntryDate(java.util.Date latelyJobEntryDate) {
		this.latelyJobEntryDate = latelyJobEntryDate;
	}

	public java.util.Date getLatelyJobEntryDate() {
		return this.latelyJobEntryDate;
	}

	public void setSubscribe(String subscribe) {
		this.subscribe = subscribe;
	}

	public String getSubscribe() {
		return this.subscribe;
	}

	public void setUnionId(String unionId) {
		this.unionId = unionId;
	}

	public String getUnionId() {
		return this.unionId;
	}

	public void setSeatNumber(String seatNumber) {
		this.seatNumber = seatNumber;
	}

	public String getSeatNumber() {
		return this.seatNumber;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return this.email;
	}

}
