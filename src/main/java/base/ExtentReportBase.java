package base;

import java.lang.reflect.Method;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import utils.StringHelper;

public class ExtentReportBase extends BaseSetup {
	
	protected static ExtentHtmlReporter htmlReporter;
	protected static ExtentReports report;
	protected ExtentTest test;
	protected ExtentTest node;
	StringHelper stringHelper = new StringHelper();

	@BeforeTest (alwaysRun = true)
	@Parameters({"testName"})
	protected void beforeTest(String testName) {
		System.out.println("in before suite");
		userDir = System.getProperty("user.dir");
		htmlReporter = new ExtentHtmlReporter(userDir+"/target/"+testName+".html");
		htmlReporter.setAppendExisting(true);
		htmlReporter.config().setDocumentTitle("DNA - TAP");
		htmlReporter.config().setReportName(testName);
		htmlReporter.setAppendExisting(true);
		report = new ExtentReports();
		report.attachReporter(htmlReporter);
	}
	
	protected void createTestForMethod(Method method) {
		String testName = method.getName();
		testName = stringHelper.splitCamelCaseString(testName);
		test = report.createTest(testName);
	}
	
	protected void createTestForClass() {
		String className = this.getClass().getSimpleName();
		className = stringHelper.splitCamelCaseString(className);
		test = report.createTest(className);
	}
	
	@AfterTest (alwaysRun = true)
	protected void afterTest() {
		report.flush();
	}
}
