package com.tracker.habitservice.mapper;


import com.tracker.habitservice.dto.HabitDto;
import com.tracker.habitservice.entity.HabitJpaEntity;
import com.tracker.habitservice.entity.HabitRedisEntity;
import com.tracker.habitservice.request.HabitSaveRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class HabitMapper {

    public Object toDto;

    public HabitDto toDto(HabitRedisEntity redisEntity) {
        return null;
    }

    public HabitDto toDto(HabitJpaEntity jpaEntity) {
        return null;
    }

    public HabitRedisEntity toRedisEntity(HabitDto dto) {
        return null;
    }

    public HabitJpaEntity toJpaEntity(HabitSaveRequest request) {
        return null;
    }

}
