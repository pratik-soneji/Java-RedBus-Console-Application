import java.util.Date;
import java.util.Scanner;
public class RedBusApp extends BookingService {
    private static final Scanner sc = new Scanner(System.in);
    static {
        initializeData();
    }
    private static void initializeData() {
        Seat s[] = new Seat[30];
        int seatCount = 0;
        for (int i = 0; i < 30; i++) {
            s[i] = new Seat(++seatCount, "Normal"); 
        }
        
        Bus newBus = new NormalBus("123", "Magan", "Normal", "a", "b", 10, 30, 30, 30, 300, s, new Date());
        busArray[busCount] = newBus;
        busCount++;
        
        User user = new User("a", "a", "a", "a@example.com", 1);
        userArray[userCount++] = user;
    }
    public void startApplication() {
        while (true) {
            displayMainMenu();
            
            if (!sc.hasNextInt()) {
                System.out.println("Invalid input. Please enter a number.");
                sc.next(); 
                continue;
            }
            int userChoice = sc.nextInt();
            sc.nextLine(); 

            switch (userChoice) {
                case 1:
                    handleAdminLogin();
                    break;
                case 2:
                    handleUserMenu();
                    break;
                case 3:
                    System.out.println("Exiting...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 3.");
                    break;
            }
        }
    }

    private void displayMainMenu() {
        System.out.println("\n========================================");
        System.out.println("        ONLINE BUS BOOKING SYSTEM");
        System.out.println("========================================");
        System.out.println("1. Admin Login");
        System.out.println("2. User Menu");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
    }

    private void handleAdminLogin() {
        System.out.print("Enter UserName: ");
        String userName = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        Admin admin = new Admin();
        if (admin.verifyCredentials(userName, password)) {
            admin.showMenu();
        } else {
            System.out.println("Invalid credentials. Please try again.");
        }
    }

    private void handleUserMenu() {
        
        BookingService system = new BookingService();
        system.handleUser();
    }

    public static void main(String[] args) {
        RedBusApp application = new RedBusApp();
        application.startApplication();
    }
}