package attendance.utils;

import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import camp.nextstep.edu.missionutils.DateTimes;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DateTimeUtil {

    // ========== 포맷터 (재사용) ==========
    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("MM월 dd일");
    private static final DateTimeFormatter DATETIME_INPUT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    // ========== 현재 시간 (우테코 라이브러리) ==========
    public static LocalDateTime now() {
        return DateTimes.now();
    }

    public static LocalDate today() {
        return DateTimes.now().toLocalDate();
    }

    public static String todayMonth() {
        LocalDate today = today();
        int month = today.getMonth().getValue();
        String monthString = String.valueOf(month);
        if (monthString.length() == 1) {
            monthString = "0" + monthString;
        }
        return monthString;
    }

    public static String getMonth(LocalDate input) {
        int m = input.getMonth().getValue();
        String mString = String.valueOf(m);
        if (mString.length() == 1) {
            mString = "0" + mString;
        }
        return mString;
    }

    public static String getDayOfMonth(LocalDate input) {
        int d = input.getDayOfMonth();
        String dString = String.valueOf(d);
        if (dString.length() == 1) {
            dString = "0" + dString;
        }
        return dString;
    }

    public static String geMinute(LocalTime input) {
        int m = input.getMinute();
        String minute = String.valueOf(m);
        if (minute.length() == 1) {
            minute = "0" + minute;
        }
        return minute;
    }

    public static String getHour(LocalTime input) {
        int h = input.getHour();
        String hour = String.valueOf(h);
        if (hour.length() == 1) {
            hour = "0" + hour;
        }
        return hour;
    }

    public static String getDayOfWeek(LocalDate input) {
        int d = input.getDayOfMonth();
        if (d % 7 == 2) return "월요일";
        if (d % 7 == 3) return "화요일";
        if (d % 7 == 4) return "수요일";
        if (d % 7 == 5) return "목요일";
        if (d % 7 == 6) return "금요일";
        if (d % 7 == 0) return "토요일";
        if (d % 7 == 1) return "일요일";

        throw AttendanceException.from(ErrorMessage.INVALID_INPUT);
    }

    public static String todayDayOfMonth() {
        LocalDate today = today();
        int day = today.getDayOfMonth();
        String dayString = String.valueOf(day);
        if (dayString.length() == 1) {
            dayString = "0" + dayString;
        }
        return dayString;
    }

    // ========== 파싱 (문자열 → 날짜/시간) ==========

    // "10:30" → LocalTime
    public static LocalTime parseTime(String timeStr) {
        try {
            return LocalTime.parse(timeStr, TIME_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }
    }

    // "2024-12-13 10:30" → LocalDateTime
    public static LocalDateTime parseDateTime(String datetimeStr) {
        try {
            return LocalDateTime.parse(datetimeStr, DATETIME_INPUT_FORMATTER);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }
    }

    // "13" → 12월 13일
    public static LocalDate parseDayOfMonth(int day) {
        try {
            LocalDate today = today();
            return LocalDate.of(today.getYear(), today.getMonth(), day);
        } catch (Exception e) {
            throw new IllegalArgumentException("[ERROR] 잘못된 형식을 입력하였습니다.");
        }
    }

    // ========== 포맷팅 (날짜/시간 → 문자열) ==========

    // LocalTime → "10:30"
    public static String formatTime(LocalTime time) {
        return time.format(TIME_FORMATTER);
    }

    // LocalDate → "12월 13일 금요일"
    public static String formatDateWithDay(LocalDate date) {
        String dayOfWeek = date.getDayOfWeek()
                .getDisplayName(TextStyle.FULL, Locale.KOREAN);
        return date.format(DATE_FORMATTER) + " " + dayOfWeek;
    }

    // LocalDateTime → "12월 13일 금요일 10:30"
    public static String formatDateTime(LocalDateTime dateTime) {
        return formatDateWithDay(dateTime.toLocalDate()) + " " + formatTime(dateTime.toLocalTime());
    }

    // 결석 표시용 "--:--"
    public static String formatAbsent() {
        return "--:--";
    }

    // ========== 요일 관련 ==========

    // 월요일인지 확인
    public static boolean isMonday(LocalDate date) {
        return date.getDayOfWeek() == DayOfWeek.MONDAY;
    }

    // 주말인지 확인
    public static boolean isWeekend(LocalDate date) {
        DayOfWeek day = date.getDayOfWeek();
        return day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY;
    }

    // 평일인지 확인
    public static boolean isWeekday(LocalDate date) {
        return !isWeekend(date);
    }

    // ========== 시간 비교 ==========

    // time1이 time2보다 이후인지
    public static boolean isAfter(LocalTime time1, LocalTime time2) {
        return time1.isAfter(time2);
    }

    // date1이 date2보다 이후인지
    public static boolean isAfter(LocalDate date1, LocalDate date2) {
        return date1.isAfter(date2);
    }

    // date1이 date2보다 이전인지
    public static boolean isBefore(LocalDate date1, LocalDate date2) {
        return date1.isBefore(date2);
    }

    // 같은 날짜인지
    public static boolean isSameDate(LocalDate date1, LocalDate date2) {
        return date1.isEqual(date2);
    }

    // ========== 날짜 범위 생성 ==========

    // startDate부터 endDate까지의 모든 날짜 리스트 (주말 제외)
    public static List<LocalDate> getWeekdaysBetween(LocalDate startDate, LocalDate endDate) {
        List<LocalDate> weekdays = new ArrayList<>();
        LocalDate current = startDate;

        while (!current.isAfter(endDate)) {
            if (isWeekday(current)) {
                weekdays.add(current);
            }
            current = current.plusDays(1);
        }

        return weekdays;
    }

    // 12월 1일부터 어제까지의 평일 리스트
    public static List<LocalDate> getDecemberWeekdaysUntilYesterday() {
        LocalDate december1st = LocalDate.of(today().getYear(), 12, 1);
        LocalDate yesterday = today().minusDays(1);
        return getWeekdaysBetween(december1st, yesterday);
    }

}
