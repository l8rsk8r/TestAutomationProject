package webdriver;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.List;
import java.util.logging.Logger;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementNotVisibleException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.ExtentTest;
import utils.StringHelper;

/**
 * This class helps manipulate elements on the page
 * @author
 *
 */
public class ElementHelper {
	
	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	WebDriver driver;
	ExtentTest childTest;
	StringHelper stringHelper = new StringHelper();
	private int defaultTimeOutInSeconds = 60;
	
	/**
	 * Class constructor to set the driver for the class object
	 * @param driver
	 */
	public ElementHelper(WebDriver driver) {
		this.driver = driver;
		LOGGER.fine("instantiated driver from the constructor");
	}
	
	/**
	 * Validates if the value of the element is null or not provided the max time out
	 * @param locator to identify the element
	 * @param valueExist true if the value should not be null or empty and false if the value should be null or empty
	 * @param timeOutInSeconds maximum time out
	 */
	public void validateElementValueIsNullOrNot(By locator, boolean valueExist, int timeOutInSeconds) {
		LOGGER.fine("validating if the value of an element is null or not null");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		WebElement ele = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		String eleValue = ele.getAttribute("value");
		if (valueExist == true) {
			try {
				assertFalse(eleValue.toString().equals("") || eleValue.toString().isEmpty());
				LOGGER.fine("value found for element by locator: "+locator.toString()+" as expected");
			} catch (AssertionError ae) {
				LOGGER.warning("AssertionError: value not found for element by locator: "+locator.toString());
			}
		} else if (valueExist == false) {
			try {
				assertTrue(eleValue.toString().equals("") || eleValue.toString().isEmpty());
				LOGGER.fine("value not found for element by locator: "+locator.toString()+" as expected");
			} catch (AssertionError ae) {
				LOGGER.warning("AssertionError: value found for element by locator: "+locator.toString());
			}
		}
	}
	
	/**
	 * Validates if the value of the element without any wait
	 * @param locator to identify the element
	 * @param valueExist true if the value should not be null or empty and false if the value should be null or empty
	 */
	public void validateElementValueIsNullOrNot(WebElement ele, boolean valueExist) {
		LOGGER.fine("validating if the value of an element is null or not null");
		String eleValue = ele.getAttribute("value");
		if (valueExist == true) {
			try {
				assertFalse(eleValue.toString().equals("") || eleValue.toString().isEmpty());
				LOGGER.fine("value found for element by locator as expected");
			} catch (AssertionError ae) {
				LOGGER.warning("AssertionError: value not found for element by locator: ");
			}
		} else if (valueExist == false) {
			try {
				assertTrue(eleValue.toString().equals("") || eleValue.toString().isEmpty());
				LOGGER.fine("value not found for element by locator as expected");
			} catch (AssertionError ae) {
				LOGGER.warning("AssertionError: value found for element by locator: ");
			}
		}
	}
	
	/**
	 * Returns true or false if the element is present on the page
	 * @param locator to identify the element
	 * @param timeOutInSeconds maximum time out
	 */
	public boolean isElementDisplayed(By locator , int timeOutInSeconds) {
		LOGGER.fine("checking if the element is present or not");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		WebElement element;
		try {
			element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
			LOGGER.fine("element found by locator: "+locator.toString());
			LOGGER.fine("element present with tag name : "+element.getTagName());
			return true;
		} catch (NullPointerException ne) {
			LOGGER.fine("NullPointerException: element not found by locator: "+locator.toString());
			ne.printStackTrace();
			return false;
		} catch (Exception e) {
			LOGGER.fine("Exception: element not found by locator: "+locator.toString());
			e.printStackTrace();
			return false;
		}
	}
	
	public void clickUsingActions(By locator) {
		LOGGER.fine("performing click an hold using Actions class for element by locator: "+locator.toString());
		WebElement element = driver.findElement(locator);
		Actions action = new Actions(driver);
	    action.click(element).build().perform();
		LOGGER.fine("performed click an hold using Actions class for element by locator: "+locator.toString());
	}
	
	/**
	 * Clicks and element and holds the element until released
	 * use method release method to release the click
	 * @param locator to identify the element
	 */
	public void clickAndHold(By locator) {
		LOGGER.fine("performing click an hold using Actions class for element by locator: "+locator.toString());
		WebElement element = driver.findElement(locator);
		Actions action = new Actions(driver);
	    action.clickAndHold(element).build().perform();
		LOGGER.fine("performed click an hold using Actions class for element by locator: "+locator.toString());
	}
	
