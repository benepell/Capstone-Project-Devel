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

package info.pelleritoudacity.android.rcapstone.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubScriptionsFragment;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class SubManageActivity extends BaseActivity {

    private static WeakReference<Context> sWeakContext;
    private static WeakReference<android.support.v4.app.FragmentManager> sWeakFragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_submanage);
        super.onCreate(savedInstanceState);

        sWeakContext = new WeakReference<>(getApplicationContext());
        sWeakFragmentManager = new WeakReference<>(getSupportFragmentManager());

        if (PrefManager.getBoolPref(getApplicationContext(), R.string.pref_insert_prefs)) {
            new RemovedItemSubRedditAsyncTask().execute();
        } else {
            Toast.makeText(getApplicationContext(), getText(R.string.text_manage_nolinks), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        startFragment();
    }

    private void startFragment() {
        if (!getSupportFragmentManager().isStateSaved()) {
            SubScriptionsFragment subScriptionsFragment = SubScriptionsFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_list_container, subScriptionsFragment).commit();
        }
    }


    private static class RemovedItemSubRedditAsyncTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            try {
                Context context = sWeakContext.get();

                Uri uri = Contract.PrefSubRedditEntry.CONTENT_URI;
                String selection = Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED + " =? AND " + Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE + " =?";
                String[] selectionArgs = {String.valueOf(0), String.valueOf(1)};
                return context.getContentResolver().query(uri,
                        null,
                        selection,
                        selectionArgs,
                        null);

            } catch (Exception e) {
                Timber.e("Failed to asynchronously load data. ");
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);
            Context context = sWeakContext.get();

            try {

                int size = cursor.getCount();
                ArrayList<String> arrayList = new ArrayList<>(size);

                String name;
                if (!cursor.isClosed()) {
                    while (cursor.moveToNext()) {
                        name = cursor.getString(cursor.getColumnIndex(Contract.PrefSubRedditEntry.COLUMN_NAME_NAME));
                        name = TextUtil.normalizeSubRedditLink(name);
                        arrayList.add(name);
                        String strPref = TextUtil.arrayToString(arrayList);
                        PrefManager.putStringPref(context, R.string.pref_subreddit_key, strPref);
                    }

                    android.support.v4.app.FragmentManager fragmentManager = sWeakFragmentManager.get();
                    SubScriptionsFragment subScriptionsFragment = new SubScriptionsFragment();
                    fragmentManager.beginTransaction()
                            .setCustomAnimations(
                                    android.R.anim.slide_in_left,
                                    android.R.anim.slide_out_right,
                                    R.anim.layout_animation_from_right,
                                    R.anim.layout_animation_from_right)
                            .replace(R.id.fragment_list_container, subScriptionsFragment).commit();

                }
            } finally {
                if ((cursor != null) && (!cursor.isClosed())) {
                    cursor.close();
                }

            }
        }
    }

    public static void manageToMainActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class).putExtra(Costants.EXTRA_RESTORE_MANAGE, Costants.RESTORE_MANAGE_REDIRECT)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }

}
