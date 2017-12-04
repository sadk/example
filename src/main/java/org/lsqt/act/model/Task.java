package org.lsqt.act.model;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 流程任务相关
 * 见表：act_ru_task
 * @author mmyuan
 *
 */
public class Task {
	
	private String id;
	private String rev; // 暂不清楚这个是干啥用的
	private String name;
	
	private String assignee;
	private String delegation; // 代办人Id或loginNo，取决于业务存什么
	
	private String category;
	private Date createTime;
	private String description;
	private Date dueDate;
	private String executionId;
	private String formKey; // 一般为外部表单的url值
	
	private String owner;
	private String parentTaskId;
	private Integer priority; // 任务优先级别
	private Integer suspensionState; // 1=未挂起
	
	private String processDefinitionId;

	
	
	private String processInstanceId;
	private String taskDefinitionKey;
	private String tenantId;
	
	
	// -------------------------------------------------------------- 辅助字段  ----------------------------
	// 当前任务对应的流程实例关联的业务主键 !!!
	private String businessKey;
	
	private String processDefinitionName; // 辅助字段
	private String processDefinitionKey; // 辅助字段
	
	private String startUserId;  // 流程发起人
	private String startUserName; 
	private String startUserOrgText;
	private String startUserPositionText;
	
	private String title; // 流程标题
	
	
	private String assignForwardCcAction; //加签、转发、抄送的动作编码
	private String assignForwardCcUserIds; //加签、转发、抄送给哪些用户
	private String assignForwardCcOpertaterUserId ; //是谁做的加签、转发、抄送动作
	
	
	private String formUrlViewGlobal; 
	private String formUrlEidtGlobal;
	private String formUrlView;
	private String formUrlEdit;
	private String formUrlCustom; 
	
	private String candidateUserIds ; // 当前任务可以审批的用户ID

	private Map<String,Object> extProperty=new HashMap<>(); // 手机端显示金额啥的，全放这里！！！
	
	
	private String businessStatus;   // 见 ActUtil.businessStatus
	private String businessStatusDesc;
	
