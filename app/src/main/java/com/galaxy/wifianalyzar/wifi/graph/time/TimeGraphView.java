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
package com.galaxy.wifianalyzar.wifi.graph.time;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.view.View;

import com.galaxy.wifianalyzar.MainActivity;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.galaxy.wifianalyzar.Configuration;
import com.galaxy.wifianalyzar.MainContext;
import com.galaxy.wifianalyzar.R;
import com.galaxy.wifianalyzar.settings.Settings;
import com.galaxy.wifianalyzar.wifi.band.WiFiBand;
import com.galaxy.wifianalyzar.wifi.graph.tools.GraphColor;
import com.galaxy.wifianalyzar.wifi.graph.tools.GraphViewBuilder;
import com.galaxy.wifianalyzar.wifi.graph.tools.GraphViewNotifier;
import com.galaxy.wifianalyzar.wifi.graph.tools.GraphViewWrapper;
import com.galaxy.wifianalyzar.wifi.model.WiFiData;
import com.galaxy.wifianalyzar.wifi.model.WiFiDetail;

import java.util.Set;
import java.util.TreeSet;

class TimeGraphView implements GraphViewNotifier {
    private static final int MAX_SCAN_COUNT = 400;
    private static final int NUM_X_SMALL = 18;
    private static final int NUM_X_LARGE = 24;
    private static final int THICKNESS_INVISIBLE = 0;

    private final WiFiBand wiFiBand;
    private GraphViewWrapper graphViewWrapper;
    private int scanCount;
    private int xValue;

    TimeGraphView(@NonNull WiFiBand wiFiBand) {
        this.wiFiBand = wiFiBand;
        this.scanCount = this.xValue = 0;
        this.graphViewWrapper = makeGraphViewWrapper();
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        Settings settings = MainContext.INSTANCE.getSettings();
        Set<WiFiDetail> newSeries = new TreeSet<>();
        for (WiFiDetail wiFiDetail : wiFiData.getWiFiDetails(wiFiBand, settings.getSortBy())) {
            newSeries.add(wiFiDetail);
            addData(wiFiDetail);
        }
        graphViewWrapper.removeSeries(newSeries);
        graphViewWrapper.updateLegend(settings.getTimeGraphLegend());
        graphViewWrapper.setVisibility(isSelected() ? View.VISIBLE : View.GONE);
        xValue++;
        if (scanCount < MAX_SCAN_COUNT) {
            scanCount++;
        }
    }

    private boolean isSelected() {
        return wiFiBand.equals(MainContext.INSTANCE.getSettings().getWiFiBand());
    }

    private void addData(@NonNull WiFiDetail wiFiDetail) {
        DataPoint dataPoint = new DataPoint(xValue, wiFiDetail.getWiFiSignal().getLevel());
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{dataPoint});
        if (graphViewWrapper.appendSeries(wiFiDetail, series, dataPoint, scanCount)) {
            series.setColor((int) graphViewWrapper.getColor().getPrimary());
            series.setDrawBackground(false);
        }
    }

    @Override
    public GraphView getGraphView() {
        return graphViewWrapper.getGraphView();
    }

    private int getNumX() {
        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        return configuration.isLargeScreenLayout() ? NUM_X_LARGE : NUM_X_SMALL;
    }

    void setGraphViewWrapper(@NonNull GraphViewWrapper graphViewWrapper) {
        this.graphViewWrapper = graphViewWrapper;
    }

    private GraphView makeGraphView() {
        Resources resources = MainContext.INSTANCE.getResources();
        MainActivity mainActivity = MainContext.INSTANCE.getMainActivity();
        return new GraphViewBuilder(mainActivity, getNumX())
            .setLabelFormatter(new TimeAxisLabel())
            .setVerticalTitle(resources.getString(R.string.graph_axis_y))
            .setHorizontalTitle(resources.getString(R.string.graph_time_axis_x))
            .build();
    }

    private GraphViewWrapper makeGraphViewWrapper() {
        Settings settings = MainContext.INSTANCE.getSettings();
        graphViewWrapper = new GraphViewWrapper(makeGraphView(), settings.getTimeGraphLegend());

        graphViewWrapper.setViewport();

        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[]{
            new DataPoint(0, GraphViewBuilder.MIN_Y),
            new DataPoint(getNumX() - 1, GraphViewBuilder.MIN_Y)
        });
        series.setColor((int) GraphColor.TRANSPARENT.getPrimary());
        series.setThickness(THICKNESS_INVISIBLE);
        graphViewWrapper.addSeries(series);

        return graphViewWrapper;
    }

}