	/**
	 * Generally used after performing a click and hold
	 * helps release the click hold
	 * @param element
	 */
	public void release(By locator) {
		LOGGER.fine("performing release using Actions class for element by locator: "+locator.toString());
		WebElement element = driver.findElement(locator);
		Actions action = new Actions(driver);
	    action.moveToElement(element).release();
		LOGGER.fine("performed release using Actions class for element by locator: "+locator.toString());
	}
	
	/**
	 * Clicks on element with the provided max time out
	 * @param locator to identify the element
	 * @param timeOutInSeconds maximum time out
	 */
	public void clickWhenVisible(By locator , int timeOutInSeconds, int pollingInSeconds) {
		LOGGER.fine("performing click when ready for elemnt by locator: "+locator.toString());
		
		WebElement element = driver.findElement(locator);
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		element.click();
		
		LOGGER.fine("performed click when ready for element by locator: "+locator.toString());
	}
	
	/**
	 * Clicks on element with the provided max time out
	 * @param locator to identify the element
	 * @param timeOutInSeconds maximum time out
	 */
	public void clickWhenReady(By locator , int timeOutInSeconds) {
		LOGGER.fine("performing click when ready for elemnt by locator: "+locator.toString());
		WebElement element = driver.findElement(locator);
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		element = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
		element.click();
		LOGGER.fine("performed click when ready for elemnt by locator: "+locator.toString());
	}
	
	/**
	 * Clicks on element with the provided max time out if the element exists
	 * @param locator to identify the element
	 * @param timeOutInSeconds maximum time out
	 */
	public void clickIfExist(By locator , int timeOutInSeconds) {
		LOGGER.fine("performing click if clickable for elemnt by locator: "+locator.toString());
		try {
			WebElement element = driver.findElement(locator);
			WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
			element = wait.until(ExpectedConditions.elementToBeClickable(locator));
			element.click();
			LOGGER.fine("performed click if clickable for elemnt by locator: "+locator.toString());
		}catch(Exception e) {
			LOGGER.warning("Exception: could not click if clickable"+locator.toString());
			e.printStackTrace();
		}
	}
	
	/**
	 * Clicks on element with the provided max time out
	 * @param element = element to be clicked
	 * @param timeOutInSeconds maximum time out
	 */
	public void clickWhenReady(WebElement element , int timeOutInSeconds) {
		LOGGER.fine("performing click when ready for element");
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		element = wait.until(ExpectedConditions.elementToBeClickable(element));
		element.click();
		LOGGER.fine("performed click when ready for element");
	}
	
	/**
	 * Clicks on element using Javascript Executor
	 * @param locator to identify the element
	 */
	public void clickUsingJavaScriptExecutor(By locator) {
		LOGGER.fine("performing click using java script executor for element with locator: "+locator.toString());
		WebElement element = driver.findElement(locator);
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		LOGGER.fine("performed click using java script executor for element with locator: "+locator.toString());
	}
	
	/**
	 * Clicks on element using Javascript Executor
	 * @param element to click
	 */
	public void clickUsingJavaScriptExecutor(WebElement element) {
		LOGGER.fine("performing click using java script executor");
		JavascriptExecutor executor = (JavascriptExecutor)driver;
		executor.executeScript("arguments[0].click();", element);
		LOGGER.fine("performed click using java script executor");
	}
	
	/**
	 * Hovers on element with the provided max time out
	 * @param locator to identify the element
	 * @param timeOutInSeconds maximum time out
	 */
	public void hoverWhenReady(By locator , int timeOutInSeconds) {
		LOGGER.fine("performing hover when ready using Actions class for element with locator: "+locator.toString());
		WebElement element = driver.findElement(locator);
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		Actions action = new Actions(driver);
		action.moveToElement(element).perform();
		LOGGER.fine("performed hover when ready using Actions class for element with locator: "+locator.toString());
	}
	
	/**
	 * Hovers and then clicks on element with the provided max time out
	 * @param locator to identify the element
	 * @param timeOutInSeconds maximum time out
	 */
	public void hoverAndClickWhenReady(By locator, int timeOutInSeconds) {
		LOGGER.fine("performing hover and click when ready using Actions class for element with locator: "+locator.toString());
		WebElement element = driver.findElement(locator);
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		Actions action = new Actions(driver);
		action.moveToElement(element).click(element).build().perform();
		LOGGER.fine("performed hover and click when ready using Actions class for element with locator: "+locator.toString());
	}
	
