package com.khaledhoues.movies.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;

import com.gigamole.navigationtabstrip.NavigationTabStrip;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.khaledhoues.movies.R;
import com.khaledhoues.movies.adapters.CustomPagerAdapter;

public class MainActivity extends FragmentActivity {


    private InterstitialAd mInterstitialAd;
    private NavigationTabStrip navigationTabStrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MobileAds.initialize(this, "ca-app-pub-1246320260393950~5292157964");
        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-3940256099942544/1033173712");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
                // Code to be executed when an ad finishes loading.
                Log.e("AD", "onAdLoaded: LOADED");
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
                // Code to be executed when an ad request fails.
                Log.e("AD", "onAdFailedToLoad: " + errorCode);
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

        NavigationTabStrip navigationTabStrip = (NavigationTabStrip) findViewById(R.id.nts_top);
        navigationTabStrip.setTabIndex(0, true);

//        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
//        viewPager.setAdapter(new CustomPagerAdapter(this));

        ViewPager vpPager = (ViewPager) findViewById(R.id.viewpager);
        CustomPagerAdapter adapterViewPager = new CustomPagerAdapter(getSupportFragmentManager());
        vpPager.setAdapter(adapterViewPager);

        navigationTabStrip.setViewPager(vpPager);

//        getSupportFragmentManager().beginTransaction()
//                .add(R.id.content, new NewsFragment(), "News")
//                .addToBackStack("AppFrags")
//                .commit();

    }


    @Override
    public void onBackPressed() {
        if (mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Log.d("TAG", "The interstitial wasn't loaded yet.");
        }
        super.onBackPressed();
    }
}
