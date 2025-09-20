# Car Rental System 🚗

A complete Car Rental System built in two versions: a **Java-based console application** and a **modern, single-page web application**.  
This project demonstrates core programming principles and is fully functional without a database.

---

## 🎥 Demo & Screenshots
Check out this short video walkthrough to see the project in action!  

https://drive.google.com/file/d/1KmxZFM7LC8tLyA5AJOLTBlS02CCL6GfE/view?usp=sharing 

---

## ✨ Features
- **Rent & Return**: Full rental and return workflow.  
- **Data Management**: Add new cars to the fleet and register new customers.  
- **Smart Search**: Filter available cars by brand, model, or type.  
- **Rental History**: View the complete rental history for any customer.  
- **Dynamic Pricing**: Automatically calculates prices with:
  - Discounts for long-term rentals (>7 days)  
  - Surcharges for sports cars  
- **View Lists**: Display all available cars, currently rented cars, and all customers.  

---

## 🏛️ Object-Oriented Programming (OOP) Concepts Demonstrated
The **Java console version** is built on a foundation of core **OOP principles** to ensure the code is modular, maintainable, and scalable.

### Encapsulation
- Each class (`Car`, `Customer`, `Rental`) encapsulates its own data by declaring fields as **private**.  
- Access is controlled through **public methods (getters)**, preventing uncontrolled modification.  
- Example: A car’s `basePricePerDay` cannot be changed directly; its state is managed internally.  

### Abstraction
- The `RentalManager` class provides a **high-level interface** for complex operations.  
- For example, when calling `rentalManager.rentCar(...)`, the UI doesn’t need to know about the internal `HashMap` or `ArrayList` usage.  
- Complex logic like updating lists, car availability, and creating rental objects is **abstracted away**.  

### Classes and Objects
- The system is modeled using clear **real-world entities**: `Car`, `Customer`, and `Rental`.  
- At runtime, **objects (instances)** of these classes are created to represent each car or customer.  

### Polymorphism
- The `toString()` method is **overridden** in multiple classes (`Car`, `Customer`, `CompletedRental`).  
- This allows objects to be printed meaningfully using the same method call (`System.out.println(object)`), demonstrating **practical polymorphism**.  

---

## 🛠️ Technologies Used

### Console Version
- Java  
- Object-Oriented Programming (OOP)  

### Website Version
- HTML5  
- CSS3  
- Vanilla JavaScript (ES6)  

---

## 🚀 Getting Started
This repository contains two separate versions of the application.

### 1. The Console Version (Java)

A robust application that runs in a **command-line terminal**.  
All `.java` files are located in the main project folder.

**How to Run:**
1. Open a terminal in the main project directory.  
2. Compile all Java files:  
javac *.java
3. Run the program:  
java Main

---

### 2. The Website Version (HTML/CSS/JS)

A **user-friendly web application** that runs entirely in your browser.  
All related files are located in the `/website` folder.

**How to Run:**
1. Navigate into the website folder:  
cd website

2. Open in browser by double-clicking `car_rental_system.html`.  

---

## 🎯 Explore Both Versions!
Feel free to explore both the **Java console version** and the **Web version** to experience different approaches to solving the same problem.
