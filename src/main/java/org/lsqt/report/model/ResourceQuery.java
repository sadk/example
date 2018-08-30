package org.lsqt.report.model;

import org.lsqt.components.db.Page;

/**
 * 报表页面元素定义
 */
public class ResourceQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	private Long id;
	
	/** 报表分类ID */
	private Long definitionId;

	/** 报表分类名 */
	private String name;

	/** 定义编码 */
	private String code;

	/**按钮事件,如:onclick**/
	private String eventName;
	
	/** 报表页面元素类型: 1=按钮 2=下拉框（单选）3=下拉框（多选）4=文本框 5=TextArae 6=File 7=日历 8=数字框 */
	private String type;

	/** 按钮点击前事件触发的js函数 */
	private String btnBeforeScript;

	/** 按钮点击后事件触发js函数 */
	private String btnAfterScript;
	/**点击按钮时触发的角本**/
	private String btnScript;
	
	/** 租户编码 */
	private String appCode;

	/** 排序 */
	private Integer sn;

	/** 备注 */
	private String remark;

	// getter、setter

	public void setDefinitionId(Long definitionId) {
		this.definitionId = definitionId;
	}

	public Long getDefinitionId() {
		return this.definitionId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return this.type;
	}

	public void setBtnBeforeScript(String btnBeforeScript) {
		this.btnBeforeScript = btnBeforeScript;
	}

	public String getBtnBeforeScript() {
		return this.btnBeforeScript;
	}

	public void setBtnAfterScript(String btnAfterScript) {
		this.btnAfterScript = btnAfterScript;
	}

	public String getBtnAfterScript() {
		return this.btnAfterScript;
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

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getBtnScript() {
		return btnScript;
	}

	public void setBtnScript(String btnScript) {
		this.btnScript = btnScript;
	}

}
