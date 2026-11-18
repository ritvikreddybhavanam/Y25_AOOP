package com.waypoint.model;

import com.waypoint.id.ParkingSpotId;

public class ParkingSpot {

    private final ParkingSpotId spotId;
    private final String location;

    private SpotStatus status;
    private String vehicleNumber;

    public ParkingSpot(ParkingSpotId spotId, String location) {

        if (spotId == null || location == null || location.isBlank()) {
            throw new IllegalArgumentException("Invalid parking spot details");
        }

        this.spotId = spotId;
        this.location = location;
        this.status = SpotStatus.AVAILABLE;
        this.vehicleNumber = null;
    }

    public boolean isAvailable() {
        return status == SpotStatus.AVAILABLE;
    }

    public void parkVehicle(String vehicleNumber) {

        if (!isAvailable()) {
            throw new IllegalStateException("Parking spot is already occupied");
        }

        if (vehicleNumber == null || vehicleNumber.isBlank()) {
            throw new IllegalArgumentException("Invalid vehicle number");
        }

        this.vehicleNumber = vehicleNumber;
        this.status = SpotStatus.OCCUPIED;
    }

    public void releaseSpot() {

        if (isAvailable()) {
            throw new IllegalStateException("Parking spot is already available");
        }

        this.vehicleNumber = null;
        this.status = SpotStatus.AVAILABLE;
    }

    public ParkingSpotView getSnapshot() {

        return new ParkingSpotView(
                spotId,
                location,
                status,
                vehicleNumber
        );
    }
}