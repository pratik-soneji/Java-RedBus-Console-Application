import java.util.Date;

class NormalBus extends Bus {
    private static double perKmPrice = 1.5;
    static String type="Normal";
    public NormalBus(String busNo, String driverName, String busType, String source, String destination,
    int departureTimeHour, int departureTimeMin, int availbleSeats, int totalSeats, int km, Seat[] availableSeatsArray,Date departureDate) {
        

        super(busNo, driverName, busType, source, destination, departureTimeHour, departureTimeMin, availbleSeats, totalSeats, km,availableSeatsArray, departureDate);
        this.busType=type;
        
    }
    
    double calculateFare(int numberOfSeats){
        return this.getKm()*perKmPrice*numberOfSeats;
    }

    public static double getPerKmPrice() {
        return perKmPrice;
    }
    void adminViewAllBus(){
        System.out.println("Bus No: " + getBusNo() + " Driver : "+getDriverName() +
                "\tBus Type: " + busType +
                "\tSource: " + getSource() +
                "\tDestination: " + getDestination() +
                "\n\tDeparture Time: " + departureTimeHour +" : "+departureTimeMin+
                // "\tFare: " + fare +
                "\tBooked Seats: " + (totalSeats-availbleSeats) +
                "\tAvailable Seats: " + availbleSeats +
                "\tTotal Seats: " + totalSeats + 
                "\nPer Seat Ticket Price: "+this.getKm()*perKmPrice*1+
                "\tTotal Amount Recieved for today's collection: "+this.getKm()*perKmPrice*(totalSeats-availbleSeats));
    }
    public static void setPerKmPrice(int perKmPrice) {
        NormalBus.perKmPrice = perKmPrice;
    }
    
}
