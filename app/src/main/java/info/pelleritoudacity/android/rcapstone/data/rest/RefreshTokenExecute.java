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
import android.util.Base64;

import java.util.HashMap;
import java.util.Objects;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditToken;
import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RefreshTokenExecute {
    private RedditToken mRedditToken;
    private static RedditAPI sApi;
    private final String mRefreshToken;
    private Call<RedditToken> mCall;

    public RefreshTokenExecute(String refreshToken) {
        sApi = RetrofitClient.createService(Costant.REDDIT_TOKEN_URL,null);
        mRefreshToken = refreshToken;
    }

    public void syncData(final Context context) {

        String authString = Costant.REDDIT_CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);

        HashMap<String, String> headerMap;
        HashMap<String, String> fieldMap;
        headerMap = new HashMap<>();
        headerMap.put("Authorization", "Basic " + encodedAuthString);

        fieldMap = new HashMap<>();
        fieldMap.put("grant_type", "refresh_token");
        fieldMap.put("refresh_token", mRefreshToken);

        sApi.getAccessToken(headerMap, fieldMap).enqueue(new Callback<RedditToken>() {
            @Override
            public void onResponse(@NonNull Call<RedditToken> call, @NonNull Response<RedditToken> response) {
                if (response.isSuccessful()) {
                    mCall = call;
                    mRedditToken = response.body();
                    String strAccessToken = Objects.requireNonNull(mRedditToken).getAccess_token();
                    String strRefreshToken = mRedditToken.getRefresh_token();
                    long expired = mRedditToken.getExpires_in();

                    if (!TextUtils.isEmpty(strAccessToken) && expired > 0) {
                        PermissionUtil.setToken(context, strAccessToken);

                        if (!TextUtils.isEmpty(strRefreshToken)) {
                            Preference.setSessionRefreshToken(context, strRefreshToken);
                        }

                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<RedditToken> call, @NonNull Throwable t) {
            }
        });

    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
