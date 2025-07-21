package com.tracker.habitservice.repository.jpa;

import com.tracker.habitservice.entity.HabitJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HabitJpaRepository extends JpaRepository<HabitJpaEntity, Long> {
    Optional<Object> findByName(String name);
    Optional<HabitJpaEntity> findByIdAndUserId(Long id, Long userId);
}