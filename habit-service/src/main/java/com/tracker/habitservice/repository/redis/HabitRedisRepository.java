package com.tracker.habitservice.repository.redis;

import com.tracker.habitservice.entity.HabitRedisEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class HabitRedisRepository extends AbstractRedisRepository<HabitRedisEntity> {
    @Autowired
    public HabitRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate, "habit:", HabitRedisEntity.class);
    }

    private String key(long habitId) {
        return "habit:" + habitId;
    }
}
