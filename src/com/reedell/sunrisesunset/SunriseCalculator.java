package com.reedell.sunrisesunset;

import java.math.BigDecimal;
import java.util.Calendar;

public class SunriseCalculator extends SolarEventCalculator {

    public SunriseCalculator(Location location, Integer zenith) {
        super(location, zenith, Calendar.getInstance());
    }

    public SunriseCalculator(Location location, Integer zenith, Calendar sunriseDate) {
        super(location, zenith, sunriseDate);
    }

    public String computeSunriseTime() {
        return getLocalTimeAsString(computeSunrise());
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
        BigDecimal localTime = getLocalTime(localMeanTime);
        return localTime;
    }

    protected BigDecimal getSunLocalHour(BigDecimal cosineSunLocalHour) {
        BigDecimal arcCosineOfCosineHourAngle = getArcCosineFor(cosineSunLocalHour);
        BigDecimal localHour = BigDecimal.valueOf(360).subtract(convertRadiansToDegrees(arcCosineOfCosineHourAngle));
        return divideBy(localHour, BigDecimal.valueOf(15));
    }

    /** possible hingepoint for a call into the SunriseCalculator from SolarEventCalculator? */
    protected BigDecimal getLocalMeanTime() {
        return getLocalMeanTime(getLongitudeHour(6), new BigDecimal("14.4864"), getSunLocalHour(new BigDecimal("0.0793")));
    }
}
