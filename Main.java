// The main entry point for the Car Rental System application.
public class Main {

    // The main method that launches the application.
    public static void main(String[] args) {
        RentalManager rentalManager = new RentalManager();
        setupInitialData(rentalManager); // Load initial cars and customers.

        // Create and start the console UI.
        CarRentalConsoleUI consoleUI = new CarRentalConsoleUI(rentalManager);
        consoleUI.start();
    }

    // Sets up the initial sample data for the system.
    private static void setupInitialData(RentalManager manager) {
        // Adding some popular Indian cars to the system.
        manager.addCar(new Car("C001", "Maruti Suzuki", "Swift", 2500.0, Car.CarType.HATCHBACK));
        manager.addCar(new Car("C002", "Hyundai", "Creta", 4000.0, Car.CarType.SUV));
        manager.addCar(new Car("C003", "Tata", "Nexon", 3800.0, Car.CarType.SUV));
        manager.addCar(new Car("C004", "Mahindra", "Thar", 5500.0, Car.CarType.SUV));
        manager.addCar(new Car("C005", "Honda", "City", 3500.0, Car.CarType.SEDAN));
        manager.addCar(new Car("C006", "Kia", "Seltos", 4200.0, Car.CarType.SUV));
        manager.addCar(new Car("C007", "Toyota", "Innova Crysta", 6000.0, Car.CarType.SUV));

        // Adding some sample customers with Indian names.
        manager.addCustomer(new Customer("CUS001", "Priya Sharma", "9876543210"));
        manager.addCustomer(new Customer("CUS002", "Rohan Kumar", "9988776655"));
        manager.addCustomer(new Customer("CUS003", "Anjali Singh", "9123456789"));
    }
}

