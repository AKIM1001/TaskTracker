package com.tracker.primeservice.entity;

import com.tracker.primeservice.entity.enums.Currency;
import com.tracker.primeservice.entity.enums.SubscribeType;
import jakarta.persistence.*;
import lombok.*;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PrimeSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;

    @Enumerated(EnumType.STRING)
    private SubscribeType type;

    @Enumerated(EnumType.STRING)
    private Currency currency;


}
