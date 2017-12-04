package org.lsqt.syswin;

import java.io.BufferedWriter;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.sql.DataSource;

import org.lsqt.components.cache.Cache;
import org.lsqt.components.cache.SimpleCache;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.IdGenerator;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.Plan;
import org.lsqt.components.db.orm.ORMappingIdGenerator;
import org.lsqt.components.db.orm.SqlStatement;
import org.lsqt.components.db.orm.SqlStatementBuilder;
import org.lsqt.components.db.orm.Table;
import org.lsqt.components.db.orm.Table.Column;
import org.lsqt.components.db.orm.util.ModelUtil;
import org.lsqt.components.util.bean.BeanUtil;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.Md5Util;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.cache.StringTemplateLoader;
import freemarker.ext.beans.BeansWrapper;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateHashModel;
import freemarker.template.TemplateModelException;

/**
 * 专用于连接平台库的DB操作类
 * @author admin
 *
 */
public class PlatformDb {
	
	static final String formatStr = " --- %s -- ===> %s";

	static final Logger log = LoggerFactory.getLogger(PlatformDb.class);

	final JDBCExecutor exe = new JDBCExecutor();

	final IdGenerator idGenerator = new ORMappingIdGenerator();
	
	private Cache jdbcCache  = new SimpleCache();
	
	private SqlStatementBuilder sqlStatementBuilder;
	public PlatformDb(){}
	public PlatformDb(SqlStatementBuilder sqlStatementBuilder) {
		this.sqlStatementBuilder = sqlStatementBuilder;
	}
	
	public void setSqlStatementBuilder(SqlStatementBuilder sqlStatementBuilder) {
		this.sqlStatementBuilder = sqlStatementBuilder;
	}
	static Configuration FTL_CONFIG ;
	static {
		FTL_CONFIG = new Configuration();
		FTL_CONFIG.setDefaultEncoding("UTF-8");
		//FTL_CONFIG.setClassicCompatible(true);//处理空值为空字符串  
		FTL_CONFIG.setNumberFormat("#"); // 数字格式化不打逗号,如: 123,335,23
	}
	static StringTemplateLoader FTL_STRINGLOADER = new StringTemplateLoader();
	
	public void threadConnectionDestory() {
		try {
			if (null == exe.getCurrentConnection()) {
				log.warn(" --- No thread connection was found for the current binding~!");
			}
			
			Connection con = exe.getCurrentConnection();
			log.debug(" --- >>>>>>>>>>>> thread-id:"+Thread.currentThread().getId()+", >>threadConnectionDestory ing...  "+con);
			
			if(con!=null && !con.isClosed() && !con.getAutoCommit()){
				con.commit();
			}
			exe.close(null, null, con);
			log.debug(" --- >>>>>>>>>>>> thread-id:"+Thread.currentThread().getId()+", >>threadConnectionDestoryed ...  "+con);
		} catch (Exception e) {
			throw new DbException("close db resource fail~!", e);
		}
	}
	
	
	public void setConfigDataSource(DataSource dataSource) {
		exe.setConfigDataSource(dataSource);
	}

	public void setCurrentConnection(Connection connection) {
		exe.setCurrentConnection(connection);
	}
	
	public Connection getCurrentConnection() {
		return exe.getCurrentConnection();
	}
	
	public String getFullTable(Class<?> requiredType) {
		/* bug fix :如果没有映射sql语名，只有列映射信息,将获取不到完整DB表名
		List<SqlStatement> list = sqlStatementBuilder.getAllStatement();
		String clazz = requiredType.getName();
		for (SqlStatement e : list) {
			if (e.getTable() != null  && clazz.equals(e.getTable().getEntityClass())) {
				String schema = e.getTable().getSchema();
				return StringUtil.isBlank(schema) ? e.getTable().getName() : schema + "." + e.getTable().getName();
			}
		}*/
		List<Table> tableList = sqlStatementBuilder.getAllTable();
		for(Table t: tableList) {
			if(requiredType.getName().equals(t.getEntityClass())){
				String schema = t.getSchema();
				return StringUtil.isBlank(schema) ? t.getName() : schema + "." + t.getName();
			}
		}
		return null;
	}
	
	
	public String getFullTable(Table table) {
		if (table == null){
			return null;
		}
		
		String schema = table.getSchema();
		return StringUtil.isBlank(schema) ? table.getName() : schema + "." + table.getName();
	}
	
	public String getColumn(Class<?> requiredType, String prop) {
		List<SqlStatement> list = sqlStatementBuilder.getAllStatement();
		String clazz = requiredType.getName();
		for (SqlStatement e : list) {
			if (e.getTable() == null || (!clazz.equals(e.getTable().getEntityClass()))){
				continue;
			}

			List<Column> cols = e.getTable().getColumnList();
			if (cols == null || cols.isEmpty()) {
				continue;
			}

			for (Column c : cols) {
				if (c.getProperty().equals(prop)) {
					return getSqlColumn(c);
				}
			}
		}
		return null;
	}
	
