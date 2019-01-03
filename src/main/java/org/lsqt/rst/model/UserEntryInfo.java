package org.lsqt.rst.model;

import java.util.Date;

import org.lsqt.components.context.annotation.model.Pattern;

/**
 * 入职用户状态变化表
 */
public class UserEntryInfo {

	/***/

	private Long id;

	/** 用户ID */
	private String userCode;

	/** 真实姓名 */
	private String userName;

	/** 手机号 */
	private String phone;

	/**
	 * 入职状态: 100=待入职    120=身份证识别状态，140=人脸识别状态  160=银行卡是被状态  180=入职信息完善  300=待人工审核  400=已入职  500=审核失败 600=已离职
	 */
	private Integer entryStatus;
	public static int ENTRY_STATUS_待入职=100;
	public static int ENTRY_STATUS_已入职=530; //400改为530, by:2019-01-02
	public static int ENTRY_STATUS_已离职=600;
	
	public static int ENTRY_STATUS_身份证识别状态_成功=120;
	public static int ENTRY_STATUS_身份证识别状态_失败=121;
	
	public static int ENTRY_STATUS_人脸识别状态_成功=140;
	public static int ENTRY_STATUS_人脸识别状态_失败=141;
	
	public static int ENTRY_STATUS_银行卡识别_成功=160;
	public static int ENTRY_STATUS_银行卡识别_失败=161;
	
	public static int ENTRY_STATUS_待人工审核=300; // 三步流程是：身份证扫描-》人脸识别-》银行卡号扫描
	public static int ENTRY_STATUS_入职失败=500; //后台审核都失败
	
	public static int ENTRY_STATUS_入职信息完善中=180;
 
 
	
	public static int ENTRY_STATUS_入职信息完善=180;
 
	
	

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

	@Pattern("yyyy-MM-dd")
	private Date entryTime;
	private Date leaveTime;
	
	// --------------------虚拟字段 
	private String sex;
	private String idCard; //身份证号码
	private String bankCard; //银行卡号
	private String bankCardName; //银行卡号的银行名称
	private String contactor; // 紧急联系人
	private String contactorPhone; // 紧急联系人电话
	private Date birthday;
	private String nation; //户籍地址
	private String idCardUrl0; //身份证正面图片
	private String idCardUrl1; // 身份证图片反面
	private String faceUrl; // 人脸识别图片地址
	private String bankUrl; // 银行卡号地图片地址
	
	
	
	
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
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

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getPhone() {
		return this.phone;
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

	public Integer getEntryStatus() {
		return entryStatus;
	}

	public void setEntryStatus(Integer entryStatus) {
		this.entryStatus = entryStatus;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public Date getLeaveTime() {
		return leaveTime;
	}

	public void setLeaveTime(Date leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	public String getBankCard() {
		return bankCard;
	}

	public void setBankCard(String bankCard) {
		this.bankCard = bankCard;
	}

	public String getBankCardName() {
		return bankCardName;
	}

	public void setBankCardName(String bankCardName) {
		this.bankCardName = bankCardName;
	}

	public String getContactor() {
		return contactor;
	}

	public void setContactor(String contactor) {
		this.contactor = contactor;
	}

	public String getContactorPhone() {
		return contactorPhone;
	}

	public void setContactorPhone(String contactorPhone) {
		this.contactorPhone = contactorPhone;
	}

	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public String getNation() {
		return nation;
	}

	public void setNation(String nation) {
		this.nation = nation;
	}

	public String getFaceUrl() {
		return faceUrl;
	}

	public void setFaceUrl(String faceUrl) {
		this.faceUrl = faceUrl;
	}

	public String getBankUrl() {
		return bankUrl;
	}

	public void setBankUrl(String bankUrl) {
		this.bankUrl = bankUrl;
	}

	public String getIdCardUrl0() {
		return idCardUrl0;
	}

	public void setIdCardUrl0(String idCardUrl0) {
		this.idCardUrl0 = idCardUrl0;
	}

	public String getIdCardUrl1() {
		return idCardUrl1;
	}

	public void setIdCardUrl1(String idCardUrl1) {
		this.idCardUrl1 = idCardUrl1;
	}

}
