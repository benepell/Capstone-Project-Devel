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



import androidx.annotation.NonNull;
import info.pelleritoudacity.android.rcapstone.data.model.reddit.T5;
import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


@SuppressWarnings("unused")
class CategoryExecute {

    private static RedditAPI sApi;
    private final OnRestCallBack mCallback;

    public CategoryExecute(OnRestCallBack callBack) {

        sApi = RetrofitClient.createService(Costant.REDDIT_BASE_URL, null);
        mCallback = callBack;
    }

    public void syncData() {
        sApi.getReddit().enqueue(new Callback<T5>() {
            @Override
            public void onResponse(@NonNull Call<T5> call, @NonNull Response<T5> response) {
                if (response.isSuccessful()) {
                    mCallback.success(response.body(),response.code());

                }
            }

            @Override
            public void onFailure(@NonNull Call<T5> call, @NonNull Throwable t) {
                call.cancel();
            }
        });


    }

    interface OnRestCallBack {

        void success(T5 response, @SuppressWarnings("unused") int code);

    }


}
