package org.lsqt.act.model;

import java.util.Date;

import org.lsqt.sys.model.Column;

/**
 * 表单字段,此模型与代码生器的字段衍生而来
 * @author mmyuan
 *
 */
public class FormField {
	public static int ORO_COLUMN_TYPE_GID=Column.ORO_COLUMN_TYPE_GID;
	public static int ORO_COLUMN_TYPE_CREATE_TIME=Column.ORO_COLUMN_TYPE_CREATE_TIME;
	public static int ORO_COLUMN_TYPE_UPDATE_TIME=Column.ORO_COLUMN_TYPE_UPDATE_TIME;
	public static int ORO_COLUMN_TYPE_ORDINARY=Column.ORO_COLUMN_TYPE_ORDINARY;
	public static int ORO_COLUMN_TYPE_PRIMARY=Column.ORO_COLUMN_TYPE_PRIMARY;
	
	public static int COLUMN_CODEGEN_TYPE_选择器=Column.COLUMN_CODEGEN_TYPE_选择器;
	public static int COLUMN_CODEGEN_TYPE_下拉框_字典=Column.COLUMN_CODEGEN_TYPE_下拉框_字典;
	//public static int COLUMN_CODEGEN_TYPE_外键=3;
	public static int COLUMN_CODEGEN_TYPE_文本=Column.COLUMN_CODEGEN_TYPE_文本;
	public static int COLUMN_CODEGEN_TYPE_数字整型=Column.COLUMN_CODEGEN_TYPE_数字整型;
	public static int COLUMN_CODEGEN_TYPE_数字精度型=Column.COLUMN_CODEGEN_TYPE_数字精度型;
	public static int COLUMN_CODEGEN_TYPE_日期=Column.COLUMN_CODEGEN_TYPE_日期;
	public static int COLUMN_CODEGEN_TYPE_文件上传=Column.COLUMN_CODEGEN_TYPE_文件上传;
	public static int COLUMN_CODEGEN_TYPE_下拉框_常量JSON = Column.COLUMN_CODEGEN_TYPE_下拉框_常量JSON;
	public static int COLUMN_CODEGEN_TYPE_未知 = Column.COLUMN_CODEGEN_TYPE_未知;
	
	
	// 是否是主键、是否作为查询条件、是否是外键、是否是大字段
	public static int YES=Column.YES;
	public static int NO=Column.NO;
	
	
	public static int JAVA_TYPE_JAVA_LANG_STRING=Column.JAVA_TYPE_JAVA_LANG_STRING;
	public static int JAVA_TYPE_JAVA_LANG_CHARACTER=Column.JAVA_TYPE_JAVA_LANG_CHARACTER;
	public static int JAVA_TYPE_JAVA_LANG_BYTE=Column.JAVA_TYPE_JAVA_LANG_BYTE;
	public static int JAVA_TYPE_JAVA_LANG_SHORT=Column.JAVA_TYPE_JAVA_LANG_SHORT;
	public static int JAVA_TYPE_JAVA_LANG_INTEGER=Column.JAVA_TYPE_JAVA_LANG_INTEGER;
	public static int JAVA_TYPE_JAVA_LANG_LONG=Column.JAVA_TYPE_JAVA_LANG_LONG;
	public static int JAVA_TYPE_JAVA_LANG_FLOAT=Column.JAVA_TYPE_JAVA_LANG_FLOAT;
	public static int JAVA_TYPE_JAVA_LANG_DOUBLE=Column.JAVA_TYPE_JAVA_LANG_DOUBLE;
	public static int JAVA_TYPE_JAVA_LANG_BOOLEAN=Column.JAVA_TYPE_JAVA_LANG_BOOLEAN;
	public static int JAVA_TYPE_JAVA_LANG_UTIL_DATE=Column.JAVA_TYPE_JAVA_LANG_UTIL_DATE;
	public static int JAVA_TYPE_JAVA_MATH_BIGDECIMAL=Column.JAVA_TYPE_JAVA_MATH_BIGDECIMAL;
	public static int JAVA_TYPE_JAVA_MATH_BIGINTEGER=Column.JAVA_TYPE_JAVA_MATH_BIGINTEGER;
	public static int JAVA_TYPE_JAVA_SQL_TIME=Column.JAVA_TYPE_JAVA_SQL_TIME;
	public static int JAVA_TYPE_JAVA_SQL_DATE=Column.JAVA_TYPE_JAVA_SQL_DATE;
	public static int JAVA_TYPE_JAVA_SQL_TIMESTAMP=Column.JAVA_TYPE_JAVA_SQL_TIMESTAMP;
	public static int JAVA_TYPE_JAVA_LANG_BYTE_ARRAY=Column.JAVA_TYPE_JAVA_LANG_BYTE_ARRAY;

	
	public static String JAVA_TYPE_DESC_JAVA_LANG_STRING=Column.JAVA_TYPE_DESC_JAVA_LANG_STRING;
	public static String JAVA_TYPE_DESC_JAVA_LANG_BYTE=Column.JAVA_TYPE_DESC_JAVA_LANG_BYTE;
	public static String JAVA_TYPE_DESC_JAVA_LANG_INTEGER=Column.JAVA_TYPE_DESC_JAVA_LANG_INTEGER;
	public static String JAVA_TYPE_DESC_JAVA_LANG_LONG=Column.JAVA_TYPE_DESC_JAVA_LANG_LONG;
	public static String JAVA_TYPE_DESC_JAVA_LANG_FLOAT=Column.JAVA_TYPE_DESC_JAVA_LANG_FLOAT;
	public static String JAVA_TYPE_DESC_JAVA_LANG_DOUBLE=Column.JAVA_TYPE_DESC_JAVA_LANG_DOUBLE;
	public static String JAVA_TYPE_DESC_JAVA_LANG_BOOLEAN =Column.JAVA_TYPE_DESC_JAVA_LANG_BOOLEAN;
	public static String JAVA_TYPE_DESC_JAVA_MATH_BIGDECIMAL=Column.JAVA_TYPE_DESC_JAVA_MATH_BIGDECIMAL;
	public static String JAVA_TYPE_DESC_JAVA_MATH_BIGINTEGER=Column.JAVA_TYPE_DESC_JAVA_MATH_BIGINTEGER;
	public static String JAVA_TYPE_DESC_JAVA_SQL_TIME=Column.JAVA_TYPE_DESC_JAVA_SQL_TIME;
	public static String JAVA_TYPE_DESC_JAVA_SQL_TIMESTAMP=Column.JAVA_TYPE_DESC_JAVA_SQL_TIMESTAMP; // 日期
	public static String JAVA_TYPE_DESC_JAVA_SQL_DATE=Column.JAVA_TYPE_DESC_JAVA_SQL_DATE;
	public static String JAVA_TYPE_DESC_JAVA_UTIL_DATE=Column.JAVA_TYPE_DESC_JAVA_UTIL_DATE;
	public static String JAVA_TYPE_DESC_JAVA_LANG_BYTE_ARRAY=Column.JAVA_TYPE_DESC_JAVA_LANG_BYTE_ARRAY; // Blob数据类型
	
