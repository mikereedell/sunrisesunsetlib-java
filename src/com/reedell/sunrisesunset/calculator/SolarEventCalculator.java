package com.reedell.sunrisesunset.calculator;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;
import java.util.TimeZone;

import com.reedell.sunrisesunset.dto.Location;

/**
 * Parent class of the Sunrise and Sunset calculator classes.
 */
public class SolarEventCalculator {
    private Boolean isSunrise = Boolean.TRUE;
    protected Calendar eventDate;

    protected Location location;
    protected BigDecimal zenith;

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
    public SolarEventCalculator(Location location, BigDecimal zenith, Calendar eventDate) {
        this.location = location;
        this.zenith = zenith;
        this.eventDate = eventDate;
    }

    public SolarEventCalculator(Location location) {
        this.location = location;
    }

    public String computeSunsetTime(BigDecimal solarZenith, Calendar date) {
        this.zenith = solarZenith;
        this.eventDate = date;
        return computeSunsetTime();
    }

    public String computeSunriseTime(BigDecimal solarZenith, Calendar date) {
        this.zenith = solarZenith;
        this.eventDate = date;
        return computeSunriseTime();
    }

    public String computeSunsetTime() {
        this.isSunrise = false;
        BigDecimal meanAnomaly = getMeanAnomaly();
        BigDecimal sunTrueLong = getSunTrueLongitude(meanAnomaly);

        BigDecimal localMeanTime = getLocalMeanTime(sunTrueLong);
        BigDecimal localTime = getLocalTime(localMeanTime);
        return getLocalTimeAsString(localTime);
    }

    public String computeSunriseTime() {
        this.isSunrise = true;
        BigDecimal meanAnomaly = getMeanAnomaly();
        BigDecimal sunTrueLong = getSunTrueLongitude(meanAnomaly);

        BigDecimal localMeanTime = getLocalMeanTime(sunTrueLong);
        BigDecimal localTime = getLocalTime(localMeanTime);
        return getLocalTimeAsString(localTime);
    }

    /**
     * Computes the base longitude hour, lngHour in the algorithm.
     * 
     * @return the longitude of the location of the solar event divided by 15 (deg/hour), in
     *         <code>BigDecimal</code> form.
     */
    private BigDecimal getBaseLongitudeHour() {
        return divideBy(location.getLongitude(), BigDecimal.valueOf(15));
    }

    /**
     * Computes the longitude time, t in the algorithm.
     * 
     * @return longitudinal time in <code>BigDecimal</code> form.
     */
    private BigDecimal getLongitudeHour() {
        int offset = 18;
        if (isSunrise) {
            offset = 6;
        }
        BigDecimal dividend = BigDecimal.valueOf(offset).subtract(getBaseLongitudeHour());
        BigDecimal addend = divideBy(dividend, BigDecimal.valueOf(24));
        BigDecimal longHour = getDayOfYear().add(addend);
        return setScale(longHour);
    }

    /**
     * Computes the mean anomaly of the Sun, M in the algorithm.
     * 
     * @return the suns mean anomaly, M, in <code>BigDecimal</code> form.
     */
    private BigDecimal getMeanAnomaly() {
        BigDecimal meanAnomaly = multiplyBy(new BigDecimal("0.9856"), getLongitudeHour()).subtract(new BigDecimal("3.289"));
        return setScale(meanAnomaly);
    }

