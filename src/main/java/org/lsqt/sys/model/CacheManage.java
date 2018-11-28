package org.lsqt.sys.model;

/**
 * 系统缓存管理表
 */
public class CacheManage {

	/***/
	private Long id;
	
	private String code;

	/***/
	private Long categoryId;

	/**辅助属性*/
	private String categoryName;

	/***/
	private String name;

	/** 缓存刷新地址 */
	private String url;
	
	/** URL请求方法,一般为post或get **/
	private String urlMethod; 

	/** 应用ID */
	private String appCode;

	/** 自定义的数据类型 */
	private String dataType;

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

	public String getUrlMethod() {
		return urlMethod;
	}

	public void setUrlMethod(String urlMethod) {
		this.urlMethod = urlMethod;
	}

}
