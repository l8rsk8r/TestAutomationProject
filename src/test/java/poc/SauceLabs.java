package poc;

import base.Properties;
import common.GuiTestBase;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.net.URL;

public class SauceLabs extends GuiTestBase{

	@Test
	public void demo() {
		String sauceUserName = Properties.sauceProperties.get("sauceUsername");
        String sauceAccessKey = Properties.sauceProperties.get("sauceAccessKey");
        String sauceURL = Properties.sauceProperties.get("sauceURL");
        /**
         * In this exercise use the Platform Configurator, located here:
         * https://wiki.saucelabs.com/display/DOCS/Platform+Configurator#/
         * in order to replace the following DesiredCapabilities: browserName, platform, and version
         * For example, I chose to use Windows 10 with Chrome version 59.
         * Note: If you use Chrome version 61+ you must use the sauce:options capability.
         * More info here: https://wiki.saucelabs.com/display/DOCS/Selenium+W3C+Capabilities+Support+-+Beta
         */
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("username", sauceUserName);
        capabilities.setCapability("accessKey", sauceAccessKey);
        capabilities.setCapability("browserName", Properties.sauceProperties.get("browserName"));
        capabilities.setCapability("platform", Properties.sauceProperties.get("platform"));
        capabilities.setCapability("version", Properties.sauceProperties.get("version"));
        capabilities.setCapability("build", Properties.sauceProperties.get("build"));
        capabilities.setCapability("name", Properties.sauceProperties.get("name"));

        /** If you're accessing the EU data center, use the following endpoint:.
         * https://ondemand.eu-central-1.saucelabs.com/wd/hub
         * */
        try {
			driver = new RemoteWebDriver(new URL(sauceURL), capabilities);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        /** Don't forget to enter in your application's URL in place of 'https://www.saucedemo.com'. */
        driver.navigate().to("https://www.saucedemo.com");
	}
	
	@AfterMethod
    public void cleanUpAfterTestMethod(ITestResult result) {
        ((JavascriptExecutor)driver).executeScript("sauce:job-result=" + (result.isSuccess() ? "passed" : "failed"));
        driver.quit();
    }
}
