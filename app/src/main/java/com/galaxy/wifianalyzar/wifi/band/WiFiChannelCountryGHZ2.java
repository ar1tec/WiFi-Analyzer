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
package com.galaxy.wifianalyzar.wifi.band;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

class WiFiChannelCountryGHZ2 {
    private final Set<String> countries;
    private final SortedSet<Integer> channels;
    private final SortedSet<Integer> world;

    WiFiChannelCountryGHZ2() {
        countries = new HashSet<>(Arrays.asList("AS", "AU", "CA", "FM", "GU", "MP", "PA", "PR", "TW", "UM", "US", "VI"));
        channels = new TreeSet<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11));
        world = new TreeSet<>(channels);
        world.add(12);
        world.add(13);
    }

    SortedSet<Integer> findChannels(@NonNull String countryCode) {
        SortedSet<Integer> result = world;
        String code = StringUtils.capitalize(countryCode);
        if (countries.contains(code)) {
            result = channels;
        }
        return Collections.unmodifiableSortedSet(result);
    }

}
