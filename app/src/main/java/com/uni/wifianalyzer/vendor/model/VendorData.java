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

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.ToStringBuilder;

class VendorData {
    private final String name;
    private final String mac;
    private final long id;

    VendorData(long id, @NonNull String name, @NonNull String mac) {
        this.id = id;
        this.name = name;
        this.mac = mac;
    }

    protected long getId() {
        return id;
    }

    protected String getName() {
        return name;
    }

    protected String getMac() {
        return mac;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}