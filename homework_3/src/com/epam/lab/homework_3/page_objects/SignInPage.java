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
	
	public void typeEmailAndSubmit(String email){
		waitForElementLoading(emailField, submitEmailButton);
		emailField.sendKeys(email);
		submitEmailButton.click();
	}
	
	public GmailPage typePasswordAndSubmit(String password){
		waitForElementLoading(passwordField, submitPasswordButton);
		passwordField.sendKeys(password);
		submitPasswordButton.click();
		return new GmailPage(driver);
	}
}
