package com.epam.lab.homework_4.elements;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

abstract class AbstractElement {

	protected WebElement webElement;
	
	public AbstractElement(WebElement webElement){
		this.webElement = webElement;
	}
	
	public void click(){
		webElement.click();
	}
	
	public void submit(){
		webElement.submit();
	}
	
	public void sendKeys(CharSequence... keys){
		webElement.sendKeys(keys);
	}
	
	public void clear(){
		webElement.clear();
	}
	
	public String getTagName(){
		return webElement.getTagName();
	}
	
	public String getAttribute(String attribute){
		return webElement.getAttribute(attribute);
	}
	
	public boolean isSelected(){
		return webElement.isSelected();
	}
	
	public boolean isEnabled(){
		return webElement.isEnabled();
	}
	
	public boolean isDisplayed(){
		return webElement.isDisplayed();
	}
	
	public String getText(){
		return webElement.getText();
	}
	
	public List<WebElement> findElements(By locator){
		return webElement.findElements(locator);
	}
	
	public WebElement findElement(By locator){
		return webElement.findElement(locator);
	}
	
	public Point getLocation(){
		return webElement.getLocation();
	}
	
	public Dimension getSize(){
		return webElement.getSize();
	}
	
	public Rectangle getRect(){
		return webElement.getRect();
	}
	
	public String getCssValue(String property){
		return webElement.getCssValue(property);
	}
	
	public WebElement getElement(){
		return webElement;
	}
}
