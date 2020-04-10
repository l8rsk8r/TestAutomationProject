package mstr.ui.refactor.steps;

import mstr.ui.refactor.pages.HomePage;
import mstr.ui.refactor.pages.HomePage.HomePageElements;
import org.openqa.selenium.WebDriver;
import webdriver.DriverHelper;
import webdriver.ElementHelper;

public class HomePageSteps {

	protected WebDriver driver;
	HomePage homePage;
	DriverHelper driverHelper;
	ElementHelper elementHelper;
	
	public HomePageSteps(WebDriver driver) {
		this.driver = driver;
		homePage = new HomePage(driver);
		elementHelper = new ElementHelper(driver);
		driverHelper = new DriverHelper(driver);
	}
	
	public void clickSharedReportLink() {
		elementHelper.hoverAndClickWhenReady(homePage.getHomePageElements(HomePageElements.SHARED_REPORTS_LINK), 5);
	}
	
}
