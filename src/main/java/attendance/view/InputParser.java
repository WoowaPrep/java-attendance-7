package attendance.view;

import attendance.domain.AttendanceTime;
import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputParser {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public AttendanceTime parseAttendanceTime(String input) {
        try {
            LocalTime localTime = LocalTime.parse(input, TIME_FORMATTER);
            return AttendanceTime.from(localTime);
        } catch (DateTimeParseException e) {
            throw AttendanceException.from(ErrorMessage.INVALID_INPUT);
        }
    }
}
