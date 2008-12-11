package com.reedell.sunrisesunset;

import java.util.Calendar;
import java.util.List;

import org.junit.Assert;
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
            String date = line[0];
            String riseTime = line[1];
            String setTime = line[2];

            Calendar calendar = createCalendar(date.split("\\/"));
            SolarEventCalculator calc = new SolarEventCalculator(createLocation(), 96, calendar);
            // System.out.println("RiseTime for date: " + calendar.getTime() + " : " +
            // calc.computeSunriseTime());
            // System.out.println("SetTime for date: " + calendar.getTime() + " : " +
            // calc.computeSunsetTime());
            assertTimeEquals(riseTime, calc.computeSunriseTime(), date);
            assertTimeEquals(setTime, calc.computeSunsetTime(), date);
            // assertEquals("RiseTime for date: " + calendar.getTime(), riseTime, calc.computeSunriseTime());
            // assertEquals("SetTime for date: " + calendar.getTime(), setTime, calc.computeSunsetTime());
        }
    }

    private Calendar createCalendar(String[] dateParts) {
        System.out.println("Year: " + dateParts[2]);
        System.out.println("Month: " + dateParts[0]);
        System.out.println("Date: " + dateParts[1]);
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(dateParts[2]), Integer.valueOf(dateParts[0]) - 1, Integer.valueOf(dateParts[1]));
        return cal;
    }

    private Location createLocation() {
        // N39deg 59' 37" W075deg 47' 06"
        return new Location("39.9937", "-75.7850");
    }

    /**
     * +- two minutes is good enough.
     * 
     * @param expectedTime
     * @param actualTime
     * @return
     */
    private void assertTimeEquals(String expectedTime, String actualTime, String date) {
        int expectedMinutes = getMinutes(expectedTime);
        int actualMinutes = getMinutes(actualTime);

        if (((expectedMinutes - 2) <= actualMinutes) && (actualMinutes <= (expectedMinutes + 2))) {
            return;
        }
        Assert.fail("Expected: " + expectedTime + ", but was: " + actualTime + " for date: " + date);
    }

    private int getMinutes(String timeString) {
        String[] timeParts = timeString.split("\\:");
        return (60 * Integer.valueOf(timeParts[0])) + Integer.valueOf(timeParts[1]);
    }
}
