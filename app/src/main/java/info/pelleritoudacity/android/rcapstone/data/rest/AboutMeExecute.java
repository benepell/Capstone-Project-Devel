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

import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditAboutMe;
import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeExecute {
    private final RestAboutMe mCallback;
    private static RedditAPI sApi;
    private final String mCode;

    public AboutMeExecute(RestAboutMe callback, String code) {
        sApi = RetrofitClient.createService(Costant.REDDIT_OAUTH_URL,null);
        mCallback = callback;
        mCode = code;

    }

    public void loginData() {
        sApi.getAboutMe(TextUtil.authCode(mCode)).enqueue(new Callback<RedditAboutMe>() {
            @Override
            public void onResponse(Call<RedditAboutMe> call, Response<RedditAboutMe> response) {
                mCallback.onRestAboutMe(response.body());
            }

            @Override
            public void onFailure(Call<RedditAboutMe> call, Throwable t) {
                mCallback.onErrorAboutMe(t);
            }
        });
    }

    public interface RestAboutMe {

        void onRestAboutMe(RedditAboutMe listenerData);

        void onErrorAboutMe(Throwable t);
    }
}
