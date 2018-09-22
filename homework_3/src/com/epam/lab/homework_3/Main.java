package com.epam.lab.homework_3;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.epam.lab.homework_3.page_objects.GmailPage;
import com.epam.lab.homework_3.page_objects.SignInPage;
import com.epam.lab.homework_3.readers.PropertiesParser;
import com.epam.lab.homework_3.readers.XMLParserDOM;

public class Main {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		driver.get("https://mail.google.com");
		SignInPage signInPage = new SignInPage(driver);
		signInPage.typeEmailAndSubmit("yurapaster@gmail.com");
		GmailPage gmailInboxPage = signInPage.typePasswordAndSubmit("WhenItComesToSun_14<88");
		//GmailPage gmailPage = new GmailPage(driver);
//		gmailInboxPage.pressWriteEmailButton();
//		gmailInboxPage.fillEmailFields(emailTo, emailSubject, emailText);
//		gmailInboxPage.pressSendEmail();
		
		//GmailPage gmailSentPage = gmailInboxPage.navigateToSentPage();
		driver.get("https://mail.google.com/mail/u/0/#sent");
		GmailPage gmailSentPage = new GmailPage(driver);
		gmailSentPage.waitForPageLoading();
		System.out.println(gmailSentPage.getEmailSubject(1));
		System.out.println(gmailSentPage.getEmailShortText(1));
	}

}
