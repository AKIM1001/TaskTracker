package com.tracker.userservice.request;

import jakarta.validation.constraints.NotBlank;
import com.tracker.userservice.entity.Role;

import java.util.Date;

public record UserRequest(
        @NotBlank Long id,
        @NotBlank Role role,
        @NotBlank String name,
        @NotBlank String surname,
        @NotBlank String email,
        @NotBlank Date dateOfBirth,
        @NotBlank String hashPassword,
        @NotBlank String telegramAccount) {
}
