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

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.ViewGroup;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T3Operation;
import info.pelleritoudacity.android.rcapstone.data.rest.RefreshTokenExecute;
import info.pelleritoudacity.android.rcapstone.media.MediaSession;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.rest.SubRedditExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.MenuLauncherDetail;
import info.pelleritoudacity.android.rcapstone.ui.view.SubRedditTab;
import info.pelleritoudacity.android.rcapstone.data.other.TabData;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

import static info.pelleritoudacity.android.rcapstone.utility.SessionUtil.getRedditSessionExpired;


public class SubRedditActivity extends BaseActivity
        implements SubRedditExecute.OnRestSubReddit, SubRedditExecute.OnRestSubRedditList,
        SubRedditTab.OnTabListener, SwipeRefreshLayout.OnRefreshListener {

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
    private ArrayList<String> mTabArrayList;

    private String mCategory;
    private String mTarget;
    private MenuLauncherDetail mLauncherMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_subreddit);
        super.onCreate(savedInstanceState);

        mContext = SubRedditActivity.this;

        mRefreshLayout.setOnRefreshListener(this);

        mTabArrayList = new TabData(mContext).getTabArrayList();

        firstInit();

        mLauncherMenu = new MenuLauncherDetail(mContext, getIntent());

        if (mLauncherMenu.showMenu()) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();

            mCategory = Preference.getLastCategory(mContext);
            mTarget = Preference.getLastTarget(mContext);

        }

        createTabLayout();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
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
    protected void onDestroy() {
        super.onDestroy();
        if (Costant.IS_MEDIA_SESSION) {
            MediaSession.removeNotification(mContext);
        }
    }

    @Override
    public void onRestSubReddit(T3 listenerData) {
        if ((getApplicationContext() != null) && (listenerData != null)) {
            T3Operation data = new T3Operation(getApplicationContext(), listenerData);

            if ((mCategory != null && mTarget != null) && (data.saveData(mCategory, mTarget))) {
                createUI(mCategory, mTarget);

            } else {
                Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onRestSubReddit(List<T3> listenerDataList) {
        if ((getApplicationContext() != null) && (listenerDataList != null)) {

            int i = listenerDataList.size();
            for (T3 listenerData : listenerDataList) {

                T3Operation data = new T3Operation(getApplicationContext(), listenerData);
                if ((mCategory != null && mTarget != null) && (data.saveData(mCategory, mTarget))) {

                    createUI(mCategory, mTarget);

                } else {
                    Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
                }

                i--;

            }

        }

    }

    @Override
    public void onErrorSubReddit(Throwable t) {
    }

    @Override
    public void tabSelected(int position, String category) {
        if (mContext != null) {

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
    public void onRefresh() {

        if (!NetworkUtil.isOnline(mContext)) {
            createUI(Preference.getLastCategory(getApplicationContext()), Preference.getLastTarget(getApplicationContext()));
            mRefreshLayout.setRefreshing(false);
            Snackbar.make(mContainer, R.string.list_snackbar_offline_text, Snackbar.LENGTH_LONG).show();

        } else if (getApplicationContext() != null) {

            initRest(Preference.getLastCategory(mContext), Preference.getLastTarget(mContext), NetworkUtil.isOnline(mContext));
        }

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

    private void createUI(String link, String target) {
        mSubRedditTab.updateTabPosition();
        startFragment(link, target);
        mRefreshLayout.setRefreshing(false);
    }

    private void createTabLayout() {
        mSubRedditTab = new SubRedditTab(this, mTabLayout, mTabArrayList);
        mSubRedditTab.initTab();
        mSubRedditTab.positionSelected(mCategory);
    }


    private void initRest(String link, String target, boolean stateNetworkOnline) {
        if (!stateNetworkOnline) {
            createUI(link, target);

        } else {
            switch (Preference.getSubredditSort(mContext)) {

                case Costant.LABEL_SUBMENU_RISING:
                case Costant.LABEL_SUBMENU_NEW:
                case Costant.LABEL_SUBMENU_HOT:

                    new SubRedditExecute(mContext, link, target).getDataList(this);
                    break;

                case Costant.LABEL_SUBMENU_CONTROVERSIAL:
                case Costant.LABEL_SUBMENU_TOP:
                    new SubRedditExecute(mContext, link, target).getData(this);
                    break;

                default:
                    new SubRedditExecute(mContext, link, target).getData(this);
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

}
