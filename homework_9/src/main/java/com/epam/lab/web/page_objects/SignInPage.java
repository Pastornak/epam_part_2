package main.java.com.epam.lab.web.page_objects;

import main.java.com.epam.lab.web.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class SignInPage extends PageObject {
	
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
		driver.get("https://accounts.google.com/ServiceLogin");
		PageFactory.initElements(new CustomFieldDecorator(driver), this);
	}
	
	public void typeEmail(String email){
		emailInput.type(email);
	}
	
	public void submitEmail(){
		submitEmailButton.click();
	}
	
	public void typePassword(String password){
		waitForElementLoading(passwordInput.getElement());
		passwordInput.type(password);
	}
	
	public void submitPassword(){
		waitForElementLoading(submitPasswordButton.getElement());
		submitPasswordButton.click();
		waitForURLToContain("https://myaccount.google.com");
	}

	public String getLoggedInUsername(){
		if(!driver.getCurrentUrl().contains("https://myaccount.google.com")){
			driver.get("https://myaccount.google.com");
		}
		Label usernameLabel = new Label(driver.findElement(By.xpath("//div[@class='ZrQ9j']")));
		String usernameString = usernameLabel.getText();
		usernameString = usernameString.replace("Welcome,", "");
		usernameString = usernameString.trim();
		return usernameString;
	}
}
