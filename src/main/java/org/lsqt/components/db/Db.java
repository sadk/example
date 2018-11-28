package org.lsqt.components.db;

import java.io.Closeable;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

public interface Db extends Closeable{
	
	int TRANSACTION_NONE = 0;
	int TRANSACTION_READ_UNCOMMITTED = 1;
	int TRANSACTION_READ_COMMITTED = 2;
	int TRANSACTION_REPEATABLE_READ = 4;
	int TRANSACTION_SERIALIZABLE = 8;
	
	void setConfigDataSource (DataSource ds) ;
	
	/**
	 * 获取空的分页对象
	 * @return page
	 */
	default <T> Page<T> getEmptyPage() {
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
	
	/**
	 * 获取执行的SQL列名
	 * @param sql 查询误句
	 * @param args 对应的SQL参数
	 * @return
	 */
	List<Column> getMetaDataColumn(String sql,Object ...args);
	
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
	Object save(Object pojo,String ... prop)  ;
	
	/**
	 * 删除一个对象
	 * @param pojo
	 * @return
	 */
	int delete(Object ... models)  ;
	
	/**
	 * 跟据id删除对象
	 * @param id
	 * @return
	 */
	int  deleteById(Class<?> type, Object ...ids)  ;
	
	/**
	 * 
	 * @param type
	 * @param id
	 * @return
	 */
	<T> T getById(Class<T> type,Object id)  ;
	
	/**
	 * 更新一个对象
	 * @param pojo
	 * @param props
	 * @return
	 */
	<T> T update(T model, String ... props) ;
	
	/**
	 * <pre>
	 * 保存或更新对象 
	 * if(model.id == null) 新增记录!!!
	 * if(model.id != null) 更新记录!!!
	 * </pre>
	 * @param model
	 * @param props
	 */
	<T> T saveOrUpdate(T model,String ... props) ;
	
	/**
	 * 级联加载指定属性
	 * @param model
	 * @param props
	 */
	void cascade(Object model,String ...props) ;
	
	
	/**
	 * 执行给定的SQL更新语句
	 * @param sql SQL语句
	 * @param args SQL参数值
	 * @return 影响的行数
	 */
	<T> T executeUpdate(String sql, Object ... args)  ;
	
	/**
	 * 执行给定的SQL查询语句,返回List<Map>
	 * @param sql SQL语句
	 * @param args
	 * @return
	 */
	List<Map<String,Object>> executeQuery(String sql, Object ... args)  ;
	
	/**
	 * 执行分页查询，默认统计总记录数
	 * @param sql 需要执行的SQL
	 * @param pageIndex 分页索引
	 * @param pageSize 分页大小
	 * @param args SQL语句参数
	 * @return 
	 */
	Page<Map<String,Object>> executeQueryForPage(String sql,Integer pageIndex, Integer pageSize, Object ... args)  ;
	
	/**
	 * 执行分页查询
	 * @param sql
	 * @param requiredCount 是否统计总记录数
	 * @param pageIndex 分页索引
	 * @param pageSize 分页大小
	 * @param args SQL参数值
	 * @return
	 */
	Page<Map<String, Object>> executeQueryForPage(String sql,boolean requiredCount,Integer pageIndex,Integer pageSize, Object... args);
	
	/**
	 * 执行给定的SQL查询语句,返回单值Object
	 * @param sql SQL语句
	 * @param args
	 * @return
	 */
	<T> T executeQueryForObject(String sql, Class<T> requiredType, Object ... args)  ;
	
	/**
	 * @param sql_or_sqlID 如果用XML做模型映射，则传入sqlID； 如果用注解做模型映射，测传入SQL语句
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args
	 * @return
	 */
	<T> T queryForObject(String sqlID_or_SQL, Class<T> requiredType, Object... args) ;
	
	/**
	 * @param nameSpace SQL定义的命名空间
	 * @param sql_or_sqlID 如果用XML做模型映射，则传入sqlID； 如果用注解做模型映射，测传入SQL语句
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args
	 * @return
	 */
	<T> T queryForObject(String nameSpace,String sqlID_or_SQL, Class<T> requiredType, Object... args) ;
	
	 
	/**
	 * 
	 * @param sql_or_sqlID 如果用XML做表和POJO映射，则传入sqlID； 如果用注解做POJO映射，测传入SQL语句
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args
	 * @return
	 */
	<T> List<T> queryForList(String sqlID_or_SQL, Class<T> requiredType, Object... args) ;
	
	/**
	 * 
	 * @param nameSpace 实体类名(空间)
	 * @param sqlID SQL语句命名ID
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args 
	 * @return
	 */
	<T> List<T> queryForList(String nameSpace,String sqlID, Class<T> requiredType, Object... args) ;
	
	/**
	 * 分页查询（默认统计总记录数）
	 * @param sql_or_sqlID 如果用XML做表和POJO映射，则传入sqlID； 如果用注解做POJO映射，测传入SQL语句
	 * @param pageIndex
	 * @param pageSize
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args
	 * @return
	 */
	<T> Page<T> queryForPage(String sqlID_or_SQL,int pageIndex,int pageSize,Class<T> requiredType,Object ... args) ;
	
	/**
	 * 
	 * @param sql_or_sqlID 如果用XML做表和POJO映射，则传入sqlID； 如果用注解做POJO映射，测传入SQL语句
	 * @param requiredCount 是否需要统计总记录数(默认统计总记录数)
	 * @param pageIndex
	 * @param pageSize
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args
	 * @return
	 */
	<T> Page<T> queryForPage(String sqlID_or_SQL,boolean requiredCount,int pageIndex,int pageSize,Class<T> requiredType,Object ... args) ;
	
	
	/**
	 * @param nameSpace 实体类名(空间)
	 * @param sqlID SQL语句定义ID
	 * @param requiredType 可以是基础类型、Map、POJO
	 * @param args
	 * @return
	 * @
	 */
	<T> Page<T> queryForPage(String nameSpace,String sqlID,int pageIndex,int pageSize,Class<T> requiredType,Object ... args) ;
	
	
	/**
	 * 执行一个SQL计划
	 * @param plan 
	 */
	void executePlan(Plan plan)  ;
	
	/**
	 * 执行一个SQL计划
	 * @param plan 
	 * @param isTransaction 是否开启事物
	 */
	void executePlan(boolean isTransaction,Plan plan) throws Exception;
	
	/**
	 * 执行一个SQL计划，指定事务隔离级别
	 * @param isTransaction 是否开启事务
	 * @param transactionIsolation 见: DB.transactionIsolation
	 * @param plan
	 */
	void executePlan(boolean isTransaction, int transactionIsolation , Plan plan) ;
	
	/**
	 * 释放绑定到当前线程上的资源
	 */
	void close() ;
	
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
	interface Dialect{
		 
		/**DB2**/
		int DB2Dialect=0;
		
		/** DB2 AS/400**/
		int DB2400Dialect=1;
		
		/**DB2 OS390**/
		int DB2390Dialect=2;
		
		/**PostgreSQL**/
		int PostgreSQLDialect=3;
		
		/**MySQL**/
		int MySQLDialect=4;
		
		/**MySQL with InnoDB**/
		int MySQLInnoDBDialect=5;
		
		/**MySQL with MyISAM**/
		int MySQLMyISAMDialect=6;
		
		/**Oracle（any version）**/
		int OracleDialect=7;
		
		/**Oracle 9i**/
		int Oracle9iDialect=8;
		
		/**Oracle 10g**/
		int Oracle10gDialect=9;
		
		/**Sybase**/
		int SybaseDialect=10;
	
		/**Sybase Anywhere**/
		int SybaseAnywhereDialect=11;
		
		/**Microsoft SQL Server**/
		int SQLServerDialect=12;
		
		/**SAP DB**/
		int SAPDBDialect=13;
		
		/**Informix**/
		int InformixDialect=14;
		
		/**HypersonicSQL**/
		int HSQLDialect=15;
		
		/**Ingres**/
		int IngresDialect=16;
		
		/**Progress**/
		int ProgressDialect=17;

		/**Mckoi SQL**/
		int MckoiDialect=18;
		
		/**Interbase**/
		int InterbaseDialect=19;
		
		/**Pointbase**/
		int PointbaseDialect=20;
		
		/**FrontBase**/
		int FrontbaseDialect=21;
		
		/**Firebird**/
		int FirebirdDialect=22;
	}
}
