package com.reedell.sunrisesunset;

import java.util.Calendar;
import java.util.List;

import org.junit.BeforeClass;
import org.junit.Test;

import com.reedell.sunrisesunset.dto.Location;
import com.reedell.sunrisesunset.util.BaseTestCase;
import com.reedell.sunrisesunset.util.CSVTestDriver;

public class SunriseSunsetDataTest extends BaseTestCase {

    private static List<String[]> data;

    @BeforeClass
    public static void setupAllTests() {
        CSVTestDriver driver = new CSVTestDriver("testdata/SunriseSunsetTestData19320.txt");
        data = driver.getData();
    }

    @Test
    public void testRiseAndSetTimes() {
        for (String[] line : data) {
            String date = line[0];
            String astroRiseTime = line[1];
            String nauticalRiseTime = line[2];
            String civilRiseTime = line[3];
            String officialRiseTime = line[4];
            String officialSetTime = line[5];
            String civilSetTime = line[6];
            String nauticalSetTime = line[7];
            String astroSetTime = line[8];

            Calendar calendar = createCalendar(date.split("\\/"));
            SunriseSunsetCalculator calc = new SunriseSunsetCalculator(createLocation());

            assertTimeEquals(astroRiseTime, calc.getAstronomicalSunriseForDate(calendar), date);
            assertTimeEquals(astroSetTime, calc.getAstronomicalSunsetForDate(calendar), date);
            assertTimeEquals(nauticalRiseTime, calc.getNauticalSunriseForDate(calendar), date);
            assertTimeEquals(nauticalSetTime, calc.getNauticalSunsetForDate(calendar), date);
            assertTimeEquals(officialRiseTime, calc.getOfficalSunriseForDate(calendar), date);
            assertTimeEquals(officialSetTime, calc.getOfficialSunsetForDate(calendar), date);
            assertTimeEquals(civilRiseTime, calc.getCivilSunriseForDate(calendar), date);
            assertTimeEquals(civilSetTime, calc.getCivilSunsetForDate(calendar), date);
        }
    }

    private Calendar createCalendar(String[] dateParts) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(dateParts[2]), Integer.valueOf(dateParts[0]) - 1, Integer.valueOf(dateParts[1]));
        return cal;
    }

    private Location createLocation() {
        // N39deg 59' 37" W075deg 47' 06"
        return new Location("39.9937", "-75.7850");
    }
}
