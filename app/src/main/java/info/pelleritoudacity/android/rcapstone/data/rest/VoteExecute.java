package info.pelleritoudacity.android.rcapstone.data.rest;


import info.pelleritoudacity.android.rcapstone.data.rest.util.RetrofitClient;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import info.pelleritoudacity.android.rcapstone.utility.TextUtil;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VoteExecute {

    private RestAccessToken mCallback;
    private static RedditAPI sApi;
    private final String mCode;
    private final String mDir;
    private final String mId;

    public VoteExecute(RestAccessToken callback, String code, String dir, String id) {

        sApi = RetrofitClient.createService(Costant.REDDIT_OAUTH_URL, null);
        mCallback = callback;
        mCode = code;
        mDir = dir;
        mId = id;

    }

    public void postData() {

        sApi.postVote(TextUtil.authCode(mCode), mDir, mId).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                mCallback.onRestVote(response.code());
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                mCallback.onErrorVote(t);
            }
        });

    }

    public interface RestAccessToken {
        void onRestVote(int responseCode);

        void onErrorVote(Throwable t);
    }
}
