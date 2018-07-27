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

    int SESSION_TIMEOUT_DEFAULT = 60;

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
    int RESTORE_MANAGE_REDIRECT = 2;

    String EXTRA_LOGIN_SUCCESS = "info.pelleritoudacity.android.rcapstone.extra.login.success";
    String EXTRA_LOGOUT_SUCCESS = "info.pelleritoudacity.android.rcapstone.extra.logout.success";

    String EXTRA_SUBREDDIT_CATEGORY = "info.pelleritoudacity.android.rcapstone.extra.subreddit.category";
    String EXTRA_SUBREDDIT_DETAIL_CATEGORY = "info.pelleritoudacity.android.rcapstone.extra.subreddit.detail.category";
    String EXTRA_PARCEL_DETAIL_MODEL = "info.pelleritoudacity.android.rcapstone.extra.parcel.detail.model";
    String EXTRA_PARCEL_MORE_DETAIL_MODEL = "info.pelleritoudacity.android.rcapstone.extra.parcel.more.detail.model";
    String EXTRA_SUBREDDIT_DETAIL_STR_ID = "info.pelleritoudacity.android.rcapstone.extra.subreddit.detail.str.id";
    String EXTRA_SUBREDDIT_DETAIL_POSITION = "info.pelleritoudacity.android.rcapstone.extra.subreddit.detail.position";
    String EXTRA_FRAGMENT_SUBREDDIT = "info.pelleritoudacity.android.rcapstone.extra.fragment.subreddit";
    String EXTRA_FRAGMENT_SUBREDDIT_SELECTED = "info.pelleritoudacity.android.rcapstone.extra.fragment.subreddit.selected";
    String EXTRA_FRAGMENT_TARGET = "info.pelleritoudacity.android.rcapstone.extra.fragment.target";
    String EXTRA_FRAGMENT_SEARCH = "info.pelleritoudacity.android.rcapstone.extra.fragment.search";
    String EXTRA_FRAGMENT_STRING_ID = "info.pelleritoudacity.android.rcapstone.extra.fragment.string.id";
    String EXTRA_TAB_POSITION = "info.pelleritoudacity.android.rcapstone.extra.tab.position";

    String EXTRA_FRAGMENT_PARCEL_SUBREDDIT_DETAIL = "info.pelleritoudacity.android.rcapstone.extra.fragment.parcel.subreddit.detail";

    String EXTRA_ACTIVITY_SUBREDDIT_REFRESH = "info.pelleritoudacity.android.rcapstone.extra.activity.subreddit.refresh";
    String EXTRA_ACTIVITY_SUBREDDIT_DETAIL_REFRESH = "info.pelleritoudacity.android.rcapstone.extra.activity.subreddit.detail.refresh";
    String EXTRA_ACTIVITY_SUBMANAGE_REFRESH = "info.pelleritoudacity.android.rcapstone.extra.activity.submanage.refresh";

    String EXTRA_PARCEL_ACTIVITY_DETAIL = "info.pelleritoudacity.android.rcapstone.extra.parcel.activity.detail";


    String EXTRA_SUBREDDIT_EXIT_FULLSCREEN = "info.pelleritoudacity.android.rcapstone.extra.subreddit.exit.fullscreen";
    String EXTRA_SUBREDDIT_FULLSCREEN = "info.pelleritoudacity.android.rcapstone.extra.subreddit.fullscreen";
    String EXTRA_SUBREDDIT_TARGET = "info.pelleritoudacity.android.rcapstone.extra.subreddit.target";
    String EXTRA_SUBREDDIT_SEARCH = "info.pelleritoudacity.android.rcapstone.extra.subreddit.search";
    String EXTRA_SUBREDDIT_SEARCH_DETAIL = "info.pelleritoudacity.android.rcapstone.extra.subreddit.detail";
    String EXTRA_MEDIA_IMA = "info.pelleritoudacity.android.rcapstone.extra.media.ima";
    String SUBREDDIT_CATEGORY_ALL = "all";
    String SUBREDDIT_CATEGORY_POPULAR = "popular";
    String SUBREDDIT_CATEGORY_FAVORITE = "favorite";


    String SUBREDDIT_TARGET_DEFAULT_START_VALUE = "info.pelleritoudacity.android.rcapstone.subreddit.target.default.start.value";
    String SUBREDDIT_TARGET_ALL = "info.pelleritoudacity.android.rcapstone.subreddit.target.all";
    String SUBREDDIT_TARGET_POPULAR = "info.pelleritoudacity.android.rcapstone.subreddit.target.popular";
    String SUBREDDIT_TARGET_FAVORITE = "info.pelleritoudacity.android.rcapstone.subreddit.target.favorite";
    String SUBREDDIT_TARGET_TAB = "info.pelleritoudacity.android.rcapstone.subreddit.target.tab";
    String SUBREDDIT_TARGET_SEARCH = "info.pelleritoudacity.android.rcapstone.subreddit.target.search";
    String SUBREDDIT_TARGET_SEARCH_DETAIL = "info.pelleritoudacity.android.rcapstone.subreddit.target.search.detail";
    String SUBREDDIT_TARGET_NAVIGATION = "info.pelleritoudacity.android.rcapstone.subreddit.target.navigation";
    String SUBREDDIT_TARGET_SORT = "info.pelleritoudacity.android.rcapstone.subreddit.target.sort";


    int TARGET_DETAIL_NO_UPDATE = 200;
    int TARGET_DETAIL = 201;
    int TARGET_MORE_DETAIL = 202;
    int TARGET_DETAIL_SEARCH = 203;
    int TARGET_MORE_DETAIL_SEARCH = 204;


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

    String BUNDLE_EXOPLAYER_WINDOW = "info.pelleritoudacity.android.rcapstone.exo.player.window";
    String BUNDLE_EXOPLAYER_POSITION = "info.pelleritoudacity.android.rcapstone.exo.player.position";
    String BUNDLE_EXOPLAYER_AUTOPLAY = "info.pelleritoudacity.android.rcapstone.exo.player.autoplay";

    boolean IS_ADAPTIVE_STREAMING = true;
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
    int PREFERENCE_CLEAR_DATA = R.string.pref_clear_data;
    int PREFERENCE_TYPE_MODE = R.string.pref_type_mode;
    int PREFERENCE_LOGIN_NAME = R.string.pref_login_name;
    int PREFERENCE_FRAGMENT_MORE_NESTED_POSITION_HEIGHT = R.string.pref_more_fragment_more_nested_position_height;
    int PREFERENCE_LOGIN_OVER18 = R.string.pref_login_over18;
    int PREFERENCE_TAB_HISTORY = R.string.pref_tab_history;
    int PREFERENCE_LAST_CATEGORY = R.string.pref_last_category;
    int PREFERENCE_LAST_TARGET = R.string.pref_last_target;
    int PREFERENCE_LAST_COMMENT = R.string.pref_last_comment;
    int PREFERENCE_RESTORE_MANAGE = R.string.pref_restore_manage;
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
