package info.pelleritoudacity.android.rcapstone.data.rest;

import android.content.Context;
import android.support.annotation.NonNull;


import java.util.List;

import info.pelleritoudacity.android.rcapstone.data.model.reddit.T1;
import info.pelleritoudacity.android.rcapstone.utility.PermissionUtil;
import info.pelleritoudacity.android.rcapstone.utility.Preference;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class SubRedditDetailExecute {
    private final SubRedditDetailManager subRedditDetailManager;
    private List<T1> mReddit;

    public SubRedditDetailExecute(Context context, String code, String subRedditName, String nameRedditId ) {
        subRedditDetailManager = SubRedditDetailManager.getInstance(context,code,subRedditName,nameRedditId,
                PermissionUtil.isLogged(context), Preference.getSubredditSort(context));
    }

    public void getData(final RestSubReddit myCallBack) {
        Callback<List<T1>> callback = new Callback<List<T1>>() {
            @Override
            public void onResponse(@NonNull Call<List<T1>> call, @NonNull Response<List<T1>> response) {
                if (response.isSuccessful()) {
                    mReddit = response.body();
                    myCallBack.onRestSubReddit(mReddit);
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<T1>> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorSubReddit(t);
                    Timber.e("VALX %s",t.getCause());
                }
            }
        };
        subRedditDetailManager.getCommentsAPI(callback);
    }


    public void cancelRequest() {
        if (subRedditDetailManager != null) {
            subRedditDetailManager.cancelRequest();
        }
    }



    public interface RestSubReddit {

        void onRestSubReddit(List<T1> listenerData);

        void onErrorSubReddit(Throwable t);
    }
}
