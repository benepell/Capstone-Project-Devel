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

    String REDDIT_BASE_URL = "https://www.reddit.com/";

    String REDDIT_SYNC_TAG = "baking-sync";


    int NAV_MODE_POPULAR_TEXT = 1;
    int NAV_MODE_ALL_TEXT = 2;
    int NAV_MODE_SEARCH_TEXT = 3;
    int NAV_MODE_SUBSCRIPTIONS = 4;
    int NAV_MODE_NIGHT = 5;
    int NAV_MODE_SETTINGS = 6;

    String PATH_SEPARATOR = "/";

    String USER_AGENT_CACHE = "CacheDataSourceFactory";
    String CACHE_VIDEO_DIR = "media";
    long EXT_CACHE_SIZE_MAX = 500 * 1024 * 1024;
    long EXT_CACHE_FILE_SIZE_MAX = 40 * 1024 * 1024;
    long CACHE_SIZE_MAX = 100 * 1024 * 1024;
    long CACHE_FILE_SIZE_MAX = 20 * 1024 * 1024;

    int PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 10;
}
