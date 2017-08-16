/*
 * WiFi Analyzer
 * Copyright (C) 2017  Galaxy Developers <galaxy1developers@gmail.com>
 *
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
 * Get Full Source Code "https://drive.google.com/open?id=0B089ZNj1OzvDWHM1ZlJZLVV6V28"
 */
package com.galaxy.wifianalyzar.wifi.band;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.SortedSet;

public class WiFiChannelCountry {
    public static final String UNKNOWN = "-Unknown";

    private static final Country COUNTRY = new Country();
    private static final WiFiChannelCountryGHZ2 WIFI_CHANNEL_GHZ2 = new WiFiChannelCountryGHZ2();
    private static final WiFiChannelCountryGHZ5 WIFI_CHANNEL_GHZ5 = new WiFiChannelCountryGHZ5();

    private final Locale country;

    private WiFiChannelCountry(@NonNull Locale country) {
        this.country = country;
    }

    public static WiFiChannelCountry get(String countryCode) {
        return new WiFiChannelCountry(COUNTRY.getCountry(countryCode));
    }

    public static List<WiFiChannelCountry> getAll() {
        List<WiFiChannelCountry> results = new ArrayList<>();
        for (Locale locale : COUNTRY.getCountries()) {
            results.add(new WiFiChannelCountry(locale));
        }
        return results;
    }

    public String getCountryCode() {
        return country.getCountry();
    }

    public String getCountryName() {
        String countryName = country.getDisplayCountry();
        return country.getCountry().equals(countryName) ? countryName + UNKNOWN : countryName;
    }

    public SortedSet<Integer> getChannelsGHZ2() {
        return WIFI_CHANNEL_GHZ2.findChannels(country.getCountry());
    }

    public SortedSet<Integer> getChannelsGHZ5() {
        return WIFI_CHANNEL_GHZ5.findChannels(country.getCountry());
    }

    boolean isChannelAvailableGHZ2(int channel) {
        return getChannelsGHZ2().contains(channel);
    }

    boolean isChannelAvailableGHZ5(int channel) {
        return getChannelsGHZ5().contains(channel);
    }
}
