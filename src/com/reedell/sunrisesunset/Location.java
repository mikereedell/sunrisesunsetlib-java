package com.reedell.sunrisesunset;

import java.math.BigDecimal;

/**
 * Simple VO class to store latitude/longitude information.
 */
public class Location {
    private BigDecimal latitude;
    private BigDecimal longitude;

    /**
     * Creates a new instance of <code>Location</code> with the given parameters.
     * 
     * @param latitude
     *            the latitude, in degrees, of this location. North latitude is positive, south negative.
     * @param longitude
     *            the longitude, in degrees of this location. East longitude is positive, west negative.
     */
    public Location(BigDecimal latitude, BigDecimal longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Creates a new instance of <code>Location</code> with the given parameters.
     * 
     * @param latitude
     *            the latitude, in degrees, of this location. North latitude is positive, south negative.
     * @param longitude
     *            the longitude, in degrees of this location. East longitude is positive, west negative.
     */
    public Location(String latitude, String longitude) {
        this.latitude = new BigDecimal(latitude);
        this.longitude = new BigDecimal(longitude);
    }

    /**
     * @return the latitude
     */
    public BigDecimal getLatitude() {
        return latitude;
    }

    /**
     * @param latitude
     *            the latitude to set
     */
    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    /**
     * @return the longitude
     */
    public BigDecimal getLongitude() {
        return longitude;
    }

    /**
     * @param longitude
     *            the longitude to set
     */
    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}
