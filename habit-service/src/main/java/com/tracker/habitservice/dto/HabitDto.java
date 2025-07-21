package com.tracker.habitservice.dto;

import lombok.Data;

@Data
public class HabitDto {
    private final long id;
    private final long userId;
    private final String name;
    private String description;
    private final String type;
    private byte targetPerWeek;
    private byte currentWeekProgress;

}
