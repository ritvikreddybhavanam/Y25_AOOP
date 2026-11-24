package com.waypoint.id;

public record PaymentId (int value) {
    public PaymentId {
        if (value <= 0) {
            throw new IllegalArgumentException("Invalid Payment ID");
        }
    }
}
