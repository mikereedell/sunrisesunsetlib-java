package com.reedell.sunrisesunset;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.reedell.sunrisesunset.dto.Location;
import com.reedell.sunrisesunset.util.BaseTestCase;
import com.reedell.sunrisesunset.util.CSVTestDriver;

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
            setTimeZone(dataSetNameParts[1]);
            Location location = createLocation(dataSetNameParts[0]);

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
                SunriseSunsetCalculator calc = new SunriseSunsetCalculator(location);

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
    }

    private void setTimeZone(String timeZone) {
        timeZone = timeZone.split("\\.")[0].replace('-', '/');
        TimeZone.setDefault(TimeZone.getTimeZone(timeZone));
    }

    private Calendar createCalendar(String[] dateParts) {
        Calendar cal = Calendar.getInstance();
        cal.set(Integer.valueOf(dateParts[2]), Integer.valueOf(dateParts[0]) - 1, Integer.valueOf(dateParts[1]));
        System.out.println(cal.getTimeZone().getDisplayName());
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
