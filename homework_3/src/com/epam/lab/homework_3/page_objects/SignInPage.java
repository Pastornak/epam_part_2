package com.epam.lab.homework_3.page_objects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class SignInPage extends PageObject {

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
	}
	
	public void typeEmail(String email){
		waitForElementLoading(emailField);
		emailField.clear();
		emailField.sendKeys(email);
	}
	
	public void submitEmail(){
		waitForElementLoading(submitEmailButton);
		submitEmailButton.click();
	}
	
	public void typePassword(String password){
		waitForElementLoading(passwordField);
		passwordField.clear();
		passwordField.sendKeys(password);
	}
	
	public GmailPage submitPassword(){
		waitForElementLoading(submitPasswordButton);
		submitPasswordButton.click();
		return new GmailPage(driver);
	}
}
