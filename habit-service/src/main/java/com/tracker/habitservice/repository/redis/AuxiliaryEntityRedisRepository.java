package com.tracker.habitservice.repository.redis;

import org.springframework.data.redis.core.RedisTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public abstract class AuxiliaryEntityRedisRepository<T> extends AbstractRedisRepository<T> {
    protected AuxiliaryEntityRedisRepository(RedisTemplate<String, Object> redisTemplate,
                                             String prefix,
                                             Class<T> clazz) {
        super(redisTemplate, prefix, clazz);
    }

    public <U> List<U> stringArrayToNumsArray(String stringArray, Function<String, U> parser) {
        return Arrays
                .stream(stringArray.split(","))
                .map(parser)
                .toList();
    }

    public String getHabitDataForUser(long userId, Map<String, String> ids) {
        return ids
                .keySet()
                .stream()
                .filter(key -> stringArrayToNumsArray(key, Long::parseLong).contains(userId))
                .findFirst()
                .orElse(null);
    }
}
