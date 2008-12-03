package com.reedell.sunrisesunset;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class SunsetCalculatorTest {

    private Calendar sunriseDate;

    private SunsetCalculator calc;

    @Before
    public void setupCalendar() {
        // Set the date to November 1, 2008 (Day 306).
        this.sunriseDate = Calendar.getInstance();
        this.sunriseDate.set(Calendar.YEAR, 2008);
        this.sunriseDate.set(Calendar.MONTH, 10);
        this.sunriseDate.set(Calendar.DAY_OF_MONTH, 1);

        BigDecimal latitude = BigDecimal.valueOf(39.9937).setScale(4);
        BigDecimal longitude = BigDecimal.valueOf(75.7850).setScale(4);
        Location location = new Location(latitude, longitude);
        calc = new SunsetCalculator(location, 96, this.sunriseDate);
    }

    @Test
    public void testGetLongitudeHour() {
        BigDecimal longitudeHourForSunset = BigDecimal.valueOf(306.5395);
        assertEquals(longitudeHourForSunset, calc.getLongitudeHour());
    }

    @Test
    public void testGetMeanAnomaly() {
        BigDecimal meanAnomaly = new BigDecimal(298.8363);
        assertEquals(meanAnomaly, calc.getMeanAnomaly());
    }

    @Test
    public void testGetSunTrueLongitude() {
        BigDecimal sunTrueLongitude = new BigDecimal(222.1958);
        assertEquals(sunTrueLongitude, calc.getSunTrueLongitude());
    }

    @Test
    public void testGetSunRightAscension() {
        BigDecimal rightAscension = new BigDecimal(39.7558);
        assertEquals(rightAscension, calc.getSunRightAscension());
    }

    @Test
    public void testGetQuadrantOfRightAscension() {
        BigDecimal rightAscension = BigDecimal.valueOf(219.7558);
        assertEquals(rightAscension, calc.setQuadrantOfRightAscension());
    }

    @Test
    public void testGetRightAscensionInHours() {
        BigDecimal rightAscensionInHours = BigDecimal.valueOf(14.6504);
        assertEquals(rightAscensionInHours, calc.getRightAscensionInHours());
    }

    @Test
    public void testGetSinOfSunDeclination() {
        BigDecimal sinOfSunDeclinationInDegrees = BigDecimal.valueOf(-0.2568);
        assertEquals(sinOfSunDeclinationInDegrees, calc.getSinOfSunDeclination());
    }

    @Test
    public void testGetCosineOfSunDeclination() {
        BigDecimal cosineOfSunDeclinationInDegrees = BigDecimal.valueOf(0.9665);
        assertEquals(cosineOfSunDeclinationInDegrees, calc.getCosineOfSunDeclination());
    }

    @Test
    public void testGetCosineOfSunLocalHourAngle() {
        BigDecimal cosineOfSunLocalHourAngleInDegrees = BigDecimal.valueOf(.0817);
        assertEquals(cosineOfSunLocalHourAngleInDegrees, calc.getCosineSunLocalHour());
    }

    @Test
    public void testGetSunLocalHour() {
        BigDecimal localHourForSettingTime = BigDecimal.valueOf(5.6875);
        assertEquals(localHourForSettingTime, calc.getSunLocalHour());
    }
}
