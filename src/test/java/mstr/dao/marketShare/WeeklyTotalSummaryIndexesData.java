package mstr.dao.marketShare;

import redShiftDbClient.ExecuteQuery;
import utils.CollectionsHelper;
import utils.StringHelper;
import utils.fileHelpers.ReadWriteFlatFiles;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class WeeklyTotalSummaryIndexesData extends ExecuteQuery {

	Connection connection;
	String userDir;
	String getMetricsQuery;
	ReadWriteFlatFiles readWriteFiles = new ReadWriteFlatFiles();
	ResultSet getMetricsResult;
	public HashMap<String, String> weightedMetricsData = new HashMap<String, String>();
	public HashMap<String, String> nonWeightedMetricsData = new HashMap<String, String>();
	StringHelper stringHelper = new StringHelper();
	CollectionsHelper collectionHelper = new CollectionsHelper();
	
	public WeeklyTotalSummaryIndexesData() {
		userDir = System.getProperty("user.dir");
		getMetricsQuery = readWriteFiles.fileToString(userDir+"/src/main/resources/queries/WeeklyMarketShareTotalSummaryIndexes");
	}
	
	public void getRawData() throws SQLException {
		connection = createConnection();
		getMetricsResult = getResultSet(connection, getMetricsQuery);
		while (getMetricsResult.next()) {
			String brand = getMetricsResult.getString("brand");
			int hotelCount = getMetricsResult.getInt("hotel_count");
			Float tyOciWeighted = getMetricsResult.getFloat("oci_ty_weighted");
			Float yoyOciWeighted = getMetricsResult.getFloat("oci_yoy_weighted");
			Float tyAriWeighted = getMetricsResult.getFloat("ari_ty_weighted");
			Float yoyAriWeighted = getMetricsResult.getFloat("ari_yoy_weighted");
			Float tyRpiWeighted = getMetricsResult.getFloat("rpi_ty_weighted");
			Float yoyRpiWeighted = getMetricsResult.getFloat("rpi_yoy_weighted");
			Float tyOciNonWeighted = getMetricsResult.getFloat("oci_ty_non_weighted");
			Float yoyOciNonWeighted = getMetricsResult.getFloat("oci_yoy_non_weighted");
			Float tyAriNonWeighted = getMetricsResult.getFloat("ari_ty_non_weighted");
			Float yoyAriNonWeighted = getMetricsResult.getFloat("ari_yoy_non_weighted");
			Float tyRpiNonWeighted = getMetricsResult.getFloat("rpi_ty_non_weighted");
			Float yoyRpiNonWeighted = getMetricsResult.getFloat("rpi_yoy_non_weighted");
			weightedMetricsData.put(brand+"HotelCount", Integer.toString(hotelCount));
			weightedMetricsData.put(brand+"occupancyIndex", Float.toString(tyOciWeighted));
			weightedMetricsData.put(brand+"yoyOccupancyIndex", Float.toString(yoyOciWeighted));
			weightedMetricsData.put(brand+"averageDailyRateIndex", Float.toString(tyAriWeighted));
			weightedMetricsData.put(brand+"yoyAverageDailyRateIndex", Float.toString(yoyAriWeighted));
			weightedMetricsData.put(brand+"revenuePerAvailableRoomIndex", Float.toString(tyRpiWeighted));
			weightedMetricsData.put(brand+"yoyRevenuePerAvailableRoomIndex", Float.toString(yoyRpiWeighted));
			nonWeightedMetricsData.put(brand+"HotelCount", Integer.toString(hotelCount));
			nonWeightedMetricsData.put(brand+"occupancyIndex", Float.toString(tyOciNonWeighted));
			nonWeightedMetricsData.put(brand+"yoyOccupancyIndex", Float.toString(yoyOciNonWeighted));
			nonWeightedMetricsData.put(brand+"averageDailyRateIndex", Float.toString(tyAriNonWeighted));
			nonWeightedMetricsData.put(brand+"yoyAverageDailyRateIndex", Float.toString(yoyAriNonWeighted));
			nonWeightedMetricsData.put(brand+"revenuePerAvailableRoomIndex", Float.toString(tyRpiNonWeighted));
			nonWeightedMetricsData.put(brand+"yoyRevenuePerAvailableRoomIndex", Float.toString(yoyRpiNonWeighted));
		}
	}
	
	public void formatData(int decimalPrecision) {
		weightedMetricsData = collectionHelper.changeDecimalPrecisionOfHashMapValues(weightedMetricsData, decimalPrecision);
		nonWeightedMetricsData = collectionHelper.changeDecimalPrecisionOfHashMapValues(nonWeightedMetricsData, decimalPrecision);
	}
	
	public static void main(String[] args) throws SQLException {
		WeeklyTotalSummaryIndexesData wtsid = new WeeklyTotalSummaryIndexesData();
		wtsid.getRawData();
		wtsid.formatData(2);
		HashMap<String, String> weighteddata = wtsid.weightedMetricsData;
		for (String key: weighteddata.keySet()) {
			System.out.println(key+"\t"+weighteddata.get(key));
		}	
		HashMap<String, String> nonweighteddata = wtsid.nonWeightedMetricsData;
		for (String key: nonweighteddata.keySet()) {
			System.out.println(key+"\t"+nonweighteddata.get(key));
		}
	}

}
