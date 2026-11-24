public record CustomerId(int value) {
    public CustomerId {
        if (value <= 0) {
            throw new IllegalArgumentException("Invalid customer ID");
        }
    }
}