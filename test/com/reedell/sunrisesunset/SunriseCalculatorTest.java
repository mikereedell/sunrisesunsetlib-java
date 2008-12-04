package com.reedell.sunrisesunset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
        BigDecimal baseLongHour = new BigDecimal("-5.0523");
        BigDecimal actualBaseLongHour = calc.getBaseLongitudeHour();
        assertTrue(getErrorMessage(baseLongHour, actualBaseLongHour), baseLongHour.compareTo(actualBaseLongHour) == 0);
    }

    @Test
    public void testGetLongitudeHourForSunrise() {
        BigDecimal longHour = new BigDecimal("306.4605");
        BigDecimal actualLongHour = calc.getLongitudeHour();
        assertTrue(getErrorMessage(longHour, actualLongHour), longHour.compareTo(actualLongHour) == 0);
    }

    @Test
    public void testGetMeanAnomaly() {
        BigDecimal meanAnomaly = new BigDecimal("298.7585");
        BigDecimal actualMeanAnomaly = calc.getMeanAnomaly();
        assertTrue(getErrorMessage(meanAnomaly, actualMeanAnomaly), meanAnomaly.compareTo(actualMeanAnomaly) == 0);
    }

    @Test
    public void testGetSunTrueLongitude() {
        // If this is in degrees: 220.2133, radians: 3.8434
        BigDecimal sunTrueLong = new BigDecimal("219.6960");
        BigDecimal actualSunTrueLong = calc.getSunTrueLongitude();
        assertTrue(getErrorMessage(sunTrueLong, actualSunTrueLong), sunTrueLong.compareTo(actualSunTrueLong) == 0);
    }

    @Test
    public void testGetSunRightAscension() {
        // Degrees: 37.7803, radians: 0.6594
        BigDecimal rightAscension = new BigDecimal("37.7803");
        BigDecimal actualRightAscension = calc.getSunRightAscension();
        assertTrue(getErrorMessage(rightAscension, actualRightAscension), rightAscension.compareTo(actualRightAscension) == 0);
    }

    @Test
    public void testGetQuadrantOfRightAscension() {
        BigDecimal rightAscension = new BigDecimal("14.5187");
        BigDecimal actualRightAscension = calc.getQuadrantOfRightAscension();
        assertTrue(getErrorMessage(rightAscension, actualRightAscension), rightAscension.compareTo(actualRightAscension) == 0);
    }

    @Test
    public void testGetRightAscensionInHours() {
        BigDecimal rightAscensionInHours = new BigDecimal("14.4865");
        BigDecimal actualRightAscensionInHours = calc.getRightAscensionInHours();
        assertTrue(getErrorMessage(rightAscensionInHours, actualRightAscensionInHours), rightAscensionInHours
                .compareTo(actualRightAscensionInHours) == 0);
    }

    @Test
    public void testGetSinOfSunDeclination() {
        BigDecimal sinOfSunDeclination = new BigDecimal("-0.2541");
        BigDecimal actualSinOfSunDeclination = calc.getSinOfSunDeclination();
        assertTrue(getErrorMessage(sinOfSunDeclination, actualSinOfSunDeclination), sinOfSunDeclination
                .compareTo(actualSinOfSunDeclination) == 0);
    }

    @Test
    public void testGetCosineOfSunDeclination() {
        BigDecimal cosineOfSunDec = new BigDecimal("0.9672");
        BigDecimal actualCosineOfSunDec = calc.getCosineOfSunDeclination();
        assertTrue(getErrorMessage(cosineOfSunDec, actualCosineOfSunDec), cosineOfSunDec.compareTo(actualCosineOfSunDec) == 0);
    }

    @Test
    public void testGetCosineOfSunLocalHourAngle() {
        BigDecimal cosSunLocalHour = new BigDecimal("0.0794");
        BigDecimal actualCosSunLocalHour = calc.getCosineSunLocalHour();
        assertTrue(getErrorMessage(cosSunLocalHour, actualCosSunLocalHour), cosSunLocalHour.compareTo(actualCosSunLocalHour) == 0);
    }

    @Test
    public void testGetSunLocalHour() {
        BigDecimal localHour = new BigDecimal("18.3124");
        BigDecimal actualLocalHour = calc.getSunLocalHour();
        assertEquals(getErrorMessage(localHour, actualLocalHour), localHour.compareTo(actualLocalHour) == 0);
    }

//    @Test
//    public void testGetLocalMeanTime() {
//        BigDecimal localMeanTime = BigDecimal.valueOf();
//    }

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

    private String getErrorMessage(BigDecimal expected, BigDecimal actual) {
        return "Expected: " + expected + " but was: " + actual;
    }
}
