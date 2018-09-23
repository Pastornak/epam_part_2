package com.epam.lab.homework_3.page_objects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage extends PageObject {

	private static final Logger LOG = Logger.getLogger(SignInPage.class);
	
	@FindBy(id="identifierId")
	private WebElement emailField;
	
	@FindBy(id="identifierNext")
	private WebElement submitEmailButton;
	
	@FindBy(name="password")
	private WebElement passwordField;
	
	@FindBy(id="passwordNext")
	private WebElement submitPasswordButton;
	
	public SignInPage(WebDriver driver){
		super(driver);
		LOG.info("Constructor.");
	}
	
	public void typeEmail(String email){
		LOG.info("typeEmail, input: " + email);
		waitForElementLoading(emailField);
		emailField.clear();
		emailField.sendKeys(email);
	}
	
	public void submitEmail(){
		LOG.info("submitEmail.");
		waitForElementLoading(submitEmailButton);
		submitEmailButton.click();
	}
	
	public void typePassword(String password){
		LOG.info("typePassword, input: " + password);
		waitForElementLoading(passwordField);
		passwordField.clear();
		passwordField.sendKeys(password);
	}
	
	public GmailPage submitPassword(){
		LOG.info("submitPassword.");
		waitForElementLoading(submitPasswordButton);
		submitPasswordButton.click();
		return new GmailPage(driver);
	}
}
