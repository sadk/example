package org.lsqt.api.model;

import org.lsqt.components.db.Page;

/**
 * API定义
 */
public class DefinitionQuery {
	private Integer pageIndex = Page.DEFAULT_PAGE_INDEX;
	private Integer pageSize = Page.DEFAULT_PAGE_SIZE;

	private String sortOrder;
	private String sortField;

	private String key; // 关键字
	private String ids; // 用逗号分割的id字符

	private Long id;
	
	private Long categoryId;
	private String categoryName;

 
	
	
	
	/**映射类型: 1=外部系统-http-json 2=本地系统*/
	private Integer type;
	
	/**报文发送方式: 1=form_data 2=x-www-form-urlencodeed 3=raw 4=binary(需选择文件)**/
	private Integer sendType;

	/** URL请求方法,一般为post或get **/
	private String method; 
	
	/** 缓存刷新地址 */
	private String url;

	/**JSON数据数据字段**/
	private String dataProp;
	
	/**请求实体**/
	private String entityClazzRequest;
	
	/**对应的响应实体**/
	private String entityClazzResponse;
	
	
	
	
	
	
	
	

	/** 应用ID */
	private String appId;

	/** 应用名称 */
	private String appName;

	/** 自定义的数据类型 */
	private String dataType;

	/** 排序 */

	private Integer sn;

	/***/
	private String remark;

	// getter、setter

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public Long getCategoryId() {
		return this.categoryId;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getCategoryName() {
		return this.categoryName;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppId() {
		return this.appId;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getAppName() {
		return this.appName;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getDataType() {
		return this.dataType;
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

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String getEntityClazzRequest() {
		return entityClazzRequest;
	}

	public void setEntityClazzRequest(String entityClazzRequest) {
		this.entityClazzRequest = entityClazzRequest;
	}

	public String getEntityClazzResponse() {
		return entityClazzResponse;
	}

	public void setEntityClazzResponse(String entityClazzResponse) {
		this.entityClazzResponse = entityClazzResponse;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public Integer getSendType() {
		return sendType;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public String getDataProp() {
		return dataProp;
	}

	public void setDataProp(String dataProp) {
		this.dataProp = dataProp;
	}

}