	/**
	 * Enters text to an element after provided max time out
	 * @param locator to identify the element
	 * @param keysToSend text to enter
	 * @param timeOutInSeconds maximum time out
	 */
	public void sendKeystWhenReady(By locator, String keysToSend, int timeOutInSeconds) {
		LOGGER.fine("entering: "+keysToSend+" using send keys when ready for element by locator: "+locator.toString());
		WebElement element;
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		element.clear();
		element.sendKeys(keysToSend);
		LOGGER.fine("entered: "+keysToSend+" using send keys when ready for element by locator: "+locator.toString());
	}
	
	/**
	 * Clicks on an element which is bound to be stale
	 * this method saves the element in a variable, clicks on it
	 * Then it looks for the element again since the element got stale and clicks on it
	 * @param locator to identify the element
	 * @param timeOutInSeconds
	 */
	public void clickStaleElement(By locator, int timeOutInSeconds) {
		LOGGER.fine("performing click stale element with locator: "+locator.toString());
		WebElement element = driver.findElement(locator);
		element.click();
		clickWhenReady(locator, timeOutInSeconds);
		LOGGER.fine("performed click stale element with locator: "+locator.toString());
	}
	
	/**
	 * Send keys to an element which is bound to be stale
	 * this method saves the element in a variable, clicks on it
	 * Then it looks for the element again since the element got stale and send keys to it
	 * @param locator to identify the element
	 * @param keysToSend
	 * @param timeOutInSeconds
	 */
	public void sendKeysToStaleElement(By locator, String keysToSend, int timeOutInSeconds) {
		LOGGER.fine("entering: "+keysToSend+" using send keys to stale element with locator: "+locator.toString());
		WebElement element = driver.findElement(locator);
		element.click();
		sendKeystWhenReady(locator, keysToSend, timeOutInSeconds);
		LOGGER.fine("entered: "+keysToSend+" using send keys to stale element with locator: "+locator.toString());
	}
	
	/**
	 * Gets value property of the element element with the provided max time out
	 * @param locator to identify the element
	 * @param timeOutInSeconds maximum time out
	 */
	public String getValue(By locator, int timeOutInSeconds) {
		LOGGER.fine("retreiving value attribute for the element by locator: "+locator.toString());
		WebElement element;
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		String value = element.getAttribute("value");
		LOGGER.fine("retreived value: "+value+" for the element by locator: "+locator.toString());
		return value;
	}
	
	public String getValue(WebElement element, int timeOutInSeconds) {
		LOGGER.fine("retreiving value attribute for the element");
		String value = element.getAttribute("value");
		LOGGER.fine("retreived value: "+value+" for the element");
		return value;
	}
	
	/**
	 * Gets text property of the element element with the provided max time out
	 * @param locator to identify the element
	 * @param timeOutInSeconds maximum time out
	 */
	public String getText(By locator, int timeOutInSeconds) {
		LOGGER.fine("retreiving text attribute for the element by locator: "+locator.toString());
		WebElement element;
		WebDriverWait wait = new WebDriverWait(driver, timeOutInSeconds);
		element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		String text = element.getText();
		LOGGER.fine("retreived text: "+text+" for the element by locator: "+locator.toString());
		return text;
	}
	
	/**
	 * Double clicks on an element
	 * @param locator to identify the element
	 */
	public void doubleClickElement(By locator) {
		LOGGER.fine("performing double click using Actions class for the element by locator: "+locator.toString());
		Actions action = new Actions(driver);
		WebElement element = driver.findElement(locator);
		action.doubleClick(element).perform();
		LOGGER.fine("performed double click using Actions class for the element by locator: "+locator.toString());
	}
	
	/**
	 * Double clicks on an element with default max time out of 60 seconds
	 * @param locator to identify the element
	 */
	public void doubleClickElementWhenReady(By locator) {
		LOGGER.fine("performing double click when ready using Actions class for the element by locator: "+locator.toString());
		WebElement element = driver.findElement(locator);
		WebDriverWait wait = new WebDriverWait(driver, defaultTimeOutInSeconds);
		element = wait.until(ExpectedConditions.elementToBeClickable(locator));
		Actions action = new Actions(driver);
		action.doubleClick(element).perform();
		LOGGER.fine("performed double click when ready using Actions class for the element by locator: "+locator.toString());
	}
	
