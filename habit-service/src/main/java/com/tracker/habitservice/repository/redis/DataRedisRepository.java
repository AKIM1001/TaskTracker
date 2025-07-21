package com.tracker.habitservice.repository.redis;

import com.tracker.habitservice.dto.DataDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.parser.Parser;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.yaml.snakeyaml.nodes.Tag.PREFIX;

@Repository
public class DataRedisRepository extends AuxiliaryEntityRedisRepository<DataDto> {

    private final Parser parser;
    @Autowired
    public DataRedisRepository(RedisTemplate<String, Object> redisTemplate, Parser parser) {
        super(redisTemplate, "data:", DataDto.class);
        this.parser = parser;
    }

    public Map<Long, List<Byte>> getHabitData(long userId) {
        return findAll()
                .stream()
                .filter(d -> getHabitDataForUser(userId, d.userIdsData()) != null)
                .map(d -> Map.entry(d.habitId(),
                        stringArrayToNumsArray(getHabitDataForUser(userId, d.userIdsData()),
                                Byte::parseByte)))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Optional<String> getUsersWithData(long habitId, List<Byte> data) {
        String key = PREFIX + habitId;

        DataDto dto = (DataDto) redisTemplate.opsForValue().get(key);
        if (dto == null) return Optional.empty();

        Map<String, String> dataMap = dto.userIdsData();
        String dataKey = parser.toString(); // Пример: "[5,3]"

        return Optional.ofNullable(dataMap.get(dataKey));
    }

}
