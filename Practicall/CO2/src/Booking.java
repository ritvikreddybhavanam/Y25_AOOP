enum BookingStatus {
    PENDING, CONFIRMED, CANCELLED
}

record BookingView(
        BookingId bookingId,
        CustomerId customerId,
        ParkingSpotId spotId,
        String vehicleNumber,
        Money fare,
        BookingStatus status
) {}

public class Booking {

    private final BookingId bookingId;
    private final CustomerId customerId;
    private final ParkingSpotId spotId;
    private final String vehicleNumber;
    private final Money fare;

    private BookingStatus status;
    private PaymentReceipt paymentReceipt;

    public Booking(
            BookingId bookingId,
            CustomerId customerId,
            ParkingSpotId spotId,
            String vehicleNumber,
            Money fare
    ) {
        if (bookingId == null || customerId == null ||
                spotId == null || vehicleNumber == null ||
                vehicleNumber.isBlank() || fare == null) {
            throw new IllegalArgumentException("Invalid booking details");
        }

        this.bookingId = bookingId;
        this.customerId = customerId;
        this.spotId = spotId;
        this.vehicleNumber = vehicleNumber;
        this.fare = fare;

        this.status = BookingStatus.PENDING;
        this.paymentReceipt = null;
    }

    public void confirm(PaymentReceipt receipt) {
        if (status != BookingStatus.PENDING)
            throw new IllegalStateException("Booking is not pending");

        if (receipt == null || !receipt.successful())
            throw new IllegalArgumentException("Payment failed");

        if (!receipt.bookingId().equals(bookingId) ||
                !receipt.amount().equals(fare)) {
            throw new IllegalArgumentException("Payment receipt mismatch");
        }

        this.paymentReceipt = receipt;
        this.status = BookingStatus.CONFIRMED;
    }

    public void cancel() {
        if (status != BookingStatus.CONFIRMED)
            throw new IllegalStateException("Only confirmed bookings can be cancelled");

        this.status = BookingStatus.CANCELLED;
    }

    public BookingId getBookingId() {
        return bookingId;
    }

    public ParkingSpotId getSpotId() {
        return spotId;
    }

    public BookingStatus getStatus() {
        return status;
    }

    public PaymentReceipt getPaymentReceipt() {
        return paymentReceipt;
    }

    public BookingView getSnapshot() {
        return new BookingView(
                bookingId, customerId, spotId,
                vehicleNumber, fare, status
        );
    }
}