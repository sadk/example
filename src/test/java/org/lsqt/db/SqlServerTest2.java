package org.lsqt.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

public class SqlServerTest2 {
	static String url = "jdbc:sqlserver://syswin-server:1433;DatabaseName=SyswinETSNew";
	static String username = "sa";
	static String password = "sa1";
	static String driverClassName="com.microsoft.sqlserver.jdbc.SQLServerDriver";
	
	public static void main(String[] args) throws Exception {
		String sql = "{call UP_Report_NewEtsTest(?,?)}";// 第三个参数是输入参数，第二个输出参数

		Class.forName(driverClassName);
		Connection con = DriverManager.getConnection(url, username, password);
		CallableStatement stmt = con.prepareCall(sql);

		
		stmt.setString(1, "1000"); // 执行之前要使用setXXX来替换SQL语句中的问号参数
		stmt.registerOutParameter(2, Types.VARCHAR); // 注册输出参数类型（注意索引要和问号的位置对应）
		
		stmt.execute();
		
		ResultSet rs = stmt.getResultSet();
		
		
		System.out.println(stmt.getMoreResults());
		/*while(rs.next()) {
			System.out.println(rs.getObject(1));
		}*/
		
		// 提取输出参数
		System.out.println(stmt.getObject(2));
		 

		// 控制台输出
		// System.out.println("姓名：" + xm);
		// System.out.println("工资：" + gz);

		// 关闭相关对象
		rs.close();
		stmt.close();
		con.close();

	}

}
