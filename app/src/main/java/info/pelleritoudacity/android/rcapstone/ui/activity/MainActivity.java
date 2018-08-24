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
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.util.Util;
import com.google.firebase.analytics.FirebaseAnalytics;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T3Operation;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditAboutMe;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.model.ui.MainModel;
import info.pelleritoudacity.android.rcapstone.data.other.TabData;
import info.pelleritoudacity.android.rcapstone.data.rest.AboutMeExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.MainExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.RefreshTokenExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.ui.fragment.MainFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.Authenticator;
import info.pelleritoudacity.android.rcapstone.ui.helper.MenuLauncher;
import info.pelleritoudacity.android.rcapstone.ui.view.Tab;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.PermissionUtil.RequestPermissionExtStorage;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AboutMeExecute.OnRestCallBack, MainExecute.OnRestCallBack,
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

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawer;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.nav_view)
    public NavigationView mNavigationView;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.img_no_internet_main)
    public ImageView mImgNoNetwork;

    private long startTimeoutRefresh;
    private Context mContext;

    private View mNavHeaderView;

    private Tab mTab;
    private MainModel mModel;
    private MenuLauncher mLauncherMenu;
    private SearchView mSearchView;
    private String mSearchString;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = MainActivity.this;

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        if (Util.SDK_INT > 23) {
            RequestPermissionExtStorage(MainActivity.this);
            Preference.setRequestPermission(getApplicationContext(), false);
        }

        FirebaseAnalytics firebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mRefreshLayout.setOnRefreshListener(this);
        initToolBar();

        if (TextUtils.isEmpty(Preference.getSubredditSort(getApplicationContext()))) {
            Preference.setSubredditSort(getApplicationContext(), Costant.DEFAULT_SORT_BY);

        }

        if (TextUtils.isEmpty(Preference.getTimeSort(getApplicationContext()))) {
            Preference.setSubredditSort(getApplicationContext(), Costant.DEFAULT_SORT_TIME);

        }

        if (PermissionUtil.isLogged(getApplicationContext())) {
            new AboutMeExecute(this, PermissionUtil.getToken(getApplicationContext())).loginData();
        }

        if (Preference.isNightMode(mContext)) {
            mRefreshLayout.setProgressBackgroundColorSchemeColor(Color.GRAY);
            mRefreshLayout.setColorSchemeColors(Color.GREEN);
        }

        if (PermissionUtil.isLogged(getApplicationContext())) {
            FirebaseRefreshTokenSync.init(mContext, Costant.SESSION_TIMEOUT_DEFAULT);
        }

        if (savedInstanceState != null) {
            mModel = savedInstanceState.getParcelable(Costant.EXTRA_PARCEL_MAIN_MODEL);
            mSearchString = savedInstanceState.getString(Costant.EXTRA_SEARCH_SUBSCRIBE);


        } else if (mModel == null) {
            mModel = new MainModel();

        }

        ArrayList<String> tabArrayList = new TabData(mContext).getTabArrayList();
        mTab = new Tab(this, mContext, mTabLayout, tabArrayList);
        mTab.initTab();

        Preference.setVolumeMuted(mContext, Costant.IS_MUTED_AUDIO);

        new Authenticator(mContext).initLogin(mContainer, getIntent());

        mLauncherMenu = new MenuLauncher(mContext, getIntent());


        if (getIntent() != null) {
            mModel.setQuerySearch(getIntent().getStringExtra(Costant.EXTRA_MAIN_SEARCH));
            if (!TextUtils.isEmpty(mModel.getQuerySearch()) &&
                    (Preference.getLastTarget(mContext)).equals(Costant.SEARCH_MAIN_TARGET)) {

                Preference.setLastTarget(mContext, Costant.DEFAULT_START_VALUE_MAIN_TARGET);
            }

        }

        mLauncherMenu.showMenu();

        if (Preference.isFactoryDataReset(mContext)) {
            Snackbar.make(mContainer, R.string.text_dialog_confirm_reset, Snackbar.LENGTH_LONG).show();
            Preference.setFactoryDataReset(mContext, false);
        }


        if (TextUtils.isEmpty(Preference.getLastTarget(mContext)) ||
                TextUtils.isEmpty(Preference.getLastCategory(mContext))) {

            Preference.setLastCategory(mContext, Costant.DEFAULT_START_VALUE_MAIN_TARGET);
            Preference.setLastCategory(mContext, Costant.DEFAULT_SUBREDDIT_CATEGORY.split(Costant.STRING_SEPARATOR)[0]);

        } else {

            mModel.setCategory(Preference.getLastCategory(mContext));
            mModel.setTarget(Preference.getLastTarget(mContext));

        }

        mTab.positionSelected(mModel.getCategory());

        Intent appLinkIntent = getIntent();
        String appLinkAction = appLinkIntent.getAction();
        @SuppressWarnings("unused") Uri appLinkData = appLinkIntent.getData();


        Bundle bundle = new Bundle();
        bundle.putString(FirebaseAnalytics.Param.ITEM_CATEGORY, mModel.getCategory());
        bundle.putString(FirebaseAnalytics.Param.ITEM_LIST, appLinkAction);
        firebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);

        if ((savedInstanceState == null) || (getIntent().getBooleanExtra(Costant.EXTRA_ACTIVITY_REDDIT_REFRESH, false))) {
            updateOperation(true);
        }

    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        MenuItem menuItemFilter = menu.findItem(R.id.submenu_filter_posts);

        MenuItem itemGeneralImages = menu.findItem(R.id.action_general_images);
        MenuItem itemGeneralVideos = menu.findItem(R.id.action_general_videos);
        MenuItem itemGeneralGifs = menu.findItem(R.id.action_general_gifs);
        MenuItem itemGeneralLinks = menu.findItem(R.id.action_general_links);
        MenuItem itemGeneralAlbums = menu.findItem(R.id.action_general_albums);
        MenuItem itemGeneralSelf = menu.findItem(R.id.action_general_self);

        MenuItem menuItemLogin = menu.findItem(R.id.menu_action_login);
        MenuItem menuItemLogout = menu.findItem(R.id.menu_action_logout);

        menuItemFilter.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_sort)
                .color(Color.WHITE)
                .sizeDp(24)
                .respectFontBounds(true));

        if (!Preference.isGeneralInit(mContext)) {
            Preference.setGeneralImages(mContext, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralVideos(mContext, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralGifs(mContext, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralLinks(mContext, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralAlbums(mContext, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralSelf(mContext, Costant.DEFAULT_GENERAL_SETTINGS);

            itemGeneralImages.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralVideos.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralGifs.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralLinks.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralAlbums.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralSelf.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);

            Preference.setGeneralInit(mContext, true);

        } else {
            if (Preference.isGeneralImages(mContext)) {
                itemGeneralImages.setChecked(true);
            } else {
                itemGeneralImages.setChecked(false);

            }

            if (Preference.isGeneralGifs(mContext)) {
                itemGeneralGifs.setChecked(true);

            } else {
                itemGeneralGifs.setChecked(false);

            }

            if (Preference.isGeneralVideos(mContext)) {
                itemGeneralVideos.setChecked(true);

            } else {
                itemGeneralVideos.setChecked(false);

            }

            if (Preference.isGeneralLinks(mContext)) {
                itemGeneralLinks.setChecked(true);

            } else {
                itemGeneralLinks.setChecked(false);

            }

            if (Preference.isGeneralAlbums(mContext)) {
                itemGeneralAlbums.setChecked(true);

            } else {
                itemGeneralAlbums.setChecked(false);

            }

            if (Preference.isGeneralSelf(mContext)) {
                itemGeneralSelf.setChecked(true);

            } else {
                itemGeneralSelf.setChecked(false);

            }
        }

        if (menu.findItem(R.id.menu_action_search) == null) {
            getMenuInflater().inflate(R.menu.menu_search, menu);

            MenuItem menuItemSearch = menu.findItem(R.id.menu_action_search);

            menuItemSearch.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_search)
                    .color(Color.WHITE)
                    .sizeDp(24)
                    .respectFontBounds(true));

            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

            mSearchView = (SearchView) menuItemSearch.getActionView();
            if (mSearchView != null) {
                mSearchView.setSearchableInfo(Objects.requireNonNull(searchManager).getSearchableInfo(getComponentName()));

                mSearchView.setQueryHint(getString(R.string.text_search_local));
                mSearchView.setOnQueryTextListener(this);
                mSearchView.setIconified(false);

                if (!TextUtils.isEmpty(mSearchString)) {
                    menuItemSearch.expandActionView();
                    mSearchView.setQuery(mSearchString, true);
                    mSearchView.clearFocus();
                }

            }
        }


        if (Preference.isLoginStart(mContext)) {
            menuItemLogin.setVisible(false);
            menuItemLogout.setVisible(true);

        } else {
            menuItemLogin.setVisible(true);
            menuItemLogout.setVisible(false);

        }

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        mDrawer.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {

            case R.id.nav_home:
                Preference.setLastTarget(mContext, Costant.NAVIGATION_MAIN_TARGET);
                Preference.setTypeMode(mContext, Costant.NAV_MODE_HOME);
                updateOperation(false);
                break;

            case R.id.nav_mode_all:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_ALL);
                Preference.setLastCategory(mContext, Costant.CATEGORY_ALL);
                Preference.setLastTarget(mContext, Costant.ALL_MAIN_TARGET);
                updateOperation(true);

                break;

            case R.id.nav_mode_popular:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_POPOLAR);
                Preference.setLastCategory(mContext, Costant.CATEGORY_POPULAR);
                Preference.setLastTarget(mContext, Costant.POPULAR_MAIN_TARGET);
                updateOperation(true);
                break;

            case R.id.nav_mode_subscriptions:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_SUBSCRIPTIONS);
                item.setEnabled(true);
                startActivity(new Intent(this, ManageActivity.class));
                break;

            case R.id.nav_mode_favorite:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_FAVORITE);
                Preference.setLastTarget(mContext, Costant.FAVORITE_MAIN_TARGET);
                updateOperation(true);
                break;

            case R.id.nav_mode_refresh:

                if (NetworkUtil.isOnline(mContext)) {
                    Preference.setTypeMode(mContext, Costant.NAV_MODE_REFRESH);
                    item.setEnabled(true);
                    updateOperation(true);

                } else {
                    Snackbar.make(mContainer, R.string.text_no_network, Snackbar.LENGTH_LONG).show();

                }
                break;

            case R.id.nav_mode_settings:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_SETTINGS);
                item.setEnabled(true);
                startActivity(new Intent(mContext, SettingsActivity.class));
                break;

        }

        return true;

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_action_restore:

                updateOperation(true);
                return true;

            case R.id.menu_action_login:

                if (NetworkUtil.isOnline(mContext)) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    Snackbar.make(mContainer, R.string.text_no_network, Snackbar.LENGTH_LONG).show();
                }

                return true;

            case R.id.menu_action_logout:

                startActivity(new Intent(this, LoginActivity.class));
                return true;

            case R.id.menu_action_refresh:

                if (NetworkUtil.isOnline(mContext)) {
                    updateOperation(true);
                } else {
                    Snackbar.make(mContainer, R.string.text_no_network, Snackbar.LENGTH_LONG).show();
                }
                return true;

            case R.id.menu_action_create:
                if (PermissionUtil.isLogged(mContext)) {
                    startActivity(new Intent(this, WebviewActivity.class));
                } else {
                    Snackbar.make(mContainer, R.string.text_start_login, Snackbar.LENGTH_LONG).show();
                }
                return true;

            case R.id.menu_action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;

            case R.id.submenu_filter_hot:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_HOT);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_NOTHING);
                updateOperation(false);
                break;

            case R.id.submenu_filter_new:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_NEW);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_NOTHING);
                updateOperation(false);
                break;

            case R.id.submenu_filter_rising:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_RISING);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_NOTHING);
                updateOperation(false);
                break;

            case R.id.submenu_top_hour:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_HOUR);
                updateOperation(false);
                break;

            case R.id.submenu_top_day:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_DAY);
                updateOperation(false);
                break;

            case R.id.submenu_top_week:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_WEEK);
                updateOperation(false);
                break;

            case R.id.submenu_top_month:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_MONTH);
                updateOperation(false);
                break;

            case R.id.submenu_top_year:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_YEAR);
                updateOperation(false);
                break;

            case R.id.submenu_top_all:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_ALL);
                updateOperation(false);
                break;

            case R.id.submenu_controver_hour:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_HOUR);
                updateOperation(false);
                break;

            case R.id.submenu_controver_day:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_DAY);
                updateOperation(false);
                break;

            case R.id.submenu_controver_week:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_WEEK);
                updateOperation(false);
                break;

            case R.id.submenu_controver_month:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_MONTH);
                updateOperation(false);
                break;

            case R.id.submenu_controver_year:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_YEAR);
                updateOperation(false);
                break;

            case R.id.submenu_controver_all:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_ALL);
                updateOperation(false);
                break;

            case R.id.action_general_images:
                Preference.setGeneralImages(mContext, !Preference.isGeneralImages(mContext));
                updateOperation(false);
                break;

            case R.id.action_general_gifs:
                Preference.setGeneralGifs(mContext, !Preference.isGeneralGifs(mContext));
                updateOperation(false);
                break;

            case R.id.action_general_albums:
                Preference.setGeneralAlbums(mContext, !Preference.isGeneralAlbums(mContext));
                updateOperation(false);
                break;

            case R.id.action_general_videos:
                Preference.setGeneralVideos(mContext, !Preference.isGeneralVideos(mContext));
                updateOperation(false);
                break;

            case R.id.action_general_self:
                Preference.setGeneralSelf(mContext, !Preference.isGeneralSelf(mContext));
                updateOperation(false);
                break;

            case R.id.action_general_links:
                Preference.setGeneralLinks(mContext, !Preference.isGeneralLinks(mContext));
                updateOperation(false);
                break;
        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mModel = savedInstanceState.getParcelable(Costant.EXTRA_PARCEL_MAIN_MODEL);
        mSearchString = savedInstanceState.getString(Costant.EXTRA_SEARCH_MAIN);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Costant.EXTRA_PARCEL_MAIN_MODEL, mModel);
        if (mSearchView != null) {
            outState.putString(Costant.EXTRA_SEARCH_MAIN, mSearchView.getQuery().toString());
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
        updateOperation(false);

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

                mModel.setCategory(category);
                mModel.setTarget(Costant.TAB_MAIN_TARGET);
                Preference.setLastTarget(mContext, Costant.TAB_MAIN_TARGET);
                updateOperation(true);

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
        updateOperation(false);
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
    public void success(RedditAboutMe response, int code) {

        switch (code) {
            case 200:
                if (response != null) {
                    String name = response.getName();
                    boolean isOver18 = response.isOver18();
                    if (!TextUtils.isEmpty(name)) {
                        Preference.setLoginOver18(getApplicationContext(), isOver18);
                        Preference.setSessionUsername(getApplicationContext(), name);

                        TextView loginNameNavHeader = mNavHeaderView.findViewById(R.id.tv_nav_name);
                        loginNameNavHeader.setText(name);
                    }
                }
                break;

            case 401:
                new RefreshTokenExecute(Preference.getSessionRefreshToken(getApplicationContext())).syncData(getApplicationContext());

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
    public void unexpectedError(Throwable tList) {
        Timber.e("START ERROR %s", tList.getMessage());
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

            if (!NetworkUtil.isOnline(mContext)) {
                if (count == 0) {
                    mImgNoNetwork.setVisibility(View.VISIBLE);
                } else {
                    mImgNoNetwork.setVisibility(View.GONE);
                }
            }

            if (count == 0) {
                switch (mModel.getTarget()) {

                    case Costant.SEARCH_MAIN_TARGET:
                        Snackbar.make(mContainer, R.string.text_no_search, Costant.DEFAULT_SNACKBAR_DURATION).show();
                        Preference.setLastTarget(mContext, Costant.DEFAULT_START_VALUE_MAIN_TARGET);
                        mModel.setTarget(Costant.DEFAULT_START_VALUE_MAIN_TARGET);
                        updateOperation(false);
                        break;

                    case Costant.FAVORITE_MAIN_TARGET:
                        Snackbar.make(mContainer, R.string.text_no_favorite, Costant.DEFAULT_SNACKBAR_DURATION).show();
                        Preference.setLastTarget(mContext, Costant.DEFAULT_START_VALUE_MAIN_TARGET);
                        mModel.setTarget(Costant.DEFAULT_START_VALUE_MAIN_TARGET);
                        updateOperation(false);
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

        if ((mDrawer != null) & (Objects.requireNonNull(mDrawer).isDrawerOpen(GravityCompat.START))) {
            mDrawer.closeDrawer(GravityCompat.START);

        } else if (mTab != null && (Preference.isTabHistory(mContext
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

    private void updateOperation(boolean refresh) {

        if ((NetworkUtil.isOnline(mContext)) && (refresh) && (mRefreshLayout != null)) {
            mRefreshLayout.setRefreshing(true);
        }

        if (System.currentTimeMillis() - startTimeoutRefresh > Costant.DEFAULT_OPERATION_REFRESH) {
            mImgNoNetwork.setVisibility(View.GONE);
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
                mRefreshLayout.setRefreshing(false);
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
                updateOperation(true);

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
            updateOperation(false);
        }
    }

    private void initToolBar() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (Preference.isNightMode(getApplicationContext())) {
            AppCompatDelegate
                    .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);

        } else {
            AppCompatDelegate
                    .setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);

        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, mDrawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(toggle);
        toggle.syncState();


        mNavigationView.inflateMenu(R.menu.activity_base_drawer_main);
        menuNavigation(mNavigationView.getMenu());
        navigationSubCategory(mNavigationView.getMenu());
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavHeaderView = mNavigationView.inflateHeaderView(R.layout.nav_header_base);


        if (Preference.isNightMode(this)) {
            LinearLayout navContainer = mNavHeaderView.findViewById(R.id.nav_container);
            navContainer.setBackgroundResource(R.drawable.dark_side_nav_bar);
        }

    }

    private void menuNavigation(Menu menu) {

        if (menu == null) return;

        MenuItem itemHome = menu.findItem(R.id.nav_home);

        MenuItem itemModePopularText = menu.findItem(R.id.nav_mode_popular);
        MenuItem itemModeAllText = menu.findItem(R.id.nav_mode_all);
        MenuItem itemModeSubscriptions = menu.findItem(R.id.nav_mode_subscriptions);
        MenuItem itemModeFavorite = menu.findItem(R.id.nav_mode_favorite);
        MenuItem itemModeRefresh = menu.findItem(R.id.nav_mode_refresh);
        MenuItem itemModeSettings = menu.findItem(R.id.nav_mode_settings);

        itemHome.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_home)
                .respectFontBounds(true));

        itemModePopularText.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_trending_up)
                .respectFontBounds(true));

        itemModeAllText.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_view_comfy)
                .respectFontBounds(true));

        itemModeSubscriptions.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_view_headline)
                .respectFontBounds(true));

        itemModeFavorite.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_star)
                .respectFontBounds(true));

        itemModeRefresh.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_refresh)
                .respectFontBounds(true));

        itemModeSettings.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_settings)
                .respectFontBounds(true));


        switch (Preference.getTypeMode(mContext)) {

            case Costant.NAV_MODE_HOME:
                itemHome.setEnabled(true);
                itemHome.setChecked(false);
                break;

            case Costant.NAV_MODE_POPOLAR:
                itemModePopularText.setEnabled(true);
                itemModePopularText.setChecked(true);
                break;

            case Costant.NAV_MODE_ALL:
                itemModeAllText.setEnabled(true);
                itemModeAllText.setChecked(true);
                break;

            case Costant.NAV_MODE_SUBSCRIPTIONS:
                itemModeSubscriptions.setEnabled(true);
                itemModeSubscriptions.setChecked(false);
                break;

            case Costant.NAV_MODE_FAVORITE:
                itemModeFavorite.setEnabled(true);
                itemModeFavorite.setChecked(false);
                break;

            case Costant.NAV_MODE_REFRESH:
                itemModeRefresh.setEnabled(true);
                itemModeRefresh.setChecked(false);
                break;

            case Costant.NAV_MODE_SETTINGS:
                itemModeSettings.setEnabled(true);
                itemModeSettings.setChecked(false);
                break;

            default:
                itemHome.setChecked(false);
        }
    }

    private void navigationSubCategory(Menu menu) {

        if (menu == null) return;

        int groupId = menu.findItem(R.id.nav_mode_subs).getGroupId();

        int colorTheme = Color.DKGRAY;
        if (Preference.isNightMode(mContext)) {
            colorTheme = Color.WHITE;
        }

        ArrayList<String> tabArrayList = new TabData(mContext).getTabArrayList();

        for (String string : tabArrayList) {

            TypefaceSpan typefaceSpan = new TypefaceSpan("/font/roboto_thin.ttf");
            SpannableStringBuilder title = new SpannableStringBuilder(string);
            title.setSpan(typefaceSpan, 0, title.length(), 0);
            title.setSpan(new ForegroundColorSpan(colorTheme), 0, title.length(), 0);

            MenuItem menuItem = menu.add(groupId, Menu.NONE, Menu.NONE, title);

            menuItem.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_account_circle)
                    .respectFontBounds(true));

            menuItem.setOnMenuItemClickListener(item -> {
                Preference.setLastCategory(mContext, item.getTitle().toString());
                Preference.setLastTarget(mContext, Costant.NAVIGATION_MAIN_TARGET);
                updateOperation(true);
                mDrawer.closeDrawer(GravityCompat.START);
                return true;
            });

        }
    }


}
