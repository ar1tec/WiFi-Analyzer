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

import android.content.Context;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.galaxy.wifianalyzar.MainContext;
import com.galaxy.wifianalyzar.R;
import com.galaxy.wifianalyzar.wifi.scanner.Scanner;

public class AccessPointsFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private AccessPointsAdapter accessPointsAdapter;
    public Context pkkk;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();



        View view = inflater.inflate(R.layout.access_points_content, container, false);
        pkkk = getActivity();
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.accessPointsRefresh);
        swipeRefreshLayout.setOnRefreshListener(new ListViewOnRefreshListener());

        accessPointsAdapter = new AccessPointsAdapter(activity);
        ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.accessPointsView);
        expandableListView.setAdapter(accessPointsAdapter);

        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.register(accessPointsAdapter);


        return view;
    }

    private void refresh() {
        swipeRefreshLayout.setRefreshing(true);
        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.update();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    @Override
    public void onDestroy() {
        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.unregister(accessPointsAdapter);
        super.onDestroy();
    }

    private class ListViewOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            refresh();
        }
    }

    }


