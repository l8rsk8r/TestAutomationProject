package tableau.ui.refactor.scenarioSteps;

import org.openqa.selenium.WebDriver;
import tableau.ui.refactor.Navigate;
import tableau.ui.refactor.steps.MarketShareReportSteps;
import webdriver.DriverHelper;

public class DownloadTableauReports {

	protected WebDriver driver;
	DriverHelper driverHelper;
	private String baseUrl = "https://tableau.hilton.com/vizql/t/CSAT/";
	private String downloadParameter = "?charset=utf16&amp;download=true";
	MarketShareReportSteps marketShareReportSteps;
	Navigate navigate;

	public DownloadTableauReports(WebDriver driver) {
		this.driver = driver;
		navigate = new Navigate(driver);
		marketShareReportSteps = new MarketShareReportSteps(driver);
		driverHelper = new DriverHelper(driver);
	}

	public enum TableauReports {
		MARKET_SHARE_MONTHLY_TOTAL_SUMMARY,
		MARKET_SHARE_WEEKLY_TOTAL_SUMMARY
	}
	
	public String getDownloadUrl(TableauReports report) {
		String url;
		String reportParameters = null;
		reportParameters = marketShareReportSteps.getDownloadReportQueryStringParameters(report);
		url = baseUrl+reportParameters+downloadParameter;
		return url;
	}
	
	public String goToReportPageUrl(TableauReports report) {
		String reportPageUrl = null;
		switch(report) {
		case MARKET_SHARE_MONTHLY_TOTAL_SUMMARY:
			navigate.toMarketShareMontlyTotalSummaryUrl();
			break;
		case MARKET_SHARE_WEEKLY_TOTAL_SUMMARY:
			navigate.toMarketShareWeeklyTotalSummaryUrl();
			break;
		default:
			throw new NullPointerException();
		}
		return reportPageUrl;
	}
	
	public void downloadReport(TableauReports report) throws Exception {
		navigate.toTableauUrl();
		goToReportPageUrl(report);
		String reportUrl = getDownloadUrl(report);
		driverHelper.navigateTo(reportUrl);
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
