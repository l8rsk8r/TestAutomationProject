package tests.examples;

import base.ExtentReportBase;
import base.Properties;
import base.WebDriverBase;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestNewBase2 extends ExtentReportBase {

	WebDriver driver1;
	WebDriverBase driverBase;
	
	private TestNewBase2() {
		driverBase = new WebDriverBase();
	}
	
	@BeforeClass
	private void testSetUp() {
		createTestForClass();
		LOGGER.info("i am in the test itself: "+userDir);
		driver1 = driverBase.instantiateDriver();
		
	}
	
	@Test
	public void test1() throws InterruptedException {
		test.info("test info 1");
		LOGGER.info("This is Test 1");
		driver1.get("https://www.google.com/");
		Thread.sleep(5000);
		System.out.println(Properties.environmentProperties.get("url"));
		driverBase.killDriver(driver1);
	}
	
}
