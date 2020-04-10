package tests.examples;

import base.ExtentReportBase;
import base.Properties;
import base.WebDriverBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestNewBase extends ExtentReportBase {

	WebDriver driver;
	WebDriverBase driverBase;
	
	private TestNewBase() {
		driverBase = new WebDriverBase();
	}
	
	@BeforeClass
	private void classSetUp() {
		createTestForClass();
		driver = driverBase.instantiateDriver();
	}
	
	@Test
	public void micrroStrategyLogin() throws InterruptedException {
		test.info("test info 1");
		LOGGER.info("This is Test 1");
		driver.get("http://www.google.com");
		//driver.get("http://microwebl00ueua.hhc.hilton.com:8080/MicroStrategy/servlet/mstrWeb");
		//driver.findElement(By.xpath("//input[@id='Uid']")).sendKeys("svcDNA_QA");
		//driver.findElement(By.xpath("//input[@id='Pwd']")).sendKeys("jgFXpeDr9mw1m6v2");
		//driver.findElement(By.xpath("//input[@value='Login']")).click();
		Thread.sleep(5000);
		System.out.println(Properties.browserProperties.get("prop1"));
		driverBase.killDriver(driver);
	}
}
