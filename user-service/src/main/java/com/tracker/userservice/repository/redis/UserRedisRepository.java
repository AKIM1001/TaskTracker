package com.tracker.userservice.repository.redis;

import com.tracker.userservice.dto.UserDto;
import com.tracker.userservice.entity.User;
import com.tracker.userservice.mapper.UserMapper;
import com.tracker.userservice.repository.jpa.UserJpaRepository;
import com.tracker.userservice.request.UserRequest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Repository;

import java.util.stream.Collectors;

@Repository
public class UserRedisRepository {

    private final RedisTemplate<String, UserDto> redisTemplate;
    private final String prefix = "user_";
    private final UserMapper userMapper;
    private final UserJpaRepository userJpaRepository;

    public UserRedisRepository(
            RedisTemplate<String, UserDto> redisTemplate,
            UserMapper userMapper,
            UserJpaRepository userJpaRepository
    ) {
        this.redisTemplate = redisTemplate;
        this.userMapper = userMapper;
        this.userJpaRepository = userJpaRepository;
    }

    public UserDto save(UserRequest userRequest) {
        User user = userJpaRepository.save(userMapper.requestToEntity(userRequest));
        UserDto userDTO = userMapper.entityToDto(user);
        redisTemplate.opsForValue().set(prefix + userDTO.getId(), userDTO);
        return userDTO;
    }

    public void saveDTO(UserDto userDTO) {
        redisTemplate.opsForValue().set(prefix + userDTO.getId(), userDTO);
    }

    public UserDto getById(long id) {
        Object obj = redisTemplate.opsForValue().get(prefix + id);
        if (obj instanceof UserDto dto) {
            return dto;
        } else if (obj != null) {
            throw new IllegalStateException("Cached object is not of type UserDTO");
        }
        return null;
    }

    public UserDto getByEmail(String email) {
        Object obj = redisTemplate.opsForValue().get(prefix + email);
        if (obj instanceof UserDto dto) {
            return dto;
        } else if (obj != null) {
            throw new IllegalStateException("Cached object is not of type UserDTO");
        }
        return null;
    }

    public List<UserDto> findAll() {
        Set<String> keys = redisTemplate.keys(prefix + "*");
        if (keys == null || keys.isEmpty()) return Collections.emptyList();

        List<Object> values = Collections.singletonList(redisTemplate.opsForValue().multiGet(keys));
        if (values == null || values.isEmpty()) return Collections.emptyList();

        return values.stream()
                .filter(UserDto.class::isInstance)
                .map(UserDto.class::cast)
                .collect(Collectors.toList());
    }

    public void deleteById(long id) {
        redisTemplate.delete(prefix + id);
    }


}
