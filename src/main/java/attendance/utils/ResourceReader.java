package attendance.utils;

import attendance.exception.AttendanceException;
import attendance.exception.ErrorMessage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ResourceReader {

    private static final String LINE_SEPARATOR = "\n";
    private static final String CSV_DELIMITER = ",";
    private static final int DATA_START_INDEX = 1;

    public static String readFile(String fileName) {
        try (InputStream inputStream = getResourceAsStream(fileName);
                InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader)) {

            return bufferedReader.lines()
                    .collect(Collectors.joining(LINE_SEPARATOR));

        } catch (IOException e) {
            throw AttendanceException.from(ErrorMessage.FILE_READ_FAILED);
        }
    }

    public static List<String> readLines(String fileName) {
        try (InputStream inputStream = getResourceAsStream(fileName);
                InputStreamReader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
                BufferedReader bufferedReader = new BufferedReader(reader)) {

            return bufferedReader.lines()
                    .collect(Collectors.toList());

        } catch (IOException e) {
            throw AttendanceException.from(ErrorMessage.FILE_READ_FAILED);
        }
    }

    public static List<List<String>> readCSV(String fileName) {
        List<String> lines = readLines(fileName);
        List<List<String>> result = new ArrayList<>();

        for (String line : lines) {
            if (isEmptyLine(line)) {
                continue;
            }
            result.add(parseCSVLine(line));
        }

        return result;
    }

    public static List<List<String>> readCSVWithoutHeader(String fileName) {
        List<List<String>> allData = readCSV(fileName);

        if (allData.isEmpty()) {
            return new ArrayList<>();
        }

        return allData.subList(DATA_START_INDEX, allData.size());
    }

    private static List<String> parseCSVLine(String line) {
        String[] values = line.split(CSV_DELIMITER);
        List<String> row = new ArrayList<>();

        for (String value : values) {
            row.add(value.trim());
        }

        return row;
    }

    private static boolean isEmptyLine(String line) {
        return line.trim().isEmpty();
    }

    private static InputStream getResourceAsStream(String fileName) {
        InputStream inputStream = ResourceReader.class
                .getClassLoader()
                .getResourceAsStream(fileName);

        if (inputStream == null) {
            throw AttendanceException.from(ErrorMessage.FILE_NOT_FOUND);
        }

        return inputStream;
    }
}
