package com.reedell.sunrisesunset;

import java.math.BigDecimal;
import java.util.Calendar;

import com.reedell.sunrisesunset.calculator.SolarEventCalculator;
import com.reedell.sunrisesunset.dto.Location;

/**
 * Public interface for getting the various types of sunrise/sunset.
 */
public class SunriseSunsetCalculator {

    private Location location;

    private SolarEventCalculator calculator;

    public SunriseSunsetCalculator(Location location) {
        this.calculator = new SolarEventCalculator(location);
    }

    /**
     * Returns the astronomical (108deg) sunrise for the given date.
     * 
     * @param date
     * @return
     */
    public String getAstronomicalSunriseForDate(Calendar date) {
        return calculator.computeSunriseTime(BigDecimal.valueOf(108), date);
    }

    public String getAstronomicalSunsetForDate(Calendar date) {
        return calculator.computeSunsetTime(BigDecimal.valueOf(108), date);
    }

    /**
     * Returns the nautical (102deg) sunrise for the given date.
     * 
     * @param date
     * @return
     */
    public String getNauticalSunriseForDate(Calendar date) {
        return calculator.computeSunriseTime(BigDecimal.valueOf(102), date);
    }

    public String getNauticalSunsetForDate(Calendar date) {
        return calculator.computeSunsetTime(BigDecimal.valueOf(102), date);
    }

    /**
     * Returns the civil sunrise (twilight, 96deg) for the given date.
     * 
     * @param date
     * @return
     */
    public String getCivilSunriseForDate(Calendar date) {
        return calculator.computeSunriseTime(BigDecimal.valueOf(96), date);
    }

    public String getCivilSunsetForDate(Calendar date) {
        return calculator.computeSunsetTime(BigDecimal.valueOf(96), date);
    }

    /**
     * Returns the official sunrise (90deg 50', 90.8333deg) for the given date.
     * 
     * @param date
     * @return
     */
    public String getOfficalSunriseForDate(Calendar date) {
        return calculator.computeSunriseTime(new BigDecimal("90.8333"), date);
    }

    public String getOfficialSunsetForDate(Calendar date) {
        return calculator.computeSunsetTime(new BigDecimal("90.8333"), date);
    }

    /**
     * Returns the location where the sunrise/sunset is calculated for.
     * 
     * @return <code>Location</code> object representing the location of the computed sunrise/sunset.
     */
    public Location getLocation() {
        return location;
    }
}
