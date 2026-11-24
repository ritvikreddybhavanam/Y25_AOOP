package com.waypoint.model;

import com.waypoint.id.ParkingSpotId;

public record ParkingSpotView(
        ParkingSpotId spotId,
        String location,
        SpotStatus status,
        String vehicleNumber
) {}