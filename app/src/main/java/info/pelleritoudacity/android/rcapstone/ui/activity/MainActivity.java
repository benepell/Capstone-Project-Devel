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
import android.text.TextUtils;

import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.BuildConfig;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.rest.RefreshTokenExecute;
import info.pelleritoudacity.android.rcapstone.rest.RestExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

import static info.pelleritoudacity.android.rcapstone.utility.SessionUtil.getRedditSessionExpired;

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

        mContext = MainActivity.this;

        startActivity(shortcutLancherIntent(mContext, getIntent().getAction()));

        initializeFirebaseDispatcherService();
        // todo remove
        getDataT5(getApplicationContext());

        mSwipeRefreshLayout.setOnRefreshListener(this);

        dataClearSnackBar();

        String resumeCategory = Preference.getLastCategory(mContext);
        if (TextUtils.isEmpty(resumeCategory)) {
            resumeCategory = getTabArrayList().get(0);
        }

        intentRequest(getIntent(), intentDefaultCategory(resumeCategory));

        Preference.setVolumeMuted(mContext, Costant.IS_MUTED_AUDIO);

        Timber.d("VALUE %s", BuildConfig.APPLICATION_ID);

    }


    private Intent shortcutLancherIntent(Context context, String intentAction) {

        if ((context != null) && (Util.SDK_INT > 24) && (!TextUtils.isEmpty(intentAction))) {

            if (intentAction.equals(context.getResources().getString(R.string.shortcut_intent_action_popular))) {
                Intent intent = new Intent(context, SubRedditActivity.class);
                intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, Costant.SUBREDDIT_CATEGORY_POPULAR);
                intent.putExtra(Costant.EXTRA_SUBREDDIT_TARGET, Costant.SUBREDDIT_TARGET_POPULAR);
                return intent;

            } else if (intentAction.equals(context.getResources().getString(R.string.shortcut_intent_action_all))) {
                Intent intent = new Intent(context, SubRedditActivity.class);
                intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, Costant.SUBREDDIT_CATEGORY_ALL);
                intent.putExtra(Costant.EXTRA_SUBREDDIT_TARGET, Costant.SUBREDDIT_TARGET_ALL);
                return intent;


            } else if (intentAction.equals(context.getResources().getString(R.string.shortcut_intent_action_search))) {


            }

        }

        return new Intent(context, MainActivity.class);

    }


    @Override
    public void onRefresh() {
        if (NetworkUtil.isOnline(getApplicationContext())) {
            getDataT5(getApplicationContext());
        } else {
            mIsRefreshing = false;
            Snackbar.make(findViewById(R.id.main_container), R.string.list_snackbar_offline_text, Snackbar.LENGTH_LONG).show();
        }
        updateRefreshingUI();

    }

    private void intentRequest(Intent intent, Intent intentDefaultPage) {

        if (intent != null) {
            boolean isLogged = intent.getBooleanExtra(Costant.EXTRA_LOGIN_SUCCESS, false);
            boolean isLogout = intent.getBooleanExtra(Costant.EXTRA_LOGOUT_SUCCESS, false);

            if (isLogged) {
                Snackbar.make(findViewById(R.id.main_container),
                        R.string.text_login_success, Snackbar.LENGTH_LONG).show();

            } else if (isLogout) {
                Snackbar.make(findViewById(R.id.main_container),
                        R.string.text_logout_success, Snackbar.LENGTH_LONG).show();

            }

            int restore = intent.getIntExtra(Costant.EXTRA_RESTORE_MANAGE, 0);

            switch (restore) {
                case Costant.RESTORE_MANAGE_RESTORE:
                    Preference.setRestoreManage(mContext,
                            Costant.RESTORE_MANAGE_RESTORE);
                    startActivity(new Intent(this, SubManageActivity.class)
                            .putExtra(Costant.EXTRA_RESTORE_MANAGE, Costant.RESTORE_MANAGE_RESTORE));

                    break;
                case Costant.RESTORE_MANAGE_REDIRECT:
                    startActivity(new Intent(this, SubManageActivity.class)
                            .putExtra(Costant.EXTRA_RESTORE_MANAGE, Costant.RESTORE_MANAGE_REDIRECT));
                    break;
                default:
                    startActivity(intentDefaultPage);

            }

        }
    }

    private Intent intentDefaultCategory(String category) {
        Intent intent = new Intent(getApplication(), SubRedditActivity.class);
        if (getTabArrayList() != null) {
            intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, category);
            intent.putExtra(Costant.EXTRA_SUBREDDIT_TARGET, Costant.SUBREDDIT_TARGET_POPULAR);


        } else {
            intent.putExtra(Costant.EXTRA_SUBREDDIT_CATEGORY, Costant.SUBREDDIT_CATEGORY_POPULAR);
            intent.putExtra(Costant.EXTRA_SUBREDDIT_TARGET, Costant.SUBREDDIT_TARGET_POPULAR);

        }
        return intent;
    }

    private void updateRefreshingUI() {
        mSwipeRefreshLayout.setRefreshing(mIsRefreshing);
    }

    public static void homeActivity(Context context) {
        context.startActivity(new Intent(context, MainActivity.class)
                .setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NO_ANIMATION
                        | Intent.FLAG_ACTIVITY_NO_HISTORY));
    }


    private void dataClearSnackBar() {
        if (Preference.isClearData(mContext)) {
            Snackbar.make(findViewById(R.id.main_container), R.string.text_dialog_confirm_reset, Snackbar.LENGTH_LONG).show();
            Preference.setClearData(mContext,false);
        }
    }

    private void initializeFirebaseDispatcherService() {

        if (Preference.isLoginStart(mContext)) {

            int redditSessionExpired = getRedditSessionExpired(getApplicationContext());
            if (redditSessionExpired <= Costant.SESSION_TIMEOUT_DEFAULT) {

                String strRefreshToken = Preference.getSessionRefreshToken(mContext);
                new RefreshTokenExecute(strRefreshToken).syncData(getApplicationContext());

            } else {

                FirebaseRefreshTokenSync.initialize(this, redditSessionExpired);
            }
        }
    }

    private void getDataT5(Context context) {
        if (context != null) {
            new RestExecute(context).syncData();
        }
    }

}
