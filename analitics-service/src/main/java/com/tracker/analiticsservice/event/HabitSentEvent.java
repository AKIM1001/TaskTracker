package com.tracker.analiticsservice.event;

import com.tracker.analiticsservice.dto.DataDto;
import com.tracker.analiticsservice.dto.HabitDto;

public record HabitSentEvent(HabitDto habit,
                             DataDto data) {
}

