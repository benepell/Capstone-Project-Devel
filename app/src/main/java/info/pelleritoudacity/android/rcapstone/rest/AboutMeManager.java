package info.pelleritoudacity.android.rcapstone.rest;

import android.util.Base64;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import info.pelleritoudacity.android.rcapstone.model.RedditAboutMe;
import info.pelleritoudacity.android.rcapstone.service.RedditAPI;
import info.pelleritoudacity.android.rcapstone.utility.Costants;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AboutMeManager {

    private static RedditAPI sRedditAPI;
    private static AboutMeManager sAboutMeManager;
    private final String mAccessToken;
    private Call<RedditAboutMe> mCall;


//    HashMap<String, String> headerMap;


    private AboutMeManager(String accessToken) {

        mAccessToken = accessToken;

//        headerMap = new HashMap<>();
//        headerMap.put("User-Agent", Costants.REDDIT_USER_AGENT);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(Costants.OK_HTTP_CONNECTION_TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(Costants.OK_HTTP_CONNECTION_READ_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(Costants.OK_HTTP_CONNECTION_WRITE_TIMEOUT, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Costants.REDDIT_OAUTH_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        sRedditAPI = retrofit.create(RedditAPI.class);

    }

    public static AboutMeManager getInstance(String accessToken) {
        if (sAboutMeManager == null) {
            sAboutMeManager = new AboutMeManager(accessToken);
        }

        return sAboutMeManager;
    }

    public void getAboutMeAPI(Callback<RedditAboutMe> callback) {
        String headerAuth = Costants.REDDIT_BEARER + mAccessToken;
        mCall = sRedditAPI.getAboutMe(headerAuth);
        mCall.enqueue(callback);
    }

    public void cancelRequest() {
        if (mCall != null) {
            mCall.cancel();
        }
    }
}
