/*
 *
 * ______                _____ _
 * | ___ \              /  ___| |
 * | |_/ /___ __ _ _ __ \ `--.| |_ ___  _ __   ___
 * |    // __/ _` | '_ \ `--. \ __/ _ \| '_ \ / _ \
 * | |\ \ (_| (_| | |_) /\__/ / || (_) | | | |  __/
 * \_| \_\___\__,_| .__/\____/ \__\___/|_| |_|\___|
 *                | |
 *                |_|
 *
 * Copyright (C) 2018 Benedetto Pellerito
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;

import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T3Operation;
import info.pelleritoudacity.android.rcapstone.media.MediaSession;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.rest.SubRedditExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditFragment;
import info.pelleritoudacity.android.rcapstone.ui.view.SubRedditTab;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.PermissionUtil.RequestPermissionExtStorage;


public class SubRedditActivity extends BaseActivity
        implements SubRedditExecute.RestSubReddit, ActivityCompat.OnRequestPermissionsResultCallback, SubRedditTab.OnTabListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.tab_layout)
    public TabLayout mTabLayout;

    private Context mContext;
    private String mRedditCategory;
    private String mRedditTarget;
    public static MediaSessionCompat sMediaSessionCompat = null;
    private SubRedditTab mSubRedditTab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_subreddit);
        super.onCreate(savedInstanceState);

        mContext = SubRedditActivity.this;

        if (Util.SDK_INT > 23) {
            RequestPermissionExtStorage(SubRedditActivity.this);
            PermissionUtil.isDeniedPermissionExtStorage(SubRedditActivity.this);
        }


        if (savedInstanceState == null) {

            if (TextUtils.isEmpty(mRedditCategory)) {
                Intent intentCategory = getIntent();
                if (intentCategory != null) {
                    mRedditCategory = intentCategory.getStringExtra(Costant.EXTRA_SUBREDDIT_CATEGORY);
                    mRedditTarget = intentCategory.getStringExtra(Costant.EXTRA_SUBREDDIT_TARGET);
                    createTabLayout();
                }
            }

        } else {
            mRedditCategory = savedInstanceState.getString(Costant.EXTRA_SUBREDDIT_CATEGORY);
            mRedditTarget = savedInstanceState.getString(Costant.EXTRA_SUBREDDIT_TARGET);

            createTabLayout();

        }

        if (mContext != null) {
            initRest(mRedditCategory, mRedditTarget, NetworkUtil.isOnline(mContext));

        }

    }

    @Override
    public void onRestSubReddit(T3 listenerData) {

        if (listenerData != null) {
            T3Operation data = new T3Operation(getApplicationContext(), listenerData);
            if (data.saveData(mRedditCategory, mRedditTarget)) {
                startFragment(mRedditCategory, mRedditTarget);
            } else {
                Snackbar.make(findViewById(R.id.subreddit_container), R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public void onErrorSubReddit(Throwable t) {

    }

    @Override
    public void tabSelected(int position, String category) {
        if (mContext != null) {
            mRedditTarget = null;
            mRedditCategory = category;
            Preference.setLastCategory(mContext,  mRedditCategory);
            initRest(category, null, NetworkUtil.isOnline(mContext));

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (Costant.IS_MEDIA_SESSION) {
            MediaSession.removeNotification(mContext);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Costant.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   Preference.setWriteExternalStorage(mContext,true);
                   Preference.setRequestPermission(mContext,false);

                }
            }
        }
    }

    @Override
    public boolean shouldShowRequestPermissionRationale(@NonNull String permission) {
        if ((!Objects.equals(permission, Manifest.permission.WRITE_EXTERNAL_STORAGE)) ||
                (PermissionUtil.isPermissionExtStorage(mContext) ||
                        Preference.isRequestPermission(mContext))) {
            return super.shouldShowRequestPermissionRationale(permission);
        }
        ActivityCompat.requestPermissions(SubRedditActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                Costant.PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE);
        Preference.setRequestPermission(mContext,true);
        return super.shouldShowRequestPermissionRationale(permission);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(Costant.EXTRA_SUBREDDIT_CATEGORY, mRedditCategory);
        outState.putString(Costant.EXTRA_SUBREDDIT_TARGET, mRedditTarget);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onBackPressed() {
        if (mSubRedditTab != null) {
            String historyCategory = mSubRedditTab.getHistoryPosition();
            Timber.d("SUBREDDIT BACK %s", mSubRedditTab.getHistorySize());
            if (!TextUtils.isEmpty(historyCategory)) {
                mSubRedditTab.positionSelected(historyCategory);
            } else {
                super.onBackPressed();
            }

        } else {
            super.onBackPressed();

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

    private void startFragment(String link, String target) {
        if (!getSupportFragmentManager().isStateSaved()) {
            SubRedditFragment subRedditFragment = SubRedditFragment.newInstance(link, target);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_subreddit_container, subRedditFragment).commit();
        }
    }

    private void initRest(String link, String target, boolean stateNetworkOnline) {
        if (!TextUtils.isEmpty(link)) {
            if (stateNetworkOnline) {
                new SubRedditExecute(mContext, link).getData(this);
            } else {
                startFragment(link, target);

            }

        }
    }

    private void createTabLayout() {
        mSubRedditTab = new SubRedditTab(this, mTabLayout, getTabArrayList());
        mSubRedditTab.initTab();
        mSubRedditTab.positionSelected(mRedditCategory);
    }

}
