package test.java.com.epam.lab.web;

import main.java.com.epam.lab.web.business_objects.GmailBO;
import main.java.com.epam.lab.web.business_objects.GoogleBO;
import main.java.com.epam.lab.web.drivers.ChromeDriverPool;
import main.java.com.epam.lab.web.readers.CSVParser;
import main.java.com.epam.lab.web.readers.PropertiesParser;
import main.java.com.epam.lab.web.users_emails.Email;
import main.java.com.epam.lab.web.users_emails.User;
import main.java.com.epam.lab.web.users_emails.UserEmailPairs;
import main.java.com.epam.lab.web.utils.Pair;
import org.testng.annotations.DataProvider;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class GmailTest {

	private static final Logger LOG = Logger.getLogger(GmailTest.class);

	@BeforeClass
	public void setUp(){
		LOG.info("BeforeClass");
		PropertiesParser pp = new PropertiesParser("src/main/resources/driver_config.properties");
		String pathToChromeDriver = pp.getChromeDriverPath();
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
	}

	@Test(dataProvider = "user-email-csv")
	public void testGmailSendEmail(String username, String login, String password
		, String emailTo, String emailSubject, String emailText){
		LOG.info("Testing: testGmailSendEmail, input: [" + username + ", " + login + ", " + password + ": "
				+ emailTo + ", " + emailSubject + ", " + emailText + "]");

		WebDriver driver = ChromeDriverPool.getNewDriver();
		LOG.info("Creating Google Business Object");
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
		driver.quit();
	}

	@DataProvider(name = "user-email", parallel=true)
	public Object[][] provideUserEmail(){
		UserEmailPairs userEmailPairs = new UserEmailPairs();
		int size = userEmailPairs.getSize();
		Object[][] result = new Object[size][];
		for(int i = 0; i < size; i++){
			Pair<User, Email> pair = userEmailPairs.getPair();
			result[i] = new String[]{pair.getKey().getUsername(), pair.getKey().getLogin(), pair.getKey().getPassword()
					, pair.getValue().getTo(), pair.getValue().getSubject(), pair.getValue().getText()};
		}
		return result;
	}

	@DataProvider(name = "user-email-csv", parallel = true)
	public Object[][] provideUserEmailCSV(){
		String[][] array = CSVParser.parse("src/test/resources/test-data.csv");
		int size = array.length;
		Object[][] result = new Object[size][];
		for(int i = 0; i < size; i++){
			result[i] = new String[]{array[i][0], array[i][1], array[i][2], array[i][3], array[i][4], array[i][5]};
		}
		return result;
	}
}
