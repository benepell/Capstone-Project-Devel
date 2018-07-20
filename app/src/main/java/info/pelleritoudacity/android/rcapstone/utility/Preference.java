package info.pelleritoudacity.android.rcapstone.utility;

import android.content.Context;

import info.pelleritoudacity.android.rcapstone.R;

public class Preference {

    private static final int[] prefArrays = {
            R.string.pref_title_sync_frequency,
            R.string.pref_insert_prefs,
            R.string.pref_login_start,
            R.string.pref_session_username,
            R.string.pref_session_access_token,
            R.string.pref_session_refresh_token,
            R.string.pref_session_expired,
            R.string.pref_time_token,
            R.string.pref_subreddit_key,
            R.string.pref_subreddit_sort,
            R.string.pref_tab_history,
            R.string.pref_time_sort,
            R.string.pref_last_category,
            R.string.pref_last_target,
            R.string.pref_last_comment,
            R.string.pref_sync_frequency,
            R.string.pref_item_page,
            R.string.pref_volume_muted,
            R.string.pref_clear_data,
            R.string.pref_type_mode,
            R.string.pref_login_name,
            R.string.pref_login_over18,
            R.string.pref_restore_manage,
            R.string.pref_request_permission,
            R.string.pref_general_images,
            R.string.pref_general_gifs,
            R.string.pref_general_albums,
            R.string.pref_general_videos,
            R.string.pref_general_self,
            R.string.pref_general_links,
            R.string.pref_general_init,
            R.string.pref_more_fragment_more_nested_position_height



    };


    private Preference() {
    }

