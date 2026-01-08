package attendance.view;


import attendance.domain.Attendance;
import attendance.domain.AttendanceRepository;
import attendance.domain.AttendanceStatus;
import attendance.domain.Score;
import attendance.utils.DateTimeUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OutputView {

    private static final String NEW_LINE = System.lineSeparator();

    private final static String ATTENDANCE_TIME_CHECK_MESSAGE = "%s (%s)" + NEW_LINE;
    private final static String ATTENDANCE_MODIFY_MESSAGE = "%s (%s) -> %s (%s) 수정 완료!" + NEW_LINE;
    private final static String ATTENDANCE_HISTORY_MESSAGE = "이번 달 %s의 출석 기록입니다." + NEW_LINE;
    private final static String ATTENDANCE_SINGLE_HISTORY_MESSAGE = "%s (%s)" + NEW_LINE;
    private final static String ATTENDANCE_SINGLE_HISTORY_ABSENT_MESSAGE = "%s --:-- (결석)" + NEW_LINE;

    private final static String DANGEROUS_PERSON_CHECK_HEADER_MESSAGE = "제적 위험자 조회 결과";
    private final static String DANGEROUS_PERSON_CHECK_MESSAGE = "- %s: 결석 %d회, 지각 %d회 (%s)" + NEW_LINE;


    public void printAttendanceResult(Attendance attendance) {
        LocalDateTime ldt = LocalDateTime.of(attendance.getDate(), attendance.getTime());
        System.out.printf(ATTENDANCE_TIME_CHECK_MESSAGE, DateTimeUtil.formatDateTime(ldt), attendance.getStatus().getName());
    }

    public void printModifyResult(Attendance oldAttendance, Attendance newAttendance){
        LocalDateTime ldt = LocalDateTime.of(oldAttendance.getDate(), oldAttendance.getTime());
        String oldDateTime = DateTimeUtil.formatDateTime(ldt);
        String oldStatus = oldAttendance.getStatus().getName();
        String newTime = DateTimeUtil.formatTime(newAttendance.getTime());
        String newStatus = newAttendance.getStatus().getName();

        System.out.printf(ATTENDANCE_MODIFY_MESSAGE, oldDateTime, oldStatus, newTime, newStatus);
    }

    public void printAttendanceRecord(String nickname, List<LocalDate> schoolDays, List<Attendance> attendances) {
        System.out.printf(ATTENDANCE_HISTORY_MESSAGE, nickname);
        printNewLine();
        attendances.sort((a1, a2) -> {
            LocalDate ld1 = a1.getDate();
            LocalTime lt1 = a1.getTime();
            LocalDateTime ldt1 = LocalDateTime.of(ld1, lt1);
            LocalDate ld2 = a2.getDate();
            LocalTime lt2 = a2.getTime();
            LocalDateTime ldt2 = LocalDateTime.of(ld2, lt2);
            return lt1.compareTo(lt2);
        });
        int att = 0, late = 0, absent = 0;
        for (LocalDate schoolDay : schoolDays) {
            LocalDateTime ldt = null;
            AttendanceStatus attendanceStatus = null;
            for (Attendance attendance : attendances) {
                if (attendance.getDate().equals(schoolDay)) {
                    ldt = LocalDateTime.of(attendance.getDate(), attendance.getTime());
                    attendanceStatus = attendance.getStatus();
                    break;
                }
            }
            if (ldt != null) {
                String dateTime = DateTimeUtil.formatDateTime(ldt);
                String status = attendanceStatus.getName();
                System.out.printf(ATTENDANCE_SINGLE_HISTORY_MESSAGE, dateTime, status);
                if (attendanceStatus == AttendanceStatus.ATTENDANCE) att++;
                if (attendanceStatus == AttendanceStatus.LATENESS) late++;
                if (attendanceStatus == AttendanceStatus.ABSENT) absent++;
            }
            else {
                String date = DateTimeUtil.formatDateWithDay(schoolDay);
                System.out.printf(ATTENDANCE_SINGLE_HISTORY_ABSENT_MESSAGE, date);
                absent++;
            }
        }
        printNewLine();
        System.out.println("출석: " + att + "회");
        System.out.println("지각: " + late + "회");
        System.out.println("결석: " + absent + "회");
        printNewLine();
        int totalAbsent = absent + late/3;
        if (totalAbsent > 5) {
            System.out.println("제적 대상자입니다.");
            return;
        }
        if (totalAbsent >= 3) {
            System.out.println("면담 대상자입니다.");
            return;
        }
        if (totalAbsent >= 2) {
            System.out.println("경고 대상자입니다.");
        }
    }

    public void printRiskList(Set<String> allNicknames, List<LocalDate> schoolDays) {
        System.out.println(DANGEROUS_PERSON_CHECK_HEADER_MESSAGE);
        List<Score> crews = new ArrayList<>();

        for (String name : allNicknames) {
            Score score = Score.from(name, schoolDays);
            crews.add(score);
        }

        crews.sort((s1, s2) -> {
            int a1 = s1.getAbsentCount();
            int l1 = s1.getLateCount();
            int scr1 = s1.getScore();
            String name1 = s1.getName();
            int a2 = s2.getAbsentCount();
            int l2 = s2.getLateCount();
            int scr2 = s2.getScore();
            String name2 = s2.getName();
            if (scr1 != scr2) return Integer.compare(scr2, scr1);
            if (a1 + l1 != a2 + l2) return Integer.compare(a2 + l2, a1 + l1);
            return name1.compareTo(name2);
        });
        for (Score crew : crews) {
            if (crew.getScore() < 2) continue;
            System.out.printf(DANGEROUS_PERSON_CHECK_MESSAGE,
                    crew.getName(), crew.getAbsentCount(), crew.getLateCount(), crew.getStatus());
        }
    }

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printNewLine() {
        System.out.printf(NEW_LINE);
    }
}
