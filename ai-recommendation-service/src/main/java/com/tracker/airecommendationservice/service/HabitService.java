package com.tracker.airecommendationservice.service;

import com.tracker.airecommendationservice.dto.DescriptionsDto;
import com.tracker.airecommendationservice.dto.HabitDto;
import com.tracker.airecommendationservice.repository.DescriptionsRedisRepository;
import com.tracker.airecommendationservice.repository.HabitRedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class HabitService {
    private final HabitRedisRepository habitRedisRepository;
    private final DescriptionsRedisRepository descriptionsRedisRepository;

    public void saveHabit(HabitDto habitDto, DescriptionsDto descriptionsDto) {
        habitRedisRepository.save(habitDto, habitDto.getId());

        if (descriptionsDto != null) {
            descriptionsRedisRepository.save(descriptionsDto, habitDto.getId());
        }
    }

    public List<HabitDto> getAllHabitsOfUser(long userId) {
        Map<Long, String> descriptions = descriptionsRedisRepository.getHabitDescriptions(userId);

        if (descriptions.isEmpty()) return List.of();

        List<HabitDto> habits = descriptions
                .keySet()
                .stream()
                .map(habitRedisRepository::findById)
                .toList();

        habits.forEach(h -> {
            String description = descriptions.get(h.getId());

            if (!h.getDescription().equals(description)) {
                h.setDescription(descriptions.get(h.getId()));
            }
        });

        return habits;
    }

    public void deleteHabit(long habitId) {
        habitRedisRepository.deleteHabitById(habitId);
    }
}
