package org.lsqt.syswin.authority.model;

/**
 * 权限_角色 1、角色类型(1独立2员工3岗位) 2、删除岗位，需要删除岗位相对应的角色，还有角色
 */
public class Role {

	/** 角色id */
	private Long id;

	/** 角色编码 */
	private String code;

	/** 角色分类id */
	private Long categoryId;
	private String categoryName;

	/** 角色类型(1独立2员工3岗位) */
	private Integer type;
	public static final int TYPE_ALONE_ROLE=1;
	public static final int TYPE_USER=2;
	public static final int TYPE_POSITION=3;

	public String getTypeDesc() {
		if (type == null)
			return "";
		if (TYPE_ALONE_ROLE == type) {
			return "独立角色";
		}
		if (TYPE_USER == type) {
			return "员工角色";
		}
		if (TYPE_POSITION == type) {
			return "岗位角色";
		}
		return "";
	}

	/** 角色名称 */
	private String name;

	/** 角色描述 */
	private String description;

	/** 创建者id */
	private Long createUserId;

	/** 创建者名称 */
	private String createUserName;

	/** 创建时间 */
	private java.util.Date createTime;

	/** 修改者id */
	private Long updateUserId;

	/** 修改者名称 */
	private String updateUserName;

	/** 修改时间 */
	private java.util.Date updateTime;

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

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setCreateUserId(Long createUserId) {
		this.createUserId = createUserId;
	}

	public Long getCreateUserId() {
		return this.createUserId;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getCreateUserName() {
		return this.createUserName;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setUpdateUserId(Long updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Long getUpdateUserId() {
		return this.updateUserId;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}

	public String getUpdateUserName() {
		return this.updateUserName;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

}
