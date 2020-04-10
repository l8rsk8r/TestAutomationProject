package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import webdriver.DriverHelper;
import webdriver.ElementHelper;

public class MarketShareSummaryPage {

	protected WebDriver driver;
	ElementHelper elementHelper; 
	DriverHelper driverHelper;
	

	public MarketShareSummaryPage(WebDriver driver) {
		this.driver = driver;
		elementHelper = new ElementHelper(driver);
		driverHelper = new DriverHelper(driver);
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
		DOWNLOAD_DATA_OPTION,
		FILTER_APPLY,
		MSTR_WAIT_BOX
	}
	
	By leftPaneMtdYtdMarketShareContainer = By.xpath("//div[text()='MTD/YTD Market Share']/../../../..");
	By leftPaneWeeklyMarketShareContainer = By.xpath("//div[text()='Weekly Market Share']/../../../..");
	By filterHeadingRightPane = By.xpath("///div[text()='Filter']");
	By filterButtonRightPane = By.xpath("//span[text()='Filter']/../div");
	
	By monthFilter = By.xpath("//div[starts-with(text(),'Month')]");
	By monthFilterDropdownButton = By.xpath("//div[starts-with(text(),'Month')]/../../../..//div[contains(@class,'mstrmojo-ui-Pulldown-text')]");
	By monthlyTotalSummaryIndexesResultTableRandomElement = By.xpath("(//div[@class='mstrmojo-Xtab-content ']/table//table)[2]//tr[2]/td[3]");
	
	By weekOfYearFilter = By.xpath("//div[starts-with(text(),'Week Of Year Filter')]");
	 
	//By weekOfYearFilterDropdownButton = By.xpath("//div[starts-with(text(),'Week Of Year')]/../../../..//div[contains(@class,'mstrmojo-ui-Pulldown-text')]");
	By weekOfYearFilterDropdownButton = By.id("mstr1219");
	By weeklyTotalSummaryIndexesResultTableRandomElement = By.xpath("//tbody/tr[1]//tbody[1]/tr[2]/td[2]");
	By filterApplybtn = By.xpath("//div[@class='mstrmojo-Button-text '][contains(text(),'Apply')]");
	
	By WeeklyMarketShareMenuTotalSummaryIndexes = By.xpath("//div[text()='Weekly Market Share']/../../../..//div[text()='Weekly Market Share']/../../../..//span[text()='Total Summary - Indexes']");
	
	
	By mstrImageSource = By.xpath("//div[@class='img-cntr']"); 
	By totalSummaryHeader = By.xpath("//div[starts-with(text(),'Total Summary')]");

	
	By downloadElipsesButton = By.xpath("//div[@class='hover-menu-btn mouse-left']");
	By downloadExportOption = By.xpath("//div[text()='Export']");
	By downloadDataOption = By.xpath("//div[text()='Data']");
	By mstrWaitBox = By.cssSelector("#waitBox > div.mstrmojo-Editor.mstrWaitBox.modal");
	
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
		case FILTER_APPLY:
			locator=filterApplybtn;
			break;	
		case MSTR_WAIT_BOX:
		    locator = mstrWaitBox;
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
			locator = getWeekOfYearFilterElementsModified(elemnetParameter);
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
		String leftPaneMtdYtdMarketShareContainerXpath = "//div[text()='Weekly Market Share']/../../../..";
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
	
	public By getWeekOfYearFilterElementsModified(String weekOfYearValue) {
		By locator = null;
		String xpath = null;
			xpath = "//span[contains(text(),'"+weekOfYearValue+"')]/..";
		locator = By.xpath(xpath);
		return locator;
	}

	public void clickDownloadElipsesButton() {
		driver.findElement(downloadElipsesButton).click();
	}
	
	public void hoverOverMonthlyIndex() throws InterruptedException{
		 //String PageTitle = driver.getTitle();
		//System.out.println("PageTitle is " + PageTitle);
		 WebDriverWait wait = new WebDriverWait(driver, 20);
	       wait.until(ExpectedConditions.visibilityOfElementLocated(mstrImageSource));
		//elementHelper.clickWhenReady(getPageElements(PageElements.WEEKLY_TOTAL_SUMMARY_INDEXES_TAB_LEFT_PANE), 120);
	       driver.findElement(WeeklyMarketShareMenuTotalSummaryIndexes).click();
		     		 
	}
	
	public void clickFilterButtonIfFilterPaneNotOpen(int timeInSeconds) {
		driverHelper.waitForPageLoaded(120);
		System.out.println("perform clickFilterButtonIfFilterPaneNotOpen");
		elementHelper.clickIfExist(filterButtonRightPane, 5);
		//driver.findElement(filterButtonRightPane).click();

	}
	
	/*
	 * WEEK OF YEAR FILTER STEPS
	 */
	
