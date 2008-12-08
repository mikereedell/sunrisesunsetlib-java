package com.reedell.sunrisesunset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class SunsetCalculatorTest extends BaseTestCase {

    private Calendar sunriseDate;

    private SunsetCalculator calc;

    @Before
    public void setupCalculator() {
        this.setup();
        calc = new SunsetCalculator(location, 96, eventDate);
    }

    @Test
    public void testGetLongitudeHour() {
        BigDecimal longHour = new BigDecimal("306.9605");
        BigDecimal actualLongHour = calc.getLongitudeHour();
        assertTrue(getMessage(longHour, actualLongHour), longHour.compareTo(actualLongHour) == 0);
    }

    @Test
    public void testGetMeanAnomaly() {
        BigDecimal meanAnomaly = new BigDecimal("299.2513");
        BigDecimal actualMeanAnomaly = calc.getMeanAnomaly();
        assertTrue(getMessage(meanAnomaly, actualMeanAnomaly), meanAnomaly.compareTo(actualMeanAnomaly) == 0);
    }

    @Test
    public void testGetSunTrueLongitude() {
        BigDecimal sunTrueLong = new BigDecimal("220.1965");
        BigDecimal actualSunTrueLong = calc.getSunTrueLongitude();
        assertTrue(getMessage(sunTrueLong, actualSunTrueLong), sunTrueLong.compareTo(actualSunTrueLong) == 0);
    }

    @Test
    public void testGetSunRightAscension() {
        BigDecimal rightAscension = new BigDecimal("37.7900");
        BigDecimal actualRightAscension = calc.getSunRightAscension();
        assertTrue(getMessage(rightAscension, actualRightAscension), rightAscension.compareTo(actualRightAscension) == 0);
    }

    @Test
    public void testGetQuadrantOfRightAscension() {
        BigDecimal rightAscension = new BigDecimal("217.7900");
        BigDecimal actualRightAscension = calc.getQuadrantOfRightAscension();
        assertTrue(getMessage(rightAscension, actualRightAscension), rightAscension.compareTo(actualRightAscension) == 0);
    }

    @Test
    public void testGetRightAscensionInHours() {
        BigDecimal raInHours = new BigDecimal("14.5193");
        BigDecimal actualRAInHours = calc.getRightAscensionInHours();
        assertTrue(getMessage(raInHours, actualRAInHours), raInHours.compareTo(actualRAInHours) == 0);
    }

    @Test
    public void testGetSinOfSunDeclination() {
        BigDecimal sinOfSunDec = new BigDecimal("-0.2568");
        BigDecimal actualSinOfSunDec = calc.getSinOfSunDeclination();
        assertTrue(getMessage(sinOfSunDec, actualSinOfSunDec), sinOfSunDec.compareTo(actualSinOfSunDec) == 0);
    }

    @Test
    public void testGetCosineOfSunDeclination() {
        BigDecimal cosineOfSunDec = new BigDecimal("0.9665");
        BigDecimal actualCosineOfSunDec = calc.getCosineOfSunDeclination();
        assertTrue(getMessage(cosineOfSunDec, actualCosineOfSunDec), cosineOfSunDec.compareTo(actualCosineOfSunDec) == 0);
    }

    @Test
    public void testGetCosineOfSunLocalHourAngle() {
        BigDecimal cosineSunLocalHour = new BigDecimal("0.0817");
        BigDecimal actualCosineSunLocalHour = calc.getCosineSunLocalHour();
        assertTrue(getMessage(cosineSunLocalHour, actualCosineSunLocalHour), cosineSunLocalHour
                .compareTo(actualCosineSunLocalHour) == 0);
    }

    @Test
    public void testGetSunLocalHour() {
        BigDecimal localHour = new BigDecimal("5.6876");
        BigDecimal actualLocalHour = calc.getSunLocalHour();
        assertTrue(getMessage(localHour, actualLocalHour), localHour.compareTo(actualLocalHour) == 0);
    }

    @Test
    public void testGetLocalMeanTime() {
        BigDecimal localMeanTime = new BigDecimal("-6.5855");
        BigDecimal actualLocalMeanTime = calc.getLocalMeanTime();
        assertTrue(getMessage(localMeanTime, actualLocalMeanTime), localMeanTime.compareTo(actualLocalMeanTime) == 0);
    }

    @Test
    public void testGetUTCTime() {
        BigDecimal utcTime = new BigDecimal("22.4668");
        BigDecimal actualUTCTime = calc.getUTCTime();
        assertTrue(getMessage(utcTime, actualUTCTime), utcTime.compareTo(actualUTCTime) == 0);
    }

    @Test
    public void testGetLocalTime() {
        BigDecimal localTime = new BigDecimal("17.4668");
        BigDecimal actualLocalTime = calc.getLocalTime();
        assertTrue(getMessage(localTime, actualLocalTime), localTime.compareTo(actualLocalTime) == 0);
    }

    @Test
    public void testGetLocalTimeAsString() {
        String localTime = "17:28";
        String actualLocalTime = calc.getLocalTimeAsString();
        assertEquals(localTime, actualLocalTime);
    }
}
