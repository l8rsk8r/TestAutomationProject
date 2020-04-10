package poc;

import java.sql.*;
import java.util.Properties;


public class RedShift {
	String DB_USERNAME;
	String DB_PASSWORD;
	

	public static void getData(Connection connection) throws SQLException, ClassNotFoundException {
		Statement stmt = connection.createStatement();
		ResultSet rs=stmt.executeQuery("select * from rdm.calendar");
		ResultSetMetaData rsmd = rs.getMetaData();
		int numberOfColumns = rsmd.getColumnCount();
		System.out.println(numberOfColumns);
			System.out.println(rs.findColumn("as_of_date"));
			System.out.println(rsmd.getColumnType(rs.findColumn("dow_name")));
	}
	
	public static void main(String[] args) {

		final String DB_URL = base.Properties.environmentProperties.get("redshiftUrl");
		final String DB_DRIVER = "com.amazon.redshift.jdbc.Driver";
		String DB_USERNAME = base.Properties.environmentProperties.get("redshiftUsername");
		String DB_PASSWORD = base.Properties.environmentProperties.get("redshiftPassword");
		
		Connection connection = null;
		try {
			Class.forName(DB_DRIVER);
			Properties properties = new Properties();
			properties.setProperty("user", DB_USERNAME);
			properties.setProperty("password", DB_PASSWORD);
			connection = DriverManager.getConnection(DB_URL, properties);
			connection.clearWarnings();
			RedShift.getData(connection);
			connection.close();

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
}
