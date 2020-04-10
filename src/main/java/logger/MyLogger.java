package logger;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class MyLogger {

    public Logger LOGGER;

    private FileHandler textLogFileHandler;
    private FileHandler htmlLogFileHnadler;
    private SimpleFormatter txtFormatter;
    private Formatter htmlFormatter;
    private String textLogFilePath;
    private String htmlLogFilePath;
    private String userDir;
    
    public MyLogger() {
    	LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    	userDir = System.getProperty("user.dir");
    	textLogFilePath = userDir+"/target/logs.txt";
    	htmlLogFilePath = userDir+"/target/logs.html";
    	setLogLevel();
    	createTextFormatter();
    	createHtmlFormatter();
    }
    
    public void setLogLevel() {
    	LOGGER.setLevel(Level.WARNING);
//    	String logLevel = "warning";
//    	try {
//    		logLevel = System.getProperty("logLevel").toLowerCase();
//    	} catch(NullPointerException npe) {
//    		LOGGER.setLevel(Level.WARNING);
//    	}
//    	switch(logLevel) {
//    	case "info":
//    		LOGGER.setLevel(Level.INFO);
//            break;
//    	case "warning":
//    		LOGGER.setLevel(Level.WARNING);
//            break;
//    	case "off":
//    		LOGGER.setLevel(Level.OFF);
//    		break;
//    	case "fine":
//    		LOGGER.setLevel(Level.FINE);
//    		break;
//    	}
    }
    
    public void createTextFormatter() {
    	txtFormatter = new SimpleFormatter();
    	try {
			textLogFileHandler = new FileHandler(textLogFilePath);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
    	textLogFileHandler.setFormatter(txtFormatter);
    	LOGGER.addHandler(textLogFileHandler);
    	System.out.println("added handler");
    }
    
    public void createHtmlFormatter() {
    	htmlFormatter = new MyHtmlFormatter();
    	try {
			htmlLogFileHnadler = new FileHandler(htmlLogFilePath);
		} catch (SecurityException | IOException e) {
			e.printStackTrace();
		}
    	htmlLogFileHnadler.setFormatter(htmlFormatter);
    	LOGGER.addHandler(htmlLogFileHnadler);
    }
}