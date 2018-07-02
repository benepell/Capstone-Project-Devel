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

import android.util.Base64;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditToken;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RefreshTokenManager {

    private static RedditAPI sRefreshTokenAPI;
    private static RefreshTokenManager sRefreshTokenManager;
    private Call<RedditToken> mCall;


    final HashMap<String, String> headerMap;
    final HashMap<String, String> fieldMap;


    private RefreshTokenManager(String refreshToken) {

        String authString = Costant.REDDIT_CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);

        headerMap = new HashMap<>();
        headerMap.put("Authorization", "Basic " + encodedAuthString);

        fieldMap = new HashMap<>();
        fieldMap.put("grant_type", "refresh_token");
        fieldMap.put("refresh_token", refreshToken);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costant.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costant.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costant.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Costant.REDDIT_TOKEN_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sRefreshTokenAPI = retrofit.create(RedditAPI.class);

    }

    public static RefreshTokenManager getInstance(String refreshToken) {
        if (sRefreshTokenManager != null) {
            sRefreshTokenManager.cancelRequest();
        }
        sRefreshTokenManager = new RefreshTokenManager(refreshToken);

        return sRefreshTokenManager;
    }

    public void getLoginAPI(Callback<RedditToken> callback) {
        mCall = sRefreshTokenAPI.getAccessToken(headerMap, fieldMap);
        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
