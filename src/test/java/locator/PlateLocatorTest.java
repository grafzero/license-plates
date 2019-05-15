package locator;

import org.junit.Test;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;

public class PlateLocatorTest {

    @Test
    public void getLocationFromPlate() throws IOException {
        List<String> testPlates = getTestPlates();
        for (String testPlate : testPlates) {
            List<String> locationFromPlate = PlateLocator.getLocationFromPlate(testPlate);
            System.out.println(testPlate + ": " + locationFromPlate);
        }
    }

    @Test
    public void shouldReturnOnlyVoivForShortPlate() throws IOException {

        String shortPlate = "G 67H";

        List<String> expected = Collections.singletonList("POMORSKIE");
        List<String> actual = PlateLocator.getLocationFromPlate(shortPlate);

        assertEquals(expected, actual);
    }

    @Test
    public void shouldReturnEmptyListOnNullPlate() throws IOException {

        String nullPlate = null;

        List<String> expected = Collections.emptyList();
        List<String> actual = PlateLocator.getLocationFromPlate(nullPlate);

        assertEquals(expected, actual);
    }


    private static List<String> getTestPlates() {

        return Arrays.asList(
                "BI 3378P",
                "WB 9092N",
                "WD 6660L",
                "KWI 56050",
                "DW 6CF25",
                "SCI 64WY",
                "G 67H",
                "RKR 5S56",
                "CWL KG74",
                "KR 7KR71",

                "BI3378P",
                "WB9092N",
                "WD6660L",
                "KWI56050",
                "DW6CF25",
                "SCI64WY",
                "G67H",
                "RKR5S56",
                "CWLKG74",
                "KR7KR71",

                "bI3378P",
                "wb9092N",
                null,
                "dhwxcvledhcv2eicgh2eiucgfdejbce;lfbce;2cbhecb2;ebc2;4ejbc2;ecbfghi24ucg24p;uicgh2chbe2uch5r3e2367r423dugr",
                "X 12A",
                "!@#$%^HJVX",
                "G"
        );

    }

}
