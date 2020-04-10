package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import webdriver.DriverHelper;
import webdriver.ElementHelper;

public class MarketShareSummaryPage2 {

	protected WebDriver driver;
	ElementHelper elementHelper; 
	DriverHelper driverHelper;
	

	public MarketShareSummaryPage2(WebDriver driver) {
		this.driver = driver;
		elementHelper = new ElementHelper(driver);
		driverHelper = new DriverHelper(driver);
	}
	 
	By weeklyTotalSummaryIndexesResultTableRandomElement = By.xpath("//tbody/tr[1]//tbody[1]/tr[2]/td[2]");
	 
	
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
	
	public void hoverOverMonthlyIndex() throws InterruptedException{
		//Thread.sleep(5000);
		//String PageTitle = driver.getTitle();
		//System.out.println("PageTitle is " + PageTitle);
		 WebDriverWait wait = new WebDriverWait(driver, 25);
	        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(weeklyTotalSummaryIndexesResultTableRandomElement)));
		elementHelper.clickWhenReady(weeklyTotalSummaryIndexesResultTableRandomElement, 60);
	 	  
	}
		

}
