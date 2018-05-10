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
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.Contract;
import info.pelleritoudacity.android.rcapstone.rest.RefreshTokenExecute;
import info.pelleritoudacity.android.rcapstone.rest.RestExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import timber.log.Timber;

public class MainActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    private Context mContext;
    private static WeakReference<Context> sWeakReference;
    private boolean mIsRefreshing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();
        sWeakReference = new WeakReference<>(mContext);

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);

        initializeFirebaseDispatcherService();

        getDataT5(getApplicationContext());

        mSwipeRefreshLayout.setOnRefreshListener(this);

        dataClearSnackBar();

        Intent intent = getIntent();


        if (intent != null)

        {
            boolean isLogged = intent.getBooleanExtra(Costants.EXTRA_LOGIN_SUCCESS, false);
            boolean isLogout = intent.getBooleanExtra(Costants.EXTRA_LOGOUT_SUCCESS, false);

            if (isLogged) {
                Snackbar.make(findViewById(R.id.main_container), R.string.text_login_success, Snackbar.LENGTH_LONG).show();
            } else if (isLogout) {
                Snackbar.make(findViewById(R.id.main_container), R.string.text_logout_success, Snackbar.LENGTH_LONG).show();
            }
        }
    }


    @Override
    public void onRefresh() {
        if (Utility.isOnline(getApplicationContext())) {
            getDataT5(getApplicationContext());
        } else {
            mIsRefreshing = false;
            Snackbar.make(findViewById(R.id.main_container), R.string.list_snackbar_offline_text, Snackbar.LENGTH_LONG).show();
        }
        updateRefreshingUI();

    }

    private void updateRefreshingUI() {
        mSwipeRefreshLayout.setRefreshing(mIsRefreshing);
    }

    public static void homeActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_NO_ANIMATION
                        | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NO_HISTORY));
    }

    private void dataClearSnackBar() {
        if (PrefManager.getBoolPref(mContext, R.string.pref_clear_data)) {
            Snackbar.make(findViewById(R.id.main_container), R.string.text_dialog_confirm_reset, Snackbar.LENGTH_LONG).show();
            PrefManager.putBoolPref(mContext, R.string.pref_clear_data, false);
        }
    }

    private void initializeFirebaseDispatcherService() {

        if (PrefManager.getBoolPref(getApplicationContext(), R.string.pref_login_start)) {

            int redditSessionExpired = Utility.getRedditSessionExpired(getApplicationContext());
            if (redditSessionExpired <= Costants.SESSION_TIMEOUT_DEFAULT) {

                String strRefreshToken = PrefManager.getStringPref(getApplicationContext(), R.string.pref_session_refresh_token);
                new RefreshTokenExecute(strRefreshToken).syncData(getApplicationContext());

            } else {

                FirebaseRefreshTokenSync.initialize(this, redditSessionExpired);
            }
        }
    }

    private void getDataT5(Context context) {
        if (context != null) {
            new RestExecute().syncData(context);
            new RemovedItemSubRedditAsyncTask().execute();
        }
    }


    private static class RemovedItemSubRedditAsyncTask extends AsyncTask<Void, Void, Cursor> {

        @Override
        protected Cursor doInBackground(Void... voids) {
            try {
                Context context = sWeakReference.get();
                Uri uri = Contract.PrefSubRedditEntry.CONTENT_URI;
                String selection = Contract.PrefSubRedditEntry.COLUMN_NAME_REMOVED + " =?";
                String[] selectionArgs = {String.valueOf(0)};
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
            Context context = sWeakReference.get();

            try {

                int size = cursor.getCount();
                ArrayList<String> arrayList = new ArrayList<>(size);

                String name;
                if (!cursor.isClosed()) {
                    while (cursor.moveToNext()) {
                        name = cursor.getString(cursor.getColumnIndex(Contract.PrefSubRedditEntry.COLUMN_NAME_NAME));
                        name = Utility.normalizeSubRedditLink(name);
                        arrayList.add(name);

                        String strPref = Utility.arrayToString(arrayList);
                        PrefManager.putStringPref(context, R.string.pref_subreddit_key, strPref);
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
