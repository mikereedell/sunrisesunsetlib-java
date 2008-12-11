package com.reedell.sunrisesunset;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

public class SunriseSunsetDataTest {

    private static List<String[]> data;

    @BeforeClass
    public static void setupAllTests() {
        CSVTestDriver driver = new CSVTestDriver("SunriseSunsetTestData.txt");
        data = driver.getData();
    }

    @Test
    public void testRiseAndSetTimes() {
        for (String[] line : data) {
            // Create a new Calendar then instantiate a new Calculator and get the rise/set time to compare
            // against the data values.
            String date = line[0];
            String riseTime = line[1];
            String setTime = line[2];

            Calendar calendar = createCalendar(date.split("\\/"));
            SolarEventCalculator calc = new SolarEventCalculator(createLocation(), 96, calendar);
            assertEquals("RiseTime for date: " + calendar.getTime(), riseTime, calc.computeSunriseTime());
            assertEquals("SetTime for date: " + calendar.getTime(), setTime, calc.computeSunsetTime());
        }
    }

    private Calendar createCalendar(String[] dateParts) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(dateParts[0]), Integer.valueOf(dateParts[1]) - 1, Integer.valueOf(dateParts[2]));
        return cal;
    }

    private Location createLocation() {
        return new Location("39.9937", "-75.7850");
    }
}
