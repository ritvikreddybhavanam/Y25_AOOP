enum SpotStatus {
    AVAILABLE, OCCUPIED
}

record ParkingSpotView(
        ParkingSpotId spotId,
        String location,
        String spotType,
        SpotStatus status,
        String vehicleNumber
) {}

sealed abstract class ParkingSpot
        permits CarParkingSpot, BikeParkingSpot, ElectricParkingSpot {

    private final ParkingSpotId spotId;
    private final String location;

    private SpotStatus status;
    private String vehicleNumber;

    public ParkingSpot(ParkingSpotId spotId, String location) {
        if (spotId == null || location == null || location.isBlank())
            throw new IllegalArgumentException("Invalid parking spot details");

        this.spotId = spotId;
        this.location = location;
        this.status = SpotStatus.AVAILABLE;
        this.vehicleNumber = null;
    }

    protected abstract Money getHourlyRate();

    public abstract String getSpotType();

    public final Money calculateFare(int hours) {
        if (hours <= 0)
            throw new IllegalArgumentException("Hours must be positive");

        return getHourlyRate().times(hours);
    }

    public final boolean isAvailable() {
        return status == SpotStatus.AVAILABLE;
    }

    public final void parkVehicle(String vehicleNumber) {
        if (!isAvailable())
            throw new IllegalStateException("Parking spot is already occupied");

        if (vehicleNumber == null || vehicleNumber.isBlank())
            throw new IllegalArgumentException("Vehicle number is required");

        this.vehicleNumber = vehicleNumber;
        this.status = SpotStatus.OCCUPIED;
    }

    public final void releaseSpot() {
        if (isAvailable())
            throw new IllegalStateException("Parking spot is already available");

        this.vehicleNumber = null;
        this.status = SpotStatus.AVAILABLE;
    }

    public final ParkingSpotId getSpotId() {
        return spotId;
    }

    public final ParkingSpotView getSnapshot() {
        return new ParkingSpotView(
                spotId, location, getSpotType(), status, vehicleNumber
        );
    }
}

final class CarParkingSpot extends ParkingSpot {

    public CarParkingSpot(ParkingSpotId id, String location) {
        super(id, location);
    }

    @Override
    protected Money getHourlyRate() {
        return new Money(5000, "INR");
    }

    @Override
    public String getSpotType() {
        return "CAR";
    }
}

final class BikeParkingSpot extends ParkingSpot {

    public BikeParkingSpot(ParkingSpotId id, String location) {
        super(id, location);
    }

    @Override
    protected Money getHourlyRate() {
        return new Money(2000, "INR");
    }

    @Override
    public String getSpotType() {
        return "BIKE";
    }
}

final class ElectricParkingSpot extends ParkingSpot {

    public ElectricParkingSpot(ParkingSpotId id, String location) {
        super(id, location);
    }

    @Override
    protected Money getHourlyRate() {
        return new Money(7000, "INR");
    }

    @Override
    public String getSpotType() {
        return "ELECTRIC VEHICLE";
    }
}