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

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.Objects;

import butterknife.BindView;
import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditToken;
import info.pelleritoudacity.android.rcapstone.data.rest.AccessTokenExecute;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.NetworkUtil;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import timber.log.Timber;

public class LoginActivity extends BaseActivity {

    @SuppressWarnings({"WeakerAccess", "CanBeFinal", "unused"})
    @BindView(R.id.login_webview)
    protected WebView mWebview;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        setLayoutResource(R.layout.activity_login);
        super.onCreate(savedInstanceState);

        if ((NetworkUtil.isOnline(getApplicationContext())) &&
                (!Preference.isLoginStart(getApplicationContext()))) {

            createWebview(loadUrl());

        } else {
            openHomeActivity(false);
        }

    }

    private String loadUrl() {

        Uri.Builder builder = new Uri.Builder();

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
                                .authority(Costant.REDDIT_ABOUT_URL)
                                .appendPath("my_redirect").build().toString()
                )

                .appendQueryParameter("duration", "permanent")
                .appendQueryParameter("scope", Costant.PERMISSION_STATE_REDDIT);

        return builder.build().toString();
    }

    private void createWebview(String url) {

        mWebview.clearCache(true);
        mWebview.clearHistory();

        CookieManager.getInstance().removeAllCookies(null);
        CookieManager.getInstance().flush();

        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                if (url.contains("code=")) {
                    Uri uri = Uri.parse(url);

                    if (Objects.requireNonNull(uri).getQueryParameter("error") != null) {
                        String error = uri.getQueryParameter("error");
                        Timber.e("An error has occurred :%s ", error);

                    } else {

                        String state = uri.getQueryParameter("state");
                        if (state.equals(Costant.REDDIT_STATE_RANDOM)) {

                            String code = uri.getQueryParameter("code");
                            new AccessTokenExecute(new AccessTokenExecute.RestAccessToken() {
                                @Override
                                public void onRestAccessToken(RedditToken listenerData) {
                                    if (listenerData != null) {
                                        String strAccessToken = listenerData.getAccess_token();
                                        String strRefreshToken = listenerData.getRefresh_token();
                                        long expired = listenerData.getExpires_in();

                                        if (!TextUtils.isEmpty(strAccessToken) && !TextUtils.isEmpty(strRefreshToken)) {
                                            PermissionUtil.setToken(getApplicationContext(), strAccessToken);
                                            Preference.setSessionRefreshToken(getApplicationContext(), strRefreshToken);
                                            Preference.setSessionExpired(getApplicationContext(), (int) expired);
                                            Preference.setTimeToken(getApplicationContext(), System.currentTimeMillis());
                                            Preference.setLoginStart(getApplicationContext(), true);
                                            openHomeActivity(true);
                                        }
                                    }

                                }

                                @Override
                                public void onErrorAccessToken(Throwable t) {
                                    Timber.e("Error Login %s", t.getMessage());

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

    private void openHomeActivity(boolean success) {
        Intent intent = new Intent(this, SubRedditActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra(Costant.EXTRA_LOGIN_SUCCESS, success);
        startActivity(intent);
    }
}

