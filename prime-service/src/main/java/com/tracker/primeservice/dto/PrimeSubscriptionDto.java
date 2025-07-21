package com.tracker.primeservice.dto;

import com.tracker.primeservice.entity.enums.Currency;
import com.tracker.primeservice.entity.enums.SubscribeType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PrimeSubscriptionDto {
    private Long userId;
    private SubscribeType type;
    private Currency currency;
    private boolean active;
    private LocalDate startDate;
    private LocalDate endDate;
}