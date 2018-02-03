/*
 *WiFiAnalyzer Copyright (C) 2017 VREM Software Development <VREMSoftwareDevelopment@gmail.com>"
 *
 *
 * License:

      WiFi Analyzer is licensed under the GNU General Public License v3.0 (GNU GPLv3)

   License Details at "http://www.gnu.org/licenses/gpl-3.0.html"
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
package com.uni.wifianalyzer.wifi;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.uni.wifianalyzer.MainContext;
import com.uni.wifianalyzer.R;
import com.uni.wifianalyzer.wifi.scanner.Scanner;

public class ChannelRatingFragment extends Fragment {
    private SwipeRefreshLayout swipeRefreshLayout;
    private ChannelRatingAdapter channelRatingAdapter;
    //public Context pkkk;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FragmentActivity activity = getActivity();

        View view = inflater.inflate(R.layout.channel_rating_content, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.channelRatingRefresh);
        swipeRefreshLayout.setOnRefreshListener(new ListViewOnRefreshListener());
//pkkk=getActivity();
        TextView bestChannels = (TextView) view.findViewById(R.id.channelRatingBestChannels);
        ListView listView = (ListView) view.findViewById(R.id.channelRatingView);
      //  TextView connectedssid = (TextView) view.findViewById(R.id.connecteded);
      //  WifiManager wifiManager = (WifiManager) pkkk.getSystemService(pkkk.WIFI_SERVICE);
       // WifiInfo wifiInfo = wifiManager.getConnectionInfo();

        //String ssidc = wifiInfo.getSSID();
        //connectedssid.setText("Connected : "+ssidc);
        channelRatingAdapter = new ChannelRatingAdapter(activity, bestChannels);
        listView.setAdapter(channelRatingAdapter);

        Scanner scanner = MainContext.INSTANCE.getScanner();
        scanner.register(channelRatingAdapter);

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
        scanner.unregister(channelRatingAdapter);
        super.onDestroy();
    }

    private class ListViewOnRefreshListener implements SwipeRefreshLayout.OnRefreshListener {
        @Override
        public void onRefresh() {
            refresh();
        }
    }
}
