package org.lsqt.db;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Types;

public class MysqlProduceTest {
	static String url = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&connectTimeout=6000&socketTimeout=6000";
	static String username = "root";
	static String password = "123456";

	public static void main(String[] args) throws Exception {
		String sql = "{call sp()}";

		// 加载驱动程序
		Class.forName("com.mysql.jdbc.Driver");

		// 获取连接对象
		Connection con = DriverManager.getConnection(url, username, password);

		// 获取执行对象
		CallableStatement cst = con.prepareCall(sql);

		// 执行之前要使用setXXX来替换SQL语句中的问号参数
		//cst.setInt(1, 7788);

		// 注册输出参数类型（注意索引要和问号的位置对应）
		//cst.registerOutParameter(2, Types.VARCHAR);
		//cst.registerOutParameter(3, Types.FLOAT);

		// 执行SQL命令
		cst.execute();
		ResultSet rs = cst.getResultSet();
		while(rs.next()) {
			System.out.println(rs.getObject(3));
		}
		
		// 提取输出参数
		// xm = cst.getString(2);
		// gz = cst.getFloat(3);

		// 控制台输出
		// System.out.println("姓名：" + xm);
		// System.out.println("工资：" + gz);

		// 关闭相关对象
		rs.close();
		cst.close();
		con.close();

		// db.execute("xxxx",)
	}

}
