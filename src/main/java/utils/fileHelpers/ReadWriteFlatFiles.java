package utils.fileHelpers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;

public class ReadWriteFlatFiles {	
	
	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public String fileToString(String filePath) {
		String content = null;
		try {
            content = FileUtils.readFileToString(new File(filePath), StandardCharsets.UTF_8);
            LOGGER.fine("Converted file text to a string from file path "+filePath);
        } catch (IOException e) {
        	LOGGER.warning("IOException: Could not convert file text to string");
            e.printStackTrace();
        }
		LOGGER.fine("Converted text in file "+filePath+" to string");
		return content;
	}
	
	public void writeToFile(String filePath, String textToAppend) {
	    FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filePath, true);
			LOGGER.fine("Created a FileWriter at path "+filePath);
		} catch (IOException e) {
			LOGGER.warning("IOException: Could not create FileWriter at path "+filePath);
			e.printStackTrace();
		}
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println(textToAppend);
	    printWriter.close();
	    LOGGER.fine("Added text "+textToAppend+" to the file at path "+filePath);
	}
	
	public void writeToFile(String filePath, int textToAppend) {
	    FileWriter fileWriter = null;
		try {
			fileWriter = new FileWriter(filePath, true);
			LOGGER.fine("Created a FileWriter at path "+filePath);
		} catch (IOException e) {
			LOGGER.warning("IOException: Could not create FileWriter at path "+filePath);
			e.printStackTrace();
		}
	    PrintWriter printWriter = new PrintWriter(fileWriter);
	    printWriter.println(textToAppend);
	    printWriter.close();
	    LOGGER.fine("Added int "+textToAppend+" to the file at path "+filePath);
	}
	
	public static void main(String[] args) throws Exception {
		String path = System.getProperty("user.dir") +"/src/main/resources/validationReport/monthlyMarketShareTotalSummaryBase/styles.css";
		ReadWriteFlatFiles rwf = new ReadWriteFlatFiles();
		System.out.println(rwf.fileToString(path));
		String wpath = System.getProperty("user.dir") +"/target/testingwrite.csv";
		String text1 = "this is line 1";
		String text2 = "this is line 2";
		rwf.writeToFile(wpath, text1);
		rwf.writeToFile(wpath, text2);
		rwf.writeToFile(wpath, 554);
	}

}
