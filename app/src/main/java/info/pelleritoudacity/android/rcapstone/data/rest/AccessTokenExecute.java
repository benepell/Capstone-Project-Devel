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

import android.support.annotation.NonNull;
import android.util.Base64;

import java.util.HashMap;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditToken;
import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessTokenExecute {

    private final OnRestCallBack mCallback;
    private static RedditAPI sApi;
    private final String mCode;

    public AccessTokenExecute(OnRestCallBack callback, String code) {

        sApi = RetrofitClient.createService(Costant.REDDIT_TOKEN_URL,null);
        mCallback = callback;
        mCode = code;

    }

    public void loginData() {

        String authString = Costant.REDDIT_CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);

        HashMap<String, String> headerMap;
        HashMap<String, String> fieldMap;

        headerMap = new HashMap<>();
        headerMap.put("Authorization", "Basic " + encodedAuthString);

        fieldMap = new HashMap<>();
        fieldMap.put("grant_type", "authorization_code");
        fieldMap.put("User-Agent", Costant.REDDIT_USER_AGENT);
        fieldMap.put("code", mCode);
        fieldMap.put("redirect_uri", Costant.REDDIT_REDIRECT_URL);

        sApi.getAccessToken(headerMap, fieldMap).enqueue(new Callback<RedditToken>() {
            @Override
            public void onResponse(@NonNull Call<RedditToken> call, @NonNull Response<RedditToken> response) {
                if (response.isSuccessful()) {
                    mCallback.success(response.body(),response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<RedditToken> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    mCallback.unexpectedError(t);
                }
            }
        });
    }

    public interface OnRestCallBack {

        @SuppressWarnings("unused")
        void success(RedditToken response, int code);

        void unexpectedError(@SuppressWarnings("unused") Throwable tList);
    }

}
