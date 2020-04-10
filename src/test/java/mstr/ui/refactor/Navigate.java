package mstr.ui.refactor;

import org.openqa.selenium.WebDriver;

public class Navigate {

	protected WebDriver driver;
	private static final String OLD_BASE_URL = "http://microwebl00ueua.hhc.hilton.com:8080/MicroStrategy/servlet/mstrWeb";
	private static final String MSTR_URL = "https://mstr.hilton.com/MicroStrategy/servlet/mstrWeb";
	private static final String MARKET_SHARE_REPORT_URL = "https:/mstr.hilton.com/MicroStrategy/servlet/mstrWeb?evt=3140&"
			+ "src=mstrWeb.3140&documentID=3AAB381F11E97D88438F0080EF65B039&Server=MICROINTL02UEPC&Project=Revenue%20Manage"
			+ "ment&Port=0&share=1";
	
	private static final String MARKET_SHARE_REPORT_URL_UAT = "https://mstruat.hilton.com/MicroStrategy/servlet/mstrWeb?Server=MICROINTL03UEUA&"
			+ "Project=Revenue+Management+QA&Port=0&evt=3140&src=mstrWeb.3140&documentID=3AAB381F11E97D88438F0080EF65B039";


	public Navigate(WebDriver driver) {
		this.driver = driver;
	}

	public void toOldMStrUrl() {
		driver.get(OLD_BASE_URL);
	}
	
	public void toMstrUrl() {
		driver.get(MSTR_URL);
	}
	
	public void toMstrMarketShareReport() {
		driver.get(MARKET_SHARE_REPORT_URL);
	}
}


