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

public interface Costants {

    String REDDIT_BASE_URL = "https://www.reddit.com/";
    String REDDIT_TOKEN_URL = "https://www.reddit.com/";
    String REDDIT_OAUTH_URL = "https://oauth.reddit.com/";

    String REDDIT_CLIENT_ID = BuildConfig.REDDIT_CLIENT_ID;
    String REDDIT_REDIRECT_URL = BuildConfig.REDDIT_REDIRECT_URL;
    String REDDIT_ABOUT_URL = BuildConfig.REDDIT_ABOUT_URL;
    String REDDIT_STATE_RANDOM = BuildConfig.REDDIT_SECRET_STATE;


    String REDDIT_AUTHORIZATION = "Authorization";
    String REDDIT_BEARER = "Bearer ";
    String REDDIT_ACCESS_TOKEN = "access_token";
    int REDDIT_REVOKE_SUCCESS = 204;
    String REDDIT_AUTH_URL = "www.reddit.com";
    String REDDIT_USER_AGENT = "android:info.pelleritoudacity.android.rcapstone:v1.0.0 (by /u/benedettopellerito)";

    int SESSION_TIMEOUT_DEFAULT = 60;

    String REDDIT_SYNC_TAG = "rcapstone-sync";
    String TOKEN_SYNC_TAG = "rcapstone-token-sync";

    int REDDIT_LOADER_ID = 1;
    int SUBREDDIT_LOADER_ID = 2;

    int LOGIN_REQUEST_SIGNUP = 0;

    int NAV_MODE_HOME = 0;
    int NAV_MODE_LOGIN = 1;
    int NAV_MODE_SUBSCRIPTIONS = 4;
    int NAV_MODE_REFRESH = 5;
    int NAV_MODE_SETTINGS = 6;

    String PATH_SEPARATOR = "/";
    String STRING_SEPARATOR = ",";

    int RESTORE_MANAGE_RESTORE = 1;
    int RESTORE_MANAGE_REDIRECT = 2;

    String EXTRA_LOGIN_SUCCESS = "info.pelleritoudacity.android.rcapstone.extra.login.success";
    String EXTRA_LOGOUT_SUCCESS = "info.pelleritoudacity.android.rcapstone.extra.logout.success";

    String EXTRA_SUBREDDIT_CATEGORY = "info.pelleritoudacity.android.rcapstone.extra.subreddit.category";
    String EXTRA_FRAGMENT_SUBREDDIT = "info.pelleritoudacity.android.rcapstone.extra.fragment.subreddit";
    String EXTRA_FRAGMENT_STATE = "info.pelleritoudacity.android.rcapstone.extra.fragment.subreddit";

    String EXTRA_SUBSCRIPTION_STATE = "info.pelleritoudacity.android.rcapstone.extra.fragment.subreddit";

    String EXTRA_RESTORE_MANAGE = "info.pelleritoudacity.android.rcapstone.extra.restore.manage";


    String CONTENT_TYPE = "Content-Type";
    String APPLICATION_JSON = "application/json";
    String JSON_TYPE = "json";
    String LOGIN_API_USERNAME = "username";

    String USER_AGENT_CACHE = "info.pelleritoudacity.android.rcapstone.exo.player.CacheDataSourceFactory";
    String USER_AGENT_MEDIA = "info.pelleritoudacity.android.rcapstone.exo.player.media";
    String CACHE_VIDEO_DIR = "media";
    long EXT_CACHE_SIZE_MAX = 500 * 1024 * 1024;
    long EXT_CACHE_FILE_SIZE_MAX = 40 * 1024 * 1024;
    long CACHE_SIZE_MAX = 100 * 1024 * 1024;
    long CACHE_FILE_SIZE_MAX = 20 * 1024 * 1024;

    int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 10;

    int OK_HTTP_CONNECTION_TIMEOUT = 60;
    int OK_HTTP_CONNECTION_READ_TIMEOUT = 30;
    int OK_HTTP_CONNECTION_WRITE_TIMEOUT = 15;

    String TEXT_SEPARATOR = " - ";

    String DEFAULT_SUBREDDIT_CATEGORY = "AskReddit,politics,worldnews,nba,videos,funny," +
            "todayilearned,soccer,cfb,gaming,movies,news,gifs,aww,wtf,showerthoughts,technology";

    int DEFAULT_SUBREDDIT_ITEMS = 3;
    int REMOVED_SUBREDDIT_ITEMS = 1;
    int RESTORE_SUBREDDIT_ITEMS = 0;

    String MORE_SPACE = "\u202F\u202F\u202F";

    int DEFAULT_SUBREDDIT_VISIBLE = 1;

    int EXO_PROGRESSBAR_DELAY = 200;

    String EXO_PLAYER_MANAGER_TAG = "info.pelleritoudacity.android.rcapstone.exo.player.tag.subreddit";

    int NOTIFICATION_ID = 1;

    String NOTIFICATION_CHANNEL_ID = "info.pelleritoudacity.android.rcapstone.exo.player.tag.one";
    String NOTIFICATION_CHANNEL_NAME = "Channel Media Exoplayer";

    String BUNDLE_EXOPLAYER_WINDOW = "info.pelleritoudacity.android.rcapstone.exo.player.window";
    String BUNDLE_EXOPLAYER_POSITION = "info.pelleritoudacity.android.rcapstone.exo.player.position";
    String BUNDLE_EXOPLAYER_AUTOPLAY = "info.pelleritoudacity.android.rcapstone.exo.player.autoplay";

    boolean IS_MEDIA_SESSION = true;
}
