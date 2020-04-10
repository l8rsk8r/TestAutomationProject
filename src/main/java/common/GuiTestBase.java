package common;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import base.Properties;

import io.github.bonigarcia.wdm.WebDriverManager;

/**
 * Includes the set up and teardown for all tests
 * @author
 *
 */
public class GuiTestBase extends BaseTest {

	String userDir;
	String chromeDriverPath;
	String geckoDriverPath;
	String executionEnvironment;
	protected WebDriver driver;
	
	/**
	 * class constructor to set the chrome driver path
	 */
	public GuiTestBase() {
		userDir = System.getProperty("user.dir");
		chromeDriverPath = userDir+"/src/test/resources/drivers/mac/chromedriver 2";
		geckoDriverPath = userDir+"/src/test/resources/drivers/mac/geckodriver";
		LOGGER.fine("GuiTestBase constructor: chrome and gecko driver paths set");
		LOGGER.fine("chrome driver path: "+chromeDriverPath);
		LOGGER.fine("gecko driver path: "+geckoDriverPath);
	}
	
	/**
	 * method to create map of chrome preferences
	 * @return map of chrome preferences
	 */
	public Map<String, Object> chromePreferences() {
		String downloadFilepath = userDir+"/target";
		System.out.println(downloadFilepath);
		Map<String, Object> preferences = new Hashtable<String, Object>();
		preferences.put("download.prompt_for_download", "false");
		preferences.put("download.default_directory", downloadFilepath);
		LOGGER.fine("returning chrome preferences map with download location set to the target folder");
		return preferences;
	}
	
	/**
	 * Set up before the execution of a test scenario
	 * initiates the browser, maximize the browser and sets implicit wait
	 */
	@BeforeClass(alwaysRun=true)
	public void setUpAll() {
		String executionEnvironment = Properties.browserProperties.get("executionEnvironment");
		if (executionEnvironment.equalsIgnoreCase("local")) {
			WebDriverManager.chromedriver().setup();
			ChromeOptions options = new ChromeOptions();
			Map<String, Object> preferences = chromePreferences();
			options.setExperimentalOption("prefs", preferences);
			options.setProxy(null);
			options.addArguments("enable-automation");
//			options.addArguments("--headless");
			options.addArguments("--window-size=1920,1080");
			options.addArguments("--no-sandbox");
			options.addArguments("--disable-extensions");
			options.addArguments("--dns-prefetch-disable");
			options.addArguments("--disable-gpu");
			options.addArguments("--disable-browser-side-navigation");
			options.addArguments("--disable-dev-shm-usage");
			options.addArguments("--disable-infobars");
			options.setPageLoadStrategy(PageLoadStrategy.NORMAL);
			driver = new ChromeDriver(options);
			driver.manage().window().maximize();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		} else if (executionEnvironment.equalsIgnoreCase("saucelabs")) {
			ChromeOptions options = new ChromeOptions();
	        String sauceURL = Properties.sauceProperties.get("sauceURL");
	        Map<String, Object> preferences = chromePreferences();
			options.setExperimentalOption("prefs", preferences);
			String sauceUserName = Properties.sauceProperties.get("sauceUserName");
	        String sauceAccessKey = Properties.sauceProperties.get("sauceAccessKey");
			options.setCapability("username", sauceUserName);
			options.setCapability("accessKey", sauceAccessKey);
			options.setCapability("browserName", Properties.sauceProperties.get("browserName"));
			options.setCapability("platform", Properties.sauceProperties.get("platform"));
			options.setCapability("version", Properties.sauceProperties.get("version"));
			options.setCapability("build", Properties.sauceProperties.get("build"));
			options.setCapability("name", Properties.sauceProperties.get("name"));
	        /** If you're accessing the EU data center, use the following endpoint:.
	         * https://ondemand.eu-central-1.saucelabs.com/wd/hub
	         * */
	        try {
				driver = new RemoteWebDriver(new URL(sauceURL), options);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
	        driver.manage().window().maximize();
			LOGGER.fine("set the driver window size to maximum");
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		}
	}
	
	/**
	 * Tear down for the execution of a test scenario
	 * closes all open browser windows
	 */
	@AfterClass(alwaysRun=true)
	public void tearDownAll() {
		driver.quit();
		LOGGER.fine("closed all browsers");
	}
}
