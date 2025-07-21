package com.tracker.userservice.mapper;

import com.tracker.userservice.dto.UserDto;
import com.tracker.userservice.entity.User;
import com.tracker.userservice.request.UserRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto entityToDto(User user);

    User requestToEntity(UserRequest userRequest);

    User toDTO(User user);

}
