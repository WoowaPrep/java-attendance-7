package attendance.domain;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AttendanceRepository {

    private static final Map<String, Map<LocalDate, Attendance>> storage = new HashMap<>();

    private static final Set<String> names = new HashSet<>();

    public static void clear() {
        storage.clear();
        names.clear();
    }

    public static void save(Attendance attendance) {
        String nickname = attendance.getNickname();
        LocalDate date = attendance.getDate();

        names.add(nickname);
        storage.computeIfAbsent(nickname, k -> new HashMap<>()).put(date, attendance);
    }

    public static boolean hasAttendance(String nickname, LocalDate date) {
        Map<LocalDate, Attendance> records = storage.get(nickname);
        return records != null && records.containsKey(date);
    }

    public static Attendance getAttendance(String nickname, LocalDate date) {
        Map<LocalDate, Attendance> records = storage.get(nickname);
        if (records == null) return null;
        return records.get(date);
    }

    public static List<Attendance> findAllByNickname(String nickname) {
        Map<LocalDate, Attendance> records = storage.get(nickname);
        if (records == null) return new ArrayList<>();
        return new ArrayList<>(records.values());
    }

    public static Set<String> getAllNicknames() {
        return new HashSet<>(names);
    }

    public static void update(String nickname, LocalDate date, LocalTime newTime) {
        Attendance oldAttendance = getAttendance(nickname, date);
        if (oldAttendance == null) {
            throw new IllegalArgumentException("[ERROR] 출석 기록이 없습니다.");
        }

        Attendance newAttendance = new Attendance(nickname, date, newTime);
        storage.get(nickname).put(date, newAttendance);
    }
}
