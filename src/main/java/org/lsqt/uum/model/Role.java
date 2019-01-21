package org.lsqt.uum.model;


/**
 * 角色表
 */
public class Role implements java.io.Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5277982119437928537L;
	/**授权给不同实体(uum_role_object表)，如:用户、部门、组、岗位、职称**/
	public static final String OBJ_TYPE_职称="1";
	public static final String OBJ_TYPE_岗位="2";
	
	public static final String OBJ_TYPE_部门="3";
	public static final String OBJ_TYPE_组="4";
	public static final String OBJ_TYPE_用户="5";
	public static final String OBJ_TYPE_资源="6";
	public static final String OBJ_TYPE_角色="7";
	// 租户 code=0 
	
	/** 角色ID */
	private Long id;

	/** 名称 */
	private String name;

	/** 角色简称 */
	private String nameShort;

	/** 角色编码 */
	private String code;

	/** 排序 */
	private Integer sn;
	
	/**状态：UUM统一用户状态 1=启用 0=禁用 -1=已删除**/
	private Integer status;
	
	public String getStatusDesc() {
		if (status != null && status == 1) {
			return "启用";
		}
		if (status != null && status == 0) {
			return "启用";
		}
		if (status != null && status == -1) {
			return "已删除";
		}
		return "";
	}

	/** 备注 */
	private String remark;

	/***/
	private String appCode;
	
	/***/
	private String gid;

	/** 创建日期 */
	private java.util.Date createTime;

	/***/

	private java.util.Date updateTime;

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

	public void setNameShort(String nameShort) {
		this.nameShort = nameShort;
	}

	public String getNameShort() {
		return this.nameShort;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
