
class UserUpiPay extends Thread{
        UpiPay p;
        UserUpiPay(UpiPay p){
            this.p=p;
            start();
        }
    public void run(){
        
            synchronized(p){
            p.put();
            }
            try {
            Thread.sleep(500);
            } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            }
    }
}
class UserUpiPayResponse extends Thread{
    UpiPay p;
    UserUpiPayResponse(UpiPay p){
        this.p=p;
        start();
    }
    public void run(){
            synchronized(p){
            p.get();
            }
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
    }
}
