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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;

import com.google.android.exoplayer2.util.Util;

import java.util.Objects;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T3Operation;
import info.pelleritoudacity.android.rcapstone.data.rest.RefreshTokenExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.RestExecute;
import info.pelleritoudacity.android.rcapstone.media.MediaSession;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.rest.SubRedditExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditFragment;
import info.pelleritoudacity.android.rcapstone.ui.view.SubRedditTab;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

import static info.pelleritoudacity.android.rcapstone.utility.PermissionUtil.RequestPermissionExtStorage;
import static info.pelleritoudacity.android.rcapstone.utility.SessionUtil.getRedditSessionExpired;


public class SubRedditActivity extends BaseActivity
        implements SubRedditExecute.RestSubReddit, ActivityCompat.OnRequestPermissionsResultCallback,
        SubRedditTab.OnTabListener, SwipeRefreshLayout.OnRefreshListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.subreddit_container)
    public CoordinatorLayout mContainer;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.tab_layout)
    public TabLayout mTabLayout;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.swipe_refresh_subreddit)
    public SwipeRefreshLayout mRefreshLayout;

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

        mRefreshLayout.setOnRefreshListener(this);

        firstInit();

        if (Util.SDK_INT > 23) {
            RequestPermissionExtStorage(SubRedditActivity.this);
            PermissionUtil.isDeniedPermissionExtStorage(SubRedditActivity.this);
        }

        if (savedInstanceState == null) {

            if ((Util.SDK_INT > 24) && (getIntent() != null) && (getIntent().getAction() != null)) {

                switch (getIntent().getAction()) {

                    case Costant.ACTION_SHORTCUT_ALL:
                        mRedditCategory = Costant.SUBREDDIT_CATEGORY_ALL;
                        mRedditTarget = Costant.SUBREDDIT_TARGET_ALL;
                        break;

                    case Costant.ACTION_SHORTCUT_POPULAR:
                        mRedditCategory = Costant.SUBREDDIT_CATEGORY_ALL;
                        mRedditTarget = Costant.SUBREDDIT_TARGET_ALL;
                        break;

                    case Costant.ACTION_SHORTCUT_SEARCH:
                        break;

                }
            }

            if (TextUtils.isEmpty(mRedditTarget)) {

                if ((getIntent() != null) &&
                        (!TextUtils.isEmpty(getIntent().getStringExtra(Costant.EXTRA_SUBREDDIT_TARGET)))) {

                    mRedditCategory = getIntent().getStringExtra(Costant.EXTRA_SUBREDDIT_CATEGORY);
                    mRedditTarget = getIntent().getStringExtra(Costant.EXTRA_SUBREDDIT_TARGET);

                } else {

                    if (!TextUtils.isEmpty(Preference.getLastCategory(mContext))) {
                        mRedditCategory = Preference.getLastCategory(mContext);
                        mRedditTarget = Preference.getLastTarget(mContext);

                    } else {
                        if (getTabArrayList() != null) {
                            mRedditCategory = getTabArrayList().get(0);
                            mRedditTarget = null;
                        }
                    }

                }


            }


        } else {
            mRedditCategory = savedInstanceState.getString(Costant.EXTRA_SUBREDDIT_CATEGORY);
            mRedditTarget = savedInstanceState.getString(Costant.EXTRA_SUBREDDIT_TARGET);
        }

        createTabLayout();

        if (mContext != null) {
                mRefreshLayout.setRefreshing(true);
                onRefresh();

                Preference.setLastCategory(mContext, mRedditCategory);
                Preference.setLastTarget(mContext, mRedditTarget);
                initRest(mRedditCategory, mRedditTarget, NetworkUtil.isOnline(mContext));

        }

    }

    private void firstInit() {

        if (!Preference.isInsertPrefs(mContext)) {
            new RestExecute(getApplicationContext()).syncData();

        }

        initializeFirebaseDispatcherService();

        Preference.setVolumeMuted(mContext, Costant.IS_MUTED_AUDIO);

        if (Preference.isClearData(mContext)) {
            Snackbar.make(mContainer, R.string.text_dialog_confirm_reset, Snackbar.LENGTH_LONG).show();
            Preference.setClearData(mContext, false);
        }

        Intent intent = getIntent();
        if (intent != null) {
            int restore = intent.getIntExtra(Costant.EXTRA_RESTORE_MANAGE, 0);

            switch (restore) {
                case Costant.RESTORE_MANAGE_RESTORE:
                    Preference.setRestoreManage(mContext,
                            Costant.RESTORE_MANAGE_RESTORE);
                    startActivity(new Intent(this, SubManageActivity.class)
                            .putExtra(Costant.EXTRA_RESTORE_MANAGE, Costant.RESTORE_MANAGE_RESTORE));

                    break;
                case Costant.RESTORE_MANAGE_REDIRECT:
                    startActivity(new Intent(this, SubManageActivity.class)
                            .putExtra(Costant.EXTRA_RESTORE_MANAGE, Costant.RESTORE_MANAGE_REDIRECT));
                    break;

            }

            boolean isLogged = intent.getBooleanExtra(Costant.EXTRA_LOGIN_SUCCESS, false);
            boolean isLogout = intent.getBooleanExtra(Costant.EXTRA_LOGOUT_SUCCESS, false);

            if (isLogged) {
                Snackbar.make(mContainer,
                        R.string.text_login_success, Snackbar.LENGTH_LONG).show();

            } else if (isLogout) {
                Snackbar.make(mContainer,
                        R.string.text_logout_success, Snackbar.LENGTH_LONG).show();

            }


            if(intent.getBooleanExtra(Costant.EXTRA_ACTIVITY_SUBREDDIT_REFRESH,false)){
                mRefreshLayout.setRefreshing(true);
                onRefresh();
            }

        }
    }

    @Override
    public void onRestSubReddit(T3 listenerData) {

        if (listenerData != null) {
            T3Operation data = new T3Operation(getApplicationContext(), listenerData);
            if (data.saveData(mRedditCategory, mRedditTarget)) {
                startFragment(mRedditCategory, mRedditTarget);
               if(mRefreshLayout!=null){
                   mRefreshLayout.setRefreshing(false);

               }
            } else {
                // todo comment not available implement function
                Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
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
            Preference.setLastCategory(mContext, mRedditCategory);
            Preference.setLastTarget(mContext, mRedditTarget);
            mRefreshLayout.setRefreshing(true);
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
                    Preference.setWriteExternalStorage(mContext, true);
                    Preference.setRequestPermission(mContext, false);

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
        Preference.setRequestPermission(mContext, true);
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
            if (!TextUtils.isEmpty(historyCategory)) {
                mSubRedditTab.positionSelected(historyCategory);
            } else {
                super.onBackPressed();
            }

        } else {
            super.onBackPressed();

        }
    }

    @Override
    public void onRefresh() {

        if (!NetworkUtil.isOnline(mContext)) {
            mRefreshLayout.setRefreshing(false);
            Snackbar.make(mContainer, R.string.error_refresh_offline, Snackbar.LENGTH_LONG).show();

        } else if (getApplicationContext() != null) {
            initRest(Preference.getLastCategory(getApplicationContext()), Preference.getLastTarget(mContext), NetworkUtil.isOnline(mContext));
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
                mRefreshLayout.setRefreshing(false);

            }

        }
    }

    private void createTabLayout() {
        mSubRedditTab = new SubRedditTab(this, mTabLayout, getTabArrayList());
        mSubRedditTab.initTab();
        mSubRedditTab.positionSelected(mRedditCategory);
    }


    public static void homeActivity(Context context) {
        context.startActivity(new Intent(context, SubRedditActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION
                        | Intent.FLAG_ACTIVITY_NO_HISTORY));
    }


    private void initializeFirebaseDispatcherService() {

        if (Preference.isLoginStart(mContext)) {

            int redditSessionExpired = getRedditSessionExpired(getApplicationContext());
            if (redditSessionExpired <= Costant.SESSION_TIMEOUT_DEFAULT) {

                String strRefreshToken = Preference.getSessionRefreshToken(mContext);
                new RefreshTokenExecute(strRefreshToken).syncData(getApplicationContext());

            } else {

                FirebaseRefreshTokenSync.initialize(this, redditSessionExpired);
            }
        }
    }


}
