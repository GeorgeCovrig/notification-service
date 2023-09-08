package cool.programmer.kafka.producer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Properties;

@RestController
public class MessageProducerController {

    @GetMapping("/producer/{userId}/{message}")
    public ResponseEntity<String> postData(@PathVariable("message") String message, @PathVariable("userId") int userId){
        // 1) INITIALISATION OF CONNECTION PROPERTIES
        String bootstrapServers = "127.0.0.1:9092";
        String groupId = "cool_programmer";
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);

        // 2) INITIALISATION OF THE PRODUCER
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        // 3) CREATION, SENDING AND CLOSING THE CONNECTION TO THE KAFKA SERVER
        ProducerRecord<String, String> producerRecord =	new ProducerRecord<>("cool_programmer_topic", message + ";" + userId);
        producer.send(producerRecord);
        producer.flush();
        producer.close();
        return ResponseEntity.ok("ok");
    }
}
