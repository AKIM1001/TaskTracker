package com.tracker.airecommendationservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class AiConfig {
    @Value("${ai.model}")
    private String model;

    @Value("${ai.auth_key}")
    private String authKey;

    @Value("${ai.sender.role}")
    private String senderRole;

    @Value("${ai.temp}")
    private String temperature;

    @Value("${ai.api.url}")
    private String url;

    @Value("${ai.message.type}")
    private String messageType;

    @Bean
    public Map<String, String> aiApiConfig() {
        HashMap<String, String> config = new HashMap<>();

        config.put("model", model);

        config.put("auth key", authKey);

        config.put("sender role", senderRole);

        config.put("url", url);

        config.put("message type", messageType);

        config.put("temperature", temperature);

        return config;
    }

}
