package attendance;

import camp.nextstep.edu.missionutils.Console;

public class Application {
    public static void main(String[] args) {
        try {
            new AttendanceBook().manage();
        } finally {
            Console.close();
        }
    }
}
