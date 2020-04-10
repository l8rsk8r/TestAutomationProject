package tests.mstrlogin;

import org.testng.annotations.Test;
import pages.MarketShareSummaryPage;
import pages.MstrDownLoadPage;
import pages.MstrHomePage;
import tests.base.BaseTestsBypass;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class mstrdirectlogintests extends BaseTestsBypass {

	
	private static final String MARKET_SHARE_REPORT_URL_UAT = "http://microwebl00ueua.hhc.hilton.com:8080/MicroStrategy/servlet/mstrWeb?evt=3140&src=mstrWeb.3140&"
			+ "documentID=3AAB381F11E97D88438F0080EF65B039&Server=MICROINTL03UEUA&Project=Revenue%20Management%20QA&Port=0&share=1"; 	
	
	@Test
	public void MarketSummaryMonthlyExportTest() throws InterruptedException {
		homepage.setUsername();
		homepage.setPassword();
		MstrHomePage mstrloginpage =homepage.clickPasswordEnterButton();
		MarketShareSummaryPage marketsharesummarypage = mstrloginpage.navigatetoURL(MARKET_SHARE_REPORT_URL_UAT); 
		marketsharesummarypage.hoverOverMonthlyIndex(); 
		//marketsharesummarypage.clickFilterButtonIfFilterPaneNotOpen(120);
		// Only Needed if not using the latest week
		//marketsharesummarypage.clickWeekOfYearFilter(5);
		//marketsharesummarypage.clickWeekOfYearFilterDropdownButton(5);
		// TODO: Get rid of default week selection
		//marketsharesummarypage.selectWeekOfYearCheckboxModified("05JAN20", 5);
		//marketsharesummarypage.clickFilterApply(10);
		Thread.sleep(5000);
	   	marketsharesummarypage.clickResultTableForWeeklyTotalSummary(20);
		MstrDownLoadPage mstrdownloadpage = marketsharesummarypage.performDownloadReportAction(); 
		assertTrue(mstrdownloadpage.isExportDisplayed(),"Export Not Displayed");
		assertEquals(mstrdownloadpage.getDocumentText(),"Current status: Document ready, retrieving results...");
		mstrdownloadpage.closeExportWindow();
	}	
}
