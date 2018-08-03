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

package info.pelleritoudacity.android.rcapstone.utility;


import info.pelleritoudacity.android.rcapstone.BuildConfig;
import info.pelleritoudacity.android.rcapstone.R;

public interface Costant {

    String REDDIT_BASE_URL = "https://www.reddit.com/";
    String REDDIT_TOKEN_URL = "https://www.reddit.com/";
    String REDDIT_OAUTH_URL = "https://oauth.reddit.com/";

    String YOUTUBE_DEVELOPER_KEY = BuildConfig.YOUTUBE_DEVELOPER_KEY;
    boolean YOUTUBE_CLIENT_AUTOSTART = true;

    String REDDIT_CLIENT_ID = BuildConfig.REDDIT_CLIENT_ID;
    String REDDIT_REDIRECT_URL = BuildConfig.REDDIT_REDIRECT_URL;
    String REDDIT_ABOUT_URL = BuildConfig.REDDIT_ABOUT_URL;
    String REDDIT_STATE_RANDOM = BuildConfig.REDDIT_SECRET_STATE;
    String PERMISSION_STATE_REDDIT = "identity, edit, flair, history, modconfig, modflair, modlog, modposts, modwiki, mysubreddits, privatemessages, read, report, save, submit, subscribe, vote, wikiedit, wikiread";

    long DEFAULT_OPERATION_REFRESH = 2000;

    String REDDIT_COMMENTS = "comments";
    String BASE_TYPE_YOUTUBE = "youtube.com";
    String BASE_TYPE_VIMEO = "vimeo.com";

    int MEDIA_VIDEO_TYPE_YOUTUBE = 100;
    int MEDIA_VIDEO_TYPE_VIMEO = 200;
    int MEDIA_VIDEO_PREVIEW_TYPE_MP4 = 300;
    int MEDIA_IMAGE_FULL_TYPE = 400;

    String REDDIT_AUTHORIZATION = "Authorization";
    String REDDIT_BEARER = "Bearer ";
    String REDDIT_ACCESS_TOKEN = "access_token";
    int REDDIT_REVOKE_SUCCESS = 204;
    String REDDIT_AUTH_URL = "www.reddit.com";
    String REDDIT_USER_AGENT = "android:info.pelleritoudacity.android.rcapstone:v1.0.0 (by /u/benedettopellerito)";

    int SESSION_TIMEOUT_DEFAULT = 3500; // approx timeout refresh token 1 hour


    String TOKEN_SYNC_TAG = "rcapstone-token-sync";

    int REDDIT_LOADER_ID = 1;
    int SUBREDDIT_LOADER_ID = 2;
    int SUBREDDIT_DETAIL_LOADER_ID = 3;
    int SUBREDDIT_SELECTED_LOADER_ID = 4;

    int NAV_MODE_HOME = 1;
    int NAV_MODE_POPOLAR = 2;
    int NAV_MODE_ALL = 3;
    int NAV_MODE_SEARCH = 4;
    int NAV_MODE_SUBSCRIPTIONS = 5;
    int NAV_MODE_REFRESH = 6;
    int NAV_MODE_SETTINGS = 7;
    int NAV_MODE_FAVORITE = 8;

    String STRING_SEPARATOR = ",";

    String SUBREDDIT_FAVORITE_SAVED = "1";
    String DETAIL_MORE_REPLIES = "1";
    String NONE_DETAIL_MORE_REPLIES = "0";

    int RESTORE_MANAGE_RESTORE = 1;

    String EXTRA_LOGIN_SUCCESS = "info.pelleritoudacity.android.rcapstone.extra.login.success";
    String EXTRA_LOGOUT_SUCCESS = "info.pelleritoudacity.android.rcapstone.extra.logout.success";

