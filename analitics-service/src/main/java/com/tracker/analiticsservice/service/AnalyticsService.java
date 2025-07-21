package com.tracker.analiticsservice.service;


import com.tracker.analiticsservice.dto.HabitDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AnalyticsService {
    private final HabitService habitService;

    public float getAverageCompletionByType(long userId, String type) {
        return (float) habitService
                .getAllHabitsOfUser(userId, type)
                .stream()
                .mapToDouble(h ->
                        (float) h.getCurrentWeekProgress() / h.getTargetPerWeek())
                .average()
                .orElse(0);
    }

    public float getCompletionByHabitIdAndType(long userId, long habitId, String type) {
        HabitDto habit = habitService.getHabitByIdAndType(userId, habitId, type);

        return (float) habit.getCurrentWeekProgress() / habit.getTargetPerWeek();
    }
}
