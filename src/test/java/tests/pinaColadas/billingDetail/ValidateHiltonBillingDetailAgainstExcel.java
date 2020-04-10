package tests.pinaColadas.billingDetail;

import base.Properties;
import common.GuiTestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import utils.StringHelper;
import utils.fileHelpers.ReadWriteFlatFiles;
import webdriver.DriverHelper;
import webdriver.ElementHelper;

import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Stream;

public class ValidateHiltonBillingDetailAgainstExcel extends GuiTestBase{

	protected static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private static String URL;
	ArrayList<String> staticInnCodes = new ArrayList<String>();
	private static final int numberOfRandomInnCodes = 1;
	private static String hiveDataCsvFilePath = "";
	/*
	 * MSTR: On Q login locators
	 */
	private By onqUsernameTextbox = By.xpath("//input[@id='Uid']");
	private By onqPasswordTextbox = By.xpath("//input[@id='Pwd']");
	private By onqLoginButton = By.xpath("//input[@value='Login']");
	/*
	 * MSTR: main locators
	 */
	private By digitalAndMarketingFolderLink = By.xpath("//a[text()='Digital & Marketing']");
	private By sharedReportsFolderLink = By.xpath("//a[text()='Shared Reports']");
	private By hiltonAdvanceBillingFolderLink = By.xpath("//a[text()='Hilton Advance Billing']");
	private By hiltonAdvanceBillingDetailFolderLink = By.xpath("//a[text()='Hilton Advance Billing Detail']");
	/*
	 * MSTR: Hilton Billing Detail Report Main Page
	 */
	private By hamburgerOptions = By.xpath("//div[@class='mstrHamburger path']");
	private By exportOption = By.xpath("//span[text()='Export']");
	private By htmlOption = By.xpath("//span[text()='HTML']");
	private By innCodeSelect = By.xpath("//span[text()='InnCode:']//select");
	/*
	 * MSTR: Download Html Page 
	 */
	private By mainResultTable = By.xpath("(//table[contains(@summary,'requested report results')])[1]");
	
//	OnqLoginPageSteps onqLoginPageSteps;
	DriverHelper driverHelper;
	ElementHelper elementHelper;
	ReadWriteFlatFiles readWriteFiles = new ReadWriteFlatFiles();
	StringHelper stringHelper = new StringHelper();
	ArrayList<String> innCodes = new ArrayList<String>();
	String userDir;
	String dataFile;
	InputStream mstrReader;
	BufferedReader mstrBufferedReader;
	InputStream hiveFileReader;
	BufferedReader hiveFileBufferedReader;
	private static String reprotFilePath;
	
	private ValidateHiltonBillingDetailAgainstExcel() throws FileNotFoundException {
		URL = Properties.environmentProperties.get("mstrServerBaseUrl");
		System.out.println(URL);
		userDir = System.getProperty("user.dir");
		dataFile = userDir+"/target/dataFile.csv";
		LOGGER.info("path for the data file which would include all the data from MSTR is set to location : "+dataFile);
		hiveDataCsvFilePath = userDir+"/src/test/resources/ValidateHiltonBillingDetailsAgainstExcelData/Hilton_Advance_Billing_2019-09-23.csv";
		reprotFilePath = userDir+"/target/report.csv";
		LOGGER.info("path for the report file which would include all the results is set to : "+reprotFilePath);
		addToStaticInnCodes();
	}
	
	@BeforeClass
	private void beforeClass() {
//		onqLoginPageSteps = new OnqLoginPageSteps(driver);
		driverHelper = new DriverHelper(driver);
		elementHelper = new ElementHelper(driver);
		LOGGER.info("instancialed local before class to set the drivers to respective classes");
	}
	
	@Test (priority = 0)
	private void loginToOnq() throws InterruptedException {
		LOGGER.info("logging into OnQ");
		driverHelper.navigateTo(URL);
		elementHelper.sendKeystWhenReady(onqUsernameTextbox, Properties.environmentProperties.get("mstrServiceAccountUsername"), 5);
		elementHelper.sendKeystWhenReady(onqPasswordTextbox, Properties.environmentProperties.get("mstrServiceAccountPassword"), 5);
		elementHelper.clickWhenReady(onqLoginButton, 5);

//		onqLoginPageSteps.loginToOnQ();
	}
	
	@Test (priority = 1)
	private void goToHiltonAdvanceBillingDetailDocument() throws InterruptedException {
		LOGGER.info("goToHiltonAdvanceBillingDetailDocument");
//		driverHelper.navigateTo(URL);
		elementHelper.clickUsingJavaScriptExecutor(digitalAndMarketingFolderLink);
		elementHelper.clickUsingJavaScriptExecutor(sharedReportsFolderLink);
		elementHelper.clickUsingJavaScriptExecutor(hiltonAdvanceBillingFolderLink);
		elementHelper.clickUsingJavaScriptExecutor(hiltonAdvanceBillingDetailFolderLink);
		driverHelper.waitForElement(hamburgerOptions, 300);
	}
	
	@Test (priority = 2)
	private void downloadExcelFile() throws AWTException {
		LOGGER.info("downloadExcelFile");
		WebElement innCodesSelectElement = driver.findElement(innCodeSelect);
		List<WebElement> innCodeOptions = innCodesSelectElement.findElements(By.tagName("option"));
		for (WebElement innCodeOption: innCodeOptions) {
			String value = innCodeOption.getAttribute("value");
			innCodes.add(value);
		}
		String[] randomInnCodes = new String[numberOfRandomInnCodes];
		int randomInnCodesCount = 0;
		while(randomInnCodesCount < numberOfRandomInnCodes) {
			int randomInnCodeIndex = stringHelper.randomNumber(0, innCodeOptions.size() - 1);
			String innCodeForIndex = innCodes.get(randomInnCodeIndex);
			for (int i = 0; i < staticInnCodes.size(); i++) {
				if (!staticInnCodes.get(i).equalsIgnoreCase(innCodeForIndex)) {
					randomInnCodes[randomInnCodesCount] = innCodeForIndex;
					innCodes.remove(innCodeForIndex);
					randomInnCodesCount++;
					break;
				}
			}			
		}
		for (String randomInnCode: randomInnCodes) {
			getData(randomInnCode);
		}
		for (String staticInnCode: staticInnCodes) {
			getData(staticInnCode);
		}
	}
	