	/**
	 * 获取数据库字段
	 * @param c
	 * @return
	 */
	private String getSqlColumn(Column c) { // id、gid、updateTime,createTime、name
		if (StringUtil.isNotBlank(c.getId())) {
			return c.getId();
		} else if (StringUtil.isNotBlank(c.getGid())) {
			return c.getGid();
		} else if (StringUtil.isNotBlank(c.getCreateTime())) {
			return c.getCreateTime();
		} else if (StringUtil.isNotBlank(c.getUpdateTime())) {
			return c.getUpdateTime();
		} else if (StringUtil.isNotBlank(c.getName())) {
			return c.getName();
		}
		return null;
	}
	
	/**
	 * 获取数据库表某字段对应的实体字段值
	 * @param entity
	 * @param column
	 * @return
	 */
	private Object getSqlColumnValue(Object entity, Column column) {
		try{
			if (StringUtil.isNotBlank(column.getId())) { // id、gid、lastModified、name
				Object id = null;
				if (IdGenerator.ID_TYPE_NANOTIME.equals(column.getType())) {
					id = System.currentTimeMillis();
					
				} 
				else if (IdGenerator.ID_TYPE_UUID64.equals(column.getType())) {
					id = idGenerator.getUUID64();
					
				} 
				else if (IdGenerator.ID_TYPE_UUID58.equals(column.getType())) {
					id = idGenerator.getUUID58();
					
				} /* else if (ID_TYPE_AUTO.equals(column.getType())) {
					
				} 
				*/ 
				
				BeanUtil.forceSetProperty(entity, column.getProperty(), id);
				return id;
				
	 		} else if (StringUtil.isNotBlank(column.getGid())) {
	 			Object gid = idGenerator.getUUID58();
	 			BeanUtil.forceSetProperty(entity, column.getProperty(), gid);
	 			return gid;
	 			
			} else if (StringUtil.isNotBlank(column.getUpdateTime())) {
				Date updateTime = new Date();
				BeanUtil.forceSetProperty(entity, column.getProperty(), updateTime);
				return updateTime;
				
			} else if (StringUtil.isNotBlank(column.getCreateTime())) {
				Date createTime = new Date();
				BeanUtil.forceSetProperty(entity, column.getProperty(), createTime);
				return createTime;
				
			} else if (StringUtil.isNotBlank(column.getName())) {
				return BeanUtil.forceGetProperty(entity, column.getProperty());
			}
		}catch(Exception ex){
			throw new DbException(ex);
		}
		
		return null;
	}
	
	public static boolean isIdAuto(Column col) {
		if (StringUtil.isNotBlank(col.getId())) {
			if (StringUtil.isBlank(col.getType())) {
				return true;
			}

			if (IdGenerator.ID_TYPE_AUTO.equals(col.getType())) {
				return true;
			}
		}
		return false;
	}
	
	private Map<String, Object> prepareInsertColumn(Table table, Object entity, String... prop) {
		Map<String, Object> columnValues = new LinkedHashMap<>(); // 必须使用有序的,db缓存用到sql散列

		if (table.getColumnList() == null || table.getColumnList().isEmpty()) {
			return columnValues;
		}

		boolean isFullColumn = (prop == null || prop.length == 0);
		 
		for (Table.Column col : table.getColumnList()) {
			if (isFullColumn) {
				
				if(StringUtil.isNotBlank(col.getId())) { // id
					if (isIdAuto(col))	continue; // id： 自增长
					
					columnValues.put(col.getId(), getSqlColumnValue(entity, col));
					
				} else if (StringUtil.isNotBlank(col.getGid())) { //gid
					 
					columnValues.put(col.getGid(), getSqlColumnValue(entity, col));
					
				} else if ( StringUtil.isNotBlank(col.getUpdateTime())) { // updateTime
					
					columnValues.put(col.getUpdateTime(), getSqlColumnValue(entity, col));
					
				} else if ( StringUtil.isNotBlank(col.getCreateTime())) { // createTime
					
					columnValues.put(col.getCreateTime(), getSqlColumnValue(entity, col));
					
				} else { // 常规列
					
					if (!col.getIsVirtual()){ //虚拟的列（不是数据库物理表字段，只是一个查询字段，不做插入）
						columnValues.put(col.getName(), getSqlColumnValue(entity, col));
					}
				}
				
			} else {
				for (String p : prop) {
					if (col.getProperty().equals(p)) {
						
						// 自动增长，不需要赋值
						if ((StringUtil.isBlank(col.getId()) && IdGenerator.ID_TYPE_AUTO.equals(col.getId()))) {
							continue;
						}
						
						// 虚拟列，不需要赋值
						if (col.getIsVirtual()) {
							continue;
						}
						
						columnValues.put(getSqlColumn(col), getSqlColumnValue(entity, col));
						break;
					}
				}
			}
		}
		
		return columnValues;
	}
	 
	
	public Serializable save(Object pojo, String... prop) throws DbException {
		final String sqlFmt = "insert into %s (%s) values (%s)";
		
		List<Table> list = sqlStatementBuilder.getAllTable();
		String clazz = pojo.getClass().getName();
		Table table = null;
		for (Table e : list) {
			if (clazz.equals(e.getEntityClass())) {
				table = e;
				break;
			}
		}
		
		if (table == null) {
			throw new DbException("not found ORMapping file for entity : "+pojo);
			//return StringUtil.EMPTY;
		}
		
		Map<String,Object> columnValues = prepareInsertColumn(table,pojo,prop);
		if (columnValues==null || columnValues.isEmpty()) {
			return null;
		}
		
		StringBuilder columnSQL = new StringBuilder();
		StringBuilder columnParam = new StringBuilder();
		
		List<Object> args = new ArrayList<>();
		int i=0;
		
		Set<Entry<String, Object>>  set = columnValues.entrySet();
		for (Entry<String,Object> e: set) {
			columnSQL.append(e.getKey());
			columnParam.append("?");
			if (i != columnValues.size() - 1) {
				columnSQL.append(",");
				columnParam.append(",");
			}

			args.add(e.getValue());
			
			i++;
		}
		
		final String sql = String.format(sqlFmt, getFullTable(pojo.getClass()),columnSQL.toString(),columnParam.toString());
		Serializable id = exe.executeUpdate(sql, args.toArray());
		
		for (Column c : table.getColumnList()) {
			if(isIdAuto(c)){
				try {
					BeanUtil.forceSetProperty(pojo, c.getProperty(), id);
					break;
				} catch (Exception ex) {
					throw new DbException(ex);
				}  
			}
		}
		return id;
	}

