package org.lsqt.report.model;

/**
 * 报表定义
 */
public class Definition {

	/***/
	private Long id;

	/** 报表分类ID */
	private Long categoryId;
	private String categoryName;
	
	/** 报表所属的数据源 */
	private Long datasourceId;
	private String datasourceName;

	/** 定义全称 */
	private String name;

	/** 定义简称 */
	private String shortName;

	/** 定义编码 */
	private String code;

	/** 报表内容类型： 1=SQL 2=Table 3=View 4=http_json 5=存储过程 */
	private String type;

	/** 报表url */
	private String url;

	/** 报表数据库类型： 1=MySQL 2=oracle 3=sqlserver 4=PostgreSQL */
	private String dbType;
	
	/** 数据SQL用于导入字段用，可以直接执行 */
	private String reportSql;
	
	/** 报表SQL，带参数的真实报表SQL **/
	private String columnSql;
	
	/** 启用状态: 0=禁用 1=启用   **/
	private String status ;

	private String version;
	
	/** 租户编码 */
	private String appCode;

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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getDatasourceName() {
		return datasourceName;
	}

	public void setDatasourceName(String datasourceName) {
		this.datasourceName = datasourceName;
	}

	public String getReportSql() {
		return reportSql;
	}

	public void setReportSql(String reportSql) {
		this.reportSql = reportSql;
	}

	public String getColumnSql() {
		return columnSql;
	}

	public void setColumnSql(String columnSql) {
		this.columnSql = columnSql;
	}

}
