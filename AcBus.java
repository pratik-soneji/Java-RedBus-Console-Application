import java.util.Date;

class AcBus extends Bus {
    private static double perKmPrice = 3;
    static String type="AC";
    
    public AcBus(String busId, String driverName, String busType, String source, String destination,
    int departureTimeHour, int departureTimeMin, int availbleSeats, int totalSeats, int km, Seat[] availableSeatsArray,Date departureDate) {

        super(busId, driverName, busType, source, destination, departureTimeHour, departureTimeMin, availbleSeats, totalSeats, km, availableSeatsArray,departureDate);
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
        AcBus.perKmPrice = perKmPrice;
    }
}
