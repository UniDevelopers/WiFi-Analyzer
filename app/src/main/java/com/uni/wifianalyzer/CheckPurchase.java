package com.uni.wifianalyzer;
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

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.uni.wifianalyzer.util.IabHelper;
import com.uni.wifianalyzer.util.IabResult;
import com.uni.wifianalyzer.util.Inventory;
import com.uni.wifianalyzer.util.Purchase;


public class CheckPurchase {

    static final String ITEM_SKU_SMALL =Constants.SKU_NAME;
    public static boolean isPremium = false;
    static IabHelper mHelper;
    //    static final String ITEM_SKU_SMALL = "com.test.purchased";
    static SharedPreferences premimuPref;
    static SharedPreferences.Editor editor;
    static IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d("onQueryInventory: ", result.isSuccess() + "");
//            Log.d("onQueryInventory: ", result.getMessage() + " "+ result.getResponse());
//            Log.d("onQueryInventory: ", inventory + "");
            try{
                if (mHelper == null) return;
                if (result.isFailure()) {
                } else {
                    Purchase premiumPurchase = inventory.getPurchase(ITEM_SKU_SMALL);
                    if (inventory.hasPurchase(ITEM_SKU_SMALL)) {
                        boolean pre = true;
                    }
                    boolean premium = (premiumPurchase != null && verifyDeveloperPayload(premiumPurchase));
                    //prefManager.putPremiumInfo(premium);
                    editor =premimuPref.edit();
                    editor.putBoolean("IsPremium",premium);
                    editor.apply();
                }
            }catch (Exception e){

            }

        }
    };

    public static void checkpurchases(Context context) {

        try {
            premimuPref =context.getApplicationContext().getSharedPreferences("Premium",Context.MODE_PRIVATE); //new Preferences(context);
            String base64EncodedPublicKey = licensekey();
            mHelper = new IabHelper(context, base64EncodedPublicKey);
            mHelper.enableDebugLogging(true);
            mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
                public void onIabSetupFinished(IabResult result) {
                    if (!result.isSuccess()) {
                        return;
                    }
                    if (mHelper == null) return;
                    try {
                        mHelper.queryInventoryAsync(mGotInventoryListener);
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){

        }

    }

    static boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();
        return true;
    }

    private static String licensekey() {
        return Constants.apilicence;
    }

    public static void dispose() {
        if (mHelper != null) {
            mHelper.disposeWhenFinished();
            mHelper = null;
        }
    }
}