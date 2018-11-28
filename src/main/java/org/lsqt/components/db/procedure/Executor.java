package org.lsqt.components.db.procedure;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.lsqt.components.db.DbException;
import org.lsqt.components.db.Plan;
import org.lsqt.components.db.procedure.DataSet.Table;
import org.omg.CORBA.SystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 存储过程的实现,暂只支持结果集
 * @author mmyuan
 *
 */
public class Executor implements DbProcedure{
	static final Logger log = LoggerFactory.getLogger(Executor.class);

	private DataSource dataSource;
	
	public void setConfigDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
	
	private static ThreadLocal<Connection> THREAD_LOCAL_CON = new ThreadLocal<>();
	
	public void setCurrentConnection(Connection connection){
		if(THREAD_LOCAL_CON.get()!=null) {
			closeQuiet(connection);
		}
		THREAD_LOCAL_CON.set(connection);
	}
	
	
	public Connection getCurrentConnection() {
		return THREAD_LOCAL_CON.get();
	}
	
	private Connection prepareConnection() throws SQLException {
		//优先从绑定的当前线程取连接
		Connection con = THREAD_LOCAL_CON.get();
		if (con != null && con.isClosed() == false) {
			print(con);
			return con;
		} 
		
		THREAD_LOCAL_CON.set(this.dataSource.getConnection());
		con = THREAD_LOCAL_CON.get();
		
		print(con);
		
		return con;
	}
	
	private DataSet dataSet = new DataSet.DataSetModel();
	
	
	public DbProcedure execute(String procedureName, Object[] args, Param... paramType) throws SystemException{
		if(args!=null && paramType!=null) {
			if(args.length != paramType.length) {
				throw new IllegalArgumentException("没有指定存储过程入参类型(In,Out或InOut)");
			}
		}
		Connection con = null;
		CallableStatement stmt = null;
		try{
			String sql = prepareSql(procedureName, args);
			
			List<Integer> outputIndex = new ArrayList<>(); // 输出参数的索引
			con = prepareConnection();
			stmt = con.prepareCall(sql);//,ResultSet.TYPE_FORWARD_ONLY,ResultSet.CONCUR_READ_ONLY
			
			for (int i=0;i<paramType.length;i++) {
				int idx = i+1;
				if (Param.IN == paramType[i] || Param.NORMAL == paramType[i]  ) {
					stmt.setObject(idx, args[i]);
				} 
				
				if(Param.OUT == paramType[i] || Param.IN_OUT == paramType[i]) {
					
					stmt.registerOutParameter(idx, prepareSqlType(args[i]));
					outputIndex.add(idx);
				}
			}
			
			stmt.execute();
			
			
			// 返回结果集
			List<DataSet.Table> tables = new ArrayList<>();
			ResultSet rs = stmt.getResultSet();
			tables.add(prepareTable(rs));
				
			while(stmt.getMoreResults()) {
				rs = stmt.getResultSet();
				tables.add(prepareTable(rs));
				
			}
			
			// 提取输出参数
			List<Object> outputObjects = new ArrayList<>();
			for (Integer i : outputIndex) {
				outputObjects.add(stmt.getObject(i));
			}
			
			// 封装DataSet
			DataSet ds = new DataSet.DataSetModel();
			ds.setOutputObjects(outputObjects);
			ds.setTables(tables);
			
			this.dataSet = ds;
			
		}catch(SQLException ex) {
			throw new DbException(ex);
		}finally{
			closeQuiet(stmt,con);
		}
		
		return this;
	}
	

	
	public DbProcedure execute(String procedureName) throws SystemException {
		return this.execute(procedureName, new Object []{});
	}

	
	/**
	 * 执行一个SQL计划
	 * @param plan 
	 * @param isTransaction 是否开启事务
	 * @throws DbException
	 */
	public void executePlan(boolean isTransaction,Plan plan)  {
		Connection con = null;
		try{
			con = prepareConnection();
			con.setAutoCommit(isTransaction);
			
			if(isTransaction) {
				System.out.println(" --- >>>>>>>>>>>>> Transaction Begin !!! (Thead-id:"+Thread.currentThread().getId()+")");
			}
			
			if(isTransaction) {
				con.setTransactionIsolation(Connection.TRANSACTION_READ_COMMITTED);
			}
			plan.doExecutePlan();
			
			if(isTransaction) {
				con.commit();
				System.out.println(" --- >>>>>>>>>>>> Transaction Commited !!! (Thead-id:"+Thread.currentThread().getId()+")");
			}
		}catch(Exception ex){
			if(con!=null) {
				try {
					if(isTransaction) {
						con.rollback();
						System.out.println(" --- >>>>>>>>>>>> Transaction Rollback !!! (Thead-id:"+Thread.currentThread().getId()+" ,con:"+con+")");
					}
					
				} catch (SQLException e) {
					String info = String.format(formatStr, "rollback error~!", e.getMessage());
					System.out.println(info);
					throw new DbException(info, e);
				}
			}
			throw new DbException(ex);
		}finally{
			closeQuiet(null, null, con);
		}
	}
	
