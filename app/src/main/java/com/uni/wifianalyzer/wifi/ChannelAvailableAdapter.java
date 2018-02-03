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

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.uni.wifianalyzer.MainContext;
import com.uni.wifianalyzer.R;
import com.uni.wifianalyzer.wifi.band.WiFiBand;
import com.uni.wifianalyzer.wifi.band.WiFiChannelCountry;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

class ChannelAvailableAdapter extends ArrayAdapter<WiFiChannelCountry> {
    ChannelAvailableAdapter(@NonNull Context context, @NonNull List<WiFiChannelCountry> wiFiChannelCountries) {
        super(context, R.layout.channel_available_details, wiFiChannelCountries);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
           LayoutInflater layoutInflater = MainContext.INSTANCE.getLayoutInflater();
            view = layoutInflater.inflate(R.layout.channel_available_details, parent, false);
        }



        WiFiChannelCountry wiFiChannelCountry = getItem(position);
        ((TextView) view.findViewById(R.id.channel_available_country))
            .setText(wiFiChannelCountry.getCountryCode() + " - " + wiFiChannelCountry.getCountryName());
        ((TextView) view.findViewById(R.id.channel_available_title_ghz_2))
            .setText(WiFiBand.GHZ2.getBand() + " : ");
        ((TextView) view.findViewById(R.id.channel_available_ghz_2))
            .setText(StringUtils.join(wiFiChannelCountry.getChannelsGHZ2().toArray(), ","));
        ((TextView) view.findViewById(R.id.channel_available_title_ghz_5))
            .setText(WiFiBand.GHZ5.getBand() + " : ");
        ((TextView) view.findViewById(R.id.channel_available_ghz_5))
            .setText(StringUtils.join(wiFiChannelCountry.getChannelsGHZ5().toArray(), ","));
        return view;
    }

}
