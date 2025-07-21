package com.tracker.analiticsservice.listener;

import com.tracker.analiticsservice.event.HabitDeletedEvent;
import com.tracker.analiticsservice.event.HabitSentEvent;
import com.tracker.analiticsservice.service.HabitService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class HabitEventListener {
    private final HabitService habitService;

    @KafkaListener(topics = "habit-updation",
            groupId = "habit-group",
            containerFactory = "kafkaListenerContainerFactoryHabitSentEvent")
    public void listenerHabitSentEvent(HabitSentEvent event) {
        habitService.saveHabit(event.habit(), event.data());
    }

    @KafkaListener(topics = "habit-deletion",
            groupId = "habit-group",
            containerFactory = "kafkaListenerContainerFactoryHabitDeletedEvent")
    public void listenerHabitDeletedEvent(HabitDeletedEvent event) {
        habitService.deleteHabit(event.habitId());
    }
}
