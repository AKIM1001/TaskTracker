package com.tracker.airecommendationservice.event;

import com.tracker.airecommendationservice.dto.DescriptionsDto;
import com.tracker.airecommendationservice.dto.HabitDto;

public record HabitSentEvent(HabitDto habit,
                             DescriptionsDto descriptions) {
}
