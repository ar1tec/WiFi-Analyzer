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
package com.galaxy.wifianalyzar.navigation;

import android.support.annotation.NonNull;
import android.view.MenuItem;

import com.galaxy.wifianalyzar.MainActivity;
import com.galaxy.wifianalyzar.R;
import com.galaxy.wifianalyzar.about.AboutActivity;

import com.galaxy.wifianalyzar.settings.SettingActivity;
import com.galaxy.wifianalyzar.vendor.VendorFragment;
import com.galaxy.wifianalyzar.wifi.AccessPointsFragment;
import com.galaxy.wifianalyzar.wifi.ChannelAvailableFragment;
import com.galaxy.wifianalyzar.wifi.ChannelRatingFragment;
import com.galaxy.wifianalyzar.wifi.graph.channel.ChannelGraphFragment;
import com.galaxy.wifianalyzar.wifi.graph.time.TimeGraphFragment;

public enum NavigationMenu {
    ACCESS_POINTS(R.drawable.ic_network_wifi_grey_500_48dp, R.string.action_access_points, true, new FragmentItem( new AccessPointsFragment())),
    CHANNEL_RATING(R.drawable.ic_wifi_tethering_grey_500_48dp, R.string.action_channel_rating, true, new FragmentItem(new ChannelRatingFragment())),
    CHANNEL_GRAPH(R.drawable.ic_insert_chart_grey_500_48dp, R.string.action_channel_graph, true, new FragmentItem(new  ChannelGraphFragment())),
    TIME_GRAPH(R.drawable.ic_show_chart_grey_500_48dp, R.string.action_time_graph, true, new FragmentItem(new TimeGraphFragment())),
    CHANNEL_AVAILABLE(R.drawable.ic_location_on_grey_500_48dp, R.string.action_channel_available, new FragmentItem( new ChannelAvailableFragment())),
    VENDOR_LIST(R.drawable.ic_list_grey_500_48dp, R.string.action_vendors, new FragmentItem(new VendorFragment())),
    SETTINGS(R.drawable.ic_settings_grey_500_48dp, R.string.action_settings, new ActivityItem(SettingActivity.class)),
    Advertize(R.drawable.ic_info_black_24dp, R.string.action_about, new ActivityItem(AboutActivity.class));
    //SETTINGSS(R.drawable.ic_settings_grey_500_48dp, R.string.action_settings, AboutActivity.class), ;
    //Rate_App(R.drawable.ic_star_black_24dp, R.string.action_rate,AboutActivity.class);

    private final int icon;
    private final int title;

    private final boolean wiFiBandSwitchable;
    private final NavigationMenuItem item;

    NavigationMenu(int icon, int title, boolean wiFiBandSwitchable, @NonNull NavigationMenuItem item) {
        this.icon = icon;
        this.title = title;
        this.wiFiBandSwitchable = wiFiBandSwitchable;
        this.item = item;
    }

    NavigationMenu(int icon, int title, @NonNull NavigationMenuItem item) {
        this.icon = icon;
        this.title = title;
        this.wiFiBandSwitchable = false;
        this.item = item;
    }

    public static NavigationMenu find(int index) {
        if (index < 0 || index >= values().length) {
            return ACCESS_POINTS;
        }
        return values()[index];
    }

    public int getTitle() {
        return title;
    }

    public boolean isWiFiBandSwitchable() {
        return wiFiBandSwitchable;
    }

    int getIcon() {
        return icon;
    }

    public void activateNavigationMenu(@NonNull MainActivity mainActivity, @NonNull MenuItem menuItem) {
        item.activate(mainActivity, menuItem, this);
    }

    NavigationMenuItem getItem() {
        return item;
    }
}
