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

public enum NavigationGroup {
    GROUP_FEATURE(NavigationMenu.ACCESS_POINTS, NavigationMenu.CHANNEL_RATING, NavigationMenu.CHANNEL_GRAPH, NavigationMenu.TIME_GRAPH),
    GROUP_OTHER(NavigationMenu.CHANNEL_AVAILABLE, NavigationMenu.VENDOR_LIST),
    GROUP_SETTINGS(NavigationMenu.SETTINGS, NavigationMenu.Advertize);

    private final NavigationMenu[] navigationMenu;

    NavigationGroup(@NonNull NavigationMenu... navigationMenu) {
        this.navigationMenu = navigationMenu;
    }

    public NavigationMenu[] navigationMenu() {

        return navigationMenu;
    }
}
