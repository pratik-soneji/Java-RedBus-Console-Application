public class Seat {
    protected int seatNo;
    protected String seatType;
    boolean isBooked = false;
    public Seat(int seatNo, String seatType){
        this.seatNo=seatNo;
        this.seatType=seatType;
    }
    int getSeatNumber() {
        return seatNo;
    }

    String getSeatType() {
        return seatType;
    }
    public void setBooked(boolean isBooked) {
        this.isBooked = isBooked;
    }
}