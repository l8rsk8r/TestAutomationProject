package tests.base;

import base.WebDriverBase;
import common.GuiTestBase;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.LobbyLoginPage;


public class BaseTests extends WebDriverBase{

    private WebDriver driver;
    private WebDriverBase driverBase;
    protected LobbyLoginPage homepage;

    public BaseTests(){
        driverBase = new WebDriverBase();
    }

    @BeforeClass
    public void setup(){
        driver = driverBase.instantiateDriver();
        homepage = new LobbyLoginPage(driver);

    }

    @Test
    public void openGoogle(){
        driver.get("http://www.google.com");
    }

    @AfterClass
    public void tearDown(){
        driver.quit();
    }



}

