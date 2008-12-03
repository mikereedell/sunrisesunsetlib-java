package com.reedell.sunrisesunset;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.Calendar;

/**
 * Parent class of the Sunrise and Sunset calculator classes.
 */
public class SolarEventCalculator {
    protected Calendar eventDate;

    protected Location location;
    protected double zenith;
    protected static final BigDecimal RAD_TO_DEG = new BigDecimal(180 / Math.PI);
    protected static final BigDecimal DEG_TO_RAD = BigDecimal.valueOf(Math.PI / 180.0);
    protected static final BigDecimal FIFTEEN_DEG_IN_RAD = BigDecimal.valueOf(Math.toRadians(15));
    protected static final MathContext MATH_CONTEXT = new MathContext(4, RoundingMode.HALF_EVEN);

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

    public int getUTCOffset() {
        return this.eventDate.getTimeZone().getOffset(this.eventDate.getTimeInMillis());
    }

    public BigDecimal getZenithInRadians() {
        return BigDecimal.valueOf(Math.toRadians(this.zenith));
    }

    protected BigDecimal getBaseLongitudeHour() {
        return convertDegreesToRadians(this.location.getLongitude()).divide(FIFTEEN_DEG_IN_RAD, 4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal getArcCosineFor(BigDecimal number) {
        BigDecimal arcCosine = BigDecimal.valueOf(Math.acos(number.doubleValue()));
        return arcCosine;
    }

    protected BigDecimal convertRadiansToDegrees(BigDecimal radians) {
        return radians.multiply(RAD_TO_DEG);
    }

    protected BigDecimal convertDegreesToRadians(BigDecimal degrees) {
        return degrees.multiply(DEG_TO_RAD);
    }
}