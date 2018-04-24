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

import timber.log.Timber;

class DbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "rcapstone.db";

    private static final int DATABASE_VERSION = 1;

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_REDDIT_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Contract.RedditEntry.TABLE_NAME + " (" +
                        Contract.RedditEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Contract.RedditEntry.COLUMN_NAME_KIND + " TEXT NOT NULL, " +
                        Contract.RedditEntry.COLUMN_NAME_DATA + " INTEGER NOT NULL " +
                        ");";

        final String SQL_CREATE_DATA_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Contract.DataEntry.TABLE_NAME + " (" +
                        Contract.DataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Contract.DataEntry.COLUMN_NAME_AFTER + " TEXT NOT NULL, " +
                        Contract.DataEntry.COLUMN_NAME_DIST + " INTEGER NOT NULL, " +
                        Contract.DataEntry.COLUMN_NAME_MODHASH + " TEXT, " +
                        Contract.DataEntry.COLUMN_NAME_WHITELIST_STATUS + " TEXT, " +
                        Contract.DataEntry.COLUMN_NAME_CHILDRENS + " INTEGER NOT NULL, " +
                        Contract.DataEntry.COLUMN_NAME_BEFORE + " BLOB  " +
                        ");";

        final String SQL_CREATE_T5DATA_TABLE =
                "CREATE TABLE IF NOT EXISTS " + Contract.T5dataEntry.TABLE_NAME + " (" +
                        Contract.T5dataEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUGGESTED_COMMENT_SORT + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_HIDE_ADS + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_BANNER_IMG + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_SR_THEME_ENABLED + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_TEXT + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT_HTML + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_IS_BANNED + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_WIKI_ENABLED + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_SHOW_MEDIA + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_ID + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_CHILDREN_ID + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_DISPLAY_NAME_PREFIXED + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_CAN_FLAIR_IN_SR + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_DISPLAY_NAME + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_HEADER_IMG + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_DESCRIPTION_HTML + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_TITLE + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_COLLAPSE_DELETED_COMMENTS + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_HAS_FAVORITED + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_OVER18 + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_PUBLIC_DESCRIPTION_HTML + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_ALLOW_VIDEOS + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_SPOILERS_ENABLED + " INTEGER, " +
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
                        Contract.T5dataEntry.COLUMN_NAME_PUBLIC_TRAFFIC + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_HEADER_SIZE + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBSCRIBERS + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_CSS_CLASS + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBMIT_TEXT_LABEL + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_WHITELIST_STATUS + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_SR_FLAIR_ENABLED + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_LANG + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_IS_MODERATOR + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_IS_ENROLLED_IN_NEW_MODMAIL + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_KEY_COLOR + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_NAME + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_FLAIR_ENABLED_IN_SR + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_ALLOW_VIDEOGIFS + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_URL + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_QUARANTINE + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_CREATED + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_CREATED_UTC + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_BANNER_SIZE + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_IS_CONTRIBUTOR + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_ALLOW_DISCOVERY + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_ACCOUNTS_ACTIVE_IS_FUZZED + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_ADVERTISER_CATEGORY + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_PUBLIC_DESCRIPTION + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_LINK_FLAIR_ENABLED + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_ALLOW_IMAGES + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_SHOW_MEDIA_PREVIEW + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_COMMENT_SCORE_HIDE_MINS + " INTEGER, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBREDDIT_TYPE + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_SUBMISSION_TYPE + " TEXT, " +
                        Contract.T5dataEntry.COLUMN_NAME_USER_IS_SUBSCRIBER + " TEXT, " +

                        " FOREIGN KEY (" + Contract.T5dataEntry.COLUMN_NAME_CHILDREN_ID + ") REFERENCES " +
                        Contract.DataEntry.TABLE_NAME + "(" + Contract.DataEntry._ID + ")" +
                        ");";


        db.execSQL(SQL_CREATE_REDDIT_TABLE);
        db.execSQL(SQL_CREATE_DATA_TABLE);
        db.execSQL(SQL_CREATE_T5DATA_TABLE);


        Timber.d("SQL STATEMENT:  " +
                SQL_CREATE_REDDIT_TABLE + " " + SQL_CREATE_DATA_TABLE + " " +
                SQL_CREATE_T5DATA_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + Contract.RedditEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.DataEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + Contract.T5dataEntry.TABLE_NAME);
        onCreate(db);
    }


}
