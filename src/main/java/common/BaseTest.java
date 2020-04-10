package common;

import java.util.logging.Logger;

import org.testng.annotations.BeforeSuite;

import base.BaseSetup;
import logger.MyLogger;

public class BaseTest extends BaseSetup {

	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);	
	
	@BeforeSuite
	public void setup() {
		MyLogger logger = new MyLogger();
		LOGGER = logger.LOGGER;
		LOGGER.fine("Instantiated logger object for test site in @BeforeSuite");
	}
}
