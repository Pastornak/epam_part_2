package com.epam.lab.homework_3.page_objects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

class PageObject {

	protected WebDriver driver;

	protected PageObject(WebDriver _driver) {
		this.driver = _driver;
		PageFactory.initElements(driver, this);
	}

	protected void waitForElementLoading(WebElement ...elements){
		for(WebElement element: elements){
			waitForVisibility(element);
		}
	}
	
	protected void waitForVisibility(WebElement element) throws Error {
		new WebDriverWait(driver, 15).until(ExpectedConditions.visibilityOf(element));
	}
	
	protected void waitForElementLoading(By ...locators){
		for(By locator: locators){
			waitForPresence(locator);
		}
	}
	
	protected void waitForPresence(By locator) throws Error{
		new WebDriverWait(driver, 15).until(ExpectedConditions.presenceOfElementLocated(locator));
	}
}
