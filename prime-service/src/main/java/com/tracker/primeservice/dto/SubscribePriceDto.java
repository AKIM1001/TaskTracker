package com.tracker.primeservice.dto;

import com.tracker.primeservice.entity.enums.Currency;
import com.tracker.primeservice.entity.enums.SubscribeType;
import lombok.Data;

@Data
public class SubscribePriceDto {
    private Long id;
    private SubscribeType type;
    private Currency currency;
    private Double amount;
}
