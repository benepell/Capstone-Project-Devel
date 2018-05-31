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
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.os.Bundle;

import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.rest.RefreshTokenExecute;
import info.pelleritoudacity.android.rcapstone.rest.RestExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtils;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.SessionUtils.getRedditSessionExpired;

public class MainActivity extends BaseActivity
        implements SwipeRefreshLayout.OnRefreshListener {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.swipe_refresh_layout)
    public SwipeRefreshLayout mSwipeRefreshLayout;

    private Context mContext;
    private boolean mIsRefreshing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_main);
        super.onCreate(savedInstanceState);

        mContext = getApplicationContext();

        Timber.plant(new Timber.DebugTree());
        ButterKnife.bind(this);


        initializeFirebaseDispatcherService();
        // todo remove
        getDataT5(getApplicationContext());

        mSwipeRefreshLayout.setOnRefreshListener(this);

        dataClearSnackBar();

        intentRequest(getIntent(), intentDefaultCategoryPopular());

        PrefManager.putBoolPref(mContext, R.string.pref_volume_muted, Costants.IS_MUTED_AUDIO);

    }


    @Override
    public void onRefresh() {
        if (NetworkUtils.isOnline(getApplicationContext())) {
            getDataT5(getApplicationContext());
        } else {
            mIsRefreshing = false;
            Snackbar.make(findViewById(R.id.main_container), R.string.list_snackbar_offline_text, Snackbar.LENGTH_LONG).show();
        }
        updateRefreshingUI();

    }

    private void intentRequest(Intent intent, Intent intentDefaultPage) {

        if (intent != null) {
            boolean isLogged = intent.getBooleanExtra(Costants.EXTRA_LOGIN_SUCCESS, false);
            boolean isLogout = intent.getBooleanExtra(Costants.EXTRA_LOGOUT_SUCCESS, false);

            if (isLogged) {
                Snackbar.make(findViewById(R.id.main_container),
                        R.string.text_login_success, Snackbar.LENGTH_LONG).show();

            } else if (isLogout) {
                Snackbar.make(findViewById(R.id.main_container),
                        R.string.text_logout_success, Snackbar.LENGTH_LONG).show();

            }

            int restore = intent.getIntExtra(Costants.EXTRA_RESTORE_MANAGE, 0);

            if (restore == Costants.RESTORE_MANAGE_RESTORE) {
                PrefManager.putIntPref(mContext, R.string.pref_restore_manage,
                        Costants.RESTORE_MANAGE_RESTORE);
                startActivity(new Intent(this, SubManageActivity.class)
                        .putExtra(Costants.EXTRA_RESTORE_MANAGE, Costants.RESTORE_MANAGE_RESTORE));

            } else if (restore == Costants.RESTORE_MANAGE_REDIRECT) {
                startActivity(new Intent(this, SubManageActivity.class)
                        .putExtra(Costants.EXTRA_RESTORE_MANAGE, Costants.RESTORE_MANAGE_REDIRECT));
            } else {
//                startActivity(intentDefaultPage);
            }

        }
    }

    private Intent intentDefaultCategoryPopular() {
        Intent intent = new Intent(getApplication(), SubRedditActivity.class);
        intent.putExtra(Costants.EXTRA_SUBREDDIT_CATEGORY, Costants.SUBREDDIT_CATEGORY_POPULAR);
        intent.putExtra(Costants.EXTRA_SUBREDDIT_TARGET, Costants.SUBREDDIT_TARGET_POPULAR);
        return intent;
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

            int redditSessionExpired = getRedditSessionExpired(getApplicationContext());
            if (redditSessionExpired <= Costants.SESSION_TIMEOUT_DEFAULT) {

                String strRefreshToken = PrefManager.getStringPref(getApplicationContext(),
                        R.string.pref_session_refresh_token);
                new RefreshTokenExecute(strRefreshToken).syncData(getApplicationContext());

            } else {

                FirebaseRefreshTokenSync.initialize(this, redditSessionExpired);
            }
        }
    }

    private void getDataT5(Context context) {
        if (context != null) {
            new RestExecute().syncData(context);
        }
    }

}
