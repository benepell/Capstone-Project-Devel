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
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.Contract;
import info.pelleritoudacity.android.rcapstone.data.rest.CategoryExecute;
import info.pelleritoudacity.android.rcapstone.ui.fragment.ManageFragment;
import info.pelleritoudacity.android.rcapstone.utility.ActivityUI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import timber.log.Timber;

public class ManageActivity extends BaseActivity {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.nested_scrollview_submanage)
    public NestedScrollView mNestedScrollView;

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.submanage_container)
    public CoordinatorLayout mContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_manage);
        super.onCreate(savedInstanceState);

        WeakReference<Context> mWeakContext = new WeakReference<>(getApplicationContext());

        if (Preference.isInsertPrefs(getApplicationContext())) {
            new ManageAsyncTask(mWeakContext).execute();

        } else if ((!Preference.isInsertPrefs(getApplicationContext()) && (NetworkUtil.isOnline(getApplicationContext())))) {
            new CategoryExecute(new WeakReference<>(getApplicationContext())).syncData();

        } else {
            Snackbar.make(mContainer, R.string.text_manage_nolinks, Snackbar.LENGTH_LONG).show();

        }

        if (getIntent() != null) {
            startFragment(getIntent().getIntExtra(Costant.EXTRA_RESTORE_MANAGE, 0) != 0);
        }

    }

    private void startFragment(boolean restoreManage) {
        if (!getSupportFragmentManager().isStateSaved()) {

            ManageFragment manageFragment = ManageFragment.newInstance(restoreManage);
            if (restoreManage) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_list_container, manageFragment).commit();

            } else {
                getSupportFragmentManager().beginTransaction()
                        .setCustomAnimations(
                                android.R.anim.slide_in_left,
                                android.R.anim.slide_out_right,
                                R.anim.layout_animation_from_right,
                                R.anim.layout_animation_from_right)
                        .replace(R.id.fragment_list_container, manageFragment).commit();
            }

        } else {
            if (mNestedScrollView == null) {
                ActivityUI.startRefresh(getApplicationContext(), ManageActivity.class);

            }
        }
    }

    private static class ManageAsyncTask extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> mWeakContext;

        ManageAsyncTask(WeakReference<Context> weakContext) {
            mWeakContext = weakContext;
        }

        @Override
        protected Cursor doInBackground(Void... voids) {

            try {

                Context context = mWeakContext.get();

                Uri uri = Contract.PrefSubRedditEntry.CONTENT_URI;
                String selection = Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED + " =?" + " AND " +
                        Contract.PrefSubRedditEntry.COLUMN_NAME_VISIBLE + " =?";
                String[] selectionArgs = {String.valueOf(0), String.valueOf(1)};

                return context.getContentResolver().query(uri,
                        null,
                        selection,
                        selectionArgs,
                        null);

            } catch (Exception e) {
                Timber.e("Failed to asynchronously load data. ");
                return null;
            }
        }

        @Override
        protected void onPostExecute(Cursor cursor) {
            super.onPostExecute(cursor);

            Context context = mWeakContext.get();

            try {
                ArrayList<String> arrayList = new ArrayList<>(cursor.getCount());

                String name;
                if (!cursor.isClosed()) {
                    while (cursor.moveToNext()) {
                        name = TextUtil.normalizeSubRedditLink(
                                cursor.getString(cursor.getColumnIndex(Contract.PrefSubRedditEntry.COLUMN_NAME_NAME)));

                        arrayList.add(name);
                        Preference.setSubredditKey(context, TextUtil.arrayToString(arrayList));
                    }
                }

            } finally {
                if ((cursor != null) && (!cursor.isClosed())) {
                    cursor.close();
                }

            }
        }

    }
}
