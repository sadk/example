package org.lsqt.act.model;

import java.util.List;

import org.lsqt.components.db.Page;

/**
 * 
 */
public class TaskQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/***/
	private Integer rev;

	/***/
	private String executionId;

	/***/
	private String processInstanceId;

	/***/
	private String processDefinitionId;

	/***/
	private String name;

	/***/
	private String parentTaskId;

	/***/
	private String description;

	/***/
	private String taskDefinitionKey;
	
	/**多个定义以逗号分割 **/
	private String taskDefinitionKeys;

	/***/
	private String owner;

	/***/
	private String assignee;

	/** 代办人Id或loginNo，取决于业务存什么 */
	private String delegation;

	/***/
	private Integer priority;

	/***/
	private java.util.Date dueDate;

	/***/
	private String category;

	/** 1=未挂起 */
	private Integer suspensionState;

	/***/
	private String tenantId;

	/** 一般为外部表单的url值 */
	private String formKey;

	
	// ----------------------------- 查询 ----------------
	private String createTimeBegin;
	private String createTimeEnd;
	
	//时间戳
	private Long createTimeBeginMillis;
	private Long createTimeEndMillis;
	
	// 流程开始日期开始、结束
	private String instanceCreateTimeBegin;
	private String instanceCreateTimeEnd;
	
	
	
	private String userId; // 用于查询指定用户“待办”
	private String userName; 
	private String loginNo; // 用于查询指定用户“待办”（ekp手机端使用）
	private boolean isQueryTaskUser=false; // 是否查询待办用户
	
	
	private String userIds; // 获取多个用户的待办
	private List<String> userIdList;
	
	
	private String processDefinitionName; // 辅助字段
	private String processDefinitionKey; // 辅助字段
	
	private String startUserId;  // 流程发起人
	private String startUserName; 
	private String startLoginNo;
	
	private String title; // 流程标题
	
	//private String ccUserId; //任务抄送的用户ID
	
	private String businessKey;
	
	private String businessFlowNo ;
	
	/**
	我的待办="1";
	我的已办="2";
	抄送我的="3";
	我发起的="4"
	*/
	private String type; // 辅助查询字段
	
	private String enableMobile; //手机端是否启用 0=不启用 1=启用  
	
	
	// ----------------- 优化查询 -----------------
	private String taskIdsIncludeAssignForward; // 包含 含加签、转发 用户的待办
	private String taskIdsEscapeAssignForward; // 去除加签\转发的"主人"待办
	// getter、setter
	public void setRev(Integer rev) {
		this.rev = rev;
	}

	public Integer getRev() {
		return this.rev;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public String getExecutionId() {
		return this.executionId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setParentTaskId(String parentTaskId) {
		this.parentTaskId = parentTaskId;
	}

	public String getParentTaskId() {
		return this.parentTaskId;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setTaskDefinitionKey(String taskDefinitionKey) {
		this.taskDefinitionKey = taskDefinitionKey;
	}

	public String getTaskDefinitionKey() {
		return this.taskDefinitionKey;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getOwner() {
		return this.owner;
	}

	public void setAssignee(String assignee) {
		this.assignee = assignee;
	}

	public String getAssignee() {
		return this.assignee;
	}

	public void setDelegation(String delegation) {
		this.delegation = delegation;
	}

	public String getDelegation() {
		return this.delegation;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setDueDate(java.util.Date dueDate) {
		this.dueDate = dueDate;
	}

	public java.util.Date getDueDate() {
		return this.dueDate;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getCategory() {
		return this.category;
	}

	public void setSuspensionState(Integer suspensionState) {
		this.suspensionState = suspensionState;
	}

	public Integer getSuspensionState() {
		return this.suspensionState;
	}

	public void setTenantId(String tenantId) {
		this.tenantId = tenantId;
	}

	public String getTenantId() {
		return this.tenantId;
	}

	public void setFormKey(String formKey) {
		this.formKey = formKey;
	}

	public String getFormKey() {
		return this.formKey;
	}

	public Integer getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(Integer pageIndex) {
		this.pageIndex = pageIndex;
	}

	public Integer getPageSize() {
		return pageSize;
	}

	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	public String getSortOrder() {
		return sortOrder;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public String getSortField() {
		return sortField;
	}

	public void setSortField(String sortField) {
		this.sortField = sortField;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getIds() {
		return ids;
	}

	public void setIds(String ids) {
		this.ids = ids;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getCreateTimeBegin() {
		return createTimeBegin;
	}

	public void setCreateTimeBegin(String createTimeBegin) {
		this.createTimeBegin = createTimeBegin;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public Long getCreateTimeBeginMillis() {
		return createTimeBeginMillis;
	}

	public void setCreateTimeBeginMillis(Long createTimeBeginMillis) {
		this.createTimeBeginMillis = createTimeBeginMillis;
	}

	public Long getCreateTimeEndMillis() {
		return createTimeEndMillis;
	}

	public void setCreateTimeEndMillis(Long createTimeEndMillis) {
		this.createTimeEndMillis = createTimeEndMillis;
	}


	public String getBusinessKey() {
		return businessKey;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEnableMobile() {
		return enableMobile;
	}

	public void setEnableMobile(String enableMobile) {
		this.enableMobile = enableMobile;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserIds() {
		return userIds;
	}

	public void setUserIds(String userIds) {
		this.userIds = userIds;
	}

	public String getInstanceCreateTimeBegin() {
		return instanceCreateTimeBegin;
	}

	public void setInstanceCreateTimeBegin(String instanceCreateTimeBegin) {
		this.instanceCreateTimeBegin = instanceCreateTimeBegin;
	}

	public String getInstanceCreateTimeEnd() {
		return instanceCreateTimeEnd;
	}

	public void setInstanceCreateTimeEnd(String instanceCreateTimeEnd) {
		this.instanceCreateTimeEnd = instanceCreateTimeEnd;
	}

	public boolean getIsQueryTaskUser() {
		return isQueryTaskUser;
	}

	public void setIsQueryTaskUser(boolean isQueryTaskUser) {
		this.isQueryTaskUser = isQueryTaskUser;
	}

	public String getStartLoginNo() {
		return startLoginNo;
	}

	public void setStartLoginNo(String startLoginNo) {
		this.startLoginNo = startLoginNo;
	}

	public List<String> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<String> userIdList) {
		this.userIdList = userIdList;
	}

	public String getTaskDefinitionKeys() {
		return taskDefinitionKeys;
	}

	public void setTaskDefinitionKeys(String taskDefinitionKeys) {
		this.taskDefinitionKeys = taskDefinitionKeys;
	}

	public String getBusinessFlowNo() {
		return businessFlowNo;
	}

	public void setBusinessFlowNo(String businessFlowNo) {
		this.businessFlowNo = businessFlowNo;
	}

	public String getTaskIdsIncludeAssignForward() {
		return taskIdsIncludeAssignForward;
	}

	public void setTaskIdsIncludeAssignForward(String taskIdsIncludeAssignForward) {
		this.taskIdsIncludeAssignForward = taskIdsIncludeAssignForward;
	}

	public String getTaskIdsEscapeAssignForward() {
		return taskIdsEscapeAssignForward;
	}

	public void setTaskIdsEscapeAssignForward(String taskIdsEscapeAssignForward) {
		this.taskIdsEscapeAssignForward = taskIdsEscapeAssignForward;
	}

}
