package org.lsqt.uum.model;

/**
 * 用户称谓表，如岗位、职位等
 */
public class Title implements java.io.Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8086691294470328728L;
	/***/
	private Long id;
	private Long pid;

	/** 名称 */
	private String name;
	private String pName;

	/** 称谓简称 */
	private String nameShort;

	/** 称谓编码 */
	private String code;

	private String nodePath;
	/**
	 * 状态 1=启用 0=禁用 -1=已删除
	 */
	private Integer status;

	/** 称谓类型: 1=职称 2=岗位 */
	private String type;
	public static final String TYPE_职称="1";
	public static final String TYPE_岗位="2";
	public String getTypeDesc() {
		if(TYPE_职称.equals(type)){
			return "职称";
		}
		if(TYPE_岗位.equals(type)){
			return "岗位";
		}
		return "";
	}
	
	/** 排序 */
	private Integer sn;

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
	
	/**查询某个用户的岗位、职称用的**/
	private Long userId;

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

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
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

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

}
