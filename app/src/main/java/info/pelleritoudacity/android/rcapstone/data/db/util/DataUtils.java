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
package info.pelleritoudacity.android.rcapstone.data.db.util;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;


import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.data.db.Operation.T3Operation;
import info.pelleritoudacity.android.rcapstone.data.db.Operation.T5Operation;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.DateUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

public class DataUtils {

    private final Context mContext;

    public DataUtils(Context context) {
        mContext = context;
    }


    public boolean isSyncData(Uri uri, String category, long timeoutseconds, int item) {

        Cursor cursor = null;
        boolean isSync = false;

        if ((uri == null) || (timeoutseconds <= 0) || item == 0) return false;

        try {

            long lastTime = 0;
            String selection = null;
            String[] selectionArgs = {category};

            if (uri.equals(Contract.T3dataEntry.CONTENT_URI)) {
                selection = Contract.T3dataEntry.COLUMN_NAME_SUBREDDIT + " LIKE ?";


            }

            cursor = mContext.getContentResolver().query(uri, null, selection, selectionArgs, null);

            if ((cursor != null) && (cursor.getCount() >= item)) {
                if (cursor.moveToFirst()) {
                    String timestamp = cursor.getString(cursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_TIME_LAST_MODIFIED));
                    lastTime = DateUtil.getSecondsTimeStamp(timestamp);

                } else {
                    return false;
                }

            }

            if (lastTime > 0) {
                isSync = timeoutseconds - lastTime > 0;

            }


        } catch (Exception e) {
            Timber.e("DATABASE isSyncData %s", e.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return isSync;
    }

    public boolean isSyncDataDetail(Uri uri, DetailModel model, long timeoutseconds, int item) {

        Cursor cursor = null;
        boolean isSync = false;

        if ((uri == null) || (timeoutseconds <= 0) || model == null || item == 0) return false;

        try {

            long lastTime = 0;
            String selection = null;
            String[] selectionArgs = {Costant.STR_PARENT_LINK + model.getStrId(), Costant.NONE_DETAIL_MORE_REPLIES};

            if (uri.equals(Contract.T1dataEntry.CONTENT_URI)) {
                selection = Contract.T1dataEntry.COLUMN_NAME_LINK_ID + " =?" + " AND " +
                        Contract.T1dataEntry.COLUMN_NAME_MORE_REPLIES + " =?";


            }

            cursor = mContext.getContentResolver().query(uri, null, selection, selectionArgs, null);

            if ((cursor != null) && (cursor.getCount() >= item)) {
                if (cursor.moveToFirst()) {
                    lastTime = DateUtil.getSecondsTimeStamp(cursor.getString(cursor.getColumnIndex(Contract.T3dataEntry.COLUMN_NAME_TIME_LAST_MODIFIED)));

                } else {
                    return false;
                }
            }

            isSync = lastTime > 0 && timeoutseconds - lastTime > 0;


        } catch (Exception e) {
            Timber.e("DATABASE isSyncDataDetail %s", e.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return isSync;
    }


    public boolean isRecordData() {

        Cursor cursor = null;
        boolean isRecord = false;

        try {
            cursor = mContext.getContentResolver().query(Contract.RedditEntry.CONTENT_URI, null, null, null, null);
            isRecord = cursor != null && cursor.getCount() > 0;

        } catch (Exception e) {
            Timber.e("DATABASE isRecordData %s", e.getMessage());

        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

        return isRecord;
    }

    private void clearDataAll() {
        new T5Operation(mContext, null).clearData();
        new T3Operation(mContext, null).clearData();
    }

    public void putNullCV(ContentValues contentValues, String contractEntry, Object strObject) {
        if (strObject == null) {
            contentValues.putNull(contractEntry);
        } else {
            contentValues.put(contractEntry, String.valueOf(strObject));
        }
    }

    public boolean updateManageRemoved(String category) {
        int count;

        Uri uri = Contract.PrefSubRedditEntry.CONTENT_URI;
        String where = Contract.PrefSubRedditEntry.COLUMN_NAME_NAME + " =? ";
        String[] selectionArgs = {category};

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED, Costant.REMOVED_SUBREDDIT_ITEMS);

        count = mContext.getContentResolver().update(uri, contentValues, where, selectionArgs);

        return count > 0;
    }

    public boolean updateManageRestore() {

        int count;

        Uri uri = Contract.PrefSubRedditEntry.CONTENT_URI;

        int i = 1;
        String where;
        String[] selectionArgs = new String[1];
        do {

            where = Contract.PrefSubRedditEntry.COLUMN_NAME_BACKUP_POSITION + " =?";
            selectionArgs[0] = String.valueOf(i);

            ContentValues contentValues = new ContentValues();
            contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED, Costant.RESTORE_SUBREDDIT_ITEMS);
            contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION, i);
            contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE, Costant.DEFAULT_SUBREDDIT_VISIBLE);

            count = mContext.getContentResolver().update(uri, contentValues, where, selectionArgs);
            i++;

        } while (count > 0);

        String stringPref = restorePrefFromDb();

        if (!TextUtils.isEmpty(restorePrefFromDb())) {
            Preference.setSubredditKey(mContext, stringPref);

        } else {

            return false;
        }

        return true;
    }

