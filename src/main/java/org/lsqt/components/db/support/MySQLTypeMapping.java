package org.lsqt.components.db.support;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

import org.lsqt.components.db.Column;

import java.util.Map.Entry;


public final class MySQLTypeMapping {
	public static final Map<String/*数据库类型*/,String /*Java类型*/> DB_JAVA_MAPPING = new LinkedHashMap<>();
	static{
		// 字符型
		DB_JAVA_MAPPING.put("char", "java.lang.String");
		DB_JAVA_MAPPING.put("varchar", "java.lang.String");
		DB_JAVA_MAPPING.put("text", "java.lang.String");
		DB_JAVA_MAPPING.put("longtext", "java.lang.String");
		DB_JAVA_MAPPING.put("mediumtext", "java.lang.String");
		DB_JAVA_MAPPING.put("tinytext", "java.lang.String");
		
		// 数字型
		DB_JAVA_MAPPING.put("int", "java.lang.Integer");
		DB_JAVA_MAPPING.put("smallint", "java.lang.Integer");
		DB_JAVA_MAPPING.put("tinyint", "java.lang.Integer");
		DB_JAVA_MAPPING.put("mediumint", "java.lang.Integer");
		DB_JAVA_MAPPING.put("bigint", "java.lang.Long");
		DB_JAVA_MAPPING.put("float", "java.lang.Float");
		DB_JAVA_MAPPING.put("decimal", "java.lang.Double");
		DB_JAVA_MAPPING.put("double", "java.lang.Double");
		DB_JAVA_MAPPING.put("numeric", "java.lang.Double");
		
		// 日期型
		DB_JAVA_MAPPING.put("date", "java.util.Date");
		DB_JAVA_MAPPING.put("datetime", "java.util.Date");
		DB_JAVA_MAPPING.put("time", "java.util.Date");
		DB_JAVA_MAPPING.put("timestamp", "java.util.Date");
		DB_JAVA_MAPPING.put("year", "java.util.Date");
		
		// Boolean型
		DB_JAVA_MAPPING.put("bit", "java.lang.Boolean");
		DB_JAVA_MAPPING.put("bool", "java.lang.Boolean");
		DB_JAVA_MAPPING.put("boolean", "java.lang.Boolean");
		
		// 大字段
		DB_JAVA_MAPPING.put("blob", "java.lang.Byte []");
		DB_JAVA_MAPPING.put("longblob", "java.lang.Byte []");
		DB_JAVA_MAPPING.put("mediumblob", "java.lang.Byte []");
		DB_JAVA_MAPPING.put("tinyblob", "java.lang.Byte []");
	}
	
	 
	
	public static Integer guessJavaType(String dbType) {
		Set<Entry<String, String>> set = DB_JAVA_MAPPING.entrySet();
		for (Entry<String, String> e : set) {
			if (dbType.startsWith(e.getKey())) {
				if("java.lang.String".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_LANG_STRING;
				}
				else if("java.lang.Character".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_LANG_CHARACTER;
				}
				else if("java.lang.Byte".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_LANG_BYTE;
				}
				else if("java.lang.Short".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_LANG_SHORT;
				}
				else if("java.lang.Integer".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_LANG_INTEGER;
				}
				else if("java.lang.Long".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_LANG_LONG;
				}
				else if("java.lang.Float".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_LANG_FLOAT;
				}
				else if("java.lang.Double".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_LANG_DOUBLE;
				}
				else if("java.lang.Boolean".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_LANG_BOOLEAN;
				}
				else if("java.util.Date".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_LANG_UTIL_DATE;
				}
				else if("java.math.BigDecimal".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_MATH_BIGDECIMAL;
				}
				else if("java.math.BigInteger".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_MATH_BIGINTEGER;
				}
				else if("java.sql.Time".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_SQL_TIME;
				}
				else if("java.sql.Date".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_SQL_DATE;
				}
				else if("java.sql.Timestamp".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_SQL_TIMESTAMP;
				}
				else if("java.lang.Byte []".equals(e.getValue())) {
					return Column.JAVA_TYPE_JAVA_LANG_BYTE_ARRAY;
				}
			}
		}
		return null;
	}
}