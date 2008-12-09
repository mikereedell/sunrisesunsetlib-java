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

        BigDecimal localMeanTime = getLocalMeanTime(sunTrueLong, true);
        BigDecimal localTime = getLocalTime(localMeanTime);
        return localTime;
    }
}
