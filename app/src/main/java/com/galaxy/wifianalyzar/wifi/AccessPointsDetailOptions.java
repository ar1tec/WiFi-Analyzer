/*
 * WiFiAnalyzer Copyright (C) 2017 VREM Software Development <VREMSoftwareDevelopment@gmail.com>"
 *
 * License:WiFi Analyzer is licensed under the GNU General Public License v3.0 (GNU GPLv3)
 * License Details at "http://www.gnu.org/licenses/gpl-3.0.html"
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>
 *
 *
 * Get Full Source Code"https://github.com/galaxydevelopers/WiFi-Analyzer"
 */
package com.galaxy.wifianalyzar.wifi;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

class AccessPointsDetailOptions {
    private final boolean child;
    private final boolean frequencyRange;

    AccessPointsDetailOptions(boolean child, boolean frequencyRange) {
        this.child = child;
        this.frequencyRange = frequencyRange;
    }

    boolean isChild() {
        return child;
    }

    boolean isFrequencyRange() {
        return frequencyRange;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other == null || getClass() != other.getClass()) return false;

        AccessPointsDetailOptions otherOptions = (AccessPointsDetailOptions) other;
        return new EqualsBuilder()
            .append(isChild(), otherOptions.isChild())
            .append(isFrequencyRange(), otherOptions.isFrequencyRange())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(isChild())
            .append(isFrequencyRange())
            .toHashCode();
    }


}