    String EXTRA_SUBREDDIT_CATEGORY = "info.pelleritoudacity.android.rcapstone.extra.subreddit.category";
    String EXTRA_SUBREDDIT_DETAIL_CATEGORY = "info.pelleritoudacity.android.rcapstone.extra.subreddit.detail.category";
    String EXTRA_PARCEL_MAIN_MODEL = "info.pelleritoudacity.android.rcapstone.extra.parcel.main.model";
    String EXTRA_PARCEL_DETAIL_MODEL = "info.pelleritoudacity.android.rcapstone.extra.parcel.detail.model";
    String EXTRA_PARCEL_MORE_DETAIL_MODEL = "info.pelleritoudacity.android.rcapstone.extra.parcel.more.detail.model";
    String EXTRA_SUBREDDIT_DETAIL_STR_ID = "info.pelleritoudacity.android.rcapstone.extra.subreddit.detail.str.id";
    String EXTRA_SUBREDDIT_DETAIL_POSITION = "info.pelleritoudacity.android.rcapstone.extra.subreddit.detail.position";
    String EXTRA_FRAGMENT_PARCEL_MAIN = "info.pelleritoudacity.android.rcapstone.extra.fragment.parcel.main";
    String EXTRA_FRAGMENT_SUBREDDIT_SELECTED = "info.pelleritoudacity.android.rcapstone.extra.fragment.subreddit.selected";
    String EXTRA_FRAGMENT_STRING_ID = "info.pelleritoudacity.android.rcapstone.extra.fragment.string.id";

    String EXTRA_FRAGMENT_MANAGE_RESTORE = "info.pelleritoudacity.android.rcapstone.extra.fragment.manage.restore";

    String EXTRA_FRAGMENT_PARCEL_SUBREDDIT_DETAIL = "info.pelleritoudacity.android.rcapstone.extra.fragment.parcel.subreddit.detail";

    String EXTRA_ACTIVITY_REDDIT_RESET = "info.pelleritoudacity.android.rcapstone.extra.activity.subreddit.reset";
    String EXTRA_ACTIVITY_REDDIT_REFRESH = "info.pelleritoudacity.android.rcapstone.extra.activity.subreddit.refresh";
    String EXTRA_ACTIVITY_DETAIL_REFRESH = "info.pelleritoudacity.android.rcapstone.extra.activity.detail.refresh";
    String EXTRA_ACTIVITY_DETAIL_FAVORITE = "info.pelleritoudacity.android.rcapstone.extra.activity.detail.favorite";
    String EXTRA_ACTIVITY_MANAGE_REFRESH = "info.pelleritoudacity.android.rcapstone.extra.activity.manage.refresh";

    String EXTRA_PARCEL_ACTIVITY_DETAIL = "info.pelleritoudacity.android.rcapstone.extra.parcel.activity.detail";


    String EXTRA_MAIN_EXIT_FULLSCREEN = "info.pelleritoudacity.android.rcapstone.extra.main.exit.fullscreen";
    String EXTRA_MAIN_FULLSCREEN = "info.pelleritoudacity.android.rcapstone.extra.main.fullscreen";
    String EXTRA_MAIN_TARGET = "info.pelleritoudacity.android.rcapstone.extra.main.target";
    String EXTRA_TAB_POSITION = "info.pelleritoudacity.android.rcapstone.extra.tab.position";
    String EXTRA_MAIN_SEARCH = "info.pelleritoudacity.android.rcapstone.extra.main.search";
    String CATEGORY_ALL = "all";
    String CATEGORY_POPULAR = "popular";
    String CATEGORY_FAVORITE = "favorite";


    String DEFAULT_START_VALUE_MAIN_TARGET = "info.pelleritoudacity.android.rcapstone.default.start.value.main.target";
    String ALL_MAIN_TARGET = "info.pelleritoudacity.android.rcapstone.all.main.target";
    String POPULAR_MAIN_TARGET = "info.pelleritoudacity.android.rcapstone.popular.main.target";
    String FAVORITE_MAIN_TARGET = "info.pelleritoudacity.android.rcapstone.favorite.main.target";
    String TAB_MAIN_TARGET = "info.pelleritoudacity.android.rcapstone.tab.main.target";
    String SEARCH_MAIN_TARGET = "info.pelleritoudacity.android.rcapstone.search.main.target";
    String NAVIGATION_MAIN_TARGET = "info.pelleritoudacity.android.rcapstone.navigation.main.target";


