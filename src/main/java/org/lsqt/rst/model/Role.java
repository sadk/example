package org.lsqt.rst.model;

/**
 * 用户角色表
 */
public class Role {
	private Long id;
	
	/** 角色ID */
	private String code;

	/** 角色名称 */
	private String name;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 创建人 */
	private String createUser;

	private String remark;
	
	
	// getter、setter

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}

	public String getCreateUser() {
		return this.createUser;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
