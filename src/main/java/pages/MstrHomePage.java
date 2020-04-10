package pages;

import org.openqa.selenium.By; 
import org.openqa.selenium.WebDriver;

import webdriver.DriverHelper;


public class MstrHomePage {
	
	DriverHelper driverHelper; 
	protected WebDriver driver;
	
		
	By mstrlogo = By.id("mstrWeb_path"); 
	
	public MstrHomePage(WebDriver driver) {
		this.driver = driver; 
		driverHelper = new DriverHelper(driver);   
	}
	
	public boolean isMstrLogoPresent() {
		driverHelper.waitForPageLoaded(20);
		return driver.findElement(mstrlogo).isDisplayed();	
	}
	
	//Leave here for now - Might want to put it into a utility package
	public MarketShareSummaryPage navigatetoURL(String reportlink) {
		driver.get(reportlink);
		return new MarketShareSummaryPage(driver);	
	}
	
}
