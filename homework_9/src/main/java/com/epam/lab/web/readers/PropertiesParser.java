package main.java.com.epam.lab.web.readers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class PropertiesParser {

	private Properties properties;

	public PropertiesParser(String pathToFile){
		properties = new Properties();
		try {
			properties.load(new FileInputStream(pathToFile));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public String getProperty(String propertyName){
		return properties.getProperty(propertyName);
	}

	public int getImplicitWaitTimeProperty(){
		return Integer.parseInt(properties.getProperty("implicit_wait_time"));
	}

	public String getChromeDriverPath(){
		return properties.getProperty("chrome_driver");
	}
}
