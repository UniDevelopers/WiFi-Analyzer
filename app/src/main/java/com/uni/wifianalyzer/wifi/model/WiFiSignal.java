/*
 *WiFiAnalyzer Copyright (C) 2017 VREM Software Development <VREMSoftwareDevelopment@gmail.com>"
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
package com.uni.wifianalyzer.wifi.model;

import android.support.annotation.NonNull;

import com.uni.wifianalyzer.wifi.band.WiFiBand;
import com.uni.wifianalyzer.wifi.band.WiFiChannel;
import com.uni.wifianalyzer.wifi.band.WiFiWidth;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class WiFiSignal {
    public static final WiFiSignal EMPTY = new WiFiSignal(0, 0, WiFiWidth.MHZ_20, 0);

    private final int primaryFrequency;
    private final int centerFrequency;
    private final WiFiWidth wiFiWidth;
    private final WiFiBand wiFiBand;
    private final int level;

    public WiFiSignal(int primaryFrequency, int centerFrequency, @NonNull WiFiWidth wiFiWidth, int level) {
        this.primaryFrequency = primaryFrequency;
        this.centerFrequency = centerFrequency;
        this.wiFiWidth = wiFiWidth;
        this.level = level;
        this.wiFiBand = WiFiBand.findByFrequency(primaryFrequency);
    }

    public int getPrimaryFrequency() {
        return primaryFrequency;
    }

    public int getCenterFrequency() {
        return centerFrequency;
    }

    public int getFrequencyStart() {
        return getCenterFrequency() - getWiFiWidth().getFrequencyWidthHalf();
    }

    public int getFrequencyEnd() {
        return getCenterFrequency() + getWiFiWidth().getFrequencyWidthHalf();
    }

    public WiFiBand getWiFiBand() {
        return wiFiBand;
    }

    public WiFiWidth getWiFiWidth() {
        return wiFiWidth;
    }

    public WiFiChannel getPrimaryWiFiChannel() {
        return getWiFiBand().getWiFiChannels().getWiFiChannelByFrequency(getPrimaryFrequency());
    }

    public WiFiChannel getCenterWiFiChannel() {
        return getWiFiBand().getWiFiChannels().getWiFiChannelByFrequency(getCenterFrequency());
    }

    public int getLevel() {
        return level;
    }

    public Strength getStrength() {
        return Strength.calculate(level);
    }

    public double getDistance() {
        return WiFiUtils.calculateDistance(getPrimaryFrequency(), getLevel());
    }

    public boolean isInRange(int frequency) {
        return frequency >= getFrequencyStart() && frequency <= getFrequencyEnd();
    }

    @NonNull
    public String getChannelDisplay() {
        int primaryChannel = getPrimaryWiFiChannel().getChannel();
        int centerChannel = getCenterWiFiChannel().getChannel();
        String channel = "" + primaryChannel;
        if (primaryChannel != centerChannel) {
            channel += "(" + centerChannel + ")";
        }
        return channel;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        return new EqualsBuilder()
            .append(getPrimaryFrequency(), ((WiFiSignal) other).getPrimaryFrequency())
            .append(getWiFiWidth(), ((WiFiSignal) other).getWiFiWidth())
            .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
            .append(getPrimaryFrequency())
            .append(getWiFiWidth())
            .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

}
