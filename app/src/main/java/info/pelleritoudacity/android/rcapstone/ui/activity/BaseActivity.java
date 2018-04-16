/*
 *  _    _  _     _  _______     ___                      _
 * ( )  ( )( )   ( )(_____  )   |  _`\                   ( )
 * `\`\/'/'`\`\_/'/'     /'/'   | (_) )   __     _ _    _| |   __   _ __
 *   >  <    `\ /'     /'/'     | ,  /  /'__`\ /'_` ) /'_` | /'__`\( '__)
 *  /'/\`\    | |    /'/'___    | |\ \ (  ___/( (_| |( (_| |(  ___/| |
 * (_)  (_)   (_)   (_______)   (_) (_)`\____)`\__,_)`\__,_)`\____)(_)
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

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewStub;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;


import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import timber.log.Timber;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private int mLayoutResource;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);

        if (navigationView != null) {
            menuNavigation(navigationView);
        }

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

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
                inflater.inflate(R.menu.main, menu);
                break;
            default:
                inflater.inflate(R.menu.main, menu);
        }


        return true;
    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        MenuItem menuItemLogin;

        if (getLayoutResource() == R.layout.activity_main) {

            menuItemLogin = menu.findItem(R.id.menu_action_login);


            switch (PrefManager.getIntPref(getApplicationContext(), R.string.pref_type_mode)) {
  /*              case Costants.NAV_MODE_SINGLE:
                    menuItemSingle.setChecked(true);
                    menuItemSingle.setEnabled(false);
                    break;
                case Costants.NAV_MODE_MULTI:
                    menuItemMulti.setChecked(true);
                    menuItemMulti.setEnabled(false);
  */
                case 0:
                    break;
                default:
                    menuItemLogin.setChecked(false);
            }


        } /*else if (getLayoutResource() == R.layout.activity_article_detail) {

            menuItemShare = menu.findItem(R.id.menu_action_share);
            menuItemShare.setIcon(
                    new IconicsDrawable(getApplicationContext(), MaterialDesignIconic.Icon.gmi_share)
                            .colorRes(R.color.white)
                            .sizeDp(24)
                            .respectFontBounds(true));
            menuItemShare.setVisible(true);

            menuItemHome = menu.findItem(R.id.menu_action_home);
            menuItemTextShort = menu.findItem(R.id.menu_action_text_small);
            menuItemTextFull = menu.findItem(R.id.menu_action_text_full);

            switch (PrefManager.getIntPref(getApplicationContext(), R.string.pref_type_mode)) {
                case Costants.NAV_MODE_SMALL_TEXT:
                    menuItemTextShort.setChecked(true);
                    menuItemTextShort.setEnabled(false);
                    break;
                case Costants.NAV_MODE_FULL_TEXT:
                    menuItemTextFull.setChecked(true);
                    menuItemTextFull.setEnabled(false);
                    break;
                default:
                    menuItemHome.setChecked(false);
            }

        }*/
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (getLayoutResource() == R.layout.activity_main) {
            int id = item.getItemId();
            switch (id) {
                case R.id.menu_action_login:
                    openHomeActivity();
                    return true;
                /*case R.id.menu_action_single:
                    PrefManager.putIntPref(getApplicationContext(), R.string.pref_type_mode, Costants.NAV_MODE_SINGLE);
                    openHomeActivity();
                    return true;
                case R.id.menu_action_multi:
                    PrefManager.putIntPref(getApplicationContext(), R.string.pref_type_mode, Costants.NAV_MODE_MULTI);
                    openHomeActivity();
                    return true;
                */
                default:
                    return super.onOptionsItemSelected(item);
            }

        } /*else if (getLayoutResource() == R.layout.activity_article_detail) {

            switch (item.getItemId()) {
                case R.id.menu_action_share:
                    String title = PrefManager.getStringPref(getApplicationContext(), R.string.pref_share_title);
                    String text = PrefManager.getStringPref(getApplicationContext(), R.string.pref_share_text);
                    activityShareText(getApplicationContext(), title, text);

                    Toast.makeText(getApplicationContext(), "share", Toast.LENGTH_LONG).show();
                    return true;
                case R.id.menu_action_home:
                    openHomeActivity();
                    return true;
                case R.id.menu_action_text_small:
                    PrefManager.putIntPref(getApplicationContext(), R.string.pref_type_mode, Costants.NAV_MODE_SMALL_TEXT);
                    openHomeActivity();
                    return true;
                case R.id.menu_action_text_full:
                    PrefManager.putIntPref(getApplicationContext(), R.string.pref_type_mode, Costants.NAV_MODE_FULL_TEXT);
                    openHomeActivity();
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }

        } */ else {
            return true;
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        switch (id) {
            case R.id.nav_home:
                openHomeActivity();
                break;
            case R.id.nav_mode_settings:
                PrefManager.putIntPref(getApplicationContext(), R.string.pref_type_mode, Costants.NAV_MODE_SETTINGS);
                item.setEnabled(true);
                startActivity(new Intent(this, SettingsActivity.class));
                break;
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void menuNavigation(NavigationView navigationView) {

        switch (getLayoutResource()) {
            case R.layout.activity_main:
            default:
                navigationView.inflateMenu(R.menu.activity_base_drawer_main);
                menuItemBase(navigationView.getMenu());
        }

        navigationView.setNavigationItemSelectedListener(this);

    }

    private void menuItemBase(Menu menu) {
        MenuItem itemHome = menu.findItem(R.id.nav_home);

        MenuItem itemModePopularText = menu.findItem(R.id.nav_mode_popular);
        MenuItem itemModeAllText = menu.findItem(R.id.nav_mode_all);
        MenuItem itemModeSearchText = menu.findItem(R.id.nav_mode_search);
        MenuItem itemModeSubscriptions = menu.findItem(R.id.nav_mode_subscriptions);
        MenuItem itemModeRefresh = menu.findItem(R.id.nav_mode_refresh);
        MenuItem itemModeSettings = menu.findItem(R.id.nav_mode_settings);

        itemHome.setIcon(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_home)
                .respectFontBounds(true));

        itemModePopularText.setIcon(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_trending_up)
                .respectFontBounds(true));

        itemModeAllText.setIcon(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_view_comfy)
                .respectFontBounds(true));

        itemModeSearchText.setIcon(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_search)
                .respectFontBounds(true));

        itemModeSubscriptions.setIcon(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_view_headline)
                .respectFontBounds(true));

        itemModeRefresh.setIcon(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_refresh)
                .respectFontBounds(true));

        itemModeSettings.setIcon(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_settings)
                .respectFontBounds(true));

        switch (PrefManager.getIntPref(getApplicationContext(), R.string.pref_type_mode)) {
            case Costants.NAV_MODE_SUBSCRIPTIONS:
                itemModeSubscriptions.setChecked(true);
                itemModeSubscriptions.setEnabled(false);
                break;
            case Costants.NAV_MODE_REFRESH:
                itemModeRefresh.setChecked(false);
                itemModeRefresh.setEnabled(false);
                break;
            default:
                itemHome.setChecked(false);
        }
    }


    void openHomeActivity() {
        startActivity(new Intent(this, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }


    void setLayoutResource(int layoutResource) {
        mLayoutResource = layoutResource;
    }

    private int getLayoutResource() {
        return mLayoutResource;
    }



}

