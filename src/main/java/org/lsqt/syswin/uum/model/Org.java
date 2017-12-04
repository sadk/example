package org.lsqt.syswin.uum.model;

/**
 * 组织_组织，组织的根节点：新增第一个节点的时候，需要创建虚拟节点为根节点 组织单元id=集团id，组织单元级
 */
public class Org {

	/** 组织单元id */

	private Long id;

	/** 父节点 */
	private Long pid;

	/** 组织名称 */
	private String name;
	
	private String pName;

	/** 组织单元全称 */
	private String fullName;

	/** 组织单元级别（1集团2区域公司3公司4管理项目5部门6工作组） */
	private Integer type;
	public static final int TYPE_集团=1;
	public static final int TYPE_区域公司=2;
	public static final int TYPE_公司 = 3;
	public static final int TYPE_管理项目 = 4;
	public static final int TYPE_部门 = 5;
	public static final int TYPE_工作组 = 6;

	public String getTypeDesc() {
		if(type == null) {
			return "";
		}
		if(TYPE_集团 == type) {
			return "集团";
		}
		if(TYPE_区域公司 == type) {
			return "区域公司";
		}
		if(TYPE_公司 == type) {
			return "公司";
		}
		if(TYPE_管理项目 == type) {
			return "管理项目";
		}
		if(TYPE_部门 == type) {
			return "部门";
		}
		if(TYPE_工作组 == type) {
			return "工作组";
		}
		return "";
	}
	
	/** 排序 */
	private Integer sn;

	/** 节点路径 */
	private String nodePath;
	/** 节点路径文本展示**/
	private String nodePathText;
	
	/** ets_id */
	private String etsId;

	/** ets_parentid */
	private String etsParentid;

	/** 创建者id */
	private Long createUserId;

	/** 创建者名称 */
	private String createUserName;

	/** 修改者id */
	private Long updateUserId;

	/** 修改者名称 */
	private String updateUserName;

	/** 修改时间 */
	private java.util.Date updateTime;
	
	/** 创建时间 */
	private java.util.Date createTime;
	
	/**单元属性: 1=填报单元 2=汇总单元**/
	private String propType;
	
	public Long getUpdateTimeMillis() {
		if (updateTime != null) {
			return updateTime.getTime();
		}
		return null;
	}
	
	public Long getCreateTimeMillis() {
		if (createTime != null) {
			return createTime.getTime();
		}
		return null;
	}

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

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getFullName() {
		return this.fullName;
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

	public void setEtsId(String etsId) {
		this.etsId = etsId;
	}

	public String getEtsId() {
		return this.etsId;
	}

	public void setEtsParentid(String etsParentid) {
		this.etsParentid = etsParentid;
	}

	public String getEtsParentid() {
		return this.etsParentid;
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

	public String getPropType() {
		return propType;
	}

	public void setPropType(String propType) {
		this.propType = propType;
	}

}
