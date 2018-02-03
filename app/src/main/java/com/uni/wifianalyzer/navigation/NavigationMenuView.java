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
package com.uni.wifianalyzer.navigation;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.view.Menu;
import android.view.MenuItem;

import com.uni.wifianalyzer.R;

public class NavigationMenuView {
    private final NavigationView navigationView;
    private NavigationMenu currentNavigationMenu;

    public NavigationMenuView(@NonNull Activity activity, @NonNull NavigationMenu currentNavigationMenu) {
        navigationView = (NavigationView) activity.findViewById(R.id.nav_view);

        populateNavigationMenu();
        setCurrentNavigationMenu(currentNavigationMenu);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) activity);
    }

    private void populateNavigationMenu() {
        Menu menu = navigationView.getMenu();
        for (NavigationGroup navigationGroup : NavigationGroup.values()) {
            for (NavigationMenu navigationMenu : navigationGroup.navigationMenu()) {
                MenuItem menuItem = menu.add(navigationGroup.ordinal(), navigationMenu.ordinal(), navigationMenu.ordinal(), navigationMenu.getTitle());
                menuItem.setIcon(navigationMenu.getIcon());
            }
        }
    }

    public MenuItem getCurrentMenuItem() {
        return navigationView.getMenu().getItem(getCurrentNavigationMenu().ordinal());
    }

    public NavigationMenu getCurrentNavigationMenu() {
        return currentNavigationMenu;
    }

    public void setCurrentNavigationMenu(@NonNull NavigationMenu navigationMenu) {
        this.currentNavigationMenu = navigationMenu;
        Menu menu = navigationView.getMenu();
        for (int i = 0; i < menu.size(); i++) {
            MenuItem item = menu.getItem(i);
            item.setCheckable(navigationMenu.ordinal() == i);
            item.setChecked(navigationMenu.ordinal() == i);
        }
    }

    NavigationView getNavigationView() {
        return navigationView;
    }

}
