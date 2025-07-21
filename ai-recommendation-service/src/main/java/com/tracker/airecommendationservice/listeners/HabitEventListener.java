package com.tracker.airecommendationservice.listeners;

import com.tracker.airecommendationservice.event.HabitDeletedEvent;
import com.tracker.airecommendationservice.event.HabitSentEvent;
import com.tracker.airecommendationservice.service.HabitService;
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
        habitService.saveHabit(event.habit(), event.descriptions());
   }

    @KafkaListener(topics = "habit-deletion",
            groupId = "habit-group",
            containerFactory = "kafkaListenerContainerFactoryHabitDeletedEvent")
   public void listenerHabitDeletedEvent(HabitDeletedEvent event) {
       habitService.deleteHabit(event.habitId());
   }
}