	@Test (priority = 3)
	public void validateData() throws IOException {
		LOGGER.info("validateData");
		readWriteFiles.writeToFile(reprotFilePath, "Confirmation Number"+"|"+"MSTR InnCode"+"|"+"Hive InnCode"+"|"+"MSTR Money Revenue"+"|"+"Hive Money Revenue");
		Stream<String> dataStream = Files.lines(Paths.get(dataFile));
		dataStream.parallel().forEach(s -> {
			try {
				findAndCompareDataFromMstrToHive(s);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		dataStream.close();
	}
	
	private void addToStaticInnCodes() {
		LOGGER.info("addToStaticInnCodes");
		staticInnCodes.add("hCHIEE;8CEB859F41E89C2AB6EC6FA2B656DC4D");
		staticInnCodes.add("hMYRKP;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hAGSDT;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hABQEM;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hABEAW;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hBCWB;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hACTWH;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hABQDU;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hABECV;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hABGVA;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hATLPG;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hATLMW;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hABQAN;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hALBUU;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hBOSFR;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hATLWA;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hSFOFH;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hIAGAP;8CEB859F41E89C2AB6EC6FA2B656DC4D");
//		staticInnCodes.add("hYYZAP;8CEB859F41E89C2AB6EC6FA2B656DC4D");
	}

	private void getData(String innCode) {
		LOGGER.info("getData for innCode: "+innCode);
		String innCodeValue = innCode.split(";")[0];
		innCodeValue = innCodeValue.substring(1);
		elementHelper.selectOptionFromSelectMenuUsingValue(innCodeSelect, 10, innCode);
		elementHelper.clickUsingJavaScriptExecutor(hamburgerOptions);
		elementHelper.clickUsingJavaScriptExecutor(exportOption);
		elementHelper.clickUsingJavaScriptExecutor(htmlOption);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		WebElement mainResultTableElement = driver.findElement(mainResultTable);
		List<WebElement> allTableRowsElements = mainResultTableElement.findElements(By.tagName("tr"));
		for (int i = 0; i < allTableRowsElements.size(); i++) {
			if (i != 0) {
				List<WebElement> allRowCellsElements = allTableRowsElements.get(i).findElements(By.tagName("td"));
				String dataRow = innCodeValue+"|";
				for (WebElement cell: allRowCellsElements) {
					String cellValue = cell.getText();
					if (cellValue.contains("/")) {
						cellValue = convertDateFromMstrReport(cellValue);
					} else if (cellValue.contains("$")) {
						cellValue = cellValue.replace("$", "");
					}
					dataRow = dataRow+cellValue+"|";
				}
				dataRow = stringHelper.removeLastCharacters(dataRow, 3);
				readWriteFiles.writeToFile(dataFile, dataRow);
			}
		}
		driver.close();
		ArrayList<String> tabs1 = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs1.get(0));
	}
	
	private String convertDateFromMstrReport(String str) {
		LOGGER.info("convertDateFromMstrReport for date: "+str);
		String[] split = str.split("/");
		String convertedStr = split[2]+"-"+split[0]+"-"+split[1];
		LOGGER.info("converted date: "+convertedStr);
		return convertedStr;
	}
	
	private void findAndCompareDataFromMstrToHive(String mstrLine) throws IOException {
		LOGGER.info("findAndCompareDataFromMstrToHive for line: "+mstrLine);
		String[] mstrDataSet = mstrLine.split("\\|");
		String mstrDataInnCode = mstrDataSet[0];
		String mstrConfirmationNumber = mstrDataSet[1];
		String mstrMoneyRevenue = mstrDataSet[6].replace(",", "");
		hiveFileReader = new FileInputStream(hiveDataCsvFilePath);
		hiveFileBufferedReader = new BufferedReader(new InputStreamReader(hiveFileReader));
		String hiveLine = hiveFileBufferedReader.readLine();
		while ((hiveLine = hiveFileBufferedReader.readLine()) != null) {
			String[] hiveDataSet = hiveLine.split("\\|");
			String hiveDataInnCode = hiveDataSet[0];
			String hiveConfirmationNumber = hiveDataSet[2];
			String hiveMoneyRevenue = hiveDataSet[12];
			if(mstrConfirmationNumber.equalsIgnoreCase(hiveConfirmationNumber)) {
				if (!mstrMoneyRevenue.equalsIgnoreCase(hiveMoneyRevenue) || !mstrDataInnCode.equalsIgnoreCase(hiveDataInnCode)) {
					String failureRow = mstrConfirmationNumber+"|"+mstrDataInnCode+"|"+hiveDataInnCode+"|"+mstrMoneyRevenue+"|"+hiveMoneyRevenue;
					readWriteFiles.writeToFile(reprotFilePath, failureRow);
				}
				break;
			}
		}	
	}
	
	public static void main(String[] args) throws IOException {
		ValidateHiltonBillingDetailAgainstExcel a = new ValidateHiltonBillingDetailAgainstExcel();
		a.validateData();
		try {
			a.downloadExcelFile();
		} catch (AWTException e) {
			e.printStackTrace();
		}
	}
	
}
