package pages;
 
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import base.Properties;

public class LobbyLoginPage{

	protected WebDriver driver;
	
	By usernamefield = By.xpath("//input[@id='identifierInput']");
	By enterButton = By.id("btnLogin");
	
	//input#identifierInput.input-flat.watermark
	
	public LobbyLoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setUsername() {   
		//System.out.println(Properties.environmentProperties.get("onqUsername"));
		String onqusername = Properties.environmentProperties.get("onqUsername"); 	
		driver.findElement(usernamefield).sendKeys(onqusername);
	}
	
	public LobbyPasswordPage clickPasswordEnterButton() {
		driver.findElement(enterButton).click();
		return new LobbyPasswordPage(driver);
	}
	 
	
}
