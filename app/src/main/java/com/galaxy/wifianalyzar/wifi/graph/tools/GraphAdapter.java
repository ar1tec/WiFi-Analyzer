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
package com.galaxy.wifianalyzar.wifi.graph.tools;

import android.support.annotation.NonNull;

import com.jjoe64.graphview.GraphView;
import com.galaxy.wifianalyzar.wifi.model.WiFiData;
import com.galaxy.wifianalyzar.wifi.scanner.UpdateNotifier;

import java.util.ArrayList;
import java.util.List;

public class GraphAdapter implements UpdateNotifier {
    private final List<GraphViewNotifier> graphViewNotifiers;

    public GraphAdapter(@NonNull List<GraphViewNotifier> graphViewNotifiers) {
        this.graphViewNotifiers = graphViewNotifiers;
    }

    public List<GraphView> getGraphViews() {
        List<GraphView> graphViews = new ArrayList<>();
        for (GraphViewNotifier graphViewNotifier : graphViewNotifiers) {
            graphViews.add(graphViewNotifier.getGraphView());
        }
        return graphViews;
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        for (GraphViewNotifier graphViewNotifier : graphViewNotifiers) {
            graphViewNotifier.update(wiFiData);
        }
    }

    public List<GraphViewNotifier> getGraphViewNotifiers() {
        return graphViewNotifiers;
    }
}
