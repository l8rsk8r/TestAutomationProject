package webdriver;

import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;

/**
 * Helps manage the driver and to manipulate the driver including wait times
 * @author
 *
 */
public class DriverHelper {
	
	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	WebDriver driver;
	ExtentTest childTest;
	
	/**
	 * class constructor to get the driver and set the driver for the object
	 * @param driver
	 */
	public DriverHelper(WebDriver driver) {
		this.driver = driver;
		LOGGER.fine("instantiated the driver in the constructor");
	}
	
	/**
	 * Wait for page load before and after navigation to a specific url
	 * @param url
	 */
	public void navigateTo(String url) {
		LOGGER.fine("input url: "+url);
		waitForPageLoaded();
		LOGGER.fine("navigating to url");
		driver.navigate().to(url);
		LOGGER.fine("navigated to url: "+url);
		waitForPageLoaded();
	}
	
	/**
	 * waits for document ready state to be complete (page load) for max of 120 seconds (2 minutes)
	 */
	public void waitForPageLoaded() {
		LOGGER.fine("waiting for page load (ready state using JavaScriptExecutor ");
		ExpectedCondition<Boolean> expectation = new
				ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            LOGGER.fine("setting the WebDriverWait to 2 mins (120 secs)");
            WebDriverWait wait = new WebDriverWait(driver, 120);
            wait.until(expectation);
            LOGGER.fine("page load complete");
        } catch (Throwable t) {
        	LOGGER.warning("Throwable: timeout waiting for page load to complete");
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }
	
	/**
	 * waits for document ready state to be complete (page load)
	 * @param maxTimeInSeconds = maximum time out 
	 */
	public void waitForPageLoaded(int maxTimeInSeconds) {
		LOGGER.fine("waiting for page load (ready state using JavaScriptExecutor ");
		ExpectedCondition<Boolean> expectation = new
				ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            Thread.sleep(1000);
            LOGGER.fine("setting the WebDriverWait to: "+maxTimeInSeconds+" secs");
            WebDriverWait wait = new WebDriverWait(driver, maxTimeInSeconds);
            wait.until(expectation);
            LOGGER.fine("page load complete");
        } catch (Throwable t) {
        	LOGGER.warning("Throwable: timeout waiting for page load to complete");
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }
	
	/**
	 * waits for page title to be a certain value for max of 120 seconds (2 minutes)
	 * @param pageTitle = page title to wait for
	 */
	public void waitForTitle(String pageTitle) {
		LOGGER.fine("waiting for page title: "+pageTitle);
		try {
            LOGGER.fine("setting the WebDriverWait to 2 mins (120 secs)");
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.titleIs(pageTitle));
			LOGGER.fine("title displayed");
		} catch(Throwable t) {
        	LOGGER.warning("Throwable: timeout waiting for page title");
			Assert.fail("Timeout waiting for Title");
		}
	}
	
	/**
	 * waits for an element on the page to be present with a max wait of 120 seconds (2 mins)
	 * @param locator = locator of the element to wait for
	 */
	public void waitForElement(By locator) {
		LOGGER.fine("waiting for element by locator: "+locator);
		try {
            LOGGER.fine("setting the WebDriverWait to 2 mins (120 secs)");
			WebDriverWait wait = new WebDriverWait(driver, 120);
			wait.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(locator)
					)
				);
			LOGGER.fine("presense of element located");
		} catch(Throwable t) {
        	LOGGER.warning("Throwable: timeout waiting for element presense");
			Assert.fail("Timeout waiting for Element");
		}
	}
	
	/**
	 * waits for an element on the page to be present for the given time
	 * @param locator = locator of the element to wait for
	 * @param maxTimeInSeconds = maximum time out 
	 */
	public void waitForElement(By locator, int maxTimeInSeconds) {
		LOGGER.fine("waiting for element by locator: "+locator);
		try {
            LOGGER.fine("setting the WebDriverWait to: "+maxTimeInSeconds+" secs");
			WebDriverWait wait = new WebDriverWait(driver, maxTimeInSeconds);
			wait.until(ExpectedConditions.or(
					ExpectedConditions.presenceOfElementLocated(locator)
					)
				);
			LOGGER.fine("presense of element located");
		} catch(Throwable t) {
        	LOGGER.warning("Throwable: timeout waiting for element presense");
			Assert.fail("Timeout waiting for Element");
		}
	}
	
}
