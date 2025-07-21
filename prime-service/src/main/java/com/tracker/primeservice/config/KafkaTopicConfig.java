package com.tracker.primeservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaTopicConfig {
    private NewTopic createTopic(String name) {
        return new NewTopic(name, 3, (short) 1);
    }

    @Bean
    public NewTopic SubscribePriceTopic() {return createTopic("SubscribePrice");}

    @Bean
    public NewTopic PrimeSubscriptionTopic() {return createTopic("PrimeSubscription");}
}