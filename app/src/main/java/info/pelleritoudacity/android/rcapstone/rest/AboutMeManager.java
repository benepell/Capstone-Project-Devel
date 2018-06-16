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

package info.pelleritoudacity.android.rcapstone.rest;

import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.model.reddit.RedditAboutMe;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AboutMeManager {

    private static RedditAPI sAboutMeAPI;
    private static AboutMeManager sAboutMeManager;
    private final String mAccessToken;
    private Call<RedditAboutMe> mCall;

    private AboutMeManager(String accessToken) {

        mAccessToken = accessToken;

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // todo remove interceptor
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costants.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costants.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costants.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Costants.REDDIT_OAUTH_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sAboutMeAPI = retrofit.create(RedditAPI.class);

    }

    public static AboutMeManager getInstance(String accessToken) {
        if (sAboutMeManager != null) {
            sAboutMeManager.cancelRequest();
        }

        sAboutMeManager = new AboutMeManager(accessToken);


        return sAboutMeManager;
    }

    public void getAboutMeAPI(Callback<RedditAboutMe> callback) {
        String headerAuth = Costants.REDDIT_BEARER + mAccessToken;
        mCall = sAboutMeAPI.getAboutMe(headerAuth);
        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
