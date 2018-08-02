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

import java.util.HashMap;
import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainExecute {
    private static RedditAPI sApi;
    private final OnRestCallBack mCallBack;
    private final boolean isAuthenticated;
    private final Context mContext;
    private final String mCategory;

    public MainExecute(OnRestCallBack callBack, Context context, String category) {

        if (PermissionUtil.isLogged(context)) {
            sApi = RetrofitClient.createService(Costant.REDDIT_OAUTH_URL, null);
            isAuthenticated = true;
        } else {
            sApi = RetrofitClient.createService(Costant.REDDIT_BASE_URL, null);
            isAuthenticated = false;
        }

        mCallBack = callBack;
        mContext = context;
        mCategory = category;

    }

    public void getData() {

        HashMap<String, String> fieldMap;
        fieldMap = new HashMap<>();
        fieldMap.put("limit", String.valueOf(Preference.getGeneralSettingsItemPage(mContext)));
        fieldMap.put("showedits", "false");
        fieldMap.put("showmore", "true");

        String strTimeSort = Preference.getTimeSort(mContext);
        if (!TextUtils.isEmpty(strTimeSort)) {
            fieldMap.put("t", strTimeSort);

        }


        if (isAuthenticated) {


            sApi.getSubRedditAuth(TextUtil.authCode(PermissionUtil.getToken(mContext)), mCategory, fieldMap)
                    .enqueue(new Callback<T3>() {
                        @Override
                        public void onResponse(@NonNull Call<T3> call, @NonNull Response<T3> response) {
                            mCallBack.success(response.body(), response.code());
                        }

                        @Override
                        public void onFailure(@NonNull Call<T3> call, @NonNull Throwable t) {
                            mCallBack.unexpectedError(t);
                        }
                    });
        } else {

            sApi.getSubReddit(mCategory, fieldMap)
                    .enqueue(new Callback<T3>() {
                        @Override
                        public void onResponse(@NonNull Call<T3> call, @NonNull Response<T3> response) {
                            mCallBack.success(response.body(), response.code());
                        }

                        @Override
                        public void onFailure(@NonNull Call<T3> call, @NonNull Throwable t) {
                            mCallBack.unexpectedError(t);
                        }
                    });


        }

    }

    public void getDataList() {

        HashMap<String, String> fieldMap;
        fieldMap = new HashMap<>();
        fieldMap.put("limit", String.valueOf(Preference.getGeneralSettingsItemPage(mContext)));
        fieldMap.put("showedits", "false");
        fieldMap.put("showmore", "true");

        String strTimeSort = Preference.getTimeSort(mContext);
        if (!TextUtils.isEmpty(strTimeSort)) {
            fieldMap.put("t", strTimeSort);

        }

        if (isAuthenticated) {
            sApi.getSortSubRedditAuth(TextUtil.authCode(PermissionUtil.getToken(mContext)),
                    mCategory,
                    Preference.getSubredditSort(mContext),
                    fieldMap).enqueue(new Callback<List<T3>>() {
                @Override
                public void onResponse(@NonNull Call<List<T3>> call, @NonNull Response<List<T3>> response) {
                    mCallBack.success(response.body(), response.code());
                }

                @Override
                public void onFailure(@NonNull Call<List<T3>> call, @NonNull Throwable t) {
                    mCallBack.unexpectedError(t);
                }
            });

        } else {

            sApi.getSortSubReddit(mCategory,
                    Preference.getSubredditSort(mContext),
                    fieldMap).enqueue(new Callback<List<T3>>() {
                @Override
                public void onResponse(Call<List<T3>> call, Response<List<T3>> response) {
                    mCallBack.success(response.body(), response.code());
                }

                @Override
                public void onFailure(Call<List<T3>> call, Throwable t) {
                    mCallBack.unexpectedError(t);
                }
            });
        }
    }

    public interface OnRestCallBack {

        @SuppressWarnings("unused")
        void success(T3 response, int code);

        void success(List<T3> response, int code);

        void unexpectedError(Throwable tList);
    }


}
