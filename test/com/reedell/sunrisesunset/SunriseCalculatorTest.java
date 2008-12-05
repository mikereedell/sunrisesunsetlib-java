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
        // If this is in degrees: 219.6960
        BigDecimal sunTrueLong = new BigDecimal("219.6960");
        BigDecimal actualSunTrueLong = calc.getSunTrueLongitude();
        assertTrue(getErrorMessage(sunTrueLong, actualSunTrueLong), sunTrueLong.compareTo(actualSunTrueLong) == 0);
    }

    @Test
    public void testGetSunRightAscension() {
        BigDecimal rightAscension = new BigDecimal("37.2977");
        BigDecimal actualRightAscension = calc.getSunRightAscension();
        assertTrue(getErrorMessage(rightAscension, actualRightAscension), rightAscension.compareTo(actualRightAscension) == 0);
    }

    @Test
    public void testGetQuadrantOfRightAscension() {
        BigDecimal rightAscension = new BigDecimal("217.2977");
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
        BigDecimal cosSunLocalHour = new BigDecimal("0.0793");
        BigDecimal actualCosSunLocalHour = calc.getCosineSunLocalHour();
        assertTrue(getErrorMessage(cosSunLocalHour, actualCosSunLocalHour), cosSunLocalHour.compareTo(actualCosSunLocalHour) == 0);
    }

    @Test
    public void testGetSunLocalHour() {
        BigDecimal localHour = new BigDecimal("18.3032");
        BigDecimal actualLocalHour = calc.getSunLocalHour();
        assertTrue(getErrorMessage(localHour, actualLocalHour), localHour.compareTo(actualLocalHour) == 0);
    }

    @Test
    public void testGetLocalMeanTime() {
        BigDecimal localMeanTime = new BigDecimal("6.0302");
        BigDecimal actualLocalMeanTime = calc.getLocalMeanTime();
        assertTrue(getErrorMessage(localMeanTime, actualLocalMeanTime), localMeanTime.compareTo(actualLocalMeanTime) == 0);
    }

    @Test
    public void testGetUTCTime() {
        BigDecimal utcTime = new BigDecimal("11.0825");
        BigDecimal actualUTCTime = calc.getUTCTime();
        assertTrue(getErrorMessage(utcTime, actualUTCTime), utcTime.compareTo(actualUTCTime) == 0);
    }

    @Test
    public void testGetLocalTime() {
        BigDecimal localTime = new BigDecimal("6.0825");
        BigDecimal actualLocalTime = calc.getLocalTime();
        assertTrue(getErrorMessage(localTime, actualLocalTime), localTime.compareTo(actualLocalTime) == 0);
    }

    public void testGetLocalTimeAsString() {
        String localTime = "06:05";
        String actualLocalTime = calc.getLocalTimeAsString();
        assertEquals(getErrorMessage(localTime, actualLocalTime), localTime, actualLocalTime);

    }

    private String getErrorMessage(Object expected, Object actual) {
        return "Expected: " + expected + " but was: " + actual;
    }

    @Test
    public void testGetUTCOffSet() {
        BigDecimal utcOffSet = new BigDecimal("-5");
        BigDecimal actualUTCOffSet = calc.getUTCOffset();
        assertTrue(getErrorMessage(utcOffSet, actualUTCOffSet), utcOffSet.compareTo(actualUTCOffSet) == 0);

        // Roll the date into a non-DST date.
        sunriseDate.roll(Calendar.DATE, true);
        SunriseCalculator calc2 = new SunriseCalculator(new Location("0", "0"), 96, this.sunriseDate);
        actualUTCOffSet = calc.getUTCOffset();
        assertTrue(getErrorMessage(utcOffSet, actualUTCOffSet), utcOffSet.compareTo(actualUTCOffSet) == 0);
    }

    @Test
    public void testInDST() {
        System.out.println(sunriseDate.getTimeZone().inDaylightTime(sunriseDate.getTime()));
        System.out.println(sunriseDate.get(Calendar.ZONE_OFFSET));
        sunriseDate.roll(Calendar.DATE, true);
        System.out.println(sunriseDate.getTimeZone().inDaylightTime(sunriseDate.getTime()));
        System.out.println(sunriseDate.get(Calendar.ZONE_OFFSET));
    }
}
