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

import android.app.ActionBar;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.uni.wifianalyzer.MainContext;
import com.uni.wifianalyzer.R;

public class SettingActivity extends PreferenceActivity {

   // private static final String KEEP_VAR = "pref_keep_on";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Settings settings = MainContext.INSTANCE.getSettings();
        ThemeStyle themeStyle = settings.getThemeStyle();
        setTheme(themeStyle.themeDeviceDefaultStyle());

        super.onCreate(savedInstanceState);

        getFragmentManager().beginTransaction().replace(android.R.id.content, new SettingPreferenceFragment()).commit();
        //loadKeepScreen();
        ActionBar actionBar = getActionBar();

        ColorDrawable colorDrawable = new ColorDrawable(Color.parseColor("#69a727"));
        //actionBar.setBackgroundDrawable(colorDrawable);
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(colorDrawable);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
  /*  private void loadKeepScreen() {
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(KEEP_VAR, false)) {
            // getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);


        } else {
            // Toast.makeText(SettingActivity.this, "Restart App to Apply Changes", Toast.LENGTH_LONG).show();
            //  getWindow().clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        }

    }*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class SettingPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
