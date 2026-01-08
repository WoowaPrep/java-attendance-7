package attendance.domain;

import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import attendance.utils.ResourceReader;
import java.util.ArrayList;
import java.util.List;

public class Crew {

    private String name;

    public Crew(String name) {
        this.name = name;
    }

    public static Crew from(String input) {
        List<String> names = loadNickName();

        return names.stream()
                .filter(name -> name.equals(input))
                .findFirst()
                .map(Crew::new)
                .orElseThrow(() -> AttendanceException.from(ErrorMessage.INVALID_NICK_NAME));
    }

    private static List<String> loadNickName() {
        List<List<String>> nameDates = ResourceReader.readCSVWithoutHeader("attendances.csv");
        List<String> names = new ArrayList<>();
        for (List<String> nameDate : nameDates) {
            names.add(nameDate.get(0));
        }
        return names;
    }

    public String getName() {
        return name;
    }
}
