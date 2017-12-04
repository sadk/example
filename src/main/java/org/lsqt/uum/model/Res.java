package org.lsqt.uum.model;

/**
 * 资源表
 */
public class Res {
	public static final int type_菜单 = 100;
	public static final int type_页面元素 = 200;
	public static final int type_数据查询权限 = 300;
 

	private Long id;

	/** 上级ID */
	private Long pid;
	
	private String pName;

	/** 名称 */
	private String name;

	/** 编码 */
	private String code;

	/**资源值**/
	private String value;
	
	/** 资源类型：1=菜单 2=页面元素  3=数据查询权限*/
	private Integer type;

	/** 排序 */
	private Integer sn;

	/***/
	private String nodePath;

	private String nodePathText;
	
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

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getPid() {
		return this.pid;
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

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return this.sn;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return this.nodePath;
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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getpName() {
		return pName;
	}

	public void setpName(String pName) {
		this.pName = pName;
	}

	public String getNodePathText() {
		return nodePathText;
	}

	public void setNodePathText(String nodePathText) {
		this.nodePathText = nodePathText;
	}

	public String getTypeDesc() {
		if (type == null)
			return "";
		if (type_菜单 == type) {
			return "菜单";
		}
		if (type_页面元素 == type) {
			return "页面元素";
		}
		if (type_数据查询权限 == type) {
			return "数据查询权限";
		}
		return "";
	}
}
