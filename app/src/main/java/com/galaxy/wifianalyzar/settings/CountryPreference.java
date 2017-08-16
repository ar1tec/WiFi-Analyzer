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
package com.galaxy.wifianalyzar.settings;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.AttributeSet;

import com.galaxy.wifianalyzar.wifi.band.WiFiChannelCountry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CountryPreference extends CustomPreference {
    public CountryPreference(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs, getData(), getDefault(context));
    }

    private static List<Data> getData() {
        List<Data> result = new ArrayList<>();
        for (WiFiChannelCountry wiFiChannelCountry : WiFiChannelCountry.getAll()) {
            result.add(new Data(wiFiChannelCountry.getCountryCode(), wiFiChannelCountry.getCountryName()));
        }
        Collections.sort(result);
        return result;
    }

    private static String getDefault(@NonNull Context context) {
        Resources resources = context.getResources();
        Configuration configuration = resources.getConfiguration();
        return configuration.locale.getCountry();
    }
}