	private Long id;
	private Long formId;
	private String formName;
	
	private String dataSourceCode; // 表单主表所在的数据源
	private String dbName; //表单主表所在的数据库
	private String tableName; // 主单主表名
	private String fieldName; //字段名
	private String fieldCode; //字段编码
	
	private String comment;
	private String dbType;  // 数据库字段类型，如 bigint、varchar等
	private Integer searchType; // 当前列是否作为查询条件(代码生成器生成查询) 0=不作为查询条件 1=作为查询条件
	
	
	/**
	 * 列的java(常用)数据类型定义
	 * <pre>
	 * java.lang.String=0
	 * java.lang.Character=1
	 * java.lang.Byte=2
	 * java.lang.Short=3
	 * java.lang.Integer=4
	 * java.lang.Long=5
	 * java.lang.Float=6
	 * java.lang.Double=7
	 * java.lang.Boolean=8
	 * java.util.Date=9
	 * java.math.BigDecimal=10
	 * java.math.BigInteger=11
	 * java.sql.Time=12
	 * java.sql.Date=13
	 * java.sql.TimeStamp=14
	 * java.lang.Byte [] =15
	 * </pre>
	 */
	private Integer javaType;  
	private String propertyName; //javaBean属性名
	
	private Integer primaryKey; // 是否是主键：1=是，0=否
	public String getPrimaryKeyDesc() {
		if(this.primaryKey == null) {
			return null;
		}
		
		if(YES == this.primaryKey) {
			return "是";
		}
		else if(NO == this.primaryKey) {
			return "否";
		}
		else {
			return null;
		}
		
	}
	
	
	private Integer oroColumnType; // ORMapping映射的字段类型： gid=1 createTime=2 updateTime=3  普通列=4 主键=5  
	public String getOroColumnTypeDesc() {
		if(this.oroColumnType!=null) {
			if(ORO_COLUMN_TYPE_GID == this.oroColumnType) {
				return "全局唯一码字段";
			}
			else if(ORO_COLUMN_TYPE_CREATE_TIME == this.oroColumnType) {
				return "创建时间字段";
			}
			else if(ORO_COLUMN_TYPE_UPDATE_TIME == this.oroColumnType) {
				return "更新时间字段";
			}
		}
		return null;
	}
	
	
	//private String largeFiled; // 是否是大字段,blob=二进制大字段,clob=字符大字段
	
