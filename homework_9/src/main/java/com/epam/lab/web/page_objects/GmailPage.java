package main.java.com.epam.lab.web.page_objects;

import main.java.com.epam.lab.web.elements.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;


public class GmailPage extends PageObject {

    @FindBy(xpath = "//div[contains(@class, 'T-I J-J5-Ji T-I-KE L3') and @role='button']")
    private Button writeEmailButton;

    @FindBy(name = "to")
    private Input emailToInput;

    @FindBy(name = "subjectbox")
    private Input emailSubjectInput;

    @FindBy(xpath = "//div[@role='textbox']")
    private Input emailTextInput;

    @FindBy(xpath = "//div[@role='button' and contains(@class, 'T-I J-J5-Ji aoO T-I-atl L3')]")
    private Button sendEmailButton;

    @FindBy(xpath = "(//table[@class='F cf zt' and @id])[last()]/tbody")
    private WebElement emailList;

    public GmailPage(WebDriver driver) {
        super(driver);
        if (!driver.getCurrentUrl().contains("mail.google.com/mail")) {
            driver.get("https://mail.google.com");
        }
        PageFactory.initElements(new CustomFieldDecorator(driver), this);
        waitForPageLoading();
    }

    public void waitForPageLoading() {
        waitForURLToContain("https://mail.google.com/mail/");
        waitForElementLoading(emailList);
        waitForVisibility(emailList);
    }

    public GmailPage navigateToSentEmails() {
        if (!driver.getCurrentUrl().contains("mail.google.com/mail")
                || !driver.getCurrentUrl().contains("#sent")) {
            driver.get("https://mail.google.com/mail/#sent");
            driver.navigate().refresh();
        }
        return this;
    }

    public void pressWriteEmailButton() {
        writeEmailButton.click();
    }

    public void fillEmailFields(String to, String subject, String text) {
        fillEmailTo(to);
        fillEmailSubject(subject);
        fillEmailText(text);
    }

    public void fillEmailTo(String to) {
        emailToInput.type(to);
    }

    public void fillEmailSubject(String subject) {
        emailSubjectInput.type(subject);
    }

    public void fillEmailText(String text) {
        emailTextInput.type(text);
    }

    public void pressSendEmail() {
        sendEmailButton.click();
    }

    public boolean isEmailSent() {
        waitForEmailToBeSent();
        return true;
    }

    public void waitForEmailToBeSent() {
        WebElement notification = driver.findElement(By
                .xpath("//div[@class='vh']/span[@class='aT']"));
        waitForElementLoading(notification);
        WebElement checkElement = notification.findElement(By
                .xpath(".//*[@class='bAo']/*[@id='link_undo']"));
        waitForInvisibility(checkElement);
    }

    public String getEmailSubject(int emailNumberFromTop) {
        WebElement emailInfo = getEmailInfo(emailNumberFromTop);
        Label subjectElement = new Label(emailInfo.findElement(By.xpath(".//div/span/span")));
        return subjectElement.getText();
    }

    public String getEmailShortText(int emailNumberFromTop) {
        WebElement emailInfo = getEmailInfo(emailNumberFromTop);
        Label textElement = new Label(emailInfo.findElement(By.xpath(".//span[@class='y2']")));
        waitForElementLoading(textElement.findElement(By.tagName("span")));
        String text = textElement.getText();
        text = text.replaceFirst("-", "");
        text = text.trim();
        return text;
    }

    public void clickEmailDeleteCheckbox(int emailNumberFromTop) {
        waitForElementLoading(emailList);
        Checkbox deleteCheckboxElement = new Checkbox(emailList.findElement(By.xpath(".//tr["
                + emailNumberFromTop + "]/td[@id][1]/*[@id and @role='checkbox']")));
        deleteCheckboxElement.setChecked(true);
    }

    public void clickDeleteCheckboxedEmailsButton() {
        Button deleteButton = new Button(driver.findElement(By
                .xpath("//*[@act=10 and @role='button']")));
        deleteButton.click();
    }

    public boolean isCheckboxedEmailDeleted() {
        WebElement notificationUndoElement = driver.findElement(By
                .xpath("//*[@id='link_undo' and @role='link']"));
        waitForElementLoading(notificationUndoElement);
        // Hardcoded time due to the fact that it's a notification,
        // so it's not related to the internet speed
        // (for case where implicitWaitTime < notification lifetime)
        waitForInvisibility(notificationUndoElement, 15);
        return true;
    }

    private WebElement getEmailInfo(int emailNumberFromTop) {
        waitForElementLoading(emailList);
        emailList = driver.findElement(By.xpath("(//table[@class='F cf zt' and @id])[last()]/tbody"));
        waitForEmailListToUpdate(emailNumberFromTop);
        return emailList.findElement(By.xpath(".//tr["
                + emailNumberFromTop + "]/td[@id][2]/div/div"));
    }

    private void waitForEmailListToUpdate(int emailNumberFromTop) {
        waitForElementLoading(
                By.xpath(".//tr[" + emailNumberFromTop + "]/td[@class='yX xY ']/div[last()]/span"),
                By.xpath(".//tr[" + emailNumberFromTop + "]/td[@id][2]/div/div/span/span")
        );
    }
}
