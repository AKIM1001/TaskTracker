package com.tracker.airecommendationservice.listeners;

import com.tracker.airecommendationservice.dto.UserDto;
import com.tracker.airecommendationservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserEventListener {
    private final UserService userService;

    @KafkaListener(topics = "user-updation",
            groupId = "user-group",
            containerFactory = "kafkaListenerContainerFactoryUserDto")
    public void listenerUserSent(UserDto userDto) {
        userService.save(userDto);
    }
}
