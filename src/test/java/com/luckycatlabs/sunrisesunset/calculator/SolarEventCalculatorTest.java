/*
 * Copyright 2008-2009 Mike Reedell / LuckyCatLabs.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.luckycatlabs.sunrisesunset.calculator;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;

import com.luckycatlabs.sunrisesunset.Zenith;
import com.luckycatlabs.sunrisesunset.util.BaseTestCase;

public class SolarEventCalculatorTest extends BaseTestCase {

    private SolarEventCalculator calc;

    @Before
    public void setupCalculator() {
        super.setup(10, 1, 2008);
        calc = new SolarEventCalculator(location, "America/New_York");
    }

    @Test
    public void testComputeSunriseTime() {
        String localSunriseTime = "07:05";
        assertEquals(localSunriseTime, calc.computeSunriseTime(Zenith.CIVIL, eventDate));
    }

    @Test
    public void testComputeSunsetTime() {
        String localSunsetTime = "18:28";
        assertEquals(localSunsetTime, calc.computeSunsetTime(Zenith.CIVIL, eventDate));
    }

    @Test
    public void testGetLocalTimeAsCalendar() {
        Calendar localTime = calc.getLocalTimeAsCalendar(BigDecimal.valueOf(15.5D), Calendar.getInstance());
        assertEquals(15, localTime.get(Calendar.HOUR_OF_DAY));
        assertEquals(30, localTime.get(Calendar.MINUTE));
    }

    @Test
    public void testGetLocalTimeAsCalendarForZero() {
        Calendar localTime = calc.getLocalTimeAsCalendar(BigDecimal.valueOf(0.0D), Calendar.getInstance());
        assertEquals(0, localTime.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, localTime.get(Calendar.MINUTE));
    }

    @Test
    public void testGetLocalTimeAsCalendarForNegative() {
        Calendar localTime = calc.getLocalTimeAsCalendar(BigDecimal.valueOf(-10.0D), Calendar.getInstance());
        assertEquals(14, localTime.get(Calendar.HOUR_OF_DAY));
        assertEquals(0, localTime.get(Calendar.MINUTE));
    }
}
