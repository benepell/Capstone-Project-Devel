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

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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

        String strTimeSort = Preference.getTimeSort(mContext);

        mFieldMap = new HashMap<>();
        mFieldMap.put("limit", Costant.LIMIT_REDDIT_RESULTS);
        mFieldMap.put("showedits", "false");
        mFieldMap.put("showmore", "true");
        if(!TextUtils.isEmpty(strTimeSort)){
            mFieldMap.put("t",strTimeSort );

        }

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costant.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costant.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costant.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();


       String baseUrl = Costant.REDDIT_BASE_URL;
       if(PermissionUtil.isLogged(mContext)){
           baseUrl = Costant.REDDIT_OAUTH_URL;
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
        if(PermissionUtil.isLogged(mContext)){
            mCall = sSubRedditAPI.getSubRedditAuth(Costant.REDDIT_BEARER + PermissionUtil.getToken(mContext),
                    mSubReddit,
                    Preference.getSubredditSort(mContext),
                    mFieldMap);

        }else {
            mCall = sSubRedditAPI.getSubReddit(mSubReddit, Preference.getSubredditSort(mContext),mFieldMap);

        }
        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
