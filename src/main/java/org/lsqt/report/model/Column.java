package org.lsqt.report.model;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * @see org.lsqt.sys.model.Column
 * 报表字段定义管理
 */
public class Column {
	
	/***/
	private Long id;

	/**数据类型 1=报表展示列  2=数据导入列**/
	private Integer dataType;
	public static final int DATA_TYPE_REPORT_SHOW=1;
	public static final int DATA_TYPE_IMPORT = 2;
	
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

	/** DB字段长度 */
	private String dbTypeLength;
	
	private Integer importRequired; // 1=必填 0=非必填

	private String coordinate; // Excel表头单元格坐标
	
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
	
	/** 查询是否必填**/
	private Integer searchRequired;

	/**是否模糊查询**/
	private Integer likeSearchIs;
	
	
	/**是否是模糊查询: 1=匹配开头 ，2=匹配中间 3=匹配结尾**/
	private Integer likeSearchType; 
	public static final int LIKE_SEARCH_TYPE_LEFT = 1; //like '张%'
	public static final int LIKE_SEARCH_TYPE_MID = 2; //like '%张%'
	public static final int LIKE_SEARCH_TYPE_RIGHT = 3;//like '%张'
	public static final int LIKE_SEARCH_TYPE_NO_WRAP=4; // 不做包装处理
	
	private Integer allowSort;
	
	private Integer frozen;
	public static int FROZEN_YES = 1;
	public static int FROZEN_NO = 0;
	
	
	/**是否允许导入：1=允许  0=不允许*/
	private Integer allowImport;
	
	/**是否允许导出：1=允许  0=不允许*/
	private Integer allowExport;
	
	
	/**
	 * 字段的代码生成器类型:1=选择器 2=下拉框(字典) 3=外键  4=文本框 5=整型框 6=精度型框 7=日期(起始框) 8=文件  9=下拉框(常量JSON) 10=日期框(单个框)
	 */
	private Integer columnCodegenType;
	public static final int COLUMN_CODEGEN_TYPE_选择器 = 1;
	public static final int COLUMN_CODEGEN_TYPE_下拉框_字典 = 2;
	public static final int COLUMN_CODEGEN_TYPE_文本框 = 4;
	public static final int COLUMN_CODEGEN_TYPE_整型框  = 5;
	public static final int COLUMN_CODEGEN_TYPE_精度型框 = 6;
	public static final int COLUMN_CODEGEN_TYPE_日期_起始框= 7;
	public static final int COLUMN_CODEGEN_TYPE_文件 = 8;
	public static final int COLUMN_CODEGEN_TYPE_下拉框_常量JSON = 9;
	public static final int COLUMN_CODEGEN_TYPE_日期框_单个框 = 10;

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

	/** 列对齐方式: 左=1 中=2 右=3*/
	private Integer alignType;
	public static final int ALIGN_TYPE_LEFT=1;
	public static final int ALIGN_TYPE_MID=2;
	public static final int ALIGN_TYPE_RIGHT=3;

	/** 列宽: 默认=120 */
	private Integer width;

	/** 列高 */
	private String height;

	/** 是否隐藏: 1=隐藏 0=不隐藏*/
	private Integer hidde;
	public static int HIDE_YES=1;
	public static int HIDE_NO = 0;

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

 
	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public String getPropertyName() {
		return this.propertyName;
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

	public void setHeight(String height) {
		this.height = height;
	}

	public String getHeight() {
		return this.height;
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

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Integer getColumnCodegenType() {
		return columnCodegenType;
	}

	public void setColumnCodegenType(Integer columnCodegenType) {
		this.columnCodegenType = columnCodegenType;
	}

	public Integer getAllowSort() {
		return allowSort;
	}

	public void setAllowSort(Integer allowSort) {
		this.allowSort = allowSort;
	}

	public Integer getFrozen() {
		return frozen;
	}

	public void setFrozen(Integer frozen) {
		this.frozen = frozen;
	}

	public Integer getPrimaryKey() {
		return primaryKey;
	}

	public void setPrimaryKey(Integer primaryKey) {
		this.primaryKey = primaryKey;
	}

	public Integer getOroColumnType() {
		return oroColumnType;
	}

	public void setOroColumnType(Integer oroColumnType) {
		this.oroColumnType = oroColumnType;
	}

	public Integer getJavaType() {
		return javaType;
	}

	public void setJavaType(Integer javaType) {
		this.javaType = javaType;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public Integer getLikeSearchIs() {
		return likeSearchIs;
	}

	public void setLikeSearchIs(Integer likeSearchIs) {
		this.likeSearchIs = likeSearchIs;
	}

	public Integer getLikeSearchType() {
		return likeSearchType;
	}

	public void setLikeSearchType(Integer likeSearchType) {
		this.likeSearchType = likeSearchType;
	}

	public Integer getAlignType() {
		return alignType;
	}

	public void setAlignType(Integer alignType) {
		this.alignType = alignType;
	}

	public Integer getHidde() {
		return hidde;
	}

	public void setHidde(Integer hidde) {
		this.hidde = hidde;
	}

	public Integer getSearchRequired() {
		return searchRequired;
	}

	public void setSearchRequired(Integer searchRequired) {
		this.searchRequired = searchRequired;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public Integer getImportRequired() {
		return importRequired;
	}

	public void setImportRequired(Integer importRequired) {
		this.importRequired = importRequired;
	}

	public String getCoordinate() {
		return coordinate;
	}

	public void setCoordinate(String coordinate) {
		this.coordinate = coordinate;
	}

	public Integer getAllowImport() {
		return allowImport;
	}

	public void setAllowImport(Integer allowImport) {
		this.allowImport = allowImport;
	}

	public Integer getAllowExport() {
		return allowExport;
	}

	public void setAllowExport(Integer allowExport) {
		this.allowExport = allowExport;
	}

	public String getDbTypeLength() {
		return dbTypeLength;
	}

	public void setDbTypeLength(String dbTypeLength) {
		this.dbTypeLength = dbTypeLength;
	}

 
}
