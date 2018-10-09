package main.java.com.epam.lab.web.business_objects;

import main.java.com.epam.lab.web.page_objects.SignInPage;
import org.openqa.selenium.WebDriver;


public class GoogleBO {
	
	private final WebDriver driver;
	private SignInPage page;

	public GoogleBO(WebDriver driver){
		this.driver = driver;
	}

	public void navigateToLoginForm(){
		page = new SignInPage(driver);
	}
	
	public void login(String login, String password){
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
