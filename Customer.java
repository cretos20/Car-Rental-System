// Represents a customer in the car rental system.
public class Customer {
    private String customerId;
    private String name;
    private String contactPhone;

    public Customer(String customerId, String name, String contactPhone) {
        this.customerId = customerId;
        this.name = name;
        this.contactPhone = contactPhone;
    }

    // --- Getters ---
    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }
    
    public String getContactPhone() {
        return contactPhone;
    }

    // Provides a simple summary of the customer.
    @Override
    public String toString() {
        return String.format("ID: %-8s | Name: %-20s | Phone: %s", customerId, name, contactPhone);
    }
}

