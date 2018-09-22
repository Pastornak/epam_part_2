package com.epam.lab.homework_2;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class TestMail {
	
	private final String login = "yurii.test.email@gmail.com";
	private final String password = "CreatedByYurii_22.09.18";
	
	private final String emailReciever = "yurapaster@gmail.com";
	private final String emailSubject = "Test";
	private final String emailText = "Sent by Selenuim test";
	
	
	@BeforeClass
	public void setUp(){
		System.setProperty("webdriver.chrome.driver", "resources/chromedriver.exe");
	}
	
	@Test
	public void testChrome() throws UnsupportedEncodingException{
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("https://mail.google.com");
		
		// Authentication
		WebElement emailInputField = driver.findElement(By.xpath("//input[@id='identifierId']"));
		emailInputField.sendKeys(login);
		WebElement emailButtonNext = driver.findElement(By.xpath("//div[@role='button'][@id='identifierNext']"));
		emailButtonNext.click();
		WebElement passwordInputField = driver.findElement(By.xpath("//input[@type='password'][@name='password']"));
		passwordInputField.sendKeys(password);
		WebElement passwordButtonNext = driver.findElement(By.xpath("//div[@role='button'][@id='passwordNext']"));
		passwordButtonNext.click();
		
		// Sending email
		WebElement writeEmailButton = driver.findElement(By.xpath("//div[@role='button'][contains(@class, 'T-I J-J5-Ji T-I-KE L3')]"));
		writeEmailButton.click();
		WebElement dialogBox = driver.findElement(By.cssSelector("div[role='dialog'"));
		WebElement emailRecieverInput = dialogBox.findElement(By.xpath("//textarea[@name='to'][@class='vO']"));
		emailRecieverInput.sendKeys(emailReciever);
		WebElement emailSubjectInput = dialogBox.findElement(By.xpath("//input[@name='subjectbox']"));
		emailSubjectInput.sendKeys(emailSubject);
		WebElement emailTextInput = dialogBox.findElement(By.xpath("//div[@role='textbox']"));
		emailTextInput.sendKeys(emailText);
		WebElement sendEmailButton = dialogBox.findElement(By.xpath("//div[@role='button'][contains(@class, 'T-I J-J5-Ji aoO T-I-atl L3')]"));
		sendEmailButton.click();
		
		// Going to 'sent' page
		driver.get("https://mail.google.com/mail/u/0/#sent");
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Checking notification (valid only for Ukrainian version)
		//WebElement notification = (new WebDriverWait(driver, 10))
		//	.until(ExpectedConditions.presenceOfElementLocated(By
		//			.xpath("//div[@class='vh']/span[@class='aT']/span[@class='bAq']")));
		//
		//String notificationMessage = notification.getText();
		//byte bytes[] = notificationMessage.getBytes("UTF-8");
		//String message = new String(bytes, "UTF-8");
		//
		//String expectedMessage = "Лист надіслано.";
		//byte bytes2[] = expectedMessage.getBytes("UTF-8");
		//String expected = new String(bytes2, "UTF-8");
		//Assert.assertEquals(message, expected);
		
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// Checking sent emails
		List<WebElement> tables = driver.findElements(By.tagName("table"));
		WebElement rightTable = tables.get(tables.size() - 1);
		WebElement sentEmail = rightTable.findElement(By.cssSelector("tbody>tr:first-child"));
		WebElement emailDetails = sentEmail.findElement(By.xpath(".//td[@id][2]/div/div"));
		WebElement subjectDetails = emailDetails.findElement(By.xpath(".//div/span/span"));
		String subjectDetailsValue = subjectDetails.getText();
		WebElement textDetails = emailDetails.findElement(By.xpath(".//*[2]"));
		String textDetailsValue = textDetails.getText();
		Assert.assertEquals(subjectDetailsValue, emailSubject);
		Assert.assertTrue(textDetailsValue.contains(emailText));
	}
}
