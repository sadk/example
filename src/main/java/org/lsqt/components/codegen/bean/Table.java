package org.lsqt.components.codegen.bean;

import java.util.Date;

/**
 * 数据库表
 * @author yuanmm
 *
 */
public class Table {
	public static String TYPE_TABLE="1"; // 普通表
	public static String TYPE_VIEW="2"; // 普通视图
	public static String TYPE_MATERIALIZED="3"; // 物化视图
	
	
	private Long id;
	private String projectCode;// 工程模板
	private String dataSourceCode; // 所属的数据源
	private String dbName;
	private String tableName;
	private String modelName; //表对应的实体名称
	private String type; // table or view or 物化视图; 
	private String engine;
	private Long rows;
	private Long autoIncrement;
	private String collation;
	private String createOption;
	private String comment;
	private String remark;
	private String appCode;
	private String version;
	private Integer sn;
	
	private String gid;
	private Date updateTime;
	private Date createTime;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEngine() {
		return engine;
	}
	public void setEngine(String engine) {
		this.engine = engine;
	}
	public Long getAutoIncrement() {
		return autoIncrement;
	}
	public void setAutoIncrement(Long autoIncrement) {
		this.autoIncrement = autoIncrement;
	}
	public String getCollation() {
		return collation;
	}
	public void setCollation(String collation) {
		this.collation = collation;
	}
	public String getCreateOption() {
		return createOption;
	}
	public void setCreateOption(String createOption) {
		this.createOption = createOption;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getDataSourceCode() {
		return dataSourceCode;
	}
	public void setDataSourceCode(String dataSourceCode) {
		this.dataSourceCode = dataSourceCode;
	}
	public Long getRows() {
		return rows;
	}
	public void setRows(Long rows) {
		this.rows = rows;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getAppCode() {
		return appCode;
	}
	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}
	public String getGid() {
		return gid;
	}
	public void setGid(String gid) {
		this.gid = gid;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Integer getSn() {
		return sn;
	}
	public void setSn(Integer sn) {
		this.sn = sn;
	}
	public String getModelName() {
		return modelName;
	}
	public void setModelName(String modelName) {
		this.modelName = modelName;
	}
	public String getProjectCode() {
		return projectCode;
	}
	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}
}
