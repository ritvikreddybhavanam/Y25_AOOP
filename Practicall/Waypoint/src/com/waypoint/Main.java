package com.waypoint;

import com.waypoint.id.*;
import com.waypoint.model.*;
import com.waypoint.service.Waypoint;

public class Main {

    public static void main(String[] args) {

        Waypoint waypoint = new Waypoint();


        Customer customer = new Customer(
                new CustomerId(101),
                "Ramya"
        );

        waypoint.registerCustomer(customer);

        System.out.println("Customer registered successfully.");


        ParkingSpot spot1 = new ParkingSpot(
                new ParkingSpotId(201),
                "Bachupally"
        );

        ParkingSpot spot2 = new ParkingSpot(
                new ParkingSpotId(202),
                "Nizampet"
        );

        waypoint.addParkingSpot(spot1);
        waypoint.addParkingSpot(spot2);

        System.out.println("Parking spots added successfully.");


        waypoint.registerVehicle("TS09AB1234");

        System.out.println("Vehicle registered successfully.");

        System.out.println("\n----- PARKING AVAILABILITY -----");

        for (ParkingSpotView view : waypoint.getParkingSpots()) {

            System.out.println("Spot ID: " + view.spotId().value());
            System.out.println("Location: " + view.location());
            System.out.println("Status: " + view.status());

            System.out.println("---------------------------");
        }

        System.out.println("\n----- BOOKING PARKING -----");

        Booking booking = waypoint.reserve(
                new CustomerId(101),
                new ParkingSpotId(201),
                "TS09AB1234",
                2,
                new Money(5000, "INR")
        );

        System.out.println(
                "Booking ID: " + booking.getBookingId().value()
        );

        System.out.println(
                "Total Fare: " + booking.getFare()
        );

        System.out.println(
                "Payment Status: " + booking.getPayment().successful()
        );

        System.out.println(
                "Booking Status: " + booking.getStatus()
        );

        System.out.println("\n----- TRYING DUPLICATE BOOKING -----");

        try {

            waypoint.reserve(
                    new CustomerId(101),
                    new ParkingSpotId(201),
                    "TS09AB1234",
                    1,
                    new Money(5000, "INR")
            );

        } catch (IllegalStateException exception) {

            System.out.println(
                    "Booking failed: " + exception.getMessage()
            );
        }

        System.out.println("\n----- CANCELLING BOOKING -----");

        waypoint.cancelBooking(booking.getBookingId());

        System.out.println(
                "Booking Status After Cancellation: " + booking.getStatus()
        );

        System.out.println(
                "\n----- PARKING AVAILABILITY AFTER CANCELLATION -----"
        );

        for (ParkingSpotView view : waypoint.getParkingSpots()) {

            System.out.println("Spot ID: " + view.spotId().value());
            System.out.println("Location: " + view.location());
            System.out.println("Status: " + view.status());

            System.out.println("---------------------------");
        }

        waypoint.displayBookings();
    }
}