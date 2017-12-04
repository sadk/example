package org.lsqt.components.db;

import java.io.Closeable;
import java.sql.Connection;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public interface Db extends Closeable{

	void setConfigDataSource (DataSource ds) ;
	
	
	/**
	 * 运行时动态设置数据连接
	 * @param con
	 */
	void setCurrentConnection(Connection con);
	
	/**
	 * 获取当前缄定线程的连接
	 * @return
	 */
	Connection getCurrentConnection();
	
	
	/**
	 * 获取模型类对应的数据库表全名
	 * @param requiredType
	 * @return
	 */
	String getFullTable(Class<?> requiredType);
	
	/**
	 * 获取属性对应的数据库字段
	 * @param requiredType
	 * @param prop
	 * @return
	 */
	String getColumn(Class<?> requiredType,String prop);
	
	
	/**
	 * 保存一个对象，返回id值
	 * @param pojo
	 * @param prop
	 * @return 
	 */
	Object save(Object pojo,String ... prop)  throws DbException;
	
	/**
	 * 删除一个对象
	 * @param pojo
	 * @return
	 */
	int delete(Object ... models)  throws DbException;
	
	/**
	 * 跟据id删除对象
	 * @param id
	 * @return
	 */
	int  deleteById(Class<?> type, Object ...ids)  throws DbException;
	
	/**
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	<T> T getById(Class<T> type,Object id)  throws DbException;
	
	/**
	 * 更新一个对象
	 * @param pojo
	 * @param props
	 * @return
	 * @throws DbException
	 */
	<T> T update(T model, String ... props) throws DbException;
	
	/**
	 * <pre>
	 * 保存或更新对象 
	 * if(model.id == null) 新增记录!!!
	 * if(model.id != null) 更新记录!!!
	 * </pre>
	 * @param model
	 * @param props
	 * @return
	 * @throws DbException
	 */
	<T> T saveOrUpdate(T model,String ... props) throws DbException;
	
	/**
	 * 级联加载指定属性
	 * @param model
	 * @param props
	 * @throws DbException
	 */
	void cascade(Object model,String ...props) throws DbException;
	
	
	/**
	 * 执行给定的SQL更新语句
	 * @param sql SQL语句
	 * @param args SQL参数值
	 * @return 影响的行数
	 * @throws DbException
	 */
	<T> T executeUpdate(String sql, Object ... args) throws DbException ;
	
	/**
	 * 执行给定的SQL查询语句,返回List<Map>
	 * @param sql SQL语句
	 * @param args
	 * @return
	 * @throws DbException
	 */
	List<Map<String,Object>> executeQuery(String sql, Object ... args) throws DbException ;
	
	/**
	 * 执行给定的SQL查询语句,返回单值Object
	 * @param sql SQL语句
	 * @param args
	 * @return
	 * @throws DbException
	 */
	<T> T executeQueryForObject(String sql, Class<T> requiredType, Object ... args) throws DbException ;
	
	/**
	 * @param sql_or_sqlID 如果用XML做模型映射，则传入sqlID； 如果用注解做模型映射，测传入SQL语句
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args
	 * @return
	 * @throws DbException
	 */
	<T> T queryForObject(String sqlID_or_SQL, Class<T> requiredType, Object... args) throws DbException;
	
	/**
	 * 
	 * @param sql_or_sqlID 如果用XML做表和POJO映射，则传入sqlID； 如果用注解做POJO映射，测传入SQL语句
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args
	 * @return
	 * @throws DbException
	 */
	<T> List<T> queryForList(String sqlID_or_SQL, Class<T> requiredType, Object... args) throws DbException;
	
	/**
	 * 
	 * @param sql_or_sqlID 如果用XML做表和POJO映射，则传入sqlID； 如果用注解做POJO映射，测传入SQL语句
	 * @param pageIndex
	 * @param pageSize
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args
	 * @return
	 * @throws DbException
	 */
	<T> Page<T> queryForPage(String sqlID_or_SQL,int pageIndex,int pageSize,Class<T> requiredType,Object ... args) throws DbException;
	
	/**
	 * 获取空的分页对象
	 * @return page
	 */
	<T> Page<T> getEmptyPage() ;
	
	/**
	 * 执行一个SQL计划
	 * @param plan 
	 * @throws DbException
	 */
	void executePlan(Plan plan) throws DbException ;
	
	/**
	 * 执行一个SQL计划
	 * @param plan 
	 * @param isTransaction 是否开启事物
	 * @throws DbException
	 */
	void executePlan(boolean isTransaction,Plan plan) throws DbException ;
	
	/**
	 * 释放绑定到当前线程上的资源
	 */
	void close() throws DbException;
	
	/**
	 * 批量更新或插入
	 * @param sql 一个标准Insert或Update批处理语句
	 * @param args 所有的参数值
	 * @return
	 */
	int batchUpdate(String sql,Object ...fullArgs);
	
	/**
	 * 批量更新或插入
	 * @param sql
	 * @return
	 */
	int batchUpdate(List<String> sqls);
	
	/***
	 * 批量保存实体对象
	 * @param list 实体对象列表
	 * @param props
	 * @return
	 */
	int batchSave(List<?> list,String ...props);
	
	/**
	 * 数据库方言
	 * @author admin
	 *
	 */
	interface DbDialect{
		 
		/**DB2**/
		static final int DB2Dialect=0;
		
		/** DB2 AS/400**/
		static final int DB2400Dialect=1;
		
		/**DB2 OS390**/
		static final int DB2390Dialect=2;
		
		/**PostgreSQL**/
		static final int PostgreSQLDialect=3;
		
		/**MySQL**/
		static final int MySQLDialect=4;
		
		/**MySQL with InnoDB**/
		static final int MySQLInnoDBDialect=5;
		
		/**MySQL with MyISAM**/
		static final int MySQLMyISAMDialect=6;
		
		/**Oracle（any version）**/
		static final int OracleDialect=7;
		
		/**Oracle 9i**/
		static final int Oracle9iDialect=8;
		
		/**Oracle 10g**/
		static final int Oracle10gDialect=9;
		
		/**Sybase**/
		static final int SybaseDialect=10;
	
		/**Sybase Anywhere**/
		static final int SybaseAnywhereDialect=11;
		
		/**Microsoft SQL Server**/
		static final int SQLServerDialect=12;
		
		/**SAP DB**/
		static final int SAPDBDialect=13;
		
		/**Informix**/
		static final int InformixDialect=14;
		
		/**HypersonicSQL**/
		static final int HSQLDialect=15;
		
		/**Ingres**/
		static final int IngresDialect=16;
		
		/**Progress**/
		static final int ProgressDialect=17;

		/**Mckoi SQL**/
		static final int MckoiDialect=18;
		
		/**Interbase**/
		static final int InterbaseDialect=19;
		
		/**Pointbase**/
		static final int PointbaseDialect=20;
		
		/**FrontBase**/
		static final int FrontbaseDialect=21;
		
		/**Firebird**/
		static final int FirebirdDialect=22;
	}
}
