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

import com.galaxy.wifianalyzar.MainContext;
import com.galaxy.wifianalyzar.settings.Settings;
import com.galaxy.wifianalyzar.wifi.model.WiFiData;
import com.galaxy.wifianalyzar.wifi.model.WiFiDetail;

import java.util.ArrayList;
import java.util.List;

class AccessPointsAdapterData {


    private List<WiFiDetail> wiFiDetails = new ArrayList<>();

    void update(WiFiData wiFiData) {
        Settings settings = MainContext.INSTANCE.getSettings();
        wiFiDetails = wiFiData.getWiFiDetails(settings.getWiFiBand(), settings.getSortBy(), settings.getGroupBy());
    }

    int parentsCount() {
        return wiFiDetails.size();
    }

    private boolean validParentIndex(int index) {
        return index >= 0 && index < parentsCount();
    }

    private boolean validChildrenIndex(int indexParent, int indexChild) {
        return validParentIndex(indexParent) && indexChild >= 0 && indexChild < childrenCount(indexParent);
    }

    WiFiDetail parent(int index) {
        return validParentIndex(index) ? wiFiDetails.get(index) : WiFiDetail.EMPTY;
    }

    int childrenCount(int index) {
        return validParentIndex(index) ? wiFiDetails.get(index).getChildren().size() : 0;
    }

    WiFiDetail child(int indexParent, int indexChild) {
        return validChildrenIndex(indexParent, indexChild) ? wiFiDetails.get(indexParent).getChildren().get(indexChild) : WiFiDetail.EMPTY;
    }
}