    int DETAIL_TARGET_NO_UPDATE = 200;
    int DETAIL_TARGET = 201;
    int MORE_DETAIL_TARGET = 202;
    int SEARCH_DETAIL_TARGET = 203;
    int MORE_SEARCH_DETAIL_TARGET = 204;
    int FAVORITE_DETAIL_TARGET = 205;

    int PROCESS_LOGIN_OK = 1;
    int PROCESS_LOGOUT_OK = 2;
    int PROCESS_LOGIN_ERROR = 3;
    int PROCESS_LOGOUT_ERROR = 4;

    String EXTRA_RESTORE_MANAGE = "info.pelleritoudacity.android.rcapstone.extra.restore.manage";

    String EXTRA_YOUTUBE_PARAM = "info.pelleritoudacity.android.rcapstone.extra.youtube.param";
    String EXTRA_YOUTUBE_TITLE = "info.pelleritoudacity.android.rcapstone.extra.youtube.title";

    String ACTION_SHORTCUT_POPULAR = "info.pelleritoudacity.android.rcapstone.shortcut.popular";
    String ACTION_SHORTCUT_ALL = "info.pelleritoudacity.android.rcapstone.shortcut.all";
    String ACTION_SHORTCUT_FAVORITE = "info.pelleritoudacity.android.rcapstone.shortcut.favorite";

    String USER_AGENT_MEDIA = "info.pelleritoudacity.android.rcapstone.exo.player.media";

    int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 10;

    int OK_HTTP_CONNECTION_TIMEOUT = 60;
    int OK_HTTP_CONNECTION_READ_TIMEOUT = 30;
    int OK_HTTP_CONNECTION_WRITE_TIMEOUT = 15;

    String DEFAULT_SUBREDDIT_CATEGORY = "AskReddit,politics,worldnews,nba,videos,funny," +
            "todayilearned,soccer,cfb,gaming,movies,news,gifs,aww,wtf,showerthoughts,technology";


    int DEFAULT_SUBREDDIT_ITEMS = 3;
    int REMOVED_SUBREDDIT_ITEMS = 1;
    int RESTORE_SUBREDDIT_ITEMS = 0;

    int DEFAULT_SUBREDDIT_VISIBLE = 1;
    boolean DEFAULT_GENERAL_SETTINGS = true;
    String EXO_PLAYER_MANAGER_TAG = "info.pelleritoudacity.android.rcapstone.exo.player.tag.subreddit";

    int NOTIFICATION_ID = 1;

    String NOTIFICATION_CHANNEL_ID = "info.pelleritoudacity.android.rcapstone.exo.player.tag.one";
    String NOTIFICATION_CHANNEL_NAME = "Channel Media Exoplayer";

    boolean IS_IMA_AD_EXTENSION = true;
    boolean IS_MEDIA_SESSION = true;
    boolean IS_RENDERING_VIDEO = false;
    boolean IS_AUTOPLAY_VIDEO = true;
    boolean IS_REPEAT_VIDEO = true;
    boolean IS_VIDEO_FULL = true;

    boolean IS_MUTED_AUDIO = true;
    boolean IS_CONTROLLER_ALWAYS = true;

    int PLAYER_SHOW_CONTROLLER_ALWAYS = -1;

    String PATTERN_HH_MM_SS = "HH:mm:ss";
    String PATTERN_MM_SS = "mm:ss";

    String DEFAULT_SORT_BY = "top";
    String DEFAULT_SORT_TIME = "month";

    String SHOW_MORE_COMMENTS = "true";
    String STR_PARENT_LINK = "t3_";
    String STR_PARENT_COMMENT = "t1_";

    String DEFAULT_LISTING_KIND = "Listing";
    String DEFAULT_MORE_KIND = "more";
    String DEFAULT_COMMENT_KIND = "t1";

    int LEVEL_DEPTH_PADDING = 20;

    String JSON_REPLIES_EMPTY = "\"replies\":\"\"";
    String JSON_REPLIES_REPLACE = "\"replies\":{\"kind\":\"ReplaceString\"}";

    String DEFAULT_COLOR_INDICATOR = "#00000000" + "," + "#FF1744" + "," + "#2979FF" + "," + "#76FF03" + "," +
            "#FFEA00" + "," + "#d602ee" + "," + "#BB86FC" + "," +
            "#C51162" + "," + "#FFDE03" + "," + "#FF0266";

