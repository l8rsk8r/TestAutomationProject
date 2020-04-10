package mstr.ui.refactor.steps;

import mstr.ui.refactor.pages.MarketSharePage;
import mstr.ui.refactor.pages.MarketSharePage.PageElements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import webdriver.ElementHelper;

public class MarketSharePageSteps {

	protected WebDriver driver;
	ElementHelper elementHelper;
	MarketSharePage marketSharePage;
	
	public MarketSharePageSteps(WebDriver driver) {
		this.driver = driver;
		elementHelper = new ElementHelper(driver);
		marketSharePage = new MarketSharePage(driver);
	}
	
	public void clickMtdYtdMarketShareTabInLeftPane(int timeInSeconds) {
		System.out.println("perform clickMtdYtdMarketShareTabInLeftPane");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.MONTHLY_TOTAL_SUMMARY_INDEXES_TAB_LEFT_PANE), timeInSeconds);
	}
	
	public void clickWeeklyMarketShareTabInLeftPane(int timeInSeconds) {
		System.out.println("perform clickWeeklyMarketShareTabInLeftPane");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.WEEKLY_TOTAL_SUMMARY_INDEXES_TAB_LEFT_PANE), timeInSeconds);
	}
	
	public void clickFilterButtonIfFilterPaneNotOpen(int timeInSeconds) {
		System.out.println("perform clickFilterButtonIfFilterPaneNotOpen");
		elementHelper.clickIfExist(marketSharePage.getPageElements(PageElements.FILTER_BUTTON_TOP_NAV), 5);
	}
	
	/*
	 * MONTH FILTER STEPS 
	 */
	
	public void clickMonthFilter(int timeInSeconds) {
		System.out.println("perform clickMonthFilter");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.MONTH_FILTER), timeInSeconds);
	}
	
	public void clickMonthFilterDropdownButton(int timeInSeconds) {
		System.out.println("perform clickMonthFilterDropdownButton");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.MONTH_FILTER_DROPDOWN_BUTTON), timeInSeconds);
	}	
	
	public void clickMonthCheckbox(String monthAndYearValue, int timeInSeconds) {
		System.out.println("perform clickMonthCheckbox");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.MONTH_CHEKCBOX, monthAndYearValue), timeInSeconds);
	}
	
	public void clickMonthOkButton(String monthAndYearValue, int timeInSeconds) {
		System.out.println("perform clickMonthOkButton");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.MONTH_CHEKCBOX, monthAndYearValue), timeInSeconds);
	}
	
	//TODO: convert to select multiple months at a time
	public void selectMonthCheckbox(String monthAndYearValue, int timeInSeconds) {
		System.out.println("perform selectMonthCheckbox");
		System.out.println(monthAndYearValue);
		String allMonthAndYearValue = monthAndYearValue+",All";
		System.out.println(allMonthAndYearValue);
		String okButtonMonthAndYearValue = monthAndYearValue+",OkButton";
		By allCheckbox = marketSharePage.getPageElements(PageElements.MONTH_CHEKCBOX, allMonthAndYearValue);
		WebElement allCheckboxEle = driver.findElement(allCheckbox);
		String allCheckboxEleClassName = allCheckboxEle.getAttribute("className");
		if (allCheckboxEleClassName.contains("selected")) {
			WebElement allCheckboxEleStale = driver.findElement(allCheckbox);
			elementHelper.clickWhenReady(allCheckboxEleStale, 5);
		}
		clickMonthCheckbox(monthAndYearValue, timeInSeconds);
		clickMonthOkButton(okButtonMonthAndYearValue, timeInSeconds);
	}
	
	public void clickResultTableForMonthlyTotalSummary(int timeInSeconds) {
		System.out.println("perform clickResultTableForMonthlyTotalSummary");
		elementHelper.hoverAndClickWhenReady(marketSharePage.getPageElements(PageElements.MONTHLY_TOTAL_SUMMARY_INDEXES_RESULT_TABLE_RANDOM_ELEMENT), timeInSeconds);
	}
	
	/*
	 * WEEK OF YEAR FILTER STEPS
	 */
	
	public void clickWeekOfYearFilter(int timeInSeconds) {
		System.out.println("perform clickWeekOfYearFilter");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.WEEK_OF_YEAR_FILTER), timeInSeconds);
	}
	
	public void clickWeekOfYearFilterDropdownButton(int timeInSeconds) {
		System.out.println("perform clickWeekOfYearFilterDropdownButton");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.WEEK_OF_YEAR_FILTER_DROPDOWN_BUTTON), timeInSeconds);
	}	
	
	public void clickWeekOfYearCheckbox(String weekOfYearValue, int timeInSeconds) {
		System.out.println("perform clickWeekOfYearCheckbox");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.WEEK_OF_YEAR_CHECKBOX, weekOfYearValue), timeInSeconds);
	}
	
	public void clickWeekOfYearOkButton(String weekOfYearValue, int timeInSeconds) {
		System.out.println("perform clickWeekOfYearOkButton");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.WEEK_OF_YEAR_CHECKBOX, weekOfYearValue), timeInSeconds);
	}
	
	//TODO: convert to select multiple months at a time
	public void selectWeekOfYearCheckbox(String weekOfYearValue, int timeInSeconds) {
		System.out.println("perform selectWeekOfYearCheckbox");
		System.out.println(weekOfYearValue);
		String allweekOfYearValues = weekOfYearValue+",All";
		System.out.println(allweekOfYearValues);
		String okButtonweekOfYearValue = weekOfYearValue+",OkButton";
		By allCheckbox = marketSharePage.getPageElements(PageElements.WEEK_OF_YEAR_CHECKBOX, allweekOfYearValues);
		WebElement allCheckboxEle = driver.findElement(allCheckbox);
		String allCheckboxEleClassName = allCheckboxEle.getAttribute("className");
		if (allCheckboxEleClassName.contains("selected")) {
			WebElement allCheckboxEleStale = driver.findElement(allCheckbox);
			elementHelper.clickWhenReady(allCheckboxEleStale, 5);
		}
		clickWeekOfYearCheckbox(weekOfYearValue, timeInSeconds);
		clickWeekOfYearOkButton(okButtonweekOfYearValue, timeInSeconds);
	}
	
	public void clickResultTableForWeeklyTotalSummary(int timeInSeconds) {
		System.out.println("perform clickResultTableForWeeklyTotalSummary");
		elementHelper.hoverAndClickWhenReady(marketSharePage.getPageElements(PageElements.WEEKLY_TOTAL_SUMMARY_INDEXES_RESULT_TABLE_RANDOM_ELEMENT), timeInSeconds);
	}
	
	public void clickDownloadElipsesButton(int timeInSeconds) {
		System.out.println("perform clickDownloadElipsesButton");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		By ellipses = elementHelper.getLastElementLocatorByLocator(marketSharePage.getPageElements(PageElements.DOWNLOAD_ELLIPSIS_BUTTON));
		elementHelper.clickUsingJavaScriptExecutor(ellipses);
	}	
	
	public void clickDowloadExportOption(int timeInSeconds) {
		System.out.println("perform clickDowloadExportOption");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.DOWNLOAD_EXPORT_OPTION), timeInSeconds);
	}
	
	public void clickDowloadDataOption(int timeInSeconds) {
		System.out.println("perform clickDowloadDataOption");
		elementHelper.clickWhenReady(marketSharePage.getPageElements(PageElements.DOWNLOAD_DATA_OPTION), timeInSeconds);
	}
}
