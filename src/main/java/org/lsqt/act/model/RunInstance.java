package org.lsqt.act.model;

import org.lsqt.act.ActUtil;

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
	
	/** 流程发起人账号 **/
	private String startLoginNo;
	
	/**发起人组织信息**/
	private String startUserOrgText;
	
	/**发起人岗位信息**/
	private String startUserPositionText;
	

	/** 业务数据主键ID */
	private String businessKey;
	
	/** 业务流水号**/
	private String businessFlowNo;

	/** */
	private String businessStatus;
	
	/** 业务状态中文表示 */
	private String businessStatusDesc;
	
	/**流程已结束=1 未结束=0**/
	private Integer endStatus; 

	/**用印公司**/
	private String companyNamePrint;
	
	/**用印公司ID**/
	private String companyIdPrint;
	
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

	private Integer suspended;  // 数据库：1=激活 2=挂起
	private Integer concurrent; //数据库存储： 1=并行 0=串行 
	private Integer active; //数据库存储：  1=激活 0=非激活  
	
	public static final int SUSPENDED_YES=2;
	public static final int SUSPENDED_NO=1;
	
	public static final int CONCURRENT_YES=1;
	public static final int CONCURRENT_NO=0;
	
	public static final int ACTIVE_YES=1;
	public static final int ACTIVE_NO=0;
	
	
	private String version;
	
	
	// --------------------------- 
	private String approveUserText; //活动节点的审批人,如：张三(zhang3),李四(li4)
	private String approveUserIds; // 活动节点的审批人ID (逗号分割)
	
	public String getEndStatusDesc() {
		if (endStatus != null) {
			if (Integer.valueOf(ActUtil.END_STATUS_已结束).intValue() == endStatus) {
				return "已结束";
			} else {
				return "未结束";
			}
		}
		return "";
	}
	
	public String getSuspendedDesc() {
		if (suspended != null) {
			if (SUSPENDED_YES == suspended) {
				return "挂起";
			} else {
				return "激活";
			}
		}
		return "";
	}

	public String getConcurrentDesc() {
		if (concurrent != null) {
			if (CONCURRENT_YES == concurrent) {
				return "(会签或其它)并发";
			} else {
				return "串行";
			}
		}
		return "";
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

	public String getApproveUserText() {
		return approveUserText;
	}

	public void setApproveUserText(String approveUserText) {
		this.approveUserText = approveUserText;
	}

	public String getStartLoginNo() {
		return startLoginNo;
	}

	public void setStartLoginNo(String startLoginNo) {
		this.startLoginNo = startLoginNo;
	}

	public String getBusinessFlowNo() {
		return businessFlowNo;
	}

	public void setBusinessFlowNo(String businessFlowNo) {
		this.businessFlowNo = businessFlowNo;
	}

	public Integer getEndStatus() {
		return endStatus;
	}

	public void setEndStatus(Integer endStatus) {
		this.endStatus = endStatus;
	}

	public Integer getSuspended() {
		return suspended;
	}

	public void setSuspended(Integer suspended) {
		this.suspended = suspended;
	}

	public Integer getConcurrent() {
		return concurrent;
	}

	public void setConcurrent(Integer concurrent) {
		this.concurrent = concurrent;
	}

	public Integer getActive() {
		return active;
	}

	public void setActive(Integer active) {
		this.active = active;
	}

	public String getCompanyNamePrint() {
		return companyNamePrint;
	}

	public void setCompanyNamePrint(String companyNamePrint) {
		this.companyNamePrint = companyNamePrint;
	}

	public String getCompanyIdPrint() {
		return companyIdPrint;
	}

	public void setCompanyIdPrint(String companyIdPrint) {
		this.companyIdPrint = companyIdPrint;
	}

	public String getApproveUserIds() {
		return approveUserIds;
	}

	public void setApproveUserIds(String approveUserIds) {
		this.approveUserIds = approveUserIds;
	}
	
}
