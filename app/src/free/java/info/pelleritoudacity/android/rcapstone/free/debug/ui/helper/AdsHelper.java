package info.pelleritoudacity.android.rcapstone.free.debug.ui.helper;

import android.content.Context;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.view.View;

import com.google.android.exoplayer2.util.Util;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;

import info.pelleritoudacity.android.rcapstone.R;

public class AdsHelper {

    private final Context mContext;
    private final AdView mAdView;
    private final View mContainer;
    private InterstitialAd mInterstitialAd;
    private boolean isInitMobile;

    public AdsHelper(Context context, View container, AdView mAdView) {
        mContext = context;
        mContainer = container;
        this.mAdView = mAdView;
    }


    public void initAds() {

        MobileAds.initialize(mContext, mContext.getResources().getString(R.string.ads_app_id));
        isInitMobile = true;

        startBannerAd();

        adsErrorListener();

    }

    public void initInterstitialAd() {

        if (!isInitMobile) {
            MobileAds.initialize(mContext, mContext.getResources().getString(R.string.ads_app_id));
        }
        mInterstitialAd= new InterstitialAd(mContext);

        mInterstitialAd.setAdUnitId(mContext.getResources().getString(R.string.ad_unit_id));

        startInterstitialAd();

        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                startInterstitialAd();
            }
        });

    }

    private void adsErrorListener() {
        mAdView.setAdListener(new AdListener() {
            @Override
            public void onAdFailedToLoad(int i) {
                switch (i) {

                    case AdRequest.ERROR_CODE_INTERNAL_ERROR:
                        snackAds(R.string.error_load_banner_internal_error);
                        break;

                    case AdRequest.ERROR_CODE_INVALID_REQUEST:
                        snackAds(R.string.error_load_banner_invalid_request);
                        break;
                    case AdRequest.ERROR_CODE_NETWORK_ERROR:
                        snackAds(R.string.error_load_banner_network_error);
                        break;
                    case AdRequest.ERROR_CODE_NO_FILL:
                        snackAds(R.string.error_load_banner_no_fill);
                        break;

                }

                super.onAdFailedToLoad(i);
            }

        });
    }

    private void startBannerAd(){
        AdRequest adRequest = new AdRequest.Builder()
                .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
                .build();

        mAdView.loadAd(adRequest);

    }

    private void startInterstitialAd() {

        if (!mInterstitialAd.isLoading() && !mInterstitialAd.isLoaded()) {
            AdRequest adRequest = new AdRequest.Builder().build();
            mInterstitialAd.loadAd(adRequest);
        }

    }

    public void showInterstitial() {
        if (mInterstitialAd != null && mInterstitialAd.isLoaded()) {
            mInterstitialAd.show();
        }
    }


    public void snackAds(int resource) {
        Snackbar snackbar = Snackbar.make(mContainer, resource, Snackbar.LENGTH_LONG);
        if (Util.SDK_INT >= 23) {
            snackbar.addCallback(new Snackbar.Callback() {
                @Override
                public void onDismissed(Snackbar transientBottomBar, int event) {
                    mAdView.setVisibility(View.VISIBLE);
                    super.onDismissed(transientBottomBar, event);
                }

                @Override
                public void onShown(Snackbar sb) {
                    mAdView.setVisibility(View.INVISIBLE);
                    super.onShown(sb);
                }
            }).show();

        } else {
            mAdView.setVisibility(View.INVISIBLE);
            snackbar.show();

            Handler h = new Handler();
            h.postDelayed(() -> mAdView.setVisibility(View.VISIBLE), 3000);
        }

    }

}


