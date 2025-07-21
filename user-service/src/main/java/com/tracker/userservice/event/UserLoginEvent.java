package com.tracker.userservice.event;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginEvent {
    private String correlationId;
    private String username;
    private String password;
}
