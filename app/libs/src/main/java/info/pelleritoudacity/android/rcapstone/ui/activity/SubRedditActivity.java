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

import com.google.android.exoplayer2.util.Util;


import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T3Operation;
import info.pelleritoudacity.android.rcapstone.data.rest.RefreshTokenExecute;
import info.pelleritoudacity.android.rcapstone.media.MediaSession;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.rest.SubRedditExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubRedditFragment;
import info.pelleritoudacity.android.rcapstone.ui.view.SubRedditTab;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

import static info.pelleritoudacity.android.rcapstone.utility.SessionUtil.getRedditSessionExpired;


public class SubRedditActivity extends BaseActivity
        implements SubRedditExecute.RestSubReddit,
        SubRedditTab.OnTabListener, SwipeRefreshLayout.OnRefreshListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.nested_scrollview_subreddit)
    public NestedScrollView mNestedScrollView;

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

        if (savedInstanceState == null) {
            if (!menuShortcutLauncher(getIntent())) {
                if (!menuTargetLink(getIntent())) {
                    if (!menuHistoryLink(mContext)) {
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
        Preference.setLastCategory(mContext, mRedditCategory);
        Preference.setLastTarget(mContext, mRedditTarget);

    }

    @Override
    protected void onStart() {
        super.onStart();
        if (mContext != null) {
            mRefreshLayout.setRefreshing(true);
            onRefresh();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(Costant.EXTRA_SUBREDDIT_CATEGORY, mRedditCategory);
        outState.putString(Costant.EXTRA_SUBREDDIT_TARGET, mRedditTarget);
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
            if (data.saveData(mRedditCategory, mRedditTarget)) {
                createUI(mRedditCategory, mRedditTarget);

            } else {
                Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void onErrorSubReddit(Throwable t) { }

    @Override
    public void tabSelected(int position, String category) {
        if (mContext != null) {
            mRedditTarget = null;
            mRedditCategory = category;

            if (mRedditCategory.compareTo(Preference.getLastCategory(mContext)) != 0) {

                Preference.setLastCategory(mContext, mRedditCategory);
                Preference.setLastTarget(mContext, mRedditTarget);
                mRefreshLayout.setRefreshing(true);

                startActivity(new Intent(this, SubRedditActivity.class)
                        .putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, mRedditCategory)
                        .putExtra(Costant.EXTRA_TAB_POSITION, position)

                );

            }
        }

    }

    @Override
    public void onRefresh() {

        if (!NetworkUtil.isOnline(mContext)) {
            createUI(Preference.getLastCategory(getApplicationContext()),Preference.getLastTarget(getApplicationContext()));
            mRefreshLayout.setRefreshing(false);
            Snackbar.make(mContainer, R.string.list_snackbar_offline_text, Snackbar.LENGTH_LONG).show();

        } else if (getApplicationContext() != null) {
            initRest(Preference.getLastCategory(getApplicationContext()), Preference.getLastTarget(mContext), NetworkUtil.isOnline(mContext));
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
        updateTabPosition();
        startFragment(link, target);
        mRefreshLayout.setRefreshing(false);
    }

    private void createTabLayout() {
        mSubRedditTab = new SubRedditTab(this, mTabLayout, getTabArrayList());
        mSubRedditTab.initTab();
        mSubRedditTab.positionSelected(mRedditCategory);
    }

    private void updateTabPosition() {
        int position = getTabArrayList().indexOf(Preference.getLastCategory(mContext));
        if (position > 0) {
            position -= 1;
            int right = ((ViewGroup) mTabLayout.getChildAt(0)).getChildAt(position).getRight();
            mTabLayout.scrollTo(right, 0);

        }
    }

    private void initRest(String link, String target, boolean stateNetworkOnline) {
        if (!TextUtils.isEmpty(link)) {
            if (stateNetworkOnline) {
                new SubRedditExecute(mContext, link).getData(this);
            } else {
                createUI(link, target);

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

    private boolean menuHistoryLink(Context context) {
        if (!TextUtils.isEmpty(Preference.getLastCategory(mContext))) {
            mRedditCategory = Preference.getLastCategory(mContext);
            mRedditTarget = Preference.getLastTarget(mContext);
            return true;
        }
        return false;
    }

    private boolean menuTargetLink(Intent intent) {
        if ((intent != null) &&
                (!TextUtils.isEmpty(intent.getStringExtra(Costant.EXTRA_SUBREDDIT_TARGET)))) {

            mRedditCategory = intent.getStringExtra(Costant.EXTRA_SUBREDDIT_CATEGORY);
            mRedditTarget = intent.getStringExtra(Costant.EXTRA_SUBREDDIT_TARGET);

            return true;
        }
        return false;
    }

    private boolean menuShortcutLauncher(Intent intent) {
        if ((Util.SDK_INT > 24) && (intent != null) && (intent.getAction() != null)) {

            switch (intent.getAction()) {

                case Costant.ACTION_SHORTCUT_ALL:
                    mRedditCategory = Costant.SUBREDDIT_CATEGORY_ALL;
                    mRedditTarget = Costant.SUBREDDIT_TARGET_ALL;
                    return true;

                case Costant.ACTION_SHORTCUT_POPULAR:
                    mRedditCategory = Costant.SUBREDDIT_CATEGORY_ALL;
                    mRedditTarget = Costant.SUBREDDIT_TARGET_ALL;
                    return true;

                case Costant.ACTION_SHORTCUT_SEARCH:
                    return true;

            }

        }
        return false;
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
