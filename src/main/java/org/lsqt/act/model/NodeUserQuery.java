package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 节点审批人设置
 */
public class NodeUserQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 流程定义ID */
	private String definitionId;

	/** 流程定义名称 */
	private String definitionName;

	/** 节点定义Key */
	private String taskKey;

	/** 名称 */
	private String name;

	/**
	 * 结点用户类型: 1=用户 2=角色 3=组织 4=岗位 5=用户组(与流程引擎的group不是一个概念，主要是第三方系统的UUM)； 角本计算：
	 * 10=java角本计算 11=groovy角本计算
	 * 12=javascript角本计算(注：角本计算必须返回Collection<String>集合
	 */

	private Integer userType;

	/** 用户数据来源：1=内部 2=外部（如http-json数据） */

	private Integer userFromType;

	/***/
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

}
