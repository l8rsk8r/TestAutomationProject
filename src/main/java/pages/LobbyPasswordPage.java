package pages;
 
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


import base.Properties;

public class LobbyPasswordPage{

	protected WebDriver driver;
	
	By passwordfield = By.id("password");
	By enterButton = By.id("btnLogin");
	 
	
	public LobbyPasswordPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setpassword() {
		String onqpassword = Properties.environmentProperties.get("onqPassword");	
		//System.out.println("onqpassword " + onqpassword );
		driver.findElement(passwordfield).sendKeys(onqpassword);
			
	}
	
	public MstrHomePage clickLoginEnterButton() {
		driver.findElement(enterButton).click();
		return new MstrHomePage(driver);
	}
	 
	
}
