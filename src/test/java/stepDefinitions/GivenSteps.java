package stepDefinitions;

import io.cucumber.java.Scenario;
import io.cucumber.java.en.Given;
import utils.BaseTest;
import utils.KafkaConstants;

public class GivenSteps extends KafkaConstants {
    BaseTest baseTest;
    Scenario scenario;

    public GivenSteps(BaseTest baseTest) {
        this.baseTest = baseTest;
        this.scenario = baseTest.getScenario();
    }

    @Given("user should generate a kafka message with movie name as {string} and genre as {string}")
    public void generateKafkaMessage(String movieName, String genre) {
        movie.setMovieName(movieName);
        movie.setGenre(genre);
        scenario.log("Data is created: "+movie);
    }

}
