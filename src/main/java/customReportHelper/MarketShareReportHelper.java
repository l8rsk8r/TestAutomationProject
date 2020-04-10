package customReportHelper;

import java.util.logging.Logger;

import utils.Calculator;
import utils.StringHelper;

public class MarketShareReportHelper extends ValidationHtmlReportBase {

	public MarketShareReportHelper(String targetDir) {
		super(targetDir);
	}

	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	Calculator calculator = new Calculator();
	StringHelper stringHelper = new StringHelper();
	
	protected String generateHotelNameOptions(String hotel) {
		String value = hotel.toLowerCase();
		value = value.replace(" ", "-");
		String option = hotel.toUpperCase();
		String row = "<option value=\"#"+value+"-report\">"+option+"</option>"
				+ "<!--hotel-options-->";
		LOGGER.fine("generated options row for html code");
		LOGGER.fine("row: "+row);
		return row;
	}
	
	protected String generateFullReportTableCore(String hotelName, String placement) {
		String head = "";
		String hotelNameIdForOption = hotelName.toLowerCase()+"-report";
		hotelNameIdForOption = hotelNameIdForOption.replace(" ", "-");
		String hotelNameHeading = hotelName.toUpperCase();
		head = readWriteFiles.fileToString(sourceDir+"/full-report-table-core.txt");
		head = head.replace("${hotel-name-id-for-option}", hotelNameIdForOption);
		head = head.replace("${right-or-left-table}", placement);
		head = head.replace("${hotel-name-heading}", hotelNameHeading);
		LOGGER.fine("genetated full report table core");
		LOGGER.fine("head: "+head);
		return head;
	}
	
	protected String generateFullReportRow(String indicator, String mstrValue, String tableuValue) {
		String row = "";
		String matchPercentage;
		if (mstrValue.contains("$") || tableuValue.contains("$")) {
			matchPercentage = "N/A";
		}else {
			matchPercentage = String.valueOf(calculator.findMatchPercentage(mstrValue, tableuValue));
		}
		if (mstrValue.contentEquals(tableuValue)) {
			String[] rowValues = {indicator, mstrValue, tableuValue, matchPercentage, "Pass"};
			row = generateFullReportPassRow(rowValues);
		}else {
			String[] rowValues = {indicator, mstrValue, tableuValue, matchPercentage, "Fail"};
			row = generateFullReportFailRow(rowValues);
		}
		LOGGER.fine("generated full report row for html code");
		LOGGER.fine("row: "+row);
		return row;
	}
	
	protected String generateInncodeValidationTableCore() {
		String tableCore = HtmlReportBases.getHtmlBase(HtmlReportBases.MARKET_SHARE_INNCODE_VALIDATION_TABLE_CORE);
		LOGGER.fine("generated table core for inn code failures");
		LOGGER.fine("table core : "+tableCore);
		return tableCore;
	}
	
	protected String generateInncodeValidationTableRow(String inncode, String indicator, float mstrValue, float tableauValue) {
		String row = "";
		row = "<!-- table-rows -->";
		double minMathPercentageAllowed;
		try {
			 minMathPercentageAllowed = stringHelper.stringToFloat(System.getProperty("minMatchPercentageAllowed"));
		}catch (NullPointerException npe) {
			 minMathPercentageAllowed = 100;
		}
		double matchPercentage = calculator.findMatchPercentage(mstrValue, tableauValue);
		if (matchPercentage < minMathPercentageAllowed) {
			row = 	"<tr>\n" + 
					"\t<td>"+inncode+"</td>\n" + 
					"\t<td>"+indicator+"</td>\n" + 
					"\t<td>"+mstrValue+"</td>\n" + 
					"\t<td>"+tableauValue+"</td>\n" + 
					"\t<td>"+matchPercentage+"</td>\n" + 
					"</tr>\n" +
					"<!-- table-rows -->";
		}
		LOGGER.fine("generated table row for inn code failures");
		LOGGER.fine("table row : "+row);
		return row;
	}
	
	public static void main(String[] args) {
		MarketShareReportHelper mmsrh = new MarketShareReportHelper("");		
		int counter = 1;
		for (int i = 0; i < 4; i++) {
			String placement = "";
			if (counter%2 == 0) {
				placement = "right";
			}else {
				placement = "left";
			}
			System.out.println(mmsrh.generateFullReportTableCore("hilton", placement));
			counter++;	
		}
		String row = mmsrh.generateInncodeValidationTableRow("ADDJD", "testing indi", 5.4f, 7.7f);
		System.out.println(row);
		
	}

}
