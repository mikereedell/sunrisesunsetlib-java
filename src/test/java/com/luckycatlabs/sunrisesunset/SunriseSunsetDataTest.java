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

package com.luckycatlabs.sunrisesunset;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.luckycatlabs.sunrisesunset.dto.Location;
import com.luckycatlabs.sunrisesunset.util.BaseTestCase;
import com.luckycatlabs.sunrisesunset.util.CSVTestDriver;

public class SunriseSunsetDataTest extends BaseTestCase {
    private static CSVTestDriver driver;
    private static String[] dataSetNames; // The lat/long will be encoded in the filename.

    @BeforeClass
    public static void setupAllTests() {
        driver = new CSVTestDriver("testdata");
        dataSetNames = driver.getFileNames();
    }

    @AfterClass
    public static void tearDownAllTests() {
        TimeZone.setDefault(TimeZone.getTimeZone("America/New_York"));
    }

    @Test
    public void testRiseAndSetTimes() {
        for (String dataSetName : dataSetNames) {
            List<String[]> data = driver.getData(dataSetName);
            String[] dataSetNameParts = dataSetName.split("\\#");
            String timeZoneName = dataSetNameParts[1].split("\\.")[0].replace('-', '/');
            location = createLocation(dataSetNameParts[0]);

            for (String[] line : data) {
                String date = line[0];
                Calendar calendar = createCalendar(date.split("\\/"));
                SunriseSunsetCalculator calc = new SunriseSunsetCalculator(location, timeZoneName);

                assertTimeEquals(line[1], calc.getAstronomicalSunriseForDate(calendar), date);
                assertTimeEquals(line[8], calc.getAstronomicalSunsetForDate(calendar), date);
                assertTimeEquals(line[2], calc.getNauticalSunriseForDate(calendar), date);
                assertTimeEquals(line[7], calc.getNauticalSunsetForDate(calendar), date);
                assertTimeEquals(line[4], calc.getOfficialSunriseForDate(calendar), date);
                assertTimeEquals(line[5], calc.getOfficialSunsetForDate(calendar), date);
                assertTimeEquals(line[3], calc.getCivilSunriseForDate(calendar), date);
                assertTimeEquals(line[6], calc.getCivilSunsetForDate(calendar), date);
            }
        }
    }

    private Calendar createCalendar(String[] dateParts) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(dateParts[2]), Integer.valueOf(dateParts[0]) - 1, Integer.valueOf(dateParts[1]));
        return cal;
    }

    private Location createLocation(String fileName) {
        String[] latlong = fileName.split("\\-");
        String latitude = latlong[0].replace('_', '.');
        String longitude = latlong[1].replace('_', '.');

        if (latitude.endsWith("S")) {
            latitude = "-" + latitude;
        }
        if (longitude.endsWith("W")) {
            longitude = "-" + longitude;
        }
        latitude = latitude.substring(0, latitude.length() - 1);
        longitude = longitude.substring(0, longitude.length() - 1);
        return new Location(latitude, longitude);
    }
}
