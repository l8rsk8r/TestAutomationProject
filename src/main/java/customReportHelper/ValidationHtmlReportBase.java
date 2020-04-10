package customReportHelper;

import java.io.File;
import java.util.logging.Logger;

import utils.fileHelpers.DuplicateAndUpdateFiles;
import utils.fileHelpers.ReadWriteFlatFiles;

/**
 * ValidationHtmlReportBase helps generate the html report for validations
 * @author
 *
 */
public class ValidationHtmlReportBase {

	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	protected String sourceDir;
	protected String targetDir;
	protected static String reportFolder;
	protected static String summaryPage;
	protected static String summaryCsv;
	protected static String fullReportPagePath;
	protected static String failureReportPagePath;
	protected static String fullReportCsv;
	protected static String googleChartsJs;
	DuplicateAndUpdateFiles fileHelper = new DuplicateAndUpdateFiles();
	ReadWriteFlatFiles readWriteFiles = new ReadWriteFlatFiles();
//	DateHelper dateHelper = new DateHelper();
	
	public ValidationHtmlReportBase(String targetDir) {
		String userDir = System.getProperty("user.dir");
//		String timeStamp = dateHelper.getCurrentDateFormatted("yyyyMMddHHmmss");
		sourceDir = userDir + "/src/main/resources/validationReport/htmlReportBase";
		targetDir = userDir + "/target/"+targetDir;
		fileHelper.copyFolder(sourceDir, targetDir);
		deleteExtraFiles(targetDir);
		reportFolder = targetDir;
		summaryPage = reportFolder+"/index.html";
		fullReportPagePath = reportFolder+"/full-report.html";
		googleChartsJs = reportFolder+"/google-charts.js";
		summaryCsv = reportFolder+"/summary-report.csv";
		fullReportCsv = reportFolder+"/full-report.csv";
		failureReportPagePath = reportFolder+"/failures-report.html";
		LOGGER.fine("initiated constructor, set file paths for the target directory");
		LOGGER.fine("reportFolder set to: "+reportFolder);
		LOGGER.fine("summaryPage set to: "+summaryPage);
		LOGGER.fine("fullReportPagePath set to: "+fullReportPagePath);
		LOGGER.fine("googleChartsJs set to: "+googleChartsJs);
		LOGGER.fine("summaryCsv set to: "+summaryCsv);
		LOGGER.fine("fullReportCsv set to: "+fullReportCsv);
		LOGGER.fine("failureReportPagePath set to: "+failureReportPagePath);
	}

	protected void deleteExtraFiles(String targetDir) {
		File file;
		file = new File(targetDir+"/dropdown-menu-core.txt");
		file.delete();
		file = new File(targetDir+"/full-report-table-core.txt");
		file.delete();
		if (targetDir.contains("Incodes")) {
			file = new File(targetDir+"/full-report.html");
			file.delete();
		} else {
			file = new File(targetDir+"/failures-report.html");
			file.delete();
		}
	}
	
	protected String generateFullReportPassRow(String... columnValues) {
		String row = "\t<tr class=\"row-pass\">\r\n";
		for (String value: columnValues) {
			row = row + "\t\t<td>"+value+"</td>\r\n";
		}
		row = row + "\t</tr>\r\n"
				+"${table-rows}";
		LOGGER.fine("generated full report pass row");
		LOGGER.fine(row);
		return row;
	}
	
	protected String generateFullReportFailRow(String... columnValues) {
		String row = "\t<tr class=\"row-fail\">\r\n";
		for (String value: columnValues) {
			row = row + "\t\t<td>"+value+"</td>\r\n";
		}
		row = row + "\t</tr>\r\n"
				+"${table-rows}";
		LOGGER.fine("generated full report fail row");
		LOGGER.fine(row);
		return row;
	}
	
	public static void main(String[] args) {
		ValidationHtmlReportBase v = new ValidationHtmlReportBase("");
		String[] values = {"value1", "value2", "value3"};
		System.out.println(v.generateFullReportPassRow(values));
		System.out.println(v.generateFullReportFailRow(values));
		
	}

}
