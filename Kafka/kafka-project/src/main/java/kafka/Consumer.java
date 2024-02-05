package kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class Consumer {
    private static final Logger log = LoggerFactory.getLogger(Consumer.class.getSimpleName());
    public static void main(String[] args) {

        String groupId = "my-java-app";
        String topic="demo";

        //1. create producer properties
        Properties properties = new Properties();

        properties.setProperty("bootstrap.servers","active-monkey-14500-eu2-kafka.upstash.io:9092");
        properties.setProperty("sasl.mechanism","SCRAM-SHA-256");
        properties.setProperty("security.protocol","SASL_SSL");
        properties.setProperty("sasl.jaas.config","org.apache.kafka.common.security.scram.ScramLoginModule required username=\"YWN0aXZlLW1vbmtleS0xNDUwMCSUs0smb4cMfEQJjOF-dqnBoS_Qdo8Rf6DYXGA\" password=\"MTQ1NjEyNTgtZDhlNi00ODRlLWI3YzgtZDY4ODZlZGQ1ZDc2\";");

        //set producer properties
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer",StringDeserializer.class.getName());

        properties.setProperty("group.id",groupId);
        properties.setProperty("auto.offset.reset","earliest");

        //create a consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        consumer.subscribe(Arrays.asList(topic));

        //poll data
        while(true){

            log.info("polling");

            //if there is no record wait 1000ms, before making another call
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(1000));

            for (ConsumerRecord<String, String> record:records){
                log.info("key: "+record.key()+" value: "+record.value());
                log.info("partition: "+record.partition()+" offset: "+record.offset()+"\n");
            }
        }


    }
}
