import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

// Handles all the main logic for renting cars, managing customers, etc.
public class RentalManager {
    private Map<String, Car> cars;
    private Map<String, Customer> customers;
    private List<Rental> activeRentals;
    private List<CompletedRental> rentalHistory;

    // Sets up our lists and maps when the manager is created.
    public RentalManager() {
        cars = new HashMap<>();
        customers = new HashMap<>();
        activeRentals = new ArrayList<>();
        rentalHistory = new ArrayList<>();
    }

    // --- Methods for adding/updating data ---

    // Adds a new car to our system.
    public void addCar(Car car) {
        cars.put(car.getCarId(), car);
    }

    // Adds a new customer.
    public void addCustomer(Customer customer) {
        customers.put(customer.getCustomerId(), customer);
    }

    // Rents a car to a customer.
    public void rentCar(Car car, Customer customer, int days) {
        if (car.isAvailable()) {
            car.rent();
            activeRentals.add(new Rental(car, customer, days));
        } else {
            System.out.println("Error: Car is not available for rent.");
        }
    }

    // Handles returning a car. It moves the rental record to our history.
    public void returnCar(Car car) {
        car.returnCar();
        Rental rentalToRemove = null;
        for (Rental rental : activeRentals) {
            if (rental.getCar().equals(car)) {
                rentalToRemove = rental;
                break;
            }
        }

        if (rentalToRemove != null) {
            activeRentals.remove(rentalToRemove);
            rentalHistory.add(new CompletedRental(rentalToRemove)); // Add to history
        } else {
            System.out.println("Error: Car was not found in the active rentals list.");
        }
    }

    // --- Methods for finding/searching data ---

    public Car findCarById(String carId) {
        return cars.get(carId);
    }

    public Customer findCustomerById(String customerId) {
        return customers.get(customerId);
    }

    public List<Car> getAllAvailableCars() {
        return cars.values().stream()
                .filter(Car::isAvailable)
                .collect(Collectors.toList());
    }
    
    public List<Car> getAllRentedCars() {
        return cars.values().stream()
                .filter(c -> !c.isAvailable())
                .collect(Collectors.toList());
    }

    public List<Customer> getAllCustomers() {
        return new ArrayList<>(customers.values());
    }

    // Searches for available cars by brand, model, or type.
    public List<Car> searchAvailableCars(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllAvailableCars();
        }

        // Split the search query into individual words for better matching.
        String[] searchTerms = query.toLowerCase().replaceAll("[(),]", "").trim().split("\\s+");

        return getAllAvailableCars().stream()
                .filter(car -> {
                    // Combine car details into one string to search against.
                    String carDetails = (car.getBrand() + " " + car.getModel() + " " + car.getType().toString()).toLowerCase();
                    
                    // The car is a match only if it contains ALL search terms.
                    for (String term : searchTerms) {
                        if (!term.isEmpty() && !carDetails.contains(term)) {
                            return false; // A term was not found, so it's not a match.
                        }
                    }
                    return true; // All terms were found.
                })
                .collect(Collectors.toList());
    }
    
    // Gets the full rental history for a specific customer.
    public List<CompletedRental> getCustomerRentalHistory(String customerId) {
        return rentalHistory.stream()
                .filter(rental -> rental.getCustomer().getCustomerId().equals(customerId))
                .collect(Collectors.toList());
    }
}

