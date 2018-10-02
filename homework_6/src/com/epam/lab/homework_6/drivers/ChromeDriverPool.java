package com.epam.lab.homework_6.drivers;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import com.epam.lab.homework_6.readers.PropertiesParser;

public class ChromeDriverPool {

	private ChromeDriverPool(){}
	
	private static final ThreadLocal<WebDriver> localValue = new ThreadLocal<WebDriver>(){
		protected WebDriver initialValue(){
			WebDriver driver = new ChromeDriver();
			int implicitTimeWait = (new PropertiesParser("resources/driver_config.properties"))
					.getImplicitWaitTimeProperty();
			driver.manage().timeouts().implicitlyWait(implicitTimeWait, TimeUnit.SECONDS);
			driver.manage().timeouts().pageLoadTimeout(implicitTimeWait, TimeUnit.SECONDS);
			return driver;
		}
	};
	
	public static WebDriver getInstance(){
		return localValue.get();
	}
}
