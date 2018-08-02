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

import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditAboutMe;
import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeExecute {
    private final OnRestCallBack mCallback;
    private static RedditAPI sApi;
    private final String mCode;

    public AboutMeExecute(OnRestCallBack callback, String code) {
        sApi = RetrofitClient.createService(Costant.REDDIT_OAUTH_URL,null);
        mCallback = callback;
        mCode = code;

    }

    public void loginData() {
        sApi.getAboutMe(TextUtil.authCode(mCode)).enqueue(new Callback<RedditAboutMe>() {
            @Override
            public void onResponse(@NonNull Call<RedditAboutMe> call, @NonNull Response<RedditAboutMe> response) {
                mCallback.success(response.body(),response.code());
            }

            @Override
            public void onFailure(@NonNull Call<RedditAboutMe> call, @NonNull Throwable t) {
                mCallback.unexpectedError(t);
            }
        });
    }

    public interface OnRestCallBack {

        void success(RedditAboutMe response , int code);

        @SuppressWarnings("EmptyMethod")
        void unexpectedError(Throwable tList);
    }

}
