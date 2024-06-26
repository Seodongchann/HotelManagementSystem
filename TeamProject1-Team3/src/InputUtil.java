import java.util.Scanner;

public class InputUtil {
    public static final int INPUT_TYPE_NULL = 0;
    public static final int INPUT_TYPE_STRING = 1;
    public static final int INPUT_TYPE_INT = 2;
    public static final int INPUT_TYPE_DOUBLE = 3;
    private static String buffer;
    public static int inputType = INPUT_TYPE_NULL;

    public static void inputBuffer(Scanner scanner) {
        buffer = scanner.nextLine();
    }

    public static int inputInt(Scanner scanner) {
        buffer = scanner.nextLine();
        return 1;
    }

    public static int getBufferToInt(Scanner scanner) {
        return 1;
    }
    
    
}
