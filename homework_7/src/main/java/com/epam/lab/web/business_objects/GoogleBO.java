package main.java.com.epam.lab.web.business_objects;

import main.java.com.epam.lab.web.page_objects.SignInPage;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;


public class GoogleBO {

	private static final Logger LOG = Logger.getLogger(GoogleBO.class);
	
	private final WebDriver driver;
	private SignInPage page;

	public GoogleBO(WebDriver driver){
		LOG.info("Constructor");
		this.driver = driver;
	}

	public void navigateToLoginForm(){
		page = new SignInPage(driver);
	}
	
	public void login(String login, String password){
		LOG.info("login, input: " + login + ", " + password);
		//SignInPage page = new SignInPage(driver);
		page.typeEmail(login);
		page.submitEmail();
		page.typePassword(password);
		page.submitPassword();
	}

	public boolean isLoggedIn(String username){
		return driver.getCurrentUrl().contains("https://myaccount.google.com")
				&& page.getLoggedInUsername().equals(username);
	}
}
