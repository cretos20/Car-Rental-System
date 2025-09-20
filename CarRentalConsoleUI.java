import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

// Handles all console-based user interactions for the Car Rental System.
public class CarRentalConsoleUI {
    private RentalManager rentalManager;
    private Scanner scanner;

    public CarRentalConsoleUI(RentalManager rentalManager) {
        this.rentalManager = rentalManager;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");

            switch (choice) {
                case 1: handleRentCar(); break;
                case 2: handleReturnCar(); break;
                case 3: handleSearchAndListAvailableCars(); break;
                case 4: displayRentedCars(); break;
                case 5: handleViewRentalHistory(); break;
                case 6: displayAllCustomers(); break;
                case 7: handleAddCar(); break;
                case 8: handleAddCustomer(); break;
                case 9:
                    System.out.println("\nThank you for using the Car Rental System!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 9.");
            }
            System.out.println("\nPress Enter to continue...");
            scanner.nextLine();
        }
    }

    private void displayMenu() {
        System.out.println("\n===== Car Rental System Menu =====");
        System.out.println("1. Rent a Car");
        System.out.println("2. Return a Car");
        System.out.println("3. Search & List Available Cars");
        System.out.println("4. List Rented Cars");
        System.out.println("5. View Customer Rental History");
        System.out.println("6. List All Customers");
        System.out.println("7. Add a New Car");
        System.out.println("8. Add a New Customer");
        System.out.println("9. Exit");
        System.out.println("==================================");
    }

    private void handleRentCar() {
        System.out.println("\n== Rent a Car ==");
        displayAvailableCars();
        
        Car selectedCar = rentalManager.findCarById(getStringInput("Enter the car ID you want to rent: "));
        if (selectedCar == null || !selectedCar.isAvailable()) {
            System.out.println("Invalid car ID or car is not available.");
            return;
        }

        Customer customer = getCustomerForRental();
        if (customer == null) return; // User canceled or failed to provide customer info.

        int rentalDays = getIntInput("Enter the number of days for rental: ");
        if (rentalDays <= 0) {
            System.out.println("Rental days must be positive.");
            return;
        }

        displayPriceBreakdown(selectedCar, rentalDays);

        String confirm = getStringInput("\nConfirm rental (Y/N): ");
        if (confirm.equalsIgnoreCase("Y")) {
            rentalManager.rentCar(selectedCar, customer, rentalDays);
            System.out.println("\nCar rented successfully!");
        } else {
            System.out.println("\nRental canceled.");
        }
    }
    
    private void handleReturnCar() {
        System.out.println("\n== Return a Car ==");
        displayRentedCars();

        if (rentalManager.getAllRentedCars().isEmpty()) return;

        String carId = getStringInput("Enter the car ID you want to return: ");
        Car carToReturn = rentalManager.findCarById(carId);

        if (carToReturn == null || carToReturn.isAvailable()) {
            System.out.println("Invalid car ID or this car is not currently rented.");
            return;
        }

        rentalManager.returnCar(carToReturn);
        System.out.println("Car returned successfully!");
    }

    private void handleSearchAndListAvailableCars() {
        String query = getStringInput("Enter search term (brand, model, type) or leave blank to list all: ");
        List<Car> cars = rentalManager.searchAvailableCars(query);
        System.out.println("\n-- Available Cars --");
        if (cars.isEmpty()) {
            System.out.println("No cars found matching your criteria.");
        } else {
            cars.forEach(System.out::println);
        }
    }

    private void handleViewRentalHistory() {
        System.out.println("\n== View Rental History ==");
        displayAllCustomers();
        String customerId = getStringInput("Enter Customer ID to view history: ");
        Customer customer = rentalManager.findCustomerById(customerId);

        if (customer == null) {
            System.out.println("No customer found with that ID.");
            return;
        }

        List<CompletedRental> history = rentalManager.getCustomerRentalHistory(customerId);
        System.out.println("\n-- Rental History for " + customer.getName() + " --");
        if (history.isEmpty()) {
            System.out.println("No rental history found for this customer.");
        } else {
            history.forEach(System.out::println);
        }
    }

    private void handleAddCar() {
        System.out.println("\n== Add a New Car ==");
        String carId = getStringInput("Enter new Car ID (e.g., C008): ");
        if (rentalManager.findCarById(carId) != null) {
            System.out.println("Error: Car with this ID already exists.");
            return;
        }
        String brand = getStringInput("Enter car brand: ");
        String model = getStringInput("Enter car model: ");
        double price = getDoubleInput("Enter base price per day: ");
        
        System.out.println("Select Car Type:");
        AtomicInteger counter = new AtomicInteger(1);
        for(Car.CarType type : Car.CarType.values()){
            System.out.println(counter.getAndIncrement() + ". " + type);
        }
        int typeChoice = getIntInput("Enter type number: ");
        if(typeChoice < 1 || typeChoice > Car.CarType.values().length){
            System.out.println("Invalid type choice.");
            return;
        }
        Car.CarType selectedType = Car.CarType.values()[typeChoice - 1];

        rentalManager.addCar(new Car(carId, brand, model, price, selectedType));
        System.out.println("Car added successfully!");
    }

    private void handleAddCustomer() {
        System.out.println("\n== Add a New Customer ==");
        // Generate a simple new customer ID.
        String customerId = "CUS" + (rentalManager.getAllCustomers().size() + 1);
        String name = getStringInput("Enter customer name: ");
        String phone = getStringInput("Enter customer phone: ");
        rentalManager.addCustomer(new Customer(customerId, name, phone));
        System.out.println("Customer " + name + " added with ID: " + customerId);
    }

    // --- Helper & Display Methods ---

    private void displayPriceBreakdown(Car car, int days) {
        System.out.println("\n== Price Breakdown ==");
        System.out.printf("Car: %s %s (%s)\n", car.getBrand(), car.getModel(), car.getType());
        System.out.printf("Base Price per Day: ₹%.2f\n", car.getBasePricePerDay());

        if (car.getType() == Car.CarType.SPORTS) {
            System.out.printf("Sports Car Surcharge (20%%): +₹%.2f/day\n", car.getBasePricePerDay() * 0.20);
        }

        double subtotal = car.calculatePrice(days);
        if (days > 7) {
            double originalPrice = car.getBasePricePerDay() * (car.getType() == Car.CarType.SPORTS ? 1.2 : 1.0) * days;
            System.out.printf("Subtotal for %d days: ₹%.2f\n", days, originalPrice);
            System.out.printf("Long-Term Discount (10%%): -₹%.2f\n", originalPrice - subtotal);
        }

        System.out.println("--------------------------");
        System.out.printf("Total Price for %d days: ₹%.2f\n", days, subtotal);
    }
    
    private Customer getCustomerForRental() {
        String choice = getStringInput("Are you a (N)ew or (E)xisting customer? ");
        if (choice.equalsIgnoreCase("E")) {
            displayAllCustomers();
            String customerId = getStringInput("Enter your Customer ID: ");
            Customer customer = rentalManager.findCustomerById(customerId);
            if(customer == null) System.out.println("Customer ID not found.");
            return customer;
        } else {
            // Auto-generate a new ID for simplicity.
            String customerId = "CUS" + (rentalManager.getAllCustomers().size() + 1);
            String name = getStringInput("Enter your name: ");
            String phone = getStringInput("Enter your phone number: ");
            Customer newCustomer = new Customer(customerId, name, phone);
            rentalManager.addCustomer(newCustomer);
            System.out.println("New customer created with ID: " + customerId);
            return newCustomer;
        }
    }
    
    private void displayAvailableCars() {
        System.out.println("\n-- Available Cars --");
        List<Car> availableCars = rentalManager.getAllAvailableCars();
        if (availableCars.isEmpty()) {
            System.out.println("No cars are currently available.");
        } else {
            availableCars.forEach(System.out::println);
        }
    }

    private void displayRentedCars() {
        System.out.println("\n-- Rented Cars --");
        List<Car> rentedCars = rentalManager.getAllRentedCars();
        if (rentedCars.isEmpty()) {
            System.out.println("No cars are currently rented.");
        } else {
            rentedCars.forEach(System.out::println);
        }
    }

    private void displayAllCustomers() {
        System.out.println("\n-- All Customers --");
        List<Customer> customers = rentalManager.getAllCustomers();
        if (customers.isEmpty()) {
            System.out.println("No customers registered yet.");
        } else {
            customers.forEach(System.out::println);
        }
    }

    // --- Safe Input Methods ---

    private int getIntInput(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                int input = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character.
                return input;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.nextLine(); // Clear the invalid input.
            }
        }
    }
    
    private double getDoubleInput(String prompt) {
        System.out.print(prompt);
        while (true) {
            try {
                double input = scanner.nextDouble();
                scanner.nextLine(); // Consume the newline character.
                return input;
            } catch (InputMismatchException e) {
                System.out.print("Invalid input. Please enter a valid number: ");
                scanner.nextLine(); // Clear the invalid input.
            }
        }
    }

    private String getStringInput(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine();
    }
}

