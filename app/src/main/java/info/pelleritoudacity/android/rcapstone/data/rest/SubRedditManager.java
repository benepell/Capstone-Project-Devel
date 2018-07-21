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
import android.text.TextUtils;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class SubRedditManager {

    private static RedditAPI sSubRedditAPI;
    private static SubRedditManager sSubRedditManager;
    private final String mSubReddit;
    private Call<T3> mCall;
    private Call<List<T3>> mCallList;

    private final WeakReference<Context> mWeakContext;

    private SubRedditManager(WeakReference<Context> weakContext, String subReddit) {
        mSubReddit = subReddit;
        mWeakContext = weakContext;

        Context context = mWeakContext.get();

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        // todo remove interceptor
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costant.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costant.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costant.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(httpLoggingInterceptor)
                .build();


        String baseUrl = Costant.REDDIT_BASE_URL;
        if (PermissionUtil.isLogged(context)) {
            baseUrl = Costant.REDDIT_OAUTH_URL;
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sSubRedditAPI = retrofit.create(RedditAPI.class);

    }

    public static SubRedditManager getInstance(WeakReference<Context> weakContext, String subReddit) {

        if (sSubRedditManager != null) {
            sSubRedditManager.cancelRequest();
        }
        sSubRedditManager = new SubRedditManager(weakContext, subReddit);

        return sSubRedditManager;
    }

    public void getSubRedditAPI(Callback<T3> callback) {
        Context context = mWeakContext.get();

        HashMap<String, String> fieldMap;
        fieldMap = new HashMap<>();
        fieldMap.put("limit", String.valueOf(Preference.getGeneralSettingsItemPage(context)));
        fieldMap.put("showedits", "false");
        fieldMap.put("showmore", "true");

        Timber.d("PREFERENX %s", Preference.getGeneralSettingsItemPage(context));
        if (PermissionUtil.isLogged(context)) {
            mCall = sSubRedditAPI.getSubRedditAuth(Costant.REDDIT_BEARER + PermissionUtil.getToken(context),
                    mSubReddit, fieldMap);

        } else {
            mCall = sSubRedditAPI.getSubReddit(mSubReddit, fieldMap);

        }
        mCall.enqueue(callback);
    }

    public void getSortSubRedditAPI(Callback<List<T3>> callback) {
        Context context = mWeakContext.get();

        HashMap<String, String> fieldMap;
        fieldMap = new HashMap<>();
        fieldMap.put("limit", String.valueOf(Preference.getGeneralSettingsItemPage(context)));
        fieldMap.put("showedits", "false");
        fieldMap.put("showmore", "true");

        String strTimeSort = Preference.getTimeSort(context);
        if (!TextUtils.isEmpty(strTimeSort)) {
            fieldMap.put("t", strTimeSort);

        }

        if (PermissionUtil.isLogged(context)) {
            mCallList = sSubRedditAPI.getSortSubRedditAuth(Costant.REDDIT_BEARER + PermissionUtil.getToken(context),
                    mSubReddit,
                    Preference.getSubredditSort(context),
                    fieldMap);

        } else {
            mCallList = sSubRedditAPI.getSortSubReddit(mSubReddit, Preference.getSubredditSort(context), fieldMap);

        }
        mCallList.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
