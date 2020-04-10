package comparators.tableauToMstrApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import customReportHelper.MarketShareReportHelper;
import utils.Calculator;
import utils.StringHelper;
import utils.fileHelpers.DuplicateAndUpdateFiles;

public class MstrTableauMarketShareMonthlyTotalSummary extends MarketShareReportHelper{

	int tableauHotelsCount;
	int mstrHotelsCount;
	ArrayList<String> tableauHotelNames;
	ArrayList<String> mstrHotelNames;
	List<HashMap<String, String>> tableauData;
	List<HashMap<String, String>> mstrData;
	DuplicateAndUpdateFiles duplicateAndUpdateFiles = new DuplicateAndUpdateFiles();
	private String optionsOldString = "<!--hotel-options-->";
	StringHelper stringHelper = new StringHelper();
	Calculator calculator = new Calculator();

	public MstrTableauMarketShareMonthlyTotalSummary(String tableauFilePath, String mstrFilePath, String targetDir) throws Exception {
		super(targetDir);
		tableauReportReader.MarketShareMonthlyReportReader tableauReportReader = new tableauReportReader.MarketShareMonthlyReportReader(tableauFilePath);
		tableauHotelsCount = tableauReportReader.getRecordCount();
		tableauHotelNames = tableauReportReader.getHotels();
		tableauData = tableauReportReader.getData();
		mstrReportReader.MarketShareMonthlyReportReader mstrReportReader = new mstrReportReader.MarketShareMonthlyReportReader(mstrFilePath);
		mstrHotelsCount = mstrReportReader.getRecordCount();
		mstrHotelNames = mstrReportReader.getHotels();
		mstrData = mstrReportReader.getData();
	}
	
