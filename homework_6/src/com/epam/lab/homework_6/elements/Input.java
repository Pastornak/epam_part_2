package com.epam.lab.homework_6.elements;

import org.openqa.selenium.WebElement;

public class Input extends AbstractElement {
	
	public Input(WebElement webElement){
		super(webElement);
	}
	
	public void type(String input){
		webElement.clear();
		webElement.sendKeys(input);
	}
	
	public void sendKeys(CharSequence... keys){
		webElement.sendKeys(keys);
	}
	
	public void clear(){
		webElement.clear();
	}
}
