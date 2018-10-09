package test.java.com.epam.lab.web;

import main.java.com.epam.lab.web.business_objects.GmailBO;
import main.java.com.epam.lab.web.business_objects.GoogleBO;
import main.java.com.epam.lab.web.drivers.ChromeDriverPool;
import main.java.com.epam.lab.web.readers.CSVParser;
import main.java.com.epam.lab.web.users_emails.Email;
import main.java.com.epam.lab.web.users_emails.User;
import main.java.com.epam.lab.web.users_emails.UserEmailPairs;
import main.java.com.epam.lab.web.utils.Pair;
import org.testng.annotations.DataProvider;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GmailTest {

	private static final Logger LOG = Logger.getLogger(GmailTest.class);

	@Test(dataProvider = "user-email-csv")
	public void testGmailSendEmail(String username, String login, String password
		, String emailTo, String emailSubject, String emailText){
		LOG.info("Testing: testGmailSendEmail, input: [" + username + ", " + login + ", " + password + ": "
				+ emailTo + ", " + emailSubject + ", " + emailText + "]");
		LOG.info("Creating chromedriver");
		WebDriver driver = ChromeDriverPool.getNewDriver();
		LOG.info("Creating Google Business Object");
		GoogleBO googleBO = new GoogleBO(driver);
		LOG.info("Navigating to google login form");
		googleBO.navigateToLoginForm();
		LOG.info("Login into google, input: " + login + ", " + password);
		googleBO.login(login, password);
		LOG.info("Asserting that the right user is logged in [by username: " + username + "]");
		Assert.assertTrue(googleBO.isLoggedIn(username));
		LOG.info("Creating Gmail Business Object");
		GmailBO gmailBO = new GmailBO(driver);
		LOG.info("Navigating to gmail");
		gmailBO.navigateToGmail();
		LOG.info("Writing email, fields: " + emailTo + ", " + emailSubject + ", " + emailText);
		gmailBO.writeEmail(emailTo, emailSubject, emailText);
		LOG.info("Sending email");
		gmailBO.sendEmail();
		LOG.info("Asserting that email was sent");
		Assert.assertTrue(gmailBO.isEmailSent());
		LOG.info("Navigating to sent emails page");
		gmailBO.navigateToSentPage();
		LOG.info("Asserting that email subject is the same [subject: " + emailSubject + "]");
		Assert.assertEquals(gmailBO.getSentEmailSubject(1), emailSubject);
		LOG.info("Asserting that email text is the same [text: " + emailText + "]");
		Assert.assertTrue(emailText.contains(gmailBO.getSentEmailShortText(1)));
		LOG.info("Deleting sent email");
		gmailBO.deleteSentEmail(1);
		LOG.info("Asserting that email was deleted");
		Assert.assertTrue(gmailBO.isSentEmailDeleted());
		LOG.info("Closing driver");
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

	@DataProvider(name = "user-email-csv", parallel=true)
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
