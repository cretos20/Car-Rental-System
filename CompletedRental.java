import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

// Represents a completed rental for historical records.
public class CompletedRental {
    private Car car;
    private Customer customer;
    private LocalDate rentalDate;
    private LocalDate returnDate;
    private int daysRented;
    private double finalPrice;

    // Creates a historical record from an active rental.
    public CompletedRental(Rental rental) {
        this.car = rental.getCar();
        this.customer = rental.getCustomer();
        this.daysRented = rental.getDays();
        this.rentalDate = rental.getRentalDate();
        this.returnDate = LocalDate.now(); // Assumes the car is returned today.
        this.finalPrice = car.calculatePrice(daysRented);
    }

    // --- Getters ---
    public Car getCar() {
        return car;
    }

    public Customer getCustomer() {
        return customer;
    }
    
    // Provides a formatted summary for rental history logs.
    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format("Car: %-25s | Rented by: %-15s | Rented: %s | Returned: %s | Days: %-3d | Total: ₹%.2f",
                car.getBrand() + " " + car.getModel(),
                customer.getName(),
                rentalDate.format(formatter),
                returnDate.format(formatter),
                daysRented,
                finalPrice);
    }
}

