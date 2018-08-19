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
package com.uni.wifianalyzer.vendor;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdChoicesView;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.uni.wifianalyzer.MainContext;
import com.uni.wifianalyzer.R;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.uni.wifianalyzer.CheckPurchase;

import com.uni.wifianalyzer.NetworkConnectivity;
import com.uni.wifianalyzer.vendor.model.VendorService;

import java.util.ArrayList;
import java.util.List;

import static com.uni.wifianalyzer.MainActivity.AdsEnable;

public class VendorFragment extends ListFragment {
    private VendorAdapter vendorAdapter;

    private String adPlacementId = "1755828284679452_1988555198073425";
    private NativeAd nativeAd;
    private LinearLayout nativeAdContainer;
    private LinearLayout adView;
    public Context pkkv;
     public com.google.android.gms.ads.AdView Gads;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.vendor_content, container, false);
        VendorService vendorService = MainContext.INSTANCE.getVendorService();
        vendorAdapter = new VendorAdapter(getActivity(), vendorService.findAll());
        setListAdapter(vendorAdapter);

        nativeAdContainer = (LinearLayout) view.findViewById(R.id.native_ad_container);

           Gads = (AdView) view.findViewById(R.id.adViewbanner);
        Gads.setVisibility(View.GONE);
        pkkv = getActivity();

        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        if (NetworkConnectivity.isConnected(getActivity().getApplicationContext())) {
           
            CheckPurchase.checkpurchases(getActivity().getApplicationContext());
            if (!getActivity().getApplicationContext().getSharedPreferences("Premium", Context.MODE_PRIVATE).getBoolean("IsPremium", true)) {
               
                showNativeAd();
            }
          
        }

            
        

    }

    private void showNativeAd() {
        nativeAd = new NativeAd(getContext(), adPlacementId);
        nativeAd.setAdListener(new AdListener() {

            @Override
            public void onError(Ad ad, AdError error) {
                
                  Gads.setVisibility(View.VISIBLE);



                Gads.setAdListener(newAdlistner);
                Gads.loadAd(new AdRequest.Builder()



                        .build());
                // Ad error callback
            }

            @Override
            public void onAdLoaded(Ad ad) {

                if (nativeAd != null) {
                    nativeAd.unregisterView();
                }



                // Add the Ad view into the ad container.

                LayoutInflater inflater = LayoutInflater.from(pkkv);
                adView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout,
                        nativeAdContainer, false);
                nativeAdContainer.addView(adView);

                // Create native UI using the ad metadata.
                ImageView nativeAdIcon = (ImageView) adView.findViewById(R.id.native_ad_icon);
                TextView nativeAdTitle = (TextView) adView.findViewById(R.id.native_ad_title);
                MediaView nativeAdMedia = (MediaView) adView.findViewById(R.id.native_ad_media);
                TextView nativeAdSocialContext = (TextView) adView.findViewById(R.id
                        .native_ad_social_context);
                TextView nativeAdBody = (TextView) adView.findViewById(R.id.native_ad_body);
                Button nativeAdCallToAction = (Button) adView.findViewById(R.id
                        .native_ad_call_to_action);

                // Set the Text.
                nativeAdTitle.setText(nativeAd.getAdTitle());
                nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
                nativeAdBody.setText(nativeAd.getAdBody());
                nativeAdCallToAction.setText(nativeAd.getAdCallToAction());

                // Download and display the ad icon.
                NativeAd.Image adIcon = nativeAd.getAdIcon();
                NativeAd.downloadAndDisplayImage(adIcon, nativeAdIcon);

                // Download and display the cover image.
                nativeAdMedia.setNativeAd(nativeAd);

                // Add the AdChoices icon
                LinearLayout adChoicesContainer = (LinearLayout) adView.findViewById(R.id
                        .ad_choices_container);
                AdChoicesView adChoicesView = new AdChoicesView(getContext(), nativeAd, true);
                adChoicesContainer.addView(adChoicesView);

                // Register the Title and CTA button to listen for clicks.
                List<View> clickableViews = new ArrayList<>();
                clickableViews.add(nativeAdTitle);
                clickableViews.add(nativeAdCallToAction);
                nativeAd.registerViewForInteraction(nativeAdContainer, clickableViews);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // On logging impression callback
            }
        });

        // Request an ad
        nativeAd.loadAd();

    }


    @Override
    public void onDestroy() {
        if (nativeAd != null) {
            nativeAd.destroy();
        }
         if (Gads!=null){
            Gads.destroy();
        }

        super.onDestroy();
    }
      com.google.android.gms.ads.AdListener newAdlistner = (new  com.google.android.gms.ads.AdListener(){

        @Override
        public void onAdLoaded() {
           // spinner.setVisibility(View.GONE);
        }

        @Override
        public void onAdFailedToLoad(int errorCode) {
            // Code to be executed when an ad request fails.
           // Gads.setVisibility(View.GONE);

           // textnoads.setVisibility(View.VISIBLE);
           // spinner.setVisibility(View.GONE);
        }

        @Override
        public void onAdOpened() {
            // Code to be executed when an ad opens an overlay that
            // covers the screen.
        }

        @Override
        public void onAdLeftApplication() {
            // Code to be executed when the user has left the app.
        }

        @Override
        public void onAdClosed() {
            // Code to be executed when when the user is about to return
            // to the app after tapping on an ad.
        }
    });

    @Override
    public void onResume() {


        super.onResume();
        VendorService vendorService = MainContext.INSTANCE.getVendorService();
        vendorAdapter.setVendors(vendorService.findAll());
    }

    }


