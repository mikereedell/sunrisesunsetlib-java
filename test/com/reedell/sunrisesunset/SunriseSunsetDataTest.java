package com.reedell.sunrisesunset;

import java.util.Calendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class SunriseSunsetDataTest extends BaseTestCase {

    private static List<String[]> data;

    @BeforeClass
    public static void setupAllTests() {
        CSVTestDriver driver = new CSVTestDriver("SunriseSunsetTestData.txt");
        data = driver.getData();
    }

    @Test
    public void testRiseAndSetTimes() {
        for (String[] line : data) {
            String date = line[0];
            String riseTime = line[1];
            String setTime = line[2];

            Calendar calendar = createCalendar(date.split("\\/"));
            SolarEventCalculator calc = new SolarEventCalculator(createLocation(), 96, calendar);
            assertTimeEquals(riseTime, calc.computeSunriseTime(), date);
            assertTimeEquals(setTime, calc.computeSunsetTime(), date);
        }
    }

    private Calendar createCalendar(String[] dateParts) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(dateParts[2]), Integer.valueOf(dateParts[0]) - 1, Integer.valueOf(dateParts[1]));
        return cal;
    }

    private Location createLocation() {
        // N39deg 59' 37" W075deg 47' 06"
        return new Location("39.9937", "-75.7850");
    }
}
