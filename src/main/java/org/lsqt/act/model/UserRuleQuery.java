package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 用户规则定义
 */
public class UserRuleQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;
	private String sortFieldGbk; // 中文排序字段
	
	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	/****/
	private Long id;
	
	/** 工作流分类表ID */
	private Long categoryId;

	/** 规则名称 */
	private String name;

	/** 规则编码 */
	private String code;

	/** 规则内容 */
	private String content;
	
	/**规则内容类型：SQL语句=1  JavaScript代码=2  Groovy代码=3*/
	private String contentType;

	/** 规则解析类型: 1=freemark 2=velocity 3=javascript 4=groovy */
	private Integer resolveType;

	private String appCode;

	/** 排序 */
	private Integer sn;

	/** 备注 */
	private String remark;
	
	/**1=启用 0=禁用**/
	private Integer enable;

	// ------------------------- 辅助字段 -------------------------
	private String categoryName ;
	
	// getter、setter

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return this.categoryId;
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

	public void setContent(String content) {
		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public void setResolveType(Integer resolveType) {
		this.resolveType = resolveType;
	}

	public Integer getResolveType() {
		return this.resolveType;
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

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	public Integer getEnable() {
		return enable;
	}

	public void setEnable(Integer enable) {
		this.enable = enable;
	}

	public String getSortFieldGbk() {
		return sortFieldGbk;
	}

	public void setSortFieldGbk(String sortFieldGbk) {
		this.sortFieldGbk = sortFieldGbk;
	}

}