    private String restorePrefFromDb() {
        Cursor cursor = null;
        String stringPref = "";
        try {
            cursor = mContext.getContentResolver().query(Contract.PrefSubRedditEntry.CONTENT_URI, null, null, null, null);

            if ((cursor != null) && (!cursor.isClosed())) {

                String name;
                int i = 0;

                while (cursor.moveToNext()) {
                    i += 1;
                    name = cursor.getString(cursor.getColumnIndex(Contract.PrefSubRedditEntry.COLUMN_NAME_NAME));
                    if (!TextUtils.isEmpty(name)) {
                        if (i < cursor.getCount()) {
                            //noinspection StringConcatenationInLoop
                            stringPref += name + Costant.STRING_SEPARATOR;
                        } else {
                            //noinspection StringConcatenationInLoop
                            stringPref += name;
                        }
                    }
                }
            }

        } catch (Exception e) {

            Timber.e("restore pref from db error %s", e.getMessage());

        } finally {

            if ((cursor != null) && (!cursor.isClosed())) {
                cursor.close();
            }

        }

        return stringPref;
    }


    private String restoreStarsOrderFromDb() {
        Cursor cursor = null;
        String stringPref = "";
        try {
            String[] projection = {
                    " Distinct ".concat(Contract.PrefSubRedditEntry.COLUMN_NAME_NAME),
                    Contract.PrefSubRedditEntry.COLUMN_NAME_IMAGE,
                    Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE,
                    Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION,
                    Contract.PrefSubRedditEntry.COLUMN_NAME_TIME_LAST_MODIFIED
            };

            String select = Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED + " =?" + " AND " +
                    Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE + " =?";
            String[] selectArgs = new String[]{"0", "1"};


            cursor = mContext.getContentResolver()
                    .query(Contract.PrefSubRedditEntry.CONTENT_URI,
                            projection,
                            select,
                            selectArgs,
                            Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION + " ASC");

            if ((cursor != null) && (!cursor.isClosed())) {

                String name;
                int i = 0;

                while (cursor.moveToNext()) {
                    i += 1;
                    name = cursor.getString(cursor.getColumnIndex(Contract.PrefSubRedditEntry.COLUMN_NAME_NAME));
                    if (!TextUtils.isEmpty(name)) {
                        if (i < cursor.getCount()) {
                            //noinspection StringConcatenationInLoop
                            stringPref += name + Costant.STRING_SEPARATOR;
                        } else {
                            //noinspection StringConcatenationInLoop
                            stringPref += name;
                        }
                    }
                }
            }

        } catch (Exception e) {

            Timber.e("restore stars order db error %s", e.getMessage());

        } finally {

            if ((cursor != null) && (!cursor.isClosed())) {
                cursor.close();
            }

        }
        return stringPref;
    }


    public boolean updateVisibleStar(int visible, String category) {

        int count;

        Uri uri = Contract.PrefSubRedditEntry.CONTENT_URI;
        String where = Contract.PrefSubRedditEntry.COLUMN_NAME_NAME + " =?";
        String[] selectionArgs = {category};

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE, visible);

        count = mContext.getContentResolver().update(uri, contentValues, where, selectionArgs);

        if ((!TextUtils.isEmpty(category)) &&
                (visible == Costant.DEFAULT_SUBREDDIT_VISIBLE) &&
                (count > 0)) {

            String stringPref = restoreStarsOrderFromDb();
            if (!TextUtils.isEmpty(stringPref)) {
                Preference.setSubredditKey(mContext, stringPref);

            }

        }

