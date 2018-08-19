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
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.uni.wifianalyzer.settings.Settings;
import com.uni.wifianalyzer.settings.ThemeStyle;
import com.uni.wifianalyzer.util.IabHelper;
import com.uni.wifianalyzer.util.IabResult;
import com.uni.wifianalyzer.util.Purchase;

public class RemoveAds extends AppCompatActivity {

    private static  final String ITEM_SKU_SMALL = Constants.SKU_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Settings settings = MainContext.INSTANCE.getSettings();
        ThemeStyle themeStyle = settings.getThemeStyle();
        setTheme(themeStyle.themeAppCompatStyle());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remove_ads);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(true);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        TextView textview =findViewById(R.id.restartapp);

        textview.setVisibility(View.INVISIBLE);
        CheckPurchase.checkpurchases(getApplicationContext());
        if (getApplicationContext().getSharedPreferences("Premium", Context.MODE_PRIVATE).getBoolean("IsPremium", true)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setIcon(R.mipmap.ic_launcher);
            builder.setTitle("Wow!!");
            builder.setMessage("You are Awesome! You already purchased this item");
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {


                    dialog.cancel();
                    onBackPressed();

                }
            });
            builder.show();}
         else {
            Button purches= findViewById(R.id.purchesbutton);
            purches.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textview =findViewById(R.id.restartapp);
                    textview.setVisibility(View.VISIBLE);


                    if (mHelper != null) mHelper.flagEndAsync();
                    try {
                        assert mHelper != null;
                        mHelper.launchPurchaseFlow(RemoveAds.this, ITEM_SKU_SMALL, 10001,
                                mPurchaseFinishedListener, "donateSmallPurchase");
                    } catch (IabHelper.IabAsyncInProgressException e) {
                        e.printStackTrace();
                    }
                }
            });


        }






    }

    IabHelper mHelper;

    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener
            = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result,
                                          Purchase purchase) {
            if (purchase != null) {
                if (purchase.getSku().contentEquals(ITEM_SKU_SMALL)) {
                    Message("DONATE_SMALL_THANKS");
                }
            } else if (result.getResponse() == 7) {
                Toast.makeText(getApplicationContext(), "Purchase Information", Toast.LENGTH_SHORT).show();
            } else if (result.getResponse() == 6) {
                Toast.makeText(getApplicationContext(), "Purchase Cancel", Toast.LENGTH_SHORT).show();
            } else if (result.getResponse() == 0) {
                Toast.makeText(getApplicationContext(), "Purchase Success", Toast.LENGTH_SHORT).show();
                Message("DONATE_SMALL_THANKS");
            }
        }
    };

    @Override
    public void onStart() {
        super.onStart();
        String base64EncodedPublicKey = apilicense();
        mHelper = new IabHelper(this, base64EncodedPublicKey);
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                if (!result.isSuccess()) {
                } else {
                }
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,
                                 Intent data) {
        if (!mHelper.handleActivityResult(requestCode,
                resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
        } else {
        }
    }

    private void Message(String message) {

        final Dialog builder = new Dialog(this);
        try{
            builder.requestWindowFeature(Window.FEATURE_NO_TITLE);
            if( builder.getWindow()!= null) {
                builder.getWindow().setBackgroundDrawable(
                        new ColorDrawable(android.graphics.Color.TRANSPARENT));
            }
            builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                @Override
                public void onDismiss(DialogInterface dialogInterface) {
                    builder.dismiss();
                }
            });

//            ImageView imageView = new ImageView(this);
//            if (message.contentEquals("DONATE_SMALL_THANKS")) {
//                imageView.setImageResource(R.drawable.ic_crown);
//            } else {
//                imageView.setImageResource(R.drawable.ic_info_black_24dp);
//            }
//        builder.addContentView(imageView, new RelativeLayout.LayoutParams(
//                measureWidth,
//                measureHeight));
            builder.show();
        }catch (Exception e){

        }

    }
    @Override
    public void onBackPressed() {


        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mHelper != null) try {
            mHelper.dispose();
        } catch (IabHelper.IabAsyncInProgressException e) {
            e.printStackTrace();
        }
        mHelper = null;
    }

    private String apilicense() {
        return Constants.apilicence;
    }

}