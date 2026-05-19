
public class User {
    private String userName;
    private String passWord;
    private String userId;
    private String email;
    private long phoneNum;
    protected Ticket[] tickets = new Ticket[2];
    private int ticketCount = 0; // Counter for tickets array

    public User(String userName, String passWord, String userId, String email, long phoneNum) {
        this.userName = userName;
        this.passWord = passWord;
        this.userId = userId;
        this.email = email;
        this.phoneNum = phoneNum;
        // tickets array is initialized above
    }

    public void showUser() {
        System.out.println("User ID: " + userId);
        System.out.println("Name: " + userName);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phoneNum);
        System.out.println("-----------------------------");
    }

    public boolean checkCredentials(String userName,String passWord) {
        return userName.equals(this.userName) && passWord.equals(this.passWord);
    }

    public String getUserId() { return userId; }
    public String getUserName() { return userName; }
    public void setUserName(String userName) { this.userName = userName; }
    public String getPassWord() { return passWord; }
    public void setPassWord(String passWord) { this.passWord = passWord; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public long getPhoneNum() { return phoneNum; }
    public void setPhoneNum(long phoneNum) { this.phoneNum = phoneNum; }

    public int getTicketCount() {
        return ticketCount;
    }

    public void addTicket(Ticket t){
        if (ticketCount < tickets.length) {
            this.tickets[ticketCount++] = t;
        } else {
            System.out.println("Cannot add ticket. User ticket limit reached (2).");
        }
    }

    void viewBookings(){
        if (ticketCount == 0) {
            System.out.println("\nNo bookings found.");
            return;
        }
        System.out.println("\n--- YOUR BOOKINGS (" + ticketCount + ") ---");
        for (int i = 0; i < ticketCount; i++) {
            if(tickets[i]!=null){
                tickets[i].showTicket();
            }
        }
    }

    public void setTicketCount(int ticketCount) {
        this.ticketCount = ticketCount;
    }
    
}