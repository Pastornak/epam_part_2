package com.epam.lab.homework_5.elements;

import org.openqa.selenium.WebElement;

public class Input extends AbstractElement {
	
	public Input(WebElement webElement){
		super(webElement);
	}
	
	public void type(String input){
		super.clear();
		super.sendKeys(input);
	}
}
