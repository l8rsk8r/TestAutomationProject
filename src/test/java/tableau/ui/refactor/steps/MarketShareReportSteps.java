package tableau.ui.refactor.steps;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import tableau.ui.refactor.pages.MarketShareMonthlyTotalSummaryPage;
import tableau.ui.refactor.pages.MarketShareMonthlyTotalSummaryPage.PageElements;
import tableau.ui.refactor.scenarioSteps.DownloadTableauReports.TableauReports;
import webdriver.DriverHelper;
import webdriver.ElementHelper;

public class MarketShareReportSteps {

	protected WebDriver driver;
	DriverHelper driverHelper;
	ElementHelper elementHelper;
	MarketShareMonthlyTotalSummaryPage marketShareMonthlyTotalSummaryPage;
	private String reportResource = "w/MarketShareMonthly/v/TotalSummary/";
	private String view = "views/12926777198364305354_5091822848386980311";
	
	public MarketShareReportSteps(WebDriver driver) {
		this.driver = driver;
		elementHelper = new ElementHelper(driver);
		driverHelper = new DriverHelper(driver);
		marketShareMonthlyTotalSummaryPage = new MarketShareMonthlyTotalSummaryPage(driver);
	}
	
	public String getFrameUrl() {
		driverHelper.waitForPageLoaded(120);
		WebElement frame = elementHelper.getElementByIndex(By.xpath("//iframe"), 0, 120);
		return frame.getAttribute("src");
	}
	
	public void navigateToFrameUrl() {
		String url = getFrameUrl();
		driverHelper.navigateTo(url);
	}
	
	public String getConfigContainerValue() {
		return elementHelper.getValue(marketShareMonthlyTotalSummaryPage.getPageElements(PageElements.FRAME_CONFIG_CONTAINER_TEXTAREA), 5);
	}
	
	private String getSessionIdFromConfigContainer() {
		String configContainerValue = getConfigContainerValue();
		String[] splitContainerValue =  configContainerValue.split("sessionid\":\"");
		String splitWithId = splitContainerValue[1];
		String[] splitSessionId = splitWithId.split("\"");
		String sessionId = splitSessionId[0];
		System.out.println(sessionId);
		return sessionId;
	}
	
	public String getDownloadReportQueryStringParameters(TableauReports report) {
		navigateToFrameUrl();
		String sessionId = getSessionIdFromConfigContainer();
		String reportResource = "";
		String view = "";
		String queryStringParameters = "";
		switch(report) {
		case MARKET_SHARE_MONTHLY_TOTAL_SUMMARY:
			reportResource = "w/MarketShareMonthly/v/TotalSummary/";
			view = "views/12926777198364305354_5091822848386980311";
			break;
		case MARKET_SHARE_WEEKLY_TOTAL_SUMMARY:
			reportResource = "w/MarketShareWeekly/v/TotalSummary/";
			view = "views/12926777198364305354_6730920358502047707";
			break;
		default:
			throw new NullPointerException();
		}
		queryStringParameters = reportResource+"exportcrosstab/sessions/"+sessionId+"/"+view;
		return queryStringParameters;
	}
	
	public String getDownloadReportQueryStringParameters() {
		navigateToFrameUrl();
		String sessionId = getSessionIdFromConfigContainer();
		String queryStringParameters = reportResource+"exportcrosstab/sessions/"+sessionId+"/"+view;
		System.out.println(queryStringParameters);
		return queryStringParameters;
	}
}
