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
package com.galaxy.wifianalyzar.vendor.model;

import com.galaxy.wifianalyzar.MainContext;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
public class VendorService {
    private final Set<String> remoteCalls = new TreeSet<>();
    private final Map<String, String> cache = new HashMap<>();

    private RemoteCall remoteCall;

    public String findVendorName(String macAddress) {
        String key = MacAddress.clean(macAddress);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        Database database = MainContext.INSTANCE.getDatabase();
        String result = database.find(macAddress);
        if (result != null) {
            result = VendorNameUtils.cleanVendorName(result);
            cache.put(key, result);
            return result;
        }
        if (!remoteCalls.contains(key)) {
            remoteCalls.add(key);
            getRemoteCall().execute(macAddress);
        }
        return StringUtils.EMPTY;
    }

    void clear() {
        cache.clear();
        remoteCalls.clear();
    }

    public SortedMap<String, List<String>> findAll() {
        SortedMap<String, List<String>> results = new TreeMap<>();
        Database database = MainContext.INSTANCE.getDatabase();
        List<VendorData> vendorDatas = database.findAll();
        for (VendorData vendorData : vendorDatas) {
            String key = VendorNameUtils.cleanVendorName(vendorData.getName());
            List<String> macs = results.get(key);
            if (macs == null) {
                macs = new ArrayList<>();
                results.put(key, macs);
            }
            macs.add(vendorData.getMac());
            Collections.sort(macs);
        }
        return results;
    }

    // injectors start
    private RemoteCall getRemoteCall() {
        return remoteCall == null ? new RemoteCall() : remoteCall;
    }



}
