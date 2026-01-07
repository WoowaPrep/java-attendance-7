package attendance.view;

import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private final static String NEW_LINE = System.lineSeparator();


    private String readLine() {
        String input = Console.readLine();
        if (input == null || input.trim().isEmpty()) {
            throw AttendanceException.from(ErrorMessage.EMPTY_INPUT);
        }
        return input;
    }

    public void printNewLine() {
        System.out.printf(NEW_LINE);
    }
}
