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
import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.utility.Costants;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RevokeTokenExecute {
    private final RevokeTokenManager revokeTokenManager;

    public RevokeTokenExecute(String token, String typeToken) {
        revokeTokenManager = RevokeTokenManager.getInstance(token, typeToken);
    }

    public void RevokeTokenData(final RestRevokeCode myCallBack) {
        Callback<String> callback = new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    myCallBack.onRestCode(response.code());
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    Timber.e("Revoke Token Error %s", t.getMessage());
                }
            }
        };
        revokeTokenManager.getLoginAPI(callback);
    }


    public void RevokeTokenSync(final Context context) {
        Callback<String> callback = new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                if (response.isSuccessful()) {
                    if(response.code()== Costants.REDDIT_REVOKE_SUCCESS){
                        Preference.setTimeToken(context,0);
                    }
                }

            }

            @Override
            public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    Timber.e("Sync Revoke Token Error %s", t.getMessage());
                }
            }
        };
        revokeTokenManager.getLoginAPI(callback);
    }


    public interface RestRevokeCode {
        void onRestCode(Integer listenerData);
    }

}
