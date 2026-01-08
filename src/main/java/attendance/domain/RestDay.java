package attendance.domain;

import attendance.utils.DateTimeUtil;
import java.time.LocalDate;
import java.util.Arrays;

public enum RestDay {

    Rest_1(LocalDate.of(2024, 12, 1)),
    Rest_2(LocalDate.of(2024, 12, 7)),
    Rest_3(LocalDate.of(2024, 12, 8)),
    Rest_4(LocalDate.of(2024, 12, 14)),
    Rest_5(LocalDate.of(2024, 12, 15)),
    Rest_6(LocalDate.of(2024, 12, 21)),
    Rest_7(LocalDate.of(2024, 12, 22)),
    Rest_8(LocalDate.of(2024, 12, 25)),
    Rest_9(LocalDate.of(2024, 12, 28)),
    Rest_10(LocalDate.of(2024, 12, 29)),
    ;

    private final LocalDate date;

    RestDay(LocalDate date) {
        this.date = date;
    }

    public static RestDay from(LocalDate input) {
        String month = DateTimeUtil.getMonth(input);
        String day = DateTimeUtil.getDayOfMonth(input);
        String dayOfWeek = DateTimeUtil.getDayOfWeek(input);

        return Arrays.stream(values())
                .filter(restDay -> !(restDay.getMonthValue().equals(month) && restDay.getDayOfMonth().equals(day)))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(
                        String.format("[ERROR] %s월 %s일 %s은 등교일이 아닙니다.", month, day, dayOfWeek))
                );
    }

    public String getMonthValue() {
        return DateTimeUtil.getMonth(date);
    }

    public String getDayOfMonth() {
        return DateTimeUtil.getDayOfMonth(date);
    }
}
