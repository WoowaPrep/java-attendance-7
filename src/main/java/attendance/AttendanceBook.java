package attendance;

import attendance.domain.Menu;
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
        Menu menu = readMenu();

        play(menu);
    }

    private Menu readMenu() {
        return retry(() -> {
            String input = inputView.printMenu();
            return Menu.from(input);
        });
    }

    private void play(Menu menu) {
        if (menu == Menu.FIRST) {

        }
        if (menu == Menu.SECOND) {

        }
        if (menu == Menu.THIRD) {

        }
        if (menu == Menu.FOURTH) {

        }
        if (menu == Menu.QUIT) {

        }
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
