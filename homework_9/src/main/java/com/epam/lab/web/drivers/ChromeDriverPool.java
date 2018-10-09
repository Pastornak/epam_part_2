package main.java.com.epam.lab.web.drivers;

import java.util.concurrent.TimeUnit;

import main.java.com.epam.lab.web.readers.PropertiesParser;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


public class ChromeDriverPool {

	private ChromeDriverPool(){}

	private static final ThreadLocal<WebDriver> localValue = new ThreadLocal<WebDriver>(){
		protected WebDriver initialValue(){
			return getNewDriver();
		}
	};

	public static WebDriver getThreadLocalInstance(){
		return localValue.get();
	}

	public static WebDriver getNewDriver(){
		PropertiesParser pp = new PropertiesParser("src/main/resources/driver_config.properties");
		System.setProperty("webdriver.chrome.driver", pp.getChromeDriverPath());
		WebDriver driver = new ChromeDriver();
		int implicitTimeWait = pp.getImplicitWaitTimeProperty();
		driver.manage().timeouts().implicitlyWait(implicitTimeWait, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(implicitTimeWait, TimeUnit.SECONDS);
		return driver;
	}
}
