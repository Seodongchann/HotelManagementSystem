
public class Main {
    public static void main(String[] args) {
        HotelSystem system = new HotelSystem("0000");
        Customer customer = new Customer("홍길동", "90/10/10", "010-1010-1010");
        customer.equals(new Customer("홍길동", "90/11/10", "010-1010-1010"));
//        system.test();
        system.mainRun();
    }
}
