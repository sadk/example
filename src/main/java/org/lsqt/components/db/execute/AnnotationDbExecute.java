package org.lsqt.components.db.execute;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.lsqt.components.db.Db;
import org.lsqt.components.db.DbException;
import org.lsqt.components.db.JDBCExecutor;
import org.lsqt.components.db.Page;
import org.lsqt.components.db.Plan;
import org.lsqt.components.db.annotation.Column;
import org.lsqt.components.db.annotation.Gid;
import org.lsqt.components.db.annotation.Id;
import org.lsqt.components.db.execute.util.CacheReflectUtil;
import org.lsqt.components.db.orm.SqlStatementArgs;
import org.lsqt.components.db.Table;
import org.lsqt.components.util.collection.ArrayUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author yuanmm
 *
 */
public class AnnotationDbExecute implements Db{
	protected static final Logger  log = LoggerFactory.getLogger(AnnotationDbExecute.class);
	
	private static final String formatStr = " --- %s -- ===> %s";
	private final JDBCExecutor exe = new JDBCExecutor();
	
	public void close() {
		try {
			if(null == exe.getCurrentConnection()) {
				log.warn(" --- No thread connection was found for the current binding~!");
			}
			Connection con = exe.getCurrentConnection();
			log.debug(" --- >>>>>>>>>>>> thread-id:"+Thread.currentThread().getId()+", >>db.close ing...  "+con);
			
			if(con!=null && !con.isClosed() && !con.getAutoCommit()){
				con.commit();
			}
			exe.close(null, null, con);
			log.debug(" --- >>>>>>>>>>>> thread-id:"+Thread.currentThread().getId()+", >>db.closed ...  "+con);
			
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
		return ModelWrapper.getFullTable(requiredType);
	}
	
	
	public String getColumn(Class<?> requiredType, String prop) {
		final String idName = "id";
		List<Field> list = CacheReflectUtil.getBeanField(requiredType);
		if(list == null || list.size() == 0) return null;
		
		for (Field f : list) {
			if (f.getName().equals(prop)) {
				Id id = f.getAnnotation(Id.class);
				if (id != null) {
					return "".equals(id.name()) ? idName : id.name();
				}

				Column col = f.getAnnotation(Column.class);
				if (col != null) {
					return col.name();
				}

				Gid gid = f.getAnnotation(Gid.class);
				if (gid != null) {
					return gid.name();
				}
			}
		}
		return null;
	}
	
	
	public Serializable save(Object model, String... prop) {
		ModelWrapper wrapper = new ModelWrapper(model);
		
		if(!wrapper.isDbGernarteID()) {
			wrapper.toGernarteID();
		}
		
		String sql = wrapper.getInsertSql(prop); // 如果主键是DB自动生成不显示主键字段
		Object[] args = wrapper.getInsertValues(prop); 
		
		Serializable id = exe.executeUpdate(sql, args) ;
		
		
		if(wrapper.isDbGernarteID()){
			wrapper.setIdValue(id);
		}
		//id = wrapper.getIdValue();
		log.info(String.format(formatStr,"saved~! the id value ",id));
		return id;
	}

	public int delete(Object ... model) {
		if (model == null || model.length == 0) return 0;
		int cnt = 0;
		
		for (Object e : model) {
			ModelWrapper wrapper = new ModelWrapper(e);

			Integer tmp = exe.executeUpdate(wrapper.getDeleteSql(), wrapper.getIdValue());
			cnt += tmp;
		}

		return cnt;
	}
	
	private static String getParamHolder(Object[] paramValues) {
		String holdString = "";
		int holdLength = paramValues.length;
		for (int i = 0; i < holdLength; ++i)
			if (i != holdLength - 1)
				holdString = "?," + holdString;
			else
				holdString = holdString + "?";

		return holdString;
	}

	public int deleteById(Class<?> type, Object... id) {
		if (id == null || id.length == 0) return 0;

		String table = ModelWrapper.getFullTable(type);
		
		String sql = "delete from " + table + " where " + ModelWrapper.getIdColumn(type) ;
		if (id.length == 1) {
			sql = sql.concat(" = ? ");
		} else {
			sql = sql.concat(" in (" + getParamHolder(id) + ")");
		}
		
		log.info(String.format(formatStr, sql,ArrayUtil.join(id, ",")));
		
		Integer cnt = exe.executeUpdate(sql, id);
		return cnt;
	}

	public <T> T update(T model, String... prop) throws DbException {
		ModelWrapper m = new ModelWrapper(model);
		String sql = m.getUpdateSql(prop);
		Object [] args = m.getUpdateValues(prop) ;
		
		exe.executeUpdate(sql,args);
		return (T) model;
	}

	public <T> T saveOrUpdate(T model, String... props) throws DbException {
		ModelWrapper m = new ModelWrapper(model);
		if (m.getIdValue() != null) {
			update(model, props);
		} else {
			save(model, props);
		}
		return model;
	}
	
	public <T> T executeUpdate(String sql, Object ... args) throws DbException {
		return exe.executeUpdate(sql, args);
	}
	
	public <T> T executeQueryForObject(String sql,Class<T> requiredType,Object ...args) throws DbException {
		return queryForObject(sql, requiredType, args);
	}
	
	public Page<Map<String, Object>> executeQueryForPage(String sql,Integer pageIndex,Integer pageSize, Object... args) {
		
		if (pageIndex == null || pageIndex < 0) {
			pageIndex = 0;
		}
		if (pageSize == null || pageSize < 0) {
			pageSize = 20;
		}
		
		
		long total = 0;
		String totalSqlFmt = "select count(1) from (%s) _t0_amount";
		String totalSql = String.format(totalSqlFmt,sql);
		
		List<Map<String, Object>> totalMapList = exe.executeQuery(totalSql);
		if (totalMapList != null && totalMapList.size() > 0) {
			Object cnt = totalMapList.get(0).values().iterator().next();
			if (cnt != null) {
				total = Long.valueOf(cnt.toString());
			}
		}
		
		final long pageCount = Double.valueOf(Math.ceil(total * 1.000 / pageSize)).longValue();
		
		//封装分页对象
		Page<Map<String, Object>> page = new Page.PageModel<>();
		page.setTotal(total);
		page.setPageCount(pageCount);
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setHasNext(pageIndex + 1 < pageCount);
		page.setHasPrevious(pageIndex > 0 && pageIndex < pageCount - 1);
		
		
		String pageSql = sql.concat(" limit ?,? ");
		List<Object> params = new ArrayList<>();
		params.add(pageIndex * pageSize);
		params.add(pageSize);
		List<Map<String,Object>> list = exe.executeQuery(pageSql, params.toArray());
		
		List<Map<String,Object>> data = new ArrayList<>();
		if (list!=null && list.size()>0) {
			for (Map<String,Object> row: list) {
				data.add(row);
			}
		}
		
		page.setData(data);
		return page;
		
	}
	
	@SuppressWarnings("unchecked")
	public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) throws DbException {
		
		
		List<Map<String, Object>> data = exe.executeQuery(sql, args);
		
		if (data == null || data.size() == 0){
			return null;
		}
		
		// 可是以map
		if(Map.class.isAssignableFrom(requiredType)) {
			return (T) data.get(0);
		}
		
		// 可以是常规类型
		if(ModelWrapper.isCanBeBaseType(requiredType)) {
			  Collection<T> values = (Collection<T>) data.get(0).values() ;
			  if(values==null || values.size() == 0) return null; 
				 
			  return (T) ModelWrapper.prepareBaseValue(requiredType, values.iterator().next());
		}
		
		// bean类型
		return ModelWrapper.toModel(requiredType, data.get(0));
	}

