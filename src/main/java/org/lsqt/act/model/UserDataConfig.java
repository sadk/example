package org.lsqt.act.model;

/**
 * 数据映射配置
 */
public class UserDataConfig {

	/***/
	private Long id;

	/**配置键，用于区分如获取组织数据、指定某个组织树数据等（自定义编码使用）**/
	private String configKey;
	
	/**配置编码**/
	private String configCode;
	
	/** 配置名称 */
	private String configName;

	/** 应用编码 */
	private String appCode;
	private String appName;

	/** 映射类型: 1=外部系统-http-json 2=本地系统 */
	private String configType;
	public static final String CONFIG_TYPE_REMOTE_HTTP_JSON = "1";
	public static final String CONFIG_TYPE_LOCAL = "2";
	
	/** 映射的实体类型: 1=职称 2=岗位 3=部门 4=组 5=用户 7=角色 */
	private String entityType;
	public static final String ENTITY_TYPE_ORG = "3";
	public static final String ENTITY_TYPE_POSITION = "2";
	public static final String ENTITY_TYPE_USER = "5";
	
	/** 接口地址(不带参数的地址) */
	private String url;
	
	/**http请求方法**/
	private String method;
	
	/**数据字段**/
	private String dataProp;

	/** 排序 */
	private Integer sn;

	/** 备注 */
	private String remark;

	/***/
	private String gid;

	/** 创建日期 */

	private java.util.Date createTime;

	/***/

	private java.util.Date updateTime;

	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

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

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getDataProp() {
		return dataProp;
	}

	public void setDataProp(String dataProp) {
		this.dataProp = dataProp;
	}

	public String getConfigCode() {
		return configCode;
	}

	public void setConfigCode(String configCode) {
		this.configCode = configCode;
	}

	public String getConfigKey() {
		return configKey;
	}

	public void setConfigKey(String configKey) {
		this.configKey = configKey;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

}
