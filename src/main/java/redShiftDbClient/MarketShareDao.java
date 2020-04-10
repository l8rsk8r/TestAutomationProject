package redShiftDbClient;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import utils.StringHelper;
import utils.fileHelpers.ReadWriteFlatFiles;

public class MarketShareDao extends ExecuteQuery {
	
	ConnectionManager redshiftConnectionManager;
	ExecuteQuery redshiftExecuteQuery;
	ReadWriteFlatFiles readWriteFiles = new ReadWriteFlatFiles();
	String userDir;
	StringHelper stringHelper = new StringHelper();

	public MarketShareDao() {
		userDir = System.getProperty("user.dir");
	}
	
	public ArrayList<String> getWeeklyMarketShareTotalSummaryIndexesData() throws SQLException {
		ArrayList<String> data = new ArrayList<String>();
		String query = readWriteFiles.fileToString(userDir+"/src/main/resources/queries/WeeklyMarketShareTotalSummaryIndexes");
		redshiftConnectionManager = new ConnectionManager();
		Connection connection = redshiftConnectionManager.createConnection();
		redshiftExecuteQuery = new ExecuteQuery();
		ResultSet result = redshiftExecuteQuery.getResultSet(connection, query);
		System.out.println("brand"+","+"hotelCount"+","+"tyOciWeighted"+","+"yoyOciWeighted"+","+"tyAriWeighted"+","+"yoyAriWeighted"
				+","+"tyRpiWeighted"+","+"yoyRpiWeighted"+","+"tyOciNonWeighted"+","+"yoyOciNonWeighted"
				+","+"tyAriNonWeighted"+","+"yoyAriNonWeighted"+","+"tyRpiNonWeighted"+","+"yoyRpiNonWeighted");
		data.add("brand"+","+"hotelCount"+","+"tyOciWeighted"+","+"yoyOciWeighted"+","+"tyAriWeighted"+","+"yoyAriWeighted"
				+","+"tyRpiWeighted"+","+"yoyRpiWeighted"+","+"tyOciNonWeighted"+","+"yoyOciNonWeighted"
				+","+"tyAriNonWeighted"+","+"yoyAriNonWeighted"+","+"tyRpiNonWeighted"+","+"yoyRpiNonWeighted");
		while (result.next()) {
			String brand = result.getString("brand");
			int hotelCount = result.getInt("hotel_count");
			Float tyOciWeighted = result.getFloat("oci_ty_weighted");
			Float yoyOciWeighted = result.getFloat("oci_yoy_weighted");
			Float tyAriWeighted = result.getFloat("ari_ty_weighted");
			Float yoyAriWeighted = result.getFloat("ari_yoy_weighted");
			Float tyRpiWeighted = result.getFloat("rpi_ty_weighted");
			Float yoyRpiWeighted = result.getFloat("rpi_yoy_weighted");
			Float tyOciNonWeighted = result.getFloat("oci_ty_non_weighted");
			Float yoyOciNonWeighted = result.getFloat("oci_yoy_non_weighted");
			Float tyAriNonWeighted = result.getFloat("ari_ty_non_weighted");
			Float yoyAriNonWeighted = result.getFloat("ari_yoy_non_weighted");
			Float tyRpiNonWeighted = result.getFloat("rpi_ty_non_weighted");
			Float yoyRpiNonWeighted = result.getFloat("rpi_yoy_non_weighted");
			data.add(brand+","+hotelCount+","+tyOciWeighted+","+yoyOciWeighted+","+tyAriWeighted+","+yoyAriWeighted
					+","+tyRpiWeighted+","+yoyRpiWeighted+","+tyOciNonWeighted+","+yoyOciNonWeighted
					+","+tyAriNonWeighted+","+yoyAriNonWeighted+","+tyRpiNonWeighted+","+yoyRpiNonWeighted);
			System.out.println(brand+","+hotelCount+","+tyOciWeighted+","+yoyOciWeighted+","+tyAriWeighted+","+yoyAriWeighted
					+","+tyRpiWeighted+","+yoyRpiWeighted+","+tyOciNonWeighted+","+yoyOciNonWeighted
					+","+tyAriNonWeighted+","+yoyAriNonWeighted+","+tyRpiNonWeighted+","+yoyRpiNonWeighted);
		}
		return data;
	}
	
	public ArrayList<String> getWeeklyMarketShareTotalSummaryIndexesData(int decimalPrecision, boolean weighted) throws SQLException {
		ArrayList<String> rawData = getWeeklyMarketShareTotalSummaryIndexesData();
		ArrayList<String> roundedDataWeighted = new ArrayList<String>();
		ArrayList<String> roundedDataNonWeighted = new ArrayList<String>();
		roundedDataWeighted.add("brand"+","+"hotelCount"+","+"tyOci"+","+"yoyOci"+","+"tyAri"+","+"yoyAri"
				+","+"tyRpi"+","+"yoyRpi");
		roundedDataNonWeighted.add("brand"+","+"hotelCount"+","+"tyOci"+","+"yoyOci"+","+"tyAri"+","+"yoyAri"
				+","+"tyRpi"+","+"yoyRpi");
		int index = 0;

		for (String dataRow: rawData) {
		if (index != 0) {
			String[] dataSplit = dataRow.split(",");
			String brand = dataSplit[0];
			String hotelCount = dataSplit[1];
			Float tyOciWeighted = stringHelper.stringToFloat(dataSplit[2], decimalPrecision);
			Float yoyOciWeighted = stringHelper.stringToFloat(dataSplit[3], decimalPrecision);
			Float tyAriWeighted = stringHelper.stringToFloat(dataSplit[4], decimalPrecision);
			Float yoyAriWeighted = stringHelper.stringToFloat(dataSplit[5], decimalPrecision);
			Float tyRpiWeighted = stringHelper.stringToFloat(dataSplit[6], decimalPrecision);
			Float yoyRpiWeighted = stringHelper.stringToFloat(dataSplit[7], decimalPrecision);
			Float tyOciNonWeighted = stringHelper.stringToFloat(dataSplit[8], decimalPrecision);
			Float yoyOciNonWeighted = stringHelper.stringToFloat(dataSplit[9], decimalPrecision);
			Float tyAriNonWeighted = stringHelper.stringToFloat(dataSplit[10], decimalPrecision);
			Float yoyAriNonWeighted = stringHelper.stringToFloat(dataSplit[11], decimalPrecision);
			Float tyRpiNonWeighted = stringHelper.stringToFloat(dataSplit[12], decimalPrecision);
			Float yoyRpiNonWeighted = stringHelper.stringToFloat(dataSplit[13], decimalPrecision);
			roundedDataWeighted.add(brand+","+hotelCount+","+tyOciWeighted+","+yoyOciWeighted+","+tyAriWeighted+","+yoyAriWeighted
					+","+tyRpiWeighted+","+yoyRpiWeighted);	
			roundedDataNonWeighted.add(brand+","+hotelCount+","+tyOciNonWeighted+","+yoyOciNonWeighted+","+tyAriNonWeighted
					+","+yoyAriNonWeighted+","+tyRpiNonWeighted+","+yoyRpiNonWeighted);	
			}
		index++;
		}
		if (weighted == true) {
			return roundedDataWeighted;
		} else {
			return roundedDataNonWeighted;
		}
	}

	public static void main(String[] args) throws SQLException {
		MarketShareDao marketShareDao = new MarketShareDao();
		ArrayList<String> data = marketShareDao.getWeeklyMarketShareTotalSummaryIndexesData(2, false);
		for (String dataRow: data) {
			System.out.println(dataRow);
		}
	}

}
