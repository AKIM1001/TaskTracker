package com.tracker.userservice.dto;

import lombok.Data;

@Data
public class JwtAuthenticationDto {
    private String token;
    private String refreshToken;
    private String accessToken;
}
