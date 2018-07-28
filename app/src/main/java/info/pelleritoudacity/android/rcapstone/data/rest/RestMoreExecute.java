package info.pelleritoudacity.android.rcapstone.data.rest;

import android.content.Context;
import android.support.annotation.NonNull;


import java.lang.ref.WeakReference;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.More;
import info.pelleritoudacity.android.rcapstone.data.model.ui.DetailModel;
import info.pelleritoudacity.android.rcapstone.data.rest.deserialize.RestMoreManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RestMoreExecute {
    private final RestMoreManager restMoreManager;
    private More mReddit;
    private final DetailModel model;

    public RestMoreExecute(Context context, String code , DetailModel model) {

        restMoreManager = RestMoreManager.getInstance(new WeakReference<>(context),
                code,  model);
        this.model = model;
    }

    public void getMoreData(RestSubRedditMore myCallBack) {

        Callback<More> callback = new Callback<More>() {
            @Override
            public void onResponse(@NonNull Call<More> call, @NonNull Response<More> response) {
                mReddit = response.body();
                myCallBack.onRestSubRedditMore(mReddit, model.getStrArrId());

            }

            @Override
            public void onFailure(@NonNull Call<More> call, @NonNull Throwable t) {
                myCallBack.onErrorSubRedditMore(t);

            }
        };
        restMoreManager.getMoreCommentsAPI(callback);
    }


    public void cancelRequest() {
        if (restMoreManager != null) {
            restMoreManager.cancelRequest();
        }
    }


    public interface RestSubRedditMore {
        void onRestSubRedditMore(More listenerData ,String mStrArrid);

        void onErrorSubRedditMore(Throwable t);
    }
}
