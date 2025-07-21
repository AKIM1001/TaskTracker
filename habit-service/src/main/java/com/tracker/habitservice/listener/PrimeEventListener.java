package com.tracker.habitservice.listener;

import com.tracker.habitservice.event.PrimeSubscriptionEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.kafka.annotation.KafkaListener;


@RequiredArgsConstructor
public class PrimeEventListener {

    private final StringRedisTemplate redisTemplate;

    @KafkaListener(topics = "prime-subscription", groupId = "habit-service-group", containerFactory = "kafkaListenerContainerFactory")
    public void handlePrimeEvent(PrimeSubscriptionEvent event) {
        String key = "prime:" + event.getUserId();
        String value = String.valueOf(event.isActive());
        redisTemplate.opsForValue().set(key, value);
        System.out.println("✔ Prime status updated in Redis for user " + event.getUserId() + ": " + value);
    }
}

