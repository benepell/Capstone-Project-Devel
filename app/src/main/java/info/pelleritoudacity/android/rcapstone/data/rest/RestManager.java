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

import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.T5;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestManager {

    private static RedditAPI sRestAPI;
    private static RestManager sRestManager;
    private Context mContext;
    private Call<T5> mCall;

    private RestManager(Context context) {

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costant.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costant.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costant.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Costant.REDDIT_BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        mContext = context;
        sRestAPI = retrofit.create(RedditAPI.class);

    }

    public static RestManager getInstance(Context context) {
        if (sRestManager != null) {
            sRestManager.cancelRequest();
        }
        sRestManager = new RestManager(context);
        return sRestManager;
    }

    public void getRedditAPI(Callback<T5> callback) {
        mCall = sRestAPI.getReddit();
        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}