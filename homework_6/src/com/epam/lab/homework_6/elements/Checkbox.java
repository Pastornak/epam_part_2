package com.epam.lab.homework_6.elements;

import org.openqa.selenium.WebElement;

public class Checkbox extends AbstractElement{

	public Checkbox(WebElement webElement){
		super(webElement);
	}
	
	public boolean isChecked(){
		return webElement.isSelected();
	}
	
	public void setChecked(boolean value){
		if(isChecked() != value){
			webElement.click();
		}
	}
}
