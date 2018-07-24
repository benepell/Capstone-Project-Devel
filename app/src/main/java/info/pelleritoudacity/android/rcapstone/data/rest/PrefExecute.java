package info.pelleritoudacity.android.rcapstone.data.rest;


import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrefExecute {
    private final PrefManager mPrefManager;
    private ResponseBody mResponseBody;

    public PrefExecute(String code, String id) {
        mPrefManager = PrefManager.getInstance(code, id);

    }

    public void postSaveData(final RestAccessToken myCallBack) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                myCallBack.onRestPref(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorPref(t);
                }
            }
        };
        mPrefManager.getPrefSaveAPI(callback);
    }
	
	public void postUnSaveData(final RestAccessToken myCallBack) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                myCallBack.onRestPref(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorPref(t);
                }
            }
        };
        mPrefManager.getPrefUnSaveAPI(callback);
    }

    public interface RestAccessToken {
        void onRestPref(int responseCode);

        void onErrorPref(Throwable t);
    }
}
