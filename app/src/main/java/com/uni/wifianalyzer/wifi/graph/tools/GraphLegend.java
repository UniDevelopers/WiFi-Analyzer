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

import android.support.annotation.NonNull;

import com.jjoe64.graphview.LegendRenderer;

public enum GraphLegend {
    LEFT(new DisplayLeft()),
    RIGHT(new DisplayRight()),
    HIDE(new DisplayNone());

    private final Display display;

    GraphLegend(Display display) {
        this.display = display;
    }

    public static GraphLegend find(int index, @NonNull GraphLegend defaultValue) {
        if (index < 0 || index >= values().length) {
            return defaultValue;
        }
        return values()[index];
    }

    public void display(LegendRenderer legendRenderer) {
        display.display(legendRenderer);
    }

    Display getDisplay() {
        return display;
    }

    private interface Display {
        void display(LegendRenderer legendRenderer);
    }

    protected static class DisplayNone implements Display {
        @Override
        public void display(LegendRenderer legendRenderer) {
            legendRenderer.setVisible(false);
        }
    }

    protected static class DisplayLeft implements Display {
        @Override
        public void display(LegendRenderer legendRenderer) {
            legendRenderer.setVisible(true);
            legendRenderer.setFixedPosition(0, 0);
        }
    }

    protected static class DisplayRight implements Display {
        @Override
        public void display(LegendRenderer legendRenderer) {
            legendRenderer.setVisible(true);
            legendRenderer.setAlign(LegendRenderer.LegendAlign.TOP);
        }
    }

}
