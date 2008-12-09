package com.reedell.sunrisesunset;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

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
}
