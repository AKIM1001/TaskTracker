package com.tracker.habitservice.service;

import com.tracker.habitservice.dto.DataDto;
import com.tracker.habitservice.dto.DescriptionsDto;
import com.tracker.habitservice.dto.HabitDto;
import com.tracker.habitservice.entity.HabitJpaEntity;
import com.tracker.habitservice.entity.HabitRedisEntity;
import com.tracker.habitservice.mapper.HabitMapper;
import com.tracker.habitservice.repository.jpa.HabitJpaRepository;
import com.tracker.habitservice.repository.redis.DataRedisRepository;
import com.tracker.habitservice.repository.redis.DescriptionsRedisRepository;
import com.tracker.habitservice.repository.redis.HabitRedisRepository;
import com.tracker.habitservice.request.HabitSaveRequest;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import java.awt.print.Pageable;
import java.util.List;
import java.util.Map;
import java.util.*;

@Service
@RequiredArgsConstructor
public class HabitService {

    private final PrimeService primeService;

    private final HabitJpaRepository habitJpaRepository;
    private final HabitRedisRepository habitRedisRepository;
    private final DescriptionsRedisRepository descriptionsRedisRepository;
    private final DataRedisRepository dataRedisRepository;
    private final HabitMapper habitMapper;

    public HabitDto findById(long habitId, long userId) {
        HabitRedisEntity cached = habitRedisRepository.findById(habitId);
        if (cached != null) return habitMapper.toDto(cached);

        HabitJpaEntity entity = habitJpaRepository.findById(habitId)
                .orElseThrow(() -> new EntityNotFoundException("Habit not found: " + habitId));

        HabitDto dto = habitMapper.toDto(entity);

        String description = descriptionsRedisRepository.getHabitDescriptions(userId).get(habitId);
        List<Byte> data = dataRedisRepository.getHabitData(userId).get(habitId);

        if (description != null) dto.setDescription(description);
        if (data != null && data.size() == 2) {
            dto.setTargetPerWeek(data.get(0));
            dto.setCurrentWeekProgress(data.get(1));
        }

        habitRedisRepository.save(habitMapper.toRedisEntity(dto), habitId);
        return dto;
    }

    public Page<HabitDto> findAll(Pageable pageable) {
        return habitJpaRepository.findAll((org.springframework.data.domain.Pageable) pageable).map(habitMapper::toDto);
    }

    public Page<HabitDto> findAllByUserId(Pageable pageable, long userId) {
        Map<Long, String> descriptions = descriptionsRedisRepository.getHabitDescriptions(userId);
        Map<Long, List<Byte>> dataMap = dataRedisRepository.getHabitData(userId);

        List<HabitDto> result = new ArrayList<>();

        for (Long habitId : descriptions.keySet()) {
            HabitJpaEntity entity = habitJpaRepository.findById(habitId)
                    .orElseThrow(() -> new EntityNotFoundException("Habit not found: " + habitId));
            HabitDto dto = habitMapper.toDto(entity);
            dto.setDescription(descriptions.get(habitId));
            List<Byte> data = dataMap.get(habitId);
            if (data != null && data.size() == 2) {
                dto.setTargetPerWeek(data.get(0));
                dto.setCurrentWeekProgress(data.get(1));
            }
            result.add(dto);
        }

        return new PageImpl<>(result, (org.springframework.data.domain.Pageable) pageable, result.size());
    }

    public HabitDto save(HabitSaveRequest request) {
        primeService.checkPrime(request.userId());
        HabitJpaEntity entity = habitMapper.toJpaEntity(request);

        if (habitJpaRepository.findByName(entity.getName()) == null) {
            habitJpaRepository.save(entity);
        }

        long habitId = entity.getId();
        long userId = request.userId();
        String description = request.description();
        List<Byte> data = List.of(request.currentWeekProgress(), request.targetPerWeek());

        updateDescriptionIfNeeded(habitId, userId, description);
        updateDataIfNeeded(habitId, userId, data);

        return habitMapper.toDto(entity);
    }

    private void updateDescriptionIfNeeded(long habitId, long userId, String newDescription) {
        Map<Long, String> existing = descriptionsRedisRepository.getHabitDescriptions(userId);
        String current = existing.get(habitId);

        if (!newDescription.equals(current)) {
            Optional<String> userStr = descriptionsRedisRepository.getUserWithDescription(habitId, newDescription);
            List<Long> users = userStr.map(this::parseLongList).orElse(new ArrayList<>());
            if (!users.contains(userId)) users.add(userId);

            DescriptionsDto dto = descriptionsRedisRepository.findById(habitId);
            dto.userIdsDescriptions().put(newDescription, stringify(users));
            descriptionsRedisRepository.save(dto, habitId);
        }
    }

    private void updateDataIfNeeded(long habitId, long userId, List<Byte> newData) {
        Map<Long, List<Byte>> existing = dataRedisRepository.getHabitData(userId);
        List<Byte> current = existing.get(habitId);

        if (!newData.equals(current)) {
            Optional<String> userStr = dataRedisRepository.getUsersWithData(habitId, newData);
            List<Long> users = userStr.map(this::parseLongList).orElse(new ArrayList<>());
            if (!users.contains(userId)) users.add(userId);

            DataDto dto = dataRedisRepository.findById(habitId);
            dto.userIdsData().put(stringify(newData), stringify(users));
            dataRedisRepository.save(dto, habitId);
        }
    }

    private String stringify(List<?> list) {
        return list.toString(); // produces "[1, 2, 3]"
    }

    private List<Long> parseLongList(String raw) {
        if (raw == null || raw.isBlank()) return new ArrayList<>();
        raw = raw.replaceAll("[\\[\\]\\s]", "");
        if (raw.isEmpty()) return new ArrayList<>();

        String[] parts = raw.split(",");
        List<Long> result = new ArrayList<>();
        for (String part : parts) {
            try {
                result.add(Long.parseLong(part.trim()));
            } catch (NumberFormatException ignored) {}
        }
        return result;
    }

    public void deleteById(long habitId) {
        habitJpaRepository.deleteById(habitId);
        habitRedisRepository.deleteById(habitId);
        descriptionsRedisRepository.deleteByHabitId(habitId);
        dataRedisRepository.deleteByHabitId(habitId);
    }

    public void deleteByUserId(long habitId, long userId) {
        descriptionsRedisRepository.deleteForUser(habitId, userId);
        dataRedisRepository.deleteForUser(habitId, userId);
        habitRedisRepository.deleteForUser(habitId, userId);
    }

}
