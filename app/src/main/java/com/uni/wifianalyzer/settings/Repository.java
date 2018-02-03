
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

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.content.res.Resources;
import android.preference.PreferenceManager;

import com.uni.wifianalyzer.MainActivity;
import com.uni.wifianalyzer.MainContext;
import com.uni.wifianalyzer.R;

class Repository {
    protected void initializeDefaultValues() {
        MainActivity mainActivity = MainContext.INSTANCE.getMainActivity();
        PreferenceManager.setDefaultValues(mainActivity, R.xml.preferences, false);
    }

    protected void registerOnSharedPreferenceChangeListener(OnSharedPreferenceChangeListener onSharedPreferenceChangeListener) {
        getSharedPreferences().registerOnSharedPreferenceChangeListener(onSharedPreferenceChangeListener);
    }

    protected void save(int key, int value) {
        MainActivity mainActivity = MainContext.INSTANCE.getMainActivity();
        save(mainActivity.getString(key), "" + value);
    }

    private void save(String key, String value) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.apply();
    }

    protected int getStringAsInteger(int key, int defaultValue) {
        try {
            return Integer.parseInt(getString(key, "" + defaultValue));
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected String getString(int key, String defaultValue) {
        MainActivity mainActivity = MainContext.INSTANCE.getMainActivity();
        String keyValue = mainActivity.getString(key);
        try {
            return getSharedPreferences().getString(keyValue, defaultValue);
        } catch (Exception e) {
            save(keyValue, defaultValue);
            return defaultValue;
        }
    }

    protected int getResourceInteger(int key) {
        Resources resources = MainContext.INSTANCE.getResources();
        return resources.getInteger(key);
    }

    protected int getInteger(int key, int defaultValue) {
        MainActivity mainActivity = MainContext.INSTANCE.getMainActivity();
        String keyValue = mainActivity.getString(key);
        try {
            return getSharedPreferences().getInt(keyValue, defaultValue);
        } catch (Exception e) {
            save(keyValue, "" + defaultValue);
            return defaultValue;
        }
    }

    private SharedPreferences getSharedPreferences() {
        MainActivity mainActivity = MainContext.INSTANCE.getMainActivity();
        return PreferenceManager.getDefaultSharedPreferences(mainActivity);
    }
}
