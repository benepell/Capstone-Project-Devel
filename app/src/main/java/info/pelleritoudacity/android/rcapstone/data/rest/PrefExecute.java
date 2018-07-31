package info.pelleritoudacity.android.rcapstone.data.rest;


import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrefExecute {

    private final OnRestCallBack mCallBack;
    private final String mCode;
    private final String mId;
    private static RedditAPI sApi;


    public PrefExecute(OnRestCallBack callback, String code, String id) {

        sApi = RetrofitClient.createService(Costant.REDDIT_OAUTH_URL, null);

        mCallBack = callback;
        mCode = code;
        mId = id;

    }

    public void postSaveData() {

        sApi.postPrefSave(TextUtil.authCode(mCode) , mId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        mCallBack.success(response.code());
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        mCallBack.unexpectedError(t);
                    }
                });
    }

    public void postUnSaveData() {
        sApi.postPrefUnSave(TextUtil.authCode(mCode), mId)
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                        mCallBack.success(response.code());
                    }

                    @Override
                    public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                        call.cancel();
                        if (call.isCanceled()) {
                            mCallBack.unexpectedError(t);
                        }
                    }
                });
    }

    public interface OnRestCallBack {

        void success(int code);

        void unexpectedError(Throwable tList);
    }

}
