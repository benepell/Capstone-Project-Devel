package info.pelleritoudacity.android.rcapstone.data.rest;

import android.content.Context;
import android.support.annotation.NonNull;


import java.util.HashMap;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.More;
import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestMoreExecute {

    private final String mCode;
    private final DetailModel model;
    private static RedditAPI sApi;
    private final OnRestCallBack mCallback;

    public RestMoreExecute(OnRestCallBack callback, Context context, String code, DetailModel model) {

        sApi = RetrofitClient.createService(Costant.REDDIT_OAUTH_URL, More.class);
        mCallback = callback;
        mCode = code;
        this.model = model;
    }

    public void getMoreData() {

        HashMap<String, String> fieldMap = new HashMap<>();
        fieldMap.put("api_type", "json");
        fieldMap.put("link_id", model.getStrLinkId());
        fieldMap.put("children", model.getStrArrId());


        sApi.getMoreCommentsAuth(TextUtil.authCode(mCode) ,
                fieldMap).enqueue(new Callback<More>() {
            @Override
            public void onResponse(@NonNull Call<More> call, @NonNull Response<More> response) {
                mCallback.success(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<More> call, @NonNull Throwable t) {
                mCallback.unexpectedError(t);
            }
        });

    }

    public interface OnRestCallBack {

        void success(More response);

        void unexpectedError(Throwable tList);
    }

}
