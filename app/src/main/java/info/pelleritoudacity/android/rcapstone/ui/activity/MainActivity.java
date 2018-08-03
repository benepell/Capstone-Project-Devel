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
import android.graphics.Color;
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
import info.pelleritoudacity.android.rcapstone.data.model.ui.MainModel;
import info.pelleritoudacity.android.rcapstone.data.other.TabData;
import info.pelleritoudacity.android.rcapstone.data.rest.MainExecute;
import info.pelleritoudacity.android.rcapstone.media.MediaSession;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.ui.fragment.MainFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.Authenticator;
import info.pelleritoudacity.android.rcapstone.ui.helper.MenuBase;
import info.pelleritoudacity.android.rcapstone.ui.helper.MenuLauncherDetail;
import info.pelleritoudacity.android.rcapstone.ui.view.Tab;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

import static info.pelleritoudacity.android.rcapstone.utility.PermissionUtil.RequestPermissionExtStorage;

public class MainActivity extends BaseActivity
        implements MainExecute.OnRestCallBack,
        Tab.OnTabListener, SwipeRefreshLayout.OnRefreshListener, ActivityCompat.OnRequestPermissionsResultCallback, SearchView.OnQueryTextListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.main_container)
    public CoordinatorLayout mContainer;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.nested_scrollview_main)
    public NestedScrollView mNestedScrollView;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.tab_layout)
    public TabLayout mTabLayout;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.swipe_refresh_main)
    public SwipeRefreshLayout mRefreshLayout;

    private Context mContext;
    public static MediaSessionCompat sMediaSessionCompat = null;

    private Tab mTab;
    private long startTimeoutRefresh;
    private MainModel mModel;
    private boolean isStartOnRefresh;
    private MenuLauncherDetail mLauncherMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        mContext = MainActivity.this;
        mRefreshLayout.setOnRefreshListener(this);

        if (Preference.isNightMode(mContext)) {
            mRefreshLayout.setProgressBackgroundColorSchemeColor(Color.GRAY);
            mRefreshLayout.setColorSchemeColors(Color.GREEN);
        }

        if (PermissionUtil.isLogged(getApplicationContext())) {
            FirebaseRefreshTokenSync.init(mContext, Costant.SESSION_TIMEOUT_DEFAULT);
        }

        if (savedInstanceState != null) {
            mModel = savedInstanceState.getParcelable(Costant.EXTRA_PARCEL_MAIN_MODEL);

        } else {
            mModel = new MainModel();

        }

        if (Util.SDK_INT > 23) {
            RequestPermissionExtStorage(MainActivity.this);
            Preference.setRequestPermission(getApplicationContext(), false);
        }


        ArrayList<String> mTabArrayList = new TabData(mContext).getTabArrayList();

        Preference.setVolumeMuted(mContext, Costant.IS_MUTED_AUDIO);

        new Authenticator(mContext).initLogin(mContainer, getIntent());

        mLauncherMenu = new MenuLauncherDetail(mContext, getIntent());

        mTab = new Tab(this, mContext, mTabLayout, mTabArrayList);
        mTab.initTab();

        if (getIntent() != null) {
            mModel.setQuerySearch(getIntent().getStringExtra(Costant.EXTRA_MAIN_SEARCH));
            if (!TextUtils.isEmpty(mModel.getQuerySearch()) &&
                    (Preference.getLastTarget(mContext)).equals(Costant.SEARCH_MAIN_TARGET)) {

                Preference.setLastTarget(mContext, Costant.DEFAULT_START_VALUE_MAIN_TARGET);
            }
        }

        mLauncherMenu.showMenu();

        if (getIntent().getBooleanExtra(Costant.EXTRA_ACTIVITY_REDDIT_RESET, false)) {
            Snackbar.make(mContainer, R.string.text_dialog_confirm_reset, Snackbar.LENGTH_LONG).show();
        }

        mModel.setCategory(Preference.getLastCategory(mContext));
        mModel.setTarget(Preference.getLastTarget(mContext));

        mTab.positionSelected(mModel.getCategory());

        if ((savedInstanceState == null) || (getIntent().getBooleanExtra(Costant.EXTRA_ACTIVITY_REDDIT_REFRESH, false))) {
            mRefreshLayout.setRefreshing(true);
            isStartOnRefresh = true;
            onRefresh();
        }

    }

    @Override
    public void onBackPressed() {
        if ((mTab != null) && (Preference.isTabHistory(mContext
        ))) {
            String historyCategory = mTab.getHistoryPosition();
            if (!TextUtils.isEmpty(historyCategory)) {
                mTab.positionSelected(historyCategory);
            } else {
                super.onBackPressed();
            }

        } else {
            super.onBackPressed();

        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Costant.EXTRA_PARCEL_MAIN_MODEL, mModel);
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
        ActivityCompat.requestPermissions(MainActivity.this,
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
                mLauncherMenu.setTarget(Costant.TAB_MAIN_TARGET);
                mLauncherMenu.saveLastPreference();
                mRefreshLayout.setRefreshing(true);

                mModel.setCategory(category);
                mModel.setTarget(Costant.TAB_MAIN_TARGET);

                startActivity(new Intent(this, MainActivity.class)
                        .putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, category)
                        .putExtra(Costant.EXTRA_MAIN_TARGET, Costant.TAB_MAIN_TARGET)
                        .putExtra(Costant.EXTRA_TAB_POSITION, position)
                );

            }
        }

    }

    @Override
    public void tabReselected(int position, String category) {
        if (position > 0) {
            mModel.setCategory(category);
            mModel.setPositionTab(position);
            closeSearch(mModel);
        }
    }

    private void closeSearch(MainModel m) {
        if (Preference.getLastTarget(mContext).equals(Costant.SEARCH_MAIN_TARGET) &&
                (!TextUtils.isEmpty(m.getQuerySearch()))) {
            mModel.setTarget(Costant.DEFAULT_START_VALUE_MAIN_TARGET);
            startActivity(new Intent(mContext, MainActivity.class)
                    .putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, m.getCategory())
                    .putExtra(Costant.EXTRA_MAIN_TARGET, Costant.DEFAULT_START_VALUE_MAIN_TARGET)
                    .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION | Intent.FLAG_ACTIVITY_NO_HISTORY)
            );
        }
    }


    @Override
    public void onRefresh() {

        if (System.currentTimeMillis() - startTimeoutRefresh > Costant.DEFAULT_OPERATION_REFRESH) {
            startTimeoutRefresh = System.currentTimeMillis();
            if (Preference.getLastTarget(mContext).equals(Costant.SEARCH_MAIN_TARGET) && (!TextUtils.isEmpty(mModel.getQuerySearch()))) {
                mModel.setCategory(Preference.getLastCategory(getApplicationContext()));
                mModel.setTarget(Preference.getLastTarget(getApplicationContext()));
                createUI(mModel);

            } else if (!NetworkUtil.isOnline(mContext)) {
                mModel.setCategory(Preference.getLastCategory(getApplicationContext()));
                mModel.setTarget(Preference.getLastTarget(getApplicationContext()));
                createUI(mModel);

                Snackbar.make(mContainer, R.string.list_snackbar_offline_text, Snackbar.LENGTH_LONG).show();

            } else {
                mModel.setCategory(Preference.getLastCategory(getApplicationContext()));
                mModel.setTarget(Preference.getLastTarget(getApplicationContext()));

                initRest(mModel, NetworkUtil.isOnline(mContext));
            }
        } else {
            createUI(mModel);
            mRefreshLayout.setRefreshing(false);
        }
    }


    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        if (menu.findItem(R.id.menu_action_search) == null) {
            getMenuInflater().inflate(R.menu.menu_search, menu);
            MenuBase menuBase = new MenuBase(mContext, R.layout.activity_main);
            menuBase.menuItemSearch(this, getComponentName(), menu);
        }

        return super.onPrepareOptionsPanel(view, menu);
    }


    private void createUI(MainModel m) {
        if ((m.getCategory() != null && m.getTarget() != null) && (TextUtils.isEmpty(m.getQuerySearch()))) {
            if (mTab != null) {
                mTab.updateTabPosition();
            }
        }
        startFragment(m);
    }


    private void initRest(MainModel m, boolean stateNetworkOnline) {
        if (!TextUtils.isEmpty(m.getCategory())) {

            if ((!stateNetworkOnline) || (
                    new DataUtils(mContext).isSyncData(Contract.T3dataEntry.CONTENT_URI,
                            m.getCategory(),
                            Preference.getGeneralSettingsSyncFrequency(mContext)))) {

                createUI(mModel);

            } else {
                switch (Preference.getSubredditSort(mContext)) {

                    case Costant.LABEL_SUBMENU_RISING:
                    case Costant.LABEL_SUBMENU_NEW:
                    case Costant.LABEL_SUBMENU_HOT:

                        new MainExecute(this, mContext, m.getCategory()).getDataList();
                        break;

                    case Costant.LABEL_SUBMENU_CONTROVERSIAL:
                    case Costant.LABEL_SUBMENU_TOP:
                        new MainExecute(this, mContext, m.getCategory()).getData();
                        break;

                    default:
                        new MainExecute(this, mContext, m.getCategory()).getData();
                }
            }

        }
    }


    private void startFragment(MainModel m) {
        if (!getSupportFragmentManager().isStateSaved()) {
            MainFragment mainFragment = MainFragment.newInstance(m);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_subreddit_container, mainFragment).commit();
        }

        if (mRefreshLayout != null) {
            if (mRefreshLayout.isRefreshing()) mRefreshLayout.setRefreshing(false);
        }

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        mModel.setQuerySearch(s);
        mModel.setTarget(Costant.SEARCH_MAIN_TARGET);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, mModel.getCategory());
        intent.putExtra(Costant.EXTRA_MAIN_TARGET, Costant.SEARCH_MAIN_TARGET);
        intent.putExtra(Costant.EXTRA_MAIN_SEARCH, s);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(intent);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void success(T3 response, int code) {
        if ((getApplicationContext() != null) && (response != null)) {
            T3Operation data = new T3Operation(getApplicationContext(), response);

            if ((mModel.getCategory() != null && mModel.getTarget() != null) && (data.saveData(mModel.getCategory(), mModel.getTarget()))) {
                createUI(mModel);

            } else {
                Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
            }
        }

    }

    @Override
    public void success(List<T3> response, int code) {
        if ((getApplicationContext() != null) && (response != null)) {

            int i = response.size();
            for (T3 listenerData : response) {

                T3Operation data = new T3Operation(getApplicationContext(), listenerData);
                if ((mModel.getCategory() != null && mModel.getTarget() != null) && (data.saveData(mModel.getCategory(), mModel.getTarget()))) {

                    createUI(mModel);

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

}
