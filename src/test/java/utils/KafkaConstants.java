package utils;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import com.app.Movie;
import com.app.User;
import org.apache.kafka.clients.producer.KafkaProducer;

public abstract class KafkaConstants {

    public static KafkaConsumer<String, Movie> movieConsumer;
    public static KafkaConsumer<String, User> userConsumer;
    public static KafkaProducer<String, Movie> movieProducer;
    public static Movie movie = new Movie();
}
