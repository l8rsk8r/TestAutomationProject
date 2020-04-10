package redShiftDbClient;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.Properties;


public class ConnectionManager {	
	
	private String DB_URL;
	final String DB_DRIVER = "com.amazon.redshift.jdbc.Driver";
	private String DB_USERNAME;
	private String DB_PASSWORD;
	
	public ConnectionManager() {
		DB_URL = base.Properties.environmentProperties.get("redshiftUrl");
	}
	
	private void setCredentials() {
		DB_USERNAME = base.Properties.environmentProperties.get("redshiftUsername");
		DB_PASSWORD = base.Properties.environmentProperties.get("redshiftPassword");
	}
	
	public Connection createConnection() {
		Connection connection = null;
		setCredentials();
		try {
			Class.forName(DB_DRIVER);
			Properties properties = new Properties();
			properties.setProperty("user", DB_USERNAME);
			properties.setProperty("password", DB_PASSWORD);
			connection = DriverManager.getConnection(DB_URL, properties);
			connection.clearWarnings();
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
		return connection;
	}
	
	public void closeConnection(Connection connection) {
		try {
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) throws SQLException {
		ConnectionManager redshiftConnectionManager = new ConnectionManager();
		Connection connection = redshiftConnectionManager.createConnection();
		redshiftConnectionManager.closeConnection(connection);
	}
}
