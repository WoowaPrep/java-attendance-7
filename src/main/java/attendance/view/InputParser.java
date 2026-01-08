package attendance.view;

import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InputParser {

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    public LocalTime parseAttendanceTime(String input) {
        try {
            LocalTime localTime = LocalTime.parse(input, TIME_FORMATTER);
            return localTime;
        } catch (DateTimeParseException e) {
            throw AttendanceException.from(ErrorMessage.INVALID_INPUT);
        }
    }
}
