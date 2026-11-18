package com.waypoint.id;

public record ParkingSpotId(int value) {
    public ParkingSpotId {
        if (value <= 0) {
            throw new IllegalArgumentException("Invalid Parking Spot ID");
        }
    }
}