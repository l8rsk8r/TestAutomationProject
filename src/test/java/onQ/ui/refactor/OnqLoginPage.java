package onQ.ui.refactor;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class OnqLoginPage {

protected WebDriver driver;
	
	public OnqLoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public enum PageElements {
		USERNAME_TEXTBOX,
		PASSWORD_TEXTBOX,
		ENTER_BUTTON
	}
	
	By usernameTextbox = By.xpath("//input[@name='txtUserID']");
	By passwordTextbox = By.xpath("//input[@name='txtPassword']");
	By enterButton = By.xpath("//input[@name='btnLogin']");

	public By getPageElements(PageElements element) {
		By locator = null;
		switch(element) {
		case USERNAME_TEXTBOX:
			locator = usernameTextbox;
			break;
		case PASSWORD_TEXTBOX:
			locator = passwordTextbox;
			break;
		case ENTER_BUTTON:
			locator = enterButton;
			break;
		default:
			throw new NullPointerException();
		}
		return locator;
	}
}
