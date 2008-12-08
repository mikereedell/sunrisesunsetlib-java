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
        SunriseCalculator calc2 = new SunriseCalculator(new Location("0", "0"), 96, this.eventDate);
        actualUTCOffSet = calc2.getUTCOffSet();
        assertEquals(utcOffSet, actualUTCOffSet);
    }

    @Test
    public void testGetLongitudeHourForSunrise() {
        BigDecimal longHour = new BigDecimal("306.4605");
        BigDecimal actualLongHour = calc.getLongitudeHour(6);
        assertTrue(getMessage(longHour, actualLongHour), longHour.compareTo(actualLongHour) == 0);
    }
    
    @Test
    public void testGetLongitudeHourForSunset() {
        BigDecimal longHour = new BigDecimal("306.9605");
        BigDecimal actualLongHour = calc.getLongitudeHour(18);
        assertTrue(getMessage(longHour, actualLongHour), longHour.compareTo(actualLongHour) == 0);
    }
    
    @Test
    public void testGetSunRightAscension() {
        BigDecimal rightAscension = new BigDecimal("14.4864");
        BigDecimal actualRightAscension = calc.getRightAscension(new BigDecimal("219.6959"));
        assertTrue(getMessage(rightAscension, actualRightAscension), rightAscension.compareTo(actualRightAscension) == 0);
    }
    
    @Test
    public void testGetBaseLongitudeHour() {
        BigDecimal baseLongHour = new BigDecimal("-5.0523");
        BigDecimal actualBaseLongHour = calc.getBaseLongitudeHour();
        assertTrue(getMessage(baseLongHour, actualBaseLongHour), baseLongHour.compareTo(actualBaseLongHour) == 0);
    }

    @Test
    public void testGetSinOfSunDeclination() {
        BigDecimal sinOfSunDeclination = new BigDecimal("-0.2541");
        BigDecimal actualSinOfSunDeclination = calc.getSinOfSunDeclination(new BigDecimal("219.6959"));
        assertTrue(getMessage(sinOfSunDeclination, actualSinOfSunDeclination), sinOfSunDeclination
                .compareTo(actualSinOfSunDeclination) == 0);
    }
    
    @Test
    public void testGetCosineOfSunDeclination() {
        BigDecimal cosineOfSunDec = new BigDecimal("0.9672");
        BigDecimal actualCosineOfSunDec = calc.getCosineOfSunDeclination(new BigDecimal("-0.2541"));
        assertTrue(getMessage(cosineOfSunDec, actualCosineOfSunDec), cosineOfSunDec.compareTo(actualCosineOfSunDec) == 0);
    }
    
    @Test
    public void testGetCosineOfSunLocalHourAngle() {
        BigDecimal cosSunLocalHour = new BigDecimal("0.0793");
        BigDecimal actualCosSunLocalHour = calc.getCosineSunLocalHour(new BigDecimal("-0.2541"), new BigDecimal("0.9672"));
        assertTrue(getMessage(cosSunLocalHour, actualCosSunLocalHour), cosSunLocalHour.compareTo(actualCosSunLocalHour) == 0);
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
    public void testGetMeanAnomaly() {
        BigDecimal meanAnomaly = new BigDecimal("298.7585");
        BigDecimal actualMeanAnomaly = calc.getMeanAnomaly(new BigDecimal("306.4605"));
        assertTrue(getMessage(meanAnomaly, actualMeanAnomaly), meanAnomaly.compareTo(actualMeanAnomaly) == 0);
    }
    
    @Test
    public void testGetSunTrueLongitude() {
        BigDecimal sunTrueLong = new BigDecimal("219.6959");
        BigDecimal actualSunTrueLong = calc.getSunTrueLongitude(new BigDecimal("298.7585"));
        assertTrue(getMessage(sunTrueLong, actualSunTrueLong), sunTrueLong.compareTo(actualSunTrueLong) == 0);
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
