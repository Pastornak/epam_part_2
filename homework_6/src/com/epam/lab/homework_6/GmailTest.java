package com.epam.lab.homework_6;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.epam.lab.homework_6.business_objects.GmailBO;
import com.epam.lab.homework_6.readers.*;
import com.epam.lab.homework_6.users_emails.*;
import com.epam.lab.homework_6.utils.Pair;

public class GmailTest {
	
	private int implicitTimeWait;
	private String pathToChromeDriver;
	
	private UserEmailPairs userEmailPairs;
	
	//private WebDriver driver;
	
	@BeforeClass
	public void setUp(){
		PropertiesParser pp = new PropertiesParser("resources/driver_config.properties");
		implicitTimeWait = pp.getImplicitWaitTimeProperty();
		pathToChromeDriver = pp.getChromeDriverPath();
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
	
		userEmailPairs = new UserEmailPairs();
	}

	@BeforeMethod
	public void setUpDriver() {
//		driver = new ChromeDriver();
//		driver.manage().timeouts().implicitlyWait(implicitTimeWait, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void tearDownDriver(){
		//driver.quit();
	}
	
	@DataProvider(name = "user-email", parallel=true)
	public Object[][] provideUserEmail(){
		Pair<User, Email> pair = userEmailPairs.getPair();
		return new Object[][]{
			{pair.getKey(), pair.getValue()}
		};
	}
	
	@Test(dataProvider = "user-email", threadPoolSize = 3, invocationCount = 3)
	public void testGmailSendEmail(User user, Email email){
		String login = user.getLogin();
		String password = user.getPassword();
		
		String emailTo = email.getTo();
		String emailSubject = email.getSubject();
		String emailText = email.getText();
		
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(implicitTimeWait, TimeUnit.SECONDS);
		GmailBO gmailBO = new GmailBO(driver);
		gmailBO.login(login, password);
		gmailBO.writeEmail(emailTo, emailSubject, emailText);
		Assert.assertTrue(gmailBO.getSentEmailSubject(1).equals(emailSubject));
		Assert.assertTrue(emailText.contains(gmailBO.getSentEmailShortText(1)));
		Assert.assertTrue(gmailBO.deleteSentEmail(1));
		driver.quit();
	}
}
