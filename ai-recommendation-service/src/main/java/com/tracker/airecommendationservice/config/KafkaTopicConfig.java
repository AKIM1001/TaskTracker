package com.tracker.airecommendationservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    private NewTopic createTopic(String name) {
        return new NewTopic(name, 3, (short) 1);
    }

    @Bean
    public NewTopic habitUpdationTopic() {
        return createTopic("habit-updation");
    }

    @Bean
    public NewTopic habitDeletionTopic() {
        return createTopic("habit-deletion");
    }

    @Bean
    public NewTopic userUpdationTopic() {
        return createTopic("user-updation");
    }

    @Bean
    public NewTopic recommendationTopic() {
        return createTopic("recommendation");
    }
}
