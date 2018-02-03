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
package com.uni.wifianalyzer.wifi.band;

import android.support.annotation.NonNull;

import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WiFiChannel implements Comparable<WiFiChannel> {
    public static final WiFiChannel UNKNOWN = new WiFiChannel();

    private static final int ALLOWED_RANGE = WiFiChannels.FREQUENCY_SPREAD / 2;

    private final int channel;
    private final int frequency;

    private WiFiChannel() {
        channel = frequency = 0;
    }

    public WiFiChannel(int channel, int frequency) {
        this.channel = channel;
        this.frequency = frequency;
    }

    public int getChannel() {
        return channel;
    }

    public int getFrequency() {
        return frequency;
    }

    public boolean isInRange(int frequency) {
        return frequency >= this.frequency - ALLOWED_RANGE && frequency <= this.frequency + ALLOWED_RANGE;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;

        if (other == null || getClass() != other.getClass()) return false;

        WiFiChannel otherDetail = (WiFiChannel) other;
        return new EqualsBuilder()
            .append(getChannel(), (otherDetail).getChannel())
            .append(getFrequency(), (otherDetail).getFrequency())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getChannel())
            .append(getFrequency())
            .toHashCode();
    }

    @Override
    public int compareTo(@NonNull WiFiChannel another) {
        return new CompareToBuilder()
            .append(getChannel(), another.getChannel())
            .append(getFrequency(), another.getFrequency())
            .toComparison();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
