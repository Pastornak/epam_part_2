package com.epam.lab.homework_6.business_objects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.epam.lab.homework_6.page_objects.*;

public class GmailBO {

	private static final Logger LOG = Logger.getLogger(GmailBO.class);
	
	private WebDriver driver;

	public GmailBO(WebDriver driver) {
		LOG.info("Constructor");
		driver.get("https://mail.google.com");
		this.driver = driver;
	}

	public void login(String login, String password) {
		LOG.info("login, input: " + login + ", " + password);
		SignInPage page = new SignInPage(driver);
		page.typeEmail(login);
		page.submitEmail();
		page.typePassword(password);
		page.submitPassword();
	}

	public void writeEmail(String to, String subject, String text) {
		LOG.info("writeEmail, input: " + to + ", " + subject + ", " + text);
		GmailPage page = new GmailPage(driver);
		page.pressWriteEmailButton();
		page.fillEmailFields(to, subject, text);
		page.pressSendEmail();
		page.waitForEmailToBeSent();
	}

	public String getSentEmailSubject(int emailNumberFromTop) {
		LOG.info("getSentEmailSubject, input: " + emailNumberFromTop);
		GmailPage sentPage = navigateToSentPage();
		return sentPage.getEmailSubject(emailNumberFromTop);
	}

	public String getSentEmailShortText(int emailNumberFromTop) {
		LOG.info("getSentEmailShortText, input: " + emailNumberFromTop);
		GmailPage sentPage = navigateToSentPage();
		return sentPage.getEmailShortText(emailNumberFromTop);
	}

	public boolean deleteSentEmail(int emailNumberFromTop) {
		LOG.info("deleteSentEmail, input: " + emailNumberFromTop);
		GmailPage sentPage = navigateToSentPage();
		sentPage.clickEmailDeleteCheckbox(emailNumberFromTop);
		sentPage.clickDeleteCheckboxedEmailsButton();
		return sentPage.isCheckboxedEmailDeleted();
	}

	private GmailPage navigateToSentPage() {
		LOG.info("navigateToSentPage.");
		return (new GmailPage(driver)).navigateToSentEmails();
	}
}
