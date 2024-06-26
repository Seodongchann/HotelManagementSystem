
public class Room {
    public static final int ROOM_STATE_EMPTY = 0;
    public static final int ROOM_STATE_RESERVED = 1;
    public static final int ROOM_STATE_OCCUPIED = 2;
    public static final int BED_TYPE_DOUBLE = 0;
    public static final int BED_TYPE_SINGLE = 1;
    private int roomNum;
    private int bedType; // 0 : 더블베드 1 : 싱글 베드
    private int roomState; // 0 : 빈방 1 : 예약 2 : 투숙
    private Customer customer;

    public Room(int roomNum) {
        super();
        this.roomNum = roomNum;
        bedType = roomNum % 2;// 0 : 더블베드 1 : 싱글 베드
    }

    public int getRoomNum() {
        return roomNum;
    }

    public int getBedType() {
        return bedType;
    }

    public int getRoomState() {
        return roomState;
    }

    public void setRoomState(int roomState) {
        this.roomState = roomState;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public void printState() { // 객실 상태 출력
        System.out.println(roomNum + "호");
        switch (roomState) {
        case ROOM_STATE_EMPTY:
            System.out.println("-빈 객실");
            break;
        case ROOM_STATE_RESERVED:
            System.out.println("-예약된 객실");
            break;
        case ROOM_STATE_OCCUPIED:
            System.out.println("-투숙중인 객실");
            break;
        }
        switch (bedType) {
        case BED_TYPE_DOUBLE:
            System.out.println("침대 : 더블");
            break;
        case BED_TYPE_SINGLE:
            System.out.println("침대 : 싱글");
            break;
        }
        if (customer != null) {
            System.out.println("고객명 : " + customer.getName());
            System.out.println("전화번호 : " + customer.getPhoneNum());
            System.out.println("생년월일 : " + customer.getBirth());
        }
    }

    public String getRoomStateString() { // 객실 상태 문자열로 반환
        switch (roomState) {
//        case ROOM_EMPTY:
//            return "(빈방)";
        case ROOM_STATE_RESERVED:
            return "(예약)";
        case ROOM_STATE_OCCUPIED:
            return "(투숙)";
        }
        return "";
    }

}
