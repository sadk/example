package org.lsqt.sys.service.impl;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.lsqt.components.context.annotation.Inject;
import org.lsqt.components.context.annotation.Service;
import org.lsqt.components.db.Db;
import org.lsqt.components.db.Page;
import org.lsqt.components.util.lang.StringUtil;
import org.lsqt.sys.model.Application;
import org.lsqt.sys.model.Column;
import org.lsqt.sys.model.ColumnQuery;
import org.lsqt.sys.model.Dictionary;
import org.lsqt.sys.model.Table;
import org.lsqt.sys.service.ColumnService;

@Service
public class ColumnServiceImpl implements ColumnService{
	
	@Inject private Db db;
	
	public Page<Column>  queryForPage(ColumnQuery query) {
 
		return db.queryForPage("queryForPage", query.getPageIndex(), query.getPageSize(), Column.class, query); // 查sys_table定义的表元信息
	}
	
	public List<Column> getAll(){
		  return db.queryForList("queryForPage", Column.class); 
	}
	
	public Column saveOrUpdate(Column model) {
		/*
		if (model.getId() != null) {
			if(model.getColumnCodegenType()!=null) {
				if (model.getColumnCodegenType() == Column.COLUMN_CODEGEN_TYPE_选择器) {
					if (Column.SELECTOR_DATA_FROM_TYPE_URL_页面.equals(model.getSelectorDataFromType())) {
						db.update(model, "selectorDataFrom","selectorDataFromType");
						
					} else if(Column.SELECTOR_DATA_FROM_TYPE_URL_返回JSON.equals(model.getSelectorDataFromType())
							|| Column.SELECTOR_DATA_FROM_TYPE_URL_返回XML.equals(model.getSelectorDataFromType())
							|| Column.SELECTOR_DATA_FROM_TYPE_代码片断_JAVASCRIPT或数组.equals(model.getSelectorDataFromType())) {
						
						if (StringUtil.isBlank(model.getSelectorMultilSelect())){//默认可多选
							model.setSelectorMultilSelect(Column.YES+"");
						}
						db.update(model, "selectorDataFrom","selectorMultilSelect","selectorTextCols","selectorValueCols","selectorDataFromType");
						
						
					} else if(Column.SELECTOR_DATA_FROM_TYPE_SQL.equals(model.getSelectorDataFromType())) {
						if (StringUtil.isBlank(model.getSelectorMultilSelect())){//默认可多选
							model.setSelectorMultilSelect(Column.YES+"");
						}
						if (StringUtil.isNotBlank(model.getSelectorValueCols())) {
							// 值列只能是一个,如果前端不小心填了多列，以第一个为准
							if(model.getSelectorValueCols().indexOf(",")!=-1) {
								String tmp = model.getSelectorValueCols().split(",")[0];
								model.setSelectorValueCols(tmp);
							}
						}
						db.update(model, "selectorDataFrom","selectorMultilSelect","selectorTextCols","selectorValueCols","selectorDataFromType");
						
					}
				}
			}
			return model;
		} else {
			db.save(model);
			return model;
		}*/
		return db.saveOrUpdate(model);
	}

	public int deleteById(Long ... ids) {
		return db.deleteById(Column.class, Arrays.asList(ids).toArray());
	}

	public List<Column> getAllFormDb(ColumnQuery query){
		return db.queryForList("getAllFromDb", Column.class, query); // 从数据库里即时查表的元信息
	}

