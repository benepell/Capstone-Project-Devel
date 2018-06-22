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

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rcapstone.db";

    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, new LeaklessCursorFactory(), DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_REDDIT_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Contract.RedditEntry.TABLE_NAME + " (" +
                        Contract.RedditEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Contract.RedditEntry.COLUMN_NAME_KIND + " TEXT NOT NULL, " +
                        Contract.RedditEntry.COLUMN_NAME_DATA + " INTEGER NOT NULL " +
                        ");";

        final String SQL_CREATE_PREFSUBREDDIT_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Contract.PrefSubRedditEntry.TABLE_NAME + " (" +
                        Contract.PrefSubRedditEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Contract.PrefSubRedditEntry.COLUMN_NAME_NAME + " TEXT NOT NULL, " +
                        Contract.PrefSubRedditEntry.COLUMN_NAME_IMAGE + " TEXT, " +
                        Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE + " INTEGER DEFAULT 0, " +
                        Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED + " INTEGER DEFAULT 0, " +
                        Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION + " INTEGER DEFAULT 0, " +
                        Contract.PrefSubRedditEntry.COLUMN_NAME_BACKUP_POSITION + " INTEGER DEFAULT 0, " +
                        Contract.PrefSubRedditEntry.COLUMN_NAME_TIME_LAST_MODIFIED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +

                        " UNIQUE (" + Contract.PrefSubRedditEntry.COLUMN_NAME_NAME + ")" + " ON CONFLICT IGNORE " +
                        ");";

        final String SQL_CREATE_DATA_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Contract.DataEntry.TABLE_NAME + " (" +
                        Contract.DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Contract.DataEntry.COLUMN_NAME_AFTER + " TEXT , " +
                        Contract.DataEntry.COLUMN_NAME_DIST + " INTEGER , " +
                        Contract.DataEntry.COLUMN_NAME_MODHASH + " TEXT, " +
                        Contract.DataEntry.COLUMN_NAME_WHITELIST_STATUS + " TEXT, " +
                        Contract.DataEntry.COLUMN_NAME_CHILDRENS + " INTEGER , " +
                        Contract.DataEntry.COLUMN_NAME_BEFORE + " BLOB  " +
                        ");";

        final String SQL_CREATE_T5DATA_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Contract.T5dataEntry.TABLE_NAME + " (" +
                        Contract.T5dataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Contract.T5dataEntry.COLUMN_NAME_TIME_LAST_MODIFIED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUGGESTED_COMMENT_SORT + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_HIDE_ADS + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_BANNER_IMG + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_SR_THEME_ENABLED + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_TEXT + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT_HTML + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_IS_BANNED + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_WIKI_ENABLED + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_SHOW_MEDIA + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_ID + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_CHILDREN_ID + " INTEGER NOT NULL, " +
                        Contract.T5dataEntry.COLUMN_NAME_DISPLAY_NAME_PREFIXED + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_CAN_FLAIR_IN_SR + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_DISPLAY_NAME + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_HEADER_IMG + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_DESCRIPTION_HTML + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_TITLE + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_COLLAPSE_DELETED_COMMENTS + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_HAS_FAVORITED + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_OVER18 + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_PUBLIC_DESCRIPTION_HTML + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_ALLOW_VIDEOS + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_SPOILERS_ENABLED + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_ICON_SIZE + " INTEGER , " +
                        Contract.T5dataEntry.COLUMN_NAME_AUDIENCE_TARGET + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_NOTIFICATION_LEVEL + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_ACTIVE_USER_COUNT + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_ICON_IMG + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_HEADER_TITLE + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_DESCRIPTION + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_IS_MUTED + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBMIT_LINK_LABEL + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_ACCOUNTS_ACTIVE + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_PUBLIC_TRAFFIC + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_HEADER_SIZE + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBSCRIBERS + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_CSS_CLASS + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT_LABEL + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_WHITELIST_STATUS + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_SR_FLAIR_ENABLED + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_LANG + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_IS_MODERATOR + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_IS_ENROLLED_IN_NEW_MODMAIL + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_KEY_COLOR + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_NAME + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_ENABLED_IN_SR + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_ALLOW_VIDEOGIFS + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_URL + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_QUARANTINE + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_CREATED + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_CREATED_UTC + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_BANNER_SIZE + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_IS_CONTRIBUTOR + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_ALLOW_DISCOVERY + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_ACCOUNTS_ACTIVE_IS_FUZZED + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_ADVERTISER_CATEGORY + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_PUBLIC_DESCRIPTION + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_LINK_FLAIR_ENABLED + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_ALLOW_IMAGES + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_SHOW_MEDIA_PREVIEW + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_COMMENT_SCORE_HIDE_MINS + " INTEGER DEFAULT 0, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBREDDIT_TYPE + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBMISSION_TYPE + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_IS_SUBSCRIBER + " TEXT, " +

                        " UNIQUE (" + Contract.T5dataEntry.COLUMN_NAME_ID + ")" + " ON CONFLICT REPLACE, " +
                        " FOREIGN KEY (" + Contract.T5dataEntry.COLUMN_NAME_CHILDREN_ID + ") REFERENCES " +
                        Contract.DataEntry.TABLE_NAME + "(" + Contract.DataEntry._ID + ")" +
                        ");";


        final String SQL_CREATE_T3DATA_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Contract.T3dataEntry.TABLE_NAME + " (" +
                        Contract.T3dataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Contract.T3dataEntry.COLUMN_NAME_TIME_LAST_MODIFIED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        Contract.T3dataEntry.COLUMN_NAME_APPROVED_AT_UTC + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_APPROVED_BY + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_ARCHIVED + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_AUTHOR + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_AUTHOR_FLAIR_CSS_CLASS + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_AUTHOR_FLAIR_TEXT + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_BANNED_AT_UTC + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_BANNED_BY + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_CAN_GILD + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_CAN_MOD_POST + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_CHILDREN_ID + " INTEGER NOT NULL, " +
                        Contract.T3dataEntry.COLUMN_NAME_CLICKED + " INTEGER DEFAULT 0 , " +
                        Contract.T3dataEntry.COLUMN_NAME_CONTEST_MODE + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_CREATED + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_CREATED_UTC + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_DISTINGUISHED + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_DOMAIN + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_DOWNS + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_EDITED + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_GILDED + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_HIDDEN + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_HIDE_SCORE + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_ID + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_IS_CROSSPOSTABLE + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_IS_REDDIT_MEDIA_DOMAIN + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_IS_SELF + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_IS_VIDEO + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_LIKES + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_LINK_FLAIR_CSS_CLASS + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_LINK_FLAIR_TEXT + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_LOCKED + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_EMBED + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MOD_NOTE + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MOD_REASON_BY + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MOD_REASON_TITLE + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MOD_REPORTS + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_NAME + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_NO_FOLLOW + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_NUM_COMMENTS + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_NUM_CROSSPOSTS + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_NUM_REPORTS + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_OVER_18 + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_PARENT_WHITELIST_STATUS + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_PERMALINK + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_PINNED + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_PWLS + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_QUARANTINE + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_REMOVAL_REASON + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_REPORT_REASONS + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_SAVED + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_SCORE + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_SECURE_MEDIA + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_SECURE_MEDIA_EMBED + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_SELFTEXT + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_SELFTEXT_HTML + " BLOB, " +
                        Contract.T3dataEntry.COLUMN_NAME_SEND_REPLIES + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_SPOILER + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_STICKIED + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_ID + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_NAME_PREFIXE + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_SUBSCRIBERS + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT_TYPE + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_SUGGESTED_SORT + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL_HEIGHT + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_THUMBNAIL_WIDTH + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_TITLE + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_UPS + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_URL + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_USER_REPORTS + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_VIEW_COUNT + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_VISITED + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_WHITELIST_STATUS + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_WLS + " INTEGER DEFAULT 0, " +

                        Contract.T3dataEntry.COLUMN_NAME_TARGET + " TEXT, " +

                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_URL + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_WIDTH + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IMAGE_SOURCE_HEIGHT + " INTEGER DEFAULT 0, " +

                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_HLS_URL + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_DASH_URL + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_SCRUBBER_MEDIA_URL + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_FALLBACK_URL + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_TRANSCODING_STATUS + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_DURATION + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_WIDTH + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_VIDEO_HEIGHT + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_PREVIEW_IS_VIDEO_GIF + " INTEGER DEFAULT 0, " +

                        Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_URL + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_WIDTH + " INTEGER DEFAULT 0, " +
                        Contract.T3dataEntry.COLUMN_NAME_VARIANT_VIDEO_MP4_HEIGHT + " INTEGER DEFAULT 0, " +

                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_TYPE + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_IS_VIDEO + " INTEGER, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_PROVIDER_URL + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_TITLE + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_TYPE + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_HTML + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_AUTHOR_NAME + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_HEIGHT + " INTEGER, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_WIDTH + " INTEGER, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_VERSION + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_WIDTH + " INTEGER, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_PROVIDER_NAME + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_URL + " TEXT, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_THUMBNAIL_HEIGHT + " INTEGER, " +
                        Contract.T3dataEntry.COLUMN_NAME_MEDIA_OEMBED_AUTHOR_URL + " TEXT, " +

                        " UNIQUE (" + Contract.T3dataEntry.COLUMN_NAME_ID + ")" + " ON CONFLICT REPLACE, " +
                        " FOREIGN KEY (" + Contract.T3dataEntry.COLUMN_NAME_CHILDREN_ID + ") REFERENCES " +
                        Contract.DataEntry.TABLE_NAME + "(" + Contract.DataEntry._ID + ")" +
                        ");";


        final String SQL_CREATE_T1DATA_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Contract.T1dataEntry.TABLE_NAME + " (" +
                        Contract.T1dataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Contract.T1dataEntry.COLUMN_NAME_TIME_LAST_MODIFIED + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP, " +
                        Contract.T1dataEntry.COLUMN_NAME_ISCROSSPOSTABLE + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_SUBREDDIT + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_SUBREDDITID + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_APPROVEDATUTC + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_WLS + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_MODREASONBY + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_BANNEDBY + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_NUMREPORTS + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_LINK_ID + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_REMOVALREASON + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_THUMBNAILWIDTH + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_SELFTEXTHTML + " INTEGER, " +
                        Contract.T1dataEntry.COLUMN_NAME_PARENT_ID + " TEXT , " +
                        Contract.T1dataEntry.COLUMN_NAME_LIKES + " TEXT , " +
                        Contract.T1dataEntry.COLUMN_NAME_SUGGESTEDSORT + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_USERREPORTS + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_SECUREMEDIA + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_ISREDDITMEDIADOMAIN + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_SAVED + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_ID + " TEXT NOT NULL, " +
                        Contract.T1dataEntry.COLUMN_NAME_CHILDREN_ID + " TEXT NOT NULL, " +
                        Contract.T1dataEntry.COLUMN_NAME_BANNEDATUTC + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_MODREASONTITLE + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_VIEWCOUNT + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_ARCHIVED + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_CLICKED + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_NOFOLLOW + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_AUTHOR + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_NUMCROSSPOSTS + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_LINKFLAIRTEXT + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_CANMODPOST + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_SENDREPLIES + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_PINNED + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_SCORE + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_APPROVEDBY + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_OVER18 + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_REPORTREASONS + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_DOMAIN + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_HIDDEN + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_PREVIEW + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_PWLS + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_THUMBNAIL + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_EDITED + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_LINKFLAIRCSSCLASS + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_CONTESTMODE + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_GILDED + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_LOCKED + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_DOWNS + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_BODY + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_MODREPORTS + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_SUBREDDITSUBSCRIBERS + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_SECUREMEDIAEMBED + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_MEDIAEMBED + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_BODY_HTML + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_STICKIED + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_VISITED + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_CANGILD + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_THUMBNAILHEIGHT + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_NAME + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_SPOILER + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_PERMALINK + " BLOB, " +
                        Contract.T1dataEntry.COLUMN_NAME_SUBREDDITTYPE + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_PARENTWHITELISTSTATUS + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_HIDESCORE + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_CREATED + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_URL + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_AUTHORFLAIRTEXT + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_QUARANTINE + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_TITLE + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_DEPTH + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_CREATEDUTC + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_SUBREDDITNAMEPREFIXED + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_UPS + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_NUMCOMMENTS + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_ISSELF + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_WHITELISTSTATUS + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_MODNOTE + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_MEDIA + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_ISVIDEO + " INTEGER DEFAULT 0, " +
                        Contract.T1dataEntry.COLUMN_NAME_DISTINGUISHED + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_SELFTEXT + " TEXT, " +
                        Contract.T1dataEntry.COLUMN_NAME_AUTHORFLAIRCSSCLASS + " TEXT, " +

                        " FOREIGN KEY (" + Contract.T1dataEntry.COLUMN_NAME_CHILDREN_ID + ") REFERENCES " +
                        Contract.DataEntry.TABLE_NAME + "(" + Contract.DataEntry._ID + ")" +
                        ");";


        db.execSQL(SQL_CREATE_REDDIT_TABLE);
        db.execSQL(SQL_CREATE_DATA_TABLE);
        db.execSQL(SQL_CREATE_T5DATA_TABLE);
        db.execSQL(SQL_CREATE_T3DATA_TABLE);
        db.execSQL(SQL_CREATE_PREFSUBREDDIT_TABLE);
        db.execSQL(SQL_CREATE_T1DATA_TABLE);

      /*  Timber.d("SQL STATEMENT:  " +
                SQL_CREATE_REDDIT_TABLE + " " + SQL_CREATE_DATA_TABLE + " " +
                SQL_CREATE_T5DATA_TABLE + SQL_CREATE_T3DATA_TABLE + SQL_CREATE_PREFSUBREDDIT_TABLE);*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.RedditEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.DataEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.T5dataEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.T3dataEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.PrefSubRedditEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.T1dataEntry.TABLE_NAME);
        onCreate(db);
    }

}
