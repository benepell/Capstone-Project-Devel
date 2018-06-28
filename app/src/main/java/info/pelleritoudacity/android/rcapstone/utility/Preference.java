package info.pelleritoudacity.android.rcapstone.utility;

import android.content.Context;

import info.pelleritoudacity.android.rcapstone.R;

public class Preference {

    /*private final static int INSERT_PREFS = R.string.pref_insert_prefs;
    private final static int LOGIN_START = R.string.pref_login_start;
    private final static int SESSION_ACCESS_TOKEN = R.string.pref_session_access_token;
    private final static int SESSION_REFRESH_TOKEN = R.string.pref_session_refresh_token;
    private final static int SESSION_EXPIRED = R.string.pref_session_expired;
    private final static int TIME_TOKEN = R.string.pref_time_token;
    private final static int SUBREDDIT_KEY = R.string.pref_subreddit_key;
    private final static int SUBREDDIT_SORT = R.string.pref_subreddit_sort;
    private final static int TIME_SORT = R.string.pref_time_sort;
    private final static int SYNC_FREQUENCY = R.string.pref_sync_frequency;
    private final static int VOLUME_MUTED = R.string.pref_volume_muted;
    private final static int CLEAR_DATA = R.string.pref_clear_data;
    private final static int TYPE_MODE = R.string.pref_type_mode;
    private final static int LOGIN_NAME = R.string.pref_login_name;
    private final static int LOGIN_OVER18 = R.string.pref_login_over18;
    private final static int YOUTUBE_PLAYER = R.string.pref_youtube_player;
    private final static int LAST_CATEGORY = R.string.pref_last_category;
    private final static int TITLE_SYNC_FREQUENCY = R.string.pref_title_sync_frequency;
    private final static int SESSION_USERNAME = R.string.pref_session_username;
    private final static int RESTORE_MANAGE = R.string.pref_restore_manage;
    private final static int REQUEST_PERMISSION = R.string.pref_request_permission;
*/

    static final int[] prefArrays = {
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
            R.string.pref_youtube_player,
            R.string.pref_time_sort,
            R.string.pref_last_category,
            R.string.pref_sync_frequency,
            R.string.pref_volume_muted,
            R.string.pref_clear_data,
            R.string.pref_type_mode,
            R.string.pref_login_name,
            R.string.pref_login_over18,
            R.string.pref_restore_manage,
            R.string.pref_request_permission

    };


    private Preference() {
    }

    public static Boolean isInsertPrefs(Context context) {
        return PrefManager.getBoolPref(context, Costants.PREFERENCE_INSERT_PREFS);
    }

