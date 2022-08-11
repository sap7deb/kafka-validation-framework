package Consumer;

import com.app.Movie;
import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class KafkaMessageConsumer {
    public static void main(String[] args) throws IOException {

        String bootstrapServer = "127.0.0.1:9092";
        String schemaRegistry = "http://localhost:8081";

        String topicName = "movies";
        String groupName = "CG.movies";
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServer);
        properties.put("group.id", groupName);
        properties.put("auto.offset.reset", "earliest");
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", KafkaAvroDeserializer.class.getName());
        properties.put("schema.registry.url", schemaRegistry);
        properties.put("specific.avro.reader", "true");

        KafkaConsumer<String, Movie> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topicName));
        while (true) {
            ConsumerRecords<String, Movie> consumerRecord = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, Movie> record : consumerRecord) {
                System.out.printf("offset = %d, key = %s, topic = %s, value = %s%n", record.offset(), record.key(), record.topic(), record.value());
                if(record!=null) {
                    Movie movie = record.value();
                    System.out.println("Name====>" + movie.getMovieName());
                    System.out.println("Genre ====>" + movie.getGenre());
                }
            }
        }
    }

}
