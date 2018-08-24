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
import timber.log.Timber;

public class PostExecute {

    private static RedditAPI sApi;
    private final OnRestCallBack mCallback;
    private final String mCode;
    private final String mAction;
    private final String mFullname;


    public PostExecute(OnRestCallBack callback, String code, String action,String fullname) {

        sApi = RetrofitClient.createService(Costant.REDDIT_OAUTH_URL, null);

        mCallback = callback;
        mCode = code;
        mAction = action;
        mFullname = fullname;

    }

    public void postSubcribe() {

        sApi.postSubscribe(TextUtil.authCode(mCode), mAction,
                mFullname).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                mCallback.success(response.body(), response.code());

            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                Timber.e("SUBSCRIBE MINE ERROR %s", t.getMessage());
            }
        });

    }

    public interface OnRestCallBack {

        void success(@SuppressWarnings("unused") ResponseBody response, @SuppressWarnings("unused") int code);

    }

}
