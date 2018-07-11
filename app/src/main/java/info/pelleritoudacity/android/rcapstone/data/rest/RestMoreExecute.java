package info.pelleritoudacity.android.rcapstone.data.rest;

import android.content.Context;
import android.support.annotation.NonNull;


import java.lang.ref.WeakReference;

import info.pelleritoudacity.android.rcapstone.data.rest.deserialize.RestMoreManager;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class RestMoreExecute {
    private final RestMoreManager restMoreManager;
    private More mReddit;
    private final String mStrArrid;

    public RestMoreExecute(Context context, String code, String linkId, String strArrId) {

        restMoreManager = RestMoreManager.getInstance(new WeakReference<>(context),
                code, linkId, strArrId);
        mStrArrid = strArrId;
    }

    public void getMoreData(RestSubRedditMore myCallBack) {

        Callback<More> callback = new Callback<More>() {
            @Override
            public void onResponse(@NonNull Call<More> call, @NonNull Response<More> response) {
                mReddit = response.body();
//                Timber.d("MOREXXX %s", response.body());
                myCallBack.onRestSubRedditMore(mReddit, mStrArrid);

            }

            @Override
            public void onFailure(@NonNull Call<More> call, @NonNull Throwable t) {
                Timber.e("MOREXXX ERROR %s", t.toString());

                myCallBack.onErrorSubRedditMore(t);

                /* call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorSubRedditMore(t);
                }*/
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
        void onRestSubRedditMore(More listenerData, String mStrArrid);

        void onErrorSubRedditMore(Throwable t);
    }
}
