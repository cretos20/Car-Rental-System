import java.time.LocalDate;

// Represents an active rental, linking a car, a customer, and the rental duration.
public class Rental {
    private Car car;
    private Customer customer;
    private int days;
    private LocalDate rentalDate;

    public Rental(Car car, Customer customer, int days) {
        this.car = car;
        this.customer = customer;
        this.days = days;
        this.rentalDate = LocalDate.now(); // Sets rental date to today.
    }

    // --- Getters ---
    public Car getCar() { return car; }
    public Customer getCustomer() { return customer; }
    public int getDays() { return days; }
    public LocalDate getRentalDate() { return rentalDate; }

    // Provides a simple summary of the rental.
    @Override
    public String toString() {
        return String.format("Rental Details:\n  Customer: %s (%s)\n  Car: %s %s\n  Duration: %d days",
                customer.getName(), customer.getCustomerId(), car.getBrand(), car.getModel(), days);
    }
}

