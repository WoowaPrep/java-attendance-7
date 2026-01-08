package attendance.view;

import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import attendance.utils.DateTimeUtil;
import camp.nextstep.edu.missionutils.Console;

public class InputView {

    private final static String NEW_LINE = System.lineSeparator();

    private final static String MENU_INPUT_MESSAGE =
            "오늘은 %s월 %s일 금요일입니다. 기능을 선택해 주세요." + NEW_LINE +
                    "1. 출석 확인" + NEW_LINE +
                    "2. 출석 수정" + NEW_LINE +
                    "3. 크루별 출석 기록 확인" + NEW_LINE +
                    "4. 제적 위험자 확인" + NEW_LINE +
                    "Q. 종료" + NEW_LINE;

    private final static String NICK_NAME_INPUT_MESSAGE = "닉네임을 입력해 주세요.";
    private final static String ATTENDANCE_TIME_INPUT_MESSAGE = "등교 시간을 입력해 주세요.";

    private final static String MODIFY_NICK_NAME_INPUT_MESSAGE = "출석을 수정하려는 크루의 닉네임을 입력해 주세요.";
    private final static String ATTENDANCE_MODIFY_TIME_INPUT_MESSAGE = "수정하려는 날짜(일)를 입력해 주세요.";
    private final static String MODIFY_TIME_INPUT_MESSAGE = "언제로 변경하겠습니까?";

    public String printMenu() {
        String month = DateTimeUtil.todayMonth();
        String day = DateTimeUtil.todayDayOfMonth();
        printNewLine();
        System.out.printf(MENU_INPUT_MESSAGE, month, day);
        return readLine();
    }

    public String printNickName() {
        printNewLine();
        System.out.println(NICK_NAME_INPUT_MESSAGE);
        return readLine();
    }

    public String printModifyNickname() {
        printNewLine();
        System.out.println(MODIFY_NICK_NAME_INPUT_MESSAGE);
        return readLine();
    }

    public String printAttendanceTime() {
        System.out.println(ATTENDANCE_TIME_INPUT_MESSAGE);
        return readLine();
    }

    public String printModifyTime() {
        System.out.println(MODIFY_TIME_INPUT_MESSAGE);
        return readLine();
    }

    public String printModifyDay() {
        System.out.println(ATTENDANCE_MODIFY_TIME_INPUT_MESSAGE);
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
