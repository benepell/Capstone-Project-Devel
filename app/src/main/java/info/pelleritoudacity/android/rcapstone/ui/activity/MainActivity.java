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
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;

import com.google.android.exoplayer2.util.Util;
import com.google.firebase.analytics.FirebaseAnalytics;

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
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.ui.fragment.MainFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.Authenticator;
import info.pelleritoudacity.android.rcapstone.ui.helper.MenuBase;
import info.pelleritoudacity.android.rcapstone.ui.helper.MenuLauncher;
import info.pelleritoudacity.android.rcapstone.ui.view.Tab;
import info.pelleritoudacity.android.rcapstone.utility.ActivityUI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.Utility;

import static info.pelleritoudacity.android.rcapstone.utility.PermissionUtil.RequestPermissionExtStorage;

public class MainActivity extends BaseActivity
        implements MainExecute.OnRestCallBack,
        Tab.OnTabListener, SwipeRefreshLayout.OnRefreshListener,
        ActivityCompat.OnRequestPermissionsResultCallback, SearchView.OnQueryTextListener, MainFragment.OnMainClick {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.main_container)
    public CoordinatorLayout mContainer;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @Nullable
    @BindView(R.id.nested_scrollview_main)
    public NestedScrollView mNestedScrollView;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @Nullable
    @BindView(R.id.tab_layout)
    public TabLayout mTabLayout;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.swipe_refresh_main)
    public SwipeRefreshLayout mRefreshLayout;

    private Context mContext;
    private Tab mTab;
    private long startTimeoutRefresh;
    private MainModel mModel;
    private MenuLauncher mLauncherMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        mContext = MainActivity.this;

        if (Utility.isTablet(mContext)) {
            if ((mNestedScrollView != null) && (ActivityUI.isLandscapeOrientation(mContext) && (savedInstanceState == null))) {
                // Caused by java.lang.ClassCastException: android.support.v7.widget.LinearLayoutManager$SavedState cannot be cast to android.support.v7.widget.GridLayoutManager
                mNestedScrollView.setSaveEnabled(false);
            }
        }

        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);

        if (Util.SDK_INT > 23) {
            RequestPermissionExtStorage(MainActivity.this);
            Preference.setRequestPermission(getApplicationContext(), false);
        }

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
            if (mModel == null) {
                mModel = new MainModel();
            }
        }

        ArrayList<String> mTabArrayList = new TabData(mContext).getTabArrayList();

        Preference.setVolumeMuted(mContext, Costant.IS_MUTED_AUDIO);

        new Authenticator(mContext).initLogin(mContainer, getIntent());

        mLauncherMenu = new MenuLauncher(mContext, getIntent());

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
            isRefreshing();
            updateOperation();
        }
        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        @SuppressWarnings("unused") Uri appLinkData = appLinkIntent.getData();


        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, mModel.getCategory());
        bundle.putString(FirebaseAnalytics.Param.ITEM_LIST, appLinkAction);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        if (menu.findItem(R.id.menu_action_search) == null) {
            getMenuInflater().inflate(R.menu.menu_search, menu);

            MenuBase menuBase = new MenuBase(this, R.layout.activity_main);
            menuBase.menuItemSearch(this, getComponentName(), menu);
        }

        return super.onPrepareOptionsPanel(view, menu);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mModel = savedInstanceState.getParcelable(Costant.EXTRA_PARCEL_MAIN_MODEL);

        if (Utility.isTablet(mContext)) {
            final int[] position = savedInstanceState.getIntArray(Costant.EXTRA_PARCEL_SCROLL_MAIN);
            if (position != null) {
                Objects.requireNonNull(mNestedScrollView).post(() -> Objects.requireNonNull(mNestedScrollView).scrollTo(position[0], position[1]));
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Costant.EXTRA_PARCEL_MAIN_MODEL, mModel);

        if (Utility.isTablet(mContext) && (mNestedScrollView != null)) {

            if (!ActivityUI.isLandscapeOrientation(mContext)) {

                int itemPage = Utility.calculateNoOfColumns(mContext);

                outState.putIntArray(Costant.EXTRA_PARCEL_SCROLL_MAIN,
                        new int[]{mNestedScrollView.getScrollX(), mNestedScrollView.getScrollY() * itemPage});

            }
        }
        super.onSaveInstanceState(outState);

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
    public void onRefresh() {
        updateOperation();

    }

    @Override
    public void tabSelected(String category) {
        if ((mContext != null) &&
                (!TextUtils.isEmpty(category) &&
                        (!TextUtils.isEmpty(Preference.getLastTarget(mContext))))) {


            if (category.compareTo(Preference.getLastCategory(mContext)) != 0) {

                mLauncherMenu.setCategory(category);
                mLauncherMenu.setTarget(Costant.TAB_MAIN_TARGET);
                mLauncherMenu.saveLastPreference();
                isRefreshing();

                mModel.setCategory(category);
                mModel.setTarget(Costant.TAB_MAIN_TARGET);
                Preference.setLastTarget(mContext, Costant.TAB_MAIN_TARGET);
                isRefreshing();
                updateOperation();

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

    @Override
    public boolean onQueryTextSubmit(String s) {
        mModel.setQuerySearch(s);
        mModel.setCategory(Preference.getLastCategory(mContext));
        mModel.setQuerySearch(s);
        Preference.setLastTarget(mContext, Costant.SEARCH_MAIN_TARGET);
        updateOperation();
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

    @Override
    public void mainClick(int position, String category, String strId) {
        mContext.startActivity(new Intent(mContext, DetailActivity.class)
                .putExtra(Costant.EXTRA_SUBREDDIT_DETAIL_POSITION, position)
                .putExtra(Costant.EXTRA_SUBREDDIT_DETAIL_CATEGORY, category)
                .putExtra(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID, strId));

    }

    @Override
    public void mainFragmentResult(int count) {
        if (mModel != null) {
            if (count == 0) {
                switch (mModel.getTarget()) {

                    case Costant.SEARCH_MAIN_TARGET:
                        Snackbar.make(mContainer, R.string.text_no_search, Costant.DEFAULT_SNACKBAR_DURATION).show();
                        Preference.setLastTarget(mContext, Costant.DEFAULT_START_VALUE_MAIN_TARGET);
                        mModel.setTarget(Costant.DEFAULT_START_VALUE_MAIN_TARGET);
                        updateOperation();
                        break;

                    case Costant.FAVORITE_MAIN_TARGET:
                        Snackbar.make(mContainer, R.string.text_no_favorite, Costant.DEFAULT_SNACKBAR_DURATION).show();
                        Preference.setLastTarget(mContext, Costant.DEFAULT_START_VALUE_MAIN_TARGET);
                        mModel.setTarget(Costant.DEFAULT_START_VALUE_MAIN_TARGET);
                        updateOperation();
                        break;

                }
            }

        }

        if (count > 0) mRefreshLayout.setRefreshing(false);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        int action = event.getAction();
        int keyCode = event.getKeyCode();
        switch (keyCode) {
            case KeyEvent.KEYCODE_VOLUME_UP:
                if (action == KeyEvent.ACTION_UP) {
                    Objects.requireNonNull(mNestedScrollView).pageScroll(View.FOCUS_UP);

                }
                return true;
            case KeyEvent.KEYCODE_VOLUME_DOWN:
                if (action == KeyEvent.ACTION_DOWN) {
                    Objects.requireNonNull(mNestedScrollView).pageScroll(View.FOCUS_DOWN);

                }
                return true;
            default:
                return super.dispatchKeyEvent(event);
        }
    }

    @Override
    public void onBackPressed() {

        if (mRefreshLayout != null) {
            mRefreshLayout.setRefreshing(false);
        }

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

    private void updateOperation() {
        if (System.currentTimeMillis() - startTimeoutRefresh > Costant.DEFAULT_OPERATION_REFRESH) {
            startTimeoutRefresh = System.currentTimeMillis();

            if (Preference.getLastTarget(mContext).equals(Costant.WIDGET_MAIN_TARGET)) {
                createUI(mModel);

            } else if (Preference.getLastTarget(mContext).equals(Costant.SEARCH_MAIN_TARGET) && (!TextUtils.isEmpty(mModel.getQuerySearch()))) {
                mModel.setCategory(Preference.getLastCategory(getApplicationContext()));
                mModel.setTarget(Preference.getLastTarget(getApplicationContext()));
                Preference.setLastTarget(mContext, Costant.DEFAULT_START_VALUE_MAIN_TARGET);
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
        }

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
                            Preference.getGeneralSettingsSyncFrequency(mContext),
                            Preference.getGeneralSettingsItemPage(mContext)))) {

                createUI(mModel);

            } else {
                switch (Preference.getSubredditSort(mContext)) {

                    case Costant.LABEL_SUBMENU_RISING:
                    case Costant.LABEL_SUBMENU_NEW:
                    case Costant.LABEL_SUBMENU_HOT:

                        new MainExecute(this, mContext, m.getCategory()).getData();
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
        } else {
            if (mNestedScrollView == null) {
                ActivityUI.startRefresh(getApplicationContext(), MainActivity.class);

            }

        }

    }

    private void isRefreshing() {
        if ((mContext != null) && mRefreshLayout != null) {
            if (NetworkUtil.isOnline(mContext)) {
                mRefreshLayout.setRefreshing(true);
            }
        }
    }

    private void closeSearch(MainModel m) {
        if (Preference.getLastTarget(mContext).equals(Costant.SEARCH_MAIN_TARGET) &&
                (!TextUtils.isEmpty(m.getQuerySearch()))) {
            mModel.setTarget(Costant.DEFAULT_START_VALUE_MAIN_TARGET);
            mModel.setCategory(m.getCategory());
            Preference.setLastTarget(mContext, Costant.DEFAULT_START_VALUE_MAIN_TARGET);
            mRefreshLayout.setRefreshing(true);
            updateOperation();
        }
    }
}
