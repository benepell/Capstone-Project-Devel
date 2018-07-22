package info.pelleritoudacity.android.rcapstone.ui.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.text.style.TypefaceSpan;
import android.view.Menu;
import android.view.MenuItem;

import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.material_design_iconic_typeface_library.MaterialDesignIconic;

import java.util.ArrayList;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.ui.activity.BaseActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.LoginActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.LogoutActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.SettingsActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.SubManageActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.SubRedditActivity;
import info.pelleritoudacity.android.rcapstone.ui.activity.SubRedditDetailActivity;
import info.pelleritoudacity.android.rcapstone.ui.view.SubRedditTab;
import info.pelleritoudacity.android.rcapstone.ui.view.TabData;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

import static info.pelleritoudacity.android.rcapstone.utility.TextUtil.stringToArray;

public class MenuBase {

    private final Context mContext;
    private final int mLayoutResource;

    public MenuBase(Context context, int layoutResource) {
        mContext = context;
        mLayoutResource = layoutResource;
    }

    public boolean menuItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.menu_action_restore:
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class).putExtra(Costant.EXTRA_RESTORE_MANAGE, Costant.RESTORE_MANAGE_RESTORE));
                return true;
            case R.id.menu_action_login:
                mContext.startActivity(new Intent(mContext, LoginActivity.class));
                return true;
            case R.id.menu_action_logout:
                mContext.startActivity(new Intent(mContext, LogoutActivity.class));
                return true;
            case R.id.menu_action_refresh:
                menuClickRefresh(mLayoutResource);
                return true;
            case R.id.menu_action_settings:
                mContext.startActivity(new Intent(mContext, SettingsActivity.class));
                return true;

            case R.id.submenu_filter_hot:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_HOT);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_NOTHING);
                break;
            case R.id.submenu_filter_new:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_NEW);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_NOTHING);
                break;
            case R.id.submenu_filter_rising:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_RISING);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_NOTHING);
                break;

            case R.id.submenu_top_hour:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_HOUR);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;
            case R.id.submenu_top_day:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_DAY);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;
            case R.id.submenu_top_week:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_WEEK);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;
            case R.id.submenu_top_month:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_MONTH);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;
            case R.id.submenu_top_year:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_YEAR);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;
            case R.id.submenu_top_all:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_TOP);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_ALL);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;

            case R.id.submenu_controver_hour:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_HOUR);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;
            case R.id.submenu_controver_day:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_DAY);
                break;
            case R.id.submenu_controver_week:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_WEEK);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;
            case R.id.submenu_controver_month:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_MONTH);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;
            case R.id.submenu_controver_year:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_YEAR);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;
            case R.id.submenu_controver_all:
                Preference.setSubredditSort(mContext, Costant.LABEL_SUBMENU_CONTROVERSIAL);
                Preference.setTimeSort(mContext, Costant.LABEL_TIME_ALL);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;
            case R.id.action_general_images:
                Preference.setGeneralImages(mContext, !Preference.isGeneralImages(mContext));
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;

            case R.id.action_general_gifs:
                Preference.setGeneralGifs(mContext, !Preference.isGeneralGifs(mContext));
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;

            case R.id.action_general_albums:
                Preference.setGeneralAlbums(mContext, !Preference.isGeneralAlbums(mContext));
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;

            case R.id.action_general_videos:
                Preference.setGeneralVideos(mContext, !Preference.isGeneralVideos(mContext));
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;

            case R.id.action_general_self:
                Preference.setGeneralSelf(mContext, !Preference.isGeneralSelf(mContext));
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;

            case R.id.action_general_links:
                Preference.setGeneralLinks(mContext, !Preference.isGeneralLinks(mContext));
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class));
                break;
            default:
                return false;
        }


        return true;

    }

    public boolean navigationItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.nav_home:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_HOME);
                mContext.startActivity(new Intent(mContext, SubRedditActivity.class)
                        .setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION));
                break;

            case R.id.nav_mode_all:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_ALL);
                targetMenuMain(R.id.nav_mode_all);
                break;

            case R.id.nav_mode_popular:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_POPOLAR);
                targetMenuMain(R.id.nav_mode_popular);
                break;

            case R.id.nav_mode_subscriptions:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_SUBSCRIPTIONS);
                item.setEnabled(true);
                mContext.startActivity(new Intent(mContext, SubManageActivity.class));
                break;
            case R.id.nav_mode_refresh:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_SEARCH);
                menuClickRefresh(mLayoutResource);
                item.setEnabled(true);
                break;

            case R.id.nav_mode_settings:
                Preference.setTypeMode(mContext, Costant.NAV_MODE_SETTINGS);
                item.setEnabled(true);
                mContext.startActivity(new Intent(mContext, SettingsActivity.class));
                break;

        }
        return true;
    }

    public boolean menuNavigation(Menu menu) {

        if (menu == null) return false;
        MenuItem itemHome = menu.findItem(R.id.nav_home);

        MenuItem itemModePopularText = menu.findItem(R.id.nav_mode_popular);
        MenuItem itemModeAllText = menu.findItem(R.id.nav_mode_all);
        MenuItem itemModeSearchText = menu.findItem(R.id.nav_mode_search);
        MenuItem itemModeSubscriptions = menu.findItem(R.id.nav_mode_subscriptions);
        MenuItem itemModeRefresh = menu.findItem(R.id.nav_mode_refresh);
        MenuItem itemModeSettings = menu.findItem(R.id.nav_mode_settings);

        itemHome.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_home)
                .respectFontBounds(true));

        itemModePopularText.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_trending_up)
                .respectFontBounds(true));

        itemModeAllText.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_view_comfy)
                .respectFontBounds(true));

        itemModeSearchText.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_search)
                .respectFontBounds(true));

        itemModeSubscriptions.setIcon(new IconicsDrawable(mContext, MaterialDesignIconic.Icon.gmi_view_headline)
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
            case Costant.NAV_MODE_SEARCH:
                itemModeSearchText.setEnabled(true);
                itemModeSearchText.setChecked(true);
                break;
            case Costant.NAV_MODE_SUBSCRIPTIONS:
                itemModeSubscriptions.setEnabled(true);
                itemModeSubscriptions.setChecked(true);
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
        return true;
    }


    public boolean navigationSubCategory(Menu menu) {

        if (menu == null) return false;

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

            if (Preference.getLastCategory(mContext).equals(String.valueOf(title))) {
                menuItem.setChecked(true);
            } else {
                menuItem.setChecked(false);

            }

            menuItem.setOnMenuItemClickListener(item -> {
                Intent intent = new Intent(mContext, SubRedditActivity.class);
                intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, item.getTitle().toString());


                mContext.startActivity(intent);
                return true;
            });

        }

        return true;
    }


    private void menuClickRefresh(int layoutResource) {
        switch (layoutResource) {
            case R.layout.activity_subreddit:
                Intent intentSubreddit = new Intent(mContext, SubRedditActivity.class);
                intentSubreddit.putExtra(Costant.EXTRA_ACTIVITY_SUBREDDIT_REFRESH, true);
                mContext.startActivity(intentSubreddit);
                break;

            case R.layout.activity_sub_reddit_detail:
                Intent intentSubredditDetail = new Intent(mContext, SubRedditDetailActivity.class);
                intentSubredditDetail.putExtra(Costant.EXTRA_ACTIVITY_SUBREDDIT_DETAIL_REFRESH, true);
                mContext.startActivity(intentSubredditDetail);
                break;

            case R.layout.activity_submanage:
                Intent intentSubmanage = new Intent(mContext, SubManageActivity.class);
                intentSubmanage.putExtra(Costant.EXTRA_ACTIVITY_SUBMANAGE_REFRESH, true);
                mContext.startActivity(intentSubmanage);
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

        Intent intent = new Intent(mContext, SubRedditActivity.class);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, constantCategory);
        intent.putExtra(Costant.EXTRA_SUBREDDIT_TARGET, constantsTarget);

        mContext.startActivity(intent);
    }

    public void menuGeneralSettings(Menu menu) {

        MenuItem itemGeneralImages = menu.findItem(R.id.action_general_images);
        MenuItem itemGeneralVideos = menu.findItem(R.id.action_general_videos);
        MenuItem itemGeneralGifs = menu.findItem(R.id.action_general_gifs);
        MenuItem itemGeneralAlbums = menu.findItem(R.id.action_general_albums);
        MenuItem itemGeneralLinks = menu.findItem(R.id.action_general_links);
        MenuItem itemGeneralSelf = menu.findItem(R.id.action_general_self);

        if (!Preference.isGeneralInit(mContext)) {
            Preference.setGeneralImages(mContext, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralVideos(mContext, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralGifs(mContext, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralAlbums(mContext, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralLinks(mContext, Costant.DEFAULT_GENERAL_SETTINGS);
            Preference.setGeneralSelf(mContext, Costant.DEFAULT_GENERAL_SETTINGS);

            itemGeneralImages.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralVideos.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralGifs.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralAlbums.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
            itemGeneralLinks.setChecked(Costant.DEFAULT_GENERAL_SETTINGS);
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

            if (Preference.isGeneralAlbums(mContext)) {
                itemGeneralAlbums.setChecked(true);
            } else {
                itemGeneralAlbums.setChecked(false);

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

            if (Preference.isGeneralSelf(mContext)) {
                itemGeneralSelf.setChecked(true);
            } else {
                itemGeneralSelf.setChecked(false);

            }
        }
    }



}
