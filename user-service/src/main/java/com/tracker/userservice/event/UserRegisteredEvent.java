package com.tracker.userservice.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRegisteredEvent {
    private String correlationId;
    private String userId;
    private boolean userExists;
}
