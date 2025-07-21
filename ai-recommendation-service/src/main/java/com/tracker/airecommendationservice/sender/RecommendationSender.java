package com.tracker.airecommendationservice.sender;

import com.tracker.airecommendationservice.service.RecommendationService;
import com.tracker.airecommendationservice.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Random;

@Service
@RequiredArgsConstructor
@Getter
public class RecommendationSender {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RecommendationService recommendationService;
    private final UserService userService;
    private final Random random = new Random();
    private String currentCron;
    private static final String TOPIC = "recommendation";

    @PostConstruct
    public void init() {
        int nextMinute = LocalTime.now().getMinute() + 1;

        currentCron = String.format("0 %d * * * ?", nextMinute);
    }

    @Scheduled(cron = "#{@recommendationSender.getCurrentCron()}")
    public void sendRecommendation() throws IOException {
        long id = userService.getRandomUserId();

        String recommendation = recommendationService.getShortRecommendation(id);

        kafkaTemplate.send(TOPIC, recommendation);
    }
}
