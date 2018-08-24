package info.pelleritoudacity.android.rcapstone.ui.activity;


import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T1Operation;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.More;
import info.pelleritoudacity.android.rcapstone.data.model.ui.MainModel;
import info.pelleritoudacity.android.rcapstone.data.other.TabData;
import info.pelleritoudacity.android.rcapstone.data.rest.RestMoreExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.RestDetailExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.DetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.fragment.MainFragment;
import info.pelleritoudacity.android.rcapstone.ui.fragment.TitleDetailFragment;
import info.pelleritoudacity.android.rcapstone.ui.helper.DetailHelper;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

public class DetailActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, RestDetailExecute.OnRestCallBack,
        DetailFragment.OnFragmentInteractionListener, RestMoreExecute.OnRestCallBack,
        SwipeRefreshLayout.OnRefreshListener, SearchView.OnQueryTextListener, MainFragment.OnMainClick {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.detail_container)
    public CoordinatorLayout mContainer;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @Nullable // no used in tablet version
    @BindView(R.id.nested_scrollview_detail)
    public NestedScrollView mNestedScrollView;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.swipe_refresh_detail)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.drawer_layout)
    public DrawerLayout mDrawer;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.nav_view)
    public NavigationView mNavigationView;

    private Context mContext;

    private DetailModel model;
    private DetailHelper mDetailHelper;
    private long startTimeoutRefresh;
    private SearchView mSearchView;
    private String mSearchString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        }
        super.onCreate(savedInstanceState);


        if (Utility.isTablet(this)) {
            setContentView(R.layout.activity_detail_tablet);

        } else {
            setContentView(R.layout.activity_detail);

        }

        mContext = getApplicationContext();

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        initToolBar();

        mSwipeRefreshLayout.setOnRefreshListener(this);

        if (Utility.isTablet(mContext)) {
            mSwipeRefreshLayout.setEnabled(false);

        }

        if (Preference.isNightMode(mContext)) {
            mSwipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.GRAY);
            mSwipeRefreshLayout.setColorSchemeColors(Color.GREEN);
        }


        mDetailHelper = new DetailHelper(mContext);

        if (savedInstanceState == null) {
            model = mDetailHelper.initModelTarget(getIntent());

            updateOperation(true);
        } else {
            mSearchString = savedInstanceState.getString(Costant.EXTRA_SEARCH_DETAIL);
            model = savedInstanceState.getParcelable(Costant.EXTRA_PARCEL_ACTIVITY_DETAIL);

        }

        Preference.setLastComment(mContext, Objects.requireNonNull(model).getStrId());

    }

    @Override
    public void onRefresh() {
        updateOperation(false);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        model = savedInstanceState.getParcelable(Costant.EXTRA_PARCEL_ACTIVITY_DETAIL);
        mSearchString = savedInstanceState.getString(Costant.EXTRA_SEARCH_DETAIL);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Costant.EXTRA_PARCEL_ACTIVITY_DETAIL, model);

        if (mSearchView != null) {
            outState.putString(Costant.EXTRA_SEARCH_DETAIL, mSearchView.getQuery().toString());

        }

        super.onSaveInstanceState(outState);

    }

    @Override
    public boolean onQueryTextSubmit(String s) {

        model.setStrQuerySearch(s);

        switch (model.getTarget()) {
            case Costant.DETAIL_TARGET_NO_UPDATE:
            case Costant.DETAIL_TARGET:
                model.setTarget(Costant.SEARCH_DETAIL_TARGET);
                updateOperation(false);
                break;

            case Costant.MORE_DETAIL_TARGET:
                model.setTarget(Costant.MORE_SEARCH_DETAIL_TARGET);
                updateOperation(false);
                break;

            default:
                return false;
        }
        return true;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        return false;
    }

    @Override
    public void clickSelector(int position, int itemCount) {
        if (!Utility.isTablet(mContext)) {
            if (position == itemCount - 1) {
                if (mNestedScrollView != null) {
                    mNestedScrollView.smoothScrollBy(0, mNestedScrollView.getBottom());
                }
            }
        }
    }

    @Override
    public void mainClick(int position, String category, String strId) {
        if (model == null) {
            model = new DetailModel();
        }
        model.setCategory(category);
        model.setStrId(strId);
        model.setPosition(position);
        model.setTarget(Costant.DETAIL_TARGET);

        updateOperation(true);
    }

    @Override
    public void mainFragmentResult(int count) {
        if ((count > 0) && Utility.isTablet(mContext)) mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onClickMore(DetailModel detailModel) {
        model = detailModel;
        updateOperation(true);
    }

    @Override
    public void detailFragmentResult(int count) {
        if (count > 0 && (mSwipeRefreshLayout != null) && (mSwipeRefreshLayout.isRefreshing())) {
            mSwipeRefreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void success(List<T1> response, int code) {
        if ((response != null) && (model.getStrId() != null)) {
            T1Operation data = new T1Operation(getApplicationContext());
            if (data.saveData(response, model.getStrId())) {
                startFragment(model, false);
            } else {
                Snackbar.make(mContainer, R.string.error_state_critical, Snackbar.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void success(More response, int code) {
        if (response != null) {
            if (response.getJson().getData() != null) {

                T1Operation t1moreOperation = new T1Operation(getApplicationContext());

                if (t1moreOperation.saveMoreData(response.getJson())) {

                    startFragment(model, true);

                }

            }

        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        mDrawer.closeDrawer(GravityCompat.START);

        switch (item.getItemId()) {

            case R.id.nav_home:
                Preference.setLastTarget(mContext, Costant.NAVIGATION_MAIN_TARGET);
                Preference.setTypeMode(mContext, Costant.NAV_MODE_HOME);
                startActivity(new Intent(mContext, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;

            case R.id.nav_mode_all:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_ALL);
                Preference.setLastCategory(mContext, Costant.CATEGORY_ALL);
                Preference.setLastTarget(mContext, Costant.ALL_MAIN_TARGET);
                targetMenuMain(R.id.nav_mode_all);

                break;

            case R.id.nav_mode_popular:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_POPOLAR);
                Preference.setLastCategory(mContext, Costant.CATEGORY_POPULAR);
                Preference.setLastTarget(mContext, Costant.POPULAR_MAIN_TARGET);
                targetMenuMain(R.id.nav_mode_popular);
                break;

            case R.id.nav_mode_subscriptions:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_SUBSCRIPTIONS);
                item.setEnabled(true);
                startActivity(new Intent(this, ManageActivity.class));
                break;

            case R.id.nav_mode_favorite:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_FAVORITE);
                Preference.setLastTarget(mContext, Costant.FAVORITE_MAIN_TARGET);
                targetMenuMain(R.id.nav_mode_favorite);
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
    protected boolean onPrepareOptionsPanel(View view, Menu menu) {

        if (!Utility.isTablet(getApplicationContext())) {

            MenuItem menuItemFilter = menu.findItem(R.id.submenu_filter_posts);

            MenuItem itemGeneralImages = menu.findItem(R.id.action_general_images);
            MenuItem itemGeneralVideos = menu.findItem(R.id.action_general_videos);
            MenuItem itemGeneralGifs = menu.findItem(R.id.action_general_gifs);
            MenuItem itemGeneralLinks = menu.findItem(R.id.action_general_links);
            MenuItem itemGeneralAlbums = menu.findItem(R.id.action_general_albums);
            MenuItem itemGeneralSelf = menu.findItem(R.id.action_general_self);


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
        }

        MenuItem menuItemLogin = menu.findItem(R.id.menu_action_login);
        MenuItem menuItemLogout = menu.findItem(R.id.menu_action_logout);

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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        if (Utility.isTablet(mContext)) {
            inflater.inflate(R.menu.tablet, menu);

        } else {
            inflater.inflate(R.menu.main, menu);

        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

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
    public void onBackPressed() {
        if ((mDrawer != null) & (Objects.requireNonNull(mDrawer).isDrawerOpen(GravityCompat.START))) {
            mDrawer.closeDrawer(GravityCompat.START);

        } else if ((model != null) && (model.getTarget() == Costant.MORE_DETAIL_TARGET)) {
            model.setTarget(Costant.DETAIL_TARGET);
            model.setStrArrId(null);
            model.setStrLinkId(null);

            updateOperation(false);

        } else {
            super.onBackPressed();

        }

    }

    private void updateOperation(boolean refresh) {

        if ((refresh) && NetworkUtil.isOnline(mContext)) {
            mSwipeRefreshLayout.setRefreshing(true);
        }

        if (!Utility.isTablet(mContext)) {
            if (mNestedScrollView != null) {
                if (mNestedScrollView.getChildAt(0).getScrollY() > 0) {
                    mNestedScrollView.getChildAt(0).setScrollY(mNestedScrollView.getTop());
                }
            }
        }
        String tokenLogin = PermissionUtil.getToken(mContext);

        switch (mDetailHelper.getJob(model, isUpdateData(), NetworkUtil.isOnline(mContext))) {

            case Costant.DETAIL_TARGET_NO_UPDATE:

                startFragment(model, false);

                break;

            case Costant.DETAIL_TARGET:
                if (System.currentTimeMillis() - startTimeoutRefresh > Costant.DEFAULT_OPERATION_REFRESH) {
                    startTimeoutRefresh = System.currentTimeMillis();
                    new RestDetailExecute(this, mContext, tokenLogin, model).getData();

                } else {
                    startFragment(model, false);

                }
                break;

            case Costant.MORE_DETAIL_TARGET:
                if (System.currentTimeMillis() - startTimeoutRefresh > Costant.DEFAULT_OPERATION_REFRESH) {
                    startTimeoutRefresh = System.currentTimeMillis();
                    new RestMoreExecute(this, mContext, tokenLogin, model).getMoreData();

                } else {
                    startFragment(model, true);

                }
                break;

            case Costant.SEARCH_DETAIL_TARGET:
                startFragment(model, false);
                break;

            case Costant.MORE_SEARCH_DETAIL_TARGET:
                startFragment(model, false);
                break;

            case Costant.FAVORITE_DETAIL_TARGET:
                startFragment(model, false);
                break;

        }

    }

    private void createFragmentTablet(Context context, String category, int position) {
        MainModel mainModel;

        if (Utility.isTablet(context)) {
            mainModel = new MainModel();
            mainModel.setPosition(position);
            mainModel.setCategory(category);
            mainModel.setTarget(Costant.DEFAULT_START_VALUE_MAIN_TARGET);
            mainModel.setOver18(Preference.isLoginOver18(mContext));

            MainFragment mainFragment = MainFragment.newInstance(mainModel);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_subreddit_container, mainFragment).commit();

        }

    }

    private void startFragment(DetailModel m, boolean moreFragment) {

        if (!getSupportFragmentManager().isStateSaved()) {

            if (moreFragment) {

                createFragmentTablet(mContext, m.getCategory(), m.getPosition());

                DetailFragment fragment = DetailFragment.newInstance(m);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail_container, fragment).commit();

            } else {

                createFragmentTablet(mContext, m.getCategory(), m.getPosition());

                TitleDetailFragment titleDetailFragment = TitleDetailFragment.newInstance(m.getStrId());
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_title_container, titleDetailFragment).commit();

                DetailFragment fragment = DetailFragment.newInstance(m);
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_detail_container, fragment).commit();


            }
        } else {
            if ((!Utility.isTablet(mContext)) && (mNestedScrollView == null)) {
                updateOperation(true);
            }
        }


    }

    private boolean isUpdateData() {
        return new DataUtils(mContext).isSyncDataDetail(Contract.T1dataEntry.CONTENT_URI,
                model, Preference.getGeneralSettingsSyncFrequency(mContext),
                Preference.getGeneralSettingsItemPage(mContext));
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
        View mNavHeaderView = mNavigationView.inflateHeaderView(R.layout.nav_header_base);

        if (mNavHeaderView != null) {
            TextView loginNameNavHeader = mNavHeaderView.findViewById(R.id.tv_nav_name);
            if (!TextUtils.isEmpty(Preference.getSessionUsername(mContext))) {
                loginNameNavHeader.setText(Preference.getSessionUsername(mContext));
            }
        }

        if (Preference.isNightMode(this)) {
            LinearLayout navContainer = Objects.requireNonNull(mNavHeaderView).findViewById(R.id.nav_container);
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

            TypefaceSpan typefaceSpan = new TypefaceSpan("/font/roboto_thin.ttf"); // OR  THIS
            SpannableStringBuilder title = new SpannableStringBuilder(string);
            title.setSpan(typefaceSpan, 0, title.length(), 0);
            title.setSpan(new ForegroundColorSpan(colorTheme), 0, title.length(), 0);

            MenuItem menuItem = menu.add(groupId, Menu.NONE, Menu.NONE, title);

            menuItem.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_account_circle)
                    .respectFontBounds(true));

            menuItem.setOnMenuItemClickListener(item -> {

                Intent intent = new Intent(mContext, MainActivity.class);
                intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, item.getTitle().toString());
                intent.putExtra(Costant.EXTRA_MAIN_TARGET, Costant.NAVIGATION_MAIN_TARGET);

                startActivity(intent);

                return true;
            });

        }
    }

    private void targetMenuMain(int resource) {

        String constantCategory = null;
        String constantsTarget = null;

        switch (resource) {

            case R.id.nav_mode_popular:
                constantCategory = Costant.CATEGORY_POPULAR;
                constantsTarget = Costant.POPULAR_MAIN_TARGET;
                break;

            case R.id.nav_mode_all:
                constantCategory = Costant.CATEGORY_ALL;
                constantsTarget = Costant.ALL_MAIN_TARGET;
                break;

            case R.id.nav_mode_favorite:
                constantCategory = Preference.getLastCategory(mContext);
                constantsTarget = Costant.FAVORITE_MAIN_TARGET;
                break;

        }

        Intent intent = new Intent(mContext, MainActivity.class);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, constantCategory);
        intent.putExtra(Costant.EXTRA_MAIN_TARGET, constantsTarget);

        startActivity(intent);
    }
}
