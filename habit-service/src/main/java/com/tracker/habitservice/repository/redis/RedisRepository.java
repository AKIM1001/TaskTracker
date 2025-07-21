package com.tracker.habitservice.repository.redis;

import jakarta.persistence.EntityNotFoundException;

import java.util.List;

public interface RedisRepository<T> {
    void save(T t, long id);

    List<T> findAll();

    T findById(Long id) throws IllegalStateException, EntityNotFoundException;

    void deleteById(long habitId);

    void deleteByHabitId(long habitId);

    void deleteForUser(long habitId, long userId);

}
