package org.lsqt.act.model;

/**
 * 流程实例扩展
 */
public class RunInstance {

	/***/
	private Long id;

	/** 流程实例ID */
	private String instanceId;

	/** 流程标题 */
	private String title;

	/** 流程定义ID */
	private String processDefinitionId;

	/** 流程定义key */
	private String processDefinitionKey;

	/** 流程定义名称 */
	private String processDefinitionName;

	/** 填制人部门 **/
	private String createDeptId;
	
	/** 流程发起人ID */
	private String startUserId;

	/** 流程发起人名称 */
	private String startUserName;
	
	/**发起人组织信息**/
	private String startUserOrgText;
	
	/**发起人岗位信息**/
	private String startUserPositionText;
	

	/** 业务数据主键ID */
	private String businessKey;

	/** 业务自定义的状态,如 待发起=0 待审批=1 审批中=3 审批通过=2 未通过=-1 已完成=4 */
	private String businessStatus;
	
	/** 业务状态中文表示 */
	private String businessStatusDesc;
	
	

	/** 业务自定义的分类 */
	private String businessCategory;

	/** 租户编码 */
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

	//---------------------------- activiti 属性
	private boolean isSuspended;
	private boolean isConcurrent;
	private boolean isActive;
	private String version;
	
	public String getIsEndedDesc() {
		if(isSuspended) {
			return "已结束";
		}else {
			return "未结束";
		}
	}
	
	public String getIsSuspendedDesc() {
		if(isSuspended) {
			return "已挂起";
		}else {
			return "未挂起";
		}
		
	}
	
	
	private String taskName; // 当前活动节点
	private String taskKey;
	
	private String createDeptName; // 填制人部门名称 
	
	private String variableJSONInit;
	private String variableJSONStarting;
	
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	public String getInstanceId() {
		return this.instanceId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}


	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getStartUserId() {
		return this.startUserId;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}

	public String getStartUserName() {
		return this.startUserName;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public String getBusinessStatus() {
		return this.businessStatus;
	}

	public void setBusinessStatusDesc(String businessStatusDesc) {
		this.businessStatusDesc = businessStatusDesc;
	}

	public String getBusinessStatusDesc() {
		return this.businessStatusDesc;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

	public String getBusinessCategory() {
		return this.businessCategory;
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

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getStartUserOrgText() {
		return startUserOrgText;
	}

	public void setStartUserOrgText(String startUserOrgText) {
		this.startUserOrgText = startUserOrgText;
	}

	public String getStartUserPositionText() {
		return startUserPositionText;
	}

	public void setStartUserPositionText(String startUserPositionText) {
		this.startUserPositionText = startUserPositionText;
	}

	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionName() {
		return processDefinitionName;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public String getCreateDeptId() {
		return createDeptId;
	}

	public void setCreateDeptId(String createDeptId) {
		this.createDeptId = createDeptId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public boolean getIsSuspended() {
		return isSuspended;
	}

	public void setIsSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}
	
	
	public boolean getIsConcurrent() {
		return isConcurrent;
	}

	public void setIsConcurrent(boolean isConcurrent) {
		this.isConcurrent = isConcurrent;
	}

	public boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(boolean isActive) {
		this.isActive = isActive;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreateDeptName() {
		return createDeptName;
	}

	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}

	public String getVariableJSONInit() {
		return variableJSONInit;
	}

	public void setVariableJSONInit(String variableJSONInit) {
		this.variableJSONInit = variableJSONInit;
	}

	public String getVariableJSONStarting() {
		return variableJSONStarting;
	}

	public void setVariableJSONStarting(String variableJSONStarting) {
		this.variableJSONStarting = variableJSONStarting;
	}
	
}
