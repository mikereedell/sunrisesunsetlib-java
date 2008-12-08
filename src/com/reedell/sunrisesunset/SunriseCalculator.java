package com.reedell.sunrisesunset;

import java.math.BigDecimal;
import java.util.Calendar;

/**
 * 
 */
public class SunriseCalculator extends SolarEventCalculator {

    public SunriseCalculator(Location location, Integer zenith) {
        super(location, zenith, Calendar.getInstance());
    }

    public SunriseCalculator(Location location, Integer zenith, Calendar sunriseDate) {
        super(location, zenith, sunriseDate);
    }

    public BigDecimal computeSunrise() {
        BigDecimal lngHour = getLongitudeHour(6);
        BigDecimal meanAnomaly = getMeanAnomaly(lngHour);
        BigDecimal sunTrueLong = getSunTrueLongitude(meanAnomaly);
        BigDecimal rightAscension = getRightAscension(sunTrueLong);
        BigDecimal sinSunDeclination = getSinOfSunDeclination(sunTrueLong);
        BigDecimal cosineSunDeclination = getCosineOfSunDeclination(sinSunDeclination);
        BigDecimal cosineSunLocalHour = getCosineSunLocalHour(sinSunDeclination, cosineSunDeclination);
        BigDecimal sunLocalHour = getSunLocalHour(cosineSunLocalHour);
        BigDecimal localMeanTime = getLocalMeanTime(lngHour, rightAscension, sunLocalHour);
        BigDecimal utcTime = getUTCTime(localMeanTime);
        BigDecimal localTime = getLocalTime(utcTime);
        return localTime;
    }

    public String computeSunriseTime() {
        return getLocalTimeAsString(computeSunrise());
    }

    protected BigDecimal getLongitudeHour() {
        return this.getLongitudeHour(6);
    }

    protected BigDecimal getMeanAnomaly() {
        return getMeanAnomaly(getLongitudeHour());
    }

    protected BigDecimal getSunTrueLongitude() {
        return getSunTrueLongitude(getMeanAnomaly());
    }

    protected BigDecimal getRightAscension() {
        return getRightAscension(getSunTrueLongitude());
    }

    protected BigDecimal getSinOfSunDeclination() {
        return this.getSinOfSunDeclination(getSunTrueLongitude());
    }

    protected BigDecimal getCosineOfSunDeclination() {
        return getCosineOfSunDeclination(getSinOfSunDeclination());
    }

    protected BigDecimal getCosineSunLocalHour() {
        return getCosineSunLocalHour(getSinOfSunDeclination(), getCosineOfSunDeclination());
    }

    protected BigDecimal getSunLocalHour() {
        BigDecimal arcCosineOfCosineHourAngle = getArcCosineFor(getCosineSunLocalHour());
        BigDecimal localHour = BigDecimal.valueOf(360).subtract(convertRadiansToDegrees(arcCosineOfCosineHourAngle));
        return divideBy(localHour, BigDecimal.valueOf(15));
    }

    protected BigDecimal getSunLocalHour(BigDecimal cosineSunLocalHour) {
        BigDecimal arcCosineOfCosineHourAngle = getArcCosineFor(cosineSunLocalHour);
        BigDecimal localHour = BigDecimal.valueOf(360).subtract(convertRadiansToDegrees(arcCosineOfCosineHourAngle));
        return divideBy(localHour, BigDecimal.valueOf(15));
    }

    protected BigDecimal getLocalMeanTime() {
        return getLocalMeanTime(getLongitudeHour(), getRightAscension(), getSunLocalHour());
    }

    protected BigDecimal getUTCTime() {
        return getUTCTime(getLocalMeanTime());
    }

    protected BigDecimal getLocalTime() {
        return getLocalTime(getUTCTime());
    }

    protected String getLocalTimeAsString() {
        return getLocalTimeAsString(getLocalTime());
    }
}
