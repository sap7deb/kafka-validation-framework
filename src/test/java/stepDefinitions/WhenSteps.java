package stepDefinitions;

import com.app.Movie;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import utils.KafkaConfigs;
import utils.KafkaConstants;

import java.io.IOException;

@Slf4j
public class WhenSteps extends KafkaConstants {

    @When("user triggers the kafka message for topic {string} on key {string}")
    public void userTriggersTheKafkaMessageForTopic(String topicName, String key) throws IOException {
        movieProducer = new KafkaProducer<>(KafkaConfigs.producerConfigs());
        ProducerRecord<String, Movie> producerRecord =
                new ProducerRecord<String, Movie>(topicName, 0, key, movie);
        movieProducer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                log.info("Successfully published message");
            } else {
                exception.printStackTrace();
            }
        });
        movieProducer.close();
    }

}