    public static Boolean isInsertPrefs(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_INSERT_PREFS);
    }

    public static void setInsertPrefs(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_INSERT_PREFS, b);
    }

    public static Boolean isRequestPermission(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_REQUEST_PERMISSION);
    }

    public static void setRequestPermission(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_REQUEST_PERMISSION, b);
    }

    public static Boolean isLoginStart(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_LOGIN_START);
    }

    public static void setLoginStart(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_LOGIN_START, b);
    }

    public static Boolean isGeneralInit(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_GENERAL_INIT);
    }

    public static void setGeneralInit(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_GENERAL_INIT, b);
    }

    public static Boolean isClearData(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_CLEAR_DATA);
    }

    public static void setClearData(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_CLEAR_DATA, b);
    }

    public static Boolean isVolumeMuted(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_VOLUME_MUTED);
    }

    public static void setVolumeMuted(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_VOLUME_MUTED, b);
    }

    public static void setLoginOver18(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_LOGIN_OVER18, b);
    }

    public static Boolean isLoginOver18(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_LOGIN_OVER18);
    }

    public static void setWriteExternalStorage(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_WRITE_EXTERNAL_STORAGE, b);
    }

    public static Boolean isWriteExternalStorage(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_WRITE_EXTERNAL_STORAGE);
    }


    public static String getSessionAccessToken(Context context) {
        return PrefManager.getStringPref(context, Costant.PREFERENCE_SESSION_ACCESS_TOKEN);
    }

    public static void setSessionAccessToken(Context context, String s) {
        PrefManager.putStringPref(context, Costant.PREFERENCE_SESSION_ACCESS_TOKEN, s);
    }

    public static String getLastCategory(Context context) {
        return PrefManager.getStringPref(context, Costant.PREFERENCE_LAST_CATEGORY);
    }

    public static void setLastCategory(Context context, String s) {
        PrefManager.putStringPref(context, Costant.PREFERENCE_LAST_CATEGORY, s);
    }

    public static void setLastTarget(Context context, String s) {
        PrefManager.putStringPref(context, Costant.PREFERENCE_LAST_TARGET, s);
    }

    public static String getLastTarget(Context context) {
        return PrefManager.getStringPref(context, Costant.PREFERENCE_LAST_TARGET);
    }

    public static String getLastComment(Context context) {
        return PrefManager.getStringPref(context, Costant.PREFERENCE_LAST_COMMENT);
    }

    public static void setLastComment(Context context, String s) {
        PrefManager.putStringPref(context, Costant.PREFERENCE_LAST_COMMENT, s);
    }

    public static String getLoginName(Context context) {
        return PrefManager.getStringPref(context, Costant.PREFERENCE_LOGIN_NAME);
    }

    public static void setLoginName(Context context, String s) {
        PrefManager.putStringPref(context, Costant.PREFERENCE_LOGIN_NAME, s);
    }

    public static int getMoreNestedPositionHeight(Context context) {
        return PrefManager.getIntPref(context, Costant.PREFERENCE_FRAGMENT_MORE_NESTED_POSITION_HEIGHT);
    }

    public static void setMoreNestedPositionHeight(Context context, int i) {
        PrefManager.putIntPref(context, Costant.PREFERENCE_FRAGMENT_MORE_NESTED_POSITION_HEIGHT, i);
    }

    public static String getSessionRefreshToken(Context context) {
        return PrefManager.getStringPref(context, Costant.PREFERENCE_SESSION_REFRESH_TOKEN);
    }

    public static void setSessionRefreshToken(Context context, String s) {
        PrefManager.putStringPref(context, Costant.PREFERENCE_SESSION_REFRESH_TOKEN, s);
    }

    public static int getSessionExpired(Context context) {
        return PrefManager.getIntPref(context, Costant.PREFERENCE_SESSION_EXPIRED);
    }

    public static void setSessionExpired(Context context, int i) {
        PrefManager.putIntPref(context, Costant.PREFERENCE_SESSION_EXPIRED, i);
    }

    public static int getTypeMode(Context context) {
        return PrefManager.getIntPref(context, Costant.PREFERENCE_TYPE_MODE);
    }

    public static void setTypeMode(Context context, int i) {
        PrefManager.putIntPref(context, Costant.PREFERENCE_TYPE_MODE, i);
    }

    public static void setRestoreManage(Context context, int i) {
        PrefManager.putIntPref(context, Costant.PREFERENCE_RESTORE_MANAGE, i);
    }

    public static int getRestoreManage(Context context) {
        return PrefManager.getIntPref(context, Costant.PREFERENCE_RESTORE_MANAGE);
    }

    public static long getTimeToken(Context context) {
        return PrefManager.getLongPref(context, Costant.PREFERENCE_TIME_TOKEN);
    }

    public static void setTimeToken(Context context, long l) {
        PrefManager.putLongPref(context, Costant.PREFERENCE_TIME_TOKEN, l);
    }

    public static String getTimeSort(Context context) {
        return PrefManager.getStringPref(context, Costant.PREFERENCE_TIME_SORT);
    }

    public static void setTimeSort(Context context, String s) {
        PrefManager.putStringPref(context, Costant.PREFERENCE_TIME_SORT, s);
    }

    public static String getSubredditKey(Context context) {
        return PrefManager.getStringPref(context, Costant.PREFERENCE_SUBREDDIT_KEY);
    }

    public static void setSubredditKey(Context context, String s) {
        PrefManager.putStringPref(context, Costant.PREFERENCE_SUBREDDIT_KEY, s);
    }

    public static void setSubredditSort(Context context, String s) {
        PrefManager.putStringPref(context, Costant.PREFERENCE_SUBREDDIT_SORT, s);
    }

    public static String getSubredditSort(Context context) {
        return PrefManager.getStringPref(context, Costant.PREFERENCE_SUBREDDIT_SORT);
    }


    public static Boolean isGeneralGifs(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_GENERAL_GIFS);
    }

    public static void setGeneralGifs(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_GENERAL_GIFS, b);
    }

    public static Boolean isGeneralAlbums(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_GENERAL_ALBUMS);
    }

    public static void setGeneralAlbums(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_GENERAL_ALBUMS, b);
    }

    public static Boolean isGeneralLinks(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_GENERAL_LINKS);
    }

    public static void setGeneralLinks(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_GENERAL_LINKS, b);
    }

    public static Boolean isGeneralVideos(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_GENERAL_VIDEOS);
    }

    public static void setGeneralVideos(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_GENERAL_VIDEOS, b);
    }

    public static Boolean isGeneralSelf(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_GENERAL_SELF);
    }

    public static void setGeneralSelf(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_GENERAL_SELF, b);
    }

    public static Boolean isGeneralImages(Context context) {
        return PrefManager.getBoolPref(context, Costant.PREFERENCE_GENERAL_IMAGES);
    }

    public static void setGeneralImages(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costant.PREFERENCE_GENERAL_IMAGES, b);
    }


    public static Boolean isTabHistory(Context context) {
        return PrefManager.isGeneralSettings(context, context.getString(Costant.PREFERENCE_TAB_HISTORY));
    }

    public static Boolean isOriginalSizeContent(Context context) {
        return PrefManager.isGeneralSettings(context, context.getString(Costant.PREFERENCE_ORIGINAL_SIZE_CONTENT));
    }

    public static Boolean isNightMode(Context context) {
        return PrefManager.isGeneralSettings(context, context.getString(Costant.PREFERENCE_NIGHT_MODE));
    }

    public static void clearAll(Context context) {
        PrefManager.clearPref(context, prefArrays);
    }

    public static void clearGeneralSettings(Context context) {
        PrefManager.clearGeneralSettings(context);
    }

    public static int getGeneralSettingsSyncFrequency(Context context) {
        return PrefManager.getIntGeneralSettings(context, Costant.PREFERENCE_SYNC_FREQUENCY);
    }

    public static int getGeneralSettingsItemPage(Context context) {
        return PrefManager.getIntGeneralSettings(context, Costant.PREFERENCE_ITEM_PAGE);
    }

    public static int getGeneralSettingsDepthPage(Context context) {
        return PrefManager.getIntGeneralSettings(context, Costant.PREFERENCE_DEPTH_PAGE);
    }

}
