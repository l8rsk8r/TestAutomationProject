package customReportHelper;

import java.util.logging.Logger;

public enum HtmlReportBases {
	
	MARKET_SHARE_INNCODE_VALIDATION_TABLE_CORE(
			"<table class=\"full-report-table\" style=\"width:75%\" align=\"center\">\n" + 
			"    <colgroup>\n" + 
			"        <col style=\"width: 15%\">\n" + 
			"        <col style=\"width: 40%\">\n" + 
			"        <col style=\"width: 15%\">\n" + 
			"        <col style=\"width: 15%\">\n" + 
			"        <col style=\"width: 15%\">\n" + 
			"    </colgroup>\n" + 
			"    <tr>\n" + 
			"        <th>InnCode</th>\n" + 
			"        <th>Indicator</th>\n" + 
			"        <th>MSTR</th>\n" + 
			"        <th>Tableau</th>\n" + 
			"        <th>Match %</th>\n" + 
			"    </tr>\n" + 
			"    <!-- table-rows -->\n" + 
			"</table>"
			);
	
	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private String htmlBase;
	
	private HtmlReportBases(String htmlBase) {
		this.htmlBase = htmlBase;
	}
	
	public static String getHtmlBase(HtmlReportBases base) {
		String htmlBase = base.htmlBase;
		return htmlBase;
	}
	
	public static void main(String[] args) {
		System.out.println(HtmlReportBases.getHtmlBase(MARKET_SHARE_INNCODE_VALIDATION_TABLE_CORE));
	}
}
