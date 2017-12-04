package org.lsqt.sys.model;

import org.lsqt.components.db.Page;

/**
 * 变量管理
 */
public class VariableQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/***/

	private Long objId;

	/** 变量名 */
	private String name;

	/** 变量编码 */
	private String code;

	/** 变量使用类型 1=流程变量用 2=代码生成用 3=常用变量 */
	private String useType;

	/** 变量值 */
	private String value;

	/** 变量值解析类型:1=freemark 2=velocity 3=groovy 4=javascript */
	private String valueResolveType;

	/***/

	private Integer sn;

	/** 用户备注 */
	private String remark;

	/***/
	private String appCode;

	// getter、setter

	public void setObjId(Long objId) {
		this.objId = objId;
	}

	public Long getObjId() {
		return this.objId;
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

	public void setUseType(String useType) {
		this.useType = useType;
	}

	public String getUseType() {
		return this.useType;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setValueResolveType(String valueResolveType) {
		this.valueResolveType = valueResolveType;
	}

	public String getValueResolveType() {
		return this.valueResolveType;
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

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
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
