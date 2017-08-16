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
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.galaxy.wifianalyzar.Configuration;
import com.galaxy.wifianalyzar.MainContext;
import com.galaxy.wifianalyzar.R;
import com.galaxy.wifianalyzar.wifi.model.WiFiData;
import com.galaxy.wifianalyzar.wifi.model.WiFiDetail;
import com.galaxy.wifianalyzar.wifi.scanner.UpdateNotifier;

class AccessPointsAdapter extends BaseExpandableListAdapter implements UpdateNotifier {
    private final Resources resources;
    private AccessPointsAdapterData accessPointsAdapterData;
    private AccessPointsDetail accessPointsDetail;

    AccessPointsAdapter(@NonNull Context context) {
        super();
        this.resources = context.getResources();
        setAccessPointsAdapterData(new AccessPointsAdapterData());
        setAccessPointsDetail(new AccessPointsDetail());


    }

    void setAccessPointsAdapterData(@NonNull AccessPointsAdapterData accessPointsAdapterData) {
        this.accessPointsAdapterData = accessPointsAdapterData;
    }

    void setAccessPointsDetail(@NonNull AccessPointsDetail accessPointsDetail) {
        this.accessPointsDetail = accessPointsDetail;
    }

    

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View view = getView(convertView, parent);
        WiFiDetail wiFiDetail = (WiFiDetail) getGroup(groupPosition);
        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        AccessPointsDetailOptions accessPointsDetailOptions = new AccessPointsDetailOptions(false, configuration.isLargeScreenLayout());
        accessPointsDetail.setView(resources, view, wiFiDetail, accessPointsDetailOptions);

        ImageView groupIndicator = (ImageView) view.findViewById(R.id.groupIndicator);
        int childrenCount = getChildrenCount(groupPosition);
        if (childrenCount > 0) {
            groupIndicator.setVisibility(View.VISIBLE);
            groupIndicator.setImageResource(isExpanded
                ? R.drawable.ic_expand_less_black_24dp
                : R.drawable.ic_expand_more_black_24dp);
            groupIndicator.setColorFilter(resources.getColor(R.color.icons_color));
        } else {
            groupIndicator.setVisibility(View.GONE);
        }

        return view;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        View view = getView(convertView, parent);
        WiFiDetail wiFiDetail = (WiFiDetail) getChild(groupPosition, childPosition);
        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        AccessPointsDetailOptions accessPointsDetailOptions = new AccessPointsDetailOptions(true, configuration.isLargeScreenLayout());
        accessPointsDetail.setView(resources, view, wiFiDetail, accessPointsDetailOptions);
        view.findViewById(R.id.groupIndicator).setVisibility(View.GONE);
        return view;
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        accessPointsAdapterData.update(wiFiData);
        notifyDataSetChanged();
    }

    @Override
    public int getGroupCount() {

        return accessPointsAdapterData.parentsCount();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return accessPointsAdapterData.childrenCount(groupPosition);
    }

    @Override
    public Object getGroup(int groupPosition) {
        return accessPointsAdapterData.parent(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return accessPointsAdapterData.child(groupPosition, childPosition);
    }

    @Override
    public long getGroupId(int groupPosition)
    {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }



    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    private View getView(View convertView, ViewGroup parent) {
        View view = convertView;



        if (view == null) {
            LayoutInflater layoutInflater = MainContext.INSTANCE.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.access_points_details, parent, false);


        }
        return view;
    }
}
