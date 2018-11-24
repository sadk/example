package org.lsqt.rst.model;

/**
 * 岗位信息表
 */
public class JobDefinition {

	/***/
	private Long id;

	/** 岗位ID */
	private String code;

	/** 岗位名称 */
	private String name;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 创建人 */
	private String createUser;

	/** 更新时间 */
	private java.util.Date updateTime;

	/** 更新人 */
	private String updateUser;

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

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

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

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateUser() {
		return this.updateUser;
	}

}
