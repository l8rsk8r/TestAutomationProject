package tests.business.mWaits;

import comparators.tableauToMstrApi.MstrTableauMarketShareMonthlyTotalSummary;
import mstr.ui.refactor.scenarioSteps.DownloadMstrReports;
import onQ.ui.refactor.OnqLoginPageSteps;
import org.testng.annotations.Test;
import tableau.ui.refactor.scenarioSteps.DownloadTableauReports;

public class ValidateMstrTableauMarketShareMonthlyTotalSummary {

	OnqLoginPageSteps onqLoginPageSteps;
	DownloadTableauReports downloadTableauReports;
	DownloadMstrReports downloadMstrReports;
	MstrTableauMarketShareMonthlyTotalSummary mstrTableauMarketShareMonthlyComparison;
	private String tableauFilePath;
	private String mstrFilePath;

	public ValidateMstrTableauMarketShareMonthlyTotalSummary() throws InterruptedException {
		String userPath = System.getProperty("user.dir");
//		tableauFilePath = userPath+"/target/Monthly_Indexes_crosstab.csv";	
//		mstrFilePath = userPath+"/target/Market Share.csv";	
		tableauFilePath = userPath+"/src/test/resources/testFiles/mWaits/monthlyMarketShareTotalSummary/Monthly_Indexes_crosstab.csv";	
		mstrFilePath = userPath+"/src/test/resources/testFiles/mWaits/monthlyMarketShareTotalSummary/Market Share.csv";		
	}
	
//	@BeforeClass
//	public void setUp() throws Exception{
//		onqLoginPageSteps = new OnqLoginPageSteps(driver);
//		onqLoginPageSteps.loginToOnQ();
//		downloadTableauReports = new DownloadTableauReports(driver);	
//		downloadTableauReports.downloadReport(TableauReports.MARKET_SHARE_MONTHLY_TOTAL_SUMMARY);
//		downloadMstrReports = new DownloadMstrReports(driver);
//		downloadMstrReports.downloadReport(MstrReports.MARKET_SHARE_MONTHLY_TOTAL_SUMMARY);
//	}

	@Test (groups = {"tableauToMstrMonthlyBrands"})
	public void compareMarketShareMonthlyTotalSummaryReports() throws Exception {
		mstrTableauMarketShareMonthlyComparison = new MstrTableauMarketShareMonthlyTotalSummary(tableauFilePath, mstrFilePath, "TableauToMstrMonthlyBrands");
		mstrTableauMarketShareMonthlyComparison.reportGenerator();
	}
}
