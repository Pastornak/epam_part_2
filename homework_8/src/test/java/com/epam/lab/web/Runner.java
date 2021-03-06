package test.java.com.epam.lab.web;


import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features",
        glue = {"test.java.com.epam.lab.web.steps"},
        format = {"pretty", "html:target/cucumber"})

public class Runner {
}
