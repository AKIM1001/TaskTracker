package com.tracker.habitservice.request;

public record HabitUpdateRequest(Long id,
                                 String name,
                                 String description,
                                 String type,
                                 Byte targetPerWeek,
                                 Byte currentWeekProgress) {
}
