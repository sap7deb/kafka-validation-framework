package stepDefinitions;

import com.app.Movie;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.junit.Assert;
import utils.KafkaConfigs;
import utils.KafkaConstants;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

@Slf4j
public class ThenSteps extends KafkaConstants {

    boolean status;

    @Then("user consumes kafka message from topic {string} and consumer group {string} and validates")
    public void userConsumesKafkaMessageFromTopicAndConsumerGroupAndValidates(String topicName, String consumeGroup) throws IOException {
        status = false;
        movieConsumer = new KafkaConsumer<>(KafkaConfigs.consumerConfigs(consumeGroup));
        movieConsumer.subscribe(Arrays.asList(topicName));
        while (true) {
            ConsumerRecords<String, Movie> consumerRecord = movieConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Movie> record : consumerRecord) {
                log.info("offset = %d, key = %s, topic = %s, value = %s%n", record.offset(), record.key(), record.topic(), record.value());
                if (record.value() != null) {
                    Assert.assertEquals(movie, record.value());
                    status = true;
                    log.info("The message received", record.value());
                    break;
                }

            }
            if (status) {
                break;
            }
        }
    }
}