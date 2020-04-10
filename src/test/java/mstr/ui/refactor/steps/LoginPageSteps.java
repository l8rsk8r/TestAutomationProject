package mstr.ui.refactor.steps;

import mstr.ui.refactor.pages.LoginPage;
import mstr.ui.refactor.pages.LoginPage.LoginPageElements;
import org.openqa.selenium.WebDriver;
import webdriver.DriverHelper;
import webdriver.ElementHelper;

public class LoginPageSteps {

	protected WebDriver driver;
	LoginPage loginPage = new LoginPage(driver);
	DriverHelper driverHelper;
	ElementHelper elementHelper;
	
	public LoginPageSteps(WebDriver driver) {
		this.driver = driver;
		elementHelper = new ElementHelper(driver);
		driverHelper = new DriverHelper(driver);
	}
	
	public void enterUsername() {
		elementHelper.sendKeystWhenReady(loginPage.getLoginPageElements(LoginPageElements.USERNAME_TEXTBOX), System.getProperty("onqUsername"), 5);
	}
	
	public void enterPassword() {
		elementHelper.sendKeystWhenReady(loginPage.getLoginPageElements(LoginPageElements.PASSWORD_TEXTBOX), System.getProperty("onqPassword"), 5);
	}
	
	public void clickLoginButton() {
		driver.findElement(loginPage.getLoginPageElements(LoginPageElements.LOGIN_BUTTON)).click();
	}
	
	public void performLoginAction() {
		enterUsername();
		enterPassword();
		clickLoginButton();
	}
	
}
