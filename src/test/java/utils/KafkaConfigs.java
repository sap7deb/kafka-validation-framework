package utils;

import io.confluent.kafka.serializers.KafkaAvroDeserializer;
import io.confluent.kafka.serializers.KafkaAvroSerializer;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.io.IOException;
import java.util.Properties;

public class KafkaConfigs {

    static String bootstrapServer;
    static String schemaRegistry;
    static TestProps testProps;


    public static Properties producerConfigs() throws IOException {
        testProps = ManageProps.loadProperties();
        bootstrapServer = testProps.getProducer().get("bootstrap");
        schemaRegistry = testProps.getProducer().get("schemaRegistry");

        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServer);
        properties.put("key.serializer", StringSerializer.class.getName());
        properties.put("value.serializer", KafkaAvroSerializer.class.getName());
        properties.put("schema.registry.url", schemaRegistry);
        return properties;
    }

    public static Properties consumerConfigs(String consumerGroup) throws IOException {
        testProps = ManageProps.loadProperties();
        bootstrapServer = testProps.getConsumer().get("bootstrap");
        schemaRegistry = testProps.getConsumer().get("schemaRegistry");

        // String topicName = "movies";
        //  String groupName = "CG.movies";
        Properties properties = new Properties();
        properties.put("bootstrap.servers", bootstrapServer);
        properties.put("group.id", consumerGroup);
        properties.put("auto.offset.reset", "earliest");
        properties.put("key.deserializer", StringDeserializer.class.getName());
        properties.put("value.deserializer", KafkaAvroDeserializer.class.getName());
        properties.put("schema.registry.url", schemaRegistry);
        properties.put("specific.avro.reader", "true");

        return properties;

    }
}
