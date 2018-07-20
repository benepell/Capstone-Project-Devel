package info.pelleritoudacity.android.rcapstone.data.rest;


import android.support.annotation.NonNull;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoteExecute {
    private final VoteManager mVoteManager;
    private ResponseBody mResponseBody;

    public VoteExecute(String code, String dir, String id) {
        mVoteManager = VoteManager.getInstance(code, dir, id);

    }

    public void postData(final RestAccessToken myCallBack) {
        Callback<ResponseBody> callback = new Callback<ResponseBody>() {
            @Override
            public void onResponse(@NonNull Call<ResponseBody> call, @NonNull Response<ResponseBody> response) {
                myCallBack.onRestVote(response.code());
            }

            @Override
            public void onFailure(@NonNull Call<ResponseBody> call, @NonNull Throwable t) {
                call.cancel();
                if (call.isCanceled()) {
                    myCallBack.onErrorVote(t);
                }
            }
        };
        mVoteManager.getVoteAPI(callback);
    }

    public interface RestAccessToken {
        void onRestVote(int responseCode);

        void onErrorVote(Throwable t);
    }
}
