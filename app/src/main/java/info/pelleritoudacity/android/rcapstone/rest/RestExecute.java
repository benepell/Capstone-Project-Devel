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

import info.pelleritoudacity.android.rcapstone.data.T5Operation;
import info.pelleritoudacity.android.rcapstone.model.reddit.T5;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class RestExecute {

    private final RestManager restManager;
    private T5 mReddit;

    public RestExecute() {
        restManager = RestManager.getInstance();
    }

    public void loadData(final RestData myCallBack) {

        Callback<T5> callback = new Callback<T5>() {
            @Override
            public void onResponse(@NonNull Call<T5> call, @NonNull Response<T5> response) {
                mReddit = response.body();
                if (response.isSuccessful()) {
                    myCallBack.onRestData(mReddit);
                }
            }

            @Override
            public void onFailure(@NonNull Call<T5> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorData(t);
                }
            }
        };
        restManager.getRedditAPI(callback);
    }

    public void syncData(final Context context) {
        Callback<T5> callback = new Callback<T5>() {
            @Override
            public void onResponse(@NonNull Call<T5> call, @NonNull Response<T5> response) {
                mReddit = response.body();
                if (response.isSuccessful()) {
                    T5Operation data = new T5Operation(context,mReddit);
                    data.saveData();
                }
            }

            @Override
            public void onFailure(@NonNull Call<T5> call, @NonNull Throwable t) {
                call.cancel();
            }
        };
        restManager.getRedditAPI(callback);

    }

    public void cancelRequest() {
        if (restManager != null) {
            restManager.cancelRequest();
        }
    }

    public interface RestData {
        void onRestData(T5 listenerData);

        void onErrorData(Throwable t);
    }
}
