package com.tracker.airecommendationservice.repository;

import com.tracker.airecommendationservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRedisRepository extends AbstractRedisRepository<UserDto> {
    @Autowired
    public UserRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate, "user:", UserDto.class);
    }
}
