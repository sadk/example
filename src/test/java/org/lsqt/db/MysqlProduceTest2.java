package org.lsqt.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;

public class MysqlProduceTest2 {
	static String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&connectTimeout=6000&socketTimeout=6000";
	static String username = "root";
	static String password = "123456";

	public static void main(String[] args) throws Exception {
		String sql = "{call  new_procedure3()}";

		Class.forName("com.mysql.jdbc.Driver");

		Connection con = DriverManager.getConnection(url, username, password);

		CallableStatement stmt = con.prepareCall(sql);
		stmt.execute();
		
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
		// xm = cst.getString(2);
		// gz = cst.getFloat(3);

		// 控制台输出
		// System.out.println("姓名：" + xm);
		// System.out.println("工资：" + gz);

		// 关闭相关对象
		//rs.close();
		stmt.close();
		con.close();

		// db.execute("xxxx",)
	}

}
