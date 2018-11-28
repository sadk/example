package org.lsqt.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DerbyTest {
	public static void main(String ...args) throws Exception {
		Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
		Connection conn = DriverManager.getConnection("jdbc.derby.derbyDB;create=true", "user", "pwd");

		Statement st = conn.createStatement();
		st.execute("create table test1(id int,name varchar(20))");
		st.execute("insert into test1 values(1,'sinboy')");
		st.execute("inert into test1 values(2,'Tom')");
		ResultSet rs = st.executeQuery("select * from test1");
		while (rs.next()) {
			System.out.println("id:" + rs.getInt(1) + "  name:" + rs.getString(2));
		}
		rs.close();
		st.close();
		conn.commit();
		conn.close();
	}
}
