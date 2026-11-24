public record ParkingSpotId(int value) {
    public ParkingSpotId {
        if (value <= 100) {
            throw new IllegalArgumentException("Invalid parking spot ID");
        }
    }
}