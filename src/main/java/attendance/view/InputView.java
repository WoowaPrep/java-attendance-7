package attendance.view;

import attendance.domain.AttendanceStatus;
import attendance.domain.AttendanceTime;
import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import attendance.utils.DateTimeUtil;
import camp.nextstep.edu.missionutils.Console;
import java.time.LocalDate;
import java.time.LocalTime;

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
    private final static String ATTENDANCE_TIME_CHECK_MESSAGE = "%s월 %s일 %s %s:%s (%s)" + NEW_LINE;

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

    public String printAttendanceTime() {
        System.out.println(ATTENDANCE_TIME_INPUT_MESSAGE);
        return readLine();
    }

    public void printAttendance(AttendanceTime time) {
        LocalTime localTime = time.getLocalTime();

        String month = DateTimeUtil.todayMonth();
        String day = DateTimeUtil.todayDayOfMonth();

        LocalDate localDate = DateTimeUtil.today();
        String dayOfWeek = DateTimeUtil.getDayOfWeek(localDate);

        String HH = DateTimeUtil.getHour(localTime);
        String mm = DateTimeUtil.geMinute(localTime);

        AttendanceStatus attendanceStatus = AttendanceStatus.from(localTime, dayOfWeek);
        System.out.printf(ATTENDANCE_TIME_CHECK_MESSAGE, month, day, dayOfWeek, HH, mm, attendanceStatus.getName());
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
