package pages;
 
import org.openqa.selenium.By;

import org.openqa.selenium.WebDriver;
import base.Properties;

public class MstrDirectLoginPage{

	protected WebDriver driver;
	
	By usernamefield = By.xpath("//input[@id='Uid']");
	By passwordfield = By.xpath("//input[@id='Pwd']");
	By loginButton = By.xpath("//input[@id='3054']");
	
	//input#identifierInput.input-flat.watermark
	
	public MstrDirectLoginPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public void setUsername() {   
		//System.out.println(Properties.environmentProperties.get("mstrApiServiceAccountUsername"));
		String mstrusername = Properties.environmentProperties.get("mstrApiServiceAccountUsername"); 	
		driver.findElement(usernamefield).sendKeys(mstrusername);
	}
	
	public void setPassword() {   
		//System.out.println(Properties.environmentProperties.get("mstrApiServiceAccountPassword"));
		String mstrpassword = Properties.environmentProperties.get("mstrApiServiceAccountPassword"); 	
		driver.findElement(passwordfield).sendKeys(mstrpassword);
	}
	
	public MstrHomePage clickPasswordEnterButton() {
		driver.findElement(loginButton).click();
		return new MstrHomePage(driver);
	}
	 
	
}
