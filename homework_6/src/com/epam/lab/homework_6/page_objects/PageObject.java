package com.epam.lab.homework_6.page_objects;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.epam.lab.homework_6.readers.PropertiesParser;

class PageObject {
	
	private static final Logger LOG = Logger.getLogger(PageObject.class);
	
	protected int waitTime;

	protected WebDriver driver;

	protected PageObject(WebDriver _driver) {
		LOG.info("Constructor.");
		this.driver = _driver;
		waitTime = new PropertiesParser("resources/driver_config.properties").getImplicitWaitTimeProperty();
	}

	public int getWaitTime() {
		return waitTime;
	}
	
	protected void waitForElementLoading(WebElement ...elements){
		LOG.info("waitForElementLoading, input length: " + elements.length);
		for(WebElement element: elements){
			waitForVisibility(element);
		}
	}
	
	protected void waitForVisibility(WebElement element) {
		LOG.info("waitForVisibility, input: " + element);
		new WebDriverWait(driver, waitTime).until(ExpectedConditions.visibilityOf(element));
	}
	
	protected void waitForInvisibility(WebElement element, int timeInSeconds){
		LOG.info("waitForInvisibility, input: " + element + ", " + timeInSeconds + " seconds.");
		new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.invisibilityOf(element));
	}
	
	protected void waitForInvisibility(WebElement element){
		LOG.info("waitForInvisibility, input: " + element);
		new WebDriverWait(driver, waitTime).until(ExpectedConditions.invisibilityOf(element));
	}
	
	protected void waitForElementLoading(By ...locators){
		LOG.info("waitForElementLoading, input length: " + locators.length);
		for(By locator: locators){
			waitForPresence(locator);
		}
	}
	
	protected void waitForPresence(By locator) {
		LOG.info("waitForPresence, input: " + locator);
		new WebDriverWait(driver, waitTime).until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	protected void waitForURLToContain(String url){
		LOG.info("waitForURLToContain, input: " + url);
		new WebDriverWait(driver, waitTime).until(ExpectedConditions.urlContains(url));
	}
}