	/**
	 * 执行一个SQL计划
	 * @param plan 
	 * @throws DbException
	 */
	public void executePlan(Plan plan)  {
		executePlan(true,plan);
	}
	
	public Table table() {
		return this.dataSet.getTables().get(0);
	}

	
	public List<Table> tables() {
		return this.dataSet.getTables();
	}

	
	public Table tables(int index) {
		return this.dataSet.getTables().get(index);
	}

	
	public Object outputObject() {
		return this.dataSet.getOutputObjects().get(0);
	}

	
	public <T> T outputObject(Class<T> requiredType) {
		return null;
	}

	
	public List<Object> outputObjects() {
		return this.dataSet.getOutputObjects();
	}

	// -------------------------------------------- 辅助方法 ------------------------------------------------
	void closeQuiet(AutoCloseable... objs) {
		for (AutoCloseable e : objs) {
			try {
				if (e != null) {
					e.close();
				}
			} catch (Exception ex) {
				// nothing..
			}
		}
	}
	
	static final String formatStr = " --- %s -- ===> %s"; // 用于格式化日志
	void print(Connection con) throws SQLException {
		//if (log.isDebugEnabled()) {
		DatabaseMetaData meta = con.getMetaData();
		System.out.println(" --- >>>>>>>>>>>> thread-id:" + Thread.currentThread().getId() + ", prepare Connection--> instance: " + con + ", url: "+meta.getURL()+"  , username: "+meta.getUserName()+"");
		System.out.println(" --- >>>>>>>>>>>> thread-id:" + Thread.currentThread().getId() + "," 
				+ con.getMetaData().getDatabaseProductName() 
				+ con.getMetaData().getDatabaseMajorVersion() + "." 
				+ con.getMetaData().getDatabaseMinorVersion() + "  "
				+ con.getMetaData().getDriverVersion() + "  ") ;
		//}
	}
	
	
	/** 将一个结果集转化为表格对象 (含表头和表体)**/
	DataSet.Table prepareTable(ResultSet rs) throws SQLException {
		DataSet.Table table = new DataSet.Table.TableModel();
		try {
			List<String> head = new ArrayList<>();
			ResultSetMetaData meta = rs.getMetaData();
			int columnCnt = meta.getColumnCount();
			for (int i = 0; i < columnCnt; i++) {
				head.add(meta.getColumnLabel(i + 1));
			}
			List<List<Object>> data = new ArrayList<>();
			while (rs.next()) {
				List<Object> rowCells = new ArrayList<>();
				for (int i = 0; i < columnCnt; i++) {
					rowCells.add(rs.getObject(i + 1));
				}
				data.add(rowCells);
			}
			
			table.setHead(head);
			table.setData(data);
		} catch (Exception ex) {
			throw new SQLException(ex);
		} finally {
			rs.close();
		}
		
		return table;
	}
	
