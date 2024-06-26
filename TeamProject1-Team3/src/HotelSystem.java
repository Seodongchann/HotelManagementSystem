import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class HotelSystem {
    private final int NUMBERS_OF_FLOORS = 4;
    private final int NUMBERS_OF_ROOMS = 20;
    private Scanner scanner = new Scanner(System.in);
    private Room rooms[][] = new Room[NUMBERS_OF_FLOORS][NUMBERS_OF_ROOMS];
    private int adminNum;
    private boolean isRunning;
    private boolean isManagerRunning;
    private boolean isCleanerRunning;

    public HotelSystem(int adminNum) {
        super();
        this.adminNum = adminNum;
        isRunning = true;
        for (int i = 0; i < NUMBERS_OF_FLOORS; i++) {
            for (int j = 0; j < NUMBERS_OF_ROOMS; j++) {
                int roomNum = (i + 2) * 100 + j + 1;
                rooms[i][j] = new Room(roomNum);
            }
        }
    }

    public void test() { // 테스트
        Customer customer = new Customer("홍길동", "90/10/10", "010-1010-1010");
        rooms[0][2].setRoomState(Room.ROOM_STATE_RESERVED);
        rooms[0][2].setCustomer(customer);
        rooms[3][15].setRoomState(Room.ROOM_STATE_RESERVED);
        rooms[3][15].setCustomer(customer);

        rooms[1][7].setRoomState(Room.ROOM_STATE_RESERVED);
        rooms[1][7].setCustomer(customer);

        rooms[2][18].setRoomState(Room.ROOM_STATE_RESERVED);
        rooms[2][18].setCustomer(customer);

        rooms[2][7].setRoomState(Room.ROOM_STATE_OCCUPIED);
        rooms[2][7].setCustomer(customer);
        checkOut();
        selectRoom();
    }

    public void run() {
        while (isRunning) {
            printMainScreen();
            int input = inputIntInRange(0, 2);
            switch (input) {
            case 1:
                if (checkAdmin()) {
                    isManagerRunning = true;
                    managerRun();
                }
                break;
            case 2:
                isCleanerRunning = true;
                cleanerRun();
                break;
            case 0:
                isRunning = false;
                System.out.println("프로그램을 종료합니다.");
                scanner.close();
                break;
            }
        }
    }

    private void managerRun() {

        while (isManagerRunning) {
            printManageScreen();
            int input = inputIntInRange(0, 3);
            switch (input) {
            case 1:
                checkRoomState();
                break;
            case 2:
                chageRoomState();
                break;
            case 3:
                manageReservation();
                break;
            case 0:
                isManagerRunning = false;
                break;
            }
        }
    }

    private void cleanerRun() {

        while (isCleanerRunning) {
            printCleanerScreen();
            int input = inputIntInRange(0, 1);
            switch (input) {
            case 1:
                checkFloorState();
                break;
            case 0:
                isCleanerRunning = false;
                break;
            }
        }
    }

    private void printMainScreen() { // 메인 화면 출력
        System.out.println("원하는 사용자 번호를 입력해주세요.");
        System.out.println("[1] 관리자\n[2] 청소부\n[0] 프로그램 종료");
    }

    private void printManageScreen() { // 관리자 화면 출력
        System.out.println("원하는 메뉴 번호를 입력해주세요.");
        System.out.println("[1] 객실 상태 확인\n[2] 객실 상태 변경\n[3] 예약 관리\n[0] 로그아웃");
    }

    private void printCleanerScreen() { // 청소부 화면 출력
        System.out.println("원하는 메뉴 번호를 입력해주세요.");
        System.out.println("[1] 객실 상태 확인\n[0] 로그아웃");
    }

    private boolean checkAdmin() { // 비밀번호 확인
        System.out.println("비밀번호를 입력하시오.");
        if (adminNum == inputStringToInt()) {
            return true;
        } else {
            System.out.println("비밀번호가 틀렸습니다.");
            return false;
        }
    }

    private void checkFloorState() { // 청소부가 층의 상태 확인
        int floor = selectFloor();
        printFloorState(floor);
    }

    private void printFloorState(int floor) { // 층의 방 상황 확인
        for (int i = 0; i < rooms[floor - 2].length; i++) {
            System.out
                    .print(floor + "" + String.format("%02d", i + 1) + rooms[floor - 2][i].getRoomStateString() + "\t");
            if ((i + 1) % 5 == 0)
                System.out.println();
        }
    }

    private int selectFloor() { // 층 선택해서 인트로 반환 (배열의 층 반환)
        System.out.println("층을 선택하시오. [2~" + (1 + NUMBERS_OF_FLOORS) + "]");
        return inputIntInRange(2, 1 + NUMBERS_OF_FLOORS);
    }

    private Room selectRoom() { // 객실을 선택해서 Room 반환
        int input = 0;
        int floor = 0;
        do {
            floor = selectFloor();
            System.out.println("객실을 선택하시오. [1~20] 층 선택 [0] 선택취소 [-1]");
            printFloorState(floor);
            input = inputIntInRange(-1, 20);
            if (input == -1)
                return null;
        } while (input == 0);

        return rooms[floor - 2][input - 1];
    }

    private void checkRoomState() { // 관리자가 객실 상태 확인
        Room room = selectRoom();
        if (room == null)
            return;
        room.printState();
    }

    private void chageRoomState() { // 객실 상태 변경
        printChangeRoomStateScreen();
        int changeRoomStateNum = inputIntInRange(0, 3);
        switch (changeRoomStateNum) {
        case 0:
            break;
        case 1:
            walkIn();
            break;
        case 2:
            checkIn();
            break;
        case 3:
            checkOut();
            break;
        }
    }

    private void printChangeRoomStateScreen() { // 객실 상태 변경 화면 출력
        System.out.println("원하는 메뉴 번호를 입력해주세요.");
        System.out.println("[1] 워크 인\n[2] 체크 인\n[3] 체크 아웃\n[0] 뒤로가기");
    }

    private void walkIn() { // 워크 인
        Room room = null;
        do {
            room = selectRoom();
            if (room == null) {
                return;
            }
            if (room.getRoomState() != Room.ROOM_STATE_EMPTY)
                System.out.println("투숙할 수 없는 객실입니다.");
        } while (room.getRoomState() != Room.ROOM_STATE_EMPTY);

        Customer customer = inputCustomer();

        System.out.println("워크 인 하시겠습니까? [Y/N]");
        char input = inputYN();
        if (input == 'Y') {
            room.setCustomer(customer);
            room.setRoomState(Room.ROOM_STATE_OCCUPIED);
            System.out.println("워크 인 되었습니다.");
        }
    }

    private void checkIn() { // 체크 인
        Customer customer = inputCustomer();

        if (!hasReservedRoom(customer)) {
            System.out.println("예약 정보 없음");
            return;
        }

        Room room = selectReservedRoom(customer);

        System.out.println("예약자 본인입니까? [Y/N]");
        char checkInYesOrNo = inputYN();
        switch (checkInYesOrNo) {
        case 'Y':
            break;
        case 'N':
            customer = inputCustomer();
            break;
        }

        System.out.println("체크인 하시겠습니까? [Y/N]");
        char checkInYesOrNo1 = inputYN();

        switch (checkInYesOrNo1) {
        case 'Y':
            room.setCustomer(customer);
            room.setRoomState(Room.ROOM_STATE_OCCUPIED);
            break;
        case 'N':
            break;
        }
    }

    private void checkOut() { // 체크 아웃
        Room room = null;

        do {
            room = selectRoom();
            if (room == null) {
                return;
            }
            if (room.getRoomState() != Room.ROOM_STATE_OCCUPIED) {
                System.out.println("투숙중인 방이 아닙니다.");
            }
        } while (room.getRoomState() != Room.ROOM_STATE_OCCUPIED);
        System.out.println("체크 아웃 하시겠습니까? [Y/N]");
        char inputYorN = inputYN();
        if (inputYorN == 'Y') {
            room.setCustomer(null);
            room.setRoomState(Room.ROOM_STATE_EMPTY);
            System.out.println("체크 아웃 되었습니다.");
        }
    }

    private void manageReservation() { // 예약 관리
        System.out.println("1.예약하기. 2.예약취소 0.뒤로가기");
        int s = inputIntInRange(0, 2);
        switch (s) {
        case 1:
            addReservation();
            break;
        case 2:
            cancelReservation();
            break;
        case 0:
            break;
        }
    }

    private void addReservation() {
        Room room = null;
        do {
            room = selectRoom();
            if (room == null)
                return;
            if (room.getRoomState() == Room.ROOM_STATE_EMPTY) {
                System.out.println("예약이 가능한 방 입니다.");
            } else {
                System.out.println("예약이 불가능합니다.");
            }
        } while (room.getRoomState() != Room.ROOM_STATE_EMPTY);

        Customer customer = inputCustomer();
        System.out.println("예약을 하시겠습니까? [Y/N]");
        char input = inputYN();
        if (input == 'Y') {
            room.setCustomer(customer);
            room.setRoomState(Room.ROOM_STATE_RESERVED);
            System.out.println("예약되었습니다.");
        }
    }

    private void cancelReservation() {
        Customer customer = inputCustomer();
        if (!hasReservedRoom(customer)) {
            System.out.println("예약된 정보가 없습니다.");
            return;
        }

        Room reservedRoom = selectReservedRoom(customer);

        System.out.println("예약을 취소하시겠습니까? [Y/N]");
        char input = inputYN();
        if (input == 'Y') {
            reservedRoom.setCustomer(null);
            reservedRoom.setRoomState(Room.ROOM_STATE_EMPTY);
        }
    }

    private Room[] getReservedRoom(Customer customer) { // 예약한 객실 목록 배열로 반환
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

    private Room selectReservedRoom(Customer customer) { // 예약한 객실 중 선택해서 반환
        Room[] reservedRooms = getReservedRoom(customer);
        System.out.println("예약된 객실을 선택하세요.");
        for (int i = 0; i < reservedRooms.length; i++) {
            System.out.println("[" + (i + 1) + "] " + reservedRooms[i].getRoomNum() + "호");
        }
        int input = inputIntInRange(1, reservedRooms.length);
        return reservedRooms[input - 1];
    }

    private boolean hasReservedRoom(Customer customer) {
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

    private Customer inputCustomer() { // 고객 입력 받아서 고객 생성하여 반환
        System.out.println("고객 정보를 입력합니다.");
        System.out.println("이름을 입력해주세요.");
        String name = scanner.nextLine();

        System.out.println("전화번호를 입력해주세요.");
        String phoneNum = scanner.nextLine();

        System.out.println("생년원일을 입력해주세요");
        String birth = scanner.nextLine();

        return new Customer(name, birth, phoneNum);
    }

    private int inputIntInRange(int start, int end) { // 범위 안에 있는 정수 입력 받기
        int intInput;
        do {
            intInput = inputStringToInt();
            if (!isInRange(intInput, start, end))
                System.out.println("잘못된 입력 [다른 숫자]");
        } while (!isInRange(intInput, start, end));
        return intInput;
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
            case "y":
            case "Y":
                return 'Y';
            case "n":
            case "N":
                return 'N';
            default:
                System.out.println("잘못된 입력 [다른 문자|공백 X]");
            }
        }
    }
}
