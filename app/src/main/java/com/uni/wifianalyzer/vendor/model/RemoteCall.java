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
package com.uni.wifianalyzer.vendor.model;

import android.os.AsyncTask;

import com.uni.wifianalyzer.Logger;
import com.uni.wifianalyzer.MainContext;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

class RemoteCall extends AsyncTask<String, Void, String> {
    protected static final String MAX_VENDOR_LOOKUP = "http://api.macvendors.com/%s";

    protected String doInBackground(String... params) {
        if (params == null || params.length < 1 || StringUtils.isBlank(params[0])) {
            return StringUtils.EMPTY;
        }
        String macAddress = params[0];
        String request = String.format(MAX_VENDOR_LOOKUP, macAddress);
        BufferedReader bufferedReader = null;
        try {
            URLConnection urlConnection = getURLConnection(request);
            bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                response.append(line);
            }
            return new RemoteResult(macAddress, response.toString()).toJson();
        } catch (Exception e) {
            return StringUtils.EMPTY;
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    protected URLConnection getURLConnection(String request) throws IOException {
        return new URL(request).openConnection();
    }

    @Override
    protected void onPostExecute(String result) {
        if (StringUtils.isNotBlank(result)) {
            Logger logger = MainContext.INSTANCE.getLogger();
            try {
                RemoteResult remoteResult = new RemoteResult(result);
                String macAddress = remoteResult.getMacAddress();
                String vendorName = remoteResult.getVendorName();
                if (StringUtils.isNotBlank(macAddress)) {
                    logger.info(this, macAddress + " " + vendorName);
                    Database database = MainContext.INSTANCE.getDatabase();
                    database.insert(macAddress, vendorName);
                }
            } catch (JSONException e) {
                logger.error(this, result, e);
            }
        }
    }
}
