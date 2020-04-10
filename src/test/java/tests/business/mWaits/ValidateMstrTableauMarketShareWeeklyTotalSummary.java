package tests.business.mWaits;

import comparators.tableauToMstrApi.MstrTableauMarketShareWeeklyTotalSummary;
import mstr.ui.refactor.scenarioSteps.DownloadMstrReports;
import onQ.ui.refactor.OnqLoginPageSteps;
import org.testng.annotations.Test;
import tableau.ui.refactor.scenarioSteps.DownloadTableauReports;

public class ValidateMstrTableauMarketShareWeeklyTotalSummary {

	OnqLoginPageSteps onqLoginPageSteps;
	DownloadTableauReports downloadTableauReports;
	DownloadMstrReports downloadMstrReports;
	MstrTableauMarketShareWeeklyTotalSummary mstrTableauMarketShareWeeklyComparison;
	private String tableauFilePath;
	private String mstrFilePath;

	public ValidateMstrTableauMarketShareWeeklyTotalSummary() throws InterruptedException {
		String userPath = System.getProperty("user.dir");
//		tableauFilePath = userPath+"/target/Total_Summary_-_Weekly_Indexes_crosstab.csv";	
//		mstrFilePath = userPath+"/target/Market Share.csv";	
		tableauFilePath = userPath+"/src/test/resources/testFiles/mWaits/weeklyMarketShareTotalSummary/Total_Summary_-_Weekly_Indexes_crosstab.csv";	
		mstrFilePath = userPath+"/src/test/resources/testFiles/mWaits/weeklyMarketShareTotalSummary/Market Share.csv";
	}
	
//	@BeforeClass
//	public void setUp() throws Exception{
//		System.out.println("setup");
//		onqLoginPageSteps = new OnqLoginPageSteps(driver);
//		onqLoginPageSteps.loginToOnQ();
//		downloadTableauReports = new DownloadTableauReports(driver);	
//		downloadTableauReports.downloadReport(TableauReports.MARKET_SHARE_WEEKLY_TOTAL_SUMMARY);
//		downloadMstrReports = new DownloadMstrReports(driver);
//		downloadMstrReports.downloadReport(MstrReports.MARKET_SHARE_WEEKLY_TOTAL_SUMMARY);
//	}
	
	@Test (groups = {"tableauToMstrWeeklyBrands"})
	public void compareMarketShareWeeklyTotalSummaryReports() throws Exception {
		mstrTableauMarketShareWeeklyComparison = new MstrTableauMarketShareWeeklyTotalSummary(tableauFilePath, mstrFilePath, "TableauToMstrWeeklyBrands");
		mstrTableauMarketShareWeeklyComparison.reportGenerator();
	}
}
