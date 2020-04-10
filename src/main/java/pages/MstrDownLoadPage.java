package pages;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import pages.MarketShareSummaryPage.PageElements;
import webdriver.DriverHelper;
import webdriver.ElementHelper;
 

public class MstrDownLoadPage {
	
	protected WebDriver driver;
	ElementHelper elementHelper; 
	DriverHelper driverHelper; 
	String PageTitle;
	
	
	
	public enum PageElements {
		EXPORTHEADER,
		DOCUMENTREADYTEXT,
		EXPORTCLOSEWINDOW
	}
	
	//By ExportHeader =  By.xpath("//span[text()='Export']");
	By DocumentReady = By.xpath("//div[contains(text(),'Document ready')]");
	//By ExportHeader = By.className("mstrPathLast");
	By ExportHeader = By.id("mstrWeb_content");
	By Exportclosewindow = By.linkText("Close window");
	
	
	public By getPageElements(PageElements element) {
		By locator = null;
		switch(element) {
		case EXPORTHEADER:
			locator = ExportHeader;
			break;
		case DOCUMENTREADYTEXT:
			locator = DocumentReady;
			break;
		case EXPORTCLOSEWINDOW:
			locator = Exportclosewindow;
			break;
		default:
			throw new NullPointerException();
		}
		return locator;
	}
	

	
	public MstrDownLoadPage(WebDriver driver) {
		this.driver = driver;  
		elementHelper = new ElementHelper(driver);
		driverHelper = new DriverHelper(driver);
	}	
	
	public boolean isExportDisplayed() {
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driverHelper.waitForPageLoaded(); 
		driver.switchTo().window(tabs.get(1));
		PageTitle = driver.getTitle();
		System.out.println("Export Displayed Check - PageTitle is " + PageTitle);
		return elementHelper.isElementDisplayed(getPageElements(PageElements.EXPORTHEADER), 10);
		}
	
	public String getDocumentText() {
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driverHelper.waitForPageLoaded();
		driver.switchTo().window(tabs.get(1));
		PageTitle = driver.getTitle();
		System.out.println("Get Document Check - PageTitle is " + PageTitle);
		return elementHelper.getText(getPageElements(PageElements.DOCUMENTREADYTEXT), 10);
	}
	
	public void closeExportWindow() {
		ArrayList<String> tabs = new ArrayList<String> (driver.getWindowHandles());
		driverHelper.waitForPageLoaded();
		driver.switchTo().window(tabs.get(1));
		elementHelper.clickWhenReady(getPageElements(PageElements.EXPORTCLOSEWINDOW), 5); 		
	}
	
}
