package test.java.com.epam.lab.web;

import main.java.com.epam.lab.web.business_objects.GmailBO;
import main.java.com.epam.lab.web.business_objects.GoogleBO;
import main.java.com.epam.lab.web.drivers.ChromeDriverPool;
import main.java.com.epam.lab.web.readers.PropertiesParser;
import org.apache.log4j.Logger;
import org.easetech.easytest.annotation.DataLoader;
import org.easetech.easytest.annotation.Param;
import org.easetech.easytest.loader.LoaderType;
import org.easetech.easytest.runner.DataDrivenTestRunner;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.WebDriver;

@RunWith(DataDrivenTestRunner.class)
public class GmailTest {

	private static final Logger LOG = Logger.getLogger(GmailTest.class);

	private static String pathToChromeDriver;
	private WebDriver driver;

	@BeforeClass
	public static void setUp(){
		LOG.info("BeforeClass");
		PropertiesParser pp = new PropertiesParser("src/main/resources/driver_config.properties");
		pathToChromeDriver = pp.getChromeDriverPath();
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
	}

	@Test
	@DataLoader(filePaths={"test-data.xls"}, loaderType=LoaderType.EXCEL)
	public void testGmailSendEmail(@Param(name="username")String username, @Param(name="login")String login
			, @Param(name="password")String password, @Param(name="to")String emailTo
			, @Param(name="subject")String emailSubject, @Param(name="text")String emailText){

		LOG.info("Testing: testGmailSendEmail, input: [" + username + ", " + login + ", " + password + ": "
				+ emailTo + ", " + emailSubject + ", " + emailSubject + "]");
		LOG.info("Creating Google Business Object");
		driver = ChromeDriverPool.getNewDriver();
		GoogleBO googleBO = new GoogleBO(driver);
		googleBO.navigateToLoginForm();
		LOG.info("Login into google");
		googleBO.login(login, password);
		Assert.assertTrue(googleBO.isLoggedIn(username));
		LOG.info("Creating Gmail Business Object");
		GmailBO gmailBO = new GmailBO(driver);
		gmailBO.navigateToGmail();
		LOG.info("Writing email");
		gmailBO.writeEmail(emailTo, emailSubject, emailText);
		gmailBO.sendEmail();
		Assert.assertTrue(gmailBO.isEmailSent());
		gmailBO.navigateToSentPage();
		LOG.info("Checking if email subject is the same");
		Assert.assertEquals(gmailBO.getSentEmailSubject(1), emailSubject);
		LOG.info("Checking if email text is the same");
		Assert.assertTrue(emailText.contains(gmailBO.getSentEmailShortText(1)));
		LOG.info("Deleting sent email");
		gmailBO.deleteSentEmail(1);
		Assert.assertTrue(gmailBO.isSentEmailDeleted());
	}

	@After
	public void cleanUp(){
		LOG.info("AfterMethod, Closing thread's driver");
		driver.quit();
	}
}
