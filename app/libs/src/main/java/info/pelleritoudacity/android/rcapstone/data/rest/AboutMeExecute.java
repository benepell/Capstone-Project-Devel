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
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AboutMeExecute {
    private final AboutMeManager aboutMeManager;
    private RedditAboutMe mLogin;

    public AboutMeExecute(String code) {
        aboutMeManager = AboutMeManager.getInstance(code);
    }

    public void loginData(final RestAboutMe myCallBack) {
        Callback<RedditAboutMe> callback = new Callback<RedditAboutMe>() {
            @Override
            public void onResponse(@NonNull Call<RedditAboutMe> call, @NonNull Response<RedditAboutMe> response) {
                if (response.isSuccessful()) {
                    mLogin = response.body();
                    myCallBack.onRestAboutMe(mLogin);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RedditAboutMe> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorAboutMe(t);
                }
            }
        };
        aboutMeManager.getAboutMeAPI(callback);
    }

    public interface RestAboutMe {

        void onRestAboutMe(RedditAboutMe listenerData);

        void onErrorAboutMe(Throwable t);
    }
}
