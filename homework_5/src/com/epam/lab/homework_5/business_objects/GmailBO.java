package com.epam.lab.homework_5.business_objects;

import org.openqa.selenium.WebDriver;

import com.epam.lab.homework_5.page_objects.*;

public class GmailBO {

	private WebDriver driver;

	public GmailBO(WebDriver driver) {
		driver.get("https://mail.google.com");
		this.driver = driver;
	}

	public void login(String login, String password) {
		SignInPage page = new SignInPage(driver);
		page.typeEmail(login);
		page.submitEmail();
		page.typePassword(password);
		page.submitPassword();
	}

	public void writeEmail(String to, String subject, String text) {
		GmailPage page = new GmailPage(driver);
		page.pressWriteEmailButton();
		page.fillEmailFields(to, subject, text);
		page.pressSendEmail();
		page.waitForEmailToBeSent();
	}

	public String getSentEmailSubject(int emailNumberFromTop) {
		GmailPage sentPage = navigateToSentPage();
		return sentPage.getEmailSubject(emailNumberFromTop);
	}

	public String getSentEmailShortText(int emailNumberFromTop) {
		GmailPage sentPage = navigateToSentPage();
		return sentPage.getEmailShortText(emailNumberFromTop);
	}

	public boolean deleteSentEmail(int emailNumberFromTop) {
		GmailPage sentPage = navigateToSentPage();
		sentPage.clickEmailDeleteCheckbox(emailNumberFromTop);
		sentPage.clickDeleteCheckboxedEmailsButton();
		return sentPage.isCheckboxedEmailDeleted();
	}

	private GmailPage navigateToSentPage() {
		if (!driver.getCurrentUrl().contains("mail.google.com/mail") || !driver.getCurrentUrl().contains("#sent")) {
			driver.get("https://mail.google.com/mail/#sent");
			driver.navigate().refresh();
		}
		return new GmailPage(driver);
	}
}
