package com.reedell.sunrisesunset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

/**
 * 
 */
public class SunriseCalculatorTest extends BaseTestCase {

    private SunriseCalculator calc;

    @Before
    public void setupCalculator() {
        this.setup();
        calc = new SunriseCalculator(location, 96, eventDate);
    }

    @Test
    public void testComputeSunrise() {
        BigDecimal localSunrise = new BigDecimal("6.0825");
        BigDecimal actualLocalSunrise = calc.computeSunrise();
        assertTrue(getMessage(localSunrise, actualLocalSunrise), localSunrise.compareTo(actualLocalSunrise) == 0);
    }

    @Test
    public void testComputeSunriseTime() {
        String localSunriseTime = "06:05";
        assertEquals(localSunriseTime, calc.computeSunriseTime());
    }

    @Test
    public void testGetLongitudeHourForSunrise() {
        BigDecimal longHour = new BigDecimal("306.4605");
        BigDecimal actualLongHour = calc.getLongitudeHour();
        assertTrue(getMessage(longHour, actualLongHour), longHour.compareTo(actualLongHour) == 0);
    }

    @Test
    public void testGetMeanAnomaly() {
        BigDecimal meanAnomaly = new BigDecimal("298.7585");
        BigDecimal actualMeanAnomaly = calc.getMeanAnomaly();
        assertTrue(getMessage(meanAnomaly, actualMeanAnomaly), meanAnomaly.compareTo(actualMeanAnomaly) == 0);
    }

    @Test
    public void testGetSunTrueLongitude() {
        BigDecimal sunTrueLong = new BigDecimal("219.6959");
        BigDecimal actualSunTrueLong = calc.getSunTrueLongitude();
        assertTrue(getMessage(sunTrueLong, actualSunTrueLong), sunTrueLong.compareTo(actualSunTrueLong) == 0);
    }

    @Test
    public void testGetSunRightAscension() {
        BigDecimal rightAscension = new BigDecimal("14.4864");
        BigDecimal actualRightAscension = calc.getRightAscension();
        assertTrue(getMessage(rightAscension, actualRightAscension), rightAscension.compareTo(actualRightAscension) == 0);
    }

    @Test
    public void testGetSinOfSunDeclination() {
        BigDecimal sinOfSunDeclination = new BigDecimal("-0.2541");
        BigDecimal actualSinOfSunDeclination = calc.getSinOfSunDeclination();
        assertTrue(getMessage(sinOfSunDeclination, actualSinOfSunDeclination), sinOfSunDeclination
                .compareTo(actualSinOfSunDeclination) == 0);
    }

    @Test
    public void testGetCosineOfSunDeclination() {
        BigDecimal cosineOfSunDec = new BigDecimal("0.9672");
        BigDecimal actualCosineOfSunDec = calc.getCosineOfSunDeclination();
        assertTrue(getMessage(cosineOfSunDec, actualCosineOfSunDec), cosineOfSunDec.compareTo(actualCosineOfSunDec) == 0);
    }

    @Test
    public void testGetCosineOfSunLocalHourAngle() {
        BigDecimal cosSunLocalHour = new BigDecimal("0.0793");
        BigDecimal actualCosSunLocalHour = calc.getCosineSunLocalHour();
        assertTrue(getMessage(cosSunLocalHour, actualCosSunLocalHour), cosSunLocalHour.compareTo(actualCosSunLocalHour) == 0);
    }

    @Test
    public void testGetSunLocalHour() {
        BigDecimal localHour = new BigDecimal("18.3033");
        BigDecimal actualLocalHour = calc.getSunLocalHour();
        assertTrue(getMessage(localHour, actualLocalHour), localHour.compareTo(actualLocalHour) == 0);
    }

    @Test
    public void testGetLocalMeanTime() {
        BigDecimal localMeanTime = new BigDecimal("6.0302");
        BigDecimal actualLocalMeanTime = calc.getLocalMeanTime();
        assertTrue(getMessage(localMeanTime, actualLocalMeanTime), localMeanTime.compareTo(actualLocalMeanTime) == 0);
    }

    @Test
    public void testGetUTCTime() {
        BigDecimal utcTime = new BigDecimal("11.0825");
        BigDecimal actualUTCTime = calc.getUTCTime();
        assertTrue(getMessage(utcTime, actualUTCTime), utcTime.compareTo(actualUTCTime) == 0);
    }

    @Test
    public void testGetLocalTime() {
        BigDecimal localTime = new BigDecimal("6.0825");
        BigDecimal actualLocalTime = calc.getLocalTime();
        assertTrue(getMessage(localTime, actualLocalTime), localTime.compareTo(actualLocalTime) == 0);
    }

    @Test
    public void testGetLocalTimeAsString() {
        String localTime = "06:05";
        String actualLocalTime = calc.getLocalTimeAsString();
        assertEquals(localTime, actualLocalTime);
    }
}