	public <T> T saveOrUpdate(T model, String... props) throws DbException {
		Object id = getIdValue(model);
		if (id == null) {
			save(model, props);
		} else {
			update(model, props);
		}
		return model;
	}
	
	private Object getIdValue(Object model) {
		List<Table> list = sqlStatementBuilder.getAllTable();
		if (list == null || list.size()==0) return null;
		
		Table table = null;
		for (Table t : list) {
			List<Column> cols = t.getColumnList();
			if(cols==null || cols.isEmpty()) continue;
			
			if(t.getEntityClass().equals(model.getClass().getName())) {
				table = t;
				break;
			}
			
		}
		
		if(table == null) return null;
		
		for (Column c : table.getColumnList()) {
			if (StringUtil.isNotBlank(c.getId())) {
				try {
					return BeanUtil.forceGetProperty(model, c.getProperty());
				} catch (Exception e) {
					 throw new DbException(e);
				}  
			}
		}
		return null;
	}
	
	public int delete(Object... pojo) {
		if (pojo == null || pojo.length ==0) return 0;
		
		int cnt = 0;
		if (pojo.length == 1) {
			cnt += deleteById(pojo[0].getClass(), getIdValue(pojo[0]));
			return cnt;
		} 

		// 批量删除
		boolean isSameClazz = true;
		String clazz = pojo[0].getClass().getName();
		for (int i=1;i<pojo.length;i++) {
			if (!clazz.equals(pojo[i].getClass().getName())) {
				isSameClazz = false;
				break;
			}
		}
		
		if (isSameClazz) { // 删除的是同一张表的多个记录
			Table table = getTable(pojo[0].getClass());
			if(table == null) {
				throw new DbException("没有找到数据库表的映射("+pojo[0].getClass().getName()+")~!");
			}
			
			Column idColumn = getIdColumn(table);
			if(idColumn == null) {
				throw new DbException("没有找到映射的主键("+table.getEntityClass()+")");
			}
			
			String sqlFmt = "delete from %s where %s in (";
			StringBuilder sqlHolds = new StringBuilder();
			List<Object> args = new ArrayList<>();
			for (int i = 0; i < pojo.length; i++) {
				try {
					sqlHolds.append("?");
					args.add(BeanUtil.forceGetProperty(pojo[i], idColumn.getProperty()));
					
					if (i != pojo.length - 1) {
						sqlHolds.append(",");
					}
				} catch (Exception e) {
					throw new DbException(e);
				}
			}
			sqlFmt = sqlFmt.concat(sqlHolds.toString()).concat(")");
			
			String sql = String.format(sqlFmt, getFullTable(table),idColumn.getId());
			
			return exe.executeUpdate(sql, args.toArray());
			
		} else {
			
			for (Object e : pojo) {
				try {
					Table table = getTable(e.getClass());
					if(table == null) {
						throw new DbException("没有找到数据库表的映射("+pojo[0].getClass().getName()+")~!");
					}
					
					Column idColumn = getIdColumn(table);
					if(idColumn == null) {
						throw new DbException("没有找到映射的主键("+table.getEntityClass()+")");
					}
					
					cnt += deleteById(e.getClass(), BeanUtil.forceGetProperty(e, idColumn.getProperty()));
				} catch (Exception ex) {
					throw new DbException(ex);
				}
			}
			
			PlatformDb.this.jdbcCache.clear();
			return cnt;
		}
		 
		
	}

	
	public int deleteById(Class<?> type, Object... id) {
		if (id.length == 0) {
			throw new DbException("id不能为空");
		}

		final Table table = getTable(type);

		if (table == null) {
			return 0;
		}

		String sqlFmt = "";
		if (id.length == 1) {
			sqlFmt = "delete from %s where %s=?";
		} else {
			sqlFmt = "delete from %s where %s in (";
			StringBuilder sqlHolds = new StringBuilder();
			for (int i = 0; i < id.length; i++) {
				sqlHolds.append("?");
				if (i != id.length - 1) {
					sqlHolds.append(",");
				}
			}
			sqlFmt = sqlFmt.concat(sqlHolds.toString()).concat(")");
		}

		Column idCol = getIdColumn(table);

		if (idCol == null) {
			throw new DbException("没有定义id,映射文件:[" + table.getPath() + "]");
		}

		String sql = String.format(sqlFmt, getFullTable(table), idCol.getId());

		return exe.executeUpdate(sql, id);
	}

