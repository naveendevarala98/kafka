package com.event.kafka.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    @Bean
    public NewTopic createtopic(){
        return TopicBuilder
                .name("topic1")
                //.partitions() - to create topic
                .build();
    }

    @Bean
    public NewTopic createtopic_json(){
        return TopicBuilder
                .name("topic1_json")
                //.partitions() - to create topic
                .build();
    }
}
