package test.java.com.epam.lab.web;

import main.java.com.epam.lab.web.business_objects.GmailBO;
import main.java.com.epam.lab.web.business_objects.GoogleBO;
import main.java.com.epam.lab.web.drivers.ChromeDriverPool;
import main.java.com.epam.lab.web.readers.PropertiesParser;
import main.java.com.epam.lab.web.users_emails.Email;
import main.java.com.epam.lab.web.users_emails.User;
import main.java.com.epam.lab.web.users_emails.UserEmailPairs;
import main.java.com.epam.lab.web.utils.Pair;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GmailTest {

	private static final Logger LOG = Logger.getLogger(GmailTest.class);

	private String pathToChromeDriver;
	private UserEmailPairs userEmailPairs;

	@BeforeClass
	public void setUp(){
		LOG.info("BeforeClass");
		PropertiesParser pp = new PropertiesParser("src/main/resources/driver_config.properties");
		pathToChromeDriver = pp.getChromeDriverPath();
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver);

		userEmailPairs = new UserEmailPairs();
	}

	@DataProvider(name = "user-email", parallel=true)
	public Object[][] provideUserEmail(){
		return new Object[][]{
				{userEmailPairs.getPair()}
		};
	}

	@Test(dataProvider = "user-email", threadPoolSize = 3, invocationCount = 5)
	public void testGmailSendEmail(Pair<User, Email> pair){
		User user = pair.getKey();
		Email email = pair.getValue();
		LOG.info("Testing: testGmailSendEmail, input: [" + user.toString() + "; " + email.toString() +"]");
		String username = user.getUsername();
		String login = user.getLogin();
		String password = user.getPassword();
		String emailTo = email.getTo();
		String emailSubject = email.getSubject();
		String emailText = email.getText();

		LOG.info("Creating Google Business Object");
		GoogleBO googleBO = new GoogleBO(ChromeDriverPool.getThreadLocalInstance());
		googleBO.navigateToLoginForm();
		LOG.info("Login into google");
		googleBO.login(login, password);
		Assert.assertTrue(googleBO.isLoggedIn(username));
		LOG.info("Creating Gmail Business Object");
		GmailBO gmailBO = new GmailBO(ChromeDriverPool.getThreadLocalInstance());
		gmailBO.navigateToGmail();
		LOG.info("Writing email");
		gmailBO.writeEmail(emailTo, emailSubject, emailText);
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

	@AfterMethod(alwaysRun=true)
	public void cleanUp(){
		LOG.info("AfterMethod, Closing thread's driver");
		ChromeDriverPool.getThreadLocalInstance().quit();
	}
}
