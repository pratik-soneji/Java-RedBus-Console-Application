# Online Bus Booking System (RedBus Clone)

A comprehensive Java console-based application that simulates an online bus ticket booking platform. This project provides role-based access for both Administrators and Users, featuring bus management, seat reservation, ticket cancellation, and a simulated UPI payment gateway using Java Multithreading.

## 🛠️ Features and Functionalities

### 1. User Module
- **Authentication**: Users can register and log securely into the system.
- **Search Buses**: Search for available buses by entering the source, destination, and travel date.
- **Seat Booking & Reservation**: 
  - View real-time seat availability.
  - Select specific seat numbers and enter passenger details.
- **Booking Cancellation**: Users can cancel their tickets (cancellation is allowed only if the departure time is more than 4 hours away).
- **View Bookings**: View a history of booked tickets.
- **Download Ticket**: Users can generate and download their e-tickets as a text file (`Ticket_RedBus.txt`).

### 2. Admin Module
- **Admin Dashboard**: Secure login for administrators (credentials: `admin`/`admin`).
- **Bus Management**:
  - Add new bus routes, specifying bus type (AC, AC Sleeper, Express, Normal), seat capacity, route, and timing.
  - View all registered buses.
  - Update existing bus schedules and details.
  - Delete buses from the system.
- **View All Bookings**: Admins can monitor all tickets booked by users across the platform.

### 3. Payment System & Multithreading Implementation
**Note:** The UPI payment feature in this console-based application is a simple object-oriented simulation built with standard Java classes. It **does not** integrate with any real UPI payment gateways or process actual financial transactions.

The application uses this simple setup to demonstrate the **Producer-Consumer** multithreading design pattern in an asynchronous payment processing context:

- **`UpiPay` Class (Shared Resource):** Contains synchronized `put()` (request payment) and `get()` (process payment) methods. It uses `wait()` and `notify()` for inter-thread communication, relying on a boolean `requested` flag to ensure synchronized data flow.
- **Producer Thread (`UserUpiPay`):** Represents the user initiating a payment request. It calls the `put()` method to submit the request.
- **Consumer Thread (`UserUpiPayResponse`):** Represents the payment gateway/server processing the request. It calls the `get()` method to accept and verify the transaction.
- **Synchronization**: This implementation ensures that a payment response is generated only after a payment request is made, preventing race conditions and simulating an asynchronous transaction pipeline.

## 🏗️ Project Architecture & OOP Concepts
- **Inheritance & Polymorphism**: Abstract `Bus` class acts as a base for specific bus types like `AcBus`, `AcSleeper`, `ExpressBus`, and `NormalBus`. Dynamic method dispatch is used to calculate varying fare rates.
- **Encapsulation**: Object properties (User, Ticket, Bus, Passenger) are strictly encapsulated using getter and setter methods.
- **Abstraction**: Complex business logic like booking algorithms and multithreaded payments are abstracted away from the user menu.

## 🚀 How to Run

1. Compile the Java files:
   ```bash
   javac *.java
   ```
2. Run the main application:
   ```bash
   java RedBusApp
   ```
3. Use the interactive console menu to navigate as an Admin or a User.

## 📂 Project Structure
- `RedBusApp.java` - Application entry point.
- `BookingService.java` - Core business logic for searching and booking.
- `Admin.java` - Administrator functionalities.
- `Bus.java` (and subclasses) - Bus modeling.
- `User.java` - User profile and history management.
- `Ticket.java` - Ticket generation and file I/O operations.
- `ProdConsumProb.java` & `Payment.java` - Multithreading and payment interfaces.
