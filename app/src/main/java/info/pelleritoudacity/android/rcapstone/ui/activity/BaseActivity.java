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

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;


import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditAboutMe;
import info.pelleritoudacity.android.rcapstone.data.rest.AboutMeExecute;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.TextUtil.stringToArray;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AboutMeExecute.RestAboutMe {

    private int mLayoutResource;
    private View mNavHeaderView;
    private Unbinder unbinder;

    public ArrayList<String> getTabArrayList() {
        return mTabArrayList;
    }

    private ArrayList<String> mTabArrayList;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.ThemeOverlay_AppCompat_Dark);
        }

        ViewStub mStub;

        setContentView(R.layout.activity_base);

        if (getLayoutResource() > 0) {
            mStub = findViewById(R.id.stub_base_layout);
            mStub.setLayoutResource(getLayoutResource());
            mStub.inflate();

        }

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);

        if (navigationView != null) {
            menuNavigation(navigationView);
            mNavHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_base);
        }


        if (PermissionUtil.isLogged(getApplicationContext())) {
            new AboutMeExecute(PermissionUtil.getToken(getApplicationContext())).loginData(this);
        }

        if (TextUtils.isEmpty(Preference.getSubredditSort(getApplicationContext()))) {
            Preference.setSubredditSort(getApplicationContext(), Costant.DEFAULT_SORT_BY);

        }


        Timber.plant(new Timber.DebugTree());
        unbinder = ButterKnife.bind(this);

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if ((drawer != null) && (drawer.isDrawerOpen(GravityCompat.START))) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();

        switch (getLayoutResource()) {
            case R.layout.activity_subreddit:
                inflater.inflate(R.menu.main, menu);
                break;
            case R.layout.activity_submanage:
                inflater.inflate(R.menu.manage_menu, menu);
                MenuItem menuItemRestore;
                menuItemRestore = menu.findItem(R.id.menu_action_restore);
                menuItemRestore.setIcon(
                        new IconicsDrawable(getApplicationContext(), MaterialDesignIconic.Icon.gmi_undo)
                                .colorRes(R.color.white)
                                .sizeDp(24)
                                .respectFontBounds(true));
                break;
            default:
                inflater.inflate(R.menu.main, menu);
        }


        return true;
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {
        Context context = getApplicationContext();
        MenuItem menuItemLogin;
        MenuItem menuItemLogout;

        if (getLayoutResource() == R.layout.activity_subreddit) {

            menuItemLogin = menu.findItem(R.id.menu_action_login);
            menuItemLogout = menu.findItem(R.id.menu_action_logout);

            if (Preference.isLoginStart(getApplicationContext())) {
                menuItemLogin.setVisible(false);
                menuItemLogout.setVisible(true);
            } else {
                menuItemLogin.setVisible(true);
                menuItemLogout.setVisible(false);
            }

            int prefMenu = 0;

            if (getApplicationContext() != null) {
                prefMenu = Preference.getTypeMode(getApplicationContext());

            }

            switch (prefMenu) {

                case Costant.NAV_MODE_HOME:
                default:
                    menuItemLogin.setChecked(false);
            }

            menuGeneralSettings(context, menu, Costant.DEFAULT_GENERAL_SETTINGS);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if ((getLayoutResource() == R.layout.activity_subreddit) ||
                (getLayoutResource() == R.layout.activity_sub_reddit_detail) ||
                (getLayoutResource() == R.layout.activity_submanage)) {

            int id = item.getItemId();
            switch (id) {

                case R.id.menu_action_restore:
                    startActivity(new Intent(this, SubRedditActivity.class).putExtra(Costant.EXTRA_RESTORE_MANAGE, Costant.RESTORE_MANAGE_RESTORE));
                    return true;
                case R.id.menu_action_login:
                    startActivity(new Intent(this, LoginActivity.class));
                    return true;
                case R.id.menu_action_logout:
                    startActivity(new Intent(this, LogoutActivity.class));
                    return true;
                case R.id.menu_action_refresh:
                    menuClickRefresh(getLayoutResource());
                    return true;
                case R.id.menu_action_settings:
                    startActivity(new Intent(this, SettingsActivity.class));
                    return true;

                case R.id.submenu_filter_hot:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_HOT);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_NOTHING);
                    break;
                case R.id.submenu_filter_new:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_NEW);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_NOTHING);
                    break;
                case R.id.submenu_filter_rising:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_RISING);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_NOTHING);
                    break;

                case R.id.submenu_top_hour:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_TOP);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_HOUR);
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;
                case R.id.submenu_top_day:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_TOP);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_DAY);
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;
                case R.id.submenu_top_week:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_TOP);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_WEEK);
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;
                case R.id.submenu_top_month:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_TOP);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_MONTH);
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;
                case R.id.submenu_top_year:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_TOP);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_YEAR);
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;
                case R.id.submenu_top_all:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_TOP);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_ALL);
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;

                case R.id.submenu_controver_hour:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_CONTROVERSIAL);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_HOUR);
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;
                case R.id.submenu_controver_day:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_CONTROVERSIAL);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_DAY);
                    break;
                case R.id.submenu_controver_week:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_CONTROVERSIAL);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_WEEK);
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;
                case R.id.submenu_controver_month:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_CONTROVERSIAL);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_MONTH);
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;
                case R.id.submenu_controver_year:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_CONTROVERSIAL);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_YEAR);
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;
                case R.id.submenu_controver_all:
                    Preference.setSubredditSort(getApplicationContext(), Costant.LABEL_SUBMENU_CONTROVERSIAL);
                    Preference.setTimeSort(getApplicationContext(), Costant.LABEL_TIME_ALL);
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;
                case R.id.action_general_images:
                    Preference.setGeneralImages(getApplicationContext(), !Preference.isGeneralImages(getApplicationContext()));
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;

                case R.id.action_general_gifs:
                    Preference.setGeneralGifs(getApplicationContext(), !Preference.isGeneralGifs(getApplicationContext()));
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;

                case R.id.action_general_albums:
                    Preference.setGeneralAlbums(getApplicationContext(), !Preference.isGeneralAlbums(getApplicationContext()));
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;

                case R.id.action_general_videos:
                    Preference.setGeneralVideos(getApplicationContext(), !Preference.isGeneralVideos(getApplicationContext()));
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;

                case R.id.action_general_self:
                    Preference.setGeneralSelf(getApplicationContext(), !Preference.isGeneralSelf(getApplicationContext()));
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;

                case R.id.action_general_links:
                    Preference.setGeneralLinks(getApplicationContext(), !Preference.isGeneralLinks(getApplicationContext()));
                    SubRedditActivity.homeActivity(getApplicationContext());
                    break;


                default:
                    return super.onOptionsItemSelected(item);
            }

        }
        return true;

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {

            case R.id.nav_home:
                Preference.setTypeMode(getApplicationContext(), Costant.NAV_MODE_HOME);
                openHomeActivity();
                break;

            case R.id.nav_mode_all:
                targetMenuMain(R.id.nav_mode_all);
                break;

            case R.id.nav_mode_popular:
                targetMenuMain(R.id.nav_mode_popular);
                break;

            case R.id.nav_mode_subscriptions:
                Preference.setTypeMode(getApplicationContext(), Costant.NAV_MODE_SUBSCRIPTIONS);
                item.setEnabled(true);
                startActivity(new Intent(this, SubManageActivity.class));
                break;
            case R.id.nav_mode_refresh:
                menuClickRefresh(getLayoutResource());
                item.setEnabled(true);
                break;

            case R.id.nav_mode_settings:
                Preference.setTypeMode(getApplicationContext(), Costant.NAV_MODE_SETTINGS);
                item.setEnabled(true);
                startActivity(new Intent(this, SettingsActivity.class));
                break;

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }

    private void menuClickRefresh(int layoutResource) {
        switch (layoutResource) {
            case R.layout.activity_subreddit:
                Intent intentSubreddit = new Intent(getApplicationContext(), SubRedditActivity.class);
                intentSubreddit.putExtra(Costant.EXTRA_ACTIVITY_SUBREDDIT_REFRESH, true);
                getApplicationContext().startActivity(intentSubreddit);
                break;

            case R.layout.activity_sub_reddit_detail:
                Intent intentSubredditDetail = new Intent(getApplicationContext(), SubRedditDetailActivity.class);
                intentSubredditDetail.putExtra(Costant.EXTRA_ACTIVITY_SUBREDDIT_DETAIL_REFRESH, true);
                getApplicationContext().startActivity(intentSubredditDetail);
                break;

            default:
        }

    }

    private void targetMenuMain(int resource) {

        String constantCategory = null;
        String constantsTarget = null;

        switch (resource) {

            case R.id.nav_mode_popular:
                constantCategory = Costant.SUBREDDIT_CATEGORY_POPULAR;
                constantsTarget = Costant.SUBREDDIT_TARGET_POPULAR;
                break;

            case R.id.nav_mode_all:
                constantCategory = Costant.SUBREDDIT_CATEGORY_ALL;
                constantsTarget = Costant.SUBREDDIT_TARGET_ALL;
                break;

        }

        Intent intent = new Intent(getApplication(), SubRedditActivity.class);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, constantCategory);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_TARGET, constantsTarget);

        if (this.getClass().getSimpleName().equals(SubRedditActivity.class.getSimpleName())) {
            finish();
        }
        startActivity(intent);
    }

    private void menuNavigation(NavigationView navigationView) {

        switch (getLayoutResource()) {
            case R.layout.activity_subreddit:
            case R.layout.activity_sub_reddit_detail:
            default:
                navigationView.inflateMenu(R.menu.activity_base_drawer_main);
                menuItemBase(getApplicationContext(), navigationView.getMenu());
                menuGroupSubs(getApplicationContext(), navigationView.getMenu());
        }

        navigationView.setNavigationItemSelectedListener(this);

    }

    private void menuItemBase(Context context, Menu menu) {
        if ((context != null) && (menu != null)) {
            MenuItem itemHome = menu.findItem(R.id.nav_home);

            MenuItem itemModePopularText = menu.findItem(R.id.nav_mode_popular);
            MenuItem itemModeAllText = menu.findItem(R.id.nav_mode_all);
            MenuItem itemModeSearchText = menu.findItem(R.id.nav_mode_search);
            MenuItem itemModeSubscriptions = menu.findItem(R.id.nav_mode_subscriptions);
            MenuItem itemModeRefresh = menu.findItem(R.id.nav_mode_refresh);
            MenuItem itemModeSettings = menu.findItem(R.id.nav_mode_settings);

            itemHome.setIcon(new IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_home)
                    .respectFontBounds(true));

            itemModePopularText.setIcon(new IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_trending_up)
                    .respectFontBounds(true));

            itemModeAllText.setIcon(new IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_view_comfy)
                    .respectFontBounds(true));

            itemModeSearchText.setIcon(new IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_search)
                    .respectFontBounds(true));

            itemModeSubscriptions.setIcon(new IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_view_headline)
                    .respectFontBounds(true));

            itemModeRefresh.setIcon(new IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_refresh)
                    .respectFontBounds(true));

            itemModeSettings.setIcon(new IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_settings)
                    .respectFontBounds(true));

            switch (Preference.getTypeMode(getApplicationContext())) {
                case Costant.NAV_MODE_SUBSCRIPTIONS:
                    itemModeSubscriptions.setEnabled(true);
                    itemModeSubscriptions.setChecked(false);
                    break;
                case Costant.NAV_MODE_REFRESH:
                    itemModeRefresh.setEnabled(true);
                    itemModeRefresh.setChecked(false);
                    break;
                default:
                    itemHome.setChecked(false);
            }
        }

    }

    private void menuGroupSubs(Context context, Menu menu) {
        if ((context != null) && (menu != null)) {

            String stringLink = Costant.DEFAULT_SUBREDDIT_CATEGORY;

            String prefString = Preference.getSubredditKey(getApplicationContext());

            if (!TextUtils.isEmpty(prefString)) {
                stringLink = prefString;
            }

            mTabArrayList = stringToArray(stringLink);

            int groupId = menu.findItem(R.id.nav_mode_subs).getGroupId();

            for (String string : mTabArrayList) {

                TypefaceSpan typefaceSpan = new TypefaceSpan("/font/roboto_thin.ttf"); // OR  THIS
                SpannableStringBuilder title = new SpannableStringBuilder(string);
                title.setSpan(typefaceSpan, 0, title.length(), 0);
                title.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0, title.length(), 0);

                MenuItem menuItem = menu.add(groupId, Menu.NONE, Menu.NONE, title);

                menuItem.setIcon(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_account_circle)
                        .respectFontBounds(true));

                menuItem.setOnMenuItemClickListener(item -> {
                    Intent intent = new Intent(getApplication(), SubRedditActivity.class);
                    intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, item.getTitle().toString());

                    if (this.getClass().getSimpleName().equals(SubRedditActivity.class.getSimpleName())) {
                        finish();
                    }

                    startActivity(intent);

                    return true;
                });

            }

        }
    }

    private void menuGeneralSettings(Context context, Menu menu, boolean defaultValue) {

        MenuItem itemGeneralImages = menu.findItem(R.id.action_general_images);
        MenuItem itemGeneralVideos = menu.findItem(R.id.action_general_videos);
        MenuItem itemGeneralGifs = menu.findItem(R.id.action_general_gifs);
        MenuItem itemGeneralAlbums = menu.findItem(R.id.action_general_albums);
        MenuItem itemGeneralLinks = menu.findItem(R.id.action_general_links);
        MenuItem itemGeneralSelf = menu.findItem(R.id.action_general_self);

        if (!Preference.isGeneralInit(context)) {
            Preference.setGeneralImages(context, defaultValue);
            Preference.setGeneralVideos(context, defaultValue);
            Preference.setGeneralGifs(context, defaultValue);
            Preference.setGeneralAlbums(context, defaultValue);
            Preference.setGeneralLinks(context, defaultValue);
            Preference.setGeneralSelf(context, defaultValue);

            itemGeneralImages.setChecked(defaultValue);
            itemGeneralVideos.setChecked(defaultValue);
            itemGeneralGifs.setChecked(defaultValue);
            itemGeneralAlbums.setChecked(defaultValue);
            itemGeneralLinks.setChecked(defaultValue);
            itemGeneralSelf.setChecked(defaultValue);

            Preference.setGeneralInit(context, true);

        } else {
            if (Preference.isGeneralImages(context)) {
                itemGeneralImages.setChecked(true);
            } else {
                itemGeneralImages.setChecked(false);

            }

            if (Preference.isGeneralGifs(context)) {
                itemGeneralGifs.setChecked(true);
            } else {
                itemGeneralGifs.setChecked(false);

            }

            if (Preference.isGeneralAlbums(context)) {
                itemGeneralAlbums.setChecked(true);
            } else {
                itemGeneralAlbums.setChecked(false);

            }

            if (Preference.isGeneralVideos(context)) {
                itemGeneralVideos.setChecked(true);
            } else {
                itemGeneralVideos.setChecked(false);

            }

            if (Preference.isGeneralLinks(context)) {
                itemGeneralLinks.setChecked(true);
            } else {
                itemGeneralLinks.setChecked(false);

            }

            if (Preference.isGeneralSelf(context)) {
                itemGeneralSelf.setChecked(true);
            } else {
                itemGeneralSelf.setChecked(false);

            }
        }
    }


    protected void openHomeActivity() {
        startActivity(new Intent(this, SubRedditActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }


    void setLayoutResource(int layoutResource) {
        mLayoutResource = layoutResource;
    }

    private int getLayoutResource() {
        return mLayoutResource;
    }

    @Override
    public void onRestAboutMe(RedditAboutMe listenerData) {
        if (listenerData != null) {
            String name = listenerData.getName();
            boolean isOver18 = listenerData.isOver18();
            if (!TextUtils.isEmpty(name)) {
                Preference.setLoginName(getApplicationContext(), name);
                Preference.setLoginOver18(getApplicationContext(), isOver18);

                TextView loginNameNavHeader = mNavHeaderView.findViewById(R.id.tv_nav_name);
                loginNameNavHeader.setText(name);
            }
        }
    }

    @Override
    public void onErrorAboutMe(Throwable t) {

    }

}
