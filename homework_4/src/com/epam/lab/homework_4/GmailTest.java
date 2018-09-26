package com.epam.lab.homework_4;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.epam.lab.homework_4.page_objects.*;
import com.epam.lab.homework_4.readers.*;

public class GmailTest {
	
	private int implicitTimeWait;
	private String pathToChromeDriver;
	
	private String login;
	private String password;
	
	private String emailTo;
	private String emailSubject;
	private String emailText;
	
	@BeforeClass
	public void setUp(){
		PropertiesParser pp = new PropertiesParser("resources/driver_config.properties");
		implicitTimeWait = pp.getImplicitWaitTimeProperty();
		pathToChromeDriver = pp.getChromeDriverPath();
		System.setProperty("webdriver.chrome.driver", pathToChromeDriver);
	
		XMLParserDOM xml = new XMLParserDOM("resources/user.xml");
		login = xml.getAttribute("login");
		password = xml.getAttribute("password");
		xml = new XMLParserDOM("resources/email.xml");
		emailTo = xml.getAttribute("to");
		emailSubject = xml.getAttribute("subject");
		emailText = xml.getAttribute("text");
	}

	@Test
	public void testGmailSendEmail(){
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(implicitTimeWait, TimeUnit.SECONDS);
		
		driver.get("https://mail.google.com");
		SignInPage signInPage = new SignInPage(driver);
		signInPage.typeEmail(login);
		signInPage.submitEmail();
		signInPage.typePassword(password);
		GmailPage gmailInboxPage = signInPage.submitPassword();
		gmailInboxPage.pressWriteEmailButton();
		gmailInboxPage.fillEmailFields(emailTo, emailSubject, emailText);
		gmailInboxPage.pressSendEmail();
		gmailInboxPage.waitForEmailToBeSent();
		
		driver.get("https://mail.google.com/mail/#sent");
		driver.navigate().refresh();
		GmailPage gmailSentPage = new GmailPage(driver);
		gmailSentPage.waitForPageLoading();
		Assert.assertEquals(gmailSentPage.getEmailSubject(1), emailSubject);
		Assert.assertTrue(emailText.contains(gmailSentPage.getEmailShortText(1)));
		
		gmailSentPage.clickEmailDeleteCheckbox(1);
		gmailSentPage.clickDeleteCheckboxedEmailsButton();
		Assert.assertTrue(gmailSentPage.isDeleted());
		driver.quit();
	}
}
