import java.time.LocalDateTime;



interface Payment  {
    String s = "Pending";

    public void pay();
}

class UpiPay implements Payment{
    
    protected int paymentId;
    double amount;
    LocalDateTime paymentDate;
    private String upiId;

    UpiPay(double amount, String upiId) {
        this.amount=amount;
        this.upiId = upiId;
        this.paymentDate=LocalDateTime.now();
    }

    boolean verifyUpi() {
        return upiId.contains("@");
    }

    public void pay() {
        if (verifyUpi()) {
            System.out.println("UPI payment of Rs. " + amount + " successful!");
        } else {
            System.out.println("Invalid UPI ID!");
        }
    }
    boolean requested;
    synchronized void put(){
        while(requested){
            try {
            wait();//lock
            } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
        }
        
        System.out.println("Requested : "+requested);
        notify();//release lock
        requested=true;
    }
        synchronized void get(){
            while(!requested){
                try {
                wait();//lock
                } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                }
            }
            System.out.println("Request Accepted");
            pay();
            notify();//release lock
            requested=false;
            
        }
}