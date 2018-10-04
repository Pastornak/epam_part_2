package test.java.com.epam.lab.web.steps;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import main.java.com.epam.lab.web.business_objects.GoogleBO;
import main.java.com.epam.lab.web.drivers.ChromeDriverPool;
import main.java.com.epam.lab.web.users_emails.User;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;

public class LoginSteps {

    private WebDriver driver;
    private User user;
    private GoogleBO googleBO;

    @Given("^I have registered user credentials\\(\"([^\"]*)\", \"([^\"]*)\", \"([^\"]*)\"\\)$")
    public void IHaveRegisteredUserCredentials(String username, String login, String password) {
        user = new User(username, login, password);
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

    @Then("^I can see right URL and my username$")
    public void ICanSeeRightURLAndMyUsername() {
        Assert.assertTrue(googleBO.isLoggedIn(user.getUsername()));
    }

    @Before
    public void initDriver(){
        driver = ChromeDriverPool.getNewDriver();
    }

    @After
    public void closeDriver(){
        driver.quit();
    }
}
