package com.tracker.airecommendationservice.service;


import com.tracker.airecommendationservice.repository.HabitRedisRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;




@Service
@RequiredArgsConstructor
public class PrimeService {

    private final HabitRedisRepository habitRedisRepository;

    public boolean checkPrime(long userId) {
        return habitRedisRepository.checkPrime(userId);
    }
}
