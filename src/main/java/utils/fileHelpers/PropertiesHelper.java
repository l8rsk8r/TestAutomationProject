package utils.fileHelpers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesHelper {

	private static Properties properties = new Properties();
	private static InputStream inputStream;
	
	public void loadProperties(String fileName) {
		try {
			inputStream = new FileInputStream(fileName);
			properties.load(inputStream);
			inputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getPropertyValue(String property) {
		String propertyValue = null;		
		propertyValue = properties.getProperty(property);
		return propertyValue;
	}
	
	public HashMap<String, String> getAllProperties() {
		HashMap<String, String> propertiesMap = new HashMap<String, String>();
		for (Object propertyKey: properties.keySet()) {
			propertiesMap.put(propertyKey.toString(), properties.getProperty(propertyKey.toString()));
		}
		properties.clear();
		return propertiesMap;
	}
	
	public static void main(String[] args) throws Exception {
		PropertiesHelper pfh = new PropertiesHelper();
		String userDir = System.getProperty("user.dir");
		String filePath = userDir+"/properties/browser.properties";
		pfh.loadProperties(filePath);
		pfh.getAllProperties();
	}
}
