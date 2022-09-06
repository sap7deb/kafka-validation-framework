package producer;

import com.app.Movie;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import java.util.Properties;

@Slf4j
public class KafkaMessageProducer {
    static String bootstrapServer;
    static String schemaRegistry;
    static String topic;

    public static void main(String[] args) {

        bootstrapServer = "127.0.0.1:9092";
        schemaRegistry = "http://127.0.0.1:8081";
        topic = "movies";

        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServer);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", KafkaAvroSerializer.class.getName());
        properties.put("schema.registry.url", schemaRegistry);

        Movie movie = new Movie();
        movie.setMovieName("Batman");
        movie.setGenre("Fiction");

        KafkaProducer<String, Movie> kafkaProducer = new KafkaProducer<String, Movie>(properties);

        ProducerRecord<String, Movie> producerRecord =
                new ProducerRecord<String, Movie>(topic, 0, "Test", movie);

        kafkaProducer.send(producerRecord, (metadata, exception) -> {
            if (exception == null) {
                log.info("Message was produced and published to Kafka topic: {}", topic);
            } else {
                exception.printStackTrace();
            }
        });
        kafkaProducer.close();
    }
}
