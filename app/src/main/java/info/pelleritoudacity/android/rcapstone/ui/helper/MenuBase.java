package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.app.Activity;
import android.app.SearchManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;
import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.ui.activity.LoginActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.MainActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.ManageActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.SettingsActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.DetailActivity;
import info.pelleritoudacity.android.rcapstone.data.other.TabData;
import info.pelleritoudacity.android.rcapstone.ui.activity.WebviewActivity;
import info.pelleritoudacity.android.rcapstone.utility.ActivityUI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

public class MenuBase {

    private final AppCompatActivity compatActivity;
    private final int mLayoutResource;

    public MenuBase(AppCompatActivity compatActivity, int layoutResource) {
        this.compatActivity = compatActivity;
        mLayoutResource = layoutResource;
    }

    public void menuItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.menu_action_restore:
                compatActivity.startActivity(new Intent(compatActivity, ManageActivity.class)
                        .putExtra(Costant.EXTRA_RESTORE_MANAGE, Costant.RESTORE_MANAGE_RESTORE)
                        .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                );
                return;
            case R.id.menu_action_login:
                if (NetworkUtil.isOnline(compatActivity)) {
                    compatActivity.startActivity(new Intent(compatActivity, LoginActivity.class));
                }else {
                    Toast.makeText(compatActivity,compatActivity.getText(R.string.text_no_network),Toast.LENGTH_LONG).show();
                }
                return;

            case R.id.menu_action_logout:
                compatActivity.startActivity(new Intent(compatActivity, LoginActivity.class));
                return;

            case R.id.menu_action_refresh:
                if (NetworkUtil.isOnline(compatActivity)) {
                    menuClickRefresh(mLayoutResource);
                }else {
                    Toast.makeText(compatActivity,compatActivity.getText(R.string.text_no_network),Toast.LENGTH_LONG).show();
                }
                return;

            case R.id.menu_action_create:
                compatActivity.startActivity(new Intent(compatActivity, WebviewActivity.class));
                return;

            case R.id.menu_action_settings:
                compatActivity.startActivity(new Intent(compatActivity, SettingsActivity.class));
                return;

