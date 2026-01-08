package attendance.domain;

import attendance.utils.DateTimeUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class Attendance {

    private final String nickname;
    private final LocalDate date;
    private final LocalTime time;

    public Attendance(String nickname, LocalDate date, LocalTime time) {
        this.nickname = nickname;
        this.date = date;
        this.time = time;
    }

    public static Attendance fromCSV(String nickname, String datetimeStr) {
        LocalDateTime dateTime = DateTimeUtil.parseDateTime(datetimeStr);
        return new Attendance(nickname, dateTime.toLocalDate(), dateTime.toLocalTime());
    }

    public AttendanceStatus getStatus() {
        String dayOfWeek = DateTimeUtil.getDayOfWeek(date);
        return AttendanceStatus.from(time, dayOfWeek);
    }

    public String getNickname() {
        return nickname;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getTime() {
        return time;
    }
}
