package com.epam.lab.homework_4.page_objects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.epam.lab.homework_4.elements.*;
import com.epam.lab.homework_4.readers.PropertiesParser;

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
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
		this.driver = driver;
		waitTime = new PropertiesParser("resources/driver_config.properties").getImplicitWaitTimeProperty();
		LOG.info("Constructor.");
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
	
	public GmailPage submitPassword(){
		LOG.info("submitPassword.");
		submitPasswordButton.click();
		return new GmailPage(driver);
	}
}
