package stepDefinitions;

import com.app.Movie;
import io.cucumber.java.en.Then;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.junit.Assert;
import utils.KafkaConfigs;
import utils.KafkaConstants;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;

@Slf4j
public class ThenSteps extends KafkaConstants {

    boolean status = false;

    @Then("user consumes kafka message from topic {string} and consumer group {string} and validates on key {string}")
    public void userConsumesKafkaMessageFromTopicAndConsumerGroupAndValidates(String topicName, String consumeGroup, String key) throws IOException {
        status = false;
        movieConsumer = new KafkaConsumer<>(KafkaConfigs.consumerConfigs(consumeGroup));
        movieConsumer.assign(Arrays.asList(new TopicPartition(topicName, 0)));
        do {
            ConsumerRecords<String, Movie> consumerRecord = movieConsumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Movie> record : consumerRecord) {
                log.info("offset = {}}, key = {}, topic = {}, value = {}", record.offset(), record.key(), record.topic(), record.value());
                if (record.key().equalsIgnoreCase(key)) {
                    if (record.value() != null) {
                        Assert.assertEquals(movie, record.value());
                        status = true;
                        log.info("The message received: {}", record.value());
                        break;
                    }

                }
            }
        } while (!status);
    }
}