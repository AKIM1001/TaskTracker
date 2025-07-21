package com.tracker.primeservice.service;

import com.tracker.primeservice.dto.PrimeSubscriptionDto;
import com.tracker.primeservice.request.PurchaseRequest;
import com.tracker.primeservice.entity.PrimeSubscription;
import com.tracker.primeservice.entity.enums.Currency;
import com.tracker.primeservice.entity.enums.SubscribeType;
import com.tracker.primeservice.mapper.PrimeSubscriptionMapper;
import com.tracker.primeservice.repository.PrimeJpaRepository;
import com.tracker.primeservice.repository.PriceJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class PrimeService {

    private final PrimeJpaRepository repository;
    private final PriceJpaRepository priceRepository;
    private final PrimeSubscriptionMapper mapper;

    public PrimeSubscriptionDto purchase(PurchaseRequest request) {
        Long userId = request.getUserId();
        SubscribeType type = request.getType();
        Currency currency = request.getCurrency();

        double amount = priceRepository.findByTypeAndCurrency(type, currency)
                .orElseThrow(() -> new RuntimeException("Price not set for " + type + " in " + currency))
                .getAmount();

        simulatePayment(userId, type, currency, amount);

        PrimeSubscription subscription = new PrimeSubscription();
        subscription.setUserId(userId);
        subscription.setType(type);
        subscription.setCurrency(currency);
        subscription.setActive(true);

        LocalDate now = LocalDate.now();
        subscription.setStartDate(now);
        subscription.setEndDate(
                type == SubscribeType.LIFETIME ? LocalDate.of(9999, 12, 31) : now.plusDays(type.getDays())
        );

        PrimeSubscription saved = repository.save(subscription);
        return mapper.toDto(saved);
    }

    public boolean hasActiveSubscription(Long userId) {
        return repository.findByUserIdAndActiveTrue(userId)
                .map(sub -> sub.getEndDate().isAfter(LocalDate.now()))
                .orElse(false);
    }

    private void simulatePayment(Long userId, SubscribeType type, Currency currency, double amount) {
        try {
            System.out.printf("Пользователь %d оплачивает %.2f %s за %s%n",
                    userId, amount, currency.name(), type.name());
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {}
    }
}
