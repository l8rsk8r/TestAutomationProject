package mstr.ui.refactor.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {
	
	protected WebDriver driver;

	public LoginPage(WebDriver driver) {
		this.driver = driver;
	}
		
	public enum LoginPageElements {
		USERNAME_TEXTBOX,
		PASSWORD_TEXTBOX,
		LOGIN_BUTTON,
		LDAP_AUTHENTICATION_CHECKBOX
	}
	
	By usernameTextbox = By.xpath("//input[@id='Uid']");
	By passwordTextbox = By.xpath("//input[@id='Pwd']");
	By loginButton = By.xpath("//input[@id='3054']");
	
	public By getLoginPageElements(LoginPageElements element) {
		By locator = null;
		switch(element) {
		case USERNAME_TEXTBOX:
			locator = usernameTextbox;
			break;
		case PASSWORD_TEXTBOX:
			locator = passwordTextbox;
			break;
		case LOGIN_BUTTON:
			locator = loginButton;
			break;
		default:
			throw new NullPointerException();
		}
		return locator;
	}
	
}	
