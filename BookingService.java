import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

// Assuming Bus, User, Seat, Passenger, Ticket, BookingStatus, AcBus, AcSleeper, ExpressBus, NormalBus classes exist
// and have the methods/fields used in BookingService.

public class BookingService {
    // Moved to private static or local scope where possible for better encapsulation.
    // Kept public static only where absolutely necessary based on original code structure.
    public static String userName, password, UserId, Email, destination, source;
    public static long phoneNum; // Consider making this private/local if possible later
    public int availableBuses; // Still public as it's used to communicate search results
    
    public static final Scanner sc = new Scanner(System.in);
    
    private static final long MILLIS_PER_MINUTE = 60 * 1000;
    private static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;
    private static final long TWO_HOURS_IN_MILLIS = 2 * MILLIS_PER_HOUR;
    private static final long FOUR_HOURS_IN_MILLIS = 4 * MILLIS_PER_HOUR;

    // FIX: Make busArray static and initialize it once. (Already done in original)
    static Bus busArray[] = new Bus[2];
    static int busCount = 0;
    static int userCount = 0;
    static int searchBusCount = 0;

    static User userArray[] = new User[2];

    Bus searchedBus[] = new Bus[2];
    User currentUser;

    public BookingService() {
        this.searchedBus = new Bus[2];
    }
    
    // --- Validation Methods (Cleaner, private) ---

    private boolean validateNotEmpty(String input, String fieldName) {
        if (input == null || input.trim().isEmpty()) {
            System.out.println("ERROR: " + fieldName + " cannot be empty.");
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$")) {
            System.out.println(" ERROR: Invalid Email format.");
            return false;
        }
        return true;
    }

    private boolean validatePhone(String phoneStr) {
        if (!phoneStr.matches("\\d{10}")) {
            System.out.println(" ERROR: Phone number must be exactly 10 digits.");
            return false;
        }
        return true;
    }
    
    // --- User Authentication Methods ---

    public void userLogin() {
        System.out.println("Enter UserName: ");
        // sc.nextLine(); // Consume potential newline
        userName = sc.nextLine();
        System.out.print("Enter Password: ");
        password = sc.nextLine();
        
        // Input validation for Login
        if (!validateNotEmpty(userName, "UserName") || !validateNotEmpty(password, "Password")) {
            System.out.println("Login aborted due to empty fields.");
            return;
        }

        for (int i = 0; i < userCount; i++) {
            User user = userArray[i];
            if (user.checkCredentials(userName, password)) {
                System.out.println("You Are Logged In Successfully!");
                currentUser = user;
                user.showUser();
                showMenu();
                return;
            }
        }
        System.out.println("Invalid Credentials.");
        System.out.println("Try Again Later ...");
        return;
    }

    public void userRegister() {
        if (userCount >= userArray.length) {
            System.out.println("User registration limit reached (Max " + userArray.length + " users).");
            return;
        }
        
        // Input sequence preserved
        System.out.print("Enter UserName: ");
        sc.nextLine(); 
        userName = sc.nextLine();
        
        System.out.print("Enter Password: ");
        password = sc.next();
        
        System.out.print("Enter UserId: ");
        UserId = sc.next();
        
        System.out.print("Enter Email: ");
        Email = sc.next();
        
        System.out.print("Enter Phone (10 digits): ");
        String phoneStr = sc.next();
        sc.nextLine(); // Consume remaining newline
        

        // Validation sequence preserved
        if (!validateNotEmpty(userName, "UserName") || 
            !validateNotEmpty(password, "Password") ||
            !validateNotEmpty(UserId, "UserId") ||
            !validateEmail(Email) ||
            !validatePhone(phoneStr)) {
            return; 
        }
        
       
        try {
             phoneNum = Long.parseLong(phoneStr);
        } catch (NumberFormatException e) {
             System.out.println("ERROR: Phone number conversion failed. Try again.");
             return; 
        }

        User user = new User(userName, password, UserId, Email, phoneNum);
        userArray[userCount++] = user;
        System.out.println("Register Successfully!");
        return;
    }

    // --- User Handler ---
    
