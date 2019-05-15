package locator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlateLocator {

    private static final int CSV_COUNTY_IDX_COLUMN = 3;
    private static final int CSV_VOIVOIDESHOP_IDX_COLUMN = 2;
    private static final String CSV_FILE = "licenseplates.csv";
    private static final String REGEX = "^[A-Z]{1,3}";

    public static List<String> getLocationFromPlate(String registrationPlate) throws IOException {

        if (Objects.isNull(registrationPlate)) {
            return Collections.emptyList();
        }

        String location = findLocation(registrationPlate);
        if (location.equalsIgnoreCase("not found")) {
            return Collections.emptyList();
        }

        return getLocationData(location);
    }


    private static String findLocation(String plate) {

        plate = plate.toUpperCase();

        Pattern compiledPattern = Pattern.compile(REGEX);
        Matcher matcher = compiledPattern.matcher(plate);

        if (!matcher.find()) {
            return "not found";
        }

        return matcher.group(0);
    }

    private static List<String> getLocationData(String areaCode) throws IOException {
        Path path = Paths.get(CSV_FILE);

        if (!Files.exists(path)) {
            return Collections.emptyList();
        }

        List<String> lines = Files.readAllLines(path);

        Optional<List<String>> result = lines.stream()
                .map(line -> Arrays.asList(line.split(",")))
                .filter(row -> {

                    if (areaCode.length() == 1 && row.get(CSV_VOIVOIDESHOP_IDX_COLUMN).equalsIgnoreCase(areaCode)) {
                        return true;
                    }
                    return (row.get(CSV_VOIVOIDESHOP_IDX_COLUMN) + row.get(CSV_COUNTY_IDX_COLUMN))
                            .equalsIgnoreCase(areaCode);
                })
                .map(row -> {
                    if (areaCode.length() == 1) {
                        return Collections.singletonList(row.get(0));
                    }
                    return Arrays.asList(row.get(0), row.get(1));
                })
                .findFirst();

        return result.orElse(Collections.emptyList());
    }
}
