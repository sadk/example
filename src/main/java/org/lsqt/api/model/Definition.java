package org.lsqt.api.model;

/**
 * API定义
 */
public class Definition {

	/***/
	private Long id;
	
	private Long pid;
	
	/***/
	private String name;
	
	private String code;

	private String value;
	
	/**API定义 对应的分类*/
	private Long categoryId;
	
	/**辅助属性*/
	private String categoryName;

	/**映射类型:1=本地系统  2=外部系统-http-json */
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
	private String entityClassRequest;
	
	/**对应的响应实体**/
	private String entityClassResponse;
	
	/**请求超时间,默认为0或null表示不设定超时**/
	private Long timeout;
	
	
	
	
	
	/** 应用ID */
	private String appCode;


	/** 排序 */
	private Integer sn;

	/***/
	private String remark;

	/** gid:全局唯一编号 */
	private String gid;

	/** createTime:创建日期 */

	private java.util.Date createTime;

	/** updateTime:更新时间 */

	private java.util.Date updateTime;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

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

	public void setGid(String gid) {
		this.gid = gid;
	}

	public String getGid() {
		return this.gid;
	}

	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getAppCode() {
		return appCode;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public Long getPid() {
		return pid;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
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

	public String getEntityClassRequest() {
		return entityClassRequest;
	}

	public void setEntityClassRequest(String entityClassRequest) {
		this.entityClassRequest = entityClassRequest;
	}

	public String getEntityClassResponse() {
		return entityClassResponse;
	}

	public void setEntityClassResponse(String entityClassResponse) {
		this.entityClassResponse = entityClassResponse;
	}

	public Long getTimeout() {
		return timeout;
	}

	public void setTimeout(Long timeout) {
		this.timeout = timeout;
	}
}
