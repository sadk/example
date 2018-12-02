package org.lsqt.sys.model;

import java.util.Date;

import org.lsqt.components.db.Db;

/**
 * 数据源管理
 * @author yuanmm
 */
public class DataSource {
	/**
	 * 数据源状态：1=可用 0=不可用
	 */
	public static final Integer STATUS_AVAILABLE = 1;
	public static final Integer STATUS_UNAVAILABLE = 0;
	
	private Long id;
	private String name;
	private String code;
	private Integer sn;
	private String appCode;
	
	private String driverClassName;
	private String loginName;
	private String loginPassword;
	private String loginDefaultDb; // 登陆的默认数据库
	private String url;
	
	private Integer status; // 数据源状态：1=可用 0=不可用
	
	private String address; // 地址,可以是ip也可以是域名
	private String port ;

	private Integer dialect ; //数据源对应的数据库类型方言：见,Db.Dialect
	
	
	private String remark;
	
	private String gid;
	private Date createTime;
	private Date updateTime;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getDriverClassName() {
		return driverClassName;
	}
	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}
	public String getLoginName() {
		return loginName;
	}
	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	public String getLoginPassword() {
		return loginPassword;
	}
	public void setLoginPassword(String loginPassword) {
		this.loginPassword = loginPassword;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getPort() {
		return port;
	}
	public void setPort(String port) {
		this.port = port;
	}
	public String getLoginDefaultDb() {
		return loginDefaultDb;
	}
	public void setLoginDefaultDb(String loginDefaultDb) {
		this.loginDefaultDb = loginDefaultDb;
	}
	public Integer getDialect() {
		return dialect;
	}
	public void setDialect(Integer dialect) {
		this.dialect = dialect;
	}

	
}
