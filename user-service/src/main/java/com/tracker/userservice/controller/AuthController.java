package com.tracker.userservice.controller;
import com.tracker.userservice.dto.JwtAuthenticationDto;
import com.tracker.userservice.dto.RefreshTokenDto;
import com.tracker.userservice.dto.UserCreadentialsDto;
import com.tracker.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<JwtAuthenticationDto> signIn(@RequestBody UserCreadentialsDto userCredentialsDto) {
        try {
            JwtAuthenticationDto jwtAuthentication = authService.signIn(userCredentialsDto);
            return ResponseEntity.ok(jwtAuthentication);
        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }

    @PostMapping("/refresh")
    public ResponseEntity<JwtAuthenticationDto> refresh(@RequestBody RefreshTokenDto refreshTokenDto) {
        try {
            JwtAuthenticationDto jwtAuthentication = authService.refreshToken(refreshTokenDto);
            return ResponseEntity.ok(jwtAuthentication);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }
    }
}
