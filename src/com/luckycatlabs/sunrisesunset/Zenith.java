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
