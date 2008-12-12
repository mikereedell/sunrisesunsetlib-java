package com.reedell.sunrisesunset;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Assert;

public class BaseTestCase {

    protected Calendar eventDate;

    protected Location location;

    public void setup(int month, int day, int year) {
        eventDate = Calendar.getInstance();
        eventDate.set(Calendar.YEAR, year);
        eventDate.set(Calendar.MONTH, month);
        eventDate.set(Calendar.DAY_OF_MONTH, day);

        BigDecimal latitude = BigDecimal.valueOf(39.9937).setScale(4);
        BigDecimal longitude = BigDecimal.valueOf(-75.7850).setScale(4);
        location = new Location(latitude, longitude);
    }

    protected String getMessage(Object expected, Object actual) {
        return "Expected: " + expected + " but was: " + actual;
    }
    
    /**
     * +- two minutes is good enough.
     * 
     * @param expectedTime
     * @param actualTime
     * @return
     */
    protected void assertTimeEquals(String expectedTime, String actualTime, String date) {
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
