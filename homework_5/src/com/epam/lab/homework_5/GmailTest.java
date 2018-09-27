package com.epam.lab.homework_5;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.epam.lab.homework_5.business_objects.GmailBO;
import com.epam.lab.homework_5.readers.*;

public class GmailTest {
	
	private int implicitTimeWait;
	private String pathToChromeDriver;
	
	private String login;
	private String password;
	
	private String emailTo;
	private String emailSubject;
	private String emailText;
	
	
	private WebDriver driver;
	
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

	@BeforeMethod
	public void setUpDriver(){
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(implicitTimeWait, TimeUnit.SECONDS);
	}
	
	@AfterMethod
	public void tearDownDriver(){
		driver.quit();
	}
	
	@Test
	public void testGmailSendEmail(){
		GmailBO gmailBO = new GmailBO(driver);
		gmailBO.login(login, password);
		gmailBO.writeEmail(emailTo, emailSubject, emailText);
		Assert.assertTrue(gmailBO.getSentEmailSubject(1).equals(emailSubject));
		Assert.assertTrue(emailText.contains(gmailBO.getSentEmailShortText(1)));
		Assert.assertTrue(gmailBO.deleteSentEmail(1));
	}
}