	public void clickWeekOfYearFilter(int timeInSeconds) {
		System.out.println("perform clickWeekOfYearFilter");
		elementHelper.clickWhenReady(getPageElements(PageElements.WEEK_OF_YEAR_FILTER), timeInSeconds);
	}
	
	public void clickWeekOfYearFilterDropdownButton(int timeInSeconds) {
		System.out.println("perform clickWeekOfYearFilterDropdownButton");
		elementHelper.clickWhenReady(getPageElements(PageElements.WEEK_OF_YEAR_FILTER_DROPDOWN_BUTTON), timeInSeconds);
	}	
	
	public void clickWeekOfYearCheckbox(String weekOfYearValue, int timeInSeconds) {
		System.out.println("perform clickWeekOfYearCheckbox");
		elementHelper.clickWhenReady(getPageElements(PageElements.WEEK_OF_YEAR_CHECKBOX, weekOfYearValue), timeInSeconds);
	}
	
	public void clickWeekOfYearOkButton(String weekOfYearValue, int timeInSeconds) {
		System.out.println("perform clickWeekOfYearOkButton");
		elementHelper.clickWhenReady(getPageElements(PageElements.WEEK_OF_YEAR_CHECKBOX, weekOfYearValue), timeInSeconds);
	}
	
	//TODO: convert to select multiple months at a time
		public void selectWeekOfYearCheckbox(String weekOfYearValue, int timeInSeconds) {
			System.out.println("perform selectWeekOfYearCheckbox");
			System.out.println(weekOfYearValue);
			String allweekOfYearValues = weekOfYearValue+",All";
			System.out.println(allweekOfYearValues);
			String okButtonweekOfYearValue = weekOfYearValue+",OkButton";
			By allCheckbox = getPageElements(PageElements.WEEK_OF_YEAR_CHECKBOX, allweekOfYearValues);
			WebElement allCheckboxEle = driver.findElement(allCheckbox);
			String allCheckboxEleClassName = allCheckboxEle.getAttribute("className");
			if (allCheckboxEleClassName.contains("selected")) {
				WebElement allCheckboxEleStale = driver.findElement(allCheckbox);
				elementHelper.clickWhenReady(allCheckboxEleStale, 5);
			}
			clickWeekOfYearCheckbox(weekOfYearValue, timeInSeconds);
			clickWeekOfYearOkButton(okButtonweekOfYearValue, timeInSeconds);
		}
		 
	public void selectWeekOfYearCheckboxModified(String StartDateofWeekValue, int timeInSeconds) {
		System.out.println("perform selectWeekOfYearCheckbox");
		System.out.println(StartDateofWeekValue);

		By allCheckbox = getPageElements(PageElements.WEEK_OF_YEAR_CHECKBOX, StartDateofWeekValue);
		WebElement allCheckboxEle = driver.findElement(allCheckbox);
		elementHelper.clickWhenReady(allCheckboxEle, 10);
	}
		
		public void clickResultTableForWeeklyTotalSummary(int timeInSeconds) throws InterruptedException {
			System.out.println("perform clickResultTableForWeeklyTotalSummary");
			Thread.sleep(10000);
			elementHelper.clickWhenReady(getPageElements(PageElements.WEEKLY_TOTAL_SUMMARY_INDEXES_RESULT_TABLE_RANDOM_ELEMENT), timeInSeconds);
			
		}
		
		public void clickDownloadElipsesButton(int timeInSeconds) {
			driverHelper.waitForPageLoaded(120);
				System.out.println("perform clickDownloadElipsesButton");
			By ellipses = elementHelper.getLastElementLocatorByLocator(getPageElements(PageElements.DOWNLOAD_ELLIPSIS_BUTTON));
			elementHelper.clickUsingJavaScriptExecutor(ellipses);
		}	
		
		public void clickDowloadExportOption(int timeInSeconds) {
			System.out.println("perform clickDowloadExportOption");
			elementHelper.clickWhenReady(getPageElements(PageElements.DOWNLOAD_EXPORT_OPTION), timeInSeconds);
		}
		
		public void clickDowloadDataOption(int timeInSeconds) {
			System.out.println("perform clickDowloadDataOption");
				elementHelper.clickWhenReady(getPageElements(PageElements.DOWNLOAD_DATA_OPTION), timeInSeconds);
		}
		
		public void clickFilterApply(int timeInSeconds) {
			System.out.println("perform clickFilterApply");
			driverHelper.waitForPageLoaded(120);
			elementHelper.clickWhenReady(getPageElements(PageElements.FILTER_APPLY), timeInSeconds); 
		}
		
		public MstrDownLoadPage performDownloadReportAction() {
			clickDownloadElipsesButton(20);
			clickDowloadExportOption(20); 
			clickDowloadDataOption(20);
			return new MstrDownLoadPage(driver);
		}
		 

}
