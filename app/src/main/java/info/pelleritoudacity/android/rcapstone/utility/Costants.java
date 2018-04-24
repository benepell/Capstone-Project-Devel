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

public interface Costants {

    String REDDIT_CLIENT_ID = "";
    // todo add reddit client id - url: https://www.reddit.com/prefs/apps/
    String REDDIT_TOKEN_URL = "https://www.reddit.com";
    String REDDIT_OAUTH_URL = "https://oauth.reddit.com";
    String REDDIT_AUTHORIZATION = "Authorization";
    String REDDIT_BEARER = "Bearer ";
    String REDDIT_ABOUT_URL = "rcapstone.android.pelleritoudacity.info";
    String REDDIT_REDIRECT_URL = "http://rcapstone.android.pelleritoudacity.info/my_redirect";

    String REDDIT_STATE_RANDOM = "RANDOM_STRING_RCAP";
    String REDDIT_AUTH_URL = "www.reddit.com";
    String REDDIT_USER_AGENT = "android:info.pelleritoudacity.android.rcapstone:v1.0";

    String REDDIT_SYNC_TAG = "rcapstone-sync";

    int REDDIT_LOADER_ID = 1;

    int LOGIN_REQUEST_SIGNUP = 0;

    int NAV_MODE_HOME = 0;
    int NAV_MODE_LOGIN = 1;
    int NAV_MODE_SUBSCRIPTIONS = 4;
    int NAV_MODE_REFRESH = 5;
    int NAV_MODE_SETTINGS = 6;

    String PATH_SEPARATOR = "/";
    String STRING_SEPARATOR = ",";


    String EXTRA_LOGIN_SUCCESS = "info.pelleritoudacity.android.rcapstone.extra.login.success";




    String CONTENT_TYPE = "Content-Type";
    String APPLICATION_JSON = "application/json";
    String JSON_TYPE = "json";
    String LOGIN_API_USERNAME = "username";

    String USER_AGENT_CACHE = "CacheDataSourceFactory";
    String CACHE_VIDEO_DIR = "media";
    long EXT_CACHE_SIZE_MAX = 500 * 1024 * 1024;
    long EXT_CACHE_FILE_SIZE_MAX = 40 * 1024 * 1024;
    long CACHE_SIZE_MAX = 100 * 1024 * 1024;
    long CACHE_FILE_SIZE_MAX = 20 * 1024 * 1024;

    int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 10;

    int OK_HTTP_CONNECTION_TIMEOUT = 60;
    int OK_HTTP_CONNECTION_READ_TIMEOUT = 30;
    int OK_HTTP_CONNECTION_WRITE_TIMEOUT = 15;

}
