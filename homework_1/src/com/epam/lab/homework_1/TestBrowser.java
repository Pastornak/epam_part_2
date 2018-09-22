package com.epam.lab.homework_1;

import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TestBrowser {
	
	private String query = "Apple";

	@BeforeClass
	public void setUp(){
		System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
	}
	
	@Test
	public void testChrome(){
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://www.google.com");
		WebElement searchField = driver.findElement(By.name("q"));
		searchField.sendKeys(query);
		searchField.submit();
		boolean result = driver.getTitle().toLowerCase().contains(query.toLowerCase());
		//System.out.println("Page title contains " + query + ": " + result);
		driver.quit();
		Assert.assertTrue(result);
	}
}
