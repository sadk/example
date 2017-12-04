package org.lsqt.sys.model;

import java.util.ArrayList;
import java.util.List;

/**
 * 子表管理
 */
public class TableSub {

	/***/
	private Long id;

	/** 主表id */
	private Long mainTableId;

	/** 主表名称 */
	private String mainTableName;

	/** 子表名称 */
	private String tableName;

	/** 子表数据源编码 */
	private String datasourceCode;

	/** 子表数据库 */
	private String dbName;

	/** 子表的版本号 */
	private String version;

	/***/
	private Integer sn;

	/** 用户备注 */
	private String remark;

	/***/
	private String appCode;

	/***/
	private String gid;

	private String projectCode;
	/** 创建日期 */
	private java.util.Date createTime;

	/***/
	private java.util.Date updateTime;

	// 当前子表定义的字段(代码生成器用)
	private List<Column> columnList= new ArrayList<Column>();
	
	// getter、setter
	public void setId(Long id) {
		this.id = id;
	}

	public Long getId() {
		return this.id;
	}

	public void setMainTableId(Long mainTableId) {
		this.mainTableId = mainTableId;
	}

	public Long getMainTableId() {
		return this.mainTableId;
	}

	public void setMainTableName(String mainTableName) {
		this.mainTableName = mainTableName;
	}

	public String getMainTableName() {
		return this.mainTableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public void setDatasourceCode(String datasourceCode) {
		this.datasourceCode = datasourceCode;
	}

	public String getDatasourceCode() {
		return this.datasourceCode;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public String getDbName() {
		return this.dbName;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return this.version;
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

	public void setAppCode(String appCode) {
		this.appCode = appCode;
	}

	public String getAppCode() {
		return this.appCode;
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

	public String getProjectCode() {
		return projectCode;
	}

	public void setProjectCode(String projectCode) {
		this.projectCode = projectCode;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

}
