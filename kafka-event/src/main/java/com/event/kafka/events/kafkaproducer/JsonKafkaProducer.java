package com.event.kafka.events.kafkaproducer;

import com.event.kafka.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
public class JsonKafkaProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(JsonKafkaProducer.class);

    private KafkaTemplate<String, User> kafkaTemplate;

    public JsonKafkaProducer(KafkaTemplate<String, User> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(User user){

        try {

            LOGGER.info(String.format("message send json -> %s", user.toString()));

            Message<User> message = MessageBuilder
                    .withPayload(user)
                    .setHeader(KafkaHeaders.TOPIC, "topic1_json")
                    .build();

            kafkaTemplate.send(message);

        }catch (Exception e){
            LOGGER.error(e.getMessage());
        }
    }
}
