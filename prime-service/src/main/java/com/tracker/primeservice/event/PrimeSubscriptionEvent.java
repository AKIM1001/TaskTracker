package com.tracker.primeservice.event;

import com.tracker.primeservice.entity.enums.SubscribeType;
import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrimeSubscriptionEvent {
    private Long userId;
    private SubscribeType type;
    private LocalDate startDate;
    private LocalDate endDate;
}