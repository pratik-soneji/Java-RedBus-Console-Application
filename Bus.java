import java.util.Date;

public abstract class  Bus {
    private final String busNo;
    private String driverName;
    protected  String busType;
    private String source;
    private String destination;
    public int departureTimeHour;
    public int departureTimeMin;
    protected int availbleSeats;
    protected final int totalSeats;
    protected int km;
    Seat[] availableSeatsArray;
    Date departureDate;

    public String getDriverName() {
        return driverName;
    }
    public Bus(String busNo, String driverName, String busType, String source, String destination, int departureTimeHour, int departureTimeMin,
 int availbleSeats, int totalSeats,int km,Seat[] availableSeatsArray, Date departureDate) {
        this.busNo = busNo;
        this.driverName = driverName;
        this.busType = busType;
        this.source = source;
        this.destination = destination;
        this.departureTimeHour = departureTimeHour;
        this.departureTimeMin = departureTimeMin;
        this.availbleSeats = availbleSeats;
        this.totalSeats = totalSeats;
        this.km = km;
        this.availableSeatsArray = availableSeatsArray; 
        this.departureDate=departureDate;
    }
    void updateSeats(int n){
        availbleSeats = availbleSeats - n;
    }
   
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }
    public void setSource(String source) {
        this.source = source;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public int getDepartureTimeHour() {
        return departureTimeHour;
    }
    public void setDepartureTimeHour(int departureTimeHour) {
        this.departureTimeHour = departureTimeHour;
    }
    public int getDepartureTimeMin() {
        return departureTimeMin;
    }
    public void setDepartureTimeMin(int departureTimeMin) {
        this.departureTimeMin = departureTimeMin;
    }
    public void setAvailbleSeats(int availbleSeats) {
        this.availbleSeats = availbleSeats;
    }
    public void setKm(int km) {
        this.km = km;
    }
    public Date getDepartureDate() {
        return departureDate;
    }
    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }
    abstract void adminViewAllBus();
    public final void show() {
        System.out.println("Bus No: " + busNo +
                "\tBus Type: " + busType +
                "\tSource: " + source +
                "\tDestination: " + destination +
                "\n\tDeparture Time: " + departureTimeHour +" : "+departureTimeMin+
                "\tAvailable Seats: " + this.availbleSeats +
                "\tTotal Seats: " + totalSeats + "\n ");
    }
    public void showDetails(){
        System.out.println("Available Seats Map (Dummy):");
        for (Seat seat : availableSeatsArray) {
                if(seat.isBooked==false){
                    if(seat.seatNo%3==0){
                        System.out.print(seat.seatNo+"W ");
                    }else{
                        System.out.print(seat.seatNo+" ");
                    }
                }
        }
        System.out.println();
    }

    public void setAvailableSeatsArray(Seat[] availableSeatsArray) {
        this.availableSeatsArray = availableSeatsArray;
    }
    public String getSource() { return this.source; }
    public String getDestination(){ return this.destination; }
    public String getBusNo() { return busNo; }
    public String getBusType() { return busType; }
    // public double getFare() { return fare; }
    public int getAvailbleSeats() { return availbleSeats; }
    public int getTotalSeats() { return totalSeats; }
    public int getKm() { return km; }
    // public String getDepartureTime() { return departureTime; }

}