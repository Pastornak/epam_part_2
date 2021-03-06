package com.epam.lab.homework_6.page_objects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.lab.homework_6.elements.*;

public class SignInPage extends PageObject {

	private static final Logger LOG = Logger.getLogger(SignInPage.class);
	
	@FindBy(id="identifierId")
	private Input emailInput;
	
	@FindBy(id="identifierNext")
	private Button submitEmailButton;
	
	@FindBy(name="password")
	private Input passwordInput;
	
	@FindBy(id="passwordNext")
	private Button submitPasswordButton;
	
	public SignInPage(WebDriver driver){
		super(driver);
		LOG.info("Constructor.");
		driver.get("https://accounts.google.com/ServiceLogin");
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
	}
	
	public void typeEmail(String email){
		LOG.info("typeEmail, input: " + email);
		emailInput.type(email);
	}
	
	public void submitEmail(){
		LOG.info("submitEmail.");
		submitEmailButton.click();
	}
	
	public void typePassword(String password){
		LOG.info("typePassword, input: " + password);
		waitForElementLoading(passwordInput.getElement());
		passwordInput.type(password);
	}
	
	public void submitPassword(){
		LOG.info("submitPassword.");
		waitForElementLoading(submitPasswordButton.getElement());
		submitPasswordButton.click();
		waitForURLToContain("https://myaccount.google.com");
	}
}
