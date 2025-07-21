package com.tracker.primeservice.request;

import com.tracker.primeservice.entity.enums.Currency;
import com.tracker.primeservice.entity.enums.SubscribeType;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseRequest {
    private Long userId;
    private SubscribeType type;
    private Currency currency;
}
