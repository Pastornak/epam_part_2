package test.java.com.epam.lab.web;

import main.java.com.epam.lab.web.business_objects.GoogleBO;
import main.java.com.epam.lab.web.drivers.ChromeDriverPool;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class FailTest {

    private static final Logger LOG = Logger.getLogger(FailTest.class);
    private WebDriver driver;

    @BeforeMethod
    public void setUpDriver(){
        LOG.info("BeforeMethod, Creating driver");
        driver = ChromeDriverPool.getNewDriver();
    }

    @Test
    public void testFail(){
        LOG.info("Getting account credentials with wrong username");
        String login = "yurii.test.email@gmail.com";
        String password = "CreatedByYurii_22.09.18";
        String incorrectUsername = "John Doe";
        LOG.info("Creating Google Business Object");
        GoogleBO googleBO = new GoogleBO(driver);
        LOG.info("Navigating to login form");
        googleBO.navigateToLoginForm();
        LOG.info("Logging in Google Account");
        googleBO.login(login, password);
        Assert.assertTrue(googleBO.isLoggedIn(incorrectUsername)
                , "Asserting that the user is logged in with wrong username");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDownDriver(){
        LOG.info("AfterMethod, Closing driver");
        driver.quit();
    }
}
