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

import info.pelleritoudacity.android.rcapstone.data.model.reddit.RedditToken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccessTokenExecute {
    private final AccessTokenManager accessTokenManager;
    private RedditToken mLogin;

    public AccessTokenExecute(String code) {
        accessTokenManager = AccessTokenManager.getInstance(code);
    }

    public void loginData(final RestAccessToken myCallBack) {
        Callback<RedditToken> callback = new Callback<RedditToken>() {
            @Override
            public void onResponse(@NonNull Call<RedditToken> call, @NonNull Response<RedditToken> response) {
                if (response.isSuccessful()) {
                    mLogin = response.body();
                    myCallBack.onRestAccessToken(mLogin);
                }
            }

            @Override
            public void onFailure(@NonNull Call<RedditToken> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorAccessToken(t);
                }
            }
        };
        accessTokenManager.getLoginAPI(callback);
    }

    public interface RestAccessToken {
        void onRestAccessToken(RedditToken listenerData);

        void onErrorAccessToken(Throwable t);
    }
}
