package com.tracker.analiticsservice.repository;


import com.tracker.analiticsservice.dto.DataDto;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Repository
public class DataRedisRepository extends AbstractRedisRepository<DataDto> {
    public DataRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate, "desc:", DataDto.class);
    }

    private <T> List<T> stringArrayToNumsArray(String stringArray, Function<String, T> parser) {
        return Arrays
                .stream(stringArray.split(","))
                .map(parser)
                .toList();
    }

    private String getHabitDataForUser(long userId, DataDto descriptionsDto) {
        return descriptionsDto
                .userIdsData()
                .keySet()
                .stream()
                .filter(key -> stringArrayToNumsArray(key, Long::parseLong).contains(userId))
                .findFirst()
                .orElse(null);
    }

    public Map<Long, List<Byte>> getHabitData(long userId) {
        return findAll()
                .stream()
                .filter(d -> getHabitDataForUser(userId, d) != null)
                .map(d -> Map.entry(d.habitId(),
                        stringArrayToNumsArray(getHabitDataForUser(userId, d), Byte::parseByte)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
}

