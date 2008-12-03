package com.reedell.sunrisesunset;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

/**
 *
 */
public class SunriseCalculatorTest {

    private Calendar sunriseDate;

    private SunriseCalculator calc;

    @Before
    public void setupCalendar() {
        // Set the date to November 1, 2008 (Day 306).
        this.sunriseDate = Calendar.getInstance();
        this.sunriseDate.set(Calendar.YEAR, 2008);
        this.sunriseDate.set(Calendar.MONTH, 10);
        this.sunriseDate.set(Calendar.DAY_OF_MONTH, 1);
        // At a zenith of 96 Degrees (Civil) (AKA 'Twilight')
        // Sunrise is 0604 EDT, sunset is 1729 EDT

        BigDecimal latitude = BigDecimal.valueOf(39.9937).setScale(4);
        BigDecimal longitude = BigDecimal.valueOf(-75.7850).setScale(4);
        Location location = new Location(latitude, longitude);
        calc = new SunriseCalculator(location, 96, this.sunriseDate);
    }

    @Test
    public void testGetBaseLongitudeHour() {
        assertEquals(-5.0523, calc.getBaseLongitudeHour().doubleValue(), 0.0);
    }

    @Test
    public void testGetLongitudeHourForSunrise() {
        assertEquals(306.4605, calc.getLongitudeHour().doubleValue(), 0.0);
    }

    @Test
    public void testGetMeanAnomaly() {
        BigDecimal meanAnomaly = new BigDecimal(298.7585);
        assertEquals(meanAnomaly, calc.getMeanAnomaly());
    }

    @Test
    public void testGetSunTrueLongitude() {
        // If this is in degrees: 220.2133, radians: 3.8434
        BigDecimal sunTrueLongitude = new BigDecimal(219.6959, SolarEventCalculator.MATH_CONTEXT);
        System.out.println(sunTrueLongitude);
        System.out.println(calc.getSunTrueLongitude());
        assertEquals(sunTrueLongitude, calc.getSunTrueLongitude());
    }

    @Test
    public void testGetSunRightAscension() {
        // Degrees: 37.7803, radians: 0.6594
        BigDecimal rightAscension = new BigDecimal(37.7803);
        assertEquals(rightAscension, calc.getSunRightAscension());
    }

    @Test
    public void testGetQuadrantOfRightAscension() {
        BigDecimal rightAscension = BigDecimal.valueOf(14.5187);
        assertEquals(rightAscension, calc.setQuadrantOfRightAscension());
    }

    @Test
    public void testGetRightAscensionInHours() {
        BigDecimal rightAscensionInHours = BigDecimal.valueOf(14.4865);
        assertEquals(rightAscensionInHours, calc.getRightAscensionInHours());
    }

    @Test
    public void testGetSinOfSunDeclination() {
        BigDecimal sinOfSunDeclinationInDegrees = BigDecimal.valueOf(-0.2541);
        assertEquals(sinOfSunDeclinationInDegrees, calc.getSinOfSunDeclination());
    }

    @Test
    public void testGetCosineOfSunDeclination() {
        BigDecimal cosineOfSunDeclinationInDegrees = BigDecimal.valueOf(0.9672);
        assertEquals(cosineOfSunDeclinationInDegrees, calc.getCosineOfSunDeclination());
    }

    @Test
    public void testGetCosineOfSunLocalHourAngle() {
        assertEquals(0.0794, calc.getCosineSunLocalHour().doubleValue(), 0.0);
    }

    @Test
    public void testGetSunLocalHour() {
        BigDecimal localHour = BigDecimal.valueOf(18.3124);
        assertEquals(localHour, calc.getSunLocalHour());
    }

    // @Test
    // public void testGetLocalMeanTime() {
    // BigDecimal localMeanTime = BigDecimal.valueOf();
    // }

    @Test
    public void testGetUTCTime() {
        BigDecimal utcTime = BigDecimal.valueOf(11.4961);
        assertEquals(utcTime, calc.getUTCTime());
    }

    @Test
    public void testGetLocalTime() {
        BigDecimal localTime = BigDecimal.valueOf(6.4961);
        assertEquals(localTime, calc.getLocalTime());
    }

    @Test
    public void testSinFunction() {
        System.out.println(Math.sin(50.0));
        System.out.println(Math.sin((50.0 * Math.PI) / 180.0));
    }
}
