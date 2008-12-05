package com.reedell.sunrisesunset;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

/**
 * Parent class of the Sunrise and Sunset calculator classes.
 */
public class SolarEventCalculator {
    protected Calendar eventDate;

    protected Location location;
    protected double zenith;

    /**
     * Constructs a new <code>SolarEventCalculator</code> based on the given parameters.
     * 
     * @param location
     *            <code>Location</code> object containing the coordinates to base the calculation on.
     * @param zenith
     *            zenith, in degrees, used in the solar calculation.
     */
    public SolarEventCalculator(Location location, Integer zenith) {
        this(location, zenith, Calendar.getInstance());
    }

    /**
     * Constructs a new <code>SolarEventCalculator</code> based on the given parameters.
     * 
     * @param location
     *            <code>Location</code> object containing the coordinates to base the calculation on.
     * @param zenith
     *            zenith, in degrees, used in the solar calculation.
     * @param eventDate
     *            date of the solar event to calculate.
     */
    public SolarEventCalculator(Location location, Integer zenith, Calendar eventDate) {
        this.location = location;
        this.zenith = zenith;
        this.eventDate = eventDate;
    }

    public BigDecimal getDayOfYear() {
        return new BigDecimal(this.eventDate.get(Calendar.DAY_OF_YEAR));
    }

    public BigDecimal getUTCOffset() {
        int offSetInMillis = eventDate.get(Calendar.ZONE_OFFSET);
        return new BigDecimal(offSetInMillis / 3600000);
    }

    public BigDecimal getZenithInRadians() {
        return BigDecimal.valueOf(Math.toRadians(this.zenith)).setScale(4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal getBaseLongitudeHour() {
        return this.location.getLongitude().divide(BigDecimal.valueOf(15), 4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal getLongitudeHour(int offset) {
        BigDecimal dividend = BigDecimal.valueOf(offset).subtract(this.getBaseLongitudeHour());
        BigDecimal addend = dividend.divide(BigDecimal.valueOf(24), 4, RoundingMode.HALF_EVEN);
        BigDecimal longHour = this.getDayOfYear().add(addend);
        return longHour.setScale(4, RoundingMode.HALF_EVEN);
    }

    /**
     * Compute the arc-cosine for the given argument in radians.
     * 
     * @param radians
     * @return
     */
    protected BigDecimal getArcCosineFor(BigDecimal radians) {
        BigDecimal arcCosine = BigDecimal.valueOf(Math.acos(radians.doubleValue()));
        return arcCosine.setScale(4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal convertRadiansToDegrees(BigDecimal radians) {
        return radians.multiply(new BigDecimal(180 / Math.PI)).setScale(4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal convertDegreesToRadians(BigDecimal degrees) {
        return degrees.multiply(BigDecimal.valueOf(Math.PI / 180.0)).setScale(4, RoundingMode.HALF_EVEN);
    }
}