	private Table getTable(Class<?> type) {
		List<Table> list = sqlStatementBuilder.getAllTable();
		String clazz = type.getName();
		Table table = null;
		for (Table e : list) {
			if (clazz.equals(e.getEntityClass())) {
				table = e;
				break;
			}
		}
		return table;
	}

	private Column getIdColumn(Table table) {
		if(table == null || table.getColumnList() == null) {
			return null;
		}
		
		for (Column col : table.getColumnList()) {
			if (StringUtil.isNotBlank(col.getId())) {
				return col;
			}
		}
		
		// 整表没有找到id，默认以id属性对应的列为主键?
		
		return null;
	}
 
	
	public <T> T getById(Class<T> type, Object id) {
		String sqlFmt = "select * from %s where %s=?";
		Table table = getTable(type);
		if (table == null) {
			throw new DbException("没有找到映射文件(" + type.getName() + ")~!");
		}
		
		Column idColumn = getIdColumn(table);
		if (idColumn == null) {
			throw new DbException("have no define id column,please check entity ORMapping :"+table.getEntityClass());
		}
		
		String sql = String.format(sqlFmt, getFullTable(table),idColumn.getId());
		List<Map<String, Object>> rs = exe.executeQuery(sql, id); // key=数据库字段，value=字段值
		
		if (rs == null || rs.isEmpty()) {
			return null;
		}
		
		 
		Map<String, Object> row = rs.get(0);
		try {
			return toModel(type, row);
		} catch (Exception ex) {
			throw new DbException(ex);
		}
	}
 
	@SuppressWarnings("unchecked")
	private  <T> T toModel(Class<T> type,Map<String, Object> row)  {
		try{
			Object entity = type.newInstance();
			Table table = getTable(type);
			if (table == null) {
				throw new DbException("没有找到映射文件~!");
			}
			
			if (table.getColumnList() == null || table.getColumnList().isEmpty()) {
				log.warn("没有定义映射文件与数据库表的映射元信息~!");
				return null;
			}
			
			for (Column col : table.getColumnList()) {
				//BeanUtil.forceSetProperty(entity, col.getProperty(), getPropertyValue(row, col));
				ModelUtil.forceSetPropertyValue(entity, col.getProperty(), getPropertyValue(row, col));
			}
			return (T) entity;
		}catch(Exception ex) {
			throw new DbException(ex);
		}
	}
	
	private Object getPropertyValue(Map<String, Object> record, Column col) {
		if (record.isEmpty()) {
			return null;
		}

		Set<Entry<String, Object>> set = record.entrySet();
		for (Entry<String, Object> e : set) {
			if (StringUtil.isNotBlank(col.getId()) && e.getKey().equals(col.getId())) { // id
				return e.getValue();
			} else if (StringUtil.isNotBlank(col.getGid()) && e.getKey().equals(col.getGid())) { // gid
				return e.getValue();
			} else if (StringUtil.isNotBlank(col.getUpdateTime()) && e.getKey().equals(col.getUpdateTime())) { // updateTime
				return e.getValue();
			} else if (StringUtil.isNotBlank(col.getCreateTime()) && e.getKey().equals(col.getCreateTime())) { // createTime
				return e.getValue();
			} else if (e.getKey().equals(col.getName())) { // 普通字段
				return e.getValue();
			} else if (e.getKey().equalsIgnoreCase(col.getName())) { // 不区分大小写
				return e.getValue();
			}
		}
		return null;
	}
	
