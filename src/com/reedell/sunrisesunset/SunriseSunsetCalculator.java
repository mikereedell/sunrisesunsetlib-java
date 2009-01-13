package com.reedell.sunrisesunset;

import java.util.Calendar;

import com.reedell.sunrisesunset.calculator.SolarEventCalculator;
import com.reedell.sunrisesunset.dto.Location;

/**
 * Public interface for getting the various types of sunrise/sunset.
 */
public class SunriseSunsetCalculator {

    private Location location;

    private SolarEventCalculator calculator;

    /**
     * Constructs a new <code>SunriseSunsetCalculator</code> with the given <code>Location</code>
     * 
     * @param location
     *            <code>Location</code> object containing the Latitude/Longitude of the location to compute
     *            the sunrise/sunset for.
     */
    public SunriseSunsetCalculator(Location location) {
        this.calculator = new SolarEventCalculator(location);
    }

    /**
     * Constructs a new <code>SunriseSunsetCalculator</code> with the given <code>Location</code>
     * 
     * @param location
     *            <code>Location</code> object containing the Latitude/Longitude of the location to compute
     *            the sunrise/sunset for.
     * @param timeZoneIdentifier
     *            String identifier for the timezone to compute the sunrise/sunset times in. In the form
     *            "America/New_York".
     */
    public SunriseSunsetCalculator(Location location, String timeZoneIdentifier) {
        this.calculator = new SolarEventCalculator(location, timeZoneIdentifier);
    }

    /**
     * Returns the astronomical (108deg) sunrise for the given date.
     * 
     * @param date
     *            <code>Calendar</code> object containing the date to compute the astronomical sunrise for.
     * @return the astronomical sunrise time in HH:MM (24-hour clock) form.
     */
    public String getAstronomicalSunriseForDate(Calendar date) {
        return calculator.computeSunriseTime(Zenith.ASTRONOMICAL, date);
    }

    /**
     * Returns the astronomical (108deg) sunset for the given date.
     * 
     * @param date
     *            <code>Calendar</code> object containing the date to compute the astronomical sunset for.
     * @return the astronomical sunset time in HH:MM (24-hour clock) form.
     */
    public String getAstronomicalSunsetForDate(Calendar date) {
        return calculator.computeSunsetTime(Zenith.ASTRONOMICAL, date);
    }

    /**
     * Returns the nautical (102deg) sunrise for the given date.
     * 
     * @param date
     *            <code>Calendar</code> object containing the date to compute the nautical sunrise for.
     * @return the nautical sunrise time in HH:MM (24-hour clock) form.
     */
    public String getNauticalSunriseForDate(Calendar date) {
        return calculator.computeSunriseTime(Zenith.NAUTICAL, date);
    }

    /**
     * Returns the nautical (102deg) sunset for the given date.
     * 
     * @param date
     *            <code>Calendar</code> object containing the date to compute the nautical sunset for.
     * @return the nautical sunset time in HH:MM (24-hour clock) form.
     */
    public String getNauticalSunsetForDate(Calendar date) {
        return calculator.computeSunsetTime(Zenith.NAUTICAL, date);
    }

    /**
     * Returns the civil sunrise (twilight, 96deg) for the given date.
     * 
     * @param date
     *            <code>Calendar</code> object containing the date to compute the civil sunrise for.
     * @return the civil sunrise time in HH:MM (24-hour clock) form.
     */
    public String getCivilSunriseForDate(Calendar date) {
        return calculator.computeSunriseTime(Zenith.CIVIL, date);
    }

    /**
     * Returns the civil sunset (twilight, 96deg) for the given date.
     * 
     * @param date
     *            <code>Calendar</code> object containing the date to compute the civil sunset for.
     * @return the civil sunset time in HH:MM (24-hour clock) form.
     */
    public String getCivilSunsetForDate(Calendar date) {
        return calculator.computeSunsetTime(Zenith.CIVIL, date);
    }

    /**
     * Returns the official sunrise (90deg 50', 90.8333deg) for the given date.
     * 
     * @param date
     *            <code>Calendar</code> object containing the date to compute the official sunrise for.
     * @return the official sunrise time in HH:MM (24-hour clock) form.
     */
    public String getOfficalSunriseForDate(Calendar date) {
        return calculator.computeSunriseTime(Zenith.OFFICIAL, date);
    }

    /**
     * Returns the official sunrise (90deg 50', 90.8333deg) for the given date.
     * 
     * @param date
     *            <code>Calendar</code> object containing the date to compute the official sunset for.
     * @return the official sunset time in HH:MM (24-hour clock) form.
     */
    public String getOfficialSunsetForDate(Calendar date) {
        return calculator.computeSunsetTime(Zenith.OFFICIAL, date);
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
