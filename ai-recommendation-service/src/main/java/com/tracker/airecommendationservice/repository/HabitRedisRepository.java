package com.tracker.airecommendationservice.repository;

import com.tracker.airecommendationservice.dto.HabitDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HabitRedisRepository extends AbstractRedisRepository<HabitDto> {

    @Autowired
    public HabitRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate, "habit:", HabitDto.class);
    }

    public void deleteHabitById(long habitId) {
        getRedisTemplate().delete(getPrefix() + habitId);
    }


}
