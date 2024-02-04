package kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.awt.datatransfer.StringSelection;
import java.util.Properties;

public class Producer {
    private static final Logger log = LoggerFactory.getLogger(Producer.class.getSimpleName());
    public static void main(String[] args) {
        log.info("helo wolrd");

        //1. create producer properties
        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers","active-monkey-14500-eu2-kafka.upstash.io:9092");
        properties.setProperty("sasl.mechanism","SCRAM-SHA-256");
        properties.setProperty("security.protocol","SASL_SSL");
        properties.setProperty("sasl.jaas.config","org.apache.kafka.common.security.scram.ScramLoginModule required username=\"YWN0aXZlLW1vbmtleS0xNDUwMCSUs0smb4cMfEQJjOF-dqnBoS_Qdo8Rf6DYXGA\" password=\"MTQ1NjEyNTgtZDhlNi00ODRlLWI3YzgtZDY4ODZlZGQ1ZDc2\";");

        //set producer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer",StringSerializer.class.getName());
        //2. create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

        //3.send data
        ProducerRecord<String, String> producerRecord =
                new ProducerRecord<>("demo","hello world");

        //send data - async
        producer.send(producerRecord);



        //4.flush and close the producer

        //this line makes above send to wait, otheriwse send is aync, it will continue to eexexute next line
        //tell the parameter to send all data and back untill done -- synchronous
        producer.flush();

        //flush and close the producer
        producer.close();

    }
}
