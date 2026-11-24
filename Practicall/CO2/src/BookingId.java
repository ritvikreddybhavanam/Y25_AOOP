public record BookingId(int value) {
    public BookingId {
        if (value <= 0) {
            throw new IllegalArgumentException("Invalid booking Id");
        }
    }
}