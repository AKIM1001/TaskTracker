package com.tracker.habitservice.request;

public record HabitSaveRequest(String name,
                               Long userId,
                               String description,
                               String type,
                               Byte targetPerWeek,
                               Byte currentWeekProgress) {

}
