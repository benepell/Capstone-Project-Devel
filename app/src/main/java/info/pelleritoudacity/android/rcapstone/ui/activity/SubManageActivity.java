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
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.ui.fragment.SubScriptionsFragment;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class SubManageActivity extends BaseActivity implements SwipeRefreshLayout.OnRefreshListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.submanage_container)
    public CoordinatorLayout mContainer;

    @SuppressWarnings("unused")
    @BindView(R.id.swipe_refresh_submanage)
    public SwipeRefreshLayout mRefreshLayout;


    private static WeakReference<Context> sWeakContext;
    private static WeakReference<android.support.v4.app.FragmentManager> sWeakFragmentManager;
    private static WeakReference<SwipeRefreshLayout> sWeakSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_submanage);
        super.onCreate(savedInstanceState);

        sWeakContext = new WeakReference<>(getApplicationContext());
        sWeakFragmentManager = new WeakReference<>(getSupportFragmentManager());
        sWeakSwipeRefreshLayout = new WeakReference<>(mRefreshLayout);

        mRefreshLayout.setOnRefreshListener(this);

        mRefreshLayout.setRefreshing(true);
        onRefresh();

    }

    @Override
    public void onRefresh() {
        if (Preference.isInsertPrefs(getApplicationContext())) {
            new SubManageAsyncTask().execute();
        } else {
            Snackbar.make(mContainer, R.string.text_manage_nolinks, Snackbar.LENGTH_LONG).show();
        }
    }

    private static class SubManageAsyncTask extends AsyncTask<Void, Void, Cursor> {

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
                        Preference.setSubredditKey(context, strPref);
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

                    sWeakSwipeRefreshLayout.get().setRefreshing(false);

                }
            } finally {
                if ((cursor != null) && (!cursor.isClosed())) {
                    cursor.close();
                }

            }
        }
    }

    public static void manageToMainActivity(Context context) {
        context.startActivity(new Intent(context, SubRedditActivity.class).putExtra(Costant.EXTRA_RESTORE_MANAGE, Costant.RESTORE_MANAGE_REDIRECT)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_NO_ANIMATION));
    }
}
