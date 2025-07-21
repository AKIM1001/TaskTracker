package com.tracker.airecommendationservice.service;

import com.tracker.airecommendationservice.dto.UserDto;
import com.tracker.airecommendationservice.repository.UserRedisRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRedisRepository userRedisRepository;
    private final Random random = new Random();

    public void save(UserDto userDto) {
        userRedisRepository.save(userDto, userDto.id());
    }

    public UserDto getById(long id) {
        return userRedisRepository.findById(id);
    }

    public Long getRandomUserId() {
        List<UserDto> users = userRedisRepository.findAll();

        return users
                .stream()
                .skip(random.nextInt(users.size()))
                .findFirst()
                .map(UserDto::id)
                .orElse(null);
    }
}
