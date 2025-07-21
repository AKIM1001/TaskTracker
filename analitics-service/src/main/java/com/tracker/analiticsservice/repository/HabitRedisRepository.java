package com.tracker.analiticsservice.repository;

import com.tracker.analiticsservice.dto.HabitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Objects;
import java.util.Set;

@Repository
public class HabitRedisRepository extends AbstractRedisRepository<HabitDto> {
    @Autowired
    public HabitRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate, "habit:", HabitDto.class);
    }

    public List<HabitDto> findHabitsByType(String type) {
        RedisTemplate<String, Object> redisTemplate = getRedisTemplate();

        Set<Object> habitIds = redisTemplate.opsForSet().members("type:" + type);

        if (Objects.isNull(habitIds)) return List.of();

        return habitIds
                .stream()
                .mapToLong(id -> (Long) id)
                .mapToObj(this::findById)
                .toList();
    }

    public void deleteHabitById(long habitId) {
        getRedisTemplate().delete(getPrefix() + habitId);
    }
}