    public static void setInsertPrefs(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costants.PREFERENCE_INSERT_PREFS, b);
    }

    public static Boolean isRequestPermission(Context context) {
        return PrefManager.getBoolPref(context, Costants.PREFERENCE_REQUEST_PERMISSION);
    }

    public static void setRequestPermission(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costants.PREFERENCE_REQUEST_PERMISSION, b);
    }

    public static Boolean isLoginStart(Context context) {
        return PrefManager.getBoolPref(context, Costants.PREFERENCE_LOGIN_START);
    }

    public static void setLoginStart(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costants.PREFERENCE_LOGIN_START, b);
    }

    public static Boolean isClearData(Context context) {
        return PrefManager.getBoolPref(context, Costants.PREFERENCE_CLEAR_DATA);
    }

    public static void setClearData(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costants.PREFERENCE_CLEAR_DATA, b);
    }

    public static Boolean isVolumeMuted(Context context) {
        return PrefManager.getBoolPref(context, Costants.PREFERENCE_VOLUME_MUTED);
    }

    public static void setVolumeMuted(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costants.PREFERENCE_VOLUME_MUTED, b);
    }

    public static void setLoginOver18(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costants.PREFERENCE_LOGIN_OVER18, b);
    }

    public static Boolean isLoginOver18(Context context) {
        return PrefManager.getBoolPref(context, Costants.PREFERENCE_LOGIN_OVER18);
    }

    public static void setWriteExternalStorage(Context context, Boolean b) {
        PrefManager.putBoolPref(context, Costants.PREFERENCE_WRITE_EXTERNAL_STORAGE, b);
    }

    public static Boolean isWriteExternalStorage(Context context) {
        return PrefManager.getBoolPref(context, Costants.PREFERENCE_WRITE_EXTERNAL_STORAGE);
    }


    public static String getSessionAccessToken(Context context) {
        return PrefManager.getStringPref(context, Costants.PREFERENCE_SESSION_ACCESS_TOKEN);
    }

    public static void setSessionAccessToken(Context context, String s) {
        PrefManager.putStringPref(context, Costants.PREFERENCE_SESSION_ACCESS_TOKEN, s);
    }

    public static String getLastCategory(Context context) {
        return PrefManager.getStringPref(context, Costants.PREFERENCE_LAST_CATEGORY);
    }

    public static void setLastCategory(Context context, String s) {
        PrefManager.putStringPref(context, Costants.PREFERENCE_LAST_CATEGORY, s);
    }

    public static String getLoginName(Context context) {
        return PrefManager.getStringPref(context, Costants.PREFERENCE_LOGIN_NAME);
    }

    public static void setLoginName(Context context, String s) {
        PrefManager.putStringPref(context, Costants.PREFERENCE_LOGIN_NAME, s);
    }

    public static String getSessionRefreshToken(Context context) {
        return PrefManager.getStringPref(context, Costants.PREFERENCE_SESSION_REFRESH_TOKEN);
    }

    public static void setSessionRefreshToken(Context context, String s) {
        PrefManager.putStringPref(context, Costants.PREFERENCE_SESSION_REFRESH_TOKEN, s);
    }

    public static int getSessionExpired(Context context) {
        return PrefManager.getIntPref(context, Costants.PREFERENCE_SESSION_EXPIRED);
    }

    public static void setSessionExpired(Context context, int i) {
        PrefManager.putIntPref(context, Costants.PREFERENCE_SESSION_EXPIRED, i);
    }

    public static int getTypeMode(Context context) {
        return PrefManager.getIntPref(context, Costants.PREFERENCE_TYPE_MODE);
    }

    public static void setTypeMode(Context context, int i) {
        PrefManager.putIntPref(context, Costants.PREFERENCE_TYPE_MODE, i);
    }

    public static void setRestoreManage(Context context, int i) {
        PrefManager.putIntPref(context, Costants.PREFERENCE_RESTORE_MANAGE, i);
    }

    public static int getRestoreManage(Context context) {
        return PrefManager.getIntPref(context, Costants.PREFERENCE_RESTORE_MANAGE);
    }

    public static long getTimeToken(Context context) {
        return PrefManager.getLongPref(context, Costants.PREFERENCE_TIME_TOKEN);
    }

    public static void setTimeToken(Context context, long l) {
        PrefManager.putLongPref(context, Costants.PREFERENCE_TIME_TOKEN, l);
    }

    public static String getTimeSort(Context context) {
        return PrefManager.getStringPref(context, Costants.PREFERENCE_TIME_SORT);
    }

    public static void setTimeSort(Context context, String s) {
        PrefManager.putStringPref(context, Costants.PREFERENCE_TIME_SORT, s);
    }

    public static String getSubredditKey(Context context) {
        return PrefManager.getStringPref(context, Costants.PREFERENCE_SUBREDDIT_KEY);
    }

    public static void setSubredditKey(Context context, String s) {
        PrefManager.putStringPref(context, Costants.PREFERENCE_SUBREDDIT_KEY, s);
    }

    public static void setSubredditSort(Context context, String s) {
        PrefManager.putStringPref(context, Costants.PREFERENCE_SUBREDDIT_SORT, s);
    }

    public static String getSubredditSort(Context context) {
        return PrefManager.getStringPref(context, Costants.PREFERENCE_SUBREDDIT_SORT);
    }

    public static void setSyncFrequency(Context context, int i) {
        PrefManager.putIntPref(context, Costants.PREFERENCE_SYNC_FREQUENCY, i);
    }

    public static int getSyncFrequency(Context context) {
        return PrefManager.getIntPref(context, Costants.PREFERENCE_SYNC_FREQUENCY);
    }


    public static Boolean isYoutubePlayer(Context context) {
        return PrefManager.isGeneralSettings(context, context.getString(Costants.PREFERENCE_YOUTUBE_PLAYER));
    }

    public static Boolean isOriginalSizeContent(Context context) {
        return PrefManager.isGeneralSettings(context, context.getString(Costants.PREFERENCE_ORIGINAL_SIZE_CONTENT));
    }

    public static Boolean isNightMode(Context context) {
        return PrefManager.isGeneralSettings(context, context.getString(Costants.PREFERENCE_NIGHT_MODE));
    }



    public static void clearAll(Context context) {
        PrefManager.clearPref(context, prefArrays);
    }
    public static void clearGeneralSettings(Context context) {
        PrefManager.clearGeneralSettings(context);
    }


}
