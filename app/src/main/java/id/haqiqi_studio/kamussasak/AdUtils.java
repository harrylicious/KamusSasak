package id.haqiqi_studio.kamussasak;

import android.content.Context;
import android.widget.Toast;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;

/**
 * Created by Gentong on 27/03/2018.
 */

public class AdUtils {
    private InterstitialAd mInterstitialAd;

    public InterstitialAd newInterstitialAd(Context context, String str) {
        InterstitialAd interstitialAd = new InterstitialAd(context);
        interstitialAd.setAdUnitId(str);
        interstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdLoaded() {
            }

            @Override
            public void onAdFailedToLoad(int errorCode) {
            }

            @Override
            public void onAdClosed() {
                // Proceed to the next level.
                goToNextLevel();
            }
        });
        return interstitialAd;
    }

    public void showInterstitial(Context context) {
        // Show the ad if it's ready. Otherwise toast and reload the ad.
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {
            Toast.makeText(context, "Ad did not load", Toast.LENGTH_SHORT).show();
            goToNextLevel();
        }
    }

    public void loadInterstitial(Context context, String str) {
        mInterstitialAd = newInterstitialAd(context, str);
        // Disable the next level button and load the ad.
        AdRequest adRequest = new AdRequest.Builder()
                .setRequestAgent("android_studio:ad_template").build();
        mInterstitialAd.loadAd(adRequest);

        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        } else {

        }
    }

    public void goToNextLevel() {
        // Show the next level and reload the ad to prepare for the level after.
        //mInterstitialAd = newInterstitialAd();
        //loadInterstitial();
    }
}
