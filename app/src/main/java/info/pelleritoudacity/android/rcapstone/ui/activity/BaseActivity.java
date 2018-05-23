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
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.model.reddit.RedditAboutMe;
import info.pelleritoudacity.android.rcapstone.rest.AboutMeExecute;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.TextUtil.stringToArray;

public class BaseActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AboutMeExecute.RestAboutMe {

    private int mLayoutResource;
    private View mNavHeaderView;

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
            mNavHeaderView = navigationView.inflateHeaderView(R.layout.nav_header_base);
        }

        String accessToken = PrefManager.getStringPref(this, R.string.pref_session_access_token);

        if (!TextUtils.isEmpty(accessToken)) {
            new AboutMeExecute(accessToken).loginData(this);
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

        MenuItem menuItemLogin;
        MenuItem menuItemLogout;

        if ((getLayoutResource() == R.layout.activity_main) ||
                (getLayoutResource() == R.layout.activity_subreddit)) {

            menuItemLogin = menu.findItem(R.id.menu_action_login);
            menuItemLogout = menu.findItem(R.id.menu_action_logout);

            if (PrefManager.getBoolPref(getApplicationContext(), R.string.pref_login_start)) {
                menuItemLogin.setVisible(false);
                menuItemLogout.setVisible(true);
            } else {
                menuItemLogin.setVisible(true);
                menuItemLogout.setVisible(false);
            }

            int prefMenu = 0;

            if (getApplicationContext() != null) {
                prefMenu = PrefManager.getIntPref(getApplicationContext(), R.string.pref_type_mode);

            }

            switch (prefMenu) {

                case Costants.NAV_MODE_HOME:
                default:
                    menuItemLogin.setChecked(false);
            }
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if ((getLayoutResource() == R.layout.activity_main) ||
                (getLayoutResource() == R.layout.activity_subreddit) ||
                (getLayoutResource() == R.layout.activity_submanage)) {

            int id = item.getItemId();
            switch (id) {

                case R.id.menu_action_restore:
                    startActivity(new Intent(this, MainActivity.class).putExtra(Costants.EXTRA_RESTORE_MANAGE,Costants.RESTORE_MANAGE_RESTORE));
                    return true;
                case R.id.menu_action_login:
                    startActivity(new Intent(this, LoginActivity.class));
                    return true;
                case R.id.menu_action_logout:
                    startActivity(new Intent(this, LogoutActivity.class));
                    return true;
                case R.id.menu_action_settings:
                    startActivity(new Intent(this, SettingsActivity.class));
                    return true;

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
                PrefManager.putIntPref(getApplicationContext(), R.string.pref_type_mode, Costants.NAV_MODE_HOME);
                openHomeActivity();
                break;
            case R.id.nav_mode_subscriptions:
                PrefManager.putIntPref(getApplicationContext(), R.string.pref_type_mode, Costants.NAV_MODE_SUBSCRIPTIONS);
                item.setEnabled(true);
                startActivity(new Intent(this, SubManageActivity.class));
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
            case R.layout.activity_subreddit:
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

            switch (PrefManager.getIntPref(context, R.string.pref_type_mode)) {
                case Costants.NAV_MODE_SUBSCRIPTIONS:
                    itemModeSubscriptions.setChecked(false);
                    itemModeSubscriptions.setEnabled(true);
                    break;
                case Costants.NAV_MODE_REFRESH:
                    itemModeRefresh.setChecked(false);
                    itemModeRefresh.setEnabled(true);
                    break;
                default:
                    itemHome.setChecked(false);
            }
        }

    }

    private void menuGroupSubs(Context context, Menu menu) {
        if ((context != null) && (menu != null)) {

            String stringLink = Costants.DEFAULT_SUBREDDIT_CATEGORY;

            String prefString = PrefManager.getStringPref(getApplicationContext(), R.string.pref_subreddit_key);

            if (!TextUtils.isEmpty(prefString)) {
                stringLink = prefString;
            }

            ArrayList<String> arrayList = stringToArray(stringLink);

            int groupId = menu.findItem(R.id.nav_mode_subs).getGroupId();

            for (String string : arrayList) {

                TypefaceSpan typefaceSpan = new TypefaceSpan("/font/roboto_thin.ttf"); // OR  THIS
                SpannableStringBuilder title = new SpannableStringBuilder(string);
                title.setSpan(typefaceSpan, 0, title.length(), 0);
                title.setSpan(new ForegroundColorSpan(Color.DKGRAY), 0, title.length(), 0);

                MenuItem menuItem = menu.add(groupId, Menu.NONE, Menu.NONE, title);

                menuItem.setIcon(new IconicsDrawable(this, MaterialDesignIconic.Icon.gmi_account_circle)
                        .respectFontBounds(true));

                menuItem.setOnMenuItemClickListener(item -> {
                    Intent intent = new Intent(getApplication(), SubRedditActivity.class);
                    intent.putExtra(Costants.EXTRA_SUBREDDIT_CATEGORY, item.getTitle().toString());

                    if(this.getClass().getSimpleName().equals(SubRedditActivity.class.getSimpleName())){
                        finish();
                    }

                    startActivity(intent);

                    return true;
                });

            }

        }
    }


    protected void openHomeActivity() {
        startActivity(new Intent(this, MainActivity.class)
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
                PrefManager.putStringPref(getApplicationContext(), R.string.pref_login_name, name);
                PrefManager.putBoolPref(getApplicationContext(), R.string.pref_login_over18, isOver18);

                TextView loginNameNavHeader = mNavHeaderView.findViewById(R.id.tv_nav_name);
                loginNameNavHeader.setText(name);
            }
        }
    }

    @Override
    public void onErrorAboutMe(Throwable t) {

    }

}
