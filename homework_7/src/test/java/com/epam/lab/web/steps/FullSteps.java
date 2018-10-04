package test.java.com.epam.lab.web.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import main.java.com.epam.lab.web.business_objects.GmailBO;
import main.java.com.epam.lab.web.business_objects.GoogleBO;
import main.java.com.epam.lab.web.drivers.ChromeDriverPool;
import main.java.com.epam.lab.web.users_emails.Email;
import main.java.com.epam.lab.web.users_emails.User;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class FullSteps {

    private WebDriver driver;
    private User user;
    private Email email;
    private GoogleBO googleBO;
    private GmailBO gmailBO;

    @Before
    public void initDriver(){
        driver = ChromeDriverPool.getNewDriver();
    }

    @Given("^I have registered user credentials\\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\)$")
    public void IHaveRegisteredUserCredentials(String username, String login, String password) {
        user = new User(username, login, password);
    }

    @Given("^I have email credentials\\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\)$")
    public void IHaveEmailCredentials(String to, String subject, String text) {
        email = new Email(to, subject, text);
    }

    @When("^I navigate to google login form$")
    public void INavigateToGoogleLoginForm() {
        googleBO = new GoogleBO(driver);
        googleBO.navigateToLoginForm();
    }

    @When("^I enter my credentials$")
    public void IEnterMyCredentials() {
        googleBO.login(user.getLogin(), user.getPassword());
    }

    @When("^I can see right URL and my username$")
    public void ICanSeeRightURLAndMyUsername() throws Exception {
        if(!googleBO.isLoggedIn(user.getUsername())) {
            throw new Exception("Error: log in failed.");
        }
    }

    @When("^I navigate to gmail$")
    public void INavigateToGmail(){
        gmailBO = new GmailBO(driver);
        gmailBO.navigateToGmail();
    }

    @When("^I write and send email$")
    public void IWriteAndSendEmail(){
        gmailBO.writeEmail(email.getTo(), email.getSubject(), email.getText());
        gmailBO.sendEmail();
    }

    @When("^I wait for email to be sent$")
    public void IWaitForEmailToBeSent(){
        gmailBO.waitForEmailToBeSent();
    }

    @When("^I navigate to sent emails page$")
    public void INavigateToSentEmailsPage(){
        gmailBO.navigateToSentPage();
    }

    @When("^I can see sent email in the list$")
    public void ICanSeeSentEmailInTheList() throws Exception {
        if(!email.getSubject().equals(gmailBO.getSentEmailSubject(1))
                && !email.getText().contains(gmailBO.getSentEmailShortText(1))){
            throw new Exception("Error: email wasn't sent");
        }
    }

    @When("^I delete last sent email$")
    public void IDeleteLastSentEmail(){
        gmailBO.deleteSentEmail(1);
    }

    @Then("^I can see delete notification pop-up$")
    public void ICanSeeDeleteNotificationPopUp(){
        Assert.assertTrue(gmailBO.isSentEmailDeleted());
    }

    @After
    public void closeDriver(){
        driver.close();
    }
}
