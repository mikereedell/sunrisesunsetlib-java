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
        return setScale(BigDecimal.valueOf(Math.toRadians(this.zenith)));
    }

    protected BigDecimal getBaseLongitudeHour() {
        return divideBy(this.location.getLongitude(), BigDecimal.valueOf(15));
    }

    protected BigDecimal getLongitudeHour(int offset) {
        BigDecimal dividend = BigDecimal.valueOf(offset).subtract(this.getBaseLongitudeHour());
        BigDecimal addend = this.divideBy(dividend, BigDecimal.valueOf(24));
        BigDecimal longHour = this.getDayOfYear().add(addend);
        return setScale(longHour);
    }

    protected BigDecimal getMeanAnomaly(BigDecimal longitudeHour) {
        BigDecimal meanAnomaly = multiplyBy(new BigDecimal("0.9856"), longitudeHour).subtract(new BigDecimal("3.289"));
        return setScale(meanAnomaly);
    }

    protected BigDecimal getSunTrueLongitude(BigDecimal meanAnomaly) {
        BigDecimal sinMeanAnomaly = new BigDecimal(Math.sin(convertDegreesToRadians(meanAnomaly).doubleValue()));
        BigDecimal sinDoubleMeanAnomaly = new BigDecimal(Math.sin(multiplyBy(convertDegreesToRadians(meanAnomaly),
                BigDecimal.valueOf(2)).doubleValue()));

        BigDecimal firstPart = meanAnomaly.add(multiplyBy(sinMeanAnomaly, new BigDecimal("1.916")));
        BigDecimal secondPart = multiplyBy(sinDoubleMeanAnomaly, new BigDecimal("0.020")).add(new BigDecimal("282.634"));
        BigDecimal trueLongitude = firstPart.add(secondPart);

        if (trueLongitude.doubleValue() > 360) {
            trueLongitude = trueLongitude.subtract(BigDecimal.valueOf(360));
        }
        return setScale(trueLongitude);
    }

    protected BigDecimal getRightAscension(BigDecimal sunTrueLong) {
        BigDecimal tanL = new BigDecimal(Math.tan(convertDegreesToRadians(sunTrueLong).doubleValue()));

        BigDecimal innerParens = multiplyBy(convertRadiansToDegrees(tanL), new BigDecimal("0.91764"));
        BigDecimal rightAscension = new BigDecimal(Math.atan(convertDegreesToRadians(innerParens).doubleValue()));
        rightAscension = setScale(convertRadiansToDegrees(rightAscension));

        BigDecimal ninety = BigDecimal.valueOf(90);
        BigDecimal longitudeQuadrant = sunTrueLong.divide(ninety, 0, RoundingMode.FLOOR);
        longitudeQuadrant = longitudeQuadrant.multiply(ninety);

        BigDecimal rightAscensionQuadrant = rightAscension.divide(ninety, 0, RoundingMode.FLOOR);
        rightAscensionQuadrant = rightAscensionQuadrant.multiply(ninety);

        BigDecimal augend = longitudeQuadrant.subtract(rightAscensionQuadrant);
        return divideBy(rightAscension.add(augend), BigDecimal.valueOf(15));
    }

    protected BigDecimal getSinOfSunDeclination(BigDecimal sunTrueLong) {
        BigDecimal sinTrueLongitude = BigDecimal.valueOf(Math.sin(convertDegreesToRadians(sunTrueLong).doubleValue()));
        BigDecimal sinOfDeclination = sinTrueLongitude.multiply(new BigDecimal("0.39782"));
        return setScale(sinOfDeclination);
    }

    protected BigDecimal getCosineOfSunDeclination(BigDecimal sinSunDeclination) {
        BigDecimal arcSinOfSinDeclination = BigDecimal.valueOf(Math.asin(sinSunDeclination.doubleValue()));
        BigDecimal cosDeclination = BigDecimal.valueOf(Math.cos(arcSinOfSinDeclination.doubleValue()));
        return setScale(cosDeclination);
    }

    protected BigDecimal getCosineSunLocalHour(BigDecimal sinSunDeclination, BigDecimal cosineSunDeclination) {
        BigDecimal zenithInRads = convertDegreesToRadians(BigDecimal.valueOf(this.zenith));
        BigDecimal cosineZenith = BigDecimal.valueOf(Math.cos(zenithInRads.doubleValue()));
        BigDecimal sinLatitude = BigDecimal.valueOf(Math.sin(convertDegreesToRadians(this.location.getLatitude()).doubleValue()));
        BigDecimal cosLatitude = BigDecimal.valueOf(Math.cos(convertDegreesToRadians(this.location.getLatitude()).doubleValue()));

        BigDecimal sinDeclinationTimesSinLat = sinSunDeclination.multiply(sinLatitude);
        BigDecimal dividend = cosineZenith.subtract(sinDeclinationTimesSinLat);
        BigDecimal divisor = cosineSunDeclination.multiply(cosLatitude);

        return setScale(divideBy(dividend, divisor));
    }

    protected BigDecimal getLocalMeanTime(BigDecimal longHour, BigDecimal rightAscension, BigDecimal sunLocalHour) {
        BigDecimal innerParens = longHour.multiply(new BigDecimal("0.06571"));
        BigDecimal localMeanTime = sunLocalHour.add(rightAscension).subtract(innerParens);
        localMeanTime = localMeanTime.subtract(new BigDecimal("6.622"));
        return setScale(localMeanTime);
    }

    protected BigDecimal getUTCTime(BigDecimal localMeanTime) {
        return localMeanTime.subtract(getBaseLongitudeHour());
    }

    protected BigDecimal getLocalTime(BigDecimal utcTime) {
        return utcTime.add(getUTCOffset());
    }

    protected String getLocalTimeAsString(BigDecimal localTime) {
        String localTimeString = localTime.toPlainString();
        String[] timeComponents = localTimeString.split("\\.");
        String hour = (timeComponents[0].length() == 1) ? "0" + timeComponents[0] : timeComponents[0];

        BigDecimal minutes = new BigDecimal("0." + timeComponents[1]);
        minutes = minutes.multiply(BigDecimal.valueOf(60)).setScale(0, RoundingMode.HALF_EVEN);
        String minuteString = minutes.intValue() < 10 ? "0" + minutes.toPlainString() : minutes.toPlainString();
        return hour + ":" + minuteString;
    }

    /**
     * Compute the arc-cosine for the given argument in radians.
     * 
     * @param radians
     * @return
     */
    protected BigDecimal getArcCosineFor(BigDecimal radians) {
        BigDecimal arcCosine = BigDecimal.valueOf(Math.acos(radians.doubleValue()));
        return setScale(arcCosine);
    }

    protected BigDecimal convertRadiansToDegrees(BigDecimal radians) {
        return multiplyBy(radians, new BigDecimal(180 / Math.PI));
    }

    protected BigDecimal convertDegreesToRadians(BigDecimal degrees) {
        return multiplyBy(degrees, BigDecimal.valueOf(Math.PI / 180.0));
    }

    protected BigDecimal multiplyBy(BigDecimal multiplicand, BigDecimal multiplier) {
        return setScale(multiplicand.multiply(multiplier));
    }

    protected BigDecimal divideBy(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, 4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal setScale(BigDecimal number) {
        return number.setScale(4, RoundingMode.HALF_EVEN);
    }
}