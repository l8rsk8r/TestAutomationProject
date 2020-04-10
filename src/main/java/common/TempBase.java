package common;

import java.lang.reflect.Method;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class TempBase extends BaseTest {
	
	String userDir;
	static protected ExtentHtmlReporter htmlReporter;
	static protected ExtentReports report;
	protected ExtentTest test;
	protected ExtentTest node;

	@BeforeTest (alwaysRun = true)
	@Parameters({"testName"})
	protected void beforeTest(String testName) {
		System.out.println("in before suite");
		userDir = System.getProperty("user.dir");
		htmlReporter = new ExtentHtmlReporter(userDir+"/target/"+testName+".html");
		htmlReporter.setAppendExisting(true);
		htmlReporter.config().setDocumentTitle("TAP");
		htmlReporter.config().setReportName(testName);
		htmlReporter.setAppendExisting(true);
		report = new ExtentReports();
		report.attachReporter(htmlReporter);
		System.out.println("before suite done");
	}
	
	@BeforeMethod (alwaysRun = true)
	protected void beforeMethod(Method method) {
		String testName = method.getName();
		test = report.createTest(testName);
	}
	
	@AfterTest (alwaysRun = true)
	protected void afterTest() {
		report.flush();
	}
}
