import java.util.*;

public class WaypointService {

    private final Map<CustomerId, Customer> customers = new HashMap<>();
    private final Map<ParkingSpotId, ParkingSpot> spots = new HashMap<>();
    private final Map<BookingId, Booking> bookings = new HashMap<>();

    private final Set<String> registeredVehicles = new HashSet<>();

    private final PaymentMethod paymentMethod;

    private int bookingCounter = 1;
    private int paymentCounter = 1;

    public WaypointService(PaymentMethod paymentMethod) {
        if (paymentMethod == null)
            throw new IllegalArgumentException("Payment method is required");

        this.paymentMethod = paymentMethod;
    }

    public void registerCustomer(Customer customer) {
        if (customer == null)
            throw new IllegalArgumentException("Customer is required");

        if (customers.containsKey(customer.getCustomerId()))
            throw new IllegalArgumentException("Customer already registered");

        customers.put(customer.getCustomerId(), customer);
    }

    public void addParkingSpot(ParkingSpot spot) {
        if (spot == null)
            throw new IllegalArgumentException("Parking spot is required");

        if (spots.containsKey(spot.getSpotId()))
            throw new IllegalArgumentException("Parking spot already exists");

        spots.put(spot.getSpotId(), spot);
    }

    public void registerVehicle(String vehicleNumber) {
        if (vehicleNumber == null || vehicleNumber.isBlank())
            throw new IllegalArgumentException("Vehicle number is required");

        String normalized = vehicleNumber.trim().toUpperCase(Locale.ROOT);

        if (!registeredVehicles.add(normalized))
            throw new IllegalArgumentException("Vehicle already registered");
    }

    public List<ParkingSpotView> getParkingSpots() {
        List<ParkingSpotView> result = new ArrayList<>();

        for (ParkingSpot spot : spots.values()) {
            result.add(spot.getSnapshot());
        }

        return List.copyOf(result);
    }

    public Booking reserve(
            CustomerId customerId,
            ParkingSpotId spotId,
            String vehicleNumber,
            int hours
    ) {
        if (!customers.containsKey(customerId))
            throw new IllegalArgumentException("Customer not found");

        if (hours <= 0)
            throw new IllegalArgumentException("Hours must be positive");

        if (vehicleNumber == null || vehicleNumber.isBlank())
            throw new IllegalArgumentException("Vehicle number is required");

        String normalizedVehicle =
                vehicleNumber.trim().toUpperCase(Locale.ROOT);

        if (!registeredVehicles.contains(normalizedVehicle))
            throw new IllegalArgumentException("Vehicle is not registered");

        ParkingSpot spot = spots.get(spotId);

        if (spot == null)
            throw new IllegalArgumentException("Parking spot not found");

        if (!spot.isAvailable())
            throw new IllegalStateException("Parking spot is not available");

        Money fare = spot.calculateFare(hours);

        BookingId bookingId = new BookingId(bookingCounter++);
        PaymentId paymentId = new PaymentId(paymentCounter++);

        boolean vehicleParked = false;

        try {
            spot.parkVehicle(normalizedVehicle);
            vehicleParked = true;

            Booking booking = new Booking(
                    bookingId, customerId, spotId,
                    normalizedVehicle, fare
            );

            PaymentReceipt receipt = paymentMethod.pay(
                    paymentId, bookingId, fare
            );

            booking.confirm(receipt);

            bookings.put(bookingId, booking);

            return booking;

        } catch (RuntimeException exception) {
            if (vehicleParked) {
                spot.releaseSpot();
            }

            throw exception;
        }
    }

    public void cancelBooking(BookingId bookingId) {
        Booking booking = bookings.get(bookingId);

        if (booking == null)
            throw new IllegalArgumentException("Booking not found");

        booking.cancel();

        ParkingSpot spot = spots.get(booking.getSpotId());
        spot.releaseSpot();
    }

    public List<BookingView> getBookings() {
        List<BookingView> result = new ArrayList<>();

        for (Booking booking : bookings.values()) {
            result.add(booking.getSnapshot());
        }

        return List.copyOf(result);
    }

    public void displayBookings() {
        System.out.println("\n----- BOOKING DETAILS -----");

        for (BookingView booking : getBookings()) {
            System.out.println(
                    "Booking ID: " + booking.bookingId() +
                            " | Customer ID: " + booking.customerId() +
                            " | Spot ID: " + booking.spotId() +
                            " | Vehicle: " + booking.vehicleNumber() +
                            " | Fare: " + booking.fare() +
                            " | Status: " + booking.status()
            );
        }
    }
}