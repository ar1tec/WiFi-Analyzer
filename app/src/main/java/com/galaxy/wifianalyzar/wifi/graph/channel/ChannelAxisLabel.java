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
 */ com.galaxy.wifianalyzar.wifi.graph.channel;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.jjoe64.graphview.LabelFormatter;
import com.jjoe64.graphview.Viewport;
import com.galaxy.wifianalyzar.MainContext;
import com.galaxy.wifianalyzar.settings.Settings;
import com.galaxy.wifianalyzar.wifi.band.WiFiBand;
import com.galaxy.wifianalyzar.wifi.band.WiFiChannel;
import com.galaxy.wifianalyzar.wifi.band.WiFiChannels;
import com.galaxy.wifianalyzar.wifi.graph.tools.GraphViewBuilder;

import org.apache.commons.lang3.StringUtils;

class ChannelAxisLabel implements LabelFormatter {
    private final WiFiBand wiFiBand;
    private final Pair<WiFiChannel, WiFiChannel> wiFiChannelPair;

    ChannelAxisLabel(@NonNull WiFiBand wiFiBand, @NonNull Pair<WiFiChannel, WiFiChannel> wiFiChannelPair) {
        this.wiFiBand = wiFiBand;
        this.wiFiChannelPair = wiFiChannelPair;
    }

    @Override
    public String formatLabel(double value, boolean isValueX) {
        String result = StringUtils.EMPTY;

        int valueAsInt = (int) (value + (value < 0 ? -0.5 : 0.5));
        if (isValueX) {
            result += findChannel(valueAsInt);
        } else {
            if (valueAsInt <= GraphViewBuilder.MAX_Y && valueAsInt > GraphViewBuilder.MIN_Y) {
                result += valueAsInt;
            }
        }
        return result;
    }

    @Override
    public void setViewport(Viewport viewport) {
        // ignore
    }

    private String findChannel(int value) {
        WiFiChannels wiFiChannels = wiFiBand.getWiFiChannels();
        WiFiChannel wiFiChannel = wiFiChannels.getWiFiChannelByFrequency(value, wiFiChannelPair);
        if (wiFiChannel == WiFiChannel.UNKNOWN) {
            return StringUtils.EMPTY;
        }

        int channel = wiFiChannel.getChannel();
        Settings settings = MainContext.INSTANCE.getSettings();
        String countryCode = settings.getCountryCode();
        if (!wiFiChannels.isChannelAvailable(countryCode, channel)) {
            return StringUtils.EMPTY;
        }
        return "" + channel;
    }

}
