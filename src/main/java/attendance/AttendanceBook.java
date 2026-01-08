package attendance;

import attendance.domain.AttendanceTime;
import attendance.domain.Crew;
import attendance.domain.Menu;
import attendance.domain.RestDay;
import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import attendance.utils.DateTimeUtil;
import attendance.view.InputParser;
import attendance.view.InputView;
import attendance.view.OutputView;
import java.util.function.Supplier;

public class AttendanceBook {

    private InputView inputView;
    private OutputView outputView;
    private InputParser inputParser;

    public AttendanceBook() {
        this(new InputView(), new OutputView(), new InputParser());
    }

    public AttendanceBook(InputView inputView, OutputView outputView, InputParser inputParser) {
        this.inputView = inputView;
        this.outputView = outputView;
        this.inputParser = inputParser;
    }

    public void manage() {
        boolean isContinue;
        do {
            Menu menu = readMenu();
            isContinue = play(menu);
        } while(isContinue);
    }

    private Menu readMenu() {
        return retry(() -> {
            String input = inputView.printMenu();
            return Menu.from(input);
        });
    }

    private boolean play(Menu menu) {
        if (menu == Menu.FIRST) {
            if (canAttend()) {
                Crew crew = readNickName();
                AttendanceTime time = readAttendanceTime();
                printAttendanceTime(time);
            }
            return true;
        }
        if (menu == Menu.SECOND) {

        }
        if (menu == Menu.THIRD) {

        }
        if (menu == Menu.FOURTH) {

        }
        if (menu == Menu.QUIT) {
            return false;
        }

        throw AttendanceException.from(ErrorMessage.INVALID_INPUT);
    }

    private void printAttendanceTime(AttendanceTime time) {
        inputView.printAttendance(time);
    }

    private boolean canAttend() {
        RestDay restDay = RestDay.from(DateTimeUtil.today());
        return true;
    }

    private Crew readNickName() {
        return retry(() -> {
            String input = inputView.printNickName();
            inputView.printNewLine();
            return Crew.from(input);
        });
    }

    private AttendanceTime readAttendanceTime() {
        return retry(() -> {
            String input = inputView.printAttendanceTime();
            return inputParser.parseAttendanceTime(input);
        });
    }

    private <T> T retry(Supplier<T> supplier) {
        while (true) {
            try {
                return supplier.get();
            } catch (IllegalArgumentException e) {
                outputView.printErrorMessage(e.getMessage());
            }
        }
    }
}
