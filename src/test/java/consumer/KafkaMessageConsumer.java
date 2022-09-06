package consumer;

import com.app.Movie;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

@Slf4j
public class KafkaMessageConsumer {

    public static String bootstrapServer;
    public static String schemaRegistry;
    public static String topic;
    public static String groupName;
    public static boolean status = false;

    public static void main(String[] args) {

        bootstrapServer = "127.0.0.1:9092";
        schemaRegistry = "http://localhost:8081";
        topic = "movies";
        groupName = "CG.movies";

        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServer);
        properties.put("group.id", groupName);
        properties.put("auto.offset.reset", "earliest");
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", KafkaAvroDeserializer.class.getName());
        properties.put("schema.registry.url", schemaRegistry);
        properties.put("specific.avro.reader", "true");

        KafkaConsumer<String, Movie> consumer = new KafkaConsumer<>(properties);
        consumer.assign(Arrays.asList(new TopicPartition(topic, 0)));

        do {
            ConsumerRecords<String, Movie> consumerRecord = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Movie> record : consumerRecord) {
                log.info("offset = {}}, key = {}, topic = {}, value = {}", record.offset(), record.key(), record.topic(), record.value());
                if (record.key().equalsIgnoreCase("Test")) {
                    if (record.value() != null) {
                        Movie movie = record.value();
                        log.info("Name: {}", movie.getMovieName());
                        log.info("Genre: {}", movie.getGenre());
                        status = true;
                        break;
                    }
                }
            }
        } while (!status);
    }

}
