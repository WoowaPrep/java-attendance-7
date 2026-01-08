package attendance;

import attendance.domain.Attendance;
import attendance.domain.AttendanceRepository;
import attendance.utils.ResourceReader;
import camp.nextstep.edu.missionutils.Console;
import java.util.List;

public class Application {
    public static void main(String[] args) {
        try {
            loadAttendances();

            new AttendanceBook().manage();
        } finally {
            Console.close();
        }
    }

    private static void loadAttendances() {
        List<List<String>> data = ResourceReader.readCSVWithoutHeader("attendances.csv");

        for (List<String> row : data) {
            String nickname = row.get(0);
            String datetime = row.get(1);

            Attendance attendance = Attendance.fromCSV(nickname, datetime);
            AttendanceRepository.save(attendance);
        }
    }
}
