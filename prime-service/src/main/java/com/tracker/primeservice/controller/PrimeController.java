package com.tracker.primeservice.controller;

import com.tracker.primeservice.entity.PrimeSubscription;
import com.tracker.primeservice.entity.enums.Currency;
import com.tracker.primeservice.entity.enums.SubscribeType;
import com.tracker.primeservice.service.PrimeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/prime")
@RequiredArgsConstructor
public class PrimeController {

    private final PrimeService service;

    @PostMapping("/subscribe")
    public ResponseEntity<?> subscribe(@RequestParam Long userId,
                                       @RequestParam SubscribeType type,
                                       @RequestParam Currency currency) {
        PrimeSubscription sub = service.purchase(userId, type, currency);
        return ResponseEntity.ok(sub);
    }

    @GetMapping("/status/{userId}")
    public ResponseEntity<Boolean> check(@PathVariable Long userId) {
        return ResponseEntity.ok(service.hasActiveSubscription(userId));
    }
}


