package poc;

import base.Properties;
import org.testng.annotations.Test;
import utils.DateHelper;
import utils.fileHelpers.ReadWriteFlatFiles;

import java.sql.*;

public class Hive {

	private String DB_USERNAME;
	private String DB_PASSWORD;
	private String DB_URL;
	
	private void setCredentials() {
		DB_USERNAME = Properties.environmentProperties.get("hiveUsername");
		DB_PASSWORD = Properties.environmentProperties.get("hivePassword");
		DB_URL = Properties.environmentProperties.get("hiveUrl");
	}
	
	@Test
	public void connect() throws SQLException, ClassNotFoundException {
		setCredentials();
		DateHelper dataHelper = new DateHelper();
		String timeStamp = dataHelper.getCurrentDateFormatted("hh:mm:ss");
		String connectionString = DB_URL+":8444/;transportMode=http;httpPath=gateway/default/hive;ssl=true;sslTrustStore=/Library/Java/JavaVirtualMachines/jdk1.8.0_211.jdk/Contents/Home/jre/lib/security/cacerts;trustStorePassword=changeit";
		Class.forName("org.apache.hive.jdbc.HiveDriver");
		Connection connection = DriverManager.getConnection(connectionString, DB_USERNAME, DB_PASSWORD);
		System.out.println(timeStamp);
		String print = "";
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery("select * from edz_sales_test.delphi_booking limit 10");
		String thdate = dataHelper.getCurrentDateFormatted("hh:mm:ss");
		System.out.println(thdate+"got result");
		ReadWriteFlatFiles rw = new ReadWriteFlatFiles();
		int index = 0;
		while(rs.next()) {
			String city = rs.getString("account_id");
			print = print+city+"\n";
			index++;
		}
		System.out.println("row count"+index);
		String file = System.getProperty("user.dir")+"/target/Hive Data.csv";
		rw.writeToFile(file, print);
		String timeStamp2 = dataHelper.getCurrentDateFormatted("hh:mm:ss");
		System.out.println(timeStamp2);
		connection.close();
	}
}
