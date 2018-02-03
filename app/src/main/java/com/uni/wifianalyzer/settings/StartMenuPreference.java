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
import android.util.AttributeSet;

import com.uni.wifianalyzer.navigation.NavigationGroup;
import com.uni.wifianalyzer.navigation.NavigationMenu;

import java.util.ArrayList;
import java.util.List;

public class StartMenuPreference extends CustomPreference {
    public StartMenuPreference(@NonNull Context context, AttributeSet attrs) {
        super(context, attrs, getData(context), getDefault());
    }

    private static List<Data> getData(@NonNull Context context) {
        List<Data> result = new ArrayList<>();
        for (NavigationMenu navigationMenu : NavigationGroup.GROUP_FEATURE.navigationMenu()) {
            result.add(new Data("" + navigationMenu.ordinal(), context.getString(navigationMenu.getTitle())));
        }
        return result;
    }

    private static String getDefault() {
        return "" + NavigationGroup.GROUP_FEATURE.navigationMenu()[0].ordinal();
    }

}
