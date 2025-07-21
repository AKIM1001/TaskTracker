package com.tracker.habitservice.repository.redis;

import com.tracker.habitservice.dto.DescriptionsDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.yaml.snakeyaml.nodes.Tag.PREFIX;

@Repository
public class DescriptionsRedisRepository extends AuxiliaryEntityRedisRepository<DescriptionsDto> {
    @Autowired
    public DescriptionsRedisRepository(RedisTemplate<String, Object> redisTemplate) {
        super(redisTemplate, "descrs:", DescriptionsDto.class);
    }

    public Map<Long, String> getHabitDescriptions(long userId) {
        return findAll()
                .stream()
                .filter(d -> getHabitDataForUser(userId,
                        d.userIdsDescriptions()) != null)
                .map(d -> Map.entry(d.habitId(), getHabitDataForUser(userId,
                        d.userIdsDescriptions())))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Optional<String> getUserWithDescription(long habitId, String description) {
        String key = PREFIX + habitId;

        DescriptionsDto dto = (DescriptionsDto) redisTemplate.opsForValue().get(key);
        if (dto == null) return Optional.empty();

        Map<String, String> map = dto.userIdsDescriptions();

        return Optional.ofNullable(map.get(description));
    }
}