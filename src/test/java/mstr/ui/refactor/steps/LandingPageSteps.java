package mstr.ui.refactor.steps;

import mstr.ui.refactor.pages.LandingPage;
import mstr.ui.refactor.pages.LandingPage.LandingPageElements;
import org.openqa.selenium.WebDriver;
import webdriver.DriverHelper;
import webdriver.ElementHelper;

public class LandingPageSteps {

	protected WebDriver driver;
	DriverHelper driverHelper;
	ElementHelper elementHelper;
	LandingPage landingPage;
	
	public LandingPageSteps(WebDriver driver) {
		this.driver = driver;
		landingPage = new LandingPage(driver);
		elementHelper = new ElementHelper(driver);
		driverHelper = new DriverHelper(driver);
	}
	
	public void clickMarketShareLink() {
		elementHelper.hoverAndClickWhenReady(landingPage.getLandingPageElements(LandingPageElements.MARKET_SHARE_LINK), 5);
	}
	
	public void clickClepLink() {
		elementHelper.hoverAndClickWhenReady(landingPage.getLandingPageElements(LandingPageElements.CELP_LINK), 5);
	}

	public void clickDigitalAndMarketingLink() {
		elementHelper.hoverAndClickWhenReady(landingPage.getLandingPageElements(LandingPageElements.DIGITAL_AND_MARKETING_LINK), 5);
	}
	
	public void clickRevenueManagementLink() {
		elementHelper.hoverAndClickWhenReady(landingPage.getLandingPageElements(LandingPageElements.REVENUE_MANAGEMENT_LINK), 5);
	}
	
	public void clickRevenueManagementQaLink() {
		elementHelper.hoverAndClickWhenReady(landingPage.getLandingPageElements(LandingPageElements.REVENUE_MANAGEMENT_QA_LINK), 5);
	}
	
	public void clickDelphiLink() {
		elementHelper.hoverAndClickWhenReady(landingPage.getLandingPageElements(LandingPageElements.DELPHI), 5);
	}
	
	
}