package info.pelleritoudacity.android.rcapstone.rest;

import android.support.annotation.NonNull;

import info.pelleritoudacity.android.rcapstone.model.T3;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SubRedditExecute {
    private final SubRedditManager subRedditManager;
    private T3 mReddit;

    public SubRedditExecute(String subReddit ) {
        subRedditManager = SubRedditManager.getInstance(subReddit);
    }

    public void getData(final RestSubReddit myCallBack) {
        Callback<T3> callback = new Callback<T3>() {
            @Override
            public void onResponse(@NonNull Call<T3> call, @NonNull Response<T3> response) {
                int b = response.code();
                if (response.isSuccessful()) {
                    mReddit = response.body();
                        // todo bug retrofit
//                        mReddit.getData().getChildren().get(0).getData().getPreview().getImages().get(0).getSource().getUrl();


                    myCallBack.onRestSubReddit(mReddit);
                }
            }

            @Override
            public void onFailure(@NonNull Call<T3> call, @NonNull Throwable t) {
                Timber.e("error T3 %s",t.getMessage());
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorSubReddit(t);
                }
            }
        };
        subRedditManager.getSubRedditAPI(callback);
    }


    public void cancelRequest() {
        if (subRedditManager != null) {
            subRedditManager.cancelRequest();
        }
    }



    public interface RestSubReddit {

        void onRestSubReddit(T3 listenerData);

        void onErrorSubReddit(Throwable t);
    }
}
