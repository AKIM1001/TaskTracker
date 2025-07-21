package com.tracker.airecommendationservice.event;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrimeSubscriptionEvent {
    private Long userId;
    private boolean active;
}