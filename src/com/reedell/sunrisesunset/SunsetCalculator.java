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
        return getDayOfYear().add(
                (BigDecimal.valueOf(18).subtract(this.getBaseLongitudeHour())).divide(BigDecimal.valueOf(24), 4,
                        RoundingMode.HALF_EVEN));
    }

    protected BigDecimal getMeanAnomaly() {
        BigDecimal multiplier = new BigDecimal(0.9856);
        return (multiplier.multiply(getLongitudeHour())).subtract(new BigDecimal(3.289));
    }

    protected BigDecimal getSunTrueLongitude() {
        BigDecimal meanAnomalyInDegrees = getMeanAnomaly().multiply(RAD_TO_DEG);
        BigDecimal sinMeanAnomaly = new BigDecimal(Math.sin(meanAnomalyInDegrees.doubleValue()));
        BigDecimal sinDoubleMeanAnomaly = new BigDecimal(Math.sin(meanAnomalyInDegrees.multiply(BigDecimal.valueOf(2))
                .doubleValue()));

        BigDecimal firstPart = getMeanAnomaly().add(sinMeanAnomaly.multiply(BigDecimal.valueOf(1.916)));
        BigDecimal secondPart = sinDoubleMeanAnomaly.multiply(BigDecimal.valueOf(0.020)).add(BigDecimal.valueOf(282.634));
        BigDecimal trueLongitude = firstPart.add(secondPart);

        if (trueLongitude.doubleValue() > 360) {
            trueLongitude = trueLongitude.subtract(BigDecimal.valueOf(360));
        }
        return trueLongitude;
    }

    protected BigDecimal getSunRightAscension() {
        BigDecimal sunTrueLongitudeInDeg = getSunTrueLongitude();
        BigDecimal tanL = new BigDecimal(Math.tan(sunTrueLongitudeInDeg.divide(RAD_TO_DEG, 4, RoundingMode.HALF_EVEN)
                .doubleValue()));

        BigDecimal innerParens = tanL.multiply(BigDecimal.valueOf(0.91764));
        BigDecimal rightAscension = new BigDecimal(Math.atan(innerParens.doubleValue()));
        // Convert the ascension from radians to degrees.
        rightAscension = rightAscension.multiply(RAD_TO_DEG);
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
        return this.setQuadrantOfRightAscension().divide(BigDecimal.valueOf(15.0), 4, RoundingMode.HALF_EVEN);
    }

    protected BigDecimal getSinOfSunDeclination() {
        BigDecimal sinTrueLongitude = BigDecimal.valueOf(Math.sin(this.getSunTrueLongitude().divide(RAD_TO_DEG, 4,
                RoundingMode.HALF_EVEN).doubleValue()));
        BigDecimal sinOfDeclination = BigDecimal.valueOf(0.39782).multiply(sinTrueLongitude);

        return sinOfDeclination;
        // return sinOfDeclination.multiply(RAD_TO_DEG);
    }

    protected BigDecimal getCosineOfSunDeclination() {
        BigDecimal sinOfDeclinationInRads = this.getSinOfSunDeclination().divide(RAD_TO_DEG, 4, RoundingMode.HALF_EVEN);
        BigDecimal arcSinOfSinDeclination = BigDecimal.valueOf(Math.asin(sinOfDeclinationInRads.doubleValue()));
        BigDecimal cosDeclination = BigDecimal.valueOf(Math.cos(arcSinOfSinDeclination.doubleValue()));
        return cosDeclination;
        // return cosDeclination.multiply(RAD_TO_DEG);
    }

    protected BigDecimal getCosineSunLocalHour() {
        BigDecimal cosineZenith = BigDecimal.valueOf(Math.cos(this.zenith));
        BigDecimal sinLatitude = BigDecimal.valueOf(Math.sin(this.location.getLatitude().doubleValue()));
        BigDecimal cosineLatitude = BigDecimal.valueOf(Math.cos(this.location.getLatitude().doubleValue()));

        BigDecimal sinDeclinationTimesSinLat = this.getSinOfSunDeclination().multiply(sinLatitude);
        BigDecimal top = cosineZenith.subtract(sinDeclinationTimesSinLat);
        BigDecimal bottom = this.getCosineOfSunDeclination().multiply(cosineLatitude);

        BigDecimal cosineLocalHour = top.divide(bottom, 4, RoundingMode.HALF_EVEN);
        return cosineLocalHour;
    }

    protected BigDecimal getSunLocalHour() {
        BigDecimal arcCosineOfCosineHourAngle = getArcCosineFor(this.getCosineSunLocalHour());
        return arcCosineOfCosineHourAngle.divide(BigDecimal.valueOf(15), 4, RoundingMode.HALF_EVEN);
    }
}
