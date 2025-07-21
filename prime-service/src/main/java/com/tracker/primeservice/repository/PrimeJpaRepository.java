package com.tracker.primeservice.repository;

import com.tracker.primeservice.entity.PrimeSubscription;
import com.tracker.primeservice.entity.enums.Currency;
import com.tracker.primeservice.entity.enums.SubscribeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrimeJpaRepository extends JpaRepository<PrimeSubscription,Long> {
    Optional<PrimeSubscription> findByUserIdAndActiveTrue(Long userId);

    Optional<Object> findByTypeAndCurrency(SubscribeType type, Currency currency);
}
