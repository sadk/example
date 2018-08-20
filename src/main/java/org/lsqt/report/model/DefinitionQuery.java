package org.lsqt.report.model;

import org.lsqt.components.db.Page;

/**
 * 报表定义
 */
public class DefinitionQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	
	private Long id;

	private String ids; // 用逗号分割的id字符

	/** 报表分类ID */
	private Long categoryId;

	/** 报表所属的数据源 */
	private Long datasourceId;

	/** 定义全称 */
	private String definitionName;

	/** 定义简称 */
	private String definitionShortName;

	/** 定义编码 */
	private String code;

	/** 报表内容类型： 1=SQL 2=Table 3=View 4=http_json 5=存储过程 */
	private String type;

	/** 报表url */
	private String url;

	/** 报表数据库类型： 1=MySQL 2=oracle 3=sqlserver 4=PostgreSQL */
	private String dbType;

	/** 租户编码 */
	private String appCode;

	/** 排序 */

	private Integer sn;

	/** 备注 */
	private String remark;

	// getter、setter

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setDatasourceId(Long datasourceId) {
		this.datasourceId = datasourceId;
	}

	public Long getDatasourceId() {
		return this.datasourceId;
	}

	public void setDefinitionName(String definitionName) {
		this.definitionName = definitionName;
	}

	public String getDefinitionName() {
		return this.definitionName;
	}

	public void setDefinitionShortName(String definitionShortName) {
		this.definitionShortName = definitionShortName;
	}

	public String getDefinitionShortName() {
		return this.definitionShortName;
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

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbType() {
		return this.dbType;
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

}
