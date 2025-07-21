package com.tracker.habitservice.entity;

public record HabitRedisEntity(Long id,
                               Long userId,
                               String name,
                               String description,
                               String type,
                               Byte targetPerWeek,
                               Byte currentWeekProgress) {
}
