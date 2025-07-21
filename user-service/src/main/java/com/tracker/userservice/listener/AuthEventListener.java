package com.tracker.userservice.listener;

import com.tracker.userservice.entity.User;
import com.tracker.userservice.mapper.UserMapper;
import com.tracker.userservice.repository.jpa.UserJpaRepository;
import com.tracker.userservice.repository.redis.UserRedisRepository;
import com.tracker.userservice.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthEventListener {

    private final UserJpaRepository userJpaRepository;
    private final UserRedisRepository userRedisRepository;
    private final UserMapper userMapper;

    @KafkaListener
    public void handleAuthSuccess(AuthenticationSuccessEvent event) {
        String username = event.getAuthentication().getName();
        log.info("Успешный вход пользователя: {}", username);

        userJpaRepository.findByEmail(username).ifPresentOrElse(user -> {
            UserDto userDto = userMapper.entityToDto((User) user);
            userRedisRepository.saveDTO(userDto);
            log.debug("Пользователь {} сохранён в Redis после входа", username);
        }, () -> log.warn("Пользователь {} не найден в базе при входе", username));
    }

    @KafkaListener
    public void handleAuthFailure(AbstractAuthenticationFailureEvent event) {
        String username = event.getAuthentication().getName();
        log.warn("Неудачная попытка входа пользователя: {}", username);
    }


}
