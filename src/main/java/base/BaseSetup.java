package base;

import java.util.logging.Logger;

public class BaseSetup {

	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);	
	protected String userDir;
	
	public BaseSetup() {
		userDir = System.getProperty("user.dir");
		Properties properties = new Properties();
		properties.initializeProperties();
	}
}
