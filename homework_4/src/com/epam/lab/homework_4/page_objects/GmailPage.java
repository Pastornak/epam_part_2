package com.epam.lab.homework_4.page_objects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.lab.homework_4.elements.*;

public class GmailPage extends PageObject{
	
	private static final Logger LOG = Logger.getLogger(GmailPage.class);
	
	@FindBy(xpath="//div[contains(@class, 'T-I J-J5-Ji T-I-KE L3') and @role='button']")
	private Button writeEmailButton;

	@FindBy(name="to")
	private Input emailToInput;
	
	@FindBy(name="subjectbox")
	private Input emailSubjectInput;
	
	@FindBy(xpath="//div[@role='textbox']")
	private Input emailTextInput;
	
	@FindBy(xpath="//div[@role='button' and contains(@class, 'T-I J-J5-Ji aoO T-I-atl L3')]")
	private Button sendEmailButton;
	
	@FindBy(xpath="(//table[@class='F cf zt' and @id])[last()]/tbody")
	private WebElement emailList;
	
	public GmailPage(WebDriver driver){
		super(driver);
		LOG.info("Constructor.");
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
		waitForPageLoading();
	}
	
	public void waitForPageLoading() {
		LOG.info("waitForPageLoading.");
       new WebDriverWait(driver, waitTime)
			.until(ExpectedConditions.urlMatches("^https://mail.google.com/mail/(\\w)*"));
    }

	public void pressWriteEmailButton(){
		LOG.info("pressWriteEmailButton.");
		writeEmailButton.click();
	}
	
	public void fillEmailFields(String to, String subject, String text){
		LOG.info(String.format("fillEmailFields, input: %s, %s, %s.", to, subject, text));
		fillEmailTo(to);
		fillEmailSubject(subject);
		fillEmailText(text);
	}
	
	public void fillEmailTo(String to){
		LOG.info("fillEmailTo, input: " + to);
		emailToInput.type(to);
	}
	
	public void fillEmailSubject(String subject){
		LOG.info("fillEmailSubject, input: " + subject);
		emailSubjectInput.type(subject);
	}
	
	public void fillEmailText(String text){
		LOG.info("fillEmailText, input: " + text);
		emailTextInput.type(text);
	}
	
	public void pressSendEmail(){
		LOG.info("pressSendEmail.");
		sendEmailButton.click();
	}
	
	public void waitForEmailToBeSent(){
		LOG.info("waitForEmailToBeSent.");
		WebElement notification = driver.findElement(By
				.xpath("//div[@class='vh']/span[@class='aT']"));
		waitForElementLoading(notification);
		WebElement checkElement = notification.findElement(By
				.xpath(".//*[@class='bAo']/*[@id='link_undo']"));
		waitForInvisibility(checkElement);
	}
	
	public String getEmailSubject(int emailNumberFromTop){
		LOG.info("getEmailSubject, input: " + emailNumberFromTop);
		WebElement emailInfo = getEmailInfo(emailNumberFromTop);
		Label subjectElement = new Label(emailInfo.findElement(By.xpath(".//div/span/span")));
		String subject = subjectElement.getText();
		LOG.info("getEmailSubject, result: " + subject);
		return subject;
	}
	
	public String getEmailShortText(int emailNumberFromTop){
		LOG.info("getEmailShortText, input: " + emailNumberFromTop);
		WebElement emailInfo = getEmailInfo(emailNumberFromTop);
		Label textElement = new Label(emailInfo.findElement(By.xpath(".//span[@class='y2']")));
		waitForElementLoading(textElement.findElement(By.tagName("span")));
		String text = textElement.getText();
		text = text.replaceFirst("-", "");
		text = text.trim();
		LOG.info("getEmailShortText, result: " + text);
		return text;
	}
	
	public void clickEmailDeleteCheckbox(int emailNumberFromTop){
		LOG.info("clickEmailDeleteCheckbox, input: " + emailNumberFromTop);
		waitForElementLoading(emailList);
		Checkbox deleteCheckboxElement = new Checkbox(emailList.findElement(By.xpath(".//tr[" 
				+ emailNumberFromTop + "]/td[@id][1]/*[@id and @role='checkbox']")));
		deleteCheckboxElement.setChecked(true);
	}
	
	public void clickDeleteCheckboxedEmailsButton(){
		LOG.info("clickDeleteCheckboxedEmailsButton.");
		Button deleteButton = new Button(driver.findElement(By
				.xpath("//*[@act=10 and @role='button']")));
		deleteButton.click();
	}
	
	public boolean isDeleted(){
		LOG.info("isDeleted.");
		WebElement notificationUndoElement = driver.findElement(By
				.xpath("//*[@id='link_undo' and @role='link']"));
		waitForElementLoading(notificationUndoElement);
		// Hardcoded time due to the fact that it's a notification, 
		// so it's not related to the internet speed
		// (for case where implicitWaitTime < notification lifetime)
		waitForInvisibility(notificationUndoElement, 15);
		return true;
	}
	
	private WebElement getEmailInfo(int emailNumberFromTop){
		LOG.info("getEmailInfo, input: " + emailNumberFromTop);
		waitForElementLoading(emailList);
		waitForEmailListToUpdate(emailNumberFromTop);
		return emailList.findElement(By.xpath(".//tr[" 
				+ emailNumberFromTop + "]/td[@id][2]/div/div"));
	}
	
	private void waitForEmailListToUpdate(int emailNumberFromTop){
		LOG.info("waitForEmailListToUpdate, input: " + emailNumberFromTop);
		waitForElementLoading(
				By.xpath(".//tr[" + emailNumberFromTop + "]/td[@class='yX xY ']/div[last()]/span"),
				By.xpath(".//tr[" + emailNumberFromTop + "]/td[@id][2]/div/div/span/span")
				);
	}
}
