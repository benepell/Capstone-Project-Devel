package info.pelleritoudacity.android.rcapstone.data.rest;


import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costant;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class VoteManager {

    private static RedditAPI sVoteAPI;
    private static VoteManager sVoteManager;
    private final String mAccessToken;
    private final String mDir;
    private final String mNameReddit;
    private Call<ResponseBody> mCall;

    private VoteManager(String token, String dir, String nameReddit) {

        mAccessToken = token;
        mDir = dir;
        mNameReddit = nameReddit;

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costant.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costant.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costant.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Costant.REDDIT_OAUTH_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sVoteAPI = retrofit.create(RedditAPI.class);

    }

    public static VoteManager getInstance(String accessToken, String dir, String nameReddit) {
        if (sVoteManager != null) {
            sVoteManager.cancelRequest();
        }

        sVoteManager = new VoteManager(accessToken, dir, nameReddit);
        return sVoteManager;
    }

    public void getVoteAPI(Callback<ResponseBody> callback) {
        mCall = sVoteAPI.postVote(Costant.REDDIT_BEARER + mAccessToken, mDir,mNameReddit);
        mCall.enqueue(callback);
    }

    private void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}