            case R.id.submenu_filter_hot:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_HOT);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_NOTHING);
                break;
            case R.id.submenu_filter_new:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_NEW);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_NOTHING);
                break;
            case R.id.submenu_filter_rising:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_RISING);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_NOTHING);
                break;

            case R.id.submenu_top_hour:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_HOUR);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;
            case R.id.submenu_top_day:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_DAY);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;
            case R.id.submenu_top_week:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_WEEK);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;
            case R.id.submenu_top_month:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_MONTH);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;
            case R.id.submenu_top_year:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_YEAR);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;
            case R.id.submenu_top_all:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_ALL);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;

            case R.id.submenu_controver_hour:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_HOUR);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;
            case R.id.submenu_controver_day:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_DAY);
                break;
            case R.id.submenu_controver_week:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_WEEK);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;
            case R.id.submenu_controver_month:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_MONTH);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;
            case R.id.submenu_controver_year:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_YEAR);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;
            case R.id.submenu_controver_all:
                Preference.setSubredditSort(compatActivity, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(compatActivity, Costant.LABEL_TIME_ALL);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;
            case R.id.action_general_images:
                Preference.setGeneralImages(compatActivity, !Preference.isGeneralImages(compatActivity));
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;

            case R.id.action_general_gifs:
                Preference.setGeneralGifs(compatActivity, !Preference.isGeneralGifs(compatActivity));
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;

            case R.id.action_general_albums:
                Preference.setGeneralAlbums(compatActivity, !Preference.isGeneralAlbums(compatActivity));
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;

            case R.id.action_general_videos:
                Preference.setGeneralVideos(compatActivity, !Preference.isGeneralVideos(compatActivity));
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;

            case R.id.action_general_self:
                Preference.setGeneralSelf(compatActivity, !Preference.isGeneralSelf(compatActivity));
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;

            case R.id.action_general_links:
                Preference.setGeneralLinks(compatActivity, !Preference.isGeneralLinks(compatActivity));
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class));
                break;
        }
    }

    public void navigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:
                Preference.setLastTarget(compatActivity, Costant.NAVIGATION_MAIN_TARGET);
                Preference.setTypeMode(compatActivity, Costant.NAV_MODE_HOME);
                compatActivity.startActivity(new Intent(compatActivity, MainActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;

            case R.id.nav_mode_all:
                Preference.setTypeMode(compatActivity, Costant.NAV_MODE_ALL);
                targetMenuMain(R.id.nav_mode_all);
                break;

            case R.id.nav_mode_popular:
                Preference.setTypeMode(compatActivity, Costant.NAV_MODE_POPOLAR);
                targetMenuMain(R.id.nav_mode_popular);
                break;

            case R.id.nav_mode_subscriptions:
                Preference.setTypeMode(compatActivity, Costant.NAV_MODE_SUBSCRIPTIONS);
                item.setEnabled(true);
                compatActivity.startActivity(new Intent(compatActivity, ManageActivity.class));
                break;

            case R.id.nav_mode_favorite:
                Preference.setTypeMode(compatActivity, Costant.NAV_MODE_FAVORITE);
                targetMenuMain(R.id.nav_mode_favorite);
                break;

            case R.id.nav_mode_refresh:

                if (NetworkUtil.isOnline(compatActivity)) {
                    Preference.setTypeMode(compatActivity, Costant.NAV_MODE_REFRESH);
                    menuClickRefresh(mLayoutResource);
                    item.setEnabled(true);

                }else {
                    Toast.makeText(compatActivity,compatActivity.getText(R.string.text_no_network),Toast.LENGTH_LONG).show();
                }
                break;

            case R.id.nav_mode_settings:
                Preference.setTypeMode(compatActivity, Costant.NAV_MODE_SETTINGS);
                item.setEnabled(true);
                compatActivity.startActivity(new Intent(compatActivity, SettingsActivity.class));
                break;

        }
    }

    public void menuNavigation(Menu menu) {

        if (menu == null) return;
        MenuItem itemHome = menu.findItem(R.id.nav_home);

        MenuItem itemModePopularText = menu.findItem(R.id.nav_mode_popular);
        MenuItem itemModeAllText = menu.findItem(R.id.nav_mode_all);
        MenuItem itemModeSubscriptions = menu.findItem(R.id.nav_mode_subscriptions);
        MenuItem itemModeFavorite = menu.findItem(R.id.nav_mode_favorite);
        MenuItem itemModeRefresh = menu.findItem(R.id.nav_mode_refresh);
        MenuItem itemModeSettings = menu.findItem(R.id.nav_mode_settings);

        itemHome.setIcon(new IconicsDrawable(compatActivity, MaterialDesignIconic.Icon.gmi_home)
                .respectFontBounds(true));

        itemModePopularText.setIcon(new IconicsDrawable(compatActivity, MaterialDesignIconic.Icon.gmi_trending_up)
                .respectFontBounds(true));

        itemModeAllText.setIcon(new IconicsDrawable(compatActivity, MaterialDesignIconic.Icon.gmi_view_comfy)
                .respectFontBounds(true));

        itemModeSubscriptions.setIcon(new IconicsDrawable(compatActivity, MaterialDesignIconic.Icon.gmi_view_headline)
                .respectFontBounds(true));

        itemModeFavorite.setIcon(new IconicsDrawable(compatActivity, MaterialDesignIconic.Icon.gmi_star)
                .respectFontBounds(true));

        itemModeRefresh.setIcon(new IconicsDrawable(compatActivity, MaterialDesignIconic.Icon.gmi_refresh)
                .respectFontBounds(true));

        itemModeSettings.setIcon(new IconicsDrawable(compatActivity, MaterialDesignIconic.Icon.gmi_settings)
                .respectFontBounds(true));


        switch (Preference.getTypeMode(compatActivity)) {
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


    public void navigationSubCategory(Menu menu) {

        if (menu == null) return;

        int groupId = menu.findItem(R.id.nav_mode_subs).getGroupId();

        int colorTheme = Color.DKGRAY;
        if (Preference.isNightMode(compatActivity)) {
            colorTheme = Color.WHITE;
        }

        ArrayList<String> tabArrayList = new TabData(compatActivity).getTabArrayList();

        for (String string : tabArrayList) {

            TypefaceSpan typefaceSpan = new TypefaceSpan("/font/roboto_thin.ttf"); // OR  THIS
            SpannableStringBuilder title = new SpannableStringBuilder(string);
            title.setSpan(typefaceSpan, 0, title.length(), 0);
            title.setSpan(new ForegroundColorSpan(colorTheme), 0, title.length(), 0);

            MenuItem menuItem = menu.add(groupId, Menu.NONE, Menu.NONE, title);

            menuItem.setIcon(new IconicsDrawable(compatActivity, MaterialDesignIconic.Icon.gmi_account_circle)
                    .respectFontBounds(true));

            menuItem.setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(compatActivity, MainActivity.class);
                intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, item.getTitle().toString());
                intent.putExtra(Costant.EXTRA_MAIN_TARGET, Costant.NAVIGATION_MAIN_TARGET);

                compatActivity.startActivity(intent);
                return true;
            });

        }
    }

    private void menuClickRefresh(int layoutResource) {
        switch (layoutResource) {
            case R.layout.activity_main:
                ActivityUI.startRefresh(compatActivity, MainActivity.class);
                break;

            case R.layout.activity_detail:
                ActivityUI.startRefresh(compatActivity, DetailActivity.class);
                break;

            case R.layout.activity_manage:
                ActivityUI.startRefresh(compatActivity, ManageActivity.class);
                break;

            default:
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
                constantCategory = Preference.getLastCategory(compatActivity);
                constantsTarget = Costant.FAVORITE_MAIN_TARGET;
                break;

        }

        Intent intent = new Intent(compatActivity, MainActivity.class);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, constantCategory);
        intent.putExtra(Costant.EXTRA_MAIN_TARGET, constantsTarget);

        compatActivity.startActivity(intent);
    }

    public void menuGeneralSettings(Menu menu) {

        MenuItem itemGeneralImages = menu.findItem(R.id.action_general_images);
        MenuItem itemGeneralVideos = menu.findItem(R.id.action_general_videos);
        MenuItem itemGeneralGifs = menu.findItem(R.id.action_general_gifs);
        MenuItem itemGeneralAlbums = menu.findItem(R.id.action_general_albums);
        MenuItem itemGeneralLinks = menu.findItem(R.id.action_general_links);
        MenuItem itemGeneralSelf = menu.findItem(R.id.action_general_self);

        if (!Preference.isGeneralInit(compatActivity)) {
            Preference.setGeneralImages(compatActivity, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralVideos(compatActivity, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralGifs(compatActivity, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralAlbums(compatActivity, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralLinks(compatActivity, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralSelf(compatActivity, Costant.DEFAULT_GENERAL_SETTINGS);

            itemGeneralImages.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralVideos.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralGifs.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralAlbums.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralLinks.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralSelf.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);

            Preference.setGeneralInit(compatActivity, true);

        } else {
            if (Preference.isGeneralImages(compatActivity)) {
                itemGeneralImages.setChecked(true);
            } else {
                itemGeneralImages.setChecked(false);

            }

            if (Preference.isGeneralGifs(compatActivity)) {
                itemGeneralGifs.setChecked(true);
            } else {
                itemGeneralGifs.setChecked(false);

            }

            if (Preference.isGeneralAlbums(compatActivity)) {
                itemGeneralAlbums.setChecked(true);
            } else {
                itemGeneralAlbums.setChecked(false);

            }

            if (Preference.isGeneralVideos(compatActivity)) {
                itemGeneralVideos.setChecked(true);
            } else {
                itemGeneralVideos.setChecked(false);

            }

            if (Preference.isGeneralLinks(compatActivity)) {
                itemGeneralLinks.setChecked(true);
            } else {
                itemGeneralLinks.setChecked(false);

            }

            if (Preference.isGeneralSelf(compatActivity)) {
                itemGeneralSelf.setChecked(true);
            } else {
                itemGeneralSelf.setChecked(false);

            }
        }
    }

    public void menuItemIfRoom(Menu menu) {

        MenuItem menuItemFilter = menu.findItem(R.id.submenu_filter_posts);

        menuItemFilter.setIcon(new IconicsDrawable(compatActivity, MaterialDesignIconic.Icon.gmi_sort)
                .color(Color.WHITE)
                .sizeDp(24)
                .respectFontBounds(true));

    }

    public void menuItemSearch(Activity activity, ComponentName componentName, Menu menu) {

        MenuItem menuItemSearch = menu.findItem(R.id.menu_action_search);

        menuItemSearch.setIcon(new IconicsDrawable(compatActivity, MaterialDesignIconic.Icon.gmi_search)
                .color(Color.WHITE)
                .sizeDp(24)
                .respectFontBounds(true));

        SearchManager searchManager = (SearchManager) compatActivity.getSystemService(Context.SEARCH_SERVICE);

        SearchView searchView = (SearchView) menuItemSearch.getActionView();
        if (searchView != null) {
            searchView.setSearchableInfo(Objects.requireNonNull(searchManager).getSearchableInfo(componentName));

            searchView.setQueryHint(compatActivity.getString(R.string.text_search_local));
            searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) activity);
            searchView.setIconified(false);

        }


    }


    public void menuItemLogin(Menu menu, boolean isLogged) {

        MenuItem menuItemLogin;
        MenuItem menuItemLogout;

        menuItemLogin = menu.findItem(R.id.menu_action_login);
        menuItemLogout = menu.findItem(R.id.menu_action_logout);

        if (isLogged) {
            menuItemLogin.setVisible(false);
            menuItemLogout.setVisible(true);
        } else {
            menuItemLogin.setVisible(true);
            menuItemLogout.setVisible(false);
        }

    }

}