        return count > 0;
    }


    public void updateLocalDbStars(Uri uri, int visible, String category) {

        if (uri == null) return;
        String where = Contract.T3dataEntry.COLUMN_NAME_NAME + " =?";
        String[] selectionArgs = {category};

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.T3dataEntry.COLUMN_NAME_SAVED, visible);

        mContext.getContentResolver().update(uri, contentValues, where, selectionArgs);

    }


    public boolean moveManage(int fromPosition, int toPosition) {

        Cursor cursor = null;
        int count = 0;
        Uri uri;

        if (fromPosition == 0 && toPosition == 0) {
            return false;

        } else {

            fromPosition += 1;
            toPosition += 1;
            uri = Contract.PrefSubRedditEntry.CONTENT_URI;
        }


        try {

            ContentValues contentValues = new ContentValues();
            contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION, toPosition);
            contentValues.put(Contract.PrefSubRedditEntry.COLUMN_NAME_TIME_LAST_MODIFIED, System.currentTimeMillis());

            String where = Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION + " =?";
            String[] selectionArgs = {String.valueOf(fromPosition)};

            count = mContext.getContentResolver().update(Objects.requireNonNull(uri), contentValues, where, selectionArgs);

            if (count > 0) {

                int idPosix = 0;

                String selection = Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION + " =?";
                selectionArgs[0] = String.valueOf(toPosition);
                String sortOrder = Contract.PrefSubRedditEntry.COLUMN_NAME_TIME_LAST_MODIFIED + " DESC";

                cursor = mContext.getContentResolver().query(uri, null,
                        selection,
                        selectionArgs,
                        sortOrder);

                if ((cursor != null) && (!cursor.isClosed())) {

                    cursor.moveToFirst();
                    idPosix = cursor.getInt(cursor.getColumnIndex(Contract.PrefSubRedditEntry._ID));

                }

                if (idPosix != 0) {

                    where = Contract.PrefSubRedditEntry._ID + " =?";
                    selectionArgs[0] = String.valueOf(idPosix);

                    ContentValues updateDuplicateCV = new ContentValues();
                    updateDuplicateCV.put(Contract.PrefSubRedditEntry.COLUMN_NAME_POSITION, fromPosition);

                    count = mContext.getContentResolver().update(uri, updateDuplicateCV, where, selectionArgs);

                }

            } else {

                count = 0;
            }

        } catch (Exception e) {

            Timber.e("DATABASE Move Manage Db error %s", e.getMessage());

        } finally {

            if ((cursor != null) && (!cursor.isClosed())) {
                cursor.close();
            }
        }

        return count > 0;
    }

    public void updateVote(Uri uri, int numVotes, boolean voteUp, int actionVote, String nameId) {

        int dir = 0;
        if ((TextUtils.isEmpty(nameId))) {
            return;
        }

        switch (actionVote) {
            case -1:
                dir = -1;
                numVotes -= 1;
                break;
            case 1:
                dir = 1;
                numVotes += 1;
                break;
            case 0:
                if (voteUp) {
                    numVotes -= 1;
                } else {
                    numVotes += 1;
                }

        }

        String where = null;
        String[] selectionArgs = new String[0];
        if (uri.equals(Contract.T3dataEntry.CONTENT_URI)) {
            where = Contract.T3dataEntry.COLUMN_NAME_NAME + " =?";
            selectionArgs = new String[]{nameId};

        } else if (uri.equals(Contract.T1dataEntry.CONTENT_URI)) {
            where = Contract.T1dataEntry.COLUMN_NAME_NAME + " =?";
            selectionArgs = new String[]{nameId};

        }

        ContentValues contentValues = new ContentValues();
        contentValues.put(Contract.T3dataEntry.COLUMN_NAME_SCORE, numVotes);
        contentValues.put(Contract.T3dataEntry.COLUMN_NAME_DIR_SCORE, dir);

        if (where != null) {
            mContext.getContentResolver().update(uri, contentValues, where, selectionArgs);

        }
    }

    public void clearDataPrivacy() {
        clearDataAll();
        Preference.clearAll(mContext);
    }

    public String stringInQuestionMark(String s) {

        String r = "";

        if (!TextUtils.isEmpty(s)) {
            String[] arrS = s.split(Costant.STRING_SEPARATOR);
            for (String s1 : arrS) {
                //noinspection StringConcatenationInLoop
                r += "?,";
            }
            r = r.substring(0, r.length() - 1);

        }
        return r;
    }

}
