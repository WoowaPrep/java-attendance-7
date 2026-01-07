package attendance.view;


public class OutputView {

    private static final String NEW_LINE = System.lineSeparator();

    public void printErrorMessage(String message) {
        System.out.println(message);
    }

    public void printNewLine() {
        System.out.printf(NEW_LINE);
    }
}
