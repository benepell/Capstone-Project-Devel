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

package info.pelleritoudacity.android.rcapstone.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

@SuppressWarnings("WeakerAccess")
public class Contract {
    public static final String CONTENT_AUTHORITY = "info.pelleritoudacity.android.rcapstone";

    @SuppressWarnings("WeakerAccess")
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_REDDITS = "reddits";
    public static final String PATH_DATAS = "datas";
    public static final String PATH_T5DATAS = "t5datas";


    private Contract() {
    }

    public static class RedditEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_REDDITS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REDDITS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_REDDITS;

        public static final String TABLE_NAME = "reddits";
        public static final String COLUMN_NAME_KIND = "_kind";
        public static final String COLUMN_NAME_DATA = "_data";

    }

    public static class DataEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DATAS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DATAS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DATAS;

        public static final String TABLE_NAME = "datas";
        public static final String COLUMN_NAME_AFTER = "_after";
        public static final String COLUMN_NAME_DIST = "_dist";
        public static final String COLUMN_NAME_MODHASH = "_modhash";
        public static final String COLUMN_NAME_WHITELIST_STATUS = "_whitelist_status";
        public static final String COLUMN_NAME_CHILDRENS = "_childrens";
        public static final String COLUMN_NAME_BEFORE = "_before";

    }

    public static class T5dataEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_T5DATAS).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_T5DATAS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_T5DATAS;

        public static final String TABLE_NAME = "t5datas";
        public static final String COLUMN_NAME_SUGGESTED_COMMENT_SORT = "_suggested_comment_sort";
        public static final String COLUMN_NAME_HIDE_ADS = "_hide_ads";
        public static final String COLUMN_NAME_BANNER_IMG = "_banner_img";
        public static final String COLUMN_NAME_USER_SR_THEME_ENABLED = "_user_sr_theme_enabled";
        public static final String COLUMN_NAME_USER_FLAIR_TEXT = "_user_flair_text";
        public static final String COLUMN_NAME_SUBMIT_TEXT_HTML = "_submit_text_html";
        public static final String COLUMN_NAME_USER_IS_BANNED = "_user_is_banned";
        public static final String COLUMN_NAME_WIKI_ENABLED = "_wiki_enabled";
        public static final String COLUMN_NAME_SHOW_MEDIA = "_show_media";
        public static final String COLUMN_NAME_ID = "_name_id";
        public static final String COLUMN_NAME_CHILDREN_ID = "_name_children_id";
        public static final String COLUMN_NAME_DISPLAY_NAME_PREFIXED = "_display_name_prefixed";
        public static final String COLUMN_NAME_SUBMIT_TEXT = "_submit_text";
        public static final String COLUMN_NAME_USER_CAN_FLAIR_IN_SR = "_user_can_flair_in_sr";
        public static final String COLUMN_NAME_DISPLAY_NAME = "_display_name";
        public static final String COLUMN_NAME_HEADER_IMG = "_header_img";
        public static final String COLUMN_NAME_DESCRIPTION_HTML = "_description_html";
        public static final String COLUMN_NAME_TITLE = "_title";
        public static final String COLUMN_NAME_COLLAPSE_DELETED_COMMENTS = "_collapse_deleted_comments";
        public static final String COLUMN_NAME_USER_HAS_FAVORITED = "_user_has_favorited";
        public static final String COLUMN_NAME_OVER18 = "_over18";
        public static final String COLUMN_NAME_PUBLIC_DESCRIPTION_HTML = "_public_description_html";
        public static final String COLUMN_NAME_ALLOW_VIDEOS = "_allow_videos";
        public static final String COLUMN_NAME_SPOILERS_ENABLED = "_spoilers_enabled";
        public static final String COLUMN_NAME_ICON_SIZE = "_icon_size";
        public static final String COLUMN_NAME_AUDIENCE_TARGET = "_audience_target";
        public static final String COLUMN_NAME_NOTIFICATION_LEVEL = "_notification_level";
        public static final String COLUMN_NAME_ACTIVE_USER_COUNT = "_active_user_count";
        public static final String COLUMN_NAME_ICON_IMG = "_icon_img";
        public static final String COLUMN_NAME_HEADER_TITLE = "_header_title";
        public static final String COLUMN_NAME_DESCRIPTION = "_description";
        public static final String COLUMN_NAME_USER_IS_MUTED = "_user_is_muted";
        public static final String COLUMN_NAME_SUBMIT_LINK_LABEL = "_submit_link_label";
        public static final String COLUMN_NAME_ACCOUNTS_ACTIVE = "_accounts_active";
        public static final String COLUMN_NAME_PUBLIC_TRAFFIC = "_public_traffic";
        public static final String COLUMN_NAME_HEADER_SIZE = "_header_size";
        public static final String COLUMN_NAME_SUBSCRIBERS = "_subscribers";
        public static final String COLUMN_NAME_USER_FLAIR_CSS_CLASS = "_user_flair_css_class";
        public static final String COLUMN_NAME_SUBMIT_TEXT_LABEL = "_submit_text_label";
        public static final String COLUMN_NAME_WHITELIST_STATUS = "_whitelist_status";
        public static final String COLUMN_NAME_USER_SR_FLAIR_ENABLED = "_user_sr_flair_enabled";
        public static final String COLUMN_NAME_LANG = "_lang";
        public static final String COLUMN_NAME_USER_IS_MODERATOR = "_user_is_moderator";
        public static final String COLUMN_NAME_IS_ENROLLED_IN_NEW_MODMAIL = "_is_enrolled_in_new_modmail";
        public static final String COLUMN_NAME_KEY_COLOR = "_key_color";
        public static final String COLUMN_NAME_NAME = "_name";
        public static final String COLUMN_NAME_USER_FLAIR_ENABLED_IN_SR = "_user_flair_enabled_in_sr";
        public static final String COLUMN_NAME_ALLOW_VIDEOGIFS = "_allow_videogifs";
        public static final String COLUMN_NAME_URL = "_url";
        public static final String COLUMN_NAME_QUARANTINE = "_quarantine";
        public static final String COLUMN_NAME_CREATED = "_created";
        public static final String COLUMN_NAME_CREATED_UTC = "_created_utc";
        public static final String COLUMN_NAME_BANNER_SIZE = "_banner_size";
        public static final String COLUMN_NAME_USER_IS_CONTRIBUTOR = "_user_is_contributor";
        public static final String COLUMN_NAME_ALLOW_DISCOVERY = "_allow_discovery";
        public static final String COLUMN_NAME_ACCOUNTS_ACTIVE_IS_FUZZED = "_accounts_active_is_fuzzed";
        public static final String COLUMN_NAME_ADVERTISER_CATEGORY = "_advertiser_category";
        public static final String COLUMN_NAME_PUBLIC_DESCRIPTION = "_public_description";
        public static final String COLUMN_NAME_LINK_FLAIR_ENABLED = "_link_flair_enabled";
        public static final String COLUMN_NAME_ALLOW_IMAGES = "_allow_images";
        public static final String COLUMN_NAME_SHOW_MEDIA_PREVIEW = "_show_media_preview";
        public static final String COLUMN_NAME_COMMENT_SCORE_HIDE_MINS = "_comment_score_hide_mins";
        public static final String COLUMN_NAME_SUBREDDIT_TYPE = "_subreddit_type";
        public static final String COLUMN_NAME_SUBMISSION_TYPE = "_submission_type";
        public static final String COLUMN_NAME_USER_IS_SUBSCRIBER = "_user_is_subscriber";
    }


}
