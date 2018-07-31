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
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;

import com.google.android.exoplayer2.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T3Operation;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.other.TabData;
import info.pelleritoudacity.android.rcapstone.data.rest.RefreshTokenExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.SubRedditExecute;
import info.pelleritoudacity.android.rcapstone.media.MediaSession;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.MenuBase;
import info.pelleritoudacity.android.rcapstone.ui.helper.MenuLauncherDetail;
import info.pelleritoudacity.android.rcapstone.ui.view.SubRedditTab;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

import static info.pelleritoudacity.android.rcapstone.utility.PermissionUtil.RequestPermissionExtStorage;
import static info.pelleritoudacity.android.rcapstone.utility.SessionUtil.getRedditSessionExpired;

public class SubRedditActivity extends BaseActivity
        implements SubRedditExecute.OnRestCallBack,
        SubRedditTab.OnTabListener, SwipeRefreshLayout.OnRefreshListener, ActivityCompat.OnRequestPermissionsResultCallback, SearchView.OnQueryTextListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.subreddit_container)
    public CoordinatorLayout mContainer;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.nested_scrollview_subreddit)
    public NestedScrollView mNestedScrollView;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.tab_layout)
    public TabLayout mTabLayout;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.swipe_refresh_subreddit)
    public SwipeRefreshLayout mRefreshLayout;

    private Context mContext;
    public static MediaSessionCompat sMediaSessionCompat = null;

    private SubRedditTab mSubRedditTab;

    private String mCategory;
    private String mTarget;
    private String mQuerySearchText;
    private MenuLauncherDetail mLauncherMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_subreddit);
        super.onCreate(savedInstanceState);

        mContext = SubRedditActivity.this;

        if (Util.SDK_INT > 23) {
            RequestPermissionExtStorage(SubRedditActivity.this);
            Preference.setRequestPermission(getApplicationContext(), false);
        }


        mRefreshLayout.setOnRefreshListener(this);

        ArrayList<String> mTabArrayList = new TabData(mContext).getTabArrayList();

        firstInit();

        mLauncherMenu = new MenuLauncherDetail(mContext, getIntent());

        mSubRedditTab = new SubRedditTab(this, mTabLayout, mTabArrayList);
        mSubRedditTab.initTab();

        if (getIntent() != null) {
            mQuerySearchText = getIntent().getStringExtra(Costant.EXTRA_SUBREDDIT_SEARCH);
            if (TextUtils.isEmpty(mQuerySearchText) &&
                    (Preference.getLastTarget(mContext)).equals(Costant.SUBREDDIT_TARGET_SEARCH)) {

                Preference.setLastTarget(mContext, Costant.SUBREDDIT_TARGET_DEFAULT_START_VALUE);
            }
        }

        mLauncherMenu.showMenu();

        if (savedInstanceState == null) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        }

        mCategory = Preference.getLastCategory(mContext);
        mTarget = Preference.getLastTarget(mContext);

        mSubRedditTab.positionSelected(mCategory);


    }

    @Override
    public void onBackPressed() {
        if ((mSubRedditTab != null) && (Preference.isTabHistory(mContext
        ))) {
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
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
                (Preference.isWriteExternalStorage(mContext) ||
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
    public void tabSelected(int position, String category) {
        if ((mContext != null) &&
                (!TextUtils.isEmpty(category) &&
                        (!TextUtils.isEmpty(Preference.getLastTarget(mContext))))) {


            if (category.compareTo(Preference.getLastCategory(mContext)) != 0) {

                mLauncherMenu.setCategory(category);
                mLauncherMenu.setTarget(Costant.SUBREDDIT_TARGET_TAB);
                mLauncherMenu.saveLastPreference();
                mRefreshLayout.setRefreshing(true);

                startActivity(new Intent(this, SubRedditActivity.class)
                        .putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, category)
                        .putExtra(Costant.EXTRA_SUBREDDIT_TARGET, Costant.SUBREDDIT_TARGET_TAB)
                        .putExtra(Costant.EXTRA_TAB_POSITION, position)

                );

            }
        }

    }

    @Override
    public void tabReselected(int position, String category) {
        if (position > 0) {
            closeSearch(category);
        }
    }


    @Override
    public void onRefresh() {

        if (Preference.getLastTarget(mContext).equals(Costant.SUBREDDIT_TARGET_SEARCH) && (!TextUtils.isEmpty(mQuerySearchText))) {
            createUI(Preference.getLastCategory(getApplicationContext()), Preference.getLastTarget(getApplicationContext()), mQuerySearchText);

        } else if (!NetworkUtil.isOnline(mContext)) {
            createUI(Preference.getLastCategory(getApplicationContext()), Preference.getLastTarget(getApplicationContext()), null);
            Snackbar.make(mContainer, R.string.list_snackbar_offline_text, Snackbar.LENGTH_LONG).show();

        } else {

            initRest(Preference.getLastCategory(mContext), Preference.getLastTarget(mContext), NetworkUtil.isOnline(mContext));
        }

    }


    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        if (menu.findItem(R.id.menu_action_search) == null) {
            getMenuInflater().inflate(R.menu.menu_search, menu);
            MenuBase menuBase = new MenuBase(mContext, R.layout.activity_subreddit);
            menuBase.menuItemSearch(this, getComponentName(), menu);
        }

        return super.onPrepareOptionsPanel(view, menu);
    }

    private void firstInit() {

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

            if (intent.getBooleanExtra(Costant.EXTRA_ACTIVITY_SUBREDDIT_REFRESH, false)) {
                mRefreshLayout.setRefreshing(true);
                onRefresh();
            }

        }
    }

    private void createUI(String link, String target, String querySearch) {
        if ((mCategory != null && mTarget != null) && (TextUtils.isEmpty(querySearch))) {
            mSubRedditTab.updateTabPosition();

        }
        startFragment(link, target, querySearch);
    }


    private void initRest(String link, String target, boolean stateNetworkOnline) {
        if (!TextUtils.isEmpty(link)) {

            if ((!stateNetworkOnline) || (
                    new DataUtils(mContext).isSyncData(Contract.T3dataEntry.CONTENT_URI,
                            link,
                            Preference.getGeneralSettingsSyncFrequency(mContext)))) {

                createUI(link, target, null);

            } else {
                switch (Preference.getSubredditSort(mContext)) {

                    case Costant.LABEL_SUBMENU_RISING:
                    case Costant.LABEL_SUBMENU_NEW:
                    case Costant.LABEL_SUBMENU_HOT:

                        new SubRedditExecute(this,  mContext, link).getDataList();
                        break;

                    case Costant.LABEL_SUBMENU_CONTROVERSIAL:
                    case Costant.LABEL_SUBMENU_TOP:
                        new SubRedditExecute(this,  mContext, link).getData();
                        break;

                    default:
                        new SubRedditExecute(this,  mContext, link).getData();
                }
            }

        }
    }


    private void startFragment(String link, String target, String querySearch) {
        if (!getSupportFragmentManager().isStateSaved()) {
            SubRedditFragment subRedditFragment = SubRedditFragment.newInstance(link, target, querySearch);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_subreddit_container, subRedditFragment).commit();
        }

        if (mRefreshLayout != null) {
            if (mRefreshLayout.isRefreshing()) mRefreshLayout.setRefreshing(false);
        }

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

    @Override
    public boolean onQueryTextSubmit(String s) {
        Intent intent = new Intent(getApplicationContext(), SubRedditActivity.class);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, Preference.getLastCategory(mContext));
        intent.putExtra(Costant.EXTRA_SUBREDDIT_TARGET, Costant.SUBREDDIT_TARGET_SEARCH);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_SEARCH, s);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void success(T3 response) {
        if ((getApplicationContext() != null) && (response != null)) {
            T3Operation data = new T3Operation(getApplicationContext(), response);

            if ((mCategory != null && mTarget != null) && (data.saveData(mCategory, mTarget))) {
                createUI(mCategory, mTarget, null);

            } else {
                Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void success(List<T3> response) {
        if ((getApplicationContext() != null) && (response != null)) {

            int i = response.size();
            for (T3 listenerData : response) {

                T3Operation data = new T3Operation(getApplicationContext(), listenerData);
                if ((mCategory != null && mTarget != null) && (data.saveData(mCategory, mTarget))) {

                    createUI(mCategory, mTarget, null);

                } else {
                    Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
                }

                i--;

            }

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

    private void closeSearch(String category) {
        if (Preference.getLastTarget(mContext).equals(Costant.SUBREDDIT_TARGET_SEARCH) &&
                (!TextUtils.isEmpty(mQuerySearchText))) {
            startActivity(new Intent(mContext, SubRedditActivity.class)
                    .putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, category)
                    .putExtra(Costant.EXTRA_SUBREDDIT_TARGET, Costant.SUBREDDIT_TARGET_DEFAULT_START_VALUE)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY)
            );
        }
    }
}
