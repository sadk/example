package org.lsqt.yue.model;

/**
 * 技能信息,如家教里的培训课程、服务方式等
 */
public class TechInfo {

	private Long id;

	/** 引用技能表ID */
	private Long techId;

	/** 如：培训课程 */
	private String name;

	private String code;

	private String remark;
	private String appCode;
	private String gid;

	/** 创建日期 */
	private java.util.Date createTime;
	private java.util.Date updateTime;

	
	
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setTechId(Long techId) {
		this.techId = techId;
	}

	public Long getTechId() {
		return this.techId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
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
