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

import android.content.Context;
import android.text.TextUtils;

import org.checkerframework.checker.units.qual.K;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.R;
import info.pelleritoudacity.android.rcapstone.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtils;
import info.pelleritoudacity.android.rcapstone.utility.PrefManager;
import info.pelleritoudacity.android.rcapstone.utility.Utility;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

public class SubRedditManager {

    private static RedditAPI sSubRedditAPI;
    private static SubRedditManager sSubRedditManager;
    private final String mSubReddit;
    private final HashMap<String, String> mFieldMap;
    private Call<T3> mCall;
    private Context mContext;
    private SubRedditManager(Context context, String subReddit) {
        mSubReddit = subReddit;
        mContext = context;

        String strTimeSort = PrefManager.getStringPref(mContext,R.string.pref_time_sort);

        mFieldMap = new HashMap<>();
        mFieldMap.put("limit", Costants.LIMIT_REDDIT_RESULTS);
        mFieldMap.put("showedits", "false");
        mFieldMap.put("showmore", "true");
        if(!TextUtils.isEmpty(strTimeSort)){
            mFieldMap.put("t",strTimeSort );

        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costants.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costants.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costants.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();


       String baseUrl = Costants.REDDIT_BASE_URL;
       if(PermissionUtils.isLogged(mContext)){
           baseUrl = Costants.REDDIT_OAUTH_URL;
       }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sSubRedditAPI = retrofit.create(RedditAPI.class);

    }

    public static SubRedditManager getInstance(Context context, String subReddit) {
        if (sSubRedditManager != null) {
            sSubRedditManager.cancelRequest();

        }

        sSubRedditManager = new SubRedditManager(context, subReddit);


        return sSubRedditManager;
    }

    public void getSubRedditAPI(Callback<T3> callback) {
        if(PermissionUtils.isLogged(mContext)){
            mCall = sSubRedditAPI.getSubRedditAuth(Costants.REDDIT_BEARER + PermissionUtils.getToken(mContext),
                    mSubReddit,
                    PrefManager.getStringPref(mContext,R.string.pref_subreddit_sort),
                    mFieldMap);

        }else {
            mCall = sSubRedditAPI.getSubReddit(mSubReddit, PrefManager.getStringPref(mContext,R.string.pref_subreddit_sort),mFieldMap);

        }
        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
