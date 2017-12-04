package org.lsqt.act.model;

import java.util.ArrayList;
import java.util.List;

import org.lsqt.components.db.Page;

/**
 * 流程变量数据
 */
public class InstanceVariableQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 流程实例ID */
	private String instanceId;
	private List<String> instanceIdList=new ArrayList<>();
	

	/** 流程实例标题 */
	private String title;

	/** 流程实例ID */
	private String definitionId;

	/** 业务数据主键ID */
	private String businessKey;

	/** 流程变量数据 */
	private String variableJson;

	/** 租户编码 */
	private String appCode;

	/** 排序 */

	private Integer sn;

	/** 备注 */
	private String remark;

	// getter、setter

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

	public void setDefinitionId(String definitionId) {
		this.definitionId = definitionId;
	}

	public String getDefinitionId() {
		return this.definitionId;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public void setVariableJson(String variableJson) {
		this.variableJson = variableJson;
	}

	public String getVariableJson() {
		return this.variableJson;
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

	public List<String> getInstanceIdList() {
		return instanceIdList;
	}

	public void setInstanceIdList(List<String> instanceIdList) {
		this.instanceIdList = instanceIdList;
	}

}
