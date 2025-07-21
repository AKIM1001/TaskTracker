package com.tracker.userservice.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRemoveEvent {
    private String correlationId;
    private String userId;
}
