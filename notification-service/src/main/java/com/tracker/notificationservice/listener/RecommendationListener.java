package com.tracker.notificationservice.listener;

import com.tracker.notificationservice.dto.RecommendationDto;
import com.tracker.notificationservice.service.PushNotificationService;
import com.tracker.notificationservice.service.TwilioSmsService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class RecommendationListener {

    private final PushNotificationService pushNotificationService;
    private final TwilioSmsService twilioSmsService;

    @KafkaListener(
            topics = "recommendation",
            groupId = "recommendation-group",
            containerFactory = "kafkaListenerContainerFactoryHabitSentEvent"
    )
    public void recommendationListener(RecommendationDto recommendationDto) throws Exception {
        List<String> deviceTokens = recommendationDto.userDto().deviceTokens();
        for (String deviceToken : deviceTokens) {
            pushNotificationService.sendNotification(deviceToken, "Recommendation for you", recommendationDto.recommendation());
        }

        try {
            String phoneNumber = recommendationDto.userDto().phoneNumber();
            String text = recommendationDto.recommendation();

            String sid = twilioSmsService.sendSms(phoneNumber, text);
            System.out.println(sid);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }
}

