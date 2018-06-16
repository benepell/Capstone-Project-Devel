package info.pelleritoudacity.android.rcapstone.rest;

import org.checkerframework.checker.nullness.qual.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

public class VoteExecute {
    private final VoteManager mVoteManager;
    private ResponseBody mResponseBody;

    public VoteExecute(String code, String dir, String id) {
        Timber.d("RESULTBEN code %s", code);
        Timber.d("RESULTBEN dir %s", dir);
        Timber.d("RESULTBEN id %s", id);

        mVoteManager = VoteManager.getInstance(code, dir, id);

    }

    public void postData(final RestAccessToken myCallBack) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                Timber.d("RESULTBEN %s", response.code());
                if (response.isSuccessful()) {

                    mResponseBody = response.body();
                    myCallBack.onRestVote(mResponseBody);
                }
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    Timber.d("RESULTBEN %s",t.getCause());
                    myCallBack.onErrorVote(t);
                }
            }
        };
        mVoteManager.getVoteAPI(callback);
    }

    public interface RestAccessToken {
        void onRestVote(ResponseBody listenerData);
        void onErrorVote(Throwable t);
    }
}
