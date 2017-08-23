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
package com.galaxy.wifianalyzar.wifi.model;

import com.galaxy.wifianalyzar.R;

public enum Strength {
    ZERO(R.drawable.ic_signal_wifi_0_bar_black_36dp, R.color.error_color),
    ONE(R.drawable.ic_signal_wifi_1_bar_black_36dp, R.color.warning_color),
    TWO(R.drawable.ic_signal_wifi_2_bar_black_36dp, R.color.warning_color),
    THREE(R.drawable.ic_signal_wifi_3_bar_black_36dp, R.color.success_color),
    FOUR(R.drawable.ic_signal_wifi_4_bar_black_36dp, R.color.success_color);

    private final int imageResource;
    private final int colorResource;

    Strength(int imageResource, int colorResource) {
        this.imageResource = imageResource;
        this.colorResource = colorResource;
    }

    public static Strength calculate(int level) {
        int index = WiFiUtils.calculateSignalLevel(level, values().length);
        return Strength.values()[index];
    }

    public static Strength reverse(Strength strength) {
        int index = Strength.values().length - strength.ordinal() - 1;
        return Strength.values()[index];
    }

    public int colorResource() {
        return colorResource;
    }

    public int imageResource() {
        return imageResource;
    }

    public boolean weak() {
        return ZERO.equals(this);
    }

}
