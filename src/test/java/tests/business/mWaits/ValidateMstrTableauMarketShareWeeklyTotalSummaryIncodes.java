package tests.business.mWaits;

import comparators.tableauToMstrApi.MstrTableauMarketShareWeeklyTotalSummaryIncodes;
import org.testng.annotations.Test;

public class ValidateMstrTableauMarketShareWeeklyTotalSummaryIncodes {

	MstrTableauMarketShareWeeklyTotalSummaryIncodes mstrTableauMarketShareWeeklyComparison;
	private String tableauFilePath;
	private String mstrFilePath;
	
	public ValidateMstrTableauMarketShareWeeklyTotalSummaryIncodes() throws InterruptedException {
		String userPath = System.getProperty("user.dir");
		tableauFilePath = userPath+"/src/test/resources/testFiles/mWaits/weeklyMarketShareTotalSummaryIncodes/Total_Summary_-_Weekly_Indexes_crosstab.csv";	
		mstrFilePath = userPath+"/src/test/resources/testFiles/mWaits/weeklyMarketShareTotalSummaryIncodes/Market Share.csv";
	}
	
	@Test (groups = {"tableauToMstrWeeklyIncodes"})
	public void compareMarketShareWeeklyTotalSummaryReports() throws Exception {
		mstrTableauMarketShareWeeklyComparison = new MstrTableauMarketShareWeeklyTotalSummaryIncodes(tableauFilePath, mstrFilePath, "TableauToMstrWeeklyIncodesReport");
		mstrTableauMarketShareWeeklyComparison.reportGenerator();
	}
}
