package org.lsqt.rst.model;

/**
 * 个人视频表
 */
public class PersonalVideoInfo {

	private Long id;
	
	/** 视频ID */
	private String code;

	/** 用户ID */
	private String userCode;

	/** 活动ID */
	private String activityId;

	/** 图片Url */
	private String pictureUrl;

	/** 视频ID */
	private String videoUrl;

	/** 视频状态（0:隐藏， 1展示） */
	private String status;

	/** 上传时间 */

	private java.util.Date uploadTime;

	/** 更新时间 */

	private java.util.Date reviewTime;

	/** 职位ID */
	private String positionCode;

	/** 审核原因 */
	private String reason;

	/** 租户编码 */
	private String tenantCode;
	
	
	// ----------- 虚拟列 
	private String nickName;
	private String realName;
	private String phone;

	// getter、setter
	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public String getUserCode() {
		return this.userCode;
	}

	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}

	public String getActivityId() {
		return this.activityId;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getPictureUrl() {
		return this.pictureUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public String getVideoUrl() {
		return this.videoUrl;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setUploadTime(java.util.Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public java.util.Date getUploadTime() {
		return this.uploadTime;
	}

	public void setReviewTime(java.util.Date reviewTime) {
		this.reviewTime = reviewTime;
	}

	public java.util.Date getReviewTime() {
		return this.reviewTime;
	}

	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}

	public String getPositionCode() {
		return this.positionCode;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return this.reason;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTenantCode() {
		return this.tenantCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
