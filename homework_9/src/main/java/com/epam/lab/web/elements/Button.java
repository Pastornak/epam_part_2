package main.java.com.epam.lab.web.elements;

import org.openqa.selenium.WebElement;

public class Button extends AbstractElement {

	public Button(WebElement webElement){
		super(webElement);
	}
	
	public void click(){
		webElement.click();
	}
}
