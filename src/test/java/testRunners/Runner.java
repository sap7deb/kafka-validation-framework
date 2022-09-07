package testRunners;

import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import utils.KafkaConstants;


@RunWith(Cucumber.class)
@CucumberOptions(features = "src/test/resources/features/",
        glue = {"stepDefinitions"},
        plugin = {"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
)
public class Runner {
}
