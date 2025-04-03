package ca.uqam.mgl7230.tp2;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = {"src/test/resources/test_suite"},
        stepNotifications = true,
        plugin = {"pretty",
                "html:target/cucumber",
                "json:target/cucumber.json"},
        tags = "not @ignore")
public class RunCucumber {
}
