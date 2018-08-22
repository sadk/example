package org.lsqt.report.model;

/**
 * @see org.lsqt.sys.model.Column
 * 报表字段定义管理
 */
public class Column {

	/***/
	private Long id;

	/** 当前列所属的报表 */
	private Long definitionId;

	/** 列字段sql编码 */
	private String code;

	/** 列名中文 */
	private String name;
	
	private String reportName;

	/** 列注释 */
	private String comment;

	/** DB字段类型 */
	private String dbType;

	/** JAVA类型 */

	private Integer javaType;

	/** JAVA属性名 */
	private String propertyName;

	/** 是否是主键：1=是，0=否 */

	private Integer primaryKey;

	/** ORMapping映射的字段类型：gid(全局唯一码)=1 updateTime(更新时间)=2 createTime(创建时间)=3 */
	private Integer oroColumnType;

	/** 当前列是否作为查询条件: 0=否，1=是 */

	private Integer searchType;

	/**
	 * 字段的代码生成器类型:1=选择器 2=下拉框(字典) 3=外键 4=文本框 5=整型框 6=精度型框 7=日期 8=文件  9=下拉框(常量JSON)
	 */
	private String columnCodegenType;

	/** 默认：double型的为两个小数点， date 为 [yyyy-MM-dd HH:mm:ss] */
	private String columnCodegenFormat;

	/** 字段组:用于生成html的fieldset框 */
	private String columnCodegenGroupCode;

	/** 选择器，是单选还是多选 */
	private String selectorMultilSelect;

	/** 选择器选择后显示的文本字段(多选以逗号分割) */
	private String selectorTextCols;

	/** 选择器选择后显示的值字段(多选以逗号分割) */
	private String selectorValueCols;

	/**
	 * 选择器数据来源:0=URL(页面) 1=URL(返回JSON) 2=URL(返回XML) 3=代码片断(JavaScript)数组 4=SQL
	 */
	private String selectorDataFromType;

	/** 选择器数据来源 */
	private String selectorDataFrom;

	/** 选择器的数据来源为SQL时，选定的数据源 */
	private String selectorDataSourceCode;

	/** 选择器的数据来源为SQL时，选定的数据库 */
	private String selectorDbName;

	/** 当前列是字典值时，对应的字典code值，如(值为“sex”就是男、女,对应的值就是1和0) */
	private String dictRefCode;

	/** 显示的文本字段 */
	private String dictTextCol;

	/** 显示的值字段 */
	private String dictValueCol;

	/** 是否可多选批量上传 */
	private String fileMultil;

	/** 自定义的文件上传控件(代码片断) */
	private String fileCustomContent;

	/** 列对齐方式: */
	private String alignType;

	/** 列宽: */
	private String width;

	/** 列高 */
	private String height;

	/** 是否显示: 0=隐藏 1=显示 */
	private String hidde;

	/***/
	private Integer sn;

	/** 表字段的版本号 */
	private String version;

	/** 用户操作备注 */
	private String optLog;

	/** 用户备注 */
	private String remark;

	/***/
	private String appCode;

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

	public void setDefinitionId(Long definitionId) {
		this.definitionId = definitionId;
	}

	public Long getDefinitionId() {
		return this.definitionId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return this.code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getComment() {
		return this.comment;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getDbType() {
		return this.dbType;
	}

	public void setJavaType(Integer javaType) {
		this.javaType = javaType;
	}

	public Integer getJavaType() {
		return this.javaType;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return this.propertyName;
	}

	public void setPrimaryKey(Integer primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Integer getPrimaryKey() {
		return this.primaryKey;
	}

	public void setOroColumnType(Integer oroColumnType) {
		this.oroColumnType = oroColumnType;
	}

	public Integer getOroColumnType() {
		return this.oroColumnType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public Integer getSearchType() {
		return this.searchType;
	}

	public void setColumnCodegenType(String columnCodegenType) {
		this.columnCodegenType = columnCodegenType;
	}

	public String getColumnCodegenType() {
		return this.columnCodegenType;
	}

	public void setColumnCodegenFormat(String columnCodegenFormat) {
		this.columnCodegenFormat = columnCodegenFormat;
	}

	public String getColumnCodegenFormat() {
		return this.columnCodegenFormat;
	}

	public void setColumnCodegenGroupCode(String columnCodegenGroupCode) {
		this.columnCodegenGroupCode = columnCodegenGroupCode;
	}

	public String getColumnCodegenGroupCode() {
		return this.columnCodegenGroupCode;
	}

	public void setSelectorMultilSelect(String selectorMultilSelect) {
		this.selectorMultilSelect = selectorMultilSelect;
	}

	public String getSelectorMultilSelect() {
		return this.selectorMultilSelect;
	}

	public void setSelectorTextCols(String selectorTextCols) {
		this.selectorTextCols = selectorTextCols;
	}

	public String getSelectorTextCols() {
		return this.selectorTextCols;
	}

	public void setSelectorValueCols(String selectorValueCols) {
		this.selectorValueCols = selectorValueCols;
	}

	public String getSelectorValueCols() {
		return this.selectorValueCols;
	}

	public void setSelectorDataFromType(String selectorDataFromType) {
		this.selectorDataFromType = selectorDataFromType;
	}

	public String getSelectorDataFromType() {
		return this.selectorDataFromType;
	}

	public void setSelectorDataFrom(String selectorDataFrom) {
		this.selectorDataFrom = selectorDataFrom;
	}

	public String getSelectorDataFrom() {
		return this.selectorDataFrom;
	}

	public void setSelectorDataSourceCode(String selectorDataSourceCode) {
		this.selectorDataSourceCode = selectorDataSourceCode;
	}

	public String getSelectorDataSourceCode() {
		return this.selectorDataSourceCode;
	}

	public void setSelectorDbName(String selectorDbName) {
		this.selectorDbName = selectorDbName;
	}

	public String getSelectorDbName() {
		return this.selectorDbName;
	}

	public void setDictRefCode(String dictRefCode) {
		this.dictRefCode = dictRefCode;
	}

	public String getDictRefCode() {
		return this.dictRefCode;
	}

	public void setDictTextCol(String dictTextCol) {
		this.dictTextCol = dictTextCol;
	}

	public String getDictTextCol() {
		return this.dictTextCol;
	}

	public void setDictValueCol(String dictValueCol) {
		this.dictValueCol = dictValueCol;
	}

	public String getDictValueCol() {
		return this.dictValueCol;
	}

	public void setFileMultil(String fileMultil) {
		this.fileMultil = fileMultil;
	}

	public String getFileMultil() {
		return this.fileMultil;
	}

	public void setFileCustomContent(String fileCustomContent) {
		this.fileCustomContent = fileCustomContent;
	}

	public String getFileCustomContent() {
		return this.fileCustomContent;
	}

	public void setAlignType(String alignType) {
		this.alignType = alignType;
	}

	public String getAlignType() {
		return this.alignType;
	}

	public void setWidth(String width) {
		this.width = width;
	}

	public String getWidth() {
		return this.width;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHeight() {
		return this.height;
	}

	public void setHidde(String hidde) {
		this.hidde = hidde;
	}

	public String getHidde() {
		return this.hidde;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public Integer getSn() {
		return this.sn;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getVersion() {
		return this.version;
	}

	public void setOptLog(String optLog) {
		this.optLog = optLog;
	}

	public String getOptLog() {
		return this.optLog;
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

	public String getReportName() {
		return reportName;
	}

	public void setReportName(String reportName) {
		this.reportName = reportName;
	}

}
