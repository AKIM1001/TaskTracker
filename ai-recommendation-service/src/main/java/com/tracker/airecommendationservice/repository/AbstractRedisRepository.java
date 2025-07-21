package com.tracker.airecommendationservice.repository;

import com.tracker.airecommendationservice.exceptions.EntityNotFoundException;
import lombok.Getter;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Getter
public abstract class AbstractRedisRepository<T> implements RedisRepository<T> {
    private final RedisTemplate<String, Object> redisTemplate;
    private final String prefix;
    private final Class<T> clazz;

    protected AbstractRedisRepository(RedisTemplate<String, Object> redisTemplate,
                                      String prefix,
                                      Class<T> clazz) {
        this.redisTemplate = redisTemplate;

        this.prefix = prefix;

        this.clazz = clazz;
    }

    @Override
    public void save(T t, long id) {
        redisTemplate.opsForValue().set(prefix + id, t);
    }

    @Override
    public List<T> findAll() {
        Set<String> keys = redisTemplate.keys(prefix + "*");

        if (Objects.isNull(keys) || keys.isEmpty()) {
            return Collections.emptyList();
        }

        List<Object> values = redisTemplate.opsForValue().multiGet(keys);

        if (Objects.isNull(values) || values.isEmpty()) {
            return Collections.emptyList();
        }

        values.forEach(obj -> {
            if (!clazz.isInstance(obj)) {
                throw new IllegalStateException();
            }
        });

        return values
                .stream()
                .map(clazz::cast)
                .toList();
    }

    @Override
    public T findById(Long id) throws IllegalStateException, EntityNotFoundException {
        Object obj = redisTemplate.opsForValue().get(prefix + id);

        if (clazz != null && clazz.isInstance(obj)) {
            return clazz.cast(obj);
        } else if (obj != null && clazz != null) {
            throw new IllegalStateException();
        }

        throw new EntityNotFoundException();
    }


    @Override
    public boolean checkPrime(long userId) {
        Object result = redisTemplate.opsForValue().get("prime:" + userId);
        return "true".equalsIgnoreCase(String.valueOf(result));
    }

}