    private int getUserAuthChoice() {
        while (true) {
            System.out.println("\n--- User Auth Menu ---");
            System.out.println("1. User Login");
            System.out.println("2. User Registration");
            System.out.println("3. Exit");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); 
                continue;
            }
            int choice = sc.nextInt();
            sc.nextLine(); 
            return choice;
        }
    }
    
    public void handleUser() {
        while (true) {
            int userChoice = getUserAuthChoice();

            if (userChoice == 1) {
                userLogin();
            } else if (userChoice == 2) {
                userRegister();
            } else if (userChoice == 3) {
                return;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

    // --- Bus Search Methods ---

    public void searchBus(String source, String destination, Date storedDate) {
        searchBusCount = 0;
        availableBuses = 0;
        searchedBus = new Bus[2]; // Re-initialize the array

        // Input validation for search terms
        if (!validateNotEmpty(source, "Source") || !validateNotEmpty(destination, "Destination")) {
            System.out.println("Bus search aborted.");
            return;
        }
        
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        // Only the date part of the bus departure is needed for matching
        String dateToMatch = formatter.format(storedDate);

        System.out.println("\n--- Searching for: " + source + " to " + destination + " on " + dateToMatch + " ---");
        
        for (int i = 0; i < busCount; i++) {
            Bus currentBus = busArray[i];
            
            if (currentBus == null) {
                System.out.println("(Error: Bus slot at index " + i + " is empty. Skipping.)");
                continue;
            }

            // Check Source, Destination, and Date
            if (currentBus.getSource().toLowerCase().equals(source.toLowerCase()) &&
                currentBus.getDestination().toLowerCase().equals(destination.toLowerCase())) {

                String busDateString = formatter.format(currentBus.departureDate);

                if (busDateString.equals(dateToMatch)) {
                    if (searchBusCount < searchedBus.length) {
                        searchedBus[searchBusCount++] = currentBus;
                        availableBuses++;
                        System.out.print((availableBuses) + ". "); // Prepend index for user selection
                        currentBus.show();
                    } else {
                        System.out.println("(Search results array full, displaying only the first " + searchedBus.length + " matching buses.)");
                        break;
                    }
                }
            }
        }
    }

    // --- Seat Selection Helper Methods ---
    
    private Seat[] processSeatSelection(Bus selectedBus, int numTickets) {
        Seat[] selectedSeats = new Seat[numTickets];
        System.out.println("Enter seat numbers one by one:");
        
        for (int i = 0; i < numTickets; i++) {
            System.out.print("Seat number " + (i + 1) + ": ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid seat number input. Aborting seat selection.");
                sc.next();
                return null; // Return null to indicate failure
            }
            int seatNo = sc.nextInt();
            
            // Input validation for seat number range
            if (seatNo < 1 || seatNo > selectedBus.availableSeatsArray.length) {
                System.out.println("Invalid seat number: " + seatNo + ". Must be between 1 and " + selectedBus.availableSeatsArray.length + ".");
                i--; // Decrement i to re-enter this seat
                continue;
            }
            
            // Check availability
            if (!selectedBus.availableSeatsArray[seatNo - 1].isBooked) {
                selectedBus.availableSeatsArray[seatNo - 1].isBooked = true; // Mark as booked immediately
                selectedSeats[i] = new Seat(seatNo, selectedBus.busType);
            } else {
                System.out.println("Seat " + seatNo + " is already booked. Please select a valid seat number.");
                i--; // Decrement i to re-enter this seat   
            }
        }
        sc.nextLine(); // Consume final newline after seat numbers
        return selectedSeats;
    }
    
    private Passenger[] processPassengerDetails(int numTickets) {
        Passenger[] passengers = new Passenger[numTickets];
        
        for (int i = 0; i < numTickets; i++) {
            int age;
            String gender, name;

            // Age Input
            System.out.print("Enter Age of " + (i + 1) + " Passenger: ");
            if (!sc.hasNextInt()) {
                System.out.println("Invalid age input. Aborting passenger entry.");
                sc.next();
                return null;
            }
            age = sc.nextInt();
            sc.nextLine(); // Consume newline
            
            // Name Input
            System.out.print("Enter Name of " + (i + 1) + " Passenger: ");
            name = sc.nextLine();
            
            // Gender Input
            System.out.print("Enter Gender (M/F/O) of " + (i + 1) + " Passenger: ");
            gender = sc.nextLine().toUpperCase();
            
            // Validation for Name and Gender
            if (!validateNotEmpty(name, "Passenger Name")) {
                i--; continue; // Redo this passenger entry
            }
            if (!gender.equals("M") && !gender.equals("F") && !gender.equals("O")) {
                System.out.println("Invalid gender. Please use M, F, or O.");
                i--; continue; // Redo this passenger entry
            }
            
            passengers[i] = new Passenger(age, name, gender);
        }
        return passengers;
    }
    
    private double calculateFinalFare(Bus selectedBus, int numTickets) {
        // Use the instanceof checks to call the correct calculateFare method (Polymorphism)
        // Note: Casting is necessary here based on the original structure.
        if (selectedBus instanceof AcSleeper) {
            AcSleeper sleeperBus = (AcSleeper) selectedBus;
            return sleeperBus.calculateFare(numTickets);
        } else if (selectedBus instanceof AcBus) {
            AcBus acBus = (AcBus) selectedBus;
            return acBus.calculateFare(numTickets);
        } else if (selectedBus instanceof ExpressBus) {
            ExpressBus expressBus = (ExpressBus) selectedBus;
            return expressBus.calculateFare(numTickets);
        } else {
            // Assumes it's a NormalBus (or the base Bus)
            // Casting to NormalBus if it's the default
            NormalBus normalBus = (NormalBus) selectedBus; 
            return normalBus.calculateFare(numTickets);
        }
    }
    
    // --- Seat Selection and Booking Method (Split into helpers) ---

    public void selectSeats(Bus selectedBus, Date storedDate)  {
        if (currentUser == null) {
            System.out.println("Error: User is not logged in to book tickets.");
            return;
        }

        selectedBus.showDetails();
        System.out.println("Enter number of tickets you want to book");
        
        // Input validation for number of tickets
        if (!sc.hasNextInt()) {
            System.out.println(" Invalid input for ticket count. Aborting selection.");
            sc.next();
            return;
        }
        int numTickets = sc.nextInt();

        if (numTickets < 1 || numTickets > selectedBus.availbleSeats) {
            System.out.println("Please Enter valid number of tickets (1 to " + selectedBus.availbleSeats + ").");
            return;
        }
        sc.nextLine(); // Consume newline

        // 1. Process Seat Selection
        Seat[] selectedSeats = processSeatSelection(selectedBus, numTickets);
        if (selectedSeats == null) {
            System.out.println("Seat selection failed. Aborting booking.");
            return;
        }

        // 2. Process Passenger Details
        Passenger[] passengers = processPassengerDetails(numTickets);
        if (passengers == null) {
            System.out.println("Passenger details entry failed. Aborting booking.");
            // Important: Unbook the seats that were provisionally booked
            for (Seat seat : selectedSeats) {
                if (seat != null) {
                    selectedBus.availableSeatsArray[seat.seatNo - 1].isBooked = false;
                }
            }
            return;
        }
        
        // 3. Calculate Fare
        double totalFare = calculateFinalFare(selectedBus, numTickets);
        
        // 4. Create Final Travel Date
        Date finalTravelDate = createFinalTravelDate(selectedBus, storedDate);
        System.out.println("Are you sure you want to book this ticket?y/n");
        Character choice = sc.next().charAt(0);
        if(choice=='Y'||choice=='y'){
            System.out.println("Choose Payment Option : ");
            System.out.println("1. Upi Payment");
            int paymentOption = sc.nextInt();
            switch (paymentOption) {
                case 1:
                    System.out.println("Enter Upi Id");
                    String upiId=sc.next();
                    // Payment payment = new UpiPay(totalFare, upiId);
                    if (upiId.contains("@")) {
                        UpiPay payment = new UpiPay(totalFare, upiId);
                        UserUpiPay mp=new UserUpiPay(payment);
                        UserUpiPayResponse cm=new UserUpiPayResponse(payment);
                    }else{
                        System.out.println("Invalid Upi Id");
                    }
                    break;
            
                default:
                    break;
            }
            

        }else{
            System.out.println("Sorry ticket is not booked");   
            return;
        }
        
        // 5. Create Ticket
        Ticket resultTicket = new Ticket(
                (double) ((int) (Math.random() * 90000) + 10000), // Random Ticket ID
                currentUser.getUserId(),
                selectedBus.getBusNo(),
                selectedSeats,
                passengers,
                totalFare,
                new Date(), // Booking Date
                selectedBus,
                BookingStatus.Pending , // Initial status
                finalTravelDate
        );

        // 6. Handle Ticket Display/Download
        resultTicket.showTicket();
        handleTicketDownload(resultTicket);

        // 7. Finalize Booking
        if (resultTicket.getStatus() == BookingStatus.Confirmed) {
            // The original logic prints details if confirmed, which is retained.
            for (int j = 0; j < numTickets; j++) {
                System.out.println("Seat No: " + resultTicket.getBus().availableSeatsArray[selectedSeats[j].seatNo-1].seatNo + 
                                   ", Booked: " + resultTicket.getBus().availableSeatsArray[selectedSeats[j].seatNo-1].isBooked);   
            }
        }
        
        // Update bus seat count and user's ticket list
        selectedBus.updateSeats(numTickets);
        currentUser.addTicket(resultTicket);
        
    }
    
    private void handleTicketDownload(Ticket resultTicket) {
        while (true) {
            System.out.println("Would You Like to Download Ticket ? Y/N : ");
            String input = sc.next().toLowerCase();
            if (input.equals("y")) {
                resultTicket.downloadTicket();
                break;
            } else if (input.equals("n")) {
                break;
            } else {
                System.out.println("Invalid input. Please enter 'Y' or 'N'.");
            }
        }
    }

    // --- Date/Time Utility Methods ---
    
    public Date createFinalTravelDate(Bus selectedBus, Date storedDate) {
        // 1. Calculate the time offset from midnight in milliseconds
        long timeOffsetMillis = (selectedBus.departureTimeHour * MILLIS_PER_HOUR) + 
                                (selectedBus.departureTimeMin * MILLIS_PER_MINUTE);
        
        // 2. Get the base date's milliseconds (which should be midnight)
        // Note: Assumes storedDate is already normalized to midnight (00:00:00) by the Date parser.
        long baseDateMillis = storedDate.getTime();
        
        // 3. Combine the base date and the time offset
        long finalTravelTimeMillis = baseDateMillis + timeOffsetMillis;
        
        // 4. Create the new Date object
        return new Date(finalTravelTimeMillis); 
    }
    
    public boolean canBookTicket(Bus selectedBus, Date storedDate) {
        
        // 1. Combine the stored date and bus time
        Date finalTravelDate = createFinalTravelDate(selectedBus, storedDate);
        
        Date currentDate = new Date();
        
        // 2. Calculate the difference: Final Travel Time - Current Time
        long diffInMillis = finalTravelDate.getTime() - currentDate.getTime();
        
        // Calculate difference in hours for display
        long diffInHours = diffInMillis / MILLIS_PER_HOUR; 
    
        System.out.println("--- Booking Eligibility Check ---");
        System.out.println("Scheduled Departure: " + finalTravelDate);
        System.out.println("Current Time:        " + currentDate);
        System.out.println("Time remaining (hours): " + diffInHours);

        // 3. Check if the difference is strictly greater than 2 hours
        if (diffInMillis > TWO_HOURS_IN_MILLIS) {
            System.out.println("Verdict:  BOOKABLE (More than 2 hours to departure)");
            return true;
        } else {
            System.out.println("Verdict:  NOT BOOKABLE (Less than 2 hours or time has passed)");
            return false;
        }
    }
    
    public boolean ticketCancelable(Date travelDate) {

        Date currentDate = new Date(); 
        
        long diffInMillis = travelDate.getTime() - currentDate.getTime();
        
        long diffInHours = diffInMillis / MILLIS_PER_HOUR; 
    
        System.out.println("Difference in hours (Travel - Current): " + diffInHours);

        if (diffInMillis > FOUR_HOURS_IN_MILLIS) {
            System.out.println("Status: CANCELABLE (Travel is more than 4 hours in the future)");
            return true;
        } else {
            System.out.println("Status: NOT CANCELABLE (Too close to travel, or travel has passed)");
            return false;
        }
    }

    // --- Menu Methods (Main logic separated into helpers) ---
    
    private int getUserMenuChoice() {
         while (true) {
            System.out.println("\n--- User Menu ---");
            System.out.println("1. Search & Book a Bus");
            System.out.println("2. View My Booking Details");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Logout (Return to Main Menu)");
            System.out.print("Enter your choice: ");

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
                continue;
            }
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline
            return choice;
        }
    }
    
    private Date promptAndParseDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        formatter.setLenient(false); // Enforce strict format checking

        String dateString = null;
        Date storedDate = null;

        while (true) {
            System.out.println("\nEnter a date in the format DD/MM/YYYY (e.g., 31/12/2025):");
            
            dateString = sc.nextLine();
            
            try {
                storedDate = formatter.parse(dateString);
                System.out.println("\nValid date entered!");
                return storedDate; 
                
            } catch (ParseException e) {
                System.err.println("Error: '" + dateString + "' is in a WRONG FORMAT or is an invalid date. Please use DD/MM/YYYY.");
            }
        }
    }

    private void handleSearchAndBooking() {
        System.out.print("Source : ");
        source = sc.nextLine();
        System.out.print("Destination : ");
        destination = sc.nextLine();
        
        Date storedDate = promptAndParseDate();
        searchBus(source, destination, storedDate);

        if (availableBuses == 0) {
            System.out.println(" No buses available for this route.");
        } else {
            handleBusSelectionAndBooking(storedDate);
        }
    }
    
    private void handleBusSelectionAndBooking(Date storedDate) {
        while (true) {
            System.out.println("-----------------------------------------");
            System.out.println("Select the option of the bus you want to book (enter number 1 to "
                    + availableBuses + "): "); // Changed prompt to start from 1

            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next();
                continue;
            }
            int choice = sc.nextInt();
            sc.nextLine(); // Consume newline

            // The original logic used 0 to (availableBuses - 1) for array indexing,
            // but the printout used a 1-based index `availableBuses++`.
            // Sticking to 0-based index for `searchedBus` access, but adjusting for user input.
            int busIndex = choice - 1; 

            if (busIndex >= 0 && busIndex < availableBuses) {
                Bus selectedBus = searchedBus[busIndex];
                System.out.println(" You selected: ");
                selectedBus.show();
                
                if(canBookTicket(selectedBus, storedDate)){
                    selectSeats(selectedBus, storedDate);
                } else {
                    System.out.println("Sorry, you can't book tickets for this bus now.");
                }
                break;
            } else {
                System.out.println("Invalid selection! Please try again.");
            }
        }
    }

    private void handleCancellationLogic() {
        if(currentUser.getTicketCount()==0){
            System.out.println("No tickets Found");
            return;
        }

        currentUser.viewBookings();
        System.out.println("Enter Ticket Id of the ticket you want to cancel");
        
        if (!sc.hasNextDouble()) {
            System.out.println("Invalid input for Ticket ID.");
            sc.next();
            return;
        }
        double cancelTicketId = sc.nextDouble();
        sc.nextLine(); // Consume newline

        // The original code was iterating and showing tickets again, simplified here.
        
        System.out.println("Are you sure you want to cancel this ticket (Y/N)?");
        String confirmationOfCancelTicket = sc.nextLine().toLowerCase().trim();
        
        if (!confirmationOfCancelTicket.equals("y")) {
            System.out.println("Cancellation aborted by user.");
            return;
        }
        
        boolean ticketFoundAndCanceled = false;
        
        // Find and process cancellation
        for (int i = 0; i < currentUser.tickets.length; i++) {
            Ticket t = currentUser.tickets[i];
            
            if(t != null && t.getTicketId() == cancelTicketId) {
                System.out.println("Found Ticket ID: " + t.getTicketId());
                System.out.println("Scheduled Travel Date: " + t.travelDate);
                
                if(ticketCancelable(t.travelDate)) {
                    System.out.println("Your Ticket Will be canceled...");
                    
                    // Unbook seats
                    Seat[] cancelTicketSeats = t.getSeats();
                    for (Seat seat : cancelTicketSeats) {
                        if(seat != null) {
                            t.getBus().availableSeatsArray[seat.seatNo - 1].isBooked = false;
                            t.getBus().availbleSeats++;
                        }
                    }
                    
                    // Remove ticket from user's array
                    currentUser.tickets[i] = null;
                    currentUser.setTicketCount(currentUser.getTicketCount() - 1);
                    
                    System.out.println("Successfully Canceled Your Ticket!");
                    ticketFoundAndCanceled = true;
                    break;
                } else {
                    System.out.println("Your Ticket Cannot Be Canceled: Not within the cancellation window.");
                    ticketFoundAndCanceled = true; // Still found the ticket
                    break;
                }
            }
        }
        
        if (!ticketFoundAndCanceled) {
            System.out.println("Error: Ticket with ID " + cancelTicketId + " not found or is already canceled.");
        }
    }


    // --- Main Menu Method ---
    
    public void showMenu() {
        System.out.println("\n--- User Logged In ---");
        while (true) {
            int userChoice = getUserMenuChoice();

            switch (userChoice) {
                case 1:
                    handleSearchAndBooking();
                    break;
                case 2:
                    if (currentUser != null) {
                        currentUser.viewBookings();
                    }
                    break;
                case 3:
                    if (currentUser != null) {
                        handleCancellationLogic();
                    }
                    break;
                case 4:
                    System.out.println("Logging out...");
                    currentUser = null;
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }
}