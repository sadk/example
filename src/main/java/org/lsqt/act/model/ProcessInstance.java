package org.lsqt.act.model;

import java.util.HashMap;
import java.util.Map;

/**
 * 流程实例
 * @author mmyuan
 *
 */
public class ProcessInstance {
	
	public  static final String CANDIDATE_USER_IDS_KEY="candidateUserIds";
	public  static final String CANDIDATE_USER_NAMES_KEY="candidateUserNames";
	/**流程发起人**/
	private String startUserId;
	
	/** 流程实例扩展属性 **/
	private Map<String,Object> extProperty = new HashMap<>();
	
	
	/** 流程标题  */
	private String title;
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
	
	private String taskName;
	
	/**
	   * The unique identifier of the execution.
	   */
	  String id;
	  
	  /**
	   * Indicates if the execution is suspended.
	   */
	  boolean isSuspended;
	  
	  /**
	   * Indicates if the execution is ended.
	   */
	  boolean isEnded;
	  
	  /**
	   * Returns the id of the activity where the execution currently is at.
	   * Returns null if the execution is not a 'leaf' execution (eg concurrent parent).
	   */
	  String activityId;
	  
	  /** Id of the root of the execution tree representing the process instance.
	   * It is the same as {@link #getId()} if this execution is the process instance. */ 
	  String processInstanceId;
	  
	  /**
	   * Gets the id of the parent of this execution. If null, the execution represents a process-instance.
	   */
	  String parentId;
	  
	  /** 
	   * The tenant identifier of this process instance 
	   */
	  String tenantId;
	  
	  
	  
	  //  ------------------------------------------------------------
	  /**
	   * The id of the process definition of the process instance.
	   */
	  String processDefinitionId;
	  
	  /**
	   * The name of the process definition of the process instance.
	   */
	  String processDefinitionName;
	  
	  /**
	   * The key of the process definition of the process instance.
	   */
	  String processDefinitionKey;
	  
	  /**
	   * The version of the process definition of the process instance.
	   */
	  Integer processDefinitionVersion;
	  
	  /**
	   * The deployment id of the process definition of the process instance.
	   */
	  String deploymentId;
	  
	  /**
	   * The business key of this process instance.
	   */
	  String businessKey;
	  

	  
	  /** 
	   * Returns the process variables if requested in the process instance query 
	   */
	  Map<String, Object> processVariables;
	  
	  
	  /**
	   * Returns the name of this process instance. 
	   */
	  String name;


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public boolean getIsSuspended() {
		return isSuspended;
	}


	public void setIsSuspended(boolean isSuspended) {
		this.isSuspended = isSuspended;
	}


	public boolean getIsEnded() {
		return isEnded;
	}


	public void setIsEnded(boolean isEnded) {
		this.isEnded = isEnded;
	}


	public String getActivityId() {
		return activityId;
	}


	public void setActivityId(String activityId) {
		this.activityId = activityId;
	}


	public String getProcessInstanceId() {
		return processInstanceId;
	}


	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getTenantId() {
		return tenantId;
	}


	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}


	public String getProcessDefinitionId() {
		return processDefinitionId;
	}


	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}


	public String getProcessDefinitionName() {
		return processDefinitionName;
	}


	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}


	public String getProcessDefinitionKey() {
		return processDefinitionKey;
	}


	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}


	public Integer getProcessDefinitionVersion() {
		return processDefinitionVersion;
	}


	public void setProcessDefinitionVersion(Integer processDefinitionVersion) {
		this.processDefinitionVersion = processDefinitionVersion;
	}


	public String getDeploymentId() {
		return deploymentId;
	}


	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}


	public String getBusinessKey() {
		return businessKey;
	}


	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}


	public Map<String, Object> getProcessVariables() {
		return processVariables;
	}


	public void setProcessVariables(Map<String, Object> processVariables) {
		this.processVariables = processVariables;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Map<String, Object> getExtProperty() {
		return extProperty;
	}

	public void setExtProperty(Map<String, Object> extProperty) {
		this.extProperty = extProperty;
	}

	public String getStartUserId() {
		return startUserId;
	}

	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}
}