	@SuppressWarnings("unchecked")
	
	public <T> List<T> queryForList(String sql, Class<T> requiredType, Object... args) throws DbException {
		List<Map<String, Object>> data = exe.executeQuery(sql, args);
		if (data == null || data.size() == 0) {
			return null;
		}
		
		// Map类型
		if (Map.class.isAssignableFrom(requiredType)) {
			return (List<T>) data;
		}

		List<T> rs = new ArrayList<>();
		// 常规类型(也可能多列数据，查出来可以是一个二维数组)
		if (ModelWrapper.isCanBeBaseType(requiredType)) {
			List<Object> values = new ArrayList<>();
			for(Map<String,Object> m : data) {
				values.addAll(m.values());
			}
			
			if (values != null && values.size() > 0) {
				for(Object v : values) {
					rs.add((T) ModelWrapper.prepareBaseValue(requiredType, v));
				}
			} else {
				rs.add(null);
			}
			return rs;
		}

		// bean类型
		for (Map<String, Object> e : data) {
			rs.add(ModelWrapper.toModel(requiredType, e));
		}
		return rs;
	}

	
	public <T> Page<T> queryForPage(String sql,int pageIndex,int pageSize, Class<T> requiredType, Object... args) throws DbException {
		if (pageIndex < 0) pageIndex = 0;
		if (pageSize < 0) pageSize = 20;
		
		long total = 0;
		String totalSqlFmt = "select count(1) from (%s) _t0_amount";
		String totalSql = String.format(totalSqlFmt, sql);
		
		total  = queryForObject(totalSql, Long.class, args);
		
		final long pageCount = Double.valueOf(Math.ceil(total * 1.000 / pageSize)).longValue();
		
		//封装分页对象
		Page<T> page = new Page.PageModel<>();
		page.setTotal(total);
		page.setPageCount(pageCount);
		page.setPageIndex(pageIndex);
		page.setPageSize(pageSize);
		page.setHasNext(pageIndex + 1 < pageCount);
		page.setHasPrevious(pageIndex > 0 && pageIndex < pageCount - 1);
		
		
		String pageSql = sql.concat(" limit ?,? ");
		List<Object> params = new ArrayList<>(Arrays.asList(args));
		params.add(pageIndex * pageSize);
		params.add(pageSize);
		List<T> data = queryForList(pageSql, requiredType, params.toArray());
		page.setData(data);
		
		return page;
	}

