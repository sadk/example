package org.lsqt.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;

public class SqlServerTest {
	
	static String url = "jdbc:jtds:sqlserver://syswin-server:1433;DatabaseName=SyswinETSNew";
	static String username = "sa";
	static String password = "sa1";

	public static void main(String[] args) throws Exception {
		String sql = "{call UP_Report_NewEtsTest(?,?)}";// 第三个参数是输入参数，第二个输出参数

		// 加载驱动程序
		Class.forName("net.sourceforge.jtds.jdbc.Driver");

		
		// 获取连接对象
		Connection con = DriverManager.getConnection(url, username, password);

		// 获取执行对象
		CallableStatement cst = con.prepareCall(sql);

		// 执行之前要使用setXXX来替换SQL语句中的问号参数
		cst.setString(1, "1000");
		
		
		// 注册输出参数类型（注意索引要和问号的位置对应）
		cst.registerOutParameter(2, Types.VARCHAR);
		

		// 执行SQL命令
		cst.execute();
		ResultSet rs = cst.getResultSet();
		System.out.println(rs.next());
		/*while(rs.next()) {
			System.out.println(rs.getObject(1));
		}*/
		
		// 提取输出参数
		System.out.println(cst.getObject(2));
		 

		// 控制台输出
		// System.out.println("姓名：" + xm);
		// System.out.println("工资：" + gz);

		// 关闭相关对象
		rs.close();
		cst.close();
		con.close();

		// db.execute("xxxx",Object []{"1000",null},ParamType ...types)
	}
}
