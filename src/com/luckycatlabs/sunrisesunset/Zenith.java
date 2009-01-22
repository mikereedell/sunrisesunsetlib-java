package com.luckycatlabs.sunrisesunset;

import java.math.BigDecimal;

/**
 * Enumerated type that defines the available zeniths for computing the sunrise/sunset.
 */
public enum Zenith {
    /** Astronomical sunrise/set is when the sun is 18 degrees below the horizon. */
    ASTRONOMICAL(BigDecimal.valueOf(108)),

    /** Nautical sunrise/set is when the sun is 12 degrees below the horizon. */
    NAUTICAL(BigDecimal.valueOf(102)),

    /** Civil sunrise/set (dawn/dusk) is when the sun is 6 degrees below the horizon. */
    CIVIL(BigDecimal.valueOf(96)),

    /** Official sunrise/set is when the sun is 50' below the horizon. */
    OFFICIAL(BigDecimal.valueOf(90.8333)); // 90deg, 50'

    private final BigDecimal degrees;

    private Zenith(BigDecimal degrees) {
        this.degrees = degrees;
    }

    public BigDecimal degrees() {
        return degrees;
    }
}
