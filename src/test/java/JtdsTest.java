import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class JtdsTest {
	public static void main(String[] srg) {

		try {
			String Driver = "oracle.jdbc.driver.OracleDriver"; // 连接数据库的方法
			String URL = "jdbc:oracle:thin:@10.83.24.3:1521:test_sit"; // orcl为数据库的SID
			String Username = "sitbla"; // 用户名
			String Password = "yHZ9VFpW5EgH"; // 密码
			Class.forName(Driver).newInstance(); // 加载数据库驱动
			Connection con = DriverManager.getConnection(URL, Username, Password);
			PreparedStatement pstmt = con.prepareStatement("select * from v$version");
			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				System.out.println(rs.getString(1));
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		test();
	}

	public static void test() {
		try {
			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			Connection con = DriverManager.getConnection("jdbc:jtds:oracle://10.83.24.3:1521;DatabaseName=sitbla", "sitbla", "yHZ9VFpW5EgH");

			Statement stmt = con.createStatement();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
