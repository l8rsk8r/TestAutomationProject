package mstr.ui.refactor.scenarioSteps;

import mstr.ui.refactor.Navigate;
import mstr.ui.refactor.steps.MarketSharePageSteps;
import org.openqa.selenium.WebDriver;
import webdriver.DriverHelper;

public class DownloadMstrReports {
	
	protected WebDriver driver;
	Navigate navigate;
	DriverHelper driverHelper;
	MarketSharePageSteps marketSharePageSteps;
	
	public DownloadMstrReports(WebDriver driver) throws Exception {
		this.driver = driver;
		navigate = new Navigate(driver);
		driverHelper = new DriverHelper(driver);
		marketSharePageSteps = new MarketSharePageSteps(driver);
	}
	
	private enum ReportTypes {
		EXCEL,
		PDF,
		CSV
	}
	
	private void performDownloadReportAction(ReportTypes reportType) {
		marketSharePageSteps.clickDownloadElipsesButton(5);
		marketSharePageSteps.clickDowloadExportOption(5);
		switch(reportType) {
		case EXCEL:
			//TODO
			break;
		case PDF:
			//TODO
			break;
		case CSV:
			marketSharePageSteps.clickDowloadDataOption(5);
			break;
		default:
			throw new NullPointerException();
		}
	}
	
	public enum MstrReports {
		MARKET_SHARE_MONTHLY_TOTAL_SUMMARY,
		MARKET_SHARE_WEEKLY_TOTAL_SUMMARY
	}
	
	public void downloadReport(MstrReports report) {
		switch (report) {
		case MARKET_SHARE_MONTHLY_TOTAL_SUMMARY:
			downloadMonthlyMarketShareTotalSummaryReport();
			break;
		case MARKET_SHARE_WEEKLY_TOTAL_SUMMARY:
			downloadWeeklyMarketShareTotalSummaryReport();
			break;
		}
	}
	
	public void downloadMonthlyMarketShareTotalSummaryReport() {
		navigate.toMstrMarketShareReport();
		driverHelper.waitForPageLoaded(120);
		//add wait for the table to be loaded, extra
		marketSharePageSteps.clickMtdYtdMarketShareTabInLeftPane(60);
		driverHelper.waitForPageLoaded(120);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//add wait for the table to be loaded, this would replace thread.sleep for 5000 milliseconds
		marketSharePageSteps.clickFilterButtonIfFilterPaneNotOpen(120);
		marketSharePageSteps.clickMonthFilter(5);
		marketSharePageSteps.clickMonthFilterDropdownButton(5);
		marketSharePageSteps.selectMonthCheckbox("July 2019", 5);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		marketSharePageSteps.clickResultTableForMonthlyTotalSummary(10);
		performDownloadReportAction(ReportTypes.CSV);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void downloadWeeklyMarketShareTotalSummaryReport() {
		navigate.toMstrMarketShareReport();
		driverHelper.waitForPageLoaded(120);
		//add wait for the table to be loaded, extra
		marketSharePageSteps.clickWeeklyMarketShareTabInLeftPane(60);
		driverHelper.waitForPageLoaded(120);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		//add wait for the table to be loaded, this would replace thread.sleep for 5000 milliseconds
		marketSharePageSteps.clickFilterButtonIfFilterPaneNotOpen(120);
		marketSharePageSteps.clickWeekOfYearFilter(5);
		marketSharePageSteps.clickWeekOfYearFilterDropdownButton(5);
		marketSharePageSteps.selectWeekOfYearCheckbox("WK36 01SEP19:WK 36 01SEP19-07SEP19", 5);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		marketSharePageSteps.clickResultTableForWeeklyTotalSummary(10);
		performDownloadReportAction(ReportTypes.CSV);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
