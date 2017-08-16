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
package com.galaxy.wifianalyzar.wifi;

import android.support.annotation.NonNull;
import android.view.View;

import com.galaxy.wifianalyzar.Configuration;
import com.galaxy.wifianalyzar.MainActivity;
import com.galaxy.wifianalyzar.MainContext;
import com.galaxy.wifianalyzar.R;
import com.galaxy.wifianalyzar.settings.Settings;
import com.galaxy.wifianalyzar.wifi.model.WiFiData;
import com.galaxy.wifianalyzar.wifi.model.WiFiDetail;
import com.galaxy.wifianalyzar.wifi.scanner.UpdateNotifier;

import java.util.List;

import static com.galaxy.wifianalyzar.navigation.NavigationMenu.ACCESS_POINTS;
import static com.galaxy.wifianalyzar.navigation.NavigationMenu.CHANNEL_AVAILABLE;
import static com.galaxy.wifianalyzar.navigation.NavigationMenu.VENDOR_LIST;

public class ConnectionView implements UpdateNotifier {
    private final MainActivity mainActivity;
    private AccessPointsDetail accessPointsDetail;



    public ConnectionView(@NonNull MainActivity mainActivity) {
        this.mainActivity = mainActivity;
        setAccessPointsDetail(new AccessPointsDetail());
   }


    @Override
    public void update(@NonNull WiFiData wiFiData) {

        setConnectionVisibility(wiFiData);

        setNoDataVisibility(wiFiData);
    }

    private void setNoDataVisibility(@NonNull WiFiData wiFiData) {
        int noDataVisibility = View.GONE;
        int noDataGeoVisibility = View.GONE;
        if (mainActivity.getNavigationMenuView().getCurrentNavigationMenu().isWiFiBandSwitchable()) {
            Settings settings = MainContext.INSTANCE.getSettings();
            List<WiFiDetail> wiFiDetails = wiFiData.getWiFiDetails(settings.getWiFiBand(), settings.getSortBy());
            if (wiFiDetails.isEmpty()) {
                noDataVisibility = View.VISIBLE;
                if (wiFiData.getWiFiDetails().isEmpty()) {
                    noDataGeoVisibility = View.VISIBLE;
                }
            }
        }
        mainActivity.findViewById(R.id.nodata).setVisibility(noDataVisibility);
        mainActivity.findViewById(R.id.nodatageo).setVisibility(noDataGeoVisibility);
        mainActivity.findViewById(R.id.nodatageourl).setVisibility(noDataGeoVisibility);
    }

    private void setConnectionVisibility(@NonNull WiFiData wiFiData) {
        WiFiDetail connection = wiFiData.getConnection();

        View connectionView = mainActivity.findViewById(R.id.connection);
       // View connectionViewother = mainActivity.findViewById(R.id.connectionother);
      if (mainActivity.getNavigationMenuView().getCurrentNavigationMenu()==ACCESS_POINTS || mainActivity.getNavigationMenuView().getCurrentNavigationMenu()==CHANNEL_AVAILABLE ||mainActivity.getNavigationMenuView().getCurrentNavigationMenu()==VENDOR_LIST ){
        if (connection.getWiFiAdditional().isConnected()) {


            connectionView.setVisibility(View.VISIBLE);
            //connectionViewother.setVisibility(View.VISIBLE);
            Configuration configuration = MainContext.INSTANCE.getConfiguration();
            AccessPointsDetailOptions options = new AccessPointsDetailOptions(false, configuration.isLargeScreenLayout());
            accessPointsDetail.setView(mainActivity.getResources(), connectionView, connection, options);
        }
        } else {
            connectionView.setVisibility(View.GONE);


        }}


    void setAccessPointsDetail(@NonNull AccessPointsDetail accessPointsDetail) {
        this.accessPointsDetail = accessPointsDetail;
    }
}
