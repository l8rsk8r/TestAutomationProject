package onQ.ui.refactor;

import base.Properties;
import onQ.ui.refactor.OnqLoginPage.PageElements;
import org.openqa.selenium.WebDriver;
import webdriver.DriverHelper;
import webdriver.ElementHelper;

public class OnqLoginPageSteps {

	protected WebDriver driver;
	OnqLoginPage onqLoginPage;
	DriverHelper driverHelper;
	ElementHelper elementHelper;
	private static final String URL = "https://onqinsider.hilton.com";

	public OnqLoginPageSteps(WebDriver driver) {
		this.driver = driver;
		onqLoginPage = new OnqLoginPage(driver);
		elementHelper = new ElementHelper(driver);
		driverHelper = new DriverHelper(driver);
	}
	
	public void navigateToLoginPage() {
		driver.get(URL);
	}
	
	public void enterUsername() {
		String username = Properties.environmentProperties.get("onqUsername");
		elementHelper.sendKeysToStaleElement(onqLoginPage.getPageElements(PageElements.USERNAME_TEXTBOX), username, 5);
	}
	
	public void enterPassword() {
		String password = Properties.environmentProperties.get("onqPassword");
		elementHelper.sendKeysToStaleElement(onqLoginPage.getPageElements(PageElements.PASSWORD_TEXTBOX), password, 5);
	}
	
	public void clickEnterButton() {
		elementHelper.clickWhenReady(onqLoginPage.getPageElements(PageElements.ENTER_BUTTON), 5);
	}
	
	public void waitAfterLogin() {
		driverHelper.waitForPageLoaded();
	}
	
	public void loginToOnQ() throws InterruptedException {
		navigateToLoginPage();
		enterUsername();	
		enterPassword();
		clickEnterButton();
		waitAfterLogin();
	}
}
