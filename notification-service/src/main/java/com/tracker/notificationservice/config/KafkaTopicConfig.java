package com.tracker.notificationservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    private NewTopic createNewTopic(String name) {
        return new NewTopic(name, 1, (short) 1);
    }

    @Bean
    public NewTopic recommendationTopic() {
        return createNewTopic("recommendation");
    }
}
