package org.lsqt.rst.model;

import org.lsqt.components.db.Page;

/**
 * 用户求职记录
 */
public class UserJobRecordQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	
	private String id;
	
	/** 用户编码 */
	private String userCode;

	/** 求职者名称 */
	private String userName;

	/** 求职者手机号 */
	private String userMobile;

	/** 求职者微信号 */
	private String userWechat;

	/** 视频编码 */
	private String videoCode;

	/** 职位编码 */
	private String positionCode;

	/** 职位发布者ID */
	private String positionPublishUserCode;

	/** 职位名称 */
	private String positionName;

	/** 工资 */
	private String salary;

	/** 基本福利 */
	private String welfare;

	/** 公司名称 */
	private String companyName;

	/** 面试地点 */
	private String interviewPlace;

	/** 面试时间 */
	private String interviewTime;

	/** 面试联系人名称 */
	private String interviewContactName;

	/** 面试联系人手机号 */
	private String interviewContactMobile;

	/** 最后一次跟进的编码(待删除) */
	private String lastFollowId;

	/** 状态(待面试，未面试，面试通过，面试失败) */
	private String status;

	/** 投递平台 */
	private String platfrom;


	private String tenantCode;
	
	public String getTenantCode() {
		return tenantCode;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
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

	public void setUserMobile(String userMobile) {
		this.userMobile = userMobile;
	}

	public String getUserMobile() {
		return this.userMobile;
	}

	public void setUserWechat(String userWechat) {
		this.userWechat = userWechat;
	}

	public String getUserWechat() {
		return this.userWechat;
	}

	public void setVideoCode(String videoCode) {
		this.videoCode = videoCode;
	}

	public String getVideoCode() {
		return this.videoCode;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionCode() {
		return this.positionCode;
	}

	public void setPositionPublishUserCode(String positionPublishUserCode) {
		this.positionPublishUserCode = positionPublishUserCode;
	}

	public String getPositionPublishUserCode() {
		return this.positionPublishUserCode;
	}

	public void setPositionName(String positionName) {
		this.positionName = positionName;
	}

	public String getPositionName() {
		return this.positionName;
	}

	public void setSalary(String salary) {
		this.salary = salary;
	}

	public String getSalary() {
		return this.salary;
	}

	public void setWelfare(String welfare) {
		this.welfare = welfare;
	}

	public String getWelfare() {
		return this.welfare;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCompanyName() {
		return this.companyName;
	}

	public void setInterviewPlace(String interviewPlace) {
		this.interviewPlace = interviewPlace;
	}

	public String getInterviewPlace() {
		return this.interviewPlace;
	}

	public void setInterviewTime(String interviewTime) {
		this.interviewTime = interviewTime;
	}

	public String getInterviewTime() {
		return this.interviewTime;
	}

	public void setInterviewContactName(String interviewContactName) {
		this.interviewContactName = interviewContactName;
	}

	public String getInterviewContactName() {
		return this.interviewContactName;
	}

	public void setInterviewContactMobile(String interviewContactMobile) {
		this.interviewContactMobile = interviewContactMobile;
	}

	public String getInterviewContactMobile() {
		return this.interviewContactMobile;
	}

	public void setLastFollowId(String lastFollowId) {
		this.lastFollowId = lastFollowId;
	}

	public String getLastFollowId() {
		return this.lastFollowId;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setPlatfrom(String platfrom) {
		this.platfrom = platfrom;
	}

	public String getPlatfrom() {
		return this.platfrom;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
