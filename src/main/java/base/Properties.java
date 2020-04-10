package base;

import java.util.HashMap;

import utils.fileHelpers.PropertiesHelper;


public class Properties {

	public static HashMap<String, String> browserProperties = new HashMap<String, String>();
	public static HashMap<String, String> environmentProperties = new HashMap<String, String>();
	public static HashMap<String, String> sauceProperties = new HashMap<String, String>();
	
	public void initializeProperties() {
		String userDir = System.getProperty("user.dir");
		String basePath = userDir + "/properties";
		String browserPropertiesFilePath = basePath + "/browser.properties";
		String env = System.getProperty("env");
		String environmentPropertiesFilePath = basePath + "/" + env + ".properties";
		String saucePropertiesFilePath = basePath + "/sauceLabs.properties";
		PropertiesHelper propertiesHelper = new PropertiesHelper();
		propertiesHelper.loadProperties(browserPropertiesFilePath);
		browserProperties = propertiesHelper.getAllProperties();
		propertiesHelper.loadProperties(environmentPropertiesFilePath);
		environmentProperties = propertiesHelper.getAllProperties();
		propertiesHelper.loadProperties(saucePropertiesFilePath);
		sauceProperties = propertiesHelper.getAllProperties();
	}
	
	public static void main(String[] args) {
		Properties p = new Properties();
		p.initializeProperties();
		for (String key: Properties.browserProperties.keySet()) {
			System.out.println(key+"\t"+Properties.browserProperties.get(key));
		}
		System.out.println("***********");
		for (String key: Properties.environmentProperties.keySet()) {
			System.out.println(key+"\t"+Properties.environmentProperties.get(key));
		}
		System.out.println("***********");

		for (String key: Properties.sauceProperties.keySet()) {
			System.out.println(key+"\t"+Properties.sauceProperties.get(key));
		}
	}
}
