package com.reedell.sunrisesunset;

import java.math.BigDecimal;

/**
 * Enumerated type that defines the available zeniths for computing the sunrise/sunset.
 */
public enum Zenith {
    ASTRONOMICAL (BigDecimal.valueOf(108)),
    NAUTICAL (BigDecimal.valueOf(102)),
    CIVIL (BigDecimal.valueOf(96)),
    OFFICIAL (BigDecimal.valueOf(90.8333)); //90deg, 50'

    private final BigDecimal degrees;
    
    Zenith(BigDecimal degrees) {
        this.degrees = degrees;
    }
    
    public BigDecimal degrees() {
        return degrees;
    }
}
