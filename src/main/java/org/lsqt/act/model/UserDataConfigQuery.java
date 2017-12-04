package org.lsqt.act.model;

import org.lsqt.components.db.Page;

/**
 * 数据映射配置
 */
public class UserDataConfigQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符
	private Long id;
	/** 配置名称 */
	private String configName;
	
	private String configCode;
	/** 应用编码 */
	private String appCode;

	/** 映射类型: 1=外部系统-http-json 2=本地系统 */
	private String configType;

	/** 映射的实体类型: 1=职称 2=岗位 3=部门 4=组 5=用户 7=角色 */
	private String entityType;

	/** 接口地址(不带参数的地址) */
	private String url;

	/** 排序 */
	private Integer sn;

	/** 备注 */
	private String remark;

	// getter、setter

	public void setConfigName(String configName) {
		this.configName = configName;
	}

	public String getConfigName() {
		return this.configName;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setConfigType(String configType) {
		this.configType = configType;
	}

	public String getConfigType() {
		return this.configType;
	}

	public void setEntityType(String entityType) {
		this.entityType = entityType;
	}

	public String getEntityType() {
		return this.entityType;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
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

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

}
