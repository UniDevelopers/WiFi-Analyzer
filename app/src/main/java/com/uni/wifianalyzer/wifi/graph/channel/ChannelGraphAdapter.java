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
package com.uni.wifianalyzer.wifi.graph.channel;

import android.support.annotation.NonNull;
import android.support.v4.util.Pair;

import com.uni.wifianalyzer.wifi.band.WiFiBand;
import com.uni.wifianalyzer.wifi.band.WiFiChannel;
import com.uni.wifianalyzer.wifi.graph.tools.GraphAdapter;
import com.uni.wifianalyzer.wifi.graph.tools.GraphViewNotifier;
import com.uni.wifianalyzer.wifi.model.WiFiData;

import java.util.ArrayList;
import java.util.List;

class ChannelGraphAdapter extends GraphAdapter {
    private final ChannelGraphNavigation channelGraphNavigation;

    ChannelGraphAdapter(@NonNull ChannelGraphNavigation channelGraphNavigation) {
        super(makeGraphViewNotifiers());
        this.channelGraphNavigation = channelGraphNavigation;
    }

    private static List<GraphViewNotifier> makeGraphViewNotifiers() {
        List<GraphViewNotifier> graphViewNotifiers = new ArrayList<>();
        for (WiFiBand wiFiBand : WiFiBand.values()) {
            for (Pair<WiFiChannel, WiFiChannel> wiFiChannelPair : wiFiBand.getWiFiChannels().getWiFiChannelPairs()) {
                graphViewNotifiers.add(new ChannelGraphView(wiFiBand, wiFiChannelPair));
            }
        }
        return graphViewNotifiers;
    }

    @Override
    public void update(@NonNull WiFiData wiFiData) {
        super.update(wiFiData);
        channelGraphNavigation.update();
    }
}
