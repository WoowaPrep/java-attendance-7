package attendance.exception;

public class AttendanceException extends IllegalArgumentException {

    private AttendanceException(ErrorMessage errorMessage) {
        super(errorMessage.getMessage());
    }

    public static AttendanceException from(ErrorMessage errorMessage) {
        return new AttendanceException(errorMessage);
    }
}
