package com.reedell.sunrisesunset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

public class SolarEventCalculatorTest extends BaseTestCase {

    private SolarEventCalculator calc;

    @Before
    public void setupCalculator() {
        super.setup();
        calc = new SolarEventCalculator(location, 96, this.eventDate);
    }

    @Test
    public void testGetDayOfYear() {
        BigDecimal dayOfYear = BigDecimal.valueOf(306);
        BigDecimal actualDayOfYear = calc.getDayOfYear();
        assertTrue(getMessage(dayOfYear, actualDayOfYear), dayOfYear.compareTo(actualDayOfYear) == 0);
    }

    @Test
    public void testGetUTCOffset() {
        BigDecimal utcOffSet = new BigDecimal("-5");
        BigDecimal actualUTCOffSet = calc.getUTCOffSet();
        assertEquals(utcOffSet, actualUTCOffSet);

        // Roll the date into a non-DST date.
        eventDate.roll(Calendar.DATE, true);
        SolarEventCalculator calc2 = new SolarEventCalculator(new Location("0", "0"), 96, this.eventDate);
        actualUTCOffSet = calc2.getUTCOffSet();
        assertEquals(utcOffSet, actualUTCOffSet);
    }

    @Test
    public void testGetBaseLongitudeHour() {
        BigDecimal baseLongHour = new BigDecimal("-5.0523");
        BigDecimal actualBaseLongHour = calc.getBaseLongitudeHour();
        assertTrue(getMessage(baseLongHour, actualBaseLongHour), baseLongHour.compareTo(actualBaseLongHour) == 0);
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
        BigDecimal actualSunTrueLong = calc.getSunTrueLongitude(new BigDecimal("298.7585"));
        assertTrue(getMessage(sunTrueLong, actualSunTrueLong), sunTrueLong.compareTo(actualSunTrueLong) == 0);
    }

    @Test
    public void testGetLocalMeanTime() {
        BigDecimal localMeanTime = new BigDecimal("6.0302");
        BigDecimal actualLocalMeanTime = calc.getLocalMeanTime(new BigDecimal("219.6959"));
        assertTrue(getMessage(localMeanTime, actualLocalMeanTime), localMeanTime.compareTo(actualLocalMeanTime) == 0);
    }

    @Test
    public void testGetLocalTime() {
        BigDecimal localTime = new BigDecimal("6.0825");
        BigDecimal actualLocalTime = calc.getLocalTime(new BigDecimal("6.0302"));
        assertTrue(getMessage(localTime, actualLocalTime), localTime.compareTo(actualLocalTime) == 0);
    }

    @Test
    public void testGetLocalTimeAsString() {
        String localTime = "06:05";
        String actualLocalTime = calc.getLocalTimeAsString(new BigDecimal("6.0825"));
        assertEquals(localTime, actualLocalTime);
    }

    @Test
    public void testComputeSunriseTime() {
        String localSunriseTime = "06:05";
        assertEquals(localSunriseTime, calc.computeSunriseTime());
    }

    @Test
    public void testComputeSunsetTime() {
        String localSunsetTime = "17:28";
        assertEquals(localSunsetTime, calc.computeSunsetTime());
    }

    @Test
    public void testGetArcCosineFor() {
        BigDecimal arcCosZero = new BigDecimal("1.5708");
        BigDecimal actualArcCosZero = calc.getArcCosineFor(BigDecimal.ZERO);
        assertTrue(getMessage(arcCosZero, actualArcCosZero), arcCosZero.compareTo(actualArcCosZero) == 0);
    }

    @Test
    public void testConvertRadiansToDegrees() {
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal degree = calc.convertRadiansToDegrees(BigDecimal.valueOf(Math.PI / 180));
        assertTrue(getMessage(one, degree), one.compareTo(degree) == 0);

    }

    @Test
    public void testConvertDegreesToRadians() {
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal radian = calc.convertDegreesToRadians(BigDecimal.valueOf(180 / Math.PI));
        assertTrue(getMessage(one, radian), one.compareTo(radian) == 0);
    }
}
