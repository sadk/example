package org.lsqt.chk.model;

/**
 * 用户表刑事案底核查表(北京优分数据科技接口)
 */
public class UserCrime {

	/***/
	private Long id;

	/** 引用系统用户id */
	private Long userId;

	/** 用户姓名 */
	private String name;
	
	/** 身份证号 */
	private String idcard;
	
	private String sex; // 性别
	private String policeAddress;// 签 证机关
	private String photo; // 头像
	 
	private String batchNo ; // 导入时生成的批次号
	private String matchResCode ; // 系统响应码
	private String matchResDesc ; // 系统响应说明
	private String matchBizCode ; // 匹配业务码
	private String matchBizDesc ; // 匹配业务说明
	
 
	private String resCode; // 系统响应码
	private String resMsg; // "犯罪"响应码说明|业务说明

	

	/** 排序号 */
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

	/**用户编码*/
	private String code;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getUserId() {
		return this.userId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setIdcard(String idcard) {
		this.idcard = idcard;
	}

	public String getIdcard() {
		return this.idcard;
	}
	
	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResCode() {
		return this.resCode;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getResMsg() {
		return this.resMsg;
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

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getMatchResCode() {
		return matchResCode;
	}

	public void setMatchResCode(String matchResCode) {
		this.matchResCode = matchResCode;
	}

	public String getMatchResDesc() {
		return matchResDesc;
	}

	public void setMatchResDesc(String matchResDesc) {
		this.matchResDesc = matchResDesc;
	}

	public String getMatchBizCode() {
		return matchBizCode;
	}

	public void setMatchBizCode(String matchBizCode) {
		this.matchBizCode = matchBizCode;
	}

	public String getMatchBizDesc() {
		return matchBizDesc;
	}

	public void setMatchBizDesc(String matchBizDesc) {
		this.matchBizDesc = matchBizDesc;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPoliceAddress() {
		return policeAddress;
	}

	public void setPoliceAddress(String policeAddress) {
		this.policeAddress = policeAddress;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

}