    String DEFAULT_COLOR_BACKGROUND = "#00000000" + "," + "#FFEBEE" + "," + "#E3F2FD" + "," + "#F1F8E9" + "," +
            "#FFFDE7" + "," + "#fae4fc" + "," + "#EDE7F6" + "," +
            "#FCE4EC" + "," + "#FFF8E1" + "," + "#FCE4EC";

    String LABEL_SUBMENU_HOT = "hot";
    String LABEL_SUBMENU_NEW = "new";
    String LABEL_SUBMENU_RISING = "rising";
    String LABEL_SUBMENU_TOP = "top";
    String LABEL_SUBMENU_CONTROVERSIAL = "controversial";
    String LABEL_TIME_HOUR = "hour";
    String LABEL_TIME_DAY = "day";
    String LABEL_TIME_WEEK = "week";
    String LABEL_TIME_MONTH = "month";
    String LABEL_TIME_YEAR = "year";
    String LABEL_TIME_ALL = "all";
    String LABEL_TIME_NOTHING = "";

    int TIME_APPROX_NOW = 60 * 60 * 2;

    int PREFERENCE_INSERT_PREFS = R.string.pref_insert_prefs;
    int PREFERENCE_LOGIN_START = R.string.pref_login_start;
    int PREFERENCE_SESSION_ACCESS_TOKEN = R.string.pref_session_access_token;
    int PREFERENCE_SESSION_REFRESH_TOKEN = R.string.pref_session_refresh_token;
    int PREFERENCE_SESSION_EXPIRED = R.string.pref_session_expired;
    int PREFERENCE_TIME_TOKEN = R.string.pref_time_token;
    int PREFERENCE_SUBREDDIT_KEY = R.string.pref_subreddit_key;
    int PREFERENCE_SUBREDDIT_SORT = R.string.pref_subreddit_sort;
    int PREFERENCE_TIME_SORT = R.string.pref_time_sort;
    int PREFERENCE_SYNC_FREQUENCY = R.string.pref_sync_frequency;
    int PREFERENCE_ITEM_PAGE = R.string.pref_item_page;
    int PREFERENCE_DEPTH_PAGE = R.string.pref_depth_page;
    int PREFERENCE_VOLUME_MUTED = R.string.pref_volume_muted;
    int PREFERENCE_TYPE_MODE = R.string.pref_type_mode;
    int PREFERENCE_LOGIN_NAME = R.string.pref_login_name;
    int PREFERENCE_FRAGMENT_MORE_NESTED_POSITION_HEIGHT = R.string.pref_more_fragment_more_nested_position_height;
    int PREFERENCE_LOGIN_OVER18 = R.string.pref_login_over18;
    int PREFERENCE_TAB_HISTORY = R.string.pref_tab_history;
    int PREFERENCE_LAST_CATEGORY = R.string.pref_last_category;
    int PREFERENCE_LAST_TARGET = R.string.pref_last_target;
    int PREFERENCE_LAST_COMMENT = R.string.pref_last_comment;
    int PREFERENCE_REQUEST_PERMISSION = R.string.pref_request_permission;
    int PREFERENCE_GENERAL_INIT = R.string.pref_general_init;
    int PREFERENCE_ORIGINAL_SIZE_CONTENT = R.string.pref_original_size_content;
    int PREFERENCE_WRITE_EXTERNAL_STORAGE = R.string.pref_write_external_storage;
    int PREFERENCE_NIGHT_MODE = R.string.pref_night_mode;
    int PREFERENCE_GENERAL_IMAGES = R.string.pref_general_images;
    int PREFERENCE_GENERAL_GIFS = R.string.pref_general_gifs;
    int PREFERENCE_GENERAL_ALBUMS = R.string.pref_general_albums;
    int PREFERENCE_GENERAL_VIDEOS = R.string.pref_general_videos;
    int PREFERENCE_GENERAL_SELF = R.string.pref_general_self;
    int PREFERENCE_GENERAL_LINKS = R.string.pref_general_links;

}
