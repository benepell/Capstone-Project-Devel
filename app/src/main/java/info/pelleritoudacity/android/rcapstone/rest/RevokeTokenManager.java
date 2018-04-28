package info.pelleritoudacity.android.rcapstone.rest;

import android.util.Base64;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RevokeTokenManager {

    private static RedditAPI sRevokeTokenAPI;
    private static RevokeTokenManager sRevokeTokenManager;
    private Call<String> mCall;
    private HashMap<String, String> headerMap;
    private HashMap<String, String> fieldMap;


    private RevokeTokenManager(String token, String typeToken) {

        String authString = Costants.REDDIT_CLIENT_ID + ":";
        String encodedAuthString = Base64.encodeToString(authString.getBytes(), Base64.NO_WRAP);

        headerMap = new HashMap<>();
        headerMap.put("Authorization", "Basic " + encodedAuthString);

        fieldMap = new HashMap<>();
        fieldMap.put("token", token);
        fieldMap.put("token_type_hint", typeToken);
        fieldMap.put("grant_type", "authorization_code");
        fieldMap.put("User-Agent", Costants.REDDIT_USER_AGENT);
        fieldMap.put("redirect_uri", Costants.REDDIT_REDIRECT_URL);


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

        sRevokeTokenAPI = retrofit.create(RedditAPI.class);

    }

    public static RevokeTokenManager getInstance(String token, String typeToken) {
        if (sRevokeTokenManager != null) {
            sRevokeTokenManager.cancelRequest();
        }
        sRevokeTokenManager = new RevokeTokenManager(token, typeToken);

        return sRevokeTokenManager;
    }

    public void getLoginAPI(Callback<String> callback) {
        mCall = sRevokeTokenAPI.getRevokeToken(headerMap,fieldMap);
        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
