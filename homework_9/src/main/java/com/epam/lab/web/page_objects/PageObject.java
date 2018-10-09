package main.java.com.epam.lab.web.page_objects;

import main.java.com.epam.lab.web.readers.PropertiesParser;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


class PageObject {
	
	protected int waitTime;

	protected WebDriver driver;

	protected PageObject(WebDriver _driver) {
		this.driver = _driver;
		waitTime = new PropertiesParser("src/main/resources/driver_config.properties").getImplicitWaitTimeProperty();
	}

	public int getWaitTime() {
		return waitTime;
	}
	
	protected void waitForElementLoading(WebElement ...elements){
		for(WebElement element: elements){
			waitForVisibility(element);
		}
	}
	
	protected void waitForVisibility(WebElement element) {
		new WebDriverWait(driver, waitTime).until(ExpectedConditions.visibilityOf(element));
	}
	
	protected void waitForInvisibility(WebElement element, int timeInSeconds){
		new WebDriverWait(driver, timeInSeconds).until(ExpectedConditions.invisibilityOf(element));
	}
	
	protected void waitForInvisibility(WebElement element){
		new WebDriverWait(driver, waitTime).until(ExpectedConditions.invisibilityOf(element));
	}
	
	protected void waitForElementLoading(By ...locators){
		for(By locator: locators){
			waitForPresence(locator);
		}
	}
	
	protected void waitForPresence(By locator) {
		new WebDriverWait(driver, waitTime).until(ExpectedConditions.presenceOfElementLocated(locator));
	}
	
	protected void waitForURLToContain(String url){
		new WebDriverWait(driver, waitTime).until(ExpectedConditions.urlContains(url));
	}
}
