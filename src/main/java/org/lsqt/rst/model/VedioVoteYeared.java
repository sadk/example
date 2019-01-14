package org.lsqt.rst.model;

/**
 * 年会节目单投票表
 */
public class VedioVoteYeared {

	/***/

	private Long id;

	/** 用户编码 */
	private String userId;
	
	private String userName;

	/** 视频编码 */
	private Integer videoId;

	/** 投票时间 */
	private java.util.Date voteTime;

	/** 0:取消投票； 1:投票 */
	private String status;

	/** 创建日期 */

	private java.util.Date createDate;

	/** 更新日期 */

	private java.util.Date updateDate;

	/** 租户编码 */
	private String tenantCode;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setVoteTime(java.util.Date voteTime) {
		this.voteTime = voteTime;
	}

	public java.util.Date getVoteTime() {
		return this.voteTime;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatus() {
		return this.status;
	}

	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}

	public java.util.Date getCreateDate() {
		return this.createDate;
	}

	public void setUpdateDate(java.util.Date updateDate) {
		this.updateDate = updateDate;
	}

	public java.util.Date getUpdateDate() {
		return this.updateDate;
	}

	public void setTenantCode(String tenantCode) {
		this.tenantCode = tenantCode;
	}

	public String getTenantCode() {
		return this.tenantCode;
	}

	public Integer getVideoId() {
		return videoId;
	}

	public void setVideoId(Integer videoId) {
		this.videoId = videoId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

}
