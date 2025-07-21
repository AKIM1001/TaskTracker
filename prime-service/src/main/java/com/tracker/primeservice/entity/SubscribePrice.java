package com.tracker.primeservice.entity;

import com.tracker.primeservice.entity.enums.Currency;
import com.tracker.primeservice.entity.enums.SubscribeType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class SubscribePrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private SubscribeType type;

    @Enumerated(EnumType.STRING)
    private Currency currency;

    private double amount;
}

