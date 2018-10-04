package main.java.com.epam.lab.web.elements;

import org.openqa.selenium.WebElement;

public class Label extends AbstractElement {

	public Label(WebElement webElement){
		super(webElement);
	}
	
	public String getText(){
		return webElement.getText();
	}
	
	public void sendKeys(CharSequence... keys){
		throw new UnsupportedOperationException();
	}
	
	public void clear(){
		throw new UnsupportedOperationException();
	}
}
