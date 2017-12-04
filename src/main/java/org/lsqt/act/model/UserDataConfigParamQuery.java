package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 数据映射配置
 */
public class UserDataConfigParamQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/** 数据配置ID(引用ext_user_data_config.id) */
	private String configId;

	/** 参数名称 */
	private String paramName;

	/** 参数编码 */
	private String paramCode;

	/** 参数类型: 100=登陆用户上下文 200=静态值 */
	private String paramType;

	/** 参数值 */
	private String paramValue;

	/** 排序 */
	private Integer sn;

	/** 备注 */
	private String remark;

	// getter、setter

	public void setConfigId(String configId) {
		this.configId = configId;
	}

	public String getConfigId() {
		return this.configId;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	public String getParamName() {
		return this.paramName;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	public String getParamCode() {
		return this.paramCode;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public String getParamType() {
		return this.paramType;
	}

	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}

	public String getParamValue() {
		return this.paramValue;
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
