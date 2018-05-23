package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;

import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.T3Operation;
import info.pelleritoudacity.android.rcapstone.media.MediaSession;
import info.pelleritoudacity.android.rcapstone.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.rest.SubRedditExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditFragment;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtils;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtils;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.PermissionUtils.RequestPermissionExtStorage;


public class SubRedditActivity extends BaseActivity
        implements SubRedditExecute.RestSubReddit, ActivityCompat.OnRequestPermissionsResultCallback {

    private Context mContext;
    private String mRedditCategory;
    public static MediaSessionCompat sMediaSessionCompat = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_subreddit);
        super.onCreate(savedInstanceState);

        mContext = SubRedditActivity.this;
        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        if (Util.SDK_INT > 23) {
            RequestPermissionExtStorage(SubRedditActivity.this);
            PermissionUtils.isDeniedPermissionExtStorage(SubRedditActivity.this);
        }


        Intent intentCategory = getIntent();

        if (intentCategory != null) {
            mRedditCategory = intentCategory.getStringExtra(Costants.EXTRA_SUBREDDIT_CATEGORY);
        }

        initializeRestSubReddit(mRedditCategory);

        if (!NetworkUtils.isOnline(getApplicationContext())) {
            startFragment(mRedditCategory);
        }

    }

    @Override
    public void onRestSubReddit(T3 listenerData) {

        if (listenerData != null) {
            T3Operation data = new T3Operation(getApplicationContext(), listenerData);
            data.saveData(mRedditCategory);

            if (data.saveData(mRedditCategory)) {
                startFragment(mRedditCategory);
            } else {
                Snackbar.make(findViewById(R.id.subreddit_container), R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onErrorSubReddit(Throwable t) {

    }

    private void startFragment(String link) {
        SubRedditFragment subRedditFragment = SubRedditFragment.newInstance(link);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_subreddit_container, subRedditFragment).commit();

    }

    private void initializeRestSubReddit(String link) {
        if (!TextUtils.isEmpty(link)) {
            new SubRedditExecute(link).getData(this);
        }
    }

    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            if (sMediaSessionCompat != null) {
                MediaButtonReceiver.handleIntent(sMediaSessionCompat, intent);
            }
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Costants.IS_MEDIA_SESSION) {
            MediaSession.removeNotification(mContext);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Costants.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    PrefManager.putBoolPref(mContext, R.string.pref_write_external_storage, true);
                    PrefManager.putBoolPref(mContext, R.string.pref_request_permission, false);
                }
            }
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        if ((!Objects.equals(permission, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                (PermissionUtils.isPermissionExtStorage(mContext) ||
                        PrefManager.isPref(mContext, R.string.pref_request_permission))) {
            return super.shouldShowRequestPermissionRationale(permission);
        }
        ActivityCompat.requestPermissions(SubRedditActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Costants.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        PrefManager.putBoolPref(mContext, R.string.pref_request_permission, true);
        return super.shouldShowRequestPermissionRationale(permission);
    }


}