	public <T> T update(T pojo, String... prop) throws DbException {
		if(prop == null) {
			log.warn("入参值为null");
			return (T)pojo;
		}
		
		String sqlFmt = "update %s set %s where %s=?";
		Table table = getTable(pojo.getClass());
		if (table == null) {
			throw new DbException("没有找到映射文件(" + pojo.getClass().getName() + ")~!");
		}
		
		StringBuilder updateSQL= new StringBuilder();
		List<Object> args = new ArrayList<>();
		
		final boolean isFullUpdate = (prop.length == 0);
		
		for (int n = 0; n < table.getColumnList().size(); n++) {
			final Column column = table.getColumnList().get(n);
			
			if(column.getIsVirtual()) { // 虚似字段不做更新
				continue ;
			}
			
			if(StringUtil.isNotBlank(column.getCreateTime())) { //创建时间不做更新
				continue;
			}
			
			try {
				if (isFullUpdate) { // 1.更新全部的映射字段 (除了id、gid、creatTime)
					String dbColum = "";
					
					if (StringUtil.isNotBlank(column.getUpdateTime())) { // updateTime
						dbColum = column.getUpdateTime();
						updateSQL.append(dbColum + "=?,");
						
						Object value = new Date();
						BeanUtil.forceSetProperty(pojo,column.getProperty(),value);
						
						args.add(value);
						
						
					} 
					
					else if (StringUtil.isNotBlank(column.getName())) { // 普通字段
						dbColum = column.getName();
						updateSQL.append(dbColum + "=?,");
						
						args.add(BeanUtil.forceGetProperty(pojo, column.getProperty()));
						
					}

				} else {
					// 2.更新指定字段(理论上用户强行更新id、gid、updateTime、createTime是允许的!!!
					for (String p : prop) {
						if (p.equals(column.getProperty())) {
							String dbColum = "";
							if (StringUtil.isNotBlank(column.getId())) { // id
								 dbColum = column.getId();
							} 
							else if(StringUtil.isNotBlank(column.getGid())) { // gid
								 dbColum = column.getGid();
							} 
							else if (StringUtil.isNotBlank(column.getCreateTime())) { // createTime
								dbColum = column.getCreateTime();
							} 
							else if (StringUtil.isNotBlank(column.getUpdateTime())) { // updateTime
								 dbColum = column.getUpdateTime();
							}
							else if (StringUtil.isNotBlank(column.getName())) { // 普通字段
								dbColum = column.getName();
							} else {
								throw new DbException("没有找到映射的列~!");
							}
							updateSQL.append(dbColum + "=?,");
							args.add(BeanUtil.forceGetProperty(pojo, p));
							break;
						}
					}
				}
			} catch (Exception ex) {
				throw new DbException(ex);
			}
		}
		
		String updateColString = updateSQL.toString();
		if (updateColString.endsWith(",")) {
			updateColString = updateColString.substring(0, updateColString.length() - 1);
		}
		
		Column idColumn=getIdColumn(table);
		if(idColumn == null) {
			log.warn("id have no defined ,please check oroMapping file:"+table.getPath());
		}
		String sql = String.format(sqlFmt, getFullTable(table), updateColString, idColumn == null ? null:idColumn.getId());
		args.add(getIdValue(pojo));

		exe.executeUpdate(sql, args.toArray());

		return (T) pojo;
	}

	public <T> T executeUpdate(String sql, Object... args) throws DbException {
		return exe.executeUpdate(sql, args);
	}
	
	


