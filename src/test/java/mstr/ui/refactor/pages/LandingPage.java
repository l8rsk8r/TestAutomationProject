package mstr.ui.refactor.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LandingPage {

	protected WebDriver driver;
	
	public LandingPage(WebDriver driver) {
		this.driver = driver;
	}
	
	public enum LandingPageElements {
		MARKET_SHARE_LINK,
		CELP_LINK,
		DIGITAL_AND_MARKETING_LINK,
		REVENUE_MANAGEMENT_LINK,
		REVENUE_MANAGEMENT_QA_LINK,
		DELPHI
	}
	
	By marketShareLink = By.xpath("//a[text() = 'Market Share']");
	By celpLink = By.xpath("//a[text() = 'CELP']");
	By digitalAndMarketingLink = By.xpath("//a[text() = 'Digital & Marketing']");
	By revenueManagementLink = By.xpath("//a[text() = 'Revenue Management']");
	By revenueManagementQaLink = By.xpath("//a[text() = 'Revenue Management QA']");
	By delphiLink = By.xpath("//a[text() = 'Delphi']");

	public By getLandingPageElements(LandingPageElements element) {
		By locator = null;
		switch(element) {
		case MARKET_SHARE_LINK:
			locator = marketShareLink;
			break;
		case CELP_LINK:
			locator = celpLink;
			break;
		case DIGITAL_AND_MARKETING_LINK:
			locator = digitalAndMarketingLink;
			break;
		case REVENUE_MANAGEMENT_LINK:
			locator = revenueManagementLink;
			break;
		case REVENUE_MANAGEMENT_QA_LINK:
			locator = revenueManagementQaLink;
			break;
		case DELPHI:
			locator = delphiLink;
			break;
		default:
			throw new NullPointerException();
		}
		return locator;
	}
}
