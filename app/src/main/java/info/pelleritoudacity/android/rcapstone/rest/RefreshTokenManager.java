package info.pelleritoudacity.android.rcapstone.rest;

import android.util.Base64;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.model.reddit.RedditToken;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RefreshTokenManager {

    private static RedditAPI sRefreshTokenAPI;
    private static RefreshTokenManager sRefreshTokenManager;
    private Call<RedditToken> mCall;


    HashMap<String, String> headerMap;
    HashMap<String, String> fieldMap;


    private RefreshTokenManager(String refreshToken) {

        String authString = Costants.REDDIT_CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);

        headerMap = new HashMap<>();
        headerMap.put("Authorization", "Basic " + encodedAuthString);

        fieldMap = new HashMap<>();
        fieldMap.put("grant_type", "refresh_token");
        fieldMap.put("refresh_token", refreshToken);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costants.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costants.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costants.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Costants.REDDIT_TOKEN_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sRefreshTokenAPI = retrofit.create(RedditAPI.class);

    }

    public static RefreshTokenManager getInstance(String refreshToken) {
        if (sRefreshTokenManager != null) {
            sRefreshTokenManager.cancelRequest();
        }
        sRefreshTokenManager = new RefreshTokenManager(refreshToken);

        return sRefreshTokenManager;
    }

    public void getLoginAPI(Callback<RedditToken> callback) {
        mCall = sRefreshTokenAPI.getAccessToken(headerMap, fieldMap);
        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
