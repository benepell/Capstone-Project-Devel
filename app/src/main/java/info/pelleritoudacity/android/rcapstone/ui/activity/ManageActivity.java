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

import android.app.SearchManager;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.exoplayer2.util.Util;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.BuildConfig;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.operation.T5Operation;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.MainViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.PrefCategoryViewModel;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.PrefCategoryViewModelFactory;
import info.pelleritoudacity.android.rcapstone.data.rest.MineExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.SearchExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.ManageFragment;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubscribeFragment;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class ManageActivity extends AppCompatActivity
        implements SearchView.OnQueryTextListener, SubscribeFragment.OnRestCallBack {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.submanage_container)
    public CoordinatorLayout mContainer;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.nested_scrollview_submanage)
    public NestedScrollView mNestedScrollView;


    private WeakReference<Context> mWeakContext;
    private boolean isAdded;
    private SearchView mSearchView;
    private String mSearchString;
    private String mTypeString;
    private AlertDialog mDialog;
    private AppDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeSettingsDark);

        }
        setContentView(R.layout.activity_manage);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        ButterKnife.bind(this);

        mDb = AppDatabase.getInstance(getApplicationContext());
        mWeakContext = new WeakReference<>(getApplicationContext());
        if (savedInstanceState != null) {
            mSearchString = savedInstanceState.getString(Costant.EXTRA_SEARCH_SUBSCRIBE);
            mTypeString = savedInstanceState.getString(Costant.EXTRA_SEARCH_TYPE);

        } else {
            if (getIntent() != null) {
                mSearchString = getIntent().getStringExtra(Costant.EXTRA_SEARCH_SUBSCRIBE);
                mTypeString = getIntent().getStringExtra(Costant.EXTRA_SEARCH_TYPE);

            } else {
                mTypeString = Costant.SEARCH_TYPE_SUBREDDITS;

            }

        }

        if (TextUtils.isEmpty(mSearchString)) {
            updateOperation();

        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            if (mDialog != null) {
                mDialog.dismiss();
            }
        }
    }


    private void updateOperation() {

        Context context = mWeakContext.get();

        if (!NetworkUtil.isOnline(context)) {
            manage();

        } else if (!PermissionUtil.isLogged(context)) {
            T5Operation util = new T5Operation(getApplicationContext(), mDb, null);
            if (TextUtils.isEmpty(Preference.getSubredditKey(getApplicationContext()))) {
                util.insertDefaultSubreddit();
            }
            startFragment(false);

        }

        if (!isAdded) {
            startFragment(false);
        }

    }

    private void startFragment(boolean restoreManage) {
        if (!getSupportFragmentManager().isStateSaved()) {

            ManageFragment manageFragment = ManageFragment.newInstance(restoreManage);
            if (restoreManage) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_list_container, manageFragment).commit();

            } else {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right,
                                R.anim.layout_animation_from_right,
                                R.anim.layout_animation_from_right)
                        .replace(R.id.fragment_list_container, manageFragment).commit();
                isAdded = true;
            }

        } else if (mNestedScrollView == null) {
            isAdded = true;
            updateOperation();

        }

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Context context = mWeakContext.get();

        if (TextUtils.isEmpty(mTypeString)) {
            mTypeString = Costant.SEARCH_TYPE_SUBREDDITS;

        }

        subscribeUpdateOperation(context, query, mTypeString);

        return false;
    }


    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mSearchString = savedInstanceState.getString(Costant.EXTRA_SEARCH_SUBSCRIBE);
        mTypeString = savedInstanceState.getString(Costant.EXTRA_SEARCH_TYPE);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mSearchView != null) {
            outState.putString(Costant.EXTRA_SEARCH_SUBSCRIBE, mSearchView.getQuery().toString());
            outState.putString(Costant.EXTRA_SEARCH_TYPE, mTypeString);

        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClickSubscribe(int position) {
        if ((position > RecyclerView.NO_POSITION)) {
            updateSubreddit(mWeakContext.get());

        }

    }

    @Override
    public void onClickCategory(String category, String fullname, Object userIsSubscriber) {
        if (userIsSubscriber != null) {
            switch (mTypeString) {
                case Costant.SEARCH_TYPE_SUBREDDITS:
                    startCategory(category);
                    break;

                case Costant.SEARCH_TYPE_LINK:
                    startLink(category, fullname);
                    break;

            }
            startCategory(category);
        } else {
            Snackbar.make(mContainer, R.string.text_private_category, Snackbar.LENGTH_LONG).show();


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.manage_menu, menu);
        MenuItem menuItemRestore = menu.findItem(R.id.menu_action_restore);
        menuItemRestore.setIcon(
                new IconicsDrawable(getApplicationContext(), MaterialDesignIconic.Icon.gmi_undo)
                        .colorRes(R.color.white)
                        .sizeDp(24)
                        .respectFontBounds(true));
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_action_restore:
                startFragment(true);
                return true;

            case R.id.menu_action_login:
                if (NetworkUtil.isOnline(this)) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    Snackbar.make(mContainer, R.string.text_no_network, Snackbar.LENGTH_LONG).show();
                }
                return true;

            case R.id.menu_action_logout:
                startActivity(new Intent(this, LoginActivity.class));
                return true;

            case R.id.menu_action_create:
                if (PermissionUtil.isLogged(getApplicationContext())) {
                    startActivity(new Intent(this, WebviewActivity.class));
                } else {
                    Snackbar.make(mContainer, R.string.text_start_login, Snackbar.LENGTH_LONG).show();
                }
                return true;

            case R.id.menu_action_subscribe:
                if (PermissionUtil.isLogged(getApplicationContext())) {
                    showDialogSearch();
                } else {
                    Snackbar.make(mContainer, R.string.text_start_login, Snackbar.LENGTH_LONG).show();
                }
                return true;


            case R.id.menu_action_refresh:
                if (NetworkUtil.isOnline(this)) {
                    recreate();
                } else {
                    Snackbar.make(mContainer, R.string.text_no_network, Snackbar.LENGTH_LONG).show();
                }
                return true;

            case R.id.menu_action_settings:
                startActivity(new Intent(this, SettingsActivity.class));
                return true;
        }
        return false;

    }

    @Override
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        Context context = mWeakContext.get();

        if (menu.findItem(R.id.menu_action_search) == null) {
            getMenuInflater().inflate(R.menu.menu_search, menu);

            MenuItem menuItemSearch = menu.findItem(R.id.menu_action_search);

            menuItemSearch.setIcon(new IconicsDrawable(context, MaterialDesignIconic.Icon.gmi_search)
                    .color(Color.WHITE)
                    .sizeDp(24)
                    .respectFontBounds(true));

            SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


            mSearchView = (SearchView) menuItemSearch.getActionView();
            if (mSearchView != null) {
                mSearchView.setSearchableInfo(Objects.requireNonNull(searchManager).getSearchableInfo(getComponentName()));

                mSearchView.setQueryHint(getString(R.string.text_subscribe_search));
                mSearchView.setOnQueryTextListener(this);
                mSearchView.setIconified(false);

            }

            MenuItem menuItemLogin = menu.findItem(R.id.menu_action_login);
            MenuItem menuItemLogout = menu.findItem(R.id.menu_action_logout);


            if (Preference.isLoginStart(context)) {
                menuItemLogin.setVisible(false);
                menuItemLogout.setVisible(true);

            } else {
                menuItemLogin.setVisible(true);
                menuItemLogout.setVisible(false);

            }


            if (!TextUtils.isEmpty(mSearchString)) {
                menuItemSearch.expandActionView();
                mSearchView.setQuery(mSearchString, true);
                mSearchView.clearFocus();

                MenuItem menuItemRestore = menu.findItem(R.id.menu_action_restore);
                menuItemRestore.setVisible(false);
            }


        }

        return true;

    }

    @Override
    public void onBackPressed() {
        if (mDialog != null) {
            mDialog.dismiss();

        } else {
            super.onBackPressed();

        }
    }

    private void manage() {
        PrefCategoryViewModelFactory factory = new PrefCategoryViewModelFactory(mDb, 0, 1);
        PrefCategoryViewModel viewModel = ViewModelProviders.of(this, factory).get(PrefCategoryViewModel.class);

        viewModel.getTask().observe(this, prefSubRedditEntries -> {
            if (prefSubRedditEntries != null) {
                String name;
                ArrayList<String> arrayList = new ArrayList<>(prefSubRedditEntries.size());
                for (int i = 0; i < prefSubRedditEntries.size(); i++) {
                    name = TextUtil.normalizeSubRedditLink(prefSubRedditEntries.get(i).getName());

                    arrayList.add(name);
                    Preference.setSubredditKey(getApplicationContext(), TextUtil.arrayToString(arrayList));

                }
            }
        });
    }

    private void subscribeUpdateOperation(Context context, String search, String type) {

        if (!PermissionUtil.isLogged(context)) {
            Snackbar.make(mContainer, R.string.text_start_login, Snackbar.LENGTH_LONG).show();
            return;

        }

        if (search.length() < 3) {
            Snackbar.make(mContainer, R.string.text_digit_subscript, Snackbar.LENGTH_LONG).show();
            return;

        }

        new SearchExecute((response, code) -> {

            SubscribeFragment fragment = SubscribeFragment.newInstance(response);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_list_container, fragment).commit();

            if (mDialog != null) {
                mDialog.dismiss();

            }

        }, context, PermissionUtil.getToken(context), search, type).getSearch();


    }

    private void updateSubreddit(Context context) {
        new MineExecute((response, code) -> {
            T5Operation data = new T5Operation(context, mDb, response);
            data.saveData();

            String pref = restorePrefFromDb();

            if (!TextUtils.isEmpty(pref)) {
                Preference.setSubredditKey(context, pref);

            }

        }, context, PermissionUtil.getToken(context),
                Costant.MINE_WHERE_SUBSCRIBER).getMine();

    }

    private String restorePrefFromDb() {
        final String[] stringPref = {""};

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getPrefSubRedditRecords().observe(this, prefSubRedditEntries -> {
            for (int j = 0; j < Objects.requireNonNull(prefSubRedditEntries).size(); j++) {
                if (TextUtils.isEmpty(prefSubRedditEntries.get(j).getName())) {
                    if (j == prefSubRedditEntries.size() - 1) {
                        stringPref[0] += prefSubRedditEntries.get(j).getName();

                    } else {
                        stringPref[0] += prefSubRedditEntries.get(j).getName() + Costant.STRING_SEPARATOR;

                    }

                }
            }
        });

        Preference.setSubredditKey(getApplicationContext(), stringPref[0]);

        return stringPref[0];

    }


    private void startCategory(String category) {
        String filterCat = null;
        if (!TextUtils.isEmpty(category)) {
            filterCat = TextUtil.normalizeSubRedditLink("/".concat(category));
        }
        Intent intent = new Intent(mWeakContext.get(), MainActivity.class);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, filterCat);
        intent.putExtra(Costant.EXTRA_MAIN_TARGET, Costant.DEFAULT_START_VALUE_MAIN_TARGET);
        startActivity(intent);
    }

    private void startLink(String category, String strId) {
        String filterCat = null;
        if (!TextUtils.isEmpty(category)) {
            filterCat = TextUtil.normalizeSubRedditLink("/".concat(category));

        }
        Intent intent = new Intent(mWeakContext.get(), MainActivity.class);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_DETAIL_POSITION, RecyclerView.NO_POSITION);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_DETAIL_CATEGORY, filterCat);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_DETAIL_STR_ID, strId);
        startActivity(intent);
    }

    private void showDialogSearch() {
        mDialog = new AlertDialog.Builder(this)
                .setView(R.layout.dialog_search)
                .create();
        mDialog.show();

        TextView title = mDialog.findViewById(R.id.dialog_title_search);
        EditText editText = mDialog.findViewById(R.id.editext_dialog_search);
        Button cancel = mDialog.findViewById(R.id.dialog_cancel_subscribe);
        Button submit = mDialog.findViewById(R.id.dialog_submit_subscribe);

        if (Preference.isNightMode(this)) {
            Objects.requireNonNull(title).setBackgroundColor(Color.DKGRAY);
        }
        Objects.requireNonNull(cancel).setOnClickListener(view -> mDialog.dismiss());

        Objects.requireNonNull(submit).setOnClickListener(view -> {
            if (Objects.requireNonNull(editText).getText().length() > 2) {
                mSearchString = editText.getText().toString();
                subscribeUpdateOperation(this, mSearchString, Costant.SEARCH_TYPE_SUBREDDITS);

            }
        });

    }

}
