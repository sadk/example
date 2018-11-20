package org.lsqt.sys.model;

/**
 * 服务器管理
 */
public class Machine {

	/***/
	private Long id;

	/** 服务器编码 */
	private String code;

	/***/
	private String appCode;

	/** 服务器名称 */
	private String name;

	/** 登陆名称 */
	private String userName;

	/** 登陆密码 */
	private String userPassword;

	/** 链接url */
	private String url;

	/** 启用状态：1=可用 0=不可用 */
	private Integer status;

	/** 地址,可以是ip也可以是域名 */
	private String address;

	/** 端口 */
	private String port;
	
	/** 服务器类型: 1=数据库服务器  2=redis服务器 3=rabitMQ服务器 */
	private String type; 
	public static final String TYPE_DATASOURCE="1";
	public static final String TYPE_REDIS="2";
	public static final String TYPE_RABIT_MQ = "3";

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

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserName() {
		return this.userName;
	}

	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}

	public String getUserPassword() {
		return this.userPassword;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUrl() {
		return this.url;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getAddress() {
		return this.address;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getPort() {
		return this.port;
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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
