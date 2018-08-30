package org.lsqt.components.db;

import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.lsqt.components.cache.Cache;
import org.lsqt.components.cache.SimpleCache;
import org.lsqt.components.db.Db.DbDialect;
import org.lsqt.components.db.support.ColumnUtil;
import org.lsqt.components.db.support.MySQLTypeMapping;
import org.lsqt.components.util.collection.ArrayUtil;
import org.lsqt.components.util.lang.Md5Util;
import org.lsqt.components.util.lang.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JDBCExecutor {
	
	private static final String formatStr = " --- %s -- ===> %s";
	
	private static final Logger log = LoggerFactory.getLogger(JDBCExecutor.class);
	
	private static final ThreadLocal<Connection> THREAD_LOCAL_CON = new ThreadLocal<>();
	
	private Cache jdbcCache  = new SimpleCache();
	
	public void setCache(Cache cache) {
		this.jdbcCache = cache;
	}
	
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
		/*
		if (log.isDebugEnabled()) {
			DatabaseMetaData meta = con.getMetaData();
			log.debug(" --- >>>>>>>>>>>> thread-id:" + Thread.currentThread().getId() + ", prepare Connection--> instance: " + con + ", url: "+meta.getURL()+"  , username: "+meta.getUserName()+"");
			log.debug(" --- >>>>>>>>>>>> thread-id:" + Thread.currentThread().getId() + "," 
					+ con.getMetaData().getDatabaseProductName() 
					+ con.getMetaData().getDatabaseMajorVersion() + "." 
					+ con.getMetaData().getDatabaseMinorVersion() + "  "
					+ con.getMetaData().getDriverVersion() + "  ") ;
		}
		*/
	}
	
	
	
	public Connection prepareConnection() throws SQLException {
		
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
		log.debug(String.format(formatStr, sql,ArrayUtil.join(paramValues, ",",true)));
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

			//jdbcCache.clear();
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
	
	public List<Column> getMetaDataColumn(String sql,Object ... paramValues) throws DbException {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			con = prepareConnection();
			stmt = prepareStatement(con, sql, paramValues);
			rs = stmt.executeQuery();

			List<Column> list = new ArrayList<>();
			int cnt = rs.getMetaData().getColumnCount();
			
			for(int i=1;i<=cnt; i++) {
				Column column = new Column();
				String dbType = rs.getMetaData().getColumnTypeName(i) ;
				if(StringUtil.isNotBlank(dbType)) {
					dbType = dbType.toLowerCase();
				}
				column.setJavaType(MySQLTypeMapping.guessJavaType(dbType));
				column.setName(rs.getMetaData().getColumnName(i));
				column.setText(rs.getMetaData().getColumnLabel(i));
				column.setDbType(dbType);
				column.setPropertyName(ColumnUtil.toPropertyName(column.getName()));
				
				list.add(column);
			}
			
			return list;
		} catch (Exception ex) {
			log.error(" --- executeQuery fail , " + ex.getMessage());
			throw new DbException(ex.getMessage(), ex);
		} finally {
			close(stmt, rs,null); // 没有关闭连接对象,当前线程结束的时候关闭!!!
		}
	}
	
	public List<Map<String, Object>> executeQuery(String sql,Object ... paramValues) throws DbException  {
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			/*
			final String cacheKey = getCachedKey(sql, paramValues);
			Object cacheResult = jdbcCache.get(cacheKey);
			if(cacheResult!=null) {
				log.info(String.format(formatStr, "hit cache, cacheKey:",cacheKey));
				return (List<Map<String, Object>>)cacheResult;
			}*/
			
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
			
			//jdbcCache.put(cacheKey, list);
			return list;
		} catch (Exception ex) {
			log.error(" --- executeQuery fail , " + ex.getMessage());
			throw new DbException(sql+" ,args="+Arrays.asList(paramValues)+"; \n\r"+ex.getMessage(), ex.getCause());
		} finally {
			close(stmt, rs,null); // 没有关闭连接对象,当前线程结束的时候关闭!!!
		}
	}
	
	
	
	
	
	
	
	// --------------------------------------------  批量操作 -----------------------------------------------------
	/**
	 * <pre>
	 * 批量插入阀值 !!!
	 * 解决com.mysql.jdbc.PacketTooBigException: Packet for query is too
	 * large (3158064）问题 在做查询数据库操作时，报了以上错误，还有out of memery heap hacp,
	 * 原因是mysql的max_allowed_packet设置过小引起的，我一开始设置的是1M，后来改为了20M
	 * 
	 * mysql根据配置文件会限制server接受的数据包大小。 有时候大的插入和更新会被max_allowed_packet 参数限制掉，导致失败。
	 * 
	 * 查看目前配置 show VARIABLES like '%max_allowed_packet%';
	 * 
	 * 修改方法
	 *  1） 方法1
	 *	可以编辑my.cnf来修改（windows下my.ini）,在[mysqld]段或者mysql的server配置段进行修改。
	 *	max_allowed_packet = 20M
	 *	如果找不到my.cnf可以通过
	 *	
	 *	mysql --help | grep my.cnf
	 *	
	 *	去寻找my.cnf文件。
	 *	
	 *  2） 方法2 （很妥协，很纠结的办法）
	 *	进入mysql server
	 *	在mysql 命令行中运行
	 *	set global max_allowed_packet = 2*1024*1024*10
	 *	然后关闭掉这此mysql server链接，再进入。
	 *	show VARIABLES like '%max_allowed_packet%';
	 *	查看下max_allowed_packet是否编辑成功
	 *	
	 *	经验总结：
	 *	在很多台机器上用方法一都没问题，但2011年11月14日遇到一台机器死活都不成功，
	 *	使用命令行方式：set global max_allowed_packet = 16M;
	 *	也不行，但使用
	 *	set global max_allowed_packet = 2*1024*1024*10;
	 *	成功了，很是郁闷 .问题终于找出来了，不是方法的问题，是设置完成后要把命令行退出重新登录查看，看来系统变量的值在登录后会缓存。但在这台机器上使用配置INI文件。
	 *
	 *参考文档：
	 * http://www.360doc.com/content/11/0214/17/4171006_93014351.shtml
	 * http://hi.baidu.com/jgs2009/blog/item/2de0701601186202c93d6dfd.html
	 * http://www.cnblogs.com/phpfans2012/archive/2012/3/2.html
	 * http://blog.csdn.net/wpekin/article/details/5661625
	 * http://www.360doc.com/content/11/0214/17/4171006_93014351.shtml
	 * </pre>
	 * 
	 **/
	private static int BATCH_INSERT_MAX=24;
	
	
	public int batchUpdate(List<String> sqls) {
		int sum = 0;
		if (sqls == null || sqls.size() == 0) {
			return sum;
		}

		try {
			Connection con = prepareConnection();
			
			PreparedStatement pstmt = con.prepareStatement(sqls.get(0));
			pstmt.addBatch(sqls.get(0));
			log.debug(String.format(formatStr, "[Batch Insert] ", sqls.get(0)));
			
			for (int i = 1; i < sqls.size(); i++) {
				log.debug(String.format(formatStr, "[Batch Insert] ", sqls.get(i)));
				pstmt.addBatch(sqls.get(i));
			}

			jdbcCache.clear();
			return sum += ArrayUtil.sum(pstmt.executeBatch());
		} catch (Exception ex) {
			throw new DbException(ex);
		}
	}
	
	/**
	 * 批量插入或更新
	 * @param sql 标准的SQL语句（不像MySql的insert table (a,b,c) values(1,2,3),(4,5,6),(7,8,9)的特有语句)
	 * @param paramValues 
	 * @return
	 * @throws Exception
	 */
	public int batchUpdate(String sql, Object ... paramValues) {
		if (StringUtil.isBlank(sql)) {
			throw new NullPointerException("sql statement is empty or is null!");
		}
		
		if(paramValues == null || paramValues.length==0) {
			if(sql.indexOf("?")==-1) { // 执行纯SQL
				return batchUpdate(Arrays.asList(sql));
			}else {
				return 0;
			}
			//return batchUpdate(Arrays.asList(sql));
		}
		
		int sum = 0;
		final String sqlOriginal = sql.trim();
		try {
			if (isBatchInsert(sql)) { // 如果是批量插入
				int dialect = resovleDialect();

				// 1.MySql数据库特有的批量插入
				if (DbDialect.MySQLInnoDBDialect == dialect || DbDialect.MySQLDialect == dialect
						|| DbDialect.MySQLMyISAMDialect == dialect) {
					sum += doBatchInsertForMysql(sqlOriginal, paramValues);
				} else

				// 2.常规批量插入
				{
					sum += doBatchInsertForNormal(sql, paramValues);
				}
			}
		} catch (Exception ex) {
			throw new DbException(ex);
		}

		jdbcCache.clear();
		return sum;
	}

	private int doBatchInsertForNormal(String sql,Object... paramValues) throws SQLException {
		List<List<Object>> rows = convertRowParamsForBatchInsert(sql, paramValues);
		
		Connection con = prepareConnection();
		PreparedStatement pstmt = con.prepareStatement(sql);
		//log.debug(String.format(formatStr,"[Batch Insert] " ,sql));
		
		int sum = 0;
		
		for (int n = 0; n < rows.size(); n++) {

			for (int i = 0; i < rows.get(n).size(); i++) {
				pstmt.setObject(i + 1, rows.get(n).get(i));
			}
			pstmt.addBatch();
			/*
			if (n != 0 && n % BATCH_INSERT_MAX == 0) {
				log.debug(String.format(formatStr, "[Batch Insert] ","bigger than BATCH_INSERT_MAX="+BATCH_INSERT_MAX+",executeBatch one!"));
				sum += ArrayUtil.sum(pstmt.executeBatch());
			}*/
			log.debug(String.format(formatStr, "[Batch Insert] "+sql, ArrayUtil.join(rows.get(n), ",")));
		}
		
		sum += ArrayUtil.sum(pstmt.executeBatch());
		return sum;
	}
	

	/**
	 * <pre>
	 * MySql数据库专有的批量插入模式
	 * 例如：insert into someTable(id,name) values (1,'张三'),(2,'李四'),(3,'王五'),(4,'赵六'),(5,'钱七')
	 * </pre>
	 * @param sql 原始SQL
	 * @param paramValues
	 * @return
	 * @throws SQLException
	 */
	private int doBatchInsertForMysql(final String sql, Object... paramValues) throws Exception {
		
		List<List<Object>> argsList = convertRowParamsForBatchInsert(sql, paramValues);

		if (argsList.size() > BATCH_INSERT_MAX) { //如果大于阀值分批次插入
			int sumEffect = 0; // 受影响的行数

			int times = (int) Math.ceil(argsList.size() / (double) BATCH_INSERT_MAX); //例如：假如共23条记录，（阀值为3）一个SQL插入3行记录，要8个批次
			for (int i = 1; i <= times; i++) {
				List<List<Object>> rowBatch = null;
				
				if (i == times) {
					rowBatch = argsList.subList((times - 1) * BATCH_INSERT_MAX, argsList.size());
				} else {
					rowBatch = argsList.subList((i - 1) * BATCH_INSERT_MAX, i * BATCH_INSERT_MAX);
				}
				sumEffect += doOneBatchInsertForMysql(sql,rowBatch);
			}
			return sumEffect;
		}
		
		return doOneBatchInsertForMysql(sql, argsList);
	}
	
 

	/**
	 * 
	 * @param sql 原始SQL
	 * @param argsList 适应MySql重排后的参数
	 * @param isOverflow 插入的记录数是否超过系统默认阀值
	 * @return
	 * @throws SQLException
	 */
	private int doOneBatchInsertForMysql(final String sql, List<List<Object>> argsList) throws Exception {
		Connection con = prepareConnection();
		
		final String VALUES = "values";
		StringBuffer mysqlInsertBatchSql = new StringBuffer(sql.substring(0, sql.toLowerCase().indexOf(VALUES) + VALUES.length()));
		for (int i = 0; i < argsList.size(); i++) {

			mysqlInsertBatchSql.append("(");

			for (int t = 0; t < argsList.get(i).size(); t++) {
				mysqlInsertBatchSql.append("?");
				if (t != argsList.get(i).size() - 1) {
					mysqlInsertBatchSql.append(",");
				}
			}
			
			mysqlInsertBatchSql.append(")");
			
			if(i!=argsList.size()-1) {
				mysqlInsertBatchSql.append(",");
			}
		}
		
		PreparedStatement pstmt = null;
		try {
			String pSql= mysqlInsertBatchSql.toString();
			pstmt = con.prepareStatement(pSql);

			List<Object> fullParam = new ArrayList<>();
			int idx = 1;
			for (List<Object> e : argsList) {

				fullParam.addAll(e);

				for (Object arg : e) {
					pstmt.setObject(idx++, arg);
				}
			}
			

			log.debug(String.format("Batch insert - "+formatStr, pSql,StringUtil.join(fullParam, ",")));
			

			return pstmt.executeUpdate();
		} catch (Exception ex) {
			throw ex;
		} finally {
			close(pstmt, null, null);
		}
	}

	/**
	 * 解析数据库方言
	 * @return
	 * @throws SQLException
	 */
	private int resovleDialect() throws SQLException {
		Connection con = prepareConnection();
		String prdName = con.getMetaData().getDatabaseProductName();
		if (StringUtil.isNotBlank(prdName) && prdName.toLowerCase().indexOf("mysql") != -1) {
			return DbDialect.MySQLInnoDBDialect;
		}
		return DbDialect.MySQLInnoDBDialect;
	}

	private boolean isBatchInsert(String sql) {
		if (sql.startsWith("insert ") && sql.indexOf("into") != -1 && sql.indexOf("values") != -1) {
			return true;
		}
		return false;
	}
	
	/**
	 * <pre>
	 * 	参数值转行
	 * </pre>
	 * @param sql 
	 * @param paramValues 
	 * @return
	 */
	private static List<List<Object>> convertRowParamsForBatchInsert(String sql, Object... paramValues) {
		if (sql.indexOf("?") == -1 && paramValues.length > 0) {
			throw new IllegalArgumentException("sql statement args holder and args value mismatch");
		}

		List<List<Object>> params = new ArrayList<>();

		int cnt = getRowCnt(sql, paramValues);
 
		for (int i = 0; i < paramValues.length; i++) {
			if (i % cnt == 0) {
				List<Object> args = new ArrayList<>();
				for (int n = 0; n < cnt; n++) {
					args.add(paramValues[i + n]);
				}
				params.add(args);
			}
		}
		return params;
	}

	private static int getRowCnt(String sql, Object... paramValues) {
		int cnt = 0;
		String temp = sql.replace("'?'", ""); // 去除问号字符串(，这里容易有bug,用户这样的insert tb values('xxx?yyy')容易出现问题，待优化！！！！！！）
		for (char e : temp.toCharArray()) {
			if (e == '?') {
				cnt++;
			}
		}
		
		if (cnt!=0 && paramValues.length % cnt != 0) {
			throw new IllegalArgumentException("The parameter value does not match the number");
		}
		return cnt;
	}
	
 
	public static void main(String[] args) {
		Object [] aaa= new Object[]{null,"a","b","c","d","e","f",null};
		 
		String sql="insert table(a,b) values(?,?)";
		List<List<Object>> temp = convertRowParamsForBatchInsert(sql,aaa);
		System.out.println(temp);
		
		
	}
	
	// ----------------------------------------------- 批量操作结束 ----------------------------------------
	
	public  void close(Statement stmt, ResultSet rs,Connection con ) {
		try {
			if (stmt != null){
				stmt.close();
			}
		} catch (SQLException e) {
			log.error(" --- SqlExecutor close Statement fail ==> "+ e.getMessage());
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (SQLException ex) {
				log.error(" --- SqlExecutor close ResultSet fail~！ ==> "+ ex.getMessage());
			} finally {
				try {
					if (con != null ){
						//if(!con.isClosed()) { 
							log.debug(" --- >>>>>>>>>>>> thread-id:"+ Thread.currentThread().getId() + ", >>close connection, instance:"+con);
						//}
						con.close();
					}
				} catch (SQLException ec) {
					log.error(" --- SqlExecutor close Connection fail  ,"+ ec.getMessage());
				}
			}
		}
	}
	
	
}