	/**
	 * Double clicks on an element
	 * @param element to perform action on
	 */
	public void doubleClickElement(WebElement element) {
		LOGGER.fine("performing double click on element");
		Actions action = new Actions(driver);
		action.doubleClick(element).perform();
		LOGGER.fine("performed double click on element");
	}
	
	/**
	 * Returns element by the index, the index starts with 1
	 * adds 2 minutes of fluent wait by default
	 * @param locator to identify the element
	 * @param elementIndex index of the element starting with 1
	 * @return
	 */
	public WebElement getElementByIndex(By locator, int elementIndex) {
		LOGGER.fine("retreiving element by index: "+elementIndex+" for element by locator: "+locator.toString());
		WebElement element = driver.findElements(locator).get(elementIndex);
		WebDriverWait wait = new WebDriverWait(driver, defaultTimeOutInSeconds);
		element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		LOGGER.fine("returning element by index: "+elementIndex+" for element by locator: "+locator.toString());
		return element;
	}
	
	/**
	 * Returns element by index , the index starts with 1 
	 * adds user provided of fluent wait 
	 * @param locator to identify the element
	 * @param elementIndex index of the element starting with 1
	 * @param timeOutInSeconds maximum time out
	 * @return
	 */
	public WebElement getElementByIndex(By locator, int elementIndex, int timeoutInSeconds) {
		LOGGER.fine("retreiving element by index: "+elementIndex+" for element by locator: "+locator.toString());
		WebElement element = driver.findElements(locator).get(elementIndex);
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		LOGGER.fine("returning element by index: "+elementIndex+" for element by locator: "+locator.toString());
		return element;
	}
	
	/**
	 * Returns the frame by index
	 * the index starts with 1
	 * @param frameIndex index of the frame starting with 1
	 * @return
	 */
	public WebElement getFrameByIndex(int frameIndex) {
		LOGGER.fine("retreiving frame by index: "+frameIndex);
		By locator = By.xpath("//iframe");
		WebElement frame = getElementByIndex(locator, frameIndex);
		LOGGER.fine("returning frame by index: "+frameIndex);
		return frame;
	}
	
	public WebElement getLastElementByLocator(By locator, int timeoutInSeconds) {
		LOGGER.fine("retreiving last element by locator: "+locator.toString());
		List<WebElement> elements = driver.findElements(locator);
		int numberOfElemets = elements.size();
		System.out.println("number of elements "+numberOfElemets);
		WebElement element = elements.get(numberOfElemets-1);
		WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
		element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		LOGGER.fine("returning last element by locator: "+locator.toString());
		return element;	
	}
	
	public By getLastElementLocatorByLocator(By locator) {
		LOGGER.fine("retreiving last element locator by locator: "+locator.toString());
		List<WebElement> elements = driver.findElements(locator);
		int numberOfElemets = elements.size();
		String xpath = stringHelper.extractXpathFromLocator(locator);
		xpath = "("+xpath+")["+numberOfElemets+"]";
		LOGGER.fine("returning last element locator by locator: "+locator.toString());
		return By.xpath(xpath);
	}
	
	/**
	 * allows to use Select class to select an option from the menu given its value
	 * @param locator to identify element
	 * @param timeOutInSeconds wait time condition for element presense
	 * @param optionValue the value property of the option
	 */
	public void selectOptionFromSelectMenuUsingValue(By locator, int timeOutInSeconds, String optionValue) {
		boolean isElementPresent = isElementDisplayed(locator, timeOutInSeconds);
		if (isElementPresent == true) {
			Select select = new Select(driver.findElement(locator));
			select.selectByValue(optionValue);
		}else {
			throw new ElementNotVisibleException("element with locator "+locator+" not found");
		}
	}
	
	/**
	 * allows to use Select class to select an option from the menu given its value
	 * @param locator to identify element
	 * @param timeOutInSeconds wait time condition for element presense
	 * @param optionIndex the index of the option to be selected
	 */
	public void selectOptionFromSelectMenuUsingIndex(By locator, int timeOutInSeconds, int optionIndex) {
		boolean isElementPresent = isElementDisplayed(locator, timeOutInSeconds);
		if (isElementPresent == true) {
			Select select = new Select(driver.findElement(locator));
			select.selectByIndex(optionIndex);
		}else {
			throw new ElementNotVisibleException("element with locator "+locator+" not found");
		}
	}
}
