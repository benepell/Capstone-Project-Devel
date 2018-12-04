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


import android.content.Context;
import android.text.TextUtils;

import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.AppExecutors;
import info.pelleritoudacity.android.rcapstone.data.db.operation.T1Operation;
import info.pelleritoudacity.android.rcapstone.data.db.operation.T3Operation;
import info.pelleritoudacity.android.rcapstone.data.db.operation.T5Operation;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;

@SuppressWarnings("ALL")
public class DataUtils {

    private final Context mContext;
    private final AppDatabase mDb;

    public DataUtils(Context context, AppDatabase db) {
        mContext = context;
        mDb = db;
    }


    private void clearDataAll() {
        new T5Operation(mContext, mDb, null).clearData();
        new T3Operation(mContext, mDb, null).clearData();
        new T1Operation(mContext, mDb).clearData();
    }

    public void updateManageRemoved(String category) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.prefSubRedditDao().updateRecordByRemovedStar(Costant.REMOVED_SUBREDDIT_ITEMS, category);
            }
        });

    }


    public void updateLocalDbStars(int visible, String category) {
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mDb.t3Dao().updateRecordBySaved(visible, category);
            }
        });

    }

    public void updateVote(String tableStr, int numVotes, boolean voteUp, int actionVote, String nameId) {

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

        int finalDir = dir;
        int finalNumVotes = numVotes;

        if (tableStr.equals(Costant.DB_TABLE_T3)) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.t3Dao().updateRecordByVote(finalNumVotes, finalDir, nameId);
                }
            });


        } else if (tableStr.equals(Costant.DB_TABLE_T1)) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    mDb.t1Dao().updateRecordByVote(finalNumVotes, finalDir, nameId);
                }
            });

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
