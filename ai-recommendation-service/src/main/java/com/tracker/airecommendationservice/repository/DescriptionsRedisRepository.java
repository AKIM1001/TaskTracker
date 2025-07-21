package com.tracker.airecommendationservice.repository;

import com.tracker.airecommendationservice.dto.DescriptionsDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Repository
public class DescriptionsRedisRepository extends AbstractRedisRepository<DescriptionsDto> {
    public DescriptionsRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate, "desc:", DescriptionsDto.class);
    }

    private List<Long> stringArrayToIntArray(String stringArray) {
        return Arrays
                .stream(stringArray.split(","))
                .mapToLong(Long::parseLong)
                .boxed()
                .toList();
    }

    private String getHabitDescriptionForUser(long userId, DescriptionsDto descriptionsDto) {
        return descriptionsDto
                .userIdsDescriptions()
                .keySet()
                .stream()
                .filter(key -> stringArrayToIntArray(key).contains(userId))
                .findFirst()
                .orElse(null);
    }

    public Map<Long, String> getHabitDescriptions(long userId) {
        return findAll()
                .stream()
                .filter(d -> getHabitDescriptionForUser(userId, d) != null)
                .map(d -> Map.entry(d.habitId(), getHabitDescriptionForUser(userId, d)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}
