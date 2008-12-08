package com.reedell.sunrisesunset;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

/**
 * 
 */
public class SunsetCalculator extends SolarEventCalculator {

    public SunsetCalculator(Location location, Integer zenith) {
        super(location, zenith);
    }

    public SunsetCalculator(Location location, Integer zenith, Calendar sunsetDate) {
        super(location, zenith, sunsetDate);
    }

    protected BigDecimal getLongitudeHour() {
        return this.getLongitudeHour(18);
    }

    protected BigDecimal getMeanAnomaly() {
        BigDecimal multiplier = new BigDecimal("0.9856");
        BigDecimal meanAnomaly = multiplier.multiply(getLongitudeHour()).subtract(new BigDecimal("3.289"));
        return setScale(meanAnomaly);
    }

    protected BigDecimal getSunTrueLongitude() {
        BigDecimal meanAnomalyInRads = convertDegreesToRadians(getMeanAnomaly());
        BigDecimal sinMeanAnomaly = new BigDecimal(Math.sin(meanAnomalyInRads.doubleValue()));
        BigDecimal sinDoubleMeanAnomaly = new BigDecimal(Math
                .sin(meanAnomalyInRads.multiply(BigDecimal.valueOf(2)).doubleValue()));

        BigDecimal firstPart = getMeanAnomaly().add(sinMeanAnomaly.multiply(new BigDecimal("1.916")));
        BigDecimal secondPart = multiplyBy(sinDoubleMeanAnomaly, new BigDecimal("0.020")).add(new BigDecimal("282.634"));
        BigDecimal trueLongitude = firstPart.add(secondPart);

        if (trueLongitude.doubleValue() > 360) {
            trueLongitude = trueLongitude.subtract(BigDecimal.valueOf(360));
        }
        return setScale(trueLongitude);
    }

    protected BigDecimal getSunRightAscension() {
        BigDecimal trueLongInRads = this.convertDegreesToRadians(getSunTrueLongitude());
        BigDecimal tanL = new BigDecimal(Math.tan(trueLongInRads.doubleValue()));

        BigDecimal innerParens = this.convertRadiansToDegrees(tanL).multiply(new BigDecimal("0.91764"));
        BigDecimal rightAscension = new BigDecimal(Math.atan(this.convertDegreesToRadians(innerParens).doubleValue()));
        return this.convertRadiansToDegrees(rightAscension).setScale(4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal getQuadrantOfRightAscension() {
        BigDecimal ninety = BigDecimal.valueOf(90);
        BigDecimal longitudeQuadrant = getSunTrueLongitude().divide(ninety, 0, RoundingMode.FLOOR);
        longitudeQuadrant = longitudeQuadrant.multiply(ninety);

        BigDecimal rightAscensionQuadrant = getSunRightAscension().divide(ninety, 0, RoundingMode.FLOOR);
        rightAscensionQuadrant = rightAscensionQuadrant.multiply(ninety);

        BigDecimal augend = longitudeQuadrant.subtract(rightAscensionQuadrant);
        return getSunRightAscension().add(augend);
    }

    protected BigDecimal getRightAscensionInHours() {
        return divideBy(getQuadrantOfRightAscension(), BigDecimal.valueOf(15));
    }

    protected BigDecimal getSinOfSunDeclination() {
        BigDecimal sunTrueLongInRads = convertDegreesToRadians(getSunTrueLongitude());
        BigDecimal sinTrueLongitude = BigDecimal.valueOf(Math.sin(sunTrueLongInRads.doubleValue()));
        BigDecimal sinOfDeclination = sinTrueLongitude.multiply(new BigDecimal("0.39782"));

        return setScale(sinOfDeclination);
    }

    protected BigDecimal getCosineOfSunDeclination() {
        BigDecimal arcSinOfSinDeclination = BigDecimal.valueOf(Math.asin(this.getSinOfSunDeclination().doubleValue()));
        BigDecimal cosDeclination = BigDecimal.valueOf(Math.cos(arcSinOfSinDeclination.doubleValue()));
        return cosDeclination.setScale(4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal getCosineSunLocalHour() {
        BigDecimal zenithInRads = convertDegreesToRadians(BigDecimal.valueOf(this.zenith));
        BigDecimal cosineZenith = BigDecimal.valueOf(Math.cos(zenithInRads.doubleValue()));
        BigDecimal sinLatitude = BigDecimal.valueOf(Math.sin(convertDegreesToRadians(this.location.getLatitude()).doubleValue()));
        BigDecimal cosLatitude = BigDecimal.valueOf(Math.cos(convertDegreesToRadians(this.location.getLatitude()).doubleValue()));

        BigDecimal sinDeclinationTimesSinLat = getSinOfSunDeclination().multiply(sinLatitude);
        BigDecimal top = cosineZenith.subtract(sinDeclinationTimesSinLat);
        BigDecimal bottom = getCosineOfSunDeclination().multiply(cosLatitude);

        return setScale(divideBy(top, bottom));
    }

    protected BigDecimal getSunLocalHour() {
        BigDecimal arcCosineOfCosineHourAngle = getArcCosineFor(getCosineSunLocalHour());
        BigDecimal localHour = convertRadiansToDegrees(arcCosineOfCosineHourAngle);
        return divideBy(localHour, BigDecimal.valueOf(15));
    }

    protected BigDecimal getLocalMeanTime() {
        BigDecimal innerParens = getLongitudeHour().multiply(new BigDecimal("0.06571"));
        BigDecimal localMeanTime = getSunLocalHour().add(getRightAscensionInHours()).subtract(innerParens);
        localMeanTime = localMeanTime.subtract(new BigDecimal("6.622"));
        return setScale(localMeanTime);
    }

    protected BigDecimal getUTCTime() {
        BigDecimal utcTime = getLocalMeanTime().subtract(getBaseLongitudeHour());
        if (utcTime.doubleValue() < 0) {
            return utcTime.add(BigDecimal.valueOf(24));
        }
        return utcTime;
    }

    protected BigDecimal getLocalTime() {
        return this.getUTCTime().add(getUTCOffSet());
    }

    protected String getLocalTimeAsString() {
        String localTime = getLocalTime().toPlainString();
        String[] timeComponents = localTime.split("\\.");
        String hour = (timeComponents[0].length() == 1) ? "0" + timeComponents[0] : timeComponents[0];

        BigDecimal minutes = new BigDecimal("0." + timeComponents[1]);
        minutes = minutes.multiply(BigDecimal.valueOf(60)).setScale(0, RoundingMode.HALF_EVEN);
        String minuteString = minutes.intValue() < 10 ? "0" + minutes.toPlainString() : minutes.toPlainString();
        return hour + ":" + minuteString;
    }
}
