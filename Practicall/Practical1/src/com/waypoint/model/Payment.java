package com.waypoint.model;

import com.waypoint.id.PaymentId;
import com.waypoint.id.BookingId;

public record Payment(
        PaymentId paymentId,
        BookingId bookingId,
        Money amount,
        boolean successful
) {

    public Payment {

        if (paymentId == null ||
                bookingId == null ||
                amount == null) {

            throw new IllegalArgumentException("Invalid payment details");
        }
    }
}