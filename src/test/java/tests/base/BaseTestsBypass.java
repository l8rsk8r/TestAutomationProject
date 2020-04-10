package tests.base;

import base.BaseSetup;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;
import pages.MstrDirectLoginPage;

public class BaseTestsBypass extends BaseSetup {
	
	    private WebDriver driver;
	    protected MstrDirectLoginPage homepage;

	    @BeforeClass
	    public void setUp() throws InterruptedException {
	        System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/mac/chromedriver");
	        driver = new ChromeDriver();

	       
	        homepage = new MstrDirectLoginPage(driver);

	        driver.get("http://microwebl00ueua.hhc.hilton.com:8080/MicroStrategy/servlet/mstrWeb");
			
	    }
	    

	    @AfterClass
	    public void tearDown() {
	        driver.quit();
	    }
	

}
