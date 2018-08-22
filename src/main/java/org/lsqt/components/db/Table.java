package org.lsqt.components.db;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author yuanmm
 *
 */
public class Table {
	/**数据库名**/
	private String schema;
	
	/**表名**/
	private String name;
	
	/**表所对应的实体类**/
	private String entityClass;
	
	/**表下的字段定义**/
	private List<Column> columnList = new ArrayList<>();
	
	/**元数据定义的映射文件路径**/
	private String path ;
	
	public Table(String schema,String name,String entityClass,String path){
		this.schema = schema;
		this.name = name;
		this.entityClass = entityClass;
		this.path = path;
	}
	
	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(String entityClass) {
		this.entityClass = entityClass;
	}

	public List<Column> getColumnList() {
		return columnList;
	}

	public void setColumnList(List<Column> columnList) {
		this.columnList = columnList;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

 
}
