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

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.CompareToBuilder;

import java.util.Comparator;

public enum GroupBy {
    NONE(new None(), new None()),
    SSID(new SSIDSortOrder(), new SSIDGroupBy()),
    CHANNEL(new ChannelSortOrder(), new ChannelGroupBy());

    private final Comparator<WiFiDetail> sortOrder;
    private final Comparator<WiFiDetail> groupBy;

    GroupBy(@NonNull Comparator<WiFiDetail> sortOrder, @NonNull Comparator<WiFiDetail> groupBy) {
        this.sortOrder = sortOrder;
        this.groupBy = groupBy;
    }

    public static GroupBy find(int index) {
        if (index < 0 || index >= values().length) {
            return NONE;
        }
        return values()[index];
    }

    Comparator<WiFiDetail> sortOrder() {
        return sortOrder;
    }

    Comparator<WiFiDetail> groupBy() {
        return groupBy;
    }

    static class None implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            return lhs.equals(rhs) ? 0 : 1;
        }
    }

    static class SSIDSortOrder implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            return new CompareToBuilder()
                .append(lhs.getSSID().toUpperCase(), rhs.getSSID().toUpperCase())
                .append(rhs.getWiFiSignal().getLevel(), lhs.getWiFiSignal().getLevel())
                .append(lhs.getBSSID().toUpperCase(), rhs.getBSSID().toUpperCase())
                .toComparison();
        }
    }

    static class SSIDGroupBy implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            return new CompareToBuilder()
                .append(lhs.getSSID().toUpperCase(), rhs.getSSID().toUpperCase())
                .toComparison();
        }
    }

    static class ChannelSortOrder implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            return new CompareToBuilder()
                .append(lhs.getWiFiSignal().getPrimaryWiFiChannel().getChannel(), rhs.getWiFiSignal().getPrimaryWiFiChannel().getChannel())
                .append(rhs.getWiFiSignal().getLevel(), lhs.getWiFiSignal().getLevel())
                .append(lhs.getSSID().toUpperCase(), rhs.getSSID().toUpperCase())
                .append(lhs.getBSSID().toUpperCase(), rhs.getBSSID().toUpperCase())
                .toComparison();
        }
    }

    static class ChannelGroupBy implements Comparator<WiFiDetail> {
        @Override
        public int compare(WiFiDetail lhs, WiFiDetail rhs) {
            return new CompareToBuilder()
                .append(lhs.getWiFiSignal().getPrimaryWiFiChannel().getChannel(), rhs.getWiFiSignal().getPrimaryWiFiChannel().getChannel())
                .toComparison();
        }
    }

}
