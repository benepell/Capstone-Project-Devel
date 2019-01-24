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
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.bumptech.glide.Glide;

import java.util.Objects;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.lifecycle.ViewModelProviders;
import butterknife.BindView;
import butterknife.ButterKnife;
import info.pelleritoudacity.android.rcapstone.BuildConfig;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.db.AppDatabase;
import info.pelleritoudacity.android.rcapstone.data.db.AppExecutors;
import info.pelleritoudacity.android.rcapstone.data.db.operation.T5Operation;
import info.pelleritoudacity.android.rcapstone.data.db.util.DataUtils;
import info.pelleritoudacity.android.rcapstone.data.db.viewmodel.MainViewModel;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditToken;
import info.pelleritoudacity.android.rcapstone.data.rest.AccessTokenExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.MineExecute;
import info.pelleritoudacity.android.rcapstone.data.rest.RevokeTokenExecute;
import info.pelleritoudacity.android.rcapstone.service.FirebaseRefreshTokenSync;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

public class LoginActivity extends AppCompatActivity {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.login_webview)
    protected WebView mWebview;

    private Toast mToast;
    private AppDatabase mDb;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppThemeDark);
        }
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_login);
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        ButterKnife.bind(this);

        mDb = AppDatabase.getInstance(getApplicationContext());

        if (!Preference.isLoginStart(getApplicationContext())) {
            if (TextUtils.isEmpty(loginUrl()) || TextUtils.isEmpty(Costant.REDDIT_CLIENT_ID) ||
                    TextUtils.isEmpty(Costant.REDDIT_STATE_RANDOM)) {

                setTitle("ERROR REDDIT INIT EMPTY");

            } else if (NetworkUtil.isOnline(getApplicationContext())) {
                createLogin(loginUrl());

            }

        } else {
            logout();

        }

    }

    @SuppressWarnings("ConstantConditions")
    private String loginUrl() {

        Uri.Builder builder = new Uri.Builder();

        String auth;

        if (TextUtils.isEmpty(Costant.REDDIT_ABOUT_URL)) {
            return "";

        } else {
            auth = Costant.REDDIT_ABOUT_URL;

        }


        if (auth.contains("http://")) {
            auth = auth.replace("http://", "");

        } else if (auth.contains("https://")) {
            auth = auth.replace("https://", "");

        }

        builder
                .scheme("https")
                .authority(Costant.REDDIT_AUTH_URL)
                .appendPath("api")
                .appendPath("v1")
                .appendPath("authorize.compact")
                .appendQueryParameter("client_id", Costant.REDDIT_CLIENT_ID)
                .appendQueryParameter("response_type", "code")
                .appendQueryParameter("state", Costant.REDDIT_STATE_RANDOM)

                .appendQueryParameter("redirect_uri",
                        new Uri.Builder()

                                .scheme("http")
                                .authority(auth)
                                .appendPath("my_redirect").build().toString()
                )

                .appendQueryParameter("duration", "permanent")
                .appendQueryParameter("scope", Costant.PERMISSION_STATE_REDDIT);

        return builder.build().toString();
    }

    private void createLogin(String url) {

        mWebview.clearCache(true);
        mWebview.clearHistory();
        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

                if (url.equals("https://" + Costant.REDDIT_ABOUT_URL + "/") ||
                        url.equals("https://" + Costant.REDDIT_ABOUT_URL + "/.compact")) {

                    mWebview.stopLoading();
                    mToast = Toast.makeText(getApplicationContext(), "Designed by Benedetto Pellerito", Toast.LENGTH_LONG);
                    mToast.show();

                } else if (url.contains("https://www.reddit.com/.compact") ||
                        url.equals("http://" + Costant.REDDIT_ABOUT_URL + "/.compact")) {

                    mWebview.stopLoading();
                    mToast = Toast.makeText(getApplicationContext(), "Reddit Site", Toast.LENGTH_LONG);
                    mToast.show();

                } else if (url.contains("code=")) {

                    Uri uri = Uri.parse(url);

                    if (Objects.requireNonNull(uri).getQueryParameter("error") != null) {
                        String error = uri.getQueryParameter("error");
                        Timber.e("An error has occurred :%s ", error);

                    } else {

                        String state = uri.getQueryParameter("state");
                        if (Objects.requireNonNull(state).equals(Costant.REDDIT_STATE_RANDOM)) {

                            String code = uri.getQueryParameter("code");
                            new AccessTokenExecute(new AccessTokenExecute.OnRestCallBack() {
                                @Override
                                public void success(RedditToken response, int code) {
                                    if (response != null) {
                                        String strAccessToken = response.getAccess_token();
                                        String strRefreshToken = response.getRefresh_token();

                                        if (!TextUtils.isEmpty(strAccessToken) && !TextUtils.isEmpty(strRefreshToken)) {
                                            PermissionUtil.setToken(getApplicationContext(), strAccessToken);
                                            Preference.setSessionRefreshToken(getApplicationContext(), strRefreshToken);
                                            Preference.setLoginStart(getApplicationContext(), true);

                                            updateSubredditLogin(getApplicationContext());

                                        }

                                    }

                                }

                                @Override
                                public void unexpectedError(Throwable tList) {
                                    responseActivity(Costant.PROCESS_LOGIN_ERROR);
                                }
                            }, code).loginData();
                        }
                    }
                    mWebview.stopLoading();
                    mWebview.setVisibility(View.GONE);

                }
            }
        });
        mWebview.loadUrl(url);
    }

    private void responseActivity(int state) {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Costant.EXTRA_LOGIN_SUCCESS, state);
        startActivity(intent);
    }

    private void logout() {

        FirebaseRefreshTokenSync.stopLogin(getApplicationContext());

        String token = Preference.getSessionAccessToken(getApplicationContext());
        if (NetworkUtil.isOnline(getApplicationContext())) {

            new RevokeTokenExecute(new RevokeTokenExecute.OnRestCallBack() {
                @Override
                public void success(String response, int code) {
                    Timber.d("Reset  code:%s", code);
                }

                @Override
                public void unexpectedError(Throwable tList) {
                    Timber.e("Reset Error %s", tList.getMessage());
                }
            }, token).RevokeTokenData();

        }

        Preference.clearGeneralSettings(getApplicationContext());
        Preference.clearAll(getApplicationContext());
        new DataUtils(getApplicationContext(), mDb).clearDataPrivacy();

        AppExecutors.getInstance().diskIO().execute(() -> {
            if (getApplicationContext() != null) {
                Glide.get(getApplicationContext()).clearDiskCache();

            }

        });


        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Costant.EXTRA_LOGIN_SUCCESS, Costant.PROCESS_LOGOUT_OK);
        startActivity(intent);


    }

    @Override
    public void onBackPressed() {
        if (mToast != null) {
            mToast.cancel();
        }
        super.onBackPressed();

    }

    private void updateSubredditLogin(Context context) {
        if (PermissionUtil.isLogged(context)) {
            new MineExecute((response, code) -> {
                T5Operation data = new T5Operation(context, mDb, response);
                data.saveData();

                String pref = restorePrefFromDb();

                if (!TextUtils.isEmpty(pref)) {
                    Preference.setSubredditKey(context, pref);

                }

                responseActivity(Costant.PROCESS_LOGIN_OK);

            }, context, PermissionUtil.getToken(context),
                    Costant.MINE_WHERE_SUBSCRIBER).getMine();

        }
    }

    private String restorePrefFromDb() {
        final String[] stringPref = {""};

        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getPrefSubRedditRecords().observe(this, prefSubRedditEntries -> {
            for (int j = 0; j < Objects.requireNonNull(prefSubRedditEntries).size(); j++) {
                if (TextUtils.isEmpty(prefSubRedditEntries.get(j).getName())) {
                    if (j == prefSubRedditEntries.size() - 1) {
                        stringPref[0] += prefSubRedditEntries.get(j).getName();

                    } else {
                        stringPref[0] += prefSubRedditEntries.get(j).getName() + Costant.STRING_SEPARATOR;

                    }

                }
            }
        });

        Preference.setSubredditKey(getApplicationContext(), stringPref[0]);

        return stringPref[0];

    }


}

