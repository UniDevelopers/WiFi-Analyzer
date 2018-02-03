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
package com.uni.wifianalyzer.wifi.scanner;

import android.net.wifi.ScanResult;
import android.net.wifi.WifiConfiguration;
import android.net.wifi.WifiInfo;
import android.support.annotation.NonNull;

import com.uni.wifianalyzer.wifi.band.WiFiWidth;
import com.uni.wifianalyzer.wifi.model.WiFiConnection;
import com.uni.wifianalyzer.wifi.model.WiFiData;
import com.uni.wifianalyzer.wifi.model.WiFiDetail;
import com.uni.wifianalyzer.wifi.model.WiFiSignal;
import com.uni.wifianalyzer.wifi.model.WiFiUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Transformer {

    WiFiConnection transformWifiInfo(WifiInfo wifiInfo) {
        if (wifiInfo == null || wifiInfo.getNetworkId() == -1) {
            return WiFiConnection.EMPTY;
        }
        return new WiFiConnection(
            WiFiUtils.convertSSID(wifiInfo.getSSID()),
            wifiInfo.getBSSID(),
            WiFiUtils.convertIpAddress(wifiInfo.getIpAddress()),
            wifiInfo.getLinkSpeed());
    }

    List<String> transformWifiConfigurations(List<WifiConfiguration> configuredNetworks) {
        List<String> results = new ArrayList<>();
        if (configuredNetworks != null) {
            for (WifiConfiguration wifiConfiguration : configuredNetworks) {
                results.add(WiFiUtils.convertSSID(wifiConfiguration.SSID));
            }
        }
        return Collections.unmodifiableList(results);
    }

    List<WiFiDetail> transformCacheResults(List<CacheResult> cacheResults) {
        List<WiFiDetail> results = new ArrayList<>();
        if (cacheResults != null) {
            for (CacheResult cacheResult : cacheResults) {
                ScanResult scanResult = cacheResult.getScanResult();
                WiFiWidth wiFiWidth = getWiFiWidth(scanResult);
                int centerFrequency = getCenterFrequency(scanResult, wiFiWidth);
                WiFiSignal wiFiSignal = new WiFiSignal(scanResult.frequency, centerFrequency, wiFiWidth, cacheResult.getLevelAverage());
                WiFiDetail wiFiDetail = new WiFiDetail(scanResult.SSID, scanResult.BSSID, scanResult.capabilities, wiFiSignal);
                results.add(wiFiDetail);
            }
        }
        return Collections.unmodifiableList(results);
    }

    WiFiWidth getWiFiWidth(@NonNull ScanResult scanResult) {
        try {
            return WiFiWidth.find(getFieldValue(scanResult, Fields.channelWidth));
        } catch (Exception e) {
            return WiFiWidth.MHZ_20;
        }
    }

    int getCenterFrequency(@NonNull ScanResult scanResult, @NonNull WiFiWidth wiFiWidth) {
        try {
            int centerFrequency = getFieldValue(scanResult, Fields.centerFreq0);
            if (centerFrequency == 0) {
                centerFrequency = scanResult.frequency;
            } else if (isExtensionFrequency(scanResult, wiFiWidth, centerFrequency)) {
                centerFrequency = (centerFrequency + scanResult.frequency) / 2;
            }
            return centerFrequency;
        } catch (Exception e) {
            return scanResult.frequency;
        }
    }

    boolean isExtensionFrequency(@NonNull ScanResult scanResult, @NonNull WiFiWidth wiFiWidth, int centerFrequency) {
        return WiFiWidth.MHZ_40.equals(wiFiWidth) && Math.abs(scanResult.frequency - centerFrequency) >= WiFiWidth.MHZ_40.getFrequencyWidthHalf();
    }

    int getFieldValue(@NonNull ScanResult scanResult, @NonNull Fields field) throws NoSuchFieldException, IllegalAccessException {
        Field declaredField = scanResult.getClass().getDeclaredField(field.name());
        return (int) declaredField.get(scanResult);
    }

    WiFiData transformToWiFiData(List<CacheResult> cacheResults, WifiInfo wifiInfo, List<WifiConfiguration> configuredNetworks) {
        List<WiFiDetail> wiFiDetails = transformCacheResults(cacheResults);
        WiFiConnection wiFiConnection = transformWifiInfo(wifiInfo);
        List<String> wifiConfigurations = transformWifiConfigurations(configuredNetworks);
        return new WiFiData(wiFiDetails, wiFiConnection, wifiConfigurations);
    }

    enum Fields {
        centerFreq0,
        centerFreq1,
        channelWidth
    }

}
