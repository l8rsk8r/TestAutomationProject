package tableau.ui.refactor;

import org.openqa.selenium.WebDriver;
import webdriver.DriverHelper;

public class Navigate {

	private static final String TABLEAU_URL = "https://tableau.hilton.com/#/site/CSAT/projects";
	private static final String MARKET_SHARE_MONTHLY_TOTAL_SUMMARY_URL = "https://tableau.hilton.com/#/site/CSAT/views/MarketShareMonthly/TotalSummary?:iid=1";
	private static final String MARKET_SHARE_WEEKLY_TOTAL_SUMMARY_URL = "https://tableau.hilton.com/#/site/CSAT/views/MarketShareWeekly/TotalSummary?:iid=1&:original_view=y";
	private static final String MONTHLY_SEGMENTATION_ALL_SEGMENTS_URL = "https://tableau.hilton.com/#/site/CSAT/views/MonthlySegmentation/AllSegments?:iid=1";
	
	protected WebDriver driver;
	DriverHelper driverHelper;
	
	public Navigate(WebDriver driver) {
		this.driver = driver;
		driverHelper = new DriverHelper(driver);
	}

	public void toTableauUrl() {
		driverHelper.navigateTo(TABLEAU_URL);
	}
	
	public void toMarketShareMontlyTotalSummaryUrl() {
		driverHelper.navigateTo(MARKET_SHARE_MONTHLY_TOTAL_SUMMARY_URL);
	}
	
	public void toMarketShareWeeklyTotalSummaryUrl() {
		driverHelper.navigateTo(MARKET_SHARE_WEEKLY_TOTAL_SUMMARY_URL);
	}
	
	public void toMarketShareSegmentationAllSegmentsUrl() {
		driverHelper.navigateTo(MONTHLY_SEGMENTATION_ALL_SEGMENTS_URL);
	}
}
