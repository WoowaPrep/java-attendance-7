package attendance.domain;

import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import java.util.Arrays;

public enum Menu {

    FIRST("1"),
    SECOND("2"),
    THIRD("3"),
    FOURTH("4"),
    QUIT("Q"),
    ;

    private final String name;

    Menu(String name) {
        this.name = name;
    }

    public static Menu from(String input) {
        return Arrays.stream(Menu.values())
                .filter(menu -> menu.getName().equals(input))
                .findFirst()
                .orElseThrow(() -> AttendanceException.from(ErrorMessage.INVALID_INPUT));
    }

    public String getName() {
        return name;
    }
}
