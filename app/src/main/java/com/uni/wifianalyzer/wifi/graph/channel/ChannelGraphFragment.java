
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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;

import com.jjoe64.graphview.GraphView;
import com.uni.wifianalyzer.Configuration;
import com.uni.wifianalyzer.MainContext;
import com.uni.wifianalyzer.R;
import com.uni.wifianalyzer.wifi.graph.channel.ChannelGraphNavigation.NavigationItem;
import com.uni.wifianalyzer.wifi.scanner.Scanner;

public class ChannelGraphFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChannelGraphAdapter channelGraphAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.graph_content, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.graphRefresh);
        swipeRefreshLayout.setOnRefreshListener(new ListViewOnRefreshListener());

        Configuration configuration = MainContext.INSTANCE.getConfiguration();
        ChannelGraphNavigation channelGraphNavigation = new ChannelGraphNavigation(getActivity(), configuration);
        channelGraphAdapter = new ChannelGraphAdapter(channelGraphNavigation);
        addGraphViews(swipeRefreshLayout, channelGraphAdapter);
        addGraphNavigation(view, channelGraphNavigation);

        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.register(channelGraphAdapter);

        return view;
    }

    private void addGraphNavigation(View view, ChannelGraphNavigation channelGraphNavigation) {
        LinearLayout linearLayout = (LinearLayout) view.findViewById(R.id.graphNavigation);
        for (NavigationItem navigationItem : channelGraphNavigation.getNavigationItems()) {
            linearLayout.addView(navigationItem.getButton());
        }
    }

    private void addGraphViews(View view, ChannelGraphAdapter channelGraphAdapter) {
        ViewFlipper viewFlipper = (ViewFlipper) view.findViewById(R.id.graphFlipper);
        for (GraphView graphView : channelGraphAdapter.getGraphViews()) {
            viewFlipper.addView(graphView);
        }
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
        scanner.unregister(channelGraphAdapter);
        super.onDestroy();
    }

    private class ListViewOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            refresh();
        }
    }

}
