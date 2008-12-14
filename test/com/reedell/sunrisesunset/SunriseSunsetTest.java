package com.reedell.sunrisesunset;

import org.junit.Test;

/**
 * One-off test for a specific date/place for debugging.
 */
public class SunriseSunsetTest extends BaseTestCase {

    private SolarEventCalculator calc;

    @Test
    public void testComputeSunrise() {
        super.setup(6, 1, 2008);
        calc = new SolarEventCalculator(location, 96, this.eventDate);
        assertTimeEquals("05:06", calc.computeSunriseTime(), "07/01/2008");
        assertTimeEquals("21:08", calc.computeSunsetTime(), "07/01/2008");
    }
}