	private Integer columnCodegenType ; // 字段的代码生成器类型 1=选择器 2=下拉框(字典) 3=外键    4=text 5=long 6=double 7=date 8=file 9=下拉框(常量JSON)
	private String columnCodegenFormat; // 默认：double型的为两个小数点， date 为 "yyyy-MM-dd HH:mm:ss" 
	private String columnCodegenGroupCode ; // 字段组:用于生成html的fieldset框

	public String getColumnCodegenTypeDesc() {
		if(columnCodegenType!=null) {
			if(COLUMN_CODEGEN_TYPE_选择器 == this.columnCodegenType) {
				return "选择器";
			}
			else if(COLUMN_CODEGEN_TYPE_下拉框_字典 == this.columnCodegenType) {
				return "下拉框(字典)";
			}
			/*else if(COLUMN_CODEGEN_TYPE_外键 == this.columnCodegenType) {
				return "外键";
			}*/
			else if(COLUMN_CODEGEN_TYPE_文本 == this.columnCodegenType) {
				return "文本框";
			}
			else if(COLUMN_CODEGEN_TYPE_数字整型 == this.columnCodegenType) {
				return "整数框";
			}
			else if(COLUMN_CODEGEN_TYPE_数字精度型 == this.columnCodegenType) {
				return "小数框";
			}
			else if(COLUMN_CODEGEN_TYPE_日期 == this.columnCodegenType) {
				return "日期框";
			}
			else if(COLUMN_CODEGEN_TYPE_文件上传 == this.columnCodegenType) {
				return "文件框";
			}
			else if(COLUMN_CODEGEN_TYPE_下拉框_常量JSON == this.columnCodegenType) {
				return "下拉框(常量JSON)";
			}
			else{
				return "未知";
			}
		}
		return null;
	}
	

	private String selectorMultilSelect; // （选择器）是否可多选: 1=是 0=否
	private String selectorTextCols;
	private String selectorValueCols;
	
	private String selectorDataFromType; // 选择器数据来源类型:0=URL(页面) 1=URL(返回JSON) 2=URL(返回XML) 3=代码片断(JavaScript)数组  4=SQL 
	private String selectorDataFrom;
	public static final String SELECTOR_DATA_FROM_TYPE_URL_页面=Column.SELECTOR_DATA_FROM_TYPE_URL_页面;
	public static final String SELECTOR_DATA_FROM_TYPE_URL_返回JSON=Column.SELECTOR_DATA_FROM_TYPE_URL_返回JSON;
	public static final String SELECTOR_DATA_FROM_TYPE_URL_返回XML=Column.SELECTOR_DATA_FROM_TYPE_URL_返回XML;
	public static final String SELECTOR_DATA_FROM_TYPE_代码片断_JAVASCRIPT或数组=Column.SELECTOR_DATA_FROM_TYPE_代码片断_JAVASCRIPT或数组;
	public static final String SELECTOR_DATA_FROM_TYPE_SQL=Column.SELECTOR_DATA_FROM_TYPE_SQL;
	
	
	private String selectorDataSourceCode; //选择器的数据来源为SQL时，选定的数据源
	private String selectorDbName; // 选择器的数据来源为SQL时，选定的数据库
	
	
	
	private String dictRefCode; //当前列是字典值时，对应的字典code值，如(值为“sex”就是男、女,对应的值就是1和0)
	private String dictTextCol; //显示的文本字段
	private String dictValueCol; //显示的值字段
	
	
	private String fileMultil; // 文件是否可多选批量上传
	private String fileCustomContent;// 自定义的文件上传控件（可为HTML+JS的代码片断）
	
	private String version;
	private String optLog;
	
	public Integer sn;
	private String remark;
	private String appCode;
	
	private String gid;
	private Date createTime;
	private Date updateTime;
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public Integer getJavaType() {
		return javaType;
	}

