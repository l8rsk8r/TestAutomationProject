package comparators.tableauToMstrApi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import customReportHelper.MarketShareReportHelper;
import utils.Calculator;
import utils.CollectionsHelper;
import utils.StringHelper;
import utils.fileHelpers.DuplicateAndUpdateFiles;
import utils.fileHelpers.ReadWriteFlatFiles;

public class MstrTableauMarketShareWeeklyTotalSummaryIncodes extends MarketShareReportHelper {

	int tableauIncodesCount;
	int mstrIncodesCount;
	ArrayList<String> tableauIncodeNames;
	ArrayList<String> mstrIncodeNames;
	ArrayList<String> commonIncodeNames;
	ArrayList<String> missingIncodeNamesFromMstr;
	ArrayList<String> missingIncodeNamesFromTableau;
	List<HashMap<String, String>> tableauData;
	List<HashMap<String, String>> mstrData;
	DuplicateAndUpdateFiles duplicateAndUpdateFiles = new DuplicateAndUpdateFiles();
	StringHelper stringHelper = new StringHelper();
	Calculator calculator = new Calculator();
	CollectionsHelper collectionsHelper = new CollectionsHelper();
	ReadWriteFlatFiles readWriteFiles = new ReadWriteFlatFiles();
	String csvReportFilePath;
	String summaryFilePath;
	
	public MstrTableauMarketShareWeeklyTotalSummaryIncodes(String tableauFilePath, String mstrFilePath, String targetDir) throws Exception {
		super(targetDir);
		/*tableauReportReader.MarketShareWeeklyReportReader tableauReportReader = new tableauReportReader.MarketShareWeeklyReportReader(tableauFilePath, tableauReportReader.MarketShareWeeklyReportReader.Dimensions.INCODE);
		tableauIncodesCount = tableauReportReader.getRecordCount();
		tableauIncodeNames = tableauReportReader.getIncodes();
		tableauData = tableauReportReader.getData();
		mstrReportReader.MarketShareWeeklyReportReader mstrReportReader = new mstrReportReader.MarketShareWeeklyReportReader(mstrFilePath, mstrReportReader.MarketShareWeeklyReportReader.Dimensions.INCODE);
		mstrIncodesCount = mstrReportReader.getRecordCount();
		mstrIncodeNames = mstrReportReader.getIncodes();
		mstrData = mstrReportReader.getData();
		commonIncodeNames = findCommonIncodeNames();
		missingIncodeNamesFromMstr = findMissingIncodesFromMstr();
		missingIncodeNamesFromTableau = findMissingIncodesFromTableau();
		csvReportFilePath = System.getProperty("user.dir")+"/target/TableauToMstrWeeklyIncodesReport/failures.csv";
		summaryFilePath = System.getProperty("user.dir")+"/target/TableauToMstrWeeklyIncodesReport/summary.txt";*/
	}
	
	private ArrayList<String> findCommonIncodeNames(){
		return collectionsHelper.findCommonFromArrayLists(mstrIncodeNames, tableauIncodeNames);
	}
	
	private ArrayList<String> findMissingIncodesFromMstr(){
		return collectionsHelper.removeAllFromArrayList(tableauIncodeNames, mstrIncodeNames);
	}
	
	private ArrayList<String> findMissingIncodesFromTableau(){
		return collectionsHelper.removeAllFromArrayList(mstrIncodeNames, tableauIncodeNames);
	}

