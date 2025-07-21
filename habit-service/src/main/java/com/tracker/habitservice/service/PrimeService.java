package com.tracker.habitservice.service;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class PrimeService {

    private final StringRedisTemplate redisTemplate;

    public PrimeService(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public boolean isPrime(Long userId) {
        String val = redisTemplate.opsForValue().get("prime:" + userId);
        return "true".equalsIgnoreCase(val);
    }

    public boolean checkPrime(long userId) {
        Object result = redisTemplate.opsForValue().get("prime:" + userId);
        return "true".equalsIgnoreCase(String.valueOf(result));
    }
}