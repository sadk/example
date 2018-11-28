package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 节点按钮设置
 */
public class NodeButtonQuery {
	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/***/
	private Long id;
	
	/**流程实例id**/
	private String processInstanceId;
	
	/** 流程定义ID */
	private String definitionId;

	/** 节点定义Key */
	private String taskKey;
	private String taskName;
	
	/**按钮定义类别: 1=节点按钮 2=全局按钮**/
	private Integer dataType;
	
	/** 按钮名称 */
	private String btnName;

	/** 按钮编码 */
	private String btnCode;

	/** 按钮类型: 1=同意 2=驳回 3=驳回到发起人 4=驳回到选择节点 5=转交代办 6=沟通 7=保存表单 8=保存草稿 */
	private Integer btnType;

	/** 前置角本 */
	private String beforeScript;

	/** 后置角本 */
	private String afterScript;

	/** 租户编码 */
	private String appCode;

	/** 排序 */
	private Integer sn;

	/** 备注 */
	private String remark;

	// getter、setter

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionId() {
		return this.definitionId;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getTaskKey() {
		return this.taskKey;
	}

	public void setBtnName(String btnName) {
		this.btnName = btnName;
	}

	public String getBtnName() {
		return this.btnName;
	}

	public void setBtnCode(String btnCode) {
		this.btnCode = btnCode;
	}

	public String getBtnCode() {
		return this.btnCode;
	}

	public void setBtnType(Integer btnType) {
		this.btnType = btnType;
	}

	public Integer getBtnType() {
		return this.btnType;
	}

	public void setBeforeScript(String beforeScript) {
		this.beforeScript = beforeScript;
	}

	public String getBeforeScript() {
		return this.beforeScript;
	}

	public void setAfterScript(String afterScript) {
		this.afterScript = afterScript;
	}

	public String getAfterScript() {
		return this.afterScript;
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

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public String getProcessInstanceId() {
		return processInstanceId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

}
