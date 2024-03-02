package com.event.kafka.events.kafkaconsumer;

import com.event.kafka.events.kafkaproducer.KafkaProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaConsumer.class);


    @KafkaListener(topics = "topic1", groupId = "myGroup")
    public void consume(String message){
        LOGGER.info(String.format("message received -> %s", message));
    }

    @KafkaListener(topicPartitions = {
            @TopicPartition(topic = "topic1_partition", partitions = {"0","1"})
    }, groupId = "myGroup1")
    public void consume_partition(String message){
        LOGGER.info(String.format("message partition received -> %s", message));
    }
}
