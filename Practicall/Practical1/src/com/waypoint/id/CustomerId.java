package com.waypoint.id;

public record CustomerId(int value) {
    public CustomerId {
        if (value <= 0) {
            throw new IllegalArgumentException("Invalid Customer ID");
        }
    }
}