	public <T> T getById(Class<T> type, Object id) {
		 String sql = "select * from %s where %s = ?";
		 sql = String.format(sql, ModelWrapper.getFullTable(type),ModelWrapper.getIdColumn(type));
		return queryForObject(sql, type, id);
	}
	
	public void executePlan(Plan plan) throws DbException {
		executePlan(true,plan);
	}
	
	public void executePlan(boolean isTransaction ,Plan plan) throws DbException {
		Connection con = null;
		try{
			con = exe.prepareConnection();
			con.setAutoCommit(!isTransaction);
			if (isTransaction) {
				con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
			plan.doExecutePlan();

			if (isTransaction) {
				con.commit();
			}
		}catch(Exception ex){
			if(con!=null) {
				try {
					if(isTransaction) {
						con.rollback();
					}
				} catch (SQLException e) {
					
					if (isTransaction) {
						String info = String.format(formatStr, "rollback error~!", e.getMessage());
						log.info(info);
					}
					throw new DbException(e);
				}
			}
			throw new DbException(ex.getMessage(),ex);
		}finally{
			exe.close(null, null, con);
		}
		
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


	public void cascade(Object model, String... props) throws DbException {
		 
	}

	public List<Map<String, Object>> executeQuery(String sql, Object... args) throws DbException {
		return exe.executeQuery(sql, args);
	}

	/**
	 * 批量插入或更新
	 * @param sql 标准的SQL语句（不像MySql的insert table (a,b,c) values(1,2,3),(4,5,6),(7,8,9)的特有语句)
	 * @param paramValues 
	 * @return
	 * @throws Exception
	 */
	public int batchUpdate(String sql, Object... args) {
		return exe.batchUpdate(sql, args);
	}

	public int batchSave(List<?> models, String... props) {
		int sum = 0;
		if (models == null || models.isEmpty()) {
			return 0;
		}

		Set<String> types = new HashSet<>();
		for (Object m : models) {
			types.add(m.getClass().getName());
		}

		if (types.size() > 1) {
			throw new IllegalArgumentException("Model objects must be of the same type");
		}
		
		String sql = null;
		List<Object> batchParamValues = new LinkedList<>();
		
		for (Object model : models) {
			ModelWrapper wrapper = new ModelWrapper(model);

			if(!wrapper.isDbGernarteID()) {
				wrapper.toGernarteID();
			}
			
			if(sql == null) {
				sql = wrapper.getInsertSql(props); // 如果主键是DB自动生成不显示主键字段
			}
			Object[] args = wrapper.getInsertValues(props);
			batchParamValues.addAll(Arrays.asList(args));
		}

		sum += exe.batchUpdate(sql, batchParamValues.toArray(new Object[batchParamValues.size()]));
		return sum;
	}


	public int batchUpdate(List<String> sqls) {
		return 0;
	}


	@Override
	public <T> T queryForObject(String nameSpace, String sqlID_or_SQL, Class<T> requiredType, Object... args)
			throws DbException {
		
		return null;
	}


	@Override
	public <T> List<T> queryForList(String nameSpace, String sqlID, Class<T> requiredType, Object... args)
			throws DbException {
		
		return null;
	}


	@Override
	public List<org.lsqt.components.db.Column> getMetaDataColumn(String sql, Object... args) {
		
		return null;
	}
}
