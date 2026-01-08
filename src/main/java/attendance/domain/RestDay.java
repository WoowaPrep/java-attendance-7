package attendance.domain;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Set;

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

    public static boolean isRestDay(LocalDate targetDate) {
        return Arrays.stream(values())
                .anyMatch(restDay -> restDay.date.isEqual(targetDate));
    }

    private static final Set<LocalDate> REST_DATES = Set.of(
            LocalDate.of(2024, 12, 1),
            LocalDate.of(2024, 12, 7),
            LocalDate.of(2024, 12, 8),
            LocalDate.of(2024, 12, 14),
            LocalDate.of(2024, 12, 15),
            LocalDate.of(2024, 12, 21),
            LocalDate.of(2024, 12, 22),
            LocalDate.of(2024, 12, 25),
            LocalDate.of(2024, 12, 28),
            LocalDate.of(2024, 12, 29)
    );

    public static boolean isRestDayFast(LocalDate targetDate) {
        return REST_DATES.contains(targetDate);
    }

    public LocalDate getDate() {
        return date;
    }
}
