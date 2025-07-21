package com.tracker.habitservice.event;

import com.tracker.habitservice.dto.DescriptionsDto;
import com.tracker.habitservice.dto.HabitDto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitSentEvent {
    private HabitDto habit;
    private DescriptionsDto descriptions;
}
