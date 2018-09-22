package com.epam.lab.homework_3.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class GmailPage extends PageObject{
	
	@FindBy(xpath="//div[contains(@class, 'T-I J-J5-Ji T-I-KE L3') and @role='button']")
	private WebElement writeEmailButton;

	@FindBy(name="to")
	private WebElement emailToField;
	
	@FindBy(name="subjectbox")
	private WebElement emailSubjectField;
	
	@FindBy(xpath="//div[@role='textbox']")
	private WebElement emailTextField;
	
	@FindBy(xpath="//div[@role='button' and contains(@class, 'T-I J-J5-Ji aoO T-I-atl L3')]")
	private WebElement sendEmailButton;
	
	@FindBy(xpath="(//table[@class='F cf zt' and @id])[last()]/tbody")
	private WebElement emailList;
	
	public GmailPage(WebDriver driver){
		super(driver);
		waitForPageLoading();
	}
	
	public void waitForPageLoading() throws Error {
        // Initial loading, called when creating the page object to make sure that the page is loaded to a state where it is ready to interact with us, in our case it means that button is present in DOM and visible.
		new WebDriverWait(driver, 10)
			.until(ExpectedConditions.urlMatches("^https://mail.google.com/mail/u/0/(\\w)*"));
		waitForElementLoading(writeEmailButton, emailList);
    }

	public void pressWriteEmailButton(){
		writeEmailButton.click();
	}
	
	public void fillEmailFields(String to, String subject, String text){
		fillEmailTo(to);
		fillEmailSubject(subject);
		fillEmailText(text);
	}
	
	public void fillEmailTo(String to){
		emailToField.clear();
		emailToField.sendKeys(to);
	}
	
	public void fillEmailSubject(String subject){
		emailSubjectField.clear();
		emailSubjectField.sendKeys(subject);
	}
	
	public void fillEmailText(String text){
		emailTextField.clear();
		emailTextField.sendKeys(text);
	}
	
	public void pressSendEmail(){
		sendEmailButton.click();
	}
	
	public void waitForEmailToBeSent(){
		WebElement notification = driver.findElement(By
				.xpath("//div[@class='vh']/span[@class='aT']"));
		waitForElementLoading(notification);
		WebElement checkElement = notification.findElement(By
				.xpath(".//*[@class='bAo']/*[@id='link_undo']"));
		new WebDriverWait(driver, 15).until(ExpectedConditions.invisibilityOf(checkElement));
	}
	
	public String getEmailSubject(int emailNumberFromTop){
		WebElement emailInfo = getEmailInfo(emailNumberFromTop);
		WebElement subjectElement = emailInfo.findElement(By.xpath(".//div/span/span"));
		waitForElementLoading(subjectElement);
		String subject = subjectElement.getText();
		return subject;
	}
	
	public String getEmailShortText(int emailNumberFromTop){
		WebElement emailInfo = getEmailInfo(emailNumberFromTop);
		WebElement textElement = emailInfo.findElement(By.xpath(".//span[@class='y2']"));
		waitForElementLoading(textElement.findElement(By.tagName("span")));
		String text = textElement.getAttribute("textContent");
		// Deletes " - " at the beginning
		text = text.substring(3);
		return text;
	}
	
	private WebElement getEmailInfo(int emailNumberFromTop){
		waitForElementLoading(emailList);
		waitForEmailListToUpdate(emailNumberFromTop);
		return emailList.findElement(By.xpath(".//tr[" 
				+ emailNumberFromTop + "]/td[@id][2]/div/div"));
	}
	
	private void waitForEmailListToUpdate(int emailNumberFromTop){
//		WebElement recieverField = emailList.findElement(By.xpath(".//tr[" 
//				+ emailNumberFromTop + "]/td[@class='yX xY ']/div[last()]/span"));
//		WebElement shortTextField = emailList.findElement(By.xpath(".//tr[" 
//				+ emailNumberFromTop + "]/td[@id][2]/div/div/span/span"));
//		isElementLoaded(recieverField, shortTextField);
		waitForElementLoading(
				By.xpath(".//tr[" + emailNumberFromTop + "]/td[@class='yX xY ']/div[last()]/span"),
				By.xpath(".//tr[" + emailNumberFromTop + "]/td[@id][2]/div/div/span/span")
				);
	}
}
