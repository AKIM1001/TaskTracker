package com.tracker.userservice.dto;

import com.tracker.userservice.entity.Role;

import java.util.Date;

public record UserDto(
        Long id,
        Role role,
        String name,
        String surname,
        String email,
        Date dateOfBirth,
        String hashPassword,
        String telegramAccount) {
    public Long getId() {
        return this.id; // или какое у тебя поле
    }
}
