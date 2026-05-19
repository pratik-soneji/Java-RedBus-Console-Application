import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Admin extends BookingService{
     private static final String adminUserName="admin";
     private static final String adminPass="admin";
     final static Scanner sc = new Scanner(System.in);
    boolean verifyCredentials(String userName, String passWord) {
        if (userName.equals(adminUserName)) {
            if (passWord.equals(adminPass)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public void showMenu() {
        
        int choice;
        System.out.println("\n--- Admin Logged In ---");
        do{
            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. Add New Bus Route");
            System.out.println("2. View All Buses");
            System.out.println("3. View All Bookings");
            System.out.println("4. Update Bus");
            System.out.println("5. delete Bus");
            System.out.println("6. Logout (Return to Main Menu)");
            System.out.print("Enter your choice: ");

            choice = sc.nextInt();

            switch (choice) {
                case 1:
                    addNewBusRoute();
                    break;
                case 2:
                    viewAllBuses();
                    break;
                case 3:
                    viewAllBookings();
                    break;
                case 4:
                    try {
                        updateBus();
                    } catch (BusException e) {
                        System.out.println("Exception ocurres while updating bus");
                    }
                    break;
                case 5:
                    try {
                        deleteBus();
                    } catch (BusException e) {
                        System.out.println("Exception ocurres while deleting bus");
                    }
                    break;
                case 6:
                    System.out.println("Logging out from Admin panel...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        } while (true);
    }
    public void addNewBusRoute() {
        // Check if the fixed-size array is full (busCount is 0 or 1 for a size 2 array)
        if (BookingService.busCount >= busArray.length) {
            System.out.println("Bus list is full (Maximum " + busArray.length + " buses allowed as per system constraint). Cannot add new busArray[i].");
            return;
        }

        System.out.println("\n--- Add New Bus Route ---");
        
        System.out.print("Enter Bus ID (e.g., MH12XY5678): ");
        String busId = sc.next();
        sc.nextLine(); 

        System.out.print("Enter Driver Name: ");
        String driverName = sc.nextLine();

        System.out.print("Enter Bus Type (e.g.,A-AC Bus, B-Ac Sleeper, C-Express, D-Normal): ");
        Character busTypechoice = sc.next().charAt(0);
        String busType;
        
        System.out.print("Enter Source City: ");
        sc.nextLine();
        String source = sc.nextLine();

        System.out.print("Enter Destination City: ");
        String destination = sc.nextLine();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false); 

        String dateString = null;
        Date storedDate = null;

        while (true) {
            System.out.println("\nEnter a date in the format DD/MM/YYYY (e.g., 31/12/2025):");
            
            // Read the input string
            dateString = sc.nextLine();
            
            try {
                storedDate = formatter.parse(dateString);
                System.out.println("\nValid date entered!");
                break; 
                
            } catch (ParseException e) {
                System.err.println("Error: '" + dateString + "' is in a WRONG FORMAT or is an invalid date. Please use DD/MM/YYYY.");
            }
        }        
        System.out.print("Enter Departure Time (e.g., 10:00 AM): ");
        System.out.println("Enter Hour (0-23)");
        int hour = sc.nextInt();
        System.out.println("Enter Hour (0-59)");
        int min = sc.nextInt();

        // Input validation for numbers
        // double ticketPrice = 0.0;
        // System.out.print("Enter Ticket Price (e.g., 300.00): ");
        // if(sc.hasNextDouble()) {
        //     ticketPrice = sc.nextDouble();
        // }
        int totalSeats = 0;
        System.out.print("Enter Total Seat Capacity (e.g., 30): ");
        if(sc.hasNextInt()) {
            totalSeats = sc.nextInt();
        }
        int km = 0;
        System.out.print("Enter Km: ");
        if(sc.hasNextInt()) {
            km = sc.nextInt();
        }
        Seat s[]=new Seat[totalSeats];
        int seatCount=0;
        for (int i=0;i<totalSeats;i++) {
            s[i]=new Seat(++seatCount, "None");
        }
        Bus newBus;
        switch (busTypechoice) {
            case 'A':
                busType="Ac Bus";
                newBus = new AcBus(busId, driverName, busType, source, destination, hour, min, seatCount, totalSeats, km, s, storedDate);
                break;
            case 'B':
                busType="Ac Sleeper";
                newBus = new AcSleeper(busId, driverName, busType, source, destination,  hour, min, seatCount, totalSeats, km, s,storedDate);
                break;
            case 'C':                
                busType="Express";
                newBus = new ExpressBus(busId, driverName, busType, source, destination,  hour, min, seatCount, totalSeats, km, s,storedDate);
                break;
            case 'D':
                busType="Normal";
                newBus = new NormalBus(busId, driverName, busType, source, destination,  hour, min, seatCount, totalSeats, km, s,storedDate);
                break;
            default:
                busType="Normal";
                newBus = new NormalBus(busId, driverName, busType, source, destination,  hour, min, seatCount, totalSeats, km, s,storedDate);
                break;
        }
        // new NormalBus(busId, driverName, busType, source, destination, departureTime, ticketPrice, totalSeats);
        busArray[busCount] = newBus;
        busCount++;

        System.out.println("\nSUCCESS: New Bus Route Added!");
        
    }
    public void viewAllBuses() {
        System.out.println("\n--- View All Buses (Total: " + busCount + ") ---");
        if (busCount == 0) {
            System.out.println("No buses have been added yet.");
            return;
        }
        
        // Iterate through the array up to busCount
        for (int i = 0; i < busCount; i++) {
            if (busArray[i]!=null) {
                busArray[i].adminViewAllBus();
            }
        }
    }
    public void viewAllBookings(){
            System.out.println("\n--- View All Bookings (Total Bus: " + busCount + ") ---");
            for (int i = 0; i < userCount; i++) {
                    for (Ticket ticket : userArray[i].tickets) {
                        if(ticket!=null){
                            ticket.getSummary();
                        }
                    }
            }
    }
    public void updateBus() throws BusException{
        viewAllBuses();
        if (busCount>0) {
            System.out.println("Enter Bus id You want to update");
        String busNo=sc.next();
        for (int i = 0; i < busArray.length; i++) {
            // busArray[i].show();
            // System.out.println(busArray[i]);
            if (busArray[i]!=null && busArray[i].getBusNo().equals(busNo)) {
                System.out.println("Are you sure you want to update this below bus? Y/N");
                Character choice = sc.next().charAt(0);
                busArray[i].show();
        
                if (choice == 'Y' || choice == 'y') {
                    sc.nextLine(); // Clear leftover newline
        
                    System.out.print("Enter Driver Name: ");
                    String driverName = sc.nextLine();
        
                    System.out.print("Enter Source City: ");
                    String source = sc.nextLine();
        
                    System.out.print("Enter Destination City: ");
                    String destination = sc.nextLine();
        
                    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    formatter.setLenient(false);
        
                    String dateString = null;
                    Date storedDate = null;
        
                    while (true) {
                        System.out.println("\nEnter a date in the format DD/MM/YYYY (e.g., 31/12/2025):");
                        dateString = sc.nextLine();
        
                        try {
                            storedDate = formatter.parse(dateString);
                            System.out.println("\nValid date entered!");
                            break;
                        } catch (ParseException e) {
                            System.err.println(
                                "Error: '" + dateString + "' is in a WRONG FORMAT or is an invalid date. Please use DD/MM/YYYY."
                            );
                        }
                    }
        
                    System.out.println("Enter Departure Time (e.g., 10:00 AM): ");
                    System.out.println("Enter Hour (0-23)");
                    int hour = sc.nextInt();
        
                    System.out.println("Enter Min (0-59)");
                    int min = sc.nextInt();
        
                    System.out.print("Enter Km: ");
                    int km = sc.nextInt();
        
                    // Update bus
                    busArray[i].setDriverName(driverName);
                    busArray[i].setSource(source);
                    busArray[i].setDestination(destination);
                    busArray[i].setDepartureDate(storedDate);
                    busArray[i].setDepartureTimeHour(hour);
                    busArray[i].setDepartureTimeMin(min);
                    busArray[i].setKm(km);
                    busArray[i].setAvailbleSeats(busArray[i].totalSeats);
                    System.out.println("Bus successfully updated");
                }else{
                    break;
                }
            }
        }
        }
        
    }
    public void deleteBus() throws BusException{
        viewAllBuses();
        if(busCount>0){
            System.out.println("Enter Bus id You want to delete");
        String busNo=sc.next();
        for (int i = 0; i < busArray.length; i++) {
            // busArray[i].show();
            // System.out.println(busArray[i]);
            if (busArray[i]!=null && busArray[i].getBusNo().equals(busNo)) {
                System.out.println("Are you sure you want to delete this below bus? Y/N");
                Character choice = sc.next().charAt(0);
                busArray[i].show();
        
                if (choice == 'Y' || choice == 'y') {
                    sc.nextLine(); // Clear leftover newline
                    busArray[i]=null;
                    busCount--;
                    System.out.println("Bus Successfully Deleted");
                }else{
                    break;
                }
            }
        }
        

        }
            }
}
