package com.epam.lab.homework_3.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.lab.homework_3.readers.PropertiesParser;

class PageObject {
	
	protected int waitTime;

	protected WebDriver driver;

	protected PageObject(WebDriver _driver) {
		this.driver = _driver;
		PageFactory.initElements(driver, this);
		waitTime = new PropertiesParser("resources/driver_config.properties").getImplicitWaitTimeProperty();
	}

	protected void waitForElementLoading(WebElement ...elements){
		for(WebElement element: elements){
			waitForVisibility(element);
		}
	}
	
	protected void waitForVisibility(WebElement element) {
		new WebDriverWait(driver, waitTime).until(ExpectedConditions.visibilityOf(element));
	}
	
	protected void waitForElementLoading(By ...locators){
		for(By locator: locators){
			waitForPresence(locator);
		}
	}
	
	protected void waitForPresence(By locator) {
		new WebDriverWait(driver, waitTime).until(ExpectedConditions.presenceOfElementLocated(locator));
	}
}
