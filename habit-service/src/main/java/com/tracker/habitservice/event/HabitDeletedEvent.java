package com.tracker.habitservice.event;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HabitDeletedEvent {
    private Long habitId;
}

