package kafka;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class ProducerWithKey {
    private static final Logger log = LoggerFactory.getLogger(ProducerWithKey.class.getSimpleName());
    public static void main(String[] args) throws InterruptedException {

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


        for (int j=0;j<2;j++) {
            for (int i = 0; i < 10; i++) {

                String topic = "demo";
                String key = "id_" + i; // same key will go to same partition
                String value = "hello world " + i;

                //3.send data
                ProducerRecord<String, String> producerRecord =
                        new ProducerRecord<>(topic, key, value);

                //send data - async
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if (e == null) {
                            log.info("received " +
                                    "key " + key +" | "+
                                    "partition " + recordMetadata.partition() + "\n");
                        } else {
                            log.info("exception");
                        }
                    }
                });
            }
            Thread.sleep(100);
        }



        //4.flush and close the producer

        //this line makes above send to wait, otheriwse send is aync, it will continue to eexexute next line
        //tell the parameter to send all data and back untill done -- synchronous
        producer.flush();

        //flush and close the producer
        producer.close();

    }
}
