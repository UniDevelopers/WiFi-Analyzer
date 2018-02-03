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
package com.uni.wifianalyzer.settings;

import android.content.Context;
import android.support.annotation.NonNull;

import com.uni.wifianalyzer.R;
import com.uni.wifianalyzer.navigation.NavigationMenu;
import com.uni.wifianalyzer.wifi.band.WiFiBand;
import com.uni.wifianalyzer.wifi.graph.tools.GraphLegend;
import com.uni.wifianalyzer.wifi.model.GroupBy;
import com.uni.wifianalyzer.wifi.model.SortBy;

import static android.content.SharedPreferences.OnSharedPreferenceChangeListener;

public class Settings {
    private final Context context;
    private Repository repository;

    public Settings(@NonNull Context context) {
        this.context = context;
        setRepository(new Repository());
    }

    public void setRepository(@NonNull Repository repository) {
        this.repository = repository;
    }

    public void initializeDefaultValues() {
        repository.initializeDefaultValues();
    }

    public void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        repository.registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    public int getScanInterval() {
        return repository.getInteger(R.string.scan_interval_key, repository.getResourceInteger(R.integer.scan_interval_default));
    }

    public SortBy getSortBy() {
        return SortBy.find(repository.getStringAsInteger(R.string.sort_by_key, SortBy.STRENGTH.ordinal()));
    }

    public GroupBy getGroupBy() {
        return GroupBy.find(repository.getStringAsInteger(R.string.group_by_key, GroupBy.NONE.ordinal()));
    }

    public GraphLegend getChannelGraphLegend() {
        return GraphLegend.find(repository.getStringAsInteger(R.string.channel_graph_legend_key, GraphLegend.HIDE.ordinal()), GraphLegend.HIDE);
    }

    public GraphLegend getTimeGraphLegend() {
        return GraphLegend.find(repository.getStringAsInteger(R.string.time_graph_legend_key, GraphLegend.LEFT.ordinal()), GraphLegend.LEFT);
    }

    public WiFiBand getWiFiBand() {
        return WiFiBand.find(repository.getStringAsInteger(R.string.wifi_band_key, WiFiBand.GHZ2.ordinal()));
    }

    public ThemeStyle getThemeStyle() {
        return ThemeStyle.find(repository.getStringAsInteger(R.string.theme_key, ThemeStyle.DARK.ordinal()));
    }

    public void toggleWiFiBand() {
        repository.save(R.string.wifi_band_key, getWiFiBand().toggle().ordinal());
    }

    public String getCountryCode() {
        String countryCode = context.getResources().getConfiguration().locale.getCountry();
        return repository.getString(R.string.country_code_key, countryCode);

        //TelephonyManager  telephonyManager=(TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //String sim_country_code = telephonyManager.getSimCountryIso();


       /*TelephonyManager manager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String subscriberID=manager.getDeviceId();
        if(subscriberID==null) {
          countryCode =context.getResources().getConfiguration().locale.getCountry();
            return repository.getString(R.string.country_code_key, countryCode);
        }
        else

            countryCode = manager.getNetworkCountryIso();
            return repository.getString(R.string.country_code_key, countryCode);

*/
    }

    public NavigationMenu getStartMenu() {
        return NavigationMenu.find(repository.getStringAsInteger(R.string.start_menu_key, NavigationMenu.ACCESS_POINTS.ordinal()));
    }
}
