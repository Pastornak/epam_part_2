package com.epam.lab.homework_4.elements;

import org.openqa.selenium.WebElement;

public class Label extends AbstractElement {

	public Label(WebElement webElement){
		super(webElement);
	}
	
	public void sendKeys(CharSequence... keys){
		throw new UnsupportedOperationException();
	}
	
	public void clear(){
		throw new UnsupportedOperationException();
	}
}
