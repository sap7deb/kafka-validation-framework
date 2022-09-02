package stepDefinitions;

import io.cucumber.java.en.Given;
import utils.KafkaConstants;

public class GivenSteps extends KafkaConstants {

    @Given("user should generate a kafka message with movie name as {string} and genre as {string}")
    public void generateKafkaMessage(String movieName, String genre) {
        movie.setMovieName(movieName);
        movie.setGenre(genre);
    }

}
