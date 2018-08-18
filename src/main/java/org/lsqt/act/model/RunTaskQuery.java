package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 待办任务扩展表
 */
public class RunTaskQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	private Long id;
	
	/** 流程实例ID */
	private Long instanceId;

	/** 任务ID */
	private Long taskId;
	
	private String taskUserId;
	private String taskUserName;
	private String taskUserLoginNo;

	/** 任务节点key */
	private String taskKey;

	/** 流程定义ID */
	private String processDefinitionId;

	/** 流程定义key */
	private String processDefinitionKey;

	/** 流程定义名称 */
	private String processDefinitionName;

	/** 点击待办时打开的页面链接 */
	private String taskLink;

	/** 流程标题 */
	private String title;

	/** 填制人部门：一般从表单取值，如果没有取发起用户的主部门，主部门没有再取用户的普通部门第一个 */
	private String createDeptId;

	/** 填制人部门或填制人部门节点路径 */
	private String createDeptText;

	/** 流程发起人ID */
	private String startUserId;

	/** 流程发起人名称 */
	private String startUserName;

	/***/
	private String startLoginNo;

	/** 流程发起人(主)组织 */
	private String startUserOrgText;

	/** 流程发起人(主)岗位 */
	private String startUserPositionText;

	/** 业务数据主键ID */
	private String businessKey;

	/***/
	private String businessFlowNo;

	/** 业务自定义的状态 */
	private String businessStatus;

	/** 业务状态中文表示 */
	private String businessStatusDesc;

	/** 业务数据自定义的分类 */
	private String businessCategory;

	/** 用印公司名称 */
	private String companyNamePrint;

	/***/
	private String companyIdPrint;

	/** 租户编码 */
	private String appCode;

	/** 排序 */

	private Integer sn;

	/** 备注 */
	private String remark;

	// getter、setter

	public void setInstanceId(Long instanceId) {
		this.instanceId = instanceId;
	}

	public Long getInstanceId() {
		return this.instanceId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getTaskId() {
		return this.taskId;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setProcessDefinitionId(String processDefinitionId) {
		this.processDefinitionId = processDefinitionId;
	}

	public String getProcessDefinitionId() {
		return this.processDefinitionId;
	}

	public void setProcessDefinitionKey(String processDefinitionKey) {
		this.processDefinitionKey = processDefinitionKey;
	}

	public String getProcessDefinitionKey() {
		return this.processDefinitionKey;
	}

	public void setProcessDefinitionName(String processDefinitionName) {
		this.processDefinitionName = processDefinitionName;
	}

	public String getProcessDefinitionName() {
		return this.processDefinitionName;
	}

	public void setTaskLink(String taskLink) {
		this.taskLink = taskLink;
	}

	public String getTaskLink() {
		return this.taskLink;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setCreateDeptId(String createDeptId) {
		this.createDeptId = createDeptId;
	}

	public String getCreateDeptId() {
		return this.createDeptId;
	}

	public void setCreateDeptText(String createDeptText) {
		this.createDeptText = createDeptText;
	}

	public String getCreateDeptText() {
		return this.createDeptText;
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

	public void setStartLoginNo(String startLoginNo) {
		this.startLoginNo = startLoginNo;
	}

	public String getStartLoginNo() {
		return this.startLoginNo;
	}

	public void setStartUserOrgText(String startUserOrgText) {
		this.startUserOrgText = startUserOrgText;
	}

	public String getStartUserOrgText() {
		return this.startUserOrgText;
	}

	public void setStartUserPositionText(String startUserPositionText) {
		this.startUserPositionText = startUserPositionText;
	}

	public String getStartUserPositionText() {
		return this.startUserPositionText;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setBusinessFlowNo(String businessFlowNo) {
		this.businessFlowNo = businessFlowNo;
	}

	public String getBusinessFlowNo() {
		return this.businessFlowNo;
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

	public void setCompanyNamePrint(String companyNamePrint) {
		this.companyNamePrint = companyNamePrint;
	}

	public String getCompanyNamePrint() {
		return this.companyNamePrint;
	}

	public void setCompanyIdPrint(String companyIdPrint) {
		this.companyIdPrint = companyIdPrint;
	}

	public String getCompanyIdPrint() {
		return this.companyIdPrint;
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTaskUserId() {
		return taskUserId;
	}

	public void setTaskUserId(String taskUserId) {
		this.taskUserId = taskUserId;
	}

	public String getTaskUserName() {
		return taskUserName;
	}

	public void setTaskUserName(String taskUserName) {
		this.taskUserName = taskUserName;
	}

	public String getTaskUserLoginNo() {
		return taskUserLoginNo;
	}

	public void setTaskUserLoginNo(String taskUserLoginNo) {
		this.taskUserLoginNo = taskUserLoginNo;
	}

}
