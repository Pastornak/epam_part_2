package com.epam.lab.homework_6;

import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.lab.homework_6.business_objects.GmailBO;
import com.epam.lab.homework_6.business_objects.GoogleBO;
import com.epam.lab.homework_6.drivers.ChromeDriverPool;
import com.epam.lab.homework_6.readers.*;
import com.epam.lab.homework_6.users_emails.*;
import com.epam.lab.homework_6.utils.Pair;

public class GmailTest {
	
	private static final Logger LOG = Logger.getLogger(GmailTest.class);
	
	private String pathToChromeDriver;
	private UserEmailPairs userEmailPairs;
	
	@BeforeClass
	public void setUp(){
		LOG.info("BeforeClass");
		PropertiesParser pp = new PropertiesParser("resources/driver_config.properties");
		pathToChromeDriver = pp.getChromeDriverPath();
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
	
		userEmailPairs = new UserEmailPairs();
	}
	
	@DataProvider(name = "user-email", parallel=true)
	public Object[][] provideUserEmail(){
		LOG.info("DataProvider['user-email']");
		Pair<User, Email> pair = userEmailPairs.getPair();
		LOG.info("Pair: " + pair.toString());
		return new Object[][]{
			{pair.getKey(), pair.getValue()}
		};
	}
	
	@Test(dataProvider = "user-email", threadPoolSize = 3, invocationCount = 5)
	public void testGmailSendEmail(User user, Email email){
		LOG.info("Testing: testGmailSendEmail, input: [" + user.toString() + "; " + email.toString() +"]");
		String login = user.getLogin();
		String password = user.getPassword();
		String emailTo = email.getTo();
		String emailSubject = email.getSubject();
		String emailText = email.getText();
		
		LOG.info("Creating Google Business Object");
		GoogleBO googleBO = new GoogleBO(ChromeDriverPool.getInstance());
		LOG.info("Login into google");
		googleBO.login(login, password);
		LOG.info("Creating Gmail Business Object");
		GmailBO gmailBO = new GmailBO(ChromeDriverPool.getInstance());
		LOG.info("Writing email");
		gmailBO.writeEmail(emailTo, emailSubject, emailText);
		LOG.info("Checking if email subject is the same");
		Assert.assertTrue(gmailBO.getSentEmailSubject(1).equals(emailSubject));
		LOG.info("Checking if email text is the same");
		Assert.assertTrue(emailText.contains(gmailBO.getSentEmailShortText(1)));
		LOG.info("Deleting sent email");
		Assert.assertTrue(gmailBO.deleteSentEmail(1));
	}
	
	@AfterMethod(alwaysRun=true)
	public void cleanUp(){
		LOG.info("AfterMethod, Closing thread's driver");
		ChromeDriverPool.getInstance().quit();
	}
}
