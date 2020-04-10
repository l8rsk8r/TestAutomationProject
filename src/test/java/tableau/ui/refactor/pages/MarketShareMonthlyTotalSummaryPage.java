package tableau.ui.refactor.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MarketShareMonthlyTotalSummaryPage {

	protected WebDriver driver;

	public MarketShareMonthlyTotalSummaryPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public enum PageElements {
		FRAME_CONFIG_CONTAINER_TEXTAREA
	}
	
	By frameConfigContainerTextarea = By.xpath("//textarea[@id='tsConfigContainer']");

	public By getPageElements(PageElements element) {
		By locator = null;
		switch(element) {
		case FRAME_CONFIG_CONTAINER_TEXTAREA:
			locator = frameConfigContainerTextarea;
			break;
		default:
			throw new NullPointerException();
		}
		return locator;
	}
}
