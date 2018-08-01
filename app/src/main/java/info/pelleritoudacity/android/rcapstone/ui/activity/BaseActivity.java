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
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;


import butterknife.ButterKnife;
import butterknife.Unbinder;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditAboutMe;
import info.pelleritoudacity.android.rcapstone.data.rest.AboutMeExecute;
import info.pelleritoudacity.android.rcapstone.ui.helper.MenuBase;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AboutMeExecute.OnRestCallBack {

    private int mLayoutResource;
    private View mNavHeaderView;
    private MenuBase menuBase;
    private Unbinder unbinder;


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        }

        ViewStub mStub;

        setContentView(R.layout.activity_base);

        if (getLayoutResource() > 0) {
            mStub = findViewById(R.id.stub_base_layout);
            mStub.setLayoutResource(getLayoutResource());
            mStub.inflate();

        }

        menuBase = new MenuBase(getApplicationContext(), getLayoutResource());

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

            if (Preference.isNightMode(this)) {
                LinearLayout navContainer = mNavHeaderView.findViewById(R.id.nav_container);
                navContainer.setBackgroundResource(R.drawable.dark_side_nav_bar);
            }

        }

        if (PermissionUtil.isLogged(getApplicationContext())) {
            new AboutMeExecute(this, PermissionUtil.getToken(getApplicationContext())).loginData();
        }

        if (TextUtils.isEmpty(Preference.getSubredditSort(getApplicationContext()))) {
            Preference.setSubredditSort(getApplicationContext(), Costant.DEFAULT_SORT_BY);

        }

        if (TextUtils.isEmpty(Preference.getTimeSort(getApplicationContext()))) {
            Preference.setSubredditSort(getApplicationContext(), Costant.DEFAULT_SORT_TIME);

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
            case R.layout.activity_main:
            case R.layout.activity_detail:
                inflater.inflate(R.menu.main, menu);
                break;

            case R.layout.activity_manage:
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

        switch (getLayoutResource()) {
            case R.layout.activity_main:
            case R.layout.activity_detail:
                menuBase.menuItemIfRoom(menu);
                menuBase.menuGeneralSettings(menu);
                menuBase.menuItemLogin(menu, Preference.isLoginStart(getApplicationContext()));

            case R.layout.activity_manage:
                return true;

            default:
                return false;

        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (getLayoutResource()) {

            case R.layout.activity_main:
            case R.layout.activity_detail:
            case R.layout.activity_manage:

                menuBase.menuItemSelected(item);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        menuBase.navigationItemSelected(item);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;

    }


    private void menuNavigation(NavigationView navigationView) {

        switch (getLayoutResource()) {
            case R.layout.activity_main:
            case R.layout.activity_detail:
            default:
                navigationView.inflateMenu(R.menu.activity_base_drawer_main);
                menuBase.menuNavigation(navigationView.getMenu());
                menuBase.navigationSubCategory(navigationView.getMenu());
        }

        navigationView.setNavigationItemSelectedListener(this);

    }


    void setLayoutResource(int layoutResource) {
        mLayoutResource = layoutResource;
    }

    private int getLayoutResource() {
        return mLayoutResource;
    }

    @Override
    public void success(RedditAboutMe response) {
        if (response != null) {
            String name = response.getName();
            boolean isOver18 = response.isOver18();
            if (!TextUtils.isEmpty(name)) {
                Preference.setLoginName(getApplicationContext(), name);
                Preference.setLoginOver18(getApplicationContext(), isOver18);

                TextView loginNameNavHeader = mNavHeaderView.findViewById(R.id.tv_nav_name);
                loginNameNavHeader.setText(name);
            }
        }
    }

    @Override
    public void unexpectedError(Throwable tList) {

    }
}
