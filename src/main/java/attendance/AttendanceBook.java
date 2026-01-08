package attendance;

import attendance.domain.Attendance;
import attendance.domain.AttendanceRepository;
import attendance.domain.Menu;
import attendance.domain.RestDay;
import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import attendance.utils.DateTimeUtil;
import attendance.view.InputParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Supplier;

public class AttendanceBook {

    private InputView inputView;
    private OutputView outputView;
    private InputParser inputParser;

    public AttendanceBook() {
        this(new InputView(), new OutputView(), new InputParser());
    }

    public AttendanceBook(InputView inputView, OutputView outputView, InputParser inputParser) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.inputParser = inputParser;
    }

    public void manage() {
        while (true) {
            Menu menu = readMenu();
            if (menu == Menu.QUIT) break;

            executeMenu(menu);
        }
    }

    private Menu readMenu() {
        String input = inputView.printMenu();
        return Menu.from(input);
    }

    private void executeMenu(Menu menu) {
        if (menu == Menu.FIRST) handleAttendanceCheck();
        if (menu == Menu.SECOND) handleAttendanceModify();
        if (menu == Menu.THIRD) handleAttendanceRecord();
        if (menu == Menu.FOURTH) handleRiskCheck();
    }

    private void handleAttendanceCheck() {
        LocalDate today = DateTimeUtil.today();
        validateSchoolDay(today);

        String nickname = readNickname();
        validateNickname(nickname);

        LocalTime time = readAttendanceTime();
        validateCampusTime(time);

        validateHasAttendance(AttendanceRepository.hasAttendance(nickname, today),
        "[ERROR] 이미 출석을 확인하였습니다. 필요한 경우 수정 기능을 이용해 주세요.");

        Attendance attendance = new Attendance(nickname, today, time);
        AttendanceRepository.save(attendance);

        outputView.printAttendanceResult(attendance);
    }

    private void handleAttendanceModify() {
        String nickname = readModifyNickname();
        validateNickname(nickname);
        LocalDate date = readModifyDay();

        validateSchoolDay(date);
        validateNotFuture(date);

        LocalTime newTime = readModifyTime();
        validateCampusTime(newTime);

        Attendance oldAttendance = AttendanceRepository.getAttendance(nickname, date);
        if (oldAttendance == null) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 없습니다.");
        }

        AttendanceRepository.update(nickname, date, newTime);
        Attendance newAttendance = AttendanceRepository.getAttendance(nickname, date);

        outputView.printModifyResult(oldAttendance, newAttendance);
    }

    private void handleAttendanceRecord() {
        String nickname = readNickname();
        validateNickname(nickname);
        List<LocalDate> schoolDays = DateTimeUtil.getDecemberWeekdaysUntilYesterday();
        List<Attendance> attendances = AttendanceRepository.findAllByNickname(nickname);

        outputView.printAttendanceRecord(nickname, schoolDays, attendances);
    }

    private void handleRiskCheck() {
        Set<String> allNicknames = AttendanceRepository.getAllNicknames();
        List<LocalDate> schoolDays = DateTimeUtil.getDecemberWeekdaysUntilYesterday();

        outputView.printRiskList(allNicknames, schoolDays);
    }

    private String readNickname() {
        return inputView.printNickName();
    }

    private String readModifyNickname() {
        return inputView.printModifyNickname();
    }

    private LocalDate readModifyDay() {
        String input = inputView.printModifyDay();
        validateModifyDay(input);
        return LocalDate.of(2024, 12, Integer.parseInt(input));
    }

    private void validateModifyDay(String input) {
        if (Arrays.stream(RestDay.values()).anyMatch(restDay -> {
            int day = restDay.getDate().getDayOfMonth();
            return day == Integer.parseInt(input);
        })) {
            LocalDate localDate = LocalDate.of(2024, 12, Integer.parseInt(input));
            String date = DateTimeUtil.formatDateWithDay(localDate);
            throw new IllegalArgumentException(String.format("[ERROR] %s은 등교일이 아닙니다.", date));
        }
    }

    private LocalTime readAttendanceTime() {
        String input = inputView.printAttendanceTime();
        return inputParser.parseAttendanceTime(input);
    }

    private LocalTime readModifyTime() {
        String input = inputView.printModifyTime();

        return inputParser.parseAttendanceTime(input);
    }

    private void validateNickname(String input) {
        if (!AttendanceRepository.getAllNicknames().contains(input)) {
            throw AttendanceException.from(ErrorMessage.INVALID_NICK_NAME);
        }
    }

    private void validateSchoolDay(LocalDate date) {
        if (DateTimeUtil.isWeekend(date) || isHoliday(date)) {
            String formatted = DateTimeUtil.formatDateWithDay(date);
            throw new IllegalArgumentException(
                    String.format("[ERROR] %s은 등교일이 아닙니다.", formatted)
            );
        }
    }

    private void validateCampusTime(LocalTime time) {
        LocalTime open = LocalTime.of(8, 0);
        LocalTime close = LocalTime.of(23, 0);

        if (time.isBefore(open) || time.isAfter(close)) {
            throw new IllegalArgumentException("[ERROR] 캠퍼스 운영 시간에만 출석이 가능합니다.");
        }
    }

    private boolean isHoliday(LocalDate date) {
        return RestDay.isRestDay(date);
    }

    private void validateNotFuture(LocalDate date) {
        validateHasAttendance(DateTimeUtil.isAfter(date, DateTimeUtil.today()),
                "[ERROR] 아직 수정할 수 없습니다.");
    }

    private static void validateHasAttendance(boolean nickname, String message) {
        if (nickname) {
            throw new IllegalArgumentException(message);
        }
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
