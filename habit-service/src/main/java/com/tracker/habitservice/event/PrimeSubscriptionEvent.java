package com.tracker.habitservice.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrimeSubscriptionEvent {
    private Long userId;
    private boolean active;
}