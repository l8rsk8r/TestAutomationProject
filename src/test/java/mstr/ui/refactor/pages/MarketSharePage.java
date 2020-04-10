package mstr.ui.refactor.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MarketSharePage {

	protected WebDriver driver;
	
	public MarketSharePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public enum PageElements {
		MONTHLY_TOTAL_SUMMARY_INDEXES_TAB_LEFT_PANE,
		WEEKLY_TOTAL_SUMMARY_INDEXES_TAB_LEFT_PANE,
		FILTER_HEADING_RIGHT_PANE,
		FILTER_BUTTON_TOP_NAV,
		
		MONTH_FILTER,
		MONTH_FILTER_DROPDOWN_BUTTON,
		MONTH_CHEKCBOX,
		MONTHLY_TOTAL_SUMMARY_INDEXES_RESULT_TABLE_RANDOM_ELEMENT,
		
		WEEK_OF_YEAR_FILTER,
		WEEK_OF_YEAR_FILTER_DROPDOWN_BUTTON,
		WEEK_OF_YEAR_CHECKBOX,
		WEEKLY_TOTAL_SUMMARY_INDEXES_RESULT_TABLE_RANDOM_ELEMENT,
		
		DOWNLOAD_ELLIPSIS_BUTTON,
		DOWNLOAD_EXPORT_OPTION,
		DOWNLOAD_DATA_OPTION
	}
	
	By leftPaneMtdYtdMarketShareContainer = By.xpath("//div[text()='MTD/YTD Market Share']/../../../..");
	By leftPaneWeeklyMarketShareContainer = By.xpath("//div[text()='Weekly Market Share']/../../../..");
	By filterHeadingRightPane = By.xpath("///div[text()='Filter']");
	By filterButtonRightPane = By.xpath("//span[text()='Show Filter Panel']/../div");
	
	By monthFilter = By.xpath("//div[starts-with(text(),'Month')]");
	By monthFilterDropdownButton = By.xpath("//div[starts-with(text(),'Month')]/../../../..//div[contains(@class,'mstrmojo-ui-Pulldown-text')]");
	By monthlyTotalSummaryIndexesResultTableRandomElement = By.xpath("(//div[@class='mstrmojo-Xtab-content ']/table//table)[2]//tr[2]/td[3]");
	
	By weekOfYearFilter = By.xpath("//div[starts-with(text(),'Week of Year (')]");
	By weekOfYearFilterDropdownButton = By.xpath("//div[starts-with(text(),'Week of Year (')]/../../../..//div[contains(@class,'mstrmojo-ui-Pulldown-text')]");
	By weeklyTotalSummaryIndexesResultTableRandomElement = By.xpath("//tbody/tr[1]//tbody[1]/tr[2]/td[2]");
	//By weeklyTotalSummaryIndexesResultTableRandomElement = By.cssSelector(".mstrmojo-progress");
	
	By downloadElipsesButton = By.xpath("//div[@class='hover-menu-btn mouse-left']");
	By downloadExportOption = By.xpath("//div[text()='Export']");
	By downloadDataOption = By.xpath("//div[text()='Data']");
	
	public By getPageElements(PageElements element) {
		By locator = null;
		switch(element) {
		case MONTHLY_TOTAL_SUMMARY_INDEXES_TAB_LEFT_PANE:
			locator = getLeftPaneMtdYtdMarketShareElements(element);
			break;
		case WEEKLY_TOTAL_SUMMARY_INDEXES_TAB_LEFT_PANE:
			locator = getLeftPaneMonthlyMarketShareElements(element);
			break;
		case FILTER_HEADING_RIGHT_PANE:
			locator = filterHeadingRightPane;
			break;
		case FILTER_BUTTON_TOP_NAV:
			locator = filterButtonRightPane;
			break;
			
		case MONTH_FILTER:
			locator = monthFilter;
			break;
		case MONTH_FILTER_DROPDOWN_BUTTON:
			locator = monthFilterDropdownButton;
			break;
		case MONTHLY_TOTAL_SUMMARY_INDEXES_RESULT_TABLE_RANDOM_ELEMENT:
			locator = monthlyTotalSummaryIndexesResultTableRandomElement;
			break;
		case WEEK_OF_YEAR_FILTER:
			locator = weekOfYearFilter;
			break;
		case WEEK_OF_YEAR_FILTER_DROPDOWN_BUTTON:
			locator = weekOfYearFilterDropdownButton;
			break;
		case WEEKLY_TOTAL_SUMMARY_INDEXES_RESULT_TABLE_RANDOM_ELEMENT:
			locator = weeklyTotalSummaryIndexesResultTableRandomElement;
			break;
			
		case DOWNLOAD_ELLIPSIS_BUTTON:
			locator = downloadElipsesButton;
			break;
		case DOWNLOAD_EXPORT_OPTION:
			locator = downloadExportOption;
			break;
		case DOWNLOAD_DATA_OPTION:
			locator = downloadDataOption;
			break;
		default:
			throw new NullPointerException();
		}
		return locator;
	}
	
	/**
	 * This method returns the page Elements with parameters like the filters where a specific checkbox could be passed
	 * For the filters, if All needs to be selected pass one of the other valid options as well as All seperated by *
	 * Example for above would be, to select All from year filter the elementParameter value would be 2019*All
	 * To click on the OK or cancel button, use the above way except replace All with OkButton or CancelButton respectively
	 * Example for this would be 2019*OkButton and 2019*CancelButton respectively
	 * @param element to identify
	 * @param elemnetParameter dynamic element property
	 * @return
	 */
	public By getPageElements(PageElements element, String elemnetParameter) {
		By locator = null;
		switch(element) {
		case MONTH_CHEKCBOX:
			locator = getMonthFilterElements(elemnetParameter);
			break;
		case WEEK_OF_YEAR_CHECKBOX:
			locator = getWeekOfYearFilterElements(elemnetParameter);
			break;
		default:
			throw new NullPointerException();
		}
		return locator;
		
	}
	
	private By getLeftPaneMtdYtdMarketShareElements(PageElements element) {
		By locator = null;
		String leftPaneMtdYtdMarketShareContainerXpath = "//div[text()='MTD/YTD Market Share']/../../../..";
		switch(element) {
		case MONTHLY_TOTAL_SUMMARY_INDEXES_TAB_LEFT_PANE:
			locator = By.xpath(leftPaneMtdYtdMarketShareContainerXpath+"//span[text()='Total Summary - Indexes']");
			break;
		default:
			throw new NullPointerException();
		}
		return locator;
	}
	
	private By getLeftPaneMonthlyMarketShareElements(PageElements element) {
		By locator = null;
		String leftPaneMtdYtdMarketShareContainerXpath = "//div[text()='Weekly Market Share copy ']/../../../..";
		switch(element) {
		case WEEKLY_TOTAL_SUMMARY_INDEXES_TAB_LEFT_PANE:
			locator = By.xpath(leftPaneMtdYtdMarketShareContainerXpath+"//span[text()='Total Summary - Indexes']");
			break;
		default:
			throw new NullPointerException();
		}
		return locator;
	}
	
	public By getMonthFilterElements(String monthAndYearValue) {
		By locator = null;
		String xpath = null;
		if (monthAndYearValue.contains("All")) {
			String[] split = monthAndYearValue.split(",");
			String sibling = split[0];
			String siblingXpath = "//span[text()='"+sibling+"']/..";
			String parentXpath = siblingXpath+"/..";
			xpath = parentXpath+"//span[text()='(All)']/..";	
		}else if (monthAndYearValue.contains("OkButton")) {
			String[] split = monthAndYearValue.split(",");
			String sibling = split[0];
			String siblingXpath = "//span[text()='"+sibling+"']/..";
			String parentXpath = siblingXpath+"/../../../../..";
			xpath = parentXpath+"//div[text()='OK']";
		}else {
			xpath = "//span[text()='"+monthAndYearValue+"']/..";
		}
		locator = By.xpath(xpath);
		return locator;
	}
	
	public By getWeekOfYearFilterElements(String weekOfYearValue) {
		By locator = null;
		String xpath = null;
		if (weekOfYearValue.contains("All")) {
			String[] split = weekOfYearValue.split(",");
			String sibling = split[0];
			String siblingXpath = "//span[text()='"+sibling+"']/..";
			String parentXpath = siblingXpath+"/..";
			xpath = parentXpath+"//span[text()='(All)']/..";	
		}else if (weekOfYearValue.contains("OkButton")) {
			String[] split = weekOfYearValue.split(",");
			String sibling = split[0];
			String siblingXpath = "//span[text()='"+sibling+"']/..";
			String parentXpath = siblingXpath+"/../../../../..";
			xpath = parentXpath+"//div[text()='OK']";
		}else {
			xpath = "//span[text()='"+weekOfYearValue+"']/..";
		}
		locator = By.xpath(xpath);
		return locator;
	}
}


