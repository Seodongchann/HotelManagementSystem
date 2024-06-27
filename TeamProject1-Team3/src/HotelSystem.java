import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotelSystem {
	private final int NUMBERS_OF_FLOORS = 4;
	private final int NUMBERS_OF_ROOMS = 20;
	private Scanner scanner = new Scanner(System.in);
	private Room rooms[][] = new Room[NUMBERS_OF_FLOORS][NUMBERS_OF_ROOMS];
	private String adminPassword;

	public HotelSystem(String adminPassword) { // 생성자
		super();
		this.adminPassword = adminPassword;
		for (int i = 0; i < NUMBERS_OF_FLOORS; i++) {
			for (int j = 0; j < NUMBERS_OF_ROOMS; j++) {
				int roomNum = (i + 2) * 100 + j + 1;
				rooms[i][j] = new Room(roomNum); // 모든 방 방번호로 생성
			}

		}

		for (int i = 0; i < 10; i++) {
			rooms[0][i].setRoomType(Room.ROOM_TYPE_STANDARD1);
		}
		for (int i = 10; i < 20; i++) {
			rooms[0][i].setRoomType(Room.ROOM_TYPE_STANDARD2);
		}
		for (int i = 0; i < 10; i++) {
			rooms[1][i].setRoomType(Room.ROOM_TYPE_DELUXE1);
		}
		for (int i = 10; i < 20; i++) {
			rooms[1][i].setRoomType(Room.ROOM_TYPE_DELUXE2);
		}
		for (int i = 0; i < 10; i++) {
			rooms[2][i].setRoomType(Room.ROOM_TYPE_SUPERIOR1);
		}
		for (int i = 10; i < 20; i++) {
			rooms[2][i].setRoomType(Room.ROOM_TYPE_SUPERIOR2);
		}
		for (int i = 0; i < 10; i++) {
			rooms[3][i].setRoomType(Room.ROOM_TYPE_EXECUTIVE1);
		}
		for (int i = 10; i < 19; i++) {
			rooms[3][i].setRoomType(Room.ROOM_TYPE_EXECUTIVE2);
		}

		rooms[3][19].setRoomType(Room.ROOM_TYPE_SUITE);
		rooms[2][3].setRoomState(Room.ROOM_STATE_CLOSED);
		rooms[2][13].setRoomState(Room.ROOM_STATE_CLOSED);
	}

	public void mainRun() { // 프로그램 실행
		while (true) {
			printMainScreen();
			int input = inputIntInRange(0, 2);
			switch (input) {
				case 0 :
					System.out.println("프로그램을 종료합니다.");
					scanner.close();
					return;
				case 1 :
					if (checkAdmin())
						managerRun();
					break;
				case 2 :
					cleanerRun();
					break;
			}
		}
	}

	private void managerRun() { // 관리자 실행
		while (true) {
			printManageScreen();
			int input = inputIntInRange(0, 3);
			switch (input) {
				case 0 :
					System.out.println("로그아웃 완료");
					return;
				case 1 :
					checkRoomState();
					break;
				case 2 :
					chageRoomState();
					break;
				case 3 :
					manageReservation();
					break;
			}
		}
	}

	private void cleanerRun() {
		while (true) {
			printCleanerScreen();
			int input = inputIntInRange(0, 1);
			switch (input) {
				case 0 :
					System.out.println("로그아웃 완료");
					return;
				case 1 :
					checkFloorState();
					break;
			}
		}
	}

	private void printMainScreen() { // 메인 화면 출력
		System.out.println("사용자 번호를 입력하시오.");
		System.out.println("[1] 관리자\n[2] 청소부\n[0] 프로그램 종료");
	}

	private void printManageScreen() { // 관리자 화면 출력
		System.out.println("메뉴 번호를 입력하시오.");
		System.out.println("[1] 객실 상태 확인\n[2] 객실 상태 변경\n[3] 예약 관리\n[0] 로그아웃");
	}

	private void printCleanerScreen() { // 청소부 화면 출력
		System.out.println("메뉴 번호를 입력하시오.");
		System.out.println("[1] 객실 상태 확인\n[0] 로그아웃");
	}

	private boolean checkAdmin() { // 비밀번호 확인
		System.out.println("비밀번호를 입력하시오.");
		if (adminPassword.equals(scanner.nextLine())) {
			return true;
		} else {
			System.out.println("비밀번호가 틀렸습니다.");
			return false;
		}
	}

	private void checkFloorState() { // 청소부가 층의 상태 확인
		int floor = selectFloor();
		if (floor == 0) {
			System.out.println("취소하셨습니다.");
			return;
		}
		printFloorState(floor);
	}

	private void printFloorState(int floor) { // 층의 상태 출력
		int prevRoomType = -1;
		System.out.println("A동 " + floor + "층");
		for (int i = 0; i < rooms[floor - 2].length; i++) {
			Room room = rooms[floor - 2][i];
			int roomType = room.getRoomType();
			if (roomType != prevRoomType) {
				if (i != 0)
					System.out.println();
				System.out.println(
						room.getGradeOfRoom() + " (" + room.getBedInfo() + ")");
			}
			System.out.print(room.getRoomNum() + room.getRoomStateString());
			prevRoomType = roomType;
		}
		System.out.println();
	}

	private int selectFloor() { // 층 선택해서 인트로 반환 (배열+2 반환)
		System.out.println(
				"층을 선택하시오. [2~" + (1 + NUMBERS_OF_FLOORS) + "] 취소 [0]");
		return inputIntInRangeAndSelect(2, 1 + NUMBERS_OF_FLOORS, 0);
	}

	private Room selectRoom() { // 객실을 선택해서 Room 반환
		int input = 0;
		int floor = 0;
		do {
			floor = selectFloor();
			if (floor == 0)
				return null;
			System.out.println("객실을 선택하시오. [" + floor + "01~" + floor
					+ "20] 층 선택 [1] 취소 [0]");
			printFloorState(floor);
			input = inputIntInRangeAndSelect(floor * 100 + 1, floor * 100 + 20,
					0, 1);
			if (input == 0) {
				return null;
			}
		} while (input == 1);

		return rooms[floor - 2][input - floor * 100 - 1];
	}

	private void checkRoomState() { // 관리자가 객실 상태 확인
		Room room = selectRoom();
		if (room == null) { // 취소한 상황
			System.out.println("취소하셨습니다.");
			return;
		}
		room.printState();
	}

	private void chageRoomState() { // 객실 상태 변경
		printChangeRoomStateScreen();
		int changeRoomStateNum = inputIntInRange(0, 5);
		switch (changeRoomStateNum) {
			case 0 :
				break;
			case 1 :
				walkIn();
				break;
			case 2 :
				checkIn();
				break;
			case 3 :
				checkOut();
				break;
			case 4 :
				roomChange();
				break;
			case 5 :
				roomUpgrade();
				break;
		}
	}

	private void printChangeRoomStateScreen() { // 객실 상태 변경 화면 출력
		System.out.println("메뉴 번호를 입력하시오.");
		System.out.println(
				"[1] 워크 인\n[2] 체크 인\n[3] 체크 아웃\n[4] 룸 체인지 \n[5] 룸 업그레이드 \n[0] 뒤로가기");
	}

	private void walkIn() { // 워크 인
		Room room;
		while (true) {
			room = selectRoom();
			if (room == null) { // 취소한 상황
				System.out.println("변경을 취소하셨습니다.");
				return;
			}
			if (room.getRoomState() == Room.ROOM_STATE_EMPTY)
				break;
			System.out.println("투숙할 수 없는 객실입니다.");
		}

		Customer customer = inputCustomer();

		System.out.println("워크 인 하시겠습니까? [Y/N]");
		char input = inputYN();
		switch (input) {
			case 'Y' :
				room.setCustomer(customer);
				room.setRoomState(Room.ROOM_STATE_OCCUPIED);
				System.out.println("워크 인 되었습니다.");
				break;
			case 'N' :
				System.out.println("변경을 취소하셨습니다.");
				break;
		}
	}

	private void checkIn() { // 체크 인
		Customer customer = inputCustomer();

		if (!hasReservedRoomFromUser(customer)) {
			System.out.println("예약된 정보가 없습니다.");
			return;
		}

		Room room = selectReservedRoomFromUser(customer);

		System.out.println("체크인 하시겠습니까? [Y/N]");
		char checkInYesOrNo1 = inputYN();

		switch (checkInYesOrNo1) {
			case 'Y' :
				room.setReservationCustomer(null);
				room.setRoomState(Room.ROOM_STATE_OCCUPIED);
				break;
			case 'N' :
				System.out.println("변경을 취소하셨습니다.");
				break;
		}
	}

	private void checkOut() { // 체크 아웃
		Customer customer = inputCustomer();

		if (!hasOccupiedRoom(customer)) {
			System.out.println("투숙중인 방이 없습니다.");
			return;
		}

		Room room = selectOccupiedRoom(customer);

		System.out.println("체크아웃 하시겠습니까? [Y/N]");
		char input = inputYN();

		switch (input) {
			case 'Y' :
				room.setCustomer(null);
				room.setRoomState(Room.ROOM_STATE_EMPTY);
				break;
			case 'N' :
				System.out.println("변경을 취소하셨습니다.");
				break;
		}
	}

	private void roomChange() {
		Customer customer = inputCustomer();
		if (!hasOccupiedRoom(customer)) {
			System.out.println("예약된 정보가 없습니다.");
			return;
		}
		Room prevRoom = selectOccupiedRoom(customer);
		int prevRoomType = prevRoom.getRoomType();
		if (!hasChangedRoom(prevRoomType)) {
			System.out.println("변경 가능한 객실이 없습니다.");
			return;
		}

		Room nextRoom = selectChangedRoom(prevRoomType);

		System.out.println("변경하시겠습니까? [Y/N]");
		char input = inputYN();
		switch (input) {
			case 'Y' :
				nextRoom.setCustomer(customer);
				nextRoom.setRoomState(Room.ROOM_STATE_OCCUPIED);
				prevRoom.setCustomer(null);
				prevRoom.setRoomState(Room.ROOM_STATE_EMPTY);
				break;
			case 'N' :
				break;
		}
	}

	private Room[] getChangedRooms(int roomType) {
		List<Room> roomList = new ArrayList<>();
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room room = rooms[i][j];
				if (room.getRoomState() == Room.ROOM_STATE_EMPTY) {
					if (room.getRoomType() == roomType)
						roomList.add(room);
				}
			}
		}
		return roomList.toArray(new Room[0]);
	}

	private Room selectChangedRoom(int roomType) {
		Room[] changedRooms = getChangedRooms(roomType);
		System.out.println("변경할 객실을 선택하시오.");
		for (int i = 0; i < changedRooms.length; i++) {
			System.out.print(
					"[" + (i + 1) + "] " + changedRooms[i].getRoomNum() + "호 ");
			if ((i + 1) % 5 == 0)
				System.out.println();
			if ((i + 1) % 5 != 0 && i == changedRooms.length - 1)
				System.out.println();
		}
		int input = inputIntInRange(1, changedRooms.length);
		return changedRooms[input - 1];
	}

	// 객실상태 변경 추가 룸체인지
	private boolean hasChangedRoom(int roomType) {
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room room = rooms[i][j];
				if (room.getRoomType() == roomType) {
					if (room.getRoomState() == Room.ROOM_STATE_EMPTY)
						return true;
				}
			}
		}
		return false;
	}

	private void roomUpgrade() {
		Customer customer = inputCustomer();
		if (!hasOccupiedRoom(customer)) {
			System.out.println("예약된 정보가 없습니다.");
			return;
		}

		Room prevRoom = selectOccupiedRoom(customer);
		int prevRoomType = prevRoom.getRoomType();
		if (!hasUpgradeRoom(prevRoomType)) {
			System.out.println("업그레이드 가능한 방이 없습니다.");
			return;
		}
		Room nextRoom = selectUpgradedRoom(prevRoomType);

		System.out.println("업그레이드 하시겠습니까? [Y/N]");
		char input = inputYN();
		switch (input) {
			case 'Y' :
				nextRoom.setCustomer(customer);
				nextRoom.setRoomState(Room.ROOM_STATE_OCCUPIED);
				prevRoom.setCustomer(null);
				prevRoom.setRoomState(Room.ROOM_STATE_EMPTY);
				System.out.println("업그레이드 되었습니다.");
				break;
			case 'N' :
				System.out.println("변경을 취소하셨습니다.");
				break;
		}
	}

	private boolean hasUpgradeRoom(int roomType) {
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room room = rooms[i][j];
				if (room.getRoomType() > roomType) {
					if (room.getRoomState() == Room.ROOM_STATE_EMPTY)
						return true;
				}
			}
		}
		return false;
	}

	private Room selectUpgradedRoom(int roomType) {
		int upgradedRoomType = selectUpgradeType(roomType);
		Room[] upgradedRooms = getChangedRooms(upgradedRoomType);
		System.out.println("업그레이드할 객실을 선택하시오.");
		for (int i = 0; i < upgradedRooms.length; i++) {
			System.out.println(
					"[" + (i + 1) + "] " + upgradedRooms[i].getRoomNum() + "호");
		}
		int input = inputIntInRange(1, upgradedRooms.length);
		return upgradedRooms[input - 1];
	}
	private int[] getUpgradeTypes(int roomType) {
		List<Integer> typeList = new ArrayList<>();
		for (int i = roomType + 1; i <= Room.ROOM_TYPE_SUITE; i++) {
			if (hasChangedRoom(i)) {
				typeList.add(i);
			}
		}
		return typeList.stream().mapToInt(i -> i).toArray();
	}

	private int selectUpgradeType(int roomType) {
		int[] roomTypes = getUpgradeTypes(roomType);
		for (int i = 0; i < roomTypes.length; i++) {
			System.out.println(
					"[" + (i + 1) + "] " + Room.getGradeOfRoomInfo(roomTypes[i])
							+ " " + Room.getBedInfo(roomTypes[i]));
		}
		int input = inputIntInRange(1, roomTypes.length);
		return roomTypes[input - 1];
	}

	private boolean hasOccupiedRoom(Customer customer) {
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room room = rooms[i][j];
				if (room.getRoomState() == Room.ROOM_STATE_OCCUPIED) {
					if (room.getCustomer().equals(customer))
						return true;
				}
			}
		}
		return false;
	}

	private Room selectOccupiedRoom(Customer customer) {
		Room[] occupiedRooms = getOccupiedRoom(customer);
		System.out.println("투숙 중인 객실을 선택하시오.");
		for (int i = 0; i < occupiedRooms.length; i++) {
			System.out.println(
					"[" + (i + 1) + "] " + occupiedRooms[i].getRoomNum() + "호");
		}
		int input = inputIntInRange(1, occupiedRooms.length);
		return occupiedRooms[input - 1];
	}

	private Room[] getOccupiedRoom(Customer customer) {
		List<Room> roomList = new ArrayList<>();
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room room = rooms[i][j];
				if (room.getRoomState() == Room.ROOM_STATE_OCCUPIED) {
					if (room.getCustomer().equals(customer))
						roomList.add(room);
				}
			}
		}
		return roomList.toArray(new Room[0]);
	}

	private void manageReservation() { // 예약 관리
		printManageReservationScreen();
		int s = inputIntInRange(0, 2);
		switch (s) {
			case 1 :
				addReservation();
				break;
			case 2 :
				changeReservation();
				break;
			case 3 :
				cancelReservation();
				break;
			case 0 :
				break;
		}
	}

	private void printManageReservationScreen() {
		System.out.println("메뉴 번호를 입력하시오.");
		System.out.println("[1] 예약하기\n[2] 예약변경\n[3] 예약취소\n[0] 뒤로가기");
	}

	private void addReservation() {
		Room room;
		while (true) {
			room = selectRoom();
			if (room == null) { // 취소한 상황
				System.out.println("변경을 취소하셨습니다.");
				return;
			}
			if (room.getRoomState() == Room.ROOM_STATE_EMPTY) {
				System.out.println("예약이 가능한 방입니다.");
				break;
			} else {
				System.out.println("예약이 불가능합니다.");
			}
		}

		System.out.println("-예약자 정보 입력");
		Customer reservationCustomer = inputCustomer();
		System.out.println("사용자 본인입니까? [Y/N]");
		Customer customer = null;
		char input = inputYN();
		switch (input) {
			case 'Y' :
				customer = reservationCustomer;
				break;
			case 'N' :
				System.out.println("-사용자 정보 입력");
				customer = inputCustomer();
				break;
		}
		System.out.println("예약을 하시겠습니까? [Y/N]");
		input = inputYN();
		switch (input) {
			case 'Y' :
				room.setReservationCustomer(reservationCustomer);
				room.setCustomer(customer);
				room.setRoomState(Room.ROOM_STATE_RESERVED);
				System.out.println("예약되었습니다.");
				break;
			case 'N' :
				System.out.println("변경을 취소하셨습니다.");
				break;
		}
	}

	private void changeReservation() {
		Customer reservationCustomer = inputCustomer();
		if (!hasReservedRoomFromReservation(reservationCustomer)) {
			System.out.println("예약된 정보가 없습니다.");
			return;
		}

		System.out.println("-변경할 객실 선택");
		Room reservedRoom = selectReservedRoomFromReservation(
				reservationCustomer);

		System.out.println("[1] 객실 변경 [2] 사용자 변경 [0] 취소");
		int input = inputIntInRange(0, 2);
		switch (input) {
			case 0 :
				return;
			case 1 :
				changeReservationRoom(reservedRoom, reservationCustomer);
				break;
			case 2 :
				changeReservationCustomer(reservedRoom);
				break;
		}
	}

	private void changeReservationRoom(Room reservedRoom,
			Customer reservationCustomer) {
		Room room;
		while (true) {
			room = selectRoom();
			if (room == null) { // 취소한 상황
				System.out.println("변경을 취소하셨습니다.");
				return;
			}
			if (room.getRoomState() == Room.ROOM_STATE_EMPTY) {
				System.out.println("예약이 가능한 방입니다.");
				break;
			} else {
				System.out.println("예약이 불가능합니다.");
			}
		}

		System.out.println("예약을 변경하시겠습니까? [Y/N]");
		char inputYN = inputYN();
		switch (inputYN) {
			case 'Y' :
				room.setReservationCustomer(reservationCustomer);
				room.setCustomer(reservedRoom.getCustomer());
				room.setRoomState(Room.ROOM_STATE_RESERVED);
				reservedRoom.setCustomer(null);
				reservedRoom.setReservationCustomer(null);
				reservedRoom.setRoomState(Room.ROOM_STATE_EMPTY);
				System.out.println("예약이 변경되었습니다.");
				break;
			case 'N' :
				System.out.println("변경을 취소하셨습니다.");
				break;
		}
	}

	private void changeReservationCustomer(Room reservedRoom) {
		System.out.println("-사용자 정보 입력");
		Customer customer = inputCustomer();
		System.out.println("사용자를 변경하시겠습니까? [Y/N]");
		char inputYN = inputYN();
		switch (inputYN) {
			case 'Y' :
				reservedRoom.setCustomer(customer);
				break;
			case 'N' :
				System.out.println("변경을 취소하셨습니다.");
				break;
		}
	}

	private void cancelReservation() { // 예약 취소
		Customer reservationCustomer = inputCustomer();
		if (!hasReservedRoomFromReservation(reservationCustomer)) {
			System.out.println("예약된 정보가 없습니다.");
			return;
		}

		Room reservedRoom = selectReservedRoomFromReservation(
				reservationCustomer);

		System.out.println("예약을 취소하시겠습니까? [Y/N]");
		char input = inputYN();
		switch (input) {
			case 'Y' :
				reservedRoom.setCustomer(null);
				reservedRoom.setReservationCustomer(null);
				reservedRoom.setRoomState(Room.ROOM_STATE_EMPTY);
				System.out.println("예약이 취소되었습니다.");
				break;
			case 'N' :
				System.out.println("변경을 취소하셨습니다.");
				break;
		}
	}

	private Room[] getReservedRoomFromReservation(
			Customer reservationCustomer) { // 예약자로 예약한 객실 목록 배열로 반환
		List<Room> roomList = new ArrayList<>();
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room room = rooms[i][j];
				if (room.getRoomState() == Room.ROOM_STATE_RESERVED) {
					if (room.getReservationCustomer()
							.equals(reservationCustomer))
						roomList.add(room);
				}
			}
		}
		return roomList.toArray(new Room[0]);
	}

	private Room selectReservedRoomFromReservation(
			Customer reservationCustomer) { // 예약자로 예약한 객실 중 선택해서 반환
		Room[] reservedRooms = getReservedRoomFromReservation(
				reservationCustomer);
		System.out.println("예약된 객실을 선택하시오.");
		for (int i = 0; i < reservedRooms.length; i++) {
			System.out.println(
					"[" + (i + 1) + "] " + reservedRooms[i].getRoomNum() + "호");
		}
		int input = inputIntInRange(1, reservedRooms.length);
		return reservedRooms[input - 1];
	}

	private Room[] getReservedRoomFromUser(Customer customer) { // 사용자로 예약한 객실
																// 목록 배열로 반환
		List<Room> roomList = new ArrayList<>();
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room room = rooms[i][j];
				if (room.getRoomState() == Room.ROOM_STATE_RESERVED) {
					if (room.getCustomer().equals(customer))
						roomList.add(room);
				}
			}
		}
		return roomList.toArray(new Room[0]);
	}

	private Room selectReservedRoomFromUser(Customer customer) { // 사용자로 예약한 객실
																	// 중 선택해서 반환
		Room[] reservedRooms = getReservedRoomFromUser(customer);
		System.out.println("예약된 객실을 선택하시오.");
		for (int i = 0; i < reservedRooms.length; i++) {
			System.out.println(
					"[" + (i + 1) + "] " + reservedRooms[i].getRoomNum() + "호");
		}
		int input = inputIntInRange(1, reservedRooms.length);
		return reservedRooms[input - 1];
	}

	private boolean hasReservedRoomFromReservation(
			Customer reservationCustomer) {
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room room = rooms[i][j];
				if (room.getRoomState() == Room.ROOM_STATE_RESERVED) {
					if (room.getReservationCustomer()
							.equals(reservationCustomer))
						return true;
				}
			}
		}
		return false;
	}

	private boolean hasReservedRoomFromUser(Customer customer) {
		for (int i = 0; i < rooms.length; i++) {
			for (int j = 0; j < rooms[i].length; j++) {
				Room room = rooms[i][j];
				if (room.getRoomState() == Room.ROOM_STATE_RESERVED) {
					if (room.getCustomer().equals(customer))
						return true;
				}
			}
		}
		return false;
	}

	// 고객 입력 받아서 고객 생성하여 반환
	private Customer inputCustomer() {
		String name;
		String birth;
		String phoneNum;
		System.out.println("고객 정보를 입력합니다.");
		while (true) {
			System.out.println("이름을 입력하시오.");
			name = scanner.nextLine();
			if (name.length() != 0)
				break;
			System.out.println("정보를 입력해주세요.");
		}
		while (true) {
			System.out.println("생년원일을 입력하시오");
			birth = scanner.nextLine();
			if (birth.length() != 0)
				break;
			System.out.println("정보를 입력해주세요.");
		}
		while (true) {
			System.out.println("전화번호를 입력하시오.");
			phoneNum = scanner.nextLine();
			if (phoneNum.length() != 0)
				break;
			System.out.println("정보를 입력해주세요.");
		}
		return new Customer(name, birth, phoneNum);
	}

	private int inputIntInRange(int start, int end) { // 범위 안에 있는 정수 입력 받기
		int intInput;
		while (true) {
			intInput = inputStringToInt();
			if (isInRange(intInput, start, end))
				return intInput;
			System.out.println("잘못된 입력 [다른 숫자]");
		}
	}

	private int inputIntInSelect(int... selects) {
		int intInput;
		do {
			intInput = inputStringToInt();
			if (!isInSelect(intInput, selects))
				System.out.println("잘못된 입력 [다른 숫자]");
		} while (!isInSelect(intInput, selects));
		return intInput;
	}

	private int inputIntInRangeAndSelect(int start, int end, int... selects) {
		int intInput;
		do {
			intInput = inputStringToInt();
			if (!isInSelect(intInput, selects)
					&& !isInRange(intInput, start, end))
				System.out.println("잘못된 입력 [다른 숫자]");
		} while (!isInSelect(intInput, selects)
				&& !isInRange(intInput, start, end));
		return intInput;
	}

	private boolean isInSelect(int intInput, int[] selects) {
		for (int s : selects) {
			if (s == intInput)
				return true;
		}
		return false;
	}

	private int inputStringToInt() { // 문자열을 입력받고 정수로 바꾸기
		String strInput = scanner.nextLine();
		while (!isStringInt(strInput)) {
			System.out.println("잘못된 입력 [정수 아님|공백 X]");
			strInput = scanner.nextLine();
		}
		return Integer.parseInt(strInput);
	}

	private boolean isInRange(int n, int start, int end) { // 범위 맞는지 확인
		return start <= n && n <= end;
	}

	private boolean isStringInt(String str) { // 문자열이 정수인지 확인
		if (str.length() == 0)
			return false;
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (i == 0) {
				if ((c < '0' || '9' < c) && c != '-')
					return false;
			} else {
				if (c < '0' || '9' < c)
					return false;
			}
		}
		return true;
	}

	private char inputYN() { // Y/N을 받는 함수
		while (true) {
			String strInput = scanner.nextLine();
			switch (strInput) {
				case "y" :
				case "Y" :
					return 'Y';
				case "n" :
				case "N" :
					return 'N';
				default :
					System.out.println("잘못된 입력 [다른 문자|공백 X]");
			}
		}
	}

}
