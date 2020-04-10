package utils.fileHelpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

import utils.DateHelper;

public class CsvReportReader {
	
	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	protected String inputFilePath;
	protected String tempFilePath;
	String timeStamp;
	DateHelper dateHelper = new DateHelper();
	String content = null;

	public CsvReportReader(String inputFilePath) throws Exception {
		LOGGER.fine("instantiating CsvReportReader constructor");
		this.inputFilePath = inputFilePath;
		timeStamp = dateHelper.getCurrentDateFormatted("MMddyyyyhhmmss");
		String tempFileName = createTempFilePath(inputFilePath)+timeStamp+".csv";
		this.tempFilePath = "target/"+tempFileName;
		LOGGER.fine("created temp csv file path to: "+tempFilePath);
		convertUtf16ToUtf8();
	}
	
	private String createTempFilePath(String inputFilePath) {
		String[] splitFilePath = inputFilePath.split("/");
		LOGGER.fine("split filepath, "+inputFilePath+" to find the file name and extension");
		int splitLength = splitFilePath.length;
		String fileNameWithExtension = splitFilePath[splitLength-1];
		String fileName = fileNameWithExtension.replace(".csv", "");
		LOGGER.fine("returning file name, "+fileName+" from path: "+inputFilePath);
		return fileName;
	}
	
	private void readUtf16File() {
        try {
			content = FileUtils.readFileToString(new File(inputFilePath), StandardCharsets.UTF_16);
			LOGGER.fine("read UTF 16 file ");
			LOGGER.fine(content);
		} catch (IOException e) {
			LOGGER.warning("IOException: could not read file to string");
			e.printStackTrace();
		}
	}
	
	private void writeToTempFile(String fileContent) {
		OutputStream outStream = null;
		try {
			outStream = new FileOutputStream(tempFilePath);
			LOGGER.fine("created out stream to write the following content to file: "+content);
		} catch (FileNotFoundException e) {
			LOGGER.warning("FileNotFoundException: could not create output stream ");
			e.printStackTrace();
		}
	    PrintWriter writer = new PrintWriter(new OutputStreamWriter(outStream));
	    writer.write(fileContent);
	    LOGGER.fine("wrote content to file using output stream");
	    writer.flush();
	    writer.close();
	    LOGGER.fine("flushed and closed writer");
	    LOGGER.fine("wrote content to the temp file");
	}
	
	private void convertUtf16ToUtf8() {	
		LOGGER.fine("enter: convertUtf16ToUtf8");
		readUtf16File();
		writeToTempFile(content);
		LOGGER.fine("success: convertUtf16ToUtf8");
	}
	
	public static void main(String[] args) throws Exception {
		String userDir = System.getProperty("user.dir");
//		CsvReportReader crr = new CsvReportReader("/Users//gitWork/quality_assurance/dna-qa-test-automation/src/test/resources/testFilesWeeklyMarketShareTotalSummary/Total_Summary_-_Weekly_Indexes_crosstab.csv");
//		String value = crr.createTempFilePath("/Users//gitWork/quality_assurance/dna-qa-test-automation/src/test/resources/testFilesWeeklyMarketShareTotalSummary/Total_Summary_-_Weekly_Indexes_crosstab.csv");
		String hiveDataCsvFilePath = userDir+"/src/test/resources/ValidateHiltonBillingDetailsAgainstExcelData/Hilton_Advance_Billing_2019-09-23.csv";
		CsvReportReader crr = new CsvReportReader(hiveDataCsvFilePath);
		System.out.println(crr.tempFilePath);
//		System.out.println(value);
	}
}
