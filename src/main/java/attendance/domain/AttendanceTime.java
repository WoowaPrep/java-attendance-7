package attendance.domain;

import java.time.LocalTime;

public class AttendanceTime {

    private LocalTime localTime;

    public AttendanceTime(LocalTime localTime) {
        this.localTime = localTime;
    }

    public static AttendanceTime from(LocalTime localTime) {
        return new AttendanceTime(localTime);
    }

    public LocalTime getLocalTime() {
        return localTime;
    }
}
