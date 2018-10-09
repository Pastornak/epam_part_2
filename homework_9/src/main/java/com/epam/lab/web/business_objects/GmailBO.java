package main.java.com.epam.lab.web.business_objects;

import main.java.com.epam.lab.web.page_objects.GmailPage;
import org.openqa.selenium.WebDriver;

import java.util.Objects;

public class GmailBO {
	
	private WebDriver driver;
	private GmailPage page;

	public GmailBO(WebDriver driver) {
		this.driver = driver;
	}

	public void navigateToGmail(){
		page = new GmailPage(driver);
	}

	public void writeEmail(String to, String subject, String text) {
		page.pressWriteEmailButton();
		page.fillEmailFields(to, subject, text);
	}

	public void sendEmail(){
		page.pressSendEmail();
	}

	public boolean isEmailSent(){
		return page.isEmailSent();
	}

	public void waitForEmailToBeSent(){
		page.waitForEmailToBeSent();
	}

	public String getSentEmailSubject(int emailNumberFromTop) {
		page = navigateToSentPage();
		return page.getEmailSubject(emailNumberFromTop);
	}

	public String getSentEmailShortText(int emailNumberFromTop) {
		page = navigateToSentPage();
		return page.getEmailShortText(emailNumberFromTop);
	}

	public void deleteSentEmail(int emailNumberFromTop) {
		page = navigateToSentPage();
		page.clickEmailDeleteCheckbox(emailNumberFromTop);
		page.clickDeleteCheckboxedEmailsButton();
	}

	public boolean isSentEmailDeleted(){
		return page.isCheckboxedEmailDeleted();
	}

	public GmailPage navigateToSentPage() {
		if(Objects.isNull(page)){
			page = new GmailPage(driver);
		}
		return page.navigateToSentEmails();
	}
}
