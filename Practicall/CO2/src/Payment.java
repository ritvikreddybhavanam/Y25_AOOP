record PaymentReceipt(PaymentId paymentId, BookingId bookingId, Money amount, boolean successful) {
    public PaymentReceipt {
        if (paymentId == null || bookingId == null || amount == null)
            throw new IllegalArgumentException("Invalid payment receipt");
    }
}

interface PaymentMethod {
    PaymentReceipt pay(PaymentId paymentId, BookingId bookingId, Money amount);
}

class UPIPayment implements PaymentMethod {

    @Override
    public PaymentReceipt pay(PaymentId paymentId, BookingId bookingId, Money amount) {
        System.out.println("Processing UPI payment: " + amount);
        return new PaymentReceipt(paymentId, bookingId, amount, true);
    }
}

class CardPayment implements PaymentMethod {

    @Override
    public PaymentReceipt pay(PaymentId paymentId, BookingId bookingId, Money amount) {
        System.out.println("Processing Card payment: " + amount);

        return new PaymentReceipt(paymentId, bookingId, amount, true);
    }
}