public class Room {
	public static final int ROOM_STATE_EMPTY = 0;
	public static final int ROOM_STATE_RESERVED = 1;
	public static final int ROOM_STATE_OCCUPIED = 2;
	public static final int ROOM_STATE_CLOSED = 3;

	public static final int ROOM_TYPE_STANDARD1 = 0; // 싱글1개/1명/엑스트라 불가 /
														// 201-210
	public static final int ROOM_TYPE_STANDARD2 = 1; // 싱글2개/3명/엑스트라 가능 / 211-20
	public static final int ROOM_TYPE_SUPERIOR1 = 2; // 더블 1개 싱글 1개/4명/엑스트라 가능
														// //401-10
	public static final int ROOM_TYPE_SUPERIOR2 = 3; // 더블 2개 /5명/엑스트라 가능 //
														// 411-20
	public static final int ROOM_TYPE_DELUXE1 = 4; // 퀸 1개/2명/엑스트라 불가 // 301-10
	public static final int ROOM_TYPE_DELUXE2 = 5; // 킹 1개/2명/엑스트라 불가 // 311-20
	public static final int ROOM_TYPE_EXECUTIVE1 = 6; // 퀸 1개 킹 1개/4명/엑스트라 가능
														// 501-10
	public static final int ROOM_TYPE_EXECUTIVE2 = 7; // 더블 2개 킹 1개/6명/엑스트라 가능
														// 511-19
	public static final int ROOM_TYPE_SUITE = 8; // 킹 4개/8명/엑스트라 불가 520

	private int roomNum;
	private int roomState; // 0 : 빈방 1 : 예약 2 : 투숙
	private Customer customer;
	private Customer reservationCustomer;
	private int roomType; // 스탠다드 1,2 | 슈페리어 1,2 | 디럭스 1,2 | 이그젝큐티브 1,2 | 스위트
	private int guestNum;
	private boolean useExtraBed;

	public Room(int roomNum) { // 생성자
		super();
		this.roomNum = roomNum;
	}

	public Room(int roomNum, int roomType) {
		super();
		this.roomNum = roomNum;
		this.roomType = roomType;
	}

	public int getRoomNum() {
		return roomNum;
	}

	public int getRoomType() {
		return roomType;
	}

	public void setRoomType(int roomType) {
		this.roomType = roomType;
	}

	public int getGuestNum() {
		return guestNum;
	}

	public void setGuestNum(int guestNum) {
		this.guestNum = guestNum;
	}

	public boolean isUseExtraBed() {
		return useExtraBed;
	}

