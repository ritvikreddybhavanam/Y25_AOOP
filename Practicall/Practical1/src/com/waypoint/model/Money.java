package com.waypoint.model;

import java.util.*;

public record Money(long paise, String currency) {

    public Money {
        if (paise < 0) {
            throw new IllegalArgumentException("Money cannot be negative");
        }

        if (currency == null || currency.isBlank()) {
            throw new IllegalArgumentException("Currency cannot be empty");
        }

        currency = currency.toUpperCase(Locale.ROOT);
    }

    public Money plus(Money other) {
        checkCurrency(other);

        return new Money(
                Math.addExact(paise, other.paise),
                currency
        );
    }

    public Money minus(Money other) {
        checkCurrency(other);

        if (paise < other.paise) {
            throw new IllegalArgumentException("Insufficient amount");
        }

        return new Money(paise - other.paise, currency);
    }

    public Money times(long quantity) {

        if (quantity < 0) {
            throw new IllegalArgumentException("Invalid quantity");
        }

        return new Money(
                Math.multiplyExact(paise, quantity),
                currency
        );
    }

    public List<Money> allocate(int parts) {

        if (parts <= 0) {
            throw new IllegalArgumentException("Invalid number of parts");
        }

        List<Money> result = new ArrayList<>();

        long baseAmount = paise / parts;
        long remainder = paise % parts;

        for (int i = 0; i < parts; i++) {

            long amount = baseAmount;

            if (i < remainder) {
                amount++;
            }

            result.add(new Money(amount, currency));
        }

        return List.copyOf(result);
    }

    private void checkCurrency(Money other) {

        if (other == null || !currency.equals(other.currency)) {
            throw new IllegalArgumentException("Currency mismatch");
        }
    }

    @Override
    public String toString() {
        return String.format(
                Locale.ROOT,
                "%s %d.%02d",
                currency,
                paise / 100,
                paise % 100
        );
    }
}