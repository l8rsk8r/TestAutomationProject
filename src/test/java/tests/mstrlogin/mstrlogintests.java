package tests.mstrlogin;

import org.testng.annotations.Test;
import pages.LobbyPasswordPage;
import pages.MarketShareSummaryPage;
import pages.MstrDownLoadPage;
import pages.MstrHomePage;
import tests.base.BaseTests;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class mstrlogintests extends BaseTests{
	 
	
	private static final String MARKET_SHARE_REPORT_URL_UAT = "https://mstruat.hilton.com/MicroStrategy/servlet/mstrWeb?Server=MICROINTL03UEUA&"
			+ "Project=Revenue+Management+QA&Port=0&evt=3140&src=mstrWeb.3140&documentID=3AAB381F11E97D88438F0080EF65B039";
	
	 	
	@Test
	public void MarketSummaryMonthlyExportTest() throws InterruptedException {
		homepage.setUsername();
		LobbyPasswordPage lobbypasswordpage = homepage.clickPasswordEnterButton();
		lobbypasswordpage.setpassword();
		MstrHomePage mstrloginpage = lobbypasswordpage.clickLoginEnterButton();
		MarketShareSummaryPage marketsharesummarypage = mstrloginpage.navigatetoURL(MARKET_SHARE_REPORT_URL_UAT); 
		Thread.sleep(5000);
	    marketsharesummarypage.hoverOverMonthlyIndex(); 
		//marketsharesummarypage.clickFilterButtonIfFilterPaneNotOpen(120);
		// Only Needed if not using the latest week
		//marketsharesummarypage.clickWeekOfYearFilter(5);
		//marketsharesummarypage.clickWeekOfYearFilterDropdownButton(5);
		//marketsharesummarypage.selectWeekOfYearCheckboxModified("05JAN20", 5);
		//marketsharesummarypage.clickFilterApply(10);
	    Thread.sleep(5000);
	   	marketsharesummarypage.clickResultTableForWeeklyTotalSummary(20);
	    Thread.sleep(5000);
	   	MstrDownLoadPage mstrdownloadpage = marketsharesummarypage.performDownloadReportAction(); 
		assertTrue(mstrdownloadpage.isExportDisplayed(),"Export Not Displayed");
		assertEquals(mstrdownloadpage.getDocumentText(),"Current status: Document ready, retrieving results...");
		mstrdownloadpage.closeExportWindow();
	}	
}