	public void reportGenerator() throws IOException {
		duplicateAndUpdateFiles.updateTextInFile(fullReportPagePath, "<!--report-title-->", "MONTHLY MARKET SHARE TOTAL SUMMARY");
		String mstrHotelCount = "";
		String mstrOccupancyIndex = "";
		String mstrYoyOccupancyIndex = "";
		String mstrAvgDailyRateIndex = "";
		String mstrYoyAvgDailyRateIndex = "";
		String mstrRevAvgRoomIndex = "";
		String mstrYoyRevAvgRoomIndex = "";
		String mstrRevAvgRoomImpact = "";
		String tableauHotelCount = "";
		String tableauOccupancyIndex = "";
		String tableauYoyOccupancyIndex = "";
		String tableauAvgDailyRateIndex = "";
		String tableauYoyAvgDailyRateIndex = "";
		String tableauRevAvgRoomIndex = "";
		String tableauYoyRevAvgRoomIndex = "";
		String tableauRevAvgRoomImpact = "";
		float totalMstrHotelCount = 0;
		float totalMstrOccupancyIndex = 0;
		float totalMstrYoyOccupancyIndex = 0;
		float totalMstrAvgDailyRateIndex = 0;
		float totalMstrYoyAvgDailyRateIndex = 0;
		float totalMstrRevAvgRoomIndex = 0;
		float totalMstrYoyRevAvgRoomIndex = 0;
		float totalMstrRevAvgRoomImpact = 0;
		float totalTableauHotelCount = 0;
		float totalTableauOccupancyIndex = 0;
		float totalTableauYoyOccupancyIndex = 0;
		float totalTableauAvgDailyRateIndex = 0;
		float totalTableauYoyAvgDailyRateIndex = 0;
		float totalTableauRevAvgRoomIndex = 0;
		float totalTableauYoyRevAvgRoomIndex = 0;
		float totalTableauRevAvgRoomImpact = 0;
		
		int counter = 1;
		for (String hotelName: mstrHotelNames) {
			String placement = "";
			if (counter%2 == 0) {
				placement = "right";
			}else {
				placement = "left";
			}
			counter++;	
			String optionsNewString = generateHotelNameOptions(hotelName);
			duplicateAndUpdateFiles.updateTextInFile(fullReportPagePath, optionsOldString, optionsNewString);
			
			for (HashMap<String, String> map: mstrData) {
				String mstrHotelName = map.get("hotel");
				if (hotelName.equalsIgnoreCase(mstrHotelName)) {
					mstrHotelCount = map.get("hotel-count");
					mstrOccupancyIndex = map.get("occupancy-index");
					mstrYoyOccupancyIndex = map.get("yoy-occupancy-index");
					mstrAvgDailyRateIndex = map.get("average-daily-rate-index");
					mstrYoyAvgDailyRateIndex = map.get("yoy-average-daily-rate-index");
					mstrRevAvgRoomIndex = map.get("revenue-per-available-room-index");
					mstrYoyRevAvgRoomIndex = map.get("yoy-revenue-per-available-room-index");
					mstrRevAvgRoomImpact = map.get("revenue-per-available-room-impact");
				}
			}
			for (HashMap<String, String> map: tableauData) {
				String tableauHotelName = map.get("hotel");
				if (hotelName.equalsIgnoreCase(tableauHotelName)) {
					tableauHotelCount = map.get("hotel-count");
					tableauOccupancyIndex = map.get("occupancy-index");
					tableauYoyOccupancyIndex = map.get("yoy-occupancy-index");
					tableauAvgDailyRateIndex = map.get("average-daily-rate-index");
					tableauYoyAvgDailyRateIndex = map.get("yoy-average-daily-rate-index");
					tableauRevAvgRoomIndex = map.get("revenue-per-available-room-index");
					tableauYoyRevAvgRoomIndex = map.get("yoy-revenue-per-available-room-index");
					tableauRevAvgRoomImpact = map.get("revenue-per-available-room-impact");
				}
			}
			String fullReportTable = generateFullReportTableCore(hotelName, placement);
			fullReportTable = fullReportTable.replace("${table-rows}", generateFullReportRow("Hotel Count", mstrHotelCount, tableauHotelCount));
			fullReportTable = fullReportTable.replace("${table-rows}", generateFullReportRow("Occupancy Index", mstrOccupancyIndex, tableauOccupancyIndex));
			fullReportTable = fullReportTable.replace("${table-rows}", generateFullReportRow("YOY Occupancy Index", mstrYoyOccupancyIndex, tableauYoyOccupancyIndex));
			fullReportTable = fullReportTable.replace("${table-rows}", generateFullReportRow("Avg Daily Rate Index", mstrAvgDailyRateIndex, tableauAvgDailyRateIndex));
			fullReportTable = fullReportTable.replace("${table-rows}", generateFullReportRow("YOY Avg Daily Rate Index", mstrYoyAvgDailyRateIndex, tableauYoyAvgDailyRateIndex));
			fullReportTable = fullReportTable.replace("${table-rows}", generateFullReportRow("Rev Avg Room Index", mstrRevAvgRoomIndex, tableauRevAvgRoomIndex));
			fullReportTable = fullReportTable.replace("${table-rows}", generateFullReportRow("YOY Rev Avg Room Index", mstrYoyRevAvgRoomIndex, tableauYoyRevAvgRoomIndex));
			fullReportTable = fullReportTable.replace("${table-rows}", generateFullReportRow("Rev Avg Room Impact", mstrRevAvgRoomImpact, tableauRevAvgRoomImpact));
			fullReportTable = fullReportTable.replace("${table-rows}", "")+"<!--hotel-full-report-table-->";
			duplicateAndUpdateFiles.updateTextInFile(fullReportPagePath, "<!--hotel-full-report-table-->", fullReportTable);
			
			totalMstrHotelCount = totalMstrHotelCount + stringHelper.stringToFloat(mstrHotelCount);
			totalMstrOccupancyIndex = totalMstrOccupancyIndex + stringHelper.stringToFloat(mstrOccupancyIndex);
			totalMstrYoyOccupancyIndex = totalMstrYoyOccupancyIndex + stringHelper.stringToFloat(mstrYoyOccupancyIndex);
			totalMstrAvgDailyRateIndex = totalMstrAvgDailyRateIndex + stringHelper.stringToFloat(mstrAvgDailyRateIndex);
			totalMstrYoyAvgDailyRateIndex = totalMstrYoyAvgDailyRateIndex + stringHelper.stringToFloat(mstrYoyAvgDailyRateIndex);
			totalMstrRevAvgRoomIndex = totalMstrRevAvgRoomIndex + stringHelper.stringToFloat(mstrRevAvgRoomIndex);
			totalMstrYoyRevAvgRoomIndex = totalMstrYoyRevAvgRoomIndex + stringHelper.stringToFloat(mstrYoyRevAvgRoomIndex);
			mstrRevAvgRoomImpact = mstrRevAvgRoomImpact.replace("$", "");
			mstrRevAvgRoomImpact = mstrRevAvgRoomImpact.replace("M", "");
			totalMstrRevAvgRoomImpact = totalMstrRevAvgRoomImpact + stringHelper.stringToFloat(mstrRevAvgRoomImpact);
			totalTableauHotelCount = totalTableauHotelCount + stringHelper.stringToFloat(tableauHotelCount);
			totalTableauOccupancyIndex = totalTableauOccupancyIndex + stringHelper.stringToFloat(tableauOccupancyIndex);
			totalTableauYoyOccupancyIndex = totalTableauYoyOccupancyIndex + stringHelper.stringToFloat(tableauYoyOccupancyIndex);
			totalTableauAvgDailyRateIndex = totalTableauAvgDailyRateIndex + stringHelper.stringToFloat(tableauAvgDailyRateIndex);
			totalTableauYoyAvgDailyRateIndex = totalTableauYoyAvgDailyRateIndex + stringHelper.stringToFloat(tableauYoyAvgDailyRateIndex);
			totalTableauRevAvgRoomIndex = totalTableauRevAvgRoomIndex + stringHelper.stringToFloat(tableauRevAvgRoomIndex);
			totalTableauYoyRevAvgRoomIndex = totalTableauYoyRevAvgRoomIndex + stringHelper.stringToFloat(tableauYoyRevAvgRoomIndex);
			tableauRevAvgRoomImpact = tableauRevAvgRoomImpact.replace("$", "");
			tableauRevAvgRoomImpact = tableauRevAvgRoomImpact.replace("M", "");
			totalTableauRevAvgRoomImpact = totalTableauRevAvgRoomImpact + stringHelper.stringToFloat(tableauRevAvgRoomImpact);
			System.out.println(Math.round(totalMstrHotelCount* 100.0) / 100.0);
			System.out.println(Math.round(totalMstrOccupancyIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalMstrYoyOccupancyIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalMstrAvgDailyRateIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalMstrYoyAvgDailyRateIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalMstrRevAvgRoomIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalMstrYoyRevAvgRoomIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalMstrRevAvgRoomImpact* 100.0) / 100.0);
			System.out.println(Math.round(totalTableauHotelCount* 100.0) / 100.0);
			System.out.println(Math.round(totalTableauOccupancyIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalTableauYoyOccupancyIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalTableauAvgDailyRateIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalTableauYoyAvgDailyRateIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalTableauRevAvgRoomIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalTableauYoyRevAvgRoomIndex* 100.0) / 100.0);
			System.out.println(Math.round(totalTableauRevAvgRoomImpact* 100.0) / 100.0);
		}
		
		System.out.println(Math.round(totalMstrHotelCount* 100.0) / 100.0);
		System.out.println(Math.round(totalMstrOccupancyIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalMstrYoyOccupancyIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalMstrAvgDailyRateIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalMstrYoyAvgDailyRateIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalMstrRevAvgRoomIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalMstrYoyRevAvgRoomIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalMstrRevAvgRoomImpact* 100.0) / 100.0);
		System.out.println(Math.round(totalTableauHotelCount* 100.0) / 100.0);
		System.out.println(Math.round(totalTableauOccupancyIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalTableauYoyOccupancyIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalTableauAvgDailyRateIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalTableauYoyAvgDailyRateIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalTableauRevAvgRoomIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalTableauYoyRevAvgRoomIndex* 100.0) / 100.0);
		System.out.println(Math.round(totalTableauRevAvgRoomImpact* 100.0) / 100.0);
		
	}
	
}
