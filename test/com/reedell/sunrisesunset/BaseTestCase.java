package com.reedell.sunrisesunset;

import java.math.BigDecimal;
import java.util.Calendar;

public class BaseTestCase {
    
    protected Calendar eventDate;

    protected Location location;
    
    public void setup() {
        // Set the date to November 1, 2008 (Day 306).
        eventDate = Calendar.getInstance();
        eventDate.set(Calendar.YEAR, 2008);
        eventDate.set(Calendar.MONTH, 10);
        eventDate.set(Calendar.DAY_OF_MONTH, 1);
        // At a zenith of 96 Degrees (Civil) (AKA 'Twilight')
        // Sunrise is 0604 EDT, sunset is 1729 EDT
        
        BigDecimal latitude = BigDecimal.valueOf(39.9937).setScale(4);
        BigDecimal longitude = BigDecimal.valueOf(-75.7850).setScale(4);
        location = new Location(latitude, longitude);
    }

    protected String getErrorMessage(Object expected, Object actual) {
        return "Expected: " + expected + " but was: " + actual;
    }
}