	List<Column> getColumnList(Long tableId) {
		List<Column> result = new ArrayList<Column>();
		
		Table table = db.getById(Table.class, tableId);
		
		Connection con = db.getCurrentConnection();
		try {
			DataSourceFactory factory = new DataSourceFactory();
			factory.setBaseDb(db);

			javax.sql.DataSource ds = factory.getDataSouce(table.getDataSourceCode());
			db.setCurrentConnection(ds.getConnection());
			
			try {
				result = db.queryForList("queryColumnsByTable", Column.class, table.getDbName(),table.getTableName());
			} finally {
				db.close();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			db.setCurrentConnection(con);
		}
		return result;
	}
	
	@Override
	public void impColumsByTable(Long tableId) {
		Table table = db.getById(Table.class, tableId);
		/*
		List<Column> list = db.queryForList("queryColumnsByTable", Column.class, table.getDbName(),table.getTableName());
		*/
		
		List<Column> list = getColumnList(tableId);
		if(list == null || list.isEmpty()) {
			return ;
		}
		
		db.executeUpdate("delete from "+db.getFullTable(Column.class)+" where table_id=?", tableId);
		
		// 补全字段 java_type
		// 补全字段 oro_column_type  (ORMapping映射的字段类型)：gid=全局唯一码 updateTime=更新时间 createTime=创建时间
		// 补全字段 columnCodegenType 字段的代码生成器类型 1=选择器 2=字典 3=外键 4=text 5=long 6=double 7=date 8=file
		
		// 补全外键 、选择器、字典信息
		for(Column e: list) {
			e.setTableId(tableId);
			e.setTableName(table.getTableName());
			e.setDbName(table.getDbName());
			e.setVersion(table.getVersion());
			e.setPropertyName(ColumnUtil.toPropertyName(e.getName()));
			e.setOptLog("从DB导入字段信息");
			
			if(StringUtil.isBlank(e.getAppCode())){
				e.setAppCode(Application.APP_CODE_DEFAULT);
			}
			
			resolveJavaType(e);
			resolveOroColumnType(e);
			resolveColumnCodegenType(e);
			resolveSearchType(e);
			db.save(e);
		}
		
	}
	
	private void resolveSearchType(Column e) {
		if(Column.YES == e.getPrimaryKey()) {
			e.setSearchType(Column.NO);
			return ;
		}
		
		// 字符默认可查询
		if(e.getJavaType() == Column.JAVA_TYPE_JAVA_LANG_STRING) {
			if(e.getOroColumnType()!=Column.ORO_COLUMN_TYPE_GID) {
				e.setSearchType(Column.YES);
			}
		}
		
		e.setSearchType(Column.NO);
	}
	
	private void resolveJavaType(Column e) {
		if(StringUtil.isNotBlank(e.getDbType())){
			e.setJavaType(MySQLTypeMapping.guessJavaType(e.getDbType()));
		}
	}
	
	private void resolveOroColumnType(Column e) {
		// 优先从注释里解析gid、createTime、updateTime
		if (StringUtil.isNotBlank(e.getComment())) {
			if(e.getComment().startsWith("gid")) {
				e.setOroColumnType(Column.ORO_COLUMN_TYPE_GID);
				return ;
			}
			else if(e.getComment().startsWith("createTime")) {
				e.setOroColumnType(Column.ORO_COLUMN_TYPE_CREATE_TIME);
				return ;
			}
			else if(e.getComment().startsWith("updateTime")) {
				e.setOroColumnType(Column.ORO_COLUMN_TYPE_UPDATE_TIME);
				return ;
			}
		}
		
		if("createTime".equals(e.getPropertyName())){
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_CREATE_TIME);
			return ;
		}
		if("updateTime".equals(e.getPropertyName())) {
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_UPDATE_TIME);
			return ;
		}
		if("gid".equals(e.getPropertyName())) {
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_GID);
			return ;
		}
		
		// 解析普通与主键映射类型
		if(e.getPrimaryKey()!=null && e.getPrimaryKey() == Column.NO) {
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_ORDINARY);
		}
		else if(e.getPrimaryKey()!=null && e.getPrimaryKey() == Column.YES) {
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_PRIMARY);
		}
		
		// 解析不到，就视为常规字段
		else {
			e.setOroColumnType(Column.ORO_COLUMN_TYPE_ORDINARY);
		}
	}
	
	private void resolveColumnCodegenType(Column e) {
		if(StringUtil.isNotBlank(e.getDbType())) {
			
			Set<Entry<String,String>> set =MySQLTypeMapping.DB_JAVA_MAPPING.entrySet();
			for(Entry<String,String> m: set) {
				if(e.getDbType().equals(m.getKey())) {
					String javaType = m.getValue();
					if("java.lang.String".equals(javaType)) {
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_文本);
					}
					else if("java.lang.Integer".equals(javaType) || "java.lang.Long".equals(javaType)) {
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_数字整型);
					}
					else if("java.lang.Double".equals(javaType)|| "java.lang.Float".equals(javaType)){
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_数字精度型);
					}
					else if("java.util.Date".equals(javaType)) {
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_日期);
						e.setColumnCodegenFormat("yyyy-MM-dd HH:mm:ss");
					}
					else if("java.lang.byte[]".equals(javaType)) {
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_文件上传);
					}
					else {
						e.setColumnCodegenType(Column.COLUMN_CODEGEN_TYPE_未知);
					}
				}
			}
			
		}
		
	}
}

class ColumnUtil {
	private static final Pattern pattern = Pattern.compile("_[a-z]{1}");
	private static final Pattern dePattern = Pattern.compile("[a-z]{1}[A-Z]{1}");
	
	/**
	 * 下划线转驼峰
	 * @param columnName
	 * @return
	 */
	public static String toPropertyName(String columnName) {
		if(StringUtil.isBlank(columnName)){
			return columnName;
		}
		Matcher m = pattern.matcher(columnName);
		while(m.find()) {
			String f = m.group();
			columnName = columnName.replace(f, f.replace("_", "").toUpperCase());
		}
		return columnName;
	}
	
	/**
	 * 驼峰转下划线
	 * @param propertyName
	 * @return
	 */
	public static String toDbColumn(String propertyName) {
		if(StringUtil.isBlank(propertyName)){
			return propertyName;
		}
		Matcher m = dePattern.matcher(propertyName);
		while(m.find()) {
			String f = m.group();
			String upperString = String.valueOf(f.charAt(1));
			String value = f.replace(upperString, "_"+upperString.toLowerCase());
			propertyName = propertyName.replace(f, value);
		}
		return propertyName;
	}
}

class MySQLTypeMapping {
	static final Map<String,String> DB_JAVA_MAPPING = new LinkedHashMap<>();
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

class OracleTypeMapping{
	
}

class SqlServerTypeMapping{
	
}

