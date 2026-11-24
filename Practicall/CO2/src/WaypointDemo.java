public class WaypointDemo {

    public static void main(String[] args) {

        PaymentMethod payment = new UPIPayment();

        WaypointService waypoint = new WaypointService(payment);

        Customer customer1 =
                new Customer(new CustomerId(101), "Ramya");

        Customer customer2 =
                new Customer(new CustomerId(102), "Sateesh");

        waypoint.registerCustomer(customer1);
        waypoint.registerCustomer(customer2);

        waypoint.addParkingSpot(
                new CarParkingSpot(new ParkingSpotId(201), "Bachupally")
        );

        waypoint.addParkingSpot(
                new BikeParkingSpot(new ParkingSpotId(202), "Nizampet")
        );

        waypoint.addParkingSpot(
                new ElectricParkingSpot(new ParkingSpotId(203), "Bowrampet")
        );

        waypoint.registerVehicle("TS09AB1234");
        waypoint.registerVehicle("TS10CD5678");
        waypoint.registerVehicle("TS11EF9012");

        System.out.println("----- INITIAL PARKING AVAILABILITY -----");

        for (ParkingSpotView spot : waypoint.getParkingSpots()) {
            System.out.println(
                    "Spot: " + spot.spotId() +
                            " | Location: " + spot.location() +
                            " | Type: " + spot.spotType() +
                            " | Status: " + spot.status()
            );
        }

        System.out.println("\n----- CAR BOOKING -----");

        Booking booking1 = waypoint.reserve(
                new CustomerId(101),
                new ParkingSpotId(201),
                "TS09AB1234",
                2
        );

        System.out.println("Booking ID: " + booking1.getBookingId());
        System.out.println("Payment: " +
                booking1.getPaymentReceipt().amount());
        System.out.println("Status: " + booking1.getStatus());

        System.out.println("\n----- BIKE BOOKING -----");

        Booking booking2 = waypoint.reserve(
                new CustomerId(102),
                new ParkingSpotId(202),
                "TS10CD5678",
                3
        );

        System.out.println("Booking ID: " + booking2.getBookingId());
        System.out.println("Payment: " +
                booking2.getPaymentReceipt().amount());
        System.out.println("Status: " + booking2.getStatus());

        System.out.println("\n----- INVALID BOOKING TEST -----");

        try {
            waypoint.reserve(
                    new CustomerId(102),
                    new ParkingSpotId(201),
                    "TS10CD5678",
                    1
            );

        } catch (IllegalStateException exception) {
            System.out.println("Booking rejected: " + exception.getMessage());
        }

        System.out.println("\n----- CANCELLATION -----");

        waypoint.cancelBooking(booking1.getBookingId());

        System.out.println(
                "Booking " + booking1.getBookingId() +
                        " status: " + booking1.getStatus()
        );

        System.out.println("\n----- FINAL PARKING AVAILABILITY -----");

        for (ParkingSpotView spot : waypoint.getParkingSpots()) {
            System.out.println(
                    "Spot: " + spot.spotId() +
                            " | Location: " + spot.location() +
                            " | Type: " + spot.spotType() +
                            " | Status: " + spot.status() +
                            " | Vehicle: " + spot.vehicleNumber()
            );
        }

        waypoint.displayBookings();
    }
}