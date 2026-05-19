import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Ticket {
    private double ticketId;
    private String userId;
    private String busNo;
    public Date travelDate;
    private Seat[] seats;
    public double getTicketId() {
        return ticketId;
    }
    public Bus getBus() {
        return bus;
    }
    public Seat[] getSeats() {
        return seats;
    }
    public Date getBookingDate() {
        return bookingDate;
    }
    private Passenger[] passengers;
    private double totalFare;
    private Date bookingDate;
    private Bus bus;
    private BookingStatus status;

    public BookingStatus getStatus() {
        return status;
    }
    public void getSummary() {
        System.out.println(userId +" "+bus.getBusType()+" "+bus.getSource()+" "+bus.getDestination()+" "+totalFare+" "+status);
    }

    public Ticket(double ticketId, String userId, String busNo, Seat[] seats, Passenger[] passengers, double totalFare, Date bookingDate, Bus bus, BookingStatus status,Date travelDate) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.busNo = busNo;
        this.seats = seats;
        this.passengers = passengers;
        this.totalFare = totalFare;
        this.bookingDate = bookingDate;
        this.bus = bus;
        this.status = status;
        this.travelDate=travelDate;
    }

    public void showTicket() {
        
        System.out.println("\n--- TICKET CONFIRMATION ---");
        System.out.println("Ticket ID:"+ticketId);
        System.out.println("Bus: " + bus.getBusNo() + " (" + bus.getBusType() + ")");
        System.out.println("Route: " + bus.getSource() + " -> " + bus.getDestination());
        System.out.println("Date : "+ travelDate);
        System.out.println("Passengers: " + passengers.length + " | Total Fare: " + String.format("%.2f", totalFare));
        System.out.println("Booking Status: " + status);
        System.out.println("--------------------------");
        System.out.println("Passengers   :");
        if (passengers != null) {
            for (Passenger p : passengers) {
                if (hasPassenger(p.getName())) {
                    System.out.println(" - " + p.getName() +
                                       " (Age: " + p.getAge() +
                                       ", Gender: " + p.getGender() + ")");
                }
            }
        } else {
            System.out.println("No passengers found");
        }
        System.out.println("-----------------------------------------");
            System.out.println("Selected Seats:");
            if (seats != null) {
                for (Seat s : seats) {
                    if (s != null) {
                        System.out.println(" - Seat No: " + s.getSeatNumber() + " | Type: " + s.getSeatType());
                    }
                }
            } else {
                System.out.println("   No seats selected");
            }
        
            System.out.println("-----------------------------------------");
            System.out.println("Amount       : Rs. " + totalFare);
            status=BookingStatus.Confirmed;
            System.out.println("Booking Date : " + bookingDate);
            System.out.println("Status       : " + status);
            System.out.println("=========================================");
            System.out.println("         THANK YOU FOR BOOKING!          ");
            System.out.println("=========================================");
    }
    public void downloadTicket() {
        
        String fileName = "Ticket_RedBus.txt";
        
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(fileName))) {
            
            status = BookingStatus.Confirmed; 

            
            writer.println("=========================================");
            writer.println("          BUS BOOKING E-TICKET         ");
            writer.println("=========================================");
            writer.println("--- TICKET CONFIRMATION ---");
            writer.println("Ticket ID: " + String.format("%.0f", ticketId));
            writer.println("Bus: " + busNo + " (" + bus.getBusType() + ")");
            writer.println("Route: " + bus.getSource() + " -> " + bus.getDestination());
            writer.println("Travel Date : " + travelDate);
            writer.println("Departure Time: " + new SimpleDateFormat("HH:mm").format(travelDate)); // Assuming travelDate includes time
            writer.println("Passengers: " + passengers.length + " | Total Fare: Rs. " + String.format("%.2f", totalFare));
            writer.println("Booking Status: " + status);
            writer.println("--------------------------");
            
            writer.println("Passengers:");
            if (passengers != null) {
                for (Passenger p : passengers) {
                    if (hasPassenger(p.getName())) { 
                        writer.println(" - " + p.getName() +
                                           " (Age: " + p.getAge() +
                                           ", Gender: " + p.getGender() + ")");
                    }
                }
            } else {
                writer.println("No passengers found");
            }
            
            writer.println("-----------------------------------------");
            writer.println("Selected Seats:");
            if (seats != null) {
                for (Seat s : seats) {
                    if (s != null) {
                        writer.println(" - Seat No: " + s.getSeatNumber() + " | Type: " + s.getSeatType());
                    }
                }
            } else {
                writer.println("No seats selected");
            }
            
            writer.println("-----------------------------------------");
            writer.println("Amount Paid  : Rs. " + String.format("%.2f", totalFare));
            writer.println("Booking Date : " + bookingDate);
            writer.println("Status       : " + status);
            writer.println("=========================================");
            writer.println("               HAPPY JOURNEY!            ");
            writer.println("=========================================");
            
            System.out.println("\n\nTicket successfully saved to: " + fileName);
            System.out.println("You can find the file in the current application directory.");
            
        } catch (IOException e) {
            System.err.println("\nERROR: Could not write ticket to file " + fileName + ".");
            System.err.println("Reason: " + e.getMessage());
        }
    }
    private  boolean hasPassenger(String name) {
        if (passengers != null) {
            for (Passenger p : passengers) {
                if (p != null && p.getName().equalsIgnoreCase(name)) {
                    return true;
                }
            }
        }
        return false;
    }

    
}