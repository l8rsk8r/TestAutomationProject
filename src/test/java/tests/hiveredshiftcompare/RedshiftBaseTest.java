package tests.hiveredshiftcompare;

import base.BaseSetup;
import base.Properties;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import redShiftDbClient.ConnectionManager;
import redShiftDbClient.ExecuteQuery;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.testng.Assert.assertEquals;

public class RedshiftBaseTest extends BaseSetup {
		
	ExecuteQuery executeQuery;

	ConnectionManager connectionManagerRed;
	Connection connection;

	@BeforeClass
	public void settingRedshiftBaseTest() {
		connectionManagerRed = new ConnectionManager();
		connection = connectionManagerRed.createConnection();
		executeQuery = new ExecuteQuery();
	}

	@Test
	private void testingRedShift() throws SQLException {
	
	String redshiftURL = Properties.environmentProperties.get("redshiftUrl");
	System.out.println("redshiftURL is " + redshiftURL);
	
	String query = "select * from rdm.property\n" +
			"where property_name = 'Tru by Hilton York'" +
			" and effective_to = '2173-12-31'";
	ResultSet result = executeQuery.getResultSet(connection, query);
	
	while (result.next()) {
		String property_name = result.getString("property_name");
		System.out.println(property_name);
		assertEquals(property_name,"Tru by Hilton York" );
	}
	
	}
}
	