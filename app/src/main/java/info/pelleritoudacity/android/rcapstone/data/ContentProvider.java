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

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import timber.log.Timber;

public class ContentProvider extends android.content.ContentProvider {

    private DbHelper mDbHelper;

    private static final int REDDITS = 100;
    private static final int REDDIT_WITH_ID = 101;

    private static final int DATAS = 200;
    private static final int DATA_WITH_ID = 201;

    private static final int T5DATAS = 300;
    private static final int T5DATA_WITH_ID = 301;

    private static final int T3DATAS = 400;
    private static final int T3DATA_WITH_ID = 401;

    private static final int PREFSUBREDDITS = 500;
    private static final int PREFSUBREDDIT_WITH_ID = 501;

    private static final UriMatcher sUriMatMATCHER = buildURIMatcher();

    private static UriMatcher buildURIMatcher() {

        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_REDDITS, REDDITS);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_REDDITS + "/#", REDDIT_WITH_ID);

        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_DATAS, DATAS);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_DATAS + "/#", DATA_WITH_ID);

        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_T5DATAS, T5DATAS);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_T5DATAS + "/#", T5DATA_WITH_ID);

        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_T3DATAS, T3DATAS);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_T3DATAS + "/#", T3DATA_WITH_ID);

        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_PREFSUBREDDIT, PREFSUBREDDITS);
        uriMatcher.addURI(Contract.CONTENT_AUTHORITY, Contract.PATH_T3DATAS + "/#", PREFSUBREDDIT_WITH_ID);

        return uriMatcher;
    }


    @Override
    public boolean onCreate() {
        Context context = getContext();
        mDbHelper = new DbHelper(context);
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        String mSelection;
        String[] mSelectionArgs;

        final SQLiteDatabase db = mDbHelper.getReadableDatabase();

        int match = sUriMatMATCHER.match(uri);

        Cursor returnCursor;

        switch (match) {

            case REDDITS:

                returnCursor = db.query(Contract.RedditEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            case REDDIT_WITH_ID:

                String id = uri.getPathSegments().get(1);

                mSelection = " = ? ";
                mSelectionArgs = new String[]{id};

                returnCursor = db.query(Contract.RedditEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);
                break;

            case DATAS:

                returnCursor = db.query(Contract.DataEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            case DATA_WITH_ID:

                id = uri.getPathSegments().get(1);

                mSelection = "_id=?";
                mSelectionArgs = new String[]{id};

                returnCursor = db.query(Contract.DataEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            case T5DATAS:
                returnCursor = db.query(Contract.T5dataEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            case T5DATA_WITH_ID:

                id = uri.getPathSegments().get(1);

                mSelection = "_id=?";
                mSelectionArgs = new String[]{id};

                returnCursor = db.query(Contract.T5dataEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            case T3DATAS:
                returnCursor = db.query(Contract.T3dataEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            case T3DATA_WITH_ID:

                id = uri.getPathSegments().get(1);

                mSelection = "_id=?";
                mSelectionArgs = new String[]{id};

                //noinspection UnusedAssignment
                returnCursor = db.query(Contract.T3dataEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);

            case PREFSUBREDDITS:
                returnCursor = db.query(Contract.PrefSubRedditEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);

                break;

            case PREFSUBREDDIT_WITH_ID:

                id = uri.getPathSegments().get(1);

                mSelection = "_id=?";
                mSelectionArgs = new String[]{id};

                returnCursor = db.query(Contract.PrefSubRedditEntry.TABLE_NAME,
                        projection,
                        mSelection,
                        mSelectionArgs,
                        null,
                        null,
                        sortOrder);

                break;


            default:
                throw new UnsupportedOperationException("Uri not found: " + uri);
        }

        if (getContext() != null) {

            returnCursor.setNotificationUri(getContext().getContentResolver(), uri);
        }

        return returnCursor;

    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        int match = sUriMatMATCHER.match(uri);

        switch (match) {

            case REDDITS:
                return Contract.RedditEntry.CONTENT_TYPE;

            case REDDIT_WITH_ID:
                return Contract.RedditEntry.CONTENT_ITEM_TYPE;

            case DATAS:
                return Contract.DataEntry.CONTENT_TYPE;

            case DATA_WITH_ID:
                return Contract.DataEntry.CONTENT_ITEM_TYPE;

            case T5DATAS:
                return Contract.T5dataEntry.CONTENT_TYPE;

            case T5DATA_WITH_ID:
                return Contract.T5dataEntry.CONTENT_ITEM_TYPE;

            case T3DATAS:
                return Contract.T3dataEntry.CONTENT_TYPE;

            case T3DATA_WITH_ID:
                return Contract.T3dataEntry.CONTENT_ITEM_TYPE;

            case PREFSUBREDDITS:
                return Contract.PrefSubRedditEntry.CONTENT_TYPE;

            case PREFSUBREDDIT_WITH_ID:
                return Contract.PrefSubRedditEntry.CONTENT_ITEM_TYPE;

            default:
                throw new UnsupportedOperationException("Uri not found: " + uri);
        }

    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        long id;
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatMATCHER.match(uri);

        Uri returnUri;

        switch (match) {

            case REDDITS:

                id = db.insert(Contract.RedditEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.RedditEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row .." + uri);
                }
                break;

            case DATAS:
                id = db.insert(Contract.DataEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.DataEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row .." + uri);
                }
                break;

            case T5DATAS:

                id = db.insert(Contract.T5dataEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.T5dataEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row .." + uri);
                }

                break;

            case T3DATAS:

                id = db.insert(Contract.T3dataEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.T3dataEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row .." + uri);
                }

                break;

            case PREFSUBREDDITS:

                id = db.insert(Contract.PrefSubRedditEntry.TABLE_NAME, null, values);
                if (id > 0) {
                    returnUri = ContentUris.withAppendedId(Contract.PrefSubRedditEntry.CONTENT_URI, id);
                } else {
                    throw new android.database.SQLException("Failed to insert row .." + uri);
                }

                break;

            default:
                throw new UnsupportedOperationException("Uri not found: " + uri);
        }
        if (getContext() != null) {

            getContext().getContentResolver().notifyChange(uri, null);
        }

        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        int id;
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatMATCHER.match(uri);

        int recordDelete;

        switch (match) {

            case REDDITS:
                recordDelete = db.delete(Contract.RedditEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case REDDIT_WITH_ID:
                id = Integer.parseInt(uri.getPathSegments().get(1));

                recordDelete = db.delete(Contract.RedditEntry.TABLE_NAME,
                        "_id=?",
                        new String[]{String.valueOf(id)});
                break;


            case DATAS:
                recordDelete = db.delete(Contract.DataEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case DATA_WITH_ID:

                id = Integer.parseInt(uri.getPathSegments().get(1));

                recordDelete = db.delete(Contract.DataEntry.TABLE_NAME,
                        "_id=?",
                        new String[]{String.valueOf(id)});
                break;

            case T5DATAS:
                recordDelete = db.delete(Contract.T5dataEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case T5DATA_WITH_ID:

                id = Integer.parseInt(uri.getPathSegments().get(1));

                recordDelete = db.delete(Contract.T5dataEntry.TABLE_NAME,
                        "_id=?",
                        new String[]{String.valueOf(id)});
                break;

            case T3DATAS:
                recordDelete = db.delete(Contract.T3dataEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case T3DATA_WITH_ID:

                id = Integer.parseInt(uri.getPathSegments().get(1));

                recordDelete = db.delete(Contract.T3dataEntry.TABLE_NAME,
                        "_id=?",
                        new String[]{String.valueOf(id)});
                break;

            case PREFSUBREDDITS:
                recordDelete = db.delete(Contract.PrefSubRedditEntry.TABLE_NAME,
                        selection,
                        selectionArgs);
                break;

            case PREFSUBREDDIT_WITH_ID:

                id = Integer.parseInt(uri.getPathSegments().get(1));

                recordDelete = db.delete(Contract.PrefSubRedditEntry.TABLE_NAME,
                        "_id=?",
                        new String[]{String.valueOf(id)});
                break;

            default:
                throw new UnsupportedOperationException("Uri not found: " + uri);
        }

        if ((getContext() != null) && (recordDelete != 0)) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return recordDelete;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        int id;
        int rowsUpdate;

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatMATCHER.match(uri);


        switch (match) {

            case REDDITS:
                rowsUpdate = db.update(Contract.RedditEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;

            case REDDIT_WITH_ID:
                id = Integer.parseInt(uri.getPathSegments().get(1));

                rowsUpdate = db.update(Contract.RedditEntry.TABLE_NAME,
                        values,
                        "_id=?",
                        new String[]{String.valueOf(id)});
                break;

            case DATAS:
                rowsUpdate = db.update(Contract.DataEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;

            case DATA_WITH_ID:

                id = Integer.parseInt(uri.getPathSegments().get(1));

                rowsUpdate = db.update(Contract.DataEntry.TABLE_NAME,
                        values,
                        "_id=?",
                        new String[]{String.valueOf(id)});
                break;

            case T5DATAS:
                rowsUpdate = db.update(Contract.T5dataEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;

            case T5DATA_WITH_ID:

                id = Integer.parseInt(uri.getPathSegments().get(1));

                rowsUpdate = db.update(Contract.T5dataEntry.TABLE_NAME,
                        values,
                        "_id=?",
                        new String[]{String.valueOf(id)});
                break;

            case T3DATAS:
                rowsUpdate = db.update(Contract.T3dataEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;

            case T3DATA_WITH_ID:

                id = Integer.parseInt(uri.getPathSegments().get(1));

                rowsUpdate = db.update(Contract.T3dataEntry.TABLE_NAME,
                        values,
                        "_id=?",
                        new String[]{String.valueOf(id)});
                break;

            case PREFSUBREDDITS:
                rowsUpdate = db.update(Contract.PrefSubRedditEntry.TABLE_NAME,
                        values,
                        selection,
                        selectionArgs);
                break;

            case PREFSUBREDDIT_WITH_ID:

                id = Integer.parseInt(uri.getPathSegments().get(1));

                rowsUpdate = db.update(Contract.PrefSubRedditEntry.TABLE_NAME,
                        values,
                        "_id=?",
                        new String[]{String.valueOf(id)});
                break;

            default:
                throw new UnsupportedOperationException("Uri not found: " + uri);
        }

        if (rowsUpdate != 0) {
            if (getContext() != null) {

                getContext().getContentResolver().notifyChange(uri, null);
            }
        }

        return rowsUpdate;
    }

    @Override
    public int bulkInsert(@NonNull Uri uri, @NonNull ContentValues[] values) {

        final SQLiteDatabase db = mDbHelper.getWritableDatabase();

        int match = sUriMatMATCHER.match(uri);


        int rowsInserted;
        switch (match) {

            case REDDITS:
                db.beginTransaction();
                rowsInserted = 0;

                try {
                    // insert all data
                    for (ContentValues value : values) {
                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }

                        long _id = db.insertOrThrow(Contract.RedditEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();

                } catch (SQLiteException e) {
                    Timber.v("Attempting to insert %s", e.getMessage());
                } finally {
                    // execute after ..... when is complete
                    db.endTransaction();
                }
                if ((getContext() != null) && (rowsInserted > 0)) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            case DATAS:
                db.beginTransaction();
                rowsInserted = 0;

                try {
                    // insert all data
                    for (ContentValues value : values) {

                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }


                        long _id = db.insertOrThrow(Contract.DataEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();

                } catch (SQLiteException e) {
                    Timber.v("Attempting to insert %s", e.getMessage());
                } finally {
                    // execute after ..... when is complete
                    db.endTransaction();
                }
                if ((getContext() != null) && (rowsInserted > 0)) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            case T5DATAS:
                db.beginTransaction();
                rowsInserted = 0;

                try {
                    for (ContentValues value : values) {

                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }

                        long _id = db.insertOrThrow(Contract.T5dataEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();

                } catch (SQLiteException e) {
                    Timber.v("Attempting to insert %s", e.getMessage());
                } finally {
                    db.endTransaction();
                }
                if ((getContext() != null) && (rowsInserted > 0)) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            case T3DATAS:
                db.beginTransaction();
                rowsInserted = 0;

                try {
                    for (ContentValues value : values) {

                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }

                        long _id = db.insertOrThrow(Contract.T3dataEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();

                } catch (SQLiteException e) {
                    Timber.v("Attempting to insert %s", e.getMessage());
                } finally {
                    db.endTransaction();
                }
                if ((getContext() != null) && (rowsInserted > 0)) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            case PREFSUBREDDITS:
                db.beginTransaction();
                rowsInserted = 0;

                try {
                    for (ContentValues value : values) {

                        if (value == null) {
                            throw new IllegalArgumentException("Cannot have null content values");
                        }

                        long _id = db.insertOrThrow(Contract.PrefSubRedditEntry.TABLE_NAME, null, value);
                        if (_id != -1) {
                            rowsInserted++;
                        }
                    }
                    db.setTransactionSuccessful();

                } catch (SQLiteException e) {
                    Timber.v("Attempting to insert %s", e.getMessage());
                } finally {
                    db.endTransaction();
                }
                if ((getContext() != null) && (rowsInserted > 0)) {
                    getContext().getContentResolver().notifyChange(uri, null);
                }

                return rowsInserted;

            default:
                return super.bulkInsert(uri, values);
        }

    }
}
