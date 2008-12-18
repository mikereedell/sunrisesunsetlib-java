package com.reedell.sunrisesunset.util;

import java.util.Calendar;

import org.junit.Assert;

import com.reedell.sunrisesunset.dto.Location;

public class BaseTestCase {

    protected Calendar eventDate;

    protected Location location;

    public void setup(int month, int day, int year) {
        eventDate = Calendar.getInstance();
        eventDate.set(Calendar.YEAR, year);
        eventDate.set(Calendar.MONTH, month);
        eventDate.set(Calendar.DAY_OF_MONTH, day);
        location = new Location("39.9937", "-75.7850");
    }

    /**
     * +- one minute is good enough.
     * 
     * @param expectedTime
     * @param actualTime
     * @return
     */
    protected void assertTimeEquals(String expectedTime, String actualTime, String date) {
        int expectedMinutes = getMinutes(expectedTime);
        int actualMinutes = getMinutes(actualTime);

        if (((expectedMinutes - 1) <= actualMinutes) && (actualMinutes <= (expectedMinutes + 1))) {
            return;
        }
        Assert.fail("Expected: " + expectedTime + ", but was: " + actualTime + " for date: " + date);
    }

    protected String getMessage(Object expected, Object actual) {
        return "Expected: " + expected + " but was: " + actual;
    }

    private int getMinutes(String timeString) {
        String[] timeParts = timeString.split("\\:");
        return (60 * Integer.valueOf(timeParts[0])) + Integer.valueOf(timeParts[1]);
    }
}
