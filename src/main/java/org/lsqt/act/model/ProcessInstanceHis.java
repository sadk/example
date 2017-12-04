package org.lsqt.act.model;

import java.util.Date;
import java.util.Map;

/**
 * 已结束的流程实例
 * @author mmyuan
 *
 */
public class ProcessInstanceHis {
	/** 流程标题  */
	private String title;
	private String processInstanceId; // 流程实例ID（等于主键）
	private String processDefinitionKey;
	private String processDefinitionName;
	private String version;
	private String createDeptId; // 填制人部门id
	
	private String startUserName;
	private String startUserOrgText;
	private String startUserPositionText;
	private String businessStatus; //业务自定义的状态,如自定义审批通过、未通过、正常、异常等(内置的状态是1=审批中 2=审批通过  3=审批未通过)
	private String businessStatusDesc;
	private Integer sn;
	private String remark;
	private String gid;
	private String endAcitivityId;
	private String businessCategory;
	
	//private String taskName;
	
	
	  /** The process instance id (== as the id for the runtime {@link ProcessInstance process instance}). */
	  String id;
	  
	  /** The user provided unique reference to this process instance. */
	  String businessKey;

	  /** The process definition reference. */
	  String processDefinitionId;

	  /** The time the process was started. */
	  Date startTime;

	  /** The time the process was ended. */
	  Date endTime;

	  /** The difference between {@link #getEndTime()} and {@link #getStartTime()} . */
	  Long durationInMillis;


	  
	  /** The authenticated user that started this process instance. 
	   * @see IdentityService#setAuthenticatedUserId(String) */
	  String startUserId;
	  
	  /** The start activity. */
	  String startActivityId;


	  
	  /** Obtains the reason for the process instance's deletion. */
	  String deleteReason;
	  
	  /**
	   * The process instance id of a potential super process instance or null if no super process instance exists
	   */
	  String superProcessInstanceId;
	  
	  /**
	   * The tenant identifier for the process instance.
	   */
	  String tenantId;
	  
	  /**
	   * The name for the process instance.
	   */
	  String name;
	  
	  /** Returns the process variables if requested in the process instance query */
	  Map<String, Object> processVariables;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getProcessDefinitionId() {
		return processDefinitionId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public Date getStartTime() {
		return startTime;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Long getDurationInMillis() {
		return durationInMillis;
	}

	public void setDurationInMillis(Long durationInMillis) {
		this.durationInMillis = durationInMillis;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}

	public String getStartActivityId() {
		return startActivityId;
	}

	public void setStartActivityId(String startActivityId) {
		this.startActivityId = startActivityId;
	}

	public String getDeleteReason() {
		return deleteReason;
	}

	public void setDeleteReason(String deleteReason) {
		this.deleteReason = deleteReason;
	}

	public String getSuperProcessInstanceId() {
		return superProcessInstanceId;
	}

	public void setSuperProcessInstanceId(String superProcessInstanceId) {
		this.superProcessInstanceId = superProcessInstanceId;
	}

	public String getTenantId() {
		return tenantId;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Map<String, Object> getProcessVariables() {
		return processVariables;
	}

	public void setProcessVariables(Map<String, Object> processVariables) {
		this.processVariables = processVariables;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCreateDeptId() {
		return createDeptId;
	}

	public void setCreateDeptId(String createDeptId) {
		this.createDeptId = createDeptId;
	}

	public String getStartUserName() {
		return startUserName;
	}

	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
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

	public String getBusinessStatus() {
		return businessStatus;
	}

	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getBusinessStatusDesc() {
		return businessStatusDesc;
	}

	public void setBusinessStatusDesc(String businessStatusDesc) {
		this.businessStatusDesc = businessStatusDesc;
	}

	public String getEndAcitivityId() {
		return endAcitivityId;
	}

	public void setEndAcitivityId(String endAcitivityId) {
		this.endAcitivityId = endAcitivityId;
	}

	public String getBusinessCategory() {
		return businessCategory;
	}

	public void setBusinessCategory(String businessCategory) {
		this.businessCategory = businessCategory;
	}

/*	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}*/
}
