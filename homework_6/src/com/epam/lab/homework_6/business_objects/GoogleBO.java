package com.epam.lab.homework_6.business_objects;

import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;

import com.epam.lab.homework_6.page_objects.SignInPage;

public class GoogleBO {

	private static final Logger LOG = Logger.getLogger(GoogleBO.class);
	
	private final WebDriver driver;
	
	public GoogleBO(WebDriver driver){
		LOG.info("Constructor");
		this.driver = driver;
	}
	
	public void login(String login, String password){
		LOG.info("login, input: " + login + ", " + password);
		SignInPage page = new SignInPage(driver);
		page.typeEmail(login);
		page.submitEmail();
		page.typePassword(password);
		page.submitPassword();
	}
}
