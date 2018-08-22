package org.lsqt.components.db.orm;

import org.lsqt.components.db.Table;

/**
 * 映射的SQL语句对象
 * 
 * @author Sky
 *
 */
public class SqlStatement {
	public static final String SOURCE_TYPE_XML = "xml"; // 当前对象由xml文件定义
	public static final String SOURCE_TYPE_DB = "db"; // 当前对象由db里定义产生

	private String id;
	private String namespace;

	private String parameterName; // 参数名
	private String sqlTemplateContent; // sql语句模板(未通过freemark或velocity解析的)

	
	private boolean cache;

	private String importClazz;

	private String path; // 定义所在的文件路径

	private String sourceType = SOURCE_TYPE_XML;

	private Table table;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNamespace() {
		return namespace;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public String getParameterName() {
		return parameterName;
	}

	public void setParameterName(String parameterName) {
		this.parameterName = parameterName;
	}

	public String getSqlTemplateContent() {
		return sqlTemplateContent;
	}

	public void setSqlTemplateContent(String sqlTemplateContent) {
		this.sqlTemplateContent = sqlTemplateContent;
	}

	public boolean isCache() {
		return cache;
	}

	public void setCache(boolean cache) {
		this.cache = cache;
	}

	public String getImportClazz() {
		return importClazz;
	}

	public void setImportClazz(String importClazz) {
		this.importClazz = importClazz;
	}

	public String getSourceType() {
		return sourceType;
	}

	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Table getTable() {
		return table;
	}

	public void setTable(Table table) {
		this.table = table;
	}
}