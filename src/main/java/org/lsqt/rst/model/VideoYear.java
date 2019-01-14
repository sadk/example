package org.lsqt.rst.model;

/**
 * 年会节目单视频信息表
 */
public class VideoYear {

	/** 视频编码 */

	private Long id;

	/** 视频创作者姓名 */
	private String name;

	/** 部门名称 */
	private String departmentName;

	/** 封面图片网络地址 */
	private String coverUrl;

	/** 视频网络地址 */
	private String videoUrl;

	/** 文件上传日期 */
	private java.util.Date uploadTime;

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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}

	public String getDepartmentName() {
		return this.departmentName;
	}

	public void setCoverUrl(String coverUrl) {
		this.coverUrl = coverUrl;
	}

	public String getCoverUrl() {
		return this.coverUrl;
	}

	public void setUploadTime(java.util.Date uploadTime) {
		this.uploadTime = uploadTime;
	}

	public java.util.Date getUploadTime() {
		return this.uploadTime;
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

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

}
