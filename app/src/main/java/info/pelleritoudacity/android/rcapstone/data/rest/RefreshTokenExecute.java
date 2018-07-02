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

package info.pelleritoudacity.android.rcapstone.data.rest;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditToken;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RefreshTokenExecute {
    private final RefreshTokenManager refreshTokenManager;
    private RedditToken mRedditToken;

    public RefreshTokenExecute(String refreshToken) {
        refreshTokenManager = RefreshTokenManager.getInstance(refreshToken);
    }

    public void loginData(final RestRefreshToken myCallBack) {
        Callback<RedditToken> callback = new Callback<RedditToken>() {
            @Override
            public void onResponse(@NonNull Call<RedditToken> call, @NonNull Response<RedditToken> response) {
                if (response.isSuccessful()) {
                    mRedditToken = response.body();
                    myCallBack.onRestRefreshToken(mRedditToken);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RedditToken> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorRefreshToken(t);
                }
            }
        };
        refreshTokenManager.getLoginAPI(callback);
    }

    public void syncData(final Context context) {
        Callback<RedditToken> callback = new Callback<RedditToken>() {
            @Override
            public void onResponse(@NonNull Call<RedditToken> call, @NonNull Response<RedditToken> response) {
                if (response.isSuccessful()) {
                    mRedditToken = response.body();
                    String strAccessToken = Objects.requireNonNull(mRedditToken).getAccess_token();
                    String strRefreshToken = mRedditToken.getRefresh_token();
                    long expired = mRedditToken.getExpires_in();
                    if (!TextUtils.isEmpty(strAccessToken) && expired > 0) {
                        PermissionUtil.setToken(context,strAccessToken);
                        if (!TextUtils.isEmpty(strRefreshToken)) {
                            Preference.setSessionRefreshToken(context,strRefreshToken);
                        }
                        Preference.setSessionExpired(context,(int)expired);
                        Preference.setTimeToken(context, System.currentTimeMillis());

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RedditToken> call, @NonNull Throwable t) {
                call.cancel();
                Timber.e("Sync T1Data Token Refresh failure %s", t.getMessage());
            }
        };
        refreshTokenManager.getLoginAPI(callback);
    }

    public void cancelRequest() {
        if (refreshTokenManager != null) {
            refreshTokenManager.cancelRequest();
        }
    }


    public interface RestRefreshToken {

        void onRestRefreshToken(RedditToken listenerData);

        void onErrorRefreshToken(Throwable t);
    }
}
