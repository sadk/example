package org.lsqt.components.db.orm;

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
	
	public static class Column {
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
