package org.lsqt.act.model;

/**
 * 节点审批人设置
 */
public class NodeUser {

	/***/
	private Long id;

	/** 流程定义ID */
	private String definitionId;

	/** 流程定义名称 */
	private String definitionName;

	/** 节点定义Key */
	private String taskKey;

	/** 审批对象变量编码 (任务候选人编码)**/
	private String approveObjectVariableCode;
	
	/**审批对象Id**/
	private String approveObjectId;
	
	/**审批对象JSON值**/
	private String approveObjectJson ;
	
	
	/** 名称 */
	private String name;

	/**
	 * 结点用户类型:  1=职称 2=岗位 3=组织 5=用户 7=角色 4=用户组(与流程引擎的group不是一个概念，主要是第三方系统的UUM)； 
	 * 100=用户规则（角本计算）
	 * (101=java角本计算 102=groovy角本计算 103=javascript角本计算(注：角本计算必须返回Collection<String>集合)
	 *  
	 */
	private Integer userType;
	public static final int USER_TYPE_ORG = 3;
	public static final int USER_TYPE_POSITION = 2;
	public static final int USER_TYPE_USER = 5;
	public static final int USER_TYPE_ROLE = 7;
	public static final int USER_TYPE_GROUP = 4;
	public static final int USER_TYPE_SCRIPT = 100; //用户规则（角本计算）
	
	public String getUserTypeDesc() {
		if(userType == null) return "";
		if(USER_TYPE_ORG == userType) {
			return "组织架构";
		}
		if(USER_TYPE_POSITION == userType) {
			return "岗位";
		}
		if(USER_TYPE_USER == userType) {
			return "用户";
		}
		if(USER_TYPE_ROLE == userType) {
			return "角色";
		}
		if(USER_TYPE_SCRIPT == userType) {
			return "用户规则";
		}
		return "";
	}
	
	public static String getUserTypeDesc(Integer userType) {
		NodeUser temp = new NodeUser();
		temp.setUserType(userType);
		return temp.getUserTypeDesc();
	}

	/** 用户数据来源：1=内部 2=外部（如http-json数据） */
	private Integer userFromType;
	public static final int USER_FROM_TYPE_INTERNAL=1;
	public static final int USER_FROM_TYPE_REMOTE_HTTP_JSON=2;
	
	/***/
	private String appCode;

	/** 排序 */
	private Integer sn;

	/** 备注 */
	private String remark;

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

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionId() {
		return this.definitionId;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public String getDefinitionName() {
		return this.definitionName;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setUserType(Integer userType) {
		this.userType = userType;
	}

	public Integer getUserType() {
		return this.userType;
	}

	public void setUserFromType(Integer userFromType) {
		this.userFromType = userFromType;
	}

	public Integer getUserFromType() {
		return this.userFromType;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
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

	public String getApproveObjectId() {
		return approveObjectId;
	}

	public void setApproveObjectId(String approveObjectId) {
		this.approveObjectId = approveObjectId;
	}

	public String getApproveObjectJson() {
		return approveObjectJson;
	}

	public void setApproveObjectJson(String approveObjectJson) {
		this.approveObjectJson = approveObjectJson;
	}

	public String getApproveObjectVariableCode() {
		return approveObjectVariableCode;
	}

	public void setApproveObjectVariableCode(String approveObjectVariableCode) {
		this.approveObjectVariableCode = approveObjectVariableCode;
	}

}