    /**
     * Computes the true longitude of the sun, L in the algorithm, at the given location, adjusted to fit in
     * the range [0-360].
     * 
     * @param meanAnomaly
     *            the suns mean anomaly.
     * @return the suns true longitude, in <code>BigDecimal</code> form.
     */
    private BigDecimal getSunTrueLongitude(BigDecimal meanAnomaly) {
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

    /**
     * Computes the suns right ascension, RA in the algorithm, adjusting for the quadrant of L and turning it
     * into degree-hours.
     * 
     * @param sunTrueLong
     *            Suns true longitude, in <code>BigDecimal</code>
     * @return suns right ascension in degree-hours, in <code>BigDecimal</code> form.
     */
    private BigDecimal getRightAscension(BigDecimal sunTrueLong) {
        BigDecimal tanL = new BigDecimal(Math.tan(convertDegreesToRadians(sunTrueLong).doubleValue()));

        BigDecimal innerParens = multiplyBy(convertRadiansToDegrees(tanL), new BigDecimal("0.91764"));
        BigDecimal rightAscension = new BigDecimal(Math.atan(convertDegreesToRadians(innerParens).doubleValue()));
        rightAscension = setScale(convertRadiansToDegrees(rightAscension));

        // Adjust right ascension into [0,360]
        if (rightAscension.doubleValue() < 0) {
            rightAscension = rightAscension.add(BigDecimal.valueOf(360));
        } else if (rightAscension.doubleValue() > 360) {
            rightAscension = rightAscension.subtract(BigDecimal.valueOf(360));
        }

        BigDecimal ninety = BigDecimal.valueOf(90);
        BigDecimal longitudeQuadrant = sunTrueLong.divide(ninety, 0, RoundingMode.FLOOR);
        longitudeQuadrant = longitudeQuadrant.multiply(ninety);

        BigDecimal rightAscensionQuadrant = rightAscension.divide(ninety, 0, RoundingMode.FLOOR);
        rightAscensionQuadrant = rightAscensionQuadrant.multiply(ninety);

        BigDecimal augend = longitudeQuadrant.subtract(rightAscensionQuadrant);
        return divideBy(rightAscension.add(augend), BigDecimal.valueOf(15));
    }

    private BigDecimal getCosineSunLocalHour(BigDecimal sunTrueLong) {
        BigDecimal sinSunDeclination = getSinOfSunDeclination(sunTrueLong);
        BigDecimal cosineSunDeclination = getCosineOfSunDeclination(sinSunDeclination);

        BigDecimal zenithInRads = convertDegreesToRadians(zenith);
        BigDecimal cosineZenith = BigDecimal.valueOf(Math.cos(zenithInRads.doubleValue()));
        BigDecimal sinLatitude = BigDecimal.valueOf(Math.sin(convertDegreesToRadians(location.getLatitude()).doubleValue()));
        BigDecimal cosLatitude = BigDecimal.valueOf(Math.cos(convertDegreesToRadians(location.getLatitude()).doubleValue()));

        BigDecimal sinDeclinationTimesSinLat = sinSunDeclination.multiply(sinLatitude);
        BigDecimal dividend = cosineZenith.subtract(sinDeclinationTimesSinLat);
        BigDecimal divisor = cosineSunDeclination.multiply(cosLatitude);

        return setScale(divideBy(dividend, divisor));
    }

    private BigDecimal getSinOfSunDeclination(BigDecimal sunTrueLong) {
        BigDecimal sinTrueLongitude = BigDecimal.valueOf(Math.sin(convertDegreesToRadians(sunTrueLong).doubleValue()));
        BigDecimal sinOfDeclination = sinTrueLongitude.multiply(new BigDecimal("0.39782"));
        return setScale(sinOfDeclination);
    }

    private BigDecimal getCosineOfSunDeclination(BigDecimal sinSunDeclination) {
        BigDecimal arcSinOfSinDeclination = BigDecimal.valueOf(Math.asin(sinSunDeclination.doubleValue()));
        BigDecimal cosDeclination = BigDecimal.valueOf(Math.cos(arcSinOfSinDeclination.doubleValue()));
        return setScale(cosDeclination);
    }

    private BigDecimal getSunLocalHour(BigDecimal sunTrueLong) {
        BigDecimal arcCosineOfCosineHourAngle = getArcCosineFor(getCosineSunLocalHour(sunTrueLong));
        BigDecimal localHour = convertRadiansToDegrees(arcCosineOfCosineHourAngle);
        if (this.isSunrise) {
            localHour = BigDecimal.valueOf(360).subtract(localHour);
        }
        return divideBy(localHour, BigDecimal.valueOf(15));
    }

    private BigDecimal getLocalMeanTime(BigDecimal sunTrueLong) {
        BigDecimal rightAscension = this.getRightAscension(sunTrueLong);
        BigDecimal sunLocalHour = this.getSunLocalHour(sunTrueLong);

        BigDecimal innerParens = getLongitudeHour().multiply(new BigDecimal("0.06571"));
        BigDecimal localMeanTime = sunLocalHour.add(rightAscension).subtract(innerParens);
        localMeanTime = localMeanTime.subtract(new BigDecimal("6.622"));
        if (localMeanTime.doubleValue() < 0) {
            localMeanTime = localMeanTime.add(BigDecimal.valueOf(24));
        } else if (localMeanTime.doubleValue() > 24) {
            localMeanTime = localMeanTime.subtract(BigDecimal.valueOf(24));
        }
        return setScale(localMeanTime);
    }

    private BigDecimal getLocalTime(BigDecimal localMeanTime) {
        BigDecimal utcTime = localMeanTime.subtract(getBaseLongitudeHour());
        BigDecimal utcOffSet = getUTCOffSet();
        BigDecimal utcOffSetTime = utcTime.add(utcOffSet);
        return adjustForDST(utcOffSetTime);
    }

    private BigDecimal adjustForDST(BigDecimal localMeanTime) {
        TimeZone timeZone = this.eventDate.getTimeZone();
        if (timeZone.inDaylightTime(this.eventDate.getTime())) {
            localMeanTime = localMeanTime.add(BigDecimal.ONE);
        }
        return localMeanTime;
    }

    /**
     * Returns the local rise/set time in the form HH:MM.
     * 
     * @param localTime
     *            <code>BigDecimal</code> representation of the local rise/set time.
     * @return <code>String</code> representation of the local rise/set time in HH:MM format.
     */
    private String getLocalTimeAsString(BigDecimal localTime) {
        String[] timeComponents = localTime.toPlainString().split("\\.");
        String hour = (timeComponents[0].length() == 1) ? "0" + timeComponents[0] : timeComponents[0];

        BigDecimal minutes = new BigDecimal("0." + timeComponents[1]);
        minutes = minutes.multiply(BigDecimal.valueOf(60)).setScale(0, RoundingMode.HALF_EVEN);
        String minuteString = minutes.intValue() < 10 ? "0" + minutes.toPlainString() : minutes.toPlainString();
        return hour + ":" + minuteString;
    }

    /** ******* UTILITY METHODS (Should probably go somewhere else. ***************** */

    public BigDecimal getDayOfYear() {
        return new BigDecimal(eventDate.get(Calendar.DAY_OF_YEAR));
    }

    public BigDecimal getUTCOffSet() {
        int offSetInMillis = eventDate.get(Calendar.ZONE_OFFSET);
        BigDecimal offSet = new BigDecimal(offSetInMillis / 3600000);
        return offSet.setScale(0, RoundingMode.HALF_EVEN);
    }

    private BigDecimal getArcCosineFor(BigDecimal radians) {
        BigDecimal arcCosine = BigDecimal.valueOf(Math.acos(radians.doubleValue()));
        return setScale(arcCosine);
    }

    private BigDecimal convertRadiansToDegrees(BigDecimal radians) {
        return multiplyBy(radians, new BigDecimal(180 / Math.PI));
    }

    private BigDecimal convertDegreesToRadians(BigDecimal degrees) {
        return multiplyBy(degrees, BigDecimal.valueOf(Math.PI / 180.0));
    }

    private BigDecimal multiplyBy(BigDecimal multiplicand, BigDecimal multiplier) {
        return setScale(multiplicand.multiply(multiplier));
    }

    private BigDecimal divideBy(BigDecimal dividend, BigDecimal divisor) {
        return dividend.divide(divisor, 4, RoundingMode.HALF_EVEN);
    }

    private BigDecimal setScale(BigDecimal number) {
        return number.setScale(4, RoundingMode.HALF_EVEN);
    }
}