	public void setUseExtraBed(boolean useExtraBed) {
		this.useExtraBed = useExtraBed;
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

	public Customer getReservationCustomer() {
		return reservationCustomer;
	}

	public void setReservationCustomer(Customer reservationCustomer) {
		this.reservationCustomer = reservationCustomer;
	}

	/**
	 * 객실의 상태를 출력하는 함수
	 */
	public void printState() {
		System.out.println(roomNum + "호 " + getGradeOfRoom());
		switch (roomState) { // 방 상황
			case ROOM_STATE_EMPTY :
				System.out.println("-빈 객실");
				break;
			case ROOM_STATE_RESERVED :
				System.out.println("-예약된 객실");
				break;
			case ROOM_STATE_OCCUPIED :
				System.out.println("-투숙중인 객실");
				break;
			case ROOM_STATE_CLOSED :
				System.out.println("-폐쇄된 객실");
		}
		// 엑스트라 배드 가용 여부, 수용 인원, 침대 타입 문자열 반환
		System.out.println(getBedInfo());
		System.out.println("가능 인원 : " + getCapacity() + "인");
		if (canUseExtraBed()) {
			System.out.println("엑스트라 : 가능");
		} else {
			System.out.println("엑스트라 : 불가");
		}

		if (reservationCustomer != null) {
			System.out.println("-예약자 정보");
			System.out.println("고객명 : " + reservationCustomer.getName());
			System.out.println("전화번호 : " + reservationCustomer.getPhoneNum());
			System.out.println("생년월일 : " + reservationCustomer.getBirth());
		}
		if (customer != null) { // 고객 출력
			System.out.println("-사용자 정보");
			System.out.println("고객명 : " + customer.getName());
			System.out.println("전화번호 : " + customer.getPhoneNum());
			System.out.println("생년월일 : " + customer.getBirth());
		}
	}

	public String getRoomStateString() { // 객실 상태 문자열로 반환
		switch (roomState) {
			case ROOM_STATE_EMPTY :
				return "     ";
			case ROOM_STATE_RESERVED :
				return "(예약)";
			case ROOM_STATE_OCCUPIED :
				return "(투숙)";
			case ROOM_STATE_CLOSED :
				return "(폐쇄)";
		}
		return "";
	}

	public int getRoomFee() {
		switch (roomType) {
			case ROOM_TYPE_STANDARD1 :
				return 30000;
			case ROOM_TYPE_STANDARD2 :
				return 40000;
			case ROOM_TYPE_SUPERIOR1 :
				return 60000;
			case ROOM_TYPE_SUPERIOR2 :
				return 70000;
			case ROOM_TYPE_DELUXE1 :
				return 100000;
			case ROOM_TYPE_DELUXE2 :
				return 110000;
			case ROOM_TYPE_EXECUTIVE1 :
				return 140000;
			case ROOM_TYPE_EXECUTIVE2 :
				return 150000;
			case ROOM_TYPE_SUITE :
				return 2_000_000;
		}
		return -1;
	}

	public String getGradeOfRoom() { // 객실 등급 문자열로 반환
		switch (roomType) { // 룸 타입
			case ROOM_TYPE_STANDARD1 :
				return "(스탠다드1)";
			case ROOM_TYPE_STANDARD2 :
				return "(스탠다드2)";
			case ROOM_TYPE_SUPERIOR1 :
				return "(슈페리어1)";
			case ROOM_TYPE_SUPERIOR2 :
				return "(슈페리어2)";
			case ROOM_TYPE_DELUXE1 :
				return "(디럭스1)";
			case ROOM_TYPE_DELUXE2 :
				return "(디럭스2)";
			case ROOM_TYPE_EXECUTIVE1 :
				return "(이그젝큐티브1)";
			case ROOM_TYPE_EXECUTIVE2 :
				return "(이그젝큐티브2)";
			case ROOM_TYPE_SUITE :
				return "(스위트)";
		}
		return "";
	}

	public static String getGradeOfRoomInfo(int roomType) { // 객실 등급 문자열로 반환
		switch (roomType) { // 룸 타입
			case ROOM_TYPE_STANDARD1 :
				return "[스탠다드1]";
			case ROOM_TYPE_STANDARD2 :
				return "[스탠다드2]";
			case ROOM_TYPE_SUPERIOR1 :
				return "[슈페리어1]";
			case ROOM_TYPE_SUPERIOR2 :
				return "[슈페리어2]";
			case ROOM_TYPE_DELUXE1 :
				return "[디럭스1]";
			case ROOM_TYPE_DELUXE2 :
				return "[디럭스2]";
			case ROOM_TYPE_EXECUTIVE1 :
				return "[이그젝큐티브1]";
			case ROOM_TYPE_EXECUTIVE2 :
				return "[이그젝큐티브2]";
			case ROOM_TYPE_SUITE :
				return "[스위트]";
		}
		return "";
	}

	public boolean canUseExtraBed() {
		switch (roomType) {
			case ROOM_TYPE_STANDARD1 :
			case ROOM_TYPE_DELUXE1 :
			case ROOM_TYPE_DELUXE2 :
			case ROOM_TYPE_SUITE :
				return false;
		}
		return true;
	}

	public int getCapacity() {
		switch (roomType) {
			case ROOM_TYPE_STANDARD1 :
				return 1;
			case ROOM_TYPE_DELUXE1 :
			case ROOM_TYPE_DELUXE2 :
				return 2;
			case ROOM_TYPE_STANDARD2 :
				return 3;
			case ROOM_TYPE_SUPERIOR1 :
			case ROOM_TYPE_EXECUTIVE1 :
				return 4;
			case ROOM_TYPE_SUPERIOR2 :
				return 5;
			case ROOM_TYPE_EXECUTIVE2 :
				return 6;
			case ROOM_TYPE_SUITE :
				return 8;
		}
		return 0;
	}

	public String getBedInfo() {
		switch (roomType) { // 룸 타입
			case ROOM_TYPE_STANDARD1 :
				return "침대 정보 : 싱글 1개";
			case ROOM_TYPE_STANDARD2 :
				return "침대 정보 : 싱글 2개";
			case ROOM_TYPE_SUPERIOR1 :
				return "침대 정보 : 더블 1개, 싱글 1개";
			case ROOM_TYPE_SUPERIOR2 :
				return "침대 정보 : 더블 2개";
			case ROOM_TYPE_DELUXE1 :
				return "침대 정보 : 퀸 1개";
			case ROOM_TYPE_DELUXE2 :
				return "침대 정보 : 킹 1개";
			case ROOM_TYPE_EXECUTIVE1 :
				return "침대 정보 : 퀸 1개, 킹 1개";
			case ROOM_TYPE_EXECUTIVE2 :
				return "침대 정보 : 더블 2개, 킹 1개";
			case ROOM_TYPE_SUITE :
				return "침대 정보 : 킹 4개";
		}
		return null;
	}

	public static String getBedInfo(int roomType) {
		switch (roomType) { // 룸 타입
			case ROOM_TYPE_STANDARD1 :
				return "침대 정보 : 싱글 1개";
			case ROOM_TYPE_STANDARD2 :
				return "침대 정보 : 싱글 2개";
			case ROOM_TYPE_SUPERIOR1 :
				return "침대 정보 : 더블 1개, 싱글 1개";
			case ROOM_TYPE_SUPERIOR2 :
				return "침대 정보 : 더블 2개";
			case ROOM_TYPE_DELUXE1 :
				return "침대 정보 : 퀸 1개";
			case ROOM_TYPE_DELUXE2 :
				return "침대 정보 : 킹 1개";
			case ROOM_TYPE_EXECUTIVE1 :
				return "침대 정보 : 퀸 1개, 킹 1개";
			case ROOM_TYPE_EXECUTIVE2 :
				return "침대 정보 : 더블 2개, 킹 1개";
			case ROOM_TYPE_SUITE :
				return "침대 정보 : 킹 4개";
		}
		return null;
	}
}