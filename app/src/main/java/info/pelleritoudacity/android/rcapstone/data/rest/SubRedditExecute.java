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

import java.lang.ref.WeakReference;
import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.T3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SubRedditExecute {
    private final SubRedditManager subRedditManager;
    private T3 mReddit;
    private List<T3> mRedditList;

    public SubRedditExecute(Context context, String category, String target) {
        subRedditManager = SubRedditManager.getInstance(new WeakReference<>(context), category, target);
    }


    public void getData(OnRestSubReddit myCallBack) {
        Callback<T3> callback = new Callback<T3>() {
            @Override
            public void onResponse(@NonNull Call<T3> call, @NonNull Response<T3> response) {
                if (response.isSuccessful()) {
                    mReddit = response.body();
                    myCallBack.onRestSubReddit(mReddit);
                }
            }

            @Override
            public void onFailure(@NonNull Call<T3> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorSubReddit(t);
                }
            }
        };
        subRedditManager.getSubRedditAPI(callback);
    }

    public void getDataList(OnRestSubRedditList myCallBack) {
        Callback<List<T3>> callback = new Callback<List<T3>>() {
            @Override
            public void onResponse(@NonNull Call<List<T3>> call, @NonNull Response<List<T3>> response) {
                if (response.isSuccessful()) {
                    mRedditList = response.body();
                    myCallBack.onRestSubReddit(mRedditList);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<T3>> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorSubReddit(t);
                }
            }
        };
        subRedditManager.getSortSubRedditAPI(callback);
    }


    public void cancelRequest() {
        if (subRedditManager != null) {
            subRedditManager.cancelRequest();
        }
    }


    public interface OnRestSubRedditList {

        void onRestSubReddit(List<T3> listenerDataList);

        void onErrorSubReddit(Throwable tList);
    }

    public interface OnRestSubReddit {

        void onRestSubReddit(T3 listenerData);

        void onErrorSubReddit(Throwable t);
    }
}
