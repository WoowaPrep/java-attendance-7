package attendance.view;

import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import attendance.utils.DateTimeUtil;
import camp.nextstep.edu.missionutils.Console;
import java.time.DayOfWeek;
import java.time.LocalDate;

public class InputView {

    private final static String NEW_LINE = System.lineSeparator();

    private final static String MENU_INPUT_MESSAGE =
            "오늘은 %s월 %s일 금요일입니다. 기능을 선택해 주세요." + NEW_LINE +
                    "1. 출석 확인" + NEW_LINE +
                    "2. 출석 수정" + NEW_LINE +
                    "3. 크루별 출석 기록 확인" + NEW_LINE +
                    "4. 제적 위험자 확인" + NEW_LINE +
                    "Q. 종료" + NEW_LINE;

    public String printMenu() {
        String month = DateTimeUtil.todayMonth();
        String day = DateTimeUtil.todayDayOfMonth();
        System.out.printf(MENU_INPUT_MESSAGE, month, day);
        return readLine();
    }

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
