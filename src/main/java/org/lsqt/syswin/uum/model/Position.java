package org.lsqt.syswin.uum.model;

/**
 * 组织_岗位信息表
 */
public class Position {

	/** 岗位id */
	private Long id;

	/** 岗位名称 */
	private String name;
	
	/**岗位编码**/
	private String code;

	/** 岗位职责 */
	private String remark;

	/** 直属上级 */
	private Long pid;

	/** 直属上级名称 */
	private String parentName;
	
	/**人力系统岗位UUID**/
	private String uuidHR;
	
	/**岗位节点路径**/
	private String nodePath;
	

	/** 岗位级别（1集团2区域公司3公司4管理项目5部门6工作组） */
	private Integer type;
	public static final int TYPE_集团=1;
	public static final int TYPE_区域公司=2;
	public static final int TYPE_公司 = 3;
	public static final int TYPE_管理项目 = 4;
	public static final int TYPE_部门 = 5;
	public static final int TYPE_工作组 = 6;

	/** 主岗和次岗位**/
	public static final int POSITION_MAIN=1;
	public static final int POSTION_SECOND=2;
	
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
	
	
	/** 当前岗位所属的组织 */
	private Long orgId;
	

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

	
	// --------------------------------- 辅助属性 ------------------------------
	private String orgNodeText;
	private String mainPositionDesc;//是否是“主岗”、“兼岗”
	
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

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getType() {
		return this.type;
	}

	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}

	public Long getOrgId() {
		return this.orgId;
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

	public String getOrgNodeText() {
		return orgNodeText;
	}

	public void setOrgNodeText(String orgNodeText) {
		this.orgNodeText = orgNodeText;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getUuidHR() {
		return uuidHR;
	}

	public void setUuidHR(String uuidHR) {
		this.uuidHR = uuidHR;
	}

	public String getMainPositionDesc() {
		return mainPositionDesc;
	}

	public void setMainPositionDesc(String mainPositionDesc) {
		this.mainPositionDesc = mainPositionDesc;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

	public String getNodePath() {
		return nodePath;
	}

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

}
