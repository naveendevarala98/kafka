package com.event.kafka.events.kafkaproducer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class KafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaProducer.class);

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message){
        LOGGER.info(String.format("Message sent %s",message));
        kafkaTemplate.send("topic1", message);
    }

    public void sendMessage_partition(String message){
        LOGGER.info(String.format("Message partiton sent %s",message));
        CompletableFuture<SendResult<String, String>> m = kafkaTemplate.send("topic1_partition","Partition 4", message);

        m.whenComplete((result,e)->{
           if(e==null){
               LOGGER.info(String.format("Message partiton sent %s with offset %s with partition %s"
                       ,message, result.getRecordMetadata().offset(), result.getRecordMetadata().partition()));
           }else{
               LOGGER.info("unable to send message");
           }
        });
    }
}
