package org.lsqt.cms.model;

/**
 * 站点资源
 */
public class Resource {

	/***/
	private Long id;

	/***/
	private Long pid;

	/** 资源名称 */
	private String name;
	
	/**别名**/
	private String alias;

	/** 资源值 */
	private String value;

	/** 资源编码 */
	private String code;

	/** 数据类型：100=目录 2xx=文件:200=txt 201=html 202=css 203=js  */
	private Integer type;
	public static final int TYPE_目录=100;
	public static final int TYPE_文件_TXT=200;
	public static final int TYPE_文件_HTML=201;
	public static final int TYPE_文件_CSS=202;
	public static final int TYPE_文件_JS=203;
	public static final int TYPE_文件_PDF=204;
	
	
	
	/** 是否启用: 1=启用 0=禁用 */
	private String enable;

	/** 系统编码 */
	private String appCode;
	private String appName;

	/** 排序 */
	private Integer sn;

	/** 结点路径 */
	private String nodePath;

	/** 资源路径标识符可以是http的url，也可以是磁盘路径 **/
	private String url;
	
	/** 备注 */
	private String remark;

	/***/
	private String gid;

	/** 创建日期 */
	private java.util.Date createTime;

	/***/
	private java.util.Date updateTime;

	// -----  辅助字段
	private String parentName;
	
	
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public Long getPid() {
		return this.pid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getValue() {
		return this.value;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}


	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getEnable() {
		return this.enable;
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

	public void setNodePath(String nodePath) {
		this.nodePath = nodePath;
	}

	public String getNodePath() {
		return this.nodePath;
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

	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		this.alias = alias;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