	/**
	 * 手工克隆任务对象
	 * @param task
	 * @return
	 */
	public static Task getCloneObject(Task task) { 
		if(task==null) return null;
		Task t= new Task();
		t.setAssignee(task.getAssignee());
		t.setAssignForwardCcAction(task.getAssignForwardCcAction());
		t.setAssignForwardCcOpertaterUserId(task.getAssignForwardCcOpertaterUserId());
		t.setAssignForwardCcUserIds(task.getAssignForwardCcUserIds());
		t.setBusinessKey(task.getBusinessKey());
		t.setBusinessStatus(task.getBusinessStatus());
		t.setBusinessStatusDesc(task.getBusinessStatusDesc());
		t.setCandidateUserIds(task.getCandidateUserIds());
		t.setCategory(task.getCategory());
		t.setCreateTime(task.getCreateTime());
		t.setDelegation(task.getDelegation());
		t.setDescription(task.getDescription());
		t.setDueDate(task.getDueDate());
		t.setExecutionId(task.getExecutionId());
		t.setExtProperty(task.getExtProperty());
		t.setFormKey(task.getFormKey());
		t.setFormUrlCustom(task.getFormUrlCustom());
		t.setFormUrlEdit(task.getFormUrlEdit());
		t.setFormUrlEidtGlobal(task.getFormUrlEidtGlobal());
		t.setFormUrlView(task.getFormUrlView());
		t.setFormUrlViewGlobal(task.getFormUrlViewGlobal());
		t.setId(task.getId());
		t.setName(task.getName());
		t.setOwner(task.getOwner());
		t.setParentTaskId(task.getParentTaskId());
		t.setPriority(task.getPriority());
		t.setProcessDefinitionId(task.getProcessDefinitionId());
		t.setProcessDefinitionKey(task.getProcessDefinitionKey());
		t.setProcessDefinitionName(task.getProcessDefinitionName());
		t.setProcessInstanceId(task.getProcessInstanceId());
		t.setRev(task.getRev());
		t.setStartUserId(task.getStartUserId());
		t.setStartUserName(task.getStartUserName());
		t.setStartUserOrgText(task.getStartUserOrgText());
		t.setStartUserPositionText(task.getStartUserPositionText());
		t.setSuspensionState(task.getSuspensionState());
		t.setTaskDefinitionKey(task.getTaskDefinitionKey());
		t.setTenantId(task.getTenantId());
		t.setTitle(task.getTitle());
		return t;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAssignee() {
		return assignee;
	}
	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public String getExecutionId() {
		return executionId;
	}
	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}
	public String getFormKey() {
		return formKey;
	}
	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getParentTaskId() {
		return parentTaskId;
	}
	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public String getProcessDefinitionId() {
		return processDefinitionId;
	}
	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}
	public String getProcessInstanceId() {
		return processInstanceId;
	}
	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}
	public String getTaskDefinitionKey() {
		return taskDefinitionKey;
	}
	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}
	public String getTenantId() {
		return tenantId;
	}
	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
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
	public String getBusinessKey() {
		return businessKey;
	}
	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}
	public String getDelegation() {
		return delegation;
	}
	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}
	public Integer getSuspensionState() {
		return suspensionState;
	}
	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}
	public String getStartUserId() {
		return startUserId;
	}
	public void setStartUserId(String startUserId) {
		this.startUserId = startUserId;
	}
	public String getStartUserName() {
		return startUserName;
	}
	public void setStartUserName(String startUserName) {
		this.startUserName = startUserName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getRev() {
		return rev;
	}
	public void setRev(String rev) {
		this.rev = rev;
	}
	public String getAssignForwardCcAction() {
		return assignForwardCcAction;
	}
	public void setAssignForwardCcAction(String assignForwardCcAction) {
		this.assignForwardCcAction = assignForwardCcAction;
	}
	public String getAssignForwardCcUserIds() {
		return assignForwardCcUserIds;
	}
	public void setAssignForwardCcUserIds(String assignForwardCcUserIds) {
		this.assignForwardCcUserIds = assignForwardCcUserIds;
	}
	public String getAssignForwardCcOpertaterUserId() {
		return assignForwardCcOpertaterUserId;
	}
	public void setAssignForwardCcOpertaterUserId(String assignForwardCcOpertaterUserId) {
		this.assignForwardCcOpertaterUserId = assignForwardCcOpertaterUserId;
	}
	public String getFormUrlViewGlobal() {
		return formUrlViewGlobal;
	}
	public void setFormUrlViewGlobal(String formUrlViewGlobal) {
		this.formUrlViewGlobal = formUrlViewGlobal;
	}
	public String getFormUrlEidtGlobal() {
		return formUrlEidtGlobal;
	}
	public void setFormUrlEidtGlobal(String formUrlEidtGlobal) {
		this.formUrlEidtGlobal = formUrlEidtGlobal;
	}
	public String getFormUrlView() {
		return formUrlView;
	}
	public void setFormUrlView(String formUrlView) {
		this.formUrlView = formUrlView;
	}
	public String getFormUrlEdit() {
		return formUrlEdit;
	}
	public void setFormUrlEdit(String formUrlEdit) {
		this.formUrlEdit = formUrlEdit;
	}
	public Map<String, Object> getExtProperty() {
		return extProperty;
	}
	public void setExtProperty(Map<String, Object> extProperty) {
		this.extProperty = extProperty;
	}
	public String getCandidateUserIds() {
		return candidateUserIds;
	}
	public void setCandidateUserIds(String candidateUserIds) {
		this.candidateUserIds = candidateUserIds;
	}
	public String getBusinessStatus() {
		return businessStatus;
	}
	public void setBusinessStatus(String businessStatus) {
		this.businessStatus = businessStatus;
	}
	public String getBusinessStatusDesc() {
		return businessStatusDesc;
	}
	public void setBusinessStatusDesc(String businessStatusDesc) {
		this.businessStatusDesc = businessStatusDesc;
	}
	public String getFormUrlCustom() {
		return formUrlCustom;
	}
	public void setFormUrlCustom(String formUrlCustom) {
		this.formUrlCustom = formUrlCustom;
	}
}