	/***
	 * 根据参数值，获取对应的数据库类型，暂不支持blob大字段
	 * @param arg
	 * @return
	 * @throws SQLException
	 */
	int prepareSqlType(Object arg) throws SQLException {
		if(arg == null) return java.sql.Types.OTHER;
		
		Class<?> currType = arg.getClass();
		
		// 字符
		if(String.class.isAssignableFrom(currType)) {
			return java.sql.Types.VARCHAR;
		}
		else if (Character.class.isAssignableFrom(currType) || currType == char.class) {
			return java.sql.Types.CHAR;
		}
		
		// 数字
		else if (Integer.class.isAssignableFrom(currType) || currType == int.class) { //数字
			return java.sql.Types.INTEGER;
		} 
		else if (Long.class.isAssignableFrom(currType) || currType == long.class) {
			return java.sql.Types.INTEGER;
		}  
		else if (Float.class.isAssignableFrom(currType) || currType == float.class) {
			return java.sql.Types.FLOAT;
		} 
		else if (Double.class.isAssignableFrom(currType) || currType == double.class) {
			return java.sql.Types.DOUBLE;  
		} 
		else if (Short.class.isAssignableFrom(currType) || currType == short.class) {
			return java.sql.Types.INTEGER;
		}
		else if (Byte.class.isAssignableFrom(currType) || currType == byte.class) {
			return java.sql.Types.TINYINT;
		} 
		else if(BigInteger.class.isAssignableFrom(currType)) {
			return java.sql.Types.BIGINT;
		}
		else if(BigDecimal.class.isAssignableFrom(currType)) {
			return java.sql.Types.DECIMAL;
		}
	
		// 布尔型
		else if (Boolean.class.isAssignableFrom(currType) || currType == boolean.class) { 
			return  java.sql.Types.BIT;
		}  
	
		// 日期类型
		else if (Date.class.isAssignableFrom(currType)) { 
		 	if(java.sql.Date.class.isAssignableFrom(currType)){
		 		return  java.sql.Types.DATE;
		 	}
		 	if(java.sql.Time.class.isAssignableFrom(currType)) {
		 		return java.sql.Types.TIME;
		 	}
		 	if(java.sql.Timestamp.class.isAssignableFrom(currType)) {
		 		return java.sql.Types.TIMESTAMP;
		 	}
		 	return java.sql.Types.DATE;
		} 
		
		
		else{
			throw new SQLException("无效的数据类型") ;
		}
		
	}
	
	String prepareSql(String procedureName, Object[] args) {
		String sql = "{call %s (%s)}";
		StringBuilder paramHold = new StringBuilder();
		for(int i=0;i<args.length;i++){
			paramHold.append("?");
			if(i!=args.length-1){
				paramHold.append(",");
			}
		}
		return String.format(sql, procedureName,paramHold.toString());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	public static void main(String[] args) throws Exception {
		testSqlServer();
		//testMysql();
	}
	
	public static void testMysql() throws Exception {
		final String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&connectTimeout=6000&socketTimeout=6000";
		final String username = "root";
		final String password = "123456";
		
		Class.forName("com.mysql.jdbc.Driver");
		Connection connection = DriverManager.getConnection(url, username, password);

		final DbProcedure exe = new Executor();
		exe.setCurrentConnection(connection);
		
		DbProcedure hold = exe.execute("new_procedure4",new Object [] {1,null,null} ,Param.IN ,Param.IN_OUT, Param.OUT);
		System.out.println(hold.table().getData());
		System.out.println("存储过程返回参数:"+hold.outputObjects());
		
		
		hold = exe.execute("new_procedure2");
		System.out.println(hold.table().getData());
		
		// 开启事物，可以执行多个存储过程，一般包在ServiceImpl里 ！！！！
		exe.executePlan(true,new Plan(){ // 或者 :exe.executePlan(new Plan(){...}) 默认就是事务开启!!! 
			public void doExecutePlan() throws SystemException {
				DbProcedure db = exe.execute("new_procedure3");
				System.out.println(db.table());
			}
		});
	}

	
	public static void testSqlServer() throws Exception {
		// 使用jtds 驱动
		Class.forName("net.sourceforge.jtds.jdbc.Driver");
		final String url = "jdbc:jtds:sqlserver://syswin-server:1433/SyswinETSNew";
		
		// 使用微软官方驱动
		//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		//final String url = "jdbc:sqlserver://syswin-server:1433;DatabaseName=SyswinETSNew";
		
		final String username = "sa";
		final String password = "sa1";
		
		Connection connection = DriverManager.getConnection(url, username, password);

		final DbProcedure exe = new Executor();
		exe.setCurrentConnection(connection);
		
		DbProcedure hold = exe.execute("UP_Report_NewEtsTest",new Object [] {"10000",null} ,Param.IN ,Param.OUT);
		
		System.out.println(hold.tables().get(0).getData());
		System.out.println("存储过程返回参数:"+hold.outputObject());
		
		//System.out.println(com.alibaba.fastjson.JSON.toJSONString(hold.table().forKeyValueList(),true));
	}

}
