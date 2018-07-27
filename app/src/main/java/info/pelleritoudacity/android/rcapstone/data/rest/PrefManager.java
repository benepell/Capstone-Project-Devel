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

public class PrefManager {

    private static RedditAPI sPrefAPI;
    private static PrefManager sPrefManager;
    private final String mAccessToken;
    private final String mNameReddit;
    private Call<ResponseBody> mCall;

    private PrefManager(String token, String nameReddit) {

        mAccessToken = token;
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

        sPrefAPI = retrofit.create(RedditAPI.class);

    }

    public static PrefManager getInstance(String accessToken, String nameReddit) {
        if (sPrefManager != null) {
            sPrefManager.cancelRequest();
        }

        sPrefManager = new PrefManager(accessToken, nameReddit);
        return sPrefManager;
    }

    public void getPrefSaveAPI(Callback<ResponseBody> callback) {
        mCall = sPrefAPI.postPrefSave(Costant.REDDIT_BEARER + mAccessToken, mNameReddit);
        mCall.enqueue(callback);
    }

    public void getPrefUnSaveAPI(Callback<ResponseBody> callback) {
        mCall = sPrefAPI.postPrefUnSave(Costant.REDDIT_BEARER + mAccessToken, mNameReddit);
        mCall.enqueue(callback);
    }

    private void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}