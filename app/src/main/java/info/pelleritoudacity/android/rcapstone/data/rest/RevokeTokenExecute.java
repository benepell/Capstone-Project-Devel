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
import android.util.Base64;

import java.util.HashMap;

import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RevokeTokenExecute {

    private static RedditAPI sApi;
    private final String mToken;
    private final OnRestCallBack mCallback;


    public RevokeTokenExecute(OnRestCallBack callback, String token) {

        mCallback = callback;
        sApi = RetrofitClient.createService(Costant.REDDIT_TOKEN_URL, null);
        mToken = token;

    }

    public void RevokeTokenData() {

        String authString = Costant.REDDIT_CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);

        HashMap<String, String> headerMap;
        HashMap<String, String> fieldMap;

        headerMap = new HashMap<>();
        headerMap.put("Authorization", "Basic " + encodedAuthString);

        fieldMap = new HashMap<>();
        fieldMap.put("token",Costant.REDDIT_BEARER+ mToken);
        fieldMap.put("grant_type", "authorization_code");
        fieldMap.put("User-Agent", Costant.REDDIT_USER_AGENT);
        fieldMap.put("redirect_uri", Costant.REDDIT_REDIRECT_URL);


        sApi.getRevokeToken(headerMap,fieldMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    mCallback.success(response.code());
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                mCallback.unexpectedError(t);
                Timber.e("Revoke Token Error %s", t.getMessage());
                call.cancel();
            }
        });
    }


    public void RevokeTokenSync(final Context context) {

        String authString = Costant.REDDIT_CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);

        HashMap<String, String> headerMap;
        HashMap<String, String> fieldMap;

        headerMap = new HashMap<>();
        headerMap.put("Authorization", "Basic " + encodedAuthString);

        fieldMap = new HashMap<>();
        fieldMap.put("token", TextUtil.authCode( mToken));
        fieldMap.put("grant_type", "authorization_code");
        fieldMap.put("User-Agent", Costant.REDDIT_USER_AGENT);
        fieldMap.put("redirect_uri", Costant.REDDIT_REDIRECT_URL);


        sApi.getRevokeToken(headerMap,fieldMap).enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if (response.code() == Costant.REDDIT_REVOKE_SUCCESS) {
                        Preference.setTimeToken(context, 0);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    Timber.e("Sync Revoke Token Error %s", t.getMessage());
                }
            }
        });
    }

    public interface OnRestCallBack {

        void success(int code);

        void unexpectedError(Throwable tList);
    }


}
