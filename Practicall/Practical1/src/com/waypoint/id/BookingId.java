package com.waypoint.id;

public record BookingId(int value) {
    public BookingId {
        if (value <= 0) {
            throw new IllegalArgumentException("Invalid Booking ID");
        }
    }
}