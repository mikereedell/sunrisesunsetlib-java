Sunrise/SunsetLib - Java
============

Description
-----------
Java library to compute the local sunrise and sunset at a given latitude/longitue and date combination.

Dependencies
------------
None

Installation
------------
Download the jar or clone the repo and run $ mvn clean install to build from source.


Maven
-----

You can now use Maven to pull in SunriseSunsetLib into your project, just add:

```
<dependency>
  <groupId>com.luckycatlabs</groupId>
  <artifactId>SunriseSunsetCalculator</artifactId>
  <version>1.1</version>
</dependency>
```

to your pom.xml.

Usage
-----
Create a SunriseSunsetCalculator with a location and time zone identifier:

    Location location = new Location("39.9522222", "-75.1641667");
    SunriseSunsetCalculator calculator = new SunriseSunsetCalculator(location, "America/New_York");

Then call the method for the type of sunrise/sunset you want to calculate:

    String officialSunrise = calculator.getOfficialSunriseForDate(Calendar.getInstance());
    Calendar officialSunset = calculator.getOfficialSunsetCalendarForDate(Calendar.getInstance());

Bug Reports
-----------
When filing a bug report, please include the following information:
- Date sunrise/set calculation was being run for.
- Latitude/longitute sunrise/set calculation was being run for.
- Timezone (either "America/New_York" or GMT-0500)

Author
------
Mike Reedell

[Donate with Pledgie](http://www.pledgie.com/campaigns/15328)

License
-------
Apache License, Version 2.0 
[http://www.apache.org/licenses/LICENSE-2.0](http://www.apache.org/licenses/LICENSE-2.0)
