package attendance.domain;

import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import java.time.LocalDate;
import java.util.List;

public class Score {

    private int absentCount;
    private int lateCount;
    private String name;

    public Score(int absentCount, int lateCount, String name) {
        this.absentCount = absentCount;
        this.lateCount = lateCount;
        this.name = name;
    }

    public static Score from(String name, List<LocalDate> days) {
        int aCount = 0;
        int lCount = 0;
        for (LocalDate day : days) {
            Attendance attendance = AttendanceRepository.getAttendance(name, day);
            if (attendance == null) {
                aCount++;
                continue;
            }
            AttendanceStatus status = attendance.getStatus();
            if (status == AttendanceStatus.ABSENT) aCount++;
            if (status == AttendanceStatus.LATENESS) lCount++;
        }
        return new Score(aCount, lCount, name);
    }

    public int getAbsentCount() {
        return absentCount;
    }

    public int getLateCount() {
        return lateCount;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return absentCount + lateCount/3;
    }

    public String getStatus() {
        if (getScore() > 5) return "제적";
        if (getScore() >= 3) return "면담";
        if (getScore() >= 2) return "경고";

        throw AttendanceException.from(ErrorMessage.INVALID_RISK_PERSON);
    }
}
