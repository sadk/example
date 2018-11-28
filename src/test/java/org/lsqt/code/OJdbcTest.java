package org.lsqt.code;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class OJdbcTest {
	public static void main(String args[]) throws SQLException {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			String url = "jdbc:oracle:thin:@localhost:1521:orcl";
			Connection c = DriverManager.getConnection(url, "system", "123456");
			Statement stmt = c.createStatement();
			String sql = "select 1 from dual ";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()) {
				System.out.println("dname: " + rs.getInt(1));
			}
			stmt.close();
			c.close();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
}
