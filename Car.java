// Represents a car available for rent in the system.
public class Car {

    // Enum to represent the type of car.
    public enum CarType {
        SEDAN, SUV, HATCHBACK, SPORTS
    }

    private String carId;
    private String brand;
    private String model;
    private double basePricePerDay;
    private boolean isAvailable;
    private CarType type;

    public Car(String carId, String brand, String model, double basePricePerDay, CarType type) {
        this.carId = carId;
        this.brand = brand;
        this.model = model;
        this.basePricePerDay = basePricePerDay;
        this.isAvailable = true; // A new car is always available.
        this.type = type;
    }

    // --- Getters ---
    public String getCarId() {
        return carId;
    }

    public String getBrand() {
        return brand;
    }

    public String getModel() {
        return model;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    
    public CarType getType() {
        return type;
    }

    public double getBasePricePerDay() {
        return basePricePerDay;
    }

    // Calculates the final rental price, applying any discounts or surcharges.
    public double calculatePrice(int rentalDays) {
        double currentPrice = basePricePerDay;

        // Surcharge for sports cars.
        if (this.type == CarType.SPORTS) {
            currentPrice *= 1.20; // 20% surcharge.
        }

        double totalPrice = currentPrice * rentalDays;

        // Discount for rentals longer than a week.
        if (rentalDays > 7) {
            totalPrice *= 0.90; // 10% discount.
        }
        
        return totalPrice;
    }

    // --- State Modifiers ---
    // Marks the car as rented (not available).
    public void rent() {
        isAvailable = false;
    }

    // Marks the car as returned (available).
    public void returnCar() {
        isAvailable = true;
    }

    // Provides a simple summary of the car's details.
    @Override
    public String toString() {
        return String.format("ID: %-5s | %-12s %-15s | Type: %-10s | Price/Day: ₹%.2f", 
                                  carId, brand, model, type, basePricePerDay);
    }
}