	public void setJavaType(Integer javaType) {
		this.javaType = javaType;
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
	/*
	public String getLargeFiled() {
		return largeFiled;
	}

	public void setLargeFiled(String largeFiled) {
		this.largeFiled = largeFiled;
	}
	 */
	public Integer getColumnCodegenType() {
		return columnCodegenType;
	}

	public void setColumnCodegenType(Integer columnCodegenType) {
		this.columnCodegenType = columnCodegenType;
	}
/*
	public String getFkTableName() {
		return fkTableName;
	}

	public void setFkTableName(String fkTableName) {
		this.fkTableName = fkTableName;
	}

	public String getFkTableCommnet() {
		return fkTableCommnet;
	}

	public void setFkTableCommnet(String fkTableCommnet) {
		this.fkTableCommnet = fkTableCommnet;
	}

	public String getFkCol() {
		return fkCol;
	}

	public void setFkCol(String fkCol) {
		this.fkCol = fkCol;
	}
*/
	
/*	public String getSelectorRefTableName() {
		return selectorRefTableName;
	}

	public void setSelectorRefTableName(String selectorRefTableName) {
		this.selectorRefTableName = selectorRefTableName;
	}

	public String getSelectorRefTableComment() {
		return selectorRefTableComment;
	}

	public void setSelectorRefTableComment(String selectorRefTableComment) {
		this.selectorRefTableComment = selectorRefTableComment;
	}

	public String getSelectorRefTableColumn() {
		return selectorRefTableColumn;
	}

	public void setSelectorRefTableColumn(String selectorRefTableColumn) {
		this.selectorRefTableColumn = selectorRefTableColumn;
	}
*/
	public String getSelectorMultilSelect() {
		return selectorMultilSelect;
	}

	public void setSelectorMultilSelect(String selectorMultilSelect) {
		this.selectorMultilSelect = selectorMultilSelect;
	}

	public String getSelectorTextCols() {
		return selectorTextCols;
	}

	public void setSelectorTextCols(String selectorTextCols) {
		this.selectorTextCols = selectorTextCols;
	}

	public String getSelectorValueCols() {
		return selectorValueCols;
	}

	public void setSelectorValueCols(String selectorValueCols) {
		this.selectorValueCols = selectorValueCols;
	}

	public String getDictRefCode() {
		return dictRefCode;
	}

	public void setDictRefCode(String dictRefCode) {
		this.dictRefCode = dictRefCode;
	}

	public String getDictTextCol() {
		return dictTextCol;
	}

	public void setDictTextCol(String dictTextCol) {
		this.dictTextCol = dictTextCol;
	}

	public String getDictValueCol() {
		return dictValueCol;
	}

	public void setDictValueCol(String dictValueCol) {
		this.dictValueCol = dictValueCol;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getOptLog() {
		return optLog;
	}

	public void setOptLog(String optLog) {
		this.optLog = optLog;
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

	public String getColumnCodegenFormat() {
		return columnCodegenFormat;
	}

	public void setColumnCodegenFormat(String columnCodegenFormat) {
		this.columnCodegenFormat = columnCodegenFormat;
	}

	public String getColumnCodegenGroupCode() {
		return columnCodegenGroupCode;
	}

	public void setColumnCodegenGroupCode(String columnCodegenGroupCode) {
		this.columnCodegenGroupCode = columnCodegenGroupCode;
	}

	public Integer getSn() {
		return sn;
	}

	public void setSn(Integer sn) {
		this.sn = sn;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public String getSelectorDataSourceCode() {
		return selectorDataSourceCode;
	}

	public void setSelectorDataSourceCode(String selectorDataSourceCode) {
		this.selectorDataSourceCode = selectorDataSourceCode;
	}

	public String getSelectorDbName() {
		return selectorDbName;
	}

	public void setSelectorDbName(String selectorDbName) {
		this.selectorDbName = selectorDbName;
	}

	public String getSelectorDataFromType() {
		return selectorDataFromType;
	}

	public void setSelectorDataFromType(String selectorDataFromType) {
		this.selectorDataFromType = selectorDataFromType;
	}

	public String getSelectorDataFrom() {
		return selectorDataFrom;
	}

	public void setSelectorDataFrom(String selectorDataFrom) {
		this.selectorDataFrom = selectorDataFrom;
	}

	public String getFileMultil() {
		return fileMultil;
	}

	public void setFileMultil(String fileMultil) {
		this.fileMultil = fileMultil;
	}

	public String getFileCustomContent() {
		return fileCustomContent;
	}

	public void setFileCustomContent(String fileCustomContent) {
		this.fileCustomContent = fileCustomContent;
	}

	public String getDataSourceCode() {
		return dataSourceCode;
	}

	public void setDataSourceCode(String dataSourceCode) {
		this.dataSourceCode = dataSourceCode;
	}

	public String getDbName() {
		return dbName;
	}

	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	public Long getFormId() {
		return formId;
	}

	public void setFormId(Long formId) {
		this.formId = formId;
	}

	public String getFormName() {
		return formName;
	}

	public void setFormName(String formName) {
		this.formName = formName;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public String getFieldCode() {
		return fieldCode;
	}

	public void setFieldCode(String fieldCode) {
		this.fieldCode = fieldCode;
	}

 
}
