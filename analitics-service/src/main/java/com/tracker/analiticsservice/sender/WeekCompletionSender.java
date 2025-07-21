package com.tracker.analiticsservice.sender;


import com.tracker.analiticsservice.dto.WeekCompletionDto;
import com.tracker.analiticsservice.service.AnalyticsService;
import com.tracker.analiticsservice.service.HabitService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
@AllArgsConstructor
public class WeekCompletionSender {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final AnalyticsService analyticsService;
    private final HabitService habitService;
    private static final String POSITIVE_HABITS_TOPIC = "positive-habit-completion";
    private static final String NEGATIVE_HABITS_TOPIC = "negative-habit-completion";

    private void sendWeekCompletionsByType(long userId, String type, String topic) {
        float completion = analyticsService.getAverageCompletionByType(userId, type);

        kafkaTemplate.send(topic, new WeekCompletionDto(completion));
    }

    private void sendAllUserWeekCompletionsByType(String type, String topic) throws InterruptedException {

        List<Long> userIds = habitService.getUserIds();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        userIds.forEach(id -> {
            executor.submit(() -> {
                sendWeekCompletionsByType(id, type, topic);
            });
        });

        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.MINUTES);
    }

    @Scheduled(cron = "0 0 9 * * MON")
    public void sendWeekPositiveHabitsCompletions() throws InterruptedException {
        sendAllUserWeekCompletionsByType("POSITIVE", POSITIVE_HABITS_TOPIC);
    }

    @Scheduled(cron = "0 0 8 * * MON")
    public void sendWeekNegativeHabitsCompletions() throws InterruptedException {
        sendAllUserWeekCompletionsByType("NEGATIVE", NEGATIVE_HABITS_TOPIC);
    }
}

