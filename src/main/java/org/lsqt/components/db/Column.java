package org.lsqt.components.db;

public class Column {
	public static int JAVA_TYPE_JAVA_LANG_STRING=0;
	public static int JAVA_TYPE_JAVA_LANG_CHARACTER=1;
	
	public static int JAVA_TYPE_JAVA_LANG_BYTE=2;
	public static int JAVA_TYPE_JAVA_LANG_SHORT=3;
	public static int JAVA_TYPE_JAVA_LANG_INTEGER=4;
	public static int JAVA_TYPE_JAVA_LANG_LONG=5;
	public static int JAVA_TYPE_JAVA_LANG_FLOAT=6;
	public static int JAVA_TYPE_JAVA_LANG_DOUBLE=7;
	
	public static int JAVA_TYPE_JAVA_LANG_BOOLEAN=8;
	
	public static int JAVA_TYPE_JAVA_LANG_UTIL_DATE=9;
	
	public static int JAVA_TYPE_JAVA_MATH_BIGDECIMAL=10;
	public static int JAVA_TYPE_JAVA_MATH_BIGINTEGER=11;
	
	public static int JAVA_TYPE_JAVA_SQL_TIME=12;
	public static int JAVA_TYPE_JAVA_SQL_DATE=13;
	public static int JAVA_TYPE_JAVA_SQL_TIMESTAMP=14;
	
	public static int JAVA_TYPE_JAVA_LANG_BYTE_ARRAY=15;
	
	
	// 用于标识查询出来的结果集列，是哪种类型列：id=主键 gid=全局唯一编码 createTime=记录添加时间  updateTime=记录最后更新时间
	public static final String COLUMN_ID="id";
	public static final String COLUMN_GID="gid";
	public static final String COLUMN_CREATE_TIME="createTime";
	public static final String COLUMN_UPDATE_TIME="updateTime";
	
	/**普通列名**/
	private String name;
	
	/**主键列**/
	private String id;
	
	/**主键生成类型:AUTO(自增长)、 UUID64、 UUID58、 NANOTIME(绝对时间戳精确到纳秒)**/
	private String type;
	
	/** 全局编号列 : 数据导入、导出用、对比等**/
	private String gid;
	
	/**更新日期列:**/
	private String updateTime;
	
	/**创建日期列:**/
	private String createTime;
	
	/**实体类的属性*/
	private String property;

	/**字段说明**/
	private String text;

	/**是否是虚拟列**/
	private boolean isVirtual = false;
	
	private String dbType;  // 列对应的数据库类型
	private Integer javaType; // 列对应的java类型
	private String propertyName ; // pojo属性名
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getGid() {
		return gid;
	}

	public void setGid(String gid) {
		this.gid = gid;
	}


	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public boolean getIsVirtual() {
		return isVirtual;
	}

	public void setIsVirtual(boolean isVirtual) {
		this.isVirtual = isVirtual;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getDbType() {
		return dbType;
	}

	public void setDbType(String dbType) {
		this.dbType = dbType;
	}

	public String getPropertyName() {
		return propertyName;
	}

	public void setPropertyName(String propertyName) {
		this.propertyName = propertyName;
	}

	public Integer getJavaType() {
		return javaType;
	}

	public void setJavaType(Integer javaType) {
		this.javaType = javaType;
	}
	 
}