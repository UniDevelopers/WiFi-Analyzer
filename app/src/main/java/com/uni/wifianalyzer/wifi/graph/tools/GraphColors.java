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
 * Get Full Source Code"https://github.com/UniDevelopers/WiFi-Analyzer"
 */
package com.uni.wifianalyzer.wifi.graph.tools;

import android.content.res.Resources;

import com.uni.wifianalyzer.MainContext;
import com.uni.wifianalyzer.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

class GraphColors {
    private final List<GraphColor> availableGraphColors;
    private final Stack<GraphColor> graphColors;

    GraphColors() {
        graphColors = new Stack<>();
        availableGraphColors = new ArrayList<>();
    }

    private List<GraphColor> getAvailableGraphColors() {
        if (availableGraphColors.isEmpty()) {
            Resources resources = MainContext.INSTANCE.getResources();
            String[] colorsAsStrings = resources.getStringArray(R.array.graph_colors);
            for (int i = 0; i < colorsAsStrings.length; i += 2) {
                GraphColor graphColor = new GraphColor(Long.parseLong(colorsAsStrings[i].substring(1), 16), Long.parseLong(colorsAsStrings[i + 1].substring(1), 16));
                availableGraphColors.add(graphColor);
            }
        }
        return availableGraphColors;
    }

    GraphColor getColor() {
        if (graphColors.isEmpty()) {
            graphColors.addAll(getAvailableGraphColors());
        }
        return graphColors.pop();
    }

    void addColor(long primaryColor) {
        GraphColor graphColor = findColor(primaryColor);
        if (graphColor == null || graphColors.contains(graphColor)) {
            return;
        }
        graphColors.push(graphColor);
    }

    private GraphColor findColor(long primaryColor) {
        for (GraphColor graphColor : getAvailableGraphColors()) {
            if (primaryColor == graphColor.getPrimary()) {
                return graphColor;
            }
        }
        return null;
    }

}
