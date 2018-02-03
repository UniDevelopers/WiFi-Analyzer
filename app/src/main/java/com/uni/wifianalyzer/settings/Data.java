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

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.CompareToBuilder;

class Data implements Comparable<Data> {
    private final String code;
    private final String name;

    Data(String code, String name) {
        this.code = code;
        this.name = name;
    }

    protected String getCode() {
        return code;
    }

    protected String getName() {
        return name;
    }

    @Override
    public int compareTo(@NonNull Data another) {
        return new CompareToBuilder()
            .append(getName(), another.getName())
            .append(getCode(), another.getCode())
            .toComparison();
    }
}
