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
        assertTrue(getErrorMessage(dayOfYear, actualDayOfYear), dayOfYear.compareTo(actualDayOfYear) == 0);
    }

    @Test
    public void testGetUTCOffset() {
        BigDecimal utcOffSet = new BigDecimal("-5");
        BigDecimal actualUTCOffSet = calc.getUTCOffset();
        assertEquals(utcOffSet, actualUTCOffSet);

        // Roll the date into a non-DST date.
        eventDate.roll(Calendar.DATE, true);
        SunriseCalculator calc2 = new SunriseCalculator(new Location("0", "0"), 96, this.eventDate);
        actualUTCOffSet = calc2.getUTCOffset();
        assertEquals(utcOffSet, actualUTCOffSet);
    }

    @Test
    public void testGetZenithInRadians() {
        BigDecimal zenithInRads = new BigDecimal("1.6755");
        BigDecimal actualZenithInRads = calc.getZenithInRadians();
        assertTrue(getErrorMessage(zenithInRads, actualZenithInRads), zenithInRads.compareTo(actualZenithInRads) == 0);
    }

    @Test
    public void testGetBaseLongitudeHour() {
        BigDecimal baseLongHour = new BigDecimal("-5.0523");
        BigDecimal actualBaseLongHour = calc.getBaseLongitudeHour();
        assertTrue(getErrorMessage(baseLongHour, actualBaseLongHour), baseLongHour.compareTo(actualBaseLongHour) == 0);
    }

    @Test
    public void testGetArcCosineFor() {
        BigDecimal arcCosZero = new BigDecimal("1.5708");
        BigDecimal actualArcCosZero = calc.getArcCosineFor(BigDecimal.ZERO);
        assertTrue(getErrorMessage(arcCosZero, actualArcCosZero), arcCosZero.compareTo(actualArcCosZero) == 0);
    }

    @Test
    public void testConvertRadiansToDegrees() {
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal degree = calc.convertRadiansToDegrees(BigDecimal.valueOf(Math.PI / 180));
        assertTrue(getErrorMessage(one, degree), one.compareTo(degree) == 0);

    }

    @Test
    public void testConvertDegreesToRadians() {
        BigDecimal one = BigDecimal.valueOf(1);
        BigDecimal radian = calc.convertDegreesToRadians(BigDecimal.valueOf(180 / Math.PI));
        assertTrue(getErrorMessage(one, radian), one.compareTo(radian) == 0);
    }
}
