//package test.java.com.epam.lab.web.steps;
//
//import cucumber.api.java.After;
//import cucumber.api.java.en.Given;
//import cucumber.api.java.en.Then;
//import cucumber.api.java.en.When;
//import main.java.com.epam.lab.web.business_objects.GmailBO;
//import main.java.com.epam.lab.web.drivers.ChromeDriverPool;
//import main.java.com.epam.lab.web.users_emails.Email;
//import main.java.com.epam.lab.web.users_emails.UserEmailPairs;
//import org.junit.Assert;
//
//import java.util.Objects;
//
//public class GmailSteps {
//
//    private GmailBO gmailBO;
//    private Email email;
//
//    @Given("^I have navigated to gmail$")
//    public void IHaveNavigatedToGmail(){
//        gmailBO = new GmailBO(ChromeDriverPool.getInstance());
//        gmailBO.navigateToGmail();
//    }
//
//    @Given("^I have navigated to sent emails page$")
//    public void IHaveNavigatedToSentEmailsPage(){
//        gmailBO = new GmailBO(ChromeDriverPool.getInstance());
//        gmailBO.navigateToSentPage();
//    }
//
//    @Given("^I have email credentials$")
//    public void IHaveEmailCredentials(){
//        email = (new UserEmailPairs()).getPair().getValue();
//    }
//
//    @When("^I write and send email$")
//    public void IWriteAndSendEmail(){
//        gmailBO.writeEmail(email.getTo(), email.getSubject(), email.getText());
//        gmailBO.sendEmail();
//    }
//
//    @When("^I wait for email to be sent$")
//    public void IWaitForEmailToBeSent(){
//        gmailBO.waitForEmailToBeSent();
//    }
//
//    @When("^I navigate to sent emails page$")
//    public void INavigateToSentEmailsPage(){
//        gmailBO.navigateToSentPage();
//    }
//
//    @When("^I delete last sent email$")
//    public void IDeleteLastSentEmail(){
//        gmailBO.deleteSentEmail(1);
//    }
//
//    @Then("^I can see sent email in the list$")
//    public void ICanSeeSentEmailInTheList(){
//        Assert.assertEquals(email.getSubject(), gmailBO.getSentEmailSubject(1));
//        Assert.assertTrue(email.getText().contains(gmailBO.getSentEmailShortText(1)));
//    }
//
//    @Then("^I can see delete notification pop-up$")
//    public void ICanSeeDeleteNotificationPopUp(){
//        Assert.assertTrue(gmailBO.isSentEmailDeleted());
//    }
//}
