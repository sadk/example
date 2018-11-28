package org.lsqt.db;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.lsqt.components.db.procedure.DataSet;
import org.lsqt.components.db.procedure.DbProcedure;
import org.lsqt.components.db.procedure.DbProcedure.Param;

public class MysqlProduceTest3 {
	static String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&connectTimeout=6000&socketTimeout=6000";
	static String username = "root";
	static String password = "123456";

	public DbProcedure execute(String procedureName, Object[] args, Param... paramType) throws SQLException, ClassNotFoundException{
		if(args!=null && paramType!=null) {
			if(args.length != paramType.length) {
				throw new IllegalArgumentException("没有指定存储过程入参类型(In,Out或InOut)");
			}
		}
		String sql = prepareSql(procedureName, args);
		
		List<Integer> outputIndex = new ArrayList<>(); // 输出参数的索引
		Connection con = prepareConnection();
		CallableStatement stmt = con.prepareCall(sql);
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
		
		System.out.println(ds);
		return null;
	}
	
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
	
	
	int prepareSqlType(Object obj) throws SQLException {
		if(obj == null) return java.sql.Types.JAVA_OBJECT;
		
		Class<?> currType = obj.getClass();
		
		// 字符
		if(String.class.isAssignableFrom(currType)) {
			return java.sql.Types.VARCHAR;
		}
		
		// 数字
		if (Integer.class.isAssignableFrom(currType) || currType == int.class) { //数字
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
	private Connection prepareConnection() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection con = DriverManager.getConnection(url, username, password);
		return con;
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
		MysqlProduceTest3 test = new MysqlProduceTest3();
		test.execute("new_procedure4", new Object[] { 1,null,null },Param.IN ,Param.IN_OUT, Param.OUT);
	}
	
	
	public static void mainTest(String[] args) throws Exception {
		//DataSet ds = db.execute("",Object [] args,Param ... paramType).dataSet();
		
		//Table table = db.execute("",Object [] args,Param ... paramType).table();
		//List<Table> tables = db.execute("",Object [] args,Param ... paramType).tables();
		
		//Object obj = db.execute("",Object [] args,Param ... paramType).outputObject();
		//List<Object> objs = db.execute("",Object [] args,Param ... paramType).outputObjects();
		
		//db.execute("",Object [] args,Param ... paramType).table().forList(Bean.class);
		//db.execute("",Object [] args,Param ... paramType).tables(0).forList(Bean.class);
		
		
		String sql = "{call new_procedure4(?,?,?)}";

		Class.forName("com.mysql.jdbc.Driver");

		Connection con = DriverManager.getConnection(url, username, password);

		CallableStatement stmt = con.prepareCall(sql);
		stmt.setObject(1, 1);
		stmt.registerOutParameter(2, java.sql.Types.VARCHAR);
		stmt.registerOutParameter(3, java.sql.Types.VARCHAR);
		stmt.execute();
		
		// 返回结果集
		ResultSet rs = stmt.getResultSet();
		while(rs.next()) {
			System.out.println(rs.getObject(3));
		}
		rs.close();
			
		while(stmt.getMoreResults()) {
			rs = stmt.getResultSet();
			while(rs.next()) {
				System.out.println(rs.getObject(3));
			}
			rs.close();
		}
			
		
		 
		
		// 提取输出参数
		Object output = stmt.getString(2);
		System.out.println(output);
		 
		Object output2 = stmt.getString(3);
		System.out.println(output2);

		// 关闭相关对象
		stmt.close();
		con.close();

		// db.execute("xxxx",)
	}

}
