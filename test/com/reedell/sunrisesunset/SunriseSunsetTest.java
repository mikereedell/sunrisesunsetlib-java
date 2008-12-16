package com.reedell.sunrisesunset;

import org.junit.Before;
import org.junit.Test;

import com.reedell.sunrisesunset.util.BaseTestCase;

/**
 * Unit test for the SunriseSunsetCalculator class.
 */
public class SunriseSunsetTest extends BaseTestCase {

    private SunriseSunsetCalculator calc;

    @Before
    public void setup() {
        super.setup(6, 1, 2008);
        calc = new SunriseSunsetCalculator(location);
    }

    @Test
    public void testComputeCivilSunrise() {
        assertTimeEquals("05:06", calc.getCivilSunriseForDate(eventDate), eventDate.getTime().toString());
    }

    @Test
    public void testComputeCivilSunset() {
        assertTimeEquals("21:08", calc.getCivilSunsetForDate(eventDate), eventDate.getTime().toString());
    }

    @Test
    public void testComputeAstronomicalSunrise() {
        assertTimeEquals("03:36", calc.getAstronomicalSunriseForDate(eventDate), eventDate.getTime().toString());
    }

    @Test
    public void testComputeAstronomicalSunset() {
        assertTimeEquals("22:38", calc.getAstronomicalSunsetForDate(eventDate), eventDate.getTime().toString());
    }

    @Test
    public void testComputeNauticalSunrise() {
        assertTimeEquals("04:24", calc.getNauticalSunriseForDate(eventDate), eventDate.getTime().toString());
    }

    @Test
    public void testComputeNauticalSunset() {
        assertTimeEquals("21:50", calc.getNauticalSunsetForDate(eventDate), eventDate.getTime().toString());
    }

    @Test
    public void testComputeOfficialSunrise() {
        assertTimeEquals("05:38", calc.getOfficalSunriseForDate(eventDate), eventDate.getTime().toString());
    }

    @Test
    public void testComputeOfficialSunset() {
        assertTimeEquals("20:36", calc.getOfficialSunsetForDate(eventDate), eventDate.getTime().toString());
    }
}
