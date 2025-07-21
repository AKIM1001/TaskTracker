package com.tracker.primeservice.repository;


import com.tracker.primeservice.entity.SubscribePrice;
import com.tracker.primeservice.entity.enums.Currency;
import com.tracker.primeservice.entity.enums.SubscribeType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceJpaRepository extends JpaRepository<SubscribePrice, Long> {
    Optional<SubscribePrice> findByTypeAndCurrency(SubscribeType type, Currency currency);
}