	private SqlStatement getSqlStatement(String nameSpace,String sqlID) {
		List<SqlStatement> list = sqlStatementBuilder.getAllStatement();
		if (list == null || list.isEmpty()) {
			return null;
		}

		
		for (SqlStatement e : list) {
			if (e.getNamespace().equals(nameSpace) && sqlID.equals(e.getId())) {
				return e;
			}
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T executeQueryForObject(String sql,Class<T> requiredType,Object ...args) throws DbException {
		List<Map<String, Object>> data = exe.executeQuery(sql,args);
		if (data == null || data.isEmpty()) {
			return null;
		}
		
		// 可是以map
		if (Map.class.isAssignableFrom(requiredType)) {
			return (T) data.get(0);
		}
		
		// 可以是常规类型
		if (ModelUtil.isCanBeBaseType(requiredType)) {
			Collection<T> values = (Collection<T>) data.get(0).values();
			if (values == null || values.size() == 0) {
				return null;
			}

			return (T) ModelUtil.prepareBaseValue(requiredType, values.iterator().next());
		}
		
		// 可以是bean类型
		return toModel(requiredType, data.get(0)) ;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T queryForObject(String nameSpace,String sqlID, Class<T> requiredType, Object... args) {
		SqlStatement stmt = getSqlStatement(nameSpace,sqlID);
		
		if (stmt == null) {
			throw new DbException("没有在映射文件件里找到指定的sql编号");
		}

		try {
			String sql = prepareRenderedSql(stmt, args);

			List<Map<String, Object>> data = exe.executeQuery(sql);
			if (data == null || data.isEmpty()) {
				return null;
			}

			// 可是以map
			if (Map.class.isAssignableFrom(requiredType)) {
				return (T) data.get(0);
			}

			// 可以是常规类型
			if (ModelUtil.isCanBeBaseType(requiredType)) {
				Collection<T> values = (Collection<T>) data.get(0).values();
				if (values == null || values.size() == 0) {
					return null;
				}

				return (T) ModelUtil.prepareBaseValue(requiredType, values.iterator().next());
			}

			// 可以是bean类型
			return toModel(requiredType, data.get(0)) ;
		} catch (Exception ex) {
			throw new DbException(ex);
		}
	}

	public <T> T queryForObject(String sqlID, Class<T> requiredType, Object... args) throws DbException {
		return queryForObject(requiredType.getName(), sqlID, requiredType, args);
	}
	
	/**
	 * 注册工具类方法到模板引擎调用(多个工具类以逗号分割)
	 * @param stmt
	 * @param root
	 * @throws TemplateModelException
	 */
	private void registStaticModel(SqlStatement stmt, final Map<String, Object> root) throws TemplateModelException {
		if (StringUtil.isBlank(stmt.getImportClazz())) {
			return ;
		}
		
		BeansWrapper wrapper = BeansWrapper.getDefaultInstance();
		TemplateHashModel staticModels = wrapper.getStaticModels();
		
		String [] clazzes = stmt.getImportClazz().split(",");
		for(String impClazz: clazzes) {
			TemplateHashModel utilStatics = (TemplateHashModel) staticModels.get(impClazz);
			
			String util = null;
			if (impClazz.indexOf(".") != -1) {
				util = impClazz.substring(impClazz.lastIndexOf(".") + 1, impClazz.length());

			} else {
				util = impClazz;
			}

			if (util != null) {
				root.put(util, utilStatics);
			}
		}
	}

	public <T> List<T> queryForList(String nameSpace,String sqlID, Class<T> requiredType, Object... args) {
		SqlStatement stmt = getSqlStatement(nameSpace,sqlID);
		
		if (stmt == null) {
			throw new DbException("没有在映射文件件里找到指定的sql编号");
		}

		try {
			String sql = prepareRenderedSql(stmt, args);

			List<Map<String, Object>> data = exe.executeQuery(sql);
			if (data == null || data.isEmpty()) {
				return new ArrayList<T>();
			}

			List<T> list = toRequiredTypeList(requiredType, data);
			return list;
		} catch (Exception ex) {
			throw new DbException(ex);
		}
	}
	
	/**
	 * 如果requiredType是实体类型,则不需要输入表空间名
	 */
	public <T> List<T> queryForList(String sqlID, Class<T> requiredType, Object... args) throws DbException {
		return queryForList(requiredType.getName(), sqlID, requiredType, args);
	}
	
	/**
	 * 转换数据类型
	 */
	@SuppressWarnings("unchecked")
	private <T> List<T> toRequiredTypeList(Class<T> requiredType, List<Map<String, Object>> data) {
		// 可是以map
		if (Map.class.isAssignableFrom(requiredType)) {
			return (List<T>) data;
		}

		List<T> list = new ArrayList<>();
		// 可以是常规类型
		if (ModelUtil.isCanBeBaseType(requiredType)) {
			List<Object> values = new ArrayList<>();
			for (Map<String, Object> m : data) { // (也可能多列数据，查出来可以是一个二维数组)
				values.addAll(m.values());
			}

			if (values != null && values.size() > 0) {
				for (Object v : values) {
					list.add((T) ModelUtil.prepareBaseValue(requiredType, v));
				}
			} else {
				list.add(null);
			}
			return list;
		}

		// 可以是bean类型
		for (Map<String, Object> row : data) {
			list.add(toModel(requiredType, row));
		}
		return list;
	}

	/**
	 * 获取已被模板渲染过的SQL语句
	 */
	private String prepareRenderedSql(SqlStatement stmt, Object... args) {
		try{
			Template tmpl = new Template(stmt.getId(), new StringReader(stmt.getSqlTemplateContent()), FTL_CONFIG);
			final Map<String, Object> root = new HashMap<>();
			if (args == null) {
				log.warn(" --- args is null !!!");
			} else if (args.length == 0){
				log.warn(" --- ATTENTION !!! prepareRenderedSql ==>  args length : "+args.length);
				
			} else if (args.length == 1) {
				root.put(stmt.getParameterName(), args[0]);
			} else {
				root.put(stmt.getParameterName(), args);
			}
	
			StringWriter stringWriter = new StringWriter();
			BufferedWriter writer = new BufferedWriter(stringWriter);
	
			registStaticModel(stmt, root); // 注册静态类方法在freemark中调用
	
			tmpl.process(root, writer);
	
			writer.flush();
			writer.close();
	
			String sql = stringWriter.toString().trim();
			return sql;
		}catch(Exception ex) {
			throw new DbException("渲染sql模板出错，请偿试检查参数",ex);
		}
	}

	
	public <T> Page<T> queryForPage(String nameSpace,String sqlID, int pageIndex, int pageSize, Class<T> requiredType, Object... args) {
		SqlStatement stmt = getSqlStatement(nameSpace, sqlID);
		if (stmt == null) {
			throw new DbException("没有在映射文件件里找到指定的sql编号：" + sqlID + " , namespace:" + requiredType.getName());
		}
		final String sqlMaped = prepareRenderedSql(stmt, args);
		if (pageIndex < 0) {
			pageIndex = 0;
		}
		if (pageSize < 0) {
			pageSize = 20;
		}
		
		
		long total = 0;
		String totalSqlFmt = "select count(1) from (%s) _t0_amount";
		String totalSql = String.format(totalSqlFmt,sqlMaped);
		
		List<Map<String, Object>> totalMapList = exe.executeQuery(totalSql);
		if (totalMapList != null && totalMapList.size() > 0) {
			Object cnt = totalMapList.get(0).values().iterator().next();
			if (cnt != null) {
				total = Long.valueOf(cnt.toString());
			}
		}
		
		final long pageCount = Double.valueOf(Math.ceil(total * 1.000 / pageSize)).longValue();
		
		//封装分页对象
		Page<T> page = new Page.PageModel<>();
		page.setTotal(total);
		page.setPageCount(pageCount);
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setHasNext(pageIndex + 1 < pageCount);
		page.setHasPrevious(pageIndex > 0 && pageIndex < pageCount - 1);
		
		
		String pageSql = sqlMaped.concat(" limit ?,? ");
		List<Object> params = new ArrayList<>();
		params.add(pageIndex * pageSize);
		params.add(pageSize);
		List<Map<String,Object>> list = exe.executeQuery(pageSql, params.toArray());
		
		List<T> data = new ArrayList<>();
		if (list!=null && list.size()>0) {
			for (Map<String,Object> row: list) {
				
				data.add(toModel(requiredType, row));
			}
		}
		
		page.setData(data);
		return page;
		
	}
	
	public <T> Page<T> queryForPage(String sqlID, int pageIndex, int pageSize, Class<T> requiredType, Object... args)
			throws DbException {
		return queryForPage(requiredType.getName(),sqlID,pageIndex,pageSize, requiredType, args);
	}

	public <T> Page<T> getEmptyPage() {
		Page<T> page = new Page.PageModel<>();
		page.setData(new ArrayList<>(0));
		page.setHasNext(false);
		page.setHasPrevious(false);
		page.setPageCount(0L);
		page.setPageIndex(0);
		page.setPageSize(20);
		page.setTotal(0L);
		return page;
	}

	public void executePlan(boolean isTransaction,Plan plan) throws DbException {
		Connection con = null;
		try{
			con = exe.prepareConnection();
			con.setAutoCommit(!isTransaction);

			if(isTransaction) {
				log.info(" --- >>>>>>>>>>>>> Transaction Begin !!! (Thead-id:"+Thread.currentThread().getId()+")");
			}
			
			if(isTransaction) {
				con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
			plan.doExecutePlan();
			
			if(isTransaction) { // 开启了事务，才进行事物操作！！回滚也是！
				con.commit();
				log.info(" --- >>>>>>>>>>>> Transaction Commited !!! (Thead-id:"+Thread.currentThread().getId()+")");
			}
			
		}catch(Exception ex){
			if(con!=null) {
				try {
					if(isTransaction) {
						con.rollback();
						log.info(" --- >>>>>>>>>>>> Transaction Rollback !!! (Thead-id:"+Thread.currentThread().getId()+" ,con:"+con+")");
					}
					
				} catch (SQLException e) {
					if(isTransaction) {
						String info = String.format(formatStr, "rollback error~!", e.getMessage());
						log.info(info);
					}
					throw new DbException(e);
				}
			}
			throw new DbException(ex);
		}finally{
			exe.close(null, null, con);
		}
	}
	
	public void executePlan(Plan plan) throws DbException {
		executePlan(true,plan);
	}
	 
	static final ThreadLocal<Connection> THREAD_LOCAL_CON = new ThreadLocal<>();
	
	
	private  final class JDBCExecutor {
		public JDBCExecutor(){}
		
		public void setCurrentConnection(Connection connection){
			THREAD_LOCAL_CON.set(connection);
		}
		
		public void setConfigDataSource(DataSource dataSource) {
			this.dataSource = dataSource;
		}
		
		private DataSource dataSource;

		
		private volatile boolean isBroken = false;
		
		
		public Connection getCurrentConnection() {
			return THREAD_LOCAL_CON.get();
		}
		
		private void print(Connection con) throws SQLException {
			//if (log.isDebugEnabled()) {
			DatabaseMetaData meta = con.getMetaData();
			log.info(" --- >>>>>>>>>>>> thread-id:" + Thread.currentThread().getId() + ", prepare Connection--> instance: " + con + ", url: "+meta.getURL()+"  , username: "+meta.getUserName()+"");
			log.info(" --- >>>>>>>>>>>> thread-id:" + Thread.currentThread().getId() + "," 
					+ con.getMetaData().getDatabaseProductName() 
					+ con.getMetaData().getDatabaseMajorVersion() + "." 
					+ con.getMetaData().getDatabaseMinorVersion() + "  "
					+ con.getMetaData().getDriverVersion() + "  ") ;
			//}
		}
		
		private Connection prepareConnection() throws SQLException {
			
			if (this.dataSource == null) {
				throw new SQLException("JDBCExecutor requires a DataSource or Connection to be invoked in this way");
			}
			
			//优先从绑定的当前线程取连接
			Connection con = THREAD_LOCAL_CON.get();
			if (con != null && con.isClosed() == false) {
				print(con);
				return con;
			} 
			
			THREAD_LOCAL_CON.set(this.dataSource.getConnection());
			con = THREAD_LOCAL_CON.get();
			
			/*
			log.info(" --- " 
					+ con.getMetaData().getDatabaseProductName() 
					+ con.getMetaData().getDatabaseMajorVersion() + "." 
					+ con.getMetaData().getDatabaseMinorVersion() + "  "
					+ con.getMetaData().getDriverVersion() + "  ") ;
			*/		
			print(con);
			return con;
		}

		private PreparedStatement prepareStatement(Connection conn, String sql,Object[] paramValues) throws SQLException {
			log.info(String.format(formatStr, sql,ArrayUtil.join(paramValues, ",")));
			PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			ParameterMetaData pmd = null;
			if (!(this.isBroken)) {
				pmd = stmt.getParameterMetaData();
				int stmtCount = pmd.getParameterCount();
				int paramsCount = (paramValues == null) ? 0 : paramValues.length;

				if (stmtCount != paramsCount)
					throw new SQLException("Wrong number of parameters: expected "	+ stmtCount + ", was given " + paramsCount);

			}


			for (int i = 0; i < paramValues.length; ++i)
				if (paramValues[i] != null) {
					stmt.setObject(i + 1, paramValues[i]);
				} else {
					int sqlType = 12;
					if (!(this.isBroken))
						try {
							sqlType = pmd.getParameterType(i + 1);
						} catch (SQLException e) {
							this.isBroken = true;
						}

					stmt.setNull(i + 1, sqlType);
				}

			return stmt;
		}
		
		/**
		 * 执行SQL插入或更新语句，如果有主键生成，则返回主键值
		 * @param sql
		 * @param paramValues
		 * @return
		 * @throws DbException
		 */
		@SuppressWarnings("unchecked")
		public <T> T executeUpdate(String sql, Object ... args) throws DbException {
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				con = prepareConnection();
				if(con==null)return null;
				
				stmt = prepareStatement(con, sql, args);
				
				Integer temp = Integer.valueOf(stmt.executeUpdate());
				
				rs = stmt.getGeneratedKeys();
				if (rs.next()){
					return (T) rs.getObject(1);
				}
				
				PlatformDb.this.jdbcCache.clear();
				return (T) temp;
			} catch (Exception ex) {
				ex.printStackTrace();
				throw new DbException(ex.getMessage(),ex);
			} finally {
				close(stmt, rs,null); // 没有关闭连接对象,当前线程结束的时候关闭!!!
			}
		}
		
		private String getCachedKey(String sql,Object ...paramValues) {
			StringBuilder sqlContent = new StringBuilder(sql);
			for (Object e: paramValues) {
				sqlContent.append(e+"_@@_");
			}
			return Md5Util.MD5Encode(sqlContent.toString(), "utf-8", false) ;
			//return jdbcCache.get(sqlContent);
		}
		
		@SuppressWarnings("unchecked")
		public List<Map<String, Object>> executeQuery(String sql,Object ... paramValues) throws DbException  {
			Connection con = null;
			PreparedStatement stmt = null;
			ResultSet rs = null;
			try {
				final String cacheKey = getCachedKey(sql, paramValues);
				Object cacheResult = jdbcCache.get(cacheKey);
				if(cacheResult!=null) {
					log.info(String.format(formatStr, "hit cache, cacheKey:",cacheKey));
					return (List<Map<String, Object>>)cacheResult;
				}
				
				
				con = prepareConnection();
				stmt = prepareStatement(con, sql, paramValues);
				rs = stmt.executeQuery();

				List<Map<String, Object>> list = new LinkedList<Map<String, Object>>();
				int cnt = rs.getMetaData().getColumnCount();
				while (rs.next()) {
					Map<String, Object> row = new LinkedHashMap<String, Object>();
					for (int i = 1; i <= cnt; ++i) {
						String key = rs.getMetaData().getColumnLabel(i);
						Object value = rs.getObject(i);
						row.put(key, value);
					}
					list.add(row);
				}
				
				jdbcCache.put(cacheKey, list);
				return list;
			} catch (Exception ex) {
				log.error(" --- executeQuery fail , " + ex.getMessage());
				throw new DbException(ex.getMessage(), ex);
			} finally {
				close(stmt, rs,null); // 没有关闭连接对象,当前线程结束的时候关闭!!!
			}
		}
		
		public  void close(Statement stmt, ResultSet rs,Connection con ) {
			try {
				if (stmt != null){
					stmt.close();
					//log.info(" --- >>>>>>>>>>>> close Statement success~!");
				}
			} catch (SQLException e) {
				log.info(" --- SqlExecutor close Statement fail ==> "+ e.getMessage());
				
			} finally {
				try {
					if (rs != null) {
						rs.close();
						//log.info(" --- >>>>>>>>>>>>close ResultSet success~!");
					}
				} catch (SQLException ex) {
					log.info(" --- SqlExecutor close ResultSet fail~！ ==> "+ ex.getMessage());
				} finally {					
					try {
						if (con != null ){ 
							log.info(" --- >>>>>>>>>>>> thread-id:"+ Thread.currentThread().getId() + ", >>close connection, instance:"+con);
							con.close();
						}
					} catch (SQLException ec) {
						log.error(" --- SqlExecutor close Connection fail  ,"+ ec.getMessage());
					}
				}
			}
		}
		
		
	}

	public void cascade(Object model, String... props) throws DbException {
		if (props == null || props.length == 0) return ;
		
		
		
	}

	public List<Map<String, Object>> executeQuery(String sql, Object... args) throws DbException {
		return exe.executeQuery(sql, args);
	}
	
}