	public void reportGenerator() throws IOException {
		readWriteFiles.writeToFile(csvReportFilePath, "InnCode,Indicator,MSTR,Tableau,Match%");
		duplicateAndUpdateFiles.updateTextInFile(failureReportPagePath, "<!-- report-title -->", "WEEKLY MARKET SHARE TOTAL SUMMARY");
		String table = "";
		table = generateInncodeValidationTableCore();
		String rowToReplace = "<!-- table-rows -->";
		String row = "";
		String mstrHotelCount = null;
		String mstrOccupancyIndex = null;
		String mstrYoyOccupancyIndex = null;
		String mstrAvgDailyRateIndex = null;
		String mstrYoyAvgDailyRateIndex = null;
		String mstrRevAvgRoomIndex = null;
		String mstrYoyRevAvgRoomIndex = null;
		String mstrRevAvgRoomImpact = null;
		String tableauHotelCount = null;
		String tableauOccupancyIndex = null;
		String tableauYoyOccupancyIndex = null;
		String tableauAvgDailyRateIndex = null;
		String tableauYoyAvgDailyRateIndex = null;
		String tableauRevAvgRoomIndex = null;
		String tableauYoyRevAvgRoomIndex = null;
		String tableauRevAvgRoomImpact = null;
		
		float mstrHotelCountFloat = 0;
		float mstrOccupancyIndexFloat = 0;
		float mstrYoyOccupancyIndexFloat = 0;
		float mstrAvgDailyRateIndexFloat = 0;
		float mstrYoyAvgDailyRateIndexFloat = 0;
		float mstrRevAvgRoomIndexFloat = 0;
		float mstrYoyRevAvgRoomIndexFloat = 0;
		float mstrRevAvgRoomImpactFloat = 0;
		float tableauHotelCountFloat = 0;
		float tableauOccupancyIndexFloat = 0;
		float tableauYoyOccupancyIndexFloat = 0;
		float tableauAvgDailyRateIndexFloat = 0;
		float tableauYoyAvgDailyRateIndexFloat = 0;
		float tableauRevAvgRoomIndexFloat = 0;
		float tableauYoyRevAvgRoomIndexFloat = 0;
		float tableauRevAvgRoomImpactFloat = 0;
		
		
		commonIncodeNames = findCommonIncodeNames();
		readWriteFiles.writeToFile(summaryFilePath, "Tableau Inncodes Count: "+tableauIncodesCount);
		
//		System.out.println("tableau hotel count from getRecordCount: "+tableauIncodesCount);
		readWriteFiles.writeToFile(summaryFilePath, "MSTR Inncodes Count: "+mstrIncodesCount);

//		System.out.println("mstr hotel count from getRecordCount: "+mstrIncodesCount);
		readWriteFiles.writeToFile(summaryFilePath, "Common Inncodes Count: "+commonIncodeNames.size());

//		System.out.println("common incode count : "+commonIncodeNames.size());
		readWriteFiles.writeToFile(summaryFilePath, "Missing From MSTR");
		for (String inncode: findMissingIncodesFromMstr()) {
			readWriteFiles.writeToFile(summaryFilePath, inncode);

		}
//		System.out.println("missing from mstr");
//		System.out.println(findMissingIncodesFromMstr());
		readWriteFiles.writeToFile(summaryFilePath, "Missing From Tableu");
		for (String inncode: findMissingIncodesFromTableau()) {
			readWriteFiles.writeToFile(summaryFilePath, inncode);

		}
//		System.out.println("missing from tableau");
//		System.out.println(findMissingIncodesFromTableau());
		for (String incodeName: commonIncodeNames) {
			for (HashMap<String, String> map: mstrData) {
				String mstrIncodeName = map.get("incode");
				if (incodeName.equalsIgnoreCase(mstrIncodeName)) {
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
				String tableauIncodeName = map.get("incode");
				if (incodeName.equalsIgnoreCase(tableauIncodeName)) {
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
			
			String[] removeCharsFromMillionValues = {"M","$","\""};
			mstrHotelCountFloat = stringHelper.stringToFloat(mstrHotelCount);
			mstrOccupancyIndexFloat = stringHelper.stringToFloat(mstrOccupancyIndex);
			mstrYoyOccupancyIndexFloat = stringHelper.stringToFloat(mstrYoyOccupancyIndex);
			mstrAvgDailyRateIndexFloat = stringHelper.stringToFloat(mstrAvgDailyRateIndex);
			mstrYoyAvgDailyRateIndexFloat = stringHelper.stringToFloat(mstrYoyAvgDailyRateIndex);
			mstrRevAvgRoomIndexFloat = stringHelper.stringToFloat(mstrRevAvgRoomIndex);
			mstrYoyRevAvgRoomIndexFloat = stringHelper.stringToFloat(mstrYoyRevAvgRoomIndex);
			mstrRevAvgRoomImpactFloat = stringHelper.stringToFloat(stringHelper.removeMultipleChars(mstrRevAvgRoomImpact, removeCharsFromMillionValues));

			tableauHotelCountFloat = stringHelper.stringToFloat(tableauHotelCount);
			tableauOccupancyIndexFloat = stringHelper.stringToFloat(tableauOccupancyIndex);
			tableauYoyOccupancyIndexFloat = stringHelper.stringToFloat(tableauYoyOccupancyIndex);
			tableauAvgDailyRateIndexFloat = stringHelper.stringToFloat(tableauAvgDailyRateIndex);
			tableauYoyAvgDailyRateIndexFloat = stringHelper.stringToFloat(tableauYoyAvgDailyRateIndex);
			tableauRevAvgRoomIndexFloat = stringHelper.stringToFloat(tableauRevAvgRoomIndex);
			tableauYoyRevAvgRoomIndexFloat = stringHelper.stringToFloat(tableauYoyRevAvgRoomIndex);
			//How to remove trailing spaces - trim not working
			//System.out.println("HERE IT IS"+(tableauRevAvgRoomImpact.replaceAll("\\s+","")));
			tableauRevAvgRoomImpactFloat = stringHelper.stringToFloat((stringHelper.removeMultipleChars((tableauRevAvgRoomImpact), removeCharsFromMillionValues)));
			
			if (!(mstrHotelCountFloat == tableauHotelCountFloat)) {
				row = generateInncodeValidationTableRow(incodeName, "Hotel Count", mstrHotelCountFloat, tableauHotelCountFloat);
				table = table.replace(rowToReplace, row);
				double matchPercentage = calculator.findMatchPercentage(mstrHotelCountFloat, tableauHotelCountFloat);
				readWriteFiles.writeToFile(csvReportFilePath, incodeName+","+"Hotel Count"+","+mstrHotelCountFloat+","+tableauHotelCountFloat+","+matchPercentage);
			} 
			if (!( mstrOccupancyIndexFloat ==  tableauOccupancyIndexFloat)) {
				row = generateInncodeValidationTableRow(incodeName, "Occupancy Index", mstrOccupancyIndexFloat, tableauOccupancyIndexFloat);
				table = table.replace(rowToReplace, row);
				double matchPercentage = calculator.findMatchPercentage(mstrOccupancyIndexFloat, tableauOccupancyIndexFloat);
				readWriteFiles.writeToFile(csvReportFilePath, incodeName+","+"Occupancy Index"+","+mstrOccupancyIndexFloat+","+tableauOccupancyIndexFloat+","+matchPercentage);
			} 
			if (!( mstrYoyOccupancyIndexFloat ==  tableauYoyOccupancyIndexFloat)) {
				row = generateInncodeValidationTableRow(incodeName, "YoY Occupancy Index", mstrYoyOccupancyIndexFloat, tableauYoyOccupancyIndexFloat);
				table = table.replace(rowToReplace, row);
				double matchPercentage = calculator.findMatchPercentage(mstrYoyOccupancyIndexFloat, tableauYoyOccupancyIndexFloat);
				readWriteFiles.writeToFile(csvReportFilePath, incodeName+","+"YoY Occupancy Index"+","+mstrYoyOccupancyIndexFloat+","+tableauYoyOccupancyIndexFloat+","+matchPercentage);
			} 
			if (!( mstrAvgDailyRateIndexFloat ==  tableauAvgDailyRateIndexFloat)) {
				row = generateInncodeValidationTableRow(incodeName, "Avg Daily Rate Index", mstrAvgDailyRateIndexFloat, tableauAvgDailyRateIndexFloat);
				table = table.replace(rowToReplace, row);
				double matchPercentage = calculator.findMatchPercentage(mstrAvgDailyRateIndexFloat, tableauAvgDailyRateIndexFloat);
				readWriteFiles.writeToFile(csvReportFilePath, incodeName+","+"Avg Daily Rate Index"+","+mstrAvgDailyRateIndexFloat+","+tableauAvgDailyRateIndexFloat+","+matchPercentage);
			} 
			if (!( mstrYoyAvgDailyRateIndexFloat ==  tableauYoyAvgDailyRateIndexFloat)) {
				row = generateInncodeValidationTableRow(incodeName, "YoY Avg Daily Rate Index", mstrYoyAvgDailyRateIndexFloat, tableauYoyAvgDailyRateIndexFloat);
				table = table.replace(rowToReplace, row);
				double matchPercentage = calculator.findMatchPercentage(mstrYoyAvgDailyRateIndexFloat, tableauYoyAvgDailyRateIndexFloat);
				readWriteFiles.writeToFile(csvReportFilePath, incodeName+","+"YoY Avg Daily Rate Index"+","+mstrYoyAvgDailyRateIndexFloat+","+tableauYoyAvgDailyRateIndexFloat+","+matchPercentage);
			} 
			if (!( mstrRevAvgRoomIndexFloat ==  tableauRevAvgRoomIndexFloat)) {
				row = generateInncodeValidationTableRow(incodeName, "Revenue Per Available Room Index", mstrRevAvgRoomIndexFloat, tableauRevAvgRoomIndexFloat);
				table = table.replace(rowToReplace, row);
				double matchPercentage = calculator.findMatchPercentage(mstrRevAvgRoomIndexFloat, tableauRevAvgRoomIndexFloat);
				readWriteFiles.writeToFile(csvReportFilePath, incodeName+","+"Revenue Per Available Room Index"+","+mstrRevAvgRoomIndexFloat+","+tableauRevAvgRoomIndexFloat+","+matchPercentage);
			} 
			if (!(mstrYoyRevAvgRoomIndexFloat ==  tableauYoyRevAvgRoomIndexFloat)) {
				row = generateInncodeValidationTableRow(incodeName, "YoY Revenue Per Available Room Index", mstrYoyRevAvgRoomIndexFloat, tableauYoyRevAvgRoomIndexFloat);
				table = table.replace(rowToReplace, row);
				double matchPercentage = calculator.findMatchPercentage(mstrYoyRevAvgRoomIndexFloat, tableauYoyRevAvgRoomIndexFloat);
				readWriteFiles.writeToFile(csvReportFilePath, incodeName+","+"YoY Revenue Per Available Room Index"+","+mstrYoyRevAvgRoomIndexFloat+","+tableauYoyRevAvgRoomIndexFloat+","+matchPercentage);
			} 
			if (!(mstrRevAvgRoomImpactFloat == tableauRevAvgRoomImpactFloat)) {
				row = generateInncodeValidationTableRow(incodeName, "Revenue Per Available Room Impact", mstrRevAvgRoomImpactFloat, tableauRevAvgRoomImpactFloat);
				table = table.replace(rowToReplace, row);
				double matchPercentage = calculator.findMatchPercentage(mstrRevAvgRoomImpactFloat, tableauRevAvgRoomImpactFloat);
				readWriteFiles.writeToFile(csvReportFilePath, incodeName+","+"Revenue Per Available Room Impact"+","+mstrRevAvgRoomImpactFloat+","+tableauRevAvgRoomImpactFloat+","+matchPercentage);
			} 
		}
		duplicateAndUpdateFiles.updateTextInFile(failureReportPagePath, "<!-- full-report-table -->", table);
	}
}
