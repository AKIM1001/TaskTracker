package com.tracker.primeservice.entity.enums;

public enum SubscribeType {
    MONTHLY(30), QUARTERLY(90), YEARLY(365), LIFETIME(9999);

    private final int days;

    SubscribeType(int days) {
        this.days = days;
    }

    public int getDays() {
        return days;
    }
}
