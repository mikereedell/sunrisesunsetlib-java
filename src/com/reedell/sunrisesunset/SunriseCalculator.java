package com.reedell.sunrisesunset;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Calendar;

/**
 * 
 */
public class SunriseCalculator extends SolarEventCalculator {

    public SunriseCalculator(Location location, Integer zenith) {
        super(location, zenith, Calendar.getInstance());
    }

    public SunriseCalculator(Location location, Integer zenith, Calendar sunriseDate) {
        super(location, zenith, sunriseDate);
    }

    protected BigDecimal getLongitudeHour() {
        return getDayOfYear().add(
                (BigDecimal.valueOf(6).subtract(this.getBaseLongitudeHour())).divide(BigDecimal.valueOf(24), 4,
                        RoundingMode.HALF_EVEN));
    }

    protected BigDecimal getMeanAnomaly() {
        BigDecimal multiplier = new BigDecimal(0.9856);
        return (multiplier.multiply(getLongitudeHour())).subtract(new BigDecimal(3.289));
    }

    protected BigDecimal getSunTrueLongitude() {
        BigDecimal meanAnomalyInRads = convertDegreesToRadians(getMeanAnomaly());
        BigDecimal sinMeanAnomaly = new BigDecimal(Math.sin(meanAnomalyInRads.doubleValue()));
        BigDecimal sinDoubleMeanAnomaly = new BigDecimal(Math
                .sin(meanAnomalyInRads.multiply(BigDecimal.valueOf(2)).doubleValue()));

        BigDecimal firstPart = getMeanAnomaly().add(sinMeanAnomaly.multiply(BigDecimal.valueOf(1.916)));
        BigDecimal secondPart = sinDoubleMeanAnomaly.multiply(BigDecimal.valueOf(0.020)).add(BigDecimal.valueOf(282.634));
        BigDecimal trueLongitude = firstPart.add(secondPart);

        if (trueLongitude.doubleValue() > 360) {
            trueLongitude = trueLongitude.subtract(BigDecimal.valueOf(360));
        }
        return trueLongitude;
    }

    protected BigDecimal getSunRightAscension() {
        BigDecimal trueLongInRads = this.convertDegreesToRadians(getSunTrueLongitude());
        BigDecimal tanL = new BigDecimal(Math.tan(trueLongInRads.doubleValue()));

        BigDecimal innerParens = this.convertRadiansToDegrees(tanL).multiply(BigDecimal.valueOf(0.91764));
        BigDecimal rightAscension = new BigDecimal(Math.atan(this.convertDegreesToRadians(innerParens).doubleValue()));
        return rightAscension;
    }

    protected BigDecimal setQuadrantOfRightAscension() {
        BigDecimal ninety = BigDecimal.valueOf(90);
        BigDecimal longitudeQuadrant = getSunTrueLongitude().divide(ninety, 4, RoundingMode.FLOOR);
        longitudeQuadrant = longitudeQuadrant.multiply(ninety);

        BigDecimal rightAscensionQuadrant = getSunRightAscension().divide(ninety, 4, RoundingMode.FLOOR);
        rightAscensionQuadrant = rightAscensionQuadrant.multiply(ninety);

        BigDecimal augend = longitudeQuadrant.subtract(rightAscensionQuadrant);
        return (getSunRightAscension().add(augend)).divide(BigDecimal.valueOf(15), 4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal getRightAscensionInHours() {
        return this.setQuadrantOfRightAscension().divide(FIFTEEN_DEG_IN_RAD, 4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal getSinOfSunDeclination() {
        BigDecimal sunTrueLongInRads = convertDegreesToRadians(getSunTrueLongitude());
        BigDecimal sinTrueLongitude = BigDecimal.valueOf(Math.sin(sunTrueLongInRads.doubleValue()));
        BigDecimal sinOfDeclination = BigDecimal.valueOf(0.39782).multiply(sinTrueLongitude);

        return sinOfDeclination;
    }

    protected BigDecimal getCosineOfSunDeclination() {
        BigDecimal arcSinOfSinDeclination = BigDecimal.valueOf(Math.asin(this.getSinOfSunDeclination().doubleValue()));
        BigDecimal cosDeclination = BigDecimal.valueOf(Math.cos(arcSinOfSinDeclination.doubleValue()));
        return cosDeclination;
    }

    protected BigDecimal getCosineSunLocalHour() {
        BigDecimal cosineZenith = BigDecimal.valueOf(Math.cos(this.zenith));
        BigDecimal sinLatitude = BigDecimal.valueOf(Math.sin(convertDegreesToRadians(this.location.getLatitude()).doubleValue()));
        BigDecimal cosLatitude = BigDecimal.valueOf(Math.cos(convertDegreesToRadians(this.location.getLatitude()).doubleValue()));

        BigDecimal sinDeclinationTimesSinLat = getSinOfSunDeclination().multiply(sinLatitude);
        BigDecimal top = cosineZenith.subtract(sinDeclinationTimesSinLat);
        BigDecimal bottom = getCosineOfSunDeclination().multiply(cosLatitude);

        BigDecimal cosineLocalHour = top.divide(bottom, 4, RoundingMode.HALF_EVEN);
        return cosineLocalHour;
    }

    protected BigDecimal getSunLocalHour() {
        BigDecimal arcCosineOfCosineHourAngle = getArcCosineFor(getCosineSunLocalHour());
        BigDecimal localHour = BigDecimal.valueOf(360).subtract(arcCosineOfCosineHourAngle);

        return localHour.divide(BigDecimal.valueOf(15), 4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal getLocalMeanTime() {
        BigDecimal innerParens = BigDecimal.valueOf(0.06571).multiply(getLongitudeHour());
        BigDecimal localMeanTime = getSunLocalHour().add(getRightAscensionInHours()).subtract(innerParens);
        localMeanTime = localMeanTime.subtract(BigDecimal.valueOf(6.622));
        return localMeanTime;
    }

    protected BigDecimal getUTCTime() {
        return getLocalMeanTime().subtract(getBaseLongitudeHour());
    }

    protected BigDecimal getLocalTime() {
        return this.getUTCTime().subtract(BigDecimal.valueOf(getUTCOffset()));
    }
}
