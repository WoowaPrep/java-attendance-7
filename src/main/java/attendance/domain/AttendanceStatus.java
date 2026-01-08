package attendance.domain;

import java.time.LocalTime;

public enum AttendanceStatus {

    ATTENDANCE("출석"),
    LATENESS("지각"),
    ABSENT("결석"),
    ;

    private final String name;

    AttendanceStatus(String name) {
        this.name = name;
    }

    public static AttendanceStatus from(LocalTime input, String dayOfWeek) {
        int h = input.getHour();
        int m = input.getMinute();
        if (dayOfWeek.equals("월요일")) {
            if (h < 13 || (h == 13 && m <= 5)) return ATTENDANCE;
            if (h < 14 && m <= 30) return LATENESS;
            return ABSENT;
        }

        if (h < 10 || (h == 10 && m <= 5)) return ATTENDANCE;
        if (h < 11 && m <= 30) return LATENESS;
        return ABSENT;
    }

    public String getName() {
        return name;
    }
}
