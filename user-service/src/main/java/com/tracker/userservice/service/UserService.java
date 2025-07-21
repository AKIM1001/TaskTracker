package com.tracker.userservice.service;

import com.tracker.userservice.dto.JwtAuthenticationDto;
import com.tracker.userservice.dto.RefreshTokenDto;
import com.tracker.userservice.dto.UserCreadentialsDto;
import jakarta.persistence.EntityNotFoundException;
import com.tracker.userservice.dto.UserDto;
import com.tracker.userservice.entity.User;
import com.tracker.userservice.expetions.NoSuchUserException;
import com.tracker.userservice.mapper.UserMapper;
import com.tracker.userservice.repository.jpa.UserJpaRepository;
import com.tracker.userservice.repository.redis.UserRedisRepository;
import com.tracker.userservice.request.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserJpaRepository userJpaRepository;
    private final UserRedisRepository userRedisRepository;
    private final UserMapper userMapper;
    private static final String DEFAULT_ANSWER = "default";

    @Autowired
    public UserService(UserJpaRepository userJpaRepository,
                       UserRedisRepository userRedisRepository,
                       UserMapper userMapper) {
        this.userJpaRepository = userJpaRepository;
        this.userRedisRepository = userRedisRepository;
        this.userMapper = userMapper;
    }


    public UserDto save(UserRequest userRequest) {
        User user = userJpaRepository.save(userMapper.requestToEntity(userRequest));

        userRedisRepository.deleteById(user.getId());
        UserDto userDTO = userMapper.entityToDto(user);
        userRedisRepository.saveDTO(userDTO);

        return userDTO;
    }

    public Page<UserDto> findAll(Pageable pageable) {
        return userJpaRepository.findAll(pageable).map(userMapper::entityToDto);
    }


    public UserDto findById(long id) throws NoSuchUserException {
        UserDto cachedUser = userRedisRepository.getById(id);
        if (cachedUser != null) {
            return cachedUser;
        }

        User user = userJpaRepository.findById(id).orElseThrow(NoSuchUserException::new);
        UserDto userDTO = userMapper.entityToDto(user);
        userRedisRepository.saveDTO(userDTO);

        return userDTO;
    }

    public UserDto findByEmail(String email) throws NoSuchUserException {
        UserDto cachedUser = userRedisRepository.getByEmail(email);
        if (cachedUser != null) {
            return cachedUser;
        }

        User user = (User) userJpaRepository.findByEmail(email).orElseThrow(NoSuchUserException::new);
        UserDto userDTO = userMapper.entityToDto(user);
        userRedisRepository.saveDTO(userDTO);

        return userDTO;
    }

    public void deleteById(long id) throws EntityNotFoundException {
        if (!userJpaRepository.existsById(id)) {
            throw new EntityNotFoundException();
        }
        userJpaRepository.deleteById(id);
        userRedisRepository.deleteById(id);
    }

    public void logoutUser(String userId) {
        try {
            Long id = Long.parseLong(userId);
            userRedisRepository.deleteById(id);
        } catch (NumberFormatException e) {
            System.out.println("Invalid userId for logout: " + userId);
        }
    }

    public JwtAuthenticationDto signIn(UserCreadentialsDto userCredentialsDto) {
        JwtAuthenticationDto jwt = new JwtAuthenticationDto();
        jwt.setAccessToken("fake-access-token");
        jwt.setRefreshToken("fake-refresh-token");
        return jwt;
    }

    public JwtAuthenticationDto refreshToken(RefreshTokenDto refreshTokenDto) {
        JwtAuthenticationDto jwt = new JwtAuthenticationDto();
        jwt.setAccessToken("new-fake-access-token");
        jwt.setRefreshToken("new-fake-refresh-token");
        return jwt;
    }

}
