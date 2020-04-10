package mstr.ui.refactor.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class HomePage {

	protected WebDriver driver;
	
	public HomePage(WebDriver driver) {
		this.driver = driver;
	}
	
	public enum HomePageElements {
		SHARED_REPORTS_LINK,
		MY_REPORTS_LINK,
		MY_SUBSCRIPTIONS_LINK
	}
	
	By sharedReportsLink = By.xpath("//div[text() = 'Shared Reports']");
	By myReportsLink = By.xpath("//div[text() = 'My Reports']");
	By mySubscriptionsLink = By.xpath("//div[text() = 'My Subscriptions']");

	public By getHomePageElements(HomePageElements element) {
		By locator = null;
		switch(element) {
		case SHARED_REPORTS_LINK:
			locator = sharedReportsLink;
			break;
		case MY_REPORTS_LINK:
			locator = myReportsLink;
			break;
		case MY_SUBSCRIPTIONS_LINK:
			locator = mySubscriptionsLink;
			break;
		default:
			throw new NullPointerException();
		}
		return locator;
	}